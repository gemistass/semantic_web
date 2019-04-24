package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.jline.utils.Log;

import values.Observation;
import values.Activity;
import values.SModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;

public class RDFfile {
	private ValueFactory valuefactory;
	private ModelBuilder builder;
	private final String owlFile = "sensorActivitiesOntology1.owl";
	private final String outputRDF = "final.owl";
	private Model model;
	final private Logger LOG = LogManager.getLogger(RDFfile.class);
	
	public RDFfile() {
		this.valuefactory = SimpleValueFactory.getInstance();
		this.builder = new ModelBuilder();
		this.model = new LinkedHashModel();
		
		File f = new File("logs/logfile.log");
		f.delete();
//		f.deleteOnExit();
		

		
	}
	

	public void createRDFfile(values.SModel inputData) {
		

		Set<Resource> subjects;
		try {
			subjects = parseOntologyFile(model, owlFile);
			IRI[] IRIs = retrieveIRIs(valuefactory, subjects);
			buildModel(builder, IRIs, inputData);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildModel(ModelBuilder builder, IRI[] IRIs, SModel model) {
		IRI namespace = IRIs[0];
		IRI includes = IRIs[1];
		IRI content = IRIs[2];
		IRI end = IRIs[3];
		IRI start = IRIs[4];
		IRI activity = IRIs[5];
		IRI observation = IRIs[6];

		builder.namedGraph(namespace);
		builder.subject(includes).
		add(RDFS.DOMAIN, activity).
		add(RDFS.RANGE, observation);
		// Data properties
		builder.subject(content).
		add(RDFS.DOMAIN, activity).
		add(RDFS.DOMAIN, observation).
		add(RDFS.RANGE,XMLSchema.STRING);

		builder.subject(end).add(RDFS.DOMAIN, activity)
		.add(RDFS.DOMAIN, observation)
		.add(RDFS.RANGE,XMLSchema.DATETIME);

		builder.subject(start)
		.add(RDFS.DOMAIN, activity)
		.add(RDFS.DOMAIN, observation)
		.add(RDFS.RANGE,XMLSchema.DATETIME);

		// Classes
		builder.subject(activity)
		.add(OWL.ONPROPERTY, includes)
		.add(OWL.ALLVALUESFROM, observation);

		Activity[] act = model.getActivities();
		int k = 0, j = 0;
		for (Activity a : act) {
			LOG.info("\n\n ACTIVITY:      -----  "+a.getContent()+"    -------");
			Observation[] obs = a.getObservations();
			IRI newact = valuefactory.createIRI(namespace.getNamespace() + "act" + k++);
			Literal litstartAct = valuefactory.createLiteral(a.getStart().toString(), XMLSchema.DATETIME);
			Literal litendAct = valuefactory.createLiteral(a.getEnd().toString(), XMLSchema.DATETIME);

			builder.subject(newact)
			.add(RDF.TYPE, activity)
			
			.add(content, a.getContent())
			.add(start, litstartAct)
			.add(end, litendAct);
//			j = 0;
			for (Observation o : obs) {
				LOG.info("OBSERVATION:   "+o.getContent());
				IRI newObs = valuefactory.createIRI(namespace.getNamespace() + "obs" + j++);
				Literal litstart = valuefactory.createLiteral(o.getStart().toString(), XMLSchema.DATETIME);
				Literal litend = valuefactory.createLiteral(o.getEnd().toString(), XMLSchema.DATETIME);
				builder.add(newact, includes, newObs);

				builder.subject(newObs).add(RDF.TYPE, observation).add(content, o.getContent()).add(start, litstart)
						.add(end, litend);

				
			}

		}

		Model finalmodel = builder.build();

		try {
			FileOutputStream out = new FileOutputStream("src\\main\\resources\\"+outputRDF);
			Rio.write(finalmodel, out, RDFFormat.RDFXML);
//			Rio.write(finalmodel, System.out, RDFFormat.RDFXML);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private IRI[] retrieveIRIs(ValueFactory valuefactory, Set<Resource> subjects) {
		IRI IRIs[] = new IRI[subjects.size()];
		int i = 0;
		for (Resource s : subjects) {
			System.out.println(s.stringValue());
			IRIs[i] = valuefactory.createIRI(s.stringValue());
			i++;
		}
		return IRIs;
	}

	private Set<Resource> parseOntologyFile(Model model, final String owlFile)
			throws UnsupportedEncodingException, IOException {
		Reader reader = new InputStreamReader(Main.class.getResourceAsStream("/" + owlFile), "UTF-8");
		RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
		rdfParser.setRDFHandler(new StatementCollector(model));
		File f = new File(owlFile);
		rdfParser.parse(reader, f.getAbsolutePath());
		Set<Resource> subjects = model.subjects();
		return subjects;
	}


	public String getoutputRDF() {
		return outputRDF;
	}
	
	

}

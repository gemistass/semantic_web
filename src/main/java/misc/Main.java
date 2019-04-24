package misc;


import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;


import com.google.gson.Gson;
import values.SModel;

public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException {

		GsonBuilderFactory gbf = new GsonBuilderFactory();
		RDFfile rdffile = new RDFfile();
		Gson gson = gbf.createBuilder();
		SModel model = null;

		Reader reader = new InputStreamReader(Main.class.getResourceAsStream("/example_observations.json"), "UTF-8");
		model = gson.fromJson(reader, SModel.class);

		if (model != null)
			rdffile.createRDFfile(model);


		String serverURL = "http://localhost:7200";
		String repositoryID = "SensorRep";
		 String RDFfile = rdffile.getoutputRDF();
		 myGraphDB graphDB = new myGraphDB(serverURL,repositoryID);
		if (graphDB!=null)
			graphDB.uploadData(RDFfile);
	}

}

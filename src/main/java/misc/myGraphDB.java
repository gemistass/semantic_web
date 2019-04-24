package misc;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.jline.utils.Log;

public class myGraphDB {

	private final String SERVER_URL;
	private final String SENSOR_REPOSITORY;

	private RepositoryManager repositoryManager;
	private Repository repository;
	private RepositoryConnection connection;

	final private Logger LOG = LogManager.getLogger(myGraphDB.class);

	public myGraphDB(String serverURL,String repositoryID) {
		
		this.SERVER_URL = serverURL;
		this.SENSOR_REPOSITORY = repositoryID;
		LOG.info("SERVER_URL: "+SERVER_URL +"SENSOR_REPOSITORY"+SENSOR_REPOSITORY);
		
		repositoryManager = new RemoteRepositoryManager(SERVER_URL);
		repositoryManager.init();
		try {
			repository = repositoryManager.getRepository(SENSOR_REPOSITORY);
			connection = repository.getConnection();
		}
		catch(NullPointerException e)
		{
			
			Log.info("Please create repository with repositoryID: "+SENSOR_REPOSITORY+" at http://localhost:7200/");
			connection = null;
		}
	}

	public void uploadData(String RDFfile) {

		

		try {
			if (connection == null) return;
			connection.begin();
			InputStream str1 = Main.class.getResourceAsStream("/" + RDFfile);
			connection.add(str1, "urn:base", RDFFormat.RDFXML);
			connection.commit();
			connection.close();
			repositoryManager.shutDown();
		} catch (RDFParseException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		
	}
}

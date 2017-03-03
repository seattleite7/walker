package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.sun.net.httpserver.HttpServer;

import server.handlers.building.GetBuildingHandler;
import server.handlers.building.GetBuildingsHandler;
import server.handlers.marker.DeleteMarkerHandler;
import server.handlers.marker.GetMarkersHandler;
import server.handlers.marker.SetMarkerHandler;

public class Server {

	private final int MAX_WAITING_CONNECTIONS = 30;
	private HttpServer httpServer;
	private GetMarkersHandler getMarkersHandler;
	private SetMarkerHandler setMarkerHandler;
	private DeleteMarkerHandler deleteMarkerHandler;
	private GetBuildingsHandler getBuildingsHandler;
	private GetBuildingHandler getBuildingHandler;

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		new Server().run(args);
	}

	public void run(String[] args) throws SAXException, IOException, ParserConfigurationException {

		FirebaseOptions options = new FirebaseOptions.Builder().setServiceAccount(new FileInputStream("key.json"))
				.setDatabaseUrl("https://walker-73119.firebaseio.com/").build();
		FirebaseApp.initializeApp(options);

		int port = 8081;

		System.out.println("Port = " + port);
		try {
			httpServer = HttpServer.create(new InetSocketAddress(port), MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		httpServer.setExecutor(null);
		getMarkersHandler = new GetMarkersHandler();
		httpServer.createContext("/getMarkers", getMarkersHandler);

		setMarkerHandler = new SetMarkerHandler();
		httpServer.createContext("/setMarker", setMarkerHandler);

		deleteMarkerHandler = new DeleteMarkerHandler();
		httpServer.createContext("/deleteMarker", deleteMarkerHandler);

		getBuildingsHandler = new GetBuildingsHandler();
		httpServer.createContext("/getBuildings", getBuildingsHandler);

		getBuildingHandler = new GetBuildingHandler();
		httpServer.createContext("/getBuilding", getBuildingHandler);

		httpServer.start();

	}
}

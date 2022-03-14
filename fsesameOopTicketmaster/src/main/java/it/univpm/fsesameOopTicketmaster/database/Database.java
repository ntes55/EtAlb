/**
 * 
 */
package it.univpm.fsesameOopTicketmaster.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import it.univpm.fsesameOopTicketmaster.exceptions.IOException;
import it.univpm.fsesameOopTicketmaster.model.Events;
import it.univpm.fsesameOopTicketmaster.service.EventsInterface;

/**
 * @author steve
 *
 */

@Repository
public class Database implements EventsInterface {

	ArrayList<Events> record = new ArrayList<Events>();

	/**
	 * Il costruttore scarica il json e lo rappresenta ad oggetti autonomamente,
	 * senza doverlo richiedere esplicitamente
	 */
	Database() {
		//searchJson();
		//jsonObject();
	}

	/**
	 * Questo metodo implementa il messaggio di benvenuto dove vengono mostrate
	 * tutte le operazioni che possono essere compiute
	 */

	
	@Override
	public  ArrayList<String> welcome() {

		ArrayList<String> welcMsg = new ArrayList<>();

		welcMsg.add("In questa api puoi effetuare queste operazioni");
		welcMsg.add("Per CERCARE UN EVENTO inserire:                  path:                   Metodo richiesta: [GET]");
		welcMsg.add("Per EFFETTUARE LE STATISTICHE inserire:        path      Metodo richiesta: [GET]");
		welcMsg.add("Per OTTENERE I METADATI inserire:              path          Metodo richiesta: [GET]");
		return welcMsg;
	}

	/**
	 * Questo metodo ci permette di analizzare e scaricare gli oggetti JSON
	 * diretamente da l api usando l' url.
	 * 
	 * verifica prima se non esiste gia il file .se la risposta è true esce dal
	 * programma se no effetua le operazioni.
	 */
	private void searchJson() {
		File nf = new File("ticket.txt");
		boolean exists = nf.exists();
		if (exists) {
			return;
		}

		String apiKey = "    XTpeWYvnAIfhs0qIUxPkPSQRNbdcJ4bA   ";
		String url = " https://app.ticketmaster.com/discovery/v2/events.json?page=2&size=100&apikey=" + apiKey;
		//String url = " https://app.ticketmaster.com/discovery/v2/events.json?page=10&size=100&apikey=" + apiKey;
		try {

			URLConnection openConnection = new URL(url).openConnection();
			openConnection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			InputStream in = openConnection.getInputStream();

			String data = "";
			String line = "";
			try {
				BufferedReader buf = new BufferedReader(new InputStreamReader(in));

				while ((line = buf.readLine()) != null) {
					data += line;
				}
			} finally {
				in.close();
			}
			//Map<String, >
			// parsing del json
			JSONObject obj = (JSONObject) JSONValue.parseWithException(data);
			JSONObject objI = (JSONObject) (obj.get("_embedded"));
			JSONArray objA = (JSONArray) (objI.get("events"));
			System.out.println(objA);
//			for (Object o : objA) {
//				if (o instanceof JSONObject) {
//					JSONObject o1 = (JSONObject) o;
//					String format = (String) o1.get("format");
//					String urlD = (String) o1.get("url");
//					System.out.println(format + " | " + urlD);
//					if (format.endsWith("file-type/TXT")) {
//						System.out.println("OK");
//						scaricaJson(urlD, "tiket.txt");
//
//					}
//				}
//			}
		} catch (/*IOException| */ ParseException e) {
			System.out.println("Error!!"); // errore su IOException

			System.out.println("Parsing error!");// errore sul json parsing
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo che scarica il json contenuto neel'url dell api
	 * 
	 * @param url      contenendo il json
	 * @param fileName nome del file che verrà creato, default "file.txt
	 * @throws Exception IOException
	 */
	private void scaricaJson(String url, String fileName) throws Exception {

		HttpURLConnection openConnection = (HttpURLConnection) new URL(url).openConnection();
		openConnection.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		InputStream in = openConnection.getInputStream();
		try {
			if (openConnection.getResponseCode() >= 300 && openConnection.getResponseCode() < 400) {
				scaricaJson(openConnection.getHeaderField("Location"), fileName);
				in.close();
				openConnection.disconnect();
				return;
			}
			Files.copy(in, Paths.get(fileName));
			System.out.println("File size " + Files.size(Paths.get(fileName)));
		} finally {
			in.close();
		}
	}

	/**
	 * metodo che mi permette di creare un json object
	 * @throws java.io.IOException 
	 * @throws (IOException 
	 */
	private void jsonObject()   {
		try {	BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
		String riga = "";
		riga = reader.readLine();

String[] header = riga.split("\t"); // Legge la prima riga dove c'è l'header e lo uso sulla map
while ((riga = reader.readLine()) != null) {

		Events bObj = new Events();
		String[] appoggio;

		int differenza = 0;

		Map<String, String> eventi = new HashMap<>();
		appoggio = riga.split("\t"); // inserisco in appoggio la riga spezzata da \t per separare le colonne

		if (header.length != appoggio.length) { // valuta se una riga contiene valori fino ad una certa colonna
			differenza = header.length - appoggio.length; // e poi non ne ha più per il resto della riga
		} // se differenza!=0 si attiva l'if prima di inserire la hashmap nell'attributo
			// dell'oggetto

		// i per header(chiavi) e per appoggio1(valori)... inserisce automaticamente
		// chiavi e valori
		// basta escludere la prima colonna del file quindi per appoggio1 i=1 e per
		// header j=1. header e appoggio hanno stessa dimensione

		bObj.setMap(eventi); // INSERISCE LA MAPPA NELL'ATTRIBUTO eventi DELL'OGGETTO

		record.add(bObj);
        } 

		reader.close();
		
		}catch (java.io.IOException e) {
			
			e.printStackTrace();
		}

	}
		
		
		
	@Override
	public Map<String, String> getMetadata() {

		return getMetadata();
	}
      
	
	/**
	 * Questo metodo viene chiamato dal controllore per restituire il json contenuto
	 * nella notra ArrayList
	 * 
	 * jsonObject()
	 */
	
	@Override
	public ArrayList<Events> getEvents() {

		return record;
	}
	
	//@Override
	public RestTemplate restTemplate () {
		
		return new RestTemplate();
	}

	
	
	
	
	
	
	
	
	
	
	
}

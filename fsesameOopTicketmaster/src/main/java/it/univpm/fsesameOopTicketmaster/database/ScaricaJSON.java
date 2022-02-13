
package it.univpm.fsesameOopTicketmaster.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

/**
 * //* classe che usa l url per scaricare il JSON dalla nostra API tiketmaster
 * 
 * @author steve
 * 
 * @param nome del dato che vogliamo chiamare
 * @return ci ritorna il JSON ad un dato preciso
 */
public class ScaricaJSON {

	/**
	 *
	 * https://app.ticketmaster.com/discovery/v2/events.json?apikey=XTpeWYvnAIfhs0qIUxPkPSQRNbdcJ4bA
	 * 
	 * This method gets the JSONObject from the list_events api called on the
	 * specified path, which gives all the files inside the data
	 * 
	 * @param <JSONObject>
	 * 
	 * 
	 * @return the JSon
	 */
	public static JSONObject getJSON(Path eventsName) {

		String apiKey = "    XTpeWYvnAIfhs0qIUxPkPSQRNbdcJ4bA   ";
		final String url = " https://app.ticketmaster.com/discovery/v2/events.json?apikey=" + apiKey;

		JSONObject json = new JSONObject();

		try {
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Authorization", "Bearer                              ");
			urlConnection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			urlConnection.setDoOutput(true); // to enable the input of body
			String str = " {\"path\": " + "\"" + eventsName.toString().replace('\\', '/') + "\"" + ", \"recursive\": "
					+ true + "}";
			try (OutputStream os = urlConnection.getOutputStream()) { // to read the body with the parameters for the
																		// api
				byte[] input = str.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			json = JSONSave(urlConnection, json);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * This method gets the HttpURLConnection object created by the getJSON methods,
	 * reads what's inside it and then copies it inside a string which can be parsed
	 * inside a JSONObject
	 * 
	 * @param urlConnection the HttpURLConnection created by getJSON
	 * @param json          the JSONObject in which save the JSON
	 * @return the updated JSONObject
	 */
	private static JSONObject JSONSave(HttpURLConnection urlConnection, JSONObject json) { // common method
		String jsonString = "   ";
		String line = "  ";

		try {
			InputStream input = urlConnection.getInputStream();
			try {

				BufferedReader buff = new BufferedReader(new InputStreamReader(input));
				while ((line = buff.readLine()) != null) {
					jsonString += line; // saving the JSON inside jsonString
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				input.close();
			}
		}

		catch (Exception e) {
			System.out.println("Error!!");
			e.printStackTrace();
		}
		try {
			json = (JSONObject) JSONValue.parseWithException(jsonString); // parsing jsonString inside a JSONObject
		} catch (ParseException e) {
			System.out.println("Parsing error!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		return json; // returns the JSONObject got from the api

	}

}

package it.univpm.fsesameOopTicketmaster.database;




import com.google.gson.Gson;
import it.univpm.fsesameOopTicketmaster.model.Event;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseAlbert {

    private static final String API_KEY = "XTpeWYvnAIfhs0qIUxPkPSQRNbdcJ4bA";
    private static final Integer SIZE = 20;

    //url to the ticketMaster API with the appended API_KEY
    public static final String BASE_URL="http://app.ticketmaster.com/discovery/v2/events.json?apikey=" + API_KEY;


    public List<Event> getAllCountryEvents(String countryCode) throws IOException, ParseException, InterruptedException {

        ArrayList<Event> countryEvents = new ArrayList<>();
        if (Objects.equals(countryCode, "") || countryCode == null) {
            return countryEvents;
        }

        Long totalEvent = getNumberEventsCountry(countryCode);

        long pages = totalEvent/SIZE + totalEvent%SIZE;
        for (int page = 0; page < pages; page++) {
            countryEvents.addAll(getCountryEventsPage(countryCode, page, SIZE));
        }
        return countryEvents;
    }



    public List<Event> getAUEventsPage(int page, int size) throws Exception {
        return getCountryEventsPage("au", page, size);
    }

    public List<Event> getCountryEventsPage(String countryCode, int page, int size) throws IOException, InterruptedException {

        ArrayList<Event> countryEvents = new ArrayList<>();
        if (Objects.equals(countryCode, "") || countryCode == null) {
            return countryEvents;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+ "&page=" + page + "&size=" + size + "&countryCode=" + countryCode.toLowerCase()))
                .GET() // GET is default
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Map<String, String> rawResponse = new Gson().fromJson(response.body(), HashMap.class);
        String embeddedReader = new Gson().toJson(rawResponse.get("_embedded"));
        Map<String, String> embeddedResponse = new Gson().fromJson(embeddedReader, HashMap.class);
        String eventsReader = new Gson().toJson(embeddedResponse.get("events"));
        ArrayList<String> events = new Gson().fromJson(eventsReader, ArrayList.class);
        for (int i = 0; i < events.size(); i++) {
            String eventReader = new Gson().toJson(events.get(i));
            Map<String, String> eventKeyValues = new Gson().fromJson(eventReader, HashMap.class);
            countryEvents.add(new Event(
                    eventKeyValues.get("name"),
                    eventKeyValues.get("id"),
                    eventKeyValues.get("type")
                    //eventKeyValues.get("dates")
                    ));
        }
        return countryEvents;
    }

    public Long getNumberEventsCountry(String countryCode) throws IOException, InterruptedException, ParseException {

        ArrayList<Event> countryEvents = new ArrayList<>();
        if (Objects.equals(countryCode, "") || countryCode == null) {
            return 0L;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+ "&countryCode=" + countryCode.toLowerCase()))
                .GET() // GET is default
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject obj = (JSONObject) JSONValue.parseWithException(response.body());
        JSONObject embeddedObject = (JSONObject) (obj.get("_embedded"));
        JSONObject pageObject = (JSONObject) (obj.get("page"));
        return (Long) pageObject.get("totalElements");
    }
}

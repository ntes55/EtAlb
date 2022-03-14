package it.univpm.fsesameOopTicketmaster.controller;

import it.univpm.fsesameOopTicketmaster.database.DatabaseAlbert;
import it.univpm.fsesameOopTicketmaster.model.Event;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.univpm.fsesameOopTicketmaster.service.EventsInterface;

import java.util.ArrayList;
import java.util.List;


@RestController
@ControllerAdvice
public class GeneralController {

	@Autowired
	EventsInterface eventsInterface;

	DatabaseAlbert databaseAlbert = new DatabaseAlbert();

	/**
	 * Metodo che mostra tutte le operazioni possibili
	 * @return
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ResponseEntity<Object> welcome(){
		return new ResponseEntity<>(eventsInterface.welcome(), HttpStatus.OK);	
	}
	
	/**
	 * Metodo che restituisce i dati
	 * @return oggetto costituito da vettore di oggetti e HttpStatus.OK
	 */
	@RequestMapping(value="/events", method=RequestMethod.GET)
	public ResponseEntity<Object> getEvents(){
		return new ResponseEntity<>(eventsInterface.getEvents(), HttpStatus.OK);	
	}


	@GetMapping("/all/events/country")
	public List<Event> getAllEventsCountry(@RequestParam("code") String code) throws Exception {
		return databaseAlbert.getAllCountryEvents(code);
	}

	@GetMapping("/events/number")
	public Long getNumbersOfEventInAU(@RequestParam("code") String code) throws Exception {
		return databaseAlbert.getNumberEventsCountry(code);
	}

	@GetMapping("/events/australia")
	public List<Event> getEventsAustralia() throws Exception {
		return databaseAlbert.getAUEventsPage(0, 50);
	}
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	


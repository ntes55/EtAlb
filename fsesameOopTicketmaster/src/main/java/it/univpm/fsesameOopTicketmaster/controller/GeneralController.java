package it.univpm.fsesameOopTicketmaster.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.fsesameOopTicketmaster.service.EventsInterface;



@RestController
@ControllerAdvice

public class GeneralController {
	@Autowired
	
	EventsInterface eventsInterface;
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

	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	


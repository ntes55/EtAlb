/**
 * 
 */
package it.univpm.fsesameOopTicketmaster.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.univpm.fsesameOopTicketmaster.model.Events;

/**
 * @author steve
 *
 */


@Service



public interface EventsInterface {

	
	
	public   abstract Map<String, String> getMetadata() ;
	
	public  abstract ArrayList<String> welcome() ;
		
	
	
	public abstract ArrayList<Events>  getEvents() ;
	
	
	
	
	
	
	public abstract RestTemplate restTemplate(); 
	
	
	} 
		
		
	
		
		
	
	
	
	

	
	
	
	
	
	

	
	
	
	
	
	


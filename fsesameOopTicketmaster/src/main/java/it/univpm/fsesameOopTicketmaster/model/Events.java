/**
 * 
 */
package it.univpm.fsesameOopTicketmaster.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

/**
 * @author steve
 *
 */

@Configuration

public class Events {

	// nome dell evento
	private String nameEvnt;

	// tipo di evento
	private String typeEvnt;

	// luogo evento
	private String localeEvnt;

	// id dell evento
	private String idEvnt;

	// ArrayList degli stati dove andremmo ad analizzare gli eventi
	private ArrayList<Stato> sti;

	/**
	 * questa struttura dati rappresenta tutti gli eventi contenuti dal json
	 * dell'api mappa<key,valor>
	 */
	private Map<String, String> eventi = new HashMap<>();
	



	public String getNameEvnt() {
		return nameEvnt;
	}

	public void setNameEvnt(String nameEvnt) {
		this.nameEvnt = nameEvnt;
	}

	public String getTypeEvnt() {
		return typeEvnt;
	}

	public void setTypeEvnt(String typeEvnt) {
		this.typeEvnt = typeEvnt;
	}

	public String getLocaleEvnt() {
		return localeEvnt;
	}

	public void setLocaleEvnt(String localeEvnt) {
		this.localeEvnt = localeEvnt;
	}

	public String getIdEvnt() {
		return idEvnt;
	}

	public void setIdEvnt(String idEvnt) {
		this.idEvnt = idEvnt;
	}

	public ArrayList<Stato> getSti() {
		return sti;
	}

	public void setSti(ArrayList<Stato> sti) {
		this.sti = sti;
	}

	public Map<String, String> getEventi() {
		return eventi;
	}

	public void setEventi(Map<String, String> eventi) {
		this.eventi = eventi;
	}

	public void setMap(Map<String, String> eventi) {
		this.eventi= eventi;
	}
	
	
}

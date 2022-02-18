/**
 * 
 */
package it.univpm.fsesameOopTicketmaster.model;

/**
 * @author steve
 *
 */
public class EventGenre {


	
	   // nome del genere
		private String nameGre;

		

		// id del genere
		private String idGre;



		public EventGenre(String nameGre, String idGre) {
			super();
			this.nameGre = nameGre;
			this.idGre = idGre;
		}



		public String getNameGre() {
			return nameGre;
		}



		public void setNameGre(String nameGre) {
			this.nameGre = nameGre;
		}



		public String getIdGre() {
			return idGre;
		}



		public void setIdGre(String idGre) {
			this.idGre = idGre;
		}

	
		
}

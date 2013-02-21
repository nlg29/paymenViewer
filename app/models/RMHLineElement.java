package models;

public class RMHLineElement {
	
	String label;
	String value ="";
	String commentaire ="";
	String categorie;
	boolean statusValide = false;
		
	public RMHLineElement(String label, String categorie) {
		this.label = label;
		this.categorie = categorie;
	}
	
	
}

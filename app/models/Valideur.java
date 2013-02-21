package models;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * A FAIRE 
 *	Ajouter l'ensemble des controls sur les champs vide.
 *  
 */

public class Valideur {
	
	    private static final BigDecimal ibanCheckingConstant = new BigDecimal(97); // algo iban depuis wikipedia
	    private static final String[] listedep =  new String[] {"001","002","003","004","005","006","007","008","009","010","011","012","013","014","015","016","017","018","019","02A","02B","021","022","023","024","025","026","027","028","029","030","031","032","033","034","035","036","037","038","039","040","041","042","043","044","045","046","047","048","049","050","051","052","053","054","055","056","057","058","059","060","061","062","063","064","065","066","067","068","069","070","071","072","073","074","075","076","077","078","079","080","081","082","083","084","085","086","087","088","089","090","091","092","093","094","095","971","972","973","974","976"};
	
	    boolean adresseT = false;
	    boolean adresseB = false;
	    
	    
	    public boolean checkTrainDeVirementNumeroDep(RMHLineElement element, String value){
	    	
	    	element.value = value;
	    	if (Arrays.asList(listedep).contains(value))
	    		element.statusValide = true;
	    	else{
		    	if (value.equals(""))
		    		element.value = "vide";
		    	else
		    		element.commentaire = "Le numéro de département n'est pas connu";
	    		return false;
	    	}
	    	return true;
	    }

		public boolean checkTrainDeVirementCodeCollectivite(RMHLineElement element, String value) {
			element.value = value;
	    
			if (!value.equals(""))
	    		element.statusValide = true;
	    	else{
	    		element.commentaire = "Le code collectivité est vide";
	    		element.value = "vide";
	    		return false;
	    	}
	    	return true;
		}
		
		public boolean checkTrainDeVirementNumeroTrain(RMHLineElement element, String value) {
			
	    	if (!value.equals(""))
	    		element.statusValide = true;
	    	else{
	    		element.commentaire = "Le numéro de train de virement est vide, remplacement par 000";
	    		element.value="000";
	    		return false;
	    	}
	    	
	    	element.value = value;
	    	return true;
		}
		
		public boolean checkTrainDeVirementCodeNatureDepense(RMHLineElement element, String value) {
			
			if (!value.equals(""))
	    		element.statusValide = true;
	    	else{
	    		element.value="vide";
	    		return false;
	    	}
	    	
	    	element.value = value;
	    	return true;
		}
		
		public boolean checkRefBeneficiaireMatricule(RMHLineElement element, String value) {
			
			element.value = value;
			
	    	if (!value.equals(""))
	    		element.statusValide = true;
	    	else{
	    		element.commentaire = "Le matricule du bénéficiaire est vide";
	    		element.value = "vide";
	    		return false;
	    	}
	    	return true;
		}
		
		public boolean checkMonnaieMontant(RMHLineElement element, String value) {
			 
			element.value = value;
			
			Pattern p = Pattern.compile("\\d{16}");
			Matcher m = p.matcher(value);
			
			if (m.matches())
	    		element.statusValide = true;
	    	else{
	    		element.commentaire = "Le montant est mal formé (16 chiffres) :";
	    		return false;
	    	}
	    	return true;
		}
		
		public boolean checkMonnaieCode(RMHLineElement element, String value) {
	    	
			element.value = value;
			
			if (value.equals("E"))
	    		element.statusValide = true;
	    	else{
	    		if (value.equals("")){
	    			element.value="vide";
	    		}
	    		element.commentaire = "Le code monnaie est différent de 'E'";
	    		return false;
	    	}
	    	return true;
		}
		
		public boolean checkRefMandatExercice(RMHLineElement element, String value) {
			//aucun test nécessaire ?
			
			element.statusValide = true;
			if (value.equals(""))
	    		element.value = "vide";
			else
				element.value = value;
	    	return true;
		}
		
		public boolean checkRefMandatBordereaux(RMHLineElement element, String value) { //facultatif
			//aucun test nécessaire ?
			element.statusValide = true;
			if (value.equals(""))
	    		element.value = "vide";
			else
				element.value = value;
	    	return true;
		}
		
		public boolean checkRefMandatNumMandat(RMHLineElement element, String value) { //facultatif
			//aucun test nécessaire ?
			element.statusValide = true;
			if (value.equals(""))
	    		element.value = "vide";
			else
				element.value = value;
	    	return true;
		}
		
		public boolean checkRefBancDomiciliation(RMHLineElement element, String value) { //facultatif
			//aucun test nécessaire ?
			element.statusValide = true;
			if (value.equals(""))
	    		element.value = "vide";
			else
				element.value = value;
	    	return true;
		}
		
		public boolean CheckRefBancBIC(RMHLineElement element, String value) {
			//aucun test nécessaire ?
			element.statusValide = true;
			if (value.equals(""))
	    		element.value = "vide";
			else
				element.value = value;
	    	return true;
		}
		
		public boolean CheckRefBancIBAN(RMHLineElement element, String value) { // A compléter
			
			// Ici est uniquement testé la valeur de l'iban.
			// Si l IBAN n'est pas renseigné, alors on doit trouver un mini RIB. cf doc.
			if (value.equals(""))
	    		element.value = "vide";
			else{
				element.value = value.trim();
				value = value.trim();
				
				StringBuffer sbIban = new StringBuffer(value.substring(4));
		        sbIban.append(value.substring(0, 4));
		        value = sbIban.toString();
		 
		        StringBuilder extendedIban = new StringBuilder(value.length());
		        for(char currentChar : value.toCharArray()){
		            extendedIban.append(Character.digit(currentChar,Character.MAX_RADIX));
		        }
		        if (new BigDecimal(extendedIban.toString()).remainder(ibanCheckingConstant ).intValue() != 1){
		        	element.commentaire = "Le code IBAN est mal formé";
		        	return false;
		        }
				element.statusValide = true;
				return true;
			}
			return false;
		}
		
		public boolean CheckRefBancTitulaireNomPrenom(RMHLineElement element, String value) {  // A compléter
			//ici faire test si nom et prénom sont bien séparé (regexp de char avec espace.
			element.statusValide = true;
			if (value.equals(""))
	    		element.value = "vide";
			else
				element.value = value;
	    	return true;
		}
		
		public boolean CheckRefBancTitulaireAdresse(RMHLineElement element, String value) {  
			element.statusValide = true;
	    	element.value = value;
	    	
	       	if (value.equals("")){
	       		adresseT = false;
	       		element.value = "vide";
	       	}
	    	else adresseT = true;
    		return true;
		}
		
		public boolean CheckRefBancTitulairePays(RMHLineElement element, String value) {
			
			element.value = value;
			if (value.equals("") && adresseT){
				element.commentaire = "Le pays doit être renseigné quand l'adresse est précisée";
				element.value = "vide";
				return false;
			}
			element.statusValide = true;
	    	return true;
		}
		
		public boolean CheckRefBancTitulaireId(RMHLineElement element, String value) { //facultatif
			element.statusValide = true;
	    	element.value = value;
	    	return true;
		}
		
		public boolean CheckRefBancBeneficiaireNomPrenom(RMHLineElement element, String value) {  // A facultatif
			
			element.statusValide = true;
	    	element.value = value;
	    	return true;
		}
		
		public boolean CheckRefBancBeneficiaireAdresse(RMHLineElement element, String value) {  
			element.statusValide = true;
	    	element.value = value;
	    	
	       	if (value.equals(""))    		adresseB = false;
	    	else				    		adresseB = true;
    		return true;
		}
		
		public boolean CheckRefBancBeneficiairePays(RMHLineElement element, String value) {
			
			element.value = value;
			if (value.equals("") && adresseB){
				element.commentaire = "Le pays doit être renseigné quand l'adresse est précisée";
				return false;
			}
			element.statusValide = true;
	    	return true;
		}
		
		public boolean CheckRefBancBeneficiaireId(RMHLineElement element, String value) { //facultatif
			element.statusValide = true;
	    	element.value = value;
	    	return true;
		}
		
		public boolean CheckRefBancLibelleOp(RMHLineElement element, String value) { //facultatif
			element.statusValide = true;
	    	element.value = value;
	    	return true;
		}
		
		public boolean CheckRefBancLibelleComp(RMHLineElement element, String value) { //facultatif
			element.statusValide = true;
	    	element.value = value;
	    	return true;
		}
		
		public boolean CheckRefBancFiller(RMHLineElement element, String value) { //facultatif
			element.statusValide = true;
	    	element.value = value;
	    	return true;
		}
		
}
	


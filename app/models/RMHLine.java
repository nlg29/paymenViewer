package models;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * Objet représentant une ligne d'un fichier RMH
 *  circulaire numéro NOR BUDE1228094C 
 *  
 *  A FAIRE : //////////////////////////
 *  	Menage de l'ensemble du code. Retourner V elementLigne dans une tableau pour rendre plus léger le code groovy
 */

public class RMHLine {

	static Valideur valideur = new Valideur();
	
	static String catTrain = "Référence train de virement";
	static String catMatbene = "Matricule du bénéficiaire";
	static String catMonnaie = "Monnaie";
	static String catMandat = "Références Mandat";
	static String catBank = "Références bancaires";
	
	static String labelTrainDeVirementNumeroDep =  "Numéro département";
	static String labelTrainDeVirementCodeCollectivite =  "Code collectivité";
	static String labelTrainDeVirementNumeroTrain =  "Numéro train";
	static String labelTrainDeVirementCodeNatureDepense =  "Code nature dépense";
	static String labelRefBeneficiaireMatricule =  "Matricule";
	static String labelMonnaieCode =  "Code";
	static String labelMonnaieMontant =  "Montant";
	static String labelRefMandatExercice =  "Exercice";
	static String labelRefMandatBordereaux =  "Bordereaux";
	static String labelRefMandatNumMandat =  "Numéro";
	static String labelRefBancBIC =  "Bic";
	static String labelRefBancIBAN =  "Iban";
	static String labelRefBancDomiciliation =  "Domicilation";
	static String labelRefBancTitulaireNomPrenom =  "Nom et prénom du titulaire";
	static String labelRefBancTitulaireAdresse =  "Adresse du titulaire";
	static String labelRefBancTitulairePays =  "Pays du titulaire";
	static String labelRefBancTitulaireId =  "Identification du titulaire";
	static String labelRefBancBeneficiaireNomPrenom =  "Nom et prénom du bénéficiaire";
	static String labelRefBancBeneficiaireAdresse =  "Adresse du bénéficiaire";
	static String labelRefBancBeneficiairePays =  "Pays du bénéficiaire";
	static String labelRefBancBeneficiaireId =  "Identification du bénéficiaire";
	static String labelRefBancLibelleOp =  "Libellé de l'opération";
	static String labelRefBancLibelleComp =  "Libellé complémentaire";
	static String labelRefBancFiller =  "Filler (normalement vide)";
	
	RMHLineElement  trainDeVirementNumeroDep = new RMHLineElement(labelTrainDeVirementNumeroDep,catTrain);
	RMHLineElement  trainDeVirementCodeCollectivite = new RMHLineElement(labelTrainDeVirementCodeCollectivite,catTrain);
	RMHLineElement  trainDeVirementNumeroTrain = new RMHLineElement(labelTrainDeVirementNumeroTrain,catTrain);
	RMHLineElement  trainDeVirementCodeNatureDepense = new RMHLineElement(labelTrainDeVirementCodeNatureDepense,catTrain);
	RMHLineElement  refBeneficiaireMatricule = new RMHLineElement(labelRefBeneficiaireMatricule,catMatbene);
	RMHLineElement  monnaieCode = new RMHLineElement(labelMonnaieCode,catMonnaie);
	RMHLineElement  monnaieMontant = new RMHLineElement(labelMonnaieMontant,catMonnaie);
	RMHLineElement  refMandatExercice = new RMHLineElement(labelRefMandatExercice,catMandat);
	RMHLineElement  refMandatBordereaux = new RMHLineElement(labelRefMandatBordereaux,catMandat);
	RMHLineElement  refMandatNumMandat = new RMHLineElement(labelRefMandatNumMandat,catMandat);
	RMHLineElement  refBancBIC = new RMHLineElement(labelRefBancBIC,catBank);
	RMHLineElement  refBancIBAN = new RMHLineElement(labelRefBancIBAN,catBank);
	RMHLineElement  refBancDomiciliation = new RMHLineElement(labelRefBancDomiciliation,catBank);
	RMHLineElement  refBancTitulaireNomPrenom = new RMHLineElement(labelRefBancTitulaireNomPrenom,catBank);
	RMHLineElement  refBancTitulaireAdresse = new RMHLineElement(labelRefBancTitulaireAdresse,catBank);
	RMHLineElement  refBancTitulairePays = new RMHLineElement(labelRefBancTitulairePays,catBank);
	RMHLineElement  refBancTitulaireId = new RMHLineElement(labelRefBancTitulaireId,catBank);
	RMHLineElement  refBancBeneficiaireNomPrenom = new RMHLineElement(labelRefBancBeneficiaireNomPrenom,catBank);
	RMHLineElement  refBancBeneficiaireAdresse = new RMHLineElement(labelRefBancBeneficiaireAdresse,catBank);
	RMHLineElement  refBancBeneficiairePays = new RMHLineElement(labelRefBancBeneficiairePays,catBank);
	RMHLineElement  refBancBeneficiaireId = new RMHLineElement(labelRefBancBeneficiaireId,catBank);
	RMHLineElement  refBancLibelleOp = new RMHLineElement(labelRefBancLibelleOp,catBank);
	RMHLineElement  refBancLibelleComp = new RMHLineElement(labelRefBancLibelleComp,catBank);
	RMHLineElement  refBancFiller = new RMHLineElement(labelRefBancFiller,catBank);
	
	String ligne;									// Ligne complete non parsée
	int num;										// Numéro de ligne dans le fichier global
	boolean ligneValide;							// Status de la ligne (si err ds taille ou contenu alors invalide :) )
	String commentaire = "";						// Commentaire pour afficher les erreurs eventuels ( <> commentaire par elements de ligne)
	List rmhLineElementList = new ArrayList<RMHLineElement>();	// Liste de l'ensemble des éléments constitutifs de la ligne

	
	public RMHLine(String ligne, Integer num) {
		this.ligne = ligne;
		this.num = num;
		
		if (!feedAndCheckValues()){
			if (!ligneValide)			commentaire = "La ligne n'est pas complète, et contient des erreurs";
			else						commentaire = "La ligne n'est pas complète";
			ligneValide= false;
		}
		else if (!ligneValide)			commentaire = "La ligne contient des erreurs";	

	}
	
	private boolean feedAndCheckValues() {
		
		ligneValide = true;
		
		if ( (ligne.length()+1) <= 3)	return false;
		if (!valideur.checkTrainDeVirementNumeroDep(trainDeVirementNumeroDep, ligne.substring(0, 3)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 7)	return false;
		if (!valideur.checkTrainDeVirementCodeCollectivite(trainDeVirementCodeCollectivite, ligne.substring(3, 7)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 10)	return false;
		if (!valideur.checkTrainDeVirementNumeroTrain(trainDeVirementNumeroTrain, ligne.substring(7, 10)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 13)	return false;
		if (!valideur.checkTrainDeVirementCodeNatureDepense(trainDeVirementCodeNatureDepense, ligne.substring(10, 13)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 28)	return false;
		if (!valideur.checkRefBeneficiaireMatricule(refBeneficiaireMatricule, ligne.substring(13, 28)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 29)	return false;
		if (!valideur.checkMonnaieCode(monnaieCode, ligne.substring(28, 29)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 45)	return false;
		if (!valideur.checkMonnaieMontant(monnaieMontant, ligne.substring(29, 45)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 49)	return false;
		if (!valideur.checkRefMandatExercice(refMandatExercice, ligne.substring(45, 49)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 56)	return false;
		if (!valideur.checkRefMandatBordereaux(refMandatBordereaux, ligne.substring(49, 56)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 64)	return false;
		if (!valideur.checkRefMandatNumMandat(refMandatNumMandat, ligne.substring(56, 64)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 88)	return false;
		if (!valideur.checkRefBancDomiciliation(refBancDomiciliation, ligne.substring(64, 88)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 99)	return false;
		if (!valideur.CheckRefBancBIC(refBancBIC, ligne.substring(88, 99)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=133)	return false;
		if (!valideur.CheckRefBancIBAN(refBancIBAN, ligne.substring(99, 133)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=203)	return false;
		if (!valideur.CheckRefBancTitulaireNomPrenom(refBancTitulaireNomPrenom, ligne.substring(133, 203)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=343)	return false;
		if (!valideur.CheckRefBancTitulaireAdresse(refBancTitulaireAdresse, ligne.substring(203, 343)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=345)	return false;
		if (!valideur.CheckRefBancTitulairePays(refBancTitulairePays, ligne.substring(343, 345)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=380)	return false;
		if (!valideur.CheckRefBancTitulaireId(refBancTitulaireId, ligne.substring(345, 380)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=450)	return false;
		if (!valideur.CheckRefBancBeneficiaireNomPrenom(refBancBeneficiaireNomPrenom, ligne.substring(380, 450)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=590)	return false;
		if (!valideur.CheckRefBancBeneficiaireAdresse(refBancBeneficiaireAdresse, ligne.substring(450, 590)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=592)	return false;
		if (!valideur.CheckRefBancBeneficiairePays(refBancBeneficiairePays, ligne.substring(590, 592)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <=627)	return false;
		if (!valideur.CheckRefBancBeneficiaireId(refBancBeneficiaireId, ligne.substring(592, 627)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 767)	return false;
		if (!valideur.CheckRefBancLibelleOp(refBancLibelleOp, ligne.substring(627, 767)))
			ligneValide = false;
		
		if ( (ligne.length()+1) <= 793)	return false;
		if (!valideur.CheckRefBancLibelleComp(refBancLibelleComp, ligne.substring(767, 793)))
			ligneValide = false;
		
		if ( (ligne.length()+1) != 994)	return false;
		if (!valideur.CheckRefBancFiller(refBancFiller, ligne.substring(793, 993)))
			ligneValide = false;

		return true;
	}

}

package models;

/*
 * 
 * Classe receptionnant et incluant le fichier RMH
 * Contient un jeu de ligne RMHLine représentant l'ensemble des opérations à réaliser 
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.Boolean;

public class RMHFile {
	
	File source;
	String name;
	List rmhLineList = new ArrayList<RMHLine>();
	Integer nbrLigneValide;
	Integer nbrLigneErreur;
	Integer nbrLigne;
	
	public RMHFile() {
	}
	
	public void chargeFile(Boolean statut, Integer position){
		try{
			BufferedReader buff = new BufferedReader(new FileReader(source));
			try {
				String line;
				nbrLigne = 1;
				RMHLine rmhLine;
				rmhLineList.clear();
				
				while ((line = buff.readLine()) != null){
					if (nbrLigne >= position && rmhLineList.size() <= position+50){
						rmhLine = new RMHLine(line, nbrLigne);
						if (statut == null)
							rmhLineList.add(rmhLine);
						else
							if (statut == rmhLine.ligneValide)
								rmhLineList.add(rmhLine);
					}
					nbrLigne++;
				}
			} finally {
				buff.close();
			}
		} catch (IOException exc) {}
	}

	public void analyseFile(){	// Fait un CR des ligne en erreur	
		nbrLigneErreur = 0;
		nbrLigneValide = 0;
		RMHLine rmhLine;
		try{
			BufferedReader buff = new BufferedReader(new FileReader(source));
			try {
				String line;
				nbrLigne = 1;
				nbrLigneValide = 0;
				nbrLigneErreur = 0;
				while ((line = buff.readLine()) != null){
					rmhLine = new RMHLine(line, nbrLigne);
					if (rmhLine.ligneValide)
						nbrLigneValide++;
					else
						nbrLigneErreur++;
					nbrLigne++;
				}
			} finally {
				buff.close();
			}
		} catch (IOException exc) {
			System.out.println(exc.toString());
		}
	}
	
	public File getSource() {
		return source;
	}

	public void setSource(File source) {
		this.source = source;
	}
	
	public List getRMHLineList() {
		return rmhLineList;
	}

	public void setRMHLineList(List rmhLineList) {
		this.rmhLineList = rmhLineList;
	}


	public void setName(String name) {
		this.name = name;
	}

}

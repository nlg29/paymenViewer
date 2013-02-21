package controllers;

import play.*;
import play.libs.Files;
import play.mvc.*;

import java.io.File;
import java.util.*;

import models.*;

public class Application extends Controller {

	static RMHFile rmhFile = new RMHFile();
	
    public static void index() {
    	render();
    }
    
    public static void showDoc(Boolean statut, Integer position){
    	if (position == null)
    		position = 1;
    	rmhFile.chargeFile(statut, position);
    	List<RMHLine> rmhLines = rmhFile.getRMHLineList();
    	render(rmhLines, position, statut);
    }
    
    
    
    public static void showResume(){
    	rmhFile.analyseFile();
    	renderArgs.put("rmhFile", rmhFile);
    	render();
    }
    
    public static void upload(File fInterfacePayMen){
    	notFoundIfNull(fInterfacePayMen);
    	
    	File fichierStocke = Play.getFile("data/" + fInterfacePayMen.getName());
        try {
            Files.copy(fInterfacePayMen, fichierStocke);
        } catch(RuntimeException e) {
            Play.getFile("data/").mkdir();
            Files.copy(fInterfacePayMen, fichierStocke);
        }
    	rmhFile.setName(fInterfacePayMen.getName());
    	rmhFile.setSource(fichierStocke);
    	showResume();
    }

}

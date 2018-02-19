package arcanoTpong;

public class Controleur {
	static Vue view = new Vue();
	static Moteur model = new Moteur(1,80*5,30*20);
	
	 public static void main(String args[]){
		 
		    for(int i= 0; i<model.getInGRaquettes().length;i++){
		    	view.afficheScore(model.getInGRaquettes()[i].getScore());
		    }
		    
		    view.setListeBriques(model.getInGBriquesClean());
		    view.setListeBalles(model.getInGBalles());
		    view.setListeRaquettes(model.getInGRaquettesArrayListe());
		    /*
		    Brique deadB = model.killBrique(2,1,model.getInGRaquettes()[0]);
		    view.enleveBrique(deadB);
		    */
		    
		    view.afficheScore(model.getInGRaquettes()[0].getScore());
		    
		    
		    
	 }
}

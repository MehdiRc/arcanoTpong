package arcanoTpong;

public class Controleur {
	static Vue view = new Vue();
	static Moteur model = new Moteur(5,3,1);
	
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

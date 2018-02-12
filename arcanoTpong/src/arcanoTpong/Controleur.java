package arcanoTpong;

public class Controleur {
	static Vue view = new Vue();
	static Moteur model = new Moteur(5,3,1);
	
	 public static void main(String args[]){
		 
		    view.afficheScore(45);
		    for(Raquette r:model.getInGRaquettes()){
		    	view.afficheScore(r.getScore());
		    }
		    
		    view.setListeBriques(model.getInGBriquesClean());
		    
		    
		    
	 }
}

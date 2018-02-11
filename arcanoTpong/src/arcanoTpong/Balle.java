package arcanoTpong;

public class Balle extends Forme{
	int joueur;
	
	public Balle(){
		super();
		this.joueur=1;
		
		this.largeur=20;
		this.hauteur=20;
		
		this.vecteurY=1;
		this.vecteurX=0;
		
		this.x=200;
		this.y=200;
		
	}
	
	public Balle(int player){
		super();
		this.joueur=player;
		
		this.largeur=20;
		this.hauteur=20;

		this.vecteurY=1;
		this.vecteurX=0;
		
		this.x=200;
		this.y=200;
		
	}
	
	public int getJoueur(){
		return this.joueur;
	}
	
	public void rebond(){ // NOT IMPLEMENTED YET, A FAIRE 
		this.vecteurY= -1*vecteurY;
		this.vecteurX= -1*vecteurX;
	}

}

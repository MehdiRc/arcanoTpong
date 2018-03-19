package arcanoTpong;

import java.util.Timer;
import java.util.TimerTask;



public class Balle extends Forme{
	int joueur;
	Timer t = new Timer();
	
	public Balle(int x, int y){
		super();
		
		this.joueur=1;
		
		this.largeur=20;
		this.hauteur=20;
		
		this.vecteurY=-2;
		this.vecteurX=2;
		
		this.x=x-this.largeur/2;
		this.y=y-this.hauteur/2;
		
		Balle b= this;
		t.schedule(new TimerTask(){
			@Override
			public void run(){
				Moteur.collisionBords(b);
				updateBallePosition();
			}
		},0, 20);
	}
	
	public Balle(int player){
		super();
		this.joueur=player;
		
		this.largeur=20;
		this.hauteur=20;

		this.vecteurY=-2;
		this.vecteurX=2;
		
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
	
	public void checkHit(){
		
	}
	
	public void updateBallePosition(){
		this.x= this.x+ this.vecteurX;
		this.y= this.y+ this.vecteurY;
		}

}




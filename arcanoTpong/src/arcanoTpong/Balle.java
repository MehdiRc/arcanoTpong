package arcanoTpong;

import java.util.Timer;
import java.util.TimerTask;



public class Balle extends Forme{
	private int joueur;
	
	private int vitesseMin;
	private int vitesseMax;
	
	private int vitesse;
	
	private static Raquette startingR;
	private boolean collee;
	
	private Timer t = new Timer();
	
	/**
	 * Cree une nouvelle balle comme specifie
	 * @param x
	 * @param y
	 * @param player
	 * @param difficulte
	 */
	public Balle(int x, int y, int player, int difficulte){
		super();
		this.joueur = player;
		
		
		//La vitesse min et max de la balle change en fonction de la difficulte du niveau
		switch(difficulte) {
		case 0:
			this.vitesseMin=0;
			this.vitesseMax=5;
			break;
		case 1:
			this.vitesseMin=2;
			this.vitesseMax=6;
			break;
		case 2:
			this.vitesseMin=4;
			this.vitesseMax=7;
			break;
		default:
			this.vitesseMin=0;
			this.vitesseMax=5;
			break;
	}
		
		
		this.vitesse=vitesseMin;
		
		this.collee=false; 
		
		this.largeur=20;
		this.hauteur=20;
		
		this.vecteurY=(float) -2;
		this.vecteurX=(float) 2;
		
		this.x=x-this.largeur/2;
		this.y=y-this.hauteur/2;
		
		
		// Timer de la balle
		Balle b= this;
		t.schedule(new TimerTask(){
			@Override
			public void run(){
				if(!Moteur.getGameOver() && !Moteur.getPause()){ // conditions pour stoper la balle en cas de pause ou de gameover
				Moteur.collisionBalleRaquettes(b); // verification des collisions avec la raquette 
				Controleur.CheckIfRemoveBrique(Moteur.collisionBalleBriques(b)); // verification des collisions avec les briques
				Moteur.collisionBords(b); // verification des collisions avec les bords
				b.updatePosition(); // deplacement de la balle
				}
			}
		},0, (10-vitesse)); // le taux de rafraichissement du timer determine la vitesse de deplacement de la balle 
	}
	

	/**
	 * Cree la balle de debut de partie, ratachee a la raquette et qui se lance avec un clic
	 * @param r Raquette, la raquette a laquelle est ratachee la balle de depart
	 * @param difficulte, entier, la difficulte du niveau en cours
	 */
	public Balle(Raquette r, int difficulte){
		super();
		
		this.startingR = r;
		this.joueur = r.getJoueur();
		
		this.collee=true;  // la balle est collee a la raquette r 
		
		//La vitesse min et max de la balle change en fonction de la difficulte du niveau
		switch(difficulte) {
		case 0:
			this.vitesseMin=0;
			this.vitesseMax=5;
			break;
		case 1:
			this.vitesseMin=2;
			this.vitesseMax=6;
			break;
		case 2:
			this.vitesseMin=4;
			this.vitesseMax=7;
			break;
		default:
			this.vitesseMin=0;
			this.vitesseMax=5;
			break;
	}
		
		this.vitesse=vitesseMin;
		
		this.largeur=20;
		this.hauteur=20;
		
		this.vecteurY=-2;
		this.vecteurX=2;
		
		this.y=r.getY()-this.hauteur-10;
		this.x=r.getX()+(this.startingR.getLargeur()/2)-(this.largeur/2);
		
		
		Balle b= this;
		// Timer de la balle
		t.schedule(new TimerTask(){
			@Override
			public void run(){
				
				if(!Moteur.getGameOver() && !Moteur.getPause()){ // conditions pour stoper la balle en cas de pause ou de gameover
					if(!b.collee){ 
						Moteur.collisionBalleRaquettes(b); // verification des collisions avec la raquette 
						Controleur.CheckIfRemoveBrique(Moteur.collisionBalleBriques(b)); // verification des collisions avec les briques
						Moteur.collisionBords(b); // verification des collisions avec les bords
						b.updatePosition(); // deplacement de la balle
					}else{
						// en cas de deut de partie la balle est collee a la raquette r
						ballFollowRaquette(b.startingR); // la balle suit la raquette avans le lancement 
					}
				}
			
			}
		},0, (10-vitesse)); // le taux de rafraichissement du timer determine la vitesse de deplacement de la balle 
	}
	
	
	/*
	 * @return entier, le dernier joueur a avoir touche la balle avec sa raquette
	 */
	public int getJoueur(){
		return this.joueur;
	}
	
	/**
	 * fonction qui permet d'indiquer le dernier joueur a avoir  touche la balle
	 * @param j, entier, le dernier joueur a avoir touche cette balle
	 */
	public void setJoueur(int j){
		this.joueur = j;
	}
	
	/**
	 * 
	 * @return Raquette, la raquette a laquel est ratachee la balle en debut de partie 
	 */
	public Raquette getStartingR(){
		return this.startingR;
	}
	
	/**
	 * fonction d'acceleration de la balle
	 */
	public void vitesseUp(){
		if (this.vitesse<this.vitesseMax){
			this.vitesse+=1;
			this.pause();
			this.resume();
		}
	}
	
	/**
	 * fonction de ralenticement de la balle
	 */
	public void vitesseDown(){
		if (this.vitesse>this.vitesseMin){
			this.vitesse-=1;
			this.pause();
			this.resume();
		}
	}
	
	/**
	 * fonction qui stop le thread/timer de la balle
	 */
	public void killThread(){
		this.t.cancel();
	    this.t.purge();	
	}
	
	/**
	 * fonction qui pause le fonctionement de la balle || REDONDANTE MAIS EVITE LA CONFUSION EN CAS DE BESOIN DE PAUSE ||
	 */
	public void pause() {
	    killThread();
	}
	
	
	/**
	 * Comportement en cas de collision avec la raquette
	 */
	public void actionCollisionRaquette(Raquette r){
		if(this.getX()<r.x+r.largeur/2){
			
			double p1 = ((r.x+(double)(r.largeur)/2.0)-(this.getX()+(double)(this.largeur)/2.0));
			double p2 = ((double)(r.largeur)/2);
			
			
			this.vecteurX=(float)(-2*(p1/p2)-1);
			this.vecteurY=-2;
			
		}
		if(this.getX()>=r.x+r.largeur/2){
			double p1 = ((this.getX()+(double)(this.largeur)/2.0)-(r.x+(double)(r.largeur)/2.0));
			double p2 = ((double)(r.largeur)/2);
			
			this.vecteurX=(float) (2*(p1/p2)+1);
			this.vecteurY=-2;
		}
		this.setJoueur(r.getJoueur());
	}
	
	/**
	 * relance le timer/thread de la balle
	 */
	public void resume() {
	    this.t = new Timer();
	    Balle b= this;
	    t.schedule(new TimerTask(){
			@Override
			public void run(){
				if(!Moteur.getGameOver() && !Moteur.getPause()){
					if(!b.collee){
						Moteur.collisionBalleRaquettes(b);
						Controleur.CheckIfRemoveBrique(Moteur.collisionBalleBriques(b));
						Moteur.collisionBords(b);
						b.updatePosition();
					}else{
						ballFollowRaquette(b.startingR);
					}
				}
			}
		},0, 10-vitesse);
	}
	
	/**
	 * pour lancer la balle en debut de partie
	 */
	public void decolle(){
		this.collee = false;
	}
	
	/**
	 * bloque la position de la balle sur la position de la raquette en avans le lance debut de partie 
	 * @param r Raquette, la raquette a suivre
	 */
	public void ballFollowRaquette(Raquette r){
		this.y=r.getY()-this.hauteur-2;
		this.x=r.getX()+(r.getLargeur()/2)-(this.largeur/2);
	}
	

}




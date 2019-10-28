package arcanoTpong;

import java.util.Timer;
import java.util.TimerTask;

public class PowerUp extends Forme{
	private int id = 0; // identifiant du powerUP
	
	private Moteur m;
	private Balle declancheur;
	
	private Timer t = new Timer();
	
	/**
	 * cree un powerUp aleatoire 
	 * @param Moteur, les info de la partie en cours
	 * @param b Balle, la balle qui a fait spawner le power up en detruisant une brique
	 * @param spawn Brique, la brique qui a spawner le PowerUp en se detruisant  
	 */
	public PowerUp(Moteur m, Balle b, Brique spawn){
		super();
		
		this.largeur=40;
		this.hauteur=40;
		
		this.x = spawn.x+spawn.largeur/2-this.largeur/2;
		this.y = spawn.y;
		
		this.declancheur=b;
		this.m = m;
		
		this.vecteurY = 2;
		this.vecteurX = 0;
		
		//choix au hasard d un PowerUp
		this.id = 1 + (int)(Math.random() * 7);
		
		//Timer du powerUp
		PowerUp p= this;
		t.schedule(new TimerTask(){
			public void run(){
				if(!m.getPause() && !m.getGameOver()){ // stop si pause ou gameover
				Moteur.collisionPowerUpRaquettes(p); // verifie les collisions avec la raquette 
				Moteur.powerUpSorti(p); // verifie si le power up n est pas sortie de la zone de jeu 
				p.updatePosition(); // deplace le PowerUp
				}
			}
		},0, (10));
		
	
	}
	/*
	 * action en cas de collision avec la raquette r
	 */
	public void actionCollisionRaquette(Raquette r){
		this.launchEffect();
		this.t.cancel();
		this.t.purge();
		Controleur.removePowerUp(this);
	}
	
	/**
	 * fonction pour lancer l'effet du powerUp (au moment de la collision avec la raquette)
	 */
	public void launchEffect(){
		switch(this.id){
		case 1:
			this.speedUp();
			break;
		case 2:
			this.speedDown();
			break;
		case 3:
			this.speedDown();
			break;
		case 4:
			this.newBall();
			break;
		case 5:
			this.newBall();
			break;
		case 6:
			this.raquetteSizeUp(m.getInGRaquettes()[this.declancheur.getJoueur()-1]);
			break;
		case 7:
			this.raquetteSizeDown(m.getInGRaquettes()[this.declancheur.getJoueur()-1]);
			break;
		}
	
	}
	
	
	
	/**
	 * Fonction pour accelerer toutes les balles 
	 */
	public void speedUp(){
		if(!m.getPause()){
			for(Balle ball : m.getInGBalles() ){
				ball.vitesseUp();
			}
		}
	}
	
	/**
	 *Fonction pour ralentir toutes les Balles 
	 */
	public void speedDown(){
		if(!m.getPause()){
			for(Balle ball : m.getInGBalles() ){
				ball.vitesseDown();
			}
		}
	}
	
	/**
	 *Fonction pour lancer une nouvelle balle dans la partie 
	 */
	public void newBall(){
		synchronized (m.getInGBalles()){
		if(m.getInGBalles().size()>=0)
			m.addBall(new Balle (m.getInGBalles().get(0).x, m.getInGBalles().get(0).y, m.getInGBalles().get(0).getJoueur(), m.getDifficulte()) );
		}
	}
	
	/**
	 * Fonction pour allonger la raquette r
	 * @param r, Raquette, la raquette a allonger
	 */
	public void raquetteSizeUp(Raquette r){
		r.sizeUp();
	}
	
	/**
	 * Fonction pour retrecir la raquette r
	 * @param r, Raquette, la raquette a retrecir
	 */
	public void raquetteSizeDown(Raquette r){
		r.sizeDown();
	}
	
	/**
	 * 
	 * @return entier, identifiant du powerUp
	 */
	public int getId(){ 
		return this.id; 
	}
	
	/**
	 * suprime le timer du PowerUp
	 */
	public void killThread(){
		this.t.cancel();
	    this.t.purge();	
	}
	
	/**
	 * stop le timer ( redondant mais nom plus explicite pour l'usage voulu)
	 */
	public void pause() {
	    killThread();
	}
	
	/**
	 * recree / relance le timer du PowerUp
	 */
	public void resume() {
	    this.t = new Timer();
		PowerUp p= this;
		t.schedule(new TimerTask(){
			public void run(){
				if(!m.getPause() && !m.getGameOver()){
				Moteur.collisionPowerUpRaquettes(p);
				p.updatePosition();
				}
			}
		},0, (10));
	}

}

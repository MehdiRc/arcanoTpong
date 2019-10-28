package arcanoTpong;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.io.*;


public class Controleur {
	private static int lZoneJeu=80*10;
	private static int hZoneJeu=30*20;

	private static Timer timeIncremente = new Timer();
	private static Timer repaint = new Timer();
	
	private static Vue view;
	private static Moteur model;
	
	private static int difficulte = 1;
	
	private static int currentGameId;



	public static void main(String args[]){
		 view = new Vue(lZoneJeu,hZoneJeu); //initialisation de la vue 
		 setActionBouton(); // on applique les actions au boutons 
		 
		 model = new Moteur(1,0,0,"./LEVEL/null.txt", difficulte ); // première initialisation "vierge" du moteur
		
		 //thread pour refresh la vue 
		 repaint.schedule(new TimerTask(){
				@Override
				public void run(){
					view.repaint();
					if (model.getGameOver()) {
						view.setGameOver();
					}
				}
			},0, 5);

		 //Controle souris, et thread de deplacement de la raquette
		 view.getPanel().addMouseMotionListener(new MouseMotionAdapter(){
		    	@Override
		        public void mouseMoved(MouseEvent e){
		          
		    		model.teleportRaquetteX(model.getInGRaquettes()[0],  view.getXZone(e.getX()));
		    		model.getInGRaquettes()[0].updateRaquettePosition();
		        }
		      });
		    
		    
	 }

	/**
	 * Fait aparaitre un PowerUp selon une probabilite donnee
	 * @param Moteur, les info de la partie en cours
	 * @param b Balle, la balle qui a fait spawner le powerUp en detruisant une brique
	 * @param spawn Brique, la brique qui a spawner le PowerUp en se detruisant  
	 */
	 synchronized public static void spawnPowerUp(Moteur mot, Balle ball, Brique brique){
		 int rand = (1 + (int)(Math.random()*10)); // rand = [1-10]
		 if(rand>5){
			 PowerUp p = new PowerUp(mot, ball, brique);
			 synchronized(model.getInGPowerups()) {
				 model.addPowerUp(p);
			 }
		 }
	 }
	
	 /**
	  * Enleve un powerUp du jeu
	  * @param p PowerUp, le powerUp a enlever
	  */
	 public static void removePowerUp(PowerUp p) {
		 synchronized(model.getInGPowerups()) {
			 synchronized(p) {
				 //view.enlevePowerUp(p);
				 model.killPowerUp(p);
				 p = null;
			 }
		 }
	 }
	 
	 /*
	  * Derniere etape dans le processus de colision d'une balle avec une brique b 
	  * fonction qui enl�ve les briques si elles atteignent 0 de resistance
	  * si la brique est enlevee, on appel la fonction de spawn de PowerUps et on verifie aussi si on a pas gagne la partie 
	  */
	 synchronized public static void CheckIfRemoveBrique(Brique b){
			 if(b!=null){
				 synchronized(model.getInGBriquesClean()) {
					 synchronized (b) {
						 view.enleveBrique(b);
						 spawnPowerUp(model,model.getInGBalles().get(0),b);
						 if(model.victory()){
							 model.gameOverLaunch();
						 }
					 }
				 }
				 
		 }
	 }
	 
	 /**
	  * fonction permettant de mettre en pause
	  */
	 public void chronoPause() {
		    this.timeIncremente.cancel(); //on enleve les timers
		    this.timeIncremente.purge();
		    }
	
	 /**
	  * fonction permettant de relance le chrono
	  */
	public void chronoResume(){
		this.timeIncremente = new Timer(); //on refait le timer
		timeIncremente.schedule(new TimerTask(){
			int chrono; 
			@Override
			public void run(){
				chrono++;
				view.afficheChronos(chrono);
			}
		},0, 1000);
	}
	
	/**
	 * lance une nouvelle partie a aprtir d un fichier
	 * @param level un entier correspondant au niveau 
	 */
	public static void newRandomGame(){
		
		timeIncremente.cancel(); //on nettoie les anciens timer
		timeIncremente.purge();
		
		currentGameId = 99; // on set l id aleatoire (autres que 1-24)
		
		repaint.cancel();  //on nettoie les anciens timer
		repaint.purge();
		
		timeIncremente = new Timer(); // on fait de nouveau timer
		repaint = new Timer();
		
		model.closeGame(); //on purgeles timer du model
		
		model= new Moteur(1,lZoneJeu,hZoneJeu, difficulte); // on fait une nouvelle partie
		
		timeIncremente.schedule(new TimerTask(){ //cretation du timer
				int chrono=0;//on initialise le chrono
				@Override
				public void run(){
					if (!model.getGameOver() && !model.getPause()){//tant qu on est pas en pause ou en game over
						chrono++;//on incremente le chrono
						view.afficheChronos(chrono);//on affiche le chrono
						if(!model.getPause() && chrono%5==0){// on augmente la vitesse
							for(Balle ball : model.getInGBalles() ){
								ball.vitesseUp();
							}
						}
						
					}
				}
			},0, 1000);
		 
		

		 repaint.schedule(new TimerTask(){// on lance le timer de repaint
				@Override
				public void run(){
					view.repaint();
					if (model.getGameOver()) {
						view.setGameOver();
					}
				}
			},0, 5);

	    	view.setListeBriques(model.getInGBriquesClean());// on set les objets a afficher
		    view.setListeBalles(model.getInGBalles());
		    view.setListeRaquettes(model.getInGRaquettesArrayListe());
		    view.setListePowerUp(model.getInGPowerups());
			view.unsetGameOver();
	}
	
	/**
	 * lance une nouvelle partie a aprtir d un fichier
	 * @param level un entier correspondant au niveau 
	 */
	public static void newLevelGame( int level){
			
			timeIncremente.cancel(); //on nettoie les potentiels timers
			timeIncremente.purge();
			
			currentGameId = level; // on met a 
			
			repaint.cancel(); // on nettoie les potentiels timers
			repaint.purge();
			
			timeIncremente = new Timer(); // on refait de nouveau timers
			repaint = new Timer();
			
			model.closeGame(); //on purge les timer du model
			
			model= new Moteur(1,lZoneJeu,hZoneJeu,"./LEVEL/"+Integer.toString(level)+".txt", difficulte); // on fait une nouvelle partie en ouvrant un fichier txt
			
			 timeIncremente.schedule(new TimerTask(){ //cretation du timer
					int chrono=0; //on initialise le chrono
					@Override
					public void run(){
						if (!model.getGameOver() && !model.getPause()){ //tant qu on est pas en pause ou en game over
							chrono++; //on incremente le chrono
							view.afficheChronos(chrono); //on affiche le chrono
							if(!model.getPause() && chrono%5==0){ // on augmente la vitesse
								for(Balle ball : model.getInGBalles() ){
									ball.vitesseUp();
								}
							}
							
						}
					}
				},0, 1000);
			 
			
	
			 repaint.schedule(new TimerTask(){ // on lance le timer de repaint
					@Override
					public void run(){
						view.repaint(); // on repaint
						if (model.getGameOver()) { //si on est en game over
							view.setGameOver(); // on set le game over
						}
					}
				},0, 5);
	
		    	view.setListeBriques(model.getInGBriquesClean()); // on set les objets a afficher
			    view.setListeBalles(model.getInGBalles());
			    view.setListeRaquettes(model.getInGRaquettesArrayListe());
			    view.setListePowerUp(model.getInGPowerups());
			    view.unsetGameOver();
		}
	
	/**
	 * fonction servant à relancer la parti courante
	 */
		public static void playReplay(){ 
			  int id = currentGameId; // on recuperer l'id de la derniere parti lancer
			  if(id>0 && id<=24){
			   newLevelGame(id); //on lance a partir d un fichier
			  }
			  else{
			   newRandomGame(); // on relance une partie aleatoire sinon
			  }
		}
	
	/**
	 * cette fonction sert à appliquer les actions des boutons
	 */
	 public static void setActionBouton(){
		 
		 for (int i=0; i<24; i++) { // on applique les actions aux boutons de selection de niveau
			 view.setActionListenerSelectionLevel(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String Button = ((ButtonScalable) e.getSource()).getText(); // on recuperer le numero du bouton
					int index = Integer.parseInt(Button)+1;
					newLevelGame(index); // on lance une partie a partir du fichier correspondant
					view.changeToGame(); // on change de fenetre
					
				}
				 
			 }, i);
		 }
	 
		 view.setActionListenerMenuArcade(new ActionListener() { // on applique les actions sur le bouton arcade

			@Override
			public void actionPerformed(ActionEvent e) {
				newRandomGame(); //on lance une nouvelle parti aleatoire
				view.changeToGame(); //on change de fenetre
				
			}
			 
		 });
		 
		 view.setActionListenerGamePause(new ActionListener() { // action pour le bouton pause

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getPause()){ // on verifie si le model est en pause
        			
		        	for(Balle b : model.getInGBalles()){ //si oui on relance les threads
		        		b.resume();
		        	}
        		}else{
        			
        			for(Balle b : model.getInGBalles()){ //sinon on arrete les threads
		        		b.pause();
		        	}
        			
        		}
				
	        	model.pauseSwitch();// on change l etat de pause
	        	if(model.getPause()) {view.setPause();} //on affiche le fenetre de pause si pause
	        	else {view.resetPause();} // on l enleve si l on est plus en pause
	        
			}
		 });
		 
		 view.setActionListenerGameExit(new ActionListener() { //action sur le bouton pour quitter de la fenetre de jeu

			@Override
			public void actionPerformed(ActionEvent e) {
				view.clearGame(); //on nettoie l interface
				view.changeToMenu(); // on passe au menu principale
				
			}
			 
		 });
		 
		 view.setActionListenerGameRetry(new ActionListener() { //action sur le bouton recommencer

			@Override
			public void actionPerformed(ActionEvent e) {
				view.clearGame(); //on nettoie l interface
				playReplay(); // on relance la partie courante
				
			}
			 
		 });
		 
		 view.setActionListenerOptionDifficulty(new ActionListener() { // action sur le bouton de difficulte

			@Override
			public void actionPerformed(ActionEvent e) {
				difficulte= (difficulte+1)%3; //on permute la valeur de la difficulte
				
			}
			 
		 });
	 
		 view.getPanel().addMouseListener(new MouseListener(){ // action pour lancer la balle en debut de partie

				@Override
				public void mouseClicked(MouseEvent e) { // si on clique
					if(!model.getGameOver() && !model.getPause()) { //si on est pas en game over ni en pause
					if ((model.getInGBalles().size()>=0)) // si on a au moin une balle
						model.getInGBalles().get(0).decolle(); //alors on la lance
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}
		      });
		 
		 
	 }
	 
	 
	 


}

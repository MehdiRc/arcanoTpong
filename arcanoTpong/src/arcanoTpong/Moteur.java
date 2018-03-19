package arcanoTpong;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Moteur {
	private Brique inGBriques [][];
	private ArrayList <Balle> inGBalles;
	private Raquette inGRaquettes [];
	
	private int chrono;
	private static boolean gameOver;
	private static boolean pause;
	
	private int largeurBrique=80;
	private int hauteurBrique=30;
	
	int nBriquesX;
	int nBriquesY;
	
	static int lZoneJeu;
	static int hZoneJeu;
	
	public Moteur(int nRaquettes, int lZoneJeu, int hZoneJeu){
		this.chrono=0;
		this.gameOver=false;
		this.pause=false;
		
		inGRaquettes= new Raquette [nRaquettes];
		inGBalles= new ArrayList <Balle>();
		
		this.lZoneJeu=lZoneJeu;
		this.hZoneJeu=hZoneJeu;
		
		this.nBriquesX=(int)(lZoneJeu/largeurBrique);
		this.nBriquesY=(int)(hZoneJeu/hauteurBrique/3);
		
		
		this.inGBriques = new Brique [nBriquesX][nBriquesY];
		for(int i=0; i<nBriquesY ;i++){
			for(int j=0; j<nBriquesX ;j++){
				this.inGBriques[j][i]=new Brique(1, 1, j*largeurBrique, i*hauteurBrique, largeurBrique, hauteurBrique); //Valeurs de variables arbitraires juste pour tester 
				//x= j*largeur de brique; y= i*hauteur de brique 
			}
		}
		
		for(int i=0; i<nRaquettes ;i++){
			this.inGRaquettes[i] = new Raquette(lZoneJeu/2,hZoneJeu);
		}
		addBall(new Balle(lZoneJeu/2,hZoneJeu/2));
		addBall(new Balle(lZoneJeu/3,hZoneJeu/3));
		
	}
	/*// A FINIR UN JOUR 

	public Moteur(int nRaquettes, int lZoneJeu, int hZoneJeu, int nBriquesX, int nBriquesY){
		this.chrono=0;
		this.gameOver=false;
		this.pause=false;
		
		inGRaquettes= new Raquette [nRaquettes];
		inGBalles= new ArrayList <Balle>();
		
		this.nBriquesX=nBriquesX;
		this.nBriquesY=nBriquesY;
		
		
		this.inGBriques = new Brique [nBriquesX][nBriquesY];
		for(int i=0; i<nBriquesY ;i++){
			for(int j=0; j<nBriquesX ;j++){
				this.inGBriques[j][i]=new Brique(1, 1, j*largeurBrique, i*hauteurBrique, largeurBrique, hauteurBrique); //Valeurs de variables arbitraires juste pour tester 
				//x= j*largeur de brique; y= i*hauteur de brique 
			}
		}
		
		for(int i=0; i<nRaquettes ;i++){
			this.inGRaquettes[i] = new Raquette(lZoneJeu/2,hZoneJeu);
		}
		addBall(new Balle(lZoneJeu/2,hZoneJeu/2));
	}
	*/
	//*************
	public ArrayList<Brique> getInGBriquesClean(){
		ArrayList <Brique> result = new ArrayList <Brique> ();
		for(int i=0; i<this.inGBriques.length ;i++){
			for(int j=0; j<this.inGBriques[i].length ;j++){
				if(this.inGBriques[i][j].getResistanece()>0)
					result.add(this.inGBriques[i][j]);
			}
		}
		return result;
	}
	
	public ArrayList<Balle> getInGBalles(){
		return inGBalles;
	}
	
	public ArrayList <Raquette> getInGRaquettesArrayListe() {
		return new ArrayList<>(Arrays.asList(inGRaquettes));
	}
	
	public Raquette[] getInGRaquettes() {
		return inGRaquettes;
	}
	
	//***************
	
	public Brique killBrique(int i, int j, Raquette raquette){
		raquette.addScore( this.inGBriques [i][j].kill() );
		return this.inGBriques [i][j];
	}
	
	public void killBall(Balle balle){
		inGBalles.remove(balle);
	}
	
	public void addBall(Balle balle){
		inGBalles.add(balle) ;
	}
	
	public void reviveBrique(int i, int j, Brique brique){
		this.inGBriques [i][j] = brique;
	}
	
	public void teleportRaquetteX(Raquette r, int x){
		if(x<0)
			r.x=0;
		else if(x>lZoneJeu-r.largeur)
			r.x=lZoneJeu-r.largeur;
		else
			r.x=x;
	}
	
	public void teleportRaquetteY(Raquette r, int y){
		if(y<0)
			r.y=0;
		else if(y>hZoneJeu-r.hauteur)
			r.y=hZoneJeu-r.hauteur;
		else
			r.y=y;
	}
	
	public void movementRaquetteClavierX(Raquette r, int e){
		switch (e)
		{
		  case 0:
			  r.vecteurX=0;
		    break;
		  case 1:
			  r.vecteurX=-20;
		    break;
		  case 2:
			  r.vecteurX=20;
		    break;
		  default:
			  r.vecteurX=0;
		}
		
		r.updateRaquettePosition();
	}
	
	public static void collisionBords(Balle b){
		if(!gameOver && !pause){
			if(b.x<0)
				b.vecteurX=-b.getVecteurX();
			if(b.x+b.largeur>lZoneJeu)
				b.vecteurX=-b.getVecteurX();
			if(b.y<0)
				b.vecteurY=-b.getVecteurY();
			if(b.y+b.hauteur>hZoneJeu)
				b.vecteurY=-b.getVecteurY();
		}
	}
	
	
	
}

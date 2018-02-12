package arcanoTpong;

import java.util.ArrayList;

public class Moteur {
	private Brique inGBriques [][];
	private ArrayList <Balle> inGBalles;
	private ArrayList <Raquette> inGRaquettes;
	
	private int chrono;
	private boolean gameOver;
	private boolean pause;
	
	private int largeurBrique=200;
	private int hauteurBrique=100;
	
	public Moteur(int nBriquesX, int nBriquesY, int nRaquettes){
		this.chrono=0;
		this.gameOver=false;
		this.pause=false;
		
		inGRaquettes= new ArrayList <Raquette>();
		inGBalles= new ArrayList <Balle>();
		
		
		this.inGBriques = new Brique [nBriquesX][nBriquesY];
		for(int i=0; i<nBriquesY ;i++){
			for(int j=0; j<nBriquesX ;j++){
				this.inGBriques[j][i]=new Brique(1, 1, j*largeurBrique, i*hauteurBrique, largeurBrique, hauteurBrique); //Valeurs de variables arbitraires juste pour tester 
				//x= j*largeur de brique; y= i*hauteur de brique 
			}
		}
		
		for(int i=0; i<nRaquettes ;i++){
			this.inGRaquettes.add(new Raquette());
		}
	
	}
	
	private void killBrique(int i, int j, Raquette raquette){
		raquette.addScore( this.inGBriques [i][j].kill() );
		this.inGBriques [i][j]=null;
	}
	
	private void killBall(Balle balle){
		inGBalles.remove(balle);
	}
	
	private void createBall(Balle balle){
		inGBalles.add(balle) ;
	}
	
	private void reviveBrique(int i, int j, Brique brique){
		this.inGBriques [i][j] = brique;
	}

	public ArrayList <Raquette> getInGRaquettes() {
		return inGRaquettes;
	}
	
	public ArrayList<Brique> getInGBriquesClean(){
		ArrayList <Brique> result = new ArrayList <Brique> ();
		for(int i=0; i<this.inGBriques.length ;i++){
			for(int j=0; j<this.inGBriques[i].length ;j++){
				result.add(this.inGBriques[i][j]);
			}
		}
		return result;
	}

}

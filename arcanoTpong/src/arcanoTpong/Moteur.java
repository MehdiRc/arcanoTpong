package arcanoTpong;

import java.util.ArrayList;

public class Moteur {
	private Brique inGBriques [][];
	private ArrayList <Balle> inGBalles;
	private ArrayList <Raquette> inGRaquettes;
	
	private int chrono;
	private boolean gameOver;
	private boolean pause;
	
	public Moteur(int nBriquesX, int nBriquesY, int nRaquettes){
		this.chrono=0;
		this.gameOver=false;
		this.pause=false;
		
		this.inGBriques = new Brique [nBriquesX][nBriquesY];
		for(int i=0; i<nBriquesY ;i++){
			for(int j=0; j<nBriquesX ;j++){
				this.inGBriques[j][i]=new Brique(1, 1, j*100, i*100, 300, 100); //Valeurs de variables arbitraires juste pour tester 
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
	
	

}

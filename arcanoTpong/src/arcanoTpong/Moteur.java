package arcanoTpong;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Moteur {
	
	private static Brique inGBriques [][]; // Tableau de briques de la partie
	private static ArrayList <Brique>  inGBriquesCleans; // La version arrayliste avec uniquement les briques actives (pour facilité la communication avec la vue par le biais du controleur)
	
	private static ArrayList <Balle> inGBalles; // Arrayliste des balles en jeu
	private static Raquette inGRaquettes []; // Tableau de Raquette en jeu ( en cas de multijoueur)
	private static ArrayList <PowerUp> inGPowerups;  // Arrayliste des PowerUps en jeu 
	
	private static int chrono; // le chronometre de la partie 
	private static boolean gameOver; // fin de partie 
	private static boolean pause; 
	
	//dimentions d'une brique
	final static int largeurBrique=80;
	final static int hauteurBrique=30;
	
	
	int nBriquesX;//nombre de colones
	int nBriquesY;//nombre de lignes 
	
	static int lZoneJeu;// largeur de la zone de jeu ( le scaling se fait dans la vue et est transparent pour le moteur )
	static int hZoneJeu;// hauteur de la zone de jeu ( le scaling se fait dans la vue et est transparent pour le moteur )
	
	private int difficulte; // Difficulte de la partie a lancer
	
	/**cree une partie avec des briques aleatoires 
	 * 
	 * @param nRaquettes, entier, nombre de raquettes (et donc nombre de joueurs)
	 * @param lZoneJeu, entier, largeur de la zone de jeu
	 * @param hZoneJeu entier, hauteur de la zone de jeu
	 * @param difficulte entier, Difficulte de la partie a lancer
	 */
	public Moteur(int nRaquettes, int lZoneJeu, int hZoneJeu, int difficulte){
		
		this.chrono=0;
		this.gameOver=false;
		this.pause=false;
		
		this.difficulte=difficulte;
		
		inGRaquettes= new Raquette [nRaquettes];
		inGBalles= new ArrayList <Balle>();
		inGPowerups= new ArrayList <PowerUp>();
				
		this.lZoneJeu=lZoneJeu;
		this.hZoneJeu=hZoneJeu;
		
		//calcule du nombre de ligne et colone en fonction des dimentions des briques et de la zone de jeu
		this.nBriquesX=(int)(lZoneJeu/largeurBrique);
		this.nBriquesY=(int)(hZoneJeu/hauteurBrique/3);
		
		// Initialisation du tableau de briques avec des briques aleatoires 
		this.inGBriques = new Brique [nBriquesX][nBriquesY];
		for(int i=0; i<nBriquesY ;i++){
			for(int j=0; j<nBriquesX ;j++){
				this.inGBriques[j][i]=new Brique(j*largeurBrique, i*hauteurBrique, largeurBrique, hauteurBrique);
			}
		}
		
		//creation des raquettes 
		for(int i=0; i<nRaquettes ;i++){
			this.inGRaquettes[i] = new Raquette(lZoneJeu/2,hZoneJeu,i+1,this.difficulte);
		}
		
		//creation de l'arrayList de briques qui servira a l'affichage
		this.inGBriquesCleans = setInGBriquesClean();
		
		//ajout de la balle de debut, collee a la raquette [0]
		addBall(new Balle(inGRaquettes[0],this.difficulte));
	
			
	}
	/**cree une partie en loadant un niveau preconcu a partir d un fichier text 
	 * 
	 * @param nRaquettes, entier, nombre de raquettes (et donc nombre de joueurs)
	 * @param lZoneJeu, entier, largeur de la zone de jeu
	 * @param hZoneJeu entier, hauteur de la zone de jeu
	 * @param path, String, chemain du fichier text contenant les information sur la partie a lancer
	 * @param difficulte entier, Difficulte de la partie a lancer
	 */
	public Moteur(int nRaquettes, int lZoneJeu, int hZoneJeu, String path, int difficulte){
		this.chrono=0;
		this.gameOver=false;
		this.pause=false;
		
		this.difficulte = difficulte;
		
		inGRaquettes= new Raquette [nRaquettes];
		inGBalles= new ArrayList <Balle>();
		inGPowerups= new ArrayList <PowerUp>();
		
		
		
		this.lZoneJeu=lZoneJeu;
		this.hZoneJeu=hZoneJeu;
		
		//calcule du nombre de ligne et colone en fonction des dimentions des briques et de la zone de jeu
		this.nBriquesX=(int)(lZoneJeu/largeurBrique);
		this.nBriquesY=(int)(hZoneJeu/hauteurBrique/3);
		
		//chargement du niveau dans le tableau d'entiers "levelLayout" (plus precisement on obtient un tableau avec les resistance de chaque brique; et si une brique est innexistante alors sa resistance est a 0 
		int levelLayout [][] = loadLevel(path);
		
		
		//transformation du tableau d'entiers (resistances) en tableau de Briques
		this.inGBriques = new Brique [nBriquesX][nBriquesY];
		for(int i=0; i<nBriquesY ;i++){
			for(int j=0; j<nBriquesX ;j++){
				this.inGBriques[j][i]=new Brique(1, levelLayout [j][i], j*largeurBrique, i*hauteurBrique, largeurBrique, hauteurBrique);
			}
		}
		
		//creation des raquettes 
		for(int i=0; i<nRaquettes ;i++){
			this.inGRaquettes[i] = new Raquette(lZoneJeu/2,hZoneJeu,i+1, this.difficulte);
		}
		
		//creation de l'arrayList de briques qui servira a l'affichage
		this.inGBriquesCleans = setInGBriquesClean();
		
		//ajout de la balle de debut, collee a la raquette [0]
		addBall(new Balle(inGRaquettes[0], this.difficulte));
		
	}
	/**Fontion cree un tableau d entiers a partir d un fichier text dont le chemain est path
	 * 
	 * @param path, String, chemain du fichier text a transformer et tableau
	 * @return tableau d entiers, dans notre cas les entiers representent les resistances des briques 
	 */
	public int[][] loadLevel(String path){
		Scanner s = null;
		
		try {
			s = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("can't open file");
			e.printStackTrace();
		}
		
		 ArrayList<String> ligneS = new ArrayList<String>();
		 while (s.hasNextLine()){
			 ligneS.add(s.nextLine());
		 }
		 s.close();
		
		 //////////
		 int res [][] = new int [nBriquesX][nBriquesY];
			 for(int j=0; j<this.nBriquesY ;j++){
				 for(int i =0; i<this.nBriquesX; i++){
					 res[i][j]= Integer.parseInt(ligneS.get(j).split("")[i]);
				 }
			 }
			 
			 return res;
	}
	/**
	 * fonction qui nous donne l'arrayliste des briques actives en jeu
	 * @return ArrayListe de Briques, l'arrayliste des briques actives en jeu
	 */
	public ArrayList<Brique> setInGBriquesClean(){
		ArrayList <Brique> result = new ArrayList <Brique> ();
		for(int i=0; i<this.inGBriques.length ;i++){
			for(int j=0; j<this.inGBriques[i].length ;j++){
				if(this.inGBriques[i][j].getResistanece()>0)
					result.add(this.inGBriques[i][j]);
			}
		}
		return result;
	}
	/**
	 * 
	 * @return l'arrayliste des briques actives en jeu
	 */
	public ArrayList<Brique> getInGBriquesClean(){
		return this.inGBriquesCleans;
	}
	
	/**
	 * 
	 * @return boolean, l etat de pause du jeu
	 */
	public static boolean getPause(){
		return pause;
	}
	
	/**
	 * 
	 * @return boolean, l etat du jeu (fini ou non)
	 */
	public static boolean getGameOver(){
		return gameOver;
	}
	
	/**
	 * 
	 * @return entier, la difficulte du jeu en cours 
	 */
	public int getDifficulte(){
		return this.difficulte;
	}
	
	/**
	 * 
	 * @return ArrayList de Balles, les balles actives en jeu 
	 */
	public ArrayList<Balle> getInGBalles(){
		return inGBalles;
	}
	
	/**
	 * 
	 * @return ArrayList de PowerUps, les PowerUp actifs en jeu 
	 */
	public ArrayList <PowerUp> getInGPowerups() {
		return inGPowerups;
	}
	
	/**
	 * 
	 * @return ArrayList de Raquettes, les Raquettes actives en jeu 
	 */
	public ArrayList <Raquette> getInGRaquettesArrayListe() {
		return new ArrayList<>(Arrays.asList(inGRaquettes));
	}
	
	/**
	 * 
	 * @return Tableau de Raquettes, les Raquettes actives en jeu 
	 */
	public Raquette[] getInGRaquettes() {
		return inGRaquettes;
	}
	
	//***************
	
	
	/**
	 * Fait partie du processus de supression de Brique 
	 * ajoute la valeur de la brique au score et renvoi la brique a suprimer
	 * @param i entier, index de la brique dans la tableau de briques 
	 * @param j entier, index de la brique dans la tableau de briques 
	 * @param raquette, Raquette, la raquette qui remporte les points pour la destruction de la brique
	 * @return Briquen la brique a suprimer ( une autre fonction s'en servira pour le faire dans la chaine de suppression ) 
	 */
	public static Brique killBrique(int i, int j, Raquette raquette){
		
			raquette.addScore(inGBriques[i][j].kill());
			return inGBriques [i][j];
	}
	
	/*
	 * fonction qui enleve une balle du jeu et qui stops ses threads
	 */
	public static void killBall(Balle balle){
		synchronized(inGBalles) {
			synchronized(balle) {
				balle.killThread();
				inGBalles.remove(balle);
			}
		}
	}	
	
	/*
	 * fonction qui enleve un PowerUp du jeu et qui stops ses threads
	 */
	public static void killPowerUp(PowerUp p){
		p.killThread();
		inGPowerups.remove(p);
	}	
	
	/*
	 * fonction qui ajoute une balle au jeu
	 */
	public void addBall(Balle balle){
		synchronized(inGBalles) {
			synchronized(balle) {
				inGBalles.add(balle) ;
			}
		}
	}
	
	/*
	 * fonction qui ajoute un PowerUp au jeu
	 */
	public void addPowerUp(PowerUp power){
		inGPowerups.add(power) ;
	}
	
	/**
	 * Fonction qui restaure une brique detruite ( NE SERT PAS DANS LE PROJET ACTUELLEMENT, mais peut toujours servir) 
	 * @param i index de la brique a restaurer dans la tableau de briques 
	 * @param j index de la brique a restaurer dans la tableau de briques
	 * @param brique brique de remplacement
	 */
	public void reviveBrique(int i, int j, Brique brique){
		this.inGBriques [i][j] = brique;
	}
	
	
	/**
	 * Fonction qui "teleporte" la raquette horizontalement a l'abysse x (sert dans le controle de la raquette avec la souris, ou on teleporte la raquette sur les coord de la souris)
	 * @param r, Raquette, la raquette a deplacer 
	 * @param x, entier, emplacement ou deplacer la raquette
	 */
	public void teleportRaquetteX(Raquette r, int x){
		if(!gameOver && !pause){
			if(x<0)
				r.x=0;
			else if(x>lZoneJeu-r.largeur)
				r.x=lZoneJeu-r.largeur;
			else
				r.x=x;
		}
	}
	
	/**
	 * Fonction qui "teleporte" la raquette verticalement a l'ordonnée y ( NE SERT PAS DANS LE PROJET ACTUELLEMENT, mais peut toujours servir )
	 * @param r, Raquette, la raquette a deplacer 
	 * @param y, entier, emplacement ou deplacer la raquette
	 */
	public void teleportRaquetteY(Raquette r, int y){
		if(!gameOver && !pause){
			if(y<0)
				r.y=0;
			else if(y>hZoneJeu-r.hauteur)
				r.y=hZoneJeu-r.hauteur;
			else
				r.y=y;
		}
	}
	
	/**
	 * fonction de deplacement de la raquette en fonction de l'input e du clavier (NE SERT PLUS || LES CONTROLES CLAVIERS ONT ETE RETIRES CAR MOIN NATURELS QUE LA SOURIS)
	 * @param r, Raquette, la raquette a deplacer 
	 * @param e, entier, une traduction de l'input du clavier que nous passe une autre fonction (l utilisation de l entier e sert a pouvoir changer les touches et leurs actions facilement sans toucher au listener)
	 */
	public void movementRaquetteClavierX(Raquette r, int e){
		if(!gameOver && !pause){
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
	}
	
	/**detection de collision entre une brique et une balle du cote inferieur de la brique
	 * 
	 * @param ball, Balle, la balle concernee par la collision 
	 * @param brique, Brique, la brique avec laquelle on verifie la collision
	 * @return, boolean, true si il y a collision false sinon 
	 */
	private static boolean coolisionBas(Balle ball, Brique brique){
		if( (ball.x>=brique.x-ball.largeur && ball.x<=brique.x+brique.largeur) && 
		(ball.y<=brique.y+brique.hauteur && ball.y>=brique.y+brique.hauteur-15) && 
		ball.vecteurY<0 ){
			ball.vecteurY = -ball.vecteurY;
			return true;
		}
		return false;	
	}

	/**detection de collision entre une brique et une balle du cote superieur de la brique
	 * 
	 * @param ball, Balle, la balle concernee par la collision 
	 * @param brique, Brique, la brique avec laquelle on verifie la collision
	 * @return, boolean, true si il y a collision false sinon 
	 */
	private static boolean coolisionHaut(Balle ball, Brique brique){
		if( (ball.x>=brique.x-ball.largeur && ball.x<=brique.x+brique.largeur) && 
		( ball.y+ball.hauteur>=brique.y && ball.y+ball.hauteur<=brique.y+15) && 
		ball.vecteurY>0){
			ball.vecteurY = -ball.vecteurY;
			return true;
		}
		return false;	
	}


	/**detection de collision entre une brique et une balle du cote droit de la brique
	 * 
	 * @param ball, Balle, la balle concernee par la collision 
	 * @param brique, Brique, la brique avec laquelle on verifie la collision
	 * @return, boolean, true si il y a collision false sinon 
	 */
	private static boolean coolisionDroite(Balle ball, Brique brique){
		if( (ball.y>=brique.y-ball.hauteur && ball.y<=brique.y+brique.hauteur) && 
		(ball.x<=brique.x+brique.largeur && ball.x>=brique.x+brique.largeur-15) && 
		ball.vecteurX<0 ){
			ball.vecteurX = -ball.vecteurX;
			return true;
		}
		return false;
	}

	/**detection de collision entre une brique et une balle du cote gauche de la brique
	 * 
	 * @param ball, Balle, la balle concernee par la collision 
	 * @param brique, Brique, la brique avec laquelle on verifie la collision
	 * @return, boolean, true si il y a collision false sinon 
	 */
	private static boolean coolisionGauche(Balle ball, Brique brique){
		if( (ball.y>=brique.y-ball.hauteur && ball.y<=brique.y+brique.hauteur) && 
		(ball.x+ball.largeur>=brique.x && ball.x+ball.largeur<=brique.x+15) &&
		ball.vecteurX>0 ){
			ball.vecteurX = -ball.vecteurX;
			return true;
		}
		return false;
	}
	
	/**
	 * Fonction Globale de collisions d'une balle avec les briques
	 * verification locale des brique (MAX 9 briques) pour determiner les collisions
	 * @param b, Balle, la balle pour laquel on veux realiser les collisions
	 * @return Brique, la brique touchee par une collision ( donc qui prend un coup, voir qui se fait detruire) SI IL Y EN A, sinon on retourne null
	 */
	public static Brique collisionBalleBriques(Balle b){
		if(!gameOver && !pause){
			
			int x = 0;
			int y = 0;
			
			
			// On choisie quelle coin de la balle est plus susceptible d avoir des collisions en fonction de la dirrection de la balle 
			if(b.vecteurX>0)
				x=b.x+b.largeur;
			else
				x=b.x;
			
			if(b.vecteurY>0)
				y=b.y+b.hauteur;
			else
				y=b.y;
			
			
			//On isole la Zone ou se trouve la balle par rapport au tableau de Briques 
			int briqueI = (int) x/Moteur.largeurBrique;
			int briqueJ = (int) y/Moteur.hauteurBrique; 
			
			if(	briqueI>=0 && briqueI < inGBriques.length && 
				briqueJ>=0 && briqueJ < inGBriques[0].length ){
				
				int preX;
				int postX;
				
				int preY;
				int postY;
				
				if(briqueI-1>=0)
					preX=-1;
				else
					preX=0;
					
				if(briqueI+1<=inGBriques.length)
					postX=+1;
				else
					postX=0;
				
				if(briqueJ-1>=0)
					preY=-1;
				else
					preY=0;
					
				if(briqueJ+1<=inGBriques[0].length)
					postY=+1;
				else
					postY=0;
				
				//On verifie les collisions avec les 9 briques isolées ( <9 sur les bords)
				for(int i=briqueI; i<briqueI+postX; i++){
					for(int j=briqueJ; j<briqueJ+postY; j++){
						if( inGBriques[i][j].getResistanece()>0 && 
						( coolisionHaut(b, inGBriques[i][j]) || coolisionBas(b, inGBriques[i][j]) 
						|| coolisionDroite(b, inGBriques[i][j]) || coolisionGauche(b, inGBriques[i][j])  ) )
						{
							if (inGBriques[i][j].gothit()<=0){
								//System.out.println(i+" "+j);
								return killBrique(i, j, inGRaquettes[b.getJoueur()-1] );
							}
						}
						
					}
				}
			}
			
		}
		return null; //pas de collisions, on retourne null pour signifier cela 
	}
	
	
	/**
	 * fonction de collision d'une balle avec la raquette 
	 * @param b
	 */
	public static void collisionBalleRaquettes(Balle b){
			if(!gameOver && !pause){
				for(int i=0; i<inGRaquettes.length;i++){
					b.collisionRaquette(inGRaquettes[i]);
				}
				
			}
	}
	
	/**
	 * fonction de collision d un powerUp avec la raquette 
	 * @param p
	 */
	public static void collisionPowerUpRaquettes(PowerUp p){
		if(!gameOver && !pause){
			for(int i=0; i<inGRaquettes.length;i++){
				p.collisionRaquette(inGRaquettes[i]);
			}
		}
	}
	
	/**
	 * Fonction pour lancer le GameOver (fin de partie)
	 */
	public static void gameOverLaunch(){
		gameOver=true;
		System.out.println("GAMEOVER");
		if (!(inGRaquettes[0]==null))
			System.out.println( "Score " + inGRaquettes[0].getScore());
	
	}
	
	/**
	 * Fonction de collision de la balle avec les board
	 * @param b, Balle, la balle pour laquelle on verifie les collisions 
	 */
	public static void collisionBords(Balle b){
			if(b.x<0){
				b.vecteurX=Math.abs(b.getVecteurX());	
			}
			if(b.x+b.largeur>lZoneJeu){
				b.vecteurX=-Math.abs(b.getVecteurX());

			}
			if(b.y<0){
				b.vecteurY=-b.getVecteurY();
			}
			if(b.y+b.hauteur>hZoneJeu){
				killBall(b);
				if (inGBalles.size()== 0)
					gameOverLaunch();
			}			
	}
	
	/*
	 * fonction pour supromer les power ups qui sortent de la zone de jeu
	 */
	public static void powerUpSorti( PowerUp p){
		if(p.y+p.hauteur>hZoneJeu){
			killPowerUp(p);
		}				
	}
	
	/*
	 * fonction de fin de jeu ( en cas de victoire c.a.d detruire toutes les briques d un niveau)
	 */
	public boolean victory(){
		for(int i=0; i<this.inGBriques.length ;i++){
			for(int j=0; j<this.inGBriques[i].length ;j++){
				if(this.inGBriques[i][j].getResistanece()>0){
					return false;
				}
			}
		}
		return true;
	}
	

	/*
	 * fonction pour mettre le jeu en pause ou le sortir de la pause (pause ON/OFF) 
	 */
	public void pauseSwitch(){
		this.pause=!this.pause;
		System.out.println("pause");
	}
	
	/*
	 * Fonction qui arraite les Timers/threads de la partie en cours (pour arreter la partie ou lancer un newGame)
	 */
	public void closeGame(){
		for (Balle b : inGBalles) {
			b.killThread();
		}
		
		for (PowerUp p : inGPowerups) {
		    p.killThread();
		}
	}
	
}

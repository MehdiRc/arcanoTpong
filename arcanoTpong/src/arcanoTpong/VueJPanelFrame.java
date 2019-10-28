package arcanoTpong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueJPanelFrame extends JPanel{
	
	private int hauteurInit=800;//valeur par default
	private int largeurInit=1200; 
	
	//liste des images
	private static BufferedImage Brique;
	private static BufferedImage balle;
	private static BufferedImage raquette;
	private static BufferedImage fond;
	private static BufferedImage gauche;
	private static BufferedImage droite;
	private static BufferedImage gameOver;
	private static BufferedImage pauseTitre;
	
	private static BufferedImage brique1;
	private static BufferedImage brique2;
	private static BufferedImage brique3;
	private static BufferedImage brique4;
	private static BufferedImage brique5;
	
	private static BufferedImage powerUpBallUp;
	private static BufferedImage powerUpSpeedDown;
	private static BufferedImage powerUpSpeedUp;
	private static BufferedImage powerUpSizeUp;
	private static BufferedImage powerUpSizeDown;
	
	private static BufferedImage buttonPause;
	private static BufferedImage buttonPause2;
	private static BufferedImage buttonRetry1;
	private static BufferedImage buttonRetry2;
	private static BufferedImage buttonRetour1;
	private static BufferedImage buttonRetour2;
	
	private static BufferedImage Titre;
	
	//liste des objets a afficher
	private ArrayList<Brique> briques = new ArrayList<Brique>();
	private ArrayList<Balle> balles = new ArrayList<Balle>();
	private ArrayList<Raquette> raquettes = new ArrayList<Raquette>();
	private ArrayList<PowerUp> powerUp = new ArrayList<PowerUp>();
	
	///////////////////////////////////////////
	
	private double scalex;
	private double scaley;
	
	private AffineTransform tx0;
	
	private int deca; //le decalage du a la aprtie gauche de la fenetre
	
	protected int tps; //le temps restant.
	
	
	private int largeurSansScale = 0; //pour avoir tout de même la taille totale sans le scale
	private int hauteurSansScale = 0;
	

	protected boolean isCurrent=false; // indique si c est la fenetre a afficher
	private boolean gameOverBool=false; // indique si le jeu est en etat de game over
	private boolean pauseBool=false;
	
	private ButtonScalable pause; // les boutons de la fenetre
	private ButtonScalable retry;
	private ButtonScalable exit;
	
	//////////////////////////////////////////
	
	/**
	 * constructeur de la classe VueJPanelFrame qui gere l interface de jeu
	 * @param largeur la largeur de la fenetre
	 * @param hauteur la hauteur de la fenetre
	 * @param tailleGauche la largeur des parties droites et gauches de la fenetre
	 */
	
	public VueJPanelFrame(int largeur, int hauteur, int tailleGauche) {
	
		super();
		
		this.largeurInit=largeur;
		this.hauteurInit=hauteur;
		
		this.setBounds(0, 0, largeur, hauteur);
		
		this.setLayout(null); // pour pouvoir placer les objets comme on veut
		
		this.tx0 = new AffineTransform();
		
		this.deca = tailleGauche; //defini la proportion de la fenêtre de gauche
				
		this.tps=0;
		
		try { //chargement des differentes images
			  Titre = ImageIO.read(new File("./IMAGES/Titre2.png"));
		      fond = ImageIO.read(new File("./IMAGES/imageFond.png"));
		      Brique = ImageIO.read(new File("./IMAGES/brique.png"));
		      balle = ImageIO.read(new File("./IMAGES/balle.png"));
		      raquette = ImageIO.read(new File("./IMAGES/raquette.png"));
		      gauche = ImageIO.read(new File("./IMAGES/gauche.png"));
		      droite = ImageIO.read(new File("./IMAGES/droite.png"));
		      gameOver = ImageIO.read(new File("./IMAGES/gameOver.png"));
		      pauseTitre = ImageIO.read(new File("./IMAGES/Pause.png"));
		      
		      brique1 = ImageIO.read(new File("./IMAGES/rock.png"));
		      brique2 = ImageIO.read(new File("./IMAGES/goldvestige.png"));
		      brique3 = ImageIO.read(new File("./IMAGES/cristal.png"));
		      brique4 = ImageIO.read(new File("./IMAGES/magmaRock.png"));
		      brique5 = ImageIO.read(new File("./IMAGES/brokenwall.png"));
		      
		      powerUpBallUp = ImageIO.read(new File("./IMAGES/ballUp.png"));
		      powerUpSpeedDown = ImageIO.read(new File("./IMAGES/speedDown.png"));
		      powerUpSpeedUp = ImageIO.read(new File("./IMAGES/speedUp.png"));
		      powerUpSizeUp = ImageIO.read(new File("./IMAGES/SizeUp.png"));
		      powerUpSizeDown = ImageIO.read(new File("./IMAGES/SizeDown.png"));
		      
		      buttonPause = ImageIO.read(new File("./IMAGES/pauseRond1.png"));
		      buttonPause2 = ImageIO.read(new File("./IMAGES/pauseRond2.png"));
		      buttonRetry1 = ImageIO.read(new File("./IMAGES/retryRond1.png"));
		      buttonRetry2 = ImageIO.read(new File("./IMAGES/retryRond2.png"));
		      buttonRetour1 = ImageIO.read(new File("./IMAGES/retourRond1.png"));
		      buttonRetour2 = ImageIO.read(new File("./IMAGES/retourRond2.png"));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
		this.setVisible(true);
		
		// on pause les boutons
		this.pause = new ButtonScalable("Pause",(this.getWidth()-150), this.getHeight()-150, 100,100,buttonPause, buttonPause2);
		this.add(pause);
		
		// on initialise les boutons mais on les ajoute que l orsque l on rentre dans l etat game over
		this.exit = new ButtonScalable("Exit",(this.getWidth()/2-150), this.getHeight()-175, 100,100,buttonRetour1, buttonRetour2); // on creer nos boutons pour gérer le game over, mais on ne les affiches ajoute pas avant le game over
		this.retry = new ButtonScalable("Retry",(this.getWidth()/2+50), this.getHeight()-175, 100,100,buttonRetry1, buttonRetry2);
	}
	
	
	/**
	 * fonction d affichage de la fenetre
	 */
	public void paintComponent(Graphics g){
		if (isCurrent) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g; // passage au graphics 2d
			
			AffineTransform tx1 = new AffineTransform(); 
	        scalex = (double)this.getWidth()/(double)largeurInit; //on calcule la valeur des scales
	        scaley = (double)this.getHeight()/(double)hauteurInit;
	        
	        tx1.scale(scalex, scaley); //on applique les scales
	        
	        largeurSansScale = (int)(this.getWidth()/scalex); // pour avoir la distance totale malgré le scale
	        hauteurSansScale = (int) (this.getHeight()/scaley);
	        
	        g2.setTransform(tx1);
	        
	        this.afficheFond(g2); // on affiche le fond apres la transformation a cause des panneaux lateraux
	        
	        synchronized(briques) {
		        for(Brique b : this.briques) { // on affiche les briques
		        	//if (b.getResistanece()<=0) { this.briques.remove(b);}
		        //	else {
		        	synchronized (b) {
		        		afficheBrique(b.x+deca,b.y, b.largeur,b.hauteur, g2, b.getResistanece());
		        	}
		        		//}
		        }
	        }
	        
	        synchronized (balles) {
		        for(Balle b : this.balles) { // les balles
		        	synchronized (b) {
		        		afficheBalle(b.x+deca, b.y, b.largeur, b.hauteur, g2);
		        	}
		        }
	        }
	        for(Raquette r : this.raquettes) { // les raquettes
	      	  afficheRaquette( r.x+deca, r.y-3, r.largeur, r.hauteur+6, g2);
	        }
	       
	        synchronized(powerUp) {
		        for(PowerUp p: this.powerUp) { // les powerups
		        	synchronized(p) {
		        		affichePowerUp(p.x+deca, p.y, p.largeur, p.hauteur, p.getId(), g2);
		        	}
		        }
	        }
	        
	        
	        afficheGauche(g2); // on affiches les panneaux latteraux
	        afficheDroite(g2);
	        
	        if (this.gameOverBool) { // si on est en game over on ajoute des elements
	        	afficheGameOver(g2);
	        }
	        if (this.pauseBool) {
	        	affichePause(g2);
	        }
	        
	        
	        pause.dessinButon(scalex, scaley, g2); //on affiche le bouton
	        
	        g2.setTransform(tx0); // on reinitialise la transformation
		}
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//partie graphique
	///////////////////////////////////////////////////////////////////////////////
	
	/**
	 * fonction permettant d afficher une brique
	 * @param x le x de la brique
	 * @param y le y de la brique
	 * @param width la largeur de la brique
	 * @param height la hauteur de la brique
	 * @param g2 le graphics2d du panneau
	 * @param resistance la resistance de la brique
	 */
	private void afficheBrique(int x, int y, int width, int height, Graphics2D g2, int resistance) {
		
		BufferedImage tmp=null;
		
		switch (resistance){ // en fonction de la valeur de resistance de la brique on affiche un skin different
		
		case 1: tmp=brique1;
		break;
		case 2: tmp=brique2;
		break;
		case 3: tmp=brique3;
		break;
		case 4: tmp=brique4;
		break;
		case 5: tmp=brique5;
		break;
		
		default: tmp=Brique;
		};
		
		g2.drawImage(tmp, x, y,width,height, null);//on affiche la brique
	}
	
	/**
	 * fonction d affichage de la balle
	 */
	private void afficheBalle(int x, int y, int width, int height, Graphics2D g2) {		
		g2.drawImage(balle, x, y, width, height, null);
	}
	
	/**
	 * fonction d affichage de la raquette
	 */
	private void afficheRaquette(int x, int y, int width, int height, Graphics2D g2) {
		g2.drawImage(raquette, x, y-3,width,height+6, null);
	}
	
	/**
	 * fonction d affichage des powers up
	 * @param x le x du power up
	 * @param y le y du power up
	 * @param width la largeur du power up
	 * @param height la hauteur du power up
	 * @param id l identifiant du power up
	 * @param g2 le graphics 2d de la fenetre
	 */
	private void affichePowerUp(int x, int y, int width, int height, int id, Graphics2D g2) {
		
		BufferedImage tmp=null;
		
		switch (id){ //en fonction de l id on affiche tel ou tel image
		
			case 1: tmp = powerUpSpeedUp;
			break;
			case 2: tmp = powerUpSpeedDown;
			break;
			case 3: tmp = powerUpSpeedDown;
			break;
			case 4: tmp = powerUpBallUp;
			break;
			case 5: tmp = powerUpBallUp;
			break;
			case 6: tmp = powerUpSizeUp;
			break;
			case 7: tmp = powerUpSizeDown;
			break;
			
			default: tmp = Brique;
		};
		
		g2.drawImage(tmp, x, y,width, height, null);
		
	}
	
	/**
	 * fonction d affichage du fond
	 */
	private void afficheFond(Graphics2D g2) {
		g2.drawImage(fond, deca-5, 0,largeurSansScale-2*deca+10,hauteurSansScale, null);//dessin de l'image de fond 
	}
	
	/**
	 * fonction d affichage de la partie gauche de la fenetre
	 */
	private void afficheGauche(Graphics2D g2) {
		
		g2.drawImage(gauche, 0, 0,this.deca,hauteurSansScale, null); // on affiche le fond
		g2.drawImage(Titre, 25, 25, 150, 50, null); // le titre en haut
		
		g2.setColor(new Color(0,0,0,200));	// un rectangle transparent noir pour ameliorer l affichage du texte
		g2.fillRect(20, hauteurSansScale/2-60, 150, 100);
		
		g2.setColor(Color.WHITE); // affichage du texte
		Font f = new Font("Courier", Font.BOLD, 30);
		g2.setFont(f);
		g2.drawString("Points:", 30, hauteurSansScale/2-30);
		if (raquettes.size()>0) { // petite securite dans l hypothese de l absence de requette
			g2.drawString("" + raquettes.get(0).getScore(), 30, (hauteurSansScale/2)+10);
		}
		else { g2.drawString("null", 30, (hauteurSansScale/2)+10);}
		
	}
	
	/**
	 *  fonction d affichage de la partie droite de la fenetre
	 */
	private void afficheDroite(Graphics2D g2) {
		
		// meme orincipe que la partie gauche
		g2.drawImage(droite, largeurSansScale-this.deca, 0,this.deca,hauteurSansScale, null);
		
		g2.setColor(new Color(0,0,0,200));	
		g2.fillRect(largeurSansScale-190, hauteurSansScale/2-60, 150, 100);
		
		g2.setColor(Color.WHITE);
		Font f = new Font("Courier", Font.BOLD, 30);
		g2.setFont(f);
		g2.drawString("Temps:", largeurSansScale-180, hauteurSansScale/2-30);
		g2.drawString("" + this.tps, largeurSansScale-180, (hauteurSansScale/2)+10);
	}
	
	/**
	 * fonction d affichage en cas de game over
	 */
	private void afficheGameOver(Graphics2D g2) {
		
		Color c = new Color(0,0,0,150); //on assombrit l ecran
		g2.setColor(c);
		g2.fillRect(deca, 0, largeurSansScale-2*deca, hauteurSansScale);
		g2.drawImage(gameOver, largeurSansScale/2-200, hauteurSansScale/2-150,400,300, null); // on affiche le texte
		
		retry.dessinButon(scalex,scaley, g2); // on affiche les boutons
		exit.dessinButon(scalex, scaley, g2);
	}
	
	/**
	 * fonction d affichage en cas de pause
	 */
	private void affichePause(Graphics2D g2) {
		
		Color c = new Color(0,0,0,150); //on assombrit l ecran
		g2.setColor(c);
		g2.fillRect(deca, 0, largeurSansScale-2*deca, hauteurSansScale);
		g2.drawImage(pauseTitre, largeurSansScale/2-200, hauteurSansScale/2-200,400,200, null); // on affiche le texte
		
		retry.dessinButon(scalex,scaley, g2); // on affiche les boutons
		exit.dessinButon(scalex, scaley, g2);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////
	//gestion
	///////////////////////////////////////////////////////////////////////////////
	/**
	 * fonction servant a appliquer le game over
	 */
	public void setGameOver() { 
	
		this.gameOverBool=true;
		
		this.add(retry); // on ajoute les boutons au panel
		this.add(exit);
		
	}
	
	/**
	 * fonction servant a appliquer le game over
	 */
	public void setPause() { 
	
		this.pauseBool=true;
		
		this.add(retry); // on ajoute les boutons au panel
		this.add(exit);
	}
	
	public void resetPause() {
		this.pauseBool=false;
		
		this.remove(retry); // on enleve les boutons au panel
		this.remove(exit);
	}
	
	/**
	 * fonction permettant de reinitialiser une partie
	 */
	public void clearGame() {
		
		this.tps = 0;
		this.gameOverBool=false;
		this.setListeBriques(new ArrayList<Brique>());
		this.setListeBalles(new ArrayList<Balle>());
		//this.setListeRaquettes(new ArrayList<Raquette>());
		this.remove(retry);
		this.remove(exit);
		this.pauseBool=false;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//gestion2
	///////////////////////////////////////////////////////////////////////////////
	
	/**
	 * fonction permettant de modifier la valeur du chrono
	 * @param chronos le temps a afficher
	 */
	public void setChronos(int chronos) {
		this.tps = chronos;
	}
	
	/**
	 * fonction permettant d ajouter une brique dans la liste de brique
	 * @param b la brique a ajouter
	 */
	public void ajouteBrique(Brique b) {
		briques.add(b);
	}
	
	/**
	 * fonction permettant d ajouter une balle dans la liste de balle
	 * @param b la balle a ajouter
	 */
	public void ajouteBalle(Balle b) {
	balles.add(b);
	}
	
	/**
	 * fonction permettant d ajouter une raquette dans la liste de raquette
	 * @param r la raquette a ajouter
	 */
	public void ajouteRaquette(Raquette r) {
	raquettes.add(r);
	}
	
	/**
	 * fonction permettant d appliquer directement une liste de briques a afficher
	 * @param b un ArrayList de Brique
	 */
	public void setListeBriques(ArrayList<Brique> b) {
	this.briques = b;
	}
	
	/**
	 * fonction permettant d appliquer directement une liste de balles a afficher
	 * @param b un ArrayList de Balle
	 */
	public void setListeBalles(ArrayList<Balle> b) {
	this.balles = b;
	}
	
	/**
	 * fonction permettant d appliquer directement une liste de raquettes a afficher
	 * @param r la liste de Raquette
	 */
	public void setListeRaquettes(ArrayList<Raquette> r) {
	this.raquettes = r;
	}
	
	/**
	 * fonction permettant d enlever une brique de la liste de briques
	 * @param b la Brique a enlever
	 */
	public void enleveBrique(Brique b) {
	this.briques.remove(b);
	}
	
	/**
	 * fonction permettant d enlever une Balle de la liste de balles
	 * @param b la Balle a enlever
	 */
	public void enleveBalle(Balle b) {
	this.balles.remove(b);
	}
	
	/**
	 * fonction permettant d enlever une raquette de la liste de raquettes
	 * @param r la Raquette a enlever
	 */
	public void enleveRaquette(Raquette r) {
	this.raquettes.remove(r);
	}
	
	/**
	 * fonction permettant de recuperer la liste de briques a afficher
	 * @return l ArrayList de Brique
	 */
	public ArrayList<Brique> getListeBriques() {
	return this.briques;
	}
	
	/**
	 * fonction permettant de recuperer la liste de balles a afficher
	 * @return l ArrayList de Balle
	 */
	public ArrayList<Balle> getListeBalles() {
	return this.balles;
	}
	
	/**
	 * fonction permettant de recuperer la liste de raquette a afficher
	 * @return l ArrayList de Raquette
	 */
	public ArrayList<Raquette> getListeRaquettes() {
	return this.raquettes;
	}
	
	/**
	 * fonction permettant de recuperer la valeur du scale sur x
	 * @return la valeur du scale sur x
	 */
	public double getScaleX() {
		return this.scalex;
	}
	
	/**
	 * fonction permettant de recuperer la valeur du scale sur y
	 * @return la valeur du scale sur y
	 */
	public double getScaleY() {
		return this.scaley;
	}
	
	/**
	 * fonction permettant de convertir un x donner en x sur le terrain
	 * @param x le x global
	 * @return le x local
	 */
	public int getXZone(int x) {
		return  (int)((x-this.deca*this.getScaleX())/this.getScaleX());
	}
	
	/**
	 * fonction permettant de renvoyer la valeur du decalage du a la fenetre de gauche
	 * @return la largeur de la fenetre de gauche
	 */
	public int getDeca() {
		return this.deca;
	}
	
	/**
	 * fonction permettant d appliquer directement la liste de power up
	 * @param p un ArrayList de PowerUp
	 */
	public void setListePowerUp(ArrayList<PowerUp> p) {
		this.powerUp = p;
	}
	
	/**
	 * fonction permettant d ajouter un powerUp a la liste de PowerUp a afficher
	 * @param p un powerUp
	 */
	public void ajoutePowerUp(PowerUp p) {
		this.powerUp.add(p);
	}
	
	/**
	 * fonction permettant d'enlever un power up a al liste de PowerUp a afficher
	 * @param p un powerUp
	 */
	public void enlevePowerUp(PowerUp p) {
		this.powerUp.remove(p);
	}
	
	/**
	 * fonction permettant de recuperer la liste de powerUp
	 * @return l ArrayList de powerUp
	 */
	public ArrayList<PowerUp> getListePowerUp() {
		return this.powerUp;
	}
	
	public void unsetGameOver() {
		this.gameOverBool=false;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//fonction pour appliquer des actions aux boutons
	///////////////////////////////////////////////////////////////////////////////

	// les fonction pour appliquer ou enlever les actions des boutons
	public void setActionListenerGamePause(ActionListener e) {
		pause.addActionListener(e);
	}

	public void setActionListenerGameRetry(ActionListener e) {
		retry.addActionListener(e);
	}
	
	public void setActionListenerGameExit(ActionListener e) {
		exit.addActionListener(e);
	}

	
	public void removeActionListenerGamePause(ActionListener e) {
		pause.removeActionListener(e);
	}
	
	public void removeActionListenerGameRetry(ActionListener e) {
		retry.removeActionListener(e);
	}
	
	public void removeActionListenerGameExit(ActionListener e) {
		exit.removeActionListener(e);
	}
}

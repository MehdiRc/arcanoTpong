package arcanoTpong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

public class Vue extends JFrame{
	private int hZoneJeu=800;//valeur par default
	private int lZoneJeu=1200; 
	
	
	private final static int tailleGauche=200;
	
	
	 //les jpanels
	private VueJPanelFrame jpf;
	private VueJPanelMenuP jpm;
	private VueJPanelSelection jps;
	private VueJPanelOption jpo;
	
	private CardLayout cl; //card layout pour gerer le changement  d interface
	
	private JPanel jp; // le jpanel courant utiliser par la jframe
	
	private static final String MENU = "Menu"; //string pour le card layout
	private static final String GAME = "Game";
	private static final String SELECT = "Select";
	private static final String OPTION = "Option";
	
	public Vue(int lZoneJeu, int hZoneJeu) {
		super();
		
		this.hZoneJeu=hZoneJeu;
		this.lZoneJeu=lZoneJeu+2*tailleGauche;
		
		setSize(this.lZoneJeu, this.hZoneJeu); // on set la taille de la fenetre
		
		this.setLayout(new BorderLayout());
		
		cl = new CardLayout();
		jp = new JPanel(cl);
		
		//creation des 4 fenetres
	    jpf = new VueJPanelFrame(this.lZoneJeu, this.hZoneJeu, tailleGauche);
	    jpm = new VueJPanelMenuP(this.lZoneJeu, this.hZoneJeu);
	    jps = new VueJPanelSelection(this.lZoneJeu, this.hZoneJeu);
	    jpo = new VueJPanelOption(this.lZoneJeu, this.hZoneJeu);
	    
	    //ajout des fenetres dans le card layout
		jp.add(jpf, GAME);
		jp.add(jpm, MENU);
		jp.add(jps, SELECT);
		jp.add(jpo, OPTION);
		
		jpm.isCurrent=true; // on commence dans le fenetre menu
	    ((CardLayout) jp.getLayout()).show(jp, MENU);
	    
	    getContentPane().add(jp, BorderLayout.CENTER);
	    
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    
	    /*
	     * boutons si dessous n ont aucune influence sur le contrleur ou le moteur, on initialise donc leur fonctions directement ici
	     */
	    setActionListenerSelectionRetour(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeToMenu();
			}
	    });
	    
	    setActionListenerOptionRetour(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeToMenu();
			}
	    });
	    
	    setActionListenerMenuGame(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeToSelect();
			}
	    	
	    });
	    
	    setActionListenerMenuOption(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeToOption();
			}
	    	
	    });
	    
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//controle de la vue
	///////////////////////////////////////////////////////////////////////////////
	/**
	 * fonction permettant de passer a la fenetre de jeu
	 */
	public void changeToGame() {
		jpf.isCurrent=true;
		jpm.isCurrent=false;
		jps.isCurrent=false;
		jpo.isCurrent=false;
		((CardLayout) jp.getLayout()).show(jp, GAME);
		System.out.println("passage à l'interface de jeux");
	}
	
	/**
	 * fonction permettant de passer a la fenetre de menu
	 */
	public void changeToMenu() {
		jpf.isCurrent=false;
		jpm.isCurrent=true;
		jps.isCurrent=false;
		jpo.isCurrent=false;
		((CardLayout) jp.getLayout()).show(jp, MENU);
		System.out.println("passage à l'interface du menu");
	}
	
	/**
	 * fonction permettant de passer a la fenetre de selection de niveau
	 */
	public void changeToSelect() {
		jpf.isCurrent=false;
		jpm.isCurrent=false;
		jps.isCurrent=true;
		jpo.isCurrent=false;
		((CardLayout) jp.getLayout()).show(jp, SELECT);
		System.out.println("passage à l'interface de selection de niveau");
	}
	
	/**
	 * fonction permettant de passer a la fenetre des options
	 */
	public void changeToOption() {
		jpf.isCurrent=false;
		jpm.isCurrent=false;
		jps.isCurrent=false;
		jpo.isCurrent=true;
		((CardLayout) jp.getLayout()).show(jp, OPTION);
		System.out.println("passage à l'interface d'option");
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//partie du jeu
	///////////////////////////////////////////////////////////////////////////////
	
	/**
	 * fonction devenu inutile
	 * @param score
	 */
	public void afficheScore(int score) {
	}
	
	/**
	 * fonction permettant de modifier la valeur du chrono
	 * @param chronos le temps a afficher
	 */
	public void afficheChronos(int chronos) {
		this.jpf.setChronos(chronos);
	}
	
	/**
	 * fonction permettant d ajouter une brique dans la liste de brique
	 * @param b la brique a ajouter
	 */
	public void ajouteBrique(Brique b) {
		jpf.ajouteBrique(b);
	}
	
	/**
	 * fonction permettant d ajouter une balle dans la liste de balle
	 * @param b la balle a ajouter
	 */
	public void ajouteBalle(Balle b) {
		jpf.ajouteBalle(b);
	}
	
	/**
	 * fonction permettant d ajouter une raquette dans la liste de raquette
	 * @param r la raquette a ajouter
	 */
	public void ajouteRaquette(Raquette r) {
		jpf.ajouteRaquette(r);
	}
	
	/**
	 * fonction permettant d appliquer directement une liste de briques a afficher
	 * @param b un ArrayList de Brique
	 */
	public void setListeBriques(ArrayList<Brique> b) {
		jpf.setListeBriques(b);
	}
	
	/**
	 * fonction permettant d appliquer directement une liste de balles a afficher
	 * @param b un ArrayList de Balle
	 */
	public void setListeBalles(ArrayList<Balle> b) {
		jpf.setListeBalles(b);
	}
	
	/**
	 * fonction permettant d appliquer directement une liste de raquettes a afficher
	 * @param r la liste de Raquette
	 */
	public void setListeRaquettes(ArrayList<Raquette> r) {
		jpf.setListeRaquettes(r);
	}
	
	/**
	 * fonction permettant d enlever une brique de la liste de briques
	 * @param b la Brique a enlever
	 */
	public void enleveBrique(Brique b) {
		jpf.enleveBrique(b);
	}
	
	/**
	 * fonction permettant d enlever une Balle de la liste de balles
	 * @param b la Balle a enlever
	 */
	public void enleveBalle(Balle b) {
		jpf.enleveBalle(b);
	}
	
	/**
	 * fonction permettant d enlever une raquette de la liste de raquettes
	 * @param r la Raquette a enlever
	 */
	public void enleveRaquette(Raquette r) {
		jpf.enleveRaquette(r);
	}
	
	/**
	 * fonction permettant de recuperer la liste de briques a afficher
	 * @return l ArrayList de Brique
	 */
	public ArrayList<Brique> getListeBriques() {
		return jpf.getListeBriques();
	}
	
	/**
	 * fonction permettant de recuperer la liste de balles a afficher
	 * @return l ArrayList de Balle
	 */
	public ArrayList<Balle> getListeBalles() {
		return jpf.getListeBalles();
	}
	
	/**
	 * fonction permettant de recuperer la liste de raquette a afficher
	 * @return l ArrayList de Raquette
	 */
	public ArrayList<Raquette> getListeRaquettes() {
		return jpf.getListeRaquettes();
	}

	/**
	 * fonction permettant de recuperer le panel principal
	 * @return le panel principal
	 */
	public JPanel getPanel() {
		return this.jp;
	}
	
	/**
	 * fonction permettant de recuperer la valeur du scale sur x
	 * @return la valeur du scale sur x
	 */
	public double getScaleX() {
		return this.jpf.getScaleX();
	}
	
	/**
	 * fonction permettant de recuperer la valeur du scale sur y
	 * @return la valeur du scale sur y
	 */
	public double getScaleY() {
		return this.jpf.getScaleY();
	}
	
	/**
	 * fonction permettant de renvoyer la valeur du decalage du a la fenetre de gauche
	 * @return la largeur de la fenetre de gauche
	 */
	public int getDeca() {
		return this.jpf.getDeca();
	}
	
	/**
	 * fonction permettant de convertir un x donner en x sur le terrain
	 * @param x le x global
	 * @return le x local
	 */
	public int getXZone(int x) {
		return  this.jpf.getXZone(x);
	}
	
	
	/**
	 * fonction servant a appliquer le game over
	 */
	public void setGameOver() {
		jpf.setGameOver();
	}
	
	/**
	 * fonction permettant de reinitialiser une partie
	 */
	public void clearGame() {
		jpf.clearGame();
	}
	
	/**
	 * fonction permettant d appliquer directement la liste de power up
	 * @param p un ArrayList de PowerUp
	 */
	public void setListePowerUp(ArrayList<PowerUp> p) {
		jpf.setListePowerUp(p);
	}
	
	/**
	 * fonction permettant d ajouter un powerUp a la liste de PowerUp a afficher
	 * @param p un powerUp
	 */
	public void ajoutePowerUp(PowerUp p) {
		jpf.ajoutePowerUp(p);
	}
	
	/**
	 * fonction permettant d'enlever un power up a al liste de PowerUp a afficher
	 * @param p un powerUp
	 */
	public void enlevePowerUp(PowerUp p) {
		jpf.enlevePowerUp(p);
	}
	
	/**
	 * fonction permettant de recuperer la liste de powerUp
	 * @return l ArrayList de powerUp
	 */
	public ArrayList<PowerUp> getListePowerUp() {
		return jpf.getListePowerUp();
	}
	
	public void unsetGameOver() {
		jpf.unsetGameOver();
	}
	
	/**
	 * fonction servant a appliquer le game over
	 */
	public void setPause() { 
		jpf.setPause();
	}
	
	public void resetPause() {
		jpf.resetPause();
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//fonction pour appliquer des actions aux boutons
	///////////////////////////////////////////////////////////////////////////////

	/*
	 * afin de pouvoir permettre au controleur d influencer le moteur en fonction des differents boutons appuyer on laisse le soin
	 * au controleur de creer les ActionListener et de les appliquer aux boutons via les fonctions ci dessous
	 */
	
	///////////////////////////////////////////////////////////////////////////////
	//boutons pour menu
	///////////////////////////////////////////////////////////////////////////////
	
	public void setActionListenerMenuOption(ActionListener e) {
		jpm.setActionListenerOption(e);
		System.out.println("action sur le bouton menu_option initialisé");
	}
	
	public void setActionListenerMenuGame(ActionListener e) {
		jpm.setActionListenerGame(e);
		System.out.println("action sur le bouton menu_game initialisé");
	}
	
	public void setActionListenerMenuArcade(ActionListener e) {
		jpm.setActionListenerArcade(e);
		System.out.println("action sur le bouton arcade initialisé");
	}
	
	public void removeActionListenerMenuOption(ActionListener e) {
		jpm.removeActionListenerOption(e);
		System.out.println("action sur le bouton menu_option supprimé");
	}
	
	
	public void removeActionListenerMenuGame(ActionListener e) {
		jpm.removeActionListenerGame(e);
		System.out.println("action sur le bouton menu_game supprimé");
	}
	
	public void removeActionListenerMenuArcade(ActionListener e) {
		jpm.removeActionListenerArcade(e);
		System.out.println("action sur le bouton arcade supprimé");
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//boutons pour le jeux
	///////////////////////////////////////////////////////////////////////////////
	
	public void setActionListenerGamePause(ActionListener e) {
		jpf.setActionListenerGamePause(e);
		System.out.println("action sur le bouton jeux_pause initialisé");
	}
	
	public void setActionListenerGameRetry(ActionListener e) {
		jpf.setActionListenerGameRetry(e);
		System.out.println("action sur le bouton jeux_retry initialisé");
	}
	
	public void setActionListenerGameExit(ActionListener e) {
		jpf.setActionListenerGameExit(e);
		System.out.println("action sur le bouton jeux_exit initialisé");
	}
	
	public void removeActionListenerGamePause(ActionListener e) {
		jpf.removeActionListenerGamePause(e);
		System.out.println("action sur le bouton jeux_pause supprimé");
	}
	
	public void removeActionListenerGameRetry(ActionListener e) {
		jpf.removeActionListenerGameRetry(e);
		System.out.println("action sur le bouton jeux_retry supprimé");
	}
	
	public void removeActionListenerGameExit(ActionListener e) {
		jpf.removeActionListenerGameExit(e);
		System.out.println("action sur le bouton jeux_exit supprimé");
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//boutons pour la selection de niveau
	///////////////////////////////////////////////////////////////////////////////
	
	public void setActionListenerSelectionLevel(ActionListener e, int index) {
		jps.setActionListenerSelectionLevel( e,index);
		System.out.println("action sur le boutonNiveau n°" + index + " initialisé");
	}
	
	public void removeActionListenerSelectionLevel(ActionListener e, int index) {
		jps.removeActionListenerSelectionLevel(e, index);
		System.out.println("action sur le boutonNiveau n°" + index + " supprimé");
	}
	
	public void setActionListenerSelectionRetour(ActionListener e) {
		jps.setActionListenerSelectionRetour(e);
		System.out.println("action sur le bouton SelectionRetour initialisé");
	}
	
	public void removeActionListenerSelectionRetour(ActionListener e) {
		jps.removeActionListenerSelectionRetour(e);
		System.out.println("action sur le bouton SelectionRetour supprimé");
	}

	///////////////////////////////////////////////////////////////////////////////
	//boutons pour les options
	///////////////////////////////////////////////////////////////////////////////
	
	public void setActionListenerOptionDifficulty(ActionListener e) {
		jpo.setActionListenerOptionDifficulty(e);
		System.out.println("action sur le bouton OptionDifficulty initialisé");
	}
	
	public void removeActionListenerOptionDifficulty(ActionListener e) {
		jpo.removeActionListenerOptionDifficulty(e);
		System.out.println("action sur le bouton OptionDifficulty supprimé");
	}
	
	public void setActionListenerOptionRetour(ActionListener e) {
		jpo.setActionListenerOptionRetour(e);
		System.out.println("action sur le bouton OptionRetour initialisé");
	}
	
	public void removeActionListenerOptionRetour(ActionListener e) {
		jpo.removeActionListenerOptionRetour(e);
		System.out.println("action sur le bouton OptionRetour supprimé");
	}
	
	
}

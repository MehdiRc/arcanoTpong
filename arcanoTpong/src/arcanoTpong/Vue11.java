package arcanoTpong;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

public class Vue11 extends JFrame{
	private int hZoneJeu=800;//valeur par default
	private int lZoneJeu=1200; 
	
	
	/*private static BufferedImage Brique;
	private static BufferedImage balle;
	private static BufferedImage raquette;*/
	
	private JLabel scoreJL;
	private JLabel chronosJL;
	
	private ArrayList<Brique> briques = new ArrayList<Brique>();
	private ArrayList<Balle> balles = new ArrayList<Balle>();
	private ArrayList<Raquette> raquettes = new ArrayList<Raquette>();

	
	private VueJPanel1 jp1;
	private VueJPanelFrame jpf;
	
	
	public Vue11(int lZoneJeu, int hZoneJeu) {
		super();
		
		this.hZoneJeu=hZoneJeu;
		this.lZoneJeu=lZoneJeu;
		
		setSize(lZoneJeu, hZoneJeu);
		
		AffineTransform tx0 = new AffineTransform();
		
		
		
		this.setLayout(new BorderLayout());
		scoreJL = new JLabel("score : ");
	    chronosJL   = new JLabel("temps : ");
	    JPanel top = new JPanel();
	    top.setLayout(new BorderLayout());
	    top.add(scoreJL, BorderLayout.WEST);
	    top.add(chronosJL,   BorderLayout.EAST);
	    getContentPane().add(top, BorderLayout.NORTH);
	    
	    
	    jp1= new VueJPanel1(this.lZoneJeu, this.hZoneJeu, new Vue(0,0));
	    

	    getContentPane().add(jp1, BorderLayout.CENTER);
	    
	    //JLabel chronosJL2   = new JLabel("temps : ");
	    //getContentPane().add(chronosJL2, BorderLayout.WEST);
	    //setSize(lZoneJeu, hZoneJeu)
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	

	
	//////////////////////////////////////////////////////////////////////
	
	public void afficheScore(int score) {
		scoreJL.setText("score : " + Integer.toString(score));
	}
	
	public void afficheChronos(int chronos) {
		chronosJL.setText("temps : " + Integer.toString(chronos));
	}
	
	
	public void ajouteBrique(Brique b) {
		briques.add(b);
	}
	
	public void ajouteBalle(Balle b) {
		balles.add(b);
	}
	
	public void ajouteRaquette(Raquette r) {
		raquettes.add(r);
	}
	
	
	public void setListeBriques(ArrayList<Brique> b) {
		this.briques = b;
	}
	
	public void setListeBalles(ArrayList<Balle> b) {
		this.balles = b;
	}
	
	public void setListeRaquettes(ArrayList<Raquette> r) {
		this.raquettes = r;
	}
	

	public void enleveBrique(Brique b) {
		this.briques.remove(b);
	}
	
	public void enleveBalle(Balle b) {
		this.balles.remove(b);
	}
	
	public void enleveRaquette(Raquette r) {
		this.raquettes.remove(r);
	}
	
	public ArrayList<Brique> getListeBriques() {
		return this.briques;
	}
	
	public ArrayList<Balle> getListeBalles() {
		return this.balles;
	}
	
	public ArrayList<Raquette> getListeRaquettes() {
		return this.raquettes;
	}
	
	public JPanel getPanel() {
		return this.jp1;
	}
	
	public double getScaleX() {
		return jp1.scalex;
	}
	
	public double getScaleY() {
		return jp1.scaley;
	}
	
	
	

	 public static void main(String args[]){
		    Vue v = new Vue(1200,800);
		    v.afficheScore(45);
		    v.afficheScore(1111111);
		    v.ajouteBrique(new Brique(0,0,0,0,80,40));
		    v.ajouteBalle(new Balle(40,40));
		    v.ajouteRaquette(new Raquette(10,10));
		    v.ajouteBrique(new Brique(0,0,1200-80,0,80,40));
	 }
	
}

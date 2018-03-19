package arcanoTpong;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

public class Vue extends JFrame{
	private int hZoneJeu=800;//valeur par default
	private int lZoneJeu=1200; 
	
	
	/*private static BufferedImage Brique;
	private static BufferedImage balle;
	private static BufferedImage raquette;*/
	

	private double scaleX = 1; //pour avoir acces facilement au valeur de redimentionnement. par default 1
	private double scaleY = 1;
	
	
	
	 //les jpanels
	private VueJPanelFrame jpf;
	private VueJPanelMenuP jpm;
	
	private JPanel jp; // le jpanel courant utiliser par la jframe
	
	public Vue(int lZoneJeu, int hZoneJeu) {
		super();
		
		this.hZoneJeu=hZoneJeu;
		this.lZoneJeu=lZoneJeu;
		
		setSize(lZoneJeu, hZoneJeu);
		
		
		
		
		this.setLayout(new BorderLayout());
		
		
		
	    jpf = new VueJPanelFrame(this.lZoneJeu, this.hZoneJeu);
	    jp = jpf;
	    
	    getContentPane().add(jpf, BorderLayout.CENTER);
	    

	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);

	}
	
	

	
	//////////////////////////////////////////////////////////////////////
	
	public void afficheScore(int score) {
		//scoreJL.setText("score : " + Integer.toString(score));
		//this.jpf.setScore(score);
	}
	
	public void afficheChronos(int chronos) {
		//chronosJL.setText("temps : " + Integer.toString(chronos));
		//this.jpf.setChronos(chronos);
	}
	
	
	public void ajouteBrique(Brique b) {
		jpf.briques.add(b);
	}
	
	public void ajouteBalle(Balle b) {
		jpf.balles.add(b);
	}
	
	public void ajouteRaquette(Raquette r) {
		jpf.raquettes.add(r);
	}
	
	
	public void setListeBriques(ArrayList<Brique> b) {
		jpf.briques = b;
	}
	
	public void setListeBalles(ArrayList<Balle> b) {
		jpf.balles = b;
	}
	
	public void setListeRaquettes(ArrayList<Raquette> r) {
		jpf.raquettes = r;
	}
	

	public void enleveBrique(Brique b) {
		jpf.briques.remove(b);
	}
	
	public void enleveBalle(Balle b) {
		jpf.balles.remove(b);
	}
	
	public void enleveRaquette(Raquette r) {
		jpf.raquettes.remove(r);
	}
	
	public ArrayList<Brique> getListeBriques() {
		return jpf.briques;
	}
	
	public ArrayList<Balle> getListeBalles() {
		return jpf.balles;
	}
	
	public ArrayList<Raquette> getListeRaquettes() {
		return jpf.raquettes;
	}
	
	public JPanel getPanel() {
		return this.jp;
	}
	
	public double getScaleX() {
		return this.jpf.getScaleX();
	}
	
	public double getScaleY() {
		return this.jpf.getScaleY();
	}
	
	
	

	 public static void main(String args[]){
		    Vue v = new Vue(1200,800);
		    v.afficheScore(45);
		    v.afficheScore(11111112);
		    v.ajouteBrique(new Brique(0,0,0,0,80,40));
		    v.ajouteBalle(new Balle(40,40));
		    v.ajouteRaquette(new Raquette(10,10));
		    v.ajouteBrique(new Brique(0,0,1200-80,0,80,40));
	 }
	
}

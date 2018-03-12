package arcanoTpong;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

public class Vue extends JFrame{
	private JPanel jp;
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
	
	private double scalex;
	private double scaley;
	
	private VueJPanel1 jp1;
	private VueJPanelFrame jpf;
	
	
	public Vue(int lZoneJeu, int hZoneJeu) {
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
	    
	    /*jp = new JPanel(){
	        @Override
	        public void paintComponent(Graphics g){
	          super.paintComponent(g);
	          Graphics2D g2 = (Graphics2D)g;

	          //this.setSize(new Dimension(lZoneJeu,hZoneJeu));
	          System.out.println(this.getSize());
	          AffineTransform tx1 = new AffineTransform();
	          scalex = (double)jp.getWidth()/(double)lZoneJeu;
	          scaley = (double)jp.getHeight()/(double)hZoneJeu;
	          tx1.scale(scalex, scaley);
	          System.out.println(scalex);
	          //g2.setTransform(tx1);
	          
	          //this.setSize(new Dimension(lZoneJeu,hZoneJeu));
	          
	          Color briqueColor = new Color(100, 25, 50);
	          
	          for(Brique b : briques) {
	        	  afficheBrique(b.x, b.y, b.largeur, b.hauteur, g2, briqueColor);
	          }
	          for(Balle b : balles) {
	        	  afficheBalle(b.x, b.y, b.largeur, b.hauteur, g2, Color.yellow.darker());
	          }
	          for(Raquette r : raquettes) {
	        	  afficheRaquette(r.x, r.y, r.largeur, r.hauteur, g2, Color.red);
	          }
	          //g2.setTransform(tx0);
	        }
	    };*/
	    //jp.setMinimumSize(new Dimension(lZoneJeu, hZoneJeu));
	    
	    //jp.setBackground(Color.WHITE);
	    //getContentPane().add(jp, BorderLayout.CENTER);
	    
	    jp1= new VueJPanel1(this.lZoneJeu, this.hZoneJeu, this);
	    
	    
	    
	    
	    
	    
	    getContentPane().add(jp1, BorderLayout.CENTER);
	    //JLabel chronosJL2   = new JLabel("temps : ");
	    //getContentPane().add(chronosJL2, BorderLayout.WEST);
	    //setSize(lZoneJeu, hZoneJeu);
	    System.out.println(this.getSize());
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	///////////////////////////////////////////////////////////////////////
	
	private void afficheBrique(int x, int y, int width, int height, Graphics2D g2, Color c) {
		
		g2.setColor(c);
		g2.fillRect(x, y, width, height);
		g2.setColor(Color.black);
		g2.drawRect(x, y, width, height);

	}
	
	private void afficheBalle(int x, int y, int width, int height, Graphics2D g2, Color c) {
		g2.setColor(c);
		g2.fillOval(x, y, width, height);

	}
	
	private void afficheRaquette(int x, int y, int width, int height, Graphics2D g2, Color c) {
		g2.setColor(c);
		g2.fillRect(x, y, width, height);
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
	
	
	public int getMouseX() {
		//return jp.getMousePosition().x;
		Point tmp = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(tmp, jp1);
		return tmp.x;
	}
	

	 public static void main(String args[]){
		    Vue v = new Vue(1200,800);
		    v.afficheScore(45);
		    v.afficheScore(1111111);
		    v.ajouteBrique(new Brique(0,0,0,0,80,40));
		    v.ajouteBalle(new Balle(40,40));
		    v.ajouteRaquette(new Raquette(10,10));
		    v.ajouteBrique(new Brique(0,0,1200-80,0,80,40));
		    System.out.println("mouse " + v.getMouseX());
	 }
	
}

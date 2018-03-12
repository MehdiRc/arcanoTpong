package arcanoTpong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.*;
import java.awt.*;

public class VueJPanel1 extends JPanel{
	

	private int largeurInit;
	private int hauteurInit;
	
	public double scalex;
	public double scaley;
	
	
	private Vue v;
	private AffineTransform tx0;
	
	public VueJPanel1(int largeur, int hauteur, Vue v){
		
		super();
		
		this.largeurInit=largeur;
		this.hauteurInit=hauteur;
		this.v= v;
		this.tx0 = new AffineTransform();
		
		this.setSize(largeur, hauteur);
		
		this.setBackground(Color.white);
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		AffineTransform tx1 = new AffineTransform();
        scalex = (double)this.getWidth()/(double)largeurInit;
        scaley = (double)this.getHeight()/(double)hauteurInit;
        tx1.scale(scalex, scaley);
        
        /*System.out.println("scalex " + scalex);
        System.out.println("scaley " + scaley);
        System.out.println(this.getHeight());
        System.out.println(this.getWidth());*/
        
        g2.scale(scalex, scaley);
        
        
        Color briqueColor = new Color(100, 25, 50);
        
        for(Brique b : v.getListeBriques()) {
      	  //afficheBrique((int)(b.x*scalex),(int) (b.y*scaley), (int)(b.largeur*scalex),(int)(b.hauteur*scaley), g2, briqueColor);
        	afficheBrique(b.x,b.y, b.largeur,b.hauteur, g2, briqueColor);
        }
        for(Balle b : v.getListeBalles()) {
      	  afficheBalle(b.x, b.y, b.largeur, b.hauteur, g2, Color.yellow.darker());
        }
        for(Raquette r : v.getListeRaquettes()) {
      	  afficheRaquette(r.x, r.y, r.largeur, r.hauteur, g2, Color.red);
        }
        g2.setTransform(tx0);
		
	}


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
	

}

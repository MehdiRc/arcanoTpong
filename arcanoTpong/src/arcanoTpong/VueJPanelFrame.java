package arcanoTpong;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueJPanelFrame extends JPanel{
	
	private int hauteurInit=800;//valeur par default
	private int largeurInit=1200; 
	
	
	/*private static BufferedImage Brique;
	private static BufferedImage balle;
	private static BufferedImage raquette;*/
	
	private JLabel scoreJL;
	private JLabel chronosJL;
	
	private ArrayList<Brique> briques = new ArrayList<Brique>();
	private ArrayList<Balle> balles = new ArrayList<Balle>();
	private ArrayList<Raquette> raquettes = new ArrayList<Raquette>();
	
	
	private VueJPanel1 jp1;
	
	public VueJPanelFrame(int largeur, int hauteur) {
	
		
		this.setLayout(new BorderLayout());
		scoreJL = new JLabel("score : ");
	    chronosJL   = new JLabel("temps : ");
	    JPanel top = new JPanel();
	    top.setLayout(new BorderLayout());
	    top.add(scoreJL, BorderLayout.WEST);
	    top.add(chronosJL,   BorderLayout.EAST);
	    this.add(top, BorderLayout.NORTH);
	    
		
	    jp1 = new VueJPanel1(this.largeurInit, this.hauteurInit, new Vue(0,0));
	    
	
	    this.add(jp1, BorderLayout.CENTER);
	}
	

}

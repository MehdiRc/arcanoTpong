package arcanoTpong;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

public class Vue extends JFrame{
	private JPanel jp;
	private int length=800;
	private int width=800;
	/*private static BufferedImage Brique;
	private static BufferedImage balle;
	private static BufferedImage raquette;*/
	
	JLabel scoreJL;
	JLabel chronosJL;

	
	
	public ArrayList<Brique> briques = new ArrayList<Brique>();
	public ArrayList<Balle> balles = new ArrayList<Balle>();
	public ArrayList<Raquette> raquettes = new ArrayList<Raquette>();
	
	public Vue() {
		super();
		setLayout(new BorderLayout());
		scoreJL = new JLabel("score : ");
	    chronosJL   = new JLabel("temps : ");
	    JPanel top = new JPanel();
	    top.setLayout(new BorderLayout());
	    top.add(scoreJL, BorderLayout.WEST);
	    top.add(chronosJL,   BorderLayout.EAST);
	    getContentPane().add(top, BorderLayout.NORTH);
	    
	    jp = new JPanel(){
	        @Override
	        public void paintComponent(Graphics g){
	          super.paintComponent(g);

	          Graphics2D g2 = (Graphics2D)g;
	          
	          for(Brique b : briques) {
	        	  afficheBrique(b.x, b.y, b.largeur, b.hauteur,g2);
	        	 /*g2.setColor(Color.BLUE);
	        	  g2.fillRect(30, 30, 50, 50);*/
	          }
	        }
	    };
	    
	    jp.setBackground(Color.WHITE);
	    getContentPane().add(jp, BorderLayout.CENTER);
	    setSize(width, length);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	public void afficheBrique(int x, int y, int width, int heigth, Graphics2D g2) {
		Color randomColor = new Color(100, 0, 50);
		g2.setColor(randomColor);
		g2.fillRect(x, y, width, heigth);
		g2.setColor(Color.black);
		g2.drawRect(x, y, width, heigth);

	}
	
	public void afficheBalle(int x, int y, int width, int heigth) {
		
	}
	
	public void afficheRaquette(int x, int y, int width, int heigth) {
		
	}
	
	public void afficheScore(int score) {
		scoreJL.setText("score : " + Integer.toString(score));
	}
	
	public void afficheChronos(int chronos) {
		chronosJL.setText("temps : " + Integer.toString(chronos));
	}
	
	public void ajouteBrique () {
		briques.add(new Brique(10,10,50,50,200,100));
	}
	

	
}

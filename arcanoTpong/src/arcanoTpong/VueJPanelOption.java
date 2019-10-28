package arcanoTpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class VueJPanelOption extends JPanel{

	private int largeurInit;
	private int hauteurInit;
	
	private double scalex;
	private double scaley;
	
	private AffineTransform tx0;
	
	private ButtonScalable diff;
	private ButtonScalable retour;
	
	//liste des images
	private static BufferedImage Brique;
	private static BufferedImage Fond;
	private static BufferedImage Retour1;
	private static BufferedImage Retour2;
	private static BufferedImage Option;
	private static BufferedImage DiffButton1;
	private static BufferedImage DiffButton2;
	
	protected boolean isCurrent=false;
	
	//valeur initiale
	private String Difficulty = "Medium";
	
	public VueJPanelOption(int largeur, int hauteur) {
		
		super();
		
		this.setLayout(null);
		
		this.setBackground(Color.BLACK);
		
		this.setBounds(0, 0, largeur, hauteur);
		
		this.largeurInit = largeur;
		this.hauteurInit = hauteur;
		
		//chargement des images
		try {
		      Fond = ImageIO.read(new File("./IMAGES/fondMenu.jpg"));
		      Brique = ImageIO.read(new File("./IMAGES/brique.png"));
		      Retour1 = ImageIO.read(new File("./IMAGES/retourRond1.png"));
		      Retour2 = ImageIO.read(new File("./IMAGES/retourRond2.png"));
		      Option = ImageIO.read(new File("./IMAGES/Option.png"));
		      DiffButton1 = ImageIO.read(new File("./IMAGES/DiffButton1.png"));
		      DiffButton2 = ImageIO.read(new File("./IMAGES/DiffButton2.png"));
		} catch (IOException e) {
		      e.printStackTrace();
		    }
		
		//on creer nos boutons
		this.diff = new ButtonScalable("Difficulte", this.getWidth()/2-100, this.getHeight()/2-50, 200, 75, DiffButton1, DiffButton2);
		this.add(diff);
		this.retour = new ButtonScalable("Retour", 50, this.getHeight()-125, 75,75,Retour1,Retour2);
		this.add(retour);
		
		/*
		 * on ajoute une action sur le bouton pour changer l affichage, cette action n a pas a etre gerer par les couches superieur
		 * en revanche les couches superieur doivent pouvoir ajouter des actions pour modifier en consequence
		 */
		setActionListenerOptionDifficulty(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (Difficulty) {
				
				case "Medium": Difficulty = "Hard";break; // on permute juste les valeurs
				case "Hard": Difficulty = "Easy";break;
				case "Easy": Difficulty = "Medium";break;
				default : break;
				}
				
			}
			
		});
		
	}
	
	/**
	 * surcharge de paintComponent
	 */
	public void paintComponent(Graphics g){
		
		if (isCurrent) { //si la fenetre courante a afficher ets celle ci
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			AffineTransform tx1 = new AffineTransform();
	        scalex = (double)this.getWidth()/(double)largeurInit; // calcul des valeurs du scale
	        scaley = (double)this.getHeight()/(double)hauteurInit;
	        

	        this.tx0 = new AffineTransform();
	        

	        tx1.scale(scalex, scaley);
	        
	        
	        g2.drawImage(Fond, 0, 0, this.getWidth(), this.getHeight(), null); // on affiche le fond
	        
	        g2.setTransform(tx1); // on applique le scale
	        
	        g2.drawImage(Option, 300, 25, 600, 100, null); // on affiche le titre
	        
	        diff.dessinButon(scalex, scaley, g2); // on affiche le bouton
	        
	        g2.setColor(new Color(0,0,0,200)); // affichage de du texte relatif a la difficulte	
			g2.fillRect(600+150,250, 150, 75);
			g2.setColor(Color.WHITE);
			Font f = new Font("Courier", Font.BOLD, 30);
			g2.setFont(f);
			g2.drawString(Difficulty, 600+160,300);
	        
	        
	        retour.dessinButon(scalex, scaley, g2); // on dessine l autre bouton
	        

	        g2.setTransform(tx0);

		}
	}
	
	/*
	 * fonctions permettant aux couches superieurs d appliquer des actions sur les boutons
	 */
	
	public void setActionListenerOptionDifficulty(ActionListener e) {
		diff.addActionListener(e);
	}
	
	public void removeActionListenerOptionDifficulty(ActionListener e) {
		diff.removeActionListener(e);
	}
	
	public void setActionListenerOptionRetour(ActionListener e) {
		retour.addActionListener(e);
	}
	
	public void removeActionListenerOptionRetour(ActionListener e) {
		retour.removeActionListener(e);
	}
	
}

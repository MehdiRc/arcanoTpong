package arcanoTpong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class VueJPanelSelection extends JPanel{

	private int largeurInit;
	private int hauteurInit;
	
	private double scalex;
	private double scaley;
	
	private AffineTransform tx0;
	
	private final static int nbLigne = 3; //nombre de ligne de boutons
	private final static int nbColonne = 8; // nombre de colonnes de bouton
	
	private final static int ecartButton = 50; // l ecart entre les boutons
	
	//les boutons
	private ArrayList<ButtonScalable> levels;
	private ButtonScalable retour;
	
	//liste des images
	private static BufferedImage Brique;
	private static BufferedImage Fond;
	private static BufferedImage Retour1;
	private static BufferedImage Retour2;
	private static BufferedImage Niveaux;
	
	//liste des images des boutons
	private static ArrayList<BufferedImage> skinLevels1;
	private static ArrayList<BufferedImage> skinLevels2;
	
	protected boolean isCurrent=false; // boolean permetant de savoir si c est le panel a afficher
	
	public VueJPanelSelection(int largeur, int hauteur) {
		
		super();
		
		this.setLayout(null);
		
		this.setBounds(0, 0, largeur, hauteur); // on set la taille de la fenetre
		
		this.largeurInit = largeur;
		this.hauteurInit = hauteur;
		
		this.levels = new ArrayList<ButtonScalable>();
		
		skinLevels1= new ArrayList<BufferedImage>();
		skinLevels2= new ArrayList<BufferedImage>();
		
		//chargement des images
		try {
		      Fond = ImageIO.read(new File("./IMAGES/fondMenu.jpg"));
		      Brique = ImageIO.read(new File("./IMAGES/brique.png"));
		      Retour1 = ImageIO.read(new File("./IMAGES/retourRond1.png"));
		      Retour2 = ImageIO.read(new File("./IMAGES/retourRond2.png"));
		      Niveaux = ImageIO.read(new File("./IMAGES/Niveaux.png"));
		      
		      for (int i=1; i<=nbLigne*nbColonne; i++) {
		    	  //chargements des images des boutons de niveaux
		    	  String tmp1 = "./IMAGES/" + Integer.toString(i) + "1.png";
		    	  String tmp2 = "./IMAGES/" + Integer.toString(i) + "2.png";
			      skinLevels1.add(ImageIO.read(new File(tmp1)));
			      skinLevels2.add(ImageIO.read(new File(tmp2))); 
		      }
 
		      
		} catch (IOException e) {
		      e.printStackTrace();
		    }
		
		initButtonListe(); // on initialise les boutons
		this.retour= new ButtonScalable("Retour", 50,50,75,75,Retour1,Retour2);
		this.add(retour);
		
	}

	/**
	 * fonction d initialisation de la liste de bouton
	 */
	private void initButtonListe() {
		
		int l= this.getWidth()-200-(nbColonne-1)*50; // la largeur des briques côte à côte
		int h=this.getHeight()-300;
		
		int numLevel=0;
		
		for (int i=0; i<nbLigne; i++) { // nombre de lignes = 3
			for (int j=0; j<nbColonne; j++) { // nombre de colonnes = 8
				
				ButtonScalable tmp = new ButtonScalable(""+ numLevel,100+(j*(ecartButton+l/nbColonne)),165+(i*(ecartButton+l/nbColonne)),l/nbColonne,l/nbColonne,skinLevels1.get(numLevel),skinLevels2.get(numLevel));
				this.levels.add(tmp);
				this.add(tmp);
				
				numLevel++;
			}
		}
	}
	
	public void paintComponent(Graphics g){
		
		
		if (isCurrent) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			AffineTransform tx1 = new AffineTransform();
	        scalex = (double)this.getWidth()/(double)largeurInit; // on calcul le scale
	        scaley = (double)this.getHeight()/(double)hauteurInit;
	        

	        this.tx0 = new AffineTransform();
	        

	        tx1.scale(scalex, scaley);
	        
	        g2.drawImage(Fond, 0, 0, this.getWidth(), this.getHeight(), null); // on affiche le fond avant d appliquer le scale
	        
	        g2.setTransform(tx1);
	        
	        g2.drawImage(Niveaux, 300, 25, 600, 100, null); // on affiche le titre
	        
	        //on affiche les boutons
	        for(ButtonScalable b : levels) {
	        	b.dessinButon(scalex, scaley, g2);
	        }
	        retour.dessinButon(scalex, scaley, g2);

	        g2.setTransform(tx0);

		}
	}
	
	/*
	 * fonctions pour permettre aux couches superieurs d appliquer les actions sur les boutons
	 */
	
	public void setActionListenerSelectionLevel(ActionListener e, int index) {
		levels.get(index).addActionListener(e);
	}
	
	public void removeActionListenerSelectionLevel(ActionListener e, int index) {
		levels.get(index).removeActionListener(e);
	}
	
	public void setActionListenerSelectionRetour(ActionListener e) {
		retour.addActionListener(e);
	}
	
	public void removeActionListenerSelectionRetour(ActionListener e) {
		retour.removeActionListener(e);
	}
	
	
	
	
	
}

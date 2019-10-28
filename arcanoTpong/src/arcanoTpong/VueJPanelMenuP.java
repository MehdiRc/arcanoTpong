package arcanoTpong;

import java.awt.BorderLayout;
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
import javax.swing.JButton;
import javax.swing.JPanel;

public class VueJPanelMenuP extends JPanel{
	
	private int largeurInit;
	private int hauteurInit;
	
	protected double scalex;
	protected double scaley;
	
	private AffineTransform tx0;
	
	
	//liste des images
	private static BufferedImage fond;
	private static BufferedImage Brique;
	private static BufferedImage OptionButton1;
	private static BufferedImage OptionButton2;
	private static BufferedImage ArcadeButton1;
	private static BufferedImage ArcadeButton2;
	private static BufferedImage JouerButton1;
	private static BufferedImage JouerButton2;
	private static BufferedImage Titre;
	
	protected boolean isCurrent=false;
	
	//les boutons
	private ButtonScalable option;
	private ButtonScalable game;
	private ButtonScalable arcade;
	
	public VueJPanelMenuP(int largeur, int hauteur) {
		super();
		
		this.setLayout(null);

		this.largeurInit = largeur;
		this.hauteurInit = hauteur;
		
		this.setBounds(0, 0, largeur, hauteur);
		
		this.setVisible(true);
		
		//on charge les images
		try {
		      fond = ImageIO.read(new File("./IMAGES/fondMenu.jpg"));
		      Brique = ImageIO.read(new File("./IMAGES/brique.png"));
		      OptionButton1 = ImageIO.read(new File("./IMAGES/OptionButton.png"));
		      OptionButton2 = ImageIO.read(new File("./IMAGES/OptionButton2.png"));
		      ArcadeButton1 = ImageIO.read(new File("./IMAGES/ArcadeButton1.png"));
		      ArcadeButton2 = ImageIO.read(new File("./IMAGES/ArcadeButton2.png"));
		      JouerButton1 = ImageIO.read(new File("./IMAGES/JouerButton1.png"));
		      JouerButton2 = ImageIO.read(new File("./IMAGES/JouerButton2.png"));
		      Titre = ImageIO.read(new File("./IMAGES/Titre.png"));
		} catch (IOException e) {
		      e.printStackTrace();
		    }
		
		//on creer et on pose les boutons
		game = new ButtonScalable("Jouer", this.getWidth()/2-150,200,300,75, JouerButton1, JouerButton2);
		this.add(game);
		arcade = new ButtonScalable("Arcade", this.getWidth()/2-150,200+75+75/2,300,75, ArcadeButton1, ArcadeButton2);
		this.add(arcade);
		option = new ButtonScalable("Option", this.getWidth()/2-150, this.getHeight()-175, 300,75, OptionButton1, OptionButton2);
		this.add(option);
		
		
	}
	
	public void paintComponent(Graphics g){
		
		
		if (isCurrent) { // si c est la fenetre courante, on l affiche
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			AffineTransform tx1 = new AffineTransform();
	        scalex = (double)this.getWidth()/(double)largeurInit; // on calcule le scale
	        scaley = (double)this.getHeight()/(double)hauteurInit;
	        

	        this.tx0 = new AffineTransform();
	        

	        tx1.scale(scalex, scaley);
	        
	        g2.drawImage(fond, 0, 0, this.getWidth(), this.getHeight(), null); // on affiche le fond avant d appliquer le scale
	        g2.setTransform(tx1); // on applique le scale
	        
	        game.dessinButon(scalex, scaley, g2); // on affiche les boutons
	        option.dessinButon(scalex, scaley, g2);
	        arcade.dessinButon(scalex, scaley, g2);

	        g2.drawImage(Titre, 200, 25, 800, 200, null); // on affiche le titre
	        
	        g2.setTransform(tx0);
	        
		
		}
	}
	
	/*
	 * fonction permettant aux couches superieurs d appliquer des actions aux boutons
	 */
	
	public void setActionListenerOption(ActionListener e) {
		option.addActionListener(e);
	}
	
	public void setActionListenerGame(ActionListener e) {
		game.addActionListener(e);
	}
	
	public void setActionListenerArcade(ActionListener e) {
		arcade.addActionListener(e);
	}
	
	public void removeActionListenerOption(ActionListener e) {
		option.removeActionListener(e);
	}
	
	public void removeActionListenerGame(ActionListener e) {
		game.removeActionListener(e);
	}
	
	public void removeActionListenerArcade(ActionListener e) {
		arcade.removeActionListener(e);
	}
	

}

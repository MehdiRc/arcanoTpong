package arcanoTpong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ButtonScalable extends JButton{

	private int largeur=200; //les donnees concernants le bouton
	private int hauteur=100;
	private int x=0;
	private int y=0;
	
	private BufferedImage image1; // l image non presser
	private BufferedImage image2; // l image presser
	
	private boolean pressed = false;
	
	private String texte; // le nom du bouton
	
	public ButtonScalable(String txt, int x, int y, int largeur, int hauteur, BufferedImage image1, BufferedImage image2) {
		super();
		setText(txt);
		this.texte=txt;
		
		this.largeur = largeur; //largeur du bouton
		this.hauteur=hauteur; // hauteur du bouton
		this.x=x; //x sur le panel
		this.y=y; //y sur le panel
		
		this.image1=image1; // image afficher sinon cliquer
		this.image2=image2; // image afficher si cliquer
		
		setSize(largeur, hauteur); //taille par default		
		
		setOpaque(false);
		setContentAreaFilled(false); // On met a false pour empacher le composant de peindre l interieur du JButton.
		setBorderPainted(false); // De meme, on ne veut pas afficher les bordures.
		setFocusPainted(false); // On n affiche pas l effet de focus.

		
		this.setLocation(this.x,this.y);

		
		this.setVisible(true);
		
		
		/*
		 * on applique un listener local au panel du bouton afin de pouvoir gerer a nouveau l effet de clic
		 */
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				pressed=true;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				pressed=false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				pressed=true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				pressed=false;
			}
	      });
		
	}
	
	
	//surcharge de paintComponent avec rien dedans pour ne rien afficher car les jbutton bug lors des scales et repositionnement en particulier avec windows
	public void paintComponent(Graphics g){
	}
	
	/*
	 * du fait de nombreux bugs du a l affiche du bouton lors du repositionnement en particulier sur windows,
	 * on ajoute des fonctions permettant d'afficher des images par rapport au graphics2D du panel contenant le bouton
	 * images que l'on place par dessus la position du bouton
	 */
	
	/**
	 * fonction permettant d'afficher le bouton en utilisant le graphics du panel principal
	 * @param x le x du bouton
	 * @param y le y du bouton
	 * @param largeur la largeur du bouton
	 * @param hauteur la hauteur du bouton
	 * @param scalex la valeur du scale sur x
	 * @param scaley la valeur du scale sur y
	 * @param g2 le graphics2D du panel contenant le bouton
	 */
    public void dessinButon(int x, int y, int largeur, int hauteur, double scalex, double scaley, Graphics2D g2) {
        
       	if (this.pressed) { // on verifie si le bouton est appuyer ou non
       		if (image2 == null) return;// securite
	        setBounds((int)(x*scalex), (int)(y*scaley),(int) (largeur*scalex), (int)(hauteur*scaley));// on repositionne le bouton au bon endroit
	        g2.drawImage(image2, x, y, largeur, hauteur, null);// on affiche l image par dessus (le scale est deja gerer par la couche superieur)
    	}
    	else {
	        if (image1 == null) return;
	        setBounds((int)(x*scalex), (int)(y*scaley),(int) (largeur*scalex), (int)(hauteur*scaley));
	        g2.drawImage(image1, x, y, largeur, hauteur, null);
    	}

    }
    
    /**
	 * fonction permettant d'afficher le bouton en utilisant le graphics du panel principal
	 * @param x le x du bouton
	 * @param y le y du bouton
     * @param g2 le graphics2D du panel contenant le bouton
     */
    public void dessinButon(double scalex, double scaley, Graphics2D g2) {
        
    	if (this.pressed) { // on verifie si le bouton est appuyer ou non
    		if (image2 == null) return; // securite
	        setBounds((int)(x*scalex), (int)(y*scaley),(int) (largeur*scalex), (int)(hauteur*scaley)); // on repositionne le bouton au bon endroit
	        g2.drawImage(image2, x, y, largeur, hauteur, null); // on affiche l image par dessus (le scale est deja gerer par la couche superieur)
    	}
    	else {
	        if (image1 == null) return;
	        setBounds((int)(x*scalex), (int)(y*scaley),(int) (largeur*scalex), (int)(hauteur*scaley));
	        g2.drawImage(image1, x, y, largeur, hauteur, null);
    	}
    	
    }
    
	/**
	 * fonction permettant de recuperer la hauteur du bouton
	 * @return la hauteur du bouton
	 */
	public int getH() {
		return this.hauteur;
	}
	
	/**
	 * fonction permettant de recuperer la largeur du bouton
	 * @return la largeur du bouton
	 */
	public int getL() {
		return this.largeur;
	}
	
	/**
	 * fonction permettant de recuperer le x du bouton
	 * @return le x du bouton
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * fonction permettant de recuperer le y du bouton
	 * @return le y du bouton
	 */
	public int getY() {
		return this.y;
	}
	
	
	
}

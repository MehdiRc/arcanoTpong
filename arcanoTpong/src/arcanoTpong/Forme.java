package arcanoTpong;

public class Forme {
	protected int x,y;
	protected int largeur, hauteur;
	protected int vecteurX, vecteurY;
	
	public Forme(){
		this.x=0;
		this.y=0;
		this.largeur=0;
		this.hauteur=0;
		this.vecteurX=0;
		this.vecteurY=0;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getLargeur(){
		return this.largeur;
	}
	
	public int getHauteur(){
		return this.hauteur;
	}
	
	public int getVecteurX(){
		return this.vecteurX;
	}
	
	public int getVecteurY(){
		return this.vecteurY;
	}
	
}

package arcanoTpong;

public class Forme {
	protected int x,y;
	protected int largeur, hauteur;
	protected float vecteurX, vecteurY;
	
	/**
	 * constructeur de forme vierge
	 */
	public Forme(){
		this.x=0;
		this.y=0;
		this.largeur=0;
		this.hauteur=0;
		this.vecteurX=0;
		this.vecteurY=0;
	}
	
	/**
	 * constructeur de forme definie
	 * 
	 * @param x entier, position horizontale de la forme 
	 * @param y entier, position verticale de la forme
	 * @param l entier, largeur de la forme
	 * @param h entier, hauteur de la forme
	 * @param vecX flotant, le vecteur de deplacement horizontal
	 * @param vecY flotant, le vecteur de deplacement horizontal
	 */
	public Forme(int x, int y, int l, int h, int vecX, int vecY){
		this.x=x;
		this.y=y;
		this.largeur=l;
		this.hauteur=h;
		this.vecteurX=vecX;
		this.vecteurY=vecY;
	}
	/**
	 * 
	 * @return un entier, la valeur de x
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * 
	 * @return un entier, la valeur de y
	 */
	public int getY(){
		return this.y;
	}
	
	/**
	 * 
	 * @return un entier, la largeur de la forme
	 */
	public int getLargeur(){
		return this.largeur;
	}
	
	/**
	 * 
	 * @return un entier, la hauteur de la forme
	 */
	public int getHauteur(){
		return this.hauteur;
	}
	
	/**
	 * 
	 * @return un flotant, le vecteur de deplacement horizontal
	 */
	public float getVecteurX(){
		return this.vecteurX;
	}
	/**
	 * 
	 * @return un flotant, le vecteur de deplacement vertical
	 */
	public float getVecteurY(){
		return this.vecteurY;
	}
	
	/**
	 * fonction qui met a jour la position de la forme selon les vecteurs de deplacement horizontal et vertical
	 */
	public void updatePosition(){
		this.x=Math.round(this.x+ this.vecteurX);
		this.y=Math.round(this.y+ this.vecteurY);
	}
	
	/**
	 * fonction qui se lance au moment du contacte d'une forme avec la raquette, a surcharger 
	 * @param r la Raquette avec laquel il y a eu collision 
	 */
	public void actionCollisionRaquette(Raquette r){
		
	}
	
	/**
	 * fonction qui verifie les collisions de la forme avec la raquette r
	 * elle apelle la fonction d'action en cas de collision pour lancer l'effet
	 * @param r Raquette avec laquel on verifie les collisions 
	 */
	public void collisionRaquette(Raquette r){
		if(this.vecteurY>0 && 
		(this.x+this.largeur >= r.x && this.x <= r.x+r.largeur) && (this.y+this.hauteur >= r.y && this.y<= r.y+r.hauteur/2)){
			this.actionCollisionRaquette(r);
		}
	}
	
	
}

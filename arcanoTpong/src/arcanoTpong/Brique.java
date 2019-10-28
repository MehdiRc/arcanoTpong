package arcanoTpong;

public class Brique extends Forme {
	private int valeur;
	private int resistance;
	
	/**
	 * constructeur de Brique definie
	 * 
	 * @param valeurn entier, le nombre de point que raporte la brique  
	 * @param resistance entier, le nombre de coups que peut encaisser une brique avans d etre detruite ( 0 = emplacement vide)
	 * @param x entier, position horizontale de la Brique
	 * @param y entier, position verticale de la Brique
	 * @param largeur entier, largeur de la Brique
	 * @param hauteur entier, hauteur de la Brique
	 */
	public Brique(int valeur, int resistance, int x, int y, int largeur, int hauteur){
		
		super();
		
		this.valeur=valeur;
		this.resistance=resistance;
		
		this.x=x;
		this.y=y;
		
		this.largeur=largeur;
		this.hauteur=hauteur;
	}
	
	/**
	 * Constructeur Aleatoire de brique ( utilise dans le mode "arcade")
	 * 
	 * @param x entier, position horizontale de la Brique
	 * @param y entier, position verticale de la Brique
	 * @param largeur entier, largeur de la Brique
	 * @param hauteur entier, hauteur de la Brique
	 */
	public Brique(int x, int y, int largeur, int hauteur){
		
		super();
		int rand; 
		rand = 1 + (int)(Math.random() * 10);
		this.valeur=rand;
		
		
		rand = 0 + (int)(Math.random() * 6);
		this.resistance=rand;
		
		this.x=x;
		this.y=y;
		
		this.largeur=largeur;
		this.hauteur=hauteur;
	}
	
	
	/**
	 * fonction qui met la resistance de la brique a zero ( donc qui enleve la brique du jeu) et qui renvoie sa valeur
	 * @return entier, la valeur de la brique (pour l ajouter au score)
	 */
	public int kill(){
		this.resistance=0;
		return this.valeur;
	}
	
	/**
	 * fonction qui decremente la resistance de la brique et qui renvoi la resistance restante 
	 * @return entier, la resistance restante de la brique apres le "coup"
	 */
	public int gothit(){
		this.resistance--;
		return this.resistance;
	}
	
	/**
	 * 
	 * @return entier, la resistance de la brique
	 */
	public int getResistanece(){
		return this.resistance;
	}
	
	/**
	 * 
	 * @return entier, la valeur de la brique
	 */ 
	public int getValeur(){
		return this.valeur;
	}

}

package arcanoTpong;

public class Raquette extends Forme {
	
	private int largeurMin;
	private int largeurMax;
	
	private int score;
	private int joueur;
	
	/**
	 * cree une raquette qui represente un joueur
	 * @param x entier, position horizontale de la raquette 
	 * @param y entier, position verticale de la raquette
	 * @param joueur entier, numero du joueur rattache a la raquette
	 * @param difficulte entier, difficulte du niveau en cours
	 */
	public Raquette(int x,int y,int joueur, int difficulte){
		super();
		
		this.joueur = joueur; 
		this.score=0;
		
		// la taille min et max de la raquette changent en fonction de la difficulte
		switch(difficulte) {
			case 0:
				this.largeurMax=200;
				this.largeurMin=120;
				break;
			case 1:
				this.largeurMax=150;
				this.largeurMin=80;
				break;
			case 2:
				this.largeurMax=120;
				this.largeurMin=60;
				break;
			default:
				this.largeurMax=120;
				this.largeurMin=60;
				break;
		}
		
		this.largeur=largeurMin;
		this.hauteur=20;
		
		
		
		this.x=(int)(x-this.largeur/2);
		this.y=(int)(y-this.hauteur-100);
	}
	
	/**
	 * 
	 * @return entier, le joueur proprietaire de la raquette
	 */
	public int getJoueur(){
		return this.joueur;
	}
	/**
	 * 
	 * @return rentier, le score de la raquette
	 */
	public int getScore(){
		return this.score;
	}
	
	/**
	 * fonction qui ajoute l'entier "addition" au score de la raquette 
	 * @param addition entier, points a ajouter au score
	 */
	public void addScore(int addition){
		this.score += addition;
	}
	
	/**
	 * Fonction de deplacement de la raquette, POUR LES DEPLACEMENTS CLAVIER //non implemente dans la version finale//
	 */
	public void updateRaquettePosition(){
		this.x= (int) (this.x+ this.vecteurX);
		this.y= (int) (this.y+ this.vecteurY);
		this.vecteurX=0;
		this.vecteurY=0;
		}
	
	/**
	 * fonction d'agrandissement de la raquette 
	 */
	public void sizeUp(){
		if(this.largeur<this.largeurMax){
			this.largeur+=20;
		}
	}
	
	/**
	 * fonction de retrecissement de la raquette 
	 */
	public void sizeDown(){
		if(this.largeur>this.largeurMin){
			this.largeur-=20;
		}
	}
	
	

}

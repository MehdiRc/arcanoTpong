package arcanoTpong;

public class Raquette extends Forme {
	
	private int score;
	private int vie;
	
	public Raquette(int x,int y){
		super();
		this.score=0;
		this.vie=1;
		
		this.largeur=80;
		this.hauteur=20;
		
		this.x=(int)(x-this.largeur/2);
		this.y=(int)(y-this.hauteur-100);
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void addScore(int addition){
		this.score += addition;
	}
	
	public int getVie(){
		return this.vie;
	}
	
	public void updateRaquettePosition(){
		this.x= this.x+ this.vecteurX;
		this.y= this.y+ this.vecteurY;
		this.vecteurX=0;
		this.vecteurY=0;
		}
	
	
	
	

}

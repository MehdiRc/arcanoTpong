package arcanoTpong;

public class Raquette extends Forme {
	
	private int score;
	private int vie;
	
	public Raquette(){
		super();
		this.score=0;
		this.vie=1;
		
		this.largeur=50;
		this.hauteur=20;
		
		this.x=100;
		this.y=100;
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
	
	

}

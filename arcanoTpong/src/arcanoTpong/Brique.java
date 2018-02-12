package arcanoTpong;

public class Brique extends Forme {
	private int valeur;
	private int resistance;
	
	public Brique(int valeur, int resistance, int x, int y, int largeur, int hauteur){
		
		super();
		
		this.valeur=valeur;
		this.resistance=resistance;
		
		this.x=x;
		this.y=y;
		
		this.largeur=largeur;
		this.hauteur=hauteur;
	}
	
	public int kill(){
		this.resistance=0;
		return valeur;
	}
	
	public int getResistanece(){
		return this.resistance;
	}

}

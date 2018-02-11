package arcanoTpong;

public class Brique extends Forme {
	private int valeur;
	private int resistance;
	
	public Brique(int valeur, int resistance, int x, int y){
		this.valeur=valeur;
		this.resistance=resistance;
		this.x=x;
		this.y=y;
	}
	
	public int kill(){
		this.resistance=0;
		return valeur;
	}

}

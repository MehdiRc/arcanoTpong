package arcanoTpong;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*; 


public class Controleur {
	static int lZoneJeu=80*10; 
	static int hZoneJeu=30*20;
	
	static Vue view = new Vue(lZoneJeu,hZoneJeu);
	static Moteur model = new Moteur(1,lZoneJeu,hZoneJeu);
	
	 public static void main(String args[]){
		 
		    for(int i= 0; i<model.getInGRaquettes().length;i++){
		    	view.afficheScore(model.getInGRaquettes()[i].getScore());
		    }
		    
	    	view.setListeBriques(model.getInGBriquesClean());
		    view.setListeBalles(model.getInGBalles());
		    view.setListeRaquettes(model.getInGRaquettesArrayListe());		    
		    
		    
		    view.addKeyListener(new KeyAdapter(){
		        @Override
		        public void keyPressed(KeyEvent e){
		          if (e.getKeyCode()==KeyEvent.VK_RIGHT)
		            model.movementRaquetteClavierX(model.getInGRaquettes()[0], 2);
		          else if (e.getKeyCode()==KeyEvent.VK_LEFT)
		        	model.movementRaquetteClavierX(model.getInGRaquettes()[0], 1);
		        }
		      });
		    
		    view.getPanel().addMouseMotionListener(new MouseMotionAdapter(){
		        @Override
		        public void mouseMoved(MouseEvent e){
		          model.teleportRaquetteX(model.getInGRaquettes()[0], (int) (e.getX()/view.getScaleX()));
		        }
		      });
		    
		    
		    
		    // a remplacer par un thread
		    while(true){
		    	//model.teleportRaquetteX(model.getInGRaquettes()[0],view.getMouseX());
		    
		    	/* 
		    Brique deadB = model.killBrique(2,1,model.getInGRaquettes()[0]);
		    view.enleveBrique(deadB);
		    */
		    model.getInGRaquettes()[0].updateRaquettePosition();
		    view.afficheScore(model.getInGRaquettes()[0].getScore());
		    
		    view.repaint();
		    }
		    
		    	


	 }
}

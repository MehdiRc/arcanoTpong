package arcanoTpong;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.io.*; 


public class Controleur {
	static int lZoneJeu=80*10; 
	static int hZoneJeu=30*20;
	
	static Vue view = new Vue(lZoneJeu,hZoneJeu);
	static Moteur model = new Moteur(1,lZoneJeu,hZoneJeu);
	
	
	static Timer repaint = new Timer();
	
	 public static void main(String args[]){
		 
		 repaint.schedule(new TimerTask(){
				@Override
				public void run(){
					view.repaint();
				}
			},0, 20);
		 
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
		          
		          view.repaint();
		          
		        }
		      });
		    
		    view.getPanel().addMouseMotionListener(new MouseMotionAdapter(){
		       
		    	@Override
		        public void mouseMoved(MouseEvent e){
		          model.teleportRaquetteX(model.getInGRaquettes()[0], (int) (e.getX()/view.getScaleX()));
		          
				    	model.getInGRaquettes()[0].updateRaquettePosition();
					/*
				    	for(Balle b:model.getInGBalles()){
					    	model.movementBalle(b);
					    }
					    
				   */
		          view.repaint();
		        }
		      });
		    
		    
		    
		    // a remplacer par un thread
		    //while(true){
		    	//model.teleportRaquetteX(model.getInGRaquettes()[0],view.getMouseX());
		    
		    	/* 
		    Brique deadB = model.killBrique(2,1,model.getInGRaquettes()[0]);
		    view.enleveBrique(deadB);
		    */
		
		    
		  //  view.afficheScore(model.getInGRaquettes()[0].getScore());
		    
		    
		    //}
		    
		    	


	 }
}

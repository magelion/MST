package main;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
/**
 * 
 * @author Patia 2018-2019(projet MST)
 *
 */
public class DifferentialDrive {

    /**
     * Delta of angle.
     */
    private static final float DELTA = -30/360;
    
    /**
     * Dimension de la table en cm.
     */
    private static final int LARG=50;
    private static final int LONG=60;
	
    //private VisionSensor vision;//pour ne pas rentrer dans les murs/robots
    private MovePilot pilot;
    

    public DifferentialDrive(Port left_port, Port right_port){
    	Wheel wheelleft = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(left_port),
				Config.LEFT_WHEEL_DIAMETER).offset(-1*Config.DISTANCE_TO_CENTER);
		Wheel wheelright = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(right_port),
				Config.RIGHT_WHEEL_DIAMETER).offset(Config.DISTANCE_TO_CENTER);
		Chassis chassis = new WheeledChassis(new Wheel[]{wheelleft, wheelright},
				WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		//pilot.setLinearAcceleration(Config.LINEAR_ACCELERATION);
		pilot.setLinearSpeed(Config.LINEAR_SPEED);
		pilot.setAngularSpeed(Config.ANGULAR_SPEED);
		//pilot.setAngularAcceleration(Config.ANGULAR_ACCELERATION);  
		//vision= new VisionSensor();
    }  
    
    /**
     * Avance par pas de 5cm jusqu'à rencontrer un obstacle ou un palet
     */
    public void GoUntilTouch(TouchSensor touchsensor, boolean grab){
    	Pliers pliers = new Pliers(20);
    	
    	pilot.setLinearAcceleration(10.0f);
    	pilot.forward();
    	
    	while(!touchsensor.isPressed()) {
    	}
    		System.out.println("TOUCH DETECTED");
   			pilot.stop();
    		if(grab) {
    			if(!pliers.isClosed()) {
    				pliers.close();
    			}
    		}    	
    }
    
    /**
     * Le robot suit la ligne de couleur au dessus de laquelle il est placée lors de l'appel à cette fonction
     * si le robot detecte du gris lors de l'appel il ne se déplacera pas (pas de ligne à suivre)
     * si le robot perd la ligne à suivre il tentera de la retrouver en effectuant des petits mouvements
     * (jusqu'à trois recherches anant abandon et arret de la fonction)
     */
    public void followLine(Couleur c, TouchSensor tSensor){
    	if(!c.isGrey()){
    		int compteur=3;
    		float[] couleurLigne=c.colorSample();
    		while(!tSensor.isPressed() && compteur!=0){
    	 		if(c.isGrey()){
    	 			//Pour réduire la vitesse lors de la recherche de ligne
    	 			Config.ANGULAR_SPEED = 20.0f;
	    			recoverLine(c,couleurLigne);
	    			compteur--;
	    			System.out.println("Couleur grise");
	    			//Delay.msDelay(10);
	    			
	    			//Possibilité de modifier ANGULAR_SPEED à la place
	    			Config.ANGULAR_SPEED = 230.0f;
	    		}
    	 		Delay.msDelay(10);
    			while(c.isThisColor(couleurLigne)){
	    			pilot.travel(10);	
	    			System.out.println("sur ligne");
    			}
    	 		System.out.println("perdu ligne");
    		}
    		if(tSensor.isPressed()) {
    			System.out.println("PALET DÉTECTÉ");
    		}
    	}
    	else {
    		System.out.println("j'ai commencer sur sur gris");
    	}
    }
    /**
     * Le robot essaie de retrouver la ligne de couleur à suivre si il la trouve il se place 
     * dessus,
     * pas de retours pour définir réussite ou échec
     */
    private void recoverLine(Couleur c,float[] couleur){
    	boolean inversionSens = false;
    	for(int i = 1; !c.isThisColor(couleur) && i!=6;i++){
    		if (inversionSens) {
    			pilot.rotate(-5*i);
    		}
    		else {
    			pilot.rotate(5*i);
    		}
    		inversionSens = !inversionSens;
    	}
    }
    
    
    /**
     * Précond: robot orienté parallèlement à la table, le noeud visé est atténiable
     * Se déplace (sans détour) jusqu'au noeud devant lui 
     * Postcond: robot orienté parallèlement à la table, cible atteinte
     */
    public void GoNoeudHaut(){
    	pilot.travel(LONG);
    }
    
    /**
     * Précond: robot orienté parallèlement à la table, le noeud visé est atténiable
     * Se déplace (sans détour) jusqu'au noeud derrière lui (sans effectuer de rotations)
     * Postcond: robot orienté parallèlement à la table, cible atteinte
     */
    public void GoNoeudBas(){
    	pilot.travel(-LONG);
    }
    
    /**
     * Précond: robot orienté parallèlement à la table, le noeud visé est atténiable
     * Se déplace (sans détour) jusqu'au noeud à sa gauche 
     * Postcond: robot orienté parallèlement à la table, cible atteinte
     */
    public void GoNoeudGauche(){
    	turnLeft(90);
    	pilot.travel(LARG);
    	turnRight(90);
    }
    
    /**
     * Précond: robot orienté parallèlement à la table, le noeud visé est atténiable
     * Se déplace (sans détour) jusqu'au noeud à sa droite
     * Postcond: robot orienté parallèlement à la table, cible atteinte
     */
    public void GoNoeudDroite(){
    	turnRight(90);
    	pilot.travel(LARG);
    	turnLeft(90);
    }
    
    /**
     * Précond: robot orienté parallèlement à la table, le noeud visé est atténiable 
     * et il n'y a pas d'obstacle sur le noeud haut
     * Se déplace comme si il suivait les lignes jusqu'au noeud en haut à gauche
     * Postcond: robot orienté parallèlement à la table, cible atteinte
     */
    public void GoNoeudHautGauche(){
    	GoNoeudHaut();
    	GoNoeudGauche();
    }
    
    /**
     * Précond: robot orienté parallèlement à la table, le noeud visé est atténiable 
     * et il n'y a pas d'obstacle sur le noeud haut
     * Se déplace comme si il suivait les lignes jusqu'au noeud en haut à droite
     * Postcond: robot orienté parallèlement à la table, cible atteinte
     */
    public void GoNoeudHautDroite(){
    	GoNoeudHaut();
    	GoNoeudDroite();
    }
    
    /**
     * Précond: robot orienté parallèlement à la table, le noeud visé est atténiable 
     * et il n'y a pas d'obstacle sur le noeud bas
     * Se déplace comme si il suivait les lignes jusqu'au noeud en bas à droite
     * Postcond: robot orienté parallèlement à la table, cible atteinte
     */
    public void GoNoeudBasDroite(){
    	GoNoeudBas();
    	GoNoeudDroite();
    }
    
    /**
     * Précond: robot orienté parallèlement à la table, le noeud visé est atténiable 
     * et il n'y a pas d'obstacle sur le noeud bas
     * Se déplace comme si il suivait les lignes jusqu'au noeud en bas à droite
     * Postcond: robot orienté parallèlement à la table, cible atteinte
     */
    public void GoNoeudBasGauche(){
    	GoNoeudBas();
    	GoNoeudGauche();
    }
    
    
    /**
     * Turn right of an angle given.
     * @param angle : float angle
     */
    void turnRight(final int angle) {
        float ang;
        if (angle >= 0) {
            ang = angle + DELTA*angle;
        } else {
            ang = angle - DELTA*angle;
        }
        pilot.rotate(Math.round(ang));
    }

    /**
     * Turn left of an angle given.
     * @param angle : float angle
     */
    void turnLeft(final int angle) {
        turnRight(-angle);
    }
    
    MovePilot getpilot(){
    	return pilot;
    }

    
}


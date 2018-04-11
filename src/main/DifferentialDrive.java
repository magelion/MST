package main;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class DifferentialDrive {

    /**
     * Delta of angle.
     */
    private static final float DELTA = -10/360;
    
    /**
     * Dimension table en cm.
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
    public void GoUntilTouch(TouchSensor touchsensor){	
    	while(!touchsensor.isPressed()){
    		pilot.travel(5);
    	}
    }
    
    /**
     * Le robot suit la ligne de couleur au dessus de laquelle il est placée lors de l'appel à cette fonction
     * si le robot detecte du gris lors de l'appel il ne se déplacera pas (pas de ligne à suivre)
     * si le robot perd la ligne à suivre il tentera de la retrouver en effectuant des petits mouvements
     * (jusqu'à trois recherches anant abandon etarret de la fonction)
     */
    public void followLine(Couleur c){
    	if(!c.isGrey()){
    		int compteur=3;
    		float[] couleurLigne=c.colorSample();
    		//TouchSensor tSensor = new TouchSensor();
    		while(/*!tSensor.isPressed()*/compteur!=0){
    	 		if(c.isGrey()){
	    			recoverLine(c,couleurLigne);
	    			compteur--;
	    			System.out.println("Couleur grise");
	    			Delay.msDelay(1000);
	    		}
    	 		System.out.println("trouvé ligne");
    	 		Delay.msDelay(1000);
    			while(c.isThisColor(couleurLigne)){
	    			pilot.travel(5);	
	    			System.out.println("sur ligne");
	    	 		Delay.msDelay(1000);
    			}
    	 		System.out.println("perdu ligne");
    	 		Delay.msDelay(1000);
    		}
    	}
    	else {
    		System.out.println("j'ai commencer sur sur gris");
    	}
    }
    /**
     * Le robot essaie de retrouver la ligne de couleur à suivre si il la trouve il se place 
     * dessus, sinon il reprend son orientation initiale
     * pas de retours pour définir réussite ou échec
     */
    private void recoverLine(Couleur c,float[] couleur){
    	boolean inversionSens = false;
    	for(int i = 1; !c.isThisColor(couleur) && i!=6;i++){
    		if (inversionSens) {
    			//turnRight(5*i);
    			pilot.rotate(-5*i);
    		}
    		else {
    			//turnLeft(5*i);
    			pilot.rotate(5*i);
    		}
    		inversionSens = !inversionSens;
    	}
    	
    	
    	
    	
    	
    	
    	/*turnLeft(50);
    	int i=0;
    	while(!c.isThisColor(couleur) && i!=100){
    		turnRight(5);
    		i=i+5;
    	}
    	//VERIFIER TURN LEFT OU RIGHT
    	if(i==100){
    		turnLeft(50);
    	}*/
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


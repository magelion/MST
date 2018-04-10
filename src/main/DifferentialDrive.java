package main;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

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
		//pilot.addMoveListener(this);
		pilot.setLinearAcceleration(Config.LINEAR_ACCELERATION);
		pilot.setLinearSpeed(Config.LINEAR_SPEED);
		pilot.setAngularSpeed(Config.ANGULAR_SPEED);
		pilot.setAngularAcceleration(Config.ANGULAR_ACCELERATION);  
		//vision= new VisionSensor();
    }  
    
    /**
     * Avance jusqu'à rencontrer un obstacle ou un palet
     * Renvoie vrai si les pinces detectent un palet, faux si l'arret est dû à un obstacle
     */
    /*public boolean GoUntilTouch(TouchSensor touchsensor){
    	if(vision.getRaw()[0]>=20){
    		while(!touchsensor.isPressed()&&vision.getRaw()[0]>=20){
    			pilot.travel(20);
    		}
    	}else{
    		System.out.println("obstacle detecté!");
    		Delay.msDelay(1000);
    	}
    	return touchsensor.isPressed();
    }*/
    
    /**
     * Avance jusqu'à rencontrer un obstacle ou un palet
     */
    public void GoUntilTouch(TouchSensor touchsensor){	
    	while(!touchsensor.isPressed()){
    		pilot.travel(20);
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


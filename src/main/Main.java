package main;

import java.io.IOException;

import lejos.hardware.Button;
import lejos.robotics.Color;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.utility.Delay;


public class Main {

	public static void main(String args[]) {
		//testCouleur(0);	
		//testOdometrie();
		//testDeplacementNaif();
		TouchSensor tSensor = new TouchSensor();
		//testFollowLine(tSensor);

		//testTouchSensorThenGrab(tSensor);
		testBringBackBounty(tSensor);
	}
	
	public static void testFollowLine(TouchSensor tSensor){
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT); 
		Couleur c=new Couleur(0);
		System.out.println("Place me in a line and press enter to test");
		Button.ENTER.waitForPressAndRelease();
		d.followLine(c, tSensor);
	}
	
	public static void testTouchSensor(TouchSensor tSensor){
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT);
		System.out.println("Place me in a line and press enter to test");
		Button.ENTER.waitForPressAndRelease();
		d.GoUntilTouch(tSensor, false);
	}
	
	public static void testTouchSensorThenGrab(TouchSensor tSensor){
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT);
		System.out.println("Place me in a line and press enter to test");
		Button.ENTER.waitForPressAndRelease();
		d.GoUntilTouch(tSensor, true);
	}
	
	public static void testBringBackBounty(TouchSensor tSensor) {
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT);
		System.out.println("Place me in a line and press enter to test");
		Button.ENTER.waitForPressAndRelease();
    	Pliers pliers = new Pliers(20);
    	
    	d.getpilot().setLinearAcceleration(10.0f);
    	Config.ANGULAR_SPEED=2.0f;
    	Config.ANGULAR_ACCELERATION=10.0f;
    	d.getpilot().forward();
    	
    	while(!tSensor.isPressed()) {
    	}
    		System.out.println("TOUCH DETECTED");
   			d.getpilot().stop();
    		if(!pliers.isClosed()) {
    				pliers.close();
    		}
		d.turnLeft(90);
		d.turnLeft(90);
		d.getpilot().travel(50);
		if (pliers.isClosed()) {
			pliers.open();
		}
    }
	
	public static void testBringBackBountyColor(TouchSensor tSensor) {
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT);
		Couleur c=new Couleur(0);
		System.out.println("Place me in a line and press enter to test");
		Button.ENTER.waitForPressAndRelease();
    	Pliers pliers = new Pliers(20);
    	Color cCurrent =  c.getCurrentColor();
    	
    	d.getpilot().setLinearAcceleration(10.0f);
    	d.getpilot().forward();
    	
    	while(!tSensor.isPressed()) {
    	}
    		System.out.println("TOUCH DETECTED");
   			d.getpilot().stop();
    		if(!pliers.isClosed()) {
    				pliers.close();
    		}
		d.getpilot().rotateLeft();
		while(c.getCurrentColor()!=cCurrent)
		{}
		d.getpilot().travel(40);
		if (pliers.isClosed()) {
			pliers.open();
		}
    }
	
	
	/**
	*Vérification du bon calibrage des couleurs
	*@param: paramètre du constructeur de la classe couleur
	*/
	public static void testCouleur(int param){
		Couleur c=new Couleur(param);
		System.out.println("Press enter to test blue...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isBlue()){
			System.out.println("Bleu");
		}
		Delay.msDelay(1000);
		
		System.out.println("Press enter to test black...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isBlack()){
			System.out.println("Noir");
		}
		Delay.msDelay(1000);
		
		System.out.println("Press enter to test green...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isGreen()){
			System.out.println("Vert");
		}
		Delay.msDelay(1000);
			
		System.out.println("Press enter to test red...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isRed()){
			System.out.println("Rouge");
		}
		Delay.msDelay(1000);
			
		
		System.out.println("Press enter to test white...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isWhite()){
			System.out.println("Blanc");
		}
		Delay.msDelay(1000);
			
		System.out.println("Press enter to test yellow...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isYellow()){
			System.out.println("Jaune");
		}
		Delay.msDelay(1000);
		
		System.out.println("Press enter to test grey...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isGrey()){
			System.out.println("Gris");
		}
		Delay.msDelay(1000);
	}
		
	/**
	*Le but était de vérifier les données disponibles via l'odomètrie
	*On a remarqué que l'orientation pouvait être suivie (avec des approximations)
	*Au final cette piste a été abandonnée
	*/
	public static void testOdometrie(){
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT); 
		OdometryPoseProvider o= new OdometryPoseProvider (d.getpilot());
		System.out.println("Position Init="+o.getPose().getHeading());//orientation
		Delay.msDelay(2000);
		d.turnLeft(180);
		System.out.println("Position Init="+o.getPose().getHeading());//orientation
		Delay.msDelay(2000);
		d.turnLeft(90);
		System.out.println("Position Init="+o.getPose().getHeading());//orientation
		Delay.msDelay(5000);
	}
		
	public static void testDeplacementNaif(){
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT); 
		d.GoNoeudHaut();
		d.GoNoeudBasDroite();
		d.GoUntilTouch(new TouchSensor(), false);
	}
}
	

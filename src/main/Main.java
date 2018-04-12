package main;

import lejos.hardware.Button;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.utility.Delay;


public class Main {

	public static void main(String args[]) {
		testCouleur();	
		//testOdometrie();
		//testDeplacementNaif();
		testFollowLine();
	}
	
	public static void testFollowLine(){
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT); 
		Couleur c=new Couleur(0);
		System.out.println("Place me in a line and press enter to test");
		Button.ENTER.waitForPressAndRelease();
		d.followLine(c);
	}
	
	public static void testCouleur(){
		Couleur c=new Couleur(0);
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
		d.GoUntilTouch(new TouchSensor());
	}
}
	

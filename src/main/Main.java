package main;

import lejos.hardware.Button;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.utility.Delay;


public class Main {

	public static void main(String args[]) {
		//testCouleur();	
		//testOdometrie();
		testDeplacementNaif();
	}

	public static void testCouleur(){
		Couleur c=new Couleur(1);
		System.out.println("Press enter to test blue...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isBlue()){
			System.out.println("Bleu");
		}
		Delay.msDelay(2000);
		
		System.out.println("Press enter to test black...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isBlack()){
			System.out.println("Noir");
		}
		Delay.msDelay(2000);
		
		System.out.println("Press enter to test green...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isGreen()){
			System.out.println("Vert");
		}
		Delay.msDelay(2000);
			
		System.out.println("Press enter to test red...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isRed()){
			System.out.println("Rouge");
		}
		Delay.msDelay(2000);
			
		
		System.out.println("Press enter to test white...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isWhite()){
			System.out.println("Blanc");
		}
		Delay.msDelay(2000);
			
		System.out.println("Press enter to test yellow...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isYellow()){
			System.out.println("Jaune");
		}
		Delay.msDelay(2000);
		
		System.out.println("Press enter to test grey...");
		Button.ENTER.waitForPressAndRelease();
		if(c.isGrey()){
			System.out.println("Gris");
		}
		Delay.msDelay(2000);
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
		
		//DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, Config.RIGHTWHEELPORT);
		
		/*d.pilot.forward();
		Delay.msDelay(1000);
		d.pilot.stop();*/
		//d.pilot.travel(200);
		//System.out.println("COUCOU");
		//Delay.msDelay(10000);
		//d.pilot.stop();
		//d.pilot.rotate(360);
		//d.pilot.travel(100);
		
		
		
		//DifferentialDrive d=new DifferentialDrive(Config.LEFTWHEELPORT,Config.RIGHTWHEELPORT);
		//Pliers p = new Pliers(20);
		//TouchSensor t= new TouchSensor();
		
		
		/*for(int i=0;i<150000;i++){//ATTENTION PROBLEMES ralentissement?
			d.forward();
		}
		//System.out.println("Sortie for");
		d.stop();//pas nécessaire
		//Delay.msDelay(10000);*/
		/*for(int t=0;t<6;t++){
			for(int i=0;i<1500;i++){
				d.rotateClockwise();
			}
			Delay.msDelay(1000);
		}*/
		
		
		
		
		
        /*p.init();//pinces ouvertes par défaut
        p.setClosed(true);
        if(p.isClosed()){
        	System.out.println("je vais ouvrir");
        p.open();
        }else{
        	System.out.println("je ne veux pas ouvrir");
        }
		
		while(!t.isPressed()){}
		if(!p.isClosed()){
			p.close();
		}
		p.init();//pinces ouvertes par défaut
        p.setClosed(true);*/
        
        
		
        /*Pliers p = new Pliers(20);
        p.init();
        p.open();
        if(p.isClosed()){
        	p.open();
        }
        if(!p.isClosed()){
        	p.close();
        }
		//p.close();*/
}
	

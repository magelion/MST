package main;
/*Notes
 * Rotate:
 * demi-tour= i=1510;tour SPEEDROTATE = 300.0;
 * ATTENTION LATENCE!*/
import lejos.utility.Delay;


public class Main {

	public static void main(String args[]) {
		
		DifferentialDrive d = new DifferentialDrive(Config.LEFTWHEELPORT, 
												Config.RIGHTWHEELPORT);
		
		/*d.pilot.forward();
		Delay.msDelay(1000);
		d.pilot.stop();*/
		d.pilot.travel(100);
		System.out.println("COUCOU");
		Delay.msDelay(10000);
		d.pilot.stop();
		
		
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
	
}

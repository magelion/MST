package main;
import lejos.hardware.Button;
import lejos.utility.Delay;

public class HelloWorld {

	public static void main(String[] args) {
		try {
			
		System.out.println("Hello I am\nMindStormTrooper\nbut you can call me\n    MST! ");
		Button.ENTER.waitForPress();
		
		} catch (Throwable t) {
			t.printStackTrace();
			Delay.msDelay(10000);
			System.exit(0);
		}
	}

}

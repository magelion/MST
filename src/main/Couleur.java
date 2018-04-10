package main;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class Couleur {

	private EV3ColorSensor colorSensor;
	private SampleProvider average;
	private static float[] path_white;
	private static float[] path_blue;
	private static float[] path_red;
	private static float[] path_green;
	private static float[] path_black;
	private static float[] path_yellow;
    private final static double ERROR = 0.01;
	
    /**
     *@param option: si ==0, calibrage avec une seule mesure
     * 				sinon calibrage avec trois mesures
     */
	public Couleur(int option) {
		colorSensor = new EV3ColorSensor(Config.COLORPORT);
		average = new MeanFilter(colorSensor.getRGBMode(), 1);
		
		try {
			
			lightOn();
			
			if(option==0){
			//param = String de la couleur
				path_green = calibrate("Green");
				path_black = calibrate("Black");
				path_blue = calibrate("Blue");
				path_white = calibrate("White");
				path_yellow = calibrate("Yellow");
				path_red = calibrate("Red");
			}else{
				path_green = calibrateAVG("Green");
				path_black = calibrateAVG("Black");
				path_blue = calibrateAVG("Blue");
				path_white = calibrateAVG("Write");
				path_yellow = calibrateAVG("Yellow");
				path_red = calibrateAVG("Red");
			}

		} catch (Throwable t) {
			t.printStackTrace();
			Delay.msDelay(10000);
			System.exit(0);
		}
	}
	
	
	/**
	 * Allume la led de capture de couleur d'une couleur blanche
	 */
	public void lightOn(){
		colorSensor.setFloodlight(Color.WHITE);
	}
	
	/**
	 * Eteins la led de capture de couleur
	 */
	public void lightOff(){
		colorSensor.setFloodlight(false);
	}
	
	float[] calibrate (String color){
		System.out.println("Press enter to calibrate " + color + "...");
		Button.ENTER.waitForPressAndRelease();
		float[] path = new float[average.sampleSize()];
		average.fetchSample(path, 0);
		return path;
	}
	
	float[] calibrateAVG (String color){
		float[] t1 = calibrate(color);
		float[] t2 = calibrate(color);
		float[] t3 = calibrate(color);				
		return moyenneTableau(t1,t2,t3);		
	}
	
	private float[] moyenneTableau(float[] t1 , float[] t2 , float[] t3){
		float [] res=new float[t1.length];
		for(int i=0; i<t1.length;i++){
			res[i]=(t1[i]+t2[i]+t3[i])/3;
		}
		return res;
	}
	
	boolean isThisColor(float[] path){
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		return scalaire(sample, path) < ERROR;
	}

	
	boolean isBlue(){
		return isThisColor(path_blue);
	}
		
	boolean isRed(){
		return isThisColor(path_red);
	}
		
	boolean isGreen(){
		return isThisColor(path_green);
	}
		
	boolean isBlack(){
		return isThisColor(path_black);
	}
		
	boolean isWhite(){
		return isThisColor(path_white);
	}
		
	boolean isYellow(){
		return isThisColor(path_yellow);
	}
	
	private static double scalaire(float[] v1, float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}
}

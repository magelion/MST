package main;

import java.io.File;
import java.io.ObjectInputStream;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class Couleur {

	private EV3ColorSensor colorSensor;
	private SampleProvider average;
	private static float[] line_white;
	private static float[] line_blue;
	private static float[] line_red;
	private static float[] line_green;
	private static float[] line_black;
	private static float[] line_yellow;
	private static float[] grey_zone;
    private final static double ERROR = 0.02;
	
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
				line_green = calibrate("Green");
				line_black = calibrate("Black");
				line_blue = calibrate("Blue");
				line_white = calibrate("White");
				line_yellow = calibrate("Yellow");
				line_red = calibrate("Red");
				grey_zone = calibrate("Grey");
			}else{
				line_green = calibrateAVG("Green");
				line_black = calibrateAVG("Black");
				line_blue = calibrateAVG("Blue");
				line_white = calibrateAVG("White");
				line_yellow = calibrateAVG("Yellow");
				line_red = calibrateAVG("Red");
				grey_zone = calibrateAVG("Grey");
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
	
	float[] colorSample(){
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		return sample;
	}
	
	
	boolean isThisColor(float[] path){
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		return scalaire(sample, path) < ERROR;
	}

	
	boolean isBlue(){
		return isThisColor(line_blue);
	}
		
	boolean isRed(){
		return isThisColor(line_red);
	}
		
	boolean isGreen(){
		return isThisColor(line_green);
	}
		
	boolean isBlack(){
		return isThisColor(line_black);
	}
		
	boolean isWhite(){
		return isThisColor(line_white);
	}
		
	boolean isYellow(){
		return isThisColor(line_yellow);
	}
	
	boolean isGrey(){
		return isThisColor(grey_zone);
	}
	
	private static double scalaire(float[] v1, float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}
	
	/**
	 * Tente de lire et charger le fichier de calibration.
	 */
	/*private void setCalibration(){
		try{
			File fichierRead =  new File("conf.txt") ;
			ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichierRead)) ;
			this.colors = (float[][])ois.readObject() ;
			ois.close();
		}
		catch (Exception e) {
			Main.printf("[COLOR SENSOR]          : Impossible de charger le fichier de calibration");
		}
	}*/
}

package aiPlanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import area.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

/**
 * Classe utilitaire permettant de générer un fichier de calibration des couleurs.
 * @author paul.carretero, florent.chastagner
 */
public class ColorCalibrator {
	
	/**
	 * Lance la calibration des couleurs<br/>
	 * Devrait etre lance apres chaque changement de luminosite
	 */
	public static void Calibrate() {
		Port port = LocalEV3.get().getPort(Main.COLOR_SENSOR);
		EV3ColorSensor colorSensor = new EV3ColorSensor(port);
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
		colorSensor.setFloodlight(Color.WHITE);
		
		float[][] colors = new float[7][average.sampleSize()];
		
		System.out.println("Press a button to calibrate blue...");
		Button.waitForAnyPress();
		float[] blue = new float[average.sampleSize()];
		average.fetchSample(blue, 0);
		colors[ColorSensor.COLOR_BLUE] = blue;
		
		Delay.msDelay(300);
		
		System.out.println("Press a button to calibrate red...");
		Button.waitForAnyPress();
		float[] red = new float[average.sampleSize()];
		average.fetchSample(red, 0);
		colors[ColorSensor.COLOR_RED] = red;
		
		Delay.msDelay(300);
		
		System.out.println("Press a button to calibrate green...");
		Button.waitForAnyPress();
		float[] green = new float[average.sampleSize()];
		average.fetchSample(green, 0);
		colors[ColorSensor.COLOR_GREEN] = green;
		
		Delay.msDelay(300);

		System.out.println("Press a button to calibrate black...");
		Button.waitForAnyPress();
		float[] black = new float[average.sampleSize()];
		average.fetchSample(black, 0);
		colors[ColorSensor.COLOR_BLACK] = black;
		
		Delay.msDelay(300);
		
		System.out.println("Press a button to calibrate grey...");
		Button.waitForAnyPress();
		float[] grey = new float[average.sampleSize()];
		average.fetchSample(grey, 0);
		colors[ColorSensor.COLOR_GREY] = grey;
		
		Delay.msDelay(300);
		
		System.out.println("Press a button to calibrate white...");
		Button.waitForAnyPress();
		float[] white = new float[average.sampleSize()];
		average.fetchSample(white, 0);
		colors[ColorSensor.COLOR_WHITE] = white;
		
		Delay.msDelay(300);
		
		System.out.println("Press a button to calibrate yellow...");
		Button.waitForAnyPress();
		float[] yellow = new float[average.sampleSize()];
		average.fetchSample(yellow, 0);
		colors[ColorSensor.COLOR_YELLOW] = yellow;
		
		colorSensor.setFloodlight(false);
		float[][] readColors = new float[][]{};
		
		try {
			
			File fichier =  new File("conf.txt") ;
			
			PrintWriter writer = new PrintWriter(fichier);
			writer.print("");
			writer.close();
			
			ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
			oos.writeObject(colors) ;
			oos.close();
			
			ObjectInputStream ois;
			
			ois = new ObjectInputStream(new FileInputStream(fichier));
			
			readColors = (float[][])ois.readObject() ;
			ois.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(3);
		}
		
		boolean again = true;
		
		while (again) {
			float[] sample = new float[average.sampleSize()];
			System.out.println("\nPress UP to detect a color...");
			Button.UP.waitForPressAndRelease();
			average.fetchSample(sample, 0);
			double minscal = Double.MAX_VALUE;
			String color = "";
			
			double scalaire = ColorSensor.scalaire(sample, readColors[ColorSensor.COLOR_BLUE]);	
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "blue";
			}
			
			scalaire = ColorSensor.scalaire(sample, readColors[ColorSensor.COLOR_RED]);
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "red";
			}
			
			scalaire = ColorSensor.scalaire(sample, readColors[ColorSensor.COLOR_GREEN]);
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "green";
			}
			
			scalaire = ColorSensor.scalaire(sample, readColors[ColorSensor.COLOR_BLACK]);
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "black";
			}
			
			scalaire = ColorSensor.scalaire(sample, readColors[ColorSensor.COLOR_GREY]);
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "grey";
			}
			
			scalaire = ColorSensor.scalaire(sample, readColors[ColorSensor.COLOR_YELLOW]);
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "yellow";
			}
			
			scalaire = ColorSensor.scalaire(sample, readColors[ColorSensor.COLOR_WHITE]);
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "white";
			}
			
			System.out.println("The color is " + color + " \n");
			System.out.println("Press UP to continue \n");
			System.out.println("ESCAPE to exit");
			Button.waitForAnyPress();
			if(Button.ESCAPE.isDown()) {
				colorSensor.setFloodlight(false);
				again = false;
			}
			Delay.msDelay(300);
		}
		colorSensor.close();
	}

}

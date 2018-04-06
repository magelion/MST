package src.main;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.ColorAdapter;

public class ColorDetection {
	EV3ColorSensor cSensor;
	//SampleProvider colorProvider;
	//float[] colorSample;
	ColorAdapter cAdapt;
	
	public ColorDetection(){
	 cSensor = new EV3ColorSensor(Config.COLORPORT);
	 cAdapt= new ColorAdapter(cSensor);
	
	 //colorProvider = colorSensor.getRGBMode();
	 //colorSample = new float[colorProvider.sampleSize()];
	 
//	 Apparemment pour récupérer les couleurs à un instant t
	 //colorProvider.fetchSample(colorSample, 0);
	 
//	 Fin 
		
	}
	public void end(){
		cSensor.close();
	}
}

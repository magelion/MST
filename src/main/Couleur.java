package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

/**
*Classe en grande partie inspirée du code fourni (ColorSensor MarvinPersycup)
*/
public class Couleur {
	public static Color BLACK;
	public static Color BLUE;
	public static Color GREEN;
	public static Color GREY;
	public static Color RED;
	public static Color WHITE;
	public static Color YELLOW;

    private final static double ERROR = 0.02;
    
	/**
	 * indice de la couleur bleu dans le tableau
	 */
	public static final int COLOR_BLUE 		= 0;
	/**
	 * indice de la couleur noire dans le tableau
	 */
	public static final int COLOR_BLACK 	= 1;
	/**
	 * indice de la couleur blanche dans le tableau
	 */
	public static final int COLOR_WHITE 	= 2;
	/**
	 * indice de la couleur grise dans le tableau
	 */
	public static final int COLOR_GREY 		= 3;
	/**
	 * indice de la couleur jaune dans le tableau
	 */
	public static final int COLOR_YELLOW 	= 4;
	/**
	 * indice de la couleur rouge dans le tableau
	 */
	public static final int COLOR_RED 		= 5;
	/**
	 * indice de la couleur verte dans le tableau
	 */
	public static final int COLOR_GREEN 	= 6;
	
	/**
	 * Tableau contenant l'indice de la couleur ainsi qu'un échantillon des valeurs couleurs récupérés
	 */
	private float[][]				colors;
	
	/**
	 * Capteur de couleur physique du robot.
	 */
	private final EV3ColorSensor	colorSensor;
	
	/**
	 * représnte les données fournit par le capteur de couleur sous forme standard
	 */
	private final SampleProvider	average;
	
	/**
	 * Créer une nouvelle instance du capteur de couleur et initialize le SampleProvider 
	 * et charge le fichier de configuration
	 * @param option: si ==0, calibrage d'après fichier configuration
	 * 				si ==1 avec une seule mesure
     * 				sinon calibrage avec trois mesures
     */
	public Couleur(int option) {
		colorSensor = new EV3ColorSensor(Config.COLORPORT);
		average = new MeanFilter(colorSensor.getRGBMode(), 1);
			if(option==0){
				setCalibration();
				System.out.println("[COLOR SENSOR]          : Initialized");
			}else if(option==1){
			//param = String de la couleur
				lightOn();
				colors[COLOR_GREEN] = calibrate("Green");
				colors[COLOR_BLACK] = calibrate("Black");
				colors[COLOR_BLUE] = calibrate("Blue");
				colors[COLOR_WHITE] = calibrate("White");
				colors[COLOR_YELLOW] = calibrate("Yellow");
				colors[COLOR_RED] = calibrate("Red");
				colors[COLOR_GREY] = calibrate("Grey");
			}else{
				lightOn();
				colors[COLOR_GREEN] = calibrateAVG("Green");
				colors[COLOR_BLACK] = calibrateAVG("Black");
				colors[COLOR_BLUE] = calibrateAVG("Blue");
				colors[COLOR_WHITE] = calibrateAVG("White");
				colors[COLOR_YELLOW] = calibrateAVG("Yellow");
				colors[COLOR_RED] = calibrateAVG("Red");
				colors[COLOR_GREY] = calibrateAVG("Grey");
			}
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
		return isThisColor(colors[COLOR_BLUE]);
	}
		
	boolean isRed(){
		return isThisColor(colors[COLOR_RED]);
	}
		
	boolean isGreen(){
		return isThisColor(colors[COLOR_GREEN]);
	}
		
	boolean isBlack(){
		return isThisColor(colors[COLOR_BLACK]);
	}
		
	boolean isWhite(){
		return isThisColor(colors[COLOR_WHITE]);
	}
		
	boolean isYellow(){
		return isThisColor(colors[COLOR_YELLOW]);
	}
	
	boolean isGrey(){
		return isThisColor(colors[COLOR_GREY]);
	}
	
	/**
	 * Allume le capteur de couleur
	 */
	public void lightOn(){
		this.colorSensor.setFloodlight(Color.WHITE);
	}
	
	/**
	 * Termine le capteur de couleur
	 */
	public void lightOff(){
		this.colorSensor.setFloodlight(false);
	}

	/**
	 * Renvoie la couleur connue la plus proche.
	 * 
	 * @return la couleur (Color.EXAMPLE)
	 */
	public Color getCurrentColor(){
		float[]        sample  = new float[this.average.sampleSize()];
		double         minscal = Double.MAX_VALUE;
		int            color   = -1;

		this.average.fetchSample(sample, 0);

		for(int i= 0; i< 7; i++){
			if(this.colors[i].length > 0){
				double scalaire = scalaire(sample, this.colors[i]);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = i;
				}
			}
		}
		return getRealColor(color);
	}
	
	/**
	 * Effectue la convertion entre l'indice dans le tableau de couleur et la couleur sous forme 
	 * d'Enum pour le reste du programme
	 * @param color un entier associé à une couleur
	 * @return Color.COLOR la couleur associé à l'entier en entrée
	 */
	private static Color getRealColor(final int color) {
		switch (color) {
		case COLOR_BLACK:
			return BLACK;
		case COLOR_BLUE:
			return BLUE;
		case COLOR_GREEN:
			return GREEN;
		case COLOR_GREY:
			return GREY;
		case COLOR_RED:
			return RED;
		case COLOR_WHITE:
			return WHITE;
		case COLOR_YELLOW:
			return YELLOW;
		default:
			return null;
		}
	}


	/**
	 * Calcule la distance entre deux couleurs.
	 * @param v1 la preiere couleur
	 * @param v2 la seconde couleur
	 * @return la distance entre les deux couleurs.
	 */
	public static double scalaire(final float[] v1, final float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}
	
	/**
	 * Tente de lire et charger le fichier de calibration.
	 */
	private void setCalibration(){
		try{
			File fichierRead =  new File("conf.txt") ;
			ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichierRead)) ;
			this.colors = (float[][])ois.readObject() ;
			ois.close();
		}
		catch (Exception e) {
			System.out.println("[COLOR SENSOR]          : Impossible de charger le fichier de calibration");
		}
	}
	
}

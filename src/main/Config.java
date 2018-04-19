package main;

import lejos.hardware.port.SensorPort;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;

/**
 * Config contain many constant for ev3 port and constant for other class.
 */
final class Config {

	/**Pour DiffentialDrive (options de la classe MovePilot*/
	//diamètre des roues différents pour corriger un décalage vers la gauche
	public static final float LEFT_WHEEL_DIAMETER        = 5.49f;//cm
	public static final float RIGHT_WHEEL_DIAMETER        = 5.5f;//cm
	
	public static final float DISTANCE_TO_CENTER    = 6.1f;//cm
	
	//Pour mouvements linéaires
	public static final float LINEAR_ACCELERATION   = 0.0f;
	public static float LINEAR_SPEED			= 20.0f; //cm/sec 
	
	//Pour rotations
	public static float ANGULAR_ACCELERATION  = 0.0f;
	public static float ANGULAR_SPEED			= 230.0f; //cm/sec 
	
	public static final int   MAX_ROTATION_SPEED    = 800;
	
	/** Ports moteurs	 */
    /**
     * Pliers port.
     */
    static final Port PLIERSPORT = MotorPort.B;

    /**
     * Left Wheel Port.
     */
    static final Port LEFTWHEELPORT = MotorPort.A;

     /**
     * Right Wheel Port.
     */
    static final Port RIGHTWHEELPORT = MotorPort.C;

     /**
     * Color sensor Port.
     */
    static final Port COLORPORT = SensorPort.S1;

     /**
     * Touch sensor Port.
     */
    static final Port TOUCHPORT = SensorPort.S2;

     /**
     * Ultra sonic Sensor Port.
     */
    static final Port ULTRASONICPORT = SensorPort.S4;

    /**
     * MaxSpeed for pliers.
     */
    static final int MAXSPEEDPLIERS = 6000;

    /**
     * Rotation range for pliers.
     */
    static final int ROTATEPLIERS = 1000;

     /** constructor.
     */
    private Config() { }
}

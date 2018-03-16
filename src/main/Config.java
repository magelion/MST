package main;

import lejos.hardware.port.SensorPort;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;

/**
 * Config contain many constant for ev3 port and constant for automata.
 */
final class Config {

	public static final float WHEEL_DIAMETER        = 5.5f;//cm
	public static final float DISTANCE_TO_CENTER    = 6.5f;//cm
	public static final float LINEAR_ACCELERATION   = 0.5f;
	public static final float LINEAR_SPEED			= 100; //cm/sec //150
	public static final float ANGULAR_ACCELERATION  = 0.5f;
	public static final float ANGULAR_SPEED			= 70; //cm/sec //70
	public static final int   MAX_ROTATION_SPEED    = 70;
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
     * True if the robot start to left.
     */
    static final boolean ISLEFT = false;
    /** Variable define.
     * */
    static final int LONGSIZEBLOC = 45;

    /** Variable define.
     * */
    static final int HYPFIRST = 195;

    /** Variable define.
     * */
    static final int RECUL = 20;

    /** Variable define.
     * */
    static final int DEMITOUR = 180;

    /** Speed rotate.
     * */
    //static final double SPEEDROTATE = 300.0;

    /**
     * MaxSpeed for pliers.
     */
    static final int MAXSPEEDPLIERS = 6000;

    /**
     * Rotation range for pliers.
     */
    static final int ROTATEPLIERS = 1000;

    /**
     * Nb step.
     */
    static final int TIME = 3000;

    /**
     * Rotation range for adjust pliers.
     */
    static final int ADJUSTROTATEPLIERS = 300;

    /**
     * Led green color.
     */
    public static final int GREEN = 1;

    /**
     * Led red color.
     */
    public static final int RED = 2;

    /**
     * Led orange color.
     */
    public static final int ORANGE = 3;

    /**
     * Led none color.
     */
    public static final int NONE = -1;

     /** constructor.
     */
    private Config() { }
}

package main;


//import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
//import lejos.robotics.navigation.Navigator;

public class DifferentialDrive {
    private Chassis chassis;
    private Wheel wheelleft;
    private Wheel wheelright;
    public MovePilot pilot;
    
    
    //DifferentialDrive d=new DifferentialDrive(Config.LEFTWHEELPORT,Config.RIGHTWHEELPORT);
    
    //private final static int SPEED = (int) Config.SPEEDROTATE;


    public DifferentialDrive(Port left_port, Port right_port)
    {
    	
    	wheelleft = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(left_port),
				Config.WHEEL_DIAMETER).offset(-1*Config.DISTANCE_TO_CENTER);
		wheelright = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(right_port),
				Config.WHEEL_DIAMETER).offset(Config.DISTANCE_TO_CENTER);
		chassis = new WheeledChassis(new Wheel[]{wheelleft, wheelright},
				WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		//pilot.addMoveListener(this);
		pilot.setLinearAcceleration(Config.LINEAR_ACCELERATION);
		pilot.setLinearSpeed(Config.LINEAR_SPEED);
		pilot.setAngularSpeed(Config.ANGULAR_SPEED);
		pilot.setAngularAcceleration(Config.ANGULAR_ACCELERATION);
		
		
		
		
		
        /*mLeftMotor = new EV3LargeRegulatedMotor(left_port);
        mRightMotor = new EV3LargeRegulatedMotor(right_port);
        wheelleft= WheeledChassis.modelWheel(mLeftMotor, 5.1).offset(13.5).invert(true);  //diamètre roue:5,1
        //.offset().invert()
        wheelright=WheeledChassis.modelWheel(mRightMotor, 5.1).offset(13.5).invert(true);  //diamètre roue:5,1
        chassis = new WheeledChassis(new Wheel[] {wheelright, wheelleft}, WheeledChassis.TYPE_DIFFERENTIAL);
        pilot = new MovePilot(chassis);
        pilot.setLinearSpeed(Config.SPEEDROTATE);
        pilot.setAngularSpeed(50);
        navigator = new Navigator(pilot);*/
        
    }


    /*public void forward()
    {
        mLeftMotor.forward();
        mRightMotor.forward();
    }


    public void stop()
    {
        mLeftMotor.stop();
        mRightMotor.stop();
    }


    public void rotateClockwise()
    {
        mLeftMotor.forward();
        mRightMotor.backward();
    }


    public void rotateCounterClockwise()
    {
        mLeftMotor.backward();
        mRightMotor.forward();
    }
    
    /////////////////////////////////////////////////////////////////////
    public void forward(final double range){
    	for(int i=0;i<range;i++){//ATTENTION PROBLEMES ralentissement?
			d.forward();
			//Delay.msDelay(1000);
        }
    	//Thread.yield();    	
    }
    
    void backward(final double range) {
        forward(-range);
    }
    
    void turnLeft(final int angle) {
    	
    	Thread.yield();
    }
    
    void turnRight(final int angle) {
        turnLeft(-angle);
    }*/
    
    
    
    
}


package main;


import lejos.hardware.sensor.EV3TouchSensor;

/**
* Code fourni :patia2016blr-master
*/
/**
 * TouchSensor used the bumper to detect if activate.
 * use mainly the function.
 */
class TouchSensor extends Thread {

    /**
     * sensor sensorport.
     */
    private static EV3TouchSensor touchsensor;

    /**
     * Run the thread.
     */
    public void run() {
        try {
            touchsensor = new EV3TouchSensor(Config.TOUCHPORT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Constructor touchSensor.
     */
    TouchSensor() {
        run();
    }

    /**
     * Return the value of the thouch sensor.
     * @return boolean true if the touch sensor is pressed,else false.
     */
    public boolean isPressed() {
        float[] sample = new float[1];
        touchsensor.fetchSample(sample, 0);
        //System.out.println("sample="+sample[0]);
        return sample[0] != 0;
    }
}

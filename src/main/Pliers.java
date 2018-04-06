package src.main;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.RegulatedMotor;

/**
 * Pliers can close and open.
 */
class Pliers {

	/**
	 * Pliers motor of robot.
	 */
	private RegulatedMotor pliersMotor;

	/**
	 * True if is closed.
	 */
	private boolean isClosed;

	/**
	 * Adjust angle of pliers.
	 */
	private int adjust;

	/**
	 * Constructor for Pliers.
	 * 
	 * @param adj
	 *            a size for open pliers.
	 */
	Pliers(final int adj) {
		pliersMotor = new EV3LargeRegulatedMotor(Config.PLIERSPORT);
		pliersMotor.setAcceleration(Config.MAXSPEEDPLIERS);
		pliersMotor.setSpeed((int) pliersMotor.getMaxSpeed());
		adjust = adj;
	}

	/**
	 * Close pliers.
	 */
	void close() {
		pliersMotor.rotate(-(Config.ROTATEPLIERS + adjust));
		isClosed = true;
	}

	/**
	 * Reinit pliers. Close to final size
	 */
	void reinit() {
		pliersMotor.rotate(-adjust);
		isClosed = false;
		Thread.yield();
	}

	/**
	 * Reinit pliers. Open to init size
	 */
	void init() {
		pliersMotor.rotate(adjust);
		isClosed = false;
		Thread.yield();
	}

	/**
	 * Open pliers.
	 */
	void open() {
		pliersMotor.rotate(Config.ROTATEPLIERS + adjust);
		isClosed = false;
	}

	/**
	 * check if is closed or not.
	 * 
	 * @return true if pliers is closed
	 */
	boolean isClosed() {
		return isClosed;
	}

	/**
	 * set closed to b.
	 * 
	 * @param b
	 *            : define isClose to true if pliers is closed
	 */
	void setClosed(final boolean b) {
		isClosed = b;
	}

}

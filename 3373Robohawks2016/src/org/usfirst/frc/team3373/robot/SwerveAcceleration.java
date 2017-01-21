package org.usfirst.frc.team3373.robot;

public class SwerveAcceleration {

	/**
	 * Function to define level of change of angle or speed. If angular
	 * acceleration is needed, multiply output by 360
	 * 
	 * @param target
	 *            target value
	 * @param current
	 *            current value
	 * @param deltaConstant
	 *            constant that defines level of change per loop
	 * @param initialValue
	 *            initial speed
	 * @return returns delta of change
	 */
	public double accelerationControl(int target, int current, int deltaConstant, double acceptableRange,
			double initialValue) {
		double delta = (target - current);

		if (Math.abs(target - current) >= acceptableRange) {
			delta = Math.sqrt(current + initialValue);

			if (delta > 1) {
				delta = 1;
			}
		}
		return delta;
	}
}

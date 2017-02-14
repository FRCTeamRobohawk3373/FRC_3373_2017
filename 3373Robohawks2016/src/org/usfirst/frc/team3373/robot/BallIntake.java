package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;

public class BallIntake {
	CANTalonSafetyNet intakeMotor;
	double currentSpeed;
	double maxDelta;
	public BallIntake(int intakeTalonPort) {
		currentSpeed = 0;
		maxDelta = .2;
		intakeMotor = new CANTalonSafetyNet(intakeTalonPort);
		intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}

	public void intakeBalls() {
		accelerate(0.5);
	}

	public void clearBalls() {
		accelerate(-0.25);
	}

	public void ballsOff() {
		intakeMotor.set(0);
	}

	public void calibrate() {
		System.out.println("intake speed: " + intakeMotor.get());
	}

	public void accelerate(double speed) {
		double currentDelta = Math.abs(currentSpeed - speed);
		if (currentDelta > maxDelta) {
			if (speed > currentSpeed) {
				speed = currentSpeed + maxDelta;
			} else {
				speed = currentSpeed - maxDelta;
			}
		}
		// System.out.println("Speed:" + speed);
		currentSpeed = speed;
		intakeMotor.set(speed);
	}
}

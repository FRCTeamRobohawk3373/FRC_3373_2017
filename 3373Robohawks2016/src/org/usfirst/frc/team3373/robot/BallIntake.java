package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;

public class BallIntake {
	CANTalonSafetyNet intakeMotor;
	double currentSpeed;
	double maxDelta;

	public BallIntake(int intakeTalonPort) {
		currentSpeed = 0;
		maxDelta = .025;
		intakeMotor = new CANTalonSafetyNet(intakeTalonPort);
		intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}

	public void ballsIn() {
		this.accelerate(0.57);
	
	}

	public void ballsOut() {
		this.accelerate(-0.65);

	}

	public void ballsOff() {

		this.accelerate(0);
	}

	public void calibrate() {
		System.out.println("intake speed: " + intakeMotor.get());
	}

	public void goToSetSpeed(double speed){
		this.accelerate(speed);
		System.out.println(speed);
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

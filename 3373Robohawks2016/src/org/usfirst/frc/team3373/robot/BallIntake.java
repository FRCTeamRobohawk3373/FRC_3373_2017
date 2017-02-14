package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;

public class BallIntake {
	CANTalonSafetyNet intakeMotor;

	public BallIntake(int intakeTalonPort) {
intakeMotor = new CANTalonSafetyNet(intakeTalonPort);
intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	public void intakeBalls() {
		intakeMotor.set(0.5);
	}
	public void clearBalls() {
		intakeMotor.set(-0.25);
	}
	public void ballsOff() {
		intakeMotor.set(0);
	}
	public void calibrate() {
		System.out.println("intake speed: " + intakeMotor.get());
	}
}


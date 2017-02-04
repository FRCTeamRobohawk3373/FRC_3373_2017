package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;

public class BallIntake {
	CANTalon intakeMotor;

	public BallIntake(int intakeTalonPort) {
intakeMotor = new CANTalon(intakeTalonPort);
intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	public void intakeBalls() {
		intakeMotor.set(1);
	}
	public void clearBalls() {
		intakeMotor.set(-.5);
	}
	public void ballsOff() {
		intakeMotor.set(0);
	}
	public void calibrate() {
		System.out.println("intake speed: " + intakeMotor.get());
	}
}


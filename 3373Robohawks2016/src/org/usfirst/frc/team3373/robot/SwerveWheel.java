package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveWheel {

	CANTalonSafetyNet rotateMotor;
	CANTalonSafetyNet driveMotor;
	private double targetAngle;
	private double speed;
	private static int encoderUnitsPerRotation = 855;// Maximum units, not
														// actually the range of
														// units... 855 is the
														// range. Maybe change
														// it?
	private double speedModifier = .5;
	private int encOffset;
	double rotateAngle;

	public SwerveWheel(int driveMotorChannel, int rotateMotorID, double p, double i, double d, double rotateAng,
			int distanceFromZero, int encoderOffset) {

		rotateMotor = new CANTalonSafetyNet(rotateMotorID,1);
		driveMotor = new CANTalonSafetyNet(driveMotorChannel,1);
		encOffset = encoderOffset;

		rotateMotor.setPID(p, i, d);
		rotateMotor.changeControlMode(TalonControlMode.Position);
		rotateMotor.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);
		rotateMotor.enableLimitSwitch(false, false);
		rotateMotor.enableBrakeMode(true);
		rotateAngle = rotateAng;

	}

	public int calculateTargetAngle(double x, double y) {
		int angle = (int) Math.toDegrees(Math.atan2(y, -x));
		if (angle <= 0) {
			angle += 360;
		}
		return angle;

	}

	public int calculateTargetEncoder() { // Actually used! This method
											// calculates the target encoder
											// position, fed directly into the
											// set() method in rotate(),
											// accounting for offset as a result
											// of the encoder zero value.
		double targetEnc = targetAngle;
		targetEnc = targetEnc / 360;
		targetEnc = targetEnc * encoderUnitsPerRotation;
		targetEnc += encOffset;
		targetEnc = targetEnc % 870;
		if (targetEnc < 15) {
			targetEnc = 15;
		}
		int finalityal = (int) targetEnc;
		return finalityal;
	}

	public void setTargetAngle(double TA) {
		targetAngle = TA;
	}

	public double getTargetAngle() {
		return targetAngle;
	}

	public void setSpeed(double speedIn) {
		speed = speedIn;
	}

	public double getSpeed() {
		return speed;
	}

	public int angleToEncoderUnit(double angle) {

		double deltaEncoder;
		deltaEncoder = angle * (encoderUnitsPerRotation / 360.0) + 15;

		return (int) deltaEncoder;
	}

	public int encoderUnitToAngle(int encoderValue) {
		double angle = 0;
		if (encoderValue >= 0) {
			angle = (encoderValue * (360.0 / encoderUnitsPerRotation));
			angle = angle % 360;
		} else if (encoderValue < 0) {
			angle = (encoderValue * (360.0 / encoderUnitsPerRotation));
			angle = angle % 360 + 360;
		}
		return (int) angle;// (angle+2*(90-angle));
	}

	public void setSpeedModifier(double speed) {
		speedModifier = speed;
	}

	public void disable() {
		rotateMotor.set(0);
	}

	public double getCurrentAngle() {
		double currentAngle = (rotateMotor.getAnalogInRaw() - encOffset);
		if (currentAngle <= 0) {
			currentAngle += 870;
		}
		currentAngle = currentAngle / (encoderUnitsPerRotation / 360.0);
		return currentAngle;
	}

	public double calculateDriveSpeed(double x, double y) {
		double speed = 0;
		double square = (x * x) + (y * y);
		speed = Math.sqrt(square);
		return speed;
	}

	public void drive(double x, double y) {
		/*
		 * if(Math.abs(targetAngle-currentAngle) > 2)
		 * driveMotor.set(speed*speedModifier); else{
		 * driveMotor.set(-speed*speedModifier); }
		 */
		SmartDashboard.putNumber("Speed: " + this.toString(), speed * speedModifier);
		driveMotor.set(calculateDriveSpeed(x, y) * speedModifier);// *directionalModifier
	}

	public void driveWheel() {
		driveMotor.set(speed * speedModifier);// *directionalModifier
	}

	public int encoderCheck(int targetEncoder) {
		int target = 0;
		target = targetEncoder;
		// target += encOffset;
		target = target % 870;
		if (target < 15) {
			target = 15;
		}
		return target;
	}

	public void rotate() {
		rotateMotor.changeControlMode(TalonControlMode.Position);
		int encoderTarget = angleToEncoderUnit(getDeltaTheta()) + rotateMotor.getAnalogInRaw();
		encoderTarget = encoderCheck(encoderTarget);
		rotateMotor.set(encoderTarget);
	}

	public double getDeltaTheta() {
		double deltaTheta = getTargetAngle() - getCurrentAngle();

		while ((deltaTheta < -90) || (deltaTheta > 90)) {
			if (deltaTheta > 90) {
				deltaTheta -= 180;
				speed *= -1;
			} else if (deltaTheta < -90) {
				deltaTheta += 180;
				speed *= -1;
			}
		}

		// if (deltaTheta >= 1 || deltaTheta <= -1){
		return deltaTheta;
		// } else {
		// return 0;
		// }

	}

	public double getRAngle() {
		return rotateAngle;
	}
	public void setCurrentPosition(){
		rotateMotor.set(rotateMotor.getAnalogInRaw());
	}
}

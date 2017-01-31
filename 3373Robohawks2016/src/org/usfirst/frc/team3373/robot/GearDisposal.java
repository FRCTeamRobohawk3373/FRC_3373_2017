package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;

public class GearDisposal {
	CANTalon rotateMotor;
	int rotatePort;
	DigitalInput pegDetector;
	boolean pegStatus = false;
	int downPos;
	int upPos;
	int compressPos;
	int delay;
	long startingTime;
	long currentTime;
	boolean gearUpTimer = false;
	boolean startTimer = false;
	boolean isUp = true;

	public GearDisposal(int rotateMotorChannel, int pegDetectorChannel, int downPosit, int upPosit, int compressPosit, int desiredDelay) {
		rotateMotor = new CANTalon(rotateMotorChannel);
		pegDetector = new DigitalInput(pegDetectorChannel);
		rotateMotor.changeControlMode(CANTalon.TalonControlMode.Position);
		upPos = upPosit;
		downPos = downPosit;
		compressPos = compressPosit;
		delay = desiredDelay;

	}

	public void openGearContainer() {
		rotateMotor.set(downPos);
		startingTime = System.currentTimeMillis();
		isUp = false;
	}
	
	public void closeGearContainer() {
		rotateMotor.set(upPos);
		isUp = true;
	}
	
	public void compressGearContainer() {
		rotateMotor.set(compressPos);
	}

	public boolean isPegDetected() {
		if (pegDetector.get()) {
			pegStatus = true;
		} else {
			pegStatus = false;
		}
		return pegStatus;
	}

	
	//timer method to control dropper
	/*
	 private boolean gearTimer() {
	 
		if (startTimer) {
			currentTime = System.currentTimeMillis();
			if (currentTime - startingTime >= delay) {
				gearUpTimer = true;
			} else {
				gearUpTimer = false;
			}
		} else {

		}
		return gearUpTimer;
	}
	 */
}

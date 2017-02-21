package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;

public class GearController {
	CANTalonSafetyNet rotateGearDoor;
	int rotatePort;

	boolean pegStatus = false;
	int openPos;
	int closedPos;
	int compressPos;
	int delay;

	int target = 0;

	int current = 0;

	long startingTime;
	long currentTime;
	boolean gearUpTimer = false;
	boolean startTimer = false;
	boolean isCompressed = true;

	double gearDoorError;
	double gearDoorSpeed;
	
	double gearDoorSpeedModifier;

	public GearController(int rotateMotorChannel, int downPosit, int upPosit, int compressPosit) {
		rotateGearDoor = new CANTalonSafetyNet(rotateMotorChannel, 1);
		rotateGearDoor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		closedPos = upPosit;
		openPos = downPosit;
		compressPos = compressPosit;
		rotateGearDoor.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);
		target = rotateGearDoor.getAnalogInRaw();
		rotateGearDoor.enableLimitSwitch(false, false);

	}

	public void openGearContainer() {
		isCompressed = false;
		// rotateGearDoor.set(openPos);
		target = openPos;
		//System.out.println(rotateGearDoor.getAnalogInRaw());
	}

	public void closeGearContainer() {
		// rotateGearDoor.set(closedPos);
		target = closedPos;
		isCompressed = false;
	//	System.out.println(rotateGearDoor.getAnalogInRaw());
	}

	public void compressGearContainer() {
		// rotateGearDoor.set(compressPos);
		target = compressPos;
		isCompressed = true;
	//	System.out.println(rotateGearDoor.getAnalogInRaw());
	}

	public void goToCurrentTarget() {

		rotateGearDoor.set(target);
		//System.out.println("actual: " + rotateGearDoor.getAnalogInRaw());
		//System.out.println("target: " + target);
	}

	public boolean isPegDetected() {
		if (rotateGearDoor.isRevLimitSwitchClosed()) {
			pegStatus = false;
		} else {
			pegStatus = true;
		}
		return pegStatus;
	}

	public boolean isCompressed() {
		return isCompressed;
	}

	// timer method to control dropper
	/*
	 * private boolean gearTimer() {
	 * 
	 * if (startTimer) { currentTime = System.currentTimeMillis(); if
	 * (currentTime - startingTime >= delay) { gearUpTimer = true; } else {
	 * gearUpTimer = false; } } else {
	 * 
	 * } return gearUpTimer; }
	 */

	public void calibrate() {
		System.out.println("Encoder value: " + rotateGearDoor.getAnalogInRaw());
		System.out.println("Limit Switch Status: " + rotateGearDoor.isRevLimitSwitchClosed());
		rotateGearDoor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}

	public void setGearDoorSpeed(double speedMod) {
		gearDoorSpeedModifier = speedMod;
		current = rotateGearDoor.getAnalogInRaw();
		gearDoorError=Math.abs(current-target);
		//System.out.println("Target: " + target + "   Current: " + current + "  Error:" + gearDoorError);

		// x/360*800 where x=degrees
		if (gearDoorError<10) {  // stop deadband
			gearDoorSpeed=0;
		//	System.out.print("stop");
		}
		else if (gearDoorError<80) {  // low speed deadband 
			gearDoorSpeed=0.1 * gearDoorSpeedModifier;
		//	System.out.print("slow");
		}
		else { // highs speed mode
			gearDoorSpeed=0.5 * gearDoorSpeedModifier; 
		//	System.out.print("fast");
		}

		if (target < current) {
			rotateGearDoor.set(gearDoorSpeed);
		}
		else {
			rotateGearDoor.set(-gearDoorSpeed);
		}
	}

}

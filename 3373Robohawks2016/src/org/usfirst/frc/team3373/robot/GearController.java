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
	boolean isUp = true;
 //@const param gear door
	 int gearspeedPos1 = 150;
	 double gearspeed1 = 0.2;
	 int gearspeedPos2 = 150;
	 double gearspeed2 = 0.05;
	 int gearspeedPos3 = 20;
	 double gearspeed3 = 0.0;

	public GearController(int rotateMotorChannel, int downPosit, int upPosit, int compressPosit) {
		rotateGearDoor = new CANTalonSafetyNet(rotateMotorChannel);
		rotateGearDoor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		closedPos = upPosit;
		openPos = downPosit;
		compressPos = compressPosit;
		rotateGearDoor.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);
		target = rotateGearDoor.getAnalogInRaw();
		rotateGearDoor.enableLimitSwitch(false, false);
		

	}

	public void openGearContainer() {
		//rotateGearDoor.set(openPos);
		target = openPos;
		isUp = false;
		System.out.println(rotateGearDoor.getAnalogInRaw());
	}

	public void closeGearContainer() {
		//rotateGearDoor.set(closedPos);
		target = closedPos;
		isUp = true;
		System.out.println(rotateGearDoor.getAnalogInRaw());
	}

	public void compressGearContainer() {
		//rotateGearDoor.set(compressPos);
		target = compressPos;
		System.out.println(rotateGearDoor.getAnalogInRaw());
	}

	public void goToCurrentTarget() {
		
		rotateGearDoor.set(target);
		System.out.println("actual: " + rotateGearDoor.getAnalogInRaw());
		System.out.println("target: " + target);
	}


	public boolean isPegDetected() {
		if (rotateGearDoor.isRevLimitSwitchClosed()) {
			pegStatus = true;
		} else {
			pegStatus = false;
		}
		return pegStatus;
	}

	public boolean isUp() {
		return isUp;
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


	
	public void setGearDoorSpeed(){
		current = rotateGearDoor.getAnalogInRaw();
		System.out.println("Target: " + target + "   Current: " + current + "    ");
	if (target > (current+gearspeedPos1)&& target > current){
			rotateGearDoor.set(gearspeed1);
		}
	if (target < (current-gearspeedPos1)&& target < current){
		rotateGearDoor.set(-gearspeed1);
		}
	if (target > (current+gearspeedPos2)&& target > current){
		rotateGearDoor.set(gearspeed2);
	}
	if (target < (current+gearspeedPos2)&& target < current){
		rotateGearDoor.set(-gearspeed2);
	}
	if (target < (current+gearspeedPos3)&& target < current){
		rotateGearDoor.set(gearspeed3);
	}
	if (target > (current-gearspeedPos3) && target > current){
		rotateGearDoor.set(gearspeed3);
	}
	}
	
}

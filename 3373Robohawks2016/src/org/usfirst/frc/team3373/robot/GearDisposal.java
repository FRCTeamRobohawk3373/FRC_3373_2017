package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;



public class GearDisposal {
	CANTalon rotateMotor;
	int rotatePort;

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

	public GearDisposal(int rotateMotorChannel,  int downPosit, int upPosit, int compressPosit) {
		rotateMotor = new CANTalon(rotateMotorChannel);
		rotateMotor.changeControlMode(CANTalon.TalonControlMode.Position);
		upPos = upPosit;
		downPos = downPosit;
		compressPos = compressPosit;
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
		if (rotateMotor.isFwdLimitSwitchClosed()) {
			pegStatus = true;
		} else {
			pegStatus = false;
		}
		return pegStatus;
	}

	public boolean isUp() {
		return isUp;
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

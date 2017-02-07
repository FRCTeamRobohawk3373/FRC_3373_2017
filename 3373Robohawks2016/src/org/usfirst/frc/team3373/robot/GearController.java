package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;




public class GearController {
	CANTalonSafetyNet rotateMotor;
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
	double p = 10;
	double d = 10;
	double i = 0;

	public GearController(int rotateMotorChannel,  int downPosit, int upPosit, int compressPosit) {
		rotateMotor = new CANTalonSafetyNet(rotateMotorChannel);
		rotateMotor.changeControlMode(CANTalon.TalonControlMode.Position);
		upPos = upPosit;
		downPos = downPosit;
		compressPos = compressPosit;
		rotateMotor.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);
		rotateMotor.setPID(p, i, d);

	}

	public void openGearContainer() {
		rotateMotor.set(downPos);
		startingTime = System.currentTimeMillis();
		isUp = false;
		System.out.println(rotateMotor.getAnalogInRaw());
	}
	
	public void closeGearContainer() {
		rotateMotor.set(upPos);
		isUp = true;
		System.out.println(rotateMotor.getAnalogInRaw());
	}
	
	public void compressGearContainer() {
		rotateMotor.set(compressPos);
		System.out.println(rotateMotor.getAnalogInRaw());
	}

	public boolean isPegDetected() {
		if (rotateMotor.isRevLimitSwitchClosed()) {
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
	
	public void calibrate(){
		System.out.println("Encoder value: " + rotateMotor.getAnalogInRaw());
		System.out.println("Limit Switch Status: " + rotateMotor.isRevLimitSwitchClosed());
		rotateMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	
}

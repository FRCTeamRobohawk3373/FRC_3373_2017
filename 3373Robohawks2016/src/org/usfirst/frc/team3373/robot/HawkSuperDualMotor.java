package org.usfirst.frc.team3373.robot;

public class HawkSuperDualMotor{

	public HawkSuperDualMotor(int deviceNumber, int encoderMin, int encoderMax, int maxPercent, int deviceNumber2, int encoderMin2, int encoderMax2, int maxPercent2) {
		HawkSuperMotor motor1 = new HawkSuperMotor(deviceNumber, encoderMin, encoderMax, maxPercent);
		HawkSuperMotor motor2 = new HawkSuperMotor(deviceNumber2, encoderMin2, encoderMax2, maxPercent2);
	}
	public void goToHeight(double targetHeight){
		//if()
	}
	public void manualUp(){
		
	}
	public void manualDown(){
		
	}

}

package org.usfirst.frc.team3373.robot;

public class HawkSuperDualMotor{

	HawkSuperMotor motor1;
	HawkSuperMotor motor2;
	
	public HawkSuperDualMotor(int deviceNumber, int encoderMin, int encoderMax, int maxPercent, int minPercent, double travelRange, double maxSpeedChange, int deviceNumber2, int encoderMin2, int encoderMax2, int maxPercent2, int minPercent2, double travelRange2, double maxSpeedChange2) {
		motor1 = new HawkSuperMotor(deviceNumber, encoderMin, encoderMax, maxPercent, minPercent, travelRange, maxSpeedChange);
		motor2 = new HawkSuperMotor(deviceNumber2, encoderMin2, encoderMax2, maxPercent2, minPercent2, travelRange2, maxSpeedChange2);
	}
	public void goToHeight(double targetHeight){
		if(!(motor1.currentEncHeight>motor2.currentEncHeight+100)&& !(motor2.currentEncHeight>motor1.currentEncHeight+100)){
		motor1.goToHeight(targetHeight);
		motor2.goToHeight(targetHeight);
		}else if(motor1.currentEncHeight>motor2.currentEncHeight+100 && motor1.getSpeed()>=0){
			motor1.set(.4);
			motor2.set(.5);
		}else if(motor2.currentEncHeight>motor1.currentEncHeight+100 && motor2.getSpeed()>=0){
			motor2.set(.4);
			motor1.set(.5);
		}else if(motor1.currentEncHeight<motor2.currentEncHeight-100 && motor1.getSpeed()<=0){
			motor1.set(-.4);
			motor2.set(-.5);
		}else if(motor2.currentEncHeight<motor1.currentEncHeight-100 && motor2.getSpeed()<=0){
			motor2.set(-.4);
			motor1.set(-.5);
		}
	}
	public void set(double speed){
		motor1.set(speed);
		motor2.set(speed);
	}
	public void manualUp(){
		
	}
	public void manualDown(){
		
	}

}
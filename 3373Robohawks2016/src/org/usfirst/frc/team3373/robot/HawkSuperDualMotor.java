package org.usfirst.frc.team3373.robot;

public class HawkSuperDualMotor{

	HawkSuperMotor motor1;
	HawkSuperMotor motor2;
	double height;
	
	public HawkSuperDualMotor(int deviceNumber, int encoderMin, int encoderMax, int maxPercent, int minPercent, double travelRange, double maxSpeedChange, int motorDirection, int limitSwitchForwID, int limitSwitchRevID ,int deviceNumber2, int encoderMin2, int encoderMax2, int maxPercent2, int minPercent2, double travelRange2, double maxSpeedChange2, int motorDirection2, int limitSwitchForwID2, int limitSwitchRevID2) {
		motor1 = new HawkSuperMotor(deviceNumber, encoderMin, encoderMax, maxPercent, minPercent, travelRange, maxSpeedChange, motorDirection, limitSwitchForwID,limitSwitchRevID);
		motor2 = new HawkSuperMotor(deviceNumber2, encoderMin2, encoderMax2, maxPercent2, minPercent2, travelRange2, maxSpeedChange2, motorDirection2,limitSwitchForwID2,limitSwitchRevID2);
	}
	public void goToHeight(double targetHeight){
		if(!(motor1.currentHeight>motor2.currentHeight+.02)&& !(motor2.currentHeight>motor1.currentHeight+.02)){
		motor1.goToHeight(targetHeight);
		motor2.goToHeight(targetHeight);
		}else if(motor1.currentHeight>motor2.currentHeight+.02 && motor1.getSpeed()>=0){
			motor1.set(.4);
			motor2.set(.5);
		}else if(motor2.currentHeight>motor1.currentHeight+.02 && motor2.getSpeed()>=0){
			motor2.set(.4);
			motor1.set(.5);
		}else if(motor1.currentHeight<motor2.currentHeight-.02 && motor1.getSpeed()<=0){
			motor1.set(-.4);
			motor2.set(-.5);
		}else if(motor2.currentHeight<motor1.currentHeight-.02 && motor2.getSpeed()<=0){
			motor2.set(-.4);
			motor1.set(-.5);
		}
	}
	public void sniperToHeight(double targetHeight){
		if(!(motor1.currentHeight>motor2.currentHeight+.02)&& !(motor2.currentHeight>motor1.currentHeight+.02)){
			motor1.sniperToHeight(targetHeight);
			motor2.sniperToHeight(targetHeight);
			}else if(motor1.currentHeight>motor2.currentHeight+.02 && motor1.getSpeed()>=0){
				motor1.set(.2);
				motor2.set(.25);
			}else if(motor2.currentHeight>motor1.currentHeight+.02 && motor2.getSpeed()>=0){
				motor2.set(.2);
				motor1.set(.25);
			}else if(motor1.currentHeight<motor2.currentHeight-.02 && motor1.getSpeed()<=0){
				motor1.set(-.2);
				motor2.set(-.25);
			}else if(motor2.currentHeight<motor1.currentHeight-.02 && motor2.getSpeed()<=0){
				motor2.set(-.2);
				motor1.set(-.25);
			}
	}
	public void initDown(){
		if(!(motor1.currentHeight>motor2.currentHeight+.02)&& !(motor2.currentHeight>motor1.currentHeight+.02)){
			motor1.set(-.3);
			motor2.set(-.3);
			}else if(motor1.currentHeight<motor2.currentHeight-.02){
				motor1.set(-.2);
				motor2.set(-.3);
			}else if(motor2.currentHeight<motor1.currentHeight-.02){
				motor2.set(-.2);
				motor1.set(-.3);
			}
	}
	public void set(double speed){
		motor1.set(speed);
		motor2.set(speed);
	}
	public void manualUp(){
		goToHeight(motor1.travel);
	}
	public void manualDown(){
		goToHeight(0);
	}
	public void sniperDown(){
		sniperToHeight(0);
	}
	public void sniperUp(){
		sniperToHeight(motor1.travel);
	}
	public double getHeight(){
		height = motor1.currentHeight;
		return height;
	}

}
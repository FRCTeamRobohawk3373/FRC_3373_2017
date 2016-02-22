package org.usfirst.frc.team3373.robot;

public class HawkDualLinearActuator {
	
	HawkActuator motor1;
	HawkActuator motor2;
	
	public void HawkDualLinearActuators(int actuatorID,double actuatorMaxPotValue ,double actuatorMinPotValue, double maxSpeedChange, int limitSwitchForwID, int limitSwitchRevID, int actuatorID2 ,double actuatorMaxPotValue2 ,double actuatorMinPotValue2, double maxSpeedChange2, int limitSwitchForwID2, int limitSwitchRevID2) {
		motor1 = new HawkActuator(actuatorID, actuatorMaxPotValue , actuatorMinPotValue,  maxSpeedChange, limitSwitchForwID,limitSwitchRevID);
		motor2 = new HawkActuator(actuatorID2, actuatorMaxPotValue2 , actuatorMinPotValue2,  maxSpeedChange2, limitSwitchForwID2, limitSwitchRevID2);
	}
	public void goToHeight(double targetHeight){
		if(!(motor1.getAnalogInRaw()>motor2.getAnalogInRaw()+2)&& !(motor2.getAnalogInRaw()>motor1.getAnalogInRaw()+2)){
		motor1.goToHeight(targetHeight);
		motor2.goToHeight(targetHeight);
		}else if(motor1.getAnalogInRaw()>motor2.getAnalogInRaw()+2 && motor1.getSpeed()>=0){
			motor1.set(.4);
			motor2.set(.5);
		}else if(motor2.getAnalogInRaw()>motor1.getAnalogInRaw()+2 && motor2.getSpeed()>=0){
			motor2.set(.4);
			motor1.set(.5);
		}else if(motor1.getAnalogInRaw()<motor2.getAnalogInRaw()-2 && motor1.getSpeed()<=0){
			motor1.set(-.4);
			motor2.set(-.5);
		}else if(motor2.getAnalogInRaw()<motor1.getAnalogInRaw()-2 && motor2.getSpeed()<=0){
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
package org.usfirst.frc.team3373.robot;

public class HawkDualLinearActuator {
	
	HawkActuator motor1;
	HawkActuator motor2;
	
	public void HawkDualLinearActuators(int actuatorID,double actuatorMaxPotValue ,double actuatorMinPotValue, double maxSpeedChange,int potID, int bottomLimitSwitchID, int topLimitSwitchID, int actuatorID2 ,double actuatorMaxPotValue2 ,double actuatorMinPotValue2, double maxSpeedChange2,int potID2, int bottomLimitSwitchID2, int topLimitSwitchID2) {
		motor1 = new HawkActuator(actuatorID, actuatorMaxPotValue , actuatorMinPotValue,  maxSpeedChange, potID,  bottomLimitSwitchID,  topLimitSwitchID);
		motor2 = new HawkActuator(actuatorID2, actuatorMaxPotValue2 , actuatorMinPotValue2,  maxSpeedChange2, potID2,  bottomLimitSwitchID2,  topLimitSwitchID2);
	}
	public void goToHeight(double targetHeight){
		if(!(motor1.pot.getValue()>motor2.pot.getValue()+2)&& !(motor2.pot.getValue()>motor1.pot.getValue()+2)){
		motor1.goToHeight(targetHeight);
		motor2.goToHeight(targetHeight);
		}else if(motor1.pot.getValue()>motor2.pot.getValue()+2 && motor1.getSpeed()>=0){
			motor1.set(.4);
			motor2.set(.5);
		}else if(motor2.pot.getValue()>motor1.pot.getValue()+2 && motor2.getSpeed()>=0){
			motor2.set(.4);
			motor1.set(.5);
		}else if(motor1.pot.getValue()<motor2.pot.getValue()-2 && motor1.getSpeed()<=0){
			motor1.set(-.4);
			motor2.set(-.5);
		}else if(motor2.pot.getValue()<motor1.pot.getValue()-2 && motor2.getSpeed()<=0){
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
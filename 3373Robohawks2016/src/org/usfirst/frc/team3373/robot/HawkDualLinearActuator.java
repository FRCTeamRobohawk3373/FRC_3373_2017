package org.usfirst.frc.team3373.robot;

public class HawkDualLinearActuator {
	
	HawkActuator motor1;
	HawkActuator motor2;
	double speed1;
	double speed2;
	
	public HawkDualLinearActuator(int actuatorID,double actuatorMaxPotValue ,double actuatorMinPotValue, double maxSpeedChange, int limitSwitchForwID, int limitSwitchRevID, int actuatorID2 ,double actuatorMaxPotValue2 ,double actuatorMinPotValue2, double maxSpeedChange2, int limitSwitchForwID2, int limitSwitchRevID2) {
		motor1 = new HawkActuator(actuatorID, actuatorMaxPotValue , actuatorMinPotValue,  maxSpeedChange, limitSwitchForwID,limitSwitchRevID);
		motor2 = new HawkActuator(actuatorID2, actuatorMaxPotValue2 , actuatorMinPotValue2,  maxSpeedChange2, limitSwitchForwID2, limitSwitchRevID2);
	}
	public void goToHeight(double targetHeight){
		if(!(motor1.getHeight()>motor2.getHeight()+.2)&& !(motor2.getHeight()>motor1.getHeight()+.2)){
		motor2.goToHeight(targetHeight);
		motor1.goToHeight(targetHeight);
		System.out.println("\n 1");
	
		
		/*}else if(motor1.getHeight()<motor2.getHeight()-.2 && motor1.getCurrentHeight()>targetHeight+.2){
			motor1.set(-.2);
			motor2.set(-.3);
			System.out.println("SOUP");
		}else if(motor1.getHeight()>motor2.getHeight()+.2 && motor1.getCurrentHeight()>targetHeight+.2){
			motor1.set(-.3);
			motor2.set(-.2);
			System.out.println("soup");
		}else if(motor1.getHeight()<motor2.getHeight()-.2 && motor1.getCurrentHeight()<targetHeight-.2){
			motor1.set(.3);
			motor2.set(.2);
			System.out.println("SoUp");
		}else if(motor1.getHeight()>motor2.getHeight()+.2 && motor1.getCurrentHeight()<targetHeight-.2){
			motor1.set(.2);
			motor2.set(.3);
			System.out.println("sOuP");
		}else{
			System.out.println("No soup.  3 u");
			motor1.goToHeight(targetHeight);
			motor2.goToHeight(targetHeight);*/
		}else if(motor1.getDistanceFromHeight() > 0){ //Going down
			System.out.println("POMASoUPA");
			if(motor1.getDistanceFromHeight() > motor2.getDistanceFromHeight() + .2){
				System.out.println("Soup. 12");
				motor1.goToHeight(targetHeight);
				motor2.sniperToHeight(targetHeight);
				
			} else if(motor2.getDistanceFromHeight() > motor1.getDistanceFromHeight() + .2){
				System.out.println("Stew. 12");
				motor2.goToHeight(targetHeight);
				motor1.sniperToHeight(targetHeight);
			}
		}else if(motor1.getDistanceFromHeight() < 0){ //Going up
			System.out.println("DistanceFromHeight 1: " + motor1.getDistanceFromHeight());
			System.out.println("Apple pie.");
			if(motor1.getDistanceFromHeight() < motor2.getDistanceFromHeight() - .2){
				System.out.println("Soup.");
				motor1.goToHeight(targetHeight);
				motor2.sniperToHeight(targetHeight);
				
			} else if(motor2.getDistanceFromHeight() < motor1.getDistanceFromHeight() - .2){
				System.out.println("Stew.");
				motor2.goToHeight(targetHeight);
				motor1.sniperToHeight(targetHeight);
			}
		}
		
		System.out.println("Height 1 " + motor1.getHeight());
		System.out.println("Height 2 " + motor2.getHeight());

		System.out.println("Speed" + motor1.getSpeed());
		System.out.println("Target Height" + targetHeight);
		System.out.println("Target pot pos: " + motor1.getTargetPotPos());
		System.out.println("range: "+ motor1.range);
		System.out.println("Pot pos: "+ motor1.getAnalogInRaw());
		System.out.println("\n");

	}
	public void set(double speed){
		motor1.set(speed);
		motor2.set(speed);
	}
	public void manualUp(){
		
	}
	public void manualDown(){
		
	}
	public void sniperToHeight(double targetHeight){
		if(!(motor1.currentHeight>motor2.currentHeight+.02)&& !(motor2.currentHeight>motor1.currentHeight+.02)){
			motor1.sniperToHeight(targetHeight);
			motor2.sniperToHeight(targetHeight);
		}else if(motor1.getAnalogInRaw()>motor2.getAnalogInRaw()+2 && motor1.getSpeed()>=0){
				motor1.set(.2);
				motor2.set(.25);
		}else if(motor2.getAnalogInRaw()>motor1.getAnalogInRaw()+2 && motor2.getSpeed()>=0){
				motor2.set(.2);
				motor1.set(.25);
		}else if(motor1.getAnalogInRaw()<motor2.getAnalogInRaw()-2 && motor1.getSpeed()<=0){
				motor1.set(-.2);
				motor2.set(-.25);
		}else if(motor2.getAnalogInRaw()<motor1.getAnalogInRaw()-2 && motor2.getSpeed()<=0){
				motor2.set(-.2);
				motor1.set(-.25);
			}
	}
	public void sniperDown(){
		sniperToHeight(0);
	}
	public void sniperUp(){
		sniperToHeight(motor1.travel);
	}
}
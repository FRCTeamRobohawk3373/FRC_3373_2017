package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;

public class HawkSuperDualMotor{

	HawkSuperMotor motor1;
	HawkSuperMotor motor2;
	CANTalon motor3;
	double height;
	
	public HawkSuperDualMotor(int deviceNumber, int encoderMin, int encoderMax, int maxPercent, int minPercent, double travelRange, double maxSpeedChange, int motorDirection, int limitSwitchForwID, int limitSwitchRevID ,int deviceNumber2, int encoderMin2, int encoderMax2, int maxPercent2, int minPercent2, double travelRange2, double maxSpeedChange2, int motorDirection2, int limitSwitchForwID2, int limitSwitchRevID2) {
	//	motor1 = new HawkSuperMotor(deviceNumber, encoderMin, encoderMax, maxPercent, minPercent, travelRange, maxSpeedChange, motorDirection, limitSwitchForwID,limitSwitchRevID);
		motor2 = new HawkSuperMotor(deviceNumber2, encoderMin2, encoderMax2, maxPercent2, minPercent2, travelRange2, maxSpeedChange2, motorDirection2,limitSwitchForwID2,limitSwitchRevID2);
		motor3 = new CANTalon(deviceNumber);
		motor3.setPID(4, .0005, 0);
		motor3.changeControlMode(CANTalon.TalonControlMode.Position);
		motor3.enableLimitSwitch(false, false);
		motor3.enableBrakeMode(true);
	}
	public void followToHeight(double targetHeight){
		motor2.goToHeight(targetHeight);
		motor3.set(-1*motor2.getEncPosition());
	}
	
	
	
	public void goToHeight(double targetHeight){
		/*if(motor1.getCurrentHeight() > targetHeight - .2 && motor1.getCurrentHeight()< targetHeight + .2){
			motor1.setScaled(0);
			motor2.setScaled(0);
		}else if(!(motor1.currentHeight>motor2.currentHeight+.02)&& !(motor2.currentHeight>motor1.currentHeight+.02)){
		motor1.goToHeight(targetHeight);
		motor2.goToHeight(targetHeight);
		}else if(motor1.currentHeight>motor2.currentHeight+.02 && motor1.getSpeed()>0){
			motor1.setScaled(.4);
			motor2.setScaled(.5);
			System.out.println("Case 1");
		}else if(motor2.currentHeight>motor1.currentHeight+.02 && motor2.getSpeed()>0){
			motor2.setScaled(.4);
			motor1.setScaled(.5);
			System.out.println("Case 2");
		}else if(motor1.currentHeight<motor2.currentHeight-.02 && motor1.getSpeed()<=0){
			motor1.setScaled(-.4);
			motor2.setScaled(-.5);
			System.out.println("Case 3");
		}else if(motor2.currentHeight<motor1.currentHeight-.02 && motor2.getSpeed()<=0){
			motor2.setScaled(-.4);
			motor1.setScaled(-.5);
			System.out.println("Case 4");
		}*/
		
		if(!(motor1.currentHeight>motor2.currentHeight+.02)&& !(motor2.currentHeight>motor1.currentHeight+.02)){
			motor1.goToHeight(targetHeight);
			motor2.goToHeight(targetHeight);
			}else if(motor1.currentHeight>motor2.currentHeight+.02 && motor1.currentHeight < targetHeight){
				motor1.sniperToHeight(targetHeight);
				motor2.goToHeight(targetHeight);
				System.out.println("Case 1");
				SmartDashboard.putNumber("Case", 1);
			}else if(motor2.currentHeight>motor1.currentHeight+.02 && motor2.currentHeight < targetHeight){
				motor2.sniperToHeight(targetHeight);
				motor1.goToHeight(targetHeight);
				SmartDashboard.putNumber("Case", 2);
			}else if(motor1.currentHeight<motor2.currentHeight-.02 && motor1.currentHeight > targetHeight){
				motor1.sniperToHeight(targetHeight);
				motor2.goToHeight(targetHeight);
				System.out.println("Case 3");
				SmartDashboard.putNumber("Case", 3);
			}else if(motor2.currentHeight<motor1.currentHeight-.02 && motor2.currentHeight > targetHeight){
				motor2.sniperToHeight(targetHeight);
				motor1.goToHeight(targetHeight);
				System.out.println("Case 4");
				SmartDashboard.putNumber("Case", 4);
			}
	}
/*	public void sniperToHeight(double targetHeight){
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
	}*/
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
//	public void sniperDown(){
//		sniperToHeight(0);
//	}
//	public void sniperUp(){
//		sniperToHeight(motor1.travel);
//	}
	public double getHeight(){
		height = motor1.currentHeight;
		return height;
	}

}
package org.usfirst.frc.team3373.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;;

public class HawkActuator extends HawkSuperMotor  {
	double maxPotValue;
	double minPotValue;
	int targetPotPos;
	double range;
	double currentActuatorHeight;
	double TargetHeight;
	public HawkActuator(int actuatorID,double actuatorMaxPotValue ,double actuatorMinPotValue, double maxSpeedChange, int limitSwitchForwID, int limitSwitchRevID){
		super(actuatorID, 0, 0, 100, 0, 5.625,maxSpeedChange, -1, limitSwitchForwID ,limitSwitchRevID);
		maxPotValue = actuatorMaxPotValue;
		minPotValue = actuatorMinPotValue;
		range = actuatorMaxPotValue-actuatorMinPotValue;
	}
	public void set(double speed){
		System.out.println(getAnalogInRaw());
		if(getAnalogInRaw() > maxPotValue - 5){
			super.set(-.1);
			}
		else if(getAnalogInRaw() < minPotValue + 5){
			super.set(.1);
			}
		else{
			super.set(speed);
			}
	}
	public double goToHeight(double targetHeight){
		double range1 = range/5.625;
		double range2 = range1 * targetHeight;
		double range3 = range2 + minPotValue;
		targetPotPos = (int) range3;
		if(getAnalogInRaw()<minPotValue-10){                             //Prevents the motor from hitting or passing its lower limit
			setScaled(.2);
			System.out.println("reached lower limit. Speed : " + getSpeed());
		}else if(getAnalogInRaw()>maxPotValue+10){                       //Prevents the motor from hitting or passing its upper limit
			setScaled(-.2);
			System.out.println("reached upper limit. Speed : " + getSpeed());
		}else if(getAnalogInRaw()>targetPotPos+3 && getAnalogInRaw() <targetPotPos+25){
			setScaled(-.1);
			System.out.println("slowing while going down");
		}else if(getAnalogInRaw()>targetPotPos+25){
			setScaled(-.5);
			System.out.println("going down");
		}else if(getAnalogInRaw()<targetPotPos-3 && getAnalogInRaw() > targetPotPos-25){
			setScaled(.2);
			System.out.println("Slowing whole going up.");
		}else if(getAnalogInRaw()< targetPotPos-25){
			setScaled(.25);
			System.out.println("Going down.");
		}else{
			setScaled(0);
			System.out.println("Stopping.");
		}
		currentHeight = (getAnalogInRaw() - minPotValue)/ range;
	/*	System.out.println("Target pot pos:" + targetPotPos);
		System.out.println("range1: "+ range1);
		System.out.println("range2: "+ range2);
		System.out.println("range3: "+ range3);*/
		TargetHeight = targetHeight;
		return targetPotPos;
	}
	public double sniperToHeight(double targetHeight){
		double range1 = range/5.625;
		double range2 = range1 * targetHeight;
		double range3 = range2 + minPotValue;
		targetPotPos = (int) range3;
		if(getAnalogInRaw()<minPotValue-10){                             //Prevents the motor from hitting or passing its lower limit
			setScaled(.1);
			System.out.println("reached lower limit.");
		}else if(getAnalogInRaw()>maxPotValue+10){                       //Prevents the motor from hitting or passing its upper limit
			setScaled(-.1);
			System.out.println("reached upper limit");
		}else if(getAnalogInRaw()>targetPotPos+3 && getAnalogInRaw() <targetPotPos+25){
			setScaled(-.1);
			System.out.println("slowing while going down");
		}else if(getAnalogInRaw()>targetPotPos+25){
			setScaled(-.25);
			System.out.println("going down");
		}else if(getAnalogInRaw()<targetPotPos-3 && getAnalogInRaw() > targetPotPos-25){
			setScaled(.1);
			System.out.println("Slowing whole going up.");
		}else if(getAnalogInRaw()< targetPotPos-25){
			setScaled(.25);
			System.out.println("Going down.");
		}else{
			setScaled(0/2);
			System.out.println("Stopping.");
		}
		currentHeight = (getAnalogInRaw() - minPotValue)/ range;
		System.out.println("Target pot pos:" + targetPotPos);
		System.out.println("range1: "+ range1);
		System.out.println("range2: "+ range2);
		System.out.println("range3: "+ range3);
		return targetPotPos;
	}
	public double getHeight(){
		currentActuatorHeight = ((getAnalogInRaw() - minPotValue)/range) * 5.625;
		return currentActuatorHeight;
	}
	public int getTargetPotPos(){
		return targetPotPos;
	}
	public double getDistanceFromHeight(){
		double DistanceFromHeight = TargetHeight - getHeight();
		return DistanceFromHeight;
	}
}


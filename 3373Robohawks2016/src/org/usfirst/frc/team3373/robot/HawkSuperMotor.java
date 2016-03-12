package org.usfirst.frc.team3373.robot;
// AUTHOR Alex Iasso
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;

public class HawkSuperMotor extends CANTalon {
	double range;
	double rangeMin;
	double rangeMax;
	double travel;
	double targetEncoderPos;
	double currentHeight;           //How far through of its travel it is
	double currentEncHeight;
	int maxPercentSpeed;
	int minPercentSpeed;
	double maxDelta;
	double currentSpeed;
	int motorDirection;
	DigitalInput limitSwitchRev;
	DigitalInput limitSwitchForw;
	int revLimitSwitchID;
	int forwLimitSwitchID;
	double TargetHeight;
	//max speed change = maximum speed change between iterations
	public HawkSuperMotor(int deviceNumber, int encoderMin, int encoderMax,int maxPercent, int minPercent, double travelRange, double maxSpeedChange, int motorDirection1, int limitSwitchForwID,int limitSwitchRevID ) {
		super(deviceNumber);
		double encoderRange = encoderMax-encoderMin;
		range = encoderRange;
		rangeMin = encoderMin;
		rangeMax = encoderMax;
		travel = travelRange;
		maxDelta = maxSpeedChange;
		currentSpeed = 0.0;
		maxPercentSpeed = maxPercent;
		minPercentSpeed = minPercent;
		currentHeight = ((getEncPosition()/range)*travel);
		currentEncHeight = (getEncPosition()/range);
		motorDirection = motorDirection1;
		forwLimitSwitchID = limitSwitchForwID;
		revLimitSwitchID = limitSwitchRevID;
		if(limitSwitchForwID >= 0){
			limitSwitchForw = new DigitalInput(limitSwitchForwID);
		}
		if(limitSwitchRevID >=0){
			limitSwitchRev = new DigitalInput(limitSwitchRevID);
		}
	}
	public void setScaled(double speed){
		set(speed*(maxPercentSpeed/100));
	//	set(speed*((maxPercentSpeed-minPercentSpeed)/100));
	}
	public double goToHeight(double targetHeight){
		double range1 = range/travel;
		double range2 = range1 * targetHeight;
		targetEncoderPos = (int) range2;
		System.out.println("target, mon!: " + targetEncoderPos);
		System.out.println("current, mon!: " + getEncPosition());
		if(Math.abs(getEncPosition())<Math.abs(rangeMin)+100){                             //Prevents the motor from hitting or passing its lower limit
			setScaled(.2);
			System.out.println("reached lower limit. Speed : " + getSpeed());
		}else if(Math.abs(getEncPosition())>Math.abs(rangeMax)-100){                       //Prevents the motor from hitting or passing its upper limit
			setScaled(-.2);
			System.out.println("reached upper limit. Speed : " + getSpeed());
		}else if(Math.abs(getEncPosition())>Math.abs(targetEncoderPos)+40 && Math.abs(getEncPosition()) <Math.abs(targetEncoderPos)+250){
			setScaled(-.2);
			System.out.println("slowing while going down");
		}else if(Math.abs(getEncPosition())>Math.abs(targetEncoderPos)+250){
			setScaled(-.5);
			System.out.println("going down");
		}else if(Math.abs(getEncPosition())<Math.abs(targetEncoderPos)-40 && Math.abs(getEncPosition()) > Math.abs(targetEncoderPos)-250){
			setScaled(.2);
			System.out.println("Slowing whole going up.");
		}else if(Math.abs(getEncPosition())< Math.abs(targetEncoderPos)-250){
			setScaled(.5);
			System.out.println("Going up.");
		}else{
			setScaled(0);
			System.out.println("Stopping.");
		}
		currentHeight = Math.abs(getEncPosition())/ range;
	/*	System.out.println("Target pot pos:" + targetEncoderPos);
		System.out.println("range1: "+ range1);
		System.out.println("range2: "+ range2);
		System.out.println("range3: "+ range3);*/
		TargetHeight = targetHeight;
		return targetEncoderPos;

	}
	public void initDown(){
		targetEncoderPos = (targetEncoderPos-50);
		if(Math.abs(getEncPosition())>Math.abs(targetEncoderPos)+40 && Math.abs(getEncPosition()) <Math.abs(targetEncoderPos)+250){
			setScaled(-.2);
			System.out.println("slowing while going down");
		}else if(Math.abs(getEncPosition())>Math.abs(targetEncoderPos)+250){
			setScaled(-.5);
			System.out.println("going down");
		}else if(Math.abs(getEncPosition())<Math.abs(targetEncoderPos)-40 && Math.abs(getEncPosition()) > Math.abs(targetEncoderPos)-250){
			setScaled(.2);
			System.out.println("Slowing whole going up.");
		}else if(Math.abs(getEncPosition())< Math.abs(targetEncoderPos)-250){
			setScaled(.5);
			System.out.println("Going up.");
		}else{
			setScaled(0);
			System.out.println("Stopping.");
		}
	}
	public double sniperToHeight(double targetHeight){
		targetEncoderPos = (range/travel) * targetHeight;
		if(getEncPosition()<rangeMin){                             //Prevents the motor from hitting or passing its lower limit
			setScaled(.05);
		}else if(getEncPosition()>rangeMax){                       //Prevents the motor from hitting or passing its upper limit
			setScaled(-.05);
		}else if(getEncPosition()>targetEncoderPos+30 && getEncPosition() <targetEncoderPos+50){
			setScaled(-.05);
		}else if(getEncPosition()>targetEncoderPos+30){
			setScaled(-.25);
		}else if(getEncPosition()<targetEncoderPos-30 && getEncPosition() > targetEncoderPos-50){
			setScaled(.05);
		}else if(getEncPosition()< targetEncoderPos-30){
			setScaled(.25);
		}else{
			setScaled(0);
		}
		return targetEncoderPos;
	}
/*	public double goDistance(double targetDistance){
		targetEncoderPos = getEncPosition()+ (range/travel*targetDistance);
		if(getEncPosition()<targetEncoderPos+500){
			setScaled(-.5);
		}else if(getEncPosition()>targetEncoderPos-500){
			setScaled(.5);
		}else{
			setScaled(0);
		}
		return targetEncoderPos;
		
	} */
	@Override
	public void set(double speed){
		/*
		if(forwLimitSwitchID >= 0){
			if(!limitSwitchForw.get()){
				accelerationSet(speed);
				}
			else if(limitSwitchForw.get() && speed <0){
				accelerationSet(speed);
			}else{
				super.set(0);
			}
		}else{
			accelerationSet(speed);
		}*/
		if(revLimitSwitchID >= 0){
			if(!limitSwitchRev.get()){
			accelerationSet(speed);
			}
			else if(limitSwitchRev.get() && speed >0){
				accelerationSet(speed);
			}else{
				super.set(0);
			}
		}else{
			accelerationSet(speed);
		}
		
	}
	

	public void accelerationSet(double speed){
		//If the change in speed is greater than max delta, reduce the change to max delta
		double currentDelta = Math.abs(currentSpeed - speed);
		if(currentDelta > maxDelta){
			if(speed > currentSpeed){
				speed = currentSpeed + maxDelta;
			}
			else{
				speed = currentSpeed - maxDelta;
			}
		}
	//	System.out.println("Speed:" + speed);
		currentSpeed = speed;
		super.set(speed * motorDirection);
	}
	public void resetCurrentSpeed(){
		currentSpeed = 0.0;
	}
	public double getSpeed(){
		return currentSpeed;
	}
	public void emergencyMotorStop(){
		super.set(0 * motorDirection);
	}
	public double getCurrentHeight(){
		return currentHeight;
	}

}

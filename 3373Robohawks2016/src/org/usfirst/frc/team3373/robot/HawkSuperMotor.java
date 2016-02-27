package org.usfirst.frc.team3373.robot;
// AUTHOR Alex Iasso
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;

public class HawkSuperMotor extends CANTalon {
	double range = 12;
	double rangeMin;
	double rangeMax;
	double travel;
	double targetEncoderPos;
	double currentHeight = 1;           //How far out of its travel it is
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
		set(speed*(maxPercentSpeed/100) * motorDirection);
	//	set(speed*((maxPercentSpeed-minPercentSpeed)/100));
	}
	public double goToHeight(double targetHeight){
		targetEncoderPos = (range/travel) * targetHeight;
		if(getEncPosition()<rangeMin-15){                             //Prevents the motor from hitting or passing its lower limit
			setScaled(-.1* motorDirection);
		}else if(getEncPosition()>rangeMax+15){                       //Prevents the motor from hitting or passing its upper limit
			setScaled(.1 * motorDirection);
		}else if(getEncPosition()<targetEncoderPos+30 && getEncPosition() >targetEncoderPos+50){
			setScaled(.1 * motorDirection);
		}else if(getEncPosition()>targetEncoderPos+30){
			setScaled(.5 * motorDirection);
		}else if(getEncPosition()>targetEncoderPos-30 && getEncPosition() < targetEncoderPos-50){
			setScaled(-.1 * motorDirection);
		}else if(getEncPosition()< targetEncoderPos-30){
			setScaled(-.5 * motorDirection);
		}else{
			setScaled(0 * motorDirection);
		}
		return targetEncoderPos;
	}
	public double sniperToHeight(double targetHeight){
		targetEncoderPos = (range/travel) * targetHeight;
		if(getEncPosition()<rangeMin-1){                             //Prevents the motor from hitting or passing its lower limit
			setScaled(-.1* motorDirection);
		}else if(getEncPosition()>rangeMax+1){                       //Prevents the motor from hitting or passing its upper limit
			setScaled(.1 * motorDirection);
		}else if(getEncPosition()<targetEncoderPos+15 && getEncPosition() >targetEncoderPos+25){
			setScaled(.1 * motorDirection);
		}else if(getEncPosition()>targetEncoderPos+15){
			setScaled(.25 * motorDirection);
		}else if(getEncPosition()>targetEncoderPos-15 && getEncPosition() < targetEncoderPos-25){
			setScaled(-.1 * motorDirection);
		}else if(getEncPosition()< targetEncoderPos-15){
			setScaled(-.25 * motorDirection);
		}else{
			setScaled(0 * motorDirection);
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

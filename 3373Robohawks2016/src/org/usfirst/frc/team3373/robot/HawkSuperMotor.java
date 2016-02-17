package org.usfirst.frc.team3373.robot;
// AUTHOR Alex Iasso
import edu.wpi.first.wpilibj.CANTalon;

public class HawkSuperMotor extends CANTalon {
	double range;
	double rangeMin;
	double rangeMax;
	double travel;
	double targetEncoderPos;
	double currentEncHeight;           //How far out of its travel it is
	int maxPercentSpeed;
	int minPercentSpeed;
	double maxDelta;
	double currentSpeed;
	//max speed change = maximum speed change between iterations
	public HawkSuperMotor(int deviceNumber, int encoderMin, int encoderMax,int maxPercent, int minPercent, double travelRange, double maxSpeedChange) {
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
		currentEncHeight = (getEncPosition()/range);
	}
	public void setScaled(double speed){
		set(speed*(maxPercentSpeed/100));
	//	set(speed*((maxPercentSpeed-minPercentSpeed)/100));
	}
	public double goToHeight(double targetHeight){
		targetEncoderPos = (range/travel) * targetHeight;
		if(getEncPosition()<rangeMin-1){                             //Prevents the motor from hitting or passing its lower limit
			setScaled(-.1);
		}else if(getEncPosition()>rangeMax+1){                       //Prevents the motor from hitting or passing its upper limit
			setScaled(.1);
		}else if(getEncPosition()<targetEncoderPos+300 && getEncPosition() >targetEncoderPos+50){
			setScaled(.1);
		}else if(getEncPosition()>targetEncoderPos+300){
			setScaled(.5);
		}else if(getEncPosition()>targetEncoderPos-300 && getEncPosition() < targetEncoderPos-50){
			setScaled(-.1);
		}else if(getEncPosition()< targetEncoderPos-300){
			setScaled(-.5);
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
		//If the change in speed is greater than max delta, reduce the change to max delta
		double currentDelta = Math.abs(currentSpeed - speed);
//		System.out.println(currentSpeed + " " + currentDelta);
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
		super.set(speed);
	}
	public void resetCurrentSpeed(){
		currentSpeed = 0.0;
	}
	public double getSpeed(){
		return currentSpeed;
	}

}

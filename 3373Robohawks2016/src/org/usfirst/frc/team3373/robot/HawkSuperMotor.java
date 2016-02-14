package org.usfirst.frc.team3373.robot;
// AUTHOR Alex Iasso
import edu.wpi.first.wpilibj.CANTalon;

public class HawkSuperMotor extends CANTalon {
	double range;
	double rangeMin;
	double rangeMax;
	double targetEncoderPos;
	int maxPercentSpeed;
	int minPercentSpeed;
	double maxDelta;
	double currentSpeed;
	//max speed change = maximum speed change between iterations
	public HawkSuperMotor(int deviceNumber, int encoderMin, int encoderMax, double maxSpeedChange) {
		super(deviceNumber);
		double encoderRange = encoderMax-encoderMin;
		range = encoderRange;
		rangeMin = encoderMin;
		rangeMax = encoderMax;
		maxDelta = maxSpeedChange;
		currentSpeed = 0.0;
		//maxPercentSpeed = maxPercent;
		//minPercentSpeed = minPercent;
	}
	public void setScaled(double speed){
		set(speed*(maxPercentSpeed/100));
	//	set(speed*((maxPercentSpeed-minPercentSpeed)/100));
	}
	public double goToHeight(double targetHeight){
		targetEncoderPos = (range/12) * targetHeight;
		if(getEncPosition()<rangeMin-1){                             //Prevents the motor from hitting or passing it's lower limit
			setScaled(.1);
		}else if(getEncPosition()>rangeMax+1){                       //Prevents the motor from hitting or passing it's upper limit
			setScaled(-.1);
		}else if(getEncPosition()>targetEncoderPos+50){
			setScaled(-.5);
		}else if(getEncPosition()<targetEncoderPos-50){
			setScaled(.5);
		}else{
			setScaled(0);
		}
		return targetEncoderPos;
	}
	public double goDistance(double targetDistance){
		targetEncoderPos = getEncPosition()+ (range/12*targetDistance);
		if(getEncPosition()<targetEncoderPos+500){
			setScaled(-.5);
		}else if(getEncPosition()>targetEncoderPos-500){
			setScaled(.5);
		}else{
			setScaled(0);
		}
		return targetEncoderPos;
		
	} 
	@Override
	public void set(double speed){
		//If the change in speed is greater than max delta, reduce the change to max delta
		double currentDelta = Math.abs(currentSpeed - speed);
		System.out.println(currentSpeed + " " + currentDelta);
		if(currentDelta > maxDelta){
			if(speed > currentSpeed){
				speed = currentSpeed + maxDelta;
			}
			else{
				speed = currentSpeed - maxDelta;
			}
		}
		System.out.println("Speed:" + speed);
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

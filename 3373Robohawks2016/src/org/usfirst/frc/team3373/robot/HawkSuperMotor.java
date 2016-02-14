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
	public HawkSuperMotor(int deviceNumber, int encoderMin, int encoderMax, int maxPercent, int minPercent, double travelRange) {
		super(deviceNumber);
		double encoderRange = encoderMax-encoderMin;
		range = encoderRange;
		rangeMin = encoderMin;
		rangeMax = encoderMax;
		travel = travelRange;
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
		}else if(getEncPosition()>targetEncoderPos+50){
			setScaled(.5);
		}else if(getEncPosition()<targetEncoderPos-50){
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
		
	}*/

}

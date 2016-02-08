package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class HawkSuperMotor extends CANTalon {
	double range;
	double rangeMin;
	double rangeMax;
	double targetEncoderPos;
	public HawkSuperMotor(int deviceNumber, int encoderMin, int encoderMax) {
		super(deviceNumber);
		double encoderRange = encoderMax-encoderMin;
		range = encoderRange;
		rangeMin = encoderMin;
		rangeMax = encoderMax;
	}
	public double goToHeight(double targetHeight){
		targetEncoderPos = (range/12) * targetHeight;
		if(getEncPosition()<rangeMin+200){                             //Prevents the motor from hitting or passing it's lower limit
			set(.1);
		}else if(getEncPosition()>rangeMax-200){                       //Prevents the motor from hitting or passing it's upper limit
			set(-.1);
		}else if(getEncPosition()>targetEncoderPos+50){
			set(-.5);
		}else if(getEncPosition()<targetEncoderPos-50){
			set(.5);
		}else{
			set(0);
		}
		return targetEncoderPos;
	}

}

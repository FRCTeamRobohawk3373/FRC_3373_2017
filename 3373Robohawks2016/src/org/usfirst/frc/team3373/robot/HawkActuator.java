package org.usfirst.frc.team3373.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;;

public class HawkActuator extends HawkSuperMotor  {
	double maxPotValue;
	double minPotValue;
	int targetPotPos;
	public HawkActuator(int actuatorID,double actuatorMaxPotValue ,double actuatorMinPotValue, double maxSpeedChange, int limitSwitchForwID, int limitSwitchRevID){
		super(actuatorID, 0, 0, 0, 0, 6,maxSpeedChange, 1, limitSwitchForwID ,limitSwitchRevID);
		maxPotValue = actuatorMaxPotValue;
		minPotValue = actuatorMinPotValue;
	}
	public void set(double speed){
		System.out.println(getAnalogInRaw());
		if(getAnalogInRaw() > maxPotValue){
			super.set(-1);
			}
		if(getAnalogInRaw() < minPotValue){
			super.set(1);
			}
		else{
			super.set(speed);
			}
	}
	public double goToHeight(double targetHeight){
		targetPotPos = (int) ((maxPotValue/6) * targetHeight);
		if(getEncPosition()<rangeMin){                             //Prevents the motor from hitting or passing its lower limit
			setScaled(.1);
		}else if(getEncPosition()>rangeMax){                       //Prevents the motor from hitting or passing its upper limit
			setScaled(-.1);
		}else if(getEncPosition()>targetPotPos+3 && getEncPosition() <targetPotPos+5){
			setScaled(-.1);
		}else if(getEncPosition()>targetPotPos+3){
			setScaled(-.5);
		}else if(getEncPosition()<targetPotPos-3 && getEncPosition() > targetPotPos-5){
			setScaled(.1);
		}else if(getEncPosition()< targetPotPos-3){
			setScaled(.5);
		}else{
			setScaled(0);
		}
		return targetHeight;
	}
}


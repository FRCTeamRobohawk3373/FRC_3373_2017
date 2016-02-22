package org.usfirst.frc.team3373.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;;

public class HawkActuator extends HawkSuperMotor  {
	double maxPotValue;
	double minPotValue;
	public HawkActuator(int actuatorID,double actuatorMaxPotValue ,double actuatorMinPotValue, double maxSpeedChange, int limitSwitchForwID, int limitSwitchRevID){
		super(actuatorID,0, 0,0,0,0,maxSpeedChange, 1,limitSwitchForwID ,limitSwitchRevID);
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
}


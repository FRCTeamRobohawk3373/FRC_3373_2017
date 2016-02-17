package org.usfirst.frc.team3373.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;;

public class HawkActuator extends HawkSuperMotor  {
	double maxPotValue;
	double minPotValue;
	AnalogInput pot;
	public HawkActuator(int actuatorID,double actuatorMaxPotValue ,double actuatorMinPotValue, double maxSpeedChange,int potID, int bottomLimitSwitchID, int topLimitSwitchID){
		super(actuatorID,0, 0,0,0,0,maxSpeedChange);
		maxPotValue = actuatorMaxPotValue;
		minPotValue = actuatorMinPotValue;
		pot = new AnalogInput(potID);
	}
	public void set(double speed){
		System.out.println(pot.getValue());
		if(pot.getValue() > maxPotValue){
			super.set(-1);
			}
		if(pot.getValue() < minPotValue){
			super.set(1);
			}
		else{
			super.set(speed);
			}
	}
}


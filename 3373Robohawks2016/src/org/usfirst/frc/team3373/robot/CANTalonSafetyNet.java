package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class CANTalonSafetyNet{
	boolean isFound = true;
	CANTalon talon;
	public CANTalonSafetyNet(int port){
		try{
		talon = new CANTalon(port);
		}catch(Exception e){
			isFound = false;
		}
	}
	public int getAnalogInRaw(){
		if(isFound)
		return talon.getAnalogInRaw();
		else
			return 42; //It is the answer
	}
	public double getOutputCurrent(){
		if(isFound)
			return talon.getOutputCurrent();
		else
			return 42;
	}
	public void set(double speed){
		if(isFound)
			talon.set(speed);
	}
	public void changeControlMode(TalonControlMode mode){
		if(isFound)
			talon.changeControlMode(mode);
	}
	public void disable(){
		if(isFound)
			talon.disable();
	}
	public void enable(){
		if(isFound)
			talon.enable();
	}
	public void enableBrakeMode(boolean brake){
		if(isFound)
			talon.enableBrakeMode(brake);
	}
	public boolean isRevLimitSwitchClosed(){
		if(isFound)
			return talon.isRevLimitSwitchClosed();
		else
			return false;
	}
	public boolean isFwdLimitSwitchClosed(){
		if(isFound)
			return talon.isFwdLimitSwitchClosed();
		else
			return false;
	}
	public void setPID(double p, double i, double d){
		if(isFound)
			talon.setPID(p, i, d);
		
	}
	public void setFeedbackDevice(FeedbackDevice device){
		if(isFound)
			talon.setFeedbackDevice(device);
	}
	public double get(){
		if(isFound)
			return talon.get();
		else
			return 42;
	}
	public void enableLimitSwitch(boolean forward, boolean reverse){
		if (isFound)
			talon.enableLimitSwitch(forward, reverse);
	}
}

package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class CANTalonSafetyNet {
	boolean isFound = true;
	CANTalon talon;
	double maxDelta;
	double currentSpeed;

	public CANTalonSafetyNet(int port, double maxDel) {
		try {
			talon = new CANTalon(port);
		} catch (Exception e) {
			isFound = false;
		}
		maxDelta = maxDel;
		currentSpeed = 0;
	}

	public int getAnalogInRaw() {
		if (isFound)
			try {
				return talon.getAnalogInRaw();
			} catch (Exception e) {
				return 42;
			}
		else
			return 42; // It is the answer
	}

	public double getOutputCurrent() {
		if (isFound)
			return talon.getOutputCurrent();
		else
			return 42;
	}

	public void set(double speed) {
		if (isFound)
			talon.set(speed);
	}

	public void changeControlMode(TalonControlMode mode) {
		if (isFound)
			talon.changeControlMode(mode);
	}

	public void disable() {
		if (isFound)
			talon.disable();
	}

	public void enable() {
		if (isFound)
			talon.enable();
	}

	public void enableBrakeMode(boolean brake) {
		if (isFound)
			talon.enableBrakeMode(brake);
	}

	public boolean isRevLimitSwitchClosed() {
		if (isFound)
			return talon.isRevLimitSwitchClosed();
		else
			return false;
	}

	public boolean isFwdLimitSwitchClosed() {
		if (isFound)
			return talon.isFwdLimitSwitchClosed();
		else
			return false;
	}

	public void setPID(double p, double i, double d) {
		if (isFound)
			talon.setPID(p, i, d);

	}

	public void setFeedbackDevice(FeedbackDevice device) {
		if (isFound)
			talon.setFeedbackDevice(device);
	}

	public double get() {
		if (isFound)
			return talon.get();
		else
			return 42;
	}

	public void enableLimitSwitch(boolean forward, boolean reverse) {
		if (isFound)
			talon.enableLimitSwitch(forward, reverse);
	}

	public double getOutputVoltage() {
		if (isFound)
			return talon.getOutputVoltage();
		else
			return 42;
	}

	public void accelerate(double speed) {
		if (isFound) {
			double currentDelta = Math.abs(currentSpeed - speed);
			if (currentDelta > maxDelta) {
				if (speed > currentSpeed) {
					speed = currentSpeed + maxDelta;
				} else {
					speed = currentSpeed - maxDelta;
				}
			}
			// System.out.println("Speed:" + speed);
			currentSpeed = speed;
			this.set(speed);
		}
	}
	public void setVoltageCompensationRampRate(double r8){ //r8 == rate
		if(isFound){
			talon.setVoltageCompensationRampRate(r8);
		}
	}
	public void setEncPosition(int position){
		if(isFound){
			talon.setEncPosition(position);
		}
	}
	public int getEncPosition(){
		if(isFound){
		return talon.getEncPosition();
		}else{
			return 42;
		}
	}
}

package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.*;

public class PIDInputObject implements PIDSource {
	
	private double value = 0;
	
	public void setValue(double input){
		value = input;
	}
	
	public double pidGet(){
		return value;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}
}

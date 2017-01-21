package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.PIDOutput;

public class PIDOutputObject implements PIDOutput{
	private double value;
	
	public void pidWrite(double output){
		value = output;
	}
	
	public double getPIDValue(){
		return value;
	}
	
}
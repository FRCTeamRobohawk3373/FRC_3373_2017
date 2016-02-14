package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HawkCalibration {
	
	static int rangeMin;
	static int rangeMax;
	
	static double calibrationLeftY;
	
	static int ID;
	
	public static void main(String[] args) {
	}
	public static void calibrate(int id){
		ID = id;
		CANTalon calibMotor = new CANTalon(ID);
		
		SmartDashboard.putNumber("RangeMin: ", rangeMin);
		SmartDashboard.putNumber("RangeMax: ", rangeMax);
//		SmartDashboard.putNumber("Encoder Position: ", calibMotor.getEncPosition());
		SmartDashboard.putNumber("Range: ", rangeMax - rangeMin);
		
		if(Robot.calibrator.isAPushed()){
			calibMotor.set(0);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			calibMotor.setPosition(0);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rangeMin = calibMotor.getEncPosition();
			Robot.calibrator.clearA();
		}
		if(Robot.calibrator.isYPushed()){
			calibMotor.set(0);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rangeMax = calibMotor.getEncPosition();
			Robot.calibrator.clearY();
		}
		
		
		if(Robot.calibrator.getRawAxis(Robot.LY) >-0.1 && Robot.calibrator.getRawAxis(Robot.LY)<0.1){
    		calibrationLeftY = 0;
    	}else{
    		calibrationLeftY = Robot.calibrator.getRawAxis(Robot.LY);
    	}
		calibMotor.set(calibrationLeftY/8);
		
		
		if(Robot.calibrator.isStartPushed()){
			CalibrationPrinter.main(null);
			Robot.calibrator.clearButtons();
		}
		if(Robot.calibrator.isBackPushed()){
			CalibrationReader.main(null);
			Robot.calibrator.clearButtons();
		}		
		
		

	}

}

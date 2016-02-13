package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HawkCalibration {

	public static void main(String[] args) {
		SmartDashboard.putNumber("Encoder Value: ", Robot.motor3.getEncPosition());
		HawkDrive.main(null);
		while(Robot.motor3.isRevLimitSwitchClosed()){
			Robot.motor3.set(-1);
		}
		Robot.motor3.set(0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		Robot.motor3.setEncPosition(0);
		Robot.motor3.setPosition(0);
		Robot.motor3.setAnalogPosition(0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		SmartDashboard.putNumber("Encoder Value: ", Robot.motor3.getEncPosition());
		double reverseLimit = Robot.motor3.getEncPosition();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(Robot.motor3.isFwdLimitSwitchClosed()){
			Robot.motor3.set(1);
		}
		Robot.motor3.set(0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SmartDashboard.putNumber("Encoder Value: ", Robot.motor3.getEncPosition());
		double forwardLimit = Robot.motor3.getEncPosition();                              //Spinning forwards gives reverse limit, because it is inverted
		double fullEncRange = forwardLimit - reverseLimit;
		SmartDashboard.putNumber("Forwards Encoder Limit: ", forwardLimit);
		SmartDashboard.putNumber("Reverse Encoder Limit: ", reverseLimit);
		SmartDashboard.putNumber("Full Encoder Range: ", fullEncRange);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

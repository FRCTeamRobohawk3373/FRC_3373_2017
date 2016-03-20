package org.usfirst.frc.team3373.robot;
import edu.wpi.first.wpilibj.CANTalon;

public class HawkShooterAim {
	private CANTalon shooterAimMotor;
	private int lowBarAngle = 15; //to be tested and changed if needed
	private int generalBreachAngle = 40;
	private int maxEncValue = 972; //from calibration max Enc value and value at 60 degrees
	int targetEncPos;
	public HawkShooterAim(int id, double p, double i, double d){
		shooterAimMotor = new CANTalon(id);
		shooterAimMotor.setPID(p, i, d);
		shooterAimMotor.changeControlMode(CANTalon.TalonControlMode.Position);
        shooterAimMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        shooterAimMotor.enableLimitSwitch(false, false);
        shooterAimMotor.enableBrakeMode(true);
        targetEncPos = shooterAimMotor.getEncPosition();
	}
	
	public void setTargetAngle(int angle){
		if(angle >60){
			angle = 60;
		}
		else if(angle <0){
			angle = 0;
		}
		int targetEncPosition = maxEncValue / 60 * angle; //maxEncValue / 60 gives number of Enc units per angle
		shooterAimMotor.set(targetEncPosition);
		System.out.println("Target Encodawg Position: " + targetEncPosition);
		System.out.println("Current Encodawg Position: " + shooterAimMotor.getEncPosition());
	}
	
	public int getCurrentAngle(){
		int currentAngle = shooterAimMotor.getEncPosition() / (maxEncValue / 60); //maxEncValue / 60 gives number of Enc units per angle
		return currentAngle;
	}
	public void relativeChangeTargetAngle(int changeInAngle){
		setTargetAngle(getCurrentAngle() + changeInAngle);
	}
	public void relativeChangeTargetEncValue(int changeInEnc){
		targetEncPos = targetEncPos + changeInEnc;
		if(targetEncPos >maxEncValue){
			targetEncPos = maxEncValue;
		}else if(targetEncPos <0){
			targetEncPos = 0;
		}
		System.out.println("Target Encodawg Position: " + targetEncPos);
		System.out.println("Current Encodawg Position: " + shooterAimMotor.getEncPosition());
		shooterAimMotor.set(targetEncPos);
	}
	public void goToBreachLowBarPosition(){
		setTargetAngle(lowBarAngle);
	}
	public void goToGeneralBreachAngle(){
		setTargetAngle(generalBreachAngle);
	}
	public void manualShooterUp(){
		relativeChangeTargetEncValue(8);
	}
	public void manualShooterDown(){
		relativeChangeTargetEncValue(-8);
	}
	public void setCurrentPosition(){
		shooterAimMotor.set(shooterAimMotor.getEncPosition());
	}
}
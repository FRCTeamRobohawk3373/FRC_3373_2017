package org.usfirst.frc.team3373.robot;
public class HawkShooter {
	HawkSuperMotor shooterMain = new HawkSuperMotor(5,0,0,0,0,0,.1);
	HawkSuperMotor shooterControl = new HawkSuperMotor(6,0,0,0,0,0,.1);
	//HawkActuator shooterAimer = new HawkSuperMotor(14,0,0,0,0,0,.1);
	HawkVision hawkVision = new HawkVision();
	private void goToAngle(double angle){
		//go to angle based off pot value
	}
	public void  aimShooter(){
		//get distance from vision System
		//calculate angle based on distance
		//call go to angle to move to angle
	}
	public void shoot(){
		//start up shooterMain motor to maximum speed
		//run shooter control motor forward to release ball
		
	}
		
	public void takeInBall(){
		//angle down into pickup position
		goToAngle(0);
		//spin both motors backwards to pull in ball
		if(shooterControl.isRevLimitSwitchClosed()){
		shooterMain.set(-.5);
		shooterControl.set(-.5);
		}
		//stop both motors when shooter control limit switch is hit
		else{
			shooterMain.set(0);
			shooterControl.set(0);
		}
		
	}
}

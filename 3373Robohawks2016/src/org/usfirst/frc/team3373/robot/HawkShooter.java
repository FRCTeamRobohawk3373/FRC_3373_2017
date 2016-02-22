package org.usfirst.frc.team3373.robot;
public class HawkShooter {
	HawkSuperMotor shooterMain = new HawkSuperMotor(5,0,0,0,0,0,.1, 1);
	HawkSuperMotor shooterControl = new HawkSuperMotor(6,0,0,0,0,0,1, 1);
	HawkActuator shooterAimer = new HawkActuator(14,0,0,.1); //still need to test for max and min pot values.
	HawkVision hawkVision = new HawkVision();
	public void goToAngle(double angle){
		//go to angle based off pot value
	}	
	public void takeInBall(){
		//angle down into pickup position
		goToAngle(0);
		//spin both motors backwards to pull in ball
		if(!shooterControl.isRevLimitSwitchClosed()){
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

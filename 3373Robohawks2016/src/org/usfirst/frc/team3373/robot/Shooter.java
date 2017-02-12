package org.usfirst.frc.team3373.robot;


public class Shooter {
CANTalonSafetyNet shooterMotor;

public Shooter(int shooterPort){
	shooterMotor = new CANTalonSafetyNet(shooterPort);
}



}

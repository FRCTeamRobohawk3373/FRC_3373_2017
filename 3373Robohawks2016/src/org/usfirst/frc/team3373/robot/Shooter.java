package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon.*;

public class Shooter {
CANTalonSafetyNet shooterMotor;
LookupTable lookupTable;
double[] calibrationVoltages;
double[] calibrationDistances;
double distanceToTarget;
double targetVoltage;
public Shooter(int shooterPort){
	shooterMotor = new CANTalonSafetyNet(shooterPort, .05);
	lookupTable = new LookupTable();
	calibrationDistances = new double[]{59.5, 71.5, 125, 137.25, 149, 209}; //independent variable
	calibrationVoltages = new double[]{8.67, 9.1, 10.12, 10.6, 10.6, 11.7};  //dependent variable
	shooterMotor.changeControlMode(TalonControlMode.Voltage);
	shooterMotor.setVoltageCompensationRampRate(12);
	
}
public void determineShooterVoltage(double distance){
	distanceToTarget = distance;
	targetVoltage = lookupTable.lookUpValue(distanceToTarget, calibrationDistances, calibrationVoltages);
}

public double getDistanceToTarget(){
	return distanceToTarget;
}
public double getShooterTargetVoltage(){
	return targetVoltage;
}
public void setShooterMotor(double LX){
	shooterMotor.set(targetVoltage);
}
public void disableShooter(){
	shooterMotor.set(0);
}
public void spinUpShooter(){
	shooterMotor.set(10.4);
}
public void increaseDistanceToTarget(){
	targetVoltage += .05;
}
public void decreaseDistanceToTarget(){
	targetVoltage -= .05;
}
}

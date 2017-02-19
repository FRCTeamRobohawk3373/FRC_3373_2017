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
	calibrationDistances = new double[]{48,72,96,120,144, 192, 240}; //independent variable
	calibrationVoltages = new double[]{7.325,7.7,8.2,8.675,9.45,9.375,10.4,11.6};  //dependent variable
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

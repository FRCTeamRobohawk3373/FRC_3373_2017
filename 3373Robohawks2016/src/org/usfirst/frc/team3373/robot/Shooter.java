package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon.*;

public class Shooter {
CANTalonSafetyNet shooterMotor;
LookupTable lookupTable;
double[] calibrationVoltages;
double[] calibrationDistances;
double distanceToTarget;
double targetVoltage = 0;
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
public void setShooterMotor(){
	shooterMotor.set(targetVoltage);
	System.out.println("Shooter Voltage: " + targetVoltage + "             Actual voltage: " + shooterMotor.get() + "             Delta Voltage: " + (targetVoltage-shooterMotor.get()));
}
public void disableShooter(){
	shooterMotor.set(0);
	targetVoltage = 0;
}
public void spinUpShooter(){
	shooterMotor.set(10.4);
	targetVoltage = 10.4;
}
public void increaseDistanceToTarget(){
	targetVoltage += .025;
}
public void decreaseDistanceToTarget(){
	targetVoltage -= .025;
}
}

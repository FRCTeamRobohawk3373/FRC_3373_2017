package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

import edu.wpi.first.wpilibj.DigitalOutput;

public class Shooter {
CANTalonSafetyNet shooterMotor;
CANTalonSafetyNet indexerMotor;
DigitalOutput rotationSpike;
LookupTable lookupTable;
double[] calibrationVoltages;
double[] calibrationDistances;
double distanceToTarget;
double targetVoltage = 0;
double current;
double previousCurrent;
double spikeCurrentCounter;
boolean isReset;

double indexerSpeedModifier;
double indexerSpeed;
int currentPosition;
int indexerError;
int target;
boolean indexerUp;

int indexerUpPos;
int indexerDownPos;

public Shooter(int shooterPort, int indexerPort, int rotationPort){
	
	shooterMotor = new CANTalonSafetyNet(shooterPort, .05);
	indexerMotor = new CANTalonSafetyNet(indexerPort, .1);
	rotationSpike = new DigitalOutput(rotationPort);
	current = 0;
	previousCurrent = 0;
	spikeCurrentCounter = 0;
	isReset = false;
	lookupTable = new LookupTable();
	calibrationDistances = new double[]{48,72,96,120,144, 192, 240}; //independent variable
	calibrationVoltages = new double[]{7.325,7.7,8.2,8.675,9.45,9.375,10.4,11.6};  //dependent variable
	shooterMotor.changeControlMode(TalonControlMode.Voltage);
	shooterMotor.setVoltageCompensationRampRate(12);
	indexerMotor.changeControlMode(TalonControlMode.Position);
	
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
public void rotateBalls(){
	rotationSpike.set(true);
}
public void stopRotatingBalls(){
	rotationSpike.set(false);
}
public void zeroIndexer(){
	previousCurrent = current;
	current = indexerMotor.getOutputCurrent();
	if (current > 30 && previousCurrent > 30) {
		spikeCurrentCounter++;
	} else if (!isReset) {
		spikeCurrentCounter = 0;
	}
	
	if(spikeCurrentCounter >= 15){
		isReset = true;
	}
	
	if(!isReset){
		indexerMotor.set(-.05);
	}
	
	if(isReset){
		indexerMotor.setEncPosition(0);
	}
}
public void indexBall(){
	if(indexerUp){
		indexerMotor.set(indexerDownPos);
	}
	else{
		indexerMotor.set(indexerUpPos);
	}
}

public void setIndexerSpeed(double speedMod){
	indexerSpeedModifier = speedMod;
	currentPosition = indexerMotor.getAnalogInRaw();
	indexerError=Math.abs(currentPosition-target);
	//System.out.println("Target: " + target + "   Current: " + current + "  Error:" + gearDoorError);

	// x/360*800 where x=degrees
	if (indexerError<10) {  // stop deadband
		indexerSpeed=0;
	//	System.out.print("stop");
	}
	else if (indexerError<80) {  // low speed deadband 
		indexerSpeed=0.1 * indexerSpeedModifier;
	//	System.out.print("slow");
	}
	else { // highs speed mode
		indexerSpeed=0.5 * indexerSpeedModifier; 
	//	System.out.print("fast");
	}

	if (target < current) {
		indexerMotor.set(indexerSpeed);
	}
	else {
		indexerMotor.set(-indexerSpeed);
	}
}
}


package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Relay;

public class Shooter {
CANTalonSafetyNet shooterMotor;
CANTalonSafetyNet indexerMotor;
LookupTable lookupTable;
CANTalonSafetyNet rotationMotor;
AnalogInput potentiometer;
double[] calibrationVoltages;
double[] calibrationDistances;
double distanceToTarget;
double targetVoltage = 0;
double currentEncPosition;
double previousPosition;
double spikeCurrentCounter;
boolean isReset;

double indexerSpeedModifier;
double indexerSpeed;
int currentPosition;
int indexerError;
int target;
boolean indexerUp;

int indexerUpPos = 2300;
int indexerDownPos = 0;

public Shooter(int shooterPort, int indexerPort, int rotationPort, int potPort){
	
	shooterMotor = new CANTalonSafetyNet(shooterPort, .05);
	indexerMotor = new CANTalonSafetyNet(indexerPort, .1);
	rotationMotor = new CANTalonSafetyNet(rotationPort, .1);
	currentEncPosition = 0;
	previousPosition = 0;
	spikeCurrentCounter = 0;
	isReset = false;
	lookupTable = new LookupTable();
	calibrationDistances = new double[]{48,72,96,120,144, 192, 240}; //independent variable
	calibrationVoltages = new double[]{7.325,7.7,8.2,8.675,9.45,9.375,10.4,11.6};  //dependent variable
	shooterMotor.changeControlMode(TalonControlMode.Voltage);
	shooterMotor.setVoltageCompensationRampRate(12);
	indexerMotor.changeControlMode(TalonControlMode.PercentVbus);
	indexerMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	potentiometer = new AnalogInput(potPort);
	
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
public boolean isShooterOn(){
	if(targetVoltage > 5){
		return true;
	}else{
		return false;
	}
}
public void setShooterMotor(){
	shooterMotor.set(targetVoltage);
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
	rotationMotor.set(-.25);
}
public void stopRotatingBalls(){
	rotationMotor.set(0);
}
public void unjam(){
	rotationMotor.set(.05);
}
public void zeroIndexer(){
	if(!isReset){
		indexerMotor.changeControlMode(TalonControlMode.PercentVbus);
	previousPosition = currentEncPosition;
	System.out.println("Current position: " + indexerMotor.getEncPosition());
	currentEncPosition = indexerMotor.getEncPosition();
	if ((previousPosition < currentEncPosition +1) || (previousPosition > currentEncPosition-1)) {
		spikeCurrentCounter++;
	} else if (!isReset) {
		spikeCurrentCounter = 0;
	}
	
	if(spikeCurrentCounter >= 15){
		isReset = true;
	}
	
	if(!isReset){
		indexerMotor.set(-.1);
	}
	
	if(isReset){
		indexerMotor.setEncPosition(-40);
		indexerUp = false;
	}
	}
}
public void indexBall(){
	if(isReset){
		//indexerMotor.changeControlMode(TalonControlMode.Position);
	if(indexerUp){
		target = indexerDownPos;
	}else{
		target = indexerUpPos;
	}
}
}

public void setIndexerSpeed(double speedMod){
	if(isReset){
		indexerSpeedModifier = speedMod;
	
	currentPosition = indexerMotor.getEncPosition();
	indexerError=Math.abs(currentPosition-target);
	//System.out.println("Target: " + target + "   Current: " + current + "  Error:" + gearDoorError);

	// x/360*800 where x=degrees
	if (indexerError<40) {  // stop deadband
		indexerSpeed=0;
		System.out.println("stop");
	}
	else if (indexerError<200) {  // low speed deadband 
		indexerSpeed=0.025 * indexerSpeedModifier;
		System.out.println("slow");
	}
	else if(indexerError < 400){ // highs speed mode
		indexerSpeed=0.1 * indexerSpeedModifier; 
		System.out.println("fast");
	} else { //sanic fast mode
		indexerSpeed = 1 *indexerSpeedModifier;
	}

	if (target < currentPosition) {
		indexerMotor.set(-indexerSpeed);
	}
	else {
		indexerMotor.set(indexerSpeed);
	}
	System.out.println("Indexer target speed: " + indexerSpeed + "                 Indexer actual speed: " + indexerMotor.get() + "              Encoder Valu: " + indexerMotor.getEncPosition());
}}
public void calibrate(double LX){
	indexerMotor.changeControlMode(TalonControlMode.PercentVbus);
	indexerMotor.set(LX);
	System.out.println("Indexer Encoder Position: " + indexerMotor.getEncPosition());
}
public void setGoingUp(){
	target = indexerUpPos;
	indexerUp = true;
}
public void setGoingDown(){
	target = indexerDownPos;
	indexerUp = false;
}
public void usePotInput(){
	target = (int)((460) * potentiometer.getVoltage());
}
public boolean isIndexerUp(){
	if(indexerUp){
		return true;
	}
	else {
		return false;
	}
}
}


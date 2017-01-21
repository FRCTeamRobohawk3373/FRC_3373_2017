package org.usfirst.frc.team3373.robot;

public class SwerveAlign {
	
	static boolean leftFrontAligned = false;
	static boolean rightFrontAligned = false;
	static boolean leftBackAligned = false;
	static boolean rightBackAligned = false;

	static int leftFrontZero = 0;
	static int rightFrontZero = 0;
	static int leftBackZero = 0;
	static int rightBackZero = 0;
	
	public static void aligning() {
		// TODO Auto-generated method stub

	}
	
	public static void align(){
		if(SwerveControl.rotateLFMotor.getAnalogInRaw() > leftFrontZero + 1){
			SwerveControl.rotateLFMotor.set(-.05);
		}else if(SwerveControl.rotateLFMotor.getAnalogInRaw() < leftFrontZero - 1){
			SwerveControl.rotateLFMotor.set(.05);
		}
		
		if(SwerveControl.rotateLBMotor.getAnalogInRaw() > leftBackZero + 1){
			SwerveControl.rotateLBMotor.set(-.05);
		}else if(SwerveControl.rotateLBMotor.getAnalogInRaw() < leftBackZero - 1){
			SwerveControl.rotateLBMotor.set(.05);
		}
		
		if(SwerveControl.rotateRFMotor.getAnalogInRaw() > rightFrontZero + 1){
			SwerveControl.rotateRFMotor.set(-.05);
		}else if(SwerveControl.rotateRFMotor.getAnalogInRaw() < rightFrontZero - 1){
			SwerveControl.rotateRFMotor.set(.05);
		}
		
		if(SwerveControl.rotateRBMotor.getAnalogInRaw() > rightBackZero + 1){
			SwerveControl.rotateRBMotor.set(-.05);
		}else if(SwerveControl.rotateRBMotor.getAnalogInRaw() < rightBackZero - 1){
			SwerveControl.rotateRBMotor.set(.05);
		}
		
		if((SwerveControl.rotateLFMotor.getAnalogInRaw() > leftFrontZero - 1) && (SwerveControl.rotateLFMotor.getAnalogInRaw() < leftFrontZero +1)){
			leftFrontAligned = true;
			SwerveControl.rotateLFMotor.set(0);
		}
		
		if((SwerveControl.rotateLBMotor.getAnalogInRaw() > leftBackZero - 1) && (SwerveControl.rotateLBMotor.getAnalogInRaw() < leftBackZero +1)){
			leftBackAligned = true;
			SwerveControl.rotateLBMotor.set(0);
		}
		
		if((SwerveControl.rotateRBMotor.getAnalogInRaw() > rightBackZero - 1) && (SwerveControl.rotateRBMotor.getAnalogInRaw() < rightBackZero +1)){
			rightBackAligned = true;
			SwerveControl.rotateRBMotor.set(0);
		}
		
		if((SwerveControl.rotateRFMotor.getAnalogInRaw() > rightFrontZero - 1) && (SwerveControl.rotateRFMotor.getAnalogInRaw() < rightFrontZero +1)){
			rightFrontAligned = true;
			SwerveControl.rotateRFMotor.set(0);
		}
	}
	public static boolean aligned(){	
		if(rightFrontAligned && rightBackAligned && leftFrontAligned && leftBackAligned){

			return true;
		}
		else{
			return false;
		}
		
	}

}

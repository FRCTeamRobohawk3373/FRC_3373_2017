package org.usfirst.frc.team3373.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;

import com.ctre.*;
import edu.wpi.first.wpilibj.CANSpeedController.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController.*;
import edu.wpi.first.wpilibj.PIDInterface;
import edu.wpi.first.wpilibj.PIDOutput.*;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;


@SuppressWarnings("unused")
public class SwerveControl  {
	
	//must also use a wheel class
	
	Talon driveLFMotor;
	CANTalon rotateLFMotor;
	//Front Right Wheel
	Talon driveRFMotor;
	CANTalon rotateRFMotor;
	//Back Left Wheel
	Talon driveLBMotor;
	CANTalon rotateLBMotor;
	//Back Right Wheel
	Talon driveRBMotor;
	CANTalon rotateRBMotor;
	
	
	
	//NavX
	AHRS ahrs;
	
	
	int encoderUnitsPerRotation = 1660;//was 1665
	
    double p = 10; //100 is very close
    double i = 0;
    double d = 0;
    //double f = 0;
    //int izone = 100;

    double orientationOffset;
    
    //Used to switch between control modes
    boolean isRobotCentric = true;
    boolean isFieldCentric = false;
    boolean isObjectCentric = false;
    boolean isHookCentric = false;
    
    double radius = 55;
    
     boolean leftFrontAligned = false;
	 boolean rightFrontAligned = false;
	 boolean leftBackAligned = false;
	 boolean rightBackAligned = false;

	static int leftFrontZero = 0;
	static int rightFrontZero = 0;
	static int leftBackZero = 0;
	static int rightBackZero = 0;
    
	SwerveWheel[] wheelArray;

	SwerveWheel FLWheel;
	SwerveWheel FRWheel;
	SwerveWheel BLWheel;
    SwerveWheel BRWheel;
	
	double angleToDiagonal;
	double robotLength;
	double robotWidth;
	
	//Used for Autonomous control
	PIDOutputObject rotatePIDOutput = new PIDOutputObject();
	PIDInputObject rotatePIDInput = new PIDInputObject();
	PIDController rotationPID = new PIDController(5, 0, 8, rotatePIDInput, rotatePIDOutput);// p=5 i=0 d=10
	
	/*give dimensions between the wheels both width and length, 
	 * width is the distance between left wheels and right wheels,
	 *  length is the distance between front wheels and back wheels*/
	
	/**
	 * Initializes one SwerveControl Object
	 * @param frontLeftDriveChannel PWM Port for Talon that drives the Front Left Swerve Wheel
	 * @param frontLeftRotateID CANBus ID for Front Left Swerve Wheel
	 * @param frontRightDriveChannel PWM Port for Talon that drives the Front Left Right Wheel
	 * @param frontRightRotateID CANBus ID for Front Right Swerve Wheel
	 * @param backLeftDriveChannel  PWM Port for Talon that drives the Back Left Swerve Wheel
	 * @param backLeftRotateID CANBus ID for Back Left Swerve Wheel
	 * @param backRightDriveChannel  PWM Port for Talon that drives the Back Right Swerve Wheel
	 * @param backRightRotateID  CANBus ID for Back Right Swerve Wheel
	 * @param width Width of the robot, distance(inches) between left and right wheels
	 * @param length Length of the robot, distance(inches) between front and back wheels 
	 */
	
	public SwerveControl(int frontLeftDriveChannel, int frontLeftRotateID, int frontRightDriveChannel, 
			int frontRightRotateID, int backLeftDriveChannel, int backLeftRotateID, int backRightDriveChannel,
			int backRightRotateID, double width, double length){
		
		robotWidth = width;
		robotLength = length;
		
		//used to establish rotation angles for all four wheels
		angleToDiagonal = Math.toDegrees(Math.atan2(length, width));
		
		
		FLWheel = new SwerveWheel(frontLeftDriveChannel, frontLeftRotateID, p, i, d, (270 - angleToDiagonal), 205);
		FRWheel = new SwerveWheel(frontRightDriveChannel, frontRightRotateID, p, i, d, (angleToDiagonal + 90), 205);
		BLWheel = new SwerveWheel(backLeftDriveChannel, backLeftRotateID, p, i, d, (angleToDiagonal + 270), 0);
		BRWheel = new SwerveWheel(backRightDriveChannel, backRightRotateID, p, i, d, (90 - angleToDiagonal), 0);
		
		/*
		FLWheel = new SwerveWheel(frontLeftDriveChannel, frontLeftRotateID, p, i, d, (180 - angleToDiagonal), 0);
		FRWheel = new SwerveWheel(frontRightDriveChannel, frontRightRotateID, p, i, d, (angleToDiagonal), 0);
		BLWheel = new SwerveWheel(backLeftDriveChannel, backLeftRotateID, p, i, d, (angleToDiagonal + 180), 0);
		BRWheel = new SwerveWheel(backRightDriveChannel, backRightRotateID, p, i, d, (0 - angleToDiagonal), 0);
		*/
		
		
		wheelArray = new SwerveWheel[]{FLWheel, FRWheel, BLWheel, BRWheel};
		
        try {
        	
    		
    		// You can add a second parameter to modify the 
    		// update rate (in hz) from 4 to 100.  The default is 100.
    		// If you need to minimize CPU load, you can set it to a
    		// lower value, as shown here, depending upon your needs.
    		
    		// You can also use the IMUAdvanced class for advanced
    		// features.
    		
    		byte update_rate_hz = 100;
    		//imu = new IMU(serial_port,update_rate_hz);
    		ahrs = new AHRS(SerialPort.Port.kMXP);
        	} catch( Exception ex ) {
        		
        	}
		
		
        initPIDControllers();
	}

	
	/***********************
	 *  Autonomous Methods *
	 ***********************/
	
	/**
	 * Used in Autonomous Mode only, Rotates robot a certain angle from the current position 
	 * @param targetAngle Angle(degrees) from current position of robot, which robot will rotate to
	 */
	
	public void initPIDControllers(){
		rotationPID.enable();
		rotationPID.setInputRange(-180, 180); //sets input range from 0 to 360(degrees)
		rotationPID.setOutputRange(-0.5, 0.5); //sets output range from -1 to 1(max rotation values)
		rotationPID.setContinuous();
		updatePIDControllers();
	}
	
	public void updatePIDControllers(){
		rotatePIDInput.setValue(ahrs.getYaw());
		SmartDashboard.putNumber("PIDInput Value: ", rotatePIDInput.pidGet());
	}
	
	
	public void relativeRotateRobot(double angle){
		SmartDashboard.putNumber("Delta Angle", angle);
		double currentAngle = ahrs.getYaw();
		SmartDashboard.putNumber("Current Angle:", currentAngle);
		double targetAngle = currentAngle + angle;

		if(targetAngle >= 180){
			targetAngle -= 360;
		} else if(targetAngle < -180){
			targetAngle += 360;
		}
		SmartDashboard.putNumber("Target Angle: ", targetAngle);
		updatePIDControllers();
		while(Math.abs(currentAngle - targetAngle) >= 2){ //waits until we are within range of the angle
			rotationPID.setSetpoint(targetAngle); //tells PID loop to go to the targetAngle
			currentAngle = ahrs.getYaw();
			updatePIDControllers();
			//calculateSwerveControl(0,0,0.2);
			calculateSwerveControl(0, 0, rotatePIDOutput.getPIDValue()); //sets the wheels to rotate based off PID loop
			try{
				Thread.sleep(1);
			} catch(Exception e){
				//Do nothing
			}
		}
		calculateSwerveControl(0,0,0); //stops robot spinning
		SmartDashboard.putNumber("Current Angle:", currentAngle);
	}
	
	public void relativeMoveRobot(double angle, double speed, double time){
		calculateSwerveControl(Math.sin(Math.toRadians(angle)) * speed, Math.cos(Math.toRadians(angle)) * speed, 0);
		try{
			Thread.sleep((long) (time * 1000));
		}catch(Exception e){
			//Do nothing
		}

		calculateSwerveControl(0, 0, 0);
		
	}
	
	/**
	 * Used in Autonomous Mode Only, Rotates robot to a certain angle regardless of robots current position
	 * @param targetAngle Angle(degrees) to which the robot will rotate
	 */
	
	public void absoluteRotateRobot(double targetAngle){
		double currentAngle = ahrs.getYaw();
		if(targetAngle >= 180){
			targetAngle-=360;
		} else if(targetAngle < -180){
			targetAngle +=360;
		}
		updatePIDControllers();
		while(Math.abs(currentAngle - targetAngle) >= 1){//waits until we are within range of our target angle
			rotationPID.setSetpoint(targetAngle);//tells PID loop to go to the target angle
			currentAngle = ahrs.getYaw();
			SmartDashboard.putNumber("Absolute Current Angle", currentAngle);
			updatePIDControllers();
			calculateSwerveControl(0, 0, rotatePIDOutput.getPIDValue());//sets the wheels to rotate based off PID loop
			try{
				Thread.sleep(10);
			} catch(Exception e){
				//Do nothing
			}
		}
		calculateSwerveControl(0,0,0); //stops robot spinning
	}
	
	/***********************
	 * Calculation Methods *
	 ***********************/
	
    public int angleToEncoderUnit(double angle){//Only pass in deltaTheta
    	
    	double deltaEncoder;
    	deltaEncoder = angle*(encoderUnitsPerRotation/360.0); 
    	
    	return (int)deltaEncoder;
    }
    
    /***********************
     * Swerve Move Methods *
     **********************/
    
    /**
     * Calls the correct movement method based on control mode
     * @param LY Left stick Y Axis
     * @param LX Left stick X Axis
     * @param RX Right stick X Axis
     */
    
    public void move(double LY, double LX, double RX){
    	if(isFieldCentric || isRobotCentric){
    		calculateSwerveControl(LY, LX, RX);
    	} else if(isObjectCentric){
    		setSpeedMode(0.5);
    		calculateObjectControl(RX);
    	} else{
    		calculateHookControl(RX);
    	}
    }
    
    /**
     * Called by move command, controls both field centric and robot centric modes
     * @param LY Left stick Y Axis
     * @param LX Left stick X Axis
     * @param RX Right stick X Axis
     */
    
    public void calculateSwerveControl(double LY, double LX, double RX){
    	double translationalXComponent = LX;
    	double translationalYComponent = LY;
    	double translationalMagnitude;
    	double translationalAngle;
    	
    	double rAxis = RX;
    	double rotateXComponent;
    	double rotateYComponent;
    	double fastestSpeed = 0;
    	
    	//Deadband
    	if(Math.abs(LX) < 0.1){
    		translationalXComponent = 0;
    		LX = 0;
    	}
    	
    	if(Math.abs(LY) < 0.1){
    		translationalYComponent = 0;
    		LY = 0;
    	}
    	
    	if(Math.abs(RX) < 0.1){
    		rAxis = 0;
    		RX = 0;
    	}
    	
    	
    	if(isFieldCentric){
    		orientationOffset = ahrs.getYaw(); //if in field centric mode make offset equal to the current angle of the navX
    	}
    	
    	double rotationMagnitude = Math.abs(rAxis);
    	
    	//We break up the axis to create two vectors for the robot(and each wheel)
    		//translational vector
    		//rotation vector
    	
    	//Same for all wheels so therefore we only do the transitional vector math once
    	translationalMagnitude = Math.sqrt(Math.pow(translationalYComponent, 2) + Math.pow(translationalXComponent, 2));
    	translationalAngle = Math.toDegrees(Math.atan2(translationalYComponent, translationalXComponent));
    	
    	translationalAngle += orientationOffset; //sets the robot front to be at the angle determined by orientationOffset
    	if(translationalAngle >= 360){
    		translationalAngle -= 360;
    	} else if(translationalAngle < 0){
    		translationalAngle += 360;
    	}
    	
    	translationalYComponent = Math.sin(Math.toRadians(translationalAngle)) * translationalMagnitude; //calculates y component of translation vector
    	translationalXComponent = Math.cos(Math.toRadians(translationalAngle)) * translationalMagnitude; //calculates x component of translation vector
    	
    	
    	//math for rotation vector, different for every wheel so we calculate for each one seperately
    	for (SwerveWheel wheel : wheelArray){
    		
    		rotateXComponent = Math.cos(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; //calculates x component of rotation vector
    		rotateYComponent = Math.sin(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; //calculates y component of rotation vector
    		
    		if(rAxis > 0){//Why do we do this?
    			rotateXComponent = -rotateXComponent;
    			rotateYComponent = -rotateYComponent;
    		}
    		
    		wheel.setSpeed(Math.sqrt(Math.pow(rotateXComponent + translationalXComponent, 2) + Math.pow((rotateYComponent + translationalYComponent), 2)));//sets the speed based off translational and rotational vectors
    		wheel.setTargetAngle(Math.toDegrees(Math.atan2((rotateYComponent + translationalYComponent), (rotateXComponent + translationalXComponent))));//sets the target angle based off translation and rotational vectors
    		
    		if(LY == 0 && LX == 0 && RX == 0){//if our inputs are nothing, don't change the angle(use currentAngle as targetAngle)
    			wheel.setTargetAngle(wheel.getCurrentAngle());
    		}
    		
    		if(wheel.getSpeed() > fastestSpeed){//if speed of wheel is greater than the others store the value
    			fastestSpeed = wheel.getSpeed();
    		}
    	}
    	
    	if(fastestSpeed > 1){ //if the fastest speed is greater than 1(our max input) divide the target speed for each wheel by the fastest speed
    		for(SwerveWheel wheel : wheelArray){
        		wheel.setSpeed(wheel.getSpeed()/fastestSpeed);
        	}
    	}
    	
    	//Makes the wheels go to calculated target angle
    	FRWheel.goToAngle();
    	FLWheel.goToAngle();
    	BRWheel.goToAngle();
    	BLWheel.goToAngle();
    	//Make the wheels drive at their calculated speed
    	FRWheel.drive();
    	FLWheel.drive();
    	BRWheel.drive();
    	BLWheel.drive();
    	
    	
    	/*
    	 * FOR TESTING PURPOSES
    	 * 
    	 
    	//double FRWheelTarget = FRWheel.rotateMotor.getEncPosition() + angleToEncoderUnit(FRWheel.getDeltaTheta());
    	.rotateMotor.set(FRWheel.rotateMotor.getEncPosition() + angleToEncoderUnit(FRWheel.getDeltaTheta()));
    	SmartDashboard.putNumber("FR Target Encoder Position", (FRWheel.getTargetAngle()));
    	SmartDashboard.putNumber("FR DeltaTheta: ", angleToEncoderUnit(FRWheel.getDeltaTheta()));
    	SmartDashboard.putNumber("FR Current Encoder", FRWheel.getCurrentAngle());
    	try{
    		//Thread.sleep(5000);
    	}catch (Exception ex){
    		
    	}
    	
    	FLWheel.rotateMotor.set(FLWheel.rotateMotor.getEncPosition() + angleToEncoderUnit(FLWheel.getDeltaTheta()));
    	SmartDashboard.putNumber("FL Target Encoder Position", (FLWheel.rotateMotor.getEncPosition() + angleToEncoderUnit(FLWheel.getDeltaTheta())));
    	SmartDashboard.putNumber("FL DeltaTheta: ", FLWheel.getDeltaTheta());
    	BRWheel.rotateMotor.set(BRWheel.rotateMotor.getEncPosition() + angleToEncoderUnit(BRWheel.getDeltaTheta()));
    	SmartDashboard.putNumber("BR Target Encoder Position", (BRWheel.rotateMotor.getEncPosition() + angleToEncoderUnit(BRWheel.getDeltaTheta())));
    	SmartDashboard.putNumber("BR DeltaTheta: ", BRWheel.getDeltaTheta());
    	BLWheel.rotateMotor.set(BLWheel.rotateMotor.getEncPosition() + angleToEncoderUnit(BLWheel.getDeltaTheta()));
    	SmartDashboard.putNumber("BL Target Encoder Position", (BLWheel.rotateMotor.getEncPosition() + angleToEncoderUnit(BLWheel.getDeltaTheta())));
    	SmartDashboard.putNumber("BL DeltaTheta: ", BLWheel.getDeltaTheta());
    	
    	SmartDashboard.putNumber("Current Angle", BLWheel.getCurrentAngle());
    	SmartDashboard.putNumber("Delta Theta", BLWheel.getDeltaTheta());
    	SmartDashboard.putNumber("Target Angle", BLWheel.getTargetAngle());
    	*/
    
    }
    
    /**
     * Called by move command, controls object centric mode
     * @param RX Right stick X Axis
     */
    
    public void calculateObjectControl(double RX){
    	double distanceToFront = radius - robotLength/2;
    	double distanceToBack = radius + robotLength/2;
    	
    	FLWheel.setTargetAngle(180 - Math.toDegrees(Math.atan2(robotWidth/2, distanceToFront)));
    	FRWheel.setTargetAngle(180 + Math.toDegrees(Math.atan2(robotWidth/2, distanceToFront)));
    	BLWheel.setTargetAngle(180 - Math.toDegrees(Math.atan2(robotWidth/2, distanceToBack)));
    	BRWheel.setTargetAngle(180 + Math.toDegrees(Math.atan2(robotWidth/2, distanceToBack)));
    	
    	BLWheel.setSpeed(RX);
    	BRWheel.setSpeed(RX);
    	
    	double speedRatio = Math.sqrt(Math.pow((robotWidth/2), 2) + Math.pow(distanceToFront, 2)) / Math.sqrt(Math.pow((robotWidth/2), 2) + Math.pow(distanceToBack, 2));
    	
    	FLWheel.setSpeed(speedRatio * RX);
    	FRWheel.setSpeed(speedRatio * RX);
    	
    	FRWheel.goToAngle();
    	FLWheel.goToAngle();
    	BRWheel.goToAngle();
    	BLWheel.goToAngle();
    	
    	FRWheel.drive();
    	FLWheel.drive();
    	BRWheel.drive();
    	BLWheel.drive();
    	
    }
    
    
    public void calculateHookControl(double RX){
    	double distanceToFront = 38 - robotLength/2;
    	double distanceToBack = 38 + robotLength/2;
    	
    	FLWheel.setTargetAngle(180 - Math.toDegrees(Math.atan2(robotWidth/2, distanceToFront)));
    	FRWheel.setTargetAngle(180 + Math.toDegrees(Math.atan2(robotWidth/2, distanceToFront)));
    	BLWheel.setTargetAngle(180 - Math.toDegrees(Math.atan2(robotWidth/2, distanceToBack)));
    	BRWheel.setTargetAngle(180 + Math.toDegrees(Math.atan2(robotWidth/2, distanceToBack)));
    	
    	BLWheel.setSpeed(RX);
    	BRWheel.setSpeed(RX);
    	
    	double speedRatio = Math.sqrt(Math.pow((robotWidth/2), 2) + Math.pow(distanceToFront, 2)) / Math.sqrt(Math.pow((robotWidth/2), 2) + Math.pow(distanceToBack, 2));
    	
    	FLWheel.setSpeed(speedRatio * RX);
    	FRWheel.setSpeed(speedRatio * RX);
    	
    	FRWheel.goToAngle();
    	FLWheel.goToAngle();
    	BRWheel.goToAngle();
    	BLWheel.goToAngle();
    	
    	FRWheel.drive();
    	FLWheel.drive();
    	BRWheel.drive();
    	BLWheel.drive();
    	
    }
    
    public void swerveControl(double LY, double LX, double RX, double radius){
    	double translationalXComponent = LX;
    	double translationalYComponent = LY;
    	double rAxis = RX;
    	
    	double translationalOffset = 0.0;
    	if(isFieldCentric){
    		translationalOffset = ahrs.getYaw();
    	} else {
    		translationalOffset = 0;
    	}
    	
    	//Same for all wheels so therefore we only do the transitional vector math once
    	double translationalMagnitude = Math.sqrt(Math.pow(translationalYComponent, 2) + Math.pow(translationalXComponent, 2));
    	double translationalAngle = Math.toDegrees(Math.atan2(translationalYComponent, translationalXComponent));
    	
    	translationalAngle += translationalOffset; //sets the robot front to be at the angle determined by orientationOffset
    	if(translationalAngle >= 360){
    		translationalAngle -= 360;
    	} else if(translationalAngle < 0){
    		translationalAngle += 360;
    	}
    	
    	translationalYComponent = Math.sin(Math.toRadians(translationalAngle)) * translationalMagnitude; //calculates y component of translation vector
    	translationalXComponent = Math.cos(Math.toRadians(translationalAngle)) * translationalMagnitude; //calculates x component of translation vector
    	
    	
    	//Deadband
    	if(Math.abs(LX) < 0.1) translationalXComponent = 0;
    	if(Math.abs(LY) < 0.1) translationalYComponent = 0;
    	if(Math.abs(RX) < 0.1) rAxis = 0;
    	//End Deadband

    	double distanceToFront = radius - robotLength/2;
    	double distanceToBack = radius + robotLength/2;
    	
    	//Calculates wheel's rotational angle based on radius
    	FLWheel.setRAngle(180 - Math.toDegrees(Math.atan2(robotWidth/2, distanceToFront)));
    	FRWheel.setRAngle(180 + Math.toDegrees(Math.atan2(robotWidth/2, distanceToFront)));
    	BLWheel.setRAngle(180 - Math.toDegrees(Math.atan2(robotWidth/2, distanceToBack)));
    	BRWheel.setRAngle(180 + Math.toDegrees(Math.atan2(robotWidth/2, distanceToBack)));
    	
    	//Calculate each wheel's rotational speed based on the radius
    	//THIS ONLY ALLOWS FOR A POSITVE RADIUS
    	double speedRatio = Math.sqrt(Math.pow((robotWidth/2), 2) + Math.pow(distanceToFront, 2)) / Math.sqrt(Math.pow((robotWidth/2), 2) + Math.pow(distanceToBack, 2));
    	
    	BLWheel.setSpeed(rAxis);
    	BRWheel.setSpeed(rAxis);
    	FLWheel.setSpeed(speedRatio * rAxis);
    	FRWheel.setSpeed(speedRatio * rAxis);
    	
    	/*This seems wrong, copied code from above
    	BLWheel.setRSpeed(1);
    	BRWheel.setRSpeed(1);
    	FLWheel.setRSpeed(speedRatio);
    	FRWheel.setRSpeed(speedRatio);
    	*/
    	double fastestSpeed = 1.0;
    	for (SwerveWheel wheel : wheelArray){
    		
    		double rotateXComponent = Math.cos(Math.toRadians(wheel.getRAngle())) * wheel.getRSpeed(); //calculates x component of rotation vector
    		double rotateYComponent = Math.sin(Math.toRadians(wheel.getRAngle())) * wheel.getRSpeed(); //calculates y component of rotation vector
    		
    		if(rAxis > 0){//Why do we do this?
    			rotateXComponent = -rotateXComponent;
    			rotateYComponent = -rotateYComponent;
    		}
    		
    		wheel.setSpeed(Math.sqrt(Math.pow(rotateXComponent + translationalXComponent, 2) + Math.pow((rotateYComponent + translationalYComponent), 2)));//sets the speed based off translational and rotational vectors
    		wheel.setTargetAngle(Math.toDegrees(Math.atan2((rotateYComponent + translationalYComponent), (rotateXComponent + translationalXComponent))));//sets the target angle based off translation and rotational vectors
    		
    		if(LY == 0 && LX == 0 && RX == 0){//if our inputs are nothing, don't change the angle(use currentAngle as targetAngle)
    			wheel.setTargetAngle(wheel.getCurrentAngle());
    		}
    		
    		if(wheel.getSpeed() > fastestSpeed){//if speed of wheel is greater than the others store the value
    			fastestSpeed = wheel.getSpeed();
    		}
    	}
    	
    	if(fastestSpeed > 1){ //if the fastest speed is greater than 1(our max input) divide the target speed for each wheel by the fastest speed
    		for(SwerveWheel wheel : wheelArray){
        		wheel.setSpeed(wheel.getSpeed()/fastestSpeed);
        	}
    	}
    	
    	//Makes the wheels go to calculated target angle
    	FRWheel.goToAngle();
    	FLWheel.goToAngle();
    	BRWheel.goToAngle();
    	BLWheel.goToAngle();
    	//Make the wheels drive at their calculated speed
    	FRWheel.drive();
    	FLWheel.drive();
    	BRWheel.drive();
    	BLWheel.drive();
    }
    
    public void setSpeedMode(double newSpeedModifier){
    	for (SwerveWheel wheel : wheelArray){
			wheel.setSpeedModifier(newSpeedModifier);
		}
    }

    /**
     * Change the orientation of the robot in robot centric mode(i.e. changes the left side to become the front)
     * @param north button to make robot front the original front
     * @param east button to make robot front the original right
     * @param south button to make robot front the original back
     * @param west button to make robot front the original left
     */
    
    public void changeOrientation(boolean north, boolean east, boolean south, boolean west){
    	
    	//switch out of field centric
    	//set the robot front (N,E,S,W)
    	if(north){
    		isFieldCentric = false;
    		isObjectCentric = false;
    		orientationOffset = 0;
   		} else if(east){
   			isFieldCentric = false;
   			isObjectCentric = false;
   			orientationOffset = -90;
   		} else if(south){
   			isFieldCentric = false;
   			isObjectCentric = false;
    		orientationOffset = 180;
    	} else if(west){
    		isFieldCentric = false;
    		isObjectCentric = false;
    		orientationOffset = 90;
    	}
    	
    }
    
    /**
     * Called to switch to field centric mode
     */
    
    public void switchToFieldCentric(){
		isObjectCentric = false;
		isRobotCentric = false;
		isFieldCentric = true;
		isHookCentric = false;
    }
    
    /**
     * Called to switch to object centric mode
     */
    
    public void switchToObjectCentric(){
		isObjectCentric = true;
		isFieldCentric = false;
		isRobotCentric = false;
		isHookCentric = false;
    }
   /**
    * Called to switch to HookCentric 
    */
    public void switchToHookCentric(){
		isObjectCentric = false;
		isFieldCentric = false;
		isRobotCentric = false;
		isHookCentric = true;
    }
    
    /**
     * Called to switch to robot centric mode
     */
    
    public void switchToRobotCentric(){
		isObjectCentric = false;
		isFieldCentric = false;
		isRobotCentric = true;
		isHookCentric = false;
    }
    
    /*
    public void switchDrivingMode(boolean LStick, boolean RStick){
    	if(LStick && !isFieldCentric){
    		isObjectCentric = false;
    		isRobotCentric = false;
    		isFieldCentric = true;
    	} else if(LStick && isFieldCentric){
    		isObjectCentric = false;
    		isFieldCentric = false;
    		isRobotCentric = true;
    	} else if(RStick && !isObjectCentric){
    		isObjectCentric = true;
    		isFieldCentric = false;
    		isRobotCentric = false;
    	} else if(RStick && isObjectCentric){
    		isObjectCentric = false;
    		isFieldCentric = false;
    		isRobotCentric = true;
    	} else{
    		isObjectCentric = false;
    		isFieldCentric = false;
    		isRobotCentric = true;    		
    	}
    }*/
    
    /********************
     * Calibration Code *
     ********************/
    
    /**
     * Moves all four swerve wheels to home position(the limit switch)
     */
    
    public void wheelsToHomePos(){
    	/*for (SwerveWheel wheel : wheelArray){
    		wheel.goToHome();
    	}*/
    	FRWheel.goToHome();
    	//FRWheel.goToHome();
    	//BRWheel.goToHome();
    	//BLWheel.goToHome();
    
    }
    
    /**
     * Moves all four swerve wheels to zero position(straight sideways)(0 degrees on the unit circle)
     */
    
    public void wheelsToZero(){
    	FRWheel.goToZero();
    	FLWheel.goToZero();
    }
    
    public void test(){
    	FRWheel.test();
    	//FLWheel.test();
    	//BLWheel.test();
    	//BRWheel.test();
    }
    public void disable(){
    	FRWheel.disable();
    	//FLWheel.test();
    	//BLWheel.test();
    	//BRWheel.test();
    }
    
    public void swerveAlign(){
    	if(FLWheel.rotateMotor.getAnalogInRaw() > leftFrontZero + 10){
    		FLWheel.rotateMotor.set(-.05);
		}else if(FLWheel.rotateMotor.getAnalogInRaw() < leftFrontZero - 10){
			FLWheel.rotateMotor.set(.05);
		}
    	
		if(BLWheel.rotateMotor.getAnalogInRaw() > leftBackZero + 10){
			BLWheel.rotateMotor.set(-.05);
		}else if(BLWheel.rotateMotor.getAnalogInRaw() < leftBackZero - 10){
			BLWheel.rotateMotor.set(.05);
		}
		
		if(FRWheel.rotateMotor.getAnalogInRaw() > rightFrontZero + 10){
			FRWheel.rotateMotor.set(-.05);
		}else if(FRWheel.rotateMotor.getAnalogInRaw() < rightFrontZero - 10){
			FRWheel.rotateMotor.set(.05);
		}
		
		if(BRWheel.rotateMotor.getAnalogInRaw() > rightBackZero + 10){
			BRWheel.rotateMotor.set(-.05);
		}else if(BRWheel.rotateMotor.getAnalogInRaw() < rightBackZero - 10){
			BRWheel.rotateMotor.set(.05);
		}
		
		if((FLWheel.rotateMotor.getAnalogInRaw() > leftFrontZero - 10) && (FLWheel.rotateMotor.getAnalogInRaw() < leftFrontZero +10)){
			leftFrontAligned = true;
			FLWheel.rotateMotor.set(0);
		}
		
		if((BLWheel.rotateMotor.getAnalogInRaw() > leftBackZero - 10) && (BLWheel.rotateMotor.getAnalogInRaw() < leftBackZero +10)){
			leftBackAligned = true;
			BLWheel.rotateMotor.set(0);
		}
		
		if((BRWheel.rotateMotor.getAnalogInRaw() > rightBackZero - 10) && (BRWheel.rotateMotor.getAnalogInRaw() < rightBackZero +10)){
			rightBackAligned = true;
			BRWheel.rotateMotor.set(0);
		}
		
		if((FRWheel.rotateMotor.getAnalogInRaw() > rightFrontZero - 10) && (FRWheel.rotateMotor.getAnalogInRaw() < rightFrontZero +10)){
			rightFrontAligned = true;
			FRWheel.rotateMotor.set(0);
		}
    }
	public boolean aligned(){	
		if(rightFrontAligned && rightBackAligned && leftFrontAligned && leftBackAligned){

			return true;
		}
		else{
			return false;
		}
		
	}
}


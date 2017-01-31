
package org.usfirst.frc.team3373.robot;

import com.ctre.*;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String current = "Default";
	final String follower = "Follower";
	final String motionmagic = "motion magic";
	final String motionprofile = "Motion Profile";
	final String percentvbus = "Percent V Bus";
	final String position = "Position";
	final String speed = "speed";
	final String voltage = "voltage";

	String autoSelected;
	SendableChooser chooser;

	SwerveControl swerve;
	GearDisposal gearControl;
	/***************************
	 * Robot Talon Identifier * F * 0 ------ 1 * | | * | | * 2--------3 *
	 ***************************/
	int frontLeftRotate = 2;
	int frontRightRotate = 1;
	int backLeftRotate = 3;
	int backRightRotate = 0;

	int frontLeftDrive = 0;
	int frontRightDrive = 1;
	int backLeftDrive = 2;
	int backRightDrive = 8;

	boolean haveRun;
	boolean isAligned;

	double robotWidth = 21.125;// TODO CALIBRATE check
	double robotLength = 33.5;// TODO CALIBRATE check
	double rotateRadius = 0.;
	double objectDistance = 60.;
	double stackDistance = 45.;

	boolean ismanualLifterMode = true;
	boolean isCollisionPossible = false;

	int LX = 0;
	int LY = 1;
	int Ltrigger = 2;
	int Rtrigger = 3;
	int RX = 4;
	int RY = 5;
	
	int gearControlMode = 1;
	int gearControlModePrev = 0;

	//CANTalon testTalon = new CANTalon(2);

	SuperJoystick driver;
	SuperJoystick shooter;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		System.out.println("INITiating");
		driver = new SuperJoystick(0);
		shooter = new SuperJoystick(1);
		
		
		isAligned = false;

		swerve = new SwerveControl(frontLeftDrive, frontLeftRotate, frontRightDrive, frontRightRotate, backLeftDrive,
				backLeftRotate, backRightDrive, backRightRotate, robotWidth, robotLength);

		gearControl = new GearDisposal(2, 800, 390, 400);
				
		chooser = new SendableChooser();
		chooser.addDefault("current", current);
		chooser.addObject("follower", follower);
		chooser.addObject("motionmagic", motionmagic);
		chooser.addObject("motionprofile", motionprofile);
		chooser.addObject("percentvbus", percentvbus);
		chooser.addObject("position", position);
		chooser.addObject("speed", speed);
		chooser.addObject("voltage", voltage);

		SmartDashboard.putData("Talon choices", chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
				// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
	/*	if (!SwerveAlign.aligned()) {
			swerve.swerveAlign();
		} else if (SwerveAlign.aligned() && !isAligned) {
			swerve.FLWheel.rotateMotor.setEncPosition(0);
			swerve.FRWheel.rotateMotor.setEncPosition(0);
			swerve.BLWheel.rotateMotor.setEncPosition(0);
			swerve.BRWheel.rotateMotor.setEncPosition(0);

			isAligned = true;
		}*/

	
		}
	

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		autoSelected = (String) chooser.getSelected();


		
		
	/*	if (!swerve.aligned()) {
			swerve.swerveAlign();
			System.out.println("Front Left: " + swerve.FLWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Front Right: " + swerve.FRWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Back Left: " + swerve.BLWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Back Right: " + swerve.BRWheel.rotateMotor.getAnalogInRaw());
		} else if (swerve.aligned() && !isAligned) {
			swerve.FLWheel.rotateMotor.setEncPosition(0);
			swerve.FRWheel.rotateMotor.setEncPosition(0);
			swerve.BLWheel.rotateMotor.setEncPosition(0);
			swerve.BRWheel.rotateMotor.setEncPosition(0);

			isAligned = true;
		}*/
		
		System.out.println("Aligned? : " + isAligned + "        " + swerve.aligned());
		if(true){
			
			switch (autoSelected) {
			case follower:
	swerve.FLWheel.rotateMotor.changeControlMode(TalonControlMode.Follower);
	swerve.FRWheel.rotateMotor.changeControlMode(TalonControlMode.Follower);
	swerve.BLWheel.rotateMotor.changeControlMode(TalonControlMode.Follower);
	swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.Follower);

				break;
			case motionmagic:
				swerve.FLWheel.rotateMotor.changeControlMode(TalonControlMode.MotionMagic);
				swerve.FRWheel.rotateMotor.changeControlMode(TalonControlMode.MotionMagic);
				swerve.BLWheel.rotateMotor.changeControlMode(TalonControlMode.MotionMagic);
				swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.MotionMagic);

				break;
			case motionprofile:
				swerve.FLWheel.rotateMotor.changeControlMode(TalonControlMode.MotionProfile);
				swerve.FRWheel.rotateMotor.changeControlMode(TalonControlMode.MotionProfile);
				swerve.BLWheel.rotateMotor.changeControlMode(TalonControlMode.MotionProfile);
				swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.MotionProfile);

				break;
			case percentvbus:
				swerve.FLWheel.rotateMotor.changeControlMode(TalonControlMode.PercentVbus);
				swerve.FRWheel.rotateMotor.changeControlMode(TalonControlMode.PercentVbus);
				swerve.BLWheel.rotateMotor.changeControlMode(TalonControlMode.PercentVbus);
				swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.PercentVbus);

				break;
			case position:
				swerve.FLWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
				swerve.FRWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
				swerve.BLWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
				swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
				break;
			case speed:
				swerve.FLWheel.rotateMotor.changeControlMode(TalonControlMode.Speed);
				swerve.FRWheel.rotateMotor.changeControlMode(TalonControlMode.Speed);
				swerve.BLWheel.rotateMotor.changeControlMode(TalonControlMode.Speed);
				swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.Speed);
				break;
			case voltage:
				swerve.FLWheel.rotateMotor.changeControlMode(TalonControlMode.Voltage);
				swerve.FRWheel.rotateMotor.changeControlMode(TalonControlMode.Voltage);
				swerve.BLWheel.rotateMotor.changeControlMode(TalonControlMode.Voltage);
				swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.Voltage);
				break;
			case current:
				swerve.FLWheel.rotateMotor.changeControlMode(TalonControlMode.Current);
				swerve.FRWheel.rotateMotor.changeControlMode(TalonControlMode.Current);
				swerve.BLWheel.rotateMotor.changeControlMode(TalonControlMode.Current);
				swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.Current);
				break;
			default:
				// Put default auto code here
				break;
			}
			
		/*
		 * if(joystick.getRawAxis(LY) > .1 || joystick.getRawAxis(LY) < -.1){
		 * climbTalon1.set(joystick.getRawAxis(LY)); }else{ climbTalon1.set(0);
		 * }
		 */
	//	testTalon.set(.99);
/*		System.out.println("Analog: " + testTalon.getAnalogInRaw());
		System.out.println("Digital: " + testTalon.getEncPosition());*/

	/*	if (driver.isLBHeld()) {
			// Turbo Mode
			swerve.setSpeedMode(.8);
		} else if (driver.isRBHeld()) {
			// Sniper Mode
			swerve.setSpeedMode(0.20);
		} else {
			// Regular mode
			swerve.setSpeedMode(0.5);
		}*/
			
		swerve.switchToRobotCentric();
		//swerve.setSpeedMode(.5);
		swerve.move(.1,.1,.1);
		System.out.println(swerve.FLWheel.getEncoderValue());
		System.out.println(swerve.FRWheel.getEncoderValue());
		System.out.println(swerve.BLWheel.getEncoderValue());
		System.out.println(swerve.BRWheel.getEncoderValue());
		System.out.println(swerve.FLWheel.getTargetAngle());
		System.out.println(swerve.FLWheel.getCurrentAngle());
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		
		if(shooter.isYPushed()){
			gearControlMode = gearControlModePrev;
			gearControlMode ++;
			if (gearControlMode == 1){
				gearControl.closeGearContainer();
			} else if (gearControlMode == 2) {
				gearControl.compressGearContainer();
			} else if (gearControlMode == 3){
				gearControl.openGearContainer();
			}
			gearControlModePrev = gearControlMode;
			if (gearControlModePrev == 3) {
				gearControlModePrev = 0;
			}
		}
	//	swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.PercentVbus);
	//	swerve.FLWheel.driveMotor.set(.5);
	//	swerve.FRWheel.driveMotor.set(.1);
	//	swerve.BLWheel.driveMotor.set(.1);
		
	//	swerve.BRWheel.driveMotor.set(driver.getRawAxis(LX));
	//	swerve.BRWheel.rotateMotor.set(0);
		//System.out.println(swerve.BRWheel.driveMotor.toString());
	/*	System.out.println("Front Right Whool Analog: " + swerve.FRWheel.rotateMotor.getAnalogInRaw());
		System.out.println("Front right: " + swerve.FRWheel.rotateMotor.getAnalogInRaw());
		System.out.println("Front left: " + swerve.FLWheel.rotateMotor.getAnalogInRaw());
		System.out.println("Back right: " + swerve.BRWheel.rotateMotor.getAnalogInRaw());
		System.out.println("Back left: " + swerve.BLWheel.rotateMotor.getAnalogInRaw());*/
	}

}

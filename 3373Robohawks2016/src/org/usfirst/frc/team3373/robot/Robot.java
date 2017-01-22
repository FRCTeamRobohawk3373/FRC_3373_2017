
package org.usfirst.frc.team3373.robot;

import com.ctre.*;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser chooser;

	SwerveControl swerve;
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

	//CANTalon testTalon = new CANTalon(2);

	SuperJoystick driver;
	SuperJoystick shooter;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		driver = new SuperJoystick(0);
		shooter = new SuperJoystick(1);
		
		isAligned = false;

		swerve = new SwerveControl(frontLeftDrive, frontLeftRotate, frontRightDrive, frontRightRotate, backLeftDrive,
				backLeftRotate, backRightDrive, backRightRotate, robotWidth, robotLength);

		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
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
		autoSelected = (String) chooser.getSelected();
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

		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		
		
	/*	if (!SwerveAlign.aligned()) {
			swerve.swerveAlign();
		} else if (SwerveAlign.aligned() && !isAligned) {
			swerve.FLWheel.rotateMotor.setEncPosition(0);
			swerve.FRWheel.rotateMotor.setEncPosition(0);
			swerve.BLWheel.rotateMotor.setEncPosition(0);
			swerve.BRWheel.rotateMotor.setEncPosition(0);

			isAligned = true;
		}*/

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
		swerve.setSpeedMode(.5);
		swerve.move(-driver.getRawAxis(LX), driver.getRawAxis(LY), driver.getRawAxis(RX));

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}

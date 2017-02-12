
package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.CameraServer;
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
	final String normal = "default operation";
	final String gearCalibration = "Calibrating Gears";
	final String climberControl = "Calbrating Climber";
	final String swerveWheelCalibration = "Calbrating Wheels";
	final String shooterCalibration = "Calibrating Shooter";
	final String intakeCalibration = "Calibrating Ball Intake";
	final String hopperCalibration = "Calibrating Hopper";
	CameraServer server;

	String autoSelected;
	
	UltraSonic ultraSonic;

	SwerveControl swerve;
	GearController gearControl;
	BallIntake ballIntake;
	/***************************
	 * Robot Talon Identifier * F * 0 ------ 1 * | | * | | * 2--------3 *
	 ***************************/
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	SendableChooser<String> chooser = new SendableChooser<>();
	SuperJoystick tester;

	int testTimer;
	int angle;
	boolean firstRun = true;
	boolean secondRun = false;
	boolean thirdRun = false;
	boolean fourthRun = false;
	
	int LX = 0;
	int LY = 1;
	int Ltrigger = 2;
	int Rtrigger = 3;
	int RX = 4;
	int RY = 5;
	
	double robotWidth = 21.125;// TODO CALIBRATE check
	double robotLength = 33.5;// TODO CALIBRATE check
	
	int LBdriveChannel = 2;
	int LBrotateID = 3;
	int LBencOffset = 226;

	int LFdriveChannel = 0;
	int LFrotateID = 2;
	int LFencOffset = 127;
	
	int RBdriveChannel = 8;
	int RBrotateID = 0;
	int RBencOffset = 444;
	
	int RFdriveChannel = 1;
	int RFrotateID = 1;
	int RFencOffset = 631;

	//SuperJoystick driver;
	//SuperJoystick shooter;
	
	static JoystickOverride driver;
	static JoystickOverride shooter;
	
	int intakeControlMode = 0;
	int gearControlMode = 0;
	
	boolean pegRetreating;
	int retreatCounter = 0;
	int retreatTargetCycles = 60;

	private boolean sRecord=false;
	private boolean sPlayer=false;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		//CameraServer.getInstance().startAutomaticCapture(1);
		//CameraServer.getInstance().startAutomaticCapture(0);
		//driver = new SuperJoystick(0);
		//shooter = new SuperJoystick(1);
		
		driver = new JoystickOverride(0);
		shooter = new JoystickOverride(1);
		ultraSonic = new UltraSonic(0);
		swerve = new SwerveControl(LBdriveChannel, LBrotateID, LBencOffset, LFdriveChannel, LFrotateID, LFencOffset, RBdriveChannel, RBrotateID, RBencOffset, RFdriveChannel, RFrotateID, RFencOffset, robotWidth, robotLength);

		gearControl = new GearController(13, 275, 542, 580);
		
	//	ballIntake = new BallIntake(11);

		chooser = new SendableChooser();
		chooser.addDefault("Normal Operation", normal);
		chooser.addObject("Gear Calibration", gearCalibration);
		chooser.addObject("Climber Control", climberControl);
		chooser.addObject("Swerve wheel calibration", swerveWheelCalibration);
		chooser.addObject("Shooter Calibration", shooterCalibration);
		chooser.addObject("Intake Calibration", intakeCalibration);
		chooser.addObject("Hopper Calibration", hopperCalibration);
		SmartDashboard.putData("Calibration Choices", chooser);
		
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
	
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		this.retreatFromGearPeg();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		
		if(driver.isYPushed()){
			swerve.setSpinAngle(180);
			swerve.spinXdegrees();
			driver.clearY();
		}
		driver.clearY();
		/*
		 * if (!swerve.aligned()) { swerve.swerveAlign(); System.out.println(
		 * "Front Left: " + swerve.FLWheel.rotateMotor.getAnalogInRaw());
		 * System.out.println("Front Right: " +
		 * swerve.FRWheel.rotateMotor.getAnalogInRaw()); System.out.println(
		 * "Back Left: " + swerve.BLWheel.rotateMotor.getAnalogInRaw());
		 * System.out.println("Back Right: " +
		 * swerve.BRWheel.rotateMotor.getAnalogInRaw()); } else if
		 * (swerve.aligned() && !isAligned) {
		 * swerve.FLWheel.rotateMotor.setEncPosition(0);
		 * swerve.FRWheel.rotateMotor.setEncPosition(0);
		 * swerve.BLWheel.rotateMotor.setEncPosition(0);
		 * swerve.BRWheel.rotateMotor.setEncPosition(0);
		 * 
		 * isAligned = true; }
		 */

			if (!pegRetreating){
				if(driver.getRawAxis(Rtrigger) > .1){
					swerve.isFieldCentric = true;
					swerve.calculateSwerveControl(-driver.getRawAxis(LY), driver.getRawAxis(LX), driver.getRawAxis(RX));
				}else if(driver.getRawAxis(Ltrigger) > .1){
					swerve.isFieldCentric = false;
					swerve.calculateObjectControl(driver.getRawAxis(RX));
				}else{
					swerve.isFieldCentric = false;
					swerve.calculateSwerveControl(-driver.getRawAxis(LY), driver.getRawAxis(LX), driver.getRawAxis(RX));
				}
				
				
				if(driver.isLBHeld()){
					swerve.sniper();
				}else if(driver.isRBHeld()){
					swerve.turbo();
				}else{
					swerve.normalSpeed();
				}
				
				if(driver.getPOV() == 0){
					swerve.setRobotFront(1);
				}else if(driver.getPOV() == 90){
					swerve.setRobotFront(4);
				}else if(driver.getPOV() == 180){
					swerve.setRobotFront(3);
				}else if(driver.getPOV() == 270){
					swerve.setRobotFront(2);
				}
				
				
				
				
				

			}

				
				if(driver.isAHeld()){
					swerve.setRotateDistance(ultraSonic.getDistance());
				}
			


			/*
			 * if(joystick.getRawAxis(LY) > .1 || joystick.getRawAxis(LY) <
			 * -.1){ climbTalon1.set(joystick.getRawAxis(LY)); }else{
			 * climbTalon1.set(0); }
			 */
			// testTalon.set(.99);
			/*
			 * System.out.println("Analog: " + testTalon.getAnalogInRaw());
			 * System.out.println("Digital: " + testTalon.getEncPosition());
			 */

			/*
			 * if (driver.isLBHeld()) { // Turbo Mode swerve.setSpeedMode(.8); }
			 * else if (driver.isRBHeld()) { // Sniper Mode
			 * swerve.setSpeedMode(0.20); } else { // Regular mode
			 * swerve.setSpeedMode(0.5); }
			 */

		}
	

	/**
	 * This function is called periodically during test mode
	 */
	public void testInit() {
		sRecord = false;
		try {
			JoystickRecord.RecordInit("JoystickRecord.txt", 750);
			JoystickPlayer.PlayerInit("JoystickRecord.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testPeriodic() {
		

		if (Robot.driver.isStartHeld()) {
			while (Robot.driver.isStartHeld()) {
			}
			sRecord = true;
		}
		if (sRecord) {
			System.out.println("Started recording");
			sRecord = !JoystickRecord.record();
		}
		if (Robot.driver.isBackHeld()) {
			sPlayer = true;
		}
		if (sPlayer) {
			System.out.println("Started playing");
			sPlayer = !JoystickPlayer.Play();
		}
		autoSelected = (String) chooser.getSelected();

		switch (autoSelected) {
		case gearCalibration:
			gearControl.calibrate();
			gearControl.rotateGearDoor.set(shooter.getRawAxis(1)*.1);
			break;
		case climberControl:
			
			break;
		case swerveWheelCalibration:
			System.out.println(ultraSonic.analogSensor.getAverageVoltage());
			break;
		case shooterCalibration:
			
			break;
		case intakeCalibration:
			//ballIntake.calibrate();
			//ballIntake.intakeMotor.set(shooter.getRawAxis(1));
			break;
		case hopperCalibration:
			
			break;
		case normal:
			/* if(driver.getAxis(Rtrigger) > .1){
				swerve.isFieldCentric = true;
				swerve.calculateSwerveControl(-driver.getAxis(LY), driver.getAxis(LX), driver.getAxis(RX));
			}else if(driver.getAxis(Ltrigger) > .1){
				swerve.isFieldCentric = false;
				swerve.calculateObjectControl(driver.getAxis(RX));
			}else{
				swerve.isFieldCentric = false;
				swerve.calculateSwerveControl(-driver.getAxis(LY), driver.getAxis(LX), driver.getAxis(RX));
			} */
			
			if(driver.getRawAxis(Rtrigger) > .1){
				swerve.isFieldCentric = true;
				swerve.calculateSwerveControl(-driver.getRawAxis(LY), driver.getRawAxis(LX), driver.getRawAxis(RX));
			}else if(driver.getRawAxis(Ltrigger) > .1){
				swerve.isFieldCentric = false;
				swerve.calculateObjectControl(driver.getRawAxis(RX));
			}else{
				swerve.isFieldCentric = false;
				swerve.calculateSwerveControl(-driver.getRawAxis(LY), driver.getRawAxis(LX), driver.getRawAxis(RX));
			}
			
			if(driver.isLBHeld()){
				swerve.sniper();
			}else if(driver.isRBHeld()){
				swerve.turbo();
			}else{
				swerve.normalSpeed();
			}
			
			if(driver.getPOV() == 0){
				swerve.setRobotFront(1);
			}else if(driver.getPOV() == 90){
				swerve.setRobotFront(4);
			}else if(driver.getPOV() == 180){
				swerve.setRobotFront(3);
			}else if(driver.getPOV() == 270){
				swerve.setRobotFront(2);
			}
			
			
			if (shooter.isYPushed()) {
				gearControlMode += 1;
				gearControlMode = gearControlMode % 3;
				System.out.println("Switching gear control mode");
				if (gearControlMode == 0) {
					gearControl.closeGearContainer();
					System.out.println("Closing");
				} else if (gearControlMode == 1) {
					gearControl.compressGearContainer();
					System.out.println("compressing");
				} else {
					gearControl.openGearContainer();
					System.out.println("opening");
				}

			}

			
			

			gearControl.setGearDoorSpeed(1);
			shooter.clearY();
			
			
			if (shooter.isBackPushed()) {
				intakeControlMode += 1;
				intakeControlMode = intakeControlMode % 3;
				System.out.println("Switching intake mode");
				if (intakeControlMode == 0) {
					ballIntake.intakeBalls();
					System.out.println("intaking balls");
				} else if (intakeControlMode == 1) {
					ballIntake.clearBalls();
					System.out.println("clearing ball intake");
				} else {
					ballIntake.ballsOff();
					System.out.println("ball intake off");
				}

			}
			shooter.clearBack();
break;
		}
	}
		
		
			// swerve.BRWheel.rotateMotor.changeControlMode(TalonControlMode.PercentVbus);
			// swerve.FLWheel.driveMotor.set(.5);
			// swerve.FRWheel.driveMotor.set(.1);
			// swerve.BLWheel.driveMotor.set(.1);

			// swerve.BRWheel.driveMotor.set(driver.getRawAxis(LX));
			// swerve.BRWheel.rotateMotor.set(0);
			// System.out.println(swerve.BRWheel.driveMotor.toString());
			/*
			 * System.out.println("Front Right Whool Analog: " +
			 * swerve.FRWheel.rotateMotor.getAnalogInRaw()); System.out.println(
			 * "Front right: " + swerve.FRWheel.rotateMotor.getAnalogInRaw());
			 * System.out.println("Front left: " +
			 * swerve.FLWheel.rotateMotor.getAnalogInRaw()); System.out.println(
			 * "Back right: " + swerve.BRWheel.rotateMotor.getAnalogInRaw());
			 * System.out.println("Back left: " +
			 * swerve.BLWheel.rotateMotor.getAnalogInRaw());
		
		
		
		
		
	/*	
		
		 */
	


	public void retreatFromGearPeg(){
		if(pegRetreating){
		retreatCounter ++;
		swerve.calculateSwerveControl(-0.25, 0, 0);
		if(retreatCounter > 10){ //10 = time to wait before opening door (1/2 seconds)
		gearControl.openGearContainer();
		gearControl.setGearDoorSpeed(.5);
		}
		if(retreatCounter > 60){ //60 = target number of cycles (1/20 second each) before it ends
			pegRetreating = false;
			retreatCounter = 0;
		}
	}
	}
	
}

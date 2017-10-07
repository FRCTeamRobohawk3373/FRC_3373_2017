
package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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

	DigitalInput gearDoorLimit;

	int FLEncoderMin = 300;
	int FLEncoderMax = 300;
	int FREncoderMin = 300;
	int FREncoderMax = 300;
	int BLEncoderMin = 300;
	int BLEncoderMax = 300;
	int BREncoderMin = 300;
	int BREncoderMax = 300;

	boolean hasHitPeg = false;
	boolean truePeg = false;

	boolean hasRunRecording;
	boolean isFirstPlayback;

	int shootTimer = 0;

	boolean hasNotMovedBack;

	String autoSelected;

	CameraServer server;

	DigitalInput ones; // Input for the 16-slot dial
	DigitalInput twos;
	DigitalInput fours;
	DigitalInput eights;

	UltraSonic ultraSonic;
	Climber climber;

	SwerveControl swerve;
	GearController gearControl;
	BallIntake ballIntake;
	Shooter ballDisposal;
	/***************************
	 * Robot Talon Identifier * F * 0 ------ 1 * | | * | | * 2--------3 *
	 ***************************/
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	SendableChooser<String> chooser = new SendableChooser<>();
	SuperJoystick tester;

	int testTimer;
	int angle;
	int intakeCounter;
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

	/*
	 * double robotWidth = 23.125;// TODO CALIBRATE check double robotLength =
	 * 27.56;// TODO CALIBRATE check
	 */

	double robotWidth = 21.125;// TODO CALIBRATE check
	double robotLength = 33.5;// TODO CALIBRATE check

	// Old Values (Biscuit)
	/*
	 * int LBdriveChannel = 2; int LBrotateID = 3; int LBencOffset = 233;
	 * 
	 * int LFdriveChannel = 0; int LFrotateID = 2; int LFencOffset = 128;
	 * 
	 * int RBdriveChannel = 8; int RBrotateID = 0; int RBencOffset = 448;
	 * 
	 * int RFdriveChannel = 1; int RFrotateID = 1; int RFencOffset = 427;
	 */

	// New Values (Shouter)
	int LBdriveChannel = 1;
	int LBrotateID = 2;
	int LBencOffset = 433; // Zero values (value when wheel is turned to default
							// zero- bolt hole facing front.)
	int LBEncMin = 11;
	int LBEncMax = 873;

	int LFdriveChannel = 4;
	int LFrotateID = 3;
	int LFencOffset = 598;
	int LFEncMin = 15;
	int LFEncMax = 894;

	int RBdriveChannel = 8;
	int RBrotateID = 7;
	int RBencOffset = 475;
	int RBEncMin = 12;
	int RBEncMax = 897;

	int RFdriveChannel = 6;
	int RFrotateID = 5;
	int RFencOffset = 207;
	int RFEncMin = 12;
	int RFEncMax = 895;

	/*
	 * int LBdriveChannel = 1; int LBrotateID = 2; int LBencOffset = 423; int
	 * LBEncMin = 11; int LBEncMax = 873;
	 * 
	 * int LFdriveChannel = 4; int LFrotateID = 3; int LFencOffset = 589; int
	 * LFEncMin = 15; int LFEncMax = 894;
	 * 
	 * int RBdriveChannel = 8; int RBrotateID = 7; int RBencOffset = 475; int
	 * RBEncMin = 12; int RBEncMax = 897;
	 * 
	 * int RFdriveChannel = 6; int RFrotateID = 5; int RFencOffset = 210; int
	 * RFEncMin = 11; int RFEncMax = 888;
	 */

	// SuperJoystick driver;
	// SuperJoystick shooter;

	static JoystickOverride driver;
	static JoystickOverride shooter;

	int intakeControlMode = 0;
	int gearControlMode = 0;

	boolean pegRetreating;
	int retreatCounter = 0;
	int retreatTargetCycles = 60;
	
	int unjamTimer = 0;
	boolean unJamming = false;

	int gearAlignCounter = 1000;
	int gearAlignCycleTime = 20;

	int RotateXTimer = 10000;

	boolean pegAssaulting;

	private boolean sRecord = false;
	private boolean sPlayer = false;

	boolean intakeOn;
	boolean shooterOn;
	double intakeTarget = .5;

	int autoCounter = 0;
	boolean autoFinished = false;
	int indexerResetTimer = 0;

	int shooterTimer = 0;

	int gearOpenPos = 400;
	int gearUpPos = 600;
	int gearCompressPos = 650;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		hasRunRecording = false;
		isFirstPlayback = true;
		hasNotMovedBack = true;

		ones = new DigitalInput(0);
		twos = new DigitalInput(1);
		fours = new DigitalInput(2);
		eights = new DigitalInput(3);

		CameraServer.getInstance().startAutomaticCapture(1);
		CameraServer.getInstance().startAutomaticCapture(0);
		// driver = new SuperJoystick(0);
		// shooter = new SuperJoystick(1);

		driver = new JoystickOverride(0);
		shooter = new JoystickOverride(1);
		climber = new Climber(15, 0, 0, 0);
		ultraSonic = new UltraSonic(0);
		swerve = new SwerveControl(LBdriveChannel, LBrotateID, LBencOffset, LBEncMin, LBEncMax, LFdriveChannel,
				LFrotateID, LFencOffset, LFEncMin, LFEncMax, RBdriveChannel, RBrotateID, RBencOffset, RBEncMin,
				RBEncMax, RFdriveChannel, RFrotateID, RFencOffset, RFEncMin, RFEncMax, robotWidth, robotLength);

		gearControl = new GearController(12, gearOpenPos, gearUpPos, gearCompressPos);

		ballIntake = new BallIntake(13);

		ballDisposal = new Shooter(16, 17, 18, 3);

		chooser = new SendableChooser();
		chooser.addDefault("Normal Operation", normal);
		chooser.addObject("Gear Calibration", gearCalibration);
		chooser.addObject("Climber Control", climberControl);
		chooser.addObject("Swerve wheel calibration", swerveWheelCalibration);
		chooser.addObject("Shooter Calibration", shooterCalibration);
		chooser.addObject("Intake Calibration", intakeCalibration);
		chooser.addObject("Hopper Calibration", hopperCalibration);
		SmartDashboard.putData("Calibration Choices", chooser);

		pegAssaulting = false;
		indexerResetTimer = 30;
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
		swerve.setDriveStraightAngle(swerve.ahrs.getAngle());
		sRecord = false;
		this.resetRecordingRun();
		swerve.setRobotFront(3);
		// swerve.ahrs.reset();
		pegRetreating = false;
		pegAssaulting = false;
		swerve.LFWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
		swerve.LBWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
		swerve.RFWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
		swerve.RBWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		int index = 1;// for testing purposes
		if (ones.get()) {
			index += 1;
		}
		if (twos.get()) {
			index += 2;
		}
		if (fours.get()) {
			index += 4;
		}
		if (eights.get()) {
			index += 8;
		}
		if (index == 16) {
			index = 0;
		}
		System.out.println(index);
		switch (index) {
		case 0: // Move forwards
			swerve.driveStraight(.8);
			break;
		case 1: // Gear placement and retreat
			autoCounter++;
			if (!autoFinished) {
				if (gearControl.isPegDetected()) {
					pegRetreating = true;
					autoFinished = true;
					autoCounter = 0;
				} else {
					swerve.driveStraight(.45);
				}
			} else {
				if (autoCounter < 10 && autoCounter != 0) {
					swerve.setRobotFront(3);
					swerve.calculateSwerveControl(-.3, 0, 0);
				}
			}

			this.retreatFromGearPeg();
			break;
		case 2: // playback Move and Shoot (Red)
			this.activateControl();
			this.playbackInput("RedMoveAndShoot.txt");
			break;
		case 3: // Actually is left side gear!!
			this.activateControl();
			this.playbackInput("LeftSideGear.txt");
			break;
		case 4: // Actually is shoot blue!!
			this.activateControl();
			this.playbackInput("BluMoveAndShoot.txt");
			break;
		case 5: // playback Move and dump hopper (Blu)
			this.activateControl();
			this.playbackInput("BluMoveAndDumpHopper.txt");
			break;
		case 6:

			break;
		case 7:

			break;
		case 8:

			break;
		case 9:

			break;
		case 10:

			break;
		case 11:

			break;
		case 12:

			break;
		case 13:

			break;
		case 14: // Red autonomous shooting
			this.autonomousShooting(-.4);
			break;
		case 15:
			this.autonomousShooting(.4);
			break;
		}
		autoCounter++;

	}

	public void teleopInit() {
		sRecord = false;
		this.resetRecordingRun();
		swerve.setRobotFront(3);
		pegRetreating = false;
		pegAssaulting = false;
		swerve.LFWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
		swerve.LBWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
		swerve.RFWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
		swerve.RBWheel.rotateMotor.changeControlMode(TalonControlMode.Position);
		JoystickPlayer.Stop();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		int index = 1;// for testing purposes
		if (ones.get()) {
			index += 1;
		}
		if (twos.get()) {
			index += 2;
		}
		if (fours.get()) {
			index += 4;
		}
		if (eights.get()) {
			index += 8;
		}
		if (index == 16) {
			index = 0;
		}

		System.out.println("INDEXXXX: " + index);

		switch (index) {
		case 0: // Regular Function

			break;
		case 1: // Regular Function

			break;
		case 2: // playback Move and Shoot (Red)

			break;
		case 3: // playback Move and Shoot (Blu)

			break;
		case 4: // playback Move and dump hopper (Red)

			break;
		case 5: // playback Move and dump hopper (Blu)

			break;
		case 6: // record Move and Shoot (Red)
			this.recordInput("RedMoveAndShoot.txt");
			break;
		case 7: // Left Side Gear
			this.recordInput("LeftSideGear.txt");
			break;
		case 8: // Blue Move and Shoot.
			this.recordInput("BluMoveAndShoot.txt");
			break;
		case 9: // record Move and dump hopper (Blu)
			this.recordInput("BluMoveAndDumpHopper.txt");
			break;
		case 10: // Regular Function
			this.playbackInput("RedMoveAndShoot.txt");
			break;
		case 11: // Playback Left Gear
			this.playbackInput("LeftSideGear.txt");
			break;
		case 12: // Playback Blue Move and Shoot
			this.playbackInput("BluMoveAndShoot.txt");
			break;
		case 13: // Regular Function
			this.playbackInput("BluMoveAndDumpHopper.txt");
			break;
		case 14: // Regular Function

			break;
		case 15: // Regular Function

			break;
		}

		this.activateControl();

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testInit() {
		sRecord = false;
		this.resetRecordingRun();
	}

	public void testPeriodic() {
		autoSelected = (String) chooser.getSelected();

		switch (autoSelected) {
		case gearCalibration:
			gearControl.calibrate();
			gearControl.rotateGearDoor.set(shooter.getRawAxis(1) * .3);
			break;
		case climberControl:

			break;
		case swerveWheelCalibration:
			System.out.println("Front Left Encoder: " + swerve.LFWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Front Right Encoder: " + swerve.RFWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Back Left Encoder: " + swerve.LBWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Back Roight Encoder: " + swerve.RBWheel.rotateMotor.getAnalogInRaw());

			swerve.RFWheel.rotateMotor.changeControlMode(TalonControlMode.Disabled);
			swerve.RBWheel.rotateMotor.changeControlMode(TalonControlMode.Disabled);
			swerve.LFWheel.rotateMotor.changeControlMode(TalonControlMode.Disabled);
			swerve.LBWheel.rotateMotor.changeControlMode(TalonControlMode.Disabled);

			if (swerve.RFWheel.rotateMotor.getAnalogInRaw() < FREncoderMin) {
				FREncoderMin = swerve.RFWheel.rotateMotor.getAnalogInRaw();
			}
			if (swerve.RFWheel.rotateMotor.getAnalogInRaw() > FREncoderMax) {
				FREncoderMax = swerve.RFWheel.rotateMotor.getAnalogInRaw();
			}

			if (swerve.RBWheel.rotateMotor.getAnalogInRaw() < BREncoderMin) {
				BREncoderMin = swerve.RBWheel.rotateMotor.getAnalogInRaw();
			}
			if (swerve.RBWheel.rotateMotor.getAnalogInRaw() > BREncoderMax) {
				BREncoderMax = swerve.RBWheel.rotateMotor.getAnalogInRaw();
			}

			if (swerve.LFWheel.rotateMotor.getAnalogInRaw() < FLEncoderMin) {
				FLEncoderMin = swerve.LFWheel.rotateMotor.getAnalogInRaw();
			}
			if (swerve.LFWheel.rotateMotor.getAnalogInRaw() > FLEncoderMax) {
				FLEncoderMax = swerve.LFWheel.rotateMotor.getAnalogInRaw();
			}

			if (swerve.LBWheel.rotateMotor.getAnalogInRaw() < BLEncoderMin) {
				BLEncoderMin = swerve.LBWheel.rotateMotor.getAnalogInRaw();
			}
			if (swerve.LBWheel.rotateMotor.getAnalogInRaw() > BLEncoderMax) {
				BLEncoderMax = swerve.LBWheel.rotateMotor.getAnalogInRaw();
			}
			System.out.println("FL Encoder Min: " + FLEncoderMin);
			System.out.println("FL Encoder Max: " + FLEncoderMax);

			System.out.println("FR Encoder Min: " + FREncoderMin);
			System.out.println("FR Encoder Max: " + FREncoderMax);

			System.out.println("BL Encoder Min: " + BLEncoderMin);
			System.out.println("BL Encoder Max: " + BLEncoderMax);

			System.out.println("BR Encoder Min: " + BREncoderMin);
			System.out.println("BR Encoder Max: " + BREncoderMax);

			System.out.println("Current FL Encoder: " + swerve.LFWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Current FR Encoder: " + swerve.RFWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Current BL Encoder: " + swerve.LBWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Current BR Encoder: " + swerve.RBWheel.rotateMotor.getAnalogInRaw());

			break;
		case shooterCalibration:
			ballDisposal.calibrate(shooter.getRawAxis(LX));
			break;
		case intakeCalibration: // Actually resets NavX.
			swerve.ahrs.reset();

			break;
		case hopperCalibration:
			if (shooter.isAPushed()) {
				intakeTarget += .05;
			}
			shooter.clearA();
			if (shooter.isBPushed()) {
				intakeTarget -= .05;
			}
			shooter.clearB();
			if (shooter.isLBHeld()) {
				ballIntake.ballsOut();
			} else {
				ballIntake.goToSetSpeed(intakeTarget);
			}

			break;
		case normal:
			this.activateControl();
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * /*
	 * 
	 */
	public void resetRecordingRun() {
		hasRunRecording = false;
	}

	public void recordInput(String fileName) {
		if (Robot.driver.isStartHeld()) {
			while (Robot.driver.isStartHeld()) {
			}
			if (!sRecord) {
				sRecord = true;
				try {
					JoystickRecord.RecordInit(fileName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("started");
			} else {
				sRecord = false;
				JoystickRecord.Stop();
				System.out.println("stopped");
			}
		}
		if (sRecord) {
			JoystickRecord.record();
			System.out.println("RECORDING!! " + fileName);
		}
	}

	public void playbackInput(String fileName) {
		if ((Robot.driver.isBackHeld() || !hasRunRecording) && isFirstPlayback) {

			try {
				JoystickPlayer.PlayerInit(fileName);
				// JoystickPlayer.PlayerInit("JoystickRecord.txt");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sPlayer = true;
			hasRunRecording = true;
			isFirstPlayback = false;
		}
		if (sPlayer) {
			sPlayer = !JoystickPlayer.Play();
			System.out.println("Playing back! " + fileName);
		}

	}

	public void retreatFromGearPeg() {

		if (pegRetreating) {
			if (!hasHitPeg) {
				swerve.normalSpeed();
				retreatCounter++;
				swerve.calculateSwerveControl(.35, 0, 0);
				if (gearControl.isPegDetected()) {
					hasHitPeg = true;
					retreatCounter = 0;
					truePeg = true;
				} else if (retreatCounter > 100) {
					hasHitPeg = true;
					retreatCounter = 0;
					truePeg = false;
				}
			} else {
				retreatCounter++;
				swerve.setRobotFront(1);
				swerve.normalSpeed();
				swerve.calculateSwerveControl(0.325, 0, 0); // was .25
				if (retreatCounter > 9) { // 25 = time to wait before opening
											// door
											// (1/2 seconds)
					if (truePeg) {
						gearControl.openGearContainer();
						gearControl.setGearDoorSpeed(.9);
					}
				}
				if (retreatCounter > 100) { // (was) 60 = target number of
											// cycles (1/20
											// second each) before it ends
					pegRetreating = false;
					hasHitPeg = false;
					truePeg = false;
					retreatCounter = 0;
					gearControl.closeGearContainer();
					gearControlMode = 0;
					swerve.setRobotFront(3);
				}
			}
		}
	}

	public void assaultGearPeg() {
		if (pegAssaulting) {
			if (gearControl.isPegDetected()) {
				swerve.setRobotFront(1);
				swerve.calculateSwerveControl(.325, 0, 0);
				pegAssaulting = false;
				pegRetreating = true;
			}
		}
	}

	public void activateControl() {
		gearAlignCounter++;
		ballDisposal.zeroIndexer();
		System.out.println("         ANGlE" + swerve.ahrs.getAngle());
		System.out.println("      Acceleration" + swerve.ahrs.getRawAccelX());

		// SmartDashboard.putNumber("UltraSonic Voltage",
		// ultraSonic.printVoltage());

		SmartDashboard.putNumber("UltraSonic Voltage", ultraSonic.printVoltage());
		SmartDashboard.putNumber("Shooter Voltage", ballDisposal.targetVoltage);
		SmartDashboard.putBoolean("Compressed:", gearControl.isCompressed);
		// shooter.clearBack();
		if (shooter.isRStickHeld()) {
			climber.climb(shooter.getRawAxis(LY));
		} else {
			climber.climb(-Math.abs(shooter.getRawAxis(LY)));
		}
		climber.printCurrent();
		climber.printVoltage();

		if (driver.isYHeld()) {
			RotateXTimer = 0;
			swerve.setSpinAngle(180);
		}

		if (RotateXTimer != 0 && RotateXTimer < 250) {
			swerve.spinXdegrees();
		}

		/*
		 * if (driver.isYPushed()) { swerve.setSpinAngle(180);
		 * swerve.spinXdegrees(); driver.clearY(); } driver.clearY();
		 */
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

		if (!pegRetreating) {
			if (driver.getRawAxis(Rtrigger) > .1) {
				swerve.isFieldCentric = true;
				swerve.calculateSwerveControl(-driver.getRawAxis(LY), driver.getRawAxis(LX), driver.getRawAxis(RX));
			} else if (driver.getRawAxis(Ltrigger) > .1) {
				swerve.isFieldCentric = false;
				swerve.calculateObjectControl(driver.getRawAxis(RX));
			} else {
				swerve.isFieldCentric = false;
				swerve.calculateSwerveControl(-driver.getRawAxis(LY), driver.getRawAxis(LX), driver.getRawAxis(RX));
			}

			if (driver.isLBHeld()) {
				swerve.sniper();
			} else if (driver.isRBHeld()) {
				swerve.turbo();
			} else {
				swerve.normalSpeed();
			}

			if (driver.getPOV() == 0) {
				swerve.setRobotFront(1);
			} else if (driver.getPOV() == 90) {
				swerve.setRobotFront(4);
			} else if (driver.getPOV() == 180) {
				swerve.setRobotFront(3);
			} else if (driver.getPOV() == 270) {
				swerve.setRobotFront(2);
			}

			if (driver.isBHeld()) {
				gearAlignCounter = 0;
			}
			this.gearAlign();

		}

		if (driver.isAHeld()) {
			swerve.setRotateDistance(ultraSonic.getDistance()); // + 17.5 ?
			System.out.print("        US Distance: " + ultraSonic.getDistance());
		}

		if (shooter.isRBHeld()) {
			intakeOn = true;
			intakeCounter++;
		} else {
			intakeCounter = 0;
		}
		if (shooter.isLBPushed()) {
			intakeOn = false;
		}
		if (!intakeOn) {
			ballIntake.ballsOff();
		} else if (intakeOn) {
			if (intakeCounter >= 40) {
				ballIntake.ballsOut();
			} else {
				ballIntake.ballsIn();
			}
		}
		shooter.clearLB();

		if (shooter.isStartPushed()) {
			pegRetreating = true;
		}
		this.retreatFromGearPeg();
		shooter.clearStart();
		SmartDashboard.putBoolean("Peg Assault", pegAssaulting);
		if (shooter.isBackPushed()) {
			climber.setMaxHeightFalse();
			System.out.println("Shooter back is pushed.");
			if (!pegAssaulting) {
				pegAssaulting = true;
				System.out.println("pegAssaulting is now true.");
			} else {
				pegAssaulting = false;
				System.out.println("pegAssaulting is now false.");
			}
		}
		shooter.clearBack();
		this.assaultGearPeg();
		if (shooter.isAPushed()) {
			ballDisposal.determineShooterVoltage(ultraSonic.getDistance() + 17.5);
		}
		shooter.clearA();
		if (shooter.isDPadUpPushed()) {
			ballDisposal.increaseDistanceToTarget();
		}
		if (shooter.isDPadDownPushed()) {
			ballDisposal.decreaseDistanceToTarget();
		}
		ballDisposal.setShooterMotor();
		if (shooter.isYPushed()) {
			gearControlMode += 1;
			gearControlMode = gearControlMode % 2;
			if (gearControlMode == 0) {
				gearControl.closeGearContainer();
			} else if (gearControlMode == 1) {
				gearControl.compressGearContainer();
			}

		}
		shooter.clearY();
		gearControl.setGearDoorSpeed(1);
		this.activateShooter();

		/*
		 * if(joystick.getRawAxis(LY) > .1 || joystick.getRawAxis(LY) < -.1){
		 * climbTalon1.set(joystick.getRawAxis(LY)); }else{ climbTalon1.set(0);
		 * }
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

		if (ballDisposal.shooterMotor.get() != 0) {

			if (shooter.getRawAxis(Rtrigger) > .1) {
				shooterTimer++;
				if (shooterTimer < 25) {
					ballDisposal.setGoingUp();
					ballDisposal.stopRotatingBalls();
				} else {
					ballDisposal.setGoingDown();
					ballDisposal.rotateBalls();
				}
				if (shooterTimer > 65) {
					shooterTimer = 0;
				}
			} else {
				ballDisposal.setGoingDown();
				ballDisposal.stopRotatingBalls();
			}
		}
		if (shooter.getRawAxis(Ltrigger) > .1) {
			ballDisposal.unjam();
		}
		ballDisposal.setIndexerSpeed(.5);
	}

	public void gearAlign() {
		System.out.println("GearAligning!!!");
		if (gearAlignCounter != 0 && gearAlignCounter < gearAlignCycleTime) {
			swerve.calculateSwerveControl(-.4, 0, 0);

		}
	}

	public void autonomousShooting(double sideMovementSpeed) {
		if (ultraSonic.getDistance() < 48 && hasNotMovedBack) {
			swerve.driveStraight(-.8);
		} else if (ultraSonic.getDistance() < 60 && hasNotMovedBack) {
			swerve.driveStraight(-.5);
		} else {
			swerve.driveStraight(0);
			hasNotMovedBack = false;
			shootTimer++;
			shooterTimer++;
			System.out.println(shootTimer);
			ballDisposal.isReset = true;
			ballDisposal.determineShooterVoltage(ultraSonic.getDistance());
			ballDisposal.setShooterMotor();
			// if (shootTimer < 25) {
			// swerve.calculateSwerveControl(0, sideMovementSpeed, 0);
			// } else {
			if (shooterTimer < 25) {
				ballDisposal.setGoingUp();
				ballDisposal.stopRotatingBalls();
			} else {
				ballDisposal.setGoingDown();
				ballDisposal.rotateBalls();
			}

			if (shooterTimer > 65) {
				shooterTimer = 0;
			}
			if (ballDisposal.indexerMotor.getOutputCurrent() > 10) {
				unJamming = true;
				ballDisposal.unjam();
				ballDisposal.setIndexerSpeed(1);
			}
			if(unJamming){
				ballDisposal.unjam();
				unjamTimer ++;
				if(unjamTimer > 8){
					unjamTimer = 0;
					unJamming = false;
				}
			}
			// }

		}
		ballDisposal.setIndexerSpeed(.5);
	}

	public void activateShooter() {
		if (shooter.isXPushed()) {
			ballDisposal.spinUpShooter();
			ballDisposal.rotateBalls();
		}
		shooter.clearX();
		if (shooter.isBPushed()) {
			ballDisposal.disableShooter();
			ballDisposal.stopRotatingBalls();
		}
		shooter.clearB();
	}

}

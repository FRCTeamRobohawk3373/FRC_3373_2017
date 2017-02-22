
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

	String autoSelected;

	CameraServer server;

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
	int LBencOffset = 423;
	int LBEncMin = 11;
	int LBEncMax = 873;

	int LFdriveChannel = 4;
	int LFrotateID = 3;
	int LFencOffset = 607;
	int LFEncMin = 15;
	int LFEncMax = 894;

	int RBdriveChannel = 8;
	int RBrotateID = 7;
	int RBencOffset = 475;
	int RBEncMin = 12;
	int RBEncMax = 897;

	int RFdriveChannel = 6;
	int RFrotateID = 5;
	int RFencOffset = 210;
	int RFEncMin = 13;
	int RFEncMax = 888;

	// SuperJoystick driver;
	// SuperJoystick shooter;

	static JoystickOverride driver;
	static JoystickOverride shooter;

	int intakeControlMode = 0;
	int gearControlMode = 0;

	boolean pegRetreating;
	int retreatCounter = 0;
	int retreatTargetCycles = 60;

	boolean pegAssaulting;

	private boolean sRecord = false;
	private boolean sPlayer = false;

	boolean intakeOn;
	double intakeTarget = .5;

	int autoCounter = 0;
	boolean autoFinished = false;
	int indexerResetTimer = 0;

	int shooterTimer = 0;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
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

		gearControl = new GearController(12, 275, 542, 580);
		gearDoorLimit = new DigitalInput(0);

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

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		/*
		 * autoCounter ++; if(ultraSonic.getDistance() > 9 && !autoFinished){
		 * swerve.driveStraight(.5); } else if(!gearDoorLimit.get() &&
		 * !autoFinished){ swerve.driveStraight(.2); } else{
		 * this.retreatFromGearPeg(); autoFinished = true; }
		 */

		// ballDisposal.calibrate(shooter.getRawAxis(LX));
		System.out.println("EFWEFWE: " + swerve.ahrs.getYaw() + "     " + swerve.ahrs.getAngle());

		// this.retreatFromGearPeg();
		/*
		 * System.out.println("Front Left Encoder: " +
		 * swerve.LFWheel.rotateMotor.getAnalogInRaw()); System.out.println(
		 * "Front Right Encoder: " +
		 * swerve.RFWheel.rotateMotor.getAnalogInRaw()); System.out.println(
		 * "Back Left Encoder: " + swerve.LBWheel.rotateMotor.getAnalogInRaw());
		 * System.out.println("Back Roight Encoder: " +
		 * swerve.RBWheel.rotateMotor.getAnalogInRaw());
		 */

	}

	public void teleopInit() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		ballDisposal.zeroIndexer();

		SmartDashboard.putNumber("UltraSonic Voltage", ultraSonic.printVoltage());
		if (shooter.isBackPushed()) {
			climber.setMaxHeightFalse();
		}
		shooter.clearBack();
		climber.climb(shooter.getRawAxis(LY));
		climber.printCurrent();
		climber.printVoltage();
		if (driver.isYPushed()) {
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

		}

		if (driver.isAHeld()) {
			swerve.setRotateDistance(ultraSonic.getDistance());
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
			if (!pegAssaulting) {
				pegAssaulting = true;
			} else {
				pegAssaulting = false;
			}
		}
		shooter.clearBack();
		this.assaultGearPeg();
		if (shooter.isAPushed()) {
			ballDisposal.determineShooterVoltage(ultraSonic.getDistance());
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
		shooterTimer++;
		if (shooter.getRawAxis(Ltrigger) > .1) {
			if (shooterTimer < 30) {
				ballDisposal.setGoingUp();
				ballDisposal.stopRotatingBalls();
			} else if (30 < shooterTimer && shooterTimer < 50) {
				ballDisposal.setGoingDown();
				ballDisposal.rotateBalls();
			} else {
				shooterTimer = 0;
			}
		} else {
			ballDisposal.setGoingDown();
			ballDisposal.stopRotatingBalls();
		}

		ballDisposal.setIndexerSpeed(.5);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testInit() {
		sRecord = false;

	}

	public void testPeriodic() {
		autoSelected = (String) chooser.getSelected();

		switch (autoSelected) {
		case gearCalibration:
			gearControl.calibrate();
			gearControl.rotateGearDoor.set(shooter.getRawAxis(1) * .1);
			break;
		case climberControl:

			break;
		case swerveWheelCalibration:
			System.out.println("Front Left Encoder: " + swerve.LFWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Front Right Encoder: " + swerve.RFWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Back Left Encoder: " + swerve.LBWheel.rotateMotor.getAnalogInRaw());
			System.out.println("Back Roight Encoder: " + swerve.RBWheel.rotateMotor.getAnalogInRaw());

			swerve.RFWheel.rotateMotor.changeControlMode(TalonControlMode.Disabled);

			if (swerve.RFWheel.rotateMotor.getAnalogInRaw() < FLEncoderMin) {
				FLEncoderMin = swerve.RFWheel.rotateMotor.getAnalogInRaw();
			}
			if (swerve.RFWheel.rotateMotor.getAnalogInRaw() > FLEncoderMax) {
				FLEncoderMax = swerve.RFWheel.rotateMotor.getAnalogInRaw();
			}
			System.out.println("Encoder Min: " + FLEncoderMin);
			System.out.println("Encoder Max: " + FLEncoderMax);

			break;
		case shooterCalibration:
			ballDisposal.calibrate(shooter.getRawAxis(LX));
			break;
		case intakeCalibration:

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

			if (Robot.driver.isStartHeld()) {
				while (Robot.driver.isStartHeld()) {
				}
				if (!sRecord) {
					sRecord = true;
					try {
						JoystickRecord.RecordInit("JoystickRecord.txt");
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
			}
			if (Robot.driver.isBackHeld()) {
				while (Robot.driver.isBackHeld()) {
				}
				try {
					JoystickPlayer.PlayerInit("JoystickRecord.txt");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sPlayer = true;
			}
			if (sPlayer) {
				sPlayer = !JoystickPlayer.Play();
			}

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

			if (shooter.isBackPushed()) {
				intakeControlMode += 1;
				intakeControlMode = intakeControlMode % 2;
				if (intakeControlMode == 0) {
					ballIntake.ballsIn();
				} else if (intakeControlMode == 1) {
					ballIntake.ballsOut();
				} else {
					ballIntake.ballsOff();
				}

				if (shooter.isBackPushed()) {
					climber.setMaxHeightFalse();

				}
				shooter.clearBack();
				climber.climb(shooter.getRawAxis(LY));
				climber.printCurrent();
				climber.printVoltage();
				if (driver.isYPushed()) {
					swerve.setSpinAngle(180);
					swerve.spinXdegrees();
					driver.clearY();
				}
				driver.clearY();
				/*
				 * if (!swerve.aligned()) { swerve.swerveAlign();
				 * System.out.println( "Front Left: " +
				 * swerve.FLWheel.rotateMotor.getAnalogInRaw());
				 * System.out.println("Front Right: " +
				 * swerve.FRWheel.rotateMotor.getAnalogInRaw());
				 * System.out.println( "Back Left: " +
				 * swerve.BLWheel.rotateMotor.getAnalogInRaw());
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
						swerve.calculateSwerveControl(-driver.getRawAxis(LY), driver.getRawAxis(LX),
								driver.getRawAxis(RX));
					} else if (driver.getRawAxis(Ltrigger) > .1) {
						swerve.isFieldCentric = false;
						swerve.calculateObjectControl(driver.getRawAxis(RX));
					} else {
						swerve.isFieldCentric = false;
						swerve.calculateSwerveControl(-driver.getRawAxis(LY), driver.getRawAxis(LX),
								driver.getRawAxis(RX));
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

				}

				if (driver.isAHeld()) {
					swerve.setRotateDistance(ultraSonic.getDistance());
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

				if (shooter.isAPushed()) {
					ballDisposal.determineShooterVoltage(ultraSonic.getDistance());
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
					System.out.println("Switching gear control mode");
					if (gearControlMode == 0) {
						gearControl.closeGearContainer();
						System.out.println("Closing");
					} else if (gearControlMode == 1) {
						gearControl.compressGearContainer();
						System.out.println("compressing");
					}

				}
				shooter.clearY();
				gearControl.setGearDoorSpeed(1);
				if (shooter.isXPushed()) {
					ballDisposal.spinUpShooter();
				}
				shooter.clearX();
				if (shooter.isBPushed()) {
					ballDisposal.disableShooter();
				}
				shooter.clearB();

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
				 * if (driver.isLBHeld()) { // Turbo Mode
				 * swerve.setSpeedMode(.8); } else if (driver.isRBHeld()) { //
				 * Sniper Mode swerve.setSpeedMode(0.20); } else { // Regular
				 * mode swerve.setSpeedMode(0.5); }
				 */
			}

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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * /*
	 * 
	 */

	public void retreatFromGearPeg() {
		if (pegRetreating) {
			retreatCounter++;
			swerve.setRobotFront(1);
			swerve.calculateSwerveControl(0.25, 0, 0);
			if (retreatCounter > 17) { // 25 = time to wait before opening door
										// (1/2 seconds)
				gearControl.openGearContainer();
				gearControl.setGearDoorSpeed(.5);
			}
			if (retreatCounter > 60) { // 60 = target number of cycles (1/20
										// second each) before it ends
				pegRetreating = false;
				retreatCounter = 0;
				gearControl.closeGearContainer();
				gearControlMode = 0;
				swerve.setRobotFront(3);
			}
		}
	}

	public void assaultGearPeg() {
		if (pegAssaulting) {
			if (gearControl.isPegDetected()) {
				swerve.calculateSwerveControl(0, 0, 0);
				pegAssaulting = false;
				pegRetreating = true;
			}
		}
	}
}

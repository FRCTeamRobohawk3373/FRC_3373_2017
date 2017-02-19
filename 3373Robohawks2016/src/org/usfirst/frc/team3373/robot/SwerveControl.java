package org.usfirst.frc.team3373.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveControl {
	// pushing

	double p = 10; // 100 is very close
	double i = 0;
	double d = 100;
	boolean isRobotCentric = true;
	boolean isFieldCentric = false;
	boolean isObjectCentric = false;
	boolean isHookCentric = false;
	double spinAngle = 0;
	double angleToDiagonal;
	double robotLength;
	double robotWidth;
	double objectRadius = 48;

	boolean isToSpinAngle = false;

	double radius;

	double orientationOffset;
	double sensorToWheelDistance = 3.25;
	double boilerRadius = 10;

	int robotFront = 1; // Which side of the robot is the front. 1 is default,
						// goes up to 4.

	SwerveWheel LBWheel;
	SwerveWheel LFWheel;
	SwerveWheel RBWheel;
	SwerveWheel RFWheel;

	SwerveWheel[] wheelArray1;
	SwerveWheel[] wheelArray2;

	AHRS ahrs;

	public SwerveControl(int LBdriveChannel, int LBrotateID, int LBencOffset, int LFdriveChannel, int LFrotateID,
			int LFencOffset, int RBdriveChannel, int RBrotateID, int RBencOffset, int RFdriveChannel, int RFrotateID,
			int RFencOffset, double width, double length) {
		robotWidth = width;
		robotLength = length;
		angleToDiagonal = Math.toDegrees(Math.atan2(length, width));
		LBWheel = new SwerveWheel(LBdriveChannel, LBrotateID, p, i, d, -(270 - angleToDiagonal), 0, LBencOffset);
		LFWheel = new SwerveWheel(LFdriveChannel, LFrotateID, p, i, d, -(angleToDiagonal + 90), 0, LFencOffset);
		RBWheel = new SwerveWheel(RBdriveChannel, RBrotateID, p, i, d, -(angleToDiagonal + 270), 0, RBencOffset);
		RFWheel = new SwerveWheel(RFdriveChannel, RFrotateID, p, i, d, -(90 - angleToDiagonal), 0, RFencOffset);

		wheelArray1 = new SwerveWheel[] { LFWheel, RBWheel };
		wheelArray2 = new SwerveWheel[] { LBWheel, RFWheel };
		ahrs = new AHRS(SerialPort.Port.kUSB1);
	}

	public void turnToAngle(double x, double y) {
		for (SwerveWheel wheel : wheelArray1) {
			wheel.setTargetAngle(LBWheel.calculateTargetAngle(x, y));
			// if(y < .03 &&)
			wheel.rotate();
		}
		for (SwerveWheel wheel : wheelArray2) {
			wheel.setTargetAngle(LBWheel.calculateTargetAngle(x, y));
			// if(y < .03 &&)
			wheel.rotate();
		}
	}

	public void setRotateDistance(double distance) {
		objectRadius = distance + boilerRadius + sensorToWheelDistance;
	}

	public void setSpeed(double x, double y) {
		if ((Math.abs(x) > .1) || (Math.abs(y) > .1)) {
			LBWheel.drive(x, y);
			LFWheel.drive(x, y);
			RBWheel.drive(x, y);
			RFWheel.drive(x, y);
		} else {
			LBWheel.drive(0, 0);
			LFWheel.drive(0, 0);
			RBWheel.drive(0, 0);
			RFWheel.drive(0, 0);
		}
	}

	public void drive(double x, double y) {
		this.turnToAngle(x, y);
		this.setSpeed(x, y);
	}

	public void setRobotFront(int side) {
		if (side >= 1 && side <= 4) {
			robotFront = side;
		} else {
			robotFront = 1;
		}
	}

	public void calculateSwerveControl(double LY, double LX, double RX) {
		SmartDashboard.putNumber("LY", LY);
		SmartDashboard.putNumber("LX", LX);
		SmartDashboard.putNumber("RX", RX);
		SmartDashboard.putNumber("Encoder", LFWheel.rotateMotor.getAnalogInRaw());

		double translationalXComponent = LX;
		double translationalYComponent = LY;
		double translationalMagnitude;
		double translationalAngle;

		double rAxis = RX;
		double rotateXComponent;
		double rotateYComponent;
		double fastestSpeed = 0;

		// Deadband
		if (Math.abs(LX) < 0.1) {
			translationalXComponent = 0;
			LX = 0;
		}

		if (Math.abs(LY) < 0.1) {
			translationalYComponent = 0;
			LY = 0;
		}

		if (Math.abs(RX) < 0.1) {
			rAxis = 0;
			RX = 0;
		}
		if (isFieldCentric) {
			orientationOffset = ahrs.getYaw(); // if in field centric mode make
												// offset equal to the current
												// angle of the navX
		} else {
			orientationOffset = 0;
			if (robotFront == 1) {
				orientationOffset += 0;
			} else if (robotFront == 2) {
				orientationOffset += 90;
			} else if (robotFront == 3) {
				orientationOffset += 180;
			} else if (robotFront == 4) {
				orientationOffset += 270;
			}
		}

		orientationOffset = orientationOffset % 360;

		double rotationMagnitude = Math.abs(rAxis);

		translationalMagnitude = Math.sqrt(Math.pow(translationalYComponent, 2) + Math.pow(translationalXComponent, 2));
		translationalAngle = Math.toDegrees(Math.atan2(translationalYComponent, translationalXComponent));

		translationalAngle += orientationOffset; // sets the robot front to be
													// at the angle determined
													// by orientationOffset
		translationalAngle = translationalAngle % 360;
		if (translationalAngle < 0) {
			translationalAngle += 360;
		}

		translationalYComponent = Math.sin(Math.toRadians(translationalAngle)) * translationalMagnitude; // calculates
																											// y
																											// component
																											// of
																											// translation
																											// vector
		translationalXComponent = Math.cos(Math.toRadians(translationalAngle)) * translationalMagnitude; // calculates
																											// x
																											// component
																											// of
																											// translation
																											// vector

		for (SwerveWheel wheel : wheelArray1) {

			rotateXComponent = Math.cos(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; // calculates
																								// x
																								// component
																								// of
																								// rotation
																								// vector
			rotateYComponent = Math.sin(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; // calculates
																								// y
																								// component
																								// of
																								// rotation
																								// vector

			if (rAxis > 0) {// Why do we do this?
				rotateXComponent = -rotateXComponent;
				rotateYComponent = -rotateYComponent;
			}

			wheel.setSpeed(Math.sqrt(Math.pow(rotateXComponent + translationalXComponent, 2)
					+ Math.pow((rotateYComponent + translationalYComponent), 2)));// sets
																					// the
																					// speed
																					// based
																					// off
																					// translational
																					// and
																					// rotational
																					// vectors
			wheel.setTargetAngle((Math.toDegrees(Math.atan2((rotateYComponent + translationalYComponent),
					(rotateXComponent + translationalXComponent)))));// sets the
																		// target
																		// angle
																		// based
																		// off
																		// translation
																		// and
																		// rotational
																		// vectors
			/*
			 * if(LY == 0 && LX == 0 && RX == 0){//if our inputs are nothing,
			 * don't change the angle(use currentAngle as targetAngle)
			 * wheel.setTargetAngle(wheel.getCurrentAngle()); }
			 */

			if (wheel.getSpeed() > fastestSpeed) {// if speed of wheel is
													// greater than the others
													// store the value
				fastestSpeed = wheel.getSpeed();
			}
		}
		for (SwerveWheel wheel : wheelArray2) {

			rotateXComponent = Math.cos(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; // calculates
																								// x
																								// component
																								// of
																								// rotation
																								// vector
			rotateYComponent = Math.sin(Math.toRadians(wheel.getRAngle())) * rotationMagnitude; // calculates
																								// y
																								// component
																								// of
																								// rotation
																								// vector

			if (rAxis > 0) {// Why do we do this?
				rotateXComponent = -rotateXComponent;
				rotateYComponent = -rotateYComponent;
			}

			wheel.setSpeed(Math.sqrt(Math.pow(rotateXComponent + translationalXComponent, 2)
					+ Math.pow((rotateYComponent + translationalYComponent), 2)));// sets
																					// the
																					// speed
																					// based
																					// off
																					// translational
																					// and
																					// rotational
																					// vectors
			wheel.setTargetAngle((Math.toDegrees(Math.atan2((-rotateYComponent + translationalYComponent),
					(-rotateXComponent + translationalXComponent)))));// sets
																		// the
																		// target
																		// angle
																		// based
																		// off
																		// translation
																		// and
																		// rotational
																		// vectors
			/*
			 * if(LY == 0 && LX == 0 && RX == 0){//if our inputs are nothing,
			 * don't change the angle(use currentAngle as targetAngle)
			 * wheel.setTargetAngle(wheel.getCurrentAngle()); }
			 */

			if (wheel.getSpeed() > fastestSpeed) {// if speed of wheel is
													// greater than the others
													// store the value
				fastestSpeed = wheel.getSpeed();
			}
			
		}

		if (fastestSpeed > 1) { // if the fastest speed is greater than 1(our
								// max input) divide the target speed for each
								// wheel by the fastest speed
			for (SwerveWheel wheel : wheelArray1) {
				wheel.setSpeed(wheel.getSpeed() / fastestSpeed);
			}
			for (SwerveWheel wheel : wheelArray2) {
				wheel.setSpeed(wheel.getSpeed() / fastestSpeed);
			}
		}

		// Makes the wheels go to calculated target angle
		/*
		 * RFWheel.goToAngle(); LFWheel.goToAngle(); RBWheel.goToAngle();
		 * LBWheel.goToAngle();
		 */
		//if (LY > .05 && LX > .05 && RX > .05) {
			
		
			// Make the wheels drive at their calculated speed
			
		if(LY < .05 && LX < .05 && RX < .05 && LY > -.05 && LX > -.05 && RX > -.05){
			RFWheel.setCurrentPosition();
			LFWheel.setCurrentPosition();
			LBWheel.setCurrentPosition();
			RBWheel.setCurrentPosition(); 
		}else{
			RFWheel.rotate();
			LFWheel.rotate();
			LBWheel.rotate();
			RBWheel.rotate();
		}
			RFWheel.driveWheel();
			LFWheel.driveWheel();
			RBWheel.driveWheel();
			LBWheel.driveWheel();

	}

	public void calculateObjectControl(double RX) {

		radius = objectRadius;

		// TODO change radius to ultrasonic input in order for this to work
		// Radius: distance to front of robot (lengthwise)

		double distanceToFront = radius;
		double distanceToBack = radius + robotLength;

		LFWheel.setTargetAngle(180 + Math.toDegrees(Math.atan2(robotWidth / 2, distanceToFront)));
		RFWheel.setTargetAngle(180 - Math.toDegrees(Math.atan2(robotWidth / 2, distanceToFront)));
		LBWheel.setTargetAngle(180 + Math.toDegrees(Math.atan2(robotWidth / 2, distanceToBack)));
		RBWheel.setTargetAngle(180 - Math.toDegrees(Math.atan2(robotWidth / 2, distanceToBack)));

		LBWheel.setSpeed(RX);
		RBWheel.setSpeed(RX);

		double speedRatio = Math.sqrt(Math.pow((robotWidth / 2), 2) + Math.pow(distanceToFront, 2))
				/ Math.sqrt(Math.pow((robotWidth / 2), 2) + Math.pow(distanceToBack, 2));

		LFWheel.setSpeed(speedRatio * RX);
		RFWheel.setSpeed(speedRatio * RX);

		RFWheel.rotate();
		LFWheel.rotate();
		LBWheel.rotate();
		RBWheel.rotate();

		// Make the wheels drive at their calculated speed
		RFWheel.driveWheel();
		LFWheel.driveWheel();
		RBWheel.driveWheel();
		LBWheel.driveWheel();
	}

	public void turbo() {
		LFWheel.setSpeedModifier(.8);
		LBWheel.setSpeedModifier(.8);
		RFWheel.setSpeedModifier(.8);
		RBWheel.setSpeedModifier(.8);
	}

	public void sniper() {
		LFWheel.setSpeedModifier(.2);
		LBWheel.setSpeedModifier(.2);
		RFWheel.setSpeedModifier(.2);
		RBWheel.setSpeedModifier(.2);
	}

	public void normalSpeed() {
		LFWheel.setSpeedModifier(.5);
		LBWheel.setSpeedModifier(.5);
		RFWheel.setSpeedModifier(.5);
		RBWheel.setSpeedModifier(.5);
	}

	public void setSpinAngle(int angle) {
		System.out.println("SETTING ANGLE");
		spinAngle = angle + ahrs.getAngle();
		isToSpinAngle = false;
	}

	public void spinXdegrees() {
		while (!isToSpinAngle) {
			System.out.println("Spinning");
			if (ahrs.getAngle() < spinAngle - 50) {
				calculateSwerveControl(0, 0, .8);
				isToSpinAngle = false;
			} else if (ahrs.getAngle() < spinAngle - .2) {
				calculateSwerveControl(0, 0, .6);
				isToSpinAngle = false;
			} else {
				calculateSwerveControl(0, 0, 0);
				isToSpinAngle = true;
			}
		}
	}

}

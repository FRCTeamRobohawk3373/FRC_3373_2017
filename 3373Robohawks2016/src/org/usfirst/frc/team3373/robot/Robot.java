
package org.usfirst.frc.team3373.robot;

import java.io.IOException;

import com.ctre.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
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

	//static SuperJoystick driver;
	//static SuperJoystick shooter;

	static JoystickOverride driver;
	static JoystickOverride shooter;

	static CANTalon motor1;
	static CANTalon motor2;
	static CANTalon motor3;
	static CANTalon motor4;

	private boolean sRecord;
	private boolean sPlayer;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);

		motor1 = new CANTalon(1);
		motor2 = new CANTalon(2);
		motor3 = new CANTalon(11);
		motor4 = new CANTalon(12);

		//driver = new SuperJoystick(0);
		//shooter = new SuperJoystick(1);

		driver = new JoystickOverride(0);
		shooter = new JoystickOverride(1);
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
	public void teleopInit() {
		try {
			JoystickPlayer.PlayerInit("JoystickRecord1.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void teleopPeriodic() {
		if (Robot.driver.isBackHeld()) {
			while (Robot.driver.isBackHeld()) {
			}
			sPlayer = true;
		}
		if (sPlayer) {
			sPlayer = !JoystickPlayer.Play();
		}
		// if (JoystickPlayer.driverButtons[0].equalsIgnoreCase("true")) {
		// System.out.println("bytes 1");
		// Robot.motor1.set(1);
		// // JoystickPlayer.driverButtons[0] = "false";
		// } else {
		// Robot.motor1.set(0);
		// }
		// if (JoystickPlayer.driverButtons[1].equalsIgnoreCase("true")) {
		// System.out.println("bytes 2");
		// Robot.motor2.set(1);
		// // JoystickPlayer.driverButtons[1] = "false";
		// } else {
		// Robot.motor2.set(0);
		// }
		// if (JoystickPlayer.driverButtons[2].equalsIgnoreCase("true")) {
		// System.out.println("bytes 3");
		// Robot.motor3.set(.5);
		// // JoystickPlayer.driverButtons[2] = "false";
		// } else {
		// Robot.motor3.set(0);
		// }
		// if (JoystickPlayer.driverButtons[3].equalsIgnoreCase("true")) {
		// System.out.println("bytes 4");
		// Robot.motor4.set(.5);
		// // JoystickPlayer.driverButtons[3] = "false";
		// } else {
		// Robot.motor4.set(0);
		// }

		if (Robot.driver.isAHeld()) {
			Robot.motor1.set(1);
		} else {
			Robot.motor1.set(0);
		}
		if (Robot.driver.isBHeld()) {
			Robot.motor2.set(1);
		} else {
			Robot.motor2.set(0);
		}
		if (Robot.driver.isXHeld()) {

			Robot.motor3.set(.3);
		} else {
			Robot.motor3.set(0);
		}
		if (Robot.driver.isYHeld()) {
			Robot.motor4.set(.5);
		} else {
			Robot.motor4.set(0);
		}

		// Robot.motor1.set(Float.parseFloat(JoystickPlayer.driverButtons[11]));
		// Robot.motor2.set(Float.parseFloat(JoystickPlayer.driverButtons[12]));
		// Robot.motor3.set(Float.parseFloat(JoystickPlayer.driverButtons[13]));
		// Robot.motor4.set(Float.parseFloat(JoystickPlayer.driverButtons[15]));

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testInit() {
		sRecord = false;
		try {
			JoystickRecord.RecordInit("JoystickRecord1.txt", 200);
			JoystickPlayer.PlayerInit("JoystickRecord1.txt");
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
			sRecord = !JoystickRecord.record();
		}
		if (Robot.driver.isBackHeld()) {
			while (Robot.driver.isBackHeld()) {
			}
			sPlayer = true;
		}
		if (sPlayer) {
			sPlayer = !JoystickPlayer.Play();
		}
		

//		if (Robot.driver.isAHeld()) {
//			Robot.motor1.set(1);
//		} else {
//			Robot.motor1.set(0);
//		}
//		if (Robot.driver.isBHeld()) {
//			Robot.motor2.set(1);
//		} else {
//			Robot.motor2.set(0);
//		}
//		if (Robot.driver.isXHeld()) {
//
//			Robot.motor3.set(.3);
//		} else {
//			Robot.motor3.set(0);
//		}
//		if (Robot.driver.isYHeld()) {
//			Robot.motor4.set(.5);
//		} else {
//			Robot.motor4.set(0);
//		}

		Robot.motor1.set(Robot.shooter.getAxis(1));
		Robot.motor2.set(Robot.shooter.getAxis(5));
		Robot.motor3.set(Robot.driver.getAxis(3));
		Robot.motor4.set(Robot.driver.getAxis(2));

		// Robot.driver.clearButtons();

	}
}

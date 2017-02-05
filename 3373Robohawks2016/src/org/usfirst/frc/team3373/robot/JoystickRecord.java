package org.usfirst.frc.team3373.robot;

//@author Kofi Asenso and Matthew Heller
import java.io.*;

public class JoystickRecord {

	private static int duration;
	private static int count;
	private static BufferedWriter printline;
	//private static FileWriter fw;

	public static void RecordInit(String fileLabel, int tlength) throws Exception {
		try {
			duration = tlength;
			File f = new File("/home/lvuser/JoystickRecords/" + fileLabel);

			if (f.exists()) {
				f.delete();
				f.createNewFile();
			} else {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f);
			printline = new BufferedWriter(fw);
			count = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean record() {
		String stringdata = "";
		try {
			if (count < duration) {
				stringdata = RecordDriver();
				printline.write(stringdata);
				printline.newLine();
				//System.out.println("0: " + stringdata);

				stringdata = RecordShooter();
				printline.write(stringdata);
				printline.newLine();
				//System.out.println("1: " + stringdata);
				count++;
			} else if (count >= duration) {
//				printline.write(
//						"false false false false false false false false false false 0.0 0.0 0.0 0.0 0.0 0.0 -1");
//				printline.newLine();
//				printline.write(
//						"false false false false false false false false false false 0.0 0.0 0.0 0.0 0.0 0.0 -1");
//				printline.newLine();

				printline.flush();
				printline.close();
				System.out.println("file length: " + count * 2);
				//printline = new BufferedWriter(fw);
				count++;
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	static String RecordDriver() {
		String Buttons = "";
		if (Robot.driver.isAHeld()) {
			Buttons += "true ";
			// System.out.println("A pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isBHeld()) {
			Buttons += "true ";
			// System.out.println("B pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isXHeld()) {
			Buttons += "true ";
			// System.out.println("X pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isYHeld()) {
			Buttons += "true ";
			// System.out.println("Y pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isLBHeld()) {
			Buttons += "true ";
			// System.out.println("LB pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isRBHeld()) {
			Buttons += "true ";
			// System.out.println("RB pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isBackHeld()) {
			Buttons += "true ";
			// System.out.println("Back pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isStartHeld()) {
			Buttons += "true ";
			// System.out.println("Start pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isLStickHeld()) {
			Buttons += "true ";
			// System.out.println("LStickPushed");
		} else {
			Buttons += "false ";
		}

		if (Robot.driver.isRStickHeld()) {
			Buttons += "true ";
			// System.out.println("RStickPushed");
		} else {
			Buttons += "false ";
		}

		// System.out.println("LX Axis:" + Robot.driver.getRawAxis(0));
		// System.out.println("LY Axis:" + Robot.driver.getRawAxis(1));
		// System.out.println("L Trigger:" + Robot.driver.getRawAxis(2));
		// System.out.println("R Trigger:" + Robot.driver.getRawAxis(3));
		// System.out.println("RX Axis:" + Robot.driver.getRawAxis(4));
		// System.out.println("RY Axis:" + Robot.driver.getRawAxis(5));
		Buttons += Robot.driver.getRawAxis(0) + " ";
		Buttons += Robot.driver.getRawAxis(1) + " ";
		Buttons += Robot.driver.getRawAxis(2) + " ";
		Buttons += Robot.driver.getRawAxis(3) + " ";
		Buttons += Robot.driver.getRawAxis(4) + " ";
		Buttons += Robot.driver.getRawAxis(5) + " ";

		Buttons += Robot.driver.getPOV(0);
		// System.out.println("Buttons");
		return Buttons;
	}

	static String RecordShooter() {
		String Buttons = "";
		if (Robot.shooter.isAHeld()) {
			Buttons += "true ";
			// System.out.println("A pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isBHeld()) {
			Buttons += "true ";
			// System.out.println("B pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isXHeld()) {
			Buttons += "true ";
			// System.out.println("X pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isYHeld()) {
			Buttons += "true ";
			// System.out.println("Y pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isLBHeld()) {
			Buttons += "true ";
			// System.out.println("LB pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isRBHeld()) {
			Buttons += "true ";
			// System.out.println("RB pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isBackHeld()) {
			Buttons += "true ";
			// System.out.println("Back pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isStartHeld()) {
			Buttons += "true ";
			// System.out.println("Start pressed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isLStickHeld()) {
			Buttons += "true ";
			// System.out.println("LStickPushed");
		} else {
			Buttons += "false ";
		}

		if (Robot.shooter.isRStickHeld()) {
			Buttons += "true ";
			// System.out.println("RStickPushed");
		} else {
			Buttons += "false ";
		}

		// System.out.println("LX Axis:" + Robot.shooter.getRawAxis(0));
		// System.out.println("LY Axis:" + Robot.shooter.getRawAxis(1));
		// System.out.println("L Trigger:" + Robot.shooter.getRawAxis(2));
		// System.out.println("R Trigger:" + Robot.shooter.getRawAxis(3));
		// System.out.println("RX Axis:" + Robot.shooter.getRawAxis(4));
		// System.out.println("RY Axis:" + Robot.shooter.getRawAxis(5));

		Buttons += Robot.shooter.getRawAxis(0) + " ";
		Buttons += Robot.shooter.getRawAxis(1) + " ";
		Buttons += Robot.shooter.getRawAxis(2) + " ";
		Buttons += Robot.shooter.getRawAxis(3) + " ";
		Buttons += Robot.shooter.getRawAxis(4) + " ";
		Buttons += Robot.shooter.getRawAxis(5) + " ";

		Buttons += Robot.shooter.getPOV(0);
		// System.out.println(Buttons);
		return Buttons;
	}

}

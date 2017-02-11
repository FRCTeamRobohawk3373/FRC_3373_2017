package org.usfirst.frc.team3373.robot;

//@author Kofi Asenso and Matthew Heller
import java.io.*;

public class JoystickRecord {

	private static int duration;
	private static int count;
	private static BufferedWriter printline;
	static JoystickOverride driver = new JoystickOverride(0);
	static JoystickOverride shooter = new JoystickOverride(1);
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
		if (driver.isAHeld()) {
			Buttons += "true ";
			// System.out.println("A pressed");
		} else {
			Buttons += "false ";
		}

		if (driver.isBHeld()) {
			Buttons += "true ";
			// System.out.println("B pressed");
		} else {
			Buttons += "false ";
		}

		if (driver.isXHeld()) {
			Buttons += "true ";
			// System.out.println("X pressed");
		} else {
			Buttons += "false ";
		}

		if (driver.isYHeld()) {
			Buttons += "true ";
			// System.out.println("Y pressed");
		} else {
			Buttons += "false ";
		}

		if (driver.isLBHeld()) {
			Buttons += "true ";
			// System.out.println("LB pressed");
		} else {
			Buttons += "false ";
		}

		if (driver.isRBHeld()) {
			Buttons += "true ";
			// System.out.println("RB pressed");
		} else {
			Buttons += "false ";
		}

		if (driver.isBackHeld()) {
			Buttons += "true ";
			// System.out.println("Back pressed");
		} else {
			Buttons += "false ";
		}

		if (driver.isStartHeld()) {
			Buttons += "true ";
			// System.out.println("Start pressed");
		} else {
			Buttons += "false ";
		}

		if (driver.isLStickHeld()) {
			Buttons += "true ";
			// System.out.println("LStickPushed");
		} else {
			Buttons += "false ";
		}

		if (driver.isRStickHeld()) {
			Buttons += "true ";
			// System.out.println("RStickPushed");
		} else {
			Buttons += "false ";
		}

		// System.out.println("LX Axis:" + driver.getRawAxis(0));
		// System.out.println("LY Axis:" + driver.getRawAxis(1));
		// System.out.println("L Trigger:" + driver.getRawAxis(2));
		// System.out.println("R Trigger:" + driver.getRawAxis(3));
		// System.out.println("RX Axis:" + driver.getRawAxis(4));
		// System.out.println("RY Axis:" + driver.getRawAxis(5));
		Buttons += driver.getRawAxis(0) + " ";
		Buttons += driver.getRawAxis(1) + " ";
		Buttons += driver.getRawAxis(2) + " ";
		Buttons += driver.getRawAxis(3) + " ";
		Buttons += driver.getRawAxis(4) + " ";
		Buttons += driver.getRawAxis(5) + " ";

		Buttons += driver.getPOV(0);
		// System.out.println("Buttons");
		return Buttons;
	}

	static String RecordShooter() {
		String Buttons = "";
		if (shooter.isAHeld()) {
			Buttons += "true ";
			// System.out.println("A pressed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isBHeld()) {
			Buttons += "true ";
			// System.out.println("B pressed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isXHeld()) {
			Buttons += "true ";
			// System.out.println("X pressed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isYHeld()) {
			Buttons += "true ";
			// System.out.println("Y pressed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isLBHeld()) {
			Buttons += "true ";
			// System.out.println("LB pressed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isRBHeld()) {
			Buttons += "true ";
			// System.out.println("RB pressed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isBackHeld()) {
			Buttons += "true ";
			// System.out.println("Back pressed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isStartHeld()) {
			Buttons += "true ";
			// System.out.println("Start pressed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isLStickHeld()) {
			Buttons += "true ";
			// System.out.println("LStickPushed");
		} else {
			Buttons += "false ";
		}

		if (shooter.isRStickHeld()) {
			Buttons += "true ";
			// System.out.println("RStickPushed");
		} else {
			Buttons += "false ";
		}

		// System.out.println("LX Axis:" + shooter.getRawAxis(0));
		// System.out.println("LY Axis:" + shooter.getRawAxis(1));
		// System.out.println("L Trigger:" + shooter.getRawAxis(2));
		// System.out.println("R Trigger:" + shooter.getRawAxis(3));
		// System.out.println("RX Axis:" + shooter.getRawAxis(4));
		// System.out.println("RY Axis:" + shooter.getRawAxis(5));

		Buttons += shooter.getRawAxis(0) + " ";
		Buttons += shooter.getRawAxis(1) + " ";
		Buttons += shooter.getRawAxis(2) + " ";
		Buttons += shooter.getRawAxis(3) + " ";
		Buttons += shooter.getRawAxis(4) + " ";
		Buttons += shooter.getRawAxis(5) + " ";

		Buttons += shooter.getPOV(0);
		// System.out.println(Buttons);
		return Buttons;
	}

}

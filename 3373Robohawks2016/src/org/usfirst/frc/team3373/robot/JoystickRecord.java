package org.usfirst.frc.team3373.robot;

//@author Kofi Asenso and Matthew Heller
import java.io.*;

public class JoystickRecord {

	private static int count;
	private static BufferedWriter printline;
	static JoystickOverride driver = new JoystickOverride(0);
	static JoystickOverride shooter = new JoystickOverride(1);
	// private static FileWriter fw;

	public static void RecordInit(String fileLabel) throws Exception {
		try {
			File f = new File("/home/lvuser/" + fileLabel);

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

	public static void record() {
		String stringdata = "";
		try {
			stringdata = RecordDriver();
			printline.write(stringdata);
			printline.newLine();
			// System.out.println("0: " + stringdata);

			stringdata = RecordShooter();
			printline.write(stringdata);
			printline.newLine();
			// System.out.println("1: " + stringdata);
			count++;
			System.out.println("file length: " + count * 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Stop() {
		try {
			printline.write("0 0 0 0 0 0 0 0 0 0 0.0 0.0 0.0 0.0 0.0 0.0 -1");
			printline.newLine();
			printline.write("0 0 0 0 0 0 0 0 0 0 0.0 0.0 0.0 0.0 0.0 0.0 -1");
			printline.newLine();
			
			printline.flush();
			printline.close();
			System.out.println("final file length: " + count * 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String RecordDriver() {
		String Buttons = "";
		if (driver.isAHeld()) {
			Buttons += "1 ";
			// System.out.println("A pressed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isBHeld()) {
			Buttons += "1 ";
			// System.out.println("B pressed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isXHeld()) {
			Buttons += "1 ";
			// System.out.println("X pressed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isYHeld()) {
			Buttons += "1 ";
			// System.out.println("Y pressed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isLBHeld()) {
			Buttons += "1 ";
			// System.out.println("LB pressed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isRBHeld()) {
			Buttons += "1 ";
			// System.out.println("RB pressed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isBackHeld()) {
			Buttons += "1 ";
			// System.out.println("Back pressed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isStartHeld()) {
			Buttons += "1 ";
			// System.out.println("Start pressed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isLStickHeld()) {
			Buttons += "1 ";
			// System.out.println("LStickPushed");
		} else {
			Buttons += "0 ";
		}

		if (driver.isRStickHeld()) {
			Buttons += "1 ";
			// System.out.println("RStickPushed");
		} else {
			Buttons += "0 ";
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

	private static String RecordShooter() {
		String Buttons = "";
		if (shooter.isAHeld()) {
			Buttons += "1 ";
			// System.out.println("A pressed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isBHeld()) {
			Buttons += "1 ";
			// System.out.println("B pressed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isXHeld()) {
			Buttons += "1 ";
			// System.out.println("X pressed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isYHeld()) {
			Buttons += "1 ";
			// System.out.println("Y pressed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isLBHeld()) {
			Buttons += "1 ";
			// System.out.println("LB pressed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isRBHeld()) {
			Buttons += "1 ";
			// System.out.println("RB pressed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isBackHeld()) {
			Buttons += "1 ";
			// System.out.println("Back pressed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isStartHeld()) {
			Buttons += "1 ";
			// System.out.println("Start pressed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isLStickHeld()) {
			Buttons += "1 ";
			// System.out.println("LStickPushed");
		} else {
			Buttons += "0 ";
		}

		if (shooter.isRStickHeld()) {
			Buttons += "1 ";
			// System.out.println("RStickPushed");
		} else {
			Buttons += "0 ";
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

package org.usfirst.frc.team3373.robot;

import java.io.*;
//@author Kofi Asenso and Matthew Heller

public class JoystickPlayer {

	private static BufferedReader Reader = null;
	private static FileReader fr;
	// private static String line;
	private static int count = 0;
	private static String fname;

	static String[] driverButtons = new String[17];
	static String[] shooterButtons = new String[17];

	public static void PlayerInit(String fileLabel) throws Exception {
		fname = fileLabel;
		try {
			File f = new File("/home/lvuser/JoystickRecords/" + fileLabel);

			if (!f.exists()) {
				System.out.println("File Not Found");
				throw new FileNotFoundException();
			} else {
				System.out.println("File Found");
				fr = new FileReader("/home/lvuser/JoystickRecords/" + fileLabel);
				Reader = new BufferedReader(fr);
				count = 0;
				/*
				 * String line = ""; try { while ((line = Reader.readLine()) !=
				 * null) { System.out.println(line); count++; } // Always close
				 * files. System.out.println("file length: " + count);
				 * 
				 * } catch (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean Play() {
		// JoystickRecord.driver1.setcontrol(false);
		// JoystickRecord.shooter1.setcontrol(false);
		String driverInS;
		String shooterInS;
		try {
			if ((driverInS = Reader.readLine()) != null) {
				shooterInS = Reader.readLine();
				// System.out.println("0:" + driverInS);
				// System.out.println("1:" + shooterInS);

				driverButtons = driverInS.split(" ");
				Robot.driver.SetButtons(driverButtons);

				shooterButtons = shooterInS.split(" ");
				Robot.shooter.SetButtons(shooterButtons);

				count++;
			} else {
				System.out.println("file length: " + count * 2);
				count = 0;
				Reader.close();
				fr = new FileReader("/home/lvuser/JoystickRecords/" + fname);
				Reader = new BufferedReader(fr);

				return true;
			}
			// Always close files.
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void Stop() {
		try {
			count = 0;
			Reader.close();
			fr = new FileReader("/home/lvuser/JoystickRecords/" + fname);
			Reader = new BufferedReader(fr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
package org.usfirst.frc.team3373.robot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CalibrationReader {
  public static void main(String[] args) {

	Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream("/home/lvuser/config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		System.out.println("Motor 1 min: ");
		System.out.println(prop.getProperty("motorMin1"));
		System.out.println("Motor 1 max: ");
		System.out.println(prop.getProperty("motorMax1"));
		System.out.println("Motor 2 min: ");
		System.out.println(prop.getProperty("motorMin2"));
		System.out.println("Motor 2 max: ");
		System.out.println(prop.getProperty("motorMax2"));

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

  }
}
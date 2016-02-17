package org.usfirst.frc.team3373.robot;

import java.util.*;
import java.io.*;
import java.nio.*;

public class CalibrationPrinter {
	public static void main(String[] args) {
		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
	try{
		
		input = new FileInputStream("/home/lvuser/config.properties");

		// load a properties file
		prop.load(input);
		
	output = new FileOutputStream("/home/lvuser/config.properties");
	
	prop.setProperty("motorMin" + HawkCalibration.ID, Integer.toString(HawkCalibration.rangeMin));
	prop.setProperty("motorMax" + HawkCalibration.ID, Integer.toString(HawkCalibration.rangeMax));
	
	prop.store(output, null);
	
	System.out.println("POMASOOUPAH! BOI!");
	
	}catch (IOException io){
		io.printStackTrace();
	}finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
	}
}

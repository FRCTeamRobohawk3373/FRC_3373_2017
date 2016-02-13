package org.usfirst.frc.team3373.robot;

import java.io.*;
import java.util.*;

public class CalibrationReader {

	  public static void main( String[] args ){

	    	Properties prop = new Properties();
	    	InputStream input = null;
	    	
	    	try {
	        
	    		String filename = "/home/lvuser/config.properties";
	    		input = CalibrationReader.class.getClassLoader().getResourceAsStream(filename);
	    		if(input==null){
	    	            System.out.println("Sorry, unable to find " + filename);
	    		    return;
	    		}

	    		//load a properties file from class path, inside static method
	    		prop.load(input);
	 
	                //get the property value and print it out
	                System.out.println(prop.getProperty("database"));
	    	        System.out.println(prop.getProperty("dbuser"));
	    	        System.out.println(prop.getProperty("dbpassword"));
	 
	    	} catch (IOException ex) {
	    		ex.printStackTrace();
	        } finally{
	        	if(input!=null){
	        		try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	}
	        }
	 
	    }

}

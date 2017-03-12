package org.usfirst.frc.team3373.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Change Height based on how the robot needs to go
 * Check to make sure the limit switch is still reverse when put on the actual talon
 * 
 */

public class Climber {
	BufferedWriter printline;
	CANTalonSafetyNet climber;
	UltraSonic ultraSonic;
	double maxSpeedHeight = 12;
	double minSpeedHeight = 24;
	double maxSpeed = 1;
	double minSpeed = .4;
	double speedMod = .8;
	boolean toggle = true;
	double previousCurrent;
	double previousVoltage;
	double current;
	public double getOutputVoltage;
	boolean isMaxHeight = false;
	int spikeCurrentCounter = 0;

	int currentSpikeCounter = 0;

	public Climber(int climberPort, int ultraSonicPort, int ultraSonicPort2, int digitalTrigger) {
		// ultraSonic = new UltraSonic(ultraSonicPort, ultraSonicPort2,
		// digitalTrigger);
		// ultraSonic = new UltraSonic(0);

		climber = new CANTalonSafetyNet(climberPort, .05);
		previousCurrent = climber.getOutputCurrent();
		previousVoltage = climber.getOutputVoltage();

	}

	public void climberInit() {
		try {

			File f = new File("/home/lvuser/ClimberCurrent.txt");

			FileWriter fw = new FileWriter(f);
			printline = new BufferedWriter(fw);
			if (f.exists()) {
				f.delete();
				f.createNewFile();
			} else
				f.createNewFile();

		} catch (Exception e) {
			System.out.println("                     1233532353253");
			e.printStackTrace();
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void climb(double speed) {
		/*
		 * if (ultraSonic.getDistance() > height) { speedMod = .4; } else if
		 * (!climber.isRevLimitSwitchClosed() && speedMod != 0) { speedMod = .8;
		 * }
		 * 
		 * if (climber.isRevLimitSwitchClosed()) { speedMod = .2; }
		 * 
		 * else if (ultraSonic.getDistance() < height && speedMod != 0) {
		 * speedMod = .8; }
		 */
		/*
		 * if ((ultraSonic.getDistance() <= maxSpeedHeight ||
		 * ultraSonic.getDistance() > 40) && !isMaxHeight) speedMod = maxSpeed;
		 * else if (ultraSonic.getDistance() >= minSpeedHeight && !isMaxHeight)
		 * speedMod = minSpeed;
		 * 
		 * else if(!isMaxHeight) speedMod = (((minSpeed - maxSpeed) /
		 * minSpeedHeight) * ultraSonic.getDistance()) + maxSpeed;
		 */
		double current = climber.getOutputCurrent();
		if (current > 40 && previousCurrent > 40) {
			spikeCurrentCounter++;
		} else if (!isMaxHeight) {
			spikeCurrentCounter = 0;
		}
		if (spikeCurrentCounter >= 15) {
			speedMod = 0;
			isMaxHeight = true;
			this.enableBrake();
		}
		if (!isMaxHeight) {

			speedMod = 1;
		}
		//SmartDashboard.putNumber("spikeCurrentCounter", spikeCurrentCounter);
		previousCurrent = current;
		climber.accelerate(speed * speedMod);
		/*
		 * try { printline.write(climber.getOutputCurrent() + "," +
		 * climber.getOutputVoltage() + "," + ultraSonic.getDistance());
		 * printline.newLine(); } catch (IOException e) {
		 * System.out.println("Failed!D:"); e.printStackTrace(); }
		 */

	}

	public void climberClose() {
		try {
			printline.flush();
			printline.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printCurrent() {
		previousCurrent = current;
		current = climber.getOutputCurrent();
		SmartDashboard.putNumber("Current", climber.getOutputCurrent());
		//SmartDashboard.putNumber("Spike Number", currentSpikeCounter);
		if (climber.getOutputCurrent() >= previousCurrent * 5) {
			currentSpikeCounter++;
		}
	}

	public void printVoltage() {
		SmartDashboard.putNumber("Voltage", climber.getOutputVoltage());
		// SmartDashboard.putNumber("Spike Number", currentSpikeCounter);
		// if (climber.getOutputVoltage() >= previousVoltage * 5) {
		// currentSpikeCounter++; }
	}

	public void disableBrake() {
		climber.enableBrakeMode(false);
	}
	
	public void enableBrake(){
		climber.enableBrakeMode(true);
	}

	public void setMaxHeightFalse() {
		isMaxHeight = false;
		//System.out.println("HAHAHAHAHAHAHAHAHA  Soup.");
	}
}

/*
 * Sets the speed based on joystick input checks to see if the limit switch is
 * pressed checks the distance of the Ultra Sonic sensor Set the speed modifier
 * based on the distance returned by the sensor if the limit switch is pressed,
 * drasticly decrease the speed modifier indicator on the smartdashboard
 * display, telling the driver that the the robot is close
 */

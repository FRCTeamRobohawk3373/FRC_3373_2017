package org.usfirst.frc.team3373.robot;
//AUTHOR Alex Iasso and Dillon Rose
import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.*;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import java.io.IOException;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.*;
import edu.wpi.first.wpilibj.CANTalon;
import com.ni.vision.NIVision.Image;
import edu.wpi.first.wpilibj.image.*;

//@author Joey Dyer, Drew Marino, Alex Iasso, Dillon Rose

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	SuperJoystick stick;
	int autoLoopCounter;
	DigitalInput limitSwitch;
	AnalogInput pot;
	static CANTalon motor1;
	static CANTalon motor2;
	static SuperJoystick driver;
//	int counter;
	
	
	
	
	int LX = 0;
    static int LY = 1;
    int Ltrigger = 2;
    int Rtrigger = 3;
    int RX = 4;
    static int RY = 5;
    
    boolean CAN = true;
//	Timer robotTimer;
	//AxisCamera camera;

   	HawkVision visionSystem = new HawkVision();

	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    	stick = new SuperJoystick(0);
    	limitSwitch = new DigitalInput(0);
    	pot = new AnalogInput(0);
    	motor1 = new CANTalon(1);
    	motor2 = new CANTalon(2);
    	driver = new SuperJoystick(0);
    	myRobot = new RobotDrive(0,1);
   // 	counter = 0;
    //	robotTimer = new Timer();

    	}

    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters teleoperated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//myRobot.tankDrive(stick.getRawAxis(1), stick.getRawAxis(5));
    	if(isOperatorControl() && isEnabled()){
    		motor1.enable();
    		motor2.enable();
    		motor1.enableControl();
    		motor2.enableControl();
    		driver.clearButtons();
    	//	motor1.set(driver.getRawAxis(LY));
    	//	motor2.set(driver.getRawAxis(RY));
    		if(CAN){                                                                         //Drive System
    		HawkDrive.main(null);
			if(driver.isAPushed()){
				CAN = false;
				motor1.set(0);
				motor2.set(0);

				}
    		}
    		if(CAN == false){
    			myRobot.tankDrive(driver.getRawAxis(LY), driver.getRawAxis(RY));               //Regular Talon Control
    			if(driver.isAPushed()){
    				CAN = true;
    			}
    		}
    		if(driver.isXPushed()){
    			motor2.set(1);
    			try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			motor2.set(0);
    			driver.clearButtons();
    		}
    		
    		
    		
    		//CANTest.wheelControl(driver.getRawAxis(LY), driver.getRawAxis(RY));
    	//	motor1.set(0.5);
    	//	motor2.set(0.5);
    		//Timer.delay(0.005);
    		

    	}else{
    	motor1.disable();
    	motor2.disable();
    	}

    }
    


	@SuppressWarnings("deprecation")
    public void testInit(){
    	//Live window is enabled by default for test mode by disabling it here, it allows the use of smartdashboard to display values
    	LiveWindow.setEnabled(false);
    	String cameraIP = "cam0";
    //	VisionSystem.Camera(cameraIP);
    //	VisionSystem.Filtering(cameraIP);
    	


    }
    /**
     * 
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	visionSystem.getVisionImage();
    	SmartDashboard.putNumber("LeftAxis: ", stick.getRawAxis(1));
    	SmartDashboard.putNumber("RightAxis: ", stick.getRawAxis(5));
    	SmartDashboard.putBoolean("Limit Switch: ", limitSwitch.get());
    	SmartDashboard.putNumber("Pot Value:", pot.getVoltage());
    	String cameraIP = "cam0";
    //	VisionSystem.Filtering(cameraIP);
    	//SmartDashboard.putNumber("Particles: ", VisionSystem.Filtering(cameraIP));
    	//String cameraIP = "cam0";
    	//visionSystem.Filtering(cameraIP);
    	//SmartDashboard.putNumber("Particles: ", visionSystem.Filtering(cameraIP));
    	SmartDashboard.putNumber("Test Value Drew ", 12);

    	//LiveWindow.run(); This should be uncommented when LiveWindow is desired in test mode
    	
    }
    
}

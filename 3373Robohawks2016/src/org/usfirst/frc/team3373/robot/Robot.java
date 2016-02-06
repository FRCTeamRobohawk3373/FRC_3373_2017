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
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

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
	
	static SuperJoystick shooter;
	static boolean manualArm = false;
	
//	int counter;
	
	boolean counterBool;
	int counter;
//	int i = 0;
	int LX = 0;
    static int LY = 1;
    int Ltrigger = 2;
    int Rtrigger = 3;
    int RX = 4;
    static int RY = 5;
    
    boolean CAN = true;
    
    boolean D_Pad1 = false;
    boolean D_Pad2 = false;
    boolean D_Pad3 = false;
    
    boolean Shooting = false;
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
    	shooter = new SuperJoystick(1);
    	myRobot = new RobotDrive(4,0);
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
    int i = 0;
    int counterShooterB = 0;
    boolean counterBoolShooterB = false;
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
		i++;
    	//myRobot.tankDrive(stick.getRawAxis(1), stick.getRawAxis(5));
    	if(isOperatorControl() && isEnabled()){
    		motor1.enable();
    		motor2.enable();
    		motor1.enableControl();
    		motor2.enableControl();
    		driver.clearButtons();
    	//	motor1.set(driver.getRawAxis(LY));
    	//	motor2.set(driver.getRawAxis(RY));
    		
    		
    		//
    		//    DRIVER CONTROLS
    		//
    		
    		
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
    			motor1.set(1);
    			driver.clearButtons();
    			if(driver.isAPushed()){
    				motor1.set(0);
    				driver.clearButtons();
    			}
    		}
    		
    		
    		//
    		//    SHARED CONTROLS
    		//
    		
    		
    		
    		if(driver.getPOV() == 180 || shooter.getPOV() == 180){      //Down on the D-Pad    (Toggles)
    			if(!D_Pad1){
    			D_Pad1 = true;
    			}else{
    			D_Pad1=false;
    			}
    			try {
					Thread.sleep(150);      //Prevents pressing more than once too fast
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		if(driver.getPOV()==90 || shooter.getPOV() == 90){     //Right on the D-Pad        (Toggles)
    			if(!D_Pad2){
        			D_Pad2 = true;
        			}else{
        			D_Pad2=false;
        			}
        			try {
    					Thread.sleep(150);      //Prevents pressing more than once too fast
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    		}
    		if(driver.getPOV()==270 || shooter.getPOV() == 270){    //Left on the D-Pad                    (Toggles)
    			if(!D_Pad3){
        			D_Pad3 = true;
        			}else{
        			D_Pad3=false;
        			}
        			try {
    					Thread.sleep(150);      //Prevents pressing more than once too fast
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				};
    		}
    		
    		if(D_Pad1){                  //Down, Drawbridge automation           
    			motor1.set(1);
    		}
    		if(D_Pad2){                 //Right, Sally Port automation
    			motor1.set(-1);
    		}
    		if(D_Pad3){                //Left, Portcullis automation
    			motor2.set(1);
    		}
    		
    		
    		
    		
    		//
    		//    "SHOOTER" CONTROLS
    		//
    		
    		
    		//   SHOOTER (as in, the actual shooting mechanism) CONTROLS
    		
    		if(Shooting){
    			/*if(shooter.isXPushed()){
    				motor1.set(1);
    				shooter.clearButtons();
    			}*/
    			if(shooter.getPOV() == 0){
    				Shooting = false;
    				try {
    					Thread.sleep(150);      //Prevents pressing more than once too fast
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			
    			if(shooter.isAPushed()){
    				motor1.set(1);
    				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				shooter.clearButtons();
    			}
    			if(shooter.isXPushed()){
    				motor1.set(.3);
    				try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				motor1.set(-.3);
    				try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				motor1.set(0);
    				shooter.clearButtons();
    			}
    		}
    		
    		
    		
    		//   ARM CONTROLS
    		if(!Shooting){
    			if(shooter.getPOV() == 0){
    				Shooting = true;
    				try {
    					Thread.sleep(150);      //Prevents pressing more than once too fast
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			if(shooter.isAPushed()){
    				motor1.set(-1);
    				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				shooter.clearButtons();
    			}
    			if(shooter.isBPushed()){
    				counterBoolShooterB = true;       //Begins the B button sequence
    			}
    			if(counterBoolShooterB){
    				if(counterShooterB == 0){
    					counterShooterB = i;
    				}
    				if(i<counterShooterB+50){                    //Runs the sequence for 50 loops, or one second
    				motor2.set(-1);
    				}
    				else if(i>=counterShooterB+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				motor2.set(0);
    				shooter.clearButtons();
    				counterShooterB = 0;
    				counterBoolShooterB = false;
    				}
    			}
    		}
    				
    		//SHOOTER AND ARM CONTROLS (Function in both modes)	
    		if(Shooting || !Shooting){
    			if(shooter.isBackHeld()){
    				motor1.set(-1);
    				motor2.set(-1);
    			}
    		}
    		

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
    	counterShooterB = 0;
    	i = 0;
    	counterBool = false;


    }
    /**
     * 
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	SmartDashboard.putNumber("D-Pad Value: ", driver.getPOV());
    	SmartDashboard.putBoolean("Shooting: ", Shooting);
		HawkDrive.main(null);
		i++;
		SmartDashboard.putInt("I: ", i);
		SmartDashboard.putInt("counterShooterB: ", counterShooterB);
		if(shooter.isBPushed()){
			counterBool = true;
		}
		if(counterBool){
			if(counterShooterB == 0){
				counterShooterB = i;
			}
			if(i<counterShooterB+50){
			motor2.set(-1);
			}
			else if(i>=counterShooterB+50){
			motor2.set(0);
			shooter.clearButtons();
			counterShooterB = 0;
			counterBool = false;
			}
		}
		}

    	//LiveWindow.run(); This should be uncommented when LiveWindow is desired in test mode
    	
    }
    


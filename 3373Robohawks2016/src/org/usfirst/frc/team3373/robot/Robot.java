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
	//static limitSwitch limitSwitch1;
	static HawkSuperMotor motor1;
	static HawkSuperMotor motor2;
	static HawkSuperMotor motor3;
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
    	motor1 = new HawkSuperMotor(1, 0, 14710);                 // (Talon ID, Min Encoder Value, Max Encoder Value
    	motor2 = new HawkSuperMotor(2, 0, 14710);
    	motor3 = new HawkSuperMotor(3, 0, 14710);
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
    int robotTimer = 0;
    
    int counterShooterA = 0;
    int counterShooterB = 0;
    int counterShooterX = 0;
    int counterShooterX2 = 0;
    int counterShooterY = 0;
    int counterShooterStart = 0;
    int counterShooterLS = 0;
    int counterShooterRS = 0;
    int counterShooterToggle = 0;
    
    boolean counterBoolShooterA = false;
    boolean counterBoolShooterB = false;
    boolean counterBoolShooterX = false;
    boolean counterBoolShooterX2 = false;
    boolean counterBoolShooterY = false;
    boolean counterBoolShooterStart = false;
    boolean counterBoolShooterLS = false;
    boolean counterBoolShooterRS = false;
    boolean counterBoolShooterToggle = false;
    
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
		robotTimer++;    //50 Cycles is one second (20 ms per cycle)
    	//myRobot.tankDrive(stick.getRawAxis(1), stick.getRawAxis(5));
    	if(isOperatorControl() && isEnabled()){
    		driver.clearButtons();
    	//	motor1.set(driver.getRawAxis(LY));
    	//	motor2.set(driver.getRawAxis(RY));
    		
    		
    		//
    		//    DRIVER CONTROLS
    		//
    		
    		
    		if(CAN){                                                                         //Drive System
    		HawkDrive.main(null);
    		}
    		if(CAN == false){
    			myRobot.tankDrive(driver.getRawAxis(LY), driver.getRawAxis(RY));               //Regular Talon Control
    			if(driver.isAPushed()){
    				CAN = true;
    			}
    		}
    		
    		
    		//
    		//    SHARED CONTROLS
    		//
    		
    		
    		
    		if(driver.getPOV() == 180 || shooter.getPOV() == 180){      //Down on the D-Pad    (Toggles Drawbridge Drive)
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
    		if(driver.getPOV()==90 || shooter.getPOV() == 90){     //Right on the D-Pad        (Toggles Sally Port Drive)
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
    		if(driver.getPOV()==270 || shooter.getPOV() == 270){    //Left on the D-Pad                    (Toggles Portcullis Drive)
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
    				if(!counterBoolShooterToggle){
    				Shooting = false;
    				}
    				counterBoolShooterToggle = true;
    			}
    			if(counterBoolShooterToggle){
    				if(counterShooterToggle == 0){
    					counterShooterToggle = robotTimer;
    				}
    				if(robotTimer>=counterShooterToggle+10){                 //Resets all variables, and ends sequence (after 10 loops, or 200 ms)
    				shooter.clearButtons();
    				counterShooterToggle = 0;
    				counterBoolShooterToggle = false;
    				}
    			}
    			
    			if(shooter.isAPushed()){
    				counterBoolShooterA = true;
    			}
    			if(counterBoolShooterA){
        				if(counterShooterA == 0){
        					counterShooterA = robotTimer;
        				}
        				if(robotTimer<counterShooterA+50){                    //Runs the sequence for 50 loops, or one second
        				motor1.set(1);
        				}
        				else if(robotTimer>=counterShooterA+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
        				motor1.set(0);
        				shooter.clearButtons();
        				counterShooterA = 0;
        				counterBoolShooterA = false;
        				}
        			
    			}
    			if(shooter.isXHeld()){
    				counterBoolShooterX = true;             //Checks whether or not X is held
    			}else{
    				counterBoolShooterX = false;             //If not, cancels shooting
    				counterShooterX = 0;
    				counterShooterX2 = 0;
    			}
    			if(counterBoolShooterX){                 //If X is held, proceeds to shooting phase
    				if(shooter.isXHeld()){
    					counterBoolShooterX2 = true;
    				}
    				if(counterBoolShooterX2){
    					if(counterShooterX2 == 0){
        					counterShooterX2 = robotTimer;
        				}
        				if(robotTimer<=counterShooterX2+250){                    //Runs the sequence for 250 loops, or five seconds
        				motor1.set(1);
        				}else if(robotTimer<counterShooterX2+500){
        				motor1.set(-1);
        				}
        				else if(robotTimer>=counterShooterX2+500){                 //Resets all variables, and ends sequence (after 250 more loops, or five seconds)
        				motor1.set(0);
        				shooter.clearButtons();
        				counterShooterX = 0;
        				counterBoolShooterX = false;
        				counterShooterX2 = 0;
        				counterBoolShooterX2 = false;
        				}
    				}
    				else{
    					counterBoolShooterX = false;
    					counterBoolShooterX2 = false;
    					counterShooterX = 0;
    					counterShooterX2 = 0;
    					motor1.set(0);
    				}
    			}
    		}
    		
    		
    		
    		//   ARM CONTROLS
    		if(!Shooting){
    			if(shooter.getPOV() == 0){
    				if(!counterBoolShooterToggle){
    				Shooting = true;
    				}
    				counterBoolShooterToggle = true;
    			}
    			if(counterBoolShooterToggle){
    				if(counterShooterToggle == 0){
    					counterShooterToggle = robotTimer;
    				}
    				if(robotTimer>=counterShooterToggle+10){                 //Resets all variables, and ends sequence (after 10 loops, or 200 ms)
    				shooter.clearButtons();
    				counterShooterToggle = 0;
    				counterBoolShooterToggle = false;
    				}
    			}
    			if(shooter.isAPushed()){
    				counterBoolShooterA = true;
    			}
    			if(counterBoolShooterA){
        				if(counterShooterA == 0){
        					counterShooterA = robotTimer;
        				}
        				if(robotTimer<counterShooterA+50){                    //Runs the sequence for 50 loops, or one second
        				motor1.set(1);
        				}
        				else if(robotTimer>=counterShooterA+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
        				motor1.set(0);
        				shooter.clearButtons();
        				counterShooterA = 0;
        				counterBoolShooterA = false;
        				}
        			
    			}
    			if(shooter.isBPushed()){
    				counterBoolShooterB = true;       //Begins the B button sequence
    			}
    			if(counterBoolShooterB){
    				if(counterShooterB == 0){
    					counterShooterB = robotTimer;
    				}
    				if(robotTimer<counterShooterB+50){                    //Runs the sequence for 50 loops, or one second
    				motor2.set(-1);
    				}
    				else if(robotTimer>=counterShooterB+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				motor2.set(0);
    				shooter.clearButtons();
    				counterShooterB = 0;
    				counterBoolShooterB = false;
    				}
    			}
    			if(shooter.isXPushed()){
    				counterBoolShooterX = true;
    			}
    			if(counterBoolShooterX){
    				if(counterShooterX == 0){
    					counterShooterX = robotTimer;
    				}
    				if(robotTimer<counterShooterX+50){                    //Runs the sequence for 50 loops, or one second
    				motor2.set(-1);
    				}
    				else if(robotTimer>=counterShooterX+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				motor2.set(0);
    				shooter.clearButtons();
    				counterShooterX = 0;
    				counterBoolShooterX = false;
    				}
    			}
    			if(shooter.isYPushed()){
    				counterBoolShooterY = true;
    			}
    			if(counterBoolShooterY){
    				if(counterShooterY == 0){
    					counterShooterY = robotTimer;
    				}
    				if(robotTimer<counterShooterY+50){                    //Runs the sequence for 50 loops, or one second
    				motor2.set(-1);
    				}
    				else if(robotTimer>=counterShooterY+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				motor2.set(0);
    				shooter.clearButtons();
    				counterShooterY = 0;
    				counterBoolShooterY = false;
    				}
    			}
    			if(shooter.isStartPushed()){
    				if(counterBoolShooterStart == false){
    				counterBoolShooterStart = true;
    				}else{
    				counterBoolShooterStart = false;
    				shooter.clearButtons();
    				counterShooterStart = 0;
    				motor2.set(0);
    				}
    			}
				shooter.clearButtons();
    			if(counterBoolShooterStart){
    				if(counterShooterStart == 0){
    					counterShooterStart = robotTimer;
    				}
    				if(robotTimer<counterShooterStart+500){                    //Runs the sequence for 50 loops, or one second
    				motor2.set(-1);
    				}
    				else if(robotTimer>=counterShooterStart+500){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				motor2.set(0);
    				shooter.clearButtons();
    				counterShooterStart = 0;
    				counterBoolShooterStart = false;
    				}
    			}
    			if(shooter.isLStickPushed()){
    				counterBoolShooterLS = true;
    			}
    			if(counterBoolShooterLS){
    				if(counterShooterLS == 0){
    					counterShooterLS = robotTimer;
    				}
    				if(robotTimer<counterShooterLS+50){                    //Runs the sequence for 50 loops, or one second
    				motor2.set(-1);
    				}
    				else if(robotTimer>=counterShooterLS+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				motor2.set(0);
    				shooter.clearButtons();
    				counterShooterLS = 0;
    				counterBoolShooterLS = false;
    				}
    			}
    			if(shooter.isRStickPushed()){
    				counterBoolShooterRS = true;
    			}
    			if(counterBoolShooterRS){
    				if(counterShooterRS == 0){
    					counterShooterRS = robotTimer;
    				}
    				if(robotTimer<counterShooterRS+50){                    //Runs the sequence for 50 loops, or one second
    				motor2.set(1);
    				}
    				else if(robotTimer>=counterShooterRS+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				motor2.set(0);
    				shooter.clearButtons();
    				counterShooterRS = 0;
    				counterBoolShooterRS = false;
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
    		
    		SmartDashboard.putBoolean("Shooting: ", Shooting);
    		
    		SmartDashboard.putBoolean("A: ", counterBoolShooterA);
    		SmartDashboard.putBoolean("B: ", counterBoolShooterB);
    		SmartDashboard.putBoolean("X: ", counterBoolShooterX);
    		SmartDashboard.putBoolean("Y: ", counterBoolShooterY);
    		SmartDashboard.putBoolean("Start: ", counterBoolShooterStart);

    		
    		SmartDashboard.putBoolean("D-Pad Right: ", D_Pad2);
    		SmartDashboard.putBoolean("D-Pad Down: ", D_Pad1);
    		SmartDashboard.putBoolean("D-Pad Left: ", D_Pad3);

    	}else{
    	motor1.disable();
    	motor2.disable();
    	}

    }
    


	@SuppressWarnings("deprecation")
    public void testInit(){
    	//Live window is enabled by default for test mode by disabling it here, it allows the use of smartdashboard to display values
    	LiveWindow.setEnabled(false);
    	counterShooterB = 0;
    	robotTimer = 0;
    	counterBool = false;


    }
    /**
     * 
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	visionSystem.getVisionImage();
    	SmartDashboard.putNumber("Encoder Position: ", motor3.getEncPosition());
    	SmartDashboard.putNumber("Target Encoder Position: ", motor3.targetEncoderPos);
		HawkDrive.main(null);
		robotTimer++;
		//HawkCalibration.main(null);
		motor3.goToHeight(10.3);
		SmartDashboard.putNumber("Current Height (Inches): ", (motor3.getEncPosition()/motor3.range)*12);
		}

    	//LiveWindow.run(); This should be uncommented when LiveWindow is desired in test mode
    	
    }
    


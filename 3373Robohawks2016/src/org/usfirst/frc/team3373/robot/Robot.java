package org.usfirst.frc.team3373.robot;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

//AUTHOR Alex Iasso and Dillon Rose
import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;

//@author Joey Dyer, Drew Marino, Alex Iasso, Dillon Rose

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	CANTalon calibMotor;
	
	CameraServer server;
	double inches;
	RobotDrive myRobot;
	int autoLoopCounter;
	HawkSuperMotor motor1;
	HawkSuperMotor motor2;
	HawkSuperMotor motor3;
	
	HawkSuperDualMotor dual1;
	
	SuperJoystick driver;
	SuperJoystick shooter;
	SuperJoystick calibrator;
	
	HawkDrive hawkDrive;
	
	boolean manualArm = false;
	
	InputStream input = null;
	Properties prop = new Properties();
	
	int motorID1;
	int motorMin1;
	int motorMax1;
	int motorMaxPercent1;
	int motorMinPercent1;
	double motorTravelRange1;
	double maxSpeedChange1;
	
	int motorID2;
	int motorMin2;
	int motorMax2;
	int motorMaxPercent2;
	int motorMinPercent2;
	double motorTravelRange2;
	double maxSpeedChange2;
	
	int motorID3;
	int motorMin3;
	int motorMax3;
	int motorMaxPercent3;
	int motorMinPercent3;
	double motorTravelRange3;
	double maxSpeedChange3;

	int motorID4;
	int motorMin4;
	int motorMax4;
	int motorMaxPercent4;
	int motorMinPercent4;
	double motorTravelRange4;
	double maxSpeedChange4;
	
	int motorID5;
	int motorMin5;
	int motorMax5;
	int motorMaxPercent5;
	int motorMinPercent5;
	double motorTravelRange5;
	double maxSpeedChange5;
	
	int motorID6;
	int motorMin6;
	int motorMax6;
	int motorMaxPercent6;
	int motorMinPercent6;
	double motorTravelRange6;
	double maxSpeedChange6;
	
	int motorID7;
	int motorMin7;
	int motorMax7;
	int motorMaxPercent7;
	int motorMinPercent7;
	double motorTravelRange7;
	double maxSpeedChange7;
	
	int motorID8;
	int motorMin8;
	int motorMax8;
	int motorMaxPercent8;
	int motorMinPercent8;
	double motorTravelRange8;
	double maxSpeedChange8;
	
	int motorID9;
	int motorMin9;
	int motorMax9;
	int motorMaxPercent9;
	int motorMinPercent9;
	double motorTravelRange9;
	double maxSpeedChange9;
	
	int motorID10;
	int motorMin10;
	int motorMax10;
	int motorMaxPercent10;
	int motorMinPercent10;
	double motorTravelRange10;
	double maxSpeedChange10;
	
	int motorID11;
	int motorMin11;
	int motorMax11;
	int motorMaxPercent11;
	int motorMinPercent11;
	double motorTravelRange11;
	double maxSpeedChange11;
	
	int motorID12;
	int motorMin12;
	int motorMax12;
	int motorMaxPercent12;
	int motorMinPercent12;
	double motorTravelRange12;
	double maxSpeedChange12;
	
	int motorID13;
	int motorMin13;
	int motorMax13;
	int motorMaxPercent13;
	int motorMinPercent13;
	double motorTravelRange13;
	double maxSpeedChange13;
	
	int motorID14;
	int motorMin14;
	int motorMax14;
	int motorMaxPercent14;
	int motorMinPercent14;
	double motorTravelRange14;
	double maxSpeedChange14;



	
//	int counter;
	
	
    DigitalInput ones;
    DigitalInput twos;
    DigitalInput fours;
    DigitalInput eights;
	
	boolean counterBool;
	int counter;
//	int i = 0;
	int LX = 0;
    int LY = 1;
    int Ltrigger = 2;
    int Rtrigger = 3;
    int RX = 4;
    int RY = 5;
    int index;
    
    boolean CAN = true;
    
    boolean D_Pad1 = false;
    boolean D_Pad2 = false;
    boolean D_Pad3 = false;
    
    boolean Shooting = false;
    
    boolean goingDistance = false;
    
    
    
    
    
    
//	Timer robotTimer;
	//AxisCamera camera;

  	HawkVision visionSystem = new HawkVision();

	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code
     */
    public void robotInit() {
    	
    	
    	boolean initializingMotors = true;
    	
    	try {                                                                          //Read config file
			input = new FileInputStream("/home/lvuser/config.properties");
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    

 /*   	
     motorID7 = 7;
  //   motorMin7 = Integer.parseInt(prop.getProperty("motorMin7"));
  //   motorMax7 = Integer.parseInt(prop.getProperty("motorMax7"));
     motorMin7 = 0;
     motorMax7 = 100000;
     motorMaxPercent7 = 100;
     motorMinPercent7 = 10;
  //   motorTravelRange7 = Integer.parseInt(prop.getProperty("motorTravelRange7"));
     motorTravelRange7 = 48;
     maxSpeedChange7 = .02;
    	
     motorID9 = 9;
  //   motorMin9 = Integer.parseInt(prop.getProperty("motorMin9"));
  // 	 motorMax9 = Integer.parseInt(prop.getProperty("motorMax9"));
     motorMin9 = 0;
     motorMax9 = 100000;
   	 motorMaxPercent9 = 100;
   	 motorMinPercent9 = 10;
 //  	 motorTravelRange9 = Integer.parseInt(prop.getProperty("motorTravelRange9"));
   	 motorTravelRange9 = 48;
   	 maxSpeedChange9 = .02;
    	
    	
    	
    	
    	*/
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
     motorID1 = 1;
     motorMin1 = Integer.parseInt(prop.getProperty("motorMin1"));
   	 motorMax1 = Integer.parseInt(prop.getProperty("motorMax1"));
   	 motorMaxPercent1 = 100;
   	 motorMinPercent1 = 0;
   	 motorTravelRange1 = 12;
   	 maxSpeedChange1 = .02;
   	
   	 motorID2 = 2;
   	 motorMin2 = Integer.parseInt(prop.getProperty("motorMin2"));
   	 motorMax2 = Integer.parseInt(prop.getProperty("motorMax2"));
   	 motorMaxPercent2 = 100;
   	 motorMinPercent2 = 0;
   	 motorTravelRange2 = 12;
   	 maxSpeedChange2 = .02;
   	 /*
     motorID3 = 3;
     motorMin3 = Integer.parseInt(prop.getProperty("motorMin3"));
   	 motorMax3 = Integer.parseInt(prop.getProperty("motorMax3"));
   	 motorMaxPercent3 = Integer.parseInt(prop.getProperty("motorMaxPercent3"));
   	 motorMinPercent3 = Integer.parseInt(prop.getProperty("motorMinPercent3"));
   	 motorTravelRange3 = Integer.parseInt(prop.getProperty("motorTravelRange3"));
   	 maxSpeedChange3 = Integer.parseInt(prop.getProperty("maxSpeedChange3"));
   	 
     motorID4 = 4;
     motorMin4 = Integer.parseInt(prop.getProperty("motorMin4"));
   	 motorMax4 = Integer.parseInt(prop.getProperty("motorMax4"));
   	 motorMaxPercent4 = Integer.parseInt(prop.getProperty("motorMaxPercent4"));
   	 motorMinPercent4 = Integer.parseInt(prop.getProperty("motorMinPercent4"));
   	 motorTravelRange4 = Integer.parseInt(prop.getProperty("motorTravelRange4"));
   	 maxSpeedChange4 = Integer.parseInt(prop.getProperty("maxSpeedChange4"));
   	 
   	 motorID5 = 5;
     motorMin5 = Integer.parseInt(prop.getProperty("motorMin5"));
   	 motorMax5 = Integer.parseInt(prop.getProperty("motorMax5"));
   	 motorMaxPercent5 = Integer.parseInt(prop.getProperty("motorMaxPercent5"));
   	 motorMinPercent5 = Integer.parseInt(prop.getProperty("motorMinPercent5"));
   	 motorTravelRange5 = Integer.parseInt(prop.getProperty("motorTravelRange5"));
   	 maxSpeedChange5 = Integer.parseInt(prop.getProperty("maxSpeedChange5"));
   	 
   	 motorID6 = 6;
     motorMin6 = Integer.parseInt(prop.getProperty("motorMin6"));
   	 motorMax6 = Integer.parseInt(prop.getProperty("motorMax6"));
   	 motorMaxPercent6 = Integer.parseInt(prop.getProperty("motorMaxPercent6"));
   	 motorMinPercent6 = Integer.parseInt(prop.getProperty("motorMinPercent6"));
   	 motorTravelRange6 = Integer.parseInt(prop.getProperty("motorTravelRange6"));
   	 maxSpeedChange6 = Integer.parseInt(prop.getProperty("maxSpeedChange6"));
   	 
   	 motorID7 = 7;
     motorMin7 = Integer.parseInt(prop.getProperty("motorMin7"));
   	 motorMax7 = Integer.parseInt(prop.getProperty("motorMax7"));
   	 motorMaxPercent7 = Integer.parseInt(prop.getProperty("motorMaxPercent7"));
   	 motorMinPercent7 = Integer.parseInt(prop.getProperty("motorMinPercent7"));
   	 motorTravelRange7 = Integer.parseInt(prop.getProperty("motorTravelRange7"));
   	 maxSpeedChange7 = Integer.parseInt(prop.getProperty("maxSpeedChange7"));
   	 
   	 motorID8 = 8;
     motorMin8 = Integer.parseInt(prop.getProperty("motorMin8"));
   	 motorMax8 = Integer.parseInt(prop.getProperty("motorMax8"));
   	 motorMaxPercent8 = Integer.parseInt(prop.getProperty("motorMaxPercent8"));
   	 motorMinPercent8 = Integer.parseInt(prop.getProperty("motorMinPercent8"));
   	 motorTravelRange8 = Integer.parseInt(prop.getProperty("motorTravelRange8"));
   	 maxSpeedChange8 = Integer.parseInt(prop.getProperty("maxSpeedChange8"));
   	 
   	 motorID9 = 9;
     motorMin9 = Integer.parseInt(prop.getProperty("motorMin9"));
   	 motorMax9 = Integer.parseInt(prop.getProperty("motorMax9"));
   	 motorMaxPercent9 = Integer.parseInt(prop.getProperty("motorMaxPercent9"));
   	 motorMinPercent9 = Integer.parseInt(prop.getProperty("motorMinPercent9"));
   	 motorTravelRange9 = Integer.parseInt(prop.getProperty("motorTravelRange9"));
   	 maxSpeedChange9 = Integer.parseInt(prop.getProperty("maxSpeedChange9"));
   	 
   	 motorID10 = 10;
     motorMin10 = Integer.parseInt(prop.getProperty("motorMin10"));
   	 motorMax10 = Integer.parseInt(prop.getProperty("motorMax10"));
   	 motorMaxPercent10 = Integer.parseInt(prop.getProperty("motorMaxPercent10"));
   	 motorMinPercent10 = Integer.parseInt(prop.getProperty("motorMinPercent10"));
   	 motorTravelRange10 = Integer.parseInt(prop.getProperty("motorTravelRange10"));
   	 maxSpeedChange10 = Integer.parseInt(prop.getProperty("maxSpeedChange10"));
   	 
   	 motorID11 = 11;
     motorMin11 = Integer.parseInt(prop.getProperty("motorMin11"));
   	 motorMax11 = Integer.parseInt(prop.getProperty("motorMax11"));
   	 motorMaxPercent11 = Integer.parseInt(prop.getProperty("motorMaxPercent11"));
   	 motorMinPercent11 = Integer.parseInt(prop.getProperty("motorMinPercent11"));
   	 motorTravelRange11 = Integer.parseInt(prop.getProperty("motorTravelRange11"));
   	 maxSpeedChange11 = Integer.parseInt(prop.getProperty("maxSpeedChange11"));
   	 
   	 motorID12 = 12;
     motorMin12 = Integer.parseInt(prop.getProperty("motorMin12"));
   	 motorMax12 = Integer.parseInt(prop.getProperty("motorMax12"));
   	 motorMaxPercent12 = Integer.parseInt(prop.getProperty("motorMaxPercent12"));
   	 motorMinPercent12 = Integer.parseInt(prop.getProperty("motorMinPercent12"));
   	 motorTravelRange12 = Integer.parseInt(prop.getProperty("motorTravelRange12"));
   	 maxSpeedChange12 = Integer.parseInt(prop.getProperty("maxSpeedChange12"));
   	 
   	 motorID13 = 13;
     motorMin13 = Integer.parseInt(prop.getProperty("motorMin13"));
   	 motorMax13 = Integer.parseInt(prop.getProperty("motorMax13"));
   	 motorMaxPercent13 = Integer.parseInt(prop.getProperty("motorMaxPercent13"));
   	 motorMinPercent13 = Integer.parseInt(prop.getProperty("motorMinPercent13"));
   	 motorTravelRange13 = Integer.parseInt(prop.getProperty("motorTravelRange13"));
   	 maxSpeedChange13 = Integer.parseInt(prop.getProperty("maxSpeedChange13"));
   	 
   	 motorID14 = 14;
     motorMin14 = Integer.parseInt(prop.getProperty("motorMin14"));
   	 motorMax14 = Integer.parseInt(prop.getProperty("motorMax14"));
   	 motorMaxPercent14 = Integer.parseInt(prop.getProperty("motorMaxPercent14"));
   	 motorMinPercent14 = Integer.parseInt(prop.getProperty("motorMinPercent14"));
   	 motorTravelRange14 = Integer.parseInt(prop.getProperty("motorTravelRange14"));
   	 maxSpeedChange14 = Integer.parseInt(prop.getProperty("maxSpeedChange14"));
   	*/
   /*	 motorMin3 = Integer.parseInt(prop.getProperty("motorMin3"));
   	 motorMax3 = Integer.parseInt(prop.getProperty("motorMax3"));
   	 motorMaxPercent3 = 100;*/
    	
		//System.out.println("Soup.");
		
	

		// load a properties file

    	
    	
   // 	stick = new SuperJoystick(0);
    	motor1 = new HawkSuperMotor(motorID1, motorMin1, motorMax1, motorMaxPercent1, motorMinPercent1, motorTravelRange1, maxSpeedChange1, 1);                     // Motor ID, Min encoder value from config, max encoder value from config, speed limiter (%)
    	motor2 = new HawkSuperMotor(motorID2, motorMin2, motorMax2, motorMaxPercent2, motorMinPercent2, motorTravelRange2, maxSpeedChange2, 1);
    	motor3 = new HawkSuperMotor(10, 0, 100000, 100, 10, 48, .02, -1);
   	 /*
    	shooterMain = new HawkSuperMotor(motorID5, motorMin5, motorMax5, motorMaxPercent5, motorMinPercent5, motorTravelRange5, maxSpeedChange5);
    	shooterControl = new HawkSuperMotor(motorID6, motorMin6, motorMax6, motorMaxPercent6, motorMinPercent6, motorTravelRange6, maxSpeedChange6);
    	rightArmStage1 = new HawkSuperMotor(motorID7, motorMin7, motorMax7, motorMaxPercent7, motorMinPercent7, motorTravelRange7, maxSpeedChange7);
    	leftArmStage1 = new HawkSuperMotor(motorID8, motorMin8, motorMax8, motorMaxPercent8, motorMinPercent8, motorTravelRange8, maxSpeedChange8);
    	rightArmStage2 = new HawkSuperMotor(motorID9, motorMin9, motorMax9, motorMaxPercent9, motorMinPercent9, motorTravelRange9, maxSpeedChange9);
    	leftArmStage2 = new HawkSuperMotor(motorID10, motorMin10, motorMax10, motorMaxPercent10, motorMinPercent10, motorTravelRange10, maxSpeedChange10);
    	armActuatorRight = new HawkSuperMotor(motorID11, motorMin11, motorMax11, motorMaxPercent11, motorMinPercent11, motorTravelRange11, maxSpeedChange11);
    	armActuatorLeft = new HawkSuperMotor(motorID12, motorMin12, motorMax12, motorMaxPercent12, motorMinPercent12, motorTravelRange12, maxSpeedChange12);
    	smallArmMotor = new HawkSuperMotor(motorID13, motorMin13, motorMax13, motorMaxPercent13, motorMinPercent13, motorTravelRange13, maxSpeedChange13);
    	shooterAimMotor = new HawkSuperMotor(motorID14, motorMin14, motorMax14, motorMaxPercent14, motorMinPercent14, motorTravelRange14, maxSpeedChange14);
    	*/
    	//motor2 = new CANTalon(2);
    	dual1 = new HawkSuperDualMotor(motorID1, motorMin1, motorMax1, motorMaxPercent1, motorMinPercent1, motorTravelRange1, maxSpeedChange1, 1, motorID2, motorMin2, motorMax2, motorMaxPercent2, motorMinPercent2, motorTravelRange2, maxSpeedChange2, 1);
    //	motor3 = new HawkSuperMotor(3, Integer.parseInt((prop.getProperty("motorMin3"))), Integer.parseInt((prop.getProperty("motorMax3"))), 100);
    	driver = new SuperJoystick(0);
    	shooter = new SuperJoystick(1);
    	calibrator = new SuperJoystick(2);
    	myRobot = new RobotDrive(4,0);
    	
    	ones = new DigitalInput(6);
        twos = new DigitalInput(7);
        fours = new DigitalInput(8);
        eights = new DigitalInput(9);
        
        hawkDrive = new HawkDrive();
   // 	counter = 0;
    //	robotTimer = new Timer();
        
        
  /*      while(initializingMotors){
        	if(!motor1.isRevLimitSwitchClosed()){
        		motor1.set(-.2);
        	}else{
        		motor1.setPosition(0);
        	}
        	if(!motor2.isRevLimitSwitchClosed()){
        		motor2.set(-.2);
        	}else{
        		motor2.setPosition(0);
        	}
        	if(motor1.isRevLimitSwitchClosed() && motor2.isRevLimitSwitchClosed()){  //When all closed
        		initializingMotors = false;
        	}
        	try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        */
        server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam1");
        
    	}



	/**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    	hawkDrive.ahrs.reset();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	autoLoopCounter++;
    	switch(index){
    	case 0:
    	if(autoLoopCounter < 100){
    		hawkDrive.moveStraight(1, 0);
    	} else{
    		hawkDrive.wheelControl(0, 0, false, false);
    		}
    	break;
    	case 1:
    		
    	break;
    	case 2:
    		
    	break;
    	case 3:
    		
    	break;
    	case 4:
    		
    	break;
    	case 5:
    		
    	break;
    	case 6:
    		
    	break;
    	case 7:

    	break;
    	case 8:
    		
    	break;
    	case 9:
    		
    	break;
    	case 10:
    		
    	break;
    	case 11:

    	break;
    	case 12:
    		
    	break;
    	case 13:

    	break;
    	case 14:
    		
    	break;
    	case 15:
    		
    	break; 
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
    		hawkDrive.wheelControl(driver.getRawAxis(LY), driver.getRawAxis(RY),driver.isRBHeld(),driver.isLBHeld());
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
    			if(shooter.getRawAxis(Ltrigger)>0.02){
    				dual1.manualDown();
    			//	dual2.manualDown();
    			}else if(shooter.getRawAxis(Rtrigger)>0.02){
    				dual1.manualUp();
    			//	dual2.manualUp();
    			}else if(false){
    				//PUT PRESET ARM CONTROLS HERE!!!
    			}else{
    				dual1.set(0);
    			//	dual2.set(0);
    			//	System.out.println("Soup.");
    			//	dual1.set(0);
    				System.out.println(dual1.motor1.getEncPosition() + "                   " + dual1.motor2.getEncPosition());
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

    }
    


	@SuppressWarnings("deprecation")
    public void testInit(){
		SmartDashboard.putNumber("Height: ", 9);
		inches = 1;
    	



    }
    /**
     * 
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    	
    	
    	inches = SmartDashboard.getNumber("Height: ");
    	
		int index = 15;//for testing purposes
    	if(ones.get()){
    		index -= 1;
    	}
    	if(twos.get()){
    		index -= 2;
    	}
    	if(fours.get()){
    		index -= 4;
    	}
    	if(eights.get()){
    		index -= 8;
    	}
    	
 //   	System.out.println(index);
    	
    	switch(index){          //Switches motors for calibration. 0 = testing. Soup.
    	case 0:
    		/*
    		if(shooter.getRawAxis(Ltrigger)>0.02){
				dual1.manualDown();
			//	dual2.manualDown();
			}else if(shooter.getRawAxis(Rtrigger)>0.02){
				dual1.manualUp();
			//	dual2.manualUp();
			}else if(shooter.isAPushed()){
				
			}else{
				dual1.set(0);
			//	dual2.set(0);
			//	System.out.println("Soup.");
			//	dual1.set(0);
				System.out.println(dual1.motor1.getEncPosition() + "                   " + dual1.motor2.getEncPosition());
				}
				*/
    		motor3.set(shooter.getRawAxis(LY));
    		
		
    	break;
    	case 1:
    		calibrate(1, 1);
    	break;
    	case 2:
    		calibrate(2, 1);
    	break;
    	case 3:
    		calibrate(3, 1);
    	break;
    	case 4:
    		calibrate(4, 1);
    	break;
    	case 5:
    		calibrate(5, 1);
    	break;
    	case 6:
    		calibrate(6, 1);
    	break;
    	case 7:
    		calibrate(7, 1);
    	break;
    	case 8:
    		calibrate(8, 1);
    	break;
    	case 9:
    		calibrate(9, -1);
    		System.out.println(calibMotor.getEncPosition());
    	break;
    	case 10:
    		calibrate(10, -1);
    		System.out.println(calibMotor.getEncPosition());
    	break;
    	case 11:
    		calibrate(11, 1);
    	break;
    	case 12:
    		calibrate(12, 1);
    	break;
    	case 13:
    		calibrate(13, 1);
    	break;
    	case 14:
    		calibrate(14, 1);
    	break;
    	case 15:
    		calibrate(15, 1);
    	break;
    	}
		}
	public void calibrate(int id, int inversion){
		int rangeMin=0;
		int rangeMax=0;
		double calibrationLeftY;
		int ID;
		ID = id;
		calibMotor = new CANTalon(ID);
		
		SmartDashboard.putNumber("RangeMin: ", rangeMin);
		SmartDashboard.putNumber("RangeMax: ", rangeMax);
//		SmartDashboard.putNumber("Encoder Position: ", calibMotor.getEncPosition());
		SmartDashboard.putNumber("Range: ", rangeMax - rangeMin);
		
		if(calibMotor.isRevLimitSwitchClosed() || calibrator.isAPushed()){
			calibMotor.set(0);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			calibMotor.setPosition(0);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rangeMin = calibMotor.getEncPosition() * inversion;
			while(calibMotor.isRevLimitSwitchClosed()){
			calibMotor.set(.2 * inversion);
			}
		}
		if(calibrator.isYPushed()){
			calibMotor.set(0);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rangeMax = calibMotor.getEncPosition() * inversion;
			calibrator.clearY();
		}
		
		
		if(calibrator.getRawAxis(LY) >-0.1 && calibrator.getRawAxis(LY)<0.1){
    		calibrationLeftY = 0;
    	}else{
    		calibrationLeftY = calibrator.getRawAxis(LY);
    	}
		calibMotor.set(calibrationLeftY/8);
		
		
		if(calibrator.isStartPushed()){
			Properties prop = new Properties();
			OutputStream output = null;
			InputStream input = null;
		try{
			
			input = new FileInputStream("/home/lvuser/config.properties");

			// load a properties file
			prop.load(input);
			
		output = new FileOutputStream("/home/lvuser/config.properties");
		
		prop.setProperty("motorMin" + ID, Integer.toString(rangeMin));
		prop.setProperty("motorMax" + ID, Integer.toString(rangeMax));
		
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
			calibrator.clearButtons();
		}
		if(calibrator.isBackPushed()){
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
/*	public void armToHeight(double targetHeight){
		if(dual1.getHeight() + dual2.getHeight()<targetHeight-.1){
			dual1.goToHeight(dual1.motor1.travel);
			dual2.goToHeight(dual2.motor1.travel);
		}else if(dual1.getHeight() + dual2.getHeight()>targetHeight+.1){
			dual1.goToHeight(0);
			dual2.goToHeight(0);
		}else{
			
		}
	}*/
}   
    	//LiveWindow.run(); This should be uncommented when LiveWindow is desired in test mode
    	
    
    


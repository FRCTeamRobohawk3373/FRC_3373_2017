package org.usfirst.frc.team3373.robot;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//@author Joey Dyer, Drew Marino, Alex Iasso, Dillon Rose

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	int rangeMin=0;
	int rangeMax=0;
	
	CANTalon calibMotor;
	
	boolean goingSallyPort = false;
	boolean goingDrawbridge = false;
	boolean goingPortcullis = false;
	
	CameraServer server;
	double inches;
	RobotDrive myRobot;
	int autoLoopCounter;
	
	
	HawkSuperMotor shooterMain;
	HawkSuperMotor shooterControl;
	HawkSuperMotor smallArmMotor;
	HawkSuperMotor shooterAimMotor;

	HawkSuperDualMotor armStage2;
	HawkSuperDualMotor armStage1;

	HawkDualLinearActuator armActuators;
	
	SuperJoystick driver;
	SuperJoystick shooter;
	SuperJoystick calibrator;
	
	HawkDrive hawkDrive;
	
	boolean manualArm = false;
	
	InputStream input = null;
	Properties prop = new Properties();
	
/*	int motorID1;
	int motorMin1;
	int motorMax1;
	int motorMaxPercent1;
	int motorMinPercent1;
	double motorTravelRange1;
	double maxSpeedChange1;
	int motorDirection1;
	int limitSwitchForwID1;
	int limitSwitchRevID1;

	
	int motorID2;
	int motorMin2;
	int motorMax2;
	int motorMaxPercent2;
	int motorMinPercent2;
	double motorTravelRange2;
	double maxSpeedChange2;
	int motorDirection2;
	int limitSwitchForwID2;
	int limitSwitchRevID2;

	int motorID3;
	int motorMin3;
	int motorMax3;
	int motorMaxPercent3;
	int motorMinPercent3;
	double motorTravelRange3;
	double maxSpeedChange3;
	int motorDirection3;
	int limitSwitchForwID3;
	int limitSwitchRevID3;

	int motorID4;
	int motorMin4;
	int motorMax4;
	int motorMaxPercent4;
	int motorMinPercent4;
	double motorTravelRange4;
	double maxSpeedChange4;
	int motorDirection4;
	int limitSwitchForwID4;
	int limitSwitchRevID4;
 	*/
	int motorID5;
	int motorMin5;
	int motorMax5;
	int motorMaxPercent5;
	int motorMinPercent5;
	double motorTravelRange5;
	double maxSpeedChange5;
	int motorDirection5;
	int limitSwitchForwID5;
	int limitSwitchRevID5;

	int motorID6;
	int motorMin6;
	int motorMax6;
	int motorMaxPercent6;
	int motorMinPercent6;
	double motorTravelRange6;
	double maxSpeedChange6;
	int motorDirection6;
	int limitSwitchForwID6;
	int limitSwitchRevID6;

	int motorID7;
	int motorMin7;
	int motorMax7;
	int motorMaxPercent7;
	int motorMinPercent7;
	double motorTravelRange7;
	double maxSpeedChange7;
	int motorDirection7;
	int limitSwitchForwID7;
	int limitSwitchRevID7;

	int motorID8;
	int motorMin8;
	int motorMax8;
	int motorMaxPercent8;
	int motorMinPercent8;
	double motorTravelRange8;
	double maxSpeedChange8;
	int motorDirection8;
	int limitSwitchForwID8;
	int limitSwitchRevID8;

	int motorID9;
	int motorMin9;
	int motorMax9;
	int motorMaxPercent9;
	int motorMinPercent9;
	double motorTravelRange9;
	double maxSpeedChange9;
	int motorDirection9;
	int limitSwitchForwID9;
	int limitSwitchRevID9;

	int motorID10;
	int motorMin10;
	int motorMax10;
	int motorMaxPercent10;
	int motorMinPercent10;
	double motorTravelRange10;
	double maxSpeedChange10;
	int motorDirection10;
	int limitSwitchForwID10;
	int limitSwitchRevID10;

	int motorID11;
	double actuatorMaxPotValue11;
	double actuatorMinPotValue11;
	double maxSpeedChange11;
	int limitSwitchForwID11;
	int limitSwitchRevID11;

	int motorID12;
	double actuatorMaxPotValue12;
	double actuatorMinPotValue12;
	double maxSpeedChange12;
	int limitSwitchForwID12;
	int limitSwitchRevID12;

	int motorID13;
	int motorMin13;
	int motorMax13;
	int motorMaxPercent13;
	int motorMinPercent13;
	double motorTravelRange13;
	double maxSpeedChange13;
	int motorDirection13;
	int limitSwitchForwID13;
	int limitSwitchRevID13;

	int motorID14;
	int motorMin14;
	int motorMax14;
	int motorMaxPercent14;
	int motorMinPercent14;
	double motorTravelRange14;
	double maxSpeedChange14;
	int motorDirection14;
	int limitSwitchForwID14;
	int limitSwitchRevID14;



	
//	int counter;
	
	DigitalInput armLimitSwitch;
	
    DigitalInput ones;
    DigitalInput twos;
    DigitalInput fours;
    DigitalInput eights;
    DigitalInput test;
    
    DigitalOutput solenoid7;
	
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
  //   motorTravelRange7 = Double.parseDouble(prop.getProperty("motorTravelRange7"));
     motorTravelRange7 = 48;
     maxSpeedChange7 = .02;
    	
     motorID9 = 9;
  //   motorMin9 = Integer.parseInt(prop.getProperty("motorMin9"));
  // 	 motorMax9 = Integer.parseInt(prop.getProperty("motorMax9"));
     motorMin9 = 0;
     motorMax9 = 100000;
   	 motorMaxPercent9 = 100;
   	 motorMinPercent9 = 10;
 //  	 motorTravelRange9 = Double.parseDouble(prop.getProperty("motorTravelRange9"));
   	 motorTravelRange9 = 48;
   	 maxSpeedChange9 = .02;
    	
    	
    	
    	
    	*/
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    /* motorID1 = 1;
     motorMin1 = Integer.parseInt(prop.getProperty("motorMin1"));
   	 motorMax1 = Integer.parseInt(prop.getProperty("motorMax1"));
   	 motorMaxPercent1 = 100;
   	 motorMinPercent1 = 0;
   	 motorTravelRange1 = 12;
   	 maxSpeedChange1 = .02;
   	 motorDirection1 = Integer.parseInt(prop.getProperty("motorDirection1"));
   	 limitSwitchForwID1 = Integer.parseInt(prop.getProperty("limitSwitchForwID1"));
 	 limitSwitchRevID1 =  Integer.parseInt(prop.getProperty("limitSwitchRevID1"));
   	
   	 motorID2 = 2;
   	 motorMin2 = Integer.parseInt(prop.getProperty("motorMin2"));
   	 motorMax2 = Integer.parseInt(prop.getProperty("motorMax2"));
   	 motorMaxPercent2 = 100;
   	 motorMinPercent2 = 0;
   	 motorTravelRange2 = 12;
   	 maxSpeedChange2 = .02;
   	 motorDirection2 = Integer.parseInt(prop.getProperty("motorDirection2"));
   	 limitSwitchForwID2 = Integer.parseInt(prop.getProperty("limitSwitchForwID2"));
 	 limitSwitchRevID2 =  Integer.parseInt(prop.getProperty("limitSwitchRevID2"));

     motorID3 = 3;
     motorMin3 = Integer.parseInt(prop.getProperty("motorMin3"));
   	 motorMax3 = Integer.parseInt(prop.getProperty("motorMax3"));
   	 motorMaxPercent3 = Integer.parseInt(prop.getProperty("motorMaxPercent3"));
   	 motorMinPercent3 = Integer.parseInt(prop.getProperty("motorMinPercent3"));
   	 motorTravelRange3 = Double.parseDouble(prop.getProperty("motorTravelRange3"));
   	 maxSpeedChange3 = Double.parseDouble(prop.getProperty("maxSpeedChange3"));
   	 motorDirection3 = Integer.parseInt(prop.getProperty("motorDirection3"));
   	 limitSwitchForwID3 = Integer.parseInt(prop.getProperty("limitSwitchForwID3"));
 	 limitSwitchRevID3 =  Integer.parseInt(prop.getProperty("limitSwitchRevID3"));   	 
   	 
   	 
     motorID4 = 4;
     motorMin4 = Integer.parseInt(prop.getProperty("motorMin4"));
   	 motorMax4 = Integer.parseInt(prop.getProperty("motorMax4"));
   	 motorMaxPercent4 = Integer.parseInt(prop.getProperty("motorMaxPercent4"));
   	 motorMinPercent4 = Integer.parseInt(prop.getProperty("motorMinPercent4"));
   	 motorTravelRange4 = Double.parseDouble(prop.getProperty("motorTravelRange4"));
   	 maxSpeedChange4 = Double.parseDouble(prop.getProperty("maxSpeedChange4"));
   	 motorDirection4 = Integer.parseInt(prop.getProperty("motorDirection4"));
   	 limitSwitchForwID4 = Integer.parseInt(prop.getProperty("limitSwitchForwID4"));
 	 limitSwitchRevID4 =  Integer.parseInt(prop.getProperty("limitSwitchRevID4"));
   */	 
   	 motorID5 = 5;
     motorMin5 = Integer.parseInt(prop.getProperty("motorMin5"));
   	 motorMax5 = Integer.parseInt(prop.getProperty("motorMax5"));
   	 motorMaxPercent5 = Integer.parseInt(prop.getProperty("motorMaxPercent5"));
   	 motorMinPercent5 = Integer.parseInt(prop.getProperty("motorMinPercent5"));
   	 motorTravelRange5 = Double.parseDouble(prop.getProperty("motorTravelRange5"));
   	 maxSpeedChange5 = Double.parseDouble(prop.getProperty("maxSpeedChange5"));
   	 motorDirection5 = Integer.parseInt(prop.getProperty("motorDirection5"));
   	 limitSwitchForwID5 = Integer.parseInt(prop.getProperty("limitSwitchForwID5"));
 	 limitSwitchRevID5 =  Integer.parseInt(prop.getProperty("limitSwitchRevID5"));
   	 
   	 motorID6 = 6;
     motorMin6 = Integer.parseInt(prop.getProperty("motorMin6"));
   	 motorMax6 = Integer.parseInt(prop.getProperty("motorMax6"));
   	 motorMaxPercent6 = Integer.parseInt(prop.getProperty("motorMaxPercent6"));
   	 motorMinPercent6 = Integer.parseInt(prop.getProperty("motorMinPercent6"));
   	 motorTravelRange6 = Double.parseDouble(prop.getProperty("motorTravelRange6"));
   	 maxSpeedChange6 = Double.parseDouble(prop.getProperty("maxSpeedChange6"));
   	 motorDirection6 = Integer.parseInt(prop.getProperty("motorDirection6"));
   	 limitSwitchForwID6 = Integer.parseInt(prop.getProperty("limitSwitchForwID6"));
 	 limitSwitchRevID6 =  Integer.parseInt(prop.getProperty("limitSwitchRevID6"));
 	 
   	 motorID7 = 7;
     motorMin7 = Integer.parseInt(prop.getProperty("motorMin7"));
   	 motorMax7 = Integer.parseInt(prop.getProperty("motorMax7"));
   	 motorMaxPercent7 = Integer.parseInt(prop.getProperty("motorMaxPercent7"));
   	 motorMinPercent7 = Integer.parseInt(prop.getProperty("motorMinPercent7"));
   	 motorTravelRange7 = Double.parseDouble(prop.getProperty("motorTravelRange7"));
   	 maxSpeedChange7 = Double.parseDouble(prop.getProperty("maxSpeedChange7"));
   	 motorDirection7 = Integer.parseInt(prop.getProperty("motorDirection7"));
   	 limitSwitchForwID7 = Integer.parseInt(prop.getProperty("limitSwitchForwID7"));
 	 limitSwitchRevID7 =  Integer.parseInt(prop.getProperty("limitSwitchRevID7"));
   	 
   	 motorID8 = 8;
     motorMin8 = Integer.parseInt(prop.getProperty("motorMin8"));
   	 motorMax8 = Integer.parseInt(prop.getProperty("motorMax8"));
   	 motorMaxPercent8 = Integer.parseInt(prop.getProperty("motorMaxPercent8"));
   	 motorMinPercent8 = Integer.parseInt(prop.getProperty("motorMinPercent8"));
   	 motorTravelRange8 = Double.parseDouble(prop.getProperty("motorTravelRange8"));
   	 maxSpeedChange8 = Double.parseDouble(prop.getProperty("maxSpeedChange8"));
   	 motorDirection8 = Integer.parseInt(prop.getProperty("motorDirection8"));
   	 limitSwitchForwID8 = Integer.parseInt(prop.getProperty("limitSwitchForwID8"));
 	 limitSwitchRevID8 =  Integer.parseInt(prop.getProperty("limitSwitchRevID8"));
   	 
   	 motorID9 = 9;
     motorMin9 = Integer.parseInt(prop.getProperty("motorMin9"));
   	 motorMax9 = Integer.parseInt(prop.getProperty("motorMax9"));
   	 motorMaxPercent9 = Integer.parseInt(prop.getProperty("motorMaxPercent9"));
   	 motorMinPercent9 = Integer.parseInt(prop.getProperty("motorMinPercent9"));
   	 motorTravelRange9 = Double.parseDouble(prop.getProperty("motorTravelRange9"));
   	 maxSpeedChange9 = Double.parseDouble(prop.getProperty("maxSpeedChange9"));
   	 motorDirection9 = Integer.parseInt(prop.getProperty("motorDirection9"));
   	 limitSwitchForwID9 = Integer.parseInt(prop.getProperty("limitSwitchForwID9"));
	 limitSwitchRevID9 =  Integer.parseInt(prop.getProperty("limitSwitchRevID9"));
  	 
   	 motorID10 = 10;
     motorMin10 = Integer.parseInt(prop.getProperty("motorMin10"));
   	 motorMax10 = Integer.parseInt(prop.getProperty("motorMax10"));
   	 motorMaxPercent10 = Integer.parseInt(prop.getProperty("motorMaxPercent10"));
   	 motorMinPercent10 = Integer.parseInt(prop.getProperty("motorMinPercent10"));
   	 motorTravelRange10 = Double.parseDouble(prop.getProperty("motorTravelRange10"));
   	 maxSpeedChange10 = Double.parseDouble(prop.getProperty("maxSpeedChange10"));
   	 motorDirection10 = Integer.parseInt(prop.getProperty("motorDirection10"));
   	 limitSwitchForwID10 = Integer.parseInt(prop.getProperty("limitSwitchForwID10"));
 	 limitSwitchRevID10 =  Integer.parseInt(prop.getProperty("limitSwitchRevID10"));
   	 
   	 motorID11 = 11;
     actuatorMaxPotValue11 = Double.parseDouble(prop.getProperty("actuatorMaxPotValue11"));
   	 actuatorMinPotValue11 = Double.parseDouble(prop.getProperty("actuatorMinPotValue11"));
     maxSpeedChange11 = Double.parseDouble(prop.getProperty("maxSpeedChange11"));
   	 limitSwitchForwID11 = Integer.parseInt(prop.getProperty("limitSwitchForwID11"));
 	 limitSwitchRevID11 =  Integer.parseInt(prop.getProperty("limitSwitchRevID11"));
   	 
   	 motorID12 = 12;
   	 actuatorMaxPotValue12 = Double.parseDouble(prop.getProperty("actuatorMaxPotValue12"));
  	 actuatorMinPotValue12 = Double.parseDouble(prop.getProperty("actuatorMinPotValue12"));
   	 maxSpeedChange12 = Double.parseDouble(prop.getProperty("maxSpeedChange12"));
   	 limitSwitchForwID12 = Integer.parseInt(prop.getProperty("limitSwitchForwID12"));
 	 limitSwitchRevID12 =  Integer.parseInt(prop.getProperty("limitSwitchRevID12")); 
   	 
   	 motorID13 = 13;
     motorMin13 = Integer.parseInt(prop.getProperty("motorMin13"));
   	 motorMax13 = Integer.parseInt(prop.getProperty("motorMax13"));
   	 motorMaxPercent13 = Integer.parseInt(prop.getProperty("motorMaxPercent13"));
   	 motorMinPercent13 = Integer.parseInt(prop.getProperty("motorMinPercent13"));
   	 motorTravelRange13 = Double.parseDouble(prop.getProperty("motorTravelRange13"));
   	 maxSpeedChange13 = Double.parseDouble(prop.getProperty("maxSpeedChange13"));
   	 motorDirection13 = Integer.parseInt(prop.getProperty("motorDirection13"));
   	 limitSwitchForwID13 = Integer.parseInt(prop.getProperty("limitSwitchForwID13"));
 	 limitSwitchRevID13 =  Integer.parseInt(prop.getProperty("limitSwitchRevID13"));
   	 
   	 motorID14 = 14;
     motorMin14 = Integer.parseInt(prop.getProperty("motorMin14"));
   	 motorMax14 = Integer.parseInt(prop.getProperty("motorMax14"));
   	 motorMaxPercent14 = Integer.parseInt(prop.getProperty("motorMaxPercent14"));
   	 motorMinPercent14 = Integer.parseInt(prop.getProperty("motorMinPercent14"));
   	 motorTravelRange14 = Double.parseDouble(prop.getProperty("motorTravelRange14"));
   	 maxSpeedChange14 = Double.parseDouble(prop.getProperty("maxSpeedChange14"));
   	 motorDirection14 = Integer.parseInt(prop.getProperty("motorDirection14"));
   	 limitSwitchForwID14 = Integer.parseInt(prop.getProperty("limitSwitchForwID14"));
 	 limitSwitchRevID14 =  Integer.parseInt(prop.getProperty("limitSwitchRevID14"));

   	
   /*	 motorMin3 = Integer.parseInt(prop.getProperty("motorMin3"));
   	 motorMax3 = Integer.parseInt(prop.getProperty("motorMax3"));
   	 motorMaxPercent3 = 100;*/
    	
		//System.out.println("Soup.");
		
	

		// load a properties file

    	
   	 
    	shooterMain = new HawkSuperMotor(motorID5, motorMin5, motorMax5, motorMaxPercent5, motorMinPercent5, motorTravelRange5, maxSpeedChange5, motorDirection5, limitSwitchForwID5, limitSwitchRevID5);
    	shooterControl = new HawkSuperMotor(motorID6, motorMin6, motorMax6, motorMaxPercent6, motorMinPercent6, motorTravelRange6, maxSpeedChange6, motorDirection6, limitSwitchForwID6, limitSwitchRevID6);
    	    /*
    	armActuatorRight = new HawkSuperMotor(motorID11, motorMin11, motorMax11, motorMaxPercent11, motorMinPercent11, motorTravelRange11, maxSpeedChange11, motorDirection11, limitSwitchForwID11, limitSwitchRevID11);
    	armActuatorLeft = new HawkSuperMotor(motorID12, motorMin12, motorMax12, motorMaxPercent12, motorMinPercent12, motorTravelRange12, maxSpeedChange12, motorDirection12, limitSwitchForwID12, limitSwitchRevID12);
    	*/
    	smallArmMotor = new HawkSuperMotor(motorID13, motorMin13, motorMax13, motorMaxPercent13, motorMinPercent13, motorTravelRange13, maxSpeedChange13, motorDirection13, limitSwitchForwID13, limitSwitchRevID13);
    	
    	armStage1 = new HawkSuperDualMotor(motorID7, motorMin7, motorMax7, motorMaxPercent7, motorMinPercent7, motorTravelRange7, maxSpeedChange7, motorDirection7, limitSwitchForwID7, limitSwitchRevID7, motorID8, motorMin8, motorMax8, motorMaxPercent8, motorMinPercent8, motorTravelRange8, maxSpeedChange8, motorDirection8, limitSwitchForwID8, limitSwitchRevID8);
    	armStage2 = new HawkSuperDualMotor(motorID9, motorMin9, motorMax9, motorMaxPercent9, motorMinPercent9, motorTravelRange9, maxSpeedChange9, motorDirection9, limitSwitchForwID9, limitSwitchRevID9, motorID10, motorMin10, motorMax10, motorMaxPercent10, motorMinPercent10, motorTravelRange10, maxSpeedChange10, motorDirection10, limitSwitchForwID10, limitSwitchRevID10 );
    	
    	shooterAimMotor = new HawkSuperMotor(motorID14, motorMin14, motorMax14, motorMaxPercent14, motorMinPercent14, motorTravelRange14, maxSpeedChange14, motorDirection14, limitSwitchForwID14, limitSwitchRevID14);
    	
    	
    	//armStage2 = new HawkSuperDualMotor(motorID9, motorMin9, motorMax9, motorMaxPercent9, motorMinPercent9, motorTravelRange9, maxSpeedChange9, motorDirection9, limitSwitchForwID9, limitSwitchRevID9, motorID10, motorMin10, motorMax10, motorMaxPercent10, motorMinPercent10, motorTravelRange10, maxSpeedChange10, motorDirection10, limitSwitchForwID10, limitSwitchRevID10);
    	armActuators = new HawkDualLinearActuator(motorID11, 10000, 0, .02, limitSwitchForwID11, limitSwitchRevID11, motorID12, 10000, 0, .02, limitSwitchForwID12, limitSwitchRevID12);

    	driver = new SuperJoystick(0);
    	shooter = new SuperJoystick(1);
    	calibrator = new SuperJoystick(2);
    	myRobot = new RobotDrive(4,0);
    	
    	ones = new DigitalInput(0);
        twos = new DigitalInput(1);
        fours = new DigitalInput(2);
        eights = new DigitalInput(3);
        test = new DigitalInput(4);
        armLimitSwitch = new DigitalInput(6);
        
      // solenoid7 = new DigitalOutput(7);
        
        hawkDrive = new HawkDrive();
   // 	counter = 0;
    //	robotTimer = new Timer();
        //25.75 travel-stage 1
        
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
    	/*while(true){
    	solenoid7.set(true);
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	solenoid7.set(false);
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	}
    	*/
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	autoLoopCounter++;
    	
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
    	
    	switch(index){
    	case 0:
    		//Enter defense zone. - need to adjust time
    	if(autoLoopCounter < 200){
    		hawkDrive.moveStraight(.5, 0);
    	} else{
    		hawkDrive.wheelControl(0, 0, false, false);
    		}
    	break;
    	case 1:
    		//Low Bar - need to adjust time
    		if(autoLoopCounter < 500){
    			hawkDrive.moveStraight(.5, 0);
    		}
    		else{
    			hawkDrive.wheelControl(0, 0, false, false);
    		}
    	break;
    	case 2:
    		//Rough Terrain - will have to adjust and change values (speed and/or time)
    		if(autoLoopCounter < 500){
    			hawkDrive.moveStraight(.5, 0);
    		}
    		else{
    			hawkDrive.wheelControl(0, 0, false, false);
    		}    		
    	break;
    	case 3:
    		//Moat - will have to test for speed and/or time
    		if(autoLoopCounter < 500){
    			hawkDrive.moveStraight(.5, 0);
    		}
    		else{
    			hawkDrive.wheelControl(0, 0, false, false);
    		}
    	break;
    	case 4:
    		//Rock Wall - will again have to test for different speeds and/or time 
    	if(autoLoopCounter < 500){
			hawkDrive.moveStraight(.5, 0);
		}
		else{
			hawkDrive.wheelControl(0, 0, false, false);
		}
    	break;
    	case 5:
    		//Ramparts - will have to test speed and time
    		if(autoLoopCounter < 500){
    			hawkDrive.moveStraight(.5, 0);
    		}
    		else{
    			hawkDrive.wheelControl(0, 0, false, false);
    		}
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
    		driver.clearButtons();
    	//	motor1.set(driver.getRawAxis(LY));
    	//	motor2.set(driver.getRawAxis(RY));
    		
    		
    		//
    		//    DRIVER CONTROLS
    		//
    		
    		
                                                                      //Drive System
    		hawkDrive.wheelControl(driver.getRawAxis(LY), driver.getRawAxis(RY),driver.isRBHeld(),driver.isLBHeld());
    		

    		if(driver.getRawAxis(Rtrigger) > .02){
    			armStage2.goToHeight(armStage2.motor1.travel);
    		}
    		if(driver.getRawAxis(Ltrigger) > .02){
    			armStage2.goToHeight(0);

    			//dual3.goToHeight(dual3.getHeight - 2 (to be adjusted)) and to be uncommented  eventually
    			
    		}
    		
    		
    		//
    		//    SHARED CONTROLS
    		//
    		
    		
    		
    		if(driver.getPOV() == 180 || shooter.getPOV() == 180){      //Down on the D-Pad    (Toggles Drawbridge Drive)
    			if(!D_Pad1){
    			goingDrawbridge = true;
    			}else{
    			goingDrawbridge=false;
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
        			goingSallyPort = true;
        			}else{
        			goingSallyPort=false;
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
        			goingPortcullis = true;
        			}else{
        			goingPortcullis=false;
        			}
        			try {
    					Thread.sleep(150);      //Prevents pressing more than once too fast
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				};
    		}
    		
    	/*	if(D_Pad1){                  //Down, Drawbridge automation           
    			smallArmMotor.set(1);
    		}
    		if(D_Pad2){                 //Right, Sally Port automation
    			smallArmMotor.set(-1);
    		}
    		
    		if(D_Pad3){                //Left, Portcullis automation
    			smallArmMotor.set(1);
    		}*/
    		
    		
    		
    		
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
    			
    	/*		if(shooter.isAPushed()){
    				counterBoolShooterA = true;
    			}
    			if(counterBoolShooterA){
        				if(counterShooterA == 0){
        					counterShooterA = robotTimer;
        				}
        				if(robotTimer<counterShooterA+50){                    //Runs the sequence for 50 loops, or one second
        				smallArmMotor.set(1);
        				}
        				else if(robotTimer>=counterShooterA+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
        				smallArmMotor.set(0);
        				shooter.clearButtons();
        				counterShooterA = 0;
        				counterBoolShooterA = false;
        				}
        			
    			}*/
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
        				shooterMain.set(-1);
        				System.out.println("Powering up.");
        				}else if(robotTimer<counterShooterX2+500){
        				shooterMain.set(-1);
        				shooterControl.set(1);
        				System.out.println("Firing.");
        				}
        				else if(robotTimer>=counterShooterX2+500){                 //Resets all variables, and ends sequence (after 250 more loops, or five seconds)
        				shooterMain.set(0);
        				shooterControl.set(0);
        				shooter.clearButtons();
        				counterShooterX = 0;
        				counterBoolShooterX = false;
        				counterShooterX2 = 0;
        				counterBoolShooterX2 = false;
        				System.out.println("Resetting.");
        				}
    				}
    				else{
    					counterBoolShooterX = false;
    					counterBoolShooterX2 = false;
    					counterShooterX = 0;
    					counterShooterX2 = 0;
    					shooterMain.set(0);
        				shooterControl.set(0);
    				}
    			}
    			
				if(shooter.isLBHeld()){
					//sniperMode = true;
				}
				//goToAngle(getAngle() - 2, sniperMode);
			}
			else if(shooter.getRawAxis(Rtrigger) > .02){
				if(shooter.isLBHeld()){
    				//sniperMode = true;
				}
				//goToAngle(getAngle() + 2, sniperMode);
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
    			if(shooter.isRStickHeld()){
    			if(armStage2.motor1.getCurrentHeight()<armStage2.motor1.travel-5){
    				armStage2.goToHeight(armStage2.motor1.travel-4);
    				armActuators.goToHeight(10);
    			}else{
    				armActuators.goToHeight(1);
    			}
    			}
    			/*if(shooter.isAPushed()){
    				counterBoolShooterA = true;
    			}
    			if(counterBoolShooterA){
        				if(counterShooterA == 0){
        					counterShooterA = robotTimer;
        				}
        				if(robotTimer<counterShooterA+50){                    //Runs the sequence for 50 loops, or one second
        				//motor1.set(1);
        				}
        				else if(robotTimer>=counterShooterA+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
        				motor1.set(0);
        				shooter.clearButtons();
        				counterShooterA = 0;
        				counterBoolShooterA = false;
        				}
        			*/
    			//}
    			if(shooter.isBPushed()){
    				counterBoolShooterB = true;       //Begins the B button sequence
    			}
    			if(counterBoolShooterB){
    				if(counterShooterB == 0){
    					counterShooterB = robotTimer;
    				}
    				if(robotTimer<counterShooterB+50){                    //Runs the sequence for 50 loops, or one second
    				smallArmMotor.set(-1);
    				}
    				else if(robotTimer>=counterShooterB+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				smallArmMotor.set(0);
    				shooter.clearButtons();
    				counterShooterB = 0;
    				counterBoolShooterB = false;
    				}
    			}
    		/*	if(shooter.isXPushed()){
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
    				*/
    			//}
    			if(shooter.isStartPushed()){
    				if(counterBoolShooterStart == false){
    				counterBoolShooterStart = true;
    				}else{
    				counterBoolShooterStart = false;
    				shooter.clearButtons();
    				counterShooterStart = 0;
    				smallArmMotor.set(0);
    				}
    			} //motor 5 has a limit switch set to 1 (needs to be fixed)
				shooter.clearButtons();
    			if(counterBoolShooterStart){
    				if(counterShooterStart == 0){
    					counterShooterStart = robotTimer;
    				}
    				if(robotTimer<counterShooterStart+500){                    //Runs the sequence for 50 loops, or one second
    				smallArmMotor.set(-1);
    				}
    				else if(robotTimer>=counterShooterStart+500){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				smallArmMotor.set(0);
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
    				smallArmMotor.set(-1);
    				}
    				else if(robotTimer>=counterShooterLS+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				smallArmMotor.set(0);
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
    				smallArmMotor.set(1);
    				}
    				else if(robotTimer>=counterShooterRS+50){                 //Resets all variables, and ends sequence (after 50 loops, or one second)
    				smallArmMotor.set(0);
    				shooter.clearButtons();
    				counterShooterRS = 0;
    				counterBoolShooterRS = false;
    				}
    			}
    			
    			
    			
    			if(shooter.getRawAxis(RY)<-0.1 && shooter.isLBHeld()){
    				//dual1.sniperDown();
    			}else if(shooter.getRawAxis(RY)>0.1 && shooter.isLBHeld()){
    				//dual1.sniperUp();
    			}
    			
    			
    			
    			if(shooter.getRawAxis(Ltrigger)>0.02 && shooter.isLBHeld()){
    				armStage1.sniperToHeight(0);
    			}else if(shooter.getRawAxis(Rtrigger)>0.02 && shooter.isLBHeld()){
    				armStage1.sniperToHeight(motorTravelRange7);
    			}else if(shooter.getRawAxis(Ltrigger)>0.02){
    				armStage1.goToHeight(0);
    				System.out.println("down");
    			}else if(shooter.getRawAxis(Rtrigger)>0.02){
    				armStage1.goToHeight(motorTravelRange7);
    				System.out.println("up");
    			}else if(shooter.isAPushed()){
    				goingSallyPort = true;
    				shooter.clearA();
    				//PUT PRESET ARM CONTROLS HERE!!!
    			}else if(shooter.isXPushed()){
    				goingDrawbridge = true;
    				shooter.clearX();
    			}else if(shooter.isBPushed()){
    				goingPortcullis = true;
    				shooter.clearB(); 
    			} else{
    				//dual1.set(0);
    			//	dual2.set(0);
    			//	System.out.println("Soup.");
    			//	dual1.set(0);
    			//	System.out.println(dual1.motor1.getEncPosition() + "                   " + dual1.motor2.getEncPosition());
    		}
    			/*if(goingSallyPort){
    				armToHeight(12);
    			}else if(goingDrawbridge){
    				armToHeight(12);
    			}else if(goingPortcullis){
    				armToHeight(12);
    			}
    		*/
    		}
    
    		//SHOOTER AND ARM CONTROLS (Function in both modes)	
    		if(shooter.isBackHeld()){
				if(shooterControl.isFwdLimitSwitchClosed()){
				shooterMain.set(.8);
				shooterControl.set(-1);
				}else{
				shooterMain.set(0);
				shooterControl.set(0);
				}
				System.out.println("Yay.");
			}else if(!counterBoolShooterX){
				shooterControl.set(0);
				shooterMain.set(0);
			}
    			if(shooter.getRawAxis(LY)>0.1){
					//shooterAimMotor.setScaled(1);
				}
				if(shooter.getRawAxis(LY)<0.1){
					//shooterAimMotor.setScaled(-1);
				}
				
				
				if(shooter.getRawAxis(Ltrigger)>.2 && !(shooterAimMotor.getEncPosition()<motorMin14+50)){
					shooterAimMotor.set(-shooter.getRawAxis(Ltrigger)/3);
						System.out.println("3");
				}else if(shooter.getRawAxis(Rtrigger)>.2 && !(shooterAimMotor.getEncPosition()>motorMax14 - 50)){
					shooterAimMotor.set(shooter.getRawAxis(Rtrigger)/3);
					System.out.println("4");
				}else if(shooterAimMotor.getEncPosition()>motorMax14 - 50) {
					shooterAimMotor.set(0);
				}else if(shooterAimMotor.getEncPosition()<motorMin14+50){
					shooterAimMotor.set(0);
				}else if(shooter.isRBHeld()){
					shooterAimMotor.goToHeight(45);
				}else if(shooterAimMotor.getEncPosition()<(motorMax14 / 3)){
					shooterAimMotor.set(.1);
					System.out.println("Soup.");
				}else{
					shooterAimMotor.set(.05);
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

    	}    


	@SuppressWarnings("deprecation")
    public void testInit(){
		SmartDashboard.putNumber("Height: ", 9);
		inches = 1;
    	

		shooterAimMotor.setEncPosition(0);

    }
    /**
     * 
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    	
    //	System.out.println(index);
    	
    	robotTimer++;
    	
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
  /*  	System.out.println(index);
    	System.out.println("Digital Input 0: " + ones.get());
    	System.out.println("Digital Input 1: " + twos.get());
    	System.out.println("Digital Input 2: " + fours.get());
    	System.out.println("Digital Input 3: " + eights.get());
    	System.out.println("DI Port 5: " + test.get()); */

    	switch(index){          //Switches motors for calibration. 0 = testing. Soup.
    	case 0:
    		System.out.println(shooterAimMotor.getEncPosition());
    		
    	/*	if(!armLimitSwitch.get()){
    			armStage1.initDown();
    			System.out.println("Going down.");
    		}else{
    			armStage1.motor1.setEncPosition(0);
    			armStage1.motor2.setEncPosition(0);
    			System.out.println(armStage1.motor1.getEncPosition());
    			System.out.println(armStage1.motor2.getEncPosition());
    		}
    		
    		
    	*/	
    		/*
    		shooterAimMotor.goToHeight(15);
    		System.out.println(shooterAimMotor.getCurrentHeight());
    		*/
    		System.out.println("Motor1 Pot Value:" + armActuators.motor1.getAnalogInRaw());
    		System.out.println("Motor1 Pot Value Pos:" + armActuators.motor1.getAnalogInPosition());
    		System.out.println("Motor2 Pot Value:" + armActuators.motor2.getAnalogInRaw());
    		System.out.println("Motor2 Pot Value Pos:" + armActuators.motor2.getAnalogInPosition());
    		armActuators.motor1.set(shooter.getRawAxis(LY/8));
    		armActuators.motor2.set(shooter.getRawAxis(RY/8));

    	break;
    	case 1:
    		calibrate(1, 1, false);
    		//System.out.println("Soup.");
    	break;
    	case 2:
    		calibrate(2, 1, false);
    	break;
    	case 3:
    		calibrate(3, 1, false);
    	break;
    	case 4:
    		calibrate(4, 1, false);
    	break;
    	case 5:
    		calibrate(5, 1, false);
    	break;
    	case 6:
    		calibrate(6, 1, true);
    	break;
    	case 7:
    		calibrate(7, 1, true);
    	break;
    	case 8:
    		calibrate(8, 1, false);
    	break;
    	case 9:
    		calibrate(9, -1, false);
    		System.out.println(calibMotor.getEncPosition());
    	break;
    	case 10:
    		calibrate(10, -1, true);
    		System.out.println(calibMotor.getEncPosition());
    	break;
    	case 11:
    		calibrate(11, 1, false);
    	break;
    	case 12:
    		calibrate(12, 1, false);
    	break;
    	case 13:
    		calibrate(13, -1, false);
    	//	System.out.println(motor3.getEncPosition());
    	break;
    	case 14:
    		calibrate(14, 1, false);
    	break;
    	case 15:
    		calibrate(15, 1, false);
    	break;
    	}
		}
	public void calibrate(int id, int inversion, boolean limitSwitch){
		calibrator.clearButtons();
		double calibrationLeftY;
		int ID;
		ID = id;
		calibMotor = new CANTalon(ID);	
		System.out.println("Encoder: " + calibMotor.getEncPosition());
	//	SmartDashboard.putNumber("RangeMin: ", rangeMin);
	//	SmartDashboard.putNumber("RangeMax: ", rangeMax);
//		SmartDashboard.putNumber("Encoder Position: ", calibMotor.getEncPosition());
	//	SmartDashboard.putNumber("Range: ", rangeMax - rangeMin);
		if(limitSwitch){
		if(calibMotor.isRevLimitSwitchClosed()){
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
		}
		else{
			
		
		if(calibrator.isAPushed()){
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
			calibrator.clearA();
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
		calibMotor.set(calibrationLeftY/4);
		
		
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
				System.out.println("Motor min: ");
				System.out.println(prop.getProperty("motorMin" + ID));
				System.out.println("Motor max: ");
				System.out.println(prop.getProperty("motorMax" + ID));

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
	public void armToHeight(double targetHeight){
		if(armStage1.getHeight() + armStage2.getHeight()<targetHeight-.1){
			armStage1.goToHeight(armStage1.motor1.travel);
			armStage2.goToHeight(armStage2.motor1.travel);
		}else if(armStage1.getHeight() + armStage2.getHeight()>targetHeight+.1){
			armStage1.goToHeight(0);
			armStage2.goToHeight(0);
		}else{
			goingPortcullis = false;
			goingSallyPort = false;
			goingDrawbridge = false;
		
		}
	}
	public void sniperArmToHeight(double targetHeight){
		if(armStage1.getHeight() + armStage2.getHeight()<targetHeight-.1){
			armStage1.sniperToHeight(armStage1.motor1.travel);
			armStage2.sniperToHeight(armStage2.motor1.travel);
		}else if(armStage1.getHeight() + armStage2.getHeight()>targetHeight+.1){
			armStage1.sniperToHeight(0);
			armStage2.sniperToHeight(0);
		}else{
			goingPortcullis = false;
			goingSallyPort = false;
			goingDrawbridge = false;
		
		}
	}
}   
    	//LiveWindow.run(); This should be uncommented when LiveWindow is desired in test mode
    	
    
    


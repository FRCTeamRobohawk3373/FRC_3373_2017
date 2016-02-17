
package org.usfirst.frc.team3373.robot;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//AUTHOR Alex Iasso and Dillon Rose
import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CANTalon;

//@author Joey Dyer, Drew Marino, Alex Iasso, Dillon Rose

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	static double inches;
	RobotDrive myRobot;
	int autoLoopCounter;
	DigitalInput limitSwitch;
	AnalogInput pot;
	//static limitSwitch limitSwitch1;
	static HawkSuperMotor motor1;
	static HawkSuperMotor motor2;
//	static CANTalon motor2;
	static HawkSuperMotor motor3;
	
	static HawkSuperDualMotor dual1;
	
	static SuperJoystick driver;
	static SuperJoystick shooter;
	static SuperJoystick calibrator;
	
	static boolean manualArm = false;
	
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
	
	int motorMin3;
	int motorMax3;
	int motorMaxPercent3;




	
//	int counter;
	
	
    DigitalInput ones;
    DigitalInput twos;
    DigitalInput fours;
    DigitalInput eights;
	
	boolean counterBool;
	int counter;
//	int i = 0;
	int LX = 0;
    static int LY = 1;
    int Ltrigger = 2;
    int Rtrigger = 3;
    int RX = 4;
    static int RY = 5;
    int index;
    
    boolean CAN = true;
    
    boolean D_Pad1 = false;
    boolean D_Pad2 = false;
    boolean D_Pad3 = false;
    
    boolean Shooting = false;
    
    static boolean goingDistance = false;
    
    
    
    
    
    
//	Timer robotTimer;
	//AxisCamera camera;

 // 	HawkVision visionSystem = new HawkVision();

	
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
   	
   /*	 motorMin3 = Integer.parseInt(prop.getProperty("motorMin3"));
   	 motorMax3 = Integer.parseInt(prop.getProperty("motorMax3"));
   	 motorMaxPercent3 = 100;*/
    	
		//System.out.println("Soup.");
		
	

		// load a properties file

    	
    	
   // 	stick = new SuperJoystick(0);
    	limitSwitch = new DigitalInput(0);
    	pot = new AnalogInput(0);
    	motor1 = new HawkSuperMotor(motorID1, motorMin1, motorMax1, motorMaxPercent1, motorMinPercent1, motorTravelRange1, maxSpeedChange1);                     // Motor ID, Min encoder value from config, max encoder value from config, speed limiter (%)
    	motor2 = new HawkSuperMotor(motorID2, motorMin2, motorMax2, motorMaxPercent2, motorMinPercent2, motorTravelRange2, maxSpeedChange2);
    	//motor2 = new CANTalon(2);
    	dual1 = new HawkSuperDualMotor(motorID1, motorMin1, motorMax1, motorMaxPercent1, motorMinPercent1, motorTravelRange1, maxSpeedChange1, motorID2, motorMin2, motorMax2, motorMaxPercent2, motorMinPercent2, motorTravelRange2, maxSpeedChange2);
    //	motor3 = new HawkSuperMotor(3, Integer.parseInt((prop.getProperty("motorMin3"))), Integer.parseInt((prop.getProperty("motorMax3"))), 100);
    	driver = new SuperJoystick(0);
    	shooter = new SuperJoystick(1);
    	calibrator = new SuperJoystick(2);
    	myRobot = new RobotDrive(4,0);
    	
    	ones = new DigitalInput(6);
        twos = new DigitalInput(7);
        fours = new DigitalInput(8);
        eights = new DigitalInput(9);
   // 	counter = 0;
    //	robotTimer = new Timer();
        
        
        while(initializingMotors){
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
        	if(/*all limit switches closed*/motor1.isRevLimitSwitchClosed() && motor2.isRevLimitSwitchClosed()){
        		initializingMotors = false;
        	}
        	try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
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
    		/**TEST CODE HERE!**/
    		
    		dual1.goToHeight(3);
    	/*	motor1.goToHeight(1);
    		SmartDashboard.putNumber("Motor1 target: ", motor1.targetEncoderPos);
    		SmartDashboard.putNumber("Motor1 current: ", motor1.getEncPosition());
    		System.out.println("Target:                   " + motor1.targetEncoderPos);
    		System.out.println(motor1.getEncPosition());*/
 /*   		motor2.goToHeight(9);
    		SmartDashboard.putNumber("Motor2 target: ", motor2.targetEncoderPos);
    		SmartDashboard.putNumber("Motor2 current: ", motor2.getEncPosition());
    		System.out.println("Target:                                                  " + motor2.targetEncoderPos);
    		System.out.println("Current:                                                " + motor2.getEncPosition());
    		System.out.println("Speed: " + motor2.currentSpeed);*/
    	//	motor2.set(1);
    		System.out.print("Motor 1 Target:                      " + dual1.motor1.targetEncoderPos);
    		System.out.println("Motor2 Target:   " + dual1.motor2.targetEncoderPos);
    		System.out.print("Motor 1 Position:                      " + dual1.motor1.getEncPosition());
    		System.out.println("Motor2 Position:   " + dual1.motor2.getEncPosition());
    		
    	break;
    	case 1:
    		HawkCalibration.calibrate(1);
    	break;
    	case 2:
    		HawkCalibration.calibrate(2);
    	break;
    	case 3:
    		HawkCalibration.calibrate(3);
    	break;
    	case 4:
    		HawkCalibration.calibrate(4);
    	break;
    	case 5:
    		HawkCalibration.calibrate(5);
    	break;
    	case 6:
    		HawkCalibration.calibrate(6);
    	break;
    	case 7:
    		HawkCalibration.calibrate(7);
    	break;
    	case 8:
    		HawkCalibration.calibrate(8);
    	break;
    	case 9:
    		HawkCalibration.calibrate(9);
    	break;
    	case 10:
    		HawkCalibration.calibrate(10);
    	break;
    	case 11:
    		HawkCalibration.calibrate(11);
    	break;
    	case 12:
    		HawkCalibration.calibrate(12);
    	break;
    	case 13:
    		HawkCalibration.calibrate(13);
    	break;
    	case 14:
    		HawkCalibration.calibrate(14);
    	break;
    	case 15:
    		HawkCalibration.calibrate(15);
    	break;
    	}
		}
}
    
    	//LiveWindow.run(); This should be uncommented when LiveWindow is desired in test mode
    	
    
    


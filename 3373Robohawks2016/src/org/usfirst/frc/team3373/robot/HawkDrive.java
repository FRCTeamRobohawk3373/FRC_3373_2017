package org.usfirst.frc.team3373.robot;


public class HawkDrive {

	static boolean motorDone1 = false; //unused currently for goToDistance
	static boolean motorDone2 = false;
	HawkSuperMotor leftDriveMotorFront = new HawkSuperMotor(1,0,0,0,0,0,.1, 1);
	HawkSuperMotor leftDriveMotorBack = new HawkSuperMotor(2,0,0,0,0,0,.1, 1);
	HawkSuperMotor rightDriveMotorFront = new HawkSuperMotor(3,0,0,0,0,0,.1, 1);
	HawkSuperMotor rightDriveMotorBack= new HawkSuperMotor(4,0,0,0,0,0,.1, 1);
	
    public void wheelControl(double leftY, double rightY, boolean turboEnabled, boolean SniperEnabled){         // Acceleration and speed calculation
    	
    	if(leftY >-0.1 && leftY<0.1){
    		leftY = 0;
    	}
    	if(rightY >-0.1 && rightY<0.1){
    		rightY = 0;
    	}
    	
       if(SniperEnabled){                     
    	   leftDriveMotorFront.set(leftY/4);
    	   leftDriveMotorBack.set(leftY/4);                        // Sets motor speed to the calculated value
    	   rightDriveMotorFront.set(rightY/4);
    	   rightDriveMotorBack.set(rightY/4);
       }else if(turboEnabled){
    	   leftDriveMotorFront.set(leftY);
    	   leftDriveMotorBack.set(leftY);
    	   rightDriveMotorFront.set(rightY);
    	   rightDriveMotorBack.set(rightY);
       }else{
    	   leftDriveMotorFront.set(leftY/2);
    	   leftDriveMotorBack.set(leftY/2);
    	   rightDriveMotorFront.set(rightY/2);
    	   rightDriveMotorBack.set(rightY/2);
       }
		
	//	myRobot.tankDrive(leftY, rightY);
	

       }
    
    
 /*   public static void goDoubleDistance(double distance){
	    	   //@param distance = distance to drive... because distance isn't clear enough, apparently
    	if(Robot.motor1.getEncPosition()<Robot.motor1.targetEncoderPos+500                &&           Robot.motor1.getEncPosition()>Robot.motor1.targetEncoderPos-500){
    		motorDone1 = true;
    		Robot.motor1.set(0);
    	}else{
        	Robot.motor1.goDistance(distance);
    	}
    	if(Robot.motor2.getEncPosition()<Robot.motor2.targetEncoderPos+500                &&           Robot.motor2.getEncPosition()>Robot.motor2.targetEncoderPos-500){
    		motorDone2 = true;
    		Robot.motor2.set(0);
    	}else{
    		Robot.motor2.goDistance(distance);
    	}
    	if(motorDone1 && motorDone2){
    		Robot.goingDistance = false;
    	}
    }  */
}

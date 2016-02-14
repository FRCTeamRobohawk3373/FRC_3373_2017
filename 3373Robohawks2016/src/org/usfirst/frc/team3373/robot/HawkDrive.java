package org.usfirst.frc.team3373.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class HawkDrive {

	static boolean motorDone1 = false;
	static boolean motorDone2 = false;
	
	public static void main(String[] args) {
		wheelControl(Robot.driver.getRawAxis(Robot.LY), Robot.driver.getRawAxis(Robot.RY));

	}	
    public static void wheelControl(double leftY, double rightY){         // Acceleration and speed calculation
    	
    	if(leftY >-0.1 && leftY<0.1){
    		leftY = 0;
    	}
    	if(rightY >-0.1 && rightY<0.1){
    		rightY = 0;
    	}
    	
       if(Robot.driver.isLBHeld()){                     
		Robot.motor1.set(leftY/4);                         // Sets motor speed to the calculated value
		Robot.motor2.set(rightY/4); 
       }else if(Robot.driver.isRBHeld()){
    	   Robot.motor1.set(leftY);
    	   Robot.motor2.set(rightY);
       }else{
    	   Robot.motor1.set(leftY/2);
    	   Robot.motor2.set(rightY/2);
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

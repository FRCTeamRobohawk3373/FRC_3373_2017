package org.usfirst.frc.team3373.robot;

public class HawkDrive {

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
}

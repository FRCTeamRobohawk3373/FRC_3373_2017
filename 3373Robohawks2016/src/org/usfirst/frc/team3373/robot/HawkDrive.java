package org.usfirst.frc.team3373.robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.*;
public class HawkDrive {
	AHRS ahrs = new AHRS(SPI.Port.kMXP);
	static boolean motorDone1 = false; //unused currently for goToDistance
	static boolean motorDone2 = false;
	boolean obstacleStraight = false;
	HawkSuperMotor leftDriveMotorFront = new HawkSuperMotor(1,0,0,0,0,0,.04, -1,-1,-1);
	HawkSuperMotor leftDriveMotorBack = new HawkSuperMotor(2,0,0,0,0,0,.04, -1,-1,-1);
	HawkSuperMotor rightDriveMotorFront = new HawkSuperMotor(3,0,0,0,0,0,.04, 1,-1,-1);
	HawkSuperMotor rightDriveMotorBack= new HawkSuperMotor(4,0,0,0,0,0,.04, 1,-1,-1);
	
    public void wheelControl(double leftY, double rightY, boolean turboEnabled, boolean SniperEnabled){         // Acceleration and speed calculation
    	
    	if(leftY >-0.1 && leftY<0.1){
    		leftY = 0;
    	}
    	if(rightY >-0.1 && rightY<0.1){
    		rightY = 0;
    	}
    	
    	System.out.println("rightY: " + rightY);
    	System.out.println("leftY: " + leftY);
    	
    	
    		System.out.println("Regular drive control.");
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
    	    	   leftDriveMotorFront.set(leftY*.75);
    	    	  leftDriveMotorBack.set(leftY*.75);
    	    	   rightDriveMotorFront.set(rightY*.75);
    	    	  rightDriveMotorBack.set(rightY*.75);
    	       }
       }
    public void moveStraight(double speed, double standardAngle){
	   	 double angle = ahrs.getAngle() % 360;
	   	 SmartDashboard.putNumber("Given Angle", ahrs.getAngle());
	   	 System.out.println(ahrs.getAngle());
	   	 SmartDashboard.putNumber("Angle", angle);
	   	if(speed >= 0){
	   	 if(angle < standardAngle - 2 && angle > -180){
	   		 System.out.println("Stopping left");
	   		 wheelControl(-(speed),-(speed-.2),false,false); 
	   	 }
	   	 else if(angle < standardAngle +2 && angle < -180){
	   		 System.out.println("Stopping right");
	   		 wheelControl(-(speed-.2),-(speed),false,false);
	   	 } else {
	   		 wheelControl(-speed, -speed, false, false);
	   		 System.out.println("going straight");
	   	 }
	   		}
	   	else if(speed < 0){
	   		if(angle < standardAngle - 2 && angle > -180){
		   		 System.out.println("Stopping left");
		   		 wheelControl(-(speed+.02) ,-(speed),false,false); 
		   	 }
		   	 else if(angle < standardAngle +2 && angle < -180){
		   		 System.out.println("Stopping right");
		   		 wheelControl(-(speed) ,-(speed+.02),false,false);
		   	 } else {
		   		 wheelControl(-speed, -speed, false, false);
		   		 System.out.println("going straight");
	   	}
	   	 ahrs.free();
	       }
    	}
    	public void turnToXDegrees(double targetAngle){
    		//45 degrees is 45 to the left, 315 degrees is 45 to the right
    		double currentAngle = Math.abs(ahrs.getAngle() % 360);
    		if(currentAngle < targetAngle-2 && targetAngle<180){
    			wheelControl(0, -.5, false, false);
    		}else if(currentAngle < targetAngle-2 && targetAngle >180 && currentAngle <90 || currentAngle > targetAngle+2 && targetAngle >180){
    			wheelControl(-.5,0, false, false);
    		}else{
    			wheelControl(0,0,false, false);
    		}
    /*	if(targetAngle<180){
    		if(currentAngle < targetAngle-3){
    		wheelControl(0, -.5, false, false);
    	}
    		else if(currentAngle > targetAngle+3){
    		wheelControl(-.5, 0, false, false);
    	} else{
    		wheelControl(0,0,false,false);
    	}
    }else{
    	double newTargetAngle = Math.abs(targetAngle -360);
    	double newCurrentAngle = Math.abs(currentAngle -360);
    	if(newCurrentAngle < newTargetAngle-3){
    		wheelControl(-.5, 0, false, false);
    	}else if(newCurrentAngle >newTargetAngle+3){
    		wheelControl(0, -.5, false, false);
    	}else{
    		wheelControl(0,0,false,false);
    	}
    }
   */
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

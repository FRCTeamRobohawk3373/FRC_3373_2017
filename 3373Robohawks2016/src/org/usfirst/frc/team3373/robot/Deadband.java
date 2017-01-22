package org.usfirst.frc.team3373.robot;

public class Deadband {
   
    /**
     * Removes any values within a range to filter out noise
     * @param input input double (generally a joystick)
     * @param range absolute value of a range where a value is invalid within
     * @return returns conditioned input (0 if inside of range, input if without) 
     */
	
	public double zero(double input, double range){
        
        if(input > -range && input < range){
            return 0.00;
        } else {
            return input;
        }
        
    }
}

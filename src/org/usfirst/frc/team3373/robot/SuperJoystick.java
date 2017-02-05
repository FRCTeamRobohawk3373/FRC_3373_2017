 package org.usfirst.frc.team3373.robot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.wpi.first.wpilibj.Joystick;
/**
 *
 * @author Jamie Dyer
 */
public class SuperJoystick extends Joystick{
         
   private boolean flagA;
   boolean flagB;
   boolean flagX;
   boolean flagY;
   boolean flagLB;
   boolean flagRB;
   boolean flagStart;
   boolean flagBack;
   boolean flagLStick;
   boolean flagRStick;
    SuperJoystick(int port){
        super(port); //also need to clear joystick class
        clearButtons();
    }
    /********************************
     * Checks if a button is pushed 
     * @param button the raw button number
     * @return whether a button is pushed or not (true = pushed)
     * @see Joystick
     ********************************/
    public boolean isButtonPushed(int button){
        if (getRawButton(button)){
            return true;
        } else { 
            return false;
        }
    }
    public boolean isAPushed(){
        if(isButtonPushed(1) && flagA){
            flagA = false;
            return true;
        }
        else{
            return false;
        }
    }    
    public boolean isBPushed(){
        if(isButtonPushed(2) && flagB){
            flagB = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isXPushed(){
        if(isButtonPushed(3) && flagX){
            flagX = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isYPushed(){
        if(isButtonPushed(4) && flagY){
            flagY = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isLBPushed(){
        if(isButtonPushed(5) && flagLB){
            flagLB = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isRBPushed(){
        if(isButtonPushed(6) && flagRB){
            flagRB = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isBackPushed(){
        if(isButtonPushed(7) && flagBack){
            flagBack = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isStartPushed(){
        if(isButtonPushed(8) && flagStart){
            flagStart = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isLStickPushed(){
        if(isButtonPushed(9) && flagLStick){
            flagLStick = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isRStickPushed(){
        if(isButtonPushed(10) && flagRStick){
            flagRStick = false;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isAHeld(){
        if (getRawButton(1)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isBHeld(){
        if (getRawButton(2)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isXHeld(){
        if (getRawButton(3)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isYHeld(){
        if (getRawButton(4)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isLBHeld(){
        if (getRawButton(5)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isRBHeld(){
        if (getRawButton(6)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isBackHeld(){
        if (getRawButton(7)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isStartHeld(){
        if (getRawButton(8)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isLStickHeld(){
        if (getRawButton(9)){
            return true;
        } else {
            return false;
        }
    }
    public boolean isRStickHeld(){
        if (getRawButton(10)){
            return true;
        } else {
            return false;
        }
    }
    
    public void clearButtons(){
        if (!flagA && !getRawButton(1)) { //toggles
            flagA = true;
        } if (!flagB && !getRawButton(2)){
            flagB = true;
        } if (!flagX && !getRawButton(3)){
            flagX = true;
        } if (!flagY && !getRawButton(4)){
            flagY = true;
        } if (!flagLB && !getRawButton(5)){
            flagLB = true;
        } if (!flagRB && !getRawButton(6)){
            flagRB = true;
        } if (!flagBack && !getRawButton(7)){
            flagBack = true;
        } if (!flagStart && !getRawButton(8)){
            flagStart = true;
        } if (!flagLStick && !getRawButton(9)){
            flagLStick = true;
        } if (!flagRStick && !getRawButton(10)){
            flagRStick = true;
        }
    }
    public void clearB(){
     if (!flagB && !getRawButton(2)){
        flagB = true;
    }
    }
     public void clearA(){
         if (!flagA && !getRawButton(1)){
            flagA = true;
        }
}
     public void clearX(){
    	 if (!flagX && !getRawButton(3)){
             flagX = true;
     }
     }
     public void clearY(){
    	 if (!flagY && !getRawButton(4)){
             flagY = true;
         }
     }
     public void clearLB(){
    	 if (!flagLB && !getRawButton(5)){
             flagLB = true;
         }
     }
     public void clearRB(){
    	 if (!flagRB && !getRawButton(6)){
             flagRB = true;
     }
     }
     public void clearBack(){
    	 if (!flagBack && !getRawButton(7)){
             flagBack = true;
         }
     }
     public void clearStart(){
    	 if (!flagStart && !getRawButton(8)){
             flagStart = true;
     }
     }
     public void clearLStick(){
    	 if (!flagLStick && !getRawButton(9)){
             flagLStick = true;
    	 }
     }
     public void clearRStick(){
    	 if (!flagRStick && !getRawButton(10)){
             flagRStick = true;
    	 }
    
     }
    
}
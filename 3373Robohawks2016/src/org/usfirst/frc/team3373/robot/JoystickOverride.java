package org.usfirst.frc.team3373.robot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.Joystick;

//@author Kofi Asenso and Matthew Heller
public class JoystickOverride extends Joystick {

	private boolean flagA = true;
	private boolean flagB = true;
	private boolean flagX = true;
	private boolean flagY = true;
	private boolean flagLB = true;
	private boolean flagRB = true;
	private boolean flagStart = true;
	private boolean flagBack = true;
	private boolean flagLStick = true;
	private boolean flagRStick = true;
	private boolean flagDPadUp = true;
	private boolean flagDPadDown = true;
	private boolean flagDPadLeft = true;
	private boolean flagDPadRight = true;
	private boolean flagDPadUpRight = true;
	private boolean flagDPadUpLeft = true;
	private boolean flagDPadDownRight = true;
	private boolean flagDPadDownLeft = true;
	private boolean flagDPadNotPushed = true;


	private String[] controls = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0.0", "0.0", "0.0", "0.0", "0.0",
			"0.0", "-1" };

	JoystickOverride(int port) {
		super(port); // also need to clear joystick class
		clearButtons();
	}

	/********************************
	 * Checks if a button is pushed
	 * 
	 * @param button
	 *            the raw button number
	 * @return whether a button is pushed or not (true = pushed)
	 * @see Joystick
	 ********************************/

	public void SetButtons(String[] value) {
		controls = value;
	}

	public boolean isButtonPushed(int button) {
		if (getRawButton(button)) {
			return true;
		} else {
			if (controls[button - 1].equalsIgnoreCase("1")) {
				return true;
			} else {
				return false;
			}
		}

	}

	public double getRawAxis(int axis) {
		if (controls[axis + 10].equalsIgnoreCase("0.0")) {
			return super.getRawAxis(axis);
		} else {
			return Double.parseDouble(controls[axis + 10]);
		}
	}

	public int getPOV() {
		if (super.getPOV() > -1) {
			return super.getPOV();
		} else {
			return Integer.parseInt(controls[16]);
		}
	}

	public boolean isAPushed() {
		if (isButtonPushed(1) && flagA) {
			flagA = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isBPushed() {
		if (isButtonPushed(2) && flagB) {
			flagB = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isXPushed() {
		if (isButtonPushed(3) && flagX) {
			flagX = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isYPushed() {
		if (isButtonPushed(4) && flagY) {
			flagY = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isLBPushed() {
		if (isButtonPushed(5) && flagLB) {
			flagLB = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isRBPushed() {
		if (isButtonPushed(6) && flagRB) {
			flagRB = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isBackPushed() {
		if (isButtonPushed(7) && flagBack) {
			flagBack = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isStartPushed() {
		if (isButtonPushed(8) && flagStart) {
			flagStart = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isLStickPushed() {
		if (isButtonPushed(9) && flagLStick) {
			flagLStick = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isRStickPushed() {
		if (isButtonPushed(10) && flagRStick) {
			flagRStick = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadUpPushed() {
		if (getPOV() == 0) {
			flagDPadUp = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadDownPushed() {
		if (getPOV() == 180) {
			flagDPadDown = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadLeftPushed() {
		if (getPOV() == 270) {
			flagDPadLeft = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadRightPushed() {
		if (getPOV() == 90) {
			flagDPadUp = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadUpRightPushed() {
		if (getPOV() == 45) {
			flagDPadUpRight = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadUpLeftPushed() {
		if (getPOV() == 315) {
			flagDPadUpLeft = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadDownRightPushed() {
		if (getPOV() == 135) {
			flagDPadDownRight = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadDownLeftPushed() {
		if (getPOV() == 225) {
			flagDPadDownLeft = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadNotPushed() {
		if (getPOV() == -1) {
			flagDPadNotPushed = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean isAHeld() {
		if (isButtonPushed(1)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBHeld() {
		if (isButtonPushed(2)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isXHeld() {
		if (isButtonPushed(3)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isYHeld() {
		if (isButtonPushed(4)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLBHeld() {
		if (isButtonPushed(5)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRBHeld() {
		if (isButtonPushed(6)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBackHeld() {
		if (isButtonPushed(7)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isStartHeld() {
		if (isButtonPushed(8)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLStickHeld() {
		if (isButtonPushed(9)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRStickHeld() {
		if (isButtonPushed(10)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadUpHeld() {
		if (getPOV() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadDownHeld() {
		if (getPOV() == 180) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadLeftHeld() {
		if (getPOV() == 270) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadRightHeld() {
		if (getPOV() == 90) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadUpRightHeld() {
		if (getPOV() == 45) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadUpLeftHeld() {
		if (getPOV() == 315) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadDownRightHeld() {
		if (getPOV() == 135) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadDownLeftHeld() {
		if (getPOV() == 225) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDPadNotHeld() {
		if (getPOV() == -1) {
			return true;
		} else {
			return false;
		}
	}

	public void clearButtons() {
		if (!flagA && !isButtonPushed(1)) { // toggles
			flagA = true;
		}
		if (!flagB && !isButtonPushed(2)) {
			flagB = true;
		}
		if (!flagX && !isButtonPushed(3)) {
			flagX = true;
		}
		if (!flagY && !isButtonPushed(4)) {
			flagY = true;
		}
		if (!flagLB && !isButtonPushed(5)) {
			flagLB = true;
		}
		if (!flagRB && !isButtonPushed(6)) {
			flagRB = true;
		}
		if (!flagBack && !isButtonPushed(7)) {
			flagBack = true;
		}
		if (!flagStart && !isButtonPushed(8)) {
			flagStart = true;
		}
		if (!flagLStick && !isButtonPushed(9)) {
			flagLStick = true;
		}
		if (!flagRStick && !isButtonPushed(10)) {
			flagRStick = true;
		}
	}

	public void clearB() {
		if (!flagB && !isButtonPushed(2)) {
			flagB = true;
		}
	}

	public void clearA() {
		if (!flagA && !isButtonPushed(1)) {
			flagA = true;
		}
	}

	public void clearX() {
		if (!flagX && !isButtonPushed(3)) {
			flagX = true;
		}
	}

	public void clearY() {
		if (!flagY && !isButtonPushed(4)) {
			flagY = true;
		}
	}

	public void clearLB() {
		if (!flagLB && !isButtonPushed(5)) {
			flagLB = true;
		}
	}

	public void clearRB() {
		if (!flagRB && !isButtonPushed(6)) {
			flagRB = true;
		}
	}

	public void clearBack() {
		if (!flagBack && !isButtonPushed(7)) {
			flagBack = true;
		}
	}

	public void clearStart() {
		if (!flagStart && !isButtonPushed(8)) {
			flagStart = true;
		}
	}

	public void clearLStick() {
		if (!flagLStick && !isButtonPushed(9)) {
			flagLStick = true;
		}
	}

	public void clearRStick() {
		if (!flagRStick && !isButtonPushed(10)) {
			flagRStick = true;
		}

	}

	public void clearDPad() {
		if (!flagDPadUp && getPOV() != 0) {
			flagDPadUp = true;
		}
		if (!flagDPadDown && getPOV() != 180) {
			flagDPadDown = true;
		}
		if (!flagDPadLeft && getPOV() != 270) {
			flagDPadLeft = true;
		}
		if (!flagDPadRight && getPOV() != 0) {
			flagDPadRight = true;
		}
		if (!flagDPadNotPushed && getPOV() != -1) {
			flagDPadNotPushed = true;
		}
		if (!flagDPadUpRight && getPOV() != 45) {
			flagDPadUpRight = true;
		}
		if (!flagDPadUpLeft && getPOV() != 315) {
			flagDPadUpLeft = true;
		}
		if (!flagDPadDownRight && getPOV() != 135) {
			flagDPadDownRight = true;
		}
		if (!flagDPadDownLeft && getPOV() != 225) {
			flagDPadDownLeft = true;
		}
	}

	public void clearDPadUp() {
		if (!flagDPadUp && getPOV() != 0) {
			flagDPadUp = true;
		}
	}

	public void clearDPadDown() {
		if (!flagDPadDown && getPOV() != 180) {
			flagDPadDown = true;
		}
	}

	public void clearDPadLeft() {
		if (!flagDPadLeft && getPOV() != 270) {
			flagDPadLeft = true;
		}
	}

	public void clearDPadRight() {
		if (!flagDPadRight && getPOV() != 0) {
			flagDPadRight = true;
		}
	}

	public void clearDPadNotPushed() {
		if (!flagDPadNotPushed && getPOV() != -1) {
			flagDPadNotPushed = true;
		}
	}

	public void clearDPadUpRight() {
		if (!flagDPadUpRight && getPOV() != 45) {
			flagDPadUpRight = true;
		}
	}

	public void clearDPadUpLeft() {
		if (!flagDPadUpLeft && getPOV() != 315) {
			flagDPadUpLeft = true;
		}
	}

	public void clearDPadDownRight() {
		if (!flagDPadDownRight && getPOV() != 135) {
			flagDPadDownRight = true;
		}
	}

	public void clearDPadDownLeft() {
		if (!flagDPadDownLeft && getPOV() != 225) {
			flagDPadDownLeft = true;
		}
	}

}
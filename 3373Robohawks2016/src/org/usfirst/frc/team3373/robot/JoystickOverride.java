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

	// private boolean usercontrol = true;

	private String[] controls = { "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL",
			"NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL" };

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
		if (controls[button - 1].equalsIgnoreCase("NULL")) {
			if (getRawButton(button)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (controls[button - 1].equalsIgnoreCase("true")) {
				controls[button - 1] = "NULL";
				return true;
			} else {
				controls[button - 1] = "NULL";
				return false;
			}
		}
	}

	public double getRawAxis(int axis) {
		if (controls[axis + 10].equalsIgnoreCase("NULL")) {
			return super.getRawAxis(axis);
		} else {
			double value = Double.parseDouble(controls[axis + 10]);
			controls[axis + 10] = "NULL";
			return value;

		}
	}

	public int getPOV() {
		if (controls[16].equalsIgnoreCase("NULL")) {
			return super.getPOV();
		} else {
			int poval = Integer.parseInt(controls[16]);
			controls[16] = "NULL";
			return poval;

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

}
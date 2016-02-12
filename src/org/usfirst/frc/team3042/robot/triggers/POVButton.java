package org.usfirst.frc.team3042.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class POVButton extends Trigger {
    
	int direction;
	Joystick joy;
	
	public POVButton (Joystick joy, int direction) {
		this.joy = joy;
		this.direction = direction;
	}
	
    public boolean get() {
        return joy.getPOV() == direction;
    }
}

package org.usfirst.frc.team3042.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class GamePadAxis extends Trigger {
	Joystick gamepad;
	int axis;
	public static enum DIRECTION{UP, DOWN;}
	DIRECTION direction;
	public GamePadAxis(Joystick joystick, int axis, DIRECTION direction){
		gamepad = joystick;
		this.axis = axis;
		this.direction = direction;
	}
	
    public boolean get() {
    	boolean triggered;
    	if(direction == DIRECTION.UP){
    		triggered = gamepad.getRawAxis(axis) < -.5;
    	}
    	else{
    		triggered = gamepad.getRawAxis(axis) > .5;
    	}
    	return triggered;
    }
}

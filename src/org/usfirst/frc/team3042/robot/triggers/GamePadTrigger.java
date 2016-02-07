package org.usfirst.frc.team3042.robot.triggers;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class GamePadTrigger extends Trigger {
	Joystick gamepad;
	int axis;
	public GamePadTrigger(Joystick joystick, int axis){
		gamepad = joystick;
		this.axis = axis;
	}
	
    public boolean get() {
        return gamepad.getRawAxis(axis) >= .5;
    }
}

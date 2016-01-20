package org.usfirst.frc.team3042.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TestServo extends Subsystem {
    
	Servo servo = new Servo(1);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setPositionZero() {
    	servo.setAngle(0);
    	
    }
    
    public void setPositionOne() {
    	servo.setAngle(90);
    }
}


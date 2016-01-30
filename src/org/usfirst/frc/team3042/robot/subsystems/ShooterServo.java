package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterServo extends Subsystem {
	
	Servo servo = new Servo(RobotMap.SHOOTER_SERVO);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //Setting the loading servo to various positions
    public void setServoRetracted() {
    	servo.setAngle(90);
    }
    
    public void setServoExtended() {
    	servo.setAngle(0);
    }
    
}

package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.ShooterServo_Retract;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterServo extends Subsystem {
	
	Servo servo = new Servo(RobotMap.SHOOTER_SERVO);
	
	double retractAngle = (RobotMap.isSkoll)? 90: 180;
	double extendAngle = (RobotMap.isSkoll)? 30: 120;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ShooterServo_Retract());
    }
    
    //Setting the loading servo to various positions
    public void setServoRetracted() {
    	servo.setAngle(retractAngle);
    }
    
    public void setServoExtended() {
    	servo.setAngle(extendAngle);
    }
    
}


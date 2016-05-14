package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.DefenseExtendServo_Retract;
import org.usfirst.frc.team3042.robot.commands.HookLiftServo_Retract;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DefenseExtendServo extends Subsystem {
	
	Servo servo = new Servo(RobotMap.DEFENSE_SERVO);
	
	double retractAngle = (RobotMap.isSkoll)? 120: 65;
	double extendAngle = (RobotMap.isSkoll)? 30: 0;
	
	boolean deployed = false;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DefenseExtendServo_Retract());
    }
    
    //Setting the loading servo to various positions
    public void setServoRetracted() {
    	servo.setAngle(retractAngle);
    }
    
    public void setServoExtended() {
    	servo.setAngle(extendAngle);
    	deployed = true;
    }
    
    public boolean isDeployed () {
    	return deployed;
    }
    
    public void resetDeployed() {
    	deployed = false;
    }
}


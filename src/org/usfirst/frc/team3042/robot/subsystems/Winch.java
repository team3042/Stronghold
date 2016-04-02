package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Winch_Stop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {
    
	CANTalon winchTalon = new CANTalon(RobotMap.WINCH_TALON);
	
	double winchSpeed = 1;
	double releaseSpeed = 0.3;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.    	
    	setDefaultCommand(new Winch_Stop());
    }
    
    public void stop() {
    	setSpeed(0.0);
    }
    
    public void raise() {
    	double speed = 0;
    	if (Robot.hookLift.isDeployed()) {
    		speed = -winchSpeed;
    	}
    	setSpeed(speed);
    }
    
    public void release() {
    	setSpeed(releaseSpeed);
    }
    
    private void setSpeed(double speed) {
    	winchTalon.set(speed);
    }
}


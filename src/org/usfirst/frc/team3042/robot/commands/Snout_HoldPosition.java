package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Snout_HoldPosition extends Command {
	
	double currentPos;
	double scale = 10;
	
	Joystick gamepad = Robot.oi.gamePadGunner;
	int axis = 1;
	double deadzone = 0.05;

    public Snout_HoldPosition() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.snout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.snout.setToCurrentPosition();
    	
    	currentPos = Robot.snout.getPotValue();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentY = gamepad.getRawAxis(axis);
    	if (Math.abs(currentY) > deadzone) {
    		currentPos = Robot.snout.safetyTest(currentY * scale + currentPos);
    		Robot.snout.setPosition(currentPos);
    	}
    	
    	Robot.logger.log("Snout PID value = " + Robot.snout.talonRotate.pidGet(), 5);
    	SmartDashboard.putNumber("Setpoint", currentPos);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupt", 1);
    }
}

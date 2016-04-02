package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class HookLift_Raise extends Command {

	boolean finished = false;
	
    public HookLift_Raise() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.hookLift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    		
    	finished = !Robot.hookLiftServo.isDeployed();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.hookLift.raise();
    	SmartDashboard.putNumber("Hook Lift Encoder", Robot.hookLift.getEncDistance());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.hookLift.encoderLimitReached()) {
    		finished = true;
    		Robot.hookLift.setDeployedTrue();
    	}
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    	Robot.hookLift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrrupt", 1);
    	Robot.hookLift.stop();
    }
}

package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Overlay_Toggle extends Command {

    public Overlay_Toggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.camera.drawOverlay(NIVision.RGB_YELLOW);
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
    	Robot.logger.log("Interrupted", 1);
    }
}

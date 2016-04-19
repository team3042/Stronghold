package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_RotateOpenCV extends Command {
	private Timer timer = new Timer();
	 
	int cyclesTolerance = 4;
	double rotationsPerRadian = 0.764; //90.432 in circumference 4.8 rotations
	double timeout = 5.0;
	
    public Auto_RotateOpenCV() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	
    	double offset = Robot.cameraCV.getRotationOffset();
    	if(Robot.snout.isBackwards()) {
			offset *= -1;
		}
    	timer.reset();
    	timer.start();
    	Robot.logger.log("Rotation Offset = "+offset, 2);
    	double leftTarget = offset * rotationsPerRadian;
		double rightTarget = -offset * rotationsPerRadian;
		
		Robot.driveTrain.offsetPosition(leftTarget, rightTarget);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.driveTrain.nearSetpoint() || (timer.get() > timeout);
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

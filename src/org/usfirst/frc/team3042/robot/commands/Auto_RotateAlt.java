package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_RotateAlt extends Command {
	private Timer timer = new Timer();
 
	double OFFSET_ZERO = -16.5;
	int cyclesTolerance = 4;
	double encPerPixel = 100;
	double timeout = 4.0;

	double lastOffset;
	int stillCycles;
	boolean finished;
	
    public Auto_RotateAlt() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	lastOffset = 0.0;
    	stillCycles = 0;
    	finished = false;
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double offset = Robot.camera.getRotationOffset() - OFFSET_ZERO;
    	
    	if (offset == lastOffset) {
    		stillCycles++;
    		finished = (stillCycles > cyclesTolerance);
    	} else {
    		stillCycles = 0;
    		
    		Robot.driveTrain.resetEncoders();
    		double leftTarget = -offset * encPerPixel;
    		double rightTarget = offset * encPerPixel;
    		
    		Robot.driveTrain.setPosition(leftTarget, rightTarget);
    	}
    	lastOffset = offset;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished || (timer.get() > timeout);
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

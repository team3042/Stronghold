package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Follow extends Command {

	private double driveSpeed = 0.4;
	private double turnSpeed = 0.2;
	private double targetDistance = 8.0;
	private double rotationTolerance = 0.0;
	private double distanceTolerance = 0.0;
	
    public Auto_Follow() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double distance = Robot.camera.getDistToTarget();
    	double rotation = Robot.camera.getRotationOffset();
    	double leftSpeed = 0.0, rightSpeed = 0.0;
    	
    	if (distance > (targetDistance + distanceTolerance)) {
    		leftSpeed += driveSpeed;
    		rightSpeed += driveSpeed;
    	}
    	else if (distance < (targetDistance - distanceTolerance)) {
    		leftSpeed -= driveSpeed;
    		rightSpeed -= driveSpeed;
    	}
    	if (rotation < -rotationTolerance) {
    		leftSpeed += turnSpeed;
    		rightSpeed -= turnSpeed;
    	}
    	else if (rotation > rotationTolerance) {
    		leftSpeed -= turnSpeed;
    		rightSpeed += turnSpeed;
    	}
    	
    	Robot.driveTrain.setMotors (leftSpeed, rightSpeed);
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

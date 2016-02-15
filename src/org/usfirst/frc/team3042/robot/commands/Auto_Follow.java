package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.subsystems.CameraAPI.ParticleReport2;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Follow extends Command {

	private double driveSpeed = 0.3;
	private double turnSpeed = 0.15;
	private double TARGET_DISTANCE = 5.0;
	private double rotationTolerance = 1.0;
	private double distanceTolerance = 2.0;
	private double ROTATION_ZERO = -16.5;
	private double P = 0.1;
	
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
    	ParticleReport2 report = Robot.camera.createTargetReport(60);
		double leftSpeed = 0.0, rightSpeed = 0.0;

    	if (report != null) {
    		double distance = Robot.camera.getDistToTarget(report);
    		double rotation = Robot.camera.getRotationOffset(report);
    	
    		rotation -= ROTATION_ZERO;
    		distance -= TARGET_DISTANCE;
    	
    		if (distance > distanceTolerance) {
    			leftSpeed += driveSpeed * Math.min(1, P * Math.abs(distance));
    			rightSpeed += driveSpeed * Math.min(1, P * Math.abs(distance));
    		}
    		else if (distance < -distanceTolerance) {
    			leftSpeed -= driveSpeed * Math.min(1, P * Math.abs(distance));
    			rightSpeed -= driveSpeed * Math.min(1, P * Math.abs(distance));
    		}
    		if (rotation > rotationTolerance) {
    			leftSpeed -= turnSpeed * Math.min(1, P * Math.abs(rotation));
    			rightSpeed += turnSpeed * Math.min(1, P * Math.abs(rotation));
    		}
    		else if (rotation < -rotationTolerance) {
    			leftSpeed += turnSpeed * Math.min(1, P * Math.abs(rotation));
    			rightSpeed -= turnSpeed * Math.min(1, P * Math.abs(rotation));
    		}
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

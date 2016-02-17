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
	private double TARGET_DISTANCE = 8.0;
	private double rotationTolerance = 1.0;
	private double distanceTolerance = 0.5;
	private double ROTATION_ZERO = -16.5;
	private double rotateP = 0.2, driveP = 0.5;
	double pixelsPerEnc = 0.01;
	double leftEncStart, rightEncStart, rotationStart;
	
    public Auto_Follow() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	rotationStart = 0.0;
    	leftEncStart = Robot.driveTrain.getLeftEncoder();
    	rightEncStart = Robot.driveTrain.getRightEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	ParticleReport2 report = Robot.camera.createTargetReport(60);
		double leftSpeed = 0.0, rightSpeed = 0.0;

    	if (report != null) {
    		double distance = Robot.camera.getDistToTarget(report) - TARGET_DISTANCE;
    		double rotation = Robot.camera.getRotationOffset(report) - ROTATION_ZERO;
    		
    		Robot.logger.log("Camera Rotation = "+rotation, 3);
    		if (rotation == rotationStart){
    			double leftEnc = Robot.driveTrain.getLeftEncoder() - leftEncStart;
    			double rightEnc = Robot.driveTrain.getRightEncoder() - rightEncStart;
    			double encDiff = 0.5*(leftEnc - rightEnc);
    			
    			rotation += encDiff * pixelsPerEnc;
    		}else{
    			leftEncStart = Robot.driveTrain.getLeftEncoder();
    			rightEncStart = Robot.driveTrain.getRightEncoder();
        		rotationStart = rotation;    		
    		}
    	
    		double distanceScale = Math.min(1, driveP * Math.abs(distance));
    		if (distance > distanceTolerance) {
    			leftSpeed += driveSpeed * distanceScale;
    			rightSpeed += driveSpeed * distanceScale;
    		}
    		else if (distance < -distanceTolerance) {
    			leftSpeed -= driveSpeed * distanceScale;
    			rightSpeed -= driveSpeed * distanceScale;
    		}
    		
    		double rotationScale = Math.min(1, rotateP * Math.abs(rotation));
    		if (rotation > rotationTolerance) {
    			leftSpeed -= turnSpeed * rotationScale;
    			rightSpeed += turnSpeed * rotationScale;
    		}
    		else if (rotation < -rotationTolerance) { 
    			leftSpeed += turnSpeed * rotationScale;
    			rightSpeed -= turnSpeed * rotationScale;
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

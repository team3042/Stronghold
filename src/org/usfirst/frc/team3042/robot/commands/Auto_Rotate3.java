package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Rotate3 extends Command {
	private double P = 0.1;
	
	private Timer timer = new Timer();
	double timeout = 4.0;
	
	//double encCountsPerPixel = 0.945; //for resolution 320x240
	double encCountsPerPixel = 0.63; //for resolution 480x360
	
	double leftTarget, rightTarget;
	boolean finished;
	double tolerance = 1;
	double minSpeed = 0.05, maxSpeed = 0.3;
	
    public Auto_Rotate3() {
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	finished = false;
    	timer.reset();
    	timer.start();
    	
    	double offset = Robot.camera.getRotationOffset();
    	offset *= encCountsPerPixel;
    	leftTarget  =  offset + Robot.driveTrain.getLeftEncoder();
    	rightTarget = -offset + Robot.driveTrain.getRightEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftSpeed, rightSpeed;
    	double leftError = leftTarget - Robot.driveTrain.getLeftEncoder();
    	double rightError = rightTarget - Robot.driveTrain.getRightEncoder();
    		
    	finished =  (Math.abs(leftError)  < tolerance) && 
    				(Math.abs(rightError) < tolerance);
    	if (finished){
    		leftSpeed = 0.0;
    		rightSpeed = 0.0;
    	}
    	else {
    		leftSpeed = P*leftError;
    		rightSpeed = P*rightError;
    	}
    	
    	leftSpeed = limitSpeed(leftSpeed);
    	rightSpeed = limitSpeed(rightSpeed);
    	
    	Robot.driveTrain.setMotors(leftSpeed, rightSpeed);
    }
    
    double limitSpeed (double speed) {
    	speed = Math.min(speed, maxSpeed);
    	speed = Math.max(speed, minSpeed);
    	return speed;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timer.get() > timeout) || finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    	
    	Robot.driveTrain.setMotors(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupt", 1);
    	
    	Robot.driveTrain.setMotors(0, 0);
    }
}

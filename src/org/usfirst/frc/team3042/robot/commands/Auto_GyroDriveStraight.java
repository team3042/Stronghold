package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_GyroDriveStraight extends Command {
	
	Timer timer = new Timer();
	
	//Time between each point in ms
	int itp = 30;
			
	//Time for each filter in ms
	double time1 = 400, time2 = 200;
	
	double distance, maxSpeed;
	
	double goalPosition = 0, goalSpeed = 0;
	
	//Proportional values for control of encoder position and heading
	double pPos = 1.0 / 360, pTurn = 0.005;
		
	AutoTrajectory_MotionProfile motionProfile;
	
	double[][] profile;
	
	boolean finished = false;

    public Auto_GyroDriveStraight(double distance, double maxSpeed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	
    	this.distance = distance;
    	this.maxSpeed = maxSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	
    	Robot.driveTrain.resetGyro();
    	Robot.driveTrain.resetEncoders();
    	
    	motionProfile = new AutoTrajectory_MotionProfile(itp, time1, time2, maxSpeed, distance);
    	
    	profile = motionProfile.calculateProfileRaw();
    	
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentLeftError = goalPosition - Robot.driveTrain.getLeftEncoder();
    	double currentRightError = goalPosition - Robot.driveTrain.getRightEncoder();
    	
    	double currentHeading = Robot.driveTrain.getGyro();
    	
    	double leftSpeed = goalSpeed + pPos * currentLeftError; // - pTurn * currentHeading;
    	double rightSpeed = goalSpeed + pPos * currentRightError; // + pTurn * currentHeading;
    	
    	Robot.logger.log("Turn Corrected Left = " + (leftSpeed - pTurn * currentHeading) +
    			"Turn Corrected Right = " + (rightSpeed + pTurn * currentHeading), 4);
    	
    	Robot.driveTrain.setMotorsRaw(leftSpeed, rightSpeed);
    	
    	double currentTime = timer.get() * 1000;
    	
    	//Interpolating the profile to find the exact current position and velocity
    	if(currentTime <= profile[profile.length][0]) {
    		for(int i = 0; i < profile.length; i++) {
    			if(profile[i][0] >= currentTime) {
    				goalPosition = profile[i-1][1] + 
    						(currentTime - profile[i-1][0]) *
    						(profile[i][1] - profile[i-1][1]) /
    						(profile[i][0] - profile[i-1][0]);
    				goalSpeed = profile[i-1][2] + 
    						(currentTime - profile[i-1][0]) *
    						(profile[i][2] - profile[i-1][2]) /
    						(profile[i][0] - profile[i-1][0]);
    				
    				goalSpeed = (1023 / goalSpeed) * Robot.driveTrain.kF / Robot.driveTrain.encCounts;
    			}
    		}
    	}
    	else {
    		finished = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    	
    	Robot.driveTrain.setMotorsRaw(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupt", 1);
    	
    	Robot.driveTrain.setMotorsRaw(0, 0);
    }
}

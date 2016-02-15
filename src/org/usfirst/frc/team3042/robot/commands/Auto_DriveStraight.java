package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.MotionProfileStatus;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_DriveStraight extends Command {
	//Current point
	int pointNumber = 0;

	//Time between each point in ms
	int itp = 30;
	
	//Time for each filter in ms
	double time1 = 400, time2 = 200;
	
	//Maximum speed in RPM
	double maxVelocity = 500;
	
	//Distance traveled in rotations
	double distance = 10;
	
	Auto_MotionProfile motionProfile;
	
    public Auto_DriveStraight() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.driveTrain.initMotionProfile();
    	motionProfile = new Auto_MotionProfile(itp, time1, time2, maxVelocity, distance);
    	CANTalon.TrajectoryPoint[] leftTrajectory = motionProfile.calculateProfile();
    	CANTalon.TrajectoryPoint[] rightTrajectory = motionProfile.calculateProfile();
    	
    	for(int i = 0; i < leftTrajectory.length; i++) {
    		Robot.driveTrain.pushPoints(leftTrajectory[i], rightTrajectory[i]);
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {    	
    	MotionProfileStatus[] status = Robot.driveTrain.getMotionProfileStatus();
    	
    	if(status[0].btmBufferCnt > 5 &&
    			status[0].outputEnable != CANTalon.SetValueMotionProfile.Enable) {
    		Robot.driveTrain.enableMotionProfile();
    	}
    	else if(status[0].btmBufferCnt <= 5 &&
    			status[0].outputEnable == CANTalon.SetValueMotionProfile.Enable) {
    		Robot.driveTrain.holdMotionProfile();
    	}
    	
    	if(status[0].hasUnderrun) {
    		Robot.logger.log("Left Underrun", 2);
    		Robot.driveTrain.removeUnderrunLeft();
    	}
    	if(status[1].hasUnderrun) {
    		Robot.logger.log("Right Underrun", 2);
    		Robot.driveTrain.removeUnderrunRight();
    	}
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

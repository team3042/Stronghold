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
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.driveTrain.initMotionProfile();
    	motionProfile = new Auto_MotionProfile(itp, time1, time2, maxVelocity, distance);
    	CANTalon.TrajectoryPoint[] trajectory = motionProfile.calculateProfile();
    	
    	for(int i = 0; i < trajectory.length; i++) {
    		Robot.driveTrain.pushPoint(trajectory[i]);
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.processMotionProfile();
    	
    	MotionProfileStatus status = Robot.driveTrain.getMotionProfileStatus();
    	
    	if(status.btmBufferCnt > 5 &&
    			status.outputEnable != CANTalon.SetValueMotionProfile.Enable) {
    		Robot.driveTrain.enableMotionProfile();
    	}
    	else if(status.btmBufferCnt <= 5 &&
    			status.outputEnable == CANTalon.SetValueMotionProfile.Enable) {
    		Robot.driveTrain.holdMotionProfile();
    	}
    	
    	if(status.hasUnderrun) {
    		Robot.logger.log("Underrun", 2);
    		Robot.driveTrain.removeUnderrun();
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

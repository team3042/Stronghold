package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon.MotionProfileStatus;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_File extends Command {
	
	Auto_MotionProfile motionProfileLeft;
	Auto_MotionProfile motionProfileRight;
	
	MotionProfileStatus[] status;

    public Auto_File() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.driveTrain.initMotionProfile();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	status = Robot.driveTrain.getMotionProfileStatus();
    	
    	if(status[0].btmBufferCnt > 5) {
    		//Robot.driveTrain.enableMotionProfile();
    	}
    	Robot.logger.log(status[0].btmBufferCnt + "", 3);
    	
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
    	return status[0].activePoint.isLastPoint;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    	Robot.driveTrain.disableMotionProfile();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupt", 1);
    	Robot.driveTrain.disableMotionProfile();
    }
}

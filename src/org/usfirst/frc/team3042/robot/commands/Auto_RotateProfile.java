package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.MotionProfileStatus;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_RotateProfile extends Command {

	double rotationsPerPixel = (RobotMap.isSkoll) ? 0.00196875 : 0.00196875; 
	
	//Current point
	int pointNumber = 0;

	//Time between each point in ms
	int itp = 20;
			
	//Time for each filter in ms
	double time1 = 50, time2 = 25;
		
	double wheelbaseWidth = 2.4;
	
	double maxSpeed = 0.04;
		
	AutoTrajectory_MotionProfile motionProfileLeft;
	AutoTrajectory_MotionProfile motionProfileRight;
	
	MotionProfileStatus[] status;
	
    public Auto_RotateProfile() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	
    	double offset = 100; //Robot.camera.getRotationOffset();
    	double leftTarget = -offset * rotationsPerPixel;
    	double rightTarget = offset * rotationsPerPixel;
    	
    	double leftMaxSpeed = offset * ((leftTarget > 0)? maxSpeed : -maxSpeed);
    	double rightMaxSpeed = offset * ((rightTarget > 0)? maxSpeed : -maxSpeed);
    	
    	motionProfileLeft = new AutoTrajectory_MotionProfile(itp, time1, time2, leftMaxSpeed, leftTarget);
    	motionProfileRight = new AutoTrajectory_MotionProfile(itp, time1, time2, rightMaxSpeed, rightTarget);
    	
    	CANTalon.TrajectoryPoint[] leftTrajectory = motionProfileLeft.calculateProfile();
    	CANTalon.TrajectoryPoint[] rightTrajectory = motionProfileRight.calculateProfile();
    	
    	for(int i = 0; i < leftTrajectory.length; i++) {
    		if(i < rightTrajectory.length) {
    			Robot.driveTrain.pushPoints(leftTrajectory[i], rightTrajectory[i]);
    		}
    		else {
    			CANTalon.TrajectoryPoint zeroPoint = new CANTalon.TrajectoryPoint();
    			zeroPoint.isLastPoint = true;
    			zeroPoint.position = rightTrajectory[i - 1].position;
    			zeroPoint.timeDurMs = itp;
    			zeroPoint.velocity = 0;
    			zeroPoint.zeroPos = false;
    			Robot.driveTrain.pushPoints(leftTrajectory[i], zeroPoint);
    		}
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	status = Robot.driveTrain.getMotionProfileStatus();
    	
    	if(status[0].btmBufferCnt > 5) {
    		Robot.driveTrain.enableMotionProfile();
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

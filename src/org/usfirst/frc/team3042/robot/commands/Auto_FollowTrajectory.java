package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.MotionProfileStatus;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_FollowTrajectory extends Command {
	
	boolean isReversed;
	
	double[][] leftPoints;
	double[][] rightPoints;
	
	MotionProfileStatus[] status;

    public Auto_FollowTrajectory(double[][] leftPoints, double[][] rightPoints, boolean isReversed) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrain);
    	
    	this.leftPoints = (isReversed)? rightPoints : leftPoints;
    	this.rightPoints = (isReversed)? leftPoints : rightPoints;
    	this.isReversed = isReversed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.driveTrain.initMotionProfile();
    	
    	for(int i = 0; i < leftPoints.length; i++) {
    		CANTalon.TrajectoryPoint currentLeftPoint = new CANTalon.TrajectoryPoint();
    		CANTalon.TrajectoryPoint currentRightPoint = new CANTalon.TrajectoryPoint();
    		
    		currentLeftPoint.position = ((isReversed)? -1 : 1) * leftPoints[i][0];
    		currentRightPoint.position = ((isReversed)? -1 : 1) * rightPoints[i][0];
    		
    		currentLeftPoint.velocity = ((isReversed)? -1 : 1) * leftPoints[i][1];
    		currentRightPoint.velocity = ((isReversed)? -1 : 1) * rightPoints[i][1];
    		
    		currentLeftPoint.timeDurMs = (int) leftPoints[i][2];
    		currentRightPoint.timeDurMs = (int) rightPoints[i][2];
    		
    		currentLeftPoint.profileSlotSelect = 0;
    		currentRightPoint.profileSlotSelect = 0;
    		
    		currentLeftPoint.zeroPos = (i == 0);
    		currentRightPoint.zeroPos = (i == 0);
    		
    		currentLeftPoint.isLastPoint = (i == leftPoints.length - 1);
    		currentRightPoint.isLastPoint = (i == leftPoints.length - 1);
    		
    		Robot.driveTrain.pushPoints(currentLeftPoint, currentRightPoint);
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

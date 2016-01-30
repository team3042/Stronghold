package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveStraight extends Command {
	//Current point
	int pointNumber = 0;

	//Time between each point in ms
	double itp = 30;
	
	//Time for each filter in ms
	double time1 = 400, time2 = 200;
	
	//Maximum speed in RPM
	double maxVelocity = 500;
	
	//Distance traveled in rotations
	double distance = 10;
	
	//Length of each filter
	double filterLength1 = Math.ceil(time1 / itp), 
			filterLength2 = Math.ceil(time2 / itp);
	
	//Sum of each filter
	double filterSum1 = 0, filterSum2 = 0, filterTotalSum = 0;
	
	//Velocity and position at current point
	double currentVelocity = 0, currentPosition = 0;
	
	//Time to decceleration in ms
	double timeToDeccel = distance / maxVelocity * 60 * 1000;
	
	//Total time in ms
	double totalTime = timeToDeccel + time1 + time2;
	
	//Total number of points
	double totalPoints = Math.ceil(totalTime / itp);
	
	//Array with all values of filterSum1
	double[] filterSums1 = new double[(int) totalPoints];
	
    public AutoDriveStraight() {
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
    	CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
    	
    	//Running through first filter
    	if (pointNumber * itp < time1 + time2) {
    		//Accelerating filter 1
    		filterSum1 = pointNumber / filterLength1;
    	}
    	else if (pointNumber * itp >= timeToDeccel) {
    		//Deccelerating filter 1
    		filterSum1 = (pointNumber - timeToDeccel / itp)
    				/ filterLength1;
    	}
    	else {
    		filterSum1 = 1;
    	}
    	
    	//Creating filterSum2 from the sum of the last 20 values of filterSum1
    	filterSums1[pointNumber] = filterSum1;
    	int filter2Start = (pointNumber > 20) ? pointNumber - 20 : 0;
    	filterSum2 = 0;
    	for(int i = filter2Start; i <= pointNumber; i++) {
    		filterSum2 += filterSums1[i];
    	}
    	
    	currentVelocity = (filterSum1 + filterSum2) / (filterLength2 + 1) * maxVelocity;
    	currentPosition += currentVelocity * itp;
    	
    	point.velocity = currentVelocity;
    	point.position = currentPosition;
    	point.zeroPos = (pointNumber == 0) ? true : false;
    	point.isLastPoint = (pointNumber == totalPoints) ? true : false;
    	Robot.driveTrain.streamMotionProfile(point);
    	pointNumber++;
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

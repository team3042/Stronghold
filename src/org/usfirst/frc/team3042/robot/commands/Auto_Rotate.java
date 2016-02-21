package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.subsystems.CameraAPI.ParticleReport2;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Rotate extends Command {
	//I believe that this command does not need to be calibrated for the camera being sideways, but testing
	//is required to prove this theory, which has only been tested on paper.
	
	//The degrees of error that the offset can follow
	//The default is -16.5 for center
	private NIVision.Range OFFSET_ERROR = new NIVision.Range(-1,1);
	private double OFFSET_ZERO = -16.5;
	
    private double rotateSpeed = 0.12;
	private double p = 0.1;
	private Timer timer = new Timer();
	double timeout = 4.0;
	
	//Variables for detecting if the robot has stopped turning
	double lastOffset = 0.0;
	int stillCycles = 0;
	int cyclesTolerance = 4;
	
	//Some variables to track motion if camera is not keeping up
	double encStart, firstOffset, startOffset;
	double cycleOffsetReduction = 0.5;
	
    public Auto_Rotate() {
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	finished = false;
    	timer.reset();
    	timer.start();
    	encStart = Robot.driveTrain.getLeftEncoder();
    	firstOffset = Robot.camera.getRotationOffset();
    	startOffset = 0.0;
    	lastOffset = 0;
    	stillCycles = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	ParticleReport2 report = Robot.camera.createTargetReport(55);
    	
	    double offset = 0.0;
		double leftSpeed = 0, rightSpeed = 0;
		
    	if(report != null){
    		offset = Robot.camera.getRotationOffset(report) - OFFSET_ZERO;
    		
    		if (offset == startOffset) {
    			stillCycles++;
    			if (stillCycles >= cyclesTolerance) {
    				finished = true;
    			}
    			//offset = lastOffset * cycleOffsetReduction;
    		}else{
    			stillCycles =0;
    			startOffset = offset;
    		}
    		lastOffset = offset;
    		
   			//when the offset is negative it means that the target is to the right
    		//when the offset is positive it means that the target is to the left
    		//this comes from (imagecenter - targetcenter) which is later converted into degrees for getRotationOffset
    		if(offset < OFFSET_ERROR.minValue){
   				//If the offset is negative, and less than the allowed negative error, then rotate to the right
   				leftSpeed = rotateSpeed;
   				rightSpeed = -rotateSpeed;
    		}else if(offset > OFFSET_ERROR.maxValue){
    			//If the offset is positive, and greater than the allowed positive error, then rotate to the left
    			leftSpeed = -rotateSpeed;
   				rightSpeed = rotateSpeed;
   			}else{
   				//The robot is within the error range, meaning that we are on target
    			finished = true;
    		}
    	}else{
    		Robot.logger.log("Failed to acquire target!", 3);
    		finished = true;
    	}
    	
    	leftSpeed *= Math.min(1, p * Math.abs(offset));
    	rightSpeed *= Math.min(1, p * Math.abs(offset));
    	
    	Robot.driveTrain.setMotors(leftSpeed, rightSpeed);
    }

    boolean finished = false;
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > timeout || finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    	double pixelsPerEnc = firstOffset / (Robot.driveTrain.getLeftEncoder()-encStart);
    	Robot.logger.log("Pixels Per Encoder Count = "+pixelsPerEnc, 3);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupt", 1);
    }
}

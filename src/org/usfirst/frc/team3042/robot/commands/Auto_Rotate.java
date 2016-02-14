package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.subsystems.CameraAPI.ParticleReport2;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Rotate extends Command {
	//I believe that this command does not need to be calibrated for the camera being sideways, but testing
	//is required to prove this theory, which has only been tested on paper.
	
	//The degrees of error that the offset can follow
	private NIVision.Range OFFSET_ERROR = new NIVision.Range(-5,5);
	
    public Auto_Rotate() {
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    ParticleReport2 report;
    // Called just before this Command runs the first time
    protected void initialize() {
    	//report = Robot.camera.createTargetReport(60);
    }

    private double rotateSpeed = 0.3;
    private double offset;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		report = Robot.camera.createTargetReport(60);
    	
    	if(report != null){
    		offset = Robot.camera.getRotationOffset(report);
    		Robot.logger.log("Offset: "+offset, 1);
    		//when the offset is negative it means that the target is to the right
    		//when the offset is positive it means that the target is to the left
    		//this comes from (imagecenter - targetcenter) which is later converted into degrees for getRotationOffset
    		if(offset < OFFSET_ERROR.minValue){
    			//If the offset is negative, and less than the allowed negative error, then rotate to the right
    			//Robot.driveTrain.setMotors(rotateSpeed, -rotateSpeed);
    		}else if(offset > OFFSET_ERROR.maxValue){
    			//If the offset is positive, and greater than the allowed positive error, then rotate to the left
    			//Robot.driveTrain.setMotors(-rotateSpeed, rotateSpeed);
    		}else{
    			//The robot is within the error range, meaning that we are on target
    			//Robot.driveTrain.setMotors(0.0,  0.0);
    			finished = true;
    			return;
    		}
    		
    	}else{
    		Robot.logger.log("Failed to acquire target!", 5);
			//Robot.driveTrain.setMotors(0.0,  0.0);
    		finished = true;
    	}
    }

    boolean finished = false;
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

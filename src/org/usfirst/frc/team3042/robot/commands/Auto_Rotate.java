package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.subsystems.CameraAPI.ParticleReport2;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Rotate extends Command {

	//The degrees of error that the offset can follow
	private NIVision.Range OFFSET_ERROR = new NIVision.Range(-5,5);
	
    public Auto_Rotate() {
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    ParticleReport2 report;
    // Called just before this Command runs the first time
    protected void initialize() {
    	report = Robot.camera.createTargetReport(60);
    }

    private double rotateSpeed = 0.3;
    private double offset;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(report != null){
    		offset = Robot.camera.getRotationOffset(report);
    		
    		if(offset < OFFSET_ERROR.minValue){
    			//Rotate right
    			Robot.driveTrain.setMotors(rotateSpeed, -rotateSpeed);
    		}else if(offset > OFFSET_ERROR.maxValue){
    			//Rotate left
    			Robot.driveTrain.setMotors(-rotateSpeed, rotateSpeed);
    		}else{
    			//The robot is within the error range, meaning that we are on target
    			Robot.driveTrain.setMotors(0.0,  0.0);
    			finished = true;
    		}
    		
    		report = Robot.camera.createTargetReport(60);
    	}else{
    		Robot.logger.log("Failed to acquire target!", 5);
    		finished = true;
    	}
    }

    boolean finished = false;
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

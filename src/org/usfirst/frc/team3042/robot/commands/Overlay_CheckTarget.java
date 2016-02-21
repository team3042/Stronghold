package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.subsystems.CameraAPI.ParticleReport2;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Overlay_CheckTarget extends Command {

    public Overlay_CheckTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.camera);
    	requires(Robot.driverCamera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    }
    
    //The offset minimum
    private double offsetMin = -2;
    //The offset maximum
    private double offsetMax = 2;
    //The offset zero
    private double offsetZero = -16.5f;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Set up a new particle report, passing a score minimum from 0 to 100
    	ParticleReport2 report = Robot.camera.createTargetReport(20);
    	
    	//Making sure that create target report found a target and returned it, the report will be null if it didn't.
    	if(report != null){
    		//Send the report to the overlay to be drawn
    		Robot.driverCamera.setOverlayReport(report);
    		//Get the offset from the target
    		double offset = Robot.camera.getRotationOffset(report) - offsetZero;
    		
    		if(offset>offsetMin && offset<offsetMax){
    			Robot.driverCamera.setTimedInformation(NIVision.RGB_GREEN, "On Target", 1.0f);
    			Robot.logger.log("Overlay target is in y range", 1);
    		}else{
    			Robot.driverCamera.setTimedInformation(NIVision.RGB_RED, "Off Target", 1.0f);
    			Robot.logger.log("Overlay target isnt in y range", 1);
    		}
    	}else{
    		Robot.logger.log("Overlay failed to create target report", 1);
    	}
    	
    	finished = true;
    }
    
    boolean finished = false;
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupted", 1);
    }
}

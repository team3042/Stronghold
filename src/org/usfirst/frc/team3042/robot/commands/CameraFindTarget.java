package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.subsystems.Camera.ParticleReport2;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class CameraFindTarget extends Command {

    public CameraFindTarget() {
    	requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    //165
    //65
    //40
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	ParticleReport2 report = Robot.camera.createTargetReport(60);
    	
    	if(report != null){
        	Robot.camera.outPutImagePNG(report.image, "CameraFilteredReportImage");
    		SmartDashboard.putBoolean("FoundTarget", true);
    		SmartDashboard.putNumber("DistanceToTarget (Ft)", Robot.camera.getDistToTargetInFeet());
    		SmartDashboard.putNumber("DistanceToCenter", Robot.camera.getParticleCenterOffset());
    	}else{
        	SmartDashboard.putBoolean("FoundTarget", false);
    	}
    	
    	hasFinished = true;
    }
    
    boolean hasFinished = false;
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return hasFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

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
    public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(125, 255);	//Range for green light
	public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(125, 255);	//Range for green light
	public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(125, 255);	//Range for green light
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		
		TARGET_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TARGET_HUE_RANGE.minValue);
		TARGET_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TARGET_HUE_RANGE.maxValue);
		TARGET_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TARGET_SAT_RANGE.minValue);
		TARGET_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TARGET_SAT_RANGE.maxValue);
		TARGET_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TARGET_VAL_RANGE.minValue);
		TARGET_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TARGET_VAL_RANGE.maxValue);
    	
    	Image binaryImage = Robot.camera.getHSVFilteredCameraFrame(TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);
    	
    	//Robot.camera.outPutImagePNG(binaryImage, "HSVFiltered_");
    	
    	Robot.camera.filterOutSmallParticles(binaryImage, 5, 100);
    	
    	//Robot.camera.outPutImagePNG(binaryImage, "SmallPartsFiltered_");
    	
    	ParticleReport2 report = Robot.camera.getTarget(binaryImage,0);
    	
    	if(report != null){
    		SmartDashboard.putBoolean("FoundTarget", true);
    		SmartDashboard.putNumber("DistanceToTarget (Ft)", Robot.camera.getDistanceToTarget(binaryImage, report));
    		SmartDashboard.putNumber("DistanceToCenter", 160 - (report.boundingBox.left+report.boundingBox.width *0.25));
    		SmartDashboard.putNumber("BBWidth", report.boundingBox.width);
    	}else{
        	SmartDashboard.putBoolean("FoundTarget", false);
    	}
    	
    	//hasFinished = true;
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

package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3042.robot.subsystems.CameraAPI.ParticleReport2;
/**
 *
 */
public class CameraAPI_Test extends Command {

    public CameraAPI_Test() {
        requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    public int testStartLower = 50;
    public int testStartUpper = 255;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.logger.log("--CameraAPI TEST--", 5);
    	Robot.logger.log("Robot.Camera.isSideways IS SET TO: "+Robot.camera.isSideways, 5);
    	for(int i = testStartLower; i<testStartUpper; i++){
	    	ParticleReport2 report = Robot.camera.createTargetReport(20);
	    	
	    	if(report != null){
	    		Robot.logger.log(""+i, 1);
	    		
	    		Robot.logger.log("|Created report at Score Min of "+(20)+"|", 5);
	    		Robot.logger.log("Distance to target: "+Robot.camera.getDistToTargetInFeet(report), 5);
	    		Robot.logger.log("Rotation offset: "+Robot.camera.getRotationOffset(report), 5);
	    		Robot.logger.log("Convex hull score: "+ Robot.camera.ConvexHullAreaScore(report), 5);
	    	}else{
	    		testStartLower++;
	    	}
    	}
    	finish = true;
    }

    boolean finish = false;
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finish;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

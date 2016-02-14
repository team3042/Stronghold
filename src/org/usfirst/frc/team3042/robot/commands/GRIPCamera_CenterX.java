package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GRIPCamera_CenterX extends Command {

    public GRIPCamera_CenterX() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gripCamera);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    //Numbers may change
    protected void execute() {
    	if(Robot.gripCamera.getX() > 130){
    		Robot.driveTrain.setMotors(0.1, -0.1);
    		
    	}
    	else if(Robot.gripCamera.getX() < 110){
    		Robot.driveTrain.setMotors(-0.1, 0.1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    //Numbers may change
    protected boolean isFinished() {
    	if((Robot.gripCamera.getX() >= 110) && ((Robot.gripCamera.getX() <= 130))){
    		return true;
    	}
    	else{
    		return false;
    	}
    }	

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

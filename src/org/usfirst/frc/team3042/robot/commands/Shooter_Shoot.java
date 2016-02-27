package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter_Shoot extends Command {
	
    public Shooter_Shoot() {
    	requires(Robot.shooter);
    	requires(Robot.shooterServo);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.shooter.spinToShoot();
    	Robot.logger.log("Camera Distance = "+Robot.camera.getDistToTarget(), 2);
    	Robot.logger.log("Potentiometer Value= " + 
    			Robot.snout.getPotentiometerVal(), 2);
    	Robot.logger.log("Offset: " + Robot.camera.getRotationOffset(), 2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {  	
    	if (Robot.shooter.readyToShoot()) {
    		Robot.shooterServo.setServoExtended();
    	}
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

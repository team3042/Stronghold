package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter_Intake extends Command {

	double targetRPM = 0;
	
    public Shooter_Intake() {
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	targetRPM = SmartDashboard.getNumber("Intake Speed");
    	Robot.shooter.setRPM(targetRPM);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Getting RPM of each motor and outputting to dashboard for tracking
    	//SmartDashboard.putNumber("Encoder RPM Left", Robot.shooter.getEncoderRPMLeft());
    	//SmartDashboard.putNumber("Encoder RPM Right", Robot.shooter.getEncoderRPMRight());	
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

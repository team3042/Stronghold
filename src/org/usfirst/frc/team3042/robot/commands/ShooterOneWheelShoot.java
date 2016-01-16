package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterOneWheelShoot extends Command {

	double targetRPM = 0;
	
    public ShooterOneWheelShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	targetRPM = SmartDashboard.getNumber("Shooter Speed");
    	Robot.shooter.setRPM(targetRPM);
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*
    	//Bang-bang controller keeping motor at desired RPM
    	if(Math.abs(Robot.shooter.getEncoderRPM()) < targetRPM) {
    		Robot.shooter.shoot(1.0);
    	}
    	else {
    		Robot.shooter.coast();
    	}
    	*/
    	SmartDashboard.putNumber("Encoder RPM", Robot.shooter.getEncoderRPM());
    	
    	
    }

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

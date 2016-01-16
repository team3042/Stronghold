package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterTwoWheelShoot extends Command {

	double targetRPM = 0;
	
    public ShooterTwoWheelShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	targetRPM = SmartDashboard.getNumber("Shooter Speed");
    	Robot.shooter.setRPMTwoWheel(targetRPM);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Encoder RPM #1", Robot.shooter.getEncoderRPM());
    	SmartDashboard.putNumber("Encoder Value #2", Robot.shooter.getEncoderRPMTwo());
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

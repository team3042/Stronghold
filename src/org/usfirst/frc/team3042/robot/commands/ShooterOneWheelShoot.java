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
    	Robot.shooter.setCoastMode();
    	targetRPM = SmartDashboard.getNumber("Shooter Speed");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Bang-bang controller keeping motor at desired RPM
    	if(Robot.shooter.getEncoderRPM() < targetRPM) {
    		Robot.shooter.shoot(1.0);
    	}
    	else {
    		Robot.shooter.coast();
    	}
    	System.out.println(Robot.shooter.getEncoderRPM());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.setBrakeMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.setBrakeMode();
    }
}

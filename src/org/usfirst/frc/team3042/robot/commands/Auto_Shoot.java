package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Shoot extends Command {

	Timer timer = new Timer();
	double timeToShoot = 2;
	
	public Auto_Shoot() {
    	requires(Robot.shooter);
    	requires(Robot.shooterServo);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.shooter.spinToShoot();
    	Robot.logger.log("Camera Distance = "+Robot.camera.getDistToTarget(), 2);
    	timer.reset();
		timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {  	
    	if (Robot.shooter.readyToShoot()) {
    		Robot.shooterServo.setServoExtended();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timer.get() > timeToShoot);
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

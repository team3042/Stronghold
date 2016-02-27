package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_ConditionalShooterArm extends Command {

	double encTarget, potValue, startPot;
	boolean finished;
	Timer timer = new Timer();
	double timeout = 3.0;
	
    public Auto_ConditionalShooterArm(double startPot, double encTarget, double potValue) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooterArm);
       
        this.startPot = startPot;
        this.encTarget = encTarget;
        this.potValue = potValue;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.driveTrain.resetEncoders();
    	finished = false;
    	timer.reset();
    	Robot.shooterArm.setPosition(startPot);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int leftEnc = Robot.driveTrain.getLeftEncoder();
    	int rightEnc = Robot.driveTrain.getRightEncoder();
    	
    	if ((leftEnc > encTarget) || (rightEnc > encTarget)) {
    		Robot.shooterArm.setPosition(potValue);
    		finished = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished || (timer.get() > timeout);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupted", 1);
    }
}

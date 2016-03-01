package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_ConditionalSnout extends Command {

	int encTarget;
	double potValue, startPot;
	boolean finished, targetReached;
	Timer timer = new Timer();
	double timeout = 5.0;
	double tolerance = 10.0;
	int waitCyclesLeft, waitCycles = 2;
	
    public Auto_ConditionalSnout(double startPot, int encTarget, double potValue) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.snout);
       
        this.startPot = startPot;
        this.encTarget = Math.abs(encTarget);
        this.potValue = potValue;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	waitCyclesLeft = waitCycles;
    	finished = false;
    	targetReached = false;
    	timer.reset();
    	timer.start();
    	Robot.snout.setPosition(startPot);
    }
    
    void delayedInitialize() {
    	Robot.driveTrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (waitCycles == 0) delayedInitialize();
    	if (waitCyclesLeft <= 0) {
    		int leftEnc = Math.abs(Robot.driveTrain.getLeftEncoder());
    		int rightEnc = Math.abs(Robot.driveTrain.getRightEncoder());
    	
    		if (targetReached) {
    			finished = Math.abs(Robot.snout.getPotValue() - potValue) < tolerance;    		
    		}
    		else if ((leftEnc > encTarget) || (rightEnc > encTarget)) {
    			Robot.snout.setPosition(potValue);
    			targetReached = true;
    		}
    	}
    	waitCyclesLeft --;
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

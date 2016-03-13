package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Auto_RotateAlt extends Command {
	private Timer timer = new Timer();
 
	int cyclesTolerance = 4;
	double rotationsPerPixel = (RobotMap.isSkoll) ? 0.002070 : 0.002625;
	double timeout = 5.0;

	double lastOffset;
	int stillCycles;
	boolean finished;
	double offset;
	
    public Auto_RotateAlt() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.camera);
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	lastOffset = 0.0;
    	stillCycles = 0;
    	finished = false;
    	timer.reset();
    	timer.start();
    	offset = Robot.camera.getRotationOffset();
    	Robot.logger.log("Offset " + offset, 3);
    	
    	double leftTarget = offset * rotationsPerPixel;
		double rightTarget = -offset * rotationsPerPixel;
		Robot.driveTrain.offsetPosition(leftTarget, rightTarget);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	SmartDashboard.putNumber("Error", Robot.driveTrain.leftMotorFront.getError());
    	//TODO Add finish condition
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

package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_RotateAlt extends Command {
	private Timer timer = new Timer();
 
	int cyclesTolerance = 4;
	double rotationsPerPixel = (RobotMap.isSkoll) ? 0.002625 : 0.002625;
	double timeout = 2.0;

	double lastOffset;
	int stillCycles;
	boolean finished;
	
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
    	
    	double leftTarget = -1;
		double rightTarget = 1;
		
		logOutput();
		
		Robot.driveTrain.setPosition(leftTarget, rightTarget);
    }
    
    void logOutput() {
    	double setPoint = Robot.driveTrain.leftMotorFront.getSetpoint();
    	double position = Robot.driveTrain.leftMotorFront.getPosition();
    	Robot.logger.log("LeftL: setPoint = "+setPoint+" position = "+position, 5);    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	logOutput();
    	
    	//double offset = Robot.camera.getRotationOffset();
    	//Robot.logger.log("Offset "+offset, 3);
    	
    	//if (offset == lastOffset && false) {
    	//	stillCycles++;
    	//	finished = (stillCycles > cyclesTolerance);
    	//} else {
    	//	stillCycles = 0;
    	//	
    	//	double leftTarget = -offset * rotationsPerPixel;
    	//	double rightTarget = offset * rotationsPerPixel;
		//	Robot.driveTrain.setPosition(leftTarget, rightTarget);
    	//}
    	//lastOffset = offset;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (timer.get() > timeout);
    	//return finished || (timer.get() > timeout);
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

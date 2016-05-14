package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shooter_Shoot extends Command {
	
	Timer timer = new Timer();
	
    public Shooter_Shoot() {
    	requires(Robot.shooter);
    	requires(Robot.shooterServo);
    	requires(Robot.camera);
    	requires(Robot.ledSwitch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.shooter.spinToShoot();
    	
    	timer.reset();
    	timer.start();
    	
    	//Robot.logger.log("Camera Distance = "+Robot.camera.getDistToTarget(), 4);
    	//Robot.logger.log("Potentiometer Value= " + Robot.snout.getPotValue(), 4);
    	//Robot.logger.log("Offset: " + Robot.camera.getRotationOffset(), 4);
    	//Robot.camera.outputCleanImage();
    	
    	//Image testImage = Robot.camera.getSubtractedFrame();
    	//Robot.camera.outputImage(testImage, Robot.camera.generateFileName());
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {  
    	//SmartDashboard.putNumber("Shooter Left", Robot.shooter.getEncoderRPMLeft());
    	if (Robot.shooter.readyToShoot() || timer.get() > 4) {
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
    	//Robot.camera.logData();
    }
}

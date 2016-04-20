package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraAPI_SubtractionTest extends Command {

	//public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(18*255/180, 100*255/180);	//Range for green light
	//public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(119, 255);	//Range for green light
	//public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(11, 255);	//Range for green light
	
	public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(0, 255);	//Range for green light
	public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(0, 255);	//Range for green light
	public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(0, 255);	//Range for green light
	
    public CameraAPI_SubtractionTest() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.camera);
    	requires(Robot.ledSwitch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	
    	//Image testImage = Robot.camera.getSubtractedFrame();
    	Image testImage = Robot.camera.getHSVFilteredCameraFrame(TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);
    	
    	Robot.camera.outputImage(testImage, "filterTest.png");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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

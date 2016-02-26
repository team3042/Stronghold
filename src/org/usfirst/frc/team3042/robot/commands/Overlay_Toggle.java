package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.subsystems.CameraAPI.ParticleReport2;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.RGBValue;
import com.ni.vision.NIVision.TextAlignment;
import com.ni.vision.NIVision.VerticalTextAlignment;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is mean to show the front view of the camera when toggled on, 
 * reverting to the back view when toggled off.
 * It attempts to create a particle report every cycle,
 * if successful it creates an overlay for that particle and sends it to the camera server,
 * if unsuccessful it just sends the front view camera to the camera server, no overlay.
 */
public class Overlay_Toggle extends Command {
	
	//Default attributes
	private int centerOvalSize = 4;
	private String defaultText = "";
	private NIVision.RGBValue defaultColor = NIVision.RGB_YELLOW;
	private NIVision.RGBValue defaultTextBackgroundColor = NIVision.RGB_TRANSPARENT;
	NIVision.OverlayTextOptions textOptions = new NIVision.OverlayTextOptions("Calibri", 10, 0, 0, 0, 0, TextAlignment.CENTER, VerticalTextAlignment.BASELINE, defaultTextBackgroundColor, 0);
	
	//A timer for displaying information
	private Timer timer = new Timer();
	
	private float timeToDisplay;
	private NIVision.RGBValue timedColor;
	private String timedText;
	
    public Overlay_Toggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.camera);
    	requires(Robot.driversCamera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	timer.reset();
    	timeToDisplay = 0;
    }

    //The offset variables
    private double offsetMin = -2;
    private double offsetMax = 2;
    private double offsetZero = -16.5f;

    protected void execute() {
    	//Set up a new particle report, passing a score minimum from 0 to 100
    	ParticleReport2 report = Robot.camera.createTargetReport(20);
    	
    	//If our report is not null we draw a targeting overlay
    	if(report != null){
    		//Get the offset from the target
    		double offset = Robot.camera.getRotationOffset(report) - offsetZero;
    		
    		if(offset>offsetMin && offset<offsetMax){
    			setTimedInformation(NIVision.RGB_GREEN, "On Target", 1.0f);
    			Robot.logger.log("Overlay target is in y range", 1);
    		}else{
    			setTimedInformation(NIVision.RGB_RED, "Off Target", 1.0f);
    			Robot.logger.log("Overlay target isnt in y range", 1);
    		}
    		
    		drawOverlayFrontView(defaultColor, defaultText, report);
    	}else{
    		//If our report is null we draw the regular front camera view
    		drawCleanFrontView();
    	}
    }
    
    private void setTimedInformation(RGBValue color, String text, float time){
    	timedColor = color;
    	timedText = text;
    	timeToDisplay = time;
    }
    
    //Draw the front camera view with targeting report information
    private void drawOverlayFrontView(NIVision.RGBValue color, String text, ParticleReport2 overlayReport){
    	//See if we are supposed to be coloring the image based on our timedColor
		if(timedColor!=null && timedText!= null && timer.get()<timeToDisplay){
			color = timedColor;
			text = timedText;
		}
		
		//Send the image to the camera server
		Robot.driversCamera.setCameraServerImage(Robot.camera.getDrawnOverlay(overlayReport.unfilteredImage, color, text, overlayReport, textOptions, centerOvalSize));
    }
    
    private void drawCleanFrontView(){
    	Robot.driversCamera.setCameraServerImage(Robot.camera.getCleanImage());
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    	timer.reset();
    	timeToDisplay = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupted", 1);
    	timer.reset();
    	timeToDisplay = 0;
    }
}

package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.subsystems.CameraAPI.ParticleReport2;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.TextAlignment;
import com.ni.vision.NIVision.VerticalTextAlignment;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;
import edu.wpi.first.wpilibj.vision.AxisCamera.WhiteBalance;

/**
 *
 */
public class DriverCamera extends Subsystem {
	//Create the camera from an IP set in the robotMap
	private AxisCamera camera = new AxisCamera(RobotMap.DRIVER_CAMERA_IP);
	
	//Boolean describing if the report was refreshed or if it is was already used.
	private boolean reportIsStale = true;
	//The particle report used to draw parts of the dashboard if it is not stale
	private ParticleReport2 overlayReport;
	
	//Default attributes
	private int centerOvalSize = 4;
	private String defaultText = "";
	private NIVision.RGBValue defaultTextBackgroundColor = NIVision.RGB_TRANSPARENT;
	NIVision.OverlayTextOptions textOptions = new NIVision.OverlayTextOptions("Calibri", 10, 0, 0, 0, 0, TextAlignment.CENTER, VerticalTextAlignment.BASELINE, defaultTextBackgroundColor, 0);
	
	//A timer for displaying information
	private Timer timer = new Timer();
	
	
	private float timeToDisplay;
	private NIVision.RGBValue timedColor;
	private String timedText;
	
	//Set up the values for the driver camera
	public DriverCamera(){
	}
	
	//Make sure that we are actually finding a camera server
	public void checkCameraServer(){
		if(CameraServer.getInstance() != null){
			Robot.logger.log("Camera Server found! ", 1);
			Robot.logger.log("Camera server autocapture is: "+CameraServer.getInstance().isAutoCaptureStarted(), 1);
		}else{
			Robot.logger.log("Camera Server not found! ", 1);
		}
	}
	
	//Set up onTarget feedback for the overlay, uses the timer
	public void setTimedInformation(NIVision.RGBValue color, String text, float time){
		//Reset the timer to 0 and then start it so that it counts up
		timer.reset();
		timer.start();
		//Tell the overlay what color it should display
		this.timedColor = color;
		//Tell the overlay what text to display
		this.timedText = text;
		//Set the time to use this color
		timeToDisplay = time;
	}
	
	//There is an oval drawn at the center of the overlay, this sets it's size
	public void setOvalSize(int size){
		centerOvalSize = size;
	}
	
	public void setOverlayReport(ParticleReport2 report){
		//Set the overlay report to this report
		overlayReport = report;
		//Check to see if the report is null, the report is stale if it is null
		reportIsStale = (report == null);
	}
	
	public void drawOverlay(NIVision.RGBValue paramColor){
		//The pointer for the image to send to the camera server
		Image image;
		//The pointer for the color to draw with
		NIVision.RGBValue color = paramColor;
		//The text to draw
		String text = defaultText;
		
		//See if we are supposed to be coloring the image based on our timedColor
		if(timedColor!=null && timedText!= null && timer.get()<timeToDisplay){
			color = timedColor;
			text = timedText;
		}
		
		//Check to see if we have a new report to use or not
		if(!reportIsStale){
			//Set the image to the overlay reports unfiltered image
			image = overlayReport.unfilteredImage;
			//Draw a box around the target
			NIVision.imaqOverlayRect(image, overlayReport.boundingBox, color, NIVision.DrawMode.DRAW_VALUE, null);
		}else{
			image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
			camera.getImage(image);
		}
		
		//Create a point that designates the center of the image
		NIVision.Point center = new NIVision.Point(NIVision.imaqGetImageSize(image).width/2,NIVision.imaqGetImageSize(image).height/2);
		
		//Create a point for the text
		NIVision.Point top = new NIVision.Point(center.x,NIVision.imaqGetImageSize(image).height-10);
		
		//Draw text at the top of the image
		NIVision.imaqOverlayText(image, top, text, color, textOptions, null);
		
		//Draw an oval at the center point
		NIVision.imaqOverlayOval(image, new Rect(center.y+(centerOvalSize/2),center.x-(centerOvalSize/2),centerOvalSize,centerOvalSize), color, NIVision.DrawMode.PAINT_VALUE);
		
		//Send the image to the camera server
		CameraServer.getInstance().setImage(image);
		
		//The report is now stale
		reportIsStale = true;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}


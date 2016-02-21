package org.usfirst.frc.team3042.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;
import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ParticleReport;
import com.ni.vision.NIVision.Rect;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;
import edu.wpi.first.wpilibj.vision.AxisCamera.Rotation;
import edu.wpi.first.wpilibj.vision.AxisCamera.WhiteBalance;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

//Compiled from two of the frc examples
public class CameraAPI extends Subsystem {
	
	//Create the camera from an IP set in the robotMap
	private AxisCamera camera = new AxisCamera(RobotMap.CAMERA_IP);
	
	//Our ranges for HSV image acquisition 
	/* From the 2015 example
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(101, 64);	
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(88, 255);
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(134, 255);
	*/
	//0-180
	//
	//
	public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(90, 140);	//Range for green light
	public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(64, 255);	//Range for green light
	public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(165, 255);	//Range for green light
	
	//Variables describing our camera
	double VIEW_ANGLE = 64; //default view angle for axis m1013
	
	//Variables describing our target
	float targetWidth = 20;//The width of our target
	float targetHeight = 12;//The height of our target
	float WIDTH_HEIGHT_RATIO = targetWidth/targetHeight;//The target width: 20 inches, divided by the target height: 12 inches.
	float HEIGHT_WIDTH_RATIO = targetHeight/targetWidth;//Use when camera is on it's side
	public boolean isSideways = true;//The boolean describing whether or not the camera is on it's side
	private double DEFAULT_SCORE_MIN = 55;
	
	public CameraAPI(){
		/*
		camera.writeCompression(30);
		camera.writeResolution(Resolution.k320x240);
		camera.writeWhiteBalance(WhiteBalance.kFixedFluorescent2);
		camera.writeBrightness(20);
		camera.writeExposureControl(ExposureControl.kHold);
		*/
	}
	
	public void checkCameraServer(){
		if(CameraServer.getInstance() != null){
			Robot.logger.log("Camera Server found! ", 1);
			Robot.logger.log("Camera server autocapture is: "+CameraServer.getInstance().isAutoCaptureStarted(), 1);
		}else{
			Robot.logger.log("Camera Server not found! ", 1);
		}
	}
	
	public void initDefaultCommand() {
		
	}
	
	//This particle report stores values for analyzed images
	public class ParticleReport2 extends ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double percentAreaToImageArea;
		double convexHullArea;
		public int particleIndex;
		public int boundingBoxRight;
		public double perimeter;
		public Image unfilteredImage;
		public Image image;
		
		public int compareTo(ParticleReport r)
		{
			return (int)(r.area - this.area);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.area - r2.area);
		}
	}
	
	//Get the center of the camera image minus the center of the particle/target
	//effectively giving the camera's offset, which will equal zero when the robot is perfectly facing the particle/target
	//returns the offset in feet
	public double getRotationOffset(){
		ParticleReport2 report = createTargetReport(DEFAULT_SCORE_MIN);
		
		return getRotationOffset(report);
	}
	
	public double getRotationOffset(ParticleReport2 report){
		double offset = 0.0;
		if (report != null) {
			if(isSideways){
				int height = NIVision.imaqGetImageSize(report.image).height;
				offset = ((report.boundingBox.top-report.boundingBox.height *0.5)-((double)height/2));
			} else {
				int width = NIVision.imaqGetImageSize(report.image).width;
				offset = ((report.boundingBox.left+report.boundingBox.width *0.5)-((double)width/2));
			}
		}
		return offset;
	}
	
	//How far away the robot is from the target.
	public double getDistToTarget () {
		ParticleReport2 report = createTargetReport(DEFAULT_SCORE_MIN);
		
		return getDistToTarget(report);
	}
	
	double scaleFactor = 1400;
	public double getDistToTarget(ParticleReport2 report){
		double distance = 0.0;
		if (report != null){
			distance =  scaleFactor / report.perimeter;
		}
		return distance;
	}
	
	//Run all the filters for a stronghold target
	public ParticleReport2 createTargetReport(double SCORE_MIN){
		//Filtered HSV
    	Image binaryImage = getHSVFilteredCameraFrame(TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);
    	
    	this.outPutImagePNG(binaryImage, "HSVFiltered");
    	filterOutSmallParticles(binaryImage, 0, 100);
    	fillParticles(binaryImage);
    	this.outPutImagePNG(binaryImage, "FilteredParticle");
    	
    	ParticleReport2 targetReport = null;
    	
    	int numParticles = NIVision.imaqCountParticles(binaryImage, 1);
    	
		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport2> particles = new Vector<ParticleReport2>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport2 par = new ParticleReport2();
				
				//We only need to set the area and the index here, because we need area to sort them, and we wont
				//have the index value later on. The rest of the values can be set after finding the largest particle.
				par.particleIndex = particleIndex;
				par.area = (int)NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
				particles.add(par);
			}
			
			//"If the specified comparator is null then all elements in this list must implement the Comparable interface"
			particles.sort(null);
			//Get the largest particle, after the vector list has been sorted
			ParticleReport2 report = particles.get(0);
			
			//I only set these values after we find the largest particle.
			report.percentAreaToImageArea = NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
			report.convexHullArea = NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
			report.boundingBox.top = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			report.perimeter = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_PERIMETER);
			report.boundingBox.left = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			report.boundingBoxRight = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			report.boundingBox.width = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
			report.boundingBox.height = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
			
			boolean isTarget = this.TrapezoidScore(report) >= SCORE_MIN && 
			this.aspectRatioScore(report)>=SCORE_MIN 
			&& this.ConvexHullAreaScore(report)>= SCORE_MIN;
			//Robot.logger.log("Trapezoid: "+this.TrapezoidScore(report), 5);
			//Robot.logger.log("AspectRatio: "+this.aspectRatioScore(report), 5);
			//Robot.logger.log("ConvexHull: "+this.ConvexHullAreaScore(report), 5);
			
			if(isTarget){
				particles.get(0).image = binaryImage;
				particles.get(0).unfilteredImage = unfilteredImage;
				targetReport = particles.get(0);
			}else{
				Robot.logger.log("!!!------------------------------------", 5);
				Robot.logger.log("Trapezoid: "+this.TrapezoidScore(report), 5);
				Robot.logger.log("AspectRatio: "+this.aspectRatioScore(report), 5);
				Robot.logger.log("ConvexHull: "+this.ConvexHullAreaScore(report), 5);
				Robot.logger.log("-------------------------------------", 5);
			}
		}
    	
    	return targetReport;
	}
	
	//Creates an image in the roborios tmp folder
	public void outPutImagePNG(Image image, String name){
		try {
			//Creates a png image on the roborio of Frame in location /tmp
			NIVision.imaqWritePNGFile2(image, File.createTempFile(name, ".png").getAbsolutePath(), 100, NIVision.RGB_BLACK, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Image unfilteredImage;
	//A method that allows other classes to utilize the camera classes ability to anazlyze frames
	public Image getHSVFilteredCameraFrame(NIVision.Range hueRange, NIVision.Range satRange, NIVision.Range valRange ){
		
		//Get an image from the camera
		Image unfilteredFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
		camera.getImage(unfilteredFrame);
		unfilteredImage = unfilteredFrame;
		
		//Filter the image from the camera through an HSV filter
		Image filteredBinaryFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
		NIVision.imaqColorThreshold(filteredBinaryFrame, unfilteredFrame, 255, NIVision.ColorMode.HSV, hueRange, satRange, valRange);
		
		return filteredBinaryFrame;
	}
	
	//A method that filters out small particles from a binary image
	public void filterOutSmallParticles(Image image, float lowerPercentage, float upperPercentage){
		NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
		NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
		
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, lowerPercentage, upperPercentage, 0, 0);
		
		NIVision.imaqParticleFilter4(image, image, criteria, filterOptions, null);
	}
	
	//Fill out gaps in particles, including the U-shaped stronghold high-goal target
	public void fillParticles(Image image){
		NIVision.imaqFillHoles(image, image, 1);
		NIVision.imaqConvexHull(image, image, 1);
	}
	
	
	// ***** The overlay for the camera
	
	//Boolean describing if the report was refreshed or if it is was already used.
	private boolean reportIsStale = true;
	//The particle report used to draw parts of the dashboard if it is not stale
	private ParticleReport2 overlayReport;
	//The size of the oval at the center of the image
	private int centerOvalSize = 4;
	//The default text to display
	private String defaultText = "Overlay";
	
	// ***** Information that is displayed based on the timer
	
	//A timer for displaying information
	private Timer timer = new Timer();
	//How long to display certain information
	private float timedDisplay;
	//The color information to display
	private NIVision.RGBValue timedColor;
	//The text information to display
	private String timedText;
	
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
		timedDisplay = time;
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
		if(timedColor!=null && timedText!= null && timer.get()<timedDisplay){
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
		//NIVision.OverlayTextOptions options = new NIVision.OverlayTextOptions(fontName, fontSize, bold, italic, underline, strikeout, horizontalTextAlignment, verticalTextAlignment, backgroundColor, angle)
		//NIVision.imaqOverlayText(image, top, text, color, options, group);
		
		
		//Draw an oval at the center point
		NIVision.imaqOverlayOval(image, new Rect(center.y+(centerOvalSize/2),center.x-(centerOvalSize/2),centerOvalSize,centerOvalSize), color, NIVision.DrawMode.DRAW_VALUE);
		
		//Send the image to the camera server
		CameraServer.getInstance().setImage(image);
		
		//The report is now stale
		reportIsStale = true;
	}
	
	//Comparator function for sorting particles. Returns true if particle 1 is larger
	static boolean CompareParticleSizes(ParticleReport2 particle1, ParticleReport2 particle2)
	{
		//we want descending sort order
		return particle1.percentAreaToImageArea > particle2.percentAreaToImageArea;
	}

	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}

	/**
	 * Method to score convex hull area. This scores how "complete" the particle is. Particles with large holes will score worse than a filled in shape
	 */
	public double ConvexHullAreaScore(ParticleReport2 report)
	{
		return ratioToScore((report.area/report.convexHullArea)*1.18);
	}

	/**
	 * Method to score if the particle appears to be a trapezoid. Compares the convex hull (filled in) area to the area of the bounding box.
	 * The expectation is that the convex hull area is about 95.4% of the bounding box area for an ideal tote.
	 */
	public double TrapezoidScore(ParticleReport2 report)
	{
		return ratioToScore(report.convexHullArea/((report.boundingBox.width)*(report.boundingBox.height)*.954));
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the long side of a tote.
	 */
	public double aspectRatioScore(ParticleReport2 report)
	{
		//For the stronghold competition the camera is flipped 90 degrees which means we have to test with height to width
		if(isSideways){
			return ratioToScore(((double)(report.boundingBox.width)/(report.boundingBox.height))/HEIGHT_WIDTH_RATIO);
		}
		return ratioToScore(((report.boundingBox.width)/(report.boundingBox.height))/WIDTH_HEIGHT_RATIO);
	}
}
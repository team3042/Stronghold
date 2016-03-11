package org.usfirst.frc.team3042.robot.subsystems;

import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ContourPoint;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.OverlayTextOptions;
import com.ni.vision.NIVision.ParticleReport;
import com.ni.vision.NIVision.Point;
import com.ni.vision.NIVision.PointDouble;
import com.ni.vision.NIVision.Rect;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;
import edu.wpi.first.wpilibj.vision.AxisCamera.WhiteBalance;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

//Compiled from two of the frc examples
public class CameraAPI extends Subsystem {
	
	//Create the camera from an IP set in the robotMap
	private AxisCamera camera = new AxisCamera(RobotMap.CAMERA_IP);
	
	private static final String FILE_DATE_FORMAT = "yyyy-MM-dd-hhmmss";
	
	//Our ranges for HSV image acquisition 
	/* From the 2015 example
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(101, 64);	
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(88, 255);
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(134, 255);
	*/
	//0-180
	//
	//
	/*
	public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(90, 140);	//Range for green light

	public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(69, 255);	//Range for green light
	public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(109, 255);	//Range for green light
	*/
	/*Daytime Commons 8*/
	//public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(112, 154);	//Range for green light
	//public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(153, 255);	//Range for green light
	//public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(96, 155);	//Range for green light

	//Lights Off - B123
	//public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(0, 210);
	//public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(0, 255);
	//public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(133, 255);
	
	//Commons Evening
	//public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(90, 140);
	//public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(180, 255);
	//public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(108, 255);
	
	//Duluth Red Alliance
	//public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(30, 149);
	//public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(0, 231);
	//public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(149, 242);
	
	//Duluth Blue Alliance(NOT CALIBRATED!!!!!!!!!!!!!!!)
	//public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(48, 150);
	//public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(0, 222);
	//public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(163, 255);
	
	//Duluth Pits
	public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(93, 154);
	public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(44, 255);
	public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(83, 255);
	
	//Variables describing our camera
	double VIEW_ANGLE = 64; //default view angle for axis m1013
	
	//Variables describing our target
	float targetWidth = 20;//The width of our target
	float targetHeight = 12;//The height of our target
	float WIDTH_HEIGHT_RATIO = targetWidth/targetHeight;//The target width: 20 inches, divided by the target height: 12 inches.
	float HEIGHT_WIDTH_RATIO = targetHeight/targetWidth;//Use when camera is on it's side
	public boolean isSideways = true;//The boolean describing whether or not the camera is on it's side
	private double DEFAULT_SCORE_MIN = 55;
	
	//Fenrir offset at 320x240 is -16.5. 
	//Scaled up to 480x360 I expect it to be -25
	double OFFSET_ZERO = (RobotMap.isSkoll) ? -44 : -63.5;
	
	public CameraAPI(){
		camera.writeCompression(30);
		camera.writeResolution(Resolution.k480x360);
		camera.writeWhiteBalance(WhiteBalance.kFixedFluorescent2);
		camera.writeBrightness(20);
		camera.writeExposureControl(ExposureControl.kHold);
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
			offset -= OFFSET_ZERO;
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
	
	public void outputCleanImage() {
		String dir = "/home/lvuser/images/";
		
		Date now = new Date();
		SimpleDateFormat fileTimeStamp = new SimpleDateFormat(FILE_DATE_FORMAT);
		fileTimeStamp.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
		String name = fileTimeStamp.format(now);
		
		Robot.fileIO.openFile(dir, name);
		
		NIVision.imaqWritePNGFile2(getCleanImage(), dir + name, 100, NIVision.RGB_BLACK, 1);
	}
	
	public void logData() {
		ParticleReport2 report = createTargetReport(DEFAULT_SCORE_MIN);
		
		int perimeter = (int) report.perimeter;
		int width = report.boundingBox.width;
		int height = report.boundingBox.height;
		int centerY = (int) (report.boundingBox.top - (height / 2.0));
		
		Robot.logger.log("Width = " + width + ", Height = " + height +
				", Perimeter = " + perimeter + ", Center Y = " + centerY, 4);
	}
	
	//Run all the filters for a stronghold target
	public ParticleReport2 createTargetReport(double SCORE_MIN){
		//Filtered HSV
    	Image binaryImage = getHSVFilteredCameraFrame(TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);
    	
    	//this.outPutImagePNG(binaryImage, "HSVFiltered");
    	filterOutSmallParticles(binaryImage, 0, 100);
    	fillParticles(binaryImage);
    	//this.outPutImagePNG(binaryImage, "FilteredParticle");
    	
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
			
			boolean isTarget = false;
			int currentTargetIndex = 0;
			//"If the specified comparator is null then all elements in this list must implement the Comparable interface"
			particles.sort(null);
			
			for(int i=0; i<particles.size(); i++){
				//Get the largest particle, after the vector list has been sorted
				ParticleReport2 report = particles.get(i);
				
				//I only set these values after we find the largest particle.
				report.percentAreaToImageArea = NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				report.convexHullArea = NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
				report.boundingBox.top = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				report.perimeter = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_PERIMETER);
				report.boundingBox.left = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				report.boundingBoxRight = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				report.boundingBox.width = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				report.boundingBox.height = (int)NIVision.imaqMeasureParticle(binaryImage, report.particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				
				isTarget = this.TrapezoidScore(report) >= SCORE_MIN && 
				this.ConvexHullAreaScore(report)>= SCORE_MIN; //&&
				//this.aspectRatioScore(report)>=SCORE_MIN;
				//Robot.logger.log("Trapezoid: "+this.TrapezoidScore(report), 5);
				//Robot.logger.log("AspectRatio: "+this.aspectRatioScore(report), 5);
				//Robot.logger.log("ConvexHull: "+this.ConvexHullAreaScore(report), 5);
				if(isTarget){
					currentTargetIndex = i;
					break;
				}
			}
			if(isTarget){
				particles.get(currentTargetIndex).image = binaryImage;
				particles.get(currentTargetIndex).unfilteredImage = unfilteredImage;
				targetReport = particles.get(currentTargetIndex);
			}else{
				//Testing for getting side views of a the target, creating a triangle
				particles.get(0).image = binaryImage;
				particles.get(0).unfilteredImage = unfilteredImage;
				//ParticleReport2 triangleTest = particles.get(0);
				
				//isTriangle(triangleTest);
				
				Robot.logger.log("!!!------------------------------------", 3);
				Robot.logger.log("Didn't find target.", 3);
				Robot.logger.log("Trapezoid: "+this.TrapezoidScore(particles.get(0)), 3);
				Robot.logger.log("AspectRatio: "+this.aspectRatioScore(particles.get(0)), 3);
				Robot.logger.log("ConvexHull: "+this.ConvexHullAreaScore(particles.get(0)), 3);
				Robot.logger.log("-------------------------------------", 3);
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
	
	//Comparator function for sorting particles. Returns true if particle 1 is larger
	static boolean CompareParticleSizes(ParticleReport2 particle1, ParticleReport2 particle2)
	{
		//we want descending sort order
		return particle1.percentAreaToImageArea > particle2.percentAreaToImageArea;
	}
	
	//The overlay method meant for the targeting camera in stronghold
	public Image getDrawnOverlay(Image image, NIVision.RGBValue color, String text, ParticleReport2 overlayReport, OverlayTextOptions textOptions, int centerOvalSize){
		//Draw a box around the target
		NIVision.imaqOverlayRect(image, overlayReport.boundingBox, color, NIVision.DrawMode.DRAW_VALUE, null);
		
		//Create a point that designates the center of the image
		NIVision.Point center = new NIVision.Point(NIVision.imaqGetImageSize(image).width/2,NIVision.imaqGetImageSize(image).height/2);
		
		//Create a point for the text
		NIVision.Point top = new NIVision.Point(center.x,NIVision.imaqGetImageSize(image).height-10);
		
		//Draw text at the top of the image
		NIVision.imaqOverlayText(image, top, text, color, textOptions, null);
		
		//Draw an oval at the center point
		NIVision.imaqOverlayOval(image, new Rect(center.y+(centerOvalSize/2),center.x-(centerOvalSize/2),centerOvalSize,centerOvalSize), color, NIVision.DrawMode.PAINT_VALUE);
    
		return image;
	}
	
	public Image getCleanImage(){
		Image image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
		camera.getImage(image);
		return image;
	}

	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}
	
	public int isTriangle(ParticleReport2 report){
		NIVision.ContourInfoReport creport = NIVision.imaqContourInfo(report.image);
		NIVision.ContourFitPolynomialReport fpReport = NIVision.imaqContourFitPolynomial(report.image, 1);
		System.out.println(creport.length);
		
		//Iterate through the countour points to get the highest points on the image
		Vector<PointDouble> highestPoints = new Vector<PointDouble>();
		double highestCurrentPoint = 0;
		for(int i =0; i<creport.pointsPixel.length; i++){
			if(isSideways){
				if(creport.pointsPixel[i].x>highestCurrentPoint){
					//If the new point is higher on the y axis than any others, clear the list and add it
					highestPoints.clear();
					highestPoints.add(creport.pointsPixel[i]);
					highestCurrentPoint = creport.pointsPixel[i].x;
				}if(creport.pointsPixel[i].x==highestCurrentPoint){
					//If this point is one of the highest then we should add it to our list
					highestPoints.add(creport.pointsPixel[i]);
				}
			}else{
				if(creport.pointsPixel[i].y>highestCurrentPoint){
					//If the new point is higher on the y axis than any others, clear the list and add it
					highestPoints.clear();
					highestPoints.add(creport.pointsPixel[i]);
					highestCurrentPoint = creport.pointsPixel[i].y;
				}if(creport.pointsPixel[i].y==highestCurrentPoint){
					//If this point is one of the highest then we should add it to our list
					highestPoints.add(creport.pointsPixel[i]);
				}
			}
		}
		
		//Iterate through the highest points to get the left most point
		PointDouble leftMostPoint = null;
		for(int i=0; i<highestPoints.size(); i++){
			if(isSideways){
				if(leftMostPoint!=null){
					if(highestPoints.get(i).y>leftMostPoint.y){
						leftMostPoint = highestPoints.get(i);
					}
				}else{
					leftMostPoint = highestPoints.get(i);
				}
			}else{
				if(leftMostPoint!=null){
					if(highestPoints.get(i).x<leftMostPoint.x){
						leftMostPoint = highestPoints.get(i);
					}
				}else{
					leftMostPoint = highestPoints.get(i);
				}
			}
		}
		
		if(leftMostPoint!=null){
			if(isSideways){
				return (int) (report.boundingBox.top-leftMostPoint.y);
			}else{
				return (int) (report.boundingBox.left-leftMostPoint.x);
			}
		}

		return 0;
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
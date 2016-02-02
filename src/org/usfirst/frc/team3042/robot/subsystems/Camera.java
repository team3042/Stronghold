package org.usfirst.frc.team3042.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.subsystems.Camera.ParticleReport2;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ParticleReport;
import com.ni.vision.NIVision.RGBValue;
import com.ni.vision.NIVision.ROI;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import java.io.File;
import java.io.IOException;

//Compiled from two of the frc examples
public class Camera extends Subsystem {
	//!!! Needs testing/research to confirm
	
	//Create the camera from an IP set in the robotMap
	private AxisCamera camera = new AxisCamera(RobotMap.CAMERA_IP);
	
	//This particle report stores values for analyzed images
	//We create an array (or "vector") to store many particle reports later on
	//!!! It may extend comparator to allow it to sort what particles are most likely to be the target 
	public class ParticleReport2 extends ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double percentAreaToImageArea;
		double convexHullArea;
		public int particleIndex;
		public int boundingBoxRight;
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
	
	double VIEW_ANGLE = 49.4; //default view angle for axis m1011
	float WIDTH_HEIGHT_RATIO = 1.43f;//The target width: 20 inches, divided by the target height: 14 inches.
	
	//!!! Do not know what imaqError is, may not actually be needed anyways
	//int imaqError;
	
	public void initDefaultCommand() {
		
	}
	
	public static NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(50, 255);	//Range for green light
	public static NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(50, 255);	//Range for green light
	public static NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(50, 255);	//Range for green light
	
	//Get the center of the camera image minus the center of the particle/target
	//effectively giving the camera's offset, which will equal zero when the robot is perfectly facing the particle/target
	public double getParticleCenterOffset(){
		//160 is half of the width of the camera
		ParticleReport2 report = createTargetReport();
		
		if(report == null){
		return 0;
		}
		
		int width = NIVision.imaqGetImageSize(report.image).width;
		System.out.println("img width: "+width);
		
		return (width/2) - (report.boundingBox.left+report.boundingBox.width *0.5);
	}
	
	//How far away the robot is from the target.
	public double getDistToTargetInFeet () {
		ParticleReport2 report = createTargetReport();
		
		NIVision.GetImageSizeResult size;
		size = NIVision.imaqGetImageSize(report.image);
		double targetWidth = 20;//the width of the stronghold target is 1ft 8in
		double normalizedWidth = 2*report.boundingBox.width/size.width;
		
		return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
	}
	
	//Run all the filters for a stronghold target
	public ParticleReport2 createTargetReport(){
		TARGET_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TARGET_HUE_RANGE.minValue);
		TARGET_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TARGET_HUE_RANGE.maxValue);
		TARGET_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TARGET_SAT_RANGE.minValue);
		TARGET_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TARGET_SAT_RANGE.maxValue);
		TARGET_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TARGET_VAL_RANGE.minValue);
		TARGET_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TARGET_VAL_RANGE.maxValue);
    	
    	Image binaryImage = Robot.camera.getHSVFilteredCameraFrame(TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);
    	
    	Robot.camera.filterOutSmallParticles(binaryImage, 5, 100);
    	Robot.camera.fillParticles(binaryImage);
    	
    	ParticleReport2 report = Robot.camera.getTarget(binaryImage,60);
    	
    	return report;
	}
	
	//A method that will return a target if it finds one, otherwise returning null
	//Sort of optimized for totes in some utilized methods, and in other methods optimized for the high goal in stronghold
	//unclear if optimization for totes is also good for stronghold, deeper investigation required
	public ParticleReport2 getTarget(Image image, double SCORE_MIN){
		int numParticles = NIVision.imaqCountParticles(image, 1);
		
		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport2> particles = new Vector<ParticleReport2>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport2 par = new ParticleReport2();
				par.particleIndex = particleIndex;
				par.percentAreaToImageArea = NIVision.imaqMeasureParticle(image, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.area = (int)NIVision.imaqMeasureParticle(image, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.convexHullArea = NIVision.imaqMeasureParticle(image, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
				par.boundingBox.top = (int)NIVision.imaqMeasureParticle(image, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.boundingBox.left = (int)NIVision.imaqMeasureParticle(image, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.boundingBoxRight = (int)NIVision.imaqMeasureParticle(image, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				par.boundingBox.width = (int)NIVision.imaqMeasureParticle(image, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				par.boundingBox.height = (int)NIVision.imaqMeasureParticle(image, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				particles.add(par);
			}
			
			//"If the specified comparator is null then all elements in this list must implement the Comparable interface"
			particles.sort(null);
		    
			/*boolean isTarget = this.TrapezoidScore(particles.get(0)) > SCORE_MIN && 
			this.aspectRatioScore(particles.get(0))>SCORE_MIN && 
			this.ConvexHullAreaScore(particles.get(0)) > SCORE_MIN;*/
			
			boolean isTarget = this.TrapezoidScore(particles.get(0)) >= SCORE_MIN && 
					this.aspectRatioScore(particles.get(0))>=SCORE_MIN;
					//this.ConvexHullAreaScore(particles.get(0)) >= SCORE_MIN;
			//Convex hull score was giving values of around 0.03, unable to pass any high score_mo
			/*System.out.println("Trapezoid Score"+this.TrapezoidScore(particles.get(0)));
			System.out.println("AspectRatioScore"+this.aspectRatioScore(particles.get(0)));
			System.out.println("ConvexHullAreaScore"+this.ConvexHullAreaScore(particles.get(0)));*/
			
			if(isTarget){
				particles.get(0).image = image;
				return particles.get(0);
			}
		}
		return null;
	}
	
	public double getParticleWidth(ParticleReport2 report){
		return report.boundingBoxRight - report.boundingBox.left;
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
	
	//A method that allows other classes to utilize the camera classes ability to anazlyze frames
	public Image getHSVFilteredCameraFrame(NIVision.Range hueRange, NIVision.Range satRange, NIVision.Range valRange ){
		
		//Get an image from the camera
		Image unfilteredFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
		camera.getImage(unfilteredFrame);
		
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
	double ConvexHullAreaScore(ParticleReport2 report)
	{
		return ratioToScore((report.area/report.convexHullArea)*1.18);
	}

	/**
	 * Method to score if the particle appears to be a trapezoid. Compares the convex hull (filled in) area to the area of the bounding box.
	 * The expectation is that the convex hull area is about 95.4% of the bounding box area for an ideal tote.
	 */
	double TrapezoidScore(ParticleReport2 report)
	{
		return ratioToScore(report.convexHullArea/((report.boundingBox.width)*(report.boundingBox.height)*.954));
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the long side of a tote.
	 */
	double aspectRatioScore(ParticleReport2 report)
	{
		return ratioToScore(((report.boundingBox.width)/(report.boundingBox.height))/WIDTH_HEIGHT_RATIO);
	}
}

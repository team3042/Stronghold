package org.usfirst.frc.team3042.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team3042.robot.RobotMap;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ParticleReport;
import com.ni.vision.NIVision.RGBValue;

import edu.wpi.first.wpilibj.command.Subsystem;
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
	
	//Value used in putting images on roborio
	//NIVision.RGBValue value = new NIVision.
	
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
					this.aspectRatioScore(particles.get(0))>=SCORE_MIN && 
					this.ConvexHullAreaScore(particles.get(0)) >= SCORE_MIN;
			
			if(isTarget){
				return particles.get(0);
			}
		}
		return null;
	}
	
	//returns distance to target, further investigation required to see if works properly
	public double getDistanceToTarget(Image image, ParticleReport2 target){
		return getDistToTargetInFeet(image, target);
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
	
	public double getDistToTargetInFeet (Image image, ParticleReport2 report) {
		NIVision.GetImageSizeResult size;
		size = NIVision.imaqGetImageSize(image);
		double targetWidth = 20;//the width of the stronghold target is 1ft 8in
		double normalizedWidth = 2*report.boundingBox.width/size.width;
		
		return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
	}

}

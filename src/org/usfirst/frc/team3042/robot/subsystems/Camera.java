package org.usfirst.frc.team3042.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team3042.robot.RobotMap;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ParticleReport;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;


//Compiled from two of the frc examples
public class Camera extends Subsystem {
	
	public class ParticleReport2 extends ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double percentAreaToImageArea;
		double convexHullArea;
		
		public int compareTo(ParticleReport r)
		{
			return (int)(r.area - this.area);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.area - r2.area);
		}
	}
	
	private Image frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	private AxisCamera camera = new AxisCamera(RobotMap.CAMERA_IP);
	private ParticleReport report;
	
	double VIEW_ANGLE = 49.4; //default view angle for axis m1011
	float AREA_MINIMUM = 0.5f; //Default Area minimum for particle as a percentage of total image area
	float WIDTH_HEIGHT_RATIO = 1.43f;//The target width: 20 inches, divided by the target height: 14 inches.
	double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
	
	NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(90, 110);	//Range for green light
	NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(60, 255);	//Range for green light
	NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(50, 255);	//Range for green light
	
	//The criteria and filtering and whatnot for the photo, not quite understood yet.
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
	//Do not know what imaqError is, may not actually be needed anyways
	int imaqError;
	
	public void initDefaultCommand() {
	
		

	}

	Image binaryFrame;
	public void updateImage(){
		camera.getImage(frame);
		CameraServer.getInstance().setImage(frame);
		
		//Threshold the image looking for target hsv color
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);
	
		//Find particles
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);
		
		//Filter out small particles
		criteria[0].lower = AREA_MINIMUM;
		imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
		
		//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Filtered particles", numParticles);
		
		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport2> particles = new Vector<ParticleReport2>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport2 par = new ParticleReport2();
				par.percentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.area = (int)NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.convexHullArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
				par.boundingBox.top = (int)NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.boundingBox.left = (int)NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.boundingBox.width = (int)NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				par.boundingBox.height = (int)NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				particles.add(par);
			}
			particles.sort(null);
		    
			//
			boolean isTarget = this.TrapezoidScore(particles.get(0)) > SCORE_MIN && 
			this.aspectRatioScore(particles.get(0))>SCORE_MIN && 
			this.ConvexHullAreaScore(particles.get(0)) > SCORE_MIN;
			//
			
			SmartDashboard.putBoolean("IsTarget", isTarget);
			SmartDashboard.putNumber("Distance", getDistToTargetInFeet(binaryFrame, particles.elementAt(0)));
		}else{
			SmartDashboard.putBoolean("IsTarget", false);
		}
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

package org.usfirst.frc.team3042.robot.subsystems;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;
import edu.wpi.first.wpilibj.vision.AxisCamera.WhiteBalance;

/**
 *
 */
public class CameraOpenCV extends Subsystem {
    
	//Create the camera from an IP set in the robotMap
	private AxisCamera camera = new AxisCamera(RobotMap.CAMERA_IP);
	private static final String CAMERA_ADDRESS = "http://10.30.42.11/view/viewer_index.shtml?id=87&dummyparam=out.mjpg"; //TODO determine actual url of camera view
	VideoCapture capture;
	
	private static final double CONNECTION_TIMEOUT = 10;
	private boolean isConnected = false;
	
	//HSV bounds for filtering target (Hue, Saturation, Value)
	Scalar lowerHSVBound = new Scalar(0, 62, 57, 0);
	Scalar upperHSVBound = new Scalar(95, 255, 255, 0);
	
	//Blue Alliance
	Scalar blueLowerHSVBound = new Scalar(0, 62, 57, 0);
	Scalar blueUpperHSVBound = new Scalar(95, 255, 255, 0);
	
	//Red Alliance
	Scalar redLowerHSVBound = new Scalar(0, 62, 57, 0);
	Scalar redUpperHSVBound = new Scalar(95, 255, 255, 0);
	
	//If off to the right make less negative, to the left change to more negative
	final double OFFSET_ZERO = (RobotMap.isSkoll) ? -52 : -47.5;
	final double FOCAL_LENGTH = 0.5 * 360 / Math.tan(1.17 / 2); //TODO replace 1.17 with horizontal FOV
	
	//Formatting of file outputs
	private static final String FILE_DATE_FORMAT = "yyyy-MM-dd-hhmmss";
	private static final String OUTPUT_DIRECTORY = "/home/lvuser/images/";
	
	Mat stencil;
	
	public CameraOpenCV(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//Initializing camera settings and opening camera feed
		initCamera();
		
		//Creating a template shape to compare to the goal reflection
		stencil = new Mat(8, 1, CvType.CV_32SC2);
		stencil.put(0, 0, new int[]{/*p1*/32, 0, /*p2*/ 26, 76, /*p3*/ 184, 76, /*p4*/ 180, 0, /*p5*/ 203, 0, 
				/*p6*/ 212, 100, /*p7*/ 0, 100, /*p8*/ 9, 0});
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    private void initCamera() {
    	camera.writeCompression(30);
		camera.writeResolution(Resolution.k480x360);
		camera.writeWhiteBalance(WhiteBalance.kFixedFluorescent2);
		camera.writeBrightness(50);
		camera.writeExposureControl(ExposureControl.kHold);
		camera.writeColorLevel(50);
		
		capture = new VideoCapture(CAMERA_ADDRESS);
		
		//Waiting for the camera view to open
		Timer timer = new Timer();
		timer.start();
		int elapsedTime = 1;
		while(!capture.isOpened()) {
			if(timer.get() > CONNECTION_TIMEOUT) {
				Robot.logger.log("Camera connection timed out", 2);
				break;
			}
			else if(timer.get() > elapsedTime) {
				Robot.logger.log("Opening camera(" + elapsedTime + " second(s))...", 3);
				elapsedTime++;
			}
		}
		if(capture.isOpened()) {
			Robot.logger.log("Camera view opened", 3);
			isConnected = true;
		}
    }
    
    //Gets the current angular offset from the target in radians
    public double getRotationOffset() {
    	double angleOffset = 0;
    	if(isConnected) {
    		Point[] targetConvexHull = getTargetConvexHull();
    	
    		angleOffset = calculateRotationOffset(targetConvexHull);
    	}
    	else {
    		Robot.logger.log("No camera found", 2);
    	}
    	
    	return angleOffset;
    }
    
    //Outputs the current camera view to the image folder on the roborio
    public void outputView() {
    	if(isConnected) {
    		Mat image = getImage();
    		if(!image.empty()) {
    			outputImage(image);
    		}
    	}
    	else {
    		Robot.logger.log("No camera found", 2);
    	}
    }
    
    public void setHSVValues(Robot.Alliance alliance) {
		if(alliance == Robot.Alliance.BLUE_ALLIANCE) {
			setCameraValues(blueLowerHSVBound, blueUpperHSVBound);
		}
		else {
			setCameraValues(redLowerHSVBound, redUpperHSVBound);
		}
	}
    
    private void setCameraValues(Scalar lowerHSVBound, Scalar upperHSVBound) {
    	this.lowerHSVBound = lowerHSVBound;
    	this.upperHSVBound = upperHSVBound;
    }
    
    private Mat getImage() {
    	Mat image = new Mat();
    	if(capture.grab()) {
    		capture.retrieve(image);
    	}
    	else {
    		Robot.logger.log("Unable to retrieve image", 3);
    	}
    	return image;
    }
    
    private Mat getSubtractedImage() {
    	Timer timer = new Timer();
		Mat unlitFrame = getImage();
		timer.start();
		while(timer.get() < 0.2);
		
		Robot.ledSwitch.setOn();
		
		timer.reset();
		timer.start();
		while(timer.get() < 0.25); //TODO test shorter timeouts
		
		Mat litFrame = getImage();
		
		Robot.ledSwitch.setOff();
		Robot.logger.log("Beginning image subtraction", 3);
		Mat subtractedFrame = new Mat();
		Core.subtract(litFrame, unlitFrame, subtractedFrame);
		Robot.logger.log("End image subtraction", 3);
		
		//outputImage(subtractedFrame, "SubtractedImage" + generateFileName()); 
		//outputImage(unlitFrame, "UnlitFrame" + generateFileName());
		//outputImage(litFrame, "LitFrame" + generateFileName());
		return subtractedFrame;
    }
    
    private void outputImage(Mat image) {
    	String filename = OUTPUT_DIRECTORY + generateFileName() + ".png";
    	
    	outputImage(image, filename);
    }
    
    private void outputImage(Mat image, String filename) {
    	Imgcodecs.imwrite(filename, image);
    }
    
    private String generateFileName() {
		Date now = new Date();
		SimpleDateFormat fileTimeStamp = new SimpleDateFormat(FILE_DATE_FORMAT);
		fileTimeStamp.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
		return fileTimeStamp.format(now);		
	}
    
    private Point[] getTargetConvexHull() {
  		Mat filteredImage = getHSVFilteredSubtractedImage();
    	
    	List<MatOfPoint> contours = getContours(filteredImage);
    	MatOfPoint target = processContours(contours);
    	
    	return calculateConvexHull(target);
  	}
    
    private Mat getHSVFilteredImage() {
    	Mat image = getImage();
    	
    	return filterImageHSV(image);
    }
    
    private Mat getHSVFilteredSubtractedImage() {
    	Mat image = getSubtractedImage();
		
		return filterImageHSV(image);
    }
    
    private Mat filterImageHSV(Mat image) {
    	Mat hsvFrame = new Mat();
		Mat filteredFrame = new Mat();
		
		//Converting to HSV and filtering to a binary image
		Imgproc.cvtColor(image, hsvFrame, Imgproc.COLOR_BGR2HSV);
		Core.inRange(hsvFrame, lowerHSVBound, upperHSVBound, filteredFrame);
		filteredFrame.convertTo(filteredFrame, CvType.CV_8UC1);
		
		return filteredFrame;
    }
    
    private List<MatOfPoint> getContours(Mat image) {
    	List<MatOfPoint> contours = new ArrayList<MatOfPoint>();    
	    Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
	    
	    return contours;
    }
    
    private MatOfPoint processContours(List<MatOfPoint> contours) {
		double[] similarities = new double[contours.size()];
		for(int i = 0; i < contours.size(); i++) {
			MatOfPoint currentContour = contours.get(i);
						
			//Filtering out small contours, primarily noise
			if(Imgproc.contourArea(currentContour) > 500) {
				//Calculating similarity to the u shape of the goal
				double similarity = Imgproc.matchShapes(currentContour, stencil, Imgproc.CV_CONTOURS_MATCH_I3, 0);
				System.out.println(similarity);
				if(similarity < 10) {
					similarities[i] = similarity;
				}
				else similarities[i] = 1000;
			}
			else {
				similarities[i] = 1000;
			}
		}
		
		//Finding most similar of the contours, lower similarity is better
		int mostSimilar = -1;
		for(int i = 0; i < similarities.length; i++) {
			if(similarities[i] != 1000) {
				if(similarities[i] < ((mostSimilar == -1)? 1000: similarities[mostSimilar])) {
					mostSimilar = i;
				}
			}
		}
		
		MatOfPoint targetContour;
		if(mostSimilar == -1) {
			Robot.logger.log("No similar contour found", 3);
			targetContour = new MatOfPoint();
		}
		else {
			targetContour = contours.get(mostSimilar);
		}
		
		return targetContour;
	}
    
    //Calculating the convex hull of a roughly rectangular contour
  	private Point[] calculateConvexHull(MatOfPoint contour) {
  		Point[] targetPoints = contour.toArray();
  	    Point[] convexHull = new Point[4];
  	    convexHull[0] = new Point(10000, 10000);
  	    convexHull[1] = new Point(0, 10000);
  	    convexHull[2] = new Point(0, 0);
  	    convexHull[3] = new Point(10000, 0);
  	    
  	    //Iterating through all points in the contour to find farthest in each direction
  	    for(int i = 0; i < targetPoints.length; i++) {
  	    	Point currentPoint = targetPoints[i];
  	    	if (convexHull[0].x + convexHull[0].y > currentPoint.x + currentPoint.y) convexHull[0] = currentPoint;
  			if (convexHull[1].y - convexHull[1].x > currentPoint.y - currentPoint.x) convexHull[1] = currentPoint;
  			if (convexHull[2].x + convexHull[2].y < currentPoint.x + currentPoint.y) convexHull[2] = currentPoint;
  			if (convexHull[3].x - convexHull[3].y > currentPoint.x - currentPoint.y) convexHull[3] = currentPoint;
  	    }
  	    
  	    return convexHull;
  	}
  	
  	private double calculateRotationOffset(Point[] convexHull) {
  		double pixelOffset = 0.5 * (convexHull[1].y + convexHull[2].y) - OFFSET_ZERO;
  		
  		double angleOffset = Math.atan(pixelOffset / FOCAL_LENGTH);
  		
  		return angleOffset;
  	}
}


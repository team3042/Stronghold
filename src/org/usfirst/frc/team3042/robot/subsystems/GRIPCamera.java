
package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.Robot;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GRIPCamera extends Subsystem {
    
	double[] defaultValues = new double[] {0};
	double[] areas;
	double[] widths;
	double[] heights;
	double[] xs;
	double[] ys;
	
	final double TARGET_HEIGHT = 12.0; //The Target Height in Inches
	final double TARGET_WIDTH = 20.0; //The Target Height in Inches
	final double CAMERA_RESOLUTION_HEIGHT = 320; //Height of camera screen in pixels
	final double CAMERA_RESOLUTION_WIDTH = 240; //Width of camera screen in pixels
	final double CAMERA_HEIGHT = 0.0; //Height of the Camera from the ground in Inches
	double VIEW_ANGLE = 67.0; //47.0; 67 is m1013, 47 is m1011 View angle of Camera in Degrees
	double VIEW_ANGLE_RAD = (((VIEW_ANGLE)*(Math.PI))/(180)); //View angle in rads
	double distance;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //Returns distance in Inches camera is from target
    public double getDistance(){
    	distance = ((TARGET_HEIGHT + TARGET_WIDTH)*(CAMERA_RESOLUTION_WIDTH)) / ((getHeight() + getWidth())*2*(Math.tan(VIEW_ANGLE_RAD/2)));
    	return distance;	
    }
    
    //Returns values in Pixels from GRIP Network Table
    public double getArea(){
    	areas = Robot.table.getNumberArray("area", defaultValues);
    	return(areas[0]);
    }
    public double getWidth(){
    	widths = Robot.table.getNumberArray("width", defaultValues);
    	return(widths[0]);
    }
    public double getHeight(){
    	heights = Robot.table.getNumberArray("height", defaultValues);
    	return(heights[0]);
    }
    public double getX(){
    	xs = Robot.table.getNumberArray("centerX", defaultValues);
    	return(xs[0]);
    }
    public double getY(){
    	ys = Robot.table.getNumberArray("centerY", defaultValues);
    	return(ys[0]);
    }
    
    
}


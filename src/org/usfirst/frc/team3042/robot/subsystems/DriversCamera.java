package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriversCamera extends Subsystem {
	//The camera on the back of the stronghold robot
    //private USBCamera driverCamera = new USBCamera(RobotMap.DRIVERS_CAMERA);
	
    CameraServer driversCameraServer;
    
    public DriversCamera () {
        driversCameraServer = CameraServer.getInstance();
        driversCameraServer.setQuality(50);
        driversCameraServer.startAutomaticCapture(RobotMap.DRIVERS_CAMERA);
    }
    
    //Just a plain image from this camera
    public Image getCleanImage(){
		Image image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
		//driverCamera.getImage(image);
		return image;
	}
    
    public void setCameraServerImage(Image image){
    	driversCameraServer.setImage(image);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}


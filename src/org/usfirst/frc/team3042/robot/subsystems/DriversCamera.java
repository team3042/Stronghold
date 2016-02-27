package org.usfirst.frc.team3042.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class DriversCamera extends Subsystem {
    
    CameraServer usbCamera;
    
    public DriversCamera () {
        usbCamera = CameraServer.getInstance();
        usbCamera.setQuality(50);
        usbCamera.startAutomaticCapture("cam1");
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}


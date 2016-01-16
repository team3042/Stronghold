package org.usfirst.frc.team3042.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.ShooterStop;

/**
 *
 */
public class Shooter extends Subsystem {
	
	CANTalon talon1 = new CANTalon(RobotMap.SHOOTER_TALON);
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ShooterStop());

    }
    
    public void shoot(double speed) { 
    	talon1.set(speed);
    	
    }
    
    public void stop() {
    	talon1.set(0);
    }
    
    public void coast() {
    	this.stop();
    }
    
    public void setCoastMode() {
    	talon1.enableBrakeMode(false);
    }
    
    public void setBrakeMode() {
    	talon1.enableBrakeMode(true);
    }
    
    //Getting speed in native units per 100ms and converting to RPM
    public double getEncoderRPM() {
    	return talon1.getSpeed() * 600 / 1024;
    }
    
}


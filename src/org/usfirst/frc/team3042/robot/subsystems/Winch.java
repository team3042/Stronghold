package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Winch_Stop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {
    
	CANTalon winchTalon = new CANTalon(RobotMap.WINCH_TALON);
	
	double winchSpeed = 0.9;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	setDefaultCommand(new Winch_Stop());
    }
    
    public void stop() {
    	winchTalon.set(0);
    }
    
    public void pullUp() {
    	winchTalon.set(winchSpeed);
    }
}


package org.usfirst.frc.team3042.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.LEDSwitch_SetOff;
import org.usfirst.frc.team3042.robot.commands.LEDSwitch_SetOn;

/**
 *
 */
public class LEDSwitch extends Subsystem {
	
	Solenoid LEDSolenoid = new Solenoid(RobotMap.LED_SWITCH);
	Solenoid LEDSolenoid2 = new Solenoid(2);
		
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new LEDSwitch_SetOff());
    }
    
    public void setOn(){
    	LEDSolenoid.set(true);
    	LEDSolenoid2.set(true);
    }
    
    public void setOff(){
    	LEDSolenoid.set(false);
    	LEDSolenoid2.set(false);
    }
}


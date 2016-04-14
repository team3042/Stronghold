package org.usfirst.frc.team3042.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.LEDSwitch_SetOn;

/**
 *
 */
public class LEDSwitch extends Subsystem {
	
	Relay LEDSpike = new Relay(RobotMap.LED_SWITCH);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new LEDSwitch_SetOn());
    }
    
    public void setOn(){
    	LEDSpike.set(Relay.Value.kOn);
    }
    
    public void setOff(){
    	LEDSpike.set(Relay.Value.kOff);
    }
}


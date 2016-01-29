package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 *
 */
public class ShooterArm extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private CANTalon talonRotate = new CANTalon(RobotMap.SHOOTER_ARM_TALON);

	Potentiometer pot = new AnalogPotentiometer(RobotMap.SHOOTER_ARM_POT, 1, 0);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void stop() {
    	talonRotate.set(0);
    }
    
    public double getPotentiometerVal() {
    	return pot.get();
    }
}


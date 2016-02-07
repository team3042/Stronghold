package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.TapeShooter_Stop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TapeShooter extends Subsystem {
    
	CANTalon shooterTalon = new CANTalon(RobotMap.TAPE_SHOOTER_TALON);
	
	double tapeSpeed = 0.8;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TapeShooter_Stop());
    }
    
    public void stop() {
    	setSpeed(0);
    }
    
    public void raise() {
    	setSpeed(tapeSpeed);
    }
    
    public void lower() {
    	setSpeed(-tapeSpeed);
    }
    
    private void setSpeed(double speed) {
    	shooterTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterTalon.set(speed);
    }
    
    
}


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
	
	double raiseSpeed = 0.8, lowerSpeed = 0.2;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new TapeShooter_Stop());
    }
    
    public void stop() {
    	setSpeed(0.0);
    }
    
    public void raise() {
    	resetEncoder();
    	setSpeed(raiseSpeed);
    }
    
    public void lower() {
    	setSpeed(-lowerSpeed);
    }
    
    private void setSpeed(double speed) {
    	shooterTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterTalon.set(speed);
    }

	public void resetEncoder() {
		// TODO: Zero the encoder
	}

	public boolean encoderLimitReached() {
		// TODO: determine if we have reached the encoder limit for raising the tape.
		return false;
	}  
}


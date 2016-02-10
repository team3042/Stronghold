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
	
	final double raiseSpeed = 0.8, lowerSpeed = 0.8;
	int encoderZero;
	final double encLimit = 36 * (4096 / 5.89);
	
	public TapeShooter() {
		resetEncoder();
	}
	
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
    
    public void retract() {
    	setSpeed(-lowerSpeed);
    }
    
    private void setSpeed(double speed) {
    	shooterTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterTalon.set(speed);
    }

	public void resetEncoder() {
		encoderZero = shooterTalon.getEncPosition();
	}
	
	public boolean encoderLimitReached() {
		return getEncDistance() >= encLimit;
	} 
	
	public boolean encoderZeroReached() {
		return getEncDistance() <= 0;
	}

	public int getEncDistance(){
		return shooterTalon.getEncPosition() - encoderZero;
		
	}
}


package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.TapeShooter_Stop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TapeShooter extends Subsystem {
    	
	CANTalon shooterTalon = new CANTalon(RobotMap.TAPE_SHOOTER_TALON);
	
	final double raiseSpeed = .9, lowerSpeed = 0.5;
	int encoderZero;
	final double encLimit = 41000;
	final double tolerance = 500;
	
	
	public TapeShooter() {
		shooterTalon.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
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
    	setSpeed(raiseSpeed);
    }
    
    public void retract() {
    	setSpeed(encoderZeroReached() ? 0 : -lowerSpeed);
    }
    
    private void setSpeed(double speed) {
    	shooterTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterTalon.set(-speed);
    }

	public void resetEncoder() {
		encoderZero = shooterTalon.getEncPosition();
	}
	
	public boolean encoderLimitReached() {
		return getEncDistance() >= encLimit;
	} 
	
	public boolean encoderZeroReached() {
		return getEncDistance() <= tolerance;
	}

	public int getEncDistance(){
		return -(shooterTalon.getEncPosition() - encoderZero);
		
	}
}


package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.HookLift_Stop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HookLift extends Subsystem {
    	
	CANTalon liftTalon = new CANTalon(RobotMap.HOOK_LIFT_TALON);
	
	final double raiseSpeed = 0.5, lowerSpeed = 0.2;
	int encoderZero;
	final double encLimit = 62000;
	final double tolerance = 500;
	
	boolean deployed = false;
	
	public HookLift() {
		liftTalon.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		resetEncoder();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new HookLift_Stop());
    }
    
    public void stop() {
    	setSpeed(0.0);
    }
    
    public void raise() {
    	setSpeed((encoderLimitReached()) ? 0 : raiseSpeed);
    }
    
    public void retract() {
    	setSpeed(-lowerSpeed);
    }
    
    private void setSpeed(double speed) {
    	liftTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	liftTalon.set(-speed);
    }

	public void resetEncoder() {
		encoderZero = liftTalon.getEncPosition();
	}
	
	public boolean encoderLimitReached() {
		return getEncDistance() >= encLimit;
	} 
	
	public boolean encoderZeroReached() {
		return getEncDistance() <= tolerance;
	}

	public int getEncDistance(){
		return -(liftTalon.getEncPosition() - encoderZero);
		
	}
	
	public boolean isDeployed() {
		return true || deployed;
	}
	
	public void setDeployedTrue() {
		deployed = true;
	}
}


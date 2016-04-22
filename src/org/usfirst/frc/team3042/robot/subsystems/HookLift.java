package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.HookLift_Stop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HookLift extends Subsystem {
    	
	CANTalon liftTalon = new CANTalon(RobotMap.HOOK_LIFT_TALON);
	
	final double raiseSpeed = (RobotMap.isSkoll)? 0.75 : 0.75, lowerSpeed = (RobotMap.isSkoll)? 0.75 : 0.75;
	int encoderZero;
	final double encLimit = 47000;
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
    	double speed = 0;
    	//if (Robot.hookLiftServo.isDeployed()) {
    		speed = raiseSpeed;
    	//}
    	setSpeed(speed);
    }
    
    public void retract() {
    	double speed = 0;
    	//if (Robot.hookLiftServo.isDeployed()) {
    		speed = -lowerSpeed;
    	//}
    	setSpeed(speed);
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
		return deployed;
	}
	
	public void setDeployedTrue() {
		deployed = true;
	}
	
	public void resetDeploy() {
		deployed = false;
	}
}


package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.ShooterArm_HoldPosition;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterArm extends Subsystem {
	
	public CANTalon talonRotate = new CANTalon(RobotMap.SHOOTER_ARM_TALON);

	private double rotateSpeed = .3;
	private double upperLimit = 100;
	private double lowerLimit = 790;
	private double storage = 200;
	private double pickup = 765;
	
	private double p = 10, i = 0, d = 0;
	
	public ShooterArm() {
		talonRotate.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		talonRotate.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
		
		talonRotate.setPID(p, i ,d);
		talonRotate.setAllowableClosedLoopErr(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShooterArm_HoldPosition());
    }

    public void stop() {
    	set(0.0);
    }
    
    void set(double speed) {
    	Robot.logger.log(speed + "", 0);
    	talonRotate.changeControlMode(TalonControlMode.PercentVbus);
    	talonRotate.set(speed);
    }
    
    public void raise() {
    	if (getPotentiometerVal() > upperLimit) {
    		set(-rotateSpeed);
    	}
    	else {
    		set(0);
    	}
    }
    
    public void lower() {
    	if (getPotentiometerVal() < lowerLimit) {
    		set(rotateSpeed);
    	}
    	else {
    		set(0);
    	}
    }
    
    public boolean belowRaiseLimit() {
    	return (getPotentiometerVal() > upperLimit);
    }
    
    public boolean aboveLowerLimit(){
    	return (getPotentiometerVal() < lowerLimit);
    }
    
    public void setPosition(double position) {
    	position = safetyTest(position);
    	
    	talonRotate.changeControlMode(TalonControlMode.Position);
    	talonRotate.set(position);
    }
    
    public void goToPickup() {
    	setPosition(pickup);
    }
    
    public void goToStorage() {
    	setPosition(storage);
    }
    
    private double safetyTest(double position) {
    	if (position < upperLimit) {
    		position = upperLimit;
    	}
    	else if (position > lowerLimit) {
    		position = lowerLimit;
    	}
    	return position;
    }
    
    public double getPotentiometerVal() {
    	return talonRotate.getAnalogInRaw();
    }
}


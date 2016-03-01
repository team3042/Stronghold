package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Snout_HoldPosition;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Snout extends Subsystem {
	
	public CANTalon talonRotate = new CANTalon(RobotMap.SHOOTER_ARM_TALON);
	
	//Pot Values
	private double POT_ZERO = (RobotMap.isSkoll)? 790: 865;
	private double lowerLimit = 10;
	private double raiseLimit = 690;
	private double storage = 640;
	private double pickup = 10; 
	private double shoot = 276;
	private double layup = 340;
	
	private double rotateSpeed = .6;
	private double slowRotateSpeed = 0.3;
	private double p = 5, i = 0, d = 0;
	
	private double positionScalar = 1, positionAdditive = 0;
	
	public Snout() {
		talonRotate.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		talonRotate.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
		talonRotate.reverseOutput(true);
		talonRotate.setInverted(true);
		
		talonRotate.setPID(p, i, d);
		talonRotate.setAllowableClosedLoopErr(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Snout_HoldPosition());
    }

    public void stop() {
    	setSpeed(0.0);
    }
    
    void setSpeed(double speed) {
    	talonRotate.changeControlMode(TalonControlMode.PercentVbus);
    	talonRotate.set(speed);
    }
    
    public double getPotentiometerVal() {
    	return POT_ZERO - talonRotate.getAnalogInRaw();
    }

    public void setPosition(double position) {
    	position = POT_ZERO - safetyTest(position);
    	position = scalePosition(position);
    	
    	talonRotate.changeControlMode(TalonControlMode.Position);
    	talonRotate.set(position);
    }
    
    private double safetyTest(double position) {
    	if (position > raiseLimit) {
    		position = raiseLimit;
    	}
    	else if (position < lowerLimit) {
    		position = lowerLimit;
    	}
    	return position;
    }
    
    private double scalePosition(double position) {
    	position = position * positionScalar + positionAdditive;
    	
    	return position;
    }
    
    public boolean belowRaiseLimit() {
    	return (getPotentiometerVal() < raiseLimit);
    }
    
    public boolean aboveLowerLimit(){
    	return (getPotentiometerVal() > lowerLimit);
    }
    
    public void raise() {
    	if (belowRaiseLimit()) {
    		setSpeed(-rotateSpeed);
    	}
    	else {
    		setPosition(raiseLimit);
    	}
    }
    
    public void slowRaise() {
    	if (belowRaiseLimit()) {
    		setSpeed(-slowRotateSpeed);
    	}
    	else {
    		setPosition(raiseLimit);
    	}
    }
    
    public void lower() {
    	if (aboveLowerLimit()) {
    		setSpeed(rotateSpeed);
    	}
    	else {
    		setPosition(lowerLimit);
    	}
    }
    
    public void slowLower() {
    	if (aboveLowerLimit()) {
    		setSpeed(slowRotateSpeed);
    	}
    	else {
    		setPosition(lowerLimit);
    	}
    }
    
    public void goToPickup() {
    	setPosition(pickup);
    }
    
    public void goToStorage() {
    	setPosition(storage);
    }
    
    public void goToShoot() {
    	setPosition(shoot);
    }
    
    public void goToLayup() {
    	setPosition(layup);
    }
    
    public void holdPosition() {
    	setPosition(getPotentiometerVal());
    }    
}


package org.usfirst.frc.team3042.robot.subsystems;

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
	
	//Pot Values
	private double POT_ZERO = (RobotMap.isSkoll)? 790: 865;
	private double lowerLimit = 25;
	private double raiseLimit = 690;
	private double storage = 640;
	private double pickup = 25; 
	
	private double toleranceMin = 350, toleranceMax = (RobotMap.isSkoll)? 800: 500;
	private int allowableError = (RobotMap.isSkoll)? 35: 20;
	
	private double rotateSpeed = .6;
	private double p = 10, i = 0, d = 0;
	
	public ShooterArm() {
		talonRotate.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		talonRotate.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
		talonRotate.reverseOutput(true);
		talonRotate.setInverted(true);
		
		talonRotate.setPID(p, i ,d);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShooterArm_HoldPosition());
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
    	
    	if(position > toleranceMin && position < toleranceMax) {
    		//talonRotate.setAllowableClosedLoopErr(allowableError);
    		talonRotate.setAllowableClosedLoopErr(0);
    	}
    	else {
    		talonRotate.setAllowableClosedLoopErr(0);
    	}
    	
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
    
    public void lower() {
    	if (aboveLowerLimit()) {
    		setSpeed(rotateSpeed);
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
    
    public void holdPosition() {
    	setPosition(getPotentiometerVal());
    }    
}


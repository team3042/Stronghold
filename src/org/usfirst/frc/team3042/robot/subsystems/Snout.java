package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Snout_HoldPosition;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.CANTalon.MotionProfileStatus;
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
	private double shoot = 240;
	private double layup = 350;
	
	private double p = 5, i = 0.00, d = 0; //i = 0.009
	private int iZone = 25;
	
	double potGoal, tolerance = 5;
	
	//Dynamic f-gain to counter gravity
	double horizontalPotValue = 100;// = measured
	double verticalPotValue = 500;// = measured
	double motorScalar = 1;// = measured
	double radiansPerPotValue = 0.5 * Math.PI / (verticalPotValue - horizontalPotValue);
	
	//Creating thread to make talon process motion profile buffer when points are available in upper buffer
	class PeriodicRunnable implements java.lang.Runnable {
		public void run() { 
			talonRotate.processMotionProfileBuffer();
		}
	}
	
	Notifier notifier = new Notifier (new PeriodicRunnable());
		
	public Snout() {
		talonRotate.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		talonRotate.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
		talonRotate.reverseOutput(true);
		talonRotate.setInverted(true);
		
		talonRotate.setPID(p, i, d);
		talonRotate.setIZone(iZone);
		talonRotate.setAllowableClosedLoopErr(0);
		
		//Beginning motion profile
		talonRotate.changeMotionControlFramePeriod(5);
    	notifier.startPeriodic(0.005);
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
    
    public double getPotValue() {
    	return POT_ZERO - talonRotate.getAnalogInRaw();
    }
    
    public void setPosition(double position) {
    	talonRotate.changeControlMode(TalonControlMode.Position);
    	potGoal = position;
    	
    	//setFGain(position);
    	setTalonPosition(position);
    }
    
    public void setFGain (double targetPotValue) {
    	double theta = (targetPotValue - horizontalPotValue) * radiansPerPotValue;
    	double kF = motorScalar * Math.cos(theta) / targetPotValue;
    	talonRotate.setF(kF);
    }
    
    private void setTalonPosition(double position) {
    	position = POT_ZERO - safetyTest(position);
    	talonRotate.set(position);    	
    }
    
    public double safetyTest(double position) {
    	if (position > raiseLimit) {
    		position = raiseLimit;
    	}
    	else if (position < lowerLimit) {
    		position = lowerLimit;
    	}
    	return position;
    }
    
    public boolean belowRaiseLimit() {
    	return (getPotValue() < raiseLimit);
    }
    
    public boolean aboveLowerLimit(){
    	return (getPotValue() > lowerLimit);
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
    
    public void setToCurrentPosition() {
    	setPosition(getPotValue());
    }  
    
    public boolean nearSetpoint() {
    	return (Math.abs(getPotValue() - potGoal) < tolerance);
    }
    
    public void initMotionProfile() {
    	talonRotate.clearMotionProfileTrajectories();
    	talonRotate.changeControlMode(CANTalon.TalonControlMode.MotionProfile);
    	talonRotate.set(CANTalon.SetValueMotionProfile.Disable.value);
    	talonRotate.clearMotionProfileHasUnderrun();
    }
    
    public void pushPoint(CANTalon.TrajectoryPoint point) {
    	talonRotate.pushMotionProfileTrajectory(point);
    }
    
    public MotionProfileStatus getMotionProfileStatus() {
    	MotionProfileStatus motionProfileStatus = new MotionProfileStatus();
    	talonRotate.getMotionProfileStatus(motionProfileStatus);
    	
    	return motionProfileStatus;
    }
    
    public void removeUnderrun() {
    	talonRotate.clearMotionProfileHasUnderrun();
    }
    
    public void enableMotionProfile() {
    	talonRotate.set(CANTalon.SetValueMotionProfile.Enable.value);
    }
    
    public void holdMotionProfile() {
    	talonRotate.set(CANTalon.SetValueMotionProfile.Hold.value);
    }
    
    public void disableMotionProfile() {
    	talonRotate.set(CANTalon.SetValueMotionProfile.Disable.value);
    }
    
}


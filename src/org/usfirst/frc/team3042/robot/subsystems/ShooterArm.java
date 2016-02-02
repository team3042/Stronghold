package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.ShooterArm_Stop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterArm extends Subsystem {
	
	private CANTalon talonRotate = new CANTalon(RobotMap.SHOOTER_ARM_TALON);

	private double rotateSpeed = .6;
	private double upperLimit = 180;
	private double lowerLimit = 70;
	
	public ShooterArm() {
		talonRotate.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShooterArm_Stop());
    }

    public void stop() {
    	set(0);
    }
    
    void set(double speed) {
    	talonRotate.changeControlMode(TalonControlMode.PercentVbus);
    	talonRotate.set(speed);
    }
    
    public void raise() {
    	if (getPotentiometerVal() < upperLimit) {
    		set(-rotateSpeed);
    	}
    	else {
    		set(0);
    	}
    }
    
    public void lower() {
    	if (getPotentiometerVal() > lowerLimit) {
    		set(rotateSpeed);
    	}
    	else {
    		set(0);
    	}
    }
    
    public void setPosition(double position) {
    	position = safetyTest(position);
    	
    	talonRotate.changeControlMode(TalonControlMode.Position);
    	talonRotate.set(position);
    }
    
    private double safetyTest(double position) {
    	if (position > upperLimit) {
    		position = upperLimit;
    	}
    	else if (position < lowerLimit) {
    		position = lowerLimit;
    	}
    	return position;
    }
    
    public double getPotentiometerVal() {
    	return talonRotate.getAnalogInRaw();
    }
}


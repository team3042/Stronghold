package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.ShooterArmStop;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 *
 */
public class ShooterArm extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private CANTalon talonRotate = new CANTalon(RobotMap.SHOOTER_ARM_TALON);

	Potentiometer pot = new AnalogPotentiometer(RobotMap.SHOOTER_ARM_POT, 1, 0);
	
	private double rotateSpeed = .6;
	private double upperLimit = 180;
	private double lowerLimit = 70;
	
	public ShooterArm() {
		talonRotate.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShooterArmStop());
    }
    
    public void raise() {
    	talonRotate.changeControlMode(TalonControlMode.PercentVbus);
    	//if (getPotentiometerVal() < upperLimit) {
    		talonRotate.set(-rotateSpeed);
    	//}
    	//else {
    	//	talonRotate.set(0);
    	//}
    }
    
    public void lower() {
    	talonRotate.changeControlMode(TalonControlMode.PercentVbus);
    	//if (getPotentiometerVal() > lowerLimit) {
    		talonRotate.set(rotateSpeed);
    	//}
    	//else {
    	//	talonRotate.set(0);
    	//}
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
    
    public void stop() {
    	talonRotate.changeControlMode(TalonControlMode.PercentVbus);
    	talonRotate.set(0);
    }
    
    public double getPotentiometerVal() {
    	return pot.get();
    }
}


package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.DriveTrainTankDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	CANTalon leftMotorFront = new CANTalon(RobotMap.DRIVETRAIN_TALON_LEFT_1);
	CANTalon leftMotorRear = new CANTalon(RobotMap.DRIVETRAIN_TALON_LEFT_2);
	CANTalon rightMotorFront = new CANTalon(RobotMap.DRIVETAIN_TALON_RIGHT_1);
	CANTalon rightMotorRear = new CANTalon(RobotMap.DRIVETRAIN_TALON_RIGHT_2);
	
	private double leftScale = 1, rightScale = -1;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveTrainTankDrive());
    }
    
    public void stop() {
    	leftMotorFront.set(0);
    	leftMotorRear.set(0);
    	rightMotorFront.set(0);
    	rightMotorRear.set(0);
    }
    
    public void setMotors(double left, double right) {
    	left *= leftScale;
    	right *= rightScale;
    	
    	left = safetyTest(left);
    	right = safetyTest(right);
    	
    	setLeftMotors(left);
    	setRightMotors(right);
    }
    
    public void setMotorsRaw(double left, double right) {
    	left = safetyTest(left);
    	right = safetyTest(right);
    	
    	setLeftMotors(left);
    	setRightMotors(right);		
	}
    
    private double safetyTest(double motorValue) {
        motorValue = (motorValue < -1) ? -1 : motorValue;
        motorValue = (motorValue > 1) ? 1 : motorValue;
        
        return motorValue;
    }
    
    private void setLeftMotors(double motorSpeed) {
    	leftMotorFront.set(motorSpeed);
    	leftMotorRear.set(motorSpeed);
    }
    
    private void setRightMotors(double motorSpeed) {
    	rightMotorFront.set(motorSpeed);
    	rightMotorRear.set(motorSpeed);
    }
    
    
}


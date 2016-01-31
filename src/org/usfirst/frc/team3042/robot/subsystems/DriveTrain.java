package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.DriveTrainTankDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	CANTalon leftMotorFront = new CANTalon(RobotMap.DRIVETRAIN_TALON_LEFT_1);
	CANTalon leftMotorRear = new CANTalon(RobotMap.DRIVETRAIN_TALON_LEFT_2);
	CANTalon rightMotorFront = new CANTalon(RobotMap.DRIVETAIN_TALON_RIGHT_1);
	CANTalon rightMotorRear = new CANTalon(RobotMap.DRIVETRAIN_TALON_RIGHT_2);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public DriveTrain() {
		leftMotorRear.changeControlMode(TalonControlMode.Follower);
    	leftMotorRear.set(leftMotorFront.getDeviceID());
    	rightMotorRear.changeControlMode(TalonControlMode.Follower);
    	rightMotorRear.set(rightMotorFront.getDeviceID());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveTrainTankDrive());
    }
    
    public void stop() {
    	rightMotorFront.changeControlMode(TalonControlMode.PercentVbus);
    	
    	leftMotorFront.set(0);
    	rightMotorFront.set(0);
    }
    
    public void setMotors(double left, double right) {
    	rightMotorFront.changeControlMode(TalonControlMode.PercentVbus);
    	
    	left = scaleLeft(left);
    	right = scaleRight(right);
    	
    	left = safetyTest(left);
    	right = safetyTest(right);
    	
    	leftMotorFront.set(left);
    	rightMotorFront.set(right);
    }
    
    public void setMotorsRaw(double left, double right) {
    	rightMotorFront.changeControlMode(TalonControlMode.PercentVbus);
    	
    	left = safetyTest(left);
    	right = safetyTest(right);
    	
    	leftMotorFront.set(left);
    	rightMotorFront.set(right);		
	}
    
    public void initMotionProfile() {
    	leftMotorFront.clearMotionProfileTrajectories();
    	leftMotorFront.changeControlMode(TalonControlMode.MotionProfile);
    	rightMotorFront.changeControlMode(TalonControlMode.Follower);
    	rightMotorFront.set(leftMotorFront.getDeviceID());
    }
    
    public void streamMotionProfile(CANTalon.TrajectoryPoint point) {
    	leftMotorFront.pushMotionProfileTrajectory(point);
    	leftMotorFront.processMotionProfileBuffer();
    }
    
    private double safetyTest(double motorValue) {
        motorValue = (motorValue < -1) ? -1 : motorValue;
        motorValue = (motorValue > 1) ? 1 : motorValue;
        
        return motorValue;
    }
    
    private double scaleLeft(double left) {
    	return left;
    }
    
    private double scaleRight(double right) {
    	return -right;
    }
    
    
}


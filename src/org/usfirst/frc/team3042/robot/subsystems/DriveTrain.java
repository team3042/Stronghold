package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.DriveTrain_TankDrive;

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
	
	//Set which of the motors has the encoder attached
	CANTalon leftEncMotor = leftMotorFront;
	CANTalon rightEncMotor = rightMotorFront;
	
	//Zero points for the encoders
	private int leftEncoderZero = 0, rightEncoderZero = 0;

	public DriveTrain() {
		//Put the rear motors in follower mode
		leftMotorRear.changeControlMode(TalonControlMode.Follower);
    	leftMotorRear.set(leftMotorFront.getDeviceID());
    	rightMotorRear.changeControlMode(TalonControlMode.Follower);
    	rightMotorRear.set(rightMotorFront.getDeviceID());
    	
    	//Set the direction of the motors
    	reverseLeft(false);
    	reverseRight(true);
    	
    	initEncoders();
	}
	
	void reverseLeft (boolean isReverse){
    	reverseMotor(leftMotorFront, isReverse);
    	reverseMotor(leftMotorRear, isReverse);
	}
	
	void reverseRight(boolean isReverse) {
    	reverseMotor(rightMotorFront, isReverse);
    	reverseMotor(rightMotorRear, isReverse);
	}
	
	void reverseMotor(CANTalon motor, boolean isReverse) {
		motor.reverseOutput(isReverse);
	}

	void initEncoders() {
		leftEncMotor.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		rightEncMotor.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);

		leftEncMotor.configEncoderCodesPerRev(1024);
		rightEncMotor.configEncoderCodesPerRev(1024);
		
		resetEncoders();
	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new DriveTrain_TankDrive());
    }
    
    public void stop() {
    	setMotorsRaw(0,0);
    }
    
    public void setMotors(double left, double right) {
    	left = scaleLeft(left);
    	right = scaleRight(right);
    	
    	setMotorsRaw(left, right);
    }
    
    public void setMotorsRaw(double left, double right) {
    	left = safetyTest(left);
    	right = safetyTest(right);
    	
    	rightMotorFront.changeControlMode(TalonControlMode.PercentVbus);
    	leftMotorFront.changeControlMode(TalonControlMode.PercentVbus);
    	
    	leftMotorFront.set(left);
    	rightMotorFront.set(right);		
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
    	return right;
    }

	public void resetEncoders() {
		leftEncoderZero = leftEncMotor.getEncPosition();
		rightEncoderZero = rightEncMotor.getEncPosition();
	}

	public int getLeftEncoder() {
		return leftEncMotor.getEncPosition() - leftEncoderZero;
	}
	
	public int getRightEncoder() {
		return rightEncMotor.getEncPosition() - rightEncoderZero;
	}

	public double getLeftSpeed() {
		return leftEncMotor.getSpeed();
	}    
	public double getRightSpeed() {
		return rightEncMotor.getSpeed();
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

}


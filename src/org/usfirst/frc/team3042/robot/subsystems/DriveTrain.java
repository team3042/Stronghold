package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.DriveTrain_TankDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.MotionProfileStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Notifier;
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
	
	//PIDF values
	double kP = 0, kI = 0, kD = 0, kF = 0;

	class PeriodicRunnable implements java.lang.Runnable {
		public void run() { 
			leftMotorFront.processMotionProfileBuffer();
			rightMotorFront.processMotionProfileBuffer();
		}
	}
	
	Notifier notifier = new Notifier (new PeriodicRunnable());
	
	public DriveTrain() {
		//Put the rear motors in follower mode
		leftMotorRear.changeControlMode(TalonControlMode.Follower);
    	leftMotorRear.set(leftMotorFront.getDeviceID());
    	rightMotorRear.changeControlMode(TalonControlMode.Follower);
    	rightMotorRear.set(rightMotorFront.getDeviceID());
    	
    	leftMotorFront.reverseOutput(false);
    	leftMotorFront.setInverted(false);
    	
    	rightMotorFront.setInverted(true);
    	rightMotorFront.reverseOutput(true);
    	initEncoders();
    	
    	//Starting talons processing motion profile
    	leftMotorFront.changeMotionControlFramePeriod(5);
    	rightMotorFront.changeMotionControlFramePeriod(5);
    	notifier.startPeriodic(0.005);
    	
    	//Initializing PIDF
    	leftMotorFront.setPID(kP, kI, kD);
    	rightMotorFront.setPID(kP,  kI,  kD);
    	leftMotorFront.setF(kF);
    	rightMotorFront.setF(kF);
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
    	
    	leftMotorFront.changeControlMode(TalonControlMode.PercentVbus);
    	rightMotorFront.changeControlMode(TalonControlMode.PercentVbus);
    	leftMotorFront.set(left);
    	rightMotorFront.set(right);		
	}
    
    private double safetyTest(double motorValue) {
        motorValue = (motorValue < -1) ? -1 : motorValue;
        motorValue = (motorValue > 1) ? 1 : motorValue;
        
        return motorValue;
    }
    
    public void setPosition(double left, double right) {
    	leftMotorFront.changeControlMode(TalonControlMode.Position);
    	rightMotorFront.changeControlMode(TalonControlMode.Position);
    	
    	left += leftEncoderZero;
    	right += rightEncoderZero;
    	
    	leftMotorFront.set(left);
    	rightMotorFront.set(right);
    }
    
    private double scaleLeft(double left) {
    	if(RobotMap.isSkoll) {
    		left = left;
    	}
    	else {
    		left = left;
    	}
    	return left;
    }
    
    private double scaleRight(double right) {
    	if(RobotMap.isSkoll) {
    		right = right;
    	}
    	else {
    		right = right;
    	}
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
	
	//Motion profile functions
    public void initMotionProfile() {
    	leftMotorFront.clearMotionProfileTrajectories();
    	rightMotorFront.clearMotionProfileTrajectories();
    	
    	leftMotorFront.changeControlMode(TalonControlMode.MotionProfile);
    	rightMotorFront.changeControlMode(TalonControlMode.MotionProfile);
    	leftMotorFront.set(CANTalon.SetValueMotionProfile.Disable.value);
    	rightMotorFront.set(CANTalon.SetValueMotionProfile.Disable.value);
    	
    	leftMotorFront.clearMotionProfileHasUnderrun();
    	rightMotorFront.clearMotionProfileHasUnderrun();
    }
    
    public void pushPoints(CANTalon.TrajectoryPoint leftPoint, CANTalon.TrajectoryPoint rightPoint) {
    	leftMotorFront.pushMotionProfileTrajectory(leftPoint);
    	rightMotorFront.pushMotionProfileTrajectory(rightPoint);
    }
    
    public MotionProfileStatus[] getMotionProfileStatus() {
    	MotionProfileStatus[] motionProfileStatus = new MotionProfileStatus[2];
		leftMotorFront.getMotionProfileStatus(motionProfileStatus[0]);
		rightMotorFront.getMotionProfileStatus(motionProfileStatus[1]);
		
		return motionProfileStatus;
    }
    
    //Removing flag hasUnderrun if it has been logged
    public void removeUnderrunLeft() {
    	leftMotorFront.clearMotionProfileHasUnderrun();
    }
    
    public void removeUnderrunRight() {
    	rightMotorFront.clearMotionProfileHasUnderrun();
    }
    
    public void enableMotionProfile() {
    	leftMotorFront.set(CANTalon.SetValueMotionProfile.Enable.value);
    	rightMotorFront.set(CANTalon.SetValueMotionProfile.Enable.value);
    }
    
    public void holdMotionProfile() {
    	leftMotorFront.set(CANTalon.SetValueMotionProfile.Hold.value);
    	rightMotorFront.set(CANTalon.SetValueMotionProfile.Hold.value);
    }
    
    public void disableMotionProfile() {
    	leftMotorFront.set(CANTalon.SetValueMotionProfile.Disable.value);
    	rightMotorFront.set(CANTalon.SetValueMotionProfile.Disable.value);
    }

}


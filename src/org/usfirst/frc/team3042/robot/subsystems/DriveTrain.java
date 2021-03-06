package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.DriveTrain_TankDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.MotionProfileStatus;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	public CANTalon leftMotorFront = new CANTalon(RobotMap.DRIVETRAIN_TALON_LEFT_1);
	CANTalon leftMotorRear = new CANTalon(RobotMap.DRIVETRAIN_TALON_LEFT_2);
	CANTalon rightMotorFront = new CANTalon(RobotMap.DRIVETAIN_TALON_RIGHT_1);
	public CANTalon rightMotorRear = new CANTalon(RobotMap.DRIVETRAIN_TALON_RIGHT_2);
	
	//Set which of the motors has the encoder attached
	CANTalon leftEncMotor = leftMotorFront;
	CANTalon rightEncMotor = rightMotorFront;
	
	//Should be ADXRS450_Gyro
	Gyro gyro = new ADXRS450_Gyro();
	
	//Zero points for the encoders
	private int leftEncoderZero = 0, rightEncoderZero = 0;
	public int encCounts = (RobotMap.isSkoll) ? 360 : 360;
	private boolean leftReverseEnc = (RobotMap.isSkoll) ? true : false;
	private boolean rightReverseEnc = (RobotMap.isSkoll) ? true : true;
	private int leftEncSign = (RobotMap.isSkoll) ? -1 : 1;
	private int rightEncSign = (RobotMap.isSkoll) ? -1 : -1;
	
	//PIDF values
	public double kP = 1, kI = 0, kD = 0;
	public double kF = (RobotMap.isSkoll) ? 0.9 : 0.9;
	double pPos = 1.3, iPos = 0.015, fPos = 0;
	int iZone = 150;
	
	//Values for checking if near setpoint
	double leftSetpoint, rightSetpoint;
	double tolerance = 4.0 / encCounts;
	
	//Creating thread to make talon process motion profile buffer when points are available in upper buffer
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
    	
    	gyro.reset();
    	
    	//Starting talons processing motion profile
    	leftMotorFront.changeMotionControlFramePeriod(5);
    	rightMotorFront.changeMotionControlFramePeriod(5);
    	notifier.startPeriodic(0.005);
    	
    	//Initializing PIDF
    	leftMotorFront.setProfile(1);
    	rightMotorFront.setProfile(1);
    	leftMotorFront.setPID(pPos, iPos, kD);
    	rightMotorFront.setPID(pPos, iPos, kD);
    	leftMotorFront.setIZone(iZone);
    	rightMotorFront.setIZone(iZone);
    	leftMotorFront.setF(fPos);
    	rightMotorFront.setF(fPos);
    	
    	leftMotorFront.setProfile(0);
    	rightMotorFront.setProfile(0);
    	leftMotorFront.setPID(kP, kI, kD);
    	rightMotorFront.setPID(kP, kI, kD);
    	leftMotorFront.setF(kF);
    	rightMotorFront.setF(kF);
    	
	}
	
	public void tempReverseLeft() {
    	//leftMotorFront.setInverted(true);
    	//leftMotorFront.reverseOutput(true);
	}
	
	public void tempUnreverseLeft() {
    	//leftMotorFront.setInverted(false);
    	//leftMotorFront.reverseOutput(false);
	}
	
	void initEncoders() {
		leftEncMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightEncMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		leftEncMotor.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		rightEncMotor.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);

		leftEncMotor.configEncoderCodesPerRev(encCounts);
		rightEncMotor.configEncoderCodesPerRev(encCounts);
		
		leftEncMotor.reverseSensor(leftReverseEnc);
		rightEncMotor.reverseSensor(rightReverseEnc);
		
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
    
    public void offsetPosition(double left, double right) {
    	left += leftMotorFront.getPosition();
    	right += rightMotorFront.getPosition();
    	
    	leftSetpoint = left;
    	rightSetpoint = right;
    	
    	leftMotorFront.setProfile(1);
    	rightMotorFront.setProfile(1);
    	  	
    	leftMotorFront.changeControlMode(TalonControlMode.Position);
    	rightMotorFront.changeControlMode(TalonControlMode.Position);
    	    	
    	leftMotorFront.set(left);
    	rightMotorFront.set(right);
    }
    
    public boolean nearSetpoint() {
    	double currentLeftPosition = leftMotorFront.getPosition();
    	boolean nearLeft = Math.abs(leftSetpoint - currentLeftPosition) < tolerance;
    	
    	double currentRightPosition = rightMotorFront.getPosition();
    	boolean nearRight = Math.abs(rightSetpoint - currentRightPosition) < tolerance;
    	
    	return nearLeft && nearRight;
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
		return leftEncSign * (leftEncMotor.getEncPosition() - leftEncoderZero);
	}
	
	public int getRightEncoder() {
		return rightEncSign * (rightEncMotor.getEncPosition() - rightEncoderZero);
	}

	public double getLeftSpeed() {
		return leftEncMotor.getSpeed();
	} 
	
	public double getRightSpeed() {
		return rightEncMotor.getSpeed();
	}
	
	public boolean isLeftEncPresent() {
		return !(leftEncMotor.isSensorPresent(FeedbackDevice.QuadEncoder) == FeedbackDeviceStatus.FeedbackStatusPresent);
	}
	
	public boolean isRightEncPresent() {
		return !(rightEncMotor.isSensorPresent(FeedbackDevice.QuadEncoder) == FeedbackDeviceStatus.FeedbackStatusPresent);
	}
	
	public void resetGyro() {
		gyro.reset();
	}
	
	public double getGyro() {
		return gyro.getAngle();
	}
	
	//Motion profile functions
    public void initMotionProfile() {
    	
    	leftMotorFront.clearMotionProfileTrajectories();
    	rightMotorFront.clearMotionProfileTrajectories();
    	
    	leftMotorFront.setProfile(0);
    	rightMotorFront.setProfile(0);
    	
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
    	motionProfileStatus[0] = new MotionProfileStatus();
    	motionProfileStatus[1] = new MotionProfileStatus();
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


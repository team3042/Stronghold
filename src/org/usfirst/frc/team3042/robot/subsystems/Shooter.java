package org.usfirst.frc.team3042.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.Shooter_Stop;

/**
 *
 */
public class Shooter extends Subsystem {
	
	public CANTalon talonLeft = new CANTalon(RobotMap.SHOOTER_TALON_LEFT);
	public CANTalon talonRight = new CANTalon(RobotMap.SHOOTER_TALON_RIGHT);
	
	//Set starting points for the encoders
	int talonLeftZero = 0, talonRightZero = 0;
	
	double leftShootSpeed = 3360, rightShootSpeed = 2750;
	double batterShootSpeed = 2750, intakeSpeed = 2000;
	double toleranceRPM = 150;
	
	//Closed-Loop PIDF values
	double P = 0.01, I = 0, D = 0;
	public double leftF = (RobotMap.isSkoll)? 0.024: 0.025, 
			rightF = (RobotMap.isSkoll)? 0.0264: 0.0263;

	public Shooter() {
		//Setting Talon settings
		talonLeft.reverseOutput(true);
		talonRight.reverseOutput(false);
		talonLeft.setInverted(true);
		talonRight.setInverted(false);
		
			
		initEncoders();		
		setPIDF();
	}
	
	public void setPIDF() {
		talonLeft.setPID(P, I, D);
		talonRight.setPID(P, I, D);
		
		talonLeft.setF(leftF);
		talonRight.setF(rightF);
	}
	
	void initEncoders() {
		talonLeft.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		talonRight.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);

		talonLeft.configEncoderCodesPerRev(1024);
		talonRight.configEncoderCodesPerRev(1024);
		
		talonLeft.reverseSensor(true);
		talonRight.reverseSensor(true);

		encoderReset();
	}

    public void initDefaultCommand() {    	
        // Set the default command for a subsystem here.
    	setDefaultCommand(new Shooter_Stop());
    }
    
    public void stop() {
    	setSpeed (0);
    }
     
    public void setSpeed(double speed) { 
    	talonLeft.changeControlMode(TalonControlMode.PercentVbus);
    	talonRight.changeControlMode(TalonControlMode.PercentVbus);
    	
    	talonLeft.set(speed);
    	talonRight.set(speed);
    }
    
    public void spinToShoot() {
    	setRPM(leftShootSpeed, rightShootSpeed);
    }
    
    public void spinToBatterShoot() {
    	setRPM(batterShootSpeed, batterShootSpeed);
    }
    
    public void spinToIntake() {
    	setRPM(-intakeSpeed, -intakeSpeed);
    }
    
    public boolean readyToShoot() {
    	return getEncoderRPMRight() > (rightShootSpeed - toleranceRPM) && 
    			getEncoderRPMLeft() > (leftShootSpeed - toleranceRPM);
    }
    
    public boolean readyToBatterShoot() {
    	return getEncoderRPMRight() > (batterShootSpeed - toleranceRPM);
    }
    
    //Setting each flywheel to a target speed using PIDF through Talons
    public void setRPM(double leftSpeed, double rightSpeed) {
    	talonLeft.changeControlMode(TalonControlMode.Speed);
    	talonRight.changeControlMode(TalonControlMode.Speed);

    	talonLeft.set(leftSpeed);
    	talonRight.set(rightSpeed);
    }
    
    //Getting speed in RPM
    public double getEncoderRPMLeft() {
    	return -talonLeft.getSpeed();
    }
    public double getEncoderRPMRight() {
    	return talonRight.getSpeed();
    }
    
    //Getting encoder position and zeroing
    public int getEncoderValLeft() {
    	return talonLeft.getEncPosition() - talonLeftZero;
    }
    public int getEncoderValRight() {
    	return talonRight.getEncPosition() - talonRightZero;
    }
    
    //Finding starting point of each encoder
    public void encoderReset() {
    	talonLeftZero = talonLeft.getEncPosition();
    	talonRightZero = talonRight.getEncPosition();
    }
    
}


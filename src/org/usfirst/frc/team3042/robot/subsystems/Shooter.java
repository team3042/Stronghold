package org.usfirst.frc.team3042.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.ShooterStop;

/**
 *
 */
public class Shooter extends Subsystem {
	
	public CANTalon talonLeft = new CANTalon(RobotMap.SHOOTER_TALON_LEFT);
	public CANTalon talonRight = new CANTalon(RobotMap.SHOOTER_TALON_RIGHT);
		
	
	Potentiometer pot = new AnalogPotentiometer(RobotMap.SHOOTER_POT_ARM, 1, 0);
	
	int talonLeftZero, talonRightZero = 0;
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public Shooter() {
		//Setting Talon settings
		/*
		talonLeft.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		talonLeft.configEncoderCodesPerRev(1024);
		talonLeft.reverseOutput(true);
		talonRight.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		talonRight.configEncoderCodesPerRev(1024);
		talonRight.reverseOutput(false);
		encoderReset();
		*/
	
	}

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ShooterStop());

    }
    
    public void setSpeed(double speed) { 
    	talonLeft.set(speed);
    	talonRight.set(speed);
    }
    
    public void setRPM(double speed) {
    	talonLeft.setF(SmartDashboard.getNumber("F-Gain"));
    	talonLeft.setP(.01);
    	talonLeft.changeControlMode(TalonControlMode.Speed);
    	talonLeft.set(speed);
    }
    
    //Setting each flywheel to a target speed using PIDF through Talons
    public void setRPMTwoWheel(double speed) {
    	//talonLeft.setF(SmartDashboard.getNumber("F-Gain Left"));
    	//talonLeft.setP(.01);
    	//talonLeft.changeControlMode(TalonControlMode.Speed);
    	talonLeft.set(-speed);
    	//talonRight.setF(SmartDashboard.getNumber("F-Gain Right"));
    	//talonRight.setP(.01);
    	//talonRight.changeControlMode(TalonControlMode.Speed);
    	talonRight.set(speed);
    }
    
    //Changing Talons back to standard -1 to 1 control and stopping
    public void stop() {
    	talonLeft.changeControlMode(TalonControlMode.PercentVbus);
    	talonRight.changeControlMode(TalonControlMode.PercentVbus);
    	talonLeft.set(0);
    	talonRight.set(0);
    }
    
    
    
    //Getting speed in RPM
    public double getEncoderRPMLeft() {
    	return talonLeft.getSpeed();
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
    
    public double getPotentiometerVal() {
    	return pot.get();
    }
    
}


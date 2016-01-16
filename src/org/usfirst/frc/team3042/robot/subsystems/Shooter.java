package org.usfirst.frc.team3042.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.ShooterStop;

/**
 *
 */
public class Shooter extends Subsystem {
	
	CANTalon talon1 = new CANTalon(RobotMap.SHOOTER_TALON_1);
	CANTalon talon2 = new CANTalon(RobotMap.SHOOTER_TALON_2);
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public Shooter() {
		//Setting Talon settings
		talon1.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		talon1.configEncoderCodesPerRev(1024);
		talon1.reverseOutput(true);
		talon2.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		talon2.configEncoderCodesPerRev(1024);
		talon2.reverseOutput(false);
		encoderReset();
	
	}

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ShooterStop());

    }
    
    public void shoot(double speed) { 
    	talon1.set(speed);
    	
    }
    
    public void stop() {
    	talon1.changeControlMode(TalonControlMode.PercentVbus);
    	talon2.changeControlMode(TalonControlMode.PercentVbus);
    	talon1.set(0);
    	talon2.set(0);
    }
    
    public void coast() {
    	this.stop();
    }
    
    //Getting speed in native units per 100ms and converting to RPM
    public double getEncoderRPM() {
    	return talon1.getSpeed();
    }
    
    public double getEncoderRPMTwo() {
    	return talon2.getSpeed();
    }
    
    
    public int getEncoderVal() {
    	return talon1.getEncPosition();
    }
    
    public int getEncoderValTwo() {
    	return talon2.getEncPosition();
    }
    
    public void encoderReset() {
    	talon1.setPosition(0);
    	talon2.setPosition(0);
    }
    
    public void setRPM(double speed) {
    	talon1.setF(SmartDashboard.getNumber("F-Gain"));
    	talon1.setP(.01);
    	talon1.changeControlMode(TalonControlMode.Speed);
    	talon1.set(speed);
    }

    public void setRPMTwoWheel(double speed) {
    	talon1.setF(SmartDashboard.getNumber("F-Gain"));
    	talon1.setP(.01);
    	talon1.changeControlMode(TalonControlMode.Speed);
    	talon1.set(speed);
    	talon2.setF(SmartDashboard.getNumber("F-Gain"));
    	talon2.setP(.01);
    	talon2.changeControlMode(TalonControlMode.Speed);
    	talon2.set(speed);
    }
}


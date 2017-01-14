package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.FileIO;
import org.usfirst.frc.team3042.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.MotionProfileStatus;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto_Play extends Command {
	
	FileIO fileIO = new FileIO();
	String dir = "/home/lvuser/autonomous/";
	
	boolean useDrivetrain, useSnout;
	String filename;
	
	MotionProfileStatus[] driveTrainStatus;
	MotionProfileStatus snoutStatus;

    public Auto_Play(String filename, boolean useDrivetrain, boolean useSnout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.filename = filename;
    	this.useDrivetrain = useDrivetrain;
    	this.useSnout = useSnout;
    	
    	if(useDrivetrain) {
    		requires(Robot.driveTrain);
    	}
    	if(useSnout) {
    		requires(Robot.snout);
    	}
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	
    	fileIO.openFile(dir, filename);
    	
    	if(useDrivetrain) {
    		Robot.driveTrain.initMotionProfile();
    	}
    	if(useSnout) {
    		Robot.snout.initMotionProfile();
    	}
    	
    	boolean isFirstPoint = true;
    	while(fileIO.hasNextDouble()) {
    		int dT = (int) fileIO.readNextDouble();
    		
    		//Setting up drivetrain trajectory points
    		CANTalon.TrajectoryPoint currentLeftPoint = new CANTalon.TrajectoryPoint();
    		CANTalon.TrajectoryPoint currentRightPoint = new CANTalon.TrajectoryPoint();
    		
    		currentLeftPoint.position = fileIO.readNextDouble();
    		currentLeftPoint.velocity = fileIO.readNextDouble();
    		currentLeftPoint.timeDurMs = dT;
    		currentLeftPoint.profileSlotSelect = 0;
    		currentLeftPoint.zeroPos = isFirstPoint;
    		
    		currentRightPoint.position = fileIO.readNextDouble();
    		currentRightPoint.velocity = fileIO.readNextDouble();
    		currentRightPoint.timeDurMs = dT;
    		currentRightPoint.profileSlotSelect = 0;
    		currentRightPoint.zeroPos = isFirstPoint;
    		
    		//Setting up snout trajectory point
    		CANTalon.TrajectoryPoint currentSnoutPoint = new CANTalon.TrajectoryPoint();
    		
    		currentSnoutPoint.position = Robot.snout.POT_ZERO - fileIO.readNextDouble();
    		currentSnoutPoint.velocity = fileIO.readNextDouble();
    		currentSnoutPoint.timeDurMs = dT;
    		currentSnoutPoint.profileSlotSelect = 0;
    		currentSnoutPoint.zeroPos = isFirstPoint;
    		
    		boolean isLastPoint = !fileIO.hasNextDouble();
    		currentLeftPoint.isLastPoint = isLastPoint;
    		currentRightPoint.isLastPoint = isLastPoint;
    		currentSnoutPoint.isLastPoint = isLastPoint;
    		
    		if(useDrivetrain) {
    			Robot.driveTrain.pushPoints(currentLeftPoint, currentRightPoint);
    		}
    		if(useSnout) {
    			Robot.snout.pushPoint(currentSnoutPoint);
    		}
    		
    		isFirstPoint = false;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(useDrivetrain) {
    		driveTrainStatus = Robot.driveTrain.getMotionProfileStatus();
    	
    		if(driveTrainStatus[0].btmBufferCnt > 5) {
    			Robot.driveTrain.enableMotionProfile();
    		}
    	
    		if(driveTrainStatus[0].hasUnderrun) {
    			Robot.logger.log("Left Underrun", 2);
    			Robot.driveTrain.removeUnderrunLeft();
    		}
    		if(driveTrainStatus[1].hasUnderrun) {
    			Robot.logger.log("Right Underrun", 2);
    			Robot.driveTrain.removeUnderrunRight();
    		}
    	}
    	
    	if(useSnout) {
    		snoutStatus = Robot.snout.getMotionProfileStatus();
    		
    		if(snoutStatus.btmBufferCnt > 5) {
    			Robot.snout.enableMotionProfile();
    		}
    		
    		if(snoutStatus.hasUnderrun) {
    			Robot.logger.log("Snout Underrun", 2);
    			Robot.snout.removeUnderrun();
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    	
    	fileIO.closeFile();
    	if(useDrivetrain) {
    		Robot.driveTrain.disableMotionProfile();
    	}
    	if(useSnout) {
    		Robot.snout.disableMotionProfile();
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupt", 1);
    	
    	fileIO.closeFile();
    	if(useDrivetrain) {
    		Robot.driveTrain.disableMotionProfile();
    	}
    	if(useSnout) {
    		Robot.snout.disableMotionProfile();
    	}
    }
}

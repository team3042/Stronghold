package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.FileIO;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Auto_Record extends Command {
	
	Timer timer = new Timer();
	FileIO fileIO = new FileIO();
	
	String dir = "/home/lvuser/autonomous/";
	double dT, oldTime = 0;
	
	//Drive train state variables
	double oldLeftDrive, oldRightDrive, currentLeftDrive, currentRightDrive;
	
	//Snout state variables
	double oldSnout, currentSnout;
	
	String currentState;

    public Auto_Record() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	
    	timer.reset();
    	timer.start();
    	
    	String fileName = SmartDashboard.getString("Auto Record Filename") + ".csv";
    	fileIO.openFile(dir, fileName);
    	
    	Robot.driveTrain.resetEncoders();
    	
    	//Finding intitial encoder values for calculating velocity
    	oldLeftDrive = Robot.driveTrain.getLeftEncoder();
    	oldRightDrive = Robot.driveTrain.getRightEncoder();
    	oldSnout = Robot.snout.getPotValue();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	dT = oldTime - timer.get();
    	oldTime += dT;
    	currentState = dT * 1000 + ", "; 
    	
    	//Recording drivetrain position and velocity
    	currentLeftDrive = Robot.driveTrain.getLeftEncoder();
    	currentState += currentLeftDrive + ", ";
    	currentState += (currentLeftDrive - oldLeftDrive) / dT + ", ";
    	oldLeftDrive = currentLeftDrive;
   		currentRightDrive = Robot.driveTrain.getRightEncoder();
    	currentState += currentRightDrive + ", ";
    	currentState += (currentRightDrive - oldRightDrive) / dT + ", ";
    	oldRightDrive = currentRightDrive;
    	
    	//Recording snout position and velocity
    	currentSnout = Robot.snout.getPotValue();
    	currentState += currentSnout + ", ";
    	currentState += (currentSnout - oldSnout) / dT + "";
    	oldSnout = currentSnout;
    	
    	
    	fileIO.writeToFile(currentState);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    	
    	fileIO.closeFile();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupt", 1);
    	
    	fileIO.closeFile();
    }
}

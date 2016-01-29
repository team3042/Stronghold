package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.FileIO;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrainCalibration extends Command {
	 //The time until we set motors to zero speed
    private double timeUntilMotorStop;
    
    //The time until the command finishes
    //Allows time for robot to come to rest after motors are stopped
    private double timeUntilCommandStop;
    
    //The alloted time in seconds for the robot to come to rest 
    //after the motors shut off
    private final double decelerationTime = 2.0;
    
    //Tracks whether the motors are set to move, or are stopped
    private boolean motorsEngaged;
    
    //Directory where calibration files will be stored
    private String dir = "Calibration/";
    
    private final Timer timer = new Timer();
    private final FileIO fileIO = new FileIO();
    
    public DriveTrainCalibration() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
       // Robot.driveTrain.resetEncoders();
        
        Robot.logger.log("Initialize", 1);
        
        timer.reset();
        timer.start();
        
        fileIO.openFile(dir, SmartDashboard.getString("Calibration File Name"));
        
        //Creating header
        
        fileIO.writeToFile(Double.toString(SmartDashboard.getNumber("Calibration Motor Speed")));
        fileIO.writeToFile("Time\tLeft\tRight\tLeft Speed\tRight Speed");
        
        //Determine the time to stop motors and time to stop command
        timeUntilMotorStop = (float)SmartDashboard.getNumber("Calibration Length In Seconds");
        timeUntilCommandStop =timeUntilMotorStop + decelerationTime;

        //Set the drivetrain to these speeds
        Robot.driveTrain.setMotors(
                SmartDashboard.getNumber("Calibration Motor Speed"),
                SmartDashboard.getNumber("Calibration Motor Speed"));
        motorsEngaged = true;
    }

    protected void execute() {   
        outputCalibrationValuesToFile();
        if (motorsEngaged && (timer.get() >= timeUntilMotorStop)) {
            Robot.driveTrain.setMotorsRaw(0.0, 0.0);
            motorsEngaged = false;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= timeUntilCommandStop;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.logger.log("End", 1);
        timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.logger.log("Interupt", 1);
        timer.stop();
    }
    
    private void outputCalibrationValuesToFile(){
    	/*
        String completeOutPut;
        
        String encoderValueLeft = Double.toString(driveTrain.getLeftEncoder());
        String encoderValueRight = Double.toString(driveTrain.getRightEncoder());
        
        String encoderSpeedLeft = Double.toString(driveTrain.getLeftSpeed());
        String encoderSpeedRight = Double.toString(driveTrain.getRightSpeed());
        
        SmartDashboard.putNumber("Right encoder", driveTrain.getRightEncoder());
        SmartDashboard.putNumber("Left encoder", driveTrain.getLeftEncoder());
        
        completeOutPut = timer.get()+"\t"+encoderValueLeft+"\t"+encoderValueRight+
                "\t"+encoderSpeedLeft+"\t"+encoderSpeedRight;
        
        fileIO.writeToFile(completeOutPut);
        */
    }
}

package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.FileIO;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain_Calibration extends Command {
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
    private String dir = "/home/lvuser/Calibration/";
    
    private final Timer timer = new Timer();
    private final FileIO file = new FileIO();
    
    public DriveTrain_Calibration() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.logger.log("Initialize", 1);

        Robot.driveTrain.resetEncoders();
        timer.reset();
        timer.start();
        
        //Read from the SmartDashboard
        String filename = SmartDashboard.getString("Calibration File Name");
        double speed = SmartDashboard.getNumber("Calibration Motor Speed");
        timeUntilMotorStop = SmartDashboard.getNumber("Calibration Length In Seconds");
        timeUntilCommandStop =timeUntilMotorStop + decelerationTime;        
        
        //Open file and create header
        file.openFile(dir, filename);
        file.writeToFile("Speed = " + Double.toString(speed));
        file.writeToFile("Time\tLeft\tRight\tLeft Speed\tRight Speed");
        
        //Set the drivetrain to these speeds
        Robot.driveTrain.setMotors(speed, speed);
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
        String completeOutPut;
        
        String encoderValueLeft = Double.toString(Robot.driveTrain.getLeftEncoder());
        String encoderValueRight = Double.toString(Robot.driveTrain.getRightEncoder());
        
        String encoderSpeedLeft = Double.toString(Robot.driveTrain.getLeftSpeed());
        String encoderSpeedRight = Double.toString(Robot.driveTrain.getRightSpeed());
        
        SmartDashboard.putNumber("Right encoder", Robot.driveTrain.getRightEncoder());
        SmartDashboard.putNumber("Left encoder", Robot.driveTrain.getLeftEncoder());
        
        completeOutPut = timer.get()+"\t"+encoderValueLeft+"\t"+encoderValueRight+
                "\t"+encoderSpeedLeft+"\t"+encoderSpeedRight;
        
        file.writeToFile(completeOutPut);
    }
}

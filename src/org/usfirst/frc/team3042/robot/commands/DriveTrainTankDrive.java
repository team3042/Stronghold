package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTrainTankDrive extends Command {

	//Scale the joystick values to restrict maximum speed
    private final double speedScale = 0.75;
    Timer time = new Timer();
    
    //Inertial dampening
    final int LEFT = 0;
    final int RIGHT = 1;
    double[] oldTime = new double[] {0, 0};
    double[] currentPower = new double[] {0,0};
    double maxAccel = 1.0; //motor power per second
	
    public DriveTrainTankDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	Robot.driveTrain.setMotors(0, 0);
    	time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftPower = -Robot.oi.joystickLeft.getY() * speedScale;
        double rightPower = -Robot.oi.joystickRight.getY() * speedScale;
        
        restrictAccel(leftPower, LEFT);
        restrictAccel(rightPower, RIGHT);
        
        if(Robot.oi.lTrigger.get()){
            rightPower = leftPower;
        }
        else if (Robot.oi.rTrigger.get()){
            leftPower = rightPower;
        }
        
        Robot.driveTrain.setMotors(leftPower, rightPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.logger.log("End", 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.logger.log("Interrupt", 1);
    }
    
    private double restrictAccel (double goalValue, int SIDE) {
        double currentTime = time.get();
        double dt = currentTime - oldTime[SIDE];
        oldTime[SIDE] = currentTime;
        
        double maxDSpeed = maxAccel * dt;
        maxDSpeed *= (goalValue >= currentPower[SIDE])? 1 : -1;
         
        currentPower[SIDE] = (Math.abs(maxDSpeed) > Math.abs(goalValue - currentPower[SIDE]))? 
                goalValue : maxDSpeed + currentPower[SIDE];
        return currentPower[SIDE];
    }
}

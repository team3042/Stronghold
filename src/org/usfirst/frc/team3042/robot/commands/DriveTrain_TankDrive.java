package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain_TankDrive extends Command {

	//Scale the joystick values to restrict maximum speed
    private final double speedScale = 1.0;
    
    //Inertial dampening
    final int LEFT = 0, RIGHT = 1;
    Timer time = new Timer();
    double[] oldTime = new double[] {0, 0};
    double[] currentPower = new double[] {0,0};
    double maxAccel = 4; //motor power per second
	
    public DriveTrain_TankDrive() {
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
        
        leftPower = restrictAccel(leftPower, LEFT);
        rightPower = restrictAccel(rightPower, RIGHT);
        
        if (Robot.oi.left_1.get()){
            rightPower = leftPower;
        }
        else if (Robot.oi.right_1.get()){
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

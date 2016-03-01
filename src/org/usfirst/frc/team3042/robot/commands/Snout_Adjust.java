package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Snout_Adjust extends Command {
	
	Timer timer = new Timer();
	double timeout = 2;
	
	double[][] lookUpTable = new double[][]{
		//Currently in pot values, these are temporary fillers.
			{5.2, 340},
			{5.42, 301},
			{5.7, 274},
			{6.5, 248},
			{7.4, 230},
			{8.35, 218},
			{9.45, 210}};
			
	static final double maxDist = 9.45, minDist = 5.2;
	static double potOffset = 20;
	static double tolerance = 5;
	double potGoal;

	public Snout_Adjust() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.snout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	double distance = Robot.camera.getDistToTarget();
    	
		potGoal = Robot.snout.getPotentiometerVal();

    	if ((distance < maxDist) && (distance > minDist)) {
        	int i = 1;
    		while (i < lookUpTable.length){
    			if (lookUpTable[i][0] > distance) {
    				potGoal = lookUpTable[i-1][1] + 
    						(distance - lookUpTable[i-1][0]) *
    	    				(lookUpTable[i][1] - lookUpTable[i-1][1]) /
    	    				(lookUpTable[i][0] - lookUpTable[i-1][0]);
    				i = lookUpTable.length;
    			}
    			i++;
    		}
    	}
    	Robot.logger.log("distance = " + distance, 3);
    	Robot.logger.log("potGoal = " + potGoal, 3);
    	SmartDashboard.putNumber("Target Pot Value", potGoal);
    	
    	Robot.snout.setPosition(potGoal + potOffset);
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Robot.snout.getPotentiometerVal() - potGoal) < tolerance || 
        		timer.get() > timeout;
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
}

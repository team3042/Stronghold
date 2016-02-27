package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Snout_Adjust extends Command {
	
	double[][] lookUpTable = new double[][]{
		//Currently in pot values, these are temporary fillers.
			{7.9, 345},
			{8.25, 330},
			{8.5, 322},
			{8.9, 307},
			{9.5, 292},
			{10.1, 280},
			{11.3, 278},
			{11.65, 276},
			{13, 276}};
			
	static final double maxDist = 13, minDist = 7.9;
	double potGoal;

	public Snout_Adjust() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shooterArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	double distance = Robot.camera.getDistToTarget();
    	
		potGoal = Robot.shooterArm.getPotentiometerVal();

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
    	
    	Robot.shooterArm.setPosition(potGoal);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
}

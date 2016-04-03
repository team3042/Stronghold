package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Snout_Adjust extends Command {
		
	//Calibrated lookup tables for shooting over the front and back of the robot
	//Camera Distance, Pot Value
	double[][] forwardLookUpTable = new double[][]{
		{54, 335},
		{61, 318},
		{71, 290},
		{81,269},
		{95, 252},
		{105, 249},
		{117, 241},
		{132, 232},
		{141, 225},
		{156, 217},
		{174, 215},
		{183, 215},
		{189, 205}};
			
	double[][] backwardLookUpTable = new double[][]{
		{62, 585},
		{72, 594},
		{81, 600},
		{95, 610},
		{103, 620},
		{117, 630},
		{132, 532},
		{145, 634},
		{156, 641},
		{169, 640},
		{178, 640}};
	
			
	static final double maxDist = 189, minDist = 54;
	double potGoal;

	public Snout_Adjust() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.snout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	double distance = 1.1 * Robot.camera.getDistToTarget();
    	
		potGoal = Robot.snout.getPotValue();
		
		double[][] lookUpTable = (Robot.snout.isBackwards())? backwardLookUpTable : forwardLookUpTable;

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
    	
    	Robot.snout.setPosition(potGoal);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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

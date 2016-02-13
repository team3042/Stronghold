package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterArm_Adjust extends Command {
	
	double[][] lookUpTable = new double[][]{
		//Currently in pot values, these are temporary fillers.
			{3,350},
			{4,375},
			{5,400},
			{6,425},
			{7,450},
			{8,475},
			{9, 546},// this value is tested.
			{10,525},
			{11,550},
			{12,575}};
			
	static final double maxDist = 12, minDist = 3;
	double potGoal;
	double tolerance = 2;
			
    public ShooterArm_Adjust() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.shooterArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.logger.log("Initialize", 1);
    	//double distance = Robot.camera.getDistToTargetInFeet();
    	double distance = SmartDashboard.getNumber("Camera Distance");

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
    	Robot.logger.log("potGoal = " + potGoal, 3);
    	Robot.shooterArm.setPosition(potGoal);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double diff = Robot.shooterArm.getPotentiometerVal() - potGoal;
    	Robot.logger.log("pot-goal = " + diff, 3);
        return Math.abs(diff) < tolerance;
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

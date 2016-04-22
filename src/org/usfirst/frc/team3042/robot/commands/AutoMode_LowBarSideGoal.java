package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMode_LowBarSideGoal extends CommandGroup {
    
    public  AutoMode_LowBarSideGoal(int defensePosition) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	addParallel(new Snout_Pickup());
    	addSequential(new Auto_FollowTrajectory(AutoTrajectory_LowBarSideGoal.getLeftTrajectory(), 
    			AutoTrajectory_LowBarSideGoal.getRightTrajectory(), true));
    	
    	//Setting snout to setting for position 1, low bar
    	addParallel(new Auto_SetSnoutPosition(1)); 
    	addSequential(new Auto_Drive(Auto_Drive.AutoType.TURN_LEFT, 2.62, 2, 0));
    	if(defensePosition != 0) {
    		addSequential(new Auto_AimShoot());
    	}
    	
    	addSequential(new Snout_Storage());
    }
}

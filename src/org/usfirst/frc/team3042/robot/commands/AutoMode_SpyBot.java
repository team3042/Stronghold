package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMode_SpyBot extends CommandGroup {
    
    public  AutoMode_SpyBot() {
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
    	
    	addParallel(new Snout_AutoShootPosition());
    	addSequential(new Auto_FollowTrajectory(AutoTrajectory_SpyBot.getLeftTrajectory(), 
    			AutoTrajectory_SpyBot.getRightTrajectory(), false));
    	addSequential(new Auto_AimShoot());
    	
    }
}

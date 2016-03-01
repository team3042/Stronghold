package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMode_LowBarSideGoal extends CommandGroup {
    
    public  AutoMode_LowBarSideGoal() {
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
    	
    	addParallel(new Snout_PickupTimed());
    	addSequential(new Auto_FollowTrajectory(AutoTrajectory_LowBarSideGoal.getLeftTrajectory(), 
    			AutoTrajectory_LowBarSideGoal.getRightTrajectory(), true));
    	addParallel(new Snout_SetPosition(200));
    	addSequential(new Auto_Drive(Auto_Drive.AutoType.TURN_LEFT, 0.5, 1, 0));
    	addSequential(new Auto_AimShoot());
    }
}

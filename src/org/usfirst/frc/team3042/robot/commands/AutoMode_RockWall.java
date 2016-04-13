package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMode_RockWall extends CommandGroup {
    
    public  AutoMode_RockWall(int defensePosition) {
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
    	
    	//TODO Find values for snout positioning
    	addParallel(new Auto_ConditionalSnout(140, 5000, 640));
    	addSequential(new Auto_Drive(Auto_Drive.AutoType.STRAIGHT, -11.5, -3));
    	addParallel(new Snout_AutoShootPosition());
    	
    	addSequential(new Auto_FaceGoalReverse(defensePosition));
    	if(defensePosition != 0) {
    		addSequential(new Auto_AimShoot());
    	}
    	
    	addSequential(new Snout_Storage());
    }
}

package org.usfirst.frc.team3042.robot.commands;

import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.commands.Auto_Drive.AutoType;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMode_ChevalDeFrise extends CommandGroup {
    
    public  AutoMode_ChevalDeFrise(int defensePosition) {
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
    	
    	//TODO Determine exact distances and snout position
    	addParallel(new Snout_SetPosition(250));
    	addSequential(new Auto_Drive(AutoType.STRAIGHT, 2.56, 2));
    	
    	addSequential(new Auto_SetSnoutPosition(20)); 
    	addParallel(new Auto_ConditionalSnout(20, 1000, 240));
    	addSequential(new Auto_Drive(AutoType.STRAIGHT, 5.3, 3)); 
    	
    	addParallel(new Snout_AutoShootPosition());
    	
    	//addSequential(new Auto_FaceGoalForwards(defensePosition));
    	if(defensePosition != 0) {
    		addSequential(new Auto_AimShoot());
    	}
    	
    }
}

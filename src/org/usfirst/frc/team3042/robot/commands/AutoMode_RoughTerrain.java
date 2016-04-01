package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMode_RoughTerrain extends CommandGroup {
    
    public  AutoMode_RoughTerrain(int defensePosition) {
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
    	
    	addParallel(new Snout_Storage());
    	addSequential(new Auto_Drive(Auto_Drive.AutoType.STRAIGHT, -10, -3));
    	
    	addParallel(new Snout_SetPosition(240)); 
    	
    	switch(defensePosition) {
    		case 0:
    			break;
    		case 1:
    			break;
    		case 2:
    			break;
    		case 3:
    			addSequential(new Auto_Drive(Auto_Drive.AutoType.TURN_LEFT, 2.3, 2, 0));
    			break;
    		case 4:
    			addSequential(new Auto_Drive(Auto_Drive.AutoType.TURN_LEFT, 2.7, 2, 0));
    			break;
    		case 5:
    			break;
    	}
    	if(defensePosition != 0) {
    		addSequential(new Auto_AimShoot());
    	}
    	
    }
}

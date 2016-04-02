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
    	addParallel(new Snout_ShootPosition());
    	addSequential(new Auto_Drive(AutoType.STRAIGHT, 4, 2));
    	addSequential(new Snout_SetPosition(50));
    	addSequential(new Auto_Drive(AutoType.STRAIGHT, 6, 3));
    	
    	addParallel(new Snout_SetPosition(240));
    	
    	switch(defensePosition) {
    		case 0:
    			break;
    		case 1:
    			break;
    		case 2:
    			break;
    		case 3:
    			addSequential(new Auto_Drive(Auto_Drive.AutoType.TURN_LEFT, -0.25, 2, 0));
    			break;
    		case 4:
    			addSequential(new Auto_Drive(Auto_Drive.AutoType.TURN_LEFT, 0.15, 2, 0));
    			break;
    		case 5:
    			break;
    	}
    	if(defensePosition != 0) {
    		addSequential(new Auto_AimShoot());
    	}
    }
}

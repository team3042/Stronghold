package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMode_Moat extends CommandGroup {
    
    public  AutoMode_Moat() {
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
    	
    	
    	addParallel(new Auto_Drive(Auto_Drive.AutoType.STRAIGHT, -13, -2));
    	addSequential(new Auto_ConditionalSnout(100, 6000, 500));
    	addSequential(new Auto_ConditionalSnout(500, 500, 150));
    	addSequential(new Auto_ConditionalSnout(250, 500, 640));
    	
    }
}

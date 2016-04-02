
package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.AutoMode_LowBar;
import org.usfirst.frc.team3042.robot.commands.AutoMode_LowBarSideGoal;
import org.usfirst.frc.team3042.robot.commands.AutoMode_Moat;
import org.usfirst.frc.team3042.robot.commands.AutoMode_Ramparts;
import org.usfirst.frc.team3042.robot.commands.AutoMode_RockWall;
import org.usfirst.frc.team3042.robot.commands.AutoMode_RoughTerrain;
import org.usfirst.frc.team3042.robot.commands.AutoMode_DoNothing;
import org.usfirst.frc.team3042.robot.subsystems.CameraAPI;
import org.usfirst.frc.team3042.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3042.robot.subsystems.DriversCamera;
import org.usfirst.frc.team3042.robot.subsystems.Shooter;
import org.usfirst.frc.team3042.robot.subsystems.Snout;
import org.usfirst.frc.team3042.robot.subsystems.ShooterServo;
import org.usfirst.frc.team3042.robot.subsystems.HookLift;
import org.usfirst.frc.team3042.robot.subsystems.HookLiftServo;
import org.usfirst.frc.team3042.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final Shooter shooter = new Shooter();
	public static final ShooterServo shooterServo = new ShooterServo();	
	public static final Snout snout = new Snout();
	public static final HookLift hookLift = new HookLift();
	public static final HookLiftServo hookLiftServo = new HookLiftServo();
	public static final Winch winch = new Winch();
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final CameraAPI camera = new CameraAPI();
	public static final DriversCamera driversCamera = new DriversCamera();
	public static OI oi;

    Command autonomousCommand;
    SendableChooser defenseChooser, positionChooser;
    
    public static Logger logger;
    public static FileIO fileIO = new FileIO();
    private int LOGGER_LEVEL = 5;
    boolean useConsole = true, useFile = true;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
        logger = new Logger(useConsole, useFile, LOGGER_LEVEL);
        
		defenseChooser = new SendableChooser();
        defenseChooser.addDefault("Default (Do Nothing)", DefenseType.NOTHING);
        defenseChooser.addObject("Low Bar", DefenseType.LOW_BAR);
        defenseChooser.addObject("Rough Terrain", DefenseType.ROUGH_TERRAIN);
        defenseChooser.addObject("Moat", DefenseType.MOAT);
        defenseChooser.addObject("Rock Wall", DefenseType.ROCK_WALL);
        defenseChooser.addObject("Ramparts", DefenseType.RAMPARTS);
        SmartDashboard.putData("Auto Defense", defenseChooser);
        
        positionChooser = new SendableChooser();
        positionChooser.addDefault("Default (No Shot)", 0);
        positionChooser.addObject("Position 1", 1);
        positionChooser.addObject("Position 2", 2);
        positionChooser.addObject("Position 3", 3);
        positionChooser.addObject("Position 4", 4);
        positionChooser.addObject("Position 5", 5); 
        SmartDashboard.putData("Auto Position", positionChooser);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	Robot.logger.log("Disabled Init", 1);
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	Robot.logger.log("Autonomous Init", 1);
    	/*
    	int defensePosition = (int) positionChooser.getSelected();
    	switch((DefenseType) defenseChooser.getSelected()) {
    		case NOTHING:
    			autonomousCommand = new AutoMode_DoNothing();
    		case LOW_BAR:
    			autonomousCommand = new AutoMode_LowBarSideGoal();
    			break;
    		case ROUGH_TERRAIN:
    			autonomousCommand = new AutoMode_RoughTerrain(defensePosition);
    			break;
    		case ROCK_WALL:
    			autonomousCommand = new AutoMode_RockWall(defensePosition);
    			break;
    		case MOAT:
    			autonomousCommand = new AutoMode_Moat(defensePosition);
    			break;
    		case RAMPARTS:
    			autonomousCommand = new AutoMode_Ramparts(defensePosition);
    			break;
    		default:
    			autonomousCommand = new AutoMode_DoNothing(); 
    	} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	Robot.logger.log("Teleop Init", 1);
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        hookLift.resetEncoder();
        snout.setToCurrentPosition();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();  
        SmartDashboard.putNumber("Left Shooter Speed", shooter.getEncoderRPMLeft());
        SmartDashboard.putNumber("Right Shooter Speed", shooter.getEncoderRPMRight());
        SmartDashboard.putNumber("Potentiometer", snout.getPotValue());
       // SmartDashboard.putNumber("Gyroscope", driveTrain.getGyro());
        
        //SmartDashboard.putNumber("Left Drive Position", Robot.driveTrain.getLeftEncoder());
        //SmartDashboard.putNumber("Right Drive Position", Robot.driveTrain.getRightEncoder());
    	    	
    	//SmartDashboard.putNumber("Tape Enc", tapeShooter.getEncDistance());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public enum DefenseType {
    	ROCK_WALL, ROUGH_TERRAIN, LOW_BAR, MOAT, RAMPARTS, CHEVAL_DE_FRISE, PORTCULLIS, NOTHING;
    }
    
}

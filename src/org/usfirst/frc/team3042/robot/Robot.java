
package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.subsystems.CameraAPI;
import org.usfirst.frc.team3042.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3042.robot.subsystems.Shooter;
import org.usfirst.frc.team3042.robot.subsystems.ShooterArm;
import org.usfirst.frc.team3042.robot.subsystems.ShooterServo;

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
	public static final ShooterArm shooterArm = new ShooterArm();
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final CameraAPI camera = new CameraAPI();
	public static OI oi;

    Command autonomousCommand;
    SendableChooser chooser;
    public static Logger logger;
    
    private double INTAKE_SPEED;
    private double POT_VAL;
    
    private int LOGGER_LEVEL = 1;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
        chooser = new SendableChooser();
        logger = new Logger(true, true, LOGGER_LEVEL);
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("Shooter Speed", shooter.shootSpeed);
        SmartDashboard.putNumber("Intake Speed", shooter.intakeSpeed);
        SmartDashboard.putNumber("F-Gain Left", shooter.leftF);
        SmartDashboard.putNumber("F-Gain Right", shooter.rightF);
        SmartDashboard.putNumber("Potentiometer", POT_VAL);
        SmartDashboard.putNumber("Logger Level", LOGGER_LEVEL);
        SmartDashboard.putNumber("Dummy distance from target", 8);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
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
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
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
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();  
        
        SmartDashboard.putNumber("Potentiometer Value", Robot.shooterArm.getPotentiometerVal());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
}

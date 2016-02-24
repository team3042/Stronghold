
package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.subsystems.CameraAPI;
import org.usfirst.frc.team3042.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3042.robot.subsystems.DriversCamera;
import org.usfirst.frc.team3042.robot.subsystems.Shooter;
import org.usfirst.frc.team3042.robot.subsystems.ShooterArm;
import org.usfirst.frc.team3042.robot.subsystems.ShooterServo;
import org.usfirst.frc.team3042.robot.subsystems.TapeShooter;
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
	public static final ShooterArm shooterArm = new ShooterArm();
	public static final TapeShooter tapeShooter = new TapeShooter();
	public static final Winch winch = new Winch();
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final CameraAPI camera = new CameraAPI();
	public static final DriversCamera driversCamera = new DriversCamera();
	public static OI oi;

    Command autonomousCommand;
    SendableChooser chooser;
    public static Logger logger;
    CameraServer usbCamera;
        
    private int LOGGER_LEVEL = 5;
    String CALIBRATION_FILE_NAME = "calibration";
    double CALIBRATION_SPEED = 0;
    double CALIBRATION_LENGTH = 0;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
        logger = new Logger(true, true, LOGGER_LEVEL);
		chooser = new SendableChooser();
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("Logger Level", LOGGER_LEVEL);
        
        SmartDashboard.putString("Calibration File Name", CALIBRATION_FILE_NAME);
        SmartDashboard.putNumber("Calibration Motor Speed", CALIBRATION_SPEED);
        SmartDashboard.putNumber("Calibration Length In Seconds", CALIBRATION_LENGTH);
        SmartDashboard.putNumber("Shooter Speed", 5000);
        SmartDashboard.putNumber("Shoot Test", 330);
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
        SmartDashboard.putNumber("Left Shooter Speed", shooter.getEncoderRPMLeft());
        SmartDashboard.putNumber("Right Shooter Speed", shooter.getEncoderRPMRight());
        SmartDashboard.putNumber("Potentiometer", Robot.shooterArm.getPotentiometerVal());
        
        SmartDashboard.putNumber("Left Drive Speed", driveTrain.getLeftSpeed());
    	SmartDashboard.putNumber("Right Drive Speed", driveTrain.getRightSpeed());
    	SmartDashboard.putNumber("Left Drive Position", driveTrain.getLeftEncoder());
    	SmartDashboard.putNumber("Right Drive Position", driveTrain.getRightEncoder());
    	
    	SmartDashboard.putNumber("Left Shooter Encoder", shooter.getEncoderValLeft());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
}

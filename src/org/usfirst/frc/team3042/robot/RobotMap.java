package org.usfirst.frc.team3042.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
	public static final int DRIVETRAIN_TALON_LEFT_1 = 13;
	public static final int DRIVETRAIN_TALON_LEFT_2 = 14;
	public static final int DRIVETAIN_TALON_RIGHT_1 = 10;
	public static final int DRIVETRAIN_TALON_RIGHT_2 = 11;
	
	public static final int SHOOTER_TALON_LEFT = 2;
	public static final int SHOOTER_TALON_RIGHT = 1;
	
	public static final int SHOOTER_SERVO = 1;
	
	public static final int SHOOTER_ARM_TALON = 7;
	public static final int SHOOTER_ARM_POT = 0;
	
	public static final int LEFT_JOYSTICK_USB_PORT_0 = 0;
	public static final int RIGHT_JOYSTICK_USB_PORT_1 = 1;
	public static final int GUNNER_JOYSTICK_USB_PORT_2 = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}

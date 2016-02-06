package org.usfirst.frc.team3042.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//CAN Talons
	public static final int DRIVETRAIN_TALON_LEFT_1 = 14;
	public static final int DRIVETRAIN_TALON_LEFT_2 = 13;
	public static final int DRIVETAIN_TALON_RIGHT_1 = 11;
	public static final int DRIVETRAIN_TALON_RIGHT_2 = 10;
	public static final int SHOOTER_TALON_LEFT = 3;
	public static final int SHOOTER_TALON_RIGHT = 15;
	public static final int SHOOTER_ARM_TALON = 16;
	
	//PWM ports
	public static final int SHOOTER_SERVO = 1;
	
	//USB Ports
	public static final int LEFT_JOYSTICK_USB_PORT_0 = 0;
	public static final int RIGHT_JOYSTICK_USB_PORT_1 = 1;
	public static final int GUNNER_JOYSTICK_USB_PORT_2 = 2;
	
	//Robot Camera IP
	public static final String CAMERA_IP = "10.30.42.17";
}

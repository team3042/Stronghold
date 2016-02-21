package org.usfirst.frc.team3042.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Chose which robot is being used
	public static boolean isSkoll = false;
	
	//CAN Talons
	public static final int DRIVETRAIN_TALON_LEFT_1 = (isSkoll)? 14 : 18;
	public static final int DRIVETRAIN_TALON_LEFT_2 = (isSkoll)? 13 : 6;
	public static final int DRIVETAIN_TALON_RIGHT_1 = (isSkoll)? 11 : 4;
	public static final int DRIVETRAIN_TALON_RIGHT_2 = (isSkoll)? 10 : 2;
	public static final int SHOOTER_TALON_LEFT = (isSkoll)? 3 : 17;
	public static final int SHOOTER_TALON_RIGHT = (isSkoll)? 15 : 5;
	public static final int SHOOTER_ARM_TALON = (isSkoll)? 16 : 7;
	public static final int TAPE_SHOOTER_TALON = (isSkoll)? 12 : 12;
	public static final int WINCH_TALON = (isSkoll)? 9 : 9;
	
	//PWM ports
	public static final int SHOOTER_SERVO = 1;
	
	//USB Ports
	public static final int LEFT_JOYSTICK_USB_PORT_0 = 0;
	public static final int RIGHT_JOYSTICK_USB_PORT_1 = 1;
	public static final int GUNNER_JOYSTICK_USB_PORT_2 = 2;
	
	//Robot Camera IP
	public static final String CAMERA_IP = "axis-camera";
	public static final String DRIVER_CAMERA_IP = "axis-drivercamera";
}

package org.usfirst.frc.team3042.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Chose which robot is being used
	private static enum ROBOT_MODEL {FENRIR, SKOLL;}
	private static final ROBOT_MODEL model = ROBOT_MODEL.SKOLL;
	
	public static boolean isSkoll = model == ROBOT_MODEL.SKOLL;
	//CAN Talons
	public static final int DRIVETRAIN_TALON_LEFT_1 = (isSkoll)? 14 : 14;
	public static final int DRIVETRAIN_TALON_LEFT_2 = (isSkoll)? 13 : 13;
	public static final int DRIVETAIN_TALON_RIGHT_1 = (isSkoll)? 11 : 11;
	public static final int DRIVETRAIN_TALON_RIGHT_2 = (isSkoll)? 10 : 10;
	public static final int SHOOTER_TALON_LEFT = (isSkoll)? 3 : 3;
	public static final int SHOOTER_TALON_RIGHT = (isSkoll)? 15 : 15;
	public static final int SHOOTER_ARM_TALON = (isSkoll)? 16 : 16;
	
	//PWM ports
	public static final int SHOOTER_SERVO = (isSkoll)? 1 : 1;
	
	//USB Ports
	public static final int LEFT_JOYSTICK_USB_PORT_0 = (isSkoll)? 0 : 0;
	public static final int RIGHT_JOYSTICK_USB_PORT_1 = (isSkoll)? 1 : 1;
	public static final int GUNNER_JOYSTICK_USB_PORT_2 = (isSkoll)? 2 : 2;
	
	//Robot Camera IP
	public static final String CAMERA_IP = (isSkoll)? "10.30.42.17" : "10.30.42.17";
}

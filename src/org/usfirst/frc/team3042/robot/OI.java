package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//Declare the joysticks
	public Joystick joystickLeft = new Joystick(RobotMap.LEFT_JOYSTICK_USB_PORT_0);
	public Joystick joystickRight = new Joystick(RobotMap.RIGHT_JOYSTICK_USB_PORT_1);
	public Joystick joystickGunner = new Joystick(RobotMap.GUNNER_JOYSTICK_USB_PORT_2);
	
	//Left Joystick Buttons
	public Button leftButton_1 = new JoystickButton(joystickLeft, 1);

	//Right Joystick Buttons
	public Button rightButton_1 = new JoystickButton(joystickRight, 1);
	
	Button gunnerButton_1 = new JoystickButton(joystickGunner, 1);
	Button gunnerButton_2 = new JoystickButton(joystickGunner, 2);
	Button gunnerButton_3 = new JoystickButton(joystickGunner, 3);
	Button gunnerButton_4 = new JoystickButton(joystickGunner, 4);
	Button gunnerButton_5 = new JoystickButton(joystickGunner, 5);
	Button gunnerButton_6 = new JoystickButton(joystickGunner, 6);
	Button gunnerButton_7 = new JoystickButton(joystickGunner, 7);
	Button gunnerButton_8 = new JoystickButton(joystickGunner, 8);
    
	public OI() {
		//Shooter - Intake
		gunnerButton_1.whileHeld(new Shooter_Shoot());
		gunnerButton_2.whileHeld(new Shooter_Intake());
			
		//Shooter Arm
		gunnerButton_3.whileHeld(new ShooterArm_Raise());
		gunnerButton_4.whileHeld(new ShooterArm_Lower());
		gunnerButton_5.whenPressed(new ShooterArm_Storage());
		gunnerButton_6.whenPressed(new ShooterArm_Pickup());
		
		//Shooter Servo
		gunnerButton_7.whenPressed(new ShooterServo_Retract());
		gunnerButton_8.whenPressed(new ShooterServo_Extend());
	}
}


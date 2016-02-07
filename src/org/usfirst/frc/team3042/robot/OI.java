package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.*;
import org.usfirst.frc.team3042.robot.triggers.GamePadAxis;
import org.usfirst.frc.team3042.robot.triggers.GamePadTrigger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//Declare the joysticks
	public Joystick joystickLeft = new Joystick(RobotMap.LEFT_JOYSTICK_USB_PORT_0);
	public Joystick joystickRight = new Joystick(RobotMap.RIGHT_JOYSTICK_USB_PORT_1);
	public Joystick gamePadGunner = new Joystick(RobotMap.GUNNER_JOYSTICK_USB_PORT_2);
	
	//Left Joystick Buttons
	public Button leftButton_1 = new JoystickButton(joystickLeft, 1);

	//Right Joystick Buttons
	public Button rightButton_1 = new JoystickButton(joystickRight, 1);
	
	Button gunnerButton_A = new JoystickButton(gamePadGunner, 1);
	Button gunnerButton_B = new JoystickButton(gamePadGunner, 2);
	Button gunnerButton_X = new JoystickButton(gamePadGunner, 3);
	Button gunnerButton_Y = new JoystickButton(gamePadGunner, 4);
	Button gunnerButton_LB = new JoystickButton(gamePadGunner, 5);
	Button gunnerButton_RB = new JoystickButton(gamePadGunner, 6);
	Button gunnerButton_7 = new JoystickButton(gamePadGunner, 7);
	Button gunnerButton_8 = new JoystickButton(gamePadGunner, 8);
	
	Trigger gunner_LT = new GamePadTrigger(gamePadGunner,2);
	Trigger gunner_RT = new GamePadTrigger(gamePadGunner,3);
	Trigger gunner_LeftJoyUp = new GamePadAxis(gamePadGunner, 1, GamePadAxis.DIRECTION.UP);
	Trigger gunner_LeftJoyDown = new GamePadAxis(gamePadGunner, 1, GamePadAxis.DIRECTION.DOWN);
	
	public OI() {
		
		//Shooter - Intake
		gunner_LT.whileActive(new Shooter_Shoot());
		gunnerButton_LB.whileHeld(new Shooter_Intake());
		
		gunner_RT.whenActive(new ShooterServo_Extend());
		
		gunner_LeftJoyUp.whileActive(new ShooterArm_Raise());
		gunner_LeftJoyDown.whileActive(new ShooterArm_Lower());

		gunnerButton_A.whenPressed(new ShooterArm_Pickup());
		gunnerButton_X.whenPressed(new ShooterArm_Storage());
		
	}
}


package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.*;
import org.usfirst.frc.team3042.robot.triggers.GamePadTrigger;
import org.usfirst.frc.team3042.robot.triggers.POVButton;

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
	public Button left_1 = new JoystickButton(joystickLeft, 1);
	public Button left_3 = new JoystickButton(joystickLeft, 3);
	public Button left_4 = new JoystickButton(joystickLeft, 4);
	public Button left_5 = new JoystickButton(joystickLeft, 5);
	public Button left_6 = new JoystickButton(joystickLeft, 6);
	public Button left_8 = new JoystickButton(joystickLeft, 8);

	//Right Joystick Buttons
	public Button right_1 = new JoystickButton(joystickRight, 1);
	public Button right_3 = new JoystickButton(joystickRight, 3);
	public Button right_4 = new JoystickButton(joystickRight, 4);
	
	//Gampad buttons
	Button gunner_A = new JoystickButton(gamePadGunner, 1);
	Button gunner_B = new JoystickButton(gamePadGunner, 2);
	Button gunner_X = new JoystickButton(gamePadGunner, 3);
	Button gunner_Y = new JoystickButton(gamePadGunner, 4);
	Button gunner_LB = new JoystickButton(gamePadGunner, 5);
	Button gunner_RB = new JoystickButton(gamePadGunner, 6);
	Button gunner_Back = new JoystickButton(gamePadGunner, 7);
	Button gunner_Start = new JoystickButton(gamePadGunner, 8);

	//Triggers
	Trigger gunner_LT = new GamePadTrigger(gamePadGunner,2);
	Trigger gunner_RT = new GamePadTrigger(gamePadGunner,3);
	Trigger gunner_LeftJoyUp = new GamePadTrigger(gamePadGunner, 1, GamePadTrigger.DIRECTION.UP);
	Trigger gunner_LeftJoyDown = new GamePadTrigger(gamePadGunner, 1, GamePadTrigger.DIRECTION.DOWN);
	Trigger gunner_POVUp = new POVButton(gamePadGunner, 0);
	
	public OI() {
		
		//Shooter - Intake	
		gunner_RT.whileActive(new Shooter_Shoot());
		
		//Control Snout Position
		gunner_LeftJoyDown.whileActive(new ShooterArm_Raise());
		gunner_LeftJoyUp.whileActive(new ShooterArm_Lower());
		gunner_B.whenPressed(new ShooterArm_Pickup());
		gunner_A.whileHeld(new Shooter_Intake());
		gunner_X.whenPressed(new ShooterArm_Storage());
		
		//Winch Controls
		gunner_POVUp.whileActive(new Winch_Raise());
		
		//TapeShooter
		gunner_Start.whenPressed(new TapeShooter_Raise());
		gunner_Back.whenPressed(new TapeShooter_Retract());
		
		//Auto
		left_3.whenPressed(new Auto_LowBar());
		right_3.whenPressed(new Auto_Rotate());
		right_4.whenPressed(new Interruptible(new ShooterArm_Adjust()));
		//left_3.whenPressed(new Auto_Follow());
		left_4.whenPressed(new Auto_StopFollow());
		left_5.toggleWhenPressed(new Overlay_Toggle());
	}
}


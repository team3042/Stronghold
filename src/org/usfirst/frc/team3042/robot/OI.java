package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.*;
import org.usfirst.frc.team3042.robot.commands.Auto_Drive.AutoType;
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
	public Button right_5 = new JoystickButton(joystickRight, 5);
	
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
	Trigger gunner_POVDown = new POVButton(gamePadGunner, 180);
	
	public OI() {
		
		//Shooter - Intake	
		gunner_RT.whileActive(new Shooter_Shoot());
		gunner_RB.whileActive(new Snout_LowGoalPosition());
		
		//Control Snout Position
		//gunner_LeftJoyDown.whileActive(new Snout_Raise());
		//gunner_LeftJoyUp.whileActive(new Snout_Lower());
		gunner_B.whenPressed(new Snout_Pickup());
		gunner_A.whileHeld(new Shooter_Intake());
		gunner_X.whenPressed(new Snout_Storage());
		//gunner_LT.whenActive(new Interruptible(new Snout_Adjust()));
		gunner_Y.whenPressed(new Snout_ShootPosition());
		gunner_LB.whenActive(new Snout_LayupPosition());
		
		//Winch Controls
		gunner_POVUp.whileActive(new Winch_HookLift_Raise());
		gunner_POVDown.whenActive(new HookLiftServo_Extend());
		left_8.whileHeld(new Winch_Release());
		
		//TapeShooter
		gunner_Start.whileHeld(new HookLift_Raise());
		gunner_Back.whileHeld(new HookLift_Retract());
		
		left_3.whenPressed(new DefenseExtendServo_Retract());
		left_4.whenPressed(new DefenseExtendServo_Extend());
		
		//Auto
		/*
		left_3.whenPressed(new Auto_FollowTrajectory(
				AutoTrajectory_Test.getLeftTrajectory(), 
				AutoTrajectory_Test.getRightTrajectory(), 
				false));
				*/
		right_3.whenPressed(new Auto_Aim());
		//right_4.whenPressed(new Interruptible(new Snout_Adjust()));
		//left_4.whenPressed(new Auto_StopFollow());
		//left_5.whenPressed(new AutoMode_Ramparts(4));
		//right_4.whenPressed(new CameraAPI_TakeCalibrationImage());
		
		//Testing
		right_5.whenPressed(new CameraAPI_SubtractionTest());
		//right_5.whileHeld(new LEDSwitch_SetOn());
		//right_4.whenPressed(new Auto_GyroDriveStraight(7, 2.5));
	}
}


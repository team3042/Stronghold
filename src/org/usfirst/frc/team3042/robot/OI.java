package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.ShooterExtendServo;
import org.usfirst.frc.team3042.robot.commands.ShooterRetractServo;
import org.usfirst.frc.team3042.robot.commands.ShooterGetPotentiometer;
import org.usfirst.frc.team3042.robot.commands.ShooterIntake;
import org.usfirst.frc.team3042.robot.commands.ShooterTwoWheelShoot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
	Joystick joystickGunner = new Joystick(RobotMap.GUNNER_JOYSTICK_USB_PORT_3);
	
	Button buttonTwoWheelFire = new JoystickButton(joystickGunner, 1);
	Button buttonIntake = new JoystickButton(joystickGunner, 2);
	
	Button buttonPotentiometer = new JoystickButton(joystickGunner, 5);
	
	Button buttonRetractServo = new JoystickButton(joystickGunner, 3);
	Button buttonExtendServo = new JoystickButton(joystickGunner, 4);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	public OI() {
		buttonTwoWheelFire.whileHeld(new ShooterTwoWheelShoot());
		buttonIntake.whileHeld(new ShooterIntake());
		
		buttonPotentiometer.whileHeld(new ShooterGetPotentiometer());
		
		buttonRetractServo.whenPressed(new ShooterRetractServo());
		buttonExtendServo.whenPressed(new ShooterExtendServo());
		
	}
}


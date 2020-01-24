/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.buttons.Button;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
    SpeedController m_frontLeft = new PWMVictorSPX(0);
    SpeedController m_rearLeft = new PWMVictorSPX(1);
    SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
    
 
    SpeedController m_frontRight = new PWMVictorSPX(2);
    SpeedController m_rearRight = new PWMVictorSPX(3);
    SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
 
    DifferentialDrive m_robotDrive = new DifferentialDrive(m_left, m_right);
    
    private final Joystick m_ps4 = new Joystick(0);
    private final XboxController m_xbox = new XboxController(1);
    private final Timer m_timer = new Timer();
   


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(-0.5, 0.0); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {

    double move_sensitivity = 0.5;
    double rotate_sensitivity = 0.70;

    if (m_ps4.getRawButton(1) == true)
    {
      rotate_sensitivity = 1.0;
    }
    if (m_ps4.getRawButton(1) == false)
    {
      rotate_sensitivity = 0.70;
    }
    double xbox_move = (m_xbox.getTriggerAxis(Hand.kLeft) - m_xbox.getTriggerAxis(Hand.kRight)) * move_sensitivity;
    double xbox_rotate = m_xbox.getX(Hand.kLeft) * rotate_sensitivity;
    double ps4_move = (m_ps4.getRawAxis(3) - m_ps4.getRawAxis(4)) * move_sensitivity;
    double ps4_rotate = m_ps4.getX(Hand.kLeft) * rotate_sensitivity; 
    m_robotDrive.arcadeDrive(xbox_move + ps4_move, xbox_rotate + ps4_rotate);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

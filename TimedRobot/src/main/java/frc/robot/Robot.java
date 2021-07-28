// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  
    private CANSparkMax leftMotor1, leftMotor2, rightMotor1, rightMotor2;
    SpeedControllerGroup left_m, right_m;
    DifferentialDrive teleop_drive;
    Joystick driveStick = new Joystick(0);
    private CANEncoder encoder_left;
    private CANEncoder encoder_right;
    
    public static double elp;
    public static double erp;
    /*JoystickButton button1;*/

    /*private double leftMotorStartPosition;
    private double rightMotorStartPosition;*/
    
    public static int state;
    public static int middle_state;
    public static int end_state;

  //target variables

    public static double leftEncoderTarget;
    public static double rightEncoderTarget;
    public boolean leftReachedTarget; 
    public boolean rightReachedTarget;

  

  @Override
  
  public void robotInit() {
    leftMotor1 = new CANSparkMax(3, MotorType.kBrushless);
    leftMotor2 = new CANSparkMax(2, MotorType.kBrushless);
    rightMotor1 = new CANSparkMax(9, MotorType.kBrushless);
    rightMotor2  = new CANSparkMax(8, MotorType.kBrushless);

    left_m = new SpeedControllerGroup(leftMotor1,leftMotor2);
    right_m = new SpeedControllerGroup(rightMotor1,rightMotor2);
    teleop_drive = new DifferentialDrive(left_m, right_m);
    encoder_left = leftMotor1.getEncoder();
    encoder_right = rightMotor1.getEncoder();
    
    /*button1 = new JoystickButton(driveStick, 1);*/
    state = 0;
    //middle_state = 1;
    //end_state = 2;

    //target init

    leftEncoderTarget = 0;
    rightEncoderTarget = 0;

    leftReachedTarget = false; 
    rightReachedTarget = false;


  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */

  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    encoder_left.setPosition(0);
    encoder_right.setPosition(0);

    //set the distance in feet
   // setDistanceTarget(6);

    System.out.println("Start");
    System.out.println(encoder_left.getPosition() + "\t" + leftEncoderTarget);
  }

    

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
    
    SmartDashboard.putNumber("Encoder Position", encoder_left.getPosition());
    SmartDashboard.putNumber("Encoder Position", encoder_right.getPosition());


    if (state == 0){
     // System.out.println(state);
      setDistanceTarget(6);
      state = 1;
      

    }
    if (state == 1){
      //System.out.println(state);
      forwardDrive(2); 
      
    }

    if(state == 2){
      
      //setDistanceTarget(5);
      //state = 3;
      stopDrive();
      
    } /*
    if (state == 3){
      System.out.println("===========================");
      forwardDrive(4);
      //stopDrive();
      //System.out.println(state);
      
      
    }
    /*
    if (state == 4){
      stopDrive();
      System.out.println(state);
    }
    */
  
    //System.out.println(encoder_left.getPosition() + "\t" + encoder_right.getPosition());

   
    
    //startPath();
    //forwardDrive();

  }


  /*public void startPath(){  


    if (-encoder_left.getPosition() < 22.95 && encoder_right.getPosition() < 22.95){
      forwardDrive();
    }
    else if (-encoder_left.getPosition() >= 22.95 || encoder_right.getPosition() >= 22.95){
      turnDrive();
    }
    if(-encoder_left.getPosition() >=  55.0 || encoder_right.getPosition() >= 35.0){
      stopDrive();
      //state = middle_state;
      middlePath();
    } 
    forwardDrive();

    
    
 }
 
  public void middlePath(){

    if (-encoder_left.getPosition() < 115.0 && encoder_right.getPosition() < 100.0){
      forwardDrive();
    }
    else if (-encoder_left.getPosition() >= 115.0 || encoder_right.getPosition() >= 100.0){
      turnDrive();
      
    }
    if(-encoder_left.getPosition() >=  144.0 || encoder_right.getPosition() >= 124.0){
      stopDrive();

      endPath();
    }
  }

  public void endPath(){
    if (-encoder_left.getPosition() < 148.7 && encoder_right.getPosition() < 128.7){
      forwardDrive();
    }
    else if (-encoder_left.getPosition() >= 148.7 || encoder_right.getPosition() >= 128.7){
      stopDrive();
    }
  }
  */

  //target functions

  public void setDistanceTarget(double distanceToTravel)
    {

      leftEncoderTarget = -(encoder_left.getPosition() + (distanceToTravel*4.7));
      rightEncoderTarget = encoder_right.getPosition() + (distanceToTravel*4.7);

    }
  
  //autonomous methods 
  //forward drive
  public void forwardDrive( int next_state) {

      /*leftMotor1.set(-0.1);
      leftMotor2.set(-0.1);
      rightMotor1.set(0.128);
      rightMotor2.set(0.128);
    */

    double adjustSpeed = (0);
    double threshold = (3);
    double leftEncoderCurr = encoder_left.getPosition();
    double rightEncoderCurr =  encoder_right.getPosition();
    double differenceOf_Encoders = -leftEncoderCurr - rightEncoderCurr;

    // if (Math.abs(differenceOf_Encoders) > threshold)
    // {
    //   if (differenceOf_Encoders > (0)){
    //     adjustSpeed = 0.05;
    //   }
    //   if (differenceOf_Encoders < (0)){
    //     adjustSpeed = -0.05;
    //   }
    // }


      //target code
      leftReachedTarget = false;
      rightReachedTarget = false;
      System.out.println("R" + rightEncoderCurr + "," + rightEncoderTarget);
      System.out.println("L" + leftEncoderCurr + "," + leftEncoderTarget);
      System.out.println(adjustSpeed);

      if (encoder_left.getPosition() > -leftEncoderTarget)
      {
        leftMotor1.set(-0.236 + adjustSpeed);
        leftMotor2.set(-0.236 + adjustSpeed);
        //System.out.println(leftReachedTarget);
      }

      else{
        leftMotor1.set(0.0);
        leftMotor2.set(0.0);
        leftReachedTarget = true;
       // System.out.println(leftReachedTarget);
        //System.out.println("left position" + leftEncoderCurr);
      }

      if (encoder_right.getPosition() < rightEncoderTarget)
      {
        rightMotor1.set(0.2 - adjustSpeed);
        rightMotor2.set(0.2 - adjustSpeed);
        //System.out.println(rightReachedTarget);
  
      }
      else{
        rightMotor1.set(0.0);
        rightMotor2.set(0.0);
        rightReachedTarget = true;
        //System.out.println(rightReachedTarget);
        //System.out.println("right position" + rightEncoderCurr);
      }
      if(leftReachedTarget && rightReachedTarget){
        state = next_state;
      }
      
  }
  //right turn
 
   public void turnDrive(){
    leftMotor1.set(-0.24);
    leftMotor2.set(-0.24);
    rightMotor1.set(-0.24);
    rightMotor2.set(-0.24);
  }

  //stop drive
  public void stopDrive(){
    leftMotor1.set(0.0);
    leftMotor2.set(0.0);
    rightMotor1.set(0.0);
    rightMotor2.set(0.0);

    //encoder_left.setPosition(0);
    //encoder_right.setPosition(0);

  }

  /** This function is called once when teleop is enabled. */
  @Override
  
  public void teleopInit() {
    //encoder_left.setPosition(0);
    //encoder_right.setPosition(0);
    
  }


  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    /*double threshold = (4);*/
    //teleop_drive.arcadeDrive(-0.3, 0);

    
    //left and right encoder positions printed in dashboard

    SmartDashboard.putNumber("Encoder Position", encoder_left.getPosition());
    SmartDashboard.putNumber("Encoder Position", encoder_right.getPosition());

    System.out.println(encoder_left.getPosition() + "\t" + encoder_right.getPosition());

    //button 2 to turn right
    /*if(driveStick.getRawButton(2)){

      turnDrive();
      forwardDrive(1);


    }
    //trigger to move forward
    else if  (driveStick.getRawButton(1)){
      forwardDrive(1);

    }
    else{
    */
    teleop_drive.arcadeDrive(driveStick.getY(), -driveStick.getX());
    //}
    /*
    SmartDashboard.putNumber("Encoder Position", encoder_left.getPosition());
    SmartDashboard.putNumber("Encoder Position", encoder_right.getPosition());

    System.out.println(encoder_left.getPosition() + "\t" + encoder_right.getPosition());
    */
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}

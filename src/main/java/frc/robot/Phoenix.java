package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Phoenix extends TimedRobot{
    private final XboxController m_Controller = new XboxController(0);
    private final Phoenixtrain m_Swerve = new Phoenixtrain();

    // slewratelimiters used to make the joystick controls gentler
    private final SlewRateLimiter m_xSpeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_ySpeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

    @Override
    public void autonomousPeriodic(){

    }

    @Override
    public void teleopPeriodic(){
        joystickDrive();
    }

    private void joystickDrive(){
        // Below vars are inverted because xbox controllers return
        // negative values when pushed forward or pulled to the left
        // forward or backwards speed
        final var xSpeed
            = -m_xSpeedLimiter.calculate(MathUtil.applyDeadband(m_Controller.getLeftY(), 0.02))
            * Phoenixtrain.kMaxSpeed;

        System.out.println("xSpeed: " + xSpeed);

        //sideways or strafe speed
        final var ySpeed
            = -m_ySpeedLimiter.calculate(MathUtil.applyDeadband(m_Controller.getLeftX(), 0.02))
            * Phoenixtrain.kMaxSpeed;

        System.out.println("ySpeed: " + ySpeed);

        // rate of angular rotation
        final var rot
            = -m_rotLimiter.calculate(MathUtil.applyDeadband(m_Controller.getRightX(), 0.02))
            * Phoenixtrain.kMaxAngularSpeed;

        System.out.println("rot:" + rot);

        m_Swerve.Drive(xSpeed, ySpeed, rot);

    }
}
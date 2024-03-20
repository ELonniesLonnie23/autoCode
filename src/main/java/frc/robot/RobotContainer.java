package frc.robot;

import java.io.IOException;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lib.Controller;
import frc.robot.commands.swerve.BrakeMode;
import frc.robot.commands.swerve.Drive;
import frc.robot.commands.swerve.TrackAprilTags;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Swerve;
import frc.robot.util.Alerts;
import frc.robot.util.Constants;

public class RobotContainer {

    private Swerve swerve;
    private Limelight limelight;
    private Controller driverOne;

    public RobotContainer () {

        try { this.swerve = new Swerve(); } 
        catch (IOException ioException) { Alerts.swerveInitialized.set(true); }

        this.limelight = new Limelight();
        this.driverOne = new Controller(0);

        this.configureCommands();
    }
//autonomous stuff
    private void autonomousconfigureCommands () {

AUTO_EVENT_MAP.put("start", New PrintCommand("passed marker 1"));
AUTO_EVENT_MAP.put("end", New PrintCommand("passed marker 2"));

//build auto path commands
ArrayList<PathPlannerTrajectory> autoPath1 =
        PathPlanner.loadPathGroup(
            name: "autoPath1",
            AUTO_MAX_SPEED_METERS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECONDS_SQUARED);
Command autoTest =
        new SequentialCommandGroup(new FollowPath(autoPath1.get(0), driveTrain, true),
    autoPath1.get(0).getMarkers(), AUTO_EVENT_MAP),
            new InstantCommand(drivetrain::enableXstance, drivetrain),
            new WaitCommand(5.0),
            new InstantCommand(drivetrain::enableXstance, drivetrain),
            new FollowPathWithEvents(
                new FollowPath(autoPath1.get(1), drivetrain, false),
                autoPath1.get(1).getMarkers(),
                AUTO_EVENT_MAP)
            );
          /*   //add comands to the auto chooser
        autoChooser.addDefaultOption(key:"Do Nothing", new Instand Command());
            //demonstration of PathPlanner path group with event markers
            autoChooser.addOption("Test Path", autoTest);
            //add command for tuning the drive velocity PID
            addChooser.addOption(

            )*/




        this.swerve.setDefaultCommand(new Drive(
            this.swerve, 
            () -> MathUtil.applyDeadband(-this.driverOne.getHID().getLeftY(), Constants.SwerveConstants.TRANSLATION_DEADBAND),
            () -> MathUtil.applyDeadband(-this.driverOne.getHID().getLeftX(), Constants.SwerveConstants.TRANSLATION_DEADBAND), 
            () -> MathUtil.applyDeadband(this.driverOne.getHID().getRightX(), Constants.SwerveConstants.OMEGA_DEADBAND), 
            () -> this.driverOne.getHID().getPOV()
        ));

        this.limelight.setDefaultCommand(new TrackAprilTags(this.swerve, this.limelight));

        this.driverOne.x().onTrue(new InstantCommand(this.swerve::zeroGyro, this.swerve));
        this.driverOne.leftBumper().whileTrue(new RepeatCommand(new InstantCommand(this.swerve::lock, this.swerve)));

        /**
        this.driverOne.leftTrigger().whileTrue(this.swerve.getDriveSysidRoutine());
        this.driverOne.rightTrigger().whileTrue(this.swerve.getAngleSysidRoutine());
        */
    }

    public Command getAutonomousCommand () { return this.swerve.getAutonomousCommand(); }
    public void setBrakeMode (boolean brake) { new BrakeMode(this.swerve, brake).schedule(); }
}

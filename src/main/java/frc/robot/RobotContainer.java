package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ButtonConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContainer {

    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final VisionSubsystem vision = new VisionSubsystem();

    // Driver = port 0 (Xbox): driving. Operator = port 1 (Logitech now; can swap to Xbox later): shooter, etc.
    private final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    private final CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

    public RobotContainer() {
        configureButtonBindings();

        // Default driving: driver controller only
        m_robotDrive.setDefaultCommand(
            new RunCommand(
                () -> m_robotDrive.drive(
                    -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                    -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                    -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                    true, true
                ),
                m_robotDrive
            )
        );
    }

    private void configureButtonBindings() {
        // Shooter: operator Y — run both shooter motors while held
        m_operatorController.button(ButtonConstants.operatorShooterButton)
            .whileTrue(m_shooter.runShooterCommand(Constants.ShooterConstants.ShooterSpeed))
            .onFalse(new InstantCommand(() -> m_shooter.stopMotor(), m_shooter));

        // Intake: operator A — run intake while held
        m_operatorController.button(ButtonConstants.operatorIntakeButton)
            .whileTrue(m_intake.runIntakeCommand(Constants.IntakeConstants.IntakeSpeed))
            .onFalse(new InstantCommand(() -> m_intake.stop(), m_intake));

        // Gyro reset: co-driver can re-zero heading for field-centric if needed
        m_operatorController.button(ButtonConstants.operatorGyroResetButton)
            .onTrue(new InstantCommand(() -> m_robotDrive.zeroHeading(), m_robotDrive));

        //intake stuff
        //intake pivot
        m_operatorController.button(ButtonConstants.operatorIntakePivUp)
        .onTrue(m_intake.spinUp(IntakeConstants.PivotSpeed).withTimeout(0.5));

        m_operatorController.button(ButtonConstants.operatorIntakePivDown)
        .onTrue(m_intake.spinDown(IntakeConstants.PivotSpeed).withTimeout(0.5));


        // m_operatorController.button(ButtonConstants.operatorIntakePivUp).onTrue(new InstantCommand(() -> m_intake.MoveTo(IntakeConstants.IntakePivotLv1)));
        // m_operatorController.button(ButtonConstants.operatorIntakePivDown).onTrue(new InstantCommand(() -> m_intake.MoveTo(IntakeConstants.IntakePivotLv2)));
        //intake rollers
        m_operatorController.button(ButtonConstants.operatorIntakeRoller)
        .whileTrue(m_intake.runRollerCommand(Constants.IntakeConstants.RollerSpeed))
        .onFalse(new InstantCommand(() -> m_intake.rollerStop(), m_intake));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
        );
    }
}

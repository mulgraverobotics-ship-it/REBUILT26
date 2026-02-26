package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ButtonConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {

    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();

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
        // Shooter: operator controller (Logitech or Xbox — same button IDs). Change operatorShooterButton in Constants to remap.
        m_operatorController.button(ButtonConstants.operatorShooterButton)
            .whileTrue(m_shooter.runShooterCommand(Constants.ShooterConstants.ShooterSpeed))
            .onFalse(new InstantCommand(() -> m_shooter.stopMotor(), m_shooter));

        // Gyro reset: co-driver can re-zero heading for field-centric if needed
        m_operatorController.button(ButtonConstants.operatorGyroResetButton)
            .onTrue(new InstantCommand(() -> m_robotDrive.zeroHeading(), m_robotDrive));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
        );
    }
}

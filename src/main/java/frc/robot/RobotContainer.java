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
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TransferConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TransferSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContainer {

    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final TransferSubsystem m_transfer = new TransferSubsystem();
    private final VisionSubsystem vision = new VisionSubsystem();

    private final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    private final CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

    public RobotContainer() {
        configureButtonBindings();

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
        // Driver hold-to-aim: keep translation control, auto-correct yaw from Limelight tx.
        m_driverController.button(ButtonConstants.driverAimAssistButton)
            .whileTrue(new RunCommand(
                () -> {
                    double xSpeed = -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband);
                    double ySpeed = -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband);

                    double rotCommand = -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband);
                    if (vision.hasTarget()) {
                        double tx = vision.getTx();
                        if (Math.abs(tx) > VisionConstants.kAimDeadbandDeg) {
                            rotCommand = MathUtil.clamp(
                                -tx * VisionConstants.kAimKp,
                                -VisionConstants.kAimMaxTurnCmd,
                                VisionConstants.kAimMaxTurnCmd);
                        } else {
                            rotCommand = 0.0;
                        }
                    }

                    m_robotDrive.drive(xSpeed, ySpeed, rotCommand, true, true);
                },
                m_robotDrive));

        // Intake in + transfer towards shooter.
        m_operatorController.button(ButtonConstants.operatorIntakeInButton)
            .whileTrue(new RunCommand(
                () -> {
                    m_intake.setRollerSpeed(IntakeConstants.intakeInSpeed);
                    m_transfer.setTransferSpeed(TransferConstants.transferToShooterSpeed);
                },
                m_intake, m_transfer))
            .onFalse(new InstantCommand(
                () -> {
                    m_intake.stopRoller();
                    m_transfer.stopTransfer();
                },
                m_intake, m_transfer));

        // Unjam / reverse path.
        m_operatorController.button(ButtonConstants.operatorIntakeOutButton)
            .whileTrue(new RunCommand(
                () -> {
                    m_intake.setRollerSpeed(IntakeConstants.intakeOutSpeed);
                    m_transfer.setTransferSpeed(TransferConstants.transferReverseSpeed);
                },
                m_intake, m_transfer))
            .onFalse(new InstantCommand(
                () -> {
                    m_intake.stopRoller();
                    m_transfer.stopTransfer();
                },
                m_intake, m_transfer));

        // Shooter spin only.
        m_operatorController.button(ButtonConstants.operatorShooterSpinButton)
            .whileTrue(m_shooter.spinUpCommand(ShooterConstants.shooterSpinSpeed))
            .onFalse(new InstantCommand(() -> m_shooter.stopAll(), m_shooter));

        // Shoot + feed.
        m_operatorController.button(ButtonConstants.operatorShootAndFeedButton)
            .whileTrue(new RunCommand(
                () -> {
                    m_shooter.setAllShooters(ShooterConstants.shooterFeedSpeed);
                    m_transfer.setTransferSpeed(TransferConstants.transferToShooterSpeed);
                },
                m_shooter, m_transfer))
            .onFalse(new InstantCommand(
                () -> {
                    m_shooter.stopAll();
                    m_transfer.stopTransfer();
                },
                m_shooter, m_transfer));

        // Intake pivot controls.
        m_operatorController.button(ButtonConstants.operatorIntakePivotUpButton)
            .whileTrue(new RunCommand(
                () -> m_intake.setPivotSpeed(IntakeConstants.pivotUpSpeed),
                m_intake))
            .onFalse(new InstantCommand(() -> m_intake.stopPivot(), m_intake));

        m_operatorController.button(ButtonConstants.operatorIntakePivotDownButton)
            .whileTrue(new RunCommand(
                () -> m_intake.setPivotSpeed(IntakeConstants.pivotDownSpeed),
                m_intake))
            .onFalse(new InstantCommand(() -> m_intake.stopPivot(), m_intake));

        // Gyro reset: co-driver can re-zero heading for field-centric if needed
        m_operatorController.button(ButtonConstants.operatorGyroResetButton)
            .onTrue(new InstantCommand(() -> m_robotDrive.zeroHeading(), m_robotDrive));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
        );
    }
}

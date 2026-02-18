package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ButtonConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class RobotContainer {



    
    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();

    // Controllers
    private final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    // private final CommandXboxController m_operatorController = new CommandXboxController(OIConstants.kSecondaryControllerPort);

    public RobotContainer() {
        configureButtonBindings();

        // Default Driving Control (Main Controller)
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
        m_driverController.y().toggleOnTrue(m_shooter.runShooterCommand(Constants.ShooterConstants.ShooterSpeed));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
        );
    }
}

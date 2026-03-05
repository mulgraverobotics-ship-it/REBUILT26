package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private final SparkMax m_shooterLeft;
    private final SparkMax m_shooterRight;

    public ShooterSubsystem() {
        m_shooterLeft = new SparkMax(ShooterConstants.ShooterMotorLeftCanId, MotorType.kBrushless);
        m_shooterRight = new SparkMax(ShooterConstants.ShooterMotorRightCanId, MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(50).idleMode(IdleMode.kBrake);

        m_shooterLeft.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_shooterRight.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command runShooterCommand(double speed) {
        SmartDashboard.putNumber("Shooter Active", speed);
        return run(() -> {
            m_shooterLeft.set(speed);
            m_shooterRight.set(speed);
        });
    }

    public void stopMotor() {
        m_shooterLeft.set(0);
        m_shooterRight.set(0);
    }
}

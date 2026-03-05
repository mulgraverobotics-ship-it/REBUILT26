package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private final SparkMax m_topLeftMotor;
    private final SparkMax m_topRightMotor;
    private final SparkMax m_bottomMotor;

    public ShooterSubsystem() {
        m_topLeftMotor = new SparkMax(ShooterConstants.shooterTopLeftCanId, MotorType.kBrushless);
        m_topRightMotor = new SparkMax(ShooterConstants.shooterTopRightCanId, MotorType.kBrushless);
        m_bottomMotor = new SparkMax(ShooterConstants.shooterBottomCanId, MotorType.kBrushless);

        SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake);

        m_topLeftMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_topRightMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_bottomMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setAllShooters(double speed) {
        m_topLeftMotor.set(speed);
        m_topRightMotor.set(speed);
        m_bottomMotor.set(speed);
        SmartDashboard.putNumber("Shooter/CommandedSpeed", speed);
    }

    public Command spinUpCommand(double speed) {
        return run(() -> setAllShooters(speed));
    }

    public void stopAll() {
        setAllShooters(0.0);
    }
}

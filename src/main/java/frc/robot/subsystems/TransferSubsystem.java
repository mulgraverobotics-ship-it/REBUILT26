package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TransferConstants;

public class TransferSubsystem extends SubsystemBase {
    private final SparkMax m_transferMotor;

    public TransferSubsystem() {
        m_transferMotor = new SparkMax(TransferConstants.transferCanId, MotorType.kBrushless);

        SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig
                .smartCurrentLimit(45)
                .idleMode(IdleMode.kBrake);

        m_transferMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setTransferSpeed(double speed) {
        m_transferMotor.set(speed);
        SmartDashboard.putNumber("Transfer/Cmd", speed);
    }

    public void stopTransfer() {
        setTransferSpeed(0.0);
    }
}

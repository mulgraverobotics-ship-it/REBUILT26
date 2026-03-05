package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax m_intakeMotor;

    public IntakeSubsystem() {
        m_intakeMotor = new SparkMax(IntakeConstants.IntakeMotorCanId, MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(40).idleMode(IdleMode.kBrake);
        m_intakeMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command runIntakeCommand(double speed) {
        return run(() -> m_intakeMotor.set(speed));
    }

    public void stop() {
        m_intakeMotor.set(0);
    }
}

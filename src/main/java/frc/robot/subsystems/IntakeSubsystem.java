package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax m_pivotMotor;
    private final SparkMax m_rollerMotor;

    public IntakeSubsystem() {
        m_pivotMotor = new SparkMax(IntakeConstants.intakePivotCanId, MotorType.kBrushless);
        m_rollerMotor = new SparkMax(IntakeConstants.intakeRollerCanId, MotorType.kBrushless);

        SparkMaxConfig pivotConfig = new SparkMaxConfig();
        pivotConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kBrake);

        SparkMaxConfig rollerConfig = new SparkMaxConfig();
        rollerConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake);

        m_pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_rollerMotor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setPivotSpeed(double speed) {
        m_pivotMotor.set(speed);
        SmartDashboard.putNumber("Intake/PivotCmd", speed);
    }

    public void stopPivot() {
        setPivotSpeed(0.0);
    }

    public void setRollerSpeed(double speed) {
        m_rollerMotor.set(speed);
        SmartDashboard.putNumber("Intake/RollerCmd", speed);
    }

    public void stopRoller() {
        setRollerSpeed(0.0);
    }

    public void stopAll() {
        stopPivot();
        stopRoller();
    }
}

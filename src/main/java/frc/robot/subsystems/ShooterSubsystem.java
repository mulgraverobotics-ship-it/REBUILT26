package frc.robot.subsystems;

// import com.revrobotics.AbsoluteEncoder;
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
    private final SparkMax m_rollerMotor;
    // private final RelativeEncoder m_encoder; // do we need it?

    public ShooterSubsystem() {
        // update the canID in Constants.java
        m_rollerMotor = new SparkMax(ShooterConstants.ShooterMotorNcanID, MotorType.kBrushless);


        SparkMaxConfig moterConfig = new SparkMaxConfig();

        moterConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake);

        m_rollerMotor.configure(moterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // m_encoder = m_rollerMotor.getEncoder();
        // encoder.setPosition(0);
    }

    public Command runShooterCommand(double speed) {
        return run(() -> {
            m_rollerMotor.set(speed);
        });

    }

}
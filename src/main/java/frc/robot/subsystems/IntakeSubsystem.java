package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax m_intakeMotor;
    private final SparkMax m_intakePivMotor;
    private  final SparkMax m_intakeRoller;
    private final RelativeEncoder m_intakePivEncoder;
    public double CurState;

    public IntakeSubsystem() {
        m_intakeMotor = new SparkMax(IntakeConstants.IntakeMotorCanId, MotorType.kBrushless);
        m_intakeRoller = new SparkMax(IntakeConstants.IntakeRollerCanID, MotorType.kBrushless);
        m_intakePivMotor = new SparkMax(IntakeConstants.IntakePivMotorCanID, MotorType.kBrushless);

        m_intakePivEncoder = m_intakePivMotor.getEncoder();

        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(40).idleMode(IdleMode.kBrake);

        m_intakeMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_intakePivMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        CurState = getPivPos();
        SmartDashboard.putNumber("Pivot State", CurState);

        
        
    }

    public double getPivPos(){
        return Math.abs(m_intakePivEncoder.getPosition());
    }

    public void setPivotState (double tarState){
        CurState = getPivPos();
        double speed = 0.0;

        if (tarState > CurState){
            speed = IntakeConstants.PivotSpeed*-1;
        }

        else if (tarState < CurState){
            speed = IntakeConstants.PivotSpeed;
        }

        else{
            
        }

        m_intakeMotor.set(speed);
        SmartDashboard.putNumber("Current Intake Pivot State", CurState);

    }

    public Command runIntakeCommand(double speed) {
        return run(() -> m_intakeMotor.set(speed));
    }

    public Command setPivotCommand(double tarState){
        return run(() -> setPivotState(tarState));
    }

    public Command MoveTo(double tarState){
        if (tarState == 0.0){
            return setPivotCommand(tarState).until(() -> aroundState(tarState)).andThen(() -> {m_intakePivMotor.set(0.0);});
        }
        else{
            return setPivotCommand(tarState).until(() -> aroundState(tarState)).andThen(() -> {m_intakePivMotor.set(0.0);}); 
        }
    }

    public boolean aroundState(double state){
        return aroundState(state, IntakeConstants.tol);
    }

    public boolean aroundState(double state, double tol){
        return MathUtil.isNear(state, getPivPos(), tol);
    }

    public Command MoveUp(){
        CurState = getPivPos();
        return run(() -> m_intakePivMotor.set(1* IntakeConstants.PivotSpeed));
    }

    public Command MoveDown(){
        CurState = getPivPos();
        return run(() -> m_intakePivMotor.set(1* IntakeConstants.PivotSpeed));
    }

    public Command runRollerCommand(double speed){
        return run(() -> m_intakeRoller.set(speed));
    }



    public void stop() {
        m_intakeMotor.set(0);
    }

    public void rollerStop(){
        m_intakeRoller.set(0);
    }
}

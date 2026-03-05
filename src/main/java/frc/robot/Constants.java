package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public final class Constants {
    // Drive system constants
    public static final class DriveConstants {

        //swerve brake coast config
        public static final int BrakeMode = 0;

        //speed  multiplier
        public static final double SpeedMulti = 1;

        //ramp rate
        public static final double dirRampVal = 8.0;
        public static final double magRampVal = 6.7;
        public static final double rotRampVal = 2.0;

        public static final double kMaxSpeedMetersPerSecond = 4.8;
        public static final double kMaxAngularSpeed = 2 * Math.PI;

        public static final double kTrackWidth = Units.inchesToMeters(27);
        public static final double kWheelBase = Units.inchesToMeters(27);

        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

        public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
        public static final double kFrontRightChassisAngularOffset = 0;
        public static final double kBackLeftChassisAngularOffset = Math.PI;
        public static final double kBackRightChassisAngularOffset = Math.PI / 2;

        public static final int kFrontLeftDrivingCanId = 3;
        public static final int kRearLeftDrivingCanId = 5;
        public static final int kFrontRightDrivingCanId = 1;
        public static final int kRearRightDrivingCanId = 7;

        public static final int kFrontLeftTurningCanId = 4;
        public static final int kRearLeftTurningCanId = 6;
        public static final int kFrontRightTurningCanId = 2;
        public static final int kRearRightTurningCanId = 8;

        public static final boolean kGyroReversed = false;
    }

    // Swerve module constants
    public static final class ModuleConstants {
        public static final int kDrivingMotorPinionTeeth = 14;
        public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
        public static final double kWheelDiameterMeters = 0.0762;
        public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;

        public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
        public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
                / kDrivingMotorReduction;
    }

    public static final class OIConstants {
        public static final int kDriverControllerPort = 0;
        public static final int kOperatorControllerPort = 1;
        public static final double kDriveDeadband = 0.2;
    }

    // Autonomous settings
    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;

        public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    }

    public static final class NeoMotorConstants {
        public static final double kFreeSpeedRpm = 5676;
    }

    public static final class ButtonConstants {
        public static final int xboxA = 1;
        public static final int xboxB = 2;
        public static final int xboxX = 3;
        public static final int xboxY = 4;

        public static final int xboxLB = 5;
        public static final int xboxRB = 6;
        public static final int xboxMAPS = 7;
        public static final int xboxLINES = 8;

        public static final int driverAimAssistButton = xboxRB;

        public static final int operatorIntakeInButton = xboxA;
        public static final int operatorIntakeOutButton = xboxB;
        public static final int operatorShooterSpinButton = xboxX;
        public static final int operatorShootAndFeedButton = xboxY;
        public static final int operatorIntakePivotUpButton = xboxLB;
        public static final int operatorIntakePivotDownButton = xboxRB;
        public static final int operatorGyroResetButton = xboxMAPS;
    }

    public static final class VisionConstants {
        public static final double kAimKp = 0.02;
        public static final double kAimDeadbandDeg = 1.0;
        public static final double kAimMaxTurnCmd = 0.45;
    }

    public static final class IntakeConstants {
        public static final int intakePivotCanId = 9;
        public static final int intakeRollerCanId = 22;

        public static final double pivotUpSpeed = 0.30;
        public static final double pivotDownSpeed = -0.25;
        public static final double intakeInSpeed = 0.85;
        public static final double intakeOutSpeed = -0.70;
    }

    public static final class TransferConstants {
        public static final int transferCanId = 20;

        public static final double transferToShooterSpeed = 0.85;
        public static final double transferReverseSpeed = -0.70;
    }

    public static final class ShooterConstants {
        // Based on your hardware layout: top-left, top-right, and shared bottom shooter
        public static final int shooterTopLeftCanId = 10;
        public static final int shooterTopRightCanId = 11;
        public static final int shooterBottomCanId = 21;

        public static final double shooterSpinSpeed = 0.85;
        public static final double shooterFeedSpeed = 1.0;
    }
}

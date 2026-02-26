package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
    private final NetworkTable limelightTable = 
        NetworkTableInstance.getDefault().getTable("limelight");

    @Override
    public void periodic() {
        double tv = limelightTable.getEntry("tv").getDouble(0);
        double tx = limelightTable.getEntry("tx").getDouble(0);
        double ty = limelightTable.getEntry("ty").getDouble(0);
        double tagID = limelightTable.getEntry("tid").getDouble(0);

        SmartDashboard.putNumber("LL/tv", tv);
        SmartDashboard.putNumber("LL/tx_deg", tx);
        SmartDashboard.putNumber("LL/ty_deg", ty);
        SmartDashboard.putNumber("LL/tagID", tagID);

        SmartDashboard.putBoolean("LL/tableExists", limelightTable != null);
        SmartDashboard.putNumber("LL/rawTV", limelightTable.getEntry("tv").getDouble(-1));
    }
}
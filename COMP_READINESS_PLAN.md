# FRC 2026 REBUILT – 24h Competition Readiness Plan

This document captures a fast onboarding snapshot of the current codebase plus the key questions needed to unblock implementation work for auto and Limelight-assisted aiming.

## Current robot software status (from code review)

- Project is a Java WPILib command-based robot using a swerve drive subsystem (`DriveSubsystem`) and a shooter subsystem (`ShooterSubsystem`).
- Driver and operator controls are already split on two controllers in `RobotContainer`.
- Limelight data is already being read from NetworkTables in `VisionSubsystem` (`tv`, `tx`, `ty`, `tid`) and published to SmartDashboard.
- `getAutonomousCommand()` currently returns an empty `SequentialCommandGroup`, so auto is effectively not implemented.

## 24-hour feature priorities

1. **Baseline autonomous**
   - Add at least one robust "leave + score" routine, then a safe fallback "taxi" routine.
   - Use either timed open-loop auto (fastest) or path-based auto if calibration is already stable.
2. **Limelight-assisted aiming (driver assist)**
   - Add a command/button that rotates robot to reduce Limelight `tx` toward zero while driver controls translation.
   - Add clear fail-safe: if no target (`tv == 0`), command should gracefully return control (no oscillation).
3. **Tuning and ops hardening**
   - Add SmartDashboard entries for tuning constants (P gain, deadband, max rot assist).
   - Add clear operator controls and checklist for pit/field use.

## Questions needed immediately

Please answer these so implementation can start with minimal risk:

1. **Game strategy / scoring priority**
   - What is your must-have autonomous outcome for match 1? (e.g., taxi only, preload score + taxi, 2-piece attempt)
2. **Mechanism readiness**
   - Which mechanisms are physically ready now besides swerve + shooter? (intake, elevator, feeder/indexer)
3. **Shooter behavior**
   - Is shooter currently single-speed only, or does it need distance-based RPM selection?
4. **Limelight setup**
   - Which pipeline IDs and target types are finalized (AprilTag, retroreflective, neural)?
   - Is Limelight mounted and calibrated (height, pitch, roll) with repeatable `tx` readings?
5. **Drivetrain orientation conventions**
   - Is the current navX yaw convention validated on carpet for field-relative driving at all headings?
6. **Auto framework preference**
   - Do you want PathPlanner-based autos or command-only/timed autos for this event?
7. **Driver controls**
   - Which button should enable aim assist while held?
8. **Risk constraints**
   - Any features that are explicitly out-of-scope before comp day (to avoid churn)?

## Suggested implementation order (once answers are provided)

1. Add vision helper methods (`hasTarget`, `getTx`) and filtered tx.
2. Add a `LimelightAimAssistCommand` (rotation PID only, translation from joystick passthrough).
3. Bind aim-assist hold button in `RobotContainer`.
4. Add one guaranteed autonomous routine + one fallback.
5. Run quick driver practice and tune gains live with dashboard.

## Quick command references

- Deploy: `./gradlew deploy`
- Simulation: `./gradlew simulateJava`
- Build: `./gradlew build`

If Gradle wrapper download is blocked on this machine, build/test on the driver station laptop with WPILib + internet access.

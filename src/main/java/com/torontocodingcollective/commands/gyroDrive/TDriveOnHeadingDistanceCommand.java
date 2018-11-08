package com.torontocodingcollective.commands.gyroDrive;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.oi.TOi;
import com.torontocodingcollective.subsystem.TGyroDriveSubsystem;

/**
 * Drive on a specified heading and speed for a specified distance
 */
public class TDriveOnHeadingDistanceCommand extends TDriveOnHeadingCommand {

    double                            distanceInches = 0; // in inches
    private final TGyroDriveSubsystem driveSubsystem;

    /**
     * Construct a new DriveOnHeadingDistanceCommand
     * 
     * @param distanceInches
     * @param heading
     *            in the range 0 <= heading < 360. If the heading is not in this
     *            range, then the command will end immediately and print an error to
     *            the DriverStation
     * @param speed
     *            at which to drive in the range 0 <= speed <= 1.0. if the speed is
     *            set to a very small value, the robot will not drive and the
     *            command will end on the timeout.
     * @param timeout
     *            the time after which this command will end automatically. A value
     *            of {@link TConst#NO_COMMAND_TIMEOUT} will be used as an infinite
     *            timeout.
     * @param oi
     *            that extend the TOi operator input class
     * @param driveSubsystem
     *            that extends the TGyroDriveSubsystem
     */
    public TDriveOnHeadingDistanceCommand(double distanceInches, double heading, double speed, double timeout,
            boolean brakeWhenFinished, TOi oi, TGyroDriveSubsystem driveSubsystem) {

        super(heading, speed, timeout, brakeWhenFinished, oi, driveSubsystem);

        this.driveSubsystem = driveSubsystem;
        this.distanceInches = distanceInches;
    }

    @Override
    protected void initialize() {
        super.initialize();
        driveSubsystem.resetEncoders();
    }

    @Override
    protected boolean isFinished() {

        if (super.isFinished()) {
            return true;
        }

        if (driveSubsystem.getDistanceInches() > distanceInches) {
            return true;
        }

        return false;
    }
}

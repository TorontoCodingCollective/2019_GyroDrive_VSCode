package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.gyro.TAnalogGyro;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.subsystem.TGyroDriveSubsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.drive.DefaultDriveCommand;

/**
 * Chassis Subsystem
 * <p>
 * This class is describes all of the components in a differential (left/right)
 * drive subsystem.
 */
public class DriveSubsystem extends TGyroDriveSubsystem {

    private static final boolean LOW_GEAR     = false;
    private static final boolean HIGH_GEAR    = true;

    private Solenoid             shifter      = new Solenoid(RobotMap.SHIFTER_PNEUMATIC_PORT);
    private boolean              turboEnabled = false;

    public DriveSubsystem() {

        super(
                // Left Speed Controller
                new TCanSpeedController(
                        RobotMap.LEFT_DRIVE_SPEED_CONTROLLER_TYPE,
                        RobotMap.LEFT_DRIVE_SPEED_CONTROLLER_ADDRESS,
                        RobotMap.LEFT_DRIVE_FOLLOWER_SPEED_CONTROLLER_TYPE,
                        RobotMap.LEFT_DRIVE_FOLLOWER_SPEED_CONTROLLER_ADDRESS, RobotMap.LEFT_DRIVE_MOTOR_ISINVERTED),

                // Right Speed Controller
                new TCanSpeedController(
                        RobotMap.RIGHT_DRIVE_SPEED_CONTROLLER_TYPE,
                        RobotMap.RIGHT_DRIVE_SPEED_CONTROLLER_ADDRESS,
                        RobotMap.RIGHT_DRIVE_FOLLOWER_SPEED_CONTROLLER_TYPE,
                        RobotMap.RIGHT_DRIVE_FOLLOWER_SPEED_CONTROLLER_ADDRESS, RobotMap.RIGHT_DRIVE_MOTOR_ISINVERTED),

                // Gyro used for this subsystem
                new TAnalogGyro(RobotMap.GYRO_PORT),

                // Gyro PID Constants
                RobotConst.DRIVE_GYRO_PID_KP, RobotConst.DRIVE_GYRO_PID_KI, RobotConst.DRIVE_MAX_ROTATION_OUTPUT);

        // Get the encoders attached to the CAN bus speed controller and set the
        // inversion
        TEncoder leftEncoder = ((TCanSpeedController) super.leftMotor).getEncoder();
        leftEncoder.setInverted(RobotMap.LEFT_DRIVE_ENCODER_ISINVERTED);

        TEncoder rightEncoder = ((TCanSpeedController) super.rightMotor).getEncoder();
        rightEncoder.setInverted(RobotMap.RIGHT_DRIVE_ENCODER_ISINVERTED);

        super.setEncoders(
                leftEncoder, rightEncoder, RobotConst.ENCODER_COUNTS_PER_INCH, RobotConst.DRIVE_SPEED_PID_KP,
                RobotConst.MAX_LOW_GEAR_SPEED);
    }

    @Override
    public void init() {
        shifter.set(LOW_GEAR);
    };

    // Initialize the default command for the Chassis subsystem.
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DefaultDriveCommand());
    }

    // ********************************************************************************************************************
    // Turbo routines
    // ********************************************************************************************************************
    public void enableTurbo() {
        turboEnabled = true;
        setMaxEncoderSpeed(RobotConst.MAX_HIGH_GEAR_SPEED);
        shifter.set(HIGH_GEAR);
    }

    public void disableTurbo() {
        turboEnabled = false;
        setMaxEncoderSpeed(RobotConst.MAX_LOW_GEAR_SPEED);
        shifter.set(LOW_GEAR);
    }

    public boolean isTurboEnabled() {
        return turboEnabled;
    }

    @Override
    public void updatePeriodic() {
        super.updatePeriodic();

        SmartDashboard.putBoolean("Turbo Enabled", isTurboEnabled());
    }

}

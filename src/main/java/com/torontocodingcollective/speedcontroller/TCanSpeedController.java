package com.torontocodingcollective.speedcontroller;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.torontocodingcollective.TConst;
import com.torontocodingcollective.sensors.encoder.TCanEncoder;
import com.torontocodingcollective.sensors.encoder.TCanSparkEncoder;
import com.torontocodingcollective.sensors.encoder.TEncoder;

/**
 * TCanSpeedController controls one or more speed controllers connected to the
 * CAN interface on the RoboRio.
 * <p>
 * If more than one CAN port is specified, the same drive signal is sent to all
 * speed controllers
 * <p>
 * Care should be taken to ensure that the speed controllers are properly
 * connected and are all driving the motors in the same direction.
 */
public class TCanSpeedController extends TSpeedController {

    public enum TCanSpeedControllerType {

        /** Talon SRX see {@link TalonSRX} */
        TALON_SRX,
        /** Victor SPX see {@link VictorSPX} */
        VICTOR_SPX,
        /** SparkMax connected to a brushless motor */
        SPARK_MAX_BRUSHLESS,
        /** SparkMax connected to a brushed (non-NEO brushless) motor */
        SPARK_MAX_BRUSHED
    }

    private final BaseMotorController canCtreSpeedController;
    private final CANSparkMax         canSparkSpeedController;

    private double                    speedSetpoint = 0;

    /**
     * CAN Speed Controller
     * <p>
     * Supports any number of CAN speed controllers of the same type driving in the
     * same direction. The follower devices will be configured to follow the primary
     * CAN device. The speed will only ever be set on the primary CAN device.
     * <p>
     * If there is an encoder or limit switches attached to one of the devices, it
     * should be set as the primary device, not the follower. Encoders and limits
     * cannot be retrieved from follower devices.
     * 
     * @param controllerType
     *            a valid {@link TCanSpeedControllerType}
     * @param canAddress
     *            on the CAN bus, set using the CAN bus configuration tools
     * @param canFollowerAddresses
     *            optional list of follower CAN addresses
     */
    public TCanSpeedController(TCanSpeedControllerType controllerType, int canAddress, int... canFollowerAddresses) {
        this(controllerType, canAddress, TConst.NOT_INVERTED, canFollowerAddresses);
    }

    /**
     * CAN Speed Controller
     * <p>
     * Supports any number of CAN speed controllers of the same type driving in the
     * same direction. The follower devices will be configured to follow the primary
     * CAN device. The speed will only ever be set on the primary CAN device.
     * <p>
     * This constructor also supports motor inversion
     * <p>
     * If there is an encoder or limit switches attached to one of the devices, it
     * should be set as the primary device, not the follower. Encoders and limits
     * cannot be retrieved from follower devices.
     * 
     * @param controllerType
     *            a valid {@link TCanSpeedControllerType}
     * @param canAddress
     *            on the CAN bus, set using the CAN bus configuration tools
     * @param isInverted
     *            {@code true} if the motors are inverted, {@code false} otherwise
     * @param followerCanAddresses
     *            optional list of follower CAN addresses
     */
    public TCanSpeedController(TCanSpeedControllerType controllerType, int canAddress, boolean isInverted,
            int... followerCanAddresses) {

        super(isInverted);

        switch (controllerType) {
            case SPARK_MAX_BRUSHED:
            case SPARK_MAX_BRUSHLESS:
                canSparkSpeedController = newSparkController(controllerType, canAddress);
                canCtreSpeedController = null;
                for (int followerCanAddress : followerCanAddresses) {
                    CANSparkMax follower = newSparkController(controllerType, followerCanAddress);
                    follower.follow(canSparkSpeedController);
                }
                break;
            case TALON_SRX:
            case VICTOR_SPX:
            default:
                canSparkSpeedController = null;
                canCtreSpeedController = newCtreController(controllerType, canAddress);
                for (int followerCanAddress : followerCanAddresses) {
                    BaseMotorController follower = newCtreController(controllerType, followerCanAddress);
                    follower.follow(canCtreSpeedController);
                }
                break;
        }
    }

    /**
     * CAN Speed Controller
     * <p>
     * Supports two CAN speed controllers of different types. The follower devices
     * will be configured to follow the primary CAN device. The speed will only ever
     * be set on the primary CAN device.
     * <p>
     * If there is an encoder or limit switches attached to one of the devices, it
     * should be set as the primary device, not the follower. Encoders and limits
     * cannot be retrieved from follower devices.
     * 
     * @param controllerType
     *            a valid {@link TCanSpeedControllerType}
     * @param canAddress
     *            on the CAN bus, set using the CAN bus configuration tools
     * @param followerControllerType
     *            a valid {@link TCanSpeedControllerType}
     * @param followerCanAddress
     *            address on the CAN bus
     */
    public TCanSpeedController(TCanSpeedControllerType controllerType, int canAddress,
            TCanSpeedControllerType followerControllerType, int followerCanAddress) {
        this(controllerType, canAddress, followerControllerType, followerCanAddress, TConst.NOT_INVERTED);
    }

    /**
     * CAN Speed Controller
     * <p>
     * Supports two CAN speed controllers of different types. The follower devices
     * will be configured to follow the primary CAN device. The speed will only ever
     * be set on the primary CAN device.
     * <p>
     * This constructor also supports motor inversion
     * <p>
     * If there is an encoder or limit switches attached to one of the devices, it
     * should be set as the primary device, not the follower. Encoders and limits
     * cannot be retrieved from follower devices.
     * 
     * @param controllerType
     *            a valid {@link TCanSpeedControllerType}
     * @param canAddress
     *            on the CAN bus, set using the CAN bus configuration tools
     * @param followerControllerType
     *            a valid {@link TCanSpeedControllerType}
     * @param followerCanAddress
     *            address on the CAN bus
     * @param isInverted
     *            {@code true} if the motors are inverted, {@code false} otherwise
     */
    public TCanSpeedController(TCanSpeedControllerType controllerType, int canAddress,
            TCanSpeedControllerType followerControllerType, int followerCanAddress, boolean isInverted) {

        super(isInverted);

        switch (controllerType) {
            case SPARK_MAX_BRUSHED:
            case SPARK_MAX_BRUSHLESS:
                canSparkSpeedController = newSparkController(controllerType, canAddress);
                canCtreSpeedController = null;
                break;
            case TALON_SRX:
            case VICTOR_SPX:
            default:
                canSparkSpeedController = null;
                canCtreSpeedController = newCtreController(controllerType, canAddress);
                break;
        }

        switch (followerControllerType) {
            case SPARK_MAX_BRUSHED:
            case SPARK_MAX_BRUSHLESS:
                CANSparkMax sparkFollower = newSparkController(followerControllerType, followerCanAddress);
                if (canSparkSpeedController != null) {
                    sparkFollower.follow(canSparkSpeedController);
                }
                break;
        case TALON_SRX:
            case VICTOR_SPX:
            default:
                BaseMotorController ctreFollower = newCtreController(followerControllerType, followerCanAddress);
                if (canCtreSpeedController != null) {
                    ctreFollower.follow(canCtreSpeedController);
                }
                break;
        }
    }

    /**
     * Gets the speed currently set on the controller.
     * <p>
     * NOTE: There can be significant delay getting the speed from the controller so
     * this routine returns the last speed value sent to the controller. The actual
     * value on the CAN device is not returned.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public double get() {
        return speedSetpoint;
    }

    /**
     * Return an encoder with the same inversion setting as the motor
     * 
     * @return TEncoder attached to this TalonSRX, or {@code null} if this is not a
     *         TalonSRX device. The encoder is assumed to be a quadrature encoder.
     */
    @Override
    public TEncoder getEncoder() {

        if (this.canCtreSpeedController != null) {
            if (this.canCtreSpeedController instanceof TalonSRX) {
                return new TCanEncoder((TalonSRX) canCtreSpeedController, getInverted());
            }
        }
        if (this.canSparkSpeedController != null) {
            return new TCanSparkEncoder(canSparkSpeedController, getInverted());
        }
        return null;
    }

    /**
     * Get a new controller of the appropriate type at the given CAN address.
     * 
     * @param controllerType
     *            a valid {@link TCanSpeedControllerType}
     * @param canAddress
     *            a valid unique CAN address
     * @return TCanSpeedController of the correct type. By default, the speed
     *         controller will be a TalonSRX.
     */
    private CANSparkMax newSparkController(TCanSpeedControllerType controllerType, int canAddress) {

        switch (controllerType) {
        case SPARK_MAX_BRUSHED:
            return new CANSparkMax(canAddress, MotorType.kBrushed);
        case SPARK_MAX_BRUSHLESS:
        default:
            return new CANSparkMax(canAddress, MotorType.kBrushless);
        }
    }

    /**
     * Get a new controller of the appropriate type at the given CAN address.
     * 
     * @param controllerType
     *            a valid {@link TCanSpeedControllerType}
     * @param canAddress
     *            a valid unique CAN address
     * @return TCanSpeedController of the correct type. By default, the speed
     *         controller will be a TalonSRX.
     */
    private BaseMotorController newCtreController(TCanSpeedControllerType controllerType, int canAddress) {

        switch (controllerType) {
        case VICTOR_SPX:
            return new VictorSPX(canAddress);
        case TALON_SRX:
        default:
            return new TalonSRX(canAddress);
        }
    }

    @Override
    public void set(double speed) {

        speedSetpoint = speed;

        if (getInverted()) {
            speed = -speed;
        }

        if (canCtreSpeedController != null) {
            canCtreSpeedController.set(ControlMode.PercentOutput, speed);
        }
        else {
            canSparkSpeedController.set(speed);
        }
    }

}

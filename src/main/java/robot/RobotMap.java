package robot;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.speedcontroller.TCanSpeedController.TCanSpeedControllerType;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * <p>
 * This map is intended to define the wiring only.  Robot constants should
 * be put in {@link RobotConst}
 */
public class RobotMap {


	//******************************************
	// Speed Controllers 
	// PWM and CAN addresses
	//******************************************
	public static final int 	     LEFT_DRIVE_SPEED_CONTROLLER_ADDRESS;          
	public static final TCanSpeedControllerType
	                        	     LEFT_DRIVE_SPEED_CONTROLLER_TYPE;
	public static final int 	     LEFT_DRIVE_FOLLOWER_SPEED_CONTROLLER_ADDRESS; 
	public static final TCanSpeedControllerType
                                     LEFT_DRIVE_FOLLOWER_SPEED_CONTROLLER_TYPE;
	public static final boolean      LEFT_DRIVE_MOTOR_ISINVERTED;

	public static final int 	     RIGHT_DRIVE_SPEED_CONTROLLER_ADDRESS;          
	public static final TCanSpeedControllerType
	                        	     RIGHT_DRIVE_SPEED_CONTROLLER_TYPE;
	public static final int 	     RIGHT_DRIVE_FOLLOWER_SPEED_CONTROLLER_ADDRESS; 
	public static final TCanSpeedControllerType
                            	     RIGHT_DRIVE_FOLLOWER_SPEED_CONTROLLER_TYPE;
	public static final boolean      RIGHT_DRIVE_MOTOR_ISINVERTED;
	
	//******************************************
	// Encoders
	//******************************************
	public static final boolean      LEFT_DRIVE_ENCODER_ISINVERTED;
	public static final boolean      RIGHT_DRIVE_ENCODER_ISINVERTED;

	//******************************************
	// Gyro Ports
	//******************************************
	public static final int 	     GYRO_PORT; 
	
	//******************************************
	// Pneumatics Ports
	//******************************************
	public static final int          SHIFTER_PNEUMATIC_PORT = 0;

	// Initializers if this code will be deployed to more than one
	// robot with different mappings
	static {
		
		switch (RobotConst.robot) {

		case RobotConst.TEST_ROBOT:
		default:
			LEFT_DRIVE_SPEED_CONTROLLER_ADDRESS           = 0;
			LEFT_DRIVE_SPEED_CONTROLLER_TYPE              = TCanSpeedControllerType.TALON_SRX;
			LEFT_DRIVE_FOLLOWER_SPEED_CONTROLLER_ADDRESS  = 2;
			LEFT_DRIVE_FOLLOWER_SPEED_CONTROLLER_TYPE     = TCanSpeedControllerType.TALON_SRX;
			LEFT_DRIVE_MOTOR_ISINVERTED                   = TConst.INVERTED;
			LEFT_DRIVE_ENCODER_ISINVERTED                 = TConst.INVERTED;

			RIGHT_DRIVE_SPEED_CONTROLLER_ADDRESS          = 1;
			RIGHT_DRIVE_SPEED_CONTROLLER_TYPE             = TCanSpeedControllerType.TALON_SRX;
			RIGHT_DRIVE_FOLLOWER_SPEED_CONTROLLER_ADDRESS = 7;
			RIGHT_DRIVE_FOLLOWER_SPEED_CONTROLLER_TYPE    = TCanSpeedControllerType.VICTOR_SPX;
			RIGHT_DRIVE_MOTOR_ISINVERTED                  = TConst.NOT_INVERTED;
			RIGHT_DRIVE_ENCODER_ISINVERTED                = TConst.NOT_INVERTED;

			GYRO_PORT                                     = 0;    
		}
	}
}

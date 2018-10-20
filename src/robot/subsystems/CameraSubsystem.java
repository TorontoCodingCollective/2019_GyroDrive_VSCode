package robot.subsystems;

import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.CameraServer;

/**
 *
 */
public class CameraSubsystem extends TSubsystem {

	public CameraSubsystem() {
		
		// Uncomment this line to start a USB camera feed
		// CameraServer.getInstance().startAutomaticCapture();
	}
	
	@Override
	public void init() {
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
	}


	@Override
	protected void initDefaultCommand() {
	}

}

package com.torontocodingcollective.commands;

import com.torontocodingcollective.commands.gyroDrive.TRotateToHeadingCommand;
import com.torontocodingcollective.oi.TOi;
import com.torontocodingcollective.subsystem.TDriveSubsystem;
import com.torontocodingcollective.subsystem.TGyroDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Default Drive Command for Game Controllers
 * <p>
 * Implements the following basic controls for the driver. 
 * <p> Tank, Arcade or Single Stick drive
 * <br> Back Button to cancel a command
 * <br> Start Button to reset the gyro and encoders
 * <br> POV to rotate to angle
 */
public class TDefaultDriveCommand extends Command {

	private final TOi oi;
	private final TDriveSubsystem driveSubsystem;
	private final TGyroDriveSubsystem gyroDriveSubsystem;
	
	public TDefaultDriveCommand(TOi oi, TDriveSubsystem driveSubsystem) {
		requires(driveSubsystem);
		
		this.driveSubsystem = driveSubsystem;
		this.oi = oi;
		
		if (driveSubsystem instanceof TGyroDriveSubsystem) {
			gyroDriveSubsystem = (TGyroDriveSubsystem) driveSubsystem;
		} else {
			gyroDriveSubsystem = null;
		}
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {

		// Process all standard driver buttons before
		// driving the robot.
		
		// Reset encoders
		if (oi.getReset()) {
			
			driveSubsystem.resetEncoders();

			if (gyroDriveSubsystem != null) {
				gyroDriveSubsystem.resetGyroAngle();
			}
		}

		// Enable or disable PID controllers on the 
		// drive motors
		if (oi.getSpeedPidEnabled()) {
			driveSubsystem.enableSpeedPids();
		}
		else {
			driveSubsystem.disableSpeedPids();
		}

		// If this is a gyro subsystem, 
		// then rotate to the heading
		if (gyroDriveSubsystem != null) {
			int heading = oi.getRotateToHeading();
			if (heading != -1) {
				Scheduler.getInstance().add(new TRotateToHeadingCommand(heading, oi, gyroDriveSubsystem));
			}
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}

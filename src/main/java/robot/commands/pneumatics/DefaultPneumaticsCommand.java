package robot.commands.pneumatics;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 *
 */
public class DefaultPneumaticsCommand extends Command {

    public DefaultPneumaticsCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.pneumaticsSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        if (Robot.oi.getCompressorEnabled()) {
            Robot.pneumaticsSubsystem.enableCompressor();
        } else {
            Robot.pneumaticsSubsystem.disableCompressor();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

}

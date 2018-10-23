package com.torontocodingcollective.commands;

import com.torontocodingcollective.TConst;
import com.torontocodingcollective.oi.TOi;

import edu.wpi.first.wpilibj.command.Command;

/**
 * TSafeCommand
 * <p>
 * This command is used as a base command for all other commands and supports
 * the features: <ls>
 * <li>end the command after a given timeout
 * <li>end the command when cancelled by the user </ls>
 */
public class TSafeCommand extends Command {

    private final TOi oi;

    /**
     * TSafeCommand
     * <p>
     * Construct a safe command with the default timeout
     * {@link TConst#DEFAULT_COMMAND_TIMEOUT }
     * 
     * @param oi
     *            the TOi object that defines the cancel operation
     *            {@link TOi#getCancelCommand()}
     */
    public TSafeCommand(TOi oi) {
        this(TConst.DEFAULT_COMMAND_TIMEOUT, oi);
    }

    /**
     * TSafeCommand
     * <p>
     * Construct a safe command with unlimited timeout
     * 
     * @param timeout
     *            the time after which this command will end automatically a value
     *            of {@link TConst#NO_COMMAND_TIMEOUT} will be used as an infinite
     *            timeout.
     * @param oi
     *            the TOi object that defines the cancel operation
     *            {@link TOi#getCancelCommand()}
     */
    public TSafeCommand(double timeout, TOi oi) {
        if (timeout >= 0) {
            super.setTimeout(timeout);
        }
        this.oi = oi;
    }

    @Override
    protected boolean isFinished() {
        if (isCancelled() || super.isTimedOut()) {
            return true;
        }
        return false;
    }

    /**
     * Is Command Cancelled
     * <p>
     * Returns {@code true} if the user cancels the command using operator controls
     * see {@link TOi#getCancelCommand()}
     * 
     * @return {@code true} if the command is to be cancelled, {@code false}
     *         otherwise.
     */
    public boolean isCancelled() {
        if (oi.getCancelCommand()) {
            return true;
        }
        return false;
    }
}

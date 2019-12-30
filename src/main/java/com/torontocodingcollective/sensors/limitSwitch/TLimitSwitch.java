package com.torontocodingcollective.sensors.limitSwitch;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * TLimitSwitch implements a limit switch with the supplied default state
 */
public class TLimitSwitch {

    public enum DefaultState {
        /** Digital input with {@code true} as the default state */
        TRUE,
        /** Digital input with {@code false} as the default state */
        FALSE
    }

    private final boolean     defaultState;

    public final DigitalInput limitSwitch;

    /**
     * Normally Open limit switch
     * Construct a normally open limit switch
     * <p>
     * Since the DIO port has a pull up resistor, the value
     * of the limit switch will be {@link DefaultState#TRUE}.
     * 
     * @param port dio port
     */
    public TLimitSwitch(int port) {
    	this(port, DefaultState.TRUE);
    }

    public TLimitSwitch(int port, DefaultState defaultState) {

        limitSwitch = new DigitalInput(port);

        if (defaultState == DefaultState.TRUE) {
            this.defaultState = true;
        } else {
            this.defaultState = false;
        }
    }

    public boolean atLimit() {
        return limitSwitch.get() != defaultState;
    }

}

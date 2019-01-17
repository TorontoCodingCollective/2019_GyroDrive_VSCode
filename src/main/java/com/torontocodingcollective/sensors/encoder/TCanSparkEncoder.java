package com.torontocodingcollective.sensors.encoder;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

/**
 * TCanEncoder reads a quadrature encoder plugged into a TalonSRX
 * <p>
 * Extends {@link TEncoder}
 */
public class TCanSparkEncoder extends TEncoder {

    private CANEncoder encoder;

    /**
     * Encoder constructor. Construct a Encoder given a TalonSRX device. 
     * The encoder must be a quadrature encoder plugged into the TalonSRX.
     * <p>
     * The encoder will be reset to zero when constructed
     * @param talonSRX where the quadrature encoder is attached
     * @param isInverted {@code true} if inverted, {@code false} otherwise
     */
    public TCanSparkEncoder(CANSparkMax canSparkMax, boolean isInverted) {
        super(isInverted);
        encoder = new CANEncoder(canSparkMax);
    }

    @Override
    public int get() {
        // Convert the raw counts
        return super.get((int) encoder.getPosition());
    }

    @Override
    public double getRate() {
        // Convert the raw rate
        return super.getRate((int) encoder.getVelocity());
    }

}

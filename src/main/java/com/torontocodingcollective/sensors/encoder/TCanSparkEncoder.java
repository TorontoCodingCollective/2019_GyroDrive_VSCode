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

    private double prevEncoderPosition;
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
        prevEncoderPosition = 0;
    }

    @Override
    public int get() {
    	
    	double encoderPos = encoder.getPosition();
    	
    	// Filter out glitches in the encoder
    	// reading where it can sometimes return zero.
    	if (encoderPos == 0) {
    		encoderPos = prevEncoderPosition;
    	}
    	prevEncoderPosition = encoderPos;
    	
        // Convert the raw counts from a double to 
    	// an encoder count.
        return super.get((int) (encoderPos * 64.0));
    }

    @Override
    public double getRate() {
        // Convert the raw rate
        return super.getRate((int) encoder.getVelocity());
    }

}

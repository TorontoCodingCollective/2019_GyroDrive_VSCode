package com.torontocodingcollective.sensors.encoder;

import edu.wpi.first.wpilibj.Encoder;

public class TPwmQuadEncoder extends TEncoder {

	Encoder encoder;
	
	/**
	 * Encoder constructor. Construct a Encoder given two pwm channels a and b.
	 * <p>
	 * The encoder is not inverted.
	 * @param pwmChannelA The a channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 * @param pwmChannelB The b channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 *
	 */
	public TPwmQuadEncoder(int pwmChannelA, int pwmChannelB) {
		this(pwmChannelA, pwmChannelB, false);
	}

	/**
	 * Encoder constructor. Construct a Encoder given two pwm channels a and b.
	 * <p>
	 * The encoder counts are inverted (negated) based on the isInverted parameter.
	 * @param pwmChannelA The a channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 * @param pwmChannelB The b channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 * @param isInverted  Inversion orientation of this encoder {@code true} if inverted, {@code false}
	 * otherwise.
	 */
	public TPwmQuadEncoder(int pwmChannelA, int pwmChannelB, boolean isInverted) {
		super(isInverted);
		this.encoder = new Encoder(pwmChannelA, pwmChannelB);
	}
	
	@Override
	public int get() {
		return super.get(encoder.get());
	}

	@Override
	public double getRate() {
		return super.getRate(encoder.getRate());
	}

}

package com.torontocodingcollective.speedcontroller;

public class TMotorSpeeds {

    public double left  = 0.0;
    public double right = 0.0;

    public TMotorSpeeds() {
    }

    public TMotorSpeeds(double leftSpeed, double rightSpeed) {
        this.left = leftSpeed;
        this.right = rightSpeed;
    }
}

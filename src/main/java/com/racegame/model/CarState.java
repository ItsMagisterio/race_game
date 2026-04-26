package com.racegame.model;

public class CarState {
    private float speedKmh;
    private boolean accelerating;
    private boolean braking;
    private boolean steerLeft;
    private boolean steerRight;

    public float getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(float speedKmh) { this.speedKmh = speedKmh; }

    public boolean isAccelerating() { return accelerating; }
    public void setAccelerating(boolean accelerating) { this.accelerating = accelerating; }

    public boolean isBraking() { return braking; }
    public void setBraking(boolean braking) { this.braking = braking; }

    public boolean isSteerLeft() { return steerLeft; }
    public void setSteerLeft(boolean steerLeft) { this.steerLeft = steerLeft; }

    public boolean isSteerRight() { return steerRight; }
    public void setSteerRight(boolean steerRight) { this.steerRight = steerRight; }
}

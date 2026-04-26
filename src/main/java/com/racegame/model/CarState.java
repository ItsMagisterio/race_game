package com.racegame.model;

public class CarState {
    private float speedKmh;
    private boolean accelerating;
    private boolean reversing;
    private boolean braking;
    private boolean nitro;
    private boolean steerLeft;
    private boolean steerRight;
    private boolean cameraUp;
    private boolean cameraDown;
    private float cameraHeightOffset = 4.5f;

    public float getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(float speedKmh) { this.speedKmh = speedKmh; }

    public boolean isAccelerating() { return accelerating; }
    public void setAccelerating(boolean accelerating) { this.accelerating = accelerating; }

    public boolean isReversing() { return reversing; }
    public void setReversing(boolean reversing) { this.reversing = reversing; }

    public boolean isBraking() { return braking; }
    public void setBraking(boolean braking) { this.braking = braking; }

    public boolean isNitro() { return nitro; }
    public void setNitro(boolean nitro) { this.nitro = nitro; }

    public boolean isSteerLeft() { return steerLeft; }
    public void setSteerLeft(boolean steerLeft) { this.steerLeft = steerLeft; }

    public boolean isSteerRight() { return steerRight; }
    public void setSteerRight(boolean steerRight) { this.steerRight = steerRight; }

    public boolean isCameraUp() { return cameraUp; }
    public void setCameraUp(boolean cameraUp) { this.cameraUp = cameraUp; }

    public boolean isCameraDown() { return cameraDown; }
    public void setCameraDown(boolean cameraDown) { this.cameraDown = cameraDown; }

    public float getCameraHeightOffset() { return cameraHeightOffset; }
    public void setCameraHeightOffset(float cameraHeightOffset) { this.cameraHeightOffset = cameraHeightOffset; }
}

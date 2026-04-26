package com.racegame.config;

public final class GameConfig {
    private GameConfig() {}

    public static final float ACCELERATION = 32f;
    public static final float BRAKE_ACCELERATION = 45f;
    public static final float NATURAL_DECELERATION = 10f;
    public static final float MAX_FORWARD_SPEED = 95f;
    public static final float MAX_REVERSE_SPEED = -28f;
    public static final float BASE_STEER_SPEED = 1.9f;

    public static final float CAR_Y = 0.6f;
    public static final float CAMERA_LERP = 5f;
}

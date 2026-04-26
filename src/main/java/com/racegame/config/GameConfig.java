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
    public static final float CAMERA_LERP = 7.5f;
    public static final float CAMERA_HEIGHT_MIN = 2.0f;
    public static final float CAMERA_HEIGHT_MAX = 12.0f;

    public static final float UI_BASE_WIDTH = 1920f;
    public static final float UI_BASE_HEIGHT = 1080f;
    public static final float HUD_SIZE = 260f;
}

package com.racegame.libgdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Race Game - LibGDX Port");
        config.useVsync(true);
        config.setForegroundFPS(144);
        config.setWindowedMode(1600, 900);
        config.setBackBufferConfig(8, 8, 8, 8, 24, 8, 4);

        new Lwjgl3Application(new RacingGame(), config);
    }
}

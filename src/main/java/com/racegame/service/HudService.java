package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

import com.racegame.config.GameConfig;

import java.util.Locale;

public class HudService {
    private BitmapText speedText;

    public void initialize(AssetManager assetManager, BitmapFont guiFont, Node guiNode, int width, int height) {
        Picture speedometer = new Picture("speedometer");
        Texture2D texture = (Texture2D) assetManager.loadTexture("speedometer-car-tachometer.png");
        speedometer.setTexture(assetManager, texture, true);

        float uiScale = Math.min(width / GameConfig.UI_BASE_WIDTH, height / GameConfig.UI_BASE_HEIGHT);
        float size = GameConfig.HUD_SIZE * uiScale;
        speedometer.setWidth(size);
        speedometer.setHeight(size);
        speedometer.setPosition(35f * uiScale, 35f * uiScale);
        guiNode.attachChild(speedometer);

        speedText = new BitmapText(guiFont);
        speedText.setSize(guiFont.getCharSet().getRenderedSize() * (2.1f * uiScale));
        speedText.setColor(ColorRGBA.White);
        speedText.setLocalTranslation(110f * uiScale, 120f * uiScale, 1f);
        guiNode.attachChild(speedText);
    }

    public void updateSpeed(float speedKmh) {
        if (speedText != null) {
            speedText.setText(String.format(Locale.US, "%03d km/h", Math.round(Math.abs(speedKmh))));
        }
    }
}

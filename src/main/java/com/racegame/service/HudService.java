package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

import java.util.Locale;

public class HudService {
    private BitmapText speedText;

    public void initialize(AssetManager assetManager, BitmapFont guiFont, Node guiNode) {
        Picture speedometer = new Picture("speedometer");
        Texture2D texture = (Texture2D) assetManager.loadTexture("speedometer-car-tachometer.png");
        speedometer.setTexture(assetManager, texture, true);

        float size = 260f;
        speedometer.setWidth(size);
        speedometer.setHeight(size);
        speedometer.setPosition(35f, 35f);
        guiNode.attachChild(speedometer);

        speedText = new BitmapText(guiFont);
        speedText.setSize(guiFont.getCharSet().getRenderedSize() * 2.1f);
        speedText.setColor(ColorRGBA.White);
        speedText.setLocalTranslation(110f, 120f, 1f);
        guiNode.attachChild(speedText);
    }

    public void updateSpeed(float speedKmh) {
        if (speedText != null) {
            speedText.setText(String.format(Locale.US, "%03d km/h", Math.round(Math.abs(speedKmh))));
        }
    }
}

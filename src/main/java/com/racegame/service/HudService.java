package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import com.racegame.config.GameConfig;

public class HudService {
    private Node needlePivot;

    public void initialize(AssetManager assetManager, BitmapFont guiFont, Node guiNode, int width, int height) {
        Picture speedometer = new Picture("speedometer");
        Texture2D texture = (Texture2D) assetManager.loadTexture("speedometer-car-tachometer.png");
        speedometer.setTexture(assetManager, texture, true);

        float uiScale = Math.min(width / GameConfig.UI_BASE_WIDTH, height / GameConfig.UI_BASE_HEIGHT);
        float size = GameConfig.HUD_SIZE * uiScale;
        float x = 35f * uiScale;
        float y = 35f * uiScale;

        speedometer.setWidth(size);
        speedometer.setHeight(size);
        speedometer.setPosition(x, y);
        guiNode.attachChild(speedometer);

        float centerX = x + size * 0.5f;
        float centerY = y + size * 0.37f;

        needlePivot = new Node("needle-pivot");
        needlePivot.setLocalTranslation(centerX, centerY, 2f);

        float needleW = 6f * uiScale;
        float needleL = 88f * uiScale;
        Geometry needle = new Geometry("needle", new Quad(needleL, needleW));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        needle.setMaterial(mat);
        needle.setLocalTranslation(0f, -needleW / 2f, 0f);

        needlePivot.attachChild(needle);
        guiNode.attachChild(needlePivot);

        updateSpeed(0f);
    }

    public void updateSpeed(float speedKmh) {
        if (needlePivot == null) {
            return;
        }

        float clamped = FastMath.clamp(speedKmh, 0f, 260f);
        float t = clamped / 260f;
        float minAngle = FastMath.DEG_TO_RAD * -140f;
        float maxAngle = FastMath.DEG_TO_RAD * 45f;
        float angle = minAngle + (maxAngle - minAngle) * t;

        needlePivot.setLocalRotation(new Quaternion().fromAngleAxis(angle, com.jme3.math.Vector3f.UNIT_Z));
    }
}

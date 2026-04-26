package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;

public class NicknameService {

    public void attachNickname(AssetManager assetManager, Node carNode, String nickname) {
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText text = new BitmapText(font);
        text.setSize(font.getCharSet().getRenderedSize() * 1.1f);
        text.setColor(ColorRGBA.White);
        text.setText(nickname);
        text.center();

        Node billboardNode = new Node("nickname-billboard");
        billboardNode.setLocalTranslation(0f, 2.6f, 0f);
        billboardNode.addControl(new BillboardControl());
        billboardNode.attachChild(text);

        carNode.attachChild(billboardNode);
    }
}

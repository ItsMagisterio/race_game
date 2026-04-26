package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class SceneService {

    public void initializeScene(AssetManager assetManager, Node rootNode, ViewPort viewPort) {
        viewPort.setBackgroundColor(new ColorRGBA(0.53f, 0.79f, 0.98f, 1f));
        assetManager.registerLocator(".", FileLocator.class);
        addLight(rootNode);
        loadTrack(assetManager, rootNode);
    }

    private void addLight(Node rootNode) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.3f, -1f, 0.2f).normalizeLocal());
        sun.setColor(ColorRGBA.White.mult(1.4f));
        rootNode.addLight(sun);
    }

    private void loadTrack(AssetManager assetManager, Node rootNode) {
        Spatial track = assetManager.loadModel("race_track.glb");
        track.setLocalScale(1f);
        track.setShadowMode(RenderQueue.ShadowMode.Receive);
        rootNode.attachChild(track);
    }
}

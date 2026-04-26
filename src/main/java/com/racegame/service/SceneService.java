package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class SceneService {

    public void initializeScene(AssetManager assetManager, Node rootNode, ViewPort viewPort) {
        viewPort.setBackgroundColor(new ColorRGBA(0.53f, 0.79f, 0.98f, 1f));
        assetManager.registerLocator(".", FileLocator.class);
        addLights(rootNode);
        loadTrackWithFallback(assetManager, rootNode);
    }

    private void addLights(Node rootNode) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.3f, -1f, 0.2f).normalizeLocal());
        sun.setColor(ColorRGBA.White.mult(1.6f));
        rootNode.addLight(sun);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(ambient);
    }

    private void loadTrackWithFallback(AssetManager assetManager, Node rootNode) {
        try {
            Spatial track = assetManager.loadModel("race_track.glb");
            track.setLocalScale(1f);
            track.setShadowMode(RenderQueue.ShadowMode.Receive);
            rootNode.attachChild(track);
        } catch (Exception ex) {
            Geometry fallbackGround = new Geometry("fallback-ground", new Box(200f, 0.1f, 200f));
            Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            mat.setBoolean("UseMaterialColors", true);
            mat.setColor("Diffuse", new ColorRGBA(0.15f, 0.35f, 0.15f, 1f));
            mat.setColor("Ambient", new ColorRGBA(0.1f, 0.2f, 0.1f, 1f));
            fallbackGround.setMaterial(mat);
            rootNode.attachChild(fallbackGround);
        }
    }
}

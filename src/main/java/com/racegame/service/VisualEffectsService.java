package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.shadow.DirectionalLightShadowRenderer;

public class VisualEffectsService {

    public void setup(AssetManager assetManager, ViewPort viewPort) {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);

        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Scene);
        bloom.setBloomIntensity(0.95f);
        bloom.setExposurePower(1.15f);

        SSAOFilter ssao = new SSAOFilter(3.2f, 4.0f, 0.16f, 0.18f);

        fpp.addFilter(ssao);
        fpp.addFilter(bloom);
        viewPort.addProcessor(fpp);

        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 4096, 4);
        dlsr.setShadowIntensity(0.55f);
        dlsr.setLambda(0.6f);
        viewPort.addProcessor(dlsr);
    }
}

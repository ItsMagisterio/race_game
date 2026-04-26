package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.FXAAFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.shadow.DirectionalLightShadowRenderer;

public class VisualEffectsService {

    public void setup(AssetManager assetManager, ViewPort viewPort) {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);

        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Scene);
        bloom.setBloomIntensity(1.45f);
        bloom.setExposurePower(1.35f);

        SSAOFilter ssao = new SSAOFilter(4.2f, 5.6f, 0.2f, 0.38f);
        FXAAFilter fxaa = new FXAAFilter();

        fpp.addFilter(ssao);
        fpp.addFilter(bloom);
        fpp.addFilter(fxaa);
        viewPort.addProcessor(fpp);

        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 2048, 3);
        dlsr.setShadowIntensity(0.55f);
        dlsr.setLambda(0.6f);
        viewPort.addProcessor(dlsr);
    }
}

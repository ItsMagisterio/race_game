package com.racegame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.system.AppSettings;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.racegame.model.CarState;
import com.racegame.service.CameraService;
import com.racegame.service.CarService;
import com.racegame.service.HudService;
import com.racegame.service.InputService;
import com.racegame.service.SceneService;

import java.awt.Dimension;
import java.awt.Toolkit;

public class RaceGameApp extends SimpleApplication {

    private final CarState carState = new CarState();

    private final SceneService sceneService = new SceneService();
    private final CarService carService = new CarService();
    private final CameraService cameraService = new CameraService();
    private final HudService hudService = new HudService();

    private Node carNode;
    private boolean hudReady;
    private BulletAppState bulletAppState;

    public static void main(String[] args) {
        RaceGameApp app = new RaceGameApp();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        AppSettings settings = new AppSettings(true);
        settings.setResolution((int) screen.getWidth(), (int) screen.getHeight());
        settings.setFullscreen(false);
        settings.setTitle("Race Game");

        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setDisplayStatView(false);
        setDisplayFps(false);

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        sceneService.initializeScene(assetManager, rootNode, viewPort, bulletAppState.getPhysicsSpace());
        cam.setFrustumFar(5000f);
        carNode = carService.createCar(assetManager, rootNode, bulletAppState.getPhysicsSpace());

        InputService inputService = new InputService(carState);
        inputService.register(inputManager);

        hudService.initialize(assetManager, guiFont, guiNode, cam.getWidth(), cam.getHeight());
        hudReady = true;

        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(0, 5, -12));
        cam.lookAt(carNode.getWorldTranslation().add(0f, 1.5f, 0f), Vector3f.UNIT_Y);
    }

    @Override
    public void simpleUpdate(float tpf) {
        carService.updateSpeed(carState, tpf);
        carService.updateCarMovement(carNode, carState, tpf);
        cameraService.updateFollowCamera(cam, carNode, tpf);
        hudService.updateSpeed(carState.getSpeedKmh());
    }

    @Override
    public void reshape(int w, int h) {
        super.reshape(w, h);

        if (!hudReady || assetManager == null || guiFont == null || guiNode == null) {
            return;
        }

        guiNode.detachAllChildren();
        hudService.initialize(assetManager, guiFont, guiNode, w, h);
    }
}

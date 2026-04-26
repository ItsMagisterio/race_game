package com.racegame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;

import java.util.Locale;

public class RaceGameApp extends SimpleApplication implements ActionListener {

    private static final float ACCELERATION = 32f;
    private static final float BRAKE_ACCELERATION = 45f;
    private static final float NATURAL_DECELERATION = 10f;
    private static final float MAX_FORWARD_SPEED = 95f;
    private static final float MAX_REVERSE_SPEED = -28f;
    private static final float BASE_STEER_SPEED = 1.9f;

    private Node carNode;
    private float speedKmh = 0f;
    private boolean accelerating;
    private boolean braking;
    private boolean steerLeft;
    private boolean steerRight;
    private BitmapText speedText;

    public static void main(String[] args) {
        RaceGameApp app = new RaceGameApp();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setDisplayStatView(false);
        setDisplayFps(false);
        viewPort.setBackgroundColor(new ColorRGBA(0.53f, 0.79f, 0.98f, 1f));

        assetManager.registerLocator(".", FileLocator.class);

        setupInput();
        setupLight();
        loadTrack();
        loadCar();
        setupHud();

        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(0, 5, -12));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    @Override
    public void simpleUpdate(float tpf) {
        updateSpeed(tpf);
        updateCarMovement(tpf);
        updateCamera(tpf);
        updateHud();
    }

    private void setupInput() {
        inputManager.addMapping("Accelerate", new KeyTrigger(KeyInput.KEY_W), new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Brake", new KeyTrigger(KeyInput.KEY_S), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("SteerLeft", new KeyTrigger(KeyInput.KEY_A), new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("SteerRight", new KeyTrigger(KeyInput.KEY_D), new KeyTrigger(KeyInput.KEY_RIGHT));

        inputManager.addListener(this, "Accelerate", "Brake", "SteerLeft", "SteerRight");
    }

    private void setupLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.3f, -1f, 0.2f).normalizeLocal());
        sun.setColor(ColorRGBA.White.mult(1.4f));
        rootNode.addLight(sun);
    }

    private void loadTrack() {
        Spatial track = assetManager.loadModel("race_track.glb");
        track.setLocalScale(1f);
        track.setShadowMode(RenderQueue.ShadowMode.Receive);
        rootNode.attachChild(track);
    }

    private void loadCar() {
        carNode = new Node("player-car");
        Spatial carModel = assetManager.loadModel("bmw_racing_car.glb");

        carModel.setLocalScale(1f);
        carModel.rotate(0, FastMath.PI, 0);

        carNode.attachChild(carModel);
        carNode.setLocalTranslation(0f, 0.6f, 0f);
        rootNode.attachChild(carNode);
    }

    private void setupHud() {
        Picture speedometer = new Picture("speedometer");
        Texture texture = assetManager.loadTexture("speedometer-car-tachometer.png");
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

    private void updateSpeed(float tpf) {
        if (accelerating) {
            speedKmh += ACCELERATION * tpf;
        } else if (braking) {
            if (speedKmh > 0f) {
                speedKmh -= BRAKE_ACCELERATION * tpf;
            } else {
                speedKmh -= ACCELERATION * tpf;
            }
        } else {
            float sign = Math.signum(speedKmh);
            float decay = NATURAL_DECELERATION * tpf;
            if (Math.abs(speedKmh) <= decay) {
                speedKmh = 0f;
            } else {
                speedKmh -= decay * sign;
            }
        }

        speedKmh = FastMath.clamp(speedKmh, MAX_REVERSE_SPEED, MAX_FORWARD_SPEED);
    }

    private void updateCarMovement(float tpf) {
        if (carNode == null) {
            return;
        }

        float steerInput = 0f;
        if (steerLeft) {
            steerInput += 1f;
        }
        if (steerRight) {
            steerInput -= 1f;
        }

        float speedFactor = FastMath.clamp(Math.abs(speedKmh) / MAX_FORWARD_SPEED, 0.2f, 1f);
        float steerAmount = steerInput * BASE_STEER_SPEED * speedFactor * tpf;
        if (speedKmh < 0f) {
            steerAmount *= -0.75f;
        }

        carNode.rotate(0f, steerAmount, 0f);

        Vector3f forward = carNode.getLocalRotation().mult(Vector3f.UNIT_Z).normalizeLocal();
        float metersPerSecond = speedKmh / 3.6f;
        Vector3f movement = forward.mult(metersPerSecond * tpf);
        carNode.move(movement.x, 0f, movement.z);

        Vector3f p = carNode.getLocalTranslation();
        carNode.setLocalTranslation(p.x, 0.6f, p.z);
    }

    private void updateCamera(float tpf) {
        if (carNode == null) {
            return;
        }

        Vector3f target = carNode.getWorldTranslation();
        Quaternion rot = carNode.getWorldRotation();

        Vector3f behind = rot.mult(new Vector3f(0f, 4.5f, -11f));
        Vector3f desired = target.add(behind);

        Vector3f current = cam.getLocation();
        cam.setLocation(current.interpolateLocal(desired, FastMath.clamp(5f * tpf, 0f, 1f)));
        cam.lookAt(target.add(0f, 1.8f, 0f), Vector3f.UNIT_Y);
    }

    private void updateHud() {
        if (speedText != null) {
            speedText.setText(String.format(Locale.US, "%03d km/h", Math.round(Math.abs(speedKmh))));
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "Accelerate" -> accelerating = isPressed;
            case "Brake" -> braking = isPressed;
            case "SteerLeft" -> steerLeft = isPressed;
            case "SteerRight" -> steerRight = isPressed;
            default -> {
            }
        }
    }
}

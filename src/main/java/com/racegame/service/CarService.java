package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.racegame.config.GameConfig;
import com.racegame.model.CarState;

public class CarService {

    public Node createCar(AssetManager assetManager, Node rootNode) {
        Node carNode = new Node("player-car");
        Spatial carModel = assetManager.loadModel("bmw_racing_car.glb");
        carModel.setLocalScale(1f);

        carNode.attachChild(carModel);
        carNode.setLocalTranslation(0f, GameConfig.CAR_Y, 0f);
        rootNode.attachChild(carNode);
        return carNode;
    }

    public void updateSpeed(CarState state, float tpf) {
        float speed = state.getSpeedKmh();

        if (state.isAccelerating()) {
            speed += GameConfig.ACCELERATION * tpf;
        } else if (state.isBraking()) {
            speed -= (speed > 0f ? GameConfig.BRAKE_ACCELERATION : GameConfig.ACCELERATION) * tpf;
        } else {
            float sign = Math.signum(speed);
            float decay = GameConfig.NATURAL_DECELERATION * tpf;
            speed = Math.abs(speed) <= decay ? 0f : speed - decay * sign;
        }

        state.setSpeedKmh(FastMath.clamp(speed, GameConfig.MAX_REVERSE_SPEED, GameConfig.MAX_FORWARD_SPEED));
    }

    public void updateCarMovement(Node carNode, CarState state, float tpf) {
        float steerInput = (state.isSteerLeft() ? 1f : 0f) + (state.isSteerRight() ? -1f : 0f);
        float speedKmh = state.getSpeedKmh();

        float speedFactor = FastMath.clamp(Math.abs(speedKmh) / GameConfig.MAX_FORWARD_SPEED, 0.2f, 1f);
        float steerAmount = steerInput * GameConfig.BASE_STEER_SPEED * speedFactor * tpf;
        if (speedKmh < 0f) {
            steerAmount *= -0.75f;
        }
        carNode.rotate(0f, steerAmount, 0f);

        Vector3f forward = carNode.getLocalRotation().mult(Vector3f.UNIT_X).normalizeLocal();
        float metersPerSecond = speedKmh / 3.6f;
        Vector3f movement = forward.mult(metersPerSecond * tpf);
        carNode.move(movement.x, 0f, movement.z);

        Vector3f p = carNode.getLocalTranslation();
        carNode.setLocalTranslation(p.x, GameConfig.CAR_Y, p.z);
    }
}

package com.racegame.service;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.racegame.config.GameConfig;
import com.racegame.model.CarState;

public class CarService {

    private static final float CAR_MASS = 1200f;
    private static final float ENGINE_FORCE = 9000f;
    private static final float BRAKE_FORCE = 12000f;
    private static final float TURN_TORQUE = 2600f;

    private RigidBodyControl carBody;

    public Node createCar(AssetManager assetManager, Node rootNode, PhysicsSpace physicsSpace) {
        Node carNode = new Node("player-car");
        Spatial carModel;

        try {
            carModel = assetManager.loadModel("bmw_racing_car.glb");
            carModel.setLocalScale(1f);
            carModel.rotate(0f, FastMath.HALF_PI, 0f);
        } catch (Exception ex) {
            Geometry fallbackCar = new Geometry("fallback-car", new Box(1.2f, 0.6f, 2.4f));
            Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            mat.setBoolean("UseMaterialColors", true);
            mat.setColor("Diffuse", com.jme3.math.ColorRGBA.Blue);
            mat.setColor("Ambient", com.jme3.math.ColorRGBA.Blue.mult(0.4f));
            fallbackCar.setMaterial(mat);
            carModel = fallbackCar;
        }

        carNode.attachChild(carModel);
        carNode.setLocalTranslation(0f, GameConfig.CAR_Y + 2.2f, 0f);
        rootNode.attachChild(carNode);

        carBody = new RigidBodyControl(new BoxCollisionShape(new Vector3f(1.0f, 0.55f, 2.2f)), CAR_MASS);
        carBody.setDamping(0.2f, 0.85f);
        carBody.setAngularFactor(0.35f);
        carNode.addControl(carBody);
        physicsSpace.add(carBody);

        return carNode;
    }

    public void updateSpeed(CarState state, float tpf) {
        if (carBody == null) {
            state.setSpeedKmh(0f);
            return;
        }
        state.setSpeedKmh(carBody.getLinearVelocity().length() * 3.6f);
    }

    public void updateCarMovement(Node carNode, CarState state, float tpf) {
        if (carBody == null) return;

        Vector3f forward = carNode.getWorldRotation().mult(Vector3f.UNIT_Z).setY(0f).normalizeLocal();

        float speedKmh = state.getSpeedKmh();
        float signedSpeed = carBody.getLinearVelocity().dot(forward) * 3.6f;

        if (state.isAccelerating() && signedSpeed < GameConfig.MAX_FORWARD_SPEED) {
            carBody.applyCentralForce(forward.mult(ENGINE_FORCE));
        }

        if (state.isBraking()) {
            if (signedSpeed > 2f) {
                carBody.applyCentralForce(forward.negate().mult(BRAKE_FORCE));
            } else if (signedSpeed > GameConfig.MAX_REVERSE_SPEED) {
                carBody.applyCentralForce(forward.negate().mult(ENGINE_FORCE * 0.6f));
            }
        }

        if (!state.isAccelerating() && !state.isBraking()) {
            Vector3f vel = carBody.getLinearVelocity();
            carBody.setLinearVelocity(new Vector3f(vel.x * 0.995f, vel.y, vel.z * 0.995f));
        }

        if (Math.abs(speedKmh) > 2f) {
            float steer = 0f;
            if (state.isSteerLeft()) steer += 1f;
            if (state.isSteerRight()) steer -= 1f;
            if (steer != 0f) {
                float speedFactor = FastMath.clamp(speedKmh / GameConfig.MAX_FORWARD_SPEED, 0.25f, 1f);
                carBody.applyTorque(new Vector3f(0f, TURN_TORQUE * steer * speedFactor, 0f));
            }
        }
    }
}

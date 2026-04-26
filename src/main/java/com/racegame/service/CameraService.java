package com.racegame.service;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.racegame.config.GameConfig;
import com.racegame.model.CarState;

public class CameraService {

    public void updateFollowCamera(Camera cam, Node carNode, CarState state, float tpf) {
        float height = state.getCameraHeightOffset();
        if (state.isCameraUp()) {
            height += 6f * tpf;
        }
        if (state.isCameraDown()) {
            height -= 6f * tpf;
        }
        state.setCameraHeightOffset(FastMath.clamp(height, GameConfig.CAMERA_HEIGHT_MIN, GameConfig.CAMERA_HEIGHT_MAX));

        Vector3f target = carNode.getWorldTranslation();
        Quaternion rot = carNode.getWorldRotation();

        Vector3f behind = rot.mult(new Vector3f(0f, state.getCameraHeightOffset(), -12f));
        Vector3f desired = target.add(behind);

        Vector3f current = cam.getLocation();
        cam.setLocation(current.interpolateLocal(desired, FastMath.clamp(GameConfig.CAMERA_LERP * tpf, 0f, 1f)));
        cam.lookAt(target.add(0f, 1.8f, 0f), Vector3f.UNIT_Y);
    }
}

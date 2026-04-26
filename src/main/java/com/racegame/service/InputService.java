package com.racegame.service;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.racegame.model.CarState;

public class InputService implements ActionListener {
    private final CarState state;

    public InputService(CarState state) {
        this.state = state;
    }

    public void register(InputManager inputManager) {
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Reverse", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("SteerLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("SteerRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Nitro", new KeyTrigger(KeyInput.KEY_LSHIFT), new KeyTrigger(KeyInput.KEY_RSHIFT));
        inputManager.addMapping("Brake", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("CameraUp", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("CameraDown", new KeyTrigger(KeyInput.KEY_E));

        inputManager.addListener(this, "Forward", "Reverse", "SteerLeft", "SteerRight", "Nitro", "Brake", "CameraUp", "CameraDown");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "Forward" -> state.setAccelerating(isPressed);
            case "Reverse" -> state.setReversing(isPressed);
            case "Brake" -> state.setBraking(isPressed);
            case "Nitro" -> state.setNitro(isPressed);
            case "SteerLeft" -> state.setSteerLeft(isPressed);
            case "SteerRight" -> state.setSteerRight(isPressed);
            case "CameraUp" -> state.setCameraUp(isPressed);
            case "CameraDown" -> state.setCameraDown(isPressed);
            default -> {
            }
        }
    }
}

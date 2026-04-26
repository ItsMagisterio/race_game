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
        inputManager.addMapping("Accelerate", new KeyTrigger(KeyInput.KEY_W), new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Brake", new KeyTrigger(KeyInput.KEY_S), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("SteerLeft", new KeyTrigger(KeyInput.KEY_A), new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("SteerRight", new KeyTrigger(KeyInput.KEY_D), new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(this, "Accelerate", "Brake", "SteerLeft", "SteerRight");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "Accelerate" -> state.setAccelerating(isPressed);
            case "Brake" -> state.setBraking(isPressed);
            case "SteerLeft" -> state.setSteerLeft(isPressed);
            case "SteerRight" -> state.setSteerRight(isPressed);
            default -> {
            }
        }
    }
}

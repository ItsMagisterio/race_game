package com.racegame.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 * Порт на LibGDX (самый популярный Java-геймдвижок/фреймворк).
 * Визуал: PBR pipeline через gdx-gltf.
 */
public class RacingGame extends ApplicationAdapter {
    private PerspectiveCamera camera;
    private SceneManager sceneManager;
    private SceneAsset carAsset;
    private SceneAsset trackAsset;
    private Scene carScene;

    private float speed;

    @Override
    public void create() {
        camera = new PerspectiveCamera(67f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 6f, -12f);
        camera.lookAt(0f, 0f, 0f);
        camera.near = 0.1f;
        camera.far = 5000f;
        camera.update();

        sceneManager = new SceneManager();
        sceneManager.setCamera(camera);

        Environment env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.7f, 0.7f, 0.7f, 1f));
        env.add(new DirectionalLight().set(1f, 1f, 1f, -0.4f, -1f, 0.25f));
        sceneManager.setAmbientLight(0.7f);

        trackAsset = new GLBLoader().load(Gdx.files.absolute("race_track.glb"));
        Scene trackScene = new Scene(trackAsset.scene);
        sceneManager.addScene(trackScene);

        carAsset = new GLBLoader().load(Gdx.files.absolute("bmw_racing_car.glb"));
        carScene = new Scene(carAsset.scene);
        sceneManager.addScene(carScene);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        handleInput(dt);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0.53f, 0.79f, 0.98f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sceneManager.update(dt);
        sceneManager.render();
    }

    private void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) speed += 24f * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) speed -= 24f * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) speed *= 0.95f;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) speed += 30f * dt;

        speed *= 0.992f;

        if (carScene != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) carScene.modelInstance.transform.rotate(0, 1, 0, 75f * dt);
            if (Gdx.input.isKeyPressed(Input.Keys.D)) carScene.modelInstance.transform.rotate(0, 1, 0, -75f * dt);
            carScene.modelInstance.transform.translate(0f, 0f, speed * dt);

            if (Gdx.input.isKeyPressed(Input.Keys.Q)) camera.position.y += 8f * dt;
            if (Gdx.input.isKeyPressed(Input.Keys.E)) camera.position.y -= 8f * dt;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        sceneManager.updateViewport(width, height);
    }

    @Override
    public void dispose() {
        if (carAsset != null) carAsset.dispose();
        if (trackAsset != null) trackAsset.dispose();
        if (sceneManager != null) sceneManager.dispose();
    }
}

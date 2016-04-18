package com.mygdx.orbitals.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.helpers.MyGestureListener;
import com.mygdx.orbitals.sprites.Button;
import com.mygdx.orbitals.sprites.GameObject;
import com.mygdx.orbitals.helpers.PolyList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Conrad on 10/17/2015.
 */
public abstract class State {
    protected OrthographicCamera cam;
    private Texture background;
    protected Vector2 mouse;
    protected Vector2 mouse2;
    protected Vector2 tilt;
    protected Vector2 offset;
    protected PolyList<GameObject> gameObjects;
    List<GameObject> deadObjects;
    protected double timePassed;
    protected static int controls = 0;
    FPSLogger logger;
    private Vector3 tempVec;
    private boolean paused;

    protected Skin skin;
    protected Stage stage;

    protected Table table;

    Vector3 tiltCalibration;
    Matrix4 calibrationMatrix;

    protected State() {
        this(new Texture(Constants.DEFAULT_IMG));
    }

    protected State(Texture bg) {
        mouse = new Vector2();
        mouse2 = new Vector2();
        tilt = new Vector2();
        offset = new Vector2();
        gameObjects = new PolyList<GameObject>(GameObject.class);
        deadObjects = new ArrayList<GameObject>();
        timePassed = 0; //time that the state has been active
        logger = new FPSLogger();
        cam = new OrthographicCamera(GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT);
        cam.setToOrtho(false, GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT);
        background = bg;
        tempVec = new Vector3();
        paused = false;

        tiltCalibration = new Vector3();
        calibrationMatrix = new Matrix4();
    }

    protected void start() {

    }

    //Complete state's operations for each frame, where dt is the time that has passed since the last frame
    public void update(float dt, SpriteBatch batch) {
        if (Gdx.input.justTouched()) {
            for (GameObject go : getElements(Button.class)) {
                Button button = (Button)go;
                button.checkPressed(mouse);
            }
        }

        if (!paused) {
            handleInput();

            for (GameObject go : gameObjects) {
                go.update(dt);
                go.render(batch);
            }

            timePassed += dt;
        } else {
            for (GameObject go : gameObjects) {
                go.render(batch);
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT);
        sb.end();
        sb.setProjectionMatrix(cam.combined);
        //stage.act(Gdx.graphics.getDeltaTime());
        //stage.draw();
    }

    public void dispose() {
        for (GameObject go : gameObjects) {
            go.dispose();
        }

        background.dispose();
        stage.dispose();
    }

    //Determine results of inputs in current state
    protected void handleInput() {
        mouse.x = Gdx.input.getX();
        mouse.y = Gdx.input.getY();

        mouse2.x = Gdx.input.getX(1);
        mouse2.y = Gdx.input.getY(1);

        tilt.x = Gdx.input.getAccelerometerX();
        tilt.y = Gdx.input.getAccelerometerY();
    }

    public void addGameObject(GameObject go) {
        gameObjects.add(go);
    }

    public void removeGameObject(GameObject go) {
        deadObjects.add(go);
    }

    public void clearDead() {
        for (GameObject go : deadObjects) {
            go.dispose();
            gameObjects.remove(go);
        }
    }

    public Vector2 getMouse() {
        tempVec = cam.unproject(tempVec.set(mouse.x, mouse.y, 0));
        return new Vector2(tempVec.x, tempVec.y);
    }

    public Vector2 getMouse2() {
        tempVec = cam.unproject(tempVec.set(mouse2.x, mouse2.y, 0));
        return new Vector2(tempVec.x, tempVec.y);
    }
    public Vector2 getTilt() {
        //Vector3 tmp = new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
        //tmp.mul(calibrationMatrix);
        return new Vector2(tilt.x - offset.x, tilt.y - offset.y);
    }

    public static void changeControls() {
        controls += 1;
        if (controls > 3) {
            controls = 0;
        }
    }

    public static int getControls() {
        return controls;
    }

    public ArrayList<GameObject> getElements(Class<? extends GameObject> c) {
        return gameObjects.elements(c);
    }

    public double getTimePassed() {
        return timePassed;
    }

    public void setPaused(boolean pause) {
        paused = pause;
    }

    public void togglePaused() {
        setPaused(!paused);
    }
}
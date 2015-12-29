package com.mygdx.orbitals.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.sprites.GameObject;
import com.mygdx.orbitals.helpers.MyGestureListener;
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
    protected Vector2 tilt;
    protected PolyList<GameObject> gameObjects;
    List<GameObject> deadObjects;
    protected double timePassed;
    protected int controls = 0;
    FPSLogger logger;
    private Vector3 tempVec;


    protected State() {
        this(new Texture(Constants.DEFAULT_IMG));
    }

    protected State(Texture bg) {
        mouse = new Vector2();
        tilt = new Vector2();
        gameObjects = new PolyList<GameObject>(GameObject.class);
        deadObjects = new ArrayList<GameObject>();
        timePassed = 0; //time that the state has been active
        logger = new FPSLogger();
        cam = new OrthographicCamera(GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT);
        cam.setToOrtho(false, GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT);
        background = bg;
        tempVec = new Vector3();
    }

    protected void start() {
        Gdx.input.setInputProcessor(new GestureDetector(new MyGestureListener()));
    }

    //Complete state's operations for each frame, where dt is the time that has passed since the last frame
    public void update(float dt, SpriteBatch batch) {
        handleInput();

        for (GameObject go : gameObjects) {
            go.update(dt);
            go.render(batch);
        }

        timePassed += dt;
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0, GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT);
        sb.end();
    }

    public void dispose() {
        for (GameObject go : gameObjects) {
            go.dispose();
        }

        background.dispose();
    }

    //Determine results of inputs in current state
    protected void handleInput() {
        mouse.x = Gdx.input.getX();
        mouse.y = Gdx.input.getY();

        tilt.x = Gdx.input.getAccelerometerX() / 10;
        tilt.y = Gdx.input.getAccelerometerY() / 10;
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
    public Vector2 getTilt() { return new Vector2(tilt.x, tilt.y); }

    public void changeControls() {
        controls += 1;
        if (controls > 2) {
            controls = 0;
        }
    }

    public int getControls() {
        return controls;
    }

    public ArrayList<GameObject> getElements(Class<? extends GameObject> c) {
        return gameObjects.elements(c);
    }

    public double getTimePassed() {
        return timePassed;
    }
}
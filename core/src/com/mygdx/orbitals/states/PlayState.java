package com.mygdx.orbitals.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.sprites.BouncingObject;
import com.mygdx.orbitals.sprites.Center;
import com.mygdx.orbitals.sprites.Enemy;
import com.mygdx.orbitals.sprites.GameObject;
import com.mygdx.orbitals.sprites.Orbital;
import com.mygdx.orbitals.sprites.PowerUp;
import com.mygdx.orbitals.sprites.Spawner;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

/**
 * Created by Conrad on 10/17/2015.
 */
public class PlayState extends State {
    protected final Colliders[] colliders = {
            new Colliders(Orbital.class, BouncingObject.class),
            new Colliders(Center.class, BouncingObject.class)
    };

    protected BitmapFont font;
    protected float score;

    public PlayState() {
        super(new Texture(Constants.BACKGROUND_IMG));
        font = new BitmapFont();
        score = 0;
    }

    @Override
    protected void start() {
        super.start();
        new Center();
        new Spawner();
        offset = new Vector2(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());

        tiltCalibration = new Vector3(
                Gdx.input.getAccelerometerX(),
                Gdx.input.getAccelerometerY(),
                Gdx.input.getAccelerometerZ() );

        Vector3 tmp = new Vector3(0, 0, 1);
        Vector3 tmp2 = new Vector3(tiltCalibration).nor();
        Quaternion rotateQuaternion = new Quaternion().setFromCross(tmp, tmp2);
        Matrix4 m = new Matrix4(Vector3.Zero, rotateQuaternion, new Vector3(1f, 1f, 1f));
        calibrationMatrix = m.inv();
    }

    @Override
    public void update(float dt, SpriteBatch batch) {
        render(batch);
        super.update(dt, batch);
        checkCollisions();
        score += dt;

        if (Gdx.input.justTouched()) {
            Gdx.app.log("Compass", "X: " + Math.round(Gdx.input.getRoll()) + " Y: " + Math.round(Gdx.input.getPitch()) + " Z: " + Math.round(Gdx.input.getAzimuth()));
        }
        //        //Objects that die in collisions
//        List<GameObject> deadObjects = new ArrayList<GameObject>();
//
//        //Check for collisions between enemies and orbitals or center
//        for (GameObject enemy : gameObjects.elements(Enemy.class)) {
//            for (GameObject orbital : gameObjects.elements(Orbital.class)) {
//                if (enemy.getBounds().overlaps(orbital.getBounds())) {
//                    deadObjects.add(enemy);
//                }
//            }
//            for (GameObject center : gameObjects.elements(Center.class)) {
//                if (enemy.getBounds().overlaps(center.getBounds())) {
//                    GameStateManager.remove();
//                }
//            }
//        }
//
//        for (GameObject center : gameObjects.elements(Center.class)) {
//            for (GameObject powerUp : gameObjects.elements(PowerUp.class)) {
//                if (center.getBounds().overlaps(powerUp.getBounds())) {
//                    PowerUp pu = (PowerUp)powerUp;
//                    pu.activate();
//                }
//            }
//        }
//        for (GameObject deadObject : deadObjects) {
//            deadObject.destroy();
//        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        sb.begin();
        font.draw(sb, Integer.toString(Math.round(score)), 10, GdxOrbitals.HEIGHT - 10, GdxOrbitals.WIDTH - 10, 1, true);
        sb.end();
    }

    private void checkCollisions() {
        for (Colliders cldrs : colliders) {
            for (GameObject x : gameObjects.elements(cldrs.getX())) {
                for (GameObject y : gameObjects.elements(cldrs.getY())) {
                    if (x.getBounds().overlaps(y.getBounds())) {
                        x.onCollision(y);
                        y.onCollision(x);
                    }
                }
            }
        }
        clearDead();
    }
}

class Colliders {
    private Class<? extends GameObject> x;
    private Class<? extends GameObject> y;

    Colliders() {
        this(GameObject.class, GameObject.class);
    }

    Colliders(Class<? extends GameObject> x, Class<? extends GameObject> y) {
        this.x = x;
        this.y = y;
    }

    public Class<? extends GameObject> getX() {
        return x;
    }

    public Class<? extends GameObject> getY() {
        return y;
    }
}

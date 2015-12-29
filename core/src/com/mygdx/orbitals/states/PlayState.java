package com.mygdx.orbitals.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    private final Colliders[] colliders = {
            new Colliders(Enemy.class, Orbital.class),
            new Colliders(Center.class, BouncingObject.class)
    };

    public PlayState() {
        super(new Texture(Constants.BACKGROUND_IMG));
    }

    @Override
    protected void start() {
        super.start();
        new Center();
        new Spawner();
    }

    @Override
    public void update(float dt, SpriteBatch batch) {
        render(batch);
        super.update(dt, batch);
        checkCollisions();


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
    Class<? extends GameObject> x;
    Class<? extends GameObject> y;

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

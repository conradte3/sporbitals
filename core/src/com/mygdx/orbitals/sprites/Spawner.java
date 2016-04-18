package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.states.GameStateManager;

/**
 * Created by Conrad on 11/3/2015.
 */
public class Spawner extends Enemy {
    private boolean hasFirstOrb = false;
    private double enemyTime = 0;
    private double orbTime = 0;

    public Spawner() {
        this(new Vector2(GdxOrbitals.WIDTH / 2f, GdxOrbitals.HEIGHT - 10));
    }

    public Spawner(Vector2 position) {
        super(position, new Texture(Constants.SPAWNER_IMG), new Vector2(0, -0.5f).rotateRad((float)(Math.random()*(Math.PI / 2f))), new Rectangle(0, GdxOrbitals.HEIGHT * 0.6f, GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT));
        new FreeOrbital(position);
        health = 10000;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        double currentTime = GameStateManager.getCurrent().getTimePassed();
        //Spawn enemies
        if (hasFirstOrb) {
            if (currentTime > enemyTime) {
                enemyTime = currentTime + 1.5*Math.random() + 1;
                new Enemy(position);
            }
        }

        if (currentTime > orbTime) {
            if (hasFirstOrb) {
                new FreeOrbital(position);
            }
            orbTime = currentTime + Math.random()*5 + 10;
        }
    }

    @Override
    public void onCollision(GameObject other) {
        super.onCollision(other);
        if (other.getClass().equals(Orbital.class)) {
            //((Center)GameStateManager.getCurrent().getElements(Center.class).get(0)).changeLevel(-1);
        }
    }

    public void setHasFirstOrb(boolean hasFirstOrb) {
        this.hasFirstOrb = hasFirstOrb;
    }
}

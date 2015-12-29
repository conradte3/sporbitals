package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;

/**
 * Created by Conrad on 10/18/2015.
 */
public class Enemy extends BouncingObject {
    public Enemy() {
        this(new Vector2((int)Math.floor(Math.random()* GdxOrbitals.WIDTH), GdxOrbitals.HEIGHT - 1));
    }

    public Enemy(Vector2 position) {
        super(position, PowerUp.isActive ? new Texture(Constants.ENEMY2_IMG) : new Texture(Constants.ENEMY_IMG));
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void onCollision(GameObject other) {
        super.onCollision(other);
        if (PowerUp.isActive) {
            if (other.getClass().equals(Center.class)) {
                destroy();
            }
        }

        if (other.getClass().equals(Orbital.class)) {
            destroy();
        }
    }
}

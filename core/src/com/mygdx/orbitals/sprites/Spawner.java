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
public class Spawner extends BouncingObject {
    public Spawner() {
        this(new Vector2(10, 10));
    }

    public Spawner(Vector2 position) {
        super(position, new Texture(Constants.SPAWNER_IMG), new Vector2(0.5f, 0).rotateRad((float)(Math.random()*Math.PI)), new Rectangle(0, 0, GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT / 2));
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        //Spawn enemies
        if(Math.random() < Math.min(GameStateManager.getCurrent().getTimePassed() / 15000, 0.02) + (dt / 2)) {
            new Enemy(new Vector2(position.x, position.y));
        }
    }
}

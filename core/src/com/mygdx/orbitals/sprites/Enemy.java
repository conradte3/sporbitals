package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.states.GameStateManager;

/**
 * Created by Conrad on 10/18/2015.
 */
public class Enemy extends BouncingObject {
    private float count = 0;
    private boolean waves = false;

    public Enemy() {
        this(new Vector2((int)Math.floor(Math.random()* GdxOrbitals.WIDTH), GdxOrbitals.HEIGHT - 1));
    }

    public Enemy(Vector2 position) {
        super(position, PowerUp.isActive ? new Texture(Constants.ENEMY2_IMG) : new Texture(Constants.ENEMY_IMG));
        if (Math.random() < 0.5) {
            waves = true;
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void move(float dt) {
        Vector2 v = new Vector2(velocity);

        /*homing
        Center c = (Center)GameStateManager.getCurrent().getElements(Center.class).get(0);
        double adj = position.x - c.getPosition().x;
        double opp = position.y - c.getPosition().y;
        double ang = Math.atan2(opp, adj);
        v.x = (float)Math.cos(ang) * -0.7f;
        v.y = (float)Math.sin(ang) * -0.7f;
        */

        /*waving
        v.rotateRad((float)Math.cos(count % (2 * Math.PI)));
        count += 0.1f;
        */

        /*circling
        v.rotateRad(count);// * (float) Math.cos(count % (2 * Math.PI)));
        count += 0.05f;
        */

        position.x += v.x*levelMod*speed*dt;
        position.y += v.y*levelMod*speed*dt;
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

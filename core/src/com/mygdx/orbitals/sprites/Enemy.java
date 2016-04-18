package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.states.GameStateManager;
import com.mygdx.orbitals.states.PlayState;

/**
 * Created by Conrad on 10/18/2015.
 */
public class Enemy extends BouncingObject {
    private float count = 0;
    protected enum MovementType { STANDARD, WAVING, HOMING, CIRCLING }
    protected MovementType movementType;
    protected boolean splits;
    protected int health = 1;

    public Enemy() {
        this(new Vector2((int)Math.floor(Math.random()* GdxOrbitals.WIDTH), GdxOrbitals.HEIGHT - 1));
    }

    public Enemy(Vector2 position) {
        this(position, MovementType.STANDARD);
    }

    public Enemy(Vector2 position, MovementType movementType) {
        this(position, movementType, false);
    }

    public Enemy(Vector2 position, MovementType movementType, boolean splits) {
        super(position, new Texture(Constants.ENEMY_IMG));
        this.movementType = movementType;
        this.splits = splits;
    }

    public Enemy(Vector2 position, Texture sprite, Vector2 velocity, Rectangle boundaries) {
        super(position, sprite, velocity, boundaries);
        this.movementType = MovementType.STANDARD;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void move(float dt) {
        Vector2 v = new Vector2(velocity);

        switch (movementType) {
            case WAVING:
                v.rotateRad((float)Math.cos(count % (2 * Math.PI)));
                count += 0.1f;
                break;
            case HOMING:
                Center c = (Center)GameStateManager.getCurrent().getElements(Center.class).get(0);
                double adj = position.x - c.getPosition().x;
                double opp = position.y - c.getPosition().y;
                double ang = Math.atan2(opp, adj);
                v.x = (float)Math.cos(ang) * -0.7f;
                v.y = (float)Math.sin(ang) * -0.7f;
                break;
            case CIRCLING:
                v.rotateRad(count);// * (float) Math.cos(count % (2 * Math.PI)));
                count += 0.05f;
                break;

        }

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
            PlayState ps = (PlayState)GameStateManager.getCurrent();
            ps.addScore(10);
            health -= 1;
            if (health <= 0) {
                destroy();
            }
        }
    }
}

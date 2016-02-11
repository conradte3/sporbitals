package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.states.GameStateManager;

/**
 * Created by Conrad on 10/23/2015.
 */
public class Orbital extends GameObject {
    private int id;
    private float angleOffset;
    private float targetOffset;
    private double currentRadius;

    public Orbital() {
        this(0);
    }

    public Orbital(int id) {
        super(new Texture(Constants.ORBITAL_IMG));
        this.id = id;
        angleOffset = id == 0 ? id : (float) (2 * Math.PI / (id + 1) * id);
        targetOffset = angleOffset;
        currentRadius = 0;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        angleOffset += (targetOffset - angleOffset) * 0.075;
    }

    public void updatePosition(Vector2 centerPos, double angle, double radius) {
        currentRadius += (radius - currentRadius) * 0.075;
        position.x = (float) (centerPos.x + Math.cos(angle + angleOffset) * currentRadius);
        position.y = (float) (centerPos.y + Math.sin(angle + angleOffset) * currentRadius);
    }

    public int getId() {
        return id;
    }

    public float getAngleOffset() {
        return angleOffset;
    }

    public void setAngleOffset(float offset) {
        targetOffset = offset;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void onCollision(GameObject other) {
        super.onCollision(other);
        if (PowerUp.isActive) {
            if (other.getClass().equals(Enemy.class)) {
                Center c = (Center)GameStateManager.getCurrent().getElements(Center.class).get(0);
                c.changeLevel(-1, id);
            }
        }
    }
}

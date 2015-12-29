package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.states.GameStateManager;

/**
 * Created by Conrad on 10/23/2015.
 */
public class Orbital extends GameObject {
    private static double powerMod = 1;
    private int id;
    private float angleOffset;

    public Orbital() {
        this(0);
    }

    public Orbital(int id) {
        super(new Texture(Constants.ORBITAL_IMG));
        this.id = id;
        angleOffset = id == 0 ? id : (float) (2 * Math.PI / (id + 1) * id);
    }

    public Orbital(int id, float angleOffset) {
        this.id = id;
        this.angleOffset = angleOffset;
    }

    public int getId() {
        return id;
    }

    public float getAngleOffset() {
        return angleOffset;
    }

    public void setAngleOffset(float angleOffset) {
        this.angleOffset = angleOffset;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setPowerMod(double powerMod) {
        Orbital.powerMod = powerMod;
    }

    public static double getPowerMod() {
        return powerMod;
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

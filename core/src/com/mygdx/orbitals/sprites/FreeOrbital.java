package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.helpers.Constants;

/**
 * Created by Conrad on 1/1/2016.
 */
public class FreeOrbital extends BouncingObject {
    FreeOrbital(Vector2 position) {
        super(position, new Texture(Constants.ORBITAL_IMG));
    }

    @Override
    public void onCollision(GameObject other) {
        super.onCollision(other);
        if (other.getClass().equals(Orbital.class)) {
            destroy();
        } else if (other.getClass().equals(Center.class)) {
            ((Center) other).changeLevel(1);
            destroy();
        }
    }
}

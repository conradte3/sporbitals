package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Conrad on 3/26/2016.
 */
public class Button extends GameObject {
    public Button(Vector2 position, Texture sprite) {
        super(position, sprite);
    }

    public void checkPressed(Vector2 pressPoint) {
        if (bounds.contains(pressPoint)) {
            press();
        }
    }

    public void press() {
    }
}

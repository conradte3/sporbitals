package com.mygdx.orbitals.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;

/**
 * Created by Conrad on 10/17/2015.
 */
public class MenuState extends State {
    public MenuState() {
        super(new Texture(Constants.MENU_IMG));
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            GameStateManager.add(new PlayState());
        }
    }

    @Override
    public void update(float dt, SpriteBatch batch) {
        handleInput();
        render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

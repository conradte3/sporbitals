package com.mygdx.orbitals.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.sprites.Button;
import com.mygdx.orbitals.sprites.Center;

/**
 * Created by Conrad on 10/17/2015.
 */
public class MenuState extends State {
    public MenuState() {
        super(new Texture(Constants.MENU_IMG));
    }

    protected TextButton startButton;
    protected TextButton quitButton;

    @Override
    protected void start() {
        super.start();

        Button testButton = new Button(new Vector2(GdxOrbitals.WIDTH / 2, GdxOrbitals.HEIGHT / 2), new Texture(Constants.CENTER_IMG)) {
            @Override
            public void press() {
                GameStateManager.add(new PlayState());
            }
        };
    }
}

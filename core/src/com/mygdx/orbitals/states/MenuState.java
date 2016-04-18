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
        //startButton = new TextButton("Play", skin);
        //quitButton = new TextButton("Quit like a scrub", skin);

        Button testButton = new Button(new Vector2(GdxOrbitals.WIDTH / 2, GdxOrbitals.HEIGHT / 2), new Texture(Constants.CENTER_IMG)) {
            @Override
            public void press() {
                Gdx.app.log("Button", "hello");
                GameStateManager.add(new PlayState());
            }
        };

        /*startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameStateManager.add(new PlayState());
                event.stop();
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                event.stop();
            }
        });

        startButton.pad(50);
        startButton.padLeft(100);
        startButton.padRight(100);

        quitButton.pad(50);

        table.padTop(GdxOrbitals.HEIGHT / 3f);

        table.add(startButton);
        table.row();
        table.add(quitButton);*/
    }
}

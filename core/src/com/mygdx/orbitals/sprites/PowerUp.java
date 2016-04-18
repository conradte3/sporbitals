package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.states.GameStateManager;

/**
 * Created by Conrad on 11/26/2015.
 */
public class PowerUp extends BouncingObject {
    public static boolean isActive = false;
    private double startTime = -1;

    PowerUp() {
        super(new Vector2((int)(Math.random()*GdxOrbitals.WIDTH), (int)(Math.random()*GdxOrbitals.HEIGHT)), new Texture(Constants.SPAWNER_IMG), new Vector2(0, 0));
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (startTime != -1) { //this powerup has been activated
            double activeTime = GameStateManager.getCurrent().getTimePassed() - startTime;

            if (activeTime > 8) { //near end
                if (activeTime > 10) { //end
                    deactivate();
                } else { //blink
                    if (activeTime % 0.4 < 0.1) {
                        for (GameObject enemy : GameStateManager.getCurrent().getElements(Enemy.class)) {
                            enemy.setSprite(new Texture(Constants.ENEMY_IMG));
                        }
                    } else {
                        for (GameObject enemy : GameStateManager.getCurrent().getElements(Enemy.class)) {
                            //enemy.setSprite(new Texture(Constants.ENEMY2_IMG));
                        }
                    }
                }
            }
        }
    }

    public void activate() {
        isActive = true;
        startTime = GameStateManager.getCurrent().getTimePassed();
        sprite = new Texture(Constants.DEFAULT_IMG);

        for (GameObject enemy : GameStateManager.getCurrent().getElements(Enemy.class)) {
            //enemy.setSprite(new Texture(Constants.ENEMY2_IMG));
        }

        for (GameObject power : GameStateManager.getCurrent().getElements(PowerUp.class)) {
            power.setSprite(new Texture(Constants.DEFAULT_IMG));
        }
    }

    public void deactivate() {
        isActive = false;

        for (GameObject enemy : GameStateManager.getCurrent().getElements(Enemy.class)) {
            enemy.setSprite(new Texture(Constants.ENEMY_IMG));
        }

        for (GameObject power : GameStateManager.getCurrent().getElements(PowerUp.class)) {
            //power.setSprite(new Texture(Constants.POWERUP_IMG));
        }

        destroy();
    }

    @Override
    public void onCollision(GameObject other) {
        super.onCollision(other);
        if (!PowerUp.isActive) {
            if (other.getClass().equals(Center.class)) {
                activate();
            }
        }
    }
}

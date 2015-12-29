package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.states.GameStateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conrad on 10/17/2015.
 */
public class Particle {
    /*private final int SPEED = 10;

    private Vector2 position;
    private Vector2 targetPos;
    private Texture proton;

    private List<Enemy> enemies;

    public Vector2 getPosition() {
        return position;
    }

    public Particle(int x, int y) {
        position = new Vector2(x, y);
        proton = new Texture("proton.png");
        enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(new Vector2(position.x, position.y)));
        enemies.get(enemies.size() - 1).setOwner(this);
    }

    public void update(float dt) {
        targetPos = GameStateManager.getCurrent().getMouse();
        position = position.lerp(targetPos, SPEED * dt);
        for (Enemy enemy : enemies) {
            enemy.update(dt);
        }
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(proton, position.x - proton.getWidth() / 2, position.y - proton.getHeight() / 2);
        sb.end();
        for (Enemy enemy : enemies) {
            enemy.render(sb);
        }
    }

    public void dispose() {
        proton.dispose();
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }

    public int GetNumE() {
        return enemies.size();
    }

    public void AddE(Enemy e) {
        enemies.add(e);
    }
*/
}

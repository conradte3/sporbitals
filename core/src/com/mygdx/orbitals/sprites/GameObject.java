package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.states.GameStateManager;
import com.badlogic.gdx.math.Circle;

import java.util.ArrayList;


/**
 * Created by Conrad on 10/23/2015.
 */
public abstract class GameObject {
    protected Vector2 position;
    protected Texture sprite;
    protected Circle bounds;
    protected float scale = GdxOrbitals.WIDTH / 450f;


    public GameObject() {
        this(new Vector2(0, 0));
    }

    public GameObject(Texture sprite) {
        this(new Vector2(0, 0), sprite);
    }

    public GameObject(Vector2 position) {
        this(position, new Texture(Constants.DEFAULT_IMG));
    }

    public GameObject(Vector2 position, Texture sprite) {
        this.position = new Vector2(position);
        this.sprite = sprite;
        bounds = new Circle(position.x, position.y, sprite.getWidth() * scale / 3);
        GameStateManager.getCurrent().addGameObject(this);
    }

    //Called every frame, dt is change in time since last frame
    public void update(float dt) {
        bounds.setPosition(position); //center collision circle on position
    }

    //Called every frame
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(sprite, position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2, sprite.getWidth() * scale, sprite.getHeight() * scale);
        sb.end();
    }

    //Called when destroyed
    public void dispose() {
        sprite.dispose();
    }

    //Called when this collides with another GameObject
    public void onCollision(GameObject other) {

    }

    //Removes this object from the current State
    public void destroy() {
        GameStateManager.getCurrent().removeGameObject(this);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Texture getSprite() {
        return sprite;
    }

    public void setSprite(Texture sprite) {
        this.sprite = sprite;
    }

    public Circle getBounds() {
        return bounds;
    }


}

package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;

/**
 * Created by Conrad on 11/3/2015.
 */
public abstract class BouncingObject extends GameObject {
    protected float speed = GdxOrbitals.HEIGHT / 3;
    protected static double levelMod = 1;
    protected static double powerMod = 1;
    protected Vector2 velocity;
    protected Rectangle boundaries;

    public BouncingObject() {
        this(new Vector2(0, 0), new Texture(Constants.DEFAULT_IMG));
    }

    public BouncingObject(Vector2 position, Texture sprite) {
        this(position, sprite, new Rectangle(0, 0, GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT));
    }

    public BouncingObject(Vector2 position, Texture sprite, Rectangle boundaries) {
        this(position, sprite, new Vector2(1, 0).rotateRad((float)(Math.random()*2*Math.PI)), boundaries);
    }

    public BouncingObject(Vector2 position, Texture sprite, Vector2 velocity) {
        this(position, sprite, velocity, new Rectangle(0, 0, GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT));
    }

    public BouncingObject(Vector2 position, Texture sprite, Vector2 velocity, Rectangle boundaries) {
        super(position, sprite);
        this.velocity = velocity;
        this.boundaries = boundaries;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        move(dt);

        if (position.x > boundaries.getWidth()) {
            velocity.x *= -1;
            position.x = boundaries.getWidth();
        } else if (position.x < boundaries.getX()) {
            velocity.x *= -1;
            position.x = boundaries.getX();
        }

        if (position.y > boundaries.getHeight()) {
            velocity.y *= -1;
            position.y = boundaries.getHeight();
        } else if (position.y < boundaries.getY()){
            velocity.y *= -1;
            position.y = boundaries.getY();
        }
    }

    public void move(float dt) {
        position.x += velocity.x*levelMod*speed*powerMod*dt;
        position.y += velocity.y*levelMod*speed*powerMod*dt;
    }

    public static void setLevelMod(double levelMod) {
        BouncingObject.levelMod = levelMod;
    }

    public static void setPowerMod(double powerMod) {
        BouncingObject.powerMod = powerMod;
    }
}

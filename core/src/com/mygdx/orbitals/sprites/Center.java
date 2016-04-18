package com.mygdx.orbitals.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.orbitals.GdxOrbitals;
import com.mygdx.orbitals.helpers.Constants;
import com.mygdx.orbitals.states.GameStateManager;
import com.mygdx.orbitals.states.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conrad on 10/23/2015.
 */
public class Center extends GameObject {
    private final int UP = 1, DOWN = -1;
    private double MAX_DIST = GdxOrbitals.HEIGHT / 2;
    private final double MAX_RADIUS = 128;

    private int level;

    private double radius;
    private double angle;
    private float incBy;
    private boolean orbAdded;
    private double speed = 1;
    private Vector2 direction;
    private Vector2 prevMousePos;
    private final Vector2 invalid = new Vector2(-1, -1);
    private Vector2 totalDistance = Vector2.Zero;
    private float totalTime = 0;


    //NEW MOVEMENT STUFF
    private Vector2 point;
    Texture dpad1 = new Texture(Constants.DPAD_IMG);
    Texture dpad2 = new Texture(Constants.DPAD2_IMG);


    private List<Orbital> orbitals;

    public Center() {
        super(new Vector2(GdxOrbitals.WIDTH / 2f, GdxOrbitals.HEIGHT / 2f), new Texture(Constants.CENTER_IMG));
        level = 0;
        radius = 128;
        angle = 0;
        incBy = -10f;
        orbitals = new ArrayList<Orbital>();
        point = position;
        direction = Vector2.Zero;
        prevMousePos = invalid;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        int controls = State.getControls();

        if (controls == 2) { //tilt
            speed = -2.5f;
            Vector2 tilt = GameStateManager.getCurrent().getTilt();
            position.x += tilt.x * speed;
            position.y += tilt.y * speed;
        } else if (controls == 4) { //fling
            Vector2 mouse = GameStateManager.getCurrent().getMouse();
            Vector2 distance = new Vector2();

            if (Gdx.input.isTouched()) {
                if (!prevMousePos.equals(invalid)) {
                    distance = new Vector2(mouse.x - prevMousePos.x, mouse.y - prevMousePos.y);
                    totalDistance.x += distance.x;
                    totalDistance.y += distance.y;
                    totalTime += dt;
                }

                if (totalTime >= 0.1) {
                    Vector2 velocity = new Vector2(totalDistance.x / totalTime, totalDistance.y / totalTime);
                    float magnitude = Vector2.len(velocity.x, velocity.y);
                    if (direction.equals(Vector2.Zero) && magnitude > 0) {
                        direction = velocity.nor();
                    }
                    speed = 0.001*magnitude;//0.3*Math.pow(Math.log10(magnitude + 1), 3);
                    totalDistance = Vector2.Zero;
                    totalTime = 0;
                }
                prevMousePos = new Vector2(mouse);
            } else {
                prevMousePos = invalid;
                direction = Vector2.Zero;

                if (speed > 0) {
                    speed -= 0.05;
                } else {
                    speed = 0;
                }
            }

            position.x += direction.x * speed;
            position.y += direction.y * speed;
        } else {
            speed = 0.04;
            //update position based on input
            Vector2 mouse = new Vector2();

            if (controls == 3) {
                mouse.x = GameStateManager.getCurrent().getMouse().x;
                mouse.y = GameStateManager.getCurrent().getMouse2().y;
            } else {
                mouse = GameStateManager.getCurrent().getMouse();
            }
            if (controls == 1) { //dpad
                if (Gdx.input.justTouched()) {
                    point = mouse;
                }
            } else { //standard
                point = position;
            }

            //get distance from spore to mouse
            double xdist = mouse.x - point.x;
            double ydist = mouse.y - point.y;

            double ang = Math.atan2(ydist, xdist);
            double dist = Math.hypot(xdist, ydist);

            if (controls == 1) { //dpad
                if (dist < 10) { //deadzone
                    dist = 0;
                }
            }

            double maxDist = speed * (Math.hypot(GdxOrbitals.WIDTH, GdxOrbitals.HEIGHT));

            //If the distance is greater than the max, get the angle and move x and y based on angle and max distance
            if (dist > maxDist) {
                dist = maxDist;
            }

            if (controls == 1) {
                dist = (Math.pow(dist, 1.35)) * speed;
            }

            //Move x and y
            position.x += (float) (dist * Math.cos(ang)) * (controls == 0 ? 0.2f : 1);
            position.y += (float) (dist * Math.sin(ang)) * (controls == 0 ? 0.2f : 1);

        }

        //update sporbitals
        incBy = -0.7f / (level * 3 + 5);
        angle += incBy * 60 * dt;

        if (orbAdded) {
            radius = (radius + 128) / 2;
            orbAdded = radius < 128;
        } else {
            if (PowerUp.isActive) {
                radius = MAX_RADIUS;
            } else {
                /*Spawner spawner = (Spawner)GameStateManager.getCurrent().getElements(Spawner.class).get(0);
                double dist = Math.hypot(position.x - spawner.getPosition().x, position.y - spawner.getPosition().y);
                dist = Math.min(dist, MAX_DIST);
                radius -= dt * 40 / level * Math.cos((dist / MAX_DIST) * Math.PI);

                if (radius <= 32) {
                    changeLevel(UP);
                } else if (radius >= MAX_RADIUS) {
                    radius = MAX_RADIUS;
                }*/
            }
        }

        //Stop at screen borders
        if (position.x > GdxOrbitals.WIDTH) {
            position.x = GdxOrbitals.WIDTH;
        }
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.y > GdxOrbitals.HEIGHT) {
            position.y = GdxOrbitals.HEIGHT;
        }
        if (position.y < 0) {
            position.y = 0;
        }

        updateOrbs();
    }

    private void updateOrbs() {
        for (Orbital orb : orbitals) {
            orb.updatePosition(position, angle, radius);
            //float x = (float) (position.x + Math.cos(angle + orb.getAngleOffset()) * radius);
            //float y = (float) (position.y + Math.sin(angle + orb.getAngleOffset()) * radius);
            //orb.setPosition(new Vector2(x, y));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (State.getControls() == 1) {
            sb.begin();
            sb.draw(dpad1, point.x - dpad1.getWidth() / 2, point.y - dpad1.getHeight() / 2);
            sb.draw(dpad2, point.x, point.y);
            sb.end();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        dpad1.dispose();
        dpad2.dispose();
    }

    public void changeLevel(int dir) {
        changeLevel(dir, orbitals.size() + Math.min(dir, 0));
    }

    public void changeLevel(int dir, int orbID) {
        Spawner spawner = (Spawner)GameStateManager.getCurrent().getElements(Spawner.class).get(0);

        level += dir;

        if (level <= 0) {
            PowerUp.isActive = false;
            for (GameObject go : GameStateManager.getCurrent().getElements(PowerUp.class)) {
                PowerUp pu = (PowerUp) go;
                pu.deactivate();
            }
            level = 0;
        }

        if (dir > 0) {
            if (level == 1) {
                spawner.setHasFirstOrb(true);
            }
            addOrbital();
        } else {
            removeOrbital(orbID);
        }

        BouncingObject.setLevelMod(level * 0.25 + 0.75);
    }

    public void addOrbital() {
        Orbital orb = new Orbital(orbitals.size());
        orbitals.add(orb);
        for (Orbital o : orbitals) {
            o.setAngleOffset(getOffset(o.getId()));
        }
        orbAdded = true;
    }

    public void removeOrbital(int id) {
        Orbital orb = orbitals.get(id);
        orbitals.remove(id);
        orb.destroy();

        for (int i = 0; i < orbitals.size(); i++) {
            orbitals.get(i).setId(i);
            orbitals.get(i).setAngleOffset(getOffset(i));
        }
    }

    private float getOffset(int position) {
        if (!orbitals.isEmpty()) {
            return (float) (2 * Math.PI / orbitals.size() * position);
        } else {
            return 0;
        }
    }

    @Override
    public void onCollision(GameObject other) {
        super.onCollision(other);
        if (!PowerUp.isActive) {
            if (other.getClass().equals(Enemy.class)) {
                GameStateManager.remove();
            }
        }
    }

    public void handleGestureInput(float velocityX, float velocityY, int button) {
        Gdx.app.log("Gesture", "Fling, velocityX: " + velocityX + ", velocityY: " + velocityY + ", button: " + button);
        /*if (State.getControls() == 4) {
            float magnitude = (float)Math.hypot(velocityX, velocityY);
            if (magnitude > 0) {
                direction = new Vector2(velocityX / magnitude, -velocityY / magnitude);
            }
            speed = 0.3*Math.pow(Math.log10(magnitude + 1), 3);
        }*/
    }
}

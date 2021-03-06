package com.mygdx.orbitals.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.orbitals.sprites.Center;
import com.mygdx.orbitals.states.GameStateManager;
import com.mygdx.orbitals.states.PlayState;
import com.mygdx.orbitals.states.State;

/**
 * Created by Conrad on 11/21/2015.
 */
public class MyGestureListener implements GestureDetector.GestureListener {

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        //State.changeControls();
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (GameStateManager.getCurrent().getClass().equals(PlayState.class)) {
            Center c = (Center) GameStateManager.getCurrent().getElements(Center.class).get(0);
            c.handleGestureInput(velocityX, velocityY, button);
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){
        return false;
    }
}

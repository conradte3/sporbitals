package com.mygdx.orbitals.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Conrad on 10/17/2015.
 */
public class GameStateManager {
    private static Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    //add a new state, which will become the current state
    public static void add(State state) {
        states.push(state);
        state.start();
    }

    //remove the current state
    public static void remove() {
        if(states.size() > 1) {
            states.peek().dispose();
            states.pop();
            states.peek().start();
        }
    }

    //Replace the current state with a new one
    public static void replaceCurrent(State state) {
        remove();
        add(state);
    }

    //update the current state
    public static void update(float dt, SpriteBatch batch) {
        states.peek().update(dt, batch);
    }

    public static State getCurrent() {
        return states.peek();
    }
}

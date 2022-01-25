package com.mygdx.game.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    
    private Stack<State> states;
    
    public GameStateManager()
    {
        states = new Stack<State>();
    }
    
    public void pop()
    {
        states.pop();
    }
    
    public void push(State state)
    {
        states.push(state);
    }
    
    public void set(State state)
    {
        State poppedState = states.pop();
        poppedState.dispose();
        states.push(state);
    }
    
    public void update(float deltaTime)
    {
        states.peek().update(deltaTime);
    }
    
    public void render(SpriteBatch spriteBatch)
    {
        states.peek().render(spriteBatch);
    }
    
}

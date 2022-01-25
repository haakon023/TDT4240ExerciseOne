package com.mygdx.game.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    
    protected OrthographicCamera camera;
    protected static Vector3 mousePos;
    protected GameStateManager gameStateManager;
    
    public State(GameStateManager stateManager)
    {
        this.gameStateManager = stateManager;
        camera = new OrthographicCamera();
        mousePos = new Vector3();
    }
    
    public static Vector3 getMousePos()
    {
        return mousePos ;
    }
    public abstract void handleInput();
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void update(float deltaTime);
    public abstract void dispose();
    
}

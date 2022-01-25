package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.gameobjects.Helicopter;
import com.mygdx.game.state.SoloPlayState;


public class Player extends Entity {
    
    private Helicopter helicopter;
    public Player()
    {
        helicopter = new Helicopter(50, 50, new Vector3(0.3f, 0.3f, 0.3f));
    }
    
    @Override
    public void update(float deltaTime) {
        handleInput();
        helicopter.update(deltaTime);
        if(helicopter.getSpeed() > 0) {
            helicopter.setSpeed(helicopter.getSpeed() - 50 * deltaTime);
        }
        if(helicopter.getSpeed() < 0)
            helicopter.setSpeed(0);
    }

    private void handleInput() {
        if(Gdx.input.justTouched()) {
            Vector3 newDirection = new Vector3(SoloPlayState.getMousePos().x - getPosition().x, (SoloPlayState.getMousePos().y - getPosition().y), 0).nor();
            helicopter.setSpeed(250);
            helicopter.setDirection(newDirection);
        }
    }

    @Override
    public void dispose() {
        helicopter.dispose();
    }

    @Override
    public TextureRegion getEntitySprite() {
        return helicopter.getHelicopterSprite();
    }

    @Override
    public Rectangle getBounds() {
        return helicopter.getBounds();
    }

    @Override
    public Vector3 getPosition() {
        return helicopter.getPosition();
    }

    @Override
    public void checkCollision(Entity other) {
        
    }
}

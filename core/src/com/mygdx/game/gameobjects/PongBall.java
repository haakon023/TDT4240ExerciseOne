package com.mygdx.game.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class PongBall {
    
    private final Rectangle bounds;
    private Vector2 direction;
    
    public PongBall(Rectangle bounds)
    {
        this.bounds = bounds;
        initializeDirection();
    }
    
    public void initializeDirection()
    {
        Random random = new Random();
        direction = new Vector2(random.nextBoolean() ? 1 : - 1 , random.nextBoolean() ? 1 : -1);
    }
    
    public void update(float deltaTime)
    {
        bounds.setPosition(new Vector2(bounds.getX(), bounds.getY()).mulAdd(direction, 250 * deltaTime));
    }
    
    public void setDirection(Vector2 direction)
    {
        this.direction = direction;
    }

    public boolean checkCollision(Rectangle bounds)
    {
        return getBounds().overlaps(bounds);
    }
    
    public Vector2 getDirection()
    {
        return direction;
    }
    
    public Rectangle getBounds()
    {
        return bounds;
    }
    
}

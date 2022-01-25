package com.mygdx.game.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.gameobjects.Helicopter;

import java.util.Random;
import java.util.Vector;

public class AIHelicopter extends Entity{

    private Helicopter helicopter;
    private float collisionAvoidanceDistance = 2;
    private Sound sound;
    private AssetManager manager;
    
    private float timeSinceFleed = 0;
    private boolean hasFleed;
    
    @Override
    public Rectangle getBounds()
    {
        return helicopter.getBounds();
    }
    
    public AIHelicopter()
    {
        Random r = new Random();
        double randomX = r.nextDouble() * MyGdxGame.WIDTH;
        double randomY = r.nextDouble() * MyGdxGame.HEIGHT;
        
        helicopter = new Helicopter((float)randomX, (float)randomY, chooseNewDirection());

        
        setHelicopterDirection();
    }

    private void setHelicopterDirection() {
        if(helicopter.getHelicopterSprite().isFlipX() && helicopter.getDirection().x < 0)
            helicopter.flipX();
        if(!helicopter.getHelicopterSprite().isFlipX() && helicopter.getDirection().x > 0)
            helicopter.flipX();

    }

    @Override
    public void update(float deltaTime) {
        helicopter.update(deltaTime);
        checkBounds();
        if(hasFleed)
            timeSinceFleed += deltaTime;
        
        if(timeSinceFleed > 0.25f) {
            timeSinceFleed = 0;
            hasFleed = false;
        }
        if(getMagnitude(helicopter.getDirection()) == 0)
            chooseNewDirection();
    }
    
    private double getMagnitude(Vector3 direction)
    {
        double magnitude = direction.x * direction.x + direction.y * direction.y;
        return Math.sqrt(magnitude);
    }
    
    private Vector3 chooseNewDirection()
    {
        Random r = new Random();
        double randomX = (r.nextInt(21)-10) / 10.0;
        double randomY = (r.nextInt(21)-10) / 10.0;
        double randomZ = (r.nextInt(21)-10) / 10.0;
        
        return new Vector3(
                (float)randomX,
                (float)randomY,
                (float)randomZ);
    }
    
    //one hundred percent retarded AI
    private void checkBounds()
    {
        Vector3 helicopterPosition = helicopter.getPosition();
        Vector2 screenBounds = new Vector2(MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        Vector3 direction = helicopter.getDirection();
        Vector3 velocity = helicopter.getVelocity();
        
        float distToVerticalEdge = 0;
        float distanceToHorizontalEdge = 0;
        
        if(direction.x < 0) //going to the left
            distanceToHorizontalEdge = helicopterPosition.x;
        else if(direction.x > 0)
            distanceToHorizontalEdge = screenBounds.x - helicopterPosition.x - getEntitySprite().getRegionWidth();
        
        if(direction.y < 0)
            distToVerticalEdge = helicopterPosition.y;
        else if(direction.y > 0)
            distToVerticalEdge = screenBounds.y - helicopterPosition.y - getEntitySprite().getRegionHeight();

        //pick the closest
        float estimatedTimeToVerticalEdge = distToVerticalEdge / Math.abs(velocity.x);
        float estimatedTimeToHorizontalEdge = distanceToHorizontalEdge / Math.abs(velocity.y);

        float timeToFirstEdge = 0;
        if(estimatedTimeToVerticalEdge < estimatedTimeToHorizontalEdge)
            timeToFirstEdge = estimatedTimeToVerticalEdge;
        else 
            timeToFirstEdge = estimatedTimeToHorizontalEdge;
        
        
        if(timeToFirstEdge < collisionAvoidanceDistance)
        {
            helicopter.setDirection(chooseNewDirection());
            setHelicopterDirection();
        }
    }
    
    public void checkCollision(Entity other)
    {
        
        if(helicopter.getBounds().overlaps(other.getBounds())) {
            Vector3 otherPosition = other.getPosition();
            Vector3 directionToOther = new Vector3(getPosition().x - otherPosition.x, getPosition().y - otherPosition.y, 0).nor();
            flee(directionToOther);
        }
    }

    private void flee(Vector3 direction) {
        if(hasFleed)
            return;
        
        hasFleed = true;
        setHelicopterDirection();
        helicopter.setDirection(direction);
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
    public Vector3 getPosition() {
        return helicopter.getPosition();
    }
}
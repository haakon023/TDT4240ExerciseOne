package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class Entity {
    
    public abstract void update(float deltaTime);
    public abstract void dispose();
    public abstract TextureRegion getEntitySprite();
    public abstract Rectangle getBounds();
    public abstract Vector3 getPosition();
    public abstract void checkCollision(Entity other);

}

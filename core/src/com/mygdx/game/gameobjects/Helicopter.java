package com.mygdx.game.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Animation;
import com.mygdx.game.MyGdxGame;


public class Helicopter {

    public Helicopter(float posX, float posY, Vector3 direction) {
        Texture texture = new Texture("spritesheet.png");
        helicopterAnimation = new Animation(new TextureRegion(texture), 4, 0.1f);
        
        bounds = new Rectangle(posX, posY, texture.getWidth() / 4, texture.getHeight());
        
        position = new Vector3(posX, posY, 0);
        velocity = new Vector3();
        manager = new AssetManager();
        manager.load("mygod.ogg", Sound.class);
        manager.finishLoading();
        
        //note to self, sounds sound like shit if there are multiple of the same sound
        //and played at the "exact" same time
        if(manager.isLoaded("mygod.ogg")) {
            helicopterSound = manager.get("mygod.ogg", Sound.class);
            helicopterSound.loop();
            long id = helicopterSound.play();
            //low pitch sound scary
            //the sound should probably be handled somewhere else so that there is max 1-3 of one type sound or whatever
            helicopterSound.setPitch(id, (float) Math.random());
            helicopterSound.setVolume(id, (float) Math.random());
        }
        this.direction = direction;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    
    public Vector3 getPosition()
    {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public TextureRegion getHelicopterSprite() {
        return helicopterAnimation.getFrame();
    }


    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public Vector3 getDirection() {
        return direction;
    }
    
    public void dispose()
    {
        helicopterSound.dispose();
        helicopterAnimation.dispose();
    }
    
    public void flipX()
    {
        helicopterAnimation.flipFrames();
    }
    
    public void setSpeed(float _speed)
    {
        speed = _speed;
    }
    
    public void update(float deltaTime)
    {
        helicopterAnimation.update(deltaTime);
        velocity = new Vector3(direction.x, direction.y, direction.z).scl(speed).scl(deltaTime);
        bounds.setPosition(position.x, position.y);
        position.add(velocity.x,velocity.y, 0);
        
        if(position.x > MyGdxGame.WIDTH - helicopterAnimation.getFrame().getRegionWidth())
            position.x = MyGdxGame.WIDTH - helicopterAnimation.getFrame().getRegionWidth();

        if(position.x < 0)
            position.x = 0;
        
        if(position.y < 0)
            position.y = 0;
        
        if(position.y > MyGdxGame.HEIGHT - helicopterAnimation.getFrame().getRegionHeight())
            position.y = MyGdxGame.HEIGHT - helicopterAnimation.getFrame().getRegionHeight();
            
    }

    private Vector3 position;

    private Vector3 direction;
    private Vector3 velocity;
    private final AssetManager manager;

    private Sound helicopterSound;
    private float speed = 250;

  

    private Rectangle bounds;    
    private Animation helicopterAnimation;

    public float getSpeed() {
        return speed;
    }
}

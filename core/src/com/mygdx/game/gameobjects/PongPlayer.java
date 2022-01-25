package com.mygdx.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.EllipseShapeBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

import org.graalvm.compiler.phases.common.ConditionalEliminationPhase;
import org.w3c.dom.css.Rect;

import sun.font.TrueTypeFont;

public class PongPlayer {

    private final PongInput input;
    private Rectangle bounds;
    private final int movementSpeed = 400;
    private boolean moveUp;
    private boolean moveDown;

    private int score = 0;
    
    public PongPlayer(PongInput input, Rectangle bounds)
    {
        this.bounds = bounds;
        this.input = input;
    }
    
    public void addScore(int newScore)
    {
        score += newScore;
    }
    
    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    
    public void setYPosition(float y)
    {
        bounds.setPosition(new Vector2(bounds.getX(), y));
    }
    
    public Rectangle getBounds()
    {
        return bounds;
    }
    
    public void update(float deltaTime)
    {
        handleInput();
        
        if(moveUp)
            bounds.setY(bounds.getY() + movementSpeed * deltaTime);
        
        if(moveDown)
            bounds.setY(bounds.getY() - movementSpeed * deltaTime);
        
        if(bounds.getY() < 0)
            bounds.setY(0);
        
        
        if(bounds.getY() > MyGdxGame.HEIGHT - bounds.height)
            bounds.setY(MyGdxGame.HEIGHT - bounds.height);
    }
    
    public void handleInput()
    {
        //move up
        moveUp = Gdx.input.isKeyPressed(input.getUP());

        //move down
        moveDown = Gdx.input.isKeyPressed(input.getDOWN());
    }
}


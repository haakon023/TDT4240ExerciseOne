package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.AIHelicopter;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.gameobjects.Helicopter;

import java.util.List;
import java.util.Stack;

import jdk.nashorn.internal.objects.NativeWeakMap;

public class AIPlayState extends State {

    private Texture background;
    private Stack<Entity> entities;
    
    public AIPlayState(GameStateManager stateManager) {
        super(stateManager);
        entities = new Stack<Entity>();
        entities.add(new AIHelicopter());
        entities.add(new AIHelicopter());
        entities.add(new AIHelicopter());
        entities.add(new AIHelicopter());
        entities.add(new AIHelicopter());
        entities.add(new AIHelicopter());
        entities.add(new AIHelicopter());
        background = new Texture("background.png");
        
        camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            gameStateManager.set(new MenuState(gameStateManager));
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        for(Entity entity : entities) {
            spriteBatch.draw(entity.getEntitySprite(), entity.getPosition().x, entity.getPosition().y);
        }
        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        for (Entity entity : entities) {
            entity.update(deltaTime);
            for(Entity otherEntities : entities) {
                if(otherEntities == entity)
                    continue;
                entity.checkCollision(otherEntities);
            }
        }
        
    }

    @Override
    public void dispose() {
        for (Entity entity : entities) {
            entity.dispose();
        }
    }
}

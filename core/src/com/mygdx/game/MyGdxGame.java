package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.state.GameStateManager;
import com.mygdx.game.state.MenuState;

public class MyGdxGame extends ApplicationAdapter {
	
	public static final int HEIGHT = 800;
	public static final int WIDTH = 960;
	public static final String TITLE = "My Amazing Fantastic Shit Game";
	
	private SpriteBatch spriteBatch;
	private GameStateManager gameStateManager;
	
	@Override
	public void create () {

		gameStateManager = new GameStateManager();
		gameStateManager.push(new MenuState(gameStateManager));
		
		spriteBatch = new SpriteBatch();

		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(spriteBatch);
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}

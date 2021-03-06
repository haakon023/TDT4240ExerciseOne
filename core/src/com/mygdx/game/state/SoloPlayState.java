package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;



public class SoloPlayState extends State{

    private Entity player;
    private Texture background;
    
    public static Vector3 mouse_position;

    private final GlyphLayout playerScoreText;
    private final BitmapFont font;
    
    public SoloPlayState(GameStateManager stateManager) {
        super(stateManager);
        player = new Player();
        background = new Texture("background.png");

        camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);

        font = new BitmapFont();
        playerScoreText = new GlyphLayout();
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
        mousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        
        spriteBatch.draw(player.getEntitySprite(), player.getPosition().x, player.getPosition().y);

        playerScoreText.setText(font, String.format("Position X: %.1f Y: %.1f", player.getPosition().x, player.getPosition().y));
        font.draw(spriteBatch, playerScoreText , 5, MyGdxGame.HEIGHT - playerScoreText.height);
        
        spriteBatch.end();
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        player.update(deltaTime);
    }

    @Override
    public void dispose() {
        player.dispose();
        background.dispose();
    }
}

package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;

public class MenuState extends State{

    private Texture background;
    private Texture aiButtonTexture;
    private Texture soloButtonTexture;
    private Texture pongButtonTexture;
    
    private ImageButton aiButton;
    private ImageButton soloButton;
    private ImageButton pongButton;
    private Stage stage;
    
    private ClickListener clickAiListener;
    private ClickListener clickSoloListener;
    private ClickListener clickPongListener;
    
    public MenuState(GameStateManager stateManager) {
        super(stateManager);
        background = new Texture("background.png");
        
        soloButtonTexture = new Texture("Solo.png");
        aiButtonTexture = new Texture("AI.png");
        pongButtonTexture = new Texture("pong.png");
        
        stage = new Stage();

        TextureRegionDrawable aiButtonRegion = new TextureRegionDrawable(new TextureRegion(aiButtonTexture));
        TextureRegionDrawable solobuttonRegion = new TextureRegionDrawable(new TextureRegion(soloButtonTexture));
        TextureRegionDrawable pongButtonRegion = new TextureRegionDrawable(new TextureRegion(pongButtonTexture));
        
        aiButton = new ImageButton(aiButtonRegion);
        soloButton = new ImageButton(solobuttonRegion);
        pongButton = new ImageButton(pongButtonRegion);

        soloButton.setPosition(MyGdxGame.WIDTH / 2 - (soloButton.getWidth() / 2), MyGdxGame.HEIGHT / 2);
        aiButton.setPosition(MyGdxGame.WIDTH / 2 - (soloButton.getWidth() / 2), MyGdxGame.HEIGHT / 2 - 150);
        pongButton.setPosition(MyGdxGame.WIDTH / 2 - (soloButton.getWidth() / 2), MyGdxGame.HEIGHT / 2 - 300);
        
        clickAiListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToAiScene(new AIPlayState(gameStateManager));
            }
        };
        clickSoloListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToAiScene(new SoloPlayState(gameStateManager));
            }
        };
        clickPongListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToAiScene(new PongPlayState(gameStateManager));
            }
        };
        
       aiButton.addListener(clickAiListener);

       soloButton.addListener(clickSoloListener);

       pongButton.addListener(clickPongListener);

       Gdx.input.setInputProcessor(stage);
        
       stage.addActor(soloButton);
       stage.addActor(pongButton);
       stage.addActor(aiButton);
        
    }

    private void goToAiScene(State state) {
        gameStateManager.set(state);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        
        spriteBatch.begin();
        spriteBatch.draw(background, 0,0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        spriteBatch.end();
        stage.draw();

    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void dispose() {
        
        aiButton.removeListener(clickAiListener);
        soloButton.removeListener(clickSoloListener);
        pongButton.removeListener(clickPongListener);
        
        background.dispose();
        soloButtonTexture.dispose();
        aiButtonTexture.dispose();
        pongButtonTexture.dispose();
    }
}

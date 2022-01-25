package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.gameobjects.PongBall;
import com.mygdx.game.gameobjects.PongInput;
import com.mygdx.game.gameobjects.PongPlayer;


public class PongPlayState extends State{
    
    private final ShapeRenderer shapeRenderer;
    private final PongPlayer playerOne;
    private final PongPlayer playerTwo;
    private final PongBall pongBall;
    
    private final int playerWidth = 15;
    private final int playerHeight = 100;
    
    private final GlyphLayout playerScoreText;
    private final BitmapFont font;
    
    
    public PongPlayState(GameStateManager stateManager) {
        super(stateManager);

        camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        shapeRenderer = new ShapeRenderer();

        playerOne = new PongPlayer(new PongInput(Input.Keys.W, Input.Keys.S), new Rectangle(15, MyGdxGame.HEIGHT / 2 - (playerHeight / 2), playerWidth, playerHeight));
        playerTwo = new PongPlayer(new PongInput(Input.Keys.UP, Input.Keys.DOWN), new Rectangle(MyGdxGame.WIDTH - 15 * 2, MyGdxGame.HEIGHT / 2 - (playerHeight / 2), playerWidth, playerHeight));
        pongBall = new PongBall(new Rectangle(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2, 5,5));
        
        font = new BitmapFont();
        playerScoreText = new GlyphLayout();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            //set state to main menu again
            gameStateManager.set(new MenuState(gameStateManager));
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        
        shapeRenderer.circle(pongBall.getBounds().x, pongBall.getBounds().y, pongBall.getBounds().width);
        shapeRenderer.rect(playerOne.getBounds().x, playerOne.getBounds().y, playerWidth, playerHeight);
        shapeRenderer.rect(playerTwo.getBounds().x, playerTwo.getBounds().y, playerWidth, playerHeight);
        
        shapeRenderer.rectLine(new Vector2(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT), new Vector2(MyGdxGame.WIDTH / 2, 0), 2);
        
        shapeRenderer.rectLine(new Vector2(5, MyGdxGame.HEIGHT), new Vector2(5, 0), 1);
        shapeRenderer.rectLine(new Vector2(MyGdxGame.WIDTH - 5, MyGdxGame.HEIGHT), new Vector2(MyGdxGame.WIDTH - 5, 0), 1);

        spriteBatch.begin();
        playerScoreText.setText(font, String.format("Player 1 : %d | Player 2 : %d", playerOne.getScore(), playerTwo.getScore()));
        font.draw(spriteBatch, playerScoreText , (MyGdxGame.WIDTH - playerScoreText.width) / 2, MyGdxGame.HEIGHT - 25);
        spriteBatch.end();
        shapeRenderer.end();
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        
        if(pongBall.checkCollision(playerOne.getBounds())) {
            
            pongBall.setDirection(new Vector2(pongBall.getDirection().x * -1, pongBall.getDirection().y));
        }
        
        if(pongBall.checkCollision(playerTwo.getBounds())) {
            
            pongBall.setDirection(new Vector2(pongBall.getDirection().x * -1, pongBall.getDirection().y));
        }
        
        if(pongBall.getBounds().y > MyGdxGame.HEIGHT - pongBall.getBounds().height) {
            pongBall.setDirection(new Vector2(pongBall.getDirection().x, pongBall.getDirection().y * -1));
        }
        if(pongBall.getBounds().y < 0) {
            pongBall.setDirection(new Vector2(pongBall.getDirection().x, pongBall.getDirection().y * -1));
        }
        
        if(pongBall.getBounds().x < 0) {
            playerTwo.addScore(1);
            resetBoard();
        }
        
        if(pongBall.getBounds().x > MyGdxGame.WIDTH) {
            playerOne.addScore(1);
            resetBoard();
        }
        pongBall.update(deltaTime);
        playerOne.update(deltaTime);
        playerTwo.update(deltaTime);
    }

    private void resetBoard()
    {
        pongBall.getBounds().setPosition(new Vector2(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2));
        pongBall.initializeDirection();
        playerOne.setYPosition(MyGdxGame.HEIGHT / 2);
        playerTwo.setYPosition(MyGdxGame.HEIGHT / 2);
    }
    
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}

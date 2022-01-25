package com.mygdx.game.gameobjects;

public class PongInput {
    private final int UP;
    private final int DOWN;
    
    
    public PongInput(int up, int down)
    {
        UP = up;
        DOWN = down;
    }
    
    public int getUP()
    {
        return UP;
    }
    
    public int getDOWN()
    {
        return DOWN;
    }
}

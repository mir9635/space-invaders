package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends GameObject {
    public boolean isAlive = true;
    private List<int[][]> frames;
    private int frameIndex;
    private boolean loopAnimation = false;

    public Ship(double x, double y) {
        super(x, y);
    }

    public void setStaticView(int[][] viewFrame) {
        setMatrix(viewFrame);
        frames = new ArrayList<int[][]>();
        frames.add(viewFrame);
        frameIndex = 0;
    }

    public void setAnimatedView(boolean isLoopAnimation, int[][]... viewFrames){
        loopAnimation = isLoopAnimation;
    }

    public Bullet fire() {
        return null;
    }

    public void kill() {
        isAlive = false;
    }

    public void nextFrame() {
        frameIndex += 1;
        if (frameIndex >= frames.size() && !loopAnimation) return;
        if (frameIndex >= frames.size() && loopAnimation) frameIndex = 0;
        matrix = frames.get(frameIndex);
    }

    public boolean isVisible() {
        if (!isAlive && frameIndex >= frames.size()) return false;
        return true;
    }

    @Override
    public void draw(Game game) {
        super.draw(game);
        nextFrame();
    }
}

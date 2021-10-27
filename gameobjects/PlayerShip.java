package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.List;


public class PlayerShip extends Ship {
    private Direction direction = Direction.UP;

    public PlayerShip() {
        super(SpaceInvadersGame.WIDTH / 2., SpaceInvadersGame.HEIGHT - ShapeMatrix.PLAYER.length - 1);
        setStaticView(ShapeMatrix.PLAYER);
    }

    public void verifyHit(List<Bullet> bullet) {
        if (bullet.isEmpty()) return;
        if (this.isAlive) {
            for (Bullet bullet1 : bullet) {
                if (isAlive && bullet1.isAlive && this.isCollision(bullet1)) {
                    kill();
                    bullet1.kill();
                    break;
                }
            }
        }
    }

    public void setDirection(Direction newDirection) {
        if (newDirection != Direction.DOWN) this.direction = newDirection;
    }

    public Direction getDirection() {
        return direction;
    }

    public void win(){
        setStaticView(ShapeMatrix.WIN_PLAYER);
    }

    public void move() {
        if (!isAlive) return;
        else if (direction == Direction.LEFT) x--;
        else if (direction == Direction.RIGHT) x++;
        if (x < 0) x = 0;
        if (x + width > SpaceInvadersGame.WIDTH) x = SpaceInvadersGame.WIDTH - width;
    }

    @Override
    public void kill() {
        if (!this.isAlive) return;
        isAlive = false;
        setAnimatedView(false,ShapeMatrix.KILL_PLAYER_ANIMATION_FIRST,
                ShapeMatrix.KILL_PLAYER_ANIMATION_SECOND,
                ShapeMatrix.KILL_PLAYER_ANIMATION_THIRD,
                ShapeMatrix.DEAD_PLAYER);
    }

    @Override
    public Bullet fire() {
        if (!this.isAlive) return null;
        return new Bullet(x + 2, y - ShapeMatrix.BULLET.length, Direction.UP);
    }
}

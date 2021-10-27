package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.List;


public class EnemyFleet {
    private static final int ROWS_COUNT = 3;
    private static final int COLUMNS_COUNT = 10;
    private static final int STEP = ShapeMatrix.ENEMY.length + 1;
    private Direction direction = Direction.RIGHT;

    private List<EnemyShip> ships;

    public EnemyFleet() {
        createShips();
    }

    private void createShips() {
        ships = new ArrayList<EnemyShip>();
        for (int y = 0; y < ROWS_COUNT; y++) {
            for (int x = 0; x < COLUMNS_COUNT; x++) {
                ships.add(new EnemyShip(x * STEP, y * STEP + 12));
            }
        }
        ships.add(new Boss(STEP * COLUMNS_COUNT / 2 - ShapeMatrix.BOSS_ANIMATION_FIRST.length / 2 - 1, 5));
    }

    public double getBottomBorder(){
        if (!ships.isEmpty()){
        double max = ships.get(0).y + ships.get(0).height;
        for (EnemyShip ship : ships) {
            max = Double.max(max, ship.y+ ship.height);
        }
        return max;
    }
        return 0;
    }

    public int getShipsCount(){
        return ships.size();
    }

    public int verifyHit(List<Bullet> bullets) {
        if (bullets.isEmpty()) return 0;
        else {
            int sum = 0;
            for (Bullet bullet: bullets) {
                for (EnemyShip ship : ships) {
                    if (ship.isAlive && bullet.isAlive && ship.isCollision(bullet)) {
                        ship.kill();
                        bullet.kill();
                        sum += ship.score;
                    }
                }
            }
            return sum;
        }
    }

    public void deleteHiddenShips() {
        for (Ship ship : new ArrayList<>(ships)) {
            if (!ship.isVisible()) {
                ships.remove(ship);
            }
        }
    }

    public Bullet fire(Game game) {
        if (ships.size() == 0) return null;

        int number = game.getRandomNumber(100 / SpaceInvadersGame.COMPLEXITY);
        if (number > 0) return null;

        number = game.getRandomNumber(ships.size());
        return ships.get(number).fire();
    }

    private double getSpeed() {
        return Double.min(2., 3. / ships.size());
    }

    public void move() {
        if (ships.size() == 0) return;
        boolean n = false;
        switch (direction) {
            case LEFT:
                if (getLeftBorder() < 0) {
                    direction = Direction.RIGHT;
                    n = true;
                }
                break;
            case RIGHT:
                if (getRightBorder() > SpaceInvadersGame.WIDTH) {
                    direction = Direction.LEFT;
                    n = true;
                }
                break;
        }
        getSpeed();
        if (n) {
            for (EnemyShip ship : ships) {
                ship.move(Direction.DOWN, getSpeed());
            }
        } else {
            for (EnemyShip ship : ships) {
                ship.move(direction, getSpeed());
            }
        }

    }

    public void draw(Game game) {
        for (EnemyShip ship : ships) {
            ship.draw(game);
        }
    }

    private double getLeftBorder() {
        double x = ships.get(0).x;
        for (EnemyShip ship : ships) {
            if (ship.x < x) x = ship.x;
        }
        return x;
    }

    private double getRightBorder() {
        double x = ships.get(0).x + ships.get(0).width;
        for (EnemyShip ship : ships) {
            if (ship.x + ship.width > x) x = ship.x + ship.width;
        }

        return x;
    }
}

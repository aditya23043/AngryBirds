package com.angrybirds;

import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends Pig{
    private int health;

    public SmallPig(String levelpath, int width, int height, int x, int y, float scale, World world) {
        super(levelpath, width, height, x, y, scale, world);
    }
    @Override
    void takeDamage(int damage) {

    }

    @Override
    boolean isDead() {
        return false;
    }

    @Override
    void move_pig(int damage) {

    }
}

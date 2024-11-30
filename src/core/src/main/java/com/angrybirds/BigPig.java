package com.angrybirds;

import com.badlogic.gdx.physics.box2d.World;

public class BigPig extends Pig{
    private int health;

    public BigPig(String levelpath, int width, int height, int x, int y, float scale, World world) {
        super(levelpath, width, height, x, y, scale, world);
        int health=3;
    }
    @Override
    void takeDamage(int damage) {

    }

    @Override
    public boolean isDead() {
        return is_dead;
    }

    @Override
    public void move_pig(int damage){

    }
}

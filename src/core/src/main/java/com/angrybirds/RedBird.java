package com.angrybirds;

import com.badlogic.gdx.physics.box2d.World;

class RedBird extends Bird {

    public RedBird(String levelpath, int width, int height, int x, int y, float scale, World world, String name) {
        super(levelpath, width, height, x, y, scale, world, name);
    }

    @Override
    void dealDamage(int damage) {

    }

    @Override
    void moveBird(int damage) {

    }

}

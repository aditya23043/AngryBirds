package com.angrybirds;

import com.badlogic.gdx.physics.box2d.World;

class RedBird extends Bird {

    public RedBird(int width, int height, int x, int y, float scale, World world) {
        super("img/red1.png", width, height, x, y, scale, world);
    }

    @Override
    void dealDamage(int damage) {

    }

    @Override
    void move_bird(int damage) {

    }

}

package com.angrybirds;

import com.badlogic.gdx.physics.box2d.World;

class RedBird extends Bird {
    private int health=1;

    public RedBird(int width, int height, int x, int y, float scale, World world, String name) {
        super("img/red1.png", width, height, x, y, scale, world, name);
    }

    @Override
    void dealDamage(int damage) {

    }

    @Override
    void moveBird(int damage) {

    }

    public int get_health(){
        return health;
    }

}

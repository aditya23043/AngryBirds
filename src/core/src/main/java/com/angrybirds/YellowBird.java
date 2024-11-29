package com.angrybirds;

import com.badlogic.gdx.physics.box2d.World;

class YellowBird extends Bird {
    private int health=2;

    public YellowBird( int width, int height, int x, int y, float scale, World world, String name) {
        super("img/yellow.png", width, height, x, y, scale, world, name);
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

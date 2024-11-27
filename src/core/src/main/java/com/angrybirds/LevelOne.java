package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

class LevelOne extends Level {

    SpriteBatch batch;
    Texture img;
    String level_num;
    ArrayList<Pig> pigs;
    ArrayList<Bird> birds;
    ArrayList<Block> blocks;
    World world;
    int num_pigs=3;
    int num_birds=4;

    public LevelOne(World world){
        super(world);
        this.world=world;this.pigs = new ArrayList<>();
        this.birds = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.pigs=new ArrayList<>();
        if(world==null){
            System.out.println("temp");
        }
        int num_pigs=3;
        int num_birds=5;
    }

    public void add_birds(){
        Bird r3= new YellowBird( 60, 60,59, 170, 0.1f, world, "r3");
        r3.setIs_bounce(true);
        Bird r4= new YellowBird(60, 60,106, 170, 0.1f, world, "r3");
        r4.setIs_bounce(true);
        Bird r1= new RedBird(60, 60,54, 170, 0.18f, world, "r1");
        r1.setIs_bounce(true);
        Bird r2= new RedBird(60, 60,12, 170, 0.18f, world, "r2");
        r2.setIs_bounce(true);
        birds.add(r1);
        birds.add(r4);
        birds.add(r3);
        birds.add(r2);
    }

    public void add_pigs(){
        SmallPig pig1= new SmallPig("img/pig.png", 32, 32, 500+28+15, 234, 1.075f, world);
        MediumPig pig2= new MediumPig("img/pig.png", 32, 32, 620, 348, 1.35f, world);
        BigPig pig3 = new BigPig("img/pig.png", 32, 32, 610+42, 256, 1.55f, world);
        pigs.add(pig1);
        pigs.add(pig2);
        pigs.add(pig3);
    }

    public void add_blocks(){
        Block b1= new Block("wood_rect_small",0.8f, 550, 174, world, true, 90);
        Block b2= new Block("wood_rect_small",0.8f, 570, 174, world, true, 90);
        Block b3= new Block("wood_rect_med",0.70f, 520, 200, world, true, 0);
        Block b4=new Block("wood_rect_med",0.70f, 516, 255, world, true, 0);
        Block b5= new Block("glass_rect_med",0.70f, 580, 200, world, true, 90);
        Block b6= new Block("glass_rect_med",0.70f, 524, 200, world, true, 90);
        Block b7= new Block("glass_rect_med",0.79f, 550, 267, world, true, 90);
        Block b8= new Block("wood_rect_med",1f, 550, 316, world, true, 0);
        Block b9 = new Block("stone_rect_long",0.85f, 660-25, 174, world, true, 90);
        Block b10 = new Block("wood_rect_med",1f, 632, 225, world, true, 0);
        Block b11 = new Block("wood_rect_med",1f, 632, 316, world, true, 0);
        Block b12 = new Block("stone_rect_long",0.85f, 722, 174, world, true, 90);

        blocks.add(b1);
        blocks.add(b2);
        blocks.add(b3);
        blocks.add(b4);
        blocks.add(b5);
        blocks.add(b6);
        blocks.add(b7);
        blocks.add(b8);
        blocks.add(b9);
        blocks.add(b10);
        blocks.add(b11);
        blocks.add(b12);
    }

    public ArrayList<Pig> get_pigs(){
        return pigs;
    }

    public ArrayList<Block> get_blocks() {
        return blocks;
    }

    public ArrayList<Bird> get_birds() {
        return birds;
    }

}

package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

class LevelThree extends Level{

    SpriteBatch batch;
    Texture img;
    String level_num;
    ArrayList<Pig> pigs;
    ArrayList<Bird> birds;
    ArrayList<Block> blocks;
    World world;
    int num_pigs=4;
    int num_birds=4;

    public LevelThree(World world){
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
        Bird r4= new BlueBird(60, 60,106, 170, 0.1f, world, "r3");
        r4.setIs_bounce(true);
        Bird r1= new YellowBird( 60, 60,59, 170, 0.1f, world, "r3");
        r1.setIs_bounce(true);
        Bird r2= new RedBird( 60, 60,12, 170, 0.18f, world, "r2");
        r2.setIs_bounce(true);
        birds.add(r1);
        birds.add(r4);
        birds.add(r3);
        birds.add(r2);
    }

    public void add_pigs(){
        SmallPig pig1= new SmallPig("img/pig.png", 32, 32, 500+28+15, 275, 1.075f, world);
        SmallPig pig2= new SmallPig("img/pig.png", 32, 32, 655, 250, 1.1f, world);
        MediumPig pig3 = new MediumPig("img/pig.png", 32, 32, 764, 285, 1.27f, world);
        BigPig pig4 = new BigPig("img/pig.png", 32, 32, 610+42, 340, 1.35f, world);
        pigs.add(pig1);
        pigs.add(pig2);
        pigs.add(pig3);
        pigs.add(pig4);
    }

    public void add_blocks(){
        Block b1= new Block("stone_circle", 0.50f,571, 174, world, true, 90);
        Block b2= new Block("glass_square", 0.70f, 562, 220, world, true, 90);
        Block b3= new Block("wood_rect_med",0.70f, 520, 215, world, true, 0);
        Block b4=new Block("wood_rect_med",0.70f, 520, 245, world, true, 0);
        Block b5= new Block("glass_rect_med",0.70f, 580, 245, world, true, 90);
        Block b6= new Block("glass_rect_med",0.70f, 524, 245, world, true, 90);
        Block b7= new Block("stone_thick",0.60f, 660, 174, world, true, 90);
        Block b8= new Block("stone_thick",0.60f, 686, 174, world, true, 90);
        Block b9= new Block("glass_rect_med",0.70f, 640, 227, world, true, 90);
        Block b10= new Block("glass_rect_med",0.70f, 690, 227, world, true, 90);
        Block b11= new Block("wood_rect_med",0.85f, 625, 275, world, true, 0);
        Block b12= new Block("wood_rect_small",0.8f, 665, 288, world, true, 90);
        Block b13= new Block("wood_rect_med",0.85f, 625, 305, world, true, 0);
        Block b14= new Block("stone_rect_long",0.85f, 740, 174, world, true, 90);
        Block b15= new Block("wood_rect_med",0.85f, 738, 250, world, true, 0);
        Block b16= new Block("wood_rect_med",0.85f, 738, 316, world, true, 0);
        Block b17= new Block("stone_rect_long",0.85f, 817, 174, world, true, 90);
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
        blocks.add(b13);
        blocks.add(b14);
        blocks.add(b15);
        blocks.add(b16);
        blocks.add(b17);
    }

    public ArrayList<Pig> get_pigs(){
        return pigs;
    }

    public ArrayList<Block> get_blocks(){
        return blocks;
    }

    public ArrayList<Bird> get_birds(){
        return birds;
    }

}

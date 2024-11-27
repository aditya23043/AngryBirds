package com.angrybirds;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Level{

    private int level_num;
    private ArrayList<Pig> pigs;
    private ArrayList<Bird> birds;
    private ArrayList<Block> blocks;
    private World world;
    private int num_pigs;
    private int num_birds;

    public Level(World world){
        this.pigs = new ArrayList<>();
        this.birds = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.world = world;
    }

    public int get_num_pigs(){
        return num_pigs;
    }

    public int get_num_birds(){
        return num_birds;
    }

    public ArrayList<Pig> get_pigs(){
        return pigs;
    }

    public ArrayList<Bird> get_birds(){
        return birds;
    }

    public ArrayList<Block> get_blocks(){
        return blocks;
    }

}

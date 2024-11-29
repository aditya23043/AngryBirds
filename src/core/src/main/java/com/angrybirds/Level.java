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
    private int curr_score=0;
    private int win_score;
    private int one_star;
    private int two_star;
    private int three_star;

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

    public void setWin_score(){

    }

    public void setOne_star(){

    }

    public void setTwo_star(){

    }

    public void setThree_star(){

    }

    public int getOne_star() {
        return one_star;
    }

    public int getTwo_star() {
        return two_star;
    }

    public int getThree_star(){
        return three_star;
    }

    public int getWin_score(){
        return win_score;
    }
}

package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

class LevelOne extends Level{

    SpriteBatch batch;
    Texture img;
    String level_num;
    ArrayList<Pig> pigs;
    ArrayList<Bird> birds;
    ArrayList<Block> blocks;
    World world;
    int num_pigs=3;
    int num_birds=4;
    int curr_score;
    int win_score=3500;
    int one_star=3600;
    int two_star=3800;
    int three_star=4000;

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

    public void add_birds(int num_birds_left) {
        if (num_birds_left >= 4) {
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

        } else if (num_birds_left == 3) {
            Bird r3= new YellowBird( 60, 60,59, 170, 0.1f, world, "r3");
            r3.setIs_bounce(true);
            Bird r1= new RedBird(60, 60,54, 170, 0.18f, world, "r1");
            r1.setIs_bounce(true);
            Bird r2= new RedBird(60, 60,12, 170, 0.18f, world, "r2");
            r2.setIs_bounce(true);
            birds.add(r1);
            birds.add(r3);
            birds.add(r2);

        } else if (num_birds_left == 2) {
            Bird r1= new RedBird(60, 60,54, 170, 0.18f, world, "r1");
            r1.setIs_bounce(true);
            Bird r2= new RedBird(60, 60,12, 170, 0.18f, world, "r2");
            r2.setIs_bounce(true);
            birds.add(r1);
            birds.add(r2);

        } else if (num_birds_left == 1) {
            Bird r1= new RedBird(60, 60,54, 170, 0.18f, world, "r1");
            r1.setIs_bounce(true);
            birds.add(r1);
        }
    }

    public void add_pigs(){
        SmallPig pig1= new SmallPig("img/pig.png", 32, 32, 500+28+15, 234, 1.075f, world);
        MediumPig pig2= new MediumPig("img/pig.png", 32, 32, 620, 348, 1.35f, world);
        BigPig pig3 = new BigPig("img/pig.png", 32, 32, 610+42, 256, 1.55f, world);
        pigs.add(pig1);
        pigs.add(pig2);
        pigs.add(pig3);
    }

    public void add_pigs(int pigs_left) {
        if (pigs_left >= 3) {
            SmallPig pig1= new SmallPig("img/pig.png", 32, 32, 500+28+15, 234, 1.075f, world);
            MediumPig pig2= new MediumPig("img/pig.png", 32, 32, 620, 348, 1.35f, world);
            BigPig pig3 = new BigPig("img/pig.png", 32, 32, 610+42, 256, 1.55f, world);
            pigs.add(pig1);
            pigs.add(pig2);
            pigs.add(pig3);
        } else if (pigs_left == 2) {
            SmallPig pig1= new SmallPig("img/pig.png", 32, 32, 500+28+15, 234, 1.075f, world);
            MediumPig pig2= new MediumPig("img/pig.png", 32, 32, 620, 348, 1.35f, world);
            pigs.add(pig1);
            pigs.add(pig2);

        } else if (pigs_left == 1) {
            SmallPig pig1= new SmallPig("img/pig.png", 32, 32, 500+28+15, 234, 1.075f, world);
            pigs.add(pig1);

        }
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

    public void add_blocks(ArrayList<String> block_list) {
        for (String block : block_list) {
            if (block.equals("wood_rect_small")) {
                Block b1= new Block("wood_rect_small",0.8f, 550, 174, world, true, 90);
                blocks.add(b1);
            }
            else if (block.equals("")) {
                Block b2= new Block("wood_rect_small",0.8f, 570, 174, world, true, 90);
                blocks.add(b2);
            }
            else if (block.equals("")) {
                Block b3= new Block("wood_rect_med",0.70f, 520, 200, world, true, 0);
                blocks.add(b3);
            }
            else if (block.equals("")) {
                Block b4=new Block("wood_rect_med",0.70f, 516, 255, world, true, 0);
                blocks.add(b4);
            }
            else if (block.equals("")) {
                Block b5= new Block("glass_rect_med",0.70f, 580, 200, world, true, 90);
                blocks.add(b5);
            }
            else if (block.equals("")) {
                Block b6= new Block("glass_rect_med",0.70f, 524, 200, world, true, 90);
                blocks.add(b6);
            }
            else if (block.equals("")) {
                Block b7= new Block("glass_rect_med",0.79f, 550, 267, world, true, 90);
                blocks.add(b7);
            }
            else if (block.equals("")) {
                Block b8= new Block("wood_rect_med",1f, 550, 316, world, true, 0);
                blocks.add(b8);
            }
            else if (block.equals("")) {
                Block b9 = new Block("stone_rect_long",0.85f, 660-25, 174, world, true, 90);
                blocks.add(b9);
            }
            else if (block.equals("")) {
                Block b10 = new Block("wood_rect_med",1f, 632, 225, world, true, 0);
                blocks.add(b10);
            }
            else if (block.equals("")) {
                Block b11 = new Block("wood_rect_med",1f, 632, 316, world, true, 0);
                blocks.add(b11);
            }
            else if (block.equals("")) {
                Block b12 = new Block("stone_rect_long",0.85f, 722, 174, world, true, 90);
                blocks.add(b12);
            }
        }
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

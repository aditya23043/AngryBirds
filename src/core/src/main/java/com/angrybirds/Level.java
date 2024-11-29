package com.angrybirds;

import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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


    public void save(String text_field) {

        int returnVal;
        JFileChooser chooser;
        do {
            chooser = new JFileChooser(Gdx.files.internal("").file().getAbsolutePath());
            chooser.setFileFilter(new FileNameExtensionFilter("Angry Birds Config File", "dat"));
            returnVal = chooser.showOpenDialog(null);
        } while (returnVal != JFileChooser.APPROVE_OPTION);

        ConfData conf_data = new ConfData(chooser.getSelectedFile().getAbsoluteFile());
        ArrayList<String> blocks_left = new ArrayList<>();
        int pigs_left = pigs.size();
        for (Block block : blocks) {
            blocks_left.add(block.getName());
        }
        conf_data.add(text_field, 0, level_num, birds.size(), pigs_left, blocks_left);
        conf_data.write();

    }
}

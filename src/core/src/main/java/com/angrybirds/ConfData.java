package com.angrybirds;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;


public class ConfData implements Serializable {

    private static ArrayList<ConfData> conf_array = new ArrayList<>();

    // attribs to store
    private String username;
    private int cur_score;
    private int level_num;
    private int num_birds_left;
    private ArrayList<String> pigs_left;
    private ArrayList<String> blocks_left;

    // logic for saving data since box2d objects are not serializable
    // note: we could have saved them using transient and implementing our own read and write methods but it would take longer and similar could be acheived without storing the whole object
    // since we have birds list in every level and since they go one by one (linearly), storing just the num of birds left would be the most simplistic way to load the game from the conf file
    // for pigs and birds, we have distinct TYPES of each in every level so we store whatever type is left in an arraylist and render only those when loading

    private File conf_file;

    ConfData(File conf_file) {
        this.conf_file = conf_file;
    }

    public void add(String username, int cur_score, int level_num, int num_birds_left, ArrayList<String> pigs_left, ArrayList<String> blocks_left) {

        this.username = username;
        this.cur_score = cur_score;
        this.level_num = level_num;
        this.num_birds_left = num_birds_left;
        this.pigs_left = pigs_left;
        this.blocks_left = blocks_left;

        conf_array.add(this);
    }

    public void write() {

        try (FileOutputStream file = new FileOutputStream(this.conf_file, false);
                ObjectOutputStream out = new ObjectOutputStream(file)) {
            out.writeObject(ConfData.conf_array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try (FileInputStream file = new FileInputStream(this.conf_file);
                ObjectInputStream in = new ObjectInputStream(file)) {
            ArrayList<ConfData> _arr = (ArrayList<ConfData>) in.readObject();
            for (ConfData _conf_data : _arr) {
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ConfData> get_list() {
        return this.conf_array;
    }

    public String get_username() {
        return this.username;
    }
}

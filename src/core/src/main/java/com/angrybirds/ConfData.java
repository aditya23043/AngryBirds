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
    private int score;
    private File conf_file;

    private int level_num;
    private ArrayList<Pig> pigs;
    private ArrayList<Bird> birds;
    private ArrayList<Block> blocks;
    private World world;
    private int num_pigs;
    private int num_birds;

    ConfData(File conf_file) {
        this.username = null;
        this.score = 0;
        this.conf_file = conf_file;
    }

    public void write(String _username, int _score) {
        this.username = _username;
        this.score = _score;
        conf_array.add(this);

        try (FileOutputStream file = new FileOutputStream(this.conf_file, false);
                ObjectOutputStream out = new ObjectOutputStream(file)) {
            out.writeObject(ConfData.conf_array);
            file.close();
            out.close();
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
            file.close();
            in.close();
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

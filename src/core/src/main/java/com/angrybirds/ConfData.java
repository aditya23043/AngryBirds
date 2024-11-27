package com.angrybirds;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;


public class ConfData implements Serializable {

    private static ArrayList<ConfData> conf_array = new ArrayList<>();

    // attribs to store
    private String username;
    private int score;
    private String conf_file;

    ConfData(String conf_file) {
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
                System.out.println(_conf_data.username);
                System.out.println(_conf_data.score);
                System.out.println();
            }
            file.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

package com.angrybirds;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class ConfData implements Serializable {


    private static ArrayList<ConfData> conf_array = new ArrayList<>();

    // attribs to store
    public String username;
    public int score;
    private String conf_file;

    ConfData(String conf_file) {
        this.username = null;
        this.score = 0;
        this.conf_file = conf_file;
    }

    public void write(String name, int score) {
        this.username = name;
        this.score = score;

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.conf_file));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write_sample_conf() {
        String conf_file_debug = "something.dat";
        ConfData sample_data = new ConfData(conf_file_debug);
        sample_data.write("Aditya Gautam", 200);
        // sample_data.write("Something", 120);
        // sample_data.write("Else", 140);
        // sample_data.write("idk", 90);
        // sample_data.write("hmmmmmmmm", 500);
    }

    public static void read() {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("something.dat"))) {
            while (true) {
                try {
                    ConfData _din = (ConfData) in.readObject();
                    System.out.println(_din.username);
                }
                catch(EOFException e){
                    break;
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

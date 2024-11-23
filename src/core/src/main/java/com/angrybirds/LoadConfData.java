package com.angrybirds;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class LoadConfData implements Serializable {

    public static void main(String[] args) {


        ConfData conf_data_in;

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("something.dat"));
            conf_data_in = (ConfData) in.readObject();
            System.out.println(conf_data_in.score);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void write_sample_conf_data() {

        ConfData sample_data = new ConfData("Aditya Gautam", 200);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("something.dat"));
            out.writeObject(sample_data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}

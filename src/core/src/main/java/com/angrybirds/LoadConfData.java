package com.angrybirds;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

class LoadConfData implements Serializable {
    public static void main(String[] args) {

        ConfData d1 = new ConfData("Aditya Gautam", 200);
        ConfData d2;

        // try {
        //     ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("something.dat"));
        //     out.writeObject(d1);
        // }
        // catch (IOException e) {
        //     e.printStackTrace();
        // }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("something.dat"));
            in.defaultReadObject();
            d2 = (ConfData) in.readObject();
            System.out.println(d2.score);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

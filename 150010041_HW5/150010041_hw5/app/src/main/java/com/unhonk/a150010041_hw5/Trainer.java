package com.unhonk.a150010041_hw5;

/**
 * Created by anmol on 16/04/18.
 */

import android.os.Environment;

import java.io.*;

import umich.cse.yctung.androidlibsvm.LibSVM;

public class Trainer {

    BufferedReader fin;
    BufferedWriter fout;
    LibSVM svm;
    String input_file;
    String input_path;
    String translated_file;

    public Trainer(String input_file)
    {
        this.input_file = input_file;
        this.input_path = input_file.substring(0, input_file.lastIndexOf('/') + 1);
        translated_file = input_path + "LibSVM_train.txt";
        try {
            fin = new BufferedReader(new FileReader(input_file));
            fout = new BufferedWriter(new FileWriter(translated_file));
        }
        catch (IOException e)
        {
            System.out.println(e.getStackTrace());
        }
        svm = new LibSVM();
    }

    public void svmTrain()
    {
//        svm.scale(translated_file, input_path + "Scaled.txt");
        svm.train("-t 2 "/* svm kernel */ + translated_file + " " + input_path + "Model.txt");
    }

    public void svmPredict()
    {
        svm.predict(translated_file + " " + input_path + "Model.txt " + input_path + "Predicted.txt");
    }

    public void translate()
    {
        String inString = "";
        String[] record = {};
        String output = "";
        int count = 0;

        try {
            while ((inString = fin.readLine()) != null) {
                if (count == 0 || count == 1) {
                    count++;
                    continue;
                }

                record = inString.split(",");

                if (record[6].equals("Walking"))
                    record[6] = "1";
                else if (record[6].equals("Stationary"))
                    record[6] = "-1";

                output = record[6] + " 1:" + record[3] + " 2:" + record[4] + " 3:" + record[5];

                fout.write(output);
                fout.write('\n');
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getStackTrace());
        }
    }

    public void close()
    {
        try {
            if (fin != null) {
                fin.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getStackTrace());
        }

    }
}
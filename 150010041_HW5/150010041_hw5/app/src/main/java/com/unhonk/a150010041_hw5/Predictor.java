package com.unhonk.a150010041_hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by anmol on 16/04/18.
 */

public class Predictor {

    BufferedReader fin;
    BufferedReader fin_predicted;
    BufferedWriter fout;
    String input_file;
    String input_path;
    String output_file;

    public Predictor(String input_file)
    {
        this.input_file = input_file;
        this.output_file = input_file.substring(0, input_file.lastIndexOf('.')) + "_predicted.csv";
        this.input_path = input_file.substring(0, input_file.lastIndexOf('/')+1);
        try {
            fin = new BufferedReader(new FileReader(input_file));
            fout = new BufferedWriter(new FileWriter(output_file));
        }
        catch (IOException e)
        {
            System.out.println(e.getStackTrace());
        }
    }

    public void combine()
    {
        String inString = "";
        String output = "";
        String predicted;

        int count = 0;
        try {
            fin_predicted = new BufferedReader(new FileReader(input_path + "Predicted.txt"));

            while ((inString = fin.readLine()) != null) {
                if (count == 0) {
                    fout.write(inString);
                    fout.write('\n');
                    count++;
                    continue;
                }
                if (count == 1) {
                    output = inString + ", " + "prediction";
                    fout.write(output);
                    fout.write('\n');
                    count++;
                    continue;
                }

                predicted = fin_predicted.readLine();

                if (predicted.equals("1"))
                    output = inString + ", " + "Walking";
                else if (predicted.equals("-1"))
                    output = inString + ", " + "Stationary";

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
            if (fin_predicted != null) {
                fin_predicted.close();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getStackTrace());
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classpackage;

/**
 *
 * @author a157692
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import mdsj.MDSJ;

public class MultidimensionalScaling {

    private double[][] input;


    public void setInput(double[][] matriz) {
        this.input = matriz;
    }

    public double[][] output() {
        double[][] output = MDSJ.classicalScaling(input);
        try {
            FileReader arq = new FileReader("label.txt");
            FileWriter file = new FileWriter(new File("Arquivo.txt"));
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int n = input[0].length;    // number of data objects
            for (int i = 0; i < n; i++) {  // output all coordinates
                file.write(output[0][i] + "|" + output[1][i] + "|"+linha+ "\n");
                System.out.println(output[0][i] + " " + output[1][i]);
                 linha = lerArq.readLine();
            }
            file.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return output;
    }

}

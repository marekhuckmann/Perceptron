package com.company;

/**
 * Created by Marek Hückmann on 2016-10-22.
 */

import java.text.*;

class Perceptron {
    static int iterations = 100;
    static double learningRate = 0.1;
    static int points = 100;
    static int theta = 0;
    public static void main(String args[]) {

        double[] x = new double [points];
        double[] y = new double [points];
        double[] z = new double [points];
        int[] outputs = new int [points];

        for(int i = 0; i < points/2; i++) {
            x[i] = randomNumber(5, 10);
            y[i] = randomNumber(4, 8);
            z[i] = randomNumber(2, 9); 
            outputs[i] = 1;
            System.out.println(x[i]+ "\t" + y[i] + "\t" + z[i] + "\t" + outputs[i]);
        }

        for(int i = 50; i < points; i++) {
            x[i] = randomNumber(-1, 3);
            y[i] = randomNumber(-4, 2);
            z[i] = randomNumber(-3, 5); 
            outputs[i] = 0;
            System.out.println(x[i] + "\t" + y[i] + "\t" + z[i] + "\t" + outputs[i]);
        }

        double[] weights = new double[4]; // 3 input variables and one bias
        double localError, globalError;
        int i, p, iteration, output;

        weights[0] = randomNumber(0,1); // w1
        weights[1] = randomNumber(0,1); // w2
        weights[2] = randomNumber(0,1); // w2
        weights[3] = randomNumber(0,1); // bias

        iteration = 0;
            do {
                iteration++;
                globalError = 0;

                for (p = 0; p < points; p++) {
                    output = calculateOutput(theta,weights, x[p], y[p], z[p]);
                    localError = outputs[p] - output;
                    weights[0] += learningRate * localError * x[p];
                    weights[1] += learningRate * localError * y[p];
                    weights[2] += learningRate * localError * z[p];
                    weights[3] += learningRate * localError;
                    globalError += (localError*localError);
                }

                System.out.println("Iteration # " + iteration+": RMSE = " + Math.sqrt(globalError/points));
            
            } while(globalError != 0 && iteration<=iterations);

        System.out.println("\n=======\nEquation:");
        System.out.println(weights[0] + "x + " + weights[1] + "y +  " + weights[2] + "z + " + weights[3] + " = 0");

        // Generation of 10 new random points and checking their classes (1 or 0)
        for(int j = 0; j < 10; j++) {
            double x1 = randomNumber(-10, 10);
            double y1 = randomNumber(-10, 10);
            double z1 = randomNumber(-10, 10);

            output = calculateOutput(theta, weights, x1, y1, z1);
            System.out.println("\n=======\nNew point:");
            System.out.println("x = " + x1+ ", y = " + y1 + ", z = " + z1);
            System.out.println("Class = " + output);
        }
    }

    public static double randomNumber(int min, int max) {
        DecimalFormat df = new DecimalFormat("#,####");
        double d = min + Math.random() * (max - min);
        String s = df.format(d);
        double x = Double.parseDouble(s);
        return x;
    }


    static int (int theta, double weights[], double x, double y, double z) {
		double sum = x * weights[0] + y * weights[1] + z * weights[2] + weights[3];
        return (sum >= theta) ? 1 : 0;
    }
}

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ChannelProcessing {
    // Function does not alter parameters
    @Contract(pure = true)
    // Function for calculating Y called by the main method
    static Double @NotNull [] FindY(Double m, Double c, Double[] array) {
        double y;
        // New Double array created to hold the to be calculated Y values with the same length as the channel X array
        Double[] Y = new Double[array.length];

        //for-loop used to iterate through each element of the array
        for (int i = 0; i < array.length; i++) {

            y = m * array[i] + c;

            //Adding each value of y to the empty Y array
            Y[i] = y;


        }
        //Array Y is returned to the caller of the function
        return Y;
    }

    //Function used to calculate the values of channel B
    static @NotNull Double FindB(Double[] A, Double[] Y) throws IOException {
        double B1;
        double b;
        double total = 0;
        Double[] B = new Double[A.length];

        //for-loop to iterate through the array elements
        for (int i = 0; i < A.length; i++) {

            //Value of B calculated as a double
            B1 = A[i] + Y[i];
            //double values added to the Double array
            B[i] = B1;
            //sum of all elements in array B is calculated
            total = total + B[i];
        }

        //mean/average of the elements in array B stored as double b
        b = total / B.length;

        System.out.println("The values of channel B are: " + Arrays.toString(B));

        //File writer used to create and write to a .txt document
        FileWriter writer = new FileWriter("channelsCalculated.txt");

        //Buffer writer used as it provides better performance than file writer
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        bufferedWriter.write("B" + "," + " ");

        //for-loop used to iterate through values of array B and write each value to .txt
        for (Double aDouble : B) {

            bufferedWriter.write(aDouble + "," + " ");

        }

        //buffer reader emptied
        bufferedWriter.flush();
        //buffer reader closed
        bufferedWriter.close();

        return b;
    }

    //Function does not alter parameters
    @Contract(pure = true)
    //Function used to calculate the value of channel A
    static Double @NotNull [] FindA(Double[] arrayX) {
        double a;
        Double[] A = new Double[arrayX.length];

        //Iterate through parameter array and calculating a for each element
        for (int i = 0; i < arrayX.length; i++) {
            a = 1 / arrayX[i];
            A[i] = a;

        }

        return A;


    }

    @Contract(pure = true)
    //Function used to calculate values of channel C
    static Double @NotNull [] FindC(Double @NotNull [] X, Double b) {
        double c;
        Double[] C = new Double[X.length];

        for (int i = 0; i < X.length; i++) {

            c = X[i] + b;
            C[i] = c;

        }

        //Array returned to caller main()
        return C;
    }


    public static void main(String[] args) throws IOException {

        //Array list used to store the values of channels.txt
        ArrayList<String> channelString = new ArrayList<>();

        //Scanner file reader used to read channels.txt
        try (Scanner channelData = new Scanner(new FileReader("channels.txt"))) {

            String cLine;

            String XChannel = "";

            String[] str;


            while (channelData.hasNextLine()) {

                if (XChannel.isEmpty()) {

                    cLine = channelData.nextLine();

                } else {

                    cLine = XChannel;
                    XChannel = "";

                }

                // if statement used to ensure wrong line starts are ignored
                if (cLine.startsWith(";") || cLine.startsWith("#") || cLine.isEmpty()) {
                    continue;
                }

                //if statement used to select the correct channel values which start with a character
                if (cLine.startsWith("X,")) {

                    //split() method sed to separate the data and output the values as an array
                    str = cLine.split(",\\s");

                    //array str converted to arrayList
                    Collections.addAll(channelString, str);
                }
            }
            //Try - catch used to catch exceptions in case the .txt file is not found/does not exist
        } catch (FileNotFoundException nfe) {
            System.out.println("File doesn't exist");
            nfe.printStackTrace();
        }

        //First element of the array list is removed as it is a character and not a number
        channelString.remove(0);

        //new Double array is created
        Double[] channel = new Double[channelString.size()];

        //for loop used to parse all values of the arrayList channelString into new Double array
        for (int i = 0; i < channelString.size(); ++i) {
            channel[i] = Double.parseDouble(channelString.get(i));
        }

        //Double array contents are printed in order to test correctness
        System.out.println("The values of channel X are: " + Arrays.toString(channel));


        double m;
        double c;


        // New arrayList used to store the parameters values
        ArrayList<String> parName = new ArrayList<>();


        //Scanner and file reader used to read parameters.txt
        try (Scanner parameters = new Scanner(new FileReader("parameters.txt"))) {

            String line;

            String lineName = "";

            //While loop used in order to read all lines with characters on them
            while (parameters.hasNextLine()) {

                if (lineName.isEmpty()) {

                    line = parameters.nextLine();

                } else {

                    line = lineName;
                    lineName = "";

                }

                if (line.startsWith(";") || line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("m,")) {
                    //line String value added to the arrayList
                    //line is trimmed in order to only include the numeric value of the parameter
                    parName.add(line.isEmpty() ? "N/A" : line.split("\\s*,\\s*")[1].trim());

                    //while loop used to loop through all available lines which start in the format "m,"
                    while (parameters.hasNextLine()) {

                        line = parameters.nextLine().trim();
                        //line is trimmed in order to only include the numeric value of the parameter
                        parName.add(line.isEmpty() ? "N/A" : line.split("\\s*,\\s*")[1].trim());

                    }

                }

            }
            //try-catch used in case file is not found
        } catch (FileNotFoundException fnf) {

            System.out.println("File doesn't exist");
            fnf.printStackTrace();
        }

        //String array created
        String[] parameter;
        try {
            //ArrayList turned into an array
            parameter = parName.toArray(new String[0]);

            //try-catch for error in conversion due to type mismatch
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Values in parameter array printed
        System.out.println("The values of the parameters array are: " + Arrays.toString(parameter));

        try {
            //String array elements parsed as doubles
            m = Double.parseDouble(parameter[0]);
            c = Double.parseDouble(parameter[1]);

            //individual parameters printed
            System.out.println("The value of parameter m = " + m);
            System.out.println("The value of parameter c = " + c);

            //FindY function is called, arguments are passed, and the return value is printed
            Double[] Y = ChannelProcessing.FindY(m, c, channel);
            System.out.println("The values of channel Y are: " + Arrays.toString(Y));


            //File writer used to create and write new .txt file to hold the calculated channel values
            FileWriter writer = new FileWriter("channelsCalculated.txt", true);

            //Buffer writer used due to its better performance with higher number of operations
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            //buffer writer moves to new line
            bufferedWriter.newLine();
            bufferedWriter.write("Y" + "," + " ");

            //Values of channel Y written to .txt
            for (Double aDouble : Y) {

                bufferedWriter.write(aDouble + "," + " ");

            }

            //FindA function called and arguments passed
            Double[] A = ChannelProcessing.FindA(channel);
            System.out.println("The values of channel A are: " + Arrays.toString(A));

            bufferedWriter.newLine();
            bufferedWriter.write("A" + "," + " ");

            //values of channel A written to .txt
            for (Double aDouble : A) {

                bufferedWriter.write(aDouble + "," + " ");

            }

            //FindB function called and arguments passed
            Double b = ChannelProcessing.FindB(A, Y);

            //File writer used to create new .txt and write the value of metric b
            FileWriter fileWriter = new FileWriter("metrics.txt");

            BufferedWriter bufferedFileWriter = new BufferedWriter(fileWriter);

            bufferedFileWriter.write("b" + "," + " " + b);

            bufferedFileWriter.close();

            //FindC function called and arguments passed
            Double[] C = ChannelProcessing.FindC(channel, b);
            System.out.println("The values of channel C are: " + Arrays.toString(C));

            bufferedWriter.newLine();
            bufferedWriter.write("C" + "," + " ");

            //Values of channel C written to .txt
            for (Double aDouble : C) {

                bufferedWriter.write(aDouble + "," + " ");

            }

            //buffer writer now closed
            bufferedWriter.close();

            System.out.println("The value of metric b = " + b);

            //try-catch used due to possible arrayIndex exception due to missing .txt file
        } catch (ArrayIndexOutOfBoundsException aio) {
            System.out.println("There has been an error");
            aio.printStackTrace();

        }


    }
}

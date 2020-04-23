import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.*;

public class Network {

    public static int nbrOfCities;
    public static ArrayList<Double> reliabilityMatrix;
    public static ArrayList<Integer> costMatrix;
    public static String fileName;
    public static String fileURL = "input.txt";

    public static void main(String[] args) {
        reliabilityMatrix = new ArrayList<>();
        costMatrix = new ArrayList<>();

        readInputFile(fileURL);

    }

    /**
     * Read inputs from file
     * 
     * @param fileName
     */
    public static void readInputFile(String fileName) {
        String lineRead = null;

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(fileReader);

            while ((lineRead = buffer.readLine()) != null) {
                // number of cities
                if (lineRead.contains("#") && lineRead.contains("nodes")) {
                    lineRead = buffer.readLine();
                    nbrOfCities = Integer.valueOf(lineRead);
                    // System.out.println(nbrOfCities);

                }
                // reliability values
                else if (lineRead.contains("#") && lineRead.contains("reliability")) {
                    lineRead = buffer.readLine();
                    lineRead = buffer.readLine();
                    String[] splitReliablilities = lineRead.split(" ");
                    for (String nbr : splitReliablilities) {
                        reliabilityMatrix.add(Double.valueOf(nbr));
                    }
                    // System.out.println(reliabilityMatrix);
                }
                // cost values
                else if (lineRead.contains("#") && lineRead.contains("cost")) {
                    lineRead = buffer.readLine();
                    lineRead = buffer.readLine();
                    String[] splitCosts = lineRead.split(" ");
                    for (String nbr : splitCosts) {
                        costMatrix.add(Integer.valueOf(nbr));
                    }
                    // System.out.println(costMatrix);
                }
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

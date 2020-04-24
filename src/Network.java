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
        Graph graphNetwork = new Graph();
        ArrayList<Edge> edges = new ArrayList<>();

        readInputFile(fileURL);
        createGraphVertices(graphNetwork);
        createGraphEdges(edges, graphNetwork);
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
                    System.out.println("Number of cities: " + nbrOfCities);

                }
                // reliability values
                else if (lineRead.contains("#") && lineRead.contains("reliability")) {
                    lineRead = buffer.readLine();
                    lineRead = buffer.readLine();
                    String[] splitReliablilities = lineRead.split(" ");
                    for (String nbr : splitReliablilities) {
                        reliabilityMatrix.add(Double.valueOf(nbr));
                    }
                    System.out.println("Reliability values: " + reliabilityMatrix);
                }
                // cost values
                else if (lineRead.contains("#") && lineRead.contains("cost")) {
                    lineRead = buffer.readLine();
                    lineRead = buffer.readLine();
                    String[] splitCosts = lineRead.split(" ");
                    for (String nbr : splitCosts) {
                        costMatrix.add(Integer.valueOf(nbr));
                    }
                    System.out.println("Cost values: " + costMatrix);
                }
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * create vertices for our graph
     * 
     * @param graphNetwork
     */
    public static void createGraphVertices(Graph graphNetwork) {
        for (int v = 0; v < nbrOfCities; v++) {
            Vertex vertex = new Vertex(v);
            graphNetwork.addVertex(vertex);
        }
        // System.out.println(graphNetwork.vertices.size() + " vertices");
    }

    /**
     * create edges for our graph
     * 
     * @param edges
     * @param graphNetwork
     */
    public static void createGraphEdges(ArrayList<Edge> edges, Graph graphNetwork) {
        int index = 0;
        for (int i = 0; i < nbrOfCities; i++) {
            int v1Label = i;
            for (int j = v1Label + 1; j < nbrOfCities; j++) {
                int v2Label = j;
                edges.add(new Edge(graphNetwork.vertices.get(v1Label), graphNetwork.vertices.get(v2Label),
                        costMatrix.get(index), reliabilityMatrix.get(index)));
            }
        }
    }

}

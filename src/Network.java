import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.*;

public class Network {

    public static int nbrOfCities;
    public static double reliabilityGoal;
    public static double costConstraint;
    public static ArrayList<Double> reliabilityMatrix;
    public static ArrayList<Integer> costMatrix;
    public static String fileURL = "input.txt";

    public static void main(String[] args) {
    	//check if there were enough arguments given
    	if(args.length != 2) {
    		System.out.println("This program only accepts 2 arguments: a reliability goal and a cost constraint.");
    		System.exit(0);
    	}
    	try {
    		reliabilityGoal = Double.parseDouble(args[0]);
    		costConstraint = Double.parseDouble(args[1]);
    	} catch(NumberFormatException e) {
    		System.out.println("Reliability goal and cost constraint must be doubles.");
    		System.exit(0);
    	}
    	if(reliabilityGoal >= 1.0) {
    		System.out.println("Reliability goal can't be larger than 1.");
    		System.exit(0);
    	}
    	
    	//get data from input file
        reliabilityMatrix = new ArrayList<>();
        costMatrix = new ArrayList<>();
        Graph graphNetwork = new Graph();
        ArrayList<Edge> edges = new ArrayList<>();

        readInputFile(fileURL);
        createGraphVertices(graphNetwork);
        createGraphEdges(edges, graphNetwork);
        
        Graph reliabilityNetwork = networkToMeetReliabilityGoal(reliabilityGoal, graphNetwork, edges);
        System.out.println("The given reliability goal was: " + reliabilityGoal);
        if(reliabilityNetwork.edges.isEmpty()) {
        	System.out.println("There is no possible combination of edges that meets that goal.");
        } else {
        	System.out.println("The network that meets this goal is: ");
        	reliabilityNetwork.print();
        	System.out.println("With a reliability of " + getNetworkReliability(reliabilityNetwork) + " and a cost of " + getNetworkCost(reliabilityNetwork) + ".");
        }
        
        Graph costNetwork = networkToMeetCostConstraint(costConstraint, graphNetwork, edges);
        System.out.println("The given cost constraint was: " + costConstraint);
        if(costNetwork.edges.isEmpty()) {
        	System.out.println("There is no possible combination of edges that meets that constraint.");
        } else {
        	System.out.println("The network that meets this constraint is: ");
        	costNetwork.print();
        	System.out.println("With a reliability of " + getNetworkReliability(costNetwork) + " and a cost of " + getNetworkCost(costNetwork) + ".");
        }
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
                    //System.out.println("Number of cities: " + nbrOfCities);

                }
                // reliability values
                else if (lineRead.contains("#") && lineRead.contains("reliability")) {
                    lineRead = buffer.readLine();
                    lineRead = buffer.readLine();
                    String[] splitReliablilities = lineRead.split(" ");
                    for (String nbr : splitReliablilities) {
                        reliabilityMatrix.add(Double.valueOf(nbr));
                    }
                    //System.out.println("Reliability values: " + reliabilityMatrix);
                }
                // cost values
                else if (lineRead.contains("#") && lineRead.contains("cost")) {
                    lineRead = buffer.readLine();
                    lineRead = buffer.readLine();
                    String[] splitCosts = lineRead.split(" ");
                    for (String nbr : splitCosts) {
                        costMatrix.add(Integer.valueOf(nbr));
                    }
                    //System.out.println("Cost values: " + costMatrix);
                }
            }
            buffer.close();
        } catch (IOException e) {
            System.out.println("Error reading input file");
            System.exit(0);
        }

    }
    
    /**
     * method to calculate network cost 
     * @param g
     * @return
     */
    public static double getNetworkCost(Graph g) {
    	double cost = 0.0;
    	for(Edge e: g.edges) {
    		cost += e.cost;
    	}
    	return cost;
    }
    
    /**
     * helper function to calculate reliability of a MST
     * @param g
     * @return
     */
    public static double getMSTReliability(Graph g) {
    	double reliability = 1;
    	for(Edge e: g.edges) {
    		reliability *= e.reliability;
    	}
    	return reliability;
    }
    
    /**
     * helper function to calculate reliability of a cycle
     * @param cycle
     * @return
     */
    public static double getCycleReliability(ArrayList<Edge> cycle) {
    	double reliability = 0.0;
    	//calculate reliability for one edge not working in cycle each time
    	for(int i = 0; i < cycle.size(); i++) {
    		double iterRel = 1;
    		for(Edge e: cycle) {
    			if(cycle.indexOf(e) == i) {
    				iterRel *= (1-e.reliability);
    			} else {
    				iterRel *= e.reliability;
    			}
    		}
    		reliability += iterRel;
    	}
    	//add reliability of all edges working
    	double allEdgesWorking = 1;
    	for(Edge e: cycle) {
    		allEdgesWorking *= e.reliability;
    	}
    	return reliability+allEdgesWorking;
    }
    
    /**
     * calculates the reliability of the given network/graph including cycles
     * @param g
     * @return
     */
    public static double getNetworkReliability(Graph g) {
    	ArrayList<Edge> cycle = g.hasCycle();
    	if(cycle == null || cycle.isEmpty()) {
    		return getMSTReliability(g);
    	} else {
    		ArrayList<Edge> notCycle = g.getEdgesNotInCycle(cycle);
    		double reliability = getCycleReliability(cycle);
    		for(Edge e: notCycle) {
    			reliability *= e.reliability;
    		}
    		return reliability;
    	}
    }
    
    /**
     * 
     * @param goal
     * @param g 
     * @param edges
     * @return
     */
    public static Graph networkToMeetReliabilityGoal(double goal, Graph graph, ArrayList<Edge> edges) {
    	//generate network MST with kruskal
    	//we want max reliability so sort order is 1
    	Kruskal k = new Kruskal();
    	Graph mst = k.mst(edges, graph, 1);
    	Edge e = getEdgeBetweenVertices(graph.vertices.get(1), graph.vertices.get(5), edges);
    	mst.addEdge(e);
    	//calculate network reliability
    	//while reliability < goal
    	//find next edge that isn't in network
    	//add edge to network and calculate new reliability
    	// if new reliability > goal, break, else keep finding edges
    	return mst;
    }
    
    /**
     * 
     * @param budget
     * @param graph
     * @param edges
     * @return
     */
    public static Graph networkToMeetCostConstraint(double budget, Graph graph, ArrayList<Edge> edges) {
    	//generate network MST with kruskal
    	//we want min cost so sort order is 2
    	Kruskal k = new Kruskal();
    	Graph mst = k.mst(edges, graph, 2);
    	
    	//calculate network cost
    	//while cost < budget
    	//find next edge that isn't in network
    	//add edge to network and calculate new reliability
    	// if new cost > budget, break, else keep finding edges
    	return graph;
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
                    index++;
                }
            }
     }
    
    /**
     * returns edge between two given vertices in list of edges
     * 
     * @param u
     * @param v
     * @param edges
     * @return
     */
    public static Edge getEdgeBetweenVertices(Vertex u, Vertex v, ArrayList<Edge> edges) {
		for(Edge e: edges) {
			if((e.v1 == u && e.v2 == v) || (e.v1 == v && e.v2 == u)) {
				return e;
			}
		}
		return null;
    }

}

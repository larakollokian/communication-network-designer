import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Implementation of Kruskal's algorithm for finding minimum spanning tree
 * Uses Disjoint Sets (Union-Find algorithm) to detect cycles in graphs
 * Adapted from the following tutorial:
 * https://algorithms.tutorialhorizon.com/kruskals-algorithm-minimum-spanning-tree-mst-complete-java-implementation/ 
 */
public class Kruskal {
	
	/**
	 * This function creates a new element with a parent pointer to itself
	 * @param parent
	 */
    public void makeSet(int [] parent, int vertexCount){
        for (int i = 0; i < vertexCount ; i++) {
            parent[i] = i;
        }
    }

    /**
     * Recursive function that goes upwards following pointers to parents until it reaches
     * an element whose parent is itself
     * @param parent
     * @param vertex
     * @return
     */
    public int find(int [] parent, int vertex){
        if(parent[vertex]!=vertex)
            return find(parent, parent[vertex]);;
        return vertex;
    }

    /**
     * sets vertex x as a parent of y
     * @param parent
     * @param x
     * @param y
     */
    public void union(int [] parent, int x, int y){
        int x_set_parent = find(parent, x);
        int y_set_parent = find(parent, y);
        parent[y_set_parent] = x_set_parent;
    }
	
	/**
	 * 
	 * @param g graph to find mst
	 * @param sortOrder int 1 for max reliability, 2 for min cost
	 * @return
	 */
    public Graph mst(Graph g, int sortOrder){
    	PriorityQueue<Edge> pq;
    	
		if(sortOrder == 1) { //sort by highest reliability first
			pq = new PriorityQueue<>(g.edges.size(), Comparator.comparing(Edge::getReliability).reversed());
		} else { //sort by min cost
			pq = new PriorityQueue<>(g.edges.size(), Comparator.comparing(Edge::getCost));
		}

        for(Edge e: g.edges) {
        	pq.add(e);
        }

        int[] parent = new int[g.vertices.size()];

        makeSet(parent, g.vertices.size());

        Graph mst = new Graph();
        ArrayList<Edge> mstEdges = new ArrayList<>();

        //process vertices - 1 edges
        int index = 0;
        while(index < g.vertices.size() - 1) {
            Edge edge = pq.remove();

            //check if adding this edge creates a cycle
            int x_set = find(parent, edge.v1.getLabel());
            int y_set = find(parent, edge.v2.getLabel());

            if(x_set==y_set){
                //ignore, will create cycle
            }else {
                //add it to our final result
            	mstEdges.add(edge);
                index++;
                union(parent,x_set,y_set);
            }
        }
        mst.edges = mstEdges;
        return mst;
    }

}

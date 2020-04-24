import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

	HashMap<Integer, Vertex> vertices;
	
	ArrayList<Edge> edges;

	public Graph() {
		this.vertices = new HashMap<Integer, Vertex>();
		this.edges = new ArrayList<Edge>();
	}

	public void addVertex(Vertex v) {
		this.vertices.put(v.getLabel(), v);
	}
	
	public void addVertices(HashMap<Integer, Vertex> vertices) {
		for(Vertex v: vertices.values()) {
			this.vertices.put(v.getLabel(), v);
		}
	}

	public void addEdge(Edge e) {
		this.edges.add(e);
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}
	
	//replace edge list in vertex with function to get neighbors of a vertex in a graph dynamically to avoid updating the list over time
	public ArrayList<Edge> getNeighbors(Vertex v) {
		ArrayList<Edge> neighbors = new ArrayList<Edge>();
		for(Edge e: this.edges) {
			if(e.v1 == v || e.v2 == v) {
				neighbors.add(e);
			}
		}
		return neighbors;
	}
	
	//replaces getEdgeBetween in vertex class to avoid maintaining list of edges in vertex
	public Edge getEdgeBetween(Vertex u, Vertex v) {
		for(Edge e: this.edges) {
			if((e.v1 == u && e.v2 == v) || (e.v1 == v && e.v2 == u)) {
				return e;
			}
		}
		return null;
	}
	
	public void print() {
		for(Edge e: this.edges) {
			System.out.println(e);
		}
	}

}

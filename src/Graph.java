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

}

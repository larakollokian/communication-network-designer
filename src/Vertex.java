//import java.util.ArrayList;

public class Vertex {
	
	private int label;
	
	//private ArrayList<Edge> edges; //might remove, replace with neighbors function in graph class
	
	public Vertex(int label) {
		this.label = label;
		//this.edges = new ArrayList<Edge>();
	}

	public int getLabel() {
		return label;
	}

//	public void addEdge(Edge e) {
//		edges.add(e);
//	}
//	
//	public void removeEdge(Edge e) {
//		edges.remove(e);
//	}
//	
//	//also replaced with function in graph class
//	public Edge getEdgeBetween(Vertex v) {
//		
//		for(Edge e: edges) {
//			if(e.v1 == v || e.v2 == v) {
//				return e;
//			}
//		}
//		return null;
//	}

	@Override
	public String toString() {
		return label+"";
	}
	
	

}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Graph class
 * DFS code adapted from the following link
 * https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
 *
 */
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
	public ArrayList<Vertex> getNeighbors(Vertex v) {
		ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
		for(Edge e: this.edges) {
			if(e.v1 == v) {
				neighbors.add(e.v2);
			} else if(e.v2 == v) {
				neighbors.add(e.v1);
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

	public ArrayList<Edge> hasCycle() {
		//keep track of vertices you visited:
		boolean[] visited = new boolean[this.vertices.size()];
		for(int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		//call helper function
		Stack<Vertex> test = new Stack<Vertex>();
		for(int k = 0; k < visited.length; k++) {
			if(!visited[k]) {
				test.removeAllElements();
				if(isCyclic(k, visited, -1, test)) {
					//cycle found, returns edges in cycle
					ArrayList<Edge> cycle = getEdgesInCycle(test);
					return cycle; //true
				}
			}
		}
		//no cycle found, returns empty list
		return null; //false
	}

	private boolean isCyclic(int vertex, boolean[] visited, int parent, Stack<Vertex> cycle) {
		// Mark the current node as visited 
		visited[vertex] = true; 
		int i; 

		// Recur for all the vertices adjacent to this vertex 
		ArrayList<Vertex> neighbors = this.getNeighbors(this.vertices.get(vertex));
		for(Vertex n: neighbors) {
			i = n.getLabel();
			cycle.push(n);
			if(i != parent) { //check for neighbors that arent parent
				if(visited[i]) {
					return true; //cycle detected
				} else {
					if (isCyclic(i, visited, vertex, cycle)) {
						return true; //cycle detected in recursion
					} 
				}
			}
			cycle.pop();
		}
		return false;
	}

	private ArrayList<Edge> getEdgesInCycle(Stack<Vertex> stack) {
		ArrayList<Edge> cycle = new ArrayList<Edge>();
		Vertex first = stack.pop();
		Vertex v1 = first;
		Vertex v2;
		while(!stack.isEmpty()) {
			v2 = stack.pop();
			Edge e = getEdgeBetween(v1, v2);
			cycle.add(e);
			v1 = v2;
		}
		if(first.getLabel() == 0) {
			Edge e = getEdgeBetween(v1, first);
			cycle.add(e);
		}
		return cycle;
	}

	public ArrayList<Edge> getEdgesNotInCycle(ArrayList<Edge> cycle) {
		ArrayList<Edge> notCycle = new ArrayList<Edge>();
		for(Edge e: this.edges) {
			if(!cycle.contains(e)) {
				notCycle.add(e);
			}
		}
		return notCycle;
	}
}

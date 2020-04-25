
public class Edge {

	Vertex v1;

	Vertex v2;

	double cost;

	double reliability;

	public Edge(Vertex v1, Vertex v2, double cost, double reliability) {
		this.v1 = v1;
		this.v2 = v2;
		this.cost = cost;
		this.reliability = reliability;
	}

	public Edge() {

	}

	public double getCost() {
		return cost;
	}

	public double getReliability() {
		return reliability;
	}

	@Override
	public String toString() {
		return "Edge " + v1 + "<-->" + v2 + ", cost:" + cost + ", reliability:" + reliability;
	}

}

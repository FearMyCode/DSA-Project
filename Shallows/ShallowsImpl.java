import java.util.ArrayList;
import java.util.PriorityQueue;

//Kang Chau Yip 23268041
//Passes all large tests

/**
 * An implementation of the Shallows problem from the 2022 CITS2200 Project
 */
public class ShallowsImpl implements Shallows {
	/**
	 * Apply Dijkstra's algorithm to find the possible maximum depth from a origin
	 * port in a directed, weighted graph. Return an array of the distances from the
	 * specified start port to each of the port in the graph.
	 * 
	 * @param ports  the number of ports
	 * @param lanes  a list of shipping lanes
	 * @param origin the index of the origin port
	 * @return weight an array of the maximum draught a ship can have to each port
	 */
	public int[] maximumDraughts(int ports, Lane[] lanes, int origin) {
		int numVertices = ports;
		int[] visited = new int[numVertices];
		int[] weight = new int[numVertices];

		ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<ArrayList<Integer>>();

		// Initializes the vertices that have not been seen yet
		for (int i = 0; i < numVertices; i++) {
			visited[i] = 0;
			weight[i] = 0; // infinity
			adjacencyList.add(new ArrayList<Integer>());
		}

		// go through the lanes
		for (int laneIndex = 0; laneIndex < lanes.length; laneIndex++) {
			int start = lanes[laneIndex].depart;
			// there is already have an edge, add it to the adjacencyList
			adjacencyList.get(start).add(laneIndex);
		}

		// Dijkstra's algorithm new
		PriorityQueue<Edge> pQueue = new PriorityQueue<Edge>();
		weight[origin] = Integer.MAX_VALUE;
		pQueue.add(new Edge(origin, weight[origin]));

		while (!pQueue.isEmpty()) {
			// current is the head of the queue, refreshing by removing the first vertices
			int current = pQueue.remove().port;
			int size = adjacencyList.get(current).size();
			// go through the lanes that has an edge with the port(current)
			for (int laneIndex = 0; laneIndex < size; laneIndex++) {
				int currentLane = adjacencyList.get(current).get(laneIndex);
				int depth = lanes[currentLane].depth;
				int end = lanes[currentLane].arrive;
				// if there is an edge and haven't seen yet
				if (visited[end] != 1) {
					// if the new weight is bigger than the weight of arrive
					if (depth > weight[end]) {
						// if the current weight is the smaller(safer) than end weight
						// update the end weight with current weight
						// otherwise update the end weight with the depth
						if (weight[current] < depth)
							weight[end] = weight[current];
						else {
							weight[end] = depth;
						}
						// the larger number is at the front
						pQueue.add(new Edge(end, weight[end]));
					}
				}
			}
			visited[current] = 1;
		}
		weight[origin] = Integer.MAX_VALUE;
		return weight;
	}

	/**
	 * Inner-class of priority queue to store a given vertex with a priority, and
	 * the larger priority is at the front
	 */
	private class Edge implements Comparable<Edge> {
		int port, priority;

		Edge(int port, int priority) {
			this.port = port;
			this.priority = priority;
		}

		// Comparisons that give higher priority to lower valued edges.
		public int compareTo(Edge current) {
			// return 1 if current priority is bigger
			return Integer.compare(current.priority, this.priority);
		}
	}

}

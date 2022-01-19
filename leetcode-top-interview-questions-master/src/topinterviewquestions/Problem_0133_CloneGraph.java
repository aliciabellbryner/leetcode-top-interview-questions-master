package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Problem_0133_CloneGraph {

	class Node {
		public int val;
		public List<Node> neighbors;
		public Node() {
			val = 0;
			neighbors = new ArrayList<Node>();
		}
		public Node(int _val) {
			val = _val;
			neighbors = new ArrayList<Node>();
		}
		public Node(int _val, ArrayList<Node> _neighbors) {
			val = _val;
			neighbors = _neighbors;
		}
	}

	//https://leetcode.com/problems/clone-graph/solution/
	//Approach 1: Depth First Search
	//Time Complexity: O(N+M), where N is a number of nodes (vertices) and M is a number of edges.
	//Space Complexity: O(N). This space is occupied by the visited hash map and in addition to that,
	// space would also be occupied by the recursion stack since we are adopting a recursive approach here.
	// The space occupied by the recursion stack would be equal to O(H) where H is the height of the graph.
	// Overall, the space complexity would be O(N).
	private HashMap<Node, Node> visited = new HashMap<>();
	public Node cloneGraph(Node node) {
		if (node == null) {
			return node;
		}
		// If the node was already visited before.
		// Return the clone from the visited dictionary.
		if (visited.containsKey(node)) {
			return visited.get(node);
		}
		// Create a clone for the given node.
		// Note that we don't have cloned neighbors as of now, hence [].
		Node res = new Node(node.val, new ArrayList());
		// The key is original node and value being the clone node.
		visited.put(node, res);
		// Iterate through the neighbors to generate their clones
		// and prepare a list of cloned neighbors to be added to the cloned node.
		for (Node neighbor: node.neighbors) {
			res.neighbors.add(cloneGraph(neighbor));
		}
		return res;
	}

	//Approach 2: Breadth First Search
	//Time Complexity : O(N + M)O(N+M), where NN is a number of nodes (vertices) and MM is a number of edges.
	//Space Complexity : O(N). This space is occupied by the visited dictionary and in addition to that, space would also be occupied by the queue since we are adopting the BFS approach here.
	// The space occupied by the queue would be equal to O(W) where W is the width of the graph. Overall, the space complexity would be O(N).
	public Node cloneGraph2(Node node) {
		if (node == null) {
			return node;
		}
		// Hash map to save the visited node and it's respective clone
		// as key and value respectively. This helps to avoid cycles.
		HashMap<Node, Node> visited = new HashMap();
		// Put the first node in the queue
		LinkedList<Node> queue = new LinkedList<> ();
		queue.add(node);
		// Clone the node and put it in the visited dictionary.
		visited.put(node, new Node(node.val, new ArrayList()));
		// Start BFS traversal
		while (!queue.isEmpty()) {
			// Pop a node say "n" from the from the front of the queue.
			Node cur = queue.poll();
			// Iterate through all the neighbors of the node "cur"
			for (Node neighbor: cur.neighbors) {
				if (!visited.containsKey(neighbor)) {
					// Clone the neighbor and put in the visited, if not present already
					visited.put(neighbor, new Node(neighbor.val, new ArrayList()));
					// Add the newly encountered node to the queue.
					queue.add(neighbor);
				}
				// Add the clone of the neighbor to the neighbors of the clone node "cur".
				visited.get(cur).neighbors.add(visited.get(neighbor));
			}
		}
		// Return the clone of the node from visited.
		return visited.get(node);
	}
}

package topinterviewquestions;
/*
Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the LRUCache class:

LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
int get(int key) Return the value of the key if the key exists, otherwise return -1.
void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
The functions get and put must each run in O(1) average time complexity.

Example 1:

Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4


Constraints:

1 <= capacity <= 3000
0 <= key <= 104
0 <= value <= 105
At most 2 * 105 calls will be made to get and put.
 */
import java.util.HashMap;

/*
 * 在leetcode上提交时，把文字替换成下面的代码
 * 然后把类名、构造方法名从Problem_0146_LRUCache改为LRUCache即可
 */

public class Problem_0146_LRUCache {

	public class Node {
		Node pre;
		Node next;
		int key;
		int val;
		public Node(int k, int v) {
			this.key = k;
			this.val = v;
		}
	}
	final int capacity;
	Node dummyRoot = new Node(0,0);//this is the dummy root
	Node tail = dummyRoot;
	HashMap<Integer, Node> map = new HashMap<>();

	public Problem_0146_LRUCache(int capacity) {
		this.capacity = capacity;
	}

	public int get(int key) {
		Node node = map.getOrDefault(key, null);
		if (node != null) {
			moveToTail(node);
		}
		return node == null ? -1 : node.val;
	}

	public void put(int key, int value) {
		Node node;
		if (map.containsKey(key)) {
			node = map.get(key);
			node.val = value;
		} else {
			node = new Node(key, value);
			map.put(key, node);
		}
		moveToTail(node);
		if (map.size() > capacity) {
			deleteTheHead();
		}
	}

	private void deleteTheHead(){
		Node toDelete = dummyRoot.next;
		dummyRoot.next = toDelete.next;
		toDelete.next.pre = dummyRoot;
		toDelete.pre = null;
		toDelete.next = null;
		map.remove(toDelete.key);
	}

	private void moveToTail(Node node) {
		if (node == tail) {
			return;
		}
		if (node.pre != null) {
			node.pre.next = node.next;
		}
		if (node.next != null) {
			node.next.pre = node.pre;
		}
		node.pre = tail;
		node.next = null;
		tail.next = node;
		tail = node;
	}


	//zuo's solution: toooo complicated!!! don't use!!!
//	private MyCache<Integer, Integer> cache;
//
//	public Problem_0146_LRUCache(int capacity) {
//		cache = new MyCache<>(capacity);
//	}
//
//	public int get(int key) {
//		Integer ans = cache.get(key);
//		return ans == null ? -1 : ans;
//	}
//
//	public void put(int key, int value) {
//		cache.set(key, value);
//	}
//
//	public static class Node<K, V> {
//		public K key;
//		public V value;
//		public Node<K, V> last;
//		public Node<K, V> next;
//
//		public Node(K key, V value) {
//			this.key = key;
//			this.value = value;
//		}
//	}
//
//	public static class NodeDoubleLinkedList<K, V> {
//		private Node<K, V> head;
//		private Node<K, V> tail;
//
//		public NodeDoubleLinkedList() {
//			head = null;
//			tail = null;
//		}
//
//		public void addNode(Node<K, V> newNode) {
//			if (newNode == null) {
//				return;
//			}
//			if (head == null) {
//				head = newNode;
//				tail = newNode;
//			} else {
//				tail.next = newNode;
//				newNode.last = tail;
//				tail = newNode;
//			}
//		}
//
//		public void moveNodeToTail(Node<K, V> node) {
//			if (tail == node) {
//				return;
//			}
//			// node 不是尾巴
//			if (head == node) {
//				head = node.next;
//				head.last = null;
//			} else {
//				node.last.next = node.next;
//				node.next.last = node.last;
//			}
//			node.last = tail;
//			node.next = null;
//			tail.next = node;
//			tail = node;
//		}
//
//		public Node<K, V> removeHead() {
//			if (head == null) {
//				return null;
//			}
//			Node<K, V> res = head;
//			if (head == tail) { // 链表中只有一个节点的时候
//				head = null;
//				tail = null;
//			} else {
//				head = res.next;
//				res.next = null;
//				head.last = null;
//			}
//			return res;
//		}
//
//	}
//
//	public static class MyCache<K, V> {
//		private HashMap<K, Node<K, V>> keyNodeMap;
//		private NodeDoubleLinkedList<K, V> nodeList;
//		private final int capacity;
//
//		public MyCache(int cap) {
//			if (cap < 1) {
//				throw new RuntimeException("should be more than 0.");
//			}
//			keyNodeMap = new HashMap<K, Node<K, V>>();
//			nodeList = new NodeDoubleLinkedList<K, V>();
//			capacity = cap;
//		}
//
//		public V get(K key) {
//			if (keyNodeMap.containsKey(key)) {
//				Node<K, V> res = keyNodeMap.get(key);
//				nodeList.moveNodeToTail(res);
//				return res.value;
//			}
//			return null;
//		}
//
//		public void set(K key, V value) {
//			if (keyNodeMap.containsKey(key)) {
//				Node<K, V> node = keyNodeMap.get(key);
//				node.value = value;
//				nodeList.moveNodeToTail(node);
//			} else {
//				if (keyNodeMap.size() == capacity) {
//					removeMostUnusedCache();
//				}
//				Node<K, V> newNode = new Node<K, V>(key, value);
//				keyNodeMap.put(key, newNode);
//				nodeList.addNode(newNode);
//			}
//		}
//
//		private void removeMostUnusedCache() {
//			Node<K, V> removeNode = nodeList.removeHead();
//			keyNodeMap.remove(removeNode.key);
//		}
//
//	}

}

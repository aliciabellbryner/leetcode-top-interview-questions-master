package topinterviewquestions;

import java.util.*;
/*
Implement the RandomizedSet class:

RandomizedSet() Initializes the RandomizedSet object.
bool insert(int val) Inserts an item val into the set if not present. Returns true if the item was not present, false otherwise.
bool remove(int val) Removes an item val from the set if present. Returns true if the item was present, false otherwise.
int getRandom() Returns a random element from the current set of elements (it's guaranteed that at least one element exists when this method is called). Each element must have the same probability of being returned.
You must implement the functions of the class such that each function works in average O(1) time complexity.



Example 1:

Input
["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
[[], [1], [2], [2], [], [1], [2], []]
Output
[null, true, false, true, 2, true, false, 2]

Explanation
RandomizedSet randomizedSet = new RandomizedSet();
randomizedSet.insert(1); // Inserts 1 to the set. Returns true as 1 was inserted successfully.
randomizedSet.remove(2); // Returns false as 2 does not exist in the set.
randomizedSet.insert(2); // Inserts 2 to the set, returns true. Set now contains [1,2].
randomizedSet.getRandom(); // getRandom() should return either 1 or 2 randomly.
randomizedSet.remove(1); // Removes 1 from the set, returns true. Set now contains [2].
randomizedSet.insert(2); // 2 was already in the set, so return false.
randomizedSet.getRandom(); // Since 2 is the only number in the set, getRandom() will always return 2.


Constraints:

-231 <= val <= 231 - 1
At most 2 * 105 calls will be made to insert, remove, and getRandom.
There will be at least one element in the data structure when getRandom is called.
 */
public class Problem_0380_InsertDeleteGetRandom {
//leetcode solution: https://leetcode.com/problems/insert-delete-getrandom-o1/solution/
	//这个方案是针对不支持duplicate的解法，如果duplicate它就自动忽略，后面有支持duplicate的解法
	class RandomizedSet2 {
		HashMap<Integer, Integer> map;//key是存的数，value是这个数的idx
		List<Integer> list;
		Random rand;
		public RandomizedSet2() {
			map = new HashMap<>();
			list = new ArrayList<>();
			rand = new Random();
		}

		public boolean insert(int val) {
			if (map.containsKey(val)) {
				return false;
			}
			map.put(val, list.size());
			list.add(val);
			return true;
		}
		public boolean remove(int val) {
			if (!map.containsKey(val)) {
				return false;
			}
			int lastElement = list.get(list.size() - 1);//最后一个数的数值
			int idx = map.get(val);//val对应的idx
			list.set(idx, lastElement);//把最后一个数移到val对应的idx位置
			map.put(lastElement, idx);
			map.remove(val);
			list.remove(list.size() - 1);
			return true;
		}
		public int getRandom() {
			return list.get(rand.nextInt(list.size()));
		}
	}


	//followup: how to support duplicate elements?
	//就是把同个数值对应的idx放到hashmap中的hashset中去
	class RandomizedSet3 {
		HashMap<Integer, Set<Integer>> map;
		List<Integer> list;
		Random rand;
		public RandomizedSet3() {
			map = new HashMap<>();
			list = new ArrayList<>();
			rand = new Random();
		}
		public boolean insert(int val) {
			boolean contain = map.containsKey(val);
			if (!contain) {
				map.put(val, new HashSet<>());
			}
			map.get(val).add(list.size());
			list.add(val);
			return !contain;
		}


		public boolean remove(int val) {
			boolean contain = map.containsKey(val);
			if (!contain) {
				return false;
			}
			int pos = map.get(val).iterator().next();
			map.get(val).remove(pos);
			if (pos < list.size() - 1) {
				int lastElement = list.get(list.size() - 1);
				list.set(pos, lastElement);
				map.get(lastElement).remove(list.size() - 1);
				map.get(lastElement).add(pos);
			}
			list.remove(list.size() - 1);
			if (map.get(val).isEmpty()) {
				map.remove(val);
			}
			return true;
		}


		public int getRandom() {
			return list.get(rand.nextInt(list.size()));
		}
	}


	//Zuo's solution: not very good
	public class RandomizedSet {

		private HashMap<Integer, Integer> keyIndexMap;
		private HashMap<Integer, Integer> indexKeyMap;
		private int size;

		public RandomizedSet() {
			keyIndexMap = new HashMap<Integer, Integer>();
			indexKeyMap = new HashMap<Integer, Integer>();
			size = 0;
		}

		public boolean insert(int val) {
			if (!keyIndexMap.containsKey(val)) {
				keyIndexMap.put(val, size);
				indexKeyMap.put(size++, val);
				return true;
			}
			return false;
		}

		public boolean remove(int val) {
			if (keyIndexMap.containsKey(val)) {
				int deleteIndex = keyIndexMap.get(val);
				int lastIndex = --size;
				int lastKey = indexKeyMap.get(lastIndex);
				keyIndexMap.put(lastKey, deleteIndex);
				indexKeyMap.put(deleteIndex, lastKey);
				keyIndexMap.remove(val);
				indexKeyMap.remove(lastIndex);
				return true;
			}
			return false;
		}

		public int getRandom() {
			if (size == 0) {
				return -1;
			}
			int randomIndex = (int) (Math.random() * size);
			return indexKeyMap.get(randomIndex);
		}
	}

}

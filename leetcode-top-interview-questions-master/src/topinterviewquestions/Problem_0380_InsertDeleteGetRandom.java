package topinterviewquestions;

import java.util.*;

public class Problem_0380_InsertDeleteGetRandom {
//leetcode solution:
	class RandomizedSet2 {
		HashMap<Integer, Integer> map;
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
			int lastElement = list.get(list.size() - 1);
			int idx = map.get(val);
			list.set(idx, lastElement);
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

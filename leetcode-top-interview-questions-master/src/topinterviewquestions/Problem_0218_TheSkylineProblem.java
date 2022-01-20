package topinterviewquestions;

import java.util.*;
import java.util.Map.Entry;

public class Problem_0218_TheSkylineProblem {

	public static class Node {
		public int x;
		public boolean isAdd;//代表加还是减，头节点加尾节点减
		public int h;

		public Node(int x, boolean isAdd, int h) {
			this.x = x;
			this.isAdd = isAdd;
			this.h = h;
		}
	}

	//time O(NlogN) for the sort
	//space O(N) for the maps
	public static List<List<Integer>> getSkyline(int[][] buildings) {
		Node[] nodes = new Node[buildings.length * 2];//一个大楼两个对象，分别是头和尾记录头尾信息
		for (int i = 0; i < buildings.length; i++) {
			nodes[i * 2] = new Node(buildings[i][0], true, buildings[i][2]);
			nodes[i * 2 + 1] = new Node(buildings[i][1], false, buildings[i][2]);
		}
//		Arrays.sort(nodes, new NodeComparator());//如果有纸片楼再考虑用这个函数
		Arrays.sort(nodes, (o1, o2) -> o1.x - o2.x);
		// 有序表，key 代表某个高度 value 这个高度出现的次数
		TreeMap<Integer, Integer> heightToTimes = new TreeMap<>();//这个treemap的作用是为了辅助找到每个点最大的高度
		// 有序表 key x的值 value 处在x位置时的高度
		TreeMap<Integer, Integer> xToMaxheight = new TreeMap<>();//也可以是hashmap，用treemap的目的是为了最后生成结果的时候方便遍历
		for (int i = 0; i < nodes.length; i++) {//找到每一个高度可能变化的节点对应的最大高度信息存到xToMaxheight中去
			if (nodes[i].isAdd) {
				if (!heightToTimes.containsKey(nodes[i].h)) {
					heightToTimes.put(nodes[i].h, 1);
				} else {
					heightToTimes.put(nodes[i].h, heightToTimes.get(nodes[i].h) + 1);
				}
			} else {
				if (heightToTimes.get(nodes[i].h) == 1) {
					heightToTimes.remove(nodes[i].h);
				} else {
					heightToTimes.put(nodes[i].h, heightToTimes.get(nodes[i].h) - 1);
				}
			}
			if (heightToTimes.isEmpty()) {
				xToMaxheight.put(nodes[i].x, 0);//这句再前面的if else后面说明x的maxheight以最后一次为准
			} else {
				xToMaxheight.put(nodes[i].x, heightToTimes.lastKey());//lastKey()返回的是treemap中key的最大值
			}
		}
		List<List<Integer>> res = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : xToMaxheight.entrySet()) {//把高度变化的节点信息存到res里
			int curX = entry.getKey();
			int curMaxHeight = entry.getValue();
			if (res.isEmpty() || res.get(res.size() - 1).get(1) != curMaxHeight) {//||左边表示当第一次进入循环的时候要加，||右边表示如果最大高度没变的时候不加sub arraylist
				res.add(new ArrayList<>(Arrays.asList(curX, curMaxHeight)));
			}
		}
		return res;
	}


	public static class NodeComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			if (o1.x != o2.x) {
				return o1.x - o2.x;
			}
			if (o1.isAdd != o2.isAdd) {//如果题中没有纸片楼（【3，3.7】，意味着有两个node[3,false,7]和[3,true,7]如果false那个出现在前面，
				// 则line59heightToTimes.get(nodes[i].h) - 1)就会报错，因为这个时候根本就找不到mpHeigghtTime。get（3）的记录，所以也没办法减1，
				//如果有纸片楼那就必须把isAdd=true的排前面，也就是return o1.isAdd ? -1 : 1;
				// 但如果没有这种奇葩的纸片楼，不加这个也行，直接用x升序排列就行
				return o1.isAdd ? -1 : 1;
			}
			return 0;
		}
	}


}

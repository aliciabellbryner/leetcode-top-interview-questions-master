package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
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

	public static class NodeComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			if (o1.x != o2.x) {
				return o1.x - o2.x;
			}
			if (o1.isAdd != o2.isAdd) {//如果题中没有纸片楼（【3，3.7】，意味着有两个node[3,false,7]和[3,true,7]如果false那个出现在前面，则line59就会报错，因为这个时候根本就找不到mpHeigghtTime。get（3）的记录，所以也没办法减1，
				//如果有纸片楼那就必须把isAdd=true的排前面，也就是return o1.isAdd ? -1 : 1;
				// 但如果没有这种奇葩的纸片楼，不加这个也行，直接用x升序排列就行
				return o1.isAdd ? -1 : 1;
			}
			return 0;
		}
	}

	public static List<List<Integer>> getSkyline(int[][] matrix) {
		Node[] nodes = new Node[matrix.length * 2];//一个大楼两个对象，分别是头和尾记录头尾信息
		for (int i = 0; i < matrix.length; i++) {
			nodes[i * 2] = new Node(matrix[i][0], true, matrix[i][2]);
			nodes[i * 2 + 1] = new Node(matrix[i][1], false, matrix[i][2]);
		}
		Arrays.sort(nodes, new NodeComparator());
		// 有序表，key 代表某个高度 value 这个高度出现的次数
		TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
		// 有序表 key x的值 value 处在x位置时的高度
		TreeMap<Integer, Integer> xMaxHeight = new TreeMap<>();//也可以是hashmap，用treemap的目的是为了最后生成结果的时候方便遍历
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].isAdd) {
				if (!mapHeightTimes.containsKey(nodes[i].h)) {
					mapHeightTimes.put(nodes[i].h, 1);
				} else {
					mapHeightTimes.put(nodes[i].h, mapHeightTimes.get(nodes[i].h) + 1);
				}
			} else {
				if (mapHeightTimes.get(nodes[i].h) == 1) {
					mapHeightTimes.remove(nodes[i].h);
				} else {
					mapHeightTimes.put(nodes[i].h, mapHeightTimes.get(nodes[i].h) - 1);
				}
			}
			if (mapHeightTimes.isEmpty()) {
				xMaxHeight.put(nodes[i].x, 0);//这句再前面的if else后面说明x的maxheight以最后一次为准
			} else {
				xMaxHeight.put(nodes[i].x, mapHeightTimes.lastKey());
			}
		}
		List<List<Integer>> ans = new ArrayList<>();
		for (Entry<Integer, Integer> entry : xMaxHeight.entrySet()) {
			int curX = entry.getKey();
			int curMaxHeight = entry.getValue();
			if (ans.isEmpty() || ans.get(ans.size() - 1).get(1) != curMaxHeight) {//最大高度没变的时候不加sub arraylist
				ans.add(new ArrayList<>(Arrays.asList(curX, curMaxHeight)));
			}
		}
		return ans;
	}

}

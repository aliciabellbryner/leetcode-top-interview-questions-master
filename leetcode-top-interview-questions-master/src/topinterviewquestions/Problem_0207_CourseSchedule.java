package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0207_CourseSchedule {

	// 一个info，就是一个课程
	// name是课程的编号
	// in是课程的入度
	//Time Complexity: O(∣E∣+∣V∣) where |V| is the number of courses, and |E| is the number of dependencies.
		//As in the previous algorithm, it would take us |E|∣E∣ time complexity to build a graph in the first step.
		//Similar with the above postorder DFS traversal, we would visit each vertex and each edge once and only once in the worst case, i.e. ∣E∣+∣V∣.
		//As a result, the overall time complexity of the algorithm would be O(2⋅∣E∣+∣V∣)=O(∣E∣+∣V∣).
	//Space Complexity: O(∣E∣+∣V∣) where |V| is the number of courses, and |E| is the number of dependencies.
		//	We built a graph data structure in the algorithm, which would consume |E| + |V|∣E∣+∣V∣ space.
		//In addition, we use a container to keep track of the courses that have no prerequisite, and the size of the container would be bounded by ∣V∣.
		//As a result, the overall space complexity of the algorithm would be  O(∣E∣+2⋅∣V∣)=O(∣E∣+∣V∣).
	public static class Info {
		public int name;
		public int in;//indegree：代表要上这个课需要提前修多少们课
		public ArrayList<Info> nexts;//代表上完这个课继续可以qualify上的课

		public Info(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}

	//use topology sort
	public static boolean canFinish(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) {
			return true;
		}
		HashMap<Integer, Info> nodes = new HashMap<>();//存放所有的课对应的node信息，key是课的数字编号，value是对应的具有in、nexts信息的node
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1];
			if (!nodes.containsKey(to)) {
				nodes.put(to, new Info(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Info(from));
			}
			Info t = nodes.get(to);
			Info f = nodes.get(from);
			f.nexts.add(t);
			t.in++;
		}
		int needPrerequisiteNums = nodes.size();//代表所有需要预修课才能上的课的总数
		Queue<Info> zeroInQueue = new LinkedList<>();
		for (Info info : nodes.values()) {
			if (info.in == 0) {
				zeroInQueue.add(info);
			}
		}
		int count = 0;//代表每次剥离一门课，然后一层一层找indegree=0的课的个数
		while (!zeroInQueue.isEmpty()) {
			Info cur = zeroInQueue.poll();
			count++;
			for (Info next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums;
	}

}

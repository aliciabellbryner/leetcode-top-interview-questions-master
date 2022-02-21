package topinterviewquestions;
/*
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.



Example 1:

Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].
Example 2:

Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].
Example 3:

Input: numCourses = 1, prerequisites = []
Output: [0]


Constraints:

1 <= numCourses <= 2000
0 <= prerequisites.length <= numCourses * (numCourses - 1)
prerequisites[i].length == 2
0 <= ai, bi < numCourses
ai != bi
All the pairs [ai, bi] are distinct.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0210_CourseScheduleII {

	//思路是用topology sort来解
	//same as 207 CourseSchedule
	//	//Time Complexity: O(∣E∣+∣V∣) where |V| is the number of courses, and |E| is the number of dependencies.
	//		//As in the previous algorithm, it would take us |E| time complexity to build a graph in the first step.
	//		//Similar with the above postorder DFS traversal, we would visit each vertex and each edge once and only once in the worst case, i.e. ∣E∣+∣V∣.
	//		//As a result, the overall time complexity of the algorithm would be O(2⋅∣E∣+∣V∣)=O(∣E∣+∣V∣).
	//	//Space Complexity: O(∣E∣+∣V∣) where |V| is the number of courses, and |E| is the number of dependencies.
	//		//	We built a graph data structure in the algorithm, which would consume |E| + |V|∣E∣+∣V∣ space.
	//		//In addition, we use a container to keep track of the courses that have no prerequisite, and the size of the container would be bounded by ∣V∣.
	//		//As a result, the overall space complexity of the algorithm would be  O(∣E∣+2⋅∣V∣)=O(∣E∣+∣V∣).
	public static class Info {
		public int name;
		public int in;
		public ArrayList<Info> nexts;

		public Info(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}

	public int[] findOrder(int numCourses, int[][] prerequisites) {
		int[] res = new int[numCourses];
		for (int i = 0; i < numCourses; i++) {
			res[i] = i;
		}
		if (prerequisites == null || prerequisites.length == 0) {
			return res;
		}
		HashMap<Integer, Info> nodes = new HashMap<>();
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
		int index = 0;
		Queue<Info> zeroInQueue = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (!nodes.containsKey(i)) {
				res[index++] = i;//把没有依赖关系可以独立上完的课放进去
			} else {
				if (nodes.get(i).in == 0) {
					zeroInQueue.add(nodes.get(i));//再把indegree是0的放进去
				}
			}
		}
		int needPrerequisiteNums = nodes.size();
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			Info cur = zeroInQueue.poll();
			res[index++] = cur.name;
			count++;
			for (Info next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums ? res : new int[0];
	}

}

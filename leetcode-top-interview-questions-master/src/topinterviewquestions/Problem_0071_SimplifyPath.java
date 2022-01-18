package topinterviewquestions;

import java.util.*;

public class Problem_0071_SimplifyPath {

	//The main idea is to push to the stack every valid file name (not in {"",".",".."}),
	// popping only if there's smth to pop and we met "..".
	//time O(N)
	//space O(N)
	public String simplifyPath(String path) {
		Deque<String> stack = new LinkedList<>();
		Set<String> set = new HashSet<>(Arrays.asList("..",".",""));
		for (String name : path.split("/")) {
			if (name.equals("..") && !stack.isEmpty()) {
				stack.pop();
			}
			else if (!set.contains(name)) {
				stack.push(name);
			}
		}
		String res = "";
		for (String dir : stack) {
			res = "/" + dir + res;
		}
		return res.isEmpty() ? "/" : res;
	}

}


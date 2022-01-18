package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

//DFS： backtracking
//Time O(3^n) worst： n是s的char个数，每一次必须从每一个char开始，有三种可能，一个char，两个，三个char，所以一共是3^n
//Space O(3^n) worst
public class Problem_0093_RestoreIPAddresses {
	public List<String> restoreIpAddresses(String s) {
		List<String> result = new ArrayList<>();
		doRestore(result, "", s, 0);
		return result;
	}

	private void doRestore(List<String> result, String path, String s, int k) {//k is the number of 数字between the .
		if (s.isEmpty() || k == 4) {//有四个数字同时没有剩余的阿拉伯数字可用，说明找到了
			if (s.isEmpty() && k == 4)
				result.add(path.substring(1));
			return;
		}
		for (int i = 1; i <= (s.charAt(0) == '0' ? 1 : 3) && i <= s.length(); i++) { // Avoid leading 0
			String part = s.substring(0, i);
			if (Integer.valueOf(part) <= 255)
				doRestore(result, path + "." + part, s.substring(i), k + 1);
		}
	}
}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0763_PartitionLabels {

	//time O(N), space O(1)
	public static List<Integer> partitionLabels(String S) {
		int[] lastIdx = new int[26];
		for (int i = 0; i < S.length(); i++) {//代表i位置这个char出现的最右的idx
			lastIdx[S.charAt(i) - 'a'] = i;
		}
		List<Integer> ans = new ArrayList<>();
		int left = 0;
		int right = lastIdx[S.charAt(0) - 'a'];
		for (int i = 1; i < S.length(); i++) {
			if (i > right) {
				ans.add(right - left + 1);
				left = i;
			}
			right = Math.max(right, lastIdx[S.charAt(i) - 'a']);
		}
		ans.add(right - left + 1);
		return ans;
	}

}

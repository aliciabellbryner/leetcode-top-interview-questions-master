package topinterviewquestions;

import java.util.ArrayList;
import java.util.TreeMap;

public class Problem_0673_NumberOfLongestIncreasingSubsequence {


	//https://leetcode.com/problems/number-of-longest-increasing-subsequence/discuss/107293/JavaC%2B%2B-Simple-dp-solution-with-explanation
	//len[i]: the length of the Longest Increasing Subsequence which ends with nums[i].
	//cnt[i]: the number of the Longest Increasing Subsequence which ends with nums[i].
	public int findNumberOfLIS(int[] nums) {
		int N = nums.length, res = 0, max_len = 0;
		int[] len =  new int[N], cnt = new int[N];
		for(int i = 0; i<N; i++){
			len[i] = cnt[i] = 1;
			for(int j = 0; j <i ; j++){
				if(nums[i] > nums[j]){
					if(len[i] == len[j] + 1) {
						cnt[i] += cnt[j];
					}
					if(len[i] < len[j] + 1) {
						len[i] = len[j] + 1;
						cnt[i] = cnt[j];
					}
				}
			}
			if(max_len == len[i]) {
				res += cnt[i];
			}
			if(max_len < len[i]){
				max_len = len[i];
				res = cnt[i];
			}
		}
		return res;
	}

	public static int findNumberOfLIS2(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}

		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();

		for (int i = 0; i < nums.length; i++) {
			int L = 0;
			int R = dp.size() - 1;
			int find = -1;
			while (L <= R) {
				int mid = (L + R) / 2;
				if (dp.get(mid).firstKey() >= nums[i]) {
					find = mid;
					R = mid - 1;
				} else {
					L = mid + 1;
				}
			}
			int num = 1;
			int index = find == -1 ? dp.size() : find;
			if (index > 0) {
				TreeMap<Integer, Integer> lastMap = dp.get(index - 1);
				num = lastMap.get(lastMap.firstKey());
				if (lastMap.ceilingKey(nums[i]) != null) {
					num -= lastMap.get(lastMap.ceilingKey(nums[i]));
				}
			}
			if (index == dp.size()) {
				TreeMap<Integer, Integer> newMap = new TreeMap<Integer, Integer>();
				newMap.put(nums[i], num);
				dp.add(newMap);
			} else {
				TreeMap<Integer, Integer> curMap = dp.get(index);
				curMap.put(nums[i], curMap.get(curMap.firstKey()) + num);
			}
		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

	public static int findNumberOfLIS3(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			int L = 0;
			int R = dp.size() - 1;
			int find = -1;
			while (L <= R) {
				int mid = (L + R) / 2;
				if (dp.get(mid).firstKey() >= nums[i]) {
					find = mid;
					R = mid - 1;
				} else {
					L = mid + 1;
				}
			}
			if (find == -1) {
				dp.add(new TreeMap<>());
				int index = dp.size() - 1;
				TreeMap<Integer, Integer> cur = dp.get(index);
				int size = 1;
				if (index > 0) {
					TreeMap<Integer, Integer> pre = dp.get(index - 1);
					size = pre.get(pre.firstKey());
					if (pre.ceilingKey(nums[i]) != null) {
						size -= pre.get(pre.ceilingKey(nums[i]));
					}
				}
				cur.put(nums[i], size);
			} else {
				int newAdd = 1;
				if (find > 0) {
					TreeMap<Integer, Integer> pre = dp.get(find - 1);
					newAdd = pre.get(pre.firstKey());
					if (pre.ceilingKey(nums[i]) != null) {
						newAdd -= pre.get(pre.ceilingKey(nums[i]));
					}
				}
				// >=nums[i] ?
				TreeMap<Integer, Integer> cur = dp.get(find);
				if (cur.firstKey() == nums[i]) {
					cur.put(nums[i], cur.get(nums[i]) + newAdd);
				} else {
					int preNum = cur.get(cur.firstKey());
					cur.put(nums[i], newAdd + preNum);
				}
			}

		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

}

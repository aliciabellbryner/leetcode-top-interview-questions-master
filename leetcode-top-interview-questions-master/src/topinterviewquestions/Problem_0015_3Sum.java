package topinterviewquestions;

import java.util.*;

public class Problem_0015_3Sum {

	//Time complexity: O(n^2): not good as threeSum1 as hashset took too much constant time
	//Explanation: Sorting takes O(nlogn) & 'for' loop and 'while' loop takes O(n^2) together. But since O(n^2) > O(nlogn). Therefore, O(n^2).
	//space should be O(k) where k is the number of solutions, so maybe O(1)? I think O(n) space refers to solution like building a hash table for original array.
	public  List<List<Integer>> threeSum(int[] nums) {
		Set<List<Integer>> res  = new HashSet<>();//has to use hashset to avoid duplicate
		if (nums.length==0) {
			return new ArrayList<>(res);
		}
		Arrays.sort(nums);
		for (int i=0; i<nums.length-2;i++){
			int l =i+1;
			int  r = nums.length-1;
			while(l<r){
				int sum = nums[i]+nums[l]+nums[r];
				if (sum==0) {
					res.add(Arrays.asList(nums[i],nums[l++],nums[r--]));
				} else if ( sum >0) {
					r--;
				} else {
					l++;
				}
			}

		}
		return new ArrayList<>(res);
	}

	//时间复杂度是O（N^2）:可以作的优化：比如排序好之后找的第一个数nums[i]第一个数大于sum/3则说明可以直接break，或者2sum里排序好的数组第一个数nums[L]>sum/2则可以直接break
	public static List<List<Integer>> threeSum1(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> ans = new ArrayList<>();
		// 第一个数选了i位置的数
		for (int i = 0; i < nums.length - 2; i++) {
			if (i == 0 || nums[i - 1] != nums[i]) {
				List<List<Integer>> nexts = twoSum1(nums, i + 1, -nums[i]);
				for (List<Integer> cur : nexts) {
					cur.add(0, nums[i]);
					ans.add(cur);
				}
			}
		}
		return ans;
	}

	// nums已经有序了
	// nums[begin......]范围上，找到累加和为target的所有二元组
	public static List<List<Integer>> twoSum1(int[] nums, int begin, int target) {
		int L = begin;
		int R = nums.length - 1;
		List<List<Integer>> ans = new ArrayList<>();
		while (L < R) {
			if (nums[L] + nums[R] > target) {
				R--;
			} else if (nums[L] + nums[R] < target) {
				L++;
			} else {
				if (L == begin || nums[L - 1] != nums[L]) {//必须要判断是否重复
					List<Integer> cur = new ArrayList<>();
					cur.add(nums[L]);
					cur.add(nums[R]);
					ans.add(cur);
				}
				L++;
			}
		}
		return ans;
	}

	public static List<List<Integer>> threeSum2(int[] nums) {
		Arrays.sort(nums);
		int N = nums.length;
		List<List<Integer>> ans = new ArrayList<>();
		for (int i = N - 1; i > 1; i--) {
			if (i == N - 1 || nums[i] != nums[i + 1]) {
				List<List<Integer>> nexts = twoSum2(nums, i - 1, -nums[i]);
				for (List<Integer> cur : nexts) {
					cur.add(nums[i]);
					ans.add(cur);
				}
			}
		}
		return ans;
	}

	public static List<List<Integer>> twoSum2(int[] nums, int end, int target) {
		int L = 0;
		int R = end;
		List<List<Integer>> ans = new ArrayList<>();
		while (L < R) {
			if (nums[L] + nums[R] > target) {
				R--;
			} else if (nums[L] + nums[R] < target) {
				L++;
			} else {
				if (L == 0 || nums[L - 1] != nums[L]) {
					List<Integer> cur = new ArrayList<>();
					cur.add(nums[L]);
					cur.add(nums[R]);
					ans.add(cur);
				}
				L++;
			}
		}
		return ans;
	}

}

package topinterviewquestions;

import java.util.*;

public class Problem_0046_Permutations {

	public static List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		if (nums == null || nums.length == 0) {
			return res;
		}
		permute(nums, res, new ArrayList<Integer>(), 0);
		return res;
	}

	private static void permute(int[] nums, List<List<Integer>> res, List<Integer> path, int pos) {
		if (pos == nums.length) {
			res.add(new ArrayList(path));
			return;
		}

		for (int i = pos; i < nums.length; i++) {
			path.add(nums[i]);
			swap(nums, pos, i);
			permute(nums, res, path,pos+1);
			swap(nums, pos, i);
			path.remove(path.size() - 1);
		}
		return;
	}


	//follow up: if nums has duplicate elements
	public static List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		if (nums == null || nums.length == 0) {
			return res;
		}
		Arrays.sort(nums);
		helper(nums,res,new ArrayList<>(), new boolean[nums.length]);
		return res;
	}

	private static void helper(int[] nums, List<List<Integer>> res, List<Integer> path, boolean[] visited) {
		if (path.size() == nums.length) {
			res.add(new ArrayList(path));
			return;
		}

		for (int i = 0; i < nums.length; i++) {
			if (visited[i] || (i != 0 && nums[i] == nums[i-1] && visited[i-1])) {
				continue;
			}
			path.add(nums[i]);
			visited[i] = true;
			helper(nums, res, path, visited);
			path.remove(path.size() - 1);
			visited[i] = false;
		}
	}

	private static void swap(int[] nums, int f, int s) {
		int temp = nums[f];
		nums[f] = nums[s];
		nums[s] = temp;
	}


	public static void main(String[] args) {
		int[] test = new int[]{1,2,1};
		int[] test2 = new int[]{1,1,3};
		System.out.println(permuteUnique(test));
	}

	public static List<List<Integer>> onClass(int[] nums) {//不是很好的办法，不推荐，看下面的用queue实现的bfs方法
		List<List<Integer>> ans = new ArrayList<>();
		HashSet<Integer> rest = new HashSet<>();
		for (int num : nums) {
			rest.add(num);
		}
		ArrayList<Integer> path = new ArrayList<>();
		f(rest, path, ans);
		return ans;
	}

	// rest中有剩余数字，已经选过的数字不在rest中，选过的数字在path里
	public static void f(HashSet<Integer> rest, ArrayList<Integer> path, List<List<Integer>> ans) {
		if (rest.isEmpty()) {
			ans.add(path);
		} else {
			for (int num : rest) {
				ArrayList<Integer> curPath = new ArrayList<>(path);
				curPath.add(num);
				HashSet<Integer> clone = cloneExceptNum(rest, num);
				f(clone, curPath, ans);
			}
		}
	}

	public static HashSet<Integer> cloneExceptNum(HashSet<Integer> rest, int num) {
		HashSet<Integer> clone = new HashSet<>(rest);
		clone.remove(num);
		return clone;
	}


}

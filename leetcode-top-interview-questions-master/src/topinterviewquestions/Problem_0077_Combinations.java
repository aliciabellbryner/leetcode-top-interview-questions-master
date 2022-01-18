package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem_0077_Combinations {

	//my solution: no duplicate elements
	public static List<List<Integer>> subsets_j(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		if (nums == null || nums.length == 0) {
			return res;
		}
		List<Integer> path = new ArrayList<>();
		Arrays.sort(nums);
		process_j(nums, path, 0, res);
		return res;
	}
	public static void process_j(int[] nums, List<Integer> path, int index, List<List<Integer>> res) {
		res.add(new ArrayList(path));//deep copy: java默认都是pass by reference ，这里不new的话传递的是指向path的reference，后面path改变，res中加入的path也会改变，最后返回就变成了[[],[],[],[]......]

		for (int i = index; i < nums.length; i++) {
			path.add(nums[i]);
			process_j(nums, path, i+1, res);
			path.remove(path.size()-1);
		}
	}

	//backtracking
	public static List<List<Integer>> combine(int n, int k) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		combine(res, new ArrayList<>(), 1, n, k);
		return res;
	}
	public static void combine(List<List<Integer>> res, List<Integer> path, int start, int n, int k) {
		if (k==0) {
			res.add(new ArrayList<>(path));
			return;
		}
		for(int i=start;i<=n;i++) {
			path.add(i);
			combine(res, path, i+1, n, k-1);
			path.remove(path.size()-1);//backtracking
		}
	}

	//my solution: with duplicate elements
	public static List<List<Integer>> subsets_dup(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		if (nums == null || nums.length == 0) {
			return res;
		}
		List<Integer> path = new ArrayList<>();
		Arrays.sort(nums);
		process_dup(nums, path, 0, res);
		return res;
	}
	public static void process_dup(int[] nums, List<Integer> path, int index, List<List<Integer>> res) {
		res.add(new ArrayList(path));
		for (int i = index; i < nums.length; i++) {
			if (i != index && (nums[i] == nums[i-1])) {
				continue;
			}
			path.add(nums[i]);
			process_dup(nums, path, i+1, res);
			path.remove(path.size()-1);
		}
	}

	public static void main(String[] args) {
		int[] test = new int[]{1,1,3};
		System.out.println(subsets_dup(test));
	}

}

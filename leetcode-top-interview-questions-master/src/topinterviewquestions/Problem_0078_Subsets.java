package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Problem_0078_Subsets {

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

package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem_0018_4Sum {
	//https://leetcode.com/problems/4sum/discuss/8609/My-solution-generalized-for-kSums-in-JAVA/201112
	//Time Complexity: O(n^3).
	//Space Complexity: quick sort O(logn)
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> res = new ArrayList<>();
		if(nums == null || nums.length < 4){  //corner case
			return res;
		}
		Arrays.sort(nums);
		for(int i = 0; i < nums.length - 3; i++){
			if(i > 0 && nums[i - 1] == nums[i]){   //avoid duplicated
				continue;
			}
			for(int j = i + 1; j < nums.length - 2; j++){
				if(j > i + 1 && nums[j] == nums[j - 1]){   //avoid duplicated
					continue;
				}
				int l = j + 1, r = nums.length - 1;
				while(l < r){
					int cur = nums[i] + nums[j] + nums[l] + nums[r];
					if(cur == target){
						List<Integer> t = helper(nums, i, j, l, r);
						res.add(new ArrayList<>(t));
						l++; r--;
						while(l < r && nums[l] == nums[l - 1]){
							l++;
						}//to avoid dup
						while(l < r && nums[r] == nums[r + 1]){
							r--;
						}//to avoid dup
					}else if(cur > target){
						r--;
					}else{
						l++;
					}
				}
			}
		}
		return res;
	}
	private List<Integer> helper(int[] nums, int i, int j, int k, int l){
		List<Integer> t = new ArrayList<>();
		t.add(nums[i]); t.add(nums[j]); t.add(nums[k]); t.add(nums[l]);
		return t;
	}

}

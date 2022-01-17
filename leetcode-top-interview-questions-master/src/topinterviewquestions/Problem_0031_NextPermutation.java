package topinterviewquestions;

public class Problem_0031_NextPermutation {
    //首先找到从右往左第一个值变小的index firstReducingR, 如果没找到即firstReducingR 依旧等于初始值-1则把他们全部reverse，
    // 找到了的话，找到从右往左第一个比nums[firstReducingR]大的数nums[closestLargerR]，然后先把他们俩调换swap，再把【firstReducingR+1, N-1】区间的reverse
    //1433 -> firstReducingR = 0, closestLargerR = 3, so 1433 -> 3431 -> 3134
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int N = nums.length;
        int firstReducingR = -1;
        for (int i = N - 2; i >= 0; i--) {
            if (nums[i] < nums[i+1]) {
                firstReducingR = i;
                break;
            }
        }
        if (firstReducingR < 0) {
            reverse(nums, 0, N-1);
        } else {
            int closestLargerR = -1;
            for (int i = N - 1; i > firstReducingR; i--) {
                if (nums[i] > nums[firstReducingR]) {
                    closestLargerR = i;
                    break;
                }
            }
            swap(nums, firstReducingR, closestLargerR);
            reverse(nums, firstReducingR+1, N-1);
        }

    }
    public void reverse(int[] nums, int l, int r) {
        while (l < r) {
            swap(nums, l++, r--);
        }
    }
    public void swap(int[] nums, int l, int r) {
        int temp = nums[l];
        nums[l] = nums[r];
        nums[r] = temp;
    }
}

package topinterviewquestions;

public class Problem_0905_SortArrayByParity_G {
    //https://leetcode-cn.com/problems/sort-array-by-parity/solution/an-qi-ou-pai-xu-shu-zu-by-leetcode-solut-gpmm/
    //方法三：原地交换
    //思路
    //
    //记数组 \textit{nums}nums 的长度为 nn。先从 \textit{nums}nums 左侧开始遍历，如果遇到的是偶数，就表示这个元素已经排好序了，继续从左往右遍历，直到遇到一个奇数。然后从 \textit{nums}nums 右侧开始遍历，如果遇到的是奇数，就表示这个元素已经排好序了，继续从右往左遍历，直到遇到一个偶数。交换这个奇数和偶数的位置，并且重复两边的遍历，直到在中间相遇，\textit{nums}nums 排序完毕。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/sort-array-by-parity/solution/an-qi-ou-pai-xu-shu-zu-by-leetcode-solut-gpmm/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public int[] sortArrayByParity(int[] nums) {
            int left = 0, right = nums.length - 1;
            while (left < right) {
                while (left < right && nums[left] % 2 == 0) {
                    left++;
                }
                while (left < right && nums[right] % 2 == 1) {
                    right--;
                }
                if (left < right) {
                    int temp = nums[left];
                    nums[left] = nums[right];
                    nums[right] = temp;
                    left++;
                    right--;
                }
            }
            return nums;
        }
    }

}

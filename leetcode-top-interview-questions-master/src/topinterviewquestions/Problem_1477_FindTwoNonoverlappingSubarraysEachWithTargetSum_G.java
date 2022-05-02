package topinterviewquestions;

import java.util.ArrayList;
import java.util.Collections;

public class Problem_1477_FindTwoNonoverlappingSubarraysEachWithTargetSum_G {
    //使用滑动窗口找出所有和为 target 的子数组
    //使用 multimap 按照长度保存
    //使用双循环依次遍历两个不同的子数组
    //31. 如果有交叉跳过，注意是按长度排的，要在前在后都判断
    //32. 记录最小值
    //33. 剪枝
    //
    //作者：ikaruga
    //链接：https://leetcode-cn.com/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/solution/find-two-by-ikaruga/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    //https://leetcode-cn.com/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/solution/find-two-by-ikaruga/
    public int minSumOfLengths(int[] arr, int target) {

        int right = 0;
        int left = 0;
        int sum = 0;
        ArrayList<int[]> list = new ArrayList<>();

        // 求出所有符合要求的子数组
        while (right < arr.length) {
            sum += arr[right];
            right++;
            if (sum < target) {
                continue;
            }
            while (sum > target) {
                sum -= arr[left];
                left++;
            }
            if (sum == target) {
                list.add(new int[] { right - left, left });
            }
        }

        //结果按长度升序排序
        Collections.sort(list, (o1, o2) -> o1[0] - o2[0]);

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {

            //ans是两个长度之和，如果遍历到有超过这个长度的，后面的不是最小值无需遍历。
            int[] a = list.get(i);
            if (a[0] * 2 >= ans) {
                break;
            }
            for (int j = i + 1; j < list.size(); j++) {
                int arr1[] = list.get(i);
                int arr2[] = list.get(j);
                if (arr1[1] < arr2[1] && arr1[0] + arr1[1] > arr2[1]) {
                    continue;
                }
                if (arr2[1] < arr1[1] && arr2[0] + arr2[1] > arr1[1]) {
                    continue;
                }
                ans = Math.min(ans, arr1[0] + arr2[0]);

                //长度经过排序之后，后面的长度比当前大不满足最小，所以舍去不要进行遍历
                break;
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}

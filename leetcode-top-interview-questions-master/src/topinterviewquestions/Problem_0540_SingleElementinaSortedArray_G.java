package topinterviewquestions;

public class Problem_0540_SingleElementinaSortedArray_G {
    public static int singleNonDuplicate(int[] nums) {
        int start = 0, end = nums.length - 1;

        while (start < end) {
            // We want the first element of the middle pair,
            // which should be at an even index if the left part is sorted.
            // Example:
            // Index: 0 1 2 3 4 5 6
            // Array: 1 1 3 3 4 8 8
            //            ^
            int mid = (start + end) / 2;
            if (mid % 2 == 1) mid--;

            // We didn't find a pair. The single element must be on the left.
            // (pipes mean start & end)
            // Example: |0 1 1 3 3 6 6|
            //               ^ ^
            // Next:    |0 1 1|3 3 6 6
            if (nums[mid] != nums[mid + 1]) {
                end = mid;
            } else {
                // We found a pair. The single element must be on the right.
                // Example: |1 1 3 3 5 6 6|
                //               ^ ^
                // Next:     1 1 3 3|5 6 6|
                start = mid + 2;
            }
        }

        // 'start' should always be at the beginning of a pair.
        // When 'start > end', start must be the single element.
        return nums[start];
    }


    //int mid = (start + end)/2; is not good, better should be int mid = start + (end - start)/2; to avoid Integer overflow for start + end
    //This solution combines some cases which makes it unclear for ppl to understand, clear code but kind of more lines should be:
    //https://leetcode.com/problems/single-element-in-a-sorted-array/discuss/100754/Java-Binary-Search-short-(7l)-O(log(n))-w-explanations/235525
    class Solution2 {
        public int singleNonDuplicate(int[] nums) {
            int lo = 0;
            int hi = nums.length - 1;
            while(lo < hi){
                int mid = lo + (hi - lo)/2;

                if(mid % 2 == 0){
                    // if mid in at even index and assume the single element is not in the left part(nums[0 : mid + 1] inclusively)
                    // the number in mid and the next number should be the same; otherwise, it is in the left part: nums[0 : mid] inclusively
                    if(nums[mid] == nums[mid + 1]){
                        lo = mid + 2;
                    }else{
                        hi = mid;
                    }
                }else{
                    // similar logic as above
                    if(nums[mid] == nums[mid - 1]){
                        lo = mid + 1;
                    }else{
                        hi = mid;
                    }
                }

            }
            return nums[lo];
        }
    }
}

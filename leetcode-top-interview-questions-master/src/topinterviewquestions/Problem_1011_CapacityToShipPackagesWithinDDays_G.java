package topinterviewquestions;

//We binary search the final result.
//The left bound is max(A),
//The right bound is sum(A).
public class Problem_1011_CapacityToShipPackagesWithinDDays_G {
    public int shipWithinDays(int[] weights, int days) {
        int left = 0, right = 0;
        for (int w: weights) {
            left = Math.max(left, w);
            right += w;
        }
        while (left < right) {
            int mid = (left + right) / 2, need = 1, cur = 0;
            for (int w: weights) {
                if (cur + w > mid) {
                    need += 1;
                    cur = 0;
                }
                cur += w;
            }
            if (need > days) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}

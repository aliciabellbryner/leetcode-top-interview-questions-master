package topinterviewquestions;

public class Problem_1007_MinimumDominoRotationsForEqualRow_G {

    //算法
    //
    //选择第一块多米诺骨牌，它包含两个数字 A[0] 和 B[0]；
    //
    //检查其余的多米诺骨牌中是否出现过 A[0]。如果都出现过，则求出最少的翻转次数，其为将 A[0] 全部翻到 A 和全部翻到 B 中的较少的次数。
    //
    //检查其余的多米诺骨牌中是否出现过 B[0]。如果都出现过，则求出最少的翻转次数，其为将 B[0] 全部翻到 A 和全部翻到 B 中的较少的次数。
    //
    //如果上述两次检查都失败，则返回 -1。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/minimum-domino-rotations-for-equal-row/solution/lian-xu-chai-xiang-tong-de-shu-zi-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        /*
        Return min number of rotations
        if one could make all elements in A or B equal to x.
        Else return -1.
        */
        public int check(int x, int[] A, int[] B, int n) {
            // how many rotations should be done
            // to have all elements in A equal to x
            // and to have all elements in B equal to x
            int rotations_a = 0, rotations_b = 0;
            for (int i = 0; i < n; i++) {
                // rotations coudn't be done
                if (A[i] != x && B[i] != x) return -1;
                    // A[i] != x and B[i] == x
                else if (A[i] != x) rotations_a++;
                    // A[i] == x and B[i] != x
                else if (B[i] != x) rotations_b++;
            }
            // min number of rotations to have all
            // elements equal to x in A or B
            return Math.min(rotations_a, rotations_b);
        }

        public int minDominoRotations(int[] A, int[] B) {
            int n = A.length;
            int rotations = check(A[0], B, A, n);
            // If one could make all elements in A or B equal to A[0]
            if (rotations != -1 || A[0] == B[0]) return rotations;
                // If one could make all elements in A or B equal to B[0]
            else return check(B[0], B, A, n);
        }
    }

}

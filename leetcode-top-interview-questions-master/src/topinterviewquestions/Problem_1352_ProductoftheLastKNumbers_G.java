package topinterviewquestions;

import java.util.ArrayList;

public class Problem_1352_ProductoftheLastKNumbers_G {

    //https://leetcode.com/problems/product-of-the-last-k-numbers/discuss/510260/JavaC%2B%2BPython-Prefix-Product
    //Intuition
    //Similar to prefix sum. We can record the prefix product.
    //
    //
    //Explanation
    //If we meet 0, the product including this 0 will always be 0.
    //We only need to record the prefix product after it.
    //So I clear the A and reinitilise it as [1],
    //where 1 is the neutral element of multiplication.
    //
    //
    //Complexity
    //Time O(1) each
    //Space O(N)
    class Solution {


        ArrayList<Integer> A = new ArrayList() {{
            add(1);
        }};

        public void add(int a) {
            if (a > 0)
                A.add(A.get(A.size() - 1) * a);
            else {
                A = new ArrayList();
                A.add(1);
            }
        }

        public int getProduct(int k) {
            int n = A.size();
            return k < n ? A.get(n - 1) / A.get(n - k - 1) : 0;
        }
    }
}

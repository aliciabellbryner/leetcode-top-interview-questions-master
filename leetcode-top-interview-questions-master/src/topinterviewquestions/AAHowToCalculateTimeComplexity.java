package topinterviewquestions;

public class AAHowToCalculateTimeComplexity {
    /**
     * 形如 T(N) = a * T(n/b) + O(N^d) (a, b, d都是常数）的递归函数，可以直接通过Master公式来确定时间复杂度
     * a: 有多少个套用的recursion方程
     * b: 把原先的数据分成了几部分
     * c: 除了recursion部分之外的复杂度
     * 1. 如果log(b, a) < d, 则复杂度为O(N^d)
     * 2. 如果log(b, a) > d, 则复杂度为O(N^log(b,a))
     * 3. 如果log(b, a) == d, 则复杂度为O(N^d*logN)
     */

    //例子：
    //3和4一共有两个recursion，所以a=2
    //3和4把总的数据量N分成两部分，所以b=2，
        // 如果分成两个2/3，
            // int leftMax = process(arr, 0, (2/3)* arr.length - 1);//3
            // int rightMax = process(arr, (1/3)* arr.length + 1, arr.length - 1);//4
        //那么T(N) = 2 * T(2n/3) + O(N^0), 所以a=2, b = 3/2 = 1.5（注意不是2/3）
    //除了recursion，1，2和5都和N无关，所以时间复杂度都是O（1）= O(N^0)，所以d=0
    //所以a=2, b=2, d=0, 所以logb(a) = log2(2) = 1 > (d=0),所以时间复杂度是(N^log(b,a))=O(N)
    public int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }
    public int process(int[] arr, int i, int j) {
        if (i == j) {//1
            return arr[i];
        }
        int mid = i + ((j - i) >> 1);//2
        int leftMax = process(arr, 0, mid);//3
        int rightMax = process(arr, mid + 1, arr.length - 1);//4
        return Math.max(leftMax, rightMax);//5
    }
}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AAAAAA {
    static String findString(int n, int k)
    {

        // Initialize result with first k
        // Latin letters
        String res = "";

        for (int i = 0; i < k; i++)
            res = res + (char)('a' + i);

        // Fill remaining n-k letters by
        // repeating k letters again and
        // again.
        int count = 0;

        for (int i = 0; i < n - k; i++)
        {
            res = res + (char)('a' + count);
            count++;

            if (count == k)
                count = 0;
        }

        return res;
    }

    public static String task1 (int N, int K) {
        char[] ca = new char[N];
        for (int i = 0; i < K; i++) {
            ca[i] = (char) ('a' + i);
            ca[N - 1 - i] = (char) ('a' + i);
        }
        if (K < (N+1)/2) {
            for (int i = K; i < N - K; i++) {
                ca[i] = 'a';
                ca[N - 1 - i] = 'a';
            }
        }
        return Arrays.toString(ca);
    }


    static int printAllSubsetsRec(int arr[],
                                  int n,
                                  ArrayList<Integer> v,
                                  int sum)
    {

        // Condition if the
        // sequence is found
        if (sum == 0)
        {
            return (int)v.size();
        }

        if (sum < 0)
            return Integer.MAX_VALUE;

        // Condition when no
        // such sequence found
        if (n == 0)
            return Integer.MAX_VALUE;

        // Calling for without choosing
        // the current index value
        int x = printAllSubsetsRec(
                arr,
                n - 1, v, sum);

        // Calling for after choosing
        // the current index value
        v.add(arr[n - 1]);

        int y = printAllSubsetsRec(
                arr, n, v,
                sum - arr[n - 1]);
        v.remove(v.size() - 1);

        return Math.min(x, y);
    }

    // Function for every array
    static int printAllSubsets(int arr[],
                               int n, int sum)
    {
        ArrayList<Integer> v = new ArrayList<>();
        return printAllSubsetsRec(arr, n,
                v, sum);
    }



    public int solution(int N, int K){
        if(N >= K) return 1;
        int sum = N*(N+1)/2;
        if(sum < K){
            return -1;
        }else if(sum == K){
            return N;
        }else{
            int max = K;
            Set<Integer> set = new HashSet<>();
            while(max > 0){
                int start = Math.min(N, max);
                for (int i = start; i > 0; i--) {
                    if(!set.contains(i)){
                        set.add(i);
                        max -= i;
                        break;
                    }
                }
            }
            return set.size();
        }
    }



    static int findMax(int N, int []arr, int L, int R)
    {

        // Using a set to store the elements
        HashSet<Integer> s = new HashSet<Integer>();

        // ArrayList to store the possible answers
        ArrayList<Integer> possible
                = new ArrayList<Integer>();

        // Store the answer
        int ans = 0;

        // Traverse the array
        for (int i = 0; i < N; i++) {

            // If set has it's negation,
            // check if it is max
            if (s.contains(arr[i] * -1))
                possible.add(Math.abs(arr[i]));
            else
                s.add(arr[i]);
        }

        // Find the maximum possible answer
        for (int i = 0; i < possible.size(); i++) {
            if (possible.get(i) >= L && possible.get(i) <= R) {
                ans = Math.max(ans, possible.get(i));
            }
        }

        return ans;
    }




    static public void main (String[] args)
    {

        int n = 8, k = 3;

        System.out.println(task1(n, k));
    }
}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Given two sparse vectors, compute their dot product.

Implement class SparseVector:

SparseVector(nums) Initializes the object with the vector nums
dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute the dot product between two SparseVector.

Follow up: What if only one of the vectors is sparse?



Example 1:

Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
Output: 8
Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8
Example 2:

Input: nums1 = [0,1,0,0,0], nums2 = [0,0,0,0,2]
Output: 0
Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
v1.dotProduct(v2) = 0*0 + 1*0 + 0*0 + 0*0 + 0*2 = 0
Example 3:

Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
Output: 6


Constraints:

n == nums1.length == nums2.length
1 <= n <= 10^5
0 <= nums1[i], nums2[i] <= 100
 */
public class Problem_1570_DotProductofTwoSparseVectors_G {
    /*
    A sparse vector is a vector that has mostly zero values, while a dense vector is a vector where most of the elements are non-zero. It is inefficient to store a sparse vector as a one-dimensional array (Approach 1). Instead, we can store the non-zero values and their corresponding indices in a dictionary, with the index being the key (Approach 2). Alternatively, we can represent elements of a sparse vector as an array of pairs for each non-zero value. Each pair has an integer index and a numerical value (Approach 3).

A related question is LeetCode 311. Sparse Matrix Multiplication.

Approach 1: Non-efficient Array Approach
Intuition

We ignore the sparsity of the array and store the original array.

Algorithm


Complexity Analysis

Let nn be the length of the input array.

Time complexity: O(n)O(n) for both constructing the sparse vector and calculating the dot product.

Space complexity: O(1)O(1) for constructing the sparse vector as we simply save a reference to the input array and O(1)O(1) for calculating the dot product.

Approach 2: Hash Set
Intuition

Store the non-zero values and their corresponding indices in a dictionary, with the index being the key. Any index that is not present corresponds to a value 0 in the input array.

Algorithm


Complexity Analysis

Let nn be the length of the input array and LL be the number of non-zero elements.

Time complexity: O(n)O(n) for creating the Hash Map; O(L)O(L) for calculating the dot product.

Space complexity: O(L)O(L) for creating the Hash Map, as we only store elements that are non-zero. O(1)O(1) for calculating the dot product.

Approach 3: Index-Value Pairs
Intuition

We can also represent elements of a sparse vector as a list of <index, value> pairs. We use two pointers to iterate through the two vectors to calculate the dot product.

Algorithm


Complexity Analysis

Let nn be the length of the input array and LL and L_{2}L
2
​
  be the number of non-zero elements for the two vectors.

Time complexity: O(n)O(n) for creating the <index, value> pair for non-zero values; O(L+L_{2})O(L+L
2
​
 ) for calculating the dot product.

Space complexity: O(L)O(L) for creating the <index, value> pairs for non-zero values. O(1)O(1) for calculating the dot product.
     */

    class SparseVector1 {

        private int[] array;

        SparseVector1(int[] nums) {
            array = nums;
        }

        public int dotProduct(SparseVector1 vec) {
            int result = 0;

            for (int i = 0; i < array.length; i++) {
                result += array[i] * vec.array[i];
            }
            return result;
        }
    }


    class SparseVector2 {
        // Map the index to value for all non-zero values in the vector
        Map<Integer, Integer> mapping;

        SparseVector2(int[] nums) {
            mapping = new HashMap<>();
            for (int i = 0; i < nums.length; ++i) {
                if (nums[i] != 0) {
                    mapping.put(i, nums[i]);
                }
            }
        }

        public int dotProduct(SparseVector2 vec) {
            int result = 0;

            // iterate through each non-zero element in this sparse vector
            // update the dot product if the corresponding index has a non-zero value in the other vector
            for (Integer i : this.mapping.keySet()) {
                if (vec.mapping.containsKey(i)) {
                    result += this.mapping.get(i) * vec.mapping.get(i);
                }
            }
            return result;
        }
    }


    //Java implementation with comments, beats 100%:
    class SparseVector3 {

        List<int[]> pairs;

        SparseVector3(int[] nums) {
            pairs = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    pairs.add(new int[]{i, nums[i]});
                }
            }
        }

        // Return the dotProduct of two sparse vectors
        public int dotProduct(SparseVector3 vec) {
            int result = 0, p = 0, q = 0;
            while (p < pairs.size() && q < vec.pairs.size()) {
                if (pairs.get(p)[0] == vec.pairs.get(q)[0]) {
                    result += pairs.get(p)[1] * vec.pairs.get(q)[1];
                    p++;
                    q++;
                }
                else if (pairs.get(p)[0] > vec.pairs.get(q)[0]) {
                    q++;
                }
                else {
                    p++;
                }
            }
            return result;
        }
    }

    //diss
    class SparseVector {
        Map<Integer, Integer> map;      // For all non-zero values in the vector, we map the index to the non-zero value.

        // O(nums.length) time.
// O(numNonZeroValues) space.
        SparseVector(int[] nums) {
            map = new HashMap<>();
            // Store the position and value of non-zero values.
            for (int i = 0; i < nums.length; ++i) {
                if (nums[i] != 0) {
                    map.put(i, nums[i]);
                }
            }
        }


        // O(min(vec1numNonZeroValues, vec2numNonZeroValues)) time because we iterate through non-zero values of the vector that has fewer non-zero values and for each value we check in O(1) time whether the other vector has a non-zero value at that index.
// O(1) space.
        public int dotProduct(SparseVector vec) {
            if (vec.map.size() < this.map.size()) return vec.dotProduct(this);      // We want to iterate through the smaller map.

            int result = 0;
            for (Integer currIdx : this.map.keySet()) {
                // If both vectors have a non-zero value at currIdx then multiply the values and add them to the result.
                if (vec.map.containsKey(currIdx)) {
                    result += this.map.get(currIdx) * vec.map.get(currIdx);
                }
            }
            return result;
        }
    }


}

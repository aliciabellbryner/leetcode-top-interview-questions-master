package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/*
iven two sparse matrices mat1 of size m x k and mat2 of size k x n, return the result of mat1 x mat2. You may assume that multiplication is always possible.



Example 1:


Input: mat1 = [[1,0,0],[-1,0,3]], mat2 = [[7,0,0],[0,0,0],[0,0,1]]
Output: [[7,0,0],[-7,0,3]]
Example 2:

Input: mat1 = [[0]], mat2 = [[0]]
Output: [[0]]


Constraints:

m == mat1.length
k == mat1[i].length == mat2.length
n == mat2[i].length
1 <= m, n, k <= 100
-100 <= mat1[i][j], mat2[i][j] <= 100
 */
public class Problem_0311_SparseMatrixMultiplication {
    /*
Overview
A matrix is a 2-dimensional array, and its size is denoted as a \times ba×b, where aa and bb are the numbers of rows and columns respectively.
We have to multiply two matrices AA and BB of size m \times km×k and k \times nk×n respectively.

Matrix multiplication is a binary operation whose output is another matrix when two matrices are multiplied. To multiply two matrices both matrices must be compatible, here compatibility means if we have two matrices AA and BB, then to calculate A \cdot BA⋅B, \text{the number of columns in A}the number of columns in A should be equal to \text{the number of rows in B}the number of rows in B.
And the resultant matrix will have a size equal to \text{(number of rows in A} \times \text{number of columns in B)}(number of rows in A×number of columns in B).

How to multiply?
Let's define, matrices A = [a_{ij}]A=[a
ij
​
 ] of size m \times km×k and B = [b_{ij}]B=[b
ij
​
 ] of size k \times nk×n.
Then, the product matrix X = A \cdot BX=A⋅B will be of size m \times nm×n.
X = [x_{ij}]X=[x
ij
​
 ] where, x_{ij} = a_{i1}b_{1j} + a_{i2}b_{2j} + ..... + a_{i(k-1)}b_{(k-1)j} + a_{ik}b_{kj}x
ij
​
 =a
i1
​
 b
1j
​
 +a
i2
​
 b
2j
​
 +.....+a
i(k−1)
​
 b
(k−1)j
​
 +a
ik
​
 b
kj
​
  = \sum_{N=1}^{k} a_{iN}b_{Nj}=∑
N=1
k
​
 a
iN
​
 b
Nj
​


Each element x[i][j]x[i][j] is the sum of product of elements of i^{th}i
th
  row of matrix AA and j^{th}j
th
  column of matrix BB.
Thus, to multiply two matrices AA and BB, we multiply elements of each row of the matrix AA with the respective element of each column of the matrix BB and add them.

one cell multiply

If you are not familiar with matrix multiplication, this may seem unintuitive, but it is how matrix multiplication is done. We will not dive deep into the explanation of this method and will try to focus this article on implementation only.


Approach 1: Naive Iteration
Intuition

Given two matrices, mat1 = [a_{ij}]mat1=[a
ij
​
 ] of size m \times km×k and mat2 = [b_{ij}]mat2=[b
ij
​
 ] of size k \times nk×n.
ans = mat1 \cdot mat2ans=mat1⋅mat2, a matrix of size m \times nm×n.

To find each element ans[i][j]ans[i][j] of matrix ansans, we need to multiply i^{th}i
th
  row elements of the matrix mat1mat1 with j^{th}j
th
  column elements of the matrix mat2mat2 and add them.

A simple way to implement this will be using some for loops to iterate over all rows and columns of matrices and multiply them.

We keep one pointer to point to each row of the matrix mat1mat1, another pointer to point to each column of the matrix mat2mat2, and a third pointer to point to each element in the row of the matrix mat1mat1 (there are the same number of elements (kk) in one row of mat1mat1 as there are in one column of mat2mat2).

for (row = 0 to n) {
    for (col = 0 to m) {
        for (elementPos = 0 to k) {
            ans[row][col] += mat1[row][elementPos] * mat2[elementPos][col];
        }
    }
}
Current
1 / 15

Here, we can implement one optimization. Let's say the elementPos^{th}elementPos
th
  element of i^{th}i
th
  row is 00. Then there is no need to iterate over all columns of the second matrix for this element because after multiplication, it is guaranteed that 00 will be added in the ansans matrix.

If we first check whether this element of mat1mat1 is zero or not. Then we can save one iteration over mm columns of mat2mat2. Thus, when there are many zeros in mat1mat1, this optimization will reduce the number of computations we need to do. To accomplish this, we will iterate over rowrow and elementPoselementPos in the outer for loops so that we can check if mat1[row][elementPos]mat1[row][elementPos] equals zero before iterating over all columns of mat2mat2

for (row = 0 to n) {
    for (elementPos = 0 to k) {
        // If current element of mat1 is non-zero then iterate over 'm' columns of mat2.
        if (mat1[row][elementPos] != 0)  {
            for (col = 0 to m) {
                ans[row][col] += mat1[row][elementPos] * mat2[elementPos][col];
            }
        }
    }
}
Current
1 / 12

Algorithm

Initialize some variables:

mm, number of rows in mat1mat1
kk, number of columns in mat1mat1
nn, number of columns in mat2mat2
ansans, matrix of size m \times nm×n to store multiplication result.
Iterate over each row of the matrix mat1mat1.

For each element in the current row of matrix mat1mat1, if the element is non-zero:

Iterative over each column of mat2mat2 multiply the elements and add them in the ansans matrix at their respective place.
Return the ansans matrix.

Implementation


Complexity Analysis

Let mm and kk represent the number of rows and columns in mat1mat1, respectively. Likewise, let kk and nn represent the number of rows and columns in mat2mat2, respectively.

Time complexity: O(m \cdot k \cdot n)O(m⋅k⋅n).

We iterate over all m \cdot km⋅k elements of the matrix mat1mat1.
For each element of matrix mat1mat1, we iterate over all nn columns of the matrix mat2mat2.
Thus, it leads to a time complexity of m \cdot k \cdot nm⋅k⋅n.
Space complexity: O(1)O(1).

We use a matrix ansans of size m \times nm×n to output the multiplication result which is not included in auxiliary space.


Approach 2: List of Lists
Intuition

In the previous approach, we were checking for the non-zero elements in the matrix mat1mat1, but what if the matrix mat2mat2 is sparse and mat1mat1 is dense?
We could handle that condition by counting non-zero elements in both matrices and using some checks before multiplying. However, the purpose of this problem is more than just converting a mathematical formula to a code.

The interviewer's follow-up could be, what if the matrix is too big to store in the memory, but there are only a few non-zero elements. Here, he wants to see how we handle huge space waste. He expects us to store the matrix efficiency and do multiplication using that.

Naturally, some of you may wonder how we are passing mat1mat1 and mat2mat2 to the function if the matrices can't be stored in memory. For this approach, let's assume that we will read those matrices from an external source and then store them in an efficient way. But for convenience, right now, we will read them from function arguments because our main focus will be on efficient storage, not on how to read from a file.

Thus, we have to use some data structure to only store the non-zero elements of both matrices.

We will create some buckets where each bucket denotes one rowrow and that bucket contains an array of pairs of (value, \space column)(value, column). Zero valued elements will be missing from our data structure. Since the matrices are sparse, we will only store a few elements in our data structure.

data str

In the slideshow, in the previous approach, we saw that any element with index (row1, \space col1)(row1, col1) of mat1mat1 is multiplied with all the elements of col1^{th}col1
th
  row of mat2mat2. Thus, we can use this method to multiply only the non-zero elements of mat1mat1 with the non-zero elements of a particular row of mat2mat2.

Current
1 / 7

Algorithm

Create a function compressMatrix(matrix)compressMatrix(matrix), which inputs matrixmatrix and returns compressedMatrixcompressedMatrix with only non-zero elements. To build compressedMatrixcompressedMatrix we iterate over each element of matrixmatrix and if the element is non-zero push the (value, \space col)(value, col) pair in the respective rowrow of compressedMatrixcompressedMatrix.

Initialize some variables:

mm, number of rows in mat1mat1.
kk, number of columns in mat1mat1.
nn, number of columns in mat2mat2.
AA and BB, data structure to store matrices mat1mat1 and mat2mat2 in compressed form.
ansans, matrix of size m \times nm×n to store multiplication result.
For each row in AA, iterate over all its elements. These represent the non-zero elements from mat1mat1.

For each element, we get (value, \space col)(value, col) pair and iterate over all the elements of col^{th}col
th
  row in BB. For each pair of elements, we add their product to the ansans matrix.
Return the ansans matrix.

Implementation


Complexity Analysis

Let mm and kk represent the number of rows and columns in mat1mat1, respectively. Likewise, let kk and nn represent the number of rows and columns in mat2mat2, respectively.

Time complexity: O(m \cdot k \cdot n)O(m⋅k⋅n).

We iterate over all non-zero elements of the matrix mat1mat1. And for each non-zero element, we iterate over one row of the matrix mat2mat2.
In the worst-case, mat1mat1 can have m \cdot km⋅k elements and mat2mat2 can have nn elements in each row.
Thus, it leads to the time complexity of m \cdot k \cdot nm⋅k⋅n.
Space complexity: O(m \cdot k + k \cdot n)O(m⋅k+k⋅n).

We use a data structure (an array of arrays) to efficiently store elements of both matrices.
In the worst-case, we will store all m \cdot km⋅k elements of mat1mat1 and k \cdot nk⋅n elements of mat2mat2 in our data structures.
We use a matrix ansans of size m \times nm×n to output the multiplication result which is not included in auxiliary space.

Approach 3: Yale Format
Intuition

Another way to efficiently store a matrix is using Yale format.
Yale format or Compressed Sparse Row (CSR) represents a matrix using 33 (one dimensional) arrays: valuesvalues, rowIndexrowIndex, and colIndexcolIndex.

valuesvalues array contains all the non-zero elements of the matrix.
colIndexcolIndex array contains the column index of all the non-zero elements in valuesvalues array.
rowIndexrowIndex array stores the start index of each row's elements in the valuesvalues array.
Length of valuesvalues and colIndexcolIndex arrays will be equal to the number of non-zero elements in the matrix.
Length of rowIndexrowIndex array will be, \text{number of rows + 1}number of rows + 1, where rowIndex[i]^{th}rowIndex[i]
th
  to rowIndex[i+1]^{th}rowIndex[i+1]
th
  index (exclusive) will give us the index range where the i^{th}i
th
  row elements of the matrix are stored in valuesvalues and colIndexcolIndex arrays.

We can better understand this with the following slideshow.

Current
1 / 14

Thus, we are compressing our matrix row-wise, and all of the necessary information for each row can be stored in these three arrays.
So, for a n \times mn×m size matrix, memory used for storage will be O(\max(NZ, \space n+1))O(max(NZ, n+1)) where NZNZ is the number of non-zero elements and for sparse matrices NZ \ll n \cdot mNZ≪n⋅m.

Similarly, we have Compressed Sparse Column (CSC) format, here we compress the matrix column-wise.

valuesvalues array contains all the non-zero elements of the matrix.
rowIndexrowIndex array contains the row index of all the non-zero elements in valuesvalues array.
colIndexcolIndex array stores the start index of each column's elements in the valuesvalues array.
Now while multiplying two matrices we know to find one element of the product matrix we multiply one row of mat1mat1 and one column of mat2mat2.

mat1 = [a_{ij}]mat1=[a
ij
​
 ] of size m * km∗k and mat2 = [b_{ij}]mat2=[b
ij
​
 ] of size k * nk∗n.
X = mat1 \cdot mat2 = [x_{ij}]X=mat1⋅mat2=[x
ij
​
 ]
where, x_{ij} = a_{i1}b_{1j} + a_{i2}b_{2j} + ..... + a_{i(k-1)}b_{(k-1)j} + a_{ik}b_{kj} = \sum_{N=1}^{k} a_{iN}b_{Nj}x
ij
​
 =a
i1
​
 b
1j
​
 +a
i2
​
 b
2j
​
 +.....+a
i(k−1)
​
 b
(k−1)j
​
 +a
ik
​
 b
kj
​
 =∑
N=1
k
​
 a
iN
​
 b
Nj
​


Thus, we can compress matrices mat1mat1 using CSR and mat2mat2 using CSC format so that we can easily fetch any row from the compressed form of mat1mat1 and any column from the compressed form of mat2mat2.

And, while multiplying one row of mat1mat1 with one column of mat2mat2, we can only multiply and add any two elements if, \text{column index of mat1 element}column index of mat1 element is equal to \text{row index of mat2 element}row index of mat2 element.

Now, what if these indices don't match?
This can be a bit tricky to understand but we will approach this problem with a 2-pointer approach where two pointers point to both arrays and the smaller element pointer is incremented until both pointer's elements are equal. We can visualize how we find similar indices using this slideshow.

Current
1 / 9

Algorithm

Create a class SparseMatrix which stores a matrix in Yale format:

cols, \space rowscols, rows: variables to store dimensions of the original matrix.
values, \space rowIndex, \space colIndexvalues, rowIndex, colIndex: three arrays as discussed previously and represent the compressed form of a sparse matrix.
Compress the matrix into CSR or CSC format (as shown in the first slideshow in this approach) and store the compressed matrix in the valuesvalues, rowIndexrowIndex, and colIndexcolIndex arrays.
Initialize some variables:

AA, which is a SparseMatrix object that stores mat1mat1 in compressed sparse row format.
BB, which is a SparseMatrix object that stores mat2mat2 in compressed sparse column format.
ansans, which is a matrix of size m \times nm×n that stores the multiplication result.
Iterate over each element (row, \space col)(row, col) of the matrix ansans.

Get current row'srow
′
 s element's range from AA and current col'scol
′
 s element's range from BB.
Multiply and add all the same index elements from the mat1'smat1
′
 s row and mat2'smat2
′
 s column at the current position in ansans using the 2-pointer approach as discussed above.
Return the ansans matrix.

Implementation


Complexity Analysis

Let mm and kk represent the number of rows and columns in mat1mat1, respectively. Likewise, let kk and nn represent the number of rows and columns in mat2mat2, respectively.

Time complexity: O(m \cdot n \cdot k)O(m⋅n⋅k).

We iterate over all m \cdot nm⋅n elements of ansans matrix.
For each element we iterate over kk row elements in mat1mat1, and kk column elements in mat2mat2. In the worst-case scenario, none of the elements in either matrix is zero.
Thus, the time complexity is O(m \cdot n \cdot km⋅n⋅k).
Space complexity: O(m \cdot k + k \cdot n)O(m⋅k+k⋅n).

We use a data structure to efficiently store the non-zero elements of both matrices.
In the worst-case scenario we will store all m \cdot km⋅k elements of mat1mat1 and all k \cdot nk⋅n elements of mat2mat2 in one dimensional arrays.
We use a matrix ansans of size m \times nm×n to output the multiplication result which is not included in auxiliary space.
     */

    class Solution1 {
        public int[][] multiply(int[][] mat1, int[][] mat2) {
            int n = mat1.length;
            int k = mat1[0].length;
            int m = mat2[0].length;

            // Product matrix will have 'n x m' size.
            int[][] ans = new int[n][m];

            for (int rowIndex = 0; rowIndex < n; ++rowIndex) {
                for (int elementIndex = 0; elementIndex < k; ++elementIndex) {
                    // If current element of mat1 is non-zero then iterate over all columns of mat2.
                    if (mat1[rowIndex][elementIndex] != 0)  {
                        for (int colIndex = 0; colIndex < m; ++colIndex) {
                            ans[rowIndex][colIndex] += mat1[rowIndex][elementIndex] * mat2[elementIndex][colIndex];
                        }
                    }
                }
            }

            return ans;
        }
    }

    /*
    class Solution2 {
        public ArrayList<ArrayList<Pair<Integer, Integer>>> compressMatrix(int[][] matrix) {
            int rows = matrix.length;
            int cols = matrix[0].length;

            ArrayList<ArrayList<Pair<Integer, Integer>>> compressedMatrix = new ArrayList<>();

            for (int row = 0; row < rows; ++row) {
                ArrayList<Pair<Integer, Integer>> currRow = new ArrayList<>();
                for (int col = 0; col < cols; ++col) {
                    if (matrix[row][col] != 0) {
                        currRow.add(new Pair(matrix[row][col], col));
                    }
                }
                compressedMatrix.add(currRow);
            }
            return compressedMatrix;
        }

        public int[][] multiply(int[][] mat1, int[][] mat2) {
            int m = mat1.length;
            int k = mat1[0].length;
            int n = mat2[0].length;

            // Store the non-zero values of each matrix.
            ArrayList<ArrayList<Pair<Integer, Integer>>> A = compressMatrix(mat1);
            ArrayList<ArrayList<Pair<Integer, Integer>>> B = compressMatrix(mat2);

            int[][] ans = new int[m][n];

            for (int mat1Row = 0; mat1Row < m; ++mat1Row) {
                // Iterate on all current 'row' non-zero elements of mat1.
                for (Pair mat1Element : A.get(mat1Row)) {
                    int element1 = (int)mat1Element.getKey();
                    int mat1Col = (int)mat1Element.getValue();

                    // Multiply and add all non-zero elements of mat2
                    // where the row is equal to col of current element of mat1.
                    for (Pair mat2Element : B.get(mat1Col)) {
                        int element2 = (int)mat2Element.getKey();
                        int mat2Col = (int)mat2Element.getValue();
                        ans[mat1Row][mat2Col] += element1 * element2;
                    }
                }
            }

            return ans;
        }
    }


     */
    class Solution3 {
        class SparseMatrix {
            public int cols = 0, rows = 0;
            public ArrayList<Integer> values = new ArrayList<>();
            public ArrayList<Integer> colIndex = new ArrayList<>();
            public ArrayList<Integer> rowIndex = new ArrayList<>();

            // Compressed Sparse Row
            public SparseMatrix(int[][] matrix) {
                rows = matrix.length;
                cols = matrix[0].length;
                rowIndex.add(0);

                for (int row = 0; row < rows; ++row) {
                    for (int col = 0; col < cols; ++col) {
                        if (matrix[row][col] != 0) {
                            values.add(matrix[row][col]);
                            colIndex.add(col);
                        }
                    }
                    rowIndex.add(values.size());
                }
            }

            // Compressed Sparse Column
            public SparseMatrix(int[][] matrix, boolean colWise) {
                rows = matrix.length;
                cols = matrix[0].length;
                colIndex.add(0);

                for (int col = 0; col < cols; ++col) {
                    for (int row = 0; row < rows; ++row) {
                        if (matrix[row][col] != 0) {
                            values.add(matrix[row][col]);
                            rowIndex.add(row);
                        }
                    }
                    colIndex.add(values.size());
                }
            }
        };


        public int[][] multiply(int[][] mat1, int[][] mat2) {
            SparseMatrix A = new SparseMatrix(mat1);
            SparseMatrix B = new SparseMatrix(mat2, true);

            int[][] ans = new int[mat1.length][mat2[0].length];

            for (int row = 0; row < ans.length; ++row) {
                for (int col = 0; col < ans[0].length; ++col) {

                    // Row element range indices
                    int matrixOneRowStart = A.rowIndex.get(row);
                    int matrixOneRowEnd = A.rowIndex.get(row + 1);

                    // Column element range indices
                    int matrixTwoColStart = B.colIndex.get(col);
                    int matrixTwoColEnd = B.colIndex.get(col + 1);

                    // Iterate over both row and column.
                    while (matrixOneRowStart < matrixOneRowEnd && matrixTwoColStart < matrixTwoColEnd) {
                        if (A.colIndex.get(matrixOneRowStart) < B.rowIndex.get(matrixTwoColStart)) {
                            matrixOneRowStart++;
                        } else if (A.colIndex.get(matrixOneRowStart) > B.rowIndex.get(matrixTwoColStart)) {
                            matrixTwoColStart++;
                        } else {
                            // Row index and col index are same so we can multiply these elements.
                            ans[row][col] += A.values.get(matrixOneRowStart) * B.values.get(matrixTwoColStart);
                            matrixOneRowStart++;
                            matrixTwoColStart++;
                        }
                    }
                }
            }

            return ans;
        }
    }

//discussion
    //UPDATE: Thanks to @stpeterh we have this 70ms concise solution:
public class Solution4 {
    public int[][] multiply(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, nB = B[0].length;
        int[][] C = new int[m][nB];

        for(int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0) {
                    for (int j = 0; j < nB; j++) {
                        if (B[k][j] != 0) C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
        return C;
    }
}

//The followings is the original 75ms solution:
//
//The idea is derived from a CMU lecture.
//
//A sparse matrix can be represented as a sequence of rows, each of which is a sequence of (column-number, value) pairs of the nonzero values in the row.
//
//So let's create a non-zero array for A, and do multiplication on B.
//
//Hope it helps!
    public int[][] multiply(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, nB = B[0].length;
        int[][] result = new int[m][nB];

        List[] indexA = new List[m];
        for(int i = 0; i < m; i++) {
            List<Integer> numsA = new ArrayList<>();
            for(int j = 0; j < n; j++) {
                if(A[i][j] != 0){
                    numsA.add(j);
                    numsA.add(A[i][j]);
                }
            }
            indexA[i] = numsA;
        }

        for(int i = 0; i < m; i++) {
            List<Integer> numsA = indexA[i];
            for(int p = 0; p < numsA.size() - 1; p += 2) {
                int colA = numsA.get(p);
                int valA = numsA.get(p + 1);
                for(int j = 0; j < nB; j ++) {
                    int valB = B[colA][j];
                    result[i][j] += valA * valB;
                }
            }
        }

        return result;
    }

}

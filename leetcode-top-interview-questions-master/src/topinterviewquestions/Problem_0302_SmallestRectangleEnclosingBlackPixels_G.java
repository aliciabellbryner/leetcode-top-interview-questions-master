package topinterviewquestions;

/*
 302	Smallest Rectangle Enclosing Black Pixels

 An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel.
 The black pixels are connected, i.e., there is only one black region. 只有一个黑区
 Pixels are connected horizontally and vertically.

 Given the location (x, y) of one of the black pixels,
 return the area of the smallest (axis-aligned) rectangle that encloses all black pixels.

 For example, given the following image:

 [
 "0010",
 "0110",
 "0100"
 ]
 and x = 0, y = 2

 Return 6.
 */

public class Problem_0302_SmallestRectangleEnclosingBlackPixels_G {

        public class Solution_BinarySearch {
            /**
             * @param image: a binary matrix with '0' and '1'
             * @param x: the location of one of the black pixels
             * @param y: the location of one of the black pixels
             * @return: an integer
             */
            public int minArea(char[][] image, int x, int y) {
                if (image == null || image.length == 0) {
                    return 0;
                }

                int m = image.length;
                int n = image[0].length;

                int up = binarySearch(image, true, 0, x, 0, n, true);
                int down = binarySearch(image, true, x + 1, m, 0, n, false);
                int left = binarySearch(image, false, 0, y, up, down, true); // @note: re-use up/down to narrow down
                int right = binarySearch(image, false, y + 1, n, up, down, false);

                return (right - left) * (down - up);
            }

            int binarySearch(char[][] image, boolean isHorizontal, int i, int j, int low, int high, boolean opt) {

                while (i < j) {
                    int k = low;
                    int mid = (i + j) / 2;

                    while (k < high && (isHorizontal ? image[mid][k] : image[k][mid]) == '0') {
                        ++k;
                    }

                    if (k < high == opt) {
                        j = mid;
                    } else {
                        i = mid + 1;
                    }
                }

                return i;
            }

        }

    public static void main(String[] args) {
        System.out.println("Hello");
    }
}

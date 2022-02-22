package topinterviewquestions;

public class Problem_0157_ReadNCharactersGivenread4 {
   /*
   Overview
Interview Tendencies: Google and Facebook

A long time ago, long ago, so long ago that no one can remember, algorithm interview questions were less popular. Ten years ago big companies mainly filtered the candidates by the university ranks, and the interview questions were like please describe how DDR memory works.

Nowadays there are some tendencies to merge this "old-style interview" with the modern algorithm questions interview. The idea is to ask a question which sounds like algorithmic but checks your knowledge of how do computers work: Round-robin CPU scheduling, C10k problem first solved by nginx, etc.

Is it good or bad? That's a reality to deal with, especially if we speak about Google or Facebook interviews.

Read N Characters Given Read4

Back to the problem, the question is "how does the memory work":

Because of the physical implementation, loading 4 bytes in DDR is faster than to load 1 byte 4 times.

On the majority of computers today, collection of 4 bytes, or 32 bits, is called a word. Most modern CPUs are optimized for the operations with words.

To sum up, the problem is a practical low-level question. The standard approach (Approach 1) to solve it using the internal buffer of 4 characters:

File -> Internal Buffer of 4 Characters -> Buffer of N Characters.

img Figure 1. Approach 1: solution with internal buffer.

Once it's done, and you show your understanding of memory operations, the follow-up question is how to speed up. The answer (Approach 2) is quite straightforward. If it's possible, do not use the internal buffer of 4 characters to avoid the double copy:

File -> Buffer of N Characters.

img Figure 2. Approach 2: solution without internal buffer.



Approach 1: Use Internal Buffer of 4 Characters
img Figure 3. Solution with internal buffer.

Algorithm

Let's use an internal buffer of 4 characters to solve this problem:

File -> Internal Buffer of 4 Characters -> Buffer of N Characters.

Initialize the number of copied characters copiedChars = 0, and the number of read characters: readChars = 4. It's convenient initialize readChars to 4 and then use readChars != 4 as EOF marker.

Initialize an internal buffer of 4 characters: buf4.

While number of copied characters is less than N: copiedChars < n and there are still characters in the file: readChars == 4:

Read from file into internal buffer: readChars = read4(buf4).

Copy the characters from internal buffer buf4 into main buffer buf one by one. Increase copiedChars after each character. In the number of copied characters is equal to N: copiedChars == n, interrupt the copy process and return copiedChars.

Implementation
public class Solution extends Reader4 {
    public int read(char[] buf, int n) {
        int copiedChars = 0, readChars = 4;
        char[] buf4 = new char[4];

        while (copiedChars < n && readChars == 4) {
            readChars = read4(buf4);

            for (int i = 0; i < readChars; ++i) {
                if (copiedChars == n)
                    return copiedChars;
                buf[copiedChars] = buf4[i];
                ++copiedChars;
            }
        }
        return copiedChars;
    }
}

Complexity Analysis

Time complexity: O(N)O(N) to copy N characters.

Space complexity: O(1)O(1) to keep buf4 of 4 elements.



Approach 2: Speed Up: No Internal Buffer
img Figure 4. Solution without internal buffer.

This solution is mainly suitable for the languages (C, C++, Golang) where pointers allow to append directly to the primary buffer buf.

Algorithm

Initialize:
The number of copied characters copiedChars as 0.
The number of read characters readChars as 4.
The number of remaining characters that we want to read remainingChars as n.
While number of remaining characters that we want to read is greater than or equal to 4 and the number of read characters equals 4:
Read from the file directly into buffer (read4(buf + copiedChars)).
Increase the count of copiedChars by the number of characters read.
We break from the while loop either when we run out of characters to read (readChars != 4) or when there are less than 4 characters left that we want to read. Why break when remainingChars is less than 4? Because if we only want to read say 2 more characters, and there are 3 or more characters remaining in the given file, then we will end up writing more than n characters to buf.
So when there are less than 4 characters that we want to read, we will use an internal buffer only once and read exactly remainingChars from that buffer into buf.
Finally, buf will either contain exactly n characters, or there were less than n characters in the file and we read all of the characters. So we will return the minimum value of n and copiedChars since this represents exactly how many characters were stored in buf.
Implementation
class Solution {
public:
    int read(char *buf, int n) {
        int copiedChars = 0;
        int readChars = 4;
        int remainingChars = n;

        // While there are at least 4 characters remaining to be read and the last
        // call to readChars returned 4 characters, write directly to buf.
        while (remainingChars >= 4 && readChars == 4) {
            readChars = read4(buf + copiedChars);
            copiedChars += readChars;
        }

        // If there are between 1 and 3 characters that we still want to read and
        // readChars was not 0 last time we called read4, then create a buffer
        // for just this one call so we can ensure buf does not overflow.
        if (remainingChars && readChars) {
            char buf4[4];
            readChars = read4(buf4);
            for (int i = 0; i < min(remainingChars, readChars); i++) {
                buf[copiedChars++] = buf4[i];
            }
        }

        return min(n, copiedChars);
    }
};

Complexity Analysis

Time complexity: O(N)O(N) to copy N characters.

Space complexity: O(1)O(1).
    */

    //from discussion
    /*
    public int read(char[] buf, int n) {
        boolean eof = false;      // end of file flag
        int total = 0;            // total bytes have read
        char[] tmp = new char[4]; // temp buffer

        while (!eof && total < n) {
            int count = read4(tmp);

            // check if it's the end of the file
            eof = count < 4;

            // get the actual count
            count = Math.min(count, n - total);

            // copy from temp buffer to buf
            for (int i = 0; i < count; i++)
                buf[total++] = tmp[i];
        }

        return total;
    }
     */
}

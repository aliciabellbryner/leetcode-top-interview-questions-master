package topinterviewquestions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/*
Design a phone directory that initially has maxNumbers empty slots that can store numbers. The directory should store numbers, check if a certain slot is empty or not, and empty a given slot.

Implement the PhoneDirectory class:

PhoneDirectory(int maxNumbers) Initializes the phone directory with the number of available slots maxNumbers.
int get() Provides a number that is not assigned to anyone. Returns -1 if no number is available.
bool check(int number) Returns true if the slot number is available and false otherwise.
void release(int number) Recycles or releases the slot number.


Example 1:

Input
["PhoneDirectory", "get", "get", "check", "get", "check", "release", "check"]
[[3], [], [], [2], [], [2], [2], [2]]
Output
[null, 0, 1, true, 2, false, null, true]

Explanation
PhoneDirectory phoneDirectory = new PhoneDirectory(3);
phoneDirectory.get();      // It can return any available phone number. Here we assume it returns 0.
phoneDirectory.get();      // Assume it returns 1.
phoneDirectory.check(2);   // The number 2 is available, so return true.
phoneDirectory.get();      // It returns 2, the only number that is left.
phoneDirectory.check(2);   // The number 2 is no longer available, so return false.
phoneDirectory.release(2); // Release number 2 back to the pool.
phoneDirectory.check(2);   // Number 2 is available again, return true.


Constraints:

1 <= maxNumbers <= 104
0 <= number < maxNumbers
At most 2 * 104 calls will be made to get, check, and release.
 */
public class Problem_0379_DesignPhoneDirectory {
    //diss
    //use an array next[] to store the next available number.
    //when a number k is issued, move pointer pos = next[k] to the next available position. set next[k]=-1 and
    //when a number is recycled, sipmly move pointer from pos to the recycled number, and change the recycled number's "next" point to pos.
    class PhoneDirectory {

        /** Initialize your data structure here
         @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
        int[] next;
        int pos;
        public PhoneDirectory(int maxNumbers) {
            next = new int[maxNumbers];
            for (int i=0; i<maxNumbers; ++i){
                next[i] = (i+1)%maxNumbers;
            }
            pos=0;
        }

        /** Provide a number which is not assigned to anyone.
         @return - Return an available number. Return -1 if none is available. */
        public int get() {
            if (next[pos]==-1) return -1;
            int ret = pos;
            pos = next[pos];
            next[ret]=-1;
            return ret;
        }

        /** Check if a number is available or not. */
        public boolean check(int number) {
            return next[number]!=-1;
        }

        /** Recycle or release a number. */
        public void release(int number) {
            if (next[number]!=-1) return;
            next[number] = pos;
            pos = number;
        }
    }

    //using queue and set
    class PhoneDirectory2 {
        Set<Integer> used = new HashSet<Integer>();
        Queue<Integer> available = new LinkedList<Integer>();
        int max;
        public PhoneDirectory2(int maxNumbers) {
            max = maxNumbers;
            for (int i = 0; i < maxNumbers; i++) {
                available.offer(i);
            }
        }

        public int get() {
            Integer ret = available.poll();
            if (ret == null) {
                return -1;
            }
            used.add(ret);
            return ret;
        }

        public boolean check(int number) {
            if (number >= max || number < 0) {
                return false;
            }
            return !used.contains(number);
        }

        public void release(int number) {
            if (used.remove(number)) {
                available.offer(number);
            }
        }
    }


    //Just use iterator instead of queue
    class PhoneDirectory3 {
        private Set<Integer> set;
        /** Initialize your data structure here
         @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
        public PhoneDirectory3(int maxNumbers) {
            set = new HashSet<Integer>();
            for (int i = 0; i < maxNumbers; i++) {
                set.add(i);
            }
        }

        /** Provide a number which is not assigned to anyone.
         @return - Return an available number. Return -1 if none is available. */
        public int get() {
            if (set.isEmpty()) {
                return -1;
            }
            int number = set.iterator().next();
            set.remove(number);
            return number;
        }

        /** Check if a number is available or not. */
        public boolean check(int number) {
            return set.contains(number);
        }

        /** Recycle or release a number. */
        public void release(int number) {
            set.add(number);
        }
    }


}

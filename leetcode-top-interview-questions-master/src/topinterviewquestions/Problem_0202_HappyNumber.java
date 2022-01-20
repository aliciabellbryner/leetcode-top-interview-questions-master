package topinterviewquestions;

import java.util.HashSet;

public class Problem_0202_HappyNumber {

	//Approach 2: Floyd's Cycle-Finding Algorithm
	//Time complexity : O(logn)
	//Space complexity : O(1).
	//https://leetcode.com/problems/happy-number/solution/
	//要么变成1，要么就是一个其他数字的循环，不可能一直下去，只有前面两种可能
	public int getNext(int n) {
		int totalSum = 0;
		while (n > 0) {
			int d = n % 10;
			n = n / 10;
			totalSum += d * d;
		}
		return totalSum;
	}

	public boolean isHappy(int n) {
		int slow = n;
		int fast = getNext(n);
		while (fast != 1 && slow != fast) {
			slow = getNext(slow);
			fast = getNext(getNext(fast));
		}
		return fast == 1;
	}


	//solution2
	public static boolean isHappy2(int n) {
		while (n != 1 && n != 4) {
			int sum = 0;
			while (n != 0) {
				sum += (n % 10) * (n % 10);
				n /= 10;
			}
			n = sum;
		}
		return n == 1;
	}

    public static boolean isHappy1(int n) {
		HashSet<Integer> set = new HashSet<>();
		while (n != 1) {
			int sum = 0;
			while (n != 0) {
				int r = n % 10;
				sum += r * r;
				n /= 10;
			}
			n = sum;
			if (set.contains(n)) {
				break;
			}
			set.add(n);
		}
		return n == 1;
	}


	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			System.out.println(i + " : " + isHappy1(i));
		}
	}

}

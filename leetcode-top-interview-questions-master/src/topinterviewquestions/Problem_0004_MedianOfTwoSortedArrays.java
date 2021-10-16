package topinterviewquestions;

public class Problem_0004_MedianOfTwoSortedArrays {

	//求两个升序数组的median,即如果两个相加数组(重新排好序)长度是奇数,则返回最中间的数,如果长度是偶数,则返回中间两个数的平均数
	//思路是:
	// 1. 首先我们可以写出getupmedian,来算两个等长数组的upmedian,
	// 2. 其次我们可以根据两个数组的实际长度来分别缩小范围,从而使他们可以利用上我们第一步写的getupmedian来算子数组的upmedian
	// 3. 最后主函数我们就需要判断两个数组的是否为空数组以及数组长度和是奇数还是偶数来调用我们第二部写出来的函数
	//时间复杂度是O(logM)
	//M是nums1和nums2中长度短的长度,因为我们用的getupmedian每次缩小范围一半
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int size = nums1.length + nums2.length;
		boolean even = (size & 1) == 0;
		if (nums1.length != 0 && nums2.length != 0) {
			if (even) {
				return (double) (findKthNum(nums1, nums2, size / 2) + findKthNum(nums1, nums2, size / 2 + 1)) / 2D;
			} else {
				return findKthNum(nums1, nums2, size / 2 + 1);
			}
		} else if (nums1.length != 0) {
			if (even) {
				return (double) (nums1[(size - 1) / 2] + nums1[size / 2]) / 2;
			} else {
				return nums1[size / 2];
			}
		} else if (nums2.length != 0) {
			if (even) {
				return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2;
			} else {
				return nums2[size / 2];
			}
		} else {
			return 0;
		}
	}

	//这个函数的目的是为了求出任意两个升序数组组合之后的第k个数,注意k是从1开始算起
	public static int findKthNum(int[] arr1, int[] arr2, int kth) {
		int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
		int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
		int l = longs.length;
		int s = shorts.length;
		if (kth <= s) {//如果k比小的数组长度还小,则这第k个大的数一定在短的数组中前k位和长的数组前k位中的组合升序数组中的第k位
			return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
		}
		if (kth > l) {
			//如果k比长的数组长度还大,则首先可以排除shorts中的0->(kth-l-1),因为加上longs中所有数也不可能排在组合数组的第k位,剩下(kth-l)->(s-1),长度额外l+s-kth-1
			//同理可以排除longs中的0->(kth-s-1),剩下(Kth-s)->(l-1),长度为l+s-kth-1,与上面剪完之后的数组等长
			//这样的话虽然他们等长,但是如果直接利用getUpMedian算出来的是l+s-kth-1长度的数组中的upmedian,加上删掉的元素,其实算出来的是第k-1位数(可以直接用实例验证)
			//所以我们就必须用接下来两个条件剔除新的两个数组中最前的两位(一定要是最前,不能最后,不然第k位不对)
			if (shorts[kth - l - 1] >= longs[l - 1]) {
				return shorts[kth - l - 1];
			}
			if (longs[kth - s - 1] >= shorts[s - 1]) {
				return longs[kth - s - 1];
			}
			return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
		}
		// 当kth>s且kth<=l
		//此时首先可以排除的是long中的0->(kth-s-1)位置,因为就算加上shorts也不可能排到第k位,还有就是longs的k->(l-1)位置也要排除
		//其次因为排除之后的long的长度比shorts大一位,所以我们还是继续判断一下新数组最小的那位longs[kth - s - 1] 跟 shorts[s - 1]的大小关系,如果>=那一定是longs[kth - s - 1] ,
		// 否则此时就可以利用getupmedian因为他们等长了
		if (longs[kth - s - 1] >= shorts[s - 1]) {
			return longs[kth - s - 1];
		}
		return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);
	}

	//非递归
	//要保证A和B数组等长
	//返回值是等长数组A和B组成的新的数组中中间位置靠前的那一个数值
	//思路就是通过判断A和B中的中间位置或者中间靠前位置的数的大小关系来缩小范围,每一次缩小一半,所以时间复杂度是O(logM) M是数组的长度
	public static int getUpMedian(int[] A, int s1, int e1, int[] B, int s2, int e2) {
		int mid1 = 0;
		int mid2 = 0;
		while (s1 < e1) {
			mid1 = (s1 + e1) / 2;
			mid2 = (s2 + e2) / 2;
			if (A[mid1] == B[mid2]) {
				return A[mid1];
			}
			if (((e1 - s1 + 1) & 1) == 1) { // 奇数长度
				if (A[mid1] > B[mid2]) {
					if (B[mid2] >= A[mid1 - 1]) {
						return B[mid2];
					}
					e1 = mid1 - 1;
					s2 = mid2 + 1;
				} else { // A[mid1] < B[mid2]
					if (A[mid1] >= B[mid2 - 1]) {
						return A[mid1];
					}
					e2 = mid2 - 1;
					s1 = mid1 + 1;
				}
			} else { // 偶数长度
				if (A[mid1] > B[mid2]) {
					e1 = mid1;
					s2 = mid2 + 1;
				} else {
					e2 = mid2;
					s1 = mid1 + 1;
				}
			}
		}
		return Math.min(A[s1], B[s2]);
	}

}

package topinterviewquestions;

import java.util.Stack;

public class Problem_1209_RemoveAllAdjacentDuplicatesinStringII_G {

    //方法三：栈
    //当前字符与前一个不同时，往栈中压入 1。否则栈顶元素加 1。
    //算法
    //迭代字符串：
    //如果当前字符与前一个相同，栈顶元素加 1。
    //否则，往栈中压入 1。
    //如果栈顶元素等于 k，则从字符串中删除这 k 个字符，并将 k 从栈顶移除。
    //
    //注意：因为在 Java 中 Integer 是不可变的，需要先弹出栈顶元素，然后加 1，再压入栈顶。
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string-ii/solution/shan-chu-zi-fu-chuan-zhong-de-suo-you-xiang-lin--4/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> counts = new Stack<>();
        for (int i = 0; i < sb.length(); ++i) {
            if (i == 0 || sb.charAt(i) != sb.charAt(i - 1)) {
                counts.push(1);
            } else {
                int incremented = counts.pop() + 1;
                if (incremented == k) {
                    sb.delete(i - k + 1, i + 1);
                    i = i - k;
                } else {
                    counts.push(incremented);
                }
            }
        }
        return sb.toString();
    }




    //方法五：双指针
    //该方法由lee215提出，使用双指针可以优化方法二和三中的字符串操作。这里，使用快慢指针复制字符。每次需要删除 k 个元素时，只需要将慢指针回退 k 个位置。
    //算法
    //
    //初始慢指针 j 等于 0。
    //
    //使用快指针 i 遍历字符串：
    //
    //令 s[i] = s[j]。
    //
    //如果 s[j] = s[j - 1]，则栈顶元素加 1。
    //
    //否则，栈中压入 1。
    //如果计数器等于 k，j = j - k，并弹出栈顶元素。
    //
    //返回字符串的前 j 个字符。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string-ii/solution/shan-chu-zi-fu-chuan-zhong-de-suo-you-xiang-lin--4/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    public String removeDuplicates2(String s, int k) {
        Stack<Integer> counts = new Stack<>();//j是慢指针，i是快指针
        char[] sa = s.toCharArray();
        int j = 0;
        for (int i = 0; i < s.length(); ++i, ++j) {
            sa[j] = sa[i];
            if (j == 0 || sa[j] != sa[j - 1]) {
                counts.push(1);
            } else {
                int incremented = counts.pop() + 1;
                if (incremented == k) {
                    j = j - k;
                } else {
                    counts.push(incremented);
                }
            }
        }
        return new String(sa, 0, j);
    }


}

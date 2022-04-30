package topinterviewquestions;

public class Problem_0925_LongPressedName_G {
    //方法一：双指针
    //思路与算法
    //
    //根据题意能够分析得到：字符串 \textit{typed}typed 的每个字符，有且只有两种「用途」：
    //
    //作为 \textit{name}name 的一部分。此时会「匹配」\textit{name}name 中的一个字符
    //
    //作为长按键入的一部分。此时它应当与前一个字符相同。
    //
    //如果 \textit{typed}typed 中存在一个字符，它两个条件均不满足，则应当直接返回 \textit{false}false；否则，当 \textit{typed}typed 扫描完毕后，我们再检查 \textit{name}name 的每个字符是否都被「匹配」了。
    //
    //实现上，我们使用两个下标 i,ji,j 追踪 \textit{name}name 和 \textit{typed}typed 的位置。
    //
    //当 \textit{name}[i]=\textit{typed}[j]name[i]=typed[j] 时，说明两个字符串存在一对匹配的字符，此时将 i,ji,j 都加 11。
    //
    //否则，如果 \textit{typed}[j]=\textit{typed}[j-1]typed[j]=typed[j−1]，说明存在一次长按键入，此时只将 jj 加 11。
    //
    //最后，如果 i=\textit{name}.\text{length}i=name.length，说明 \textit{name}name 的每个字符都被「匹配」了。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/long-pressed-name/solution/chang-an-jian-ru-by-leetcode-solution/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public boolean isLongPressedName(String name, String typed) {
            int i = 0, j = 0;
            while (j < typed.length()) {
                if (i < name.length() && name.charAt(i) == typed.charAt(j)) {
                    i++;
                    j++;
                } else if (j > 0 && typed.charAt(j) == typed.charAt(j - 1)) {
                    j++;
                } else {
                    return false;
                }
            }
            return i == name.length();
        }
    }

}

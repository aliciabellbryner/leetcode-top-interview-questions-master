package topinterviewquestions;

import java.util.Arrays;

public class Problem_0616_AddBoldTaginString_G {

        //Use a boolean array to mark if character at each position is bold or not. After that, things will become simple.
        public static String addBoldTag(String s, String[] dict) {
            int N = s.length();
            boolean[] bold = new boolean[N];
            int end = 0;//这个是标记如果用dict的单词，目前最远能走到哪的后面一位，即最近的不在dict的位置
            for (int i = 0; i < N; i++) {
                for (String word : dict) {
                    if (s.startsWith(word, i)) {
                        end = Math.max(end, i + word.length());
                    }
                }
                bold[i] = end > i;
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < N; i++) {
                if (!bold[i]) {
                    result.append(s.charAt(i));
                    continue;
                }
                int j = i;//第一个在dict的位置
                while (j < N && bold[j]) {
                    j++;//找到第一个不在dict的位置
                }
                result.append("<b>" + s.substring(i, j) + "</b>");
                i = j - 1;
            }
//            System.out.println(Arrays.toString(bold));
            return result.toString();
        }



    public static void main(String[] args) {
        String test = "abcxyz123";
        String[] arr = new String[]{"abc","123"};
        System.out.println(addBoldTag(test, arr));
    }
}

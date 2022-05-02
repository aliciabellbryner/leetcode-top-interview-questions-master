package topinterviewquestions;

public class Problem_1255_MaximumScoreWordsFormedbyLetters_G {
    //解题思路：
    //提供的字母集合，每个字母只能用一次
    //提供的单词集合，每个单词也只能用一次
    //单词集合的大小，1 <= words[i].length <= 15
    //枚举 words 子集总共 2^15 种情况
    //对每一种情况统计使用了哪些字母
    //如果字母超出范围了，就不符合要求
    //否则按照字母表计算得分
    //记录最大得分
    //位压缩：
    //对于单词集合中每一个词，都可以选择，用/不用
    //所以就可以用位 0/1 来表示
    //
    //单词集合中每个单词都表示出来，总和就是 2^N2
    //N
    //  种
    //可以用 1 << N 来表示
    //
    //当遍历到其中一个组合时，其数字的二进制位表示的就是各个单词的使用状态
    //比如 5，二进制 101，代表第 0 个和第 2 个单词使用，第 1 个单词不使用
    //
    //检查时，对于第 i 个单词，使用 1 << i，得到二进制除了第 i 位（顺序是从右至左）其余全 0 的数字
    //比如第 2 个单词，1 << 2 之后得到 4（二进制 100）
    //
    //再与状态位进行&操作，得到是否使用
    //4（二进制100）与刚才的 5（二进制101）&操作，得到 true
    //
    //作者：ikaruga
    //链接：https://leetcode-cn.com/problems/maximum-score-words-formed-by-letters/solution/5258-by-ikaruga/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        // 将第（bit）种组合情况，所使用的单词中的字母数量统计出来
        int[] group(String[] words, int bit) {
            int[] g = new int[26];
            for (int i = 0; i < words.length; i++) {
                if ((bit & (1 << i)) == 0) continue;
                for (char c : words[i].toCharArray()) {
                    g[c - 'a']++;
                }
            }
            return g;
        }

        // 根据规则计算得分
        int calcScore(int[] group, int[] lettercnt, int[] score) {
            int s = 0;
            for (int j = 0; j < 26; j++) {
                if (lettercnt[j] < group[j]) return 0;
                s += group[j] * score[j];
            }
            return s;
        }

        int maxScoreWords(String[] words, char[] letters, int[] score) {
            // 统计给出的字母的数量
            int[] lettercnt = new int[26];
            for (char c : letters) {
                lettercnt[c - 'a']++;
            }

            int ans = 0;
            for (int i = 0; i < (1 << words.length); i++) {
                int[] g = group(words, i);
                ans = Math.max(ans, calcScore(g, lettercnt, score));
            }
            return ans;
        }
    }
}

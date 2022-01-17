package topinterviewquestions;

public class Problem_0043_MultiplyStrings {
    public String multiply(String num1, String num2) {
        int M = num1.length(), N = num2.length();
        int[] pos = new int[M + N];

        for(int i = M - 1; i >= 0; i--) {
            for(int j = N - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p = i + j;
                int sum = mul + pos[p+1];
                pos[p] += sum / 10;
                pos[p+1] = (sum) % 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int p : pos) {
            if(sb.length() != 0 || p != 0) {
                sb.append(p);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}

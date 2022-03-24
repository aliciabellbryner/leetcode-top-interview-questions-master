package topinterviewquestions;

public class Problem_1592_RearrangeSpacesBetweenWords_G {
    public String reorderSpaces(String text) {
        if (text == null || text.length() == 0) return "";
        int numWords = 0, numSpaces = 0;
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) == ' ') {
                numSpaces++;
            } else {
                numWords++;
                while (i < text.length() && text.charAt(i) != ' ') i++;     // Skip the current word.
                i--;
            }
        }
        int maxSpaces = numWords == 1 ? 0 : numSpaces / (numWords - 1);
        int remainingSpaces = numWords == 1 ? numSpaces : numSpaces % (numWords - 1);

        boolean skipFirst = true;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) != ' ') {
                // Don't put the whitespaces in front of the first word.
                if (!skipFirst) result.append(" ".repeat(maxSpaces)); else skipFirst = false;

                // Append the current word to the result.
                while (i < text.length() && text.charAt(i) != ' ') result.append(text.charAt(i++));
                i--;
            }
        }
        return result.append(" ".repeat(remainingSpaces)).toString();
    }
}

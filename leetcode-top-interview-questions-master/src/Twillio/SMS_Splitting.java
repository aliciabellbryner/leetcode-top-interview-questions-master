package Twillio;

import java.util.ArrayList;
import java.util.List;

public class SMS_Splitting {

    public static List<String> segments(String message){
        List<String> res = new ArrayList<>();
        if (message.length() < 160) {
            res.add(message);
            return res;
        }

        int start = 0;
        int end = start + 154;

        while (end < message.length()) {
            if (message.charAt(end) != ' ') {
                while (end >= start && message.charAt(end) != ' ' && message.charAt(end + 1) != ' ') {
                    end--;
                }
            }
            res.add(message.substring(start, end+ 1));
            start = end + 1;
            end = start + 154;
        }
        res.add(message.substring(start, message.length()));

        for (int i = 0; i < res.size(); i++) {
            res.set(i, res.get(i)+"(" + (i + 1) + "/" + (res.size()) + ")");
        }
        return res;
    }
}

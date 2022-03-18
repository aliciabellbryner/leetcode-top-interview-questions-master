package Tesla;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class TakeHomeChallenge {
    public static void main(String[] args) throws Exception{

        for (int i = 1; i <= 4; i++) {
            File newFile = new File("output-file-" + i);
            newFile.createNewFile();
        }

        //you need to put the real location of the sample txt file before you run any tests
        File file = new File("location of sample.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] sa = line.split(",", 4);

            //if the length of sa is smaller than 4, means it is not followeing the desired format, so skip
            if (sa.length < 4) {
                continue;
            }

            //the time must be a positive integer, if not we just skip
            //the partition number also need to be valid
            //if sum equal to -1, means it has different letter other than #one to #ten, so we need to skip that line
            if (isStringPositiveNumeric(sa[0]) && isValidPartition(sa[1]) && calSum(sa[3]) != -1) {
                FileWriter fw = new FileWriter("output-file-" + sa[1], true);
                fw.append(sa[0] + ',' + sa[2] + ',' + calSum(sa[3]));
                fw.append(System.getProperty( "line.separator" ));
                fw.close();
            }
        }
        sc.close();
    }

    //determine if the partition number is valid or not
    private static boolean isValidPartition(String str) {
        if (str.length() != 1 || str.charAt(0) < '1' || str.charAt(0) > '4' ) {
            return false;
        }
        return true;
    }

    //this function is used to determine if the String represents a 64-bit positive integer or not
    private static boolean isStringPositiveNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }


    //this function is used to calculate the sum of the last section,
    // and also determine if the format is following the described format
    private static int calSum(String s) {
        String[] sa = s.split(",");
        int sum = 0;
        for (String cur : sa) {
            switch (cur) {
                case "#one":
                    sum += 1;
                    break;
                case "#two":
                    sum += 2;
                    break;
                case "#three":
                    sum += 3;
                    break;
                case "#four":
                    sum += 4;
                    break;
                case "#five":
                    sum += 5;
                    break;
                case "#six":
                    sum += 6;
                    break;
                case "#seven":
                    sum += 7;
                    break;
                case "#eight":
                    sum += 8;
                    break;
                case "#nine":
                    sum += 9;
                    break;
                case "#ten":
                    sum += 10;
                    break;
                default:
                    return -1;
            }
        }
        return sum;
    }
}

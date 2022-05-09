package topinterviewquestions;

public class Problem_0551_StudentAttendanceRecordI_G {
    //https://leetcode.com/problems/student-attendance-record-i/discuss/101552/Java-Simple-without-Regex-3-lines
    public boolean checkRecord(String s) {
        if(s.indexOf("A") != s.lastIndexOf("A") || s.contains("LLL"))
            return false;
        return true;
    }
}

package topinterviewquestions;

public class Problem_0158_ReadNCharactersGivenread4II {
    //https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/discuss/49598/A-simple-Java-code/238766
    /**
     * @param bufCache: store reading results when we call read4() every time. Once it is empty, call read4() again
     * @param bufPtr: globe variable that points next reading position in bufCache. It should always < 4
     * @param bufCount: count number of elements in bufCache. Usually equals to 4, but may less than 4 when reach end of input
     * */
    char[] bufCache = new char[4];
    int bufPtr = 0;
    int bufCount = 0;

    public int read(char[] buf, int n) {
        int nIndex = 0;

        // fill out every position until reach n
        while (nIndex < n) {
            // only if bufPtr does not reach the end of bufCache array, we can assign value from bufCache to final buf array
            if (bufPtr < bufCount) {
                buf[nIndex++] = bufCache[bufPtr++];
            }

            // if we already used all characters from bufCache, we need to read new characters by calling read4()
            // and then fill the bufCache
            else {
                //bufCount = read4(bufCache);//you have to include this in the interview, comment as we don't have access to the API rn
                bufPtr = 0;

                // if no more characters we can read, we should break the entire loop and return 0
                if (bufCount == 0) {
                    break;
                }
            }
        }
        return nIndex;
    }
}

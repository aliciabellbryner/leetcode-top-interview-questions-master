package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Problem_0609_FindDuplicateFileInSystem {
    //Approach #2 Using HashMap [Accepted]
    //Time complexity : O(n*x). n strings of average length x is parsed.
    //
    //Space complexity : O(n*x). mapmap and resres size grows upto n*x.
    //In this approach, firstly we obtain the directory paths, the file names and their contents separately by appropriately splitting each string in the given pathspaths list. In order to find the files with duplicate contents, we make use of a HashMap mapmap, which stores the data in the form (contents, list\_of\_file\_paths\_with\_this\_content)(contents,list_of_file_paths_with_this_content). Thus, for every file's contents, we check if the same content already exist in the hashmap. If so, we add the current file's path to the list of files corresponding to the current contents. Otherwise, we create a new entry in the mapmap, with the current contents as the key and the value being a list with only one entry(the current file's path).
    //
    //At the end, we find out the contents corresponding to which atleast two file paths exist. We obtain the resultant list resres, which is a list of lists containing these file paths corresponding to the same contents.
    //
    //The following animation illustrates the process for a clearer understanding.
    public class Solution {
        public List <List< String >> findDuplicate(String[] paths) {
            HashMap<String, List<String>> map = new HashMap<>();
            for (String path: paths) {
                String[] values = path.split(" ");
                for (int i = 1; i < values.length; i++) {
                    String[] name_cont = values[i].split("\\(");
                    name_cont[1] = name_cont[1].replace(")", "");
                    List<String> list = map.getOrDefault(name_cont[1], new ArrayList<String>());
                    list.add(values[0] + "/" + name_cont[0]);
                    map.put(name_cont[1], list);
                }
            }
            List<List<String>> res = new ArrayList<>();
            for (String key: map.keySet()) {
                if (map.get(key).size() > 1)
                    res.add(map.get(key));
            }
            return res;
        }
    }
}

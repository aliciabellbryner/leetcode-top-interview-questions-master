package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

/*
You are asked to design a file system that allows you to create new paths and associate them with different values.

The format of a path is one or more concatenated strings of the form: / followed by one or more lowercase English letters. For example, "/leetcode" and "/leetcode/problems" are valid paths while an empty string "" and "/" are not.

Implement the FileSystem class:

bool createPath(string path, int value) Creates a new path and associates a value to it if possible and returns true. Returns false if the path already exists or its parent path doesn't exist.
int get(string path) Returns the value associated with path or returns -1 if the path doesn't exist.


Example 1:

Input:
["FileSystem","createPath","get"]
[[],["/a",1],["/a"]]
Output:
[null,true,1]
Explanation:
FileSystem fileSystem = new FileSystem();

fileSystem.createPath("/a", 1); // return true
fileSystem.get("/a"); // return 1
Example 2:

Input:
["FileSystem","createPath","createPath","get","createPath","get"]
[[],["/leet",1],["/leet/code",2],["/leet/code"],["/c/d",1],["/c"]]
Output:
[null,true,true,2,false,-1]
Explanation:
FileSystem fileSystem = new FileSystem();

fileSystem.createPath("/leet", 1); // return true
fileSystem.createPath("/leet/code", 2); // return true
fileSystem.get("/leet/code"); // return 2
fileSystem.createPath("/c/d", 1); // return false because the parent path "/c" doesn't exist.
fileSystem.get("/c"); // return -1 because this path doesn't exist.


Constraints:

The number of calls to the two functions is less than or equal to 104 in total.
2 <= path.length <= 100
1 <= value <= 109
 */
public class Problem_1166_DesignFileSystem_G {
    /*
    Approach 1: Dictionary for storing paths
Intuition

This first approach is pretty much a simulation-based approach for solving this problem. We call it a simulation-based approach because it doesn't use any fancy data-structure for storing the paths and pretty much, we do what the problem asks us to do for both the functions. We simply need a key-value data structure with some additional processing to verify the validity of a path being added. Naturally, a HashMap or a dictionary seems to be a good data structure to go with.

Let's look at a visual representation of how a HashMap would look as we keep on adding more paths to it. The following represents the state of the file system after adding the following paths: /a, /a/b, /a/b/c, and /a/b/e.

HashMap

Figure 1. A HashMap containing the various paths in the file system and their keys.

Retrieving the value corresponding to a path is relatively simple because the path itself represents a key in the HashMap. However, for adding a new path, we can simply retrieve the parent path e.g. /a/b is the parent path for /a/b/c and similarly for /a/b/e, and then check if the parent path exists in the HashMap as a key or not.

Algorithm

Initialize a dictionary or a HashMap called paths that will have the key as the path input to our create function and the value would be the value passed to the function.

For our create function, we have three steps that we need to do:

Step-1 is that we do a basic verification of the path being valid or not. Here we check if the path is empty, or "/" or if the path already exists in our dictionary. If any of these conditions are met, we simply return false.
The second step is to obtain the parent path of the provided path and check its presence in the dictionary. If the parent path doesn't exist, then we simply return false. Else, we move on.
Note that checking for just the parent is enough because the presence of the parent path ensures that the grandparent (and other ancestors by this logic) would also exist in the dictionary.

Finally, we insert the provided path and value into the dictionary and return true.
For the get function, we simply return a default value of -1 if the path doesn't exist inside the dictionary. Else, we return the actual value.


Complexity Analysis

Time Complexity: \mathcal{O}(M)O(M), where MM is the length of path. All the time is actually consumed by the operation that gives us the parent path. We first spend \mathcal{O}(M)O(M) on finding the last "/" of the path and then another \mathcal{O}(M)O(M) to obtain the parent string. Searching and addition into a HashMap/dictionary takes an ammortized \mathcal{O}(1)O(1) time.
Space Complexity: \mathcal{O}(K)O(K) where KK represents the number of unique paths that we add.

Approach 2: Trie based approach
Intuition

There is another great data structure which we can use for approaching this particular problem and that is the Trie data structure. In order to read more about this data structure and other use cases, please refer to our Explore Card for the same. A problem that we see with our previous problem is that for adding a path of length M, we need to add all of its \frac{M \times (M - 1)}{2}
2
M×(M−1)
​
  ancestors which would end up occupying a lot of space in our HashMap based solution since each of these ancestors would occupy a key in the dictionary.

We can instead make use of a Trie here because the common prefixes for various strings can be represented by a common branch in the Trie and that ends up saving a lot of space. Additionally, sub-paths along a branch can also be represented easily without cloning the Trie branch. For example all the ancestors of /a/b/c/d/e i.e. /a, /a/b, /a/b/c, /a/b/c/d can be marked on the single branch representing the path /a/b/c/d/e and that is a lot of space saving for this problem.

Here's how a Trie would look like after we have added the following paths to it: /a, /a/b, /a/b/c, /a/b/e, /a/e.

HashMap

Figure 2. A Trie representation showing the various paths we added to the File System.

Algorithm

The basic data structure that is used for representing a Trie is a dictionary. The dictionary and other potential flags/data values can be a part of a custom TreeNode data structure. For this problem, we will have a TrieNode data structure that will contain three things 1. The string representing the path name. 2. The value corresponding to this path. 3. And finally, a dictionary representing the outgoing connections to other TrieNodes.

The root of our trie will be a TrieNode containing the empty string.

Create() ~

First, we will split the given path into various components using / as the delimiter. So for the path /a/b/c, we will have four components namely , a, b, and c.

HashMap

Figure 3. Let's consider an example Trie.

Initialize a TrieNode called curr which will be equal to the root node of the trie. Note that we always start at the root node and then go down based on the various path components.

HashMap

Figure 4. Initialize the "curr" node.

We will iterate over all of these components and for each of them, we will do the following:

Check if the component exists in curr's dictionary . If it doesn't we return false unless it is the last component of the path in which case we add it to the current dictionary.

If the current component exists in the curr node, we obtain the value which will be another TrieNode and update curr to be equal to that node.

Eventually, we will process the last component of the path. If that exists in the trie as well, we return false in accordance with the problem statement. Else, we add it to the trie by creating a new node with path as path and value as value i.e. the input parameters.

HashMap

Figure 5. Add the last component to the Trie.

Get() ~

To check if a path exists in the trie, we need to verify if all its components, along with the proper connections exist in the trie.
Split the given path into various components using / as the delimiter.
Initialize a TrieNode called curr which will be equal to the root node of the trie.
We will iterate over all of these components and for each of them, we will do the following:
Check if the component exists in curr's dictionary .
If the current component exists in the curr node, we obtain the value which will be another TrieNode and update curr to be equal to that node.
If it doesn't exist, we return false.
Return true.

Complexity Analysis

Before we get into the complexity analysis, let's see why one might prefer the Trie approach. The main advantage of the trie based approach is that we are able to save on space. All the paths sharing common prefixes can be represented by a common branch in the tree. The disadvantage however is that the get operation no longer remains O(1)O(1).

Time Complexity:
create ~ It takes O(T)O(T) to add a path to the trie if it contains TT components.
get ~ It takes O(T)O(T) to find a path in the trie if it contains TT components.
Space Complexity:
create ~ Lets look at the worst case space complexity. In the worst case, none of the paths will have any common prefixes. We are not considering the ancestors of a larger path here. In such a case, each unique path will end up taking a different branch in the trie. Also, for a path containing TT components, there will be TT nodes in the trie.
get ~ O(1)O(1).
     */


    class FileSystem1 {

        HashMap<String, Integer> paths;

        public FileSystem1() {
            this.paths = new HashMap<String, Integer>();
        }

        public boolean createPath(String path, int value) {

            // Step-1: basic path validations
            if (path.isEmpty() || (path.length() == 1 && path.equals("/")) || this.paths.containsKey(path)) {
                return false;
            }

            int delimIndex = path.lastIndexOf("/");
            String parent = path.substring(0, delimIndex);

            // Step-2: if the parent doesn't exist. Note that "/" is a valid parent.
            if (parent.length() > 1 && !this.paths.containsKey(parent)) {
                return false;
            }

            // Step-3: add this new path and return true.
            this.paths.put(path, value);
            return true;
        }

        public int get(String path) {
            return this.paths.getOrDefault(path, -1);
        }
    }


    class FileSystem2 {

        // The TrieNode data structure.
        class TrieNode {

            String name;
            int val = -1;
            Map<String, TrieNode> map = new HashMap<>();

            TrieNode (String name) {
                this.name = name;
            }
        }

        TrieNode root;

        // Root node contains the empty string.
        public FileSystem2() {
            this.root = new TrieNode("");
        }

        public boolean createPath(String path, int value) {

            // Obtain all the components
            String[] components = path.split("/");

            // Start "curr" from the root node.
            TrieNode cur = root;

            // Iterate over all the components.
            for (int i = 1; i < components.length; i++) {

                String currentComponent = components[i];

                // For each component, we check if it exists in the current node's dictionary.
                if (cur.map.containsKey(currentComponent) == false) {

                    // If it doesn't and it is the last node, add it to the Trie.
                    if (i == components.length - 1) {
                        cur.map.put(currentComponent, new TrieNode(currentComponent));
                    } else {
                        return false;
                    }
                }

                cur = cur.map.get(currentComponent);
            }

            // Value not equal to -1 means the path already exists in the trie.
            if (cur.val != -1) {
                return false;
            }

            cur.val = value;
            return true;
        }

        public int get(String path) {

            // Obtain all the components
            String[] components = path.split("/");

            // Start "curr" from the root node.
            TrieNode cur = root;

            // Iterate over all the components.
            for (int i = 1; i < components.length; i++) {

                String currentComponent = components[i];

                // For each component, we check if it exists in the current node's dictionary.
                if (cur.map.containsKey(currentComponent) == false) {
                    return -1;
                }

                cur = cur.map.get(currentComponent);
            }

            return cur.val;
        }
    }



    //diss
    class FileSystem3 {
        Map<String, Integer> file = new HashMap<>();

        public FileSystem3() {
            file.put("", -1);
        }

        public boolean create(String path, int value) {
            int idx = path.lastIndexOf("/");
            String parent = path.substring(0, idx);
            if (!file.containsKey(parent)) { return false; }
            return file.putIfAbsent(path, value) == null;
        }

        public int get(String path) {
            return file.getOrDefault(path, -1);
        }

    }

    //trietree
    class FileSystem {
        class File{
            String name;
            int val = -1;
            Map<String, File> map = new HashMap<>();

            File(String name){
                this.name = name;
            }
        }
        File root;
        public FileSystem() {
            root = new File("");
        }

        public boolean create(String path, int value) {
            String[] array = path.split("/");
            File cur = root;

            for(int i=1;i<array.length;i++){
                String cur_name = array[i];
                if(cur.map.containsKey(cur_name)==false){
                    if(i==array.length-1){
                        cur.map.put(cur_name, new File(cur_name));
                    }else{
                        return false;
                    }
                }
                cur = cur.map.get(cur_name);
            }

            if(cur.val!=-1){
                return false;
            }

            cur.val = value;
            return true;
        }

        public int get(String path) {
            String[] array = path.split("/");
            File cur = root;
            for(int i=1;i<array.length;i++){
                String cur_name = array[i];
                if(cur.map.containsKey(cur_name)==false){
                    return -1;
                }
                cur = cur.map.get(cur_name);
            }

            return cur.val;


        }
    }


}

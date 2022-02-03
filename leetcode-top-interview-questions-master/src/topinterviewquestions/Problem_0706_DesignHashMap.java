package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0706_DesignHashMap {
    //https://leetcode.com/problems/design-hashmap/discuss/152746/Java-Solution/274577
    class MyHashMap {
        //Load factor = 10000/size = 0.75
        List<int[]>[] lists; //using List<List<int[]>> lists is fine as well.
        int size = 13000;

        //O(size)
        /** Initialize your data structure here. */
        public MyHashMap() {
            lists = new ArrayList[size];
            for(int i=0; i<lists.length; i++)
                lists[i] = new ArrayList<>(); //don't use linkedlist in my version which makes put() O(L^2)
        }

        public int getHashcode(int key)
        {
            return key%size;
        }

        //O(L)
        /** value will always be non-negative. */
        public void put(int key, int value) {
            int index = getHashcode(key);
            for(int i=0; i<lists[index].size(); i++)
            {
                if(lists[index].get(i)[0]==key)
                {
                    lists[index].get(i)[1]=value;
                    return;
                }
            }
            lists[index].add(new int[]{key, value});
        }

        //O(L)
        /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
        public int get(int key) {
            int index = getHashcode(key);
            for(int i=0; i<lists[index].size(); i++)
            {
                if(lists[index].get(i)[0]==key)
                    return lists[index].get(i)[1];
            }
            return -1;
        }

        //O(L)
        /** Removes the mapping of the specified value key if this map contains a mapping for the key */
        public void remove(int key) {
            int index = getHashcode(key);
            for(int i=0; i<lists[index].size(); i++)
            {
                if(lists[index].get(i)[0]==key)
                    lists[index].remove(i);
            }
            return;
        }
    }

    //Collisions are resolved using linked list
    //https://leetcode.com/problems/design-hashmap/discuss/152746/Java-Solution
    class MyHashMap1 {
        final ListNode[] nodes;
        public MyHashMap1() {
            nodes = new ListNode[10000];
        }
        public void put(int key, int value) {
            int i = getIdx(key);
            if (nodes[i] == null)
                nodes[i] = new ListNode(-1, -1);
            ListNode prev = find(nodes[i], key);
            if (prev.next == null) {
                prev.next = new ListNode(key, value);
            } else {
                prev.next.val = value;
            }
        }

        public int get(int key) {
            int i = getIdx(key);
            if (nodes[i] == null)
                return -1;
            ListNode node = find(nodes[i], key);
            return node.next == null ? -1 : node.next.val;
        }

        public void remove(int key) {
            int i = getIdx(key);
            if (nodes[i] == null) return;
            ListNode prev = find(nodes[i], key);
            if (prev.next == null) return;
            prev.next = prev.next.next;
        }

        int getIdx(int key) { return Integer.hashCode(key) % nodes.length;}

        ListNode find(ListNode bucket, int key) {
            ListNode node = bucket, prev = null;
            while (node != null && node.key != key) {
                prev = node;
                node = node.next;
            }
            return prev;
        }

        class ListNode {
            int key, val;
            ListNode next;

            ListNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }
    }
}

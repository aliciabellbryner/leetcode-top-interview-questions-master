package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Problem_2102_2102_SequentiallyOrdinalRankTracker {
    //Similar approach with two Priority Queues
    //https://leetcode.com/problems/sequentially-ordinal-rank-tracker/discuss/1623437/Java-TreeSet-+-pointer-(simple)/1195044
    class SORTracker {
        class Item {
            int price;
            String name;

            Item(int price, String name) {
                this.price = price;
                this.name = name;
            }
        }

        PriorityQueue<Item> maxH, minH;
        int count;

        public SORTracker() {
            maxH = new PriorityQueue<Item>((a, b) -> a.price == b.price ? a.name.compareTo(b.name) : b.price - a.price);
            minH = new PriorityQueue<Item>((a, b) -> a.price == b.price ? b.name.compareTo(a.name) : a.price - b.price);
            count = 0;
        }

        public void add(String name, int price) {
            minH.add(new Item(price, name));
            if (minH.size() > count) {
                maxH.add(minH.poll());
            }
        }

        public String get() {
            minH.add(maxH.poll());
            count++;
            return minH.peek().name;
        }
    }

//    class Solution {
//        public class Solution1 {

            public static class Item {

                /* attributes of an item */
                private int price;
                private String name;

                // constructor
                public Item(int p, String n) {
                    this.price = p;
                    this.name = n;
                }

            }

            /*
                Complete the 'getItems' function below.
                The function is expected to return a STRING_ARRAY.
                The function accepts 2D_STRING_ARRAY entries as parameter.
            */
            public static List<String> getItems(List<List<String>> entries) {
                // Write your code here

                /* variables */
                int size = entries.size();
                int currentView = 0; // used to keep track of how many views have been called
                int j = 0;
                List<String> result = new ArrayList<>();

                PriorityQueue<Item> maxH, minH;
                int count;
                maxH = new PriorityQueue<Item>((a, b) -> a.price == b.price ? b.name.compareTo(a.name) : b.price - a.price);
                minH = new PriorityQueue<Item>((a, b) -> a.price == b.price ? a.name.compareTo(b.name) : a.price - b.price);
                count = 0;
                /* Traverse through entries */
                for (int i = 0; i < size; i++) {
                    String s = entries.get(i).get(0); // grab the command

                    // check if current entry is an INSERT command
                    if (s.equals("INSERT")) {


                        String curName = entries.get(i).get(1); // grab item name
                        int curPrice = Integer.parseInt(entries.get(i).get(2)); // grab item price

                        maxH.add(new Item(curPrice, curName));
                        if (maxH.size() > count) {
                            minH.add(maxH.poll());
                        }
                    }

                    // else the command is VIEW
                    else {
                        maxH.add(minH.poll());
                        count++;
                        result.add(maxH.peek().name);
//
//                        j = 0; // reset j to 0
//                        currentView++; // increment the amount of view calls
//                        String resultString = "";
//
//                        // poll the priority queue to the amount of currentView calls there is currently
//                        while (j < currentView) {
//                            Item currItem = pq.poll();
//                            itemsPolled.add(currItem); // add item polled to arrayList, so you can add it back to the heap
//                            resultString = currItem.name; // this is the item the customer sees
//                            j++;
//                        }
//
//                        // add back the polled items into the priority queue
//                        for (int l = 0; l < itemsPolled.size(); l++) {
//                            pq.add(itemsPolled.get(l));
//                        }
//
//                        itemsPolled.clear(); // clear the arrayList for next iteration
//
//                        result.add(resultString);  // add the item the customer sees to the result
                    }
                }
                return result;
            }

            public static void main(String[] args) {

                /***********  Test case 1 ***********/
                List<List<String>> items = new ArrayList<List<String>>();

                ArrayList<String> insert1 = new ArrayList<>();
                insert1.add("INSERT");
                insert1.add("fries");
                insert1.add("4");

                ArrayList<String> insert2 = new ArrayList<>();
                insert2.add("INSERT");
                insert2.add("soda");
                insert2.add("2");


                ArrayList<String> view1 = new ArrayList<>();
                view1.add("VIEW");
                view1.add("-");
                view1.add("-");

                ArrayList<String> view2 = new ArrayList<>();
                view2.add("VIEW");
                view2.add("-");
                view2.add("-");

                ArrayList<String> insert3 = new ArrayList<>();
                insert3.add("INSERT");
                insert3.add("hamburger");
                insert3.add("5");

                ArrayList<String> view3 = new ArrayList<>();
                view3.add("VIEW");
                view3.add("-");
                view3.add("-");

                ArrayList<String> insert4 = new ArrayList<>();
                insert4.add("INSERT");
                insert4.add("nuggets");
                insert4.add("4");

                ArrayList<String> insert5 = new ArrayList<>();
                insert5.add("INSERT");
                insert5.add("cookies");
                insert5.add("1");


                ArrayList<String> view4 = new ArrayList<>();
                view4.add("VIEW");
                view4.add("-");
                view4.add("-");

                ArrayList<String> view5 = new ArrayList<>();
                view5.add("VIEW");
                view5.add("-");
                view5.add("-");

                items.add(insert1);
                items.add(insert2);
                items.add(view1);
                items.add(view2);
                items.add(insert3);
                items.add(view3);
                items.add(insert4);
                items.add(insert5);
                items.add(view4);
                items.add(view5);

                System.out.println("entries = " + items); //

                List<String> result = getItems(items);
                System.out.println("items customer viewed1 = " + result);


                /***********  Test case 2 ***********/
                List<List<String>> items2 = new ArrayList<List<String>>();

                ArrayList<String> i1 = new ArrayList<>();
                i1.add("INSERT");
                i1.add("ruler");
                i1.add("4");

                ArrayList<String> v1 = new ArrayList<>();
                v1.add("VIEW");
                v1.add("-");
                v1.add("-");

                ArrayList<String> i2 = new ArrayList<>();
                i2.add("INSERT");
                i2.add("notecards");
                i2.add("2");

                ArrayList<String> v2 = new ArrayList<>();
                v2.add("VIEW");
                v2.add("-");
                v2.add("-");

                ArrayList<String> i3 = new ArrayList<>();
                i3.add("INSERT");
                i3.add("notebook");
                i3.add("9");

                ArrayList<String> i4 = new ArrayList<>();
                i4.add("INSERT");
                i4.add("backpack");
                i4.add("10");

                ArrayList<String> i5 = new ArrayList<>();
                i5.add("INSERT");
                i5.add("pens");
                i5.add("6");

                ArrayList<String> i6 = new ArrayList<>();
                i6.add("INSERT");
                i6.add("pencils");
                i6.add("5");

                ArrayList<String> v3 = new ArrayList<>();
                v3.add("VIEW");
                v3.add("-");
                v3.add("-");

                items2.add(i1);
                items2.add(v1);
                items2.add(i2);
                items2.add(v2);
                items2.add(i3);
                items2.add(i4);
                items2.add(i5);
                items2.add(i6);
                items2.add(v3);

                System.out.println("entries2 = " + items2);
                List<String> result2 = getItems(items2);
                System.out.println("items customer visited 2 = " + result2);
                //expected output
                /*
                entries = [[INSERT, fries, 4], [INSERT, soda, 2], [VIEW, -, -], [VIEW, -, -], [INSERT, hamburger, 5], [VIEW, -, -], [INSERT, nuggets, 4], [INSERT, cookies, 1], [VIEW, -, -], [VIEW, -, -]]
items customer viewed1 = [soda, fries, hamburger, nuggets, hamburger]
entries2 = [[INSERT, ruler, 4], [VIEW, -, -], [INSERT, notecards, 2], [VIEW, -, -], [INSERT, notebook, 9], [INSERT, backpack, 10], [INSERT, pens, 6], [INSERT, pencils, 5], [VIEW, -, -]]
items customer visited 2 = [ruler, ruler, pencils]
                 */
            }


}

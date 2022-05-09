package topinterviewquestions;

public class Problem_1472_DesignBrowserHistory_G {
    //解题思路
    //1.创建一个class Node，可以访问前一个节点以及后一个节点以及节点赋值(String)。
    //2.创建类变量page以及一个辅助节点temp指向page头节点。
    //3.用传入的homepage字符串创立头节点。
    //4.每访问一个节点url，创建一个新的节点newPage，newPage下一个节点默认为null，上一个节点为page节点；再将page节点下一个指向创建newPage节点，将page节点移动到page.next。
    //5.当移动步数不为0时，page节点向前(pre)或者向后(next)移动。每次移动前判断前一个是否为最初的节点(temp)或者尾节点(null)，否则节点进行移动直到steps为0。
    //
    //作者：Relll-1037
    //链接：https://leetcode-cn.com/problems/design-browser-history/solution/javajian-dan-shuang-xiang-lian-biao-by-6-sm5a/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class BrowserHistory {
        Node page;
        Node temp = page;
        public BrowserHistory(String homepage) {
            page = new Node(homepage);
        }

        public void visit(String url) {
            Node newPage = new Node(url);
            newPage.next = null;
            page.next = newPage;
            newPage.pre = page;
            page = page.next;
        }

        public String back(int steps) {
            while (steps != 0) {
                if (page.pre == temp) {
                    break;
                }else {
                    page = page.pre;
                    steps--;
                }
            }
            return page.str;
        }

        public String forward(int steps) {
            while (steps != 0) {
                if (page.next == null) {
                    break;
                }else {
                    page = page.next;
                    steps--;
                }
            }
            return page.str;
        }
    }

    class Node {
        String str;
        Node pre;
        Node next;
        Node(String str) {this.str = str;}
    }

}

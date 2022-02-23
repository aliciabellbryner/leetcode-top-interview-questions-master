package topinterviewquestions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/*
A binary expression tree is a kind of binary tree used to represent arithmetic expressions. Each node of a binary expression tree has either zero or two children. Leaf nodes (nodes with 0 children) correspond to operands (numbers), and internal nodes (nodes with 2 children) correspond to the operators '+' (addition), '-' (subtraction), '*' (multiplication), and '/' (division).

For each internal node with operator o, the infix expression that it represents is (A o B), where A is the expression the left subtree represents and B is the expression the right subtree represents.

You are given a string s, an infix expression containing operands, the operators described above, and parentheses '(' and ')'.

Return any valid binary expression tree, which its in-order traversal reproduces s after omitting the parenthesis from it (see examples below).

Please note that order of operations applies in s. That is, expressions in parentheses are evaluated first, and multiplication and division happen before addition and subtraction.

Operands must also appear in the same order in both s and the in-order traversal of the tree.



Example 1:


Input: s = "3*4-2*5"
Output: [-,*,*,3,4,2,5]
Explanation: The tree above is the only valid tree whose inorder traversal produces s.
Example 2:


Input: s = "2-3/(5*2)+1"
Output: [+,-,1,2,/,null,null,null,null,3,*,null,null,5,2]
Explanation: The inorder traversal of the tree above is 2-3/5*2+1 which is the same as s without the parenthesis. The tree also produces the correct result and its operands are in the same order as they appear in s.
The tree below is also a valid binary expression tree with the same inorder traversal as s, but it not a valid answer because it does not evaluate to the same value.

The third tree below is also not valid. Although it produces the same result and is equivalent to the above trees, its inorder traversal does not produce s and its operands are not in the same order as s.

Example 3:

Input: s = "1+2+3+4+5"
Output: [+,+,5,+,4,null,null,+,3,null,null,1,2]
Explanation: The tree [+,+,5,+,+,null,null,1,2,3,4] is also one of many other valid trees.


Constraints:

1 <= s.length <= 1000
s consists of digits and the characters '+', '-', '*', and '/'.
Operands in s are exactly 1 digit.
It is guaranteed that s is a valid expression.
 */
public class Problem_1597_BuildBinaryExpressionTreeFromInfixExpression {
    //diss
    class Solution {
        public Node expTree(String s) {
            return parseExpression(s.toCharArray(), new int[1]);
        }
        public Node parseExpression(char[] arr, int[] idx){
            Node lhs = parseTerm(arr, idx);
            while(idx[0]<arr.length && (arr[idx[0]] == '+' || arr[idx[0]] == '-')){
                char op = arr[idx[0]++];
                Node rhs = parseTerm(arr, idx);
                lhs = new Node(op, lhs, rhs);
            }
            return lhs;
        }
        public Node parseTerm(char[] arr, int[] idx){
            Node lhs = parseFactor(arr, idx);
            while(idx[0]<arr.length && (arr[idx[0]] == '*' || arr[idx[0]] == '/')){
                char op = arr[idx[0]++];
                Node rhs = parseFactor(arr, idx);
                lhs = new Node(op, lhs, rhs);
            }
            return lhs;
        }
        public Node parseFactor(char[] arr, int[] idx){
            if(arr[idx[0]] == '('){
                idx[0]++; // pass '('
                Node n = parseExpression(arr, idx);
                idx[0]++; // pass ')'
                return n;
            }
            return new Node(arr[idx[0]++], null, null);

        }
    }


    //Almost identical to 772. Basic Calculator III
    /*
    772. Basic Calculator III

class Solution {
  public int calculate(final String s) {
    Stack<Integer> nums = new Stack<>(); // stores nums
    Stack<Character> ops = new Stack<>(); // stores operators and parentheses

    for (int i = 0; i < s.length(); ++i) {
      final char c = s.charAt(i);
      if (c == ' ')
        continue;
      if (Character.isDigit(c)) {
        int num = c - '0';
        while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
          num = num * 10 + (s.charAt(i + 1) - '0');
          ++i;
        }
        nums.push(num);
      } else if (c == '(') {
        ops.push(c);
      } else if (c == ')') {
        while (ops.peek() != '(')
          nums.push(calculate(ops.pop(), nums.pop(), nums.pop()));
        ops.pop(); // remove '('
      } else { // c == '+' || c == '-' || c == '*' || c == '/'
        while (!ops.isEmpty() && compare(ops.peek(), c))
          nums.push(calculate(ops.pop(), nums.pop(), nums.pop()));
        ops.push(c);
      }
    }
    while (!ops.isEmpty())
      nums.push(calculate(ops.pop(), nums.pop(), nums.pop()));

    return nums.peek();
  }

  private int calculate(char op, int b, int a) {
    switch (op) {
      case '+':
        return a + b;
      case '-':
        return a - b;
      case '*':
        return a * b;
      case '/':
        return a / b;
    }
    throw new IllegalArgumentException();
  }

  // return true if op1 is a operator and priority(op1) >= priority(op2)
  boolean compare(char op1, char op2) {
    if (op1 == '(' || op1 == ')')
      return false;
    return op1 == '*' || op1 == '/' || op2 == '+' || op2 == '-';
  }
}
1597. Build Binary Expression Tree From Infix Expression

class Solution {
  public Node expTree(String s) {
    Stack<Node> nodes = new Stack<>(); // stores nodes (new Node(ch))
    Stack<Character> ops = new Stack<>(); // stores operators and parentheses

    for (final char c : s.toCharArray())
      if (Character.isDigit(c)) {
        nodes.push(new Node(c));
      } else if (c == '(') {
        ops.push(c);
      } else if (c == ')') {
        while (ops.peek() != '(')
          nodes.push(buildNode(ops.pop(), nodes.pop(), nodes.pop()));
        ops.pop(); // remove '('
      } else { // c == '+' || c == '-' || c == '*' || c == '/'
        while (!ops.isEmpty() && compare(ops.peek(), c))
          nodes.push(buildNode(ops.pop(), nodes.pop(), nodes.pop()));
        ops.push(c);
      }

    while (!ops.isEmpty())
      nodes.push(buildNode(ops.pop(), nodes.pop(), nodes.pop()));

    return nodes.peek();
  }

  private Node buildNode(char op, Node right, Node left) {
    return new Node(op, left, right);
  }

  // return true if op1 is a operator and priority(op1) >= priority(op2)
  boolean compare(char op1, char op2) {
    if (op1 == '(' || op1 == ')')
      return false;
    return op1 == '*' || op1 == '/' || op2 == '+' || op2 == '-';
  }
}
     */




    class Solution2 {
        public Node expTree(String s) {
            s = '(' + s + ')';
            Deque<Node> nodes = new LinkedList<>();
            Deque<Character> ops = new LinkedList<>();
            Map<Character, Integer> priority = Map.of('+', 0, '-', 0, '*', 1, '/', 1);

            for (char c : s.toCharArray())
                if (Character.isDigit(c)) {
                    nodes.push(new Node(c));
                } else if (c == '(') {
                    ops.push(c);
                } else if (c == ')') {
                    while (ops.peek() != '(')
                        nodes.push(buildNode(ops.pop(), nodes.pop(), nodes.pop()));
                    ops.pop();  // remove '('
                } else {        // c == '+' || c == '-' || c == '*' || c == '/'
                    while (ops.peek() != '(' && priority.get(ops.peek()) >= priority.get(c))
                        nodes.push(buildNode(ops.pop(), nodes.pop(), nodes.pop()));
                    ops.push(c);
                }

            return nodes.peek();
        }

        private Node buildNode(char op, Node right, Node left) {
            return new Node(op, left, right);
        }
    }


    class Solution3 {
        public Node expTree(String s) {
            if(s.isEmpty()) return null;
            Deque<Node> deque = new ArrayDeque<>();
            for(int i=0;i<s.length();i++){
                if(s.charAt(i) == '('){
                    int j = i+1;
                    for(int bal=1; j<s.length(); j++){
                        if(s.charAt(j) =='(') bal++;
                        else if(s.charAt(j) ==')') bal--;
                        if(bal == 0) break;
                    }
                    Node tmp = expTree(s.substring(i+1, j));
                    if(tmp !=null) deque.add(tmp);
                    i = j;
                } else {
                    deque.add(new Node(s.charAt(i)));
                }
            }
            return helper(helper(deque, '*', '/'), '+', '-').poll();
        }
        private Deque<Node> helper(Deque<Node> deque, char op1, char op2){
            if(deque.isEmpty()) return null;
            Deque<Node> tmp = new ArrayDeque<>();
            tmp.offer(deque.poll());
            while(!deque.isEmpty()){
                Node oper = deque.poll();
                if(oper.left == null && (oper.val == op1 || oper.val == op2)){
                    oper.left = tmp.pollLast();
                    oper.right = deque.poll();
                }
                tmp.offer(oper);
            }
            return tmp;
        }
    }


    class Node {
        char val;
        Node left;
        Node right;
        Node() {this.val = ' ';}
        Node(char val) { this.val = val; }
        Node(char val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

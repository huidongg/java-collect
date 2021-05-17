package cn.hd.datastruct.tree;

import java.util.Stack;

public class BuildTree {

    public static void main(String[] args) {
        Node root = build();
        perOrder(root);
    }


    static class Node {
        public char val;
        public Node left;
        public Node right;

        public Node(char val) {
            this.val = val;
        }
    }


    static Node build() {
        Node A = new Node('A');
        Node B = new Node('B');
        Node C = new Node('C');
        Node D = new Node('D');
        Node E = new Node('E');
        Node F = new Node('F');
        Node G = new Node('G');
        Node H = new Node('H');
        Node I = new Node('I');

        A.left = B;
        A.right = C;
        B.left = D;
        B.right = E;
        D.left = G;
        D.right = H;
        C.left = F;
        F.right = I;

        return A;
    }

    //先序遍历，不用递归，
    //用栈来储存对象
    public static void perOrder(Node root) {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            Node A = stack.pop();
            System.out.print(A.val + " ");
            if (A.right != null) {
                //将右子树先放入栈
                //那木最后访问右子树
                stack.push(A.right);
            }
            if (A.left != null) {
                stack.push(A.left);
            }
        }
    }

    //中序遍历
    public static void inOrder(Node root) {

        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        Node cur = root;

        while (true) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            if (stack.empty()) {
                break;
            }
            Node top = stack.pop();
            System.out.print(top.val + " ");

            cur = top.right;
        }

    }

    //后序遍历
    public static void postOrder(Node root) {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();

        Node cur = root;
        Node prev = null;//来记录上一个被访问过的节点
        while (true) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            if (stack.isEmpty()) {
                break;
            }

            Node top = stack.peek();

            if (top.right == null || top.right == prev) {
                System.out.print(top.val + " ");
                stack.pop();
                prev = top;//保存被访问的节点
            } else {
                cur = top.right;
            }

        }

    }
}
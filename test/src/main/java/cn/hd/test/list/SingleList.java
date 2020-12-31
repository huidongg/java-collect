package cn.hd.test.list;

public class SingleList {
    public static void main(String[] args) {
        Node node = new Node<Integer>(0, null);
        Node cur = node;
        for (int i = 1; i < 4; i++) {
            cur = cur.add(i);
        }
        while (node != null) {
            System.out.println(node.t);
            node = node.next;
        }
        cur = node;
        while (cur != null) {
            System.out.println(cur.t);
            cur = cur.next;
        }
    }


    static class Node<T> {
        private T t;
        private Node next;
        public Node(T t, Node next) {
            this.t = t;
            this.next = next;
        }

        public <T> Node add(T t) {
            Node cnode = new Node(t, null);
            this.next = cnode;
            return cnode;
        }
    }
}

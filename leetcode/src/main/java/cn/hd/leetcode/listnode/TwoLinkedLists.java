package cn.hd.leetcode.listnode;

import java.util.ArrayList;
import java.util.List;

public class TwoLinkedLists<T> {
    public static void main(String[] args) {


    }

    public int getSize(){
        return this.size;
    }

    public void addFirst(T t) {
        Node node = new Node(t);
        node.next = this.head;
        this.head = node;
        this.size++;
    }

    public void addLast(T t) {
        this.add(t, size);
    }

    public void add(T t, int index) {
        if (index <0 || index > size){
            throw new IllegalArgumentException("index is error");
        }
        if (index == 0) {
            this.addFirst(t);
            return;
        }
        Node preNode = this.head;
        // 找到要插入节点的前一个节点
        for(int i = 0; i < index-1; i++){
            preNode = preNode.next;
        }
        Node node = new Node(t);
        // 要插入的节点的下一个节点指向preNode节点的下一个节点
        node.next = preNode.next;
        // preNode的下一个节点指向要插入节点node
        preNode.next = node;
        this.size++;
    }

    public TwoLinkedLists() {
        this.head = null;
        this.size = 0;
    }

    private Node<T> head;
    private int size;

    public static class Node<T> {
        private T t;
        Node next;
        public Node(T t, Node next) {
            this.t = t;
            this.next = next;
        }
        public Node(T t) {
            this(t, null);
        }
    }
}

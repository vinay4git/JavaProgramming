package interview2025;

public class CommonNodeOfTwoGivenLinkedLists {
    public static void main(String[] args) {
        // L1 --> 1, 2, 3, 4, 5 | 5
        // L2 -->    8, 3, 4, 5 | 4    3
        // 3
/*
Find the common node of two given linked Lists
* */

        Node head1 = null, head2 = null;
        commonPointOfTwoLinkedLists(head1, head2);

    }

    private static Node commonPointOfTwoLinkedLists(Node head1, Node head2) {

        int sizeOfLL1 = sizeOfLL(head1);
        int sizeOfLL2 = sizeOfLL(head2);

        if (sizeOfLL1 > sizeOfLL2) {
            int diff = sizeOfLL1 - sizeOfLL2;
            for (int i = 0; i < diff; i++) {
                head1 = head1.getNext();
            }

        } else {
            int diff = sizeOfLL2 - sizeOfLL1;
            for (int i = 0; i < diff; i++) {
                head2 = head2.getNext();
            }
        }

        while (head1 != head2) {
            head1 = head1.getNext();
            head2 = head2.getNext();
        }

        return head1;
    }

    private static int sizeOfLL(Node head) {
        int size = 0;

        while ( head != null) {
            size++;
            head = head.getNext();
        }
        return size;
    }


}

class Node {
    int val;
    Node next;


    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
public class DoublyLinkedList {
    private ListNode2 head;
    private ListNode2 tail;
    private int size;

    public DoublyLinkedList(){
        head = new ListNode2(null, null, null);
        tail = new ListNode2(null, null, null);
        size = 0;
    }

    /**
     * Prints out the elements in the list from the first to the last (front to back) and then from the last to the first
     * (back to front). This is useful to test whether the list nodes are connected correctly with next and prev references.
     */
    public void print() {
        // no elements to print for empty list
        if (head == null) {
            System.out.println("list empty.");
            return;
        }

        // follow next references to list elements from the front to the back of the list
        System.out.print("front to back: ");
        ListNode2 node = head;
        System.out.print(node.element + " ");
        while (node.next != null) {
            node = node.next;
            System.out.print(node.element + " ");
        }

        // follow prev references to list elements from the back to the front of the list
        System.out.print("-- and back to front: ");
        while (node != null) {
            System.out.print(node.element + " ");
            node = node.prev;
        }
        System.out.println();
    }

    public void addFirst(Object o) {   // your code here
        if (size == 0) {
            head = new ListNode2(o, null, null);
            tail = head;
        } else{
            ListNode2 temp = new ListNode2(o, null,null);
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
        size++;
    }

    public void insert(Object o, int n){
        if(n <= size && n >= 0){
            if (n==0) {
                addFirst(o);
                tail = head;
            }
            else if (n == size){
                ListNode2 temp = tail;
                tail = new ListNode2(o, temp, null);
                temp.next = tail;
                size++;
            }
            else{
                ListNode2 current = head;
                ListNode2 node = new ListNode2(o, null, null);

                for(int j = 0; j < n-1; j ++){
                    current = current.next;
                }

                node.next = current.next;
                node.prev = current;
                current.next.prev = node;
                current.next = node;
                size++;
            }
        }
    }

    public void remove(int i) {
        if(size == 1){
            head = null;
            tail = null;
            size--;
        }
        else if (i == 0) { // special case
            head = head.next;
            head.prev = null;
            size--;
        }
        else if (i == size - 1){
            ListNode2 current = head;
            while(current.next.next != null) {
                current = current.next;
            }
            tail = current;
            tail.next = null;
            size--;
        }
        else{
            ListNode2 current = head;

            if (head != null) {
                for (int j = 0; j < i; j++) {
                    if (current.next == null)
                        return;

                    current = current.next;
                }
                current.next.prev = current.prev;
                current.prev.next = current.next;
                size--;
            }
        }
    }

    public Object get(int i) {   // your code here
        if (i>=0 && i<=size) {

            ListNode2 current = head;

            for (int j = 0; j < i; j++) {
                current = current.next;
            }
            return current.element;
        }
        return null;
    }

    class ListNode2 {
        Object element;
        ListNode2 prev;
        ListNode2 next;

        ListNode2(Object e, ListNode2 p, ListNode2 n) {
            element = e;
            prev = p;
            next = n;
        }

    }
}

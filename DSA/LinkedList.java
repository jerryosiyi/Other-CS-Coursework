public class LinkedList {

    private ListNode head;
    private ListNode tail;
    private int size;

    public LinkedList(){
        head = new ListNode(null, null);
        tail = new ListNode(null, null);
        size = 0;
    }

    public void addFirst(Object o){
        head = new ListNode(o, head);
        size++;
    }

    public void add(Object o){
        ListNode temp;

        if(size == 0) {
            addFirst(o);
            tail = head;
        }
        else{
            temp = tail;
            tail = new ListNode(o, null);
            temp.next = tail;
            size++;
        }
    }

    public void insert(Object o, int n){
        if(n <= size && n >= 0){
            if (n==0) {
                addFirst(o);
                tail = head;
                return;
            }

            ListNode current = head;

            for(int j = 0; j < n-1; j ++){
                current = current.next;
            }
            current.next = new ListNode(o, current.next);
            tail = tail.next;
            size++;
        }
    }

    public Object get(int i) {
        if (i>=0 && i<=size) {

            ListNode current = head;

            for (int j = 0; j < i; j++) {
                current = current.next;
            }
            return current.data;
        }
        return null;
    }

    public void remove(int i) {
        if(size == 1){
            head = null;
            tail = null;
            size--;
        }
        else if (i == 0) { // special case
            head = head.next;
            size--;
        }
        else{
            ListNode current = head;
            if (i == size - 1){
                removeTail();
                return;
            }
            if (head != null) {
                for (int j = 0; j < i - 1; j++) {
                    if (current.next == null)
                        return;

                    current = current.next;
                }
                current.next = current.next.next;
                size--;
            }
        }
    }

    private void removeTail() {
        ListNode current = head;
        while(current.next.next != null) {
            current = current.next;
        }
        tail = current;
        size--;
    }

    class ListNode{
        Object data;
        ListNode next;

        ListNode(Object data, ListNode n){
            this.data = data;
            next = n;
        }
        
    }
}

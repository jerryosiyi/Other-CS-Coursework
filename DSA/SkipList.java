public class SkipList {
    private SkipListNode[] head;
    private int n = 0; // list size

    public SkipList() { // TODO implement this
        head = new SkipListNode[5];
    }

    public void createTestList() { // TODO implement this
        SkipListNode anneNode = new SkipListNode("Anne", 3);
        SkipListNode benNode = new SkipListNode("Ben", 1);
        SkipListNode charlieNode = new SkipListNode("Charlie", 2);
        SkipListNode donNode = new SkipListNode("Don", 1);
        SkipListNode ernieNode = new SkipListNode("Ernie", 3);

        for(int i = 0; i < 3; i++){
            head[i] = anneNode;
        }
        anneNode.next[0] = benNode;
        benNode.next[0] = charlieNode;
        charlieNode.next[0] = donNode;
        donNode.next[0] = ernieNode;

        anneNode.next[1] = charlieNode;
        charlieNode.next[1] = ernieNode;

        anneNode.next[2] = ernieNode;
    }

    public void print() { // TODO implement this
        int level = 4;
        while(level>= 0){
            SkipListNode current = head[level];

            while (current != null){
                if(current.next[level] != null)
                    System.out.print(current.element + ",");
                else
                    System.out.print(current.element + "\n");

                current = current.next[level];
            }
            level--;
        }
    }

    public boolean inList(String s) { // TODO implement this
        int level = 4;
        SkipListNode current = head[level];

        while (level>=0){
            if(current == null){
                level--;
                current = head[level];
            }
            else {
                String element = current.next[level].element;
                if(current.next[level] == null){
                    level--;
                }
                else if (element.compareTo(s) < 0){
                    current = current.next[level];
                }
                else if (element.compareTo(s) > 0){
                    level--;
                }
                else return element.compareTo(s) == 0;
            }
        }
        return false;
    }

    public void insert(String s) { // TODO implement this
        SkipListNode input = new SkipListNode(s);
        int size = input.next.length;
        int x = size - 1;
        System.out.println(size);

        int level = 4;
        SkipListNode current = head[level];

        if(head[0].element.compareTo(s) > 0){
            for(int i = 0; i < size; i++){
                input.next[i] = head[i];
                head[i] = input;
            }
        }
        else{
            while (level>=0){
                if(current == null){
                    if(x == level){
                        head[level] = input;
                        x--;
                    }
                    level--;
                    current = head[level];
                }
                else {
                    if(current.next[level] == null && current.element.compareTo(s) < 0){
                        if(x == level){
                            current.next[level] = input;
                            x--;
                        }
                        level--;
                    }
                    else if(current.next[level] == null && current.element.compareTo(s) > 0){
                        if(x == level){
                            input.next[level] = current;
                            x--;
                        }
                        level--;
                    }
                    else if(current.next[level] == null && current.element.compareTo(s) == 0){
                        if(x == level){
                            current.next[level] = input;
                            x--;
                        }
                        level--;
                    }
                    else if(current.next[level]  != null){
                        if (current.next[level].element.compareTo(s) < 0){
                            current = current.next[level];
                        }
                        else if (current.next[level].element.compareTo(s) > 0){
                            if(x == level){
                                input.next[level] = current.next[level];
                                current.next[level] = input;
                                x--;
                            }
                            level--;
                        }
                        else{
                            if(x == level){
                                input.next[level] = current.next[level];
                                current.next[level] = input;
                                x--;
                            }
                            level--;
                        }
                    }
                }
            }
        }
    }
}

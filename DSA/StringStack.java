/** A stack abstract data type that contains Strings. */
public class StringStack {
    private String[] stringStack;

    /**
     * Constructor for creating a new StringStack with a certain capacity.
     * @param capacity the maximum number of strings the stack can hold
     */
    public StringStack(int capacity) {
        stringStack = new String[capacity];
    }

    /**
     * Puts the given String on top of the stack (if there is enough space).
     * @param s the String to add to the top of the stack
     * @return false if there was not enough space in the stack to add the string;
     *         otherwise true
     */
    public boolean push(String s) {
        boolean hasSpace = true;
        if(stringStack.length == 0) {
            return false;
        }
        for(int i=0; i < stringStack.length; i++){
            if(stringStack[i] == null){
                stringStack[i] = s;
                hasSpace = true;
                break;
            }
            hasSpace = false;
        }
        return hasSpace;
    }

    /**
     * Removes the String on top of the stack from the stack and returns it.
     * @return the String on top of the stack, or null if the stack is empty.
     */
    public String pop() {
        String stringPop;
        int a = 0;
        if(stringStack.length == 0) {
            return null;
        }
        for(int i=0; i < stringStack.length; i++){
            if(stringStack[i] == null){
                break;
            }
            a = i;
        }
        stringPop = stringStack[a];
        stringStack[a] = null;
        return stringPop;
    }

    /**
     * Returns the number of Strings in the stack.
     * @return the number of Strings in the stack
     */
    public int count() {
        int count = 0;
        if(stringStack.length == 0) {
            return 0;
        }
        for (String s : stringStack) {
            if (s == null) {
                break;
            }
            count++;
        }
        return count;
    }
}


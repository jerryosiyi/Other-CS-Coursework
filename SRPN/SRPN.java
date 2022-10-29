import java.io.*;
import java.util.*;

public class SRPN {
    //This is the stack used to store my numbers and results.
    private static Stack<Integer> numberStack;
    //This stack was created in order to help d print of the number stack the right way.
    private Stack<Integer> placeboStack;
    //This queue is to store the operators for when the equation is written on one line.
    private Queue<Character> operatorQueue = new LinkedList<>();
    //This stack is to make sure the numbers when the equation is on one line enters numberStack the right way.
    private Stack<Integer> placebo2Stack;
    //This variable allows me to access command from functions that i don't want to pass command through.
    static String j = "";
    //This variable hold the results before it is pushed into numberStack.
    int result = 0;
    //This variable hold the position of the number we want r to print.
    int rPosition = 0;
    //This array contains all the mathematical operators accepted by srpn.
    char operatorArray[] = {'+', '-', '*', '/', '%', '%', '^'};
    //This array contains all the numbers that r is allowed to print.
    int[] randomR = new int[]{1804289383, 846930886, 1681692777, 1714636915, 1957747793,
                              424238335, 719885386, 1649760492, 596516649, 1189641421,
                              1025202362, 1350490027, 783368690, 1102520059, 2044897763,
                              1967513926, 1365180540, 1540383426, 304089172, 1303455736,
                              35005211, 521595368};
    public SRPN(){
        this.numberStack = new Stack<>();
        this.placeboStack = new Stack<>();
        this.placebo2Stack = new Stack<>();
    }
    /* This function takes in two integer numbers tries to calculate the sum of these
     * numbers. An arithmetic exception occurs if the exact sum can't be calculated as
     * a consequence of an overflow. */
    public boolean overFlow(int x, int y){
        try{
            Math.addExact(x, y);
            return false;
        }catch (ArithmeticException nfe) {
            /* When an overflow occurs, result is set to the highest number that can be
             * represented by 32 bits. */
            result = Integer.MAX_VALUE;//2147483647
            return true;
        }
    }
    /* This function checks to see if an underflow has occurred by trying to find the subtraction
     * between x and y. If the difference can not be calculated an arithmetic exception occurs and
     * result is set to the smallest number that can be represented by 32 bits. */
    public boolean underFlow(int x, int y){
        try{
            Math.subtractExact(x, y);
            return false;
        }catch (ArithmeticException nfe){
            result = Integer.MIN_VALUE;//-2147483648
            return true;
        }
    }
    /* This function try to calculate the exact product of the two integers. If it can not be
     * calculated the function uses if statements to determine whether an overflow or an
     * underflow has occurred. */
    public boolean multSaturation(int x, int y){
        try {
            Math.multiplyExact(x, y);
            return false;
        } catch (ArithmeticException nfe) {
            if (x < 0 | y < 0) {//negative * positive = negative : Underflow
                result = Integer.MIN_VALUE;
            } else if (x < 0 && y < 0) {//negative * negative = positive : Overflow
                result = Integer.MAX_VALUE;
            } else {//positive * positive = positive : Overflow
                result = Integer.MAX_VALUE;
            }
            return true;
        }
    }
    /* This function pops out all the values currently in numberStack and pushes them
     * into placeboStack. The values are then popped out of placeboStack and are printed
     * and pushed back into numberStack. */
    public void printPlaceboStack(){
        //When numberStack is empty srpn returns the smallest number that can be represented by 32 bits.
        if (numberStack.empty()){
            System.out.println(Integer.MIN_VALUE);//-2147483648
        }
        while (!numberStack.empty()){
            int a = numberStack.pop();
            placeboStack.push(a);
        }
        while (!placeboStack.empty()){
            int b = placeboStack.pop();
            System.out.println(b);
            numberStack.push(b);
        }
        return;
    }
    /* This performs the requested mathematical operation on a and b based on the given
     * operator. it pop two numbers from numberStack and if the operation is successful the
     * result is pushed into numberStack else, the numbers are pushed back into numberStack. */
    public void processOperation(char operator) throws RuntimeException{
        int a = numberStack.pop();
        int b = numberStack.pop();
        /* For single line operator my use of queues to store the operator meant that 5-2 gave -3.
         * In other to fix this i swapped the values of a and b using the if statement below. */
        if(j.length() > 1){
            int temp = a;
            a = b;
            b = temp;
        }
        switch (operator){
            case '+'://Addition
                result = a + b;
                overFlow(a, b);
                numberStack.push(result);
                break;
            case '-'://Subtraction
                result = b - a;
                underFlow(b, a);
                numberStack.push(result);
                break;
            case '*'://Multiplication
                result = a * b;
                multSaturation(b, a);
                numberStack.push(result);
                break;
            case '/'://Division
                /* This if statement check to see a is 0. It prints out an error message because
                 * a number can not we divided by 0. */
                if (a == 0) {
                    System.out.println("Divide by 0.");
                    numberStack.push(b);
                    numberStack.push(a);
                    return;
                }
                result = b / a;
                numberStack.push(result);
                break;
            case '^'://Power
                /* Because srpn calculator can not calculate negative power, if statement checks
                 * to see if a less than zero. if it is an error message is printed out. */
                if (a < 0) {
                    System.out.println("Negative power.");
                    numberStack.push(b);
                    numberStack.push(a);
                    return;
                }
                result = (int) Math.pow(b, a);
                numberStack.push(result);
                break;
            case '%'://Modulus Division
                result = b % a;
                numberStack.push(result);
                break;
            default:
                //This is for when the operator doesn't pass any of the above cases.
                System.out.println("Unrecognised operator or operand \"" + operator + "\".");
                break;
        }
    }
    //This function checks to see if operator is valid or invalid.
    public void checkOperator(char operator){
        int flag = 0;
        if (operator == 'd'){
            printPlaceboStack();
            return;
        }
        if (operator == 'r'){
            if (rPosition == 22){
                rPosition = 0;
            }
            //This if statement makes sure that there is space in numberStack before push more values in.
            if (numberStack.size() < 23){
                numberStack.push(randomR[rPosition]);
                rPosition++;
                return;
            }
            //If there is no space an error message is printed out.
            else{
                System.out.println("Stack overflow.");
            }
            return;
        }
        if (operator == '#'){//This statement makes sure that no error is received when # is entered.
            return;
        }
        if (operator == '='){
            System.out.println(numberStack.peek());
            return;
        }
        //This statement checks to see if there is enough elements to perform the operation.
        if (numberStack.size() < 2){
            for (int i = 0; i < operatorArray.length; i++){
                if (operator == operatorArray[i]){
                    System.out.println("Stack underflow.");
                    return;
                }
                else{
                    /* If the error message is returned now it would print the message
                     * for all the checks the operator fails which we do not want. */
                    flag = 1;
                }
            }
            if (flag == 1){
                System.out.println("Unrecognised operator or operand \"" + operator + "\".");
                return;
            }
        }
        processOperation(operator);
    }
    /* This function is used when the math expression is inputted in a single line. It splits the
     * string into numbers and operators. */
    public void singlelineOperation(String command){
        String parts[] = command.split("(?<=[\\s-+*/%^rd=])|(?=[-+*/%^rd=])");
        for (int i = 0; i < parts.length; i++){
            String x = parts[i].trim();//This is to remove space in front and behind the broken parts.
            try{
                int number = Integer.valueOf(x);
                if (numberStack.size() > 22){
                    System.out.println("Stack overflow.");
                    return;
                }
                placebo2Stack.push(number);
            }catch (NumberFormatException e){
                if (parts[i].length() == 1){
                    char operator = parts[i].charAt(0);
                    if (operator != ' '){//This makes sure that spaces are not ran as operators.
                        operatorQueue.add(operator);
                    }
                }
            }
        }
        //This while loop pos the numbers from placebo2Stack to numberStack
        while (!placebo2Stack.empty()){
            int b = placebo2Stack.pop();
            numberStack.push(b);
        }
        //This while loop gets all the operators in operatorQueue and passes them through checkOperator.
        while (!operatorQueue.isEmpty()){
            char operator = operatorQueue.remove();
            checkOperator(operator);
        }
    }
    /*This function handles when there is a # in the middle of the command*/
    public void processComment(String command){
        command = command.trim();
        command = command.replace(" ","");//This removes the spaces from command.
        int z = 0;
        //This while loop passes every character of command through checkOperator.
        while (z != command.length()){
            char operator = command.charAt(z);
            checkOperator(operator);
            z++;
        }
        return;
    }
    /*This function takes in the string command and try to compute it. */
    public void processCommand(String command){
        try{
            int number = Integer.valueOf(command);//Converts the string to it's integer value.
            if (numberStack.size() > 22){//Check to see if there is space in the stack.
                System.out.println("Stack overflow.");
                return;
            }
            numberStack.push(number);
        }catch (NumberFormatException ignored){//If the string cant be converted...
            //If the length of the string is 1, then the string is an operator.
            if (command.length() == 1){
                char operator = command.charAt(0);
                checkOperator(operator);
            }
            else{
                if (command.charAt(0) == '#'){//Checks to see if the line is a comment.
                    return;
                }
                try{//Check to see if there is a # in the middle of command.
                    command.indexOf('#');
                    //If there is a #, it splits the strings and runs the parts after the #
                    String [] parts = command.split("(?<=[#])|(?=[#])");
                    processComment(parts[2]);
                }catch (ArrayIndexOutOfBoundsException e){
                    //If a # can't be found...
                    singlelineOperation(command);
                }
            }
        }
    }
    public static void main(String[] args){
        SRPN sprn = new SRPN();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try{
            //Keep on accepting input from the command-line
            while (true){
                String command = reader.readLine();
                j = command;
                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if (command == null){
                    //Exit code 0 for a graceful exit
                    System.exit(0);
                }
                sprn.processCommand(command);
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
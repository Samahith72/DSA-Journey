import java.util.Stack;

class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for(String token : tokens){
            if(token.equals( "+")){
                int n2 = stack.pop();
                int n1  = stack.pop();
                stack.push(n1+n2);
            }else if(token.equals( "-")){
                int n2 = stack.pop();
                int n1  = stack.pop();
                stack.push(n1-n2);
            }else if(token.equals( "*")){
                int n2 = stack.pop();
                int n1  = stack.pop();
                stack.push(n1*n2);
            }else if(token.equals( "/")){
                int n2 = stack.pop();
                int n1  = stack.pop();
                stack.push(n1/n2);
            }else{
                int value = Integer.parseInt(token);
                stack.push(value);
            }
        }
        return stack.peek();
    }
}
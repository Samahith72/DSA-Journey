import java.util.Stack;

class Solution {
    public int minOperations(String[] logs) {

        Stack<String> stack = new Stack<>();

        for(String log: logs){
            if(stack.isEmpty() && log.equals("../")){
                continue;
            }else if(log.equals("./")){
                continue;
            }else if(!stack.isEmpty() && log.equals( "../")){
                stack.pop();
            }else{
                stack.push(log);
            }
        }
        return stack.size();
    }
}
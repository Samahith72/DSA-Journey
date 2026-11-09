import java.util.Stack;

class Solution {
    public int longestValidParentheses(String s) {

        Stack<Integer> stack = new Stack<>();
        int answer = 0;
        stack.push(-1);

        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == '('){
                stack.push(i);
            }else{
                stack.pop();
                
                if(stack.isEmpty()){
                    stack.push(i);
                }else{
                    int length = i - stack.peek();
                    answer = Math.max(length, answer);
                }
            }
        }

        return answer;
        
    }
}
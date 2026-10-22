import java.util.Stack;

class Solution {
    public String makeGood(String s) {

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {

            if (stack.isEmpty()) {
                stack.push(c);
                continue;
            }

            char ch = stack.peek();

            if (Character.toLowerCase(ch) == Character.toLowerCase(c) && ch != c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.reverse().toString();
    }
}
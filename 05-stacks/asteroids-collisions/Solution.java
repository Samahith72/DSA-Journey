import java.util.Stack;

class Solution {
    public int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> stack = new Stack<>();

        for(int asteroid: asteroids){
            boolean isAlive = true;

            while(isAlive && !stack.isEmpty() && stack.peek() > 0 && asteroid < 0){
                if(stack.peek() < -asteroid){
                    stack.pop();
                }else if(stack.peek() == -asteroid){
                    stack.pop();
                    isAlive = false;
                }else{
                    isAlive = false;
                }
            }

            if(isAlive){
                stack.push(asteroid);
            }
        }
        
        int[] result = new int[stack.size()];

        for(int i=0;i < stack.size();i++){
            result[i] = stack.get(i);
        }

        return result;
    }
}
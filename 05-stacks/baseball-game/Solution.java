import java.util.Stack;

class Solution {
    public int calPoints(String[] operations) {

        Stack<Integer> s = new Stack<>();
        for(String ch: operations){
            if(ch.equals("C")){
                if(s.isEmpty()){
                    continue;
                }
                s.pop();
            }else if(ch.equals("D")){
                if(s.isEmpty()){
                    continue;
                }
                int num = s.peek();
                num = num * 2;
                s.push(num);
            }else if(ch.equals("+")){
                if(s.size() < 2){
                    continue;
                }
                int val2 = s.pop();
                int val1 = s.pop();
                int ans = val1 + val2;
                s.push(val1);
                s.push(val2);
                s.push(ans);
            }else{
                s.push(Integer.parseInt(ch));
            }

        }
        int ans  = 0;
        while(!s.isEmpty()){
            ans += s.pop();
        }

        return ans;
    }
}
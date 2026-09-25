class Solution {
    public int characterReplacement(String s, int k) {

        int[] frequency = new int[26];

        int left = 0;
        int maxFrequency = 0;
        int answer = 0;

        for (int right = 0; right < s.length(); right++) {

            int index = s.charAt(right) - 'A';
            frequency[index]++;

            maxFrequency = Math.max(maxFrequency, frequency[index]);

            while ((right - left + 1) - maxFrequency > k) {

                frequency[s.charAt(left) - 'A']--;
                left++;
            }

            answer = Math.max(answer, right - left + 1);
        }

        return answer;
        
    }
}
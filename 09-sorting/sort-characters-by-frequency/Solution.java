import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {
    public String frequencySort(String s) {
        HashMap<Character, Integer> frequency = new HashMap<>();

        for (char c : s.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        List<Character> chars = new ArrayList<>(frequency.keySet());

        chars.sort((a, b) -> frequency.get(b) - frequency.get(a));

        StringBuilder sb = new StringBuilder();

        for (char c : chars) {
            int count = frequency.get(c);

            for (int i = 0; i < count; i++) {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
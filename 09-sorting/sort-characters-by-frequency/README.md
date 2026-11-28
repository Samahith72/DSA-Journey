
# WIN #152 — 451. Sort Characters By Frequency

[LeetCode Problem](https://leetcode.com/problems/sort-characters-by-frequency/)

**Difficulty:** Medium  
**Topics:** Hash Table, String, Sorting, Heap

---

## Problem Statement

Given a string `s`, sort it in decreasing order based on the frequency of its characters.

The frequency of a character is the number of times it appears in the string.

Return the sorted string. If multiple answers are possible, return any of them.

### Example

```text
Input: s = "tree"
Output: "eert"
```

The characters `e` and `r` appear twice and once, respectively. The two `e` characters come first, followed by `r` and `t`. Other valid outputs include `"eetr"`.

---

## Core Idea

Count how many times each character appears, then sort the distinct characters by frequency in descending order.

The solution uses:

1. A `HashMap` to store each character's frequency.
2. A list of distinct characters, sorted using a custom comparator.
3. A `StringBuilder` to append each character the number of times it appears.

The comparator places the character with the higher frequency first.

---

## Java Solution

```java
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
```

---

## Step-by-Step Explanation

1. Create a `HashMap` named `frequency` to store each character and its count.
2. Traverse the string and increment the count for every character.
3. Copy the map's distinct characters into a list.
4. Sort the list by frequency in descending order. The comparator uses `frequency.get(b) - frequency.get(a)` so higher-frequency characters come first.
5. For each character in the sorted list, append it to the `StringBuilder` as many times as its frequency.
6. Return the resulting string.

Characters with equal frequencies may appear in either order, since the problem allows any valid ordering.

---

## Dry Run

```text
s = "tree"
```

### 1. Count character frequencies

| Character | Frequency |
|---|---:|
| `t` | 1 |
| `r` | 1 |
| `e` | 2 |

### 2. Sort distinct characters by frequency

The character `e` comes before `t` and `r` because its frequency is `2`. The relative order of `t` and `r` does not matter because both have frequency `1`.

### 3. Build the result

Append each character according to its frequency:

```text
"e" + "e" + "t" + "r" = "eetr"
```

One valid result is:

```text
"eetr"
```

---

## Edge Cases

- **Empty string:** Returns an empty string.
- **One character:** Returns that character unchanged.
- **All characters are the same:** Appends that character the same number of times as it appears.
- **All characters have equal frequency:** Any ordering is valid.
- **Spaces or punctuation:** They are counted and sorted like other characters.

---

## Complexity Analysis

Let `n` be the length of the string and `k` be the number of distinct characters.

- **Time Complexity:** `O(n + k log k)` — counting and building the result take `O(n)` time, and sorting the distinct characters takes `O(k log k)`.
- **Space Complexity:** `O(n + k)` — the frequency map and character list use `O(k)` space, while the resulting string uses `O(n)` space.

---

## Pattern Recognition

When a problem asks to order items by how often they occur, use a frequency map to count occurrences and then sort the distinct items by their counts.

A custom comparator makes it possible to sort by a computed property rather than by the characters themselves.

---

## Key Takeaway

Count each character's occurrences, sort the distinct characters from highest to lowest frequency, and append each character according to its count.

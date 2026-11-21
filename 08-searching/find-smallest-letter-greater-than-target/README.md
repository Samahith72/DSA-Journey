
# WIN #143 — 744. Find Smallest Letter Greater Than Target

[LeetCode Problem](https://leetcode.com/problems/find-smallest-letter-greater-than-target/)

**Difficulty:** Easy  
**Topics:** Array, Binary Search

---

## Problem Statement

You are given a sorted array of characters `letters` and a character `target`.

Return the smallest character in `letters` that is strictly greater than `target`.

If no character is greater than `target`, return the first character in the array. The array may contain duplicate characters.

### Examples

**Example 1**

```text
Input: letters = ["c","f","j"], target = "a"
Output: "c"
```

**Example 2**

```text
Input: letters = ["c","f","j"], target = "c"
Output: "f"
```

**Example 3**

```text
Input: letters = ["x","x","y","y"], target = "z"
Output: "x"
```

---

## Core Idea

Because `letters` is sorted, binary search can find the first character that is **strictly greater** than `target`.

- If `letters[mid] > target`, this character could be the answer. Continue searching to its left.
- Otherwise, the character is too small or equal to `target`. Search to the right.
- When the search ends, `low` points to the first character greater than `target`.
- If `low` reaches the array length, wrap around and return `letters[0]`.

This is an **upper-bound binary search**.

---

## Java Solution

```java
class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        int low = 0;
        int high = letters.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (letters[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return low == letters.length ? letters[0] : letters[low];
    }
}
```

---

## Step-by-Step Explanation

1. Initialize `low` to the first index and `high` to the last index.
2. Calculate `mid` using `low + (high - low) / 2` to avoid potential overflow.
3. If `letters[mid]` is greater than `target`, save it as a possible answer by moving `high` left.
4. If `letters[mid]` is less than or equal to `target`, move `low` right to search for a strictly greater character.
5. When `low > high`, the search is complete. `low` is the insertion position of the first character greater than `target`.
6. If that position is past the end of the array, return the first character to satisfy the wrap-around rule.

---

## Dry Run

```text
letters = ["c", "f", "j"]
target = "c"
```

| low | high | mid | letters[mid] | Action |
|---:|---:|---:|:---:|---|
| 0 | 2 | 1 | 'f' | 'f' > 'c', move high to 0 |
| 0 | 0 | 0 | 'c' | 'c' is not greater, move low to 1 |

The loop ends because `low > high`.

`low = 1`, so return `letters[1]`, which is `'f'`.

---

## Edge Cases

- **Target is smaller than every character:** returns the first character.
- **Target equals a character:** continues right to find a strictly greater character.
- **Target is greater than or equal to every character:** wraps around and returns the first character.
- **Duplicate characters:** the search skips characters equal to `target` and finds the first strictly greater one.

---

## Complexity Analysis

- **Time Complexity:** `O(log n)` — each iteration halves the search range.
- **Space Complexity:** `O(1)` — only a fixed number of variables are used.

---

## Pattern Recognition

This is an **upper-bound binary search** problem. When asked for the first element strictly greater than a target in a sorted array, move right when the middle element is less than or equal to the target, and move left when it is greater.

The final `low` index identifies the first valid position, with a wrap-around check for the circular array behavior.

---

## Key Takeaway

Binary search can find the first element greater than a target by narrowing toward the leftmost valid position. The final `low` is the answer index; if it reaches the array length, wrap around to index `0`.


# WIN #145 — 367. Valid Perfect Square

[LeetCode Problem](https://leetcode.com/problems/valid-perfect-square/)

**Difficulty:** Easy  
**Topics:** Math, Binary Search

---

## Problem Statement

Given a positive integer `num`, determine whether it is a perfect square.

A perfect square is an integer that can be expressed as the product of an integer multiplied by itself. Do not use built-in square-root functions.

### Examples

**Example 1**

```text
Input: num = 16
Output: true
Explanation: 4 * 4 = 16
```

**Example 2**

```text
Input: num = 14
Output: false
```

---

## Core Idea

Use binary search to find an integer whose square equals `num`.

For each midpoint:

- If `mid * mid` equals `num`, the number is a perfect square.
- If the square is smaller than `num`, search the right half.
- If the square is greater than `num`, search the left half.

The square is stored in a `long` to avoid integer overflow when multiplying `mid * mid`.

---

## Java Solution

```java
class Solution {
    public boolean isPerfectSquare(int num) {
        if (num < 2) {
            return true;
        }

        int low = 1;
        int high = num / 2;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            long square = (long) mid * mid;

            if (square == num) {
                return true;
            } else if (square < num) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return false;
    }
}
```

---

## Step-by-Step Explanation

1. If `num` is less than `2`, return `true`. Under the problem’s positive-integer constraints, this handles `1`.
2. Initialize the search range from `1` to `num / 2`. For any `num` greater than `1`, its integer square root cannot exceed `num / 2`.
3. Calculate `mid` and its square. Casting `mid` to `long` before multiplication prevents overflow.
4. If `square == num`, the number is a perfect square, so return `true`.
5. If `square < num`, move `low` to `mid + 1` to search larger values.
6. If `square > num`, move `high` to `mid - 1` to search smaller values.
7. If the search ends without finding an exact square, return `false`.

---

## Dry Run

```text
num = 16
low = 1
high = 8
```

| low | high | mid | mid × mid | Action |
|---:|---:|---:|---:|---|
| 1 | 8 | 4 | 16 | Square matches `num`; return `true` |

---

## Edge Cases

- **`num = 1`:** Returns `true` immediately.
- **`num = 2` or `3`:** The search finds no integer square equal to `num`; returns `false`.
- **Perfect square:** Returns `true` when an exact square is found.
- **Non-perfect square:** Returns `false` after the search range becomes empty.
- **Large input:** Using `long` for the square prevents overflow during multiplication.

---

## Complexity Analysis

Let `n` be the input number.

- **Time Complexity:** `O(log n)` — binary search halves the search range each iteration.
- **Space Complexity:** `O(1)` — only a fixed number of variables are used.

---

## Pattern Recognition

When searching for an integer whose square matches a target, binary search works because the square of positive integers increases monotonically.

If the calculated square is too small, search higher values; if it is too large, search lower values.

---

## Key Takeaway

Binary search can determine whether a number is a perfect square without using a square-root function. Compare the midpoint’s square with the target and narrow the search range accordingly.

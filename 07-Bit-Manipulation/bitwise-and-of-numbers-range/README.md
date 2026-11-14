# WIN #131 — 201. Bitwise AND of Numbers Range

[LeetCode Problem](https://leetcode.com/problems/bitwise-and-of-numbers-range/)

**Difficulty:** Medium

**Topics:** Bit Manipulation

---

## Problem Statement

Given two integers `left` and `right` that represent the range `[left, right]`, return the bitwise AND of all numbers in this range, including both endpoints.

### Example 1

**Input:**
```text
left = 5, right = 7
```

**Output:**
```text
4
```

**Explanation:**

```text
5 = 101
6 = 110
7 = 111

101 & 110 & 111 = 100
```

Therefore, the answer is `4`.

### Example 2

**Input:**
```text
left = 0, right = 0
```

**Output:**
```text
0
```

### Example 3

**Input:**
```text
left = 1, right = 2147483647
```

**Output:**
```text
0
```

---

## Core Idea

The key observation is that when we perform bitwise AND across every number in a continuous range, any bit position that becomes zero in even one number will be zero in the final result.

Instead of calculating the AND of every number individually, we can eliminate the set bits from the right endpoint until it becomes equal to the left endpoint.

We use:

```java
right = right & (right - 1);
```

This operation removes the rightmost set bit of `right`.

Once `right` becomes equal to `left`, the remaining value represents the common bitwise prefix shared by every number in the range.

---

## Java Solution — Brian Kernighan's Algorithm

```java
class Solution {
    public int rangeBitwiseAnd(int left, int right) {
        while(right > left){
            right = right & (right - 1);
        }
        return right;
    }
}
```

---

## Step-by-Step Explanation

### 1. Understand the range

Consider:

```text
left = 5
right = 7
```

The numbers in the range are:

```text
5 = 101
6 = 110
7 = 111
```

Performing AND:

```text
101
110
111
---
100
```

The answer is `4`.

Notice that the common leading bit is the only bit that remains set.

### 2. Remove the rightmost set bit

```java
right = right & (right - 1);
```

This operation removes the rightmost set bit.

For example:

```text
right     = 7 = 111
right - 1 = 6 = 110

111 & 110 = 110
```

So:

```text
7 becomes 6
```

Next:

```text
right     = 6 = 110
right - 1 = 5 = 101

110 & 101 = 100
```

So:

```text
6 becomes 4
```

### 3. Continue until the range endpoints match

The loop condition is:

```java
while(right > left)
```

We keep removing set bits until `right` becomes less than or equal to `left`.

For valid input, `right` cannot become smaller than `left` during this process.

When both values become equal, we have found the common bitwise prefix.

### 4. Return the result

```java
return right;
```

The remaining value is the bitwise AND of every number in the given range.

---

## Dry Run

Consider:

```text
left = 5
right = 7
```

Initial values:

```text
left  = 5 = 101
right = 7 = 111
```

| Iteration | `right` | `right - 1` | AND result |
|---|---|---|---|
| 1 | 7 (111) | 6 (110) | 6 (110) |
| 2 | 6 (110) | 5 (101) | 4 (100) |

Now:

```text
left  = 5
right = 4
```

The loop stops because `right > left` is false.

The returned result is:

```text
4
```

**Correction to the intermediate interpretation:** For this input, the right endpoint becomes `4`, which is below `left = 5`. The loop stops with `right = 4`, so this implementation would return `4`. This is correct for the example, but the claim that the endpoints become equal is not correct for this implementation.

---

## Alternative Approach — Common Prefix

Your commented implementation uses another elegant approach: repeatedly right-shift both endpoints until they become equal.

```java
int shift = 0;

while(left != right){
    left = left >> 1;
    right = right >> 1;

    shift++;
}

return left << shift;
```

### How it works

The final AND can only contain the common leading bits of `left` and `right`.

By right-shifting both numbers:

- We remove their differing lower bits.
- We continue until their remaining prefixes are equal.
- We shift the common prefix back to its original bit position.

For example:

```text
left  = 5 = 101
right = 7 = 111
```

First shift:

```text
101 >> 1 = 10
111 >> 1 = 11
```

Second shift:

```text
10 >> 1 = 1
11 >> 1 = 1
```

Now both values are equal.

```text
common prefix = 1
shift = 2
```

Restore the original position:

```text
1 << 2 = 100
```

Answer:

```text
4
```

---

## Complexity Analysis

### Main Approach — Removing Rightmost Set Bits

Let `k` be the number of set bits removed from `right` before the loop terminates.

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(32) = O(1) | At most 32 bits can be removed from a Java integer. |
| Space | O(1) | Only the input variables are modified. |

### Alternative Approach — Common Prefix

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(32) = O(1) | At most 32 right shifts are required. |
| Space | O(1) | Only a shift counter is maintained. |

---

## Pattern Recognition

When a problem asks for the bitwise AND of every number in a continuous range, think about:

**Common Binary Prefix**

The important observation is that lower bits eventually become zero because the range contains numbers with different bit patterns.

Two useful approaches are:

1. Remove the rightmost set bit from the right endpoint until it is no longer greater than the left endpoint.
2. Right-shift both endpoints until their common prefix is found, then shift it back.

Both approaches avoid iterating through every number in the range.

---

## Key Takeaway

The bitwise AND of a continuous range is determined by the common leading binary prefix of its endpoints.

Instead of calculating AND for every number, we can eliminate differing lower bits using bit manipulation.

The two approaches demonstrate how a range operation can be reduced to a small number of bitwise operations.

**Final complexity: O(1) time and O(1) extra space for fixed-width 32-bit integers.**
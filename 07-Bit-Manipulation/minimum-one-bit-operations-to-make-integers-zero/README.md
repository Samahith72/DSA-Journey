# WIN #134 — 1611. Minimum One Bit Operations to Make Integers Zero

[LeetCode Problem](https://leetcode.com/problems/minimum-one-bit-operations-to-make-integers-zero/)

**Difficulty:** Hard

**Topics:** Dynamic Programming, Bit Manipulation, Recursion, Math

---

## Problem Statement

Given an integer `n`, return the minimum number of operations to make it equal to zero.

In one operation, you can:

- Change the rightmost bit (bit 0) of `n`.
- Change the `i`th bit if the `(i - 1)`th bit is `1` and all bits below position `i - 1` are `0`.

The goal is to find the minimum number of operations required to transform `n` into `0`.

### Example 1

**Input:**
```text
n = 3
```

**Output:**
```text
2
```

**Explanation:**

```text
3 = 11
11 -> 01 -> 00
```

Two operations are required.

### Example 2

**Input:**
```text
n = 6
```

**Output:**
```text
4
```

**Explanation:**

```text
6 = 110
110 -> 010 -> 011 -> 001 -> 000
```

Four operations are required.

### Example 3

**Input:**
```text
n = 0
```

**Output:**
```text
0
```

---

## Core Idea

The key observation is that the minimum number of operations follows a **Gray Code to Binary conversion relationship**.

Gray Code is a binary numbering system in which consecutive values differ by exactly one bit.

For this problem, the minimum number of operations required to transform `n` into zero is equal to the inverse Gray Code value of `n`.

We can calculate this using repeated XOR operations.

Instead of simulating every allowed operation, we repeatedly right-shift `n` and XOR it into the result.

---

## Java Solution — Gray Code Inversion Using XOR

```java
class Solution {
    public int minimumOneBitOperations(int n) {
        int result = 0;

        while(n > 0){
            result = result ^ n;
            n = n >> 1;
        }

        return result;
    }
}
```

---

## Step-by-Step Explanation

### 1. Initialize the result

```java
int result = 0;
```

This variable stores the decoded binary value.

### 2. Process the number using repeated right shifts

```java
while(n > 0){
    result = result ^ n;
    n = n >> 1;
}
```

Each iteration performs two operations:

**First:**

```java
result = result ^ n;
```

XOR the current value of `n` into the result.

**Second:**

```java
n = n >> 1;
```

Shift `n` one position to the right to process the next prefix.

We continue until all bits have been processed.

### 3. Return the result

```java
return result;
```

The final result represents the minimum number of operations required to reach zero.

---

## Dry Run

Consider:

```text
n = 6
```

Binary representation:

```text
6 = 110
```

Initially:

```text
result = 000
n      = 110
```

| Iteration | `n` (Binary) | `result ^ n` | Updated result |
|---|---|---|---|
| 1 | 110 | 000 ^ 110 | 110 |
| 2 | 011 | 110 ^ 011 | 101 |
| 3 | 001 | 101 ^ 001 | 100 |

After the third iteration:

```text
n = 000
result = 100
```

Binary `100` represents decimal `4`.

Therefore:

```text
Output = 4
```

---

## Understanding the Gray Code Connection

Gray Code converts a binary number into a representation where adjacent values differ by one bit.

The Gray Code encoding formula is:

\[
G = B \oplus (B >> 1)
\]

Where:

- `G` is the Gray Code representation.
- `B` is the original binary number.

The inverse operation reconstructs the binary value by repeatedly XORing the Gray Code number with its right-shifted versions.

For example:

```text
Gray Code = 110

110
011
001
---
100
```

Therefore:

```text
Binary = 100 = 4
```

This is why repeated XOR and right shifts produce the answer.

---

## Complexity Analysis

Let `b` be the number of bits in `n`.

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(log n) | The number is right-shifted until it becomes zero. |
| Space | O(1) | Only one additional variable is used. |

For a Java integer, the loop executes at most 31 times for the non-negative inputs in this problem.

---

## Pattern Recognition

When a problem involves:

- Transforming numbers through restricted bit operations.
- Changing one bit at a time.
- Finding the minimum number of operations.
- A relationship between consecutive binary states.

Think about **Gray Code**.

The important connection is that the operation sequence follows a Gray Code ordering. The minimum operation count can therefore be obtained by converting the given Gray Code value back to its binary index.

---

## Key Takeaway

This problem demonstrates how understanding a mathematical relationship can eliminate the need to simulate operations.

The minimum number of operations is the inverse Gray Code value of `n`.

The entire conversion can be performed using:

```java
result ^= n;
n >>= 1;
```

**Final complexity: O(log n) time and O(1) extra space.**
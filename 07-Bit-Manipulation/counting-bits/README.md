# WIN #123 — 338. Counting Bits

**LeetCode:** [338. Counting Bits](https://leetcode.com/problems/counting-bits/)  
**Difficulty:** Easy  
**Topics:** Dynamic Programming, Bit Manipulation

---

## 1. Problem Statement

Given an integer `n`, return an array `answer` of length `n + 1` such that, for every integer `i` from `0` to `n`, `answer[i]` contains the number of set bits in the binary representation of `i`.

A set bit is a bit whose value is `1`.

### Examples

**Example 1**

Input:
```text
n = 2
```

Output:
```text
[0,1,1]
```

Explanation:
- 0 = `0` → 0 set bits
- 1 = `1` → 1 set bit
- 2 = `10` → 1 set bit

**Example 2**

Input:
```text
n = 5
```

Output:
```text
[0,1,1,2,1,2]
```

Explanation:

| Number | Binary | Set Bits |
|---:|---|---:|
| 0 | `000` | 0 |
| 1 | `001` | 1 |
| 2 | `010` | 1 |
| 3 | `011` | 2 |
| 4 | `100` | 1 |
| 5 | `101` | 2 |

---

## 2. Core Idea — Dynamic Programming + Bit Manipulation

The key observation is that we don't need to calculate the number of set bits for every number from scratch.

We can use the answer of a smaller number to calculate the answer of the current number.

Your solution uses two bitwise operations:

```java
i >> 1
```

and

```java
i & 1
```

Let's understand both.

### Concept 1: Right Shift (`>>`)

Right shifting a binary number by one position removes its rightmost bit.

For example:

```text
i = 5

Binary:
101

i >> 1:

10
```

Therefore:

```text
5 >> 1 = 2
```

The right-shift operation is equivalent to integer division by 2 for non-negative integers.

### Concept 2: Bitwise AND (`&`)

You already learned that bitwise AND returns `1` only when both bits are `1`.

Your expression:

```java
i & 1
```

checks whether the last bit of the number is `1` or `0`.

If the number is odd, the result is `1`.

If the number is even, the result is `0`.

Examples:

| Number | Binary | `i & 1` |
|---:|---|---:|
| 2 | `10` | 0 |
| 3 | `11` | 1 |
| 4 | `100` | 0 |
| 5 | `101` | 1 |
| 6 | `110` | 0 |
| 7 | `111` | 1 |

### The DP Formula

Your solution uses:

```java
answer[i] = answer[i >> 1] + (i & 1);
```

This means:

```text
Number of set bits in i
=
Number of set bits in i / 2
+
Last bit of i
```

Why does this work?

Every binary number can be divided into:
- All bits except the last bit.
- The last bit itself.

Right shifting removes the last bit, and `i & 1` tells us whether that removed bit was set.

Therefore, we reuse the previous answer and add the last bit.

---

## 3. Java Solution

```java
class Solution {
    public int[] countBits(int n) {

        int[] answer = new int[n+1];

        for(int i=1;i <= n;i++){
            answer[i] = answer[i >> 1] + (i & 1);
        }

        return answer;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the answer array

```java
int[] answer = new int[n+1];
```

We create an array of size `n + 1` because we need the set-bit count for every number from `0` through `n`.

Java initializes integer arrays with zero.

Therefore:

```text
answer[0] = 0
```

This is our base case.

### Step 2: Traverse from 1 to n

```java
for(int i=1;i <= n;i++)
```

We calculate the number of set bits for every number.

We start at `1` because the answer for zero is already known.

### Step 3: Apply the DP recurrence

```java
answer[i] = answer[i >> 1] + (i & 1);
```

Break this into two parts.

**Part 1:**

```java
answer[i >> 1]
```

Retrieve the number of set bits in the number obtained after removing the last binary bit.

**Part 2:**

```java
(i & 1)
```

Determine whether the removed bit was `1` or `0`.

Add both values to get the current number's set-bit count.

### Step 4: Return the answer array

```java
return answer;
```

After the loop, every index contains the number of set bits for that integer.

---

## 5. Dry Run

Let's trace:

```text
n = 5
```

We need to calculate:

```text
answer = [0, 1, 1, 2, 1, 2]
```

Initial state:

```text
answer = [0, 0, 0, 0, 0, 0]
```

| i | Binary | `i >> 1` | `answer[i >> 1]` | `i & 1` | answer[i] |
|---:|---|---:|---:|---:|---:|
| 1 | `001` | 0 | 0 | 1 | 1 |
| 2 | `010` | 1 | 1 | 0 | 1 |
| 3 | `011` | 1 | 1 | 1 | 2 |
| 4 | `100` | 2 | 1 | 0 | 1 |
| 5 | `101` | 2 | 1 | 1 | 2 |

### Understanding individual calculations

**For i = 3**

Binary:

```text
3 = 011
```

Right shift:

```text
011 >> 1 = 001
```

Therefore:

```text
answer[3] = answer[1] + (3 & 1)
          = 1 + 1
          = 2
```

**For i = 4**

Binary:

```text
4 = 100
```

Right shift:

```text
100 >> 1 = 010
```

Therefore:

```text
answer[4] = answer[2] + (4 & 1)
          = 1 + 0
          = 1
```

**For i = 5**

Binary:

```text
5 = 101
```

Right shift:

```text
101 >> 1 = 010
```

Therefore:

```text
answer[5] = answer[2] + (5 & 1)
          = 1 + 1
          = 2
```

Final answer:

```text
[0,1,1,2,1,2]
```

---

## 6. Understanding the DP Pattern

Let's look at the relationship between numbers.

```text
0 → 000 → 0 set bits
1 → 001 → 1 set bit
2 → 010 → 1 set bit
3 → 011 → 2 set bits
4 → 100 → 1 set bit
5 → 101 → 2 set bits
6 → 110 → 2 set bits
7 → 111 → 3 set bits
```

Notice that each number uses the result of its right-shifted value.

For example:

```text
6 >> 1 = 3
```

Since:

```text
answer[3] = 2
```

And:

```text
6 & 1 = 0
```

We get:

```text
answer[6] = 2 + 0 = 2
```

This is why we can calculate all answers in a single pass.

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---:|---|---|
| 0 | `[0]` | Only zero |
| 1 | `[0,1]` | One set bit |
| 2 | `[0,1,1]` | Binary representations of 0, 1, 2 |
| 5 | `[0,1,1,2,1,2]` | Multiple set-bit counts |
| 8 | `[0,1,1,2,1,2,2,3,1]` | Includes a power of two |

---

## 8. Complexity Analysis

Let `n` be the input number.

**Time Complexity: O(n)**

- We iterate from `1` to `n`.
- Each iteration performs a constant number of operations.
- Array lookups and bitwise operations take constant time.

Therefore, time complexity is O(n).

**Space Complexity: O(n)**

We create an answer array of size `n + 1`.

Therefore, auxiliary space complexity is O(n).

---

## 9. Pattern Recognition

**Pattern: Dynamic Programming + Bitwise Recurrence**

This technique is useful when:
- You need to calculate the number of set bits for a range of numbers.
- The current answer can be derived from a smaller number.
- You want to avoid independently counting bits for every number.
- You can use bit shifts to simplify a problem.

The key recurrence is:

```text
dp[i] = dp[i / 2] + (i % 2)
```

Your implementation optimizes this using bitwise operations:

```text
dp[i] = dp[i >> 1] + (i & 1)
```

---

## 10. Final Mental Model

Think of every binary number as two parts:

1. The number without its last bit.
2. Its last bit.

The right shift gives us the first part.

The AND operation gives us the second part.

```text
answer[i] = answer[i >> 1] + (i & 1)
```

The complete logic:

```text
Initialize answer array with zeros

For i from 1 to n:
    Remove the last bit using right shift
    Get the previous bit count
    Check whether the last bit is 1
    Add both values

Return answer
```

**The main takeaway:** Instead of counting the set bits of every number independently, use previously calculated answers. Right shift removes the last bit, and bitwise AND identifies whether that bit was set. This gives an O(n) dynamic programming solution.
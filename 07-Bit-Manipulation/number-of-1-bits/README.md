# WIN #121 — 191. Number of 1 Bits

**LeetCode:** [191. Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/)  
**Difficulty:** Easy  
**Topics:** Bit Manipulation

---

## 1. Problem Statement

Given a positive integer `n`, write a function that returns the number of set bits in its binary representation.

A set bit is a bit whose value is `1`.

This is also known as the Hamming Weight of an integer.

### Examples

**Example 1**

Input:
```text
n = 11
```

Binary representation:
```text
1011
```

Output:
```text
3
```

Explanation: The binary representation contains three set bits.

**Example 2**

Input:
```text
n = 128
```

Binary representation:
```text
10000000
```

Output:
```text
1
```

**Example 3**

Input:
```text
n = 2147483645
```

Output:
```text
30
```

---

## 2. Core Idea — Brian Kernighan's Algorithm

The key observation is that we don't need to check every bit individually.

We can remove the rightmost set bit using:

```java
n = n & (n - 1);
```

Every time this operation executes, exactly one set bit is removed.

Therefore, we simply:
1. Initialize a counter.
2. Remove the rightmost set bit.
3. Increment the counter.
4. Repeat until the number becomes zero.

The final counter represents the number of set bits.

### Understanding the Bitwise AND Operation

The AND operator (`&`) returns `1` only when both corresponding bits are `1`.

| A | B | A & B |
|---|---|---|
| 0 | 0 | 0 |
| 0 | 1 | 0 |
| 1 | 0 | 0 |
| 1 | 1 | 1 |

### Why does `n & (n - 1)` remove the rightmost set bit?

Consider:

```text
n = 12
```

Binary representation:

```text
n     = 1100
n - 1 = 1011
```

Perform AND:

```text
  1100
& 1011
------
  1000
```

The rightmost set bit has disappeared.

This works because subtracting one changes the rightmost set bit from `1` to `0` and turns all bits to its right into `1`.

When we AND the original number with the decremented number, that rightmost set bit is removed.

---

## 3. Java Solution

```java
class Solution {
    public int hammingWeight(int n) {
        int count = 0;

        while(n != 0){
            n = n & (n-1);
            count++;
        }

        return count;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the counter

```java
int count = 0;
```

This variable stores the number of set bits removed so far.

### Step 2: Continue until all set bits are removed

```java
while(n != 0){
```

As long as the number is not zero, at least one set bit remains.

### Step 3: Remove the rightmost set bit

```java
n = n & (n-1);
```

This is the most important operation.

Every iteration removes exactly one `1` from the binary representation.

### Step 4: Increment the counter

```java
count++;
```

Since one set bit was removed, increment the counter.

### Step 5: Return the Hamming Weight

```java
return count;
```

When `n` becomes zero, all set bits have been removed.

The counter now contains the total number of set bits.

---

## 5. Dry Run

Let's trace:

```text
n = 11
```

Binary representation:

```text
1011
```

Initial state:

```text
n = 11
count = 0
```

| Iteration | n (Binary) | n - 1 (Binary) | AND Result | Count |
|---|---|---|---|---:|
| 1 | `1011` | `1010` | `1010` | 1 |
| 2 | `1010` | `1001` | `1000` | 2 |
| 3 | `1000` | `0111` | `0000` | 3 |

Now:

```text
n = 0
```

The loop terminates.

Final answer:

```text
3
```

There were three set bits in the original binary representation.

---

## 6. Understanding the Difference Between Two Approaches

There are two common ways to count set bits.

### Approach 1: Check Every Bit

Check each bit individually using bitwise operations.

For a 32-bit integer, this requires checking all 32 positions.

Time Complexity: O(32), effectively O(1) for a fixed-width integer.

### Approach 2: Brian Kernighan's Algorithm

```java
n = n & (n - 1);
```

This removes one set bit per iteration.

If the number contains `k` set bits, the loop executes exactly `k` times.

Time Complexity: O(k), where `k` is the number of set bits.

Your solution uses the second approach.

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `0` | 0 | No set bits |
| `1` | 1 | One set bit |
| `7` | 3 | Binary `111` |
| `8` | 1 | Binary `1000` |
| `15` | 4 | Binary `1111` |
| `128` | 1 | Binary `10000000` |

---

## 8. Complexity Analysis

Let `k` be the number of set bits in the integer.

**Time Complexity: O(k)**

- Every iteration removes exactly one set bit.
- The loop executes exactly as many times as there are set bits.

For a 32-bit integer, the maximum number of iterations is 32.

**Space Complexity: O(1)**

Only one additional variable, `count`, is used.

No additional data structures are required.

---

## 9. Pattern Recognition

**Pattern: Brian Kernighan's Algorithm + Bitwise AND**

This technique is useful when:
- You need to count the number of set bits.
- You need to remove the rightmost set bit.
- You need to efficiently determine whether a number contains set bits.
- You want to avoid checking every bit position individually.

The key operation is:

```text
n & (n - 1)
```

It removes exactly one set bit from the integer.

---

## 10. Final Mental Model

Think of the algorithm as repeatedly removing one `1` from the binary representation.

```text
Original:  1011
              |
Remove:    1010
              |
Remove:    1000
              |
Remove:    0000
```

Every removal increments the counter.

The complete logic:

```text
Initialize count = 0

While n is not zero:
    Remove rightmost set bit
    Increment count

Return count
```

**The main takeaway:** Brian Kernighan's Algorithm counts set bits by removing one set bit per iteration using `n & (n - 1)`. Its execution time depends on the number of set bits rather than the total number of bit positions.
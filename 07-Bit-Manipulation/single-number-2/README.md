# WIN #129 — 137. Single Number II

[LeetCode Problem](https://leetcode.com/problems/single-number-ii/)

**Difficulty:** Medium

**Topics:** Array, Bit Manipulation

---

## Problem Statement

Given an integer array `nums` where every element appears exactly three times except for one element, which appears exactly once, return the element that appears only once.

Your solution must run in linear time and use constant extra space.

### Example 1

**Input:**
```text
nums = [2,2,3,2]
```

**Output:**
```text
3
```

**Explanation:** The number `3` appears once, while `2` appears three times.

### Example 2

**Input:**
```text
nums = [0,1,0,1,0,1,99]
```

**Output:**
```text
99
```

**Explanation:** Every number except `99` appears exactly three times.

---

## Core Idea

The key observation is that every number appears three times except one.

We need to track how many times each bit has appeared, but we want to do this without maintaining a separate counter for every bit.

We use two integer variables:

- `ones`: Tracks bits that have appeared exactly once.
- `twos`: Tracks bits that have appeared exactly twice.

When a bit appears for the third time, it is removed from both states.

This creates a bitwise state machine that automatically resets every bit after its third occurrence.

---

## Java Solution — Bitwise State Tracking

```java
class Solution {
    public int singleNumber(int[] nums) {
        int ones = 0;
        int twos = 0;

        for(int num: nums){
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }

        return ones;
    }
}
```

---

## Step-by-Step Explanation

### 1. Understanding the two variables

We maintain two bitmasks:

| Variable | Meaning |
|---|---|
| `ones` | Bits that have appeared once |
| `twos` | Bits that have appeared twice |

Each bit independently cycles through three states:

| Number of appearances | `ones` bit | `twos` bit |
|---|---:|---:|
| 0 times | 0 | 0 |
| 1 time | 1 | 0 |
| 2 times | 0 | 1 |
| 3 times | 0 | 0 |

After the third occurrence, the bit resets to zero.

### 2. Updating `ones`

```java
ones = (ones ^ num) & ~twos;
```

Break this into two operations:

- `ones ^ num`: Toggles the bits that are set in the current number.
- `& ~twos`: Removes bits that are already present in the `twos` state.

This ensures that bits appearing for the third time do not remain in `ones`.

### 3. Updating `twos`

```java
twos = (twos ^ num) & ~ones;
```

Similarly:

- `twos ^ num`: Toggles the bits in the current number.
- `& ~ones`: Removes bits that are now represented in `ones`.

**Important:** The `twos` update uses the newly updated value of `ones`. This is intentional and necessary for the state transitions to work correctly.

### 4. Returning the result

```java
return ones;
```

Every number appearing three times has all its bits reset after the third occurrence.

The unique number appears once, so its bits remain in `ones`.

---

## Dry Run

Consider:

```text
nums = [2, 2, 3, 2]
```

Binary representations:

```text
2 = 010
3 = 011
```

Initially:

```text
ones = 000
twos = 000
```

| Current number | `ones` after update | `twos` after update |
|---|---|---|
| 2 (010) | 010 | 000 |
| 2 (010) | 000 | 010 |
| 3 (011) | 001 | 010 |
| 2 (010) | 001 | 000 |

Final state:

```text
ones = 001
twos = 000
```

Therefore:

```text
Answer = 1
```

Correction: for this example, the input `[2,2,3,2]` has unique value `3`, whose binary representation is `011`. The final state should therefore be:

```text
ones = 011
twos = 000
```

The algorithm returns `3`.

---

## Alternative Approach — Bit Counting

Another way to solve this problem is to count how many times each bit position is set.

Since every repeated number appears three times, the total count of each bit contributed by those numbers will be divisible by three.

The unique number's set bits will leave a remainder of one.

```java
int result = 0;

for(int bit = 0; bit < 32; bit++){
    int count = 0;

    for(int num : nums){
        if((num & (1 << bit)) != 0){
            count++;
        }
    }

    if(count % 3 != 0){
        result = result ^ (1 << bit);
    }
}

return result;
```

### How it works

1. Iterate through all 32 bit positions of an integer.
2. Count how many numbers have the current bit set.
3. Calculate `count % 3`.
4. If the remainder is nonzero, set that bit in the result.

This reconstructs the unique number bit by bit.

---

## Complexity Analysis

### Main Approach — Bitwise State Tracking

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(n) | Each number is processed once with constant-time bitwise operations. |
| Space | O(1) | Only two integer variables are maintained. |

### Alternative Approach — Bit Counting

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(32 × n) = O(n) | Every number is checked across 32 bit positions. |
| Space | O(1) | Only counters and the result variable are used. |

---

## Pattern Recognition

When a problem states that:

- Every element appears three times.
- One element appears only once.
- Linear time and constant extra space are required.

Think about **bitwise state tracking**.

Instead of storing frequencies in a HashMap, maintain the state of each bit using bitmasks.

The `ones` and `twos` variables encode whether each bit has appeared once or twice. A third appearance resets that bit.

---

## Key Takeaway

The important insight is that we do not need to count complete numbers. We can track the occurrence frequency of every bit independently.

By maintaining two bitmasks:

- `ones` represents bits seen once.
- `twos` represents bits seen twice.
- A third occurrence clears the bit from both.

This solves the problem in **O(n) time and O(1) extra space** using bitwise operations.
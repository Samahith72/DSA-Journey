# WIN #130 — 260. Single Number III

[LeetCode Problem](https://leetcode.com/problems/single-number-iii/)

**Difficulty:** Medium

**Topics:** Array, Bit Manipulation

---

## Problem Statement

Given an integer array `nums` in which exactly two elements appear only once and all the other elements appear exactly twice, return the two elements that appear only once.

You must solve the problem in linear time and use only constant extra space.

### Example 1

**Input:**
```text
nums = [1,2,1,3,2,5]
```

**Output:**
```text
[3,5]
```

**Explanation:** The elements `3` and `5` appear only once. All other elements appear twice.

### Example 2

**Input:**
```text
nums = [-1,0]
```

**Output:**
```text
[-1,0]
```

---

## Core Idea

The key observation is that XOR has three important properties:

1. `a ^ a = 0` — XOR of a number with itself becomes zero.
2. `a ^ 0 = a` — XOR with zero leaves the number unchanged.
3. XOR is commutative and associative — the order of XOR operations does not matter.

Since every repeated number appears twice, XORing the entire array eliminates all duplicate pairs.

However, unlike Single Number I, the result is not one unique number. It is the XOR of the two unique numbers.

We need to separate those two numbers into different groups so that each group contains exactly one unique number.

We achieve this using a **rightmost set bit mask**.

---

## Java Solution — XOR and Bit Partitioning

```java
class Solution {
    public int[] singleNumber(int[] nums) {
        int xor = 0;

        for(int num: nums){
            xor = xor ^ num;
        }

        int mask = xor & (-xor);
        int num1 = 0;
        int num2 = 0;

        for(int num: nums){
            if((num & mask) == 0){
                num1 = num1 ^ num;
            }else{
                num2 = num2 ^ num;
            }
        }

        return new int[]{num1, num2};
    }
}
```

---

## Step-by-Step Explanation

### 1. XOR all elements

```java
int xor = 0;

for(int num: nums){
    xor = xor ^ num;
}
```

All duplicate numbers cancel each other.

For example:

```text
nums = [1,2,1,3,2,5]

xor = 1 ^ 2 ^ 1 ^ 3 ^ 2 ^ 5
```

Rearranging:

```text
xor = (1 ^ 1) ^ (2 ^ 2) ^ 3 ^ 5
xor = 0 ^ 0 ^ 3 ^ 5
xor = 3 ^ 5
```

Therefore:

```text
xor = 6
```

The XOR result contains the bits where the two unique numbers differ.

### 2. Find the rightmost set bit

```java
int mask = xor & (-xor);
```

We use:

```text
xor & (-xor)
```

This isolates the rightmost set bit of `xor`.

Why does this work?

In two's complement representation, negating a number preserves the rightmost set bit and changes the bits to its right and left in a way that allows the AND operation to isolate that bit.

For our example:

```text
xor = 6       = 00000110
-xor = -6     = 11111010
                  --------
mask          = 00000010
```

The mask is `2`.

This tells us that the two unique numbers have different values at this bit position.

### 3. Divide numbers into two groups

```java
if((num & mask) == 0){
    num1 = num1 ^ num;
}else{
    num2 = num2 ^ num;
}
```

We divide the array into two groups:

- Group 1: Numbers whose masked bit is `0`.
- Group 2: Numbers whose masked bit is `1`.

Because the two unique numbers differ at this bit position, they must belong to different groups.

Every duplicate pair has identical bits, so both occurrences of a duplicate always enter the same group and cancel each other through XOR.

After processing the entire array:

- `num1` contains the first unique number.
- `num2` contains the second unique number.

### 4. Return both unique numbers

```java
return new int[]{num1, num2};
```

The order of the two returned numbers does not matter.

---

## Dry Run

Consider:

```text
nums = [1,2,1,3,2,5]
```

### Step 1: XOR all numbers

```text
1 ^ 2 ^ 1 ^ 3 ^ 2 ^ 5
```

Duplicate pairs cancel:

```text
xor = 3 ^ 5 = 6
```

Binary representation:

```text
xor = 00000110
```

### Step 2: Find the rightmost set bit

```java
mask = xor & (-xor);
```

```text
  00000110
& 11111010
-----------
  00000010
```

Therefore:

```text
mask = 2
```

### Step 3: Partition the array

We check each number using:

```java
(num & mask) == 0
```

| Number | Binary | `num & mask` | Group |
|---|---|---|---|
| 1 | 00000001 | 00000000 | Group 1 |
| 2 | 00000010 | 00000010 | Group 2 |
| 1 | 00000001 | 00000000 | Group 1 |
| 3 | 00000011 | 00000010 | Group 2 |
| 2 | 00000010 | 00000010 | Group 2 |
| 5 | 00000101 | 00000000 | Group 1 |

### Step 4: XOR within each group

**Group 1:**

```text
1 ^ 1 ^ 5 = 5
```

**Group 2:**

```text
2 ^ 3 ^ 2 = 3
```

Final values:

```text
num1 = 5
num2 = 3
```

Output:

```text
[5,3]
```

The expected output is `[3,5]`. Both are valid because the problem does not require a particular order.

---

## Complexity Analysis

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(n) | Two traversals of the input array. |
| Space | O(1) | Only a fixed number of integer variables are used. |

---

## Pattern Recognition

When a problem states that:

- Exactly two elements appear once.
- Every other element appears twice.
- Linear time and constant extra space are required.

Think about **XOR partitioning**.

The approach is:

1. XOR the entire array to obtain the XOR of the two unique numbers.
2. Isolate a set bit where those two numbers differ.
3. Use that bit to divide the array into two groups.
4. XOR each group independently.

The key insight is that duplicate numbers always enter the same group and cancel each other.

---

## Key Takeaway

XOR can do more than identify a single unique number.

When two unique numbers remain after XORing the entire array, their XOR tells us which bit positions differ.

By isolating one differing bit and partitioning the array using that bit, we can independently recover both numbers.

**Final complexity: O(n) time and O(1) extra space.**
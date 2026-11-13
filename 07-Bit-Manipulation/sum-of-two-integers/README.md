# WIN #128 — 371. Sum of Two Integers

**LeetCode:** [371. Sum of Two Integers](https://leetcode.com/problems/sum-of-two-integers/)  
**Difficulty:** Medium  
**Topics:** Math, Bit Manipulation

---

## 1. Problem Statement

Given two integers `a` and `b`, return their sum without using the `+` and `-` operators.

### Examples

**Example 1**

Input:
```text
a = 1, b = 2
```

Output:
```text
3
```

**Example 2**

Input:
```text
a = 2, b = 3
```

Output:
```text
5
```

---

## 2. Core Idea — Binary Addition Using XOR and AND

Normally, when we add two binary numbers, we perform two operations:

1. Calculate the sum of each pair of bits.
2. Calculate and propagate the carry whenever necessary.

Your solution separates these two operations using bitwise operators.

### Concept 1: XOR calculates the sum without carry

Remember the XOR truth table:

| A | B | A XOR B |
|---|---|---|
| 0 | 0 | 0 |
| 0 | 1 | 1 |
| 1 | 0 | 1 |
| 1 | 1 | 0 |

Notice that XOR produces the correct sum bit when we temporarily ignore the carry.

For example:

```text
  1
+ 1
---
  0
```

The XOR result is `0`, but we still need to account for the carry.

Therefore:

```java
a = a ^ b;
```

calculates the sum without carry.

### Concept 2: AND identifies where carry is generated

Carry occurs only when both bits are `1`.

Look at the AND truth table:

| A | B | A AND B |
|---|---|---|
| 0 | 0 | 0 |
| 0 | 1 | 0 |
| 1 | 0 | 0 |
| 1 | 1 | 1 |

Therefore:

```java
a & b
```

identifies the positions where carry must be generated.

### Concept 3: Left shift moves the carry to the next position

A carry generated at one bit position must be added to the next higher position.

We achieve this using:

```java
(a & b) << 1
```

The left shift moves every generated carry one position to the left.

---

## 3. Java Solution

```java
class Solution {
    public int getSum(int a, int b) {
        while(b != 0){
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }

        return a;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Continue until there is no carry

```java
while(b != 0){
```

We repeat the process as long as a carry exists.

When `b` becomes zero, no additional carry needs to be added.

### Step 2: Calculate the carry

```java
int carry = (a & b) << 1;
```

- `a & b` identifies positions where both bits are `1`.
- `<< 1` moves those carry bits to the next higher position.

### Step 3: Calculate the sum without carry

```java
a = a ^ b;
```

XOR calculates the sum bits while ignoring carry.

The updated `a` contains the partial sum.

### Step 4: Move the carry into b

```java
b = carry;
```

We store the carry in `b`.

In the next iteration, the algorithm adds this carry to the partial sum.

### Step 5: Return the result

```java
return a;
```

Once `b` becomes zero, no carry remains.

The value in `a` is the final sum.

---

## 5. Dry Run

Let's trace:

```text
a = 5
b = 3
```

Binary representation:

```text
a = 0101
b = 0011
```

### Initial State

```text
a = 0101
b = 0011
```

### Iteration 1

**Calculate carry:**

```text
  0101
& 0011
------
  0001
```

Shift left:

```text
carry = 0010
```

**Calculate sum without carry:**

```text
  0101
^ 0011
------
  0110
```

Update:

```text
a = 0110
b = 0010
```

### Iteration 2

**Calculate carry:**

```text
  0110
& 0010
------
  0010
```

Shift left:

```text
carry = 0100
```

**Calculate sum without carry:**

```text
  0110
^ 0010
------
  0100
```

Update:

```text
a = 0100
b = 0100
```

### Iteration 3

**Calculate carry:**

```text
  0100
& 0100
------
  0100
```

Shift left:

```text
carry = 1000
```

**Calculate sum without carry:**

```text
  0100
^ 0100
------
  0000
```

Update:

```text
a = 0000
b = 1000
```

### Iteration 4

**Calculate carry:**

```text
  0000
& 1000
------
  0000
```

Shift left:

```text
carry = 0000
```

**Calculate sum without carry:**

```text
  0000
^ 1000
------
  1000
```

Update:

```text
a = 1000
b = 0000
```

Now `b == 0`, so the loop terminates.

Final result:

```text
1000 = 8
```

Therefore:

```text
5 + 3 = 8
```

---

## 6. Understanding the Algorithm Visually

The entire process can be summarized as:

```text
       a          b
       |          |
       |          |
       +---- XOR--+
            |
            v
      Sum without carry

       a          b
       |          |
       |          |
       +---- AND--+
            |
            v
        Carry bits
            |
            v
         Left Shift
            |
            v
       Carry to next position
```

The algorithm repeatedly combines the partial sum and carry until no carry remains.

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `1, 2` | 3 | Basic addition |
| `5, 3` | 8 | Multiple carry operations |
| `0, 0` | 0 | No bits to add |
| `0, 5` | 5 | Adding zero |
| `-1, 1` | 0 | Carry propagation with negative integers |
| `-5, 3` | -2 | Addition involving negative numbers |

Java uses 32-bit two's-complement integers, so the same bitwise operations also work for negative values within the `int` range.

---

## 8. Complexity Analysis

Let `w` represent the integer bit width.

**Time Complexity: O(w)**

Each iteration propagates the carry to higher bit positions.

For Java's 32-bit integers, the number of iterations is bounded by the integer width.

Therefore, the practical time complexity is O(1).

**Space Complexity: O(1)**

Only a constant number of variables are used:
- `a`
- `b`
- `carry`

No additional data structures are required.

---

## 9. Pattern Recognition

**Pattern: Binary Addition Using XOR + AND + Shift**

This technique is useful when:
- You need to perform addition without arithmetic operators.
- You need to understand how binary addition works internally.
- You need to separate sum calculation from carry propagation.
- You are solving low-level bit manipulation problems.

The three essential operations are:

| Operation | Purpose |
|---|---|
| `a ^ b` | Calculate sum without carry |
| `a & b` | Identify carry positions |
| `(a & b) << 1` | Move carry to the next position |

---

## 10. Final Mental Model

Think of binary addition as two independent operations.

**First: Calculate the sum without carry.**

```java
a = a ^ b;
```

**Second: Calculate the carry.**

```java
b = (a & b) << 1;
```

In your actual implementation, the carry is calculated before updating `a`, which is important because both calculations must use the previous values.

The complete logic:

```text
While carry is not zero:

    carry = (a AND b) << 1

    a = a XOR b

    b = carry

Return a
```

**The main takeaway:** XOR performs binary addition without carry, AND identifies where carry is generated, and left shift moves the carry to the next position. Repeating these operations simulates normal binary addition without using `+` or `-`.
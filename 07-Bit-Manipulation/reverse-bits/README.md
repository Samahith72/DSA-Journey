# WIN #124 — 190. Reverse Bits

**LeetCode:** [190. Reverse Bits](https://leetcode.com/problems/reverse-bits/)  
**Difficulty:** Easy  
**Topics:** Divide and Conquer, Bit Manipulation

---

## 1. Problem Statement

Reverse the bits of a given 32-bit unsigned integer.

### Examples

**Example 1**

Input:
```text
n = 00000010100101000001111010011100
```

Output:
```text
964176192 (00111001011110000010100101000000)
```

Explanation: The input binary representation is reversed to produce the output.

**Example 2**

Input:
```text
n = 11111111111111111111111111111101
```

Output:
```text
3221225471 (10111111111111111111111111111111)
```

---

## 2. Core Idea — Extract and Rebuild Bits

The key idea is to construct a new integer by taking bits from the original integer one at a time.

We use three important bitwise operations:

| Operation | Purpose |
|---|---|
| `n & 1` | Extract the rightmost bit |
| `result << 1` | Shift the result left to make room for a new bit |
| `n >>> 1` | Shift the original number right without preserving the sign bit |

We repeat this process exactly **32 times**, because the problem requires reversing all 32 bits.

### Understanding the approach

Suppose:

```text
n = 1101
```

We extract bits from right to left:

```text
1 → 0 → 1 → 1
```

We construct the result by shifting left and adding each extracted bit:

```text
1 → 10 → 101 → 1011
```

The reversed bit sequence is:

```text
1101 → 1011
```

The important point is that we are not reversing the integer's decimal digits. We are reversing its binary bits.

---

## 3. Java Solution

```java
class Solution {
    public int reverseBits(int n) {
        int result = 0;

        for(int i=0;i < 32;i++){
            result = (result << 1) | (n & 1);
            n = n>>>1;
        }

        return result;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the result

```java
int result = 0;
```

We construct the reversed binary representation inside this variable.

Initially, all bits are zero.

### Step 2: Iterate exactly 32 times

```java
for(int i=0;i < 32;i++)
```

A Java `int` contains exactly 32 bits.

We must process every bit, including leading zeros.

This is important because reversing bits is different from reversing only the significant bits.

### Step 3: Extract the rightmost bit

```java
n & 1
```

The binary representation of `1` is:

```text
00000000000000000000000000000001
```

Performing AND extracts the least significant bit.

For example:

```text
n = 1101
1 = 0001
    ----
AND = 0001
```

The extracted bit is `1`.

### Step 4: Shift the result and append the extracted bit

```java
result = (result << 1) | (n & 1);
```

This is the most important statement.

It performs two operations:

**First:**

```java
result << 1
```

Shifts the existing result one position to the left.

This creates an empty position on the right.

**Second:**

```java
| (n & 1)
```

Appends the extracted bit to the rightmost position.

The bitwise OR combines the shifted result with the extracted bit.

### Step 5: Remove the processed bit from the original number

```java
n = n >>> 1;
```

Unsigned right shift moves every bit one position to the right and fills the leftmost position with zero.

This ensures that the next iteration processes the next bit.

We use `>>>` instead of `>>` because the input may have its most significant bit set. We want to shift in zeros, not preserve the sign bit.

### Step 6: Return the reversed result

```java
return result;
```

After 32 iterations, all bits have been processed and the reversed bit sequence is stored in `result`.

---

## 5. Dry Run

Let's understand the algorithm using a smaller 4-bit example:

```text
n = 1101
```

Initially:

```text
result = 0000
```

| Iteration | Original n | Extracted Bit (`n & 1`) | Result After Shift and Append |
|---:|---|---:|---|
| 1 | `1101` | 1 | `0001` |
| 2 | `0110` | 0 | `0010` |
| 3 | `0011` | 1 | `0101` |
| 4 | `0001` | 1 | `1011` |

Final result:

```text
Original: 1101
Reversed: 1011
```

Notice how the original number is shifted right while the result is shifted left.

This is what makes the reversal possible.

---

## 6. Understanding the Three Bitwise Operations Together

Your entire solution depends on this statement:

```java
result = (result << 1) | (n & 1);
```

Let's break it down with an example.

Suppose:

```text
result = 101
n      = 110
```

Extract the rightmost bit:

```text
n & 1

110
001
---
000
```

The extracted bit is `0`.

Shift the result:

```text
101 << 1 = 1010
```

Append the extracted bit:

```text
1010
 0000
-----
1010
```

New result:

```text
1010
```

Then shift the original number:

```text
110 >>> 1 = 011
```

The next iteration processes the next bit.

---

## 7. Important Edge Cases

| Input | Expected Result | Reason |
|---|---|---|
| `00000000000000000000000000000000` | 0 | All bits are zero |
| `00000000000000000000000000000001` | `10000000000000000000000000000000` | Rightmost bit moves to the leftmost position |
| `10000000000000000000000000000000` | 1 | Leftmost bit moves to the rightmost position |
| `11111111111111111111111111111111` | -1 as Java `int` | All bits remain set |

**Important:** Java's `int` is signed. The reversed bit pattern may have its most significant bit set, so Java can display the resulting integer as negative. The underlying 32-bit pattern is still correct.

---

## 8. Complexity Analysis

**Time Complexity: O(1)**

- The loop executes exactly 32 times.
- Each iteration performs a constant number of bitwise operations.

Since Java integers have a fixed width of 32 bits, the time complexity is O(1).

**Space Complexity: O(1)**

Only two additional integer variables are used:
- `result`
- Loop variable `i`

No additional data structures are required.

---

## 9. Pattern Recognition

**Pattern: Bit Extraction + Bit Construction**

This technique is useful when:
- You need to reverse a binary representation.
- You need to rearrange bits.
- You need to construct a new integer from individual bits.
- You need to process a fixed-width integer bit by bit.

The key operations are:

```text
Extract:       n & 1
Shift result:  result << 1
Append:        result | extractedBit
Shift input:   n >>> 1
```

---

## 10. Final Mental Model

Think of two numbers moving in opposite directions.

**Original number:**

- Extract its rightmost bit.
- Shift right to expose the next bit.

**Result number:**

- Shift left to create space.
- Append the extracted bit.

```text
Original:  1101
              |
Extract:       1

Result:   0000 → 0001
```

Repeat this process 32 times.

The complete logic:

```text
Initialize result = 0

Repeat 32 times:
    Extract rightmost bit from n
    Shift result left by one
    Append extracted bit to result
    Shift n right by one using >>>

Return result
```

**The main takeaway:** Bit reversal is achieved by extracting bits from the original number from right to left and appending them to a new number from left to right. Your solution performs this operation in constant time and constant space.
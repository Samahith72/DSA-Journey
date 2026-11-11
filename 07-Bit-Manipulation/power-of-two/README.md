# WIN #122 — 231. Power of Two

**LeetCode:** [231. Power of Two](https://leetcode.com/problems/power-of-two/)  
**Difficulty:** Easy  
**Topics:** Math, Bit Manipulation

---

## 1. Problem Statement

Given an integer `n`, return `true` if it is a power of two. Otherwise, return `false`.

An integer `n` is a power of two if there exists an integer `x` such that:

```text
n = 2^x
```

### Examples

**Example 1**

Input:
```text
n = 1
```

Output:
```text
true
```

Explanation: \(2^0 = 1\).

**Example 2**

Input:
```text
n = 16
```

Output:
```text
true
```

Explanation: \(2^4 = 16\).

**Example 3**

Input:
```text
n = 3
```

Output:
```text
false
```

Explanation: 3 is not a power of two.

**Example 4**

Input:
```text
n = 0
```

Output:
```text
false
```

---

## 2. Core Idea — Bit Manipulation

The most important observation:

**Every positive power of two contains exactly one set bit in its binary representation.**

Consider:

| Number | Binary | Number of Set Bits |
|---:|---|---:|
| 1 | `0001` | 1 |
| 2 | `0010` | 1 |
| 4 | `0100` | 1 |
| 8 | `1000` | 1 |
| 16 | `10000` | 1 |
| 32 | `100000` | 1 |

Now consider numbers that are not powers of two:

| Number | Binary | Number of Set Bits |
|---:|---|---:|
| 3 | `0011` | 2 |
| 5 | `0101` | 2 |
| 6 | `0110` | 2 |
| 10 | `1010` | 2 |
| 12 | `1100` | 2 |

We already know from the previous problem that:

```text
n & (n - 1)
```

removes the rightmost set bit.

For a number containing exactly one set bit, this operation produces zero.

Therefore:

```text
(n & (n - 1)) == 0
```

But there is one additional condition: `n` must be positive because zero is not a power of two.

---

## 3. Java Solution

```java
class Solution {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n-1)) == 0;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Check whether the number is positive

```java
n > 0
```

A power of two must be greater than zero.

This eliminates:
- Zero
- Negative numbers

### Step 2: Apply the bit manipulation operation

```java
(n & (n-1)) == 0
```

This checks whether the number contains exactly one set bit.

If it does, removing that bit produces zero.

### Step 3: Combine both conditions

```java
return n > 0 && (n & (n-1)) == 0;
```

The `&&` operator ensures both conditions must be true.

- Positive number.
- Exactly one set bit.

If both conditions hold, the number is a power of two.

---

## 5. Dry Run

Let's trace:

```text
n = 16
```

Binary representation:

```text
n     = 10000
n - 1 = 01111
```

Perform bitwise AND:

```text
  10000
& 01111
-------
  00000
```

Therefore:

```text
(n & (n - 1)) == 0
```

And:

```text
n > 0
```

Both conditions are true.

Output:

```text
true
```

### Another Example: n = 12

```text
n     = 01100
n - 1 = 01011
```

Perform AND:

```text
  01100
& 01011
-------
  01000
```

The result is not zero.

Output:

```text
false
```

---

## 6. Important Edge Cases

| Input | Output | Reason |
|---:|---|---|
| 0 | false | Zero is not a power of two |
| 1 | true | \(2^0 = 1\) |
| 2 | true | \(2^1 = 2\) |
| 3 | false | Multiple set bits |
| 8 | true | \(2^3 = 8\) |
| 16 | true | \(2^4 = 16\) |
| -16 | false | Negative numbers are invalid |
| 1024 | true | \(2^{10} = 1024\) |

---

## 7. Complexity Analysis

**Time Complexity: O(1)**

The solution performs:
- One positivity check.
- One bitwise AND operation.
- One comparison.

Integer bitwise operations take constant time for Java's fixed-width `int` type.

**Space Complexity: O(1)**

No additional data structures or variables are required.

---

## 8. Pattern Recognition

**Pattern: Power of Two + Single Set Bit + Brian Kernighan's Algorithm**

This technique is useful when:
- You need to check whether a number is a power of two.
- You need to identify numbers containing exactly one set bit.
- You need to eliminate repeated bit-by-bit checking.
- You can use the relationship between a number and its predecessor.

The central property is:

```text
Power of Two:
Exactly one set bit

n & (n - 1):
Removes the rightmost set bit

Result:
Zero
```

---

## 9. Final Mental Model

A power of two always has exactly one `1` in binary.

For example:

```text
1  = 00001
2  = 00010
4  = 00100
8  = 01000
16 = 10000
```

When we subtract one, all bits to the right of the set bit become `1`.

Performing AND between the original number and the decremented number produces zero.

The complete logic:

```text
If n <= 0:
    return false

If n & (n - 1) == 0:
    return true

Otherwise:
    return false
```

**The main takeaway:** A positive integer is a power of two if and only if its binary representation contains exactly one set bit. The expression `n & (n - 1)` checks this property in constant time.
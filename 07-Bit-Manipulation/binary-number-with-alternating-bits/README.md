# WIN #127 — 693. Binary Number with Alternating Bits

**LeetCode:** [693. Binary Number with Alternating Bits](https://leetcode.com/problems/binary-number-with-alternating-bits/)  
**Difficulty:** Easy  
**Topics:** Bit Manipulation

---

## 1. Problem Statement

Given a positive integer `n`, check whether its binary representation has alternating bits.

In other words, every two adjacent bits must be different.

### Examples

**Example 1**

Input:
```text
n = 5
```

Binary representation:
```text
101
```

Output:
```text
true
```

Explanation: Every adjacent pair of bits is different.

**Example 2**

Input:
```text
n = 7
```

Binary representation:
```text
111
```

Output:
```text
false
```

Explanation: Adjacent bits are identical.

**Example 3**

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
false
```

Explanation: The last two bits are both `1`.

---

## 2. Core Idea — XOR + Consecutive Set Bit Checking

Your solution uses two important operations:

```java
int x = n ^ (n >> 1);
```

and

```java
(x & (x + 1)) == 0
```

Let's understand why they work together.

### Concept 1: XOR with a right-shifted number

The first operation is:

```java
n ^ (n >> 1)
```

We shift the original number right by one position and XOR it with the original number.

This compares every bit with its adjacent bit.

Remember:

| A | B | A XOR B |
|---|---|---|
| 0 | 0 | 0 |
| 0 | 1 | 1 |
| 1 | 0 | 1 |
| 1 | 1 | 0 |

If adjacent bits are different, XOR produces `1`.

If adjacent bits are identical, XOR produces `0`.

For a number with alternating bits, this operation produces a sequence of consecutive `1`s.

Example:

```text
n      = 10101
n >> 1 = 01010
         -----
XOR    = 11111
```

Notice that every bit in the XOR result is `1`.

### Concept 2: Checking consecutive set bits

Your second operation is:

```java
(x & (x + 1)) == 0
```

This checks whether `x` consists of consecutive set bits starting from the least significant bit.

For example:

```text
x     = 11111
x + 1 = 100000
```

Perform AND:

```text
  011111
& 100000
--------
  000000
```

The result is zero.

Therefore, the condition is true.

But consider:

```text
x     = 10111
x + 1 = 11000
```

Perform AND:

```text
  10111
& 11000
-------
  10000
```

The result is not zero.

Therefore, the condition is false.

---

## 3. Java Solution

```java
class Solution {
    public boolean hasAlternatingBits(int n) {
        int x = n ^ (n >> 1);

        if((x & (x + 1)) == 0){
            return true;
        }

        return false;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Compare adjacent bits using XOR

```java
int x = n ^ (n >> 1);
```

- `n >> 1` shifts the number right by one position.
- XOR compares the original bits with their neighboring bits.
- If the original number has alternating bits, the result contains consecutive `1`s.

### Step 2: Check whether the result contains consecutive set bits

```java
(x & (x + 1)) == 0
```

A binary number containing consecutive set bits looks like:

```text
1
11
111
1111
11111
```

Adding one changes all those trailing ones into zeros and sets the next bit.

Therefore, ANDing the original number with its incremented value produces zero.

### Step 3: Return the result

```java
if((x & (x + 1)) == 0){
    return true;
}

return false;
```

If the condition is satisfied, the original number has alternating bits.

Otherwise, it does not.

---

## 5. Dry Run

Let's trace:

```text
n = 5
```

Binary representation:

```text
n = 101
```

### Step 1: Right shift

```text
n      = 101
n >> 1 = 010
```

### Step 2: XOR

```text
  101
^ 010
-----
  111
```

Therefore:

```text
x = 7
```

### Step 3: Add one

```text
x     = 111
x + 1 = 1000
```

### Step 4: Apply AND

```text
  0111
& 1000
------
  0000
```

Therefore:

```text
(x & (x + 1)) == 0
```

Output:

```text
true
```

---

## 6. Understanding a Non-Alternating Number

Consider:

```text
n = 11
```

Binary representation:

```text
n = 1011
```

### Step 1: Right shift

```text
n      = 1011
n >> 1 = 0101
```

### Step 2: XOR

```text
  1011
^ 0101
------
  1110
```

Therefore:

```text
x = 1110
```

### Step 3: Add one

```text
x     = 1110
x + 1 = 1111
```

### Step 4: Apply AND

```text
  1110
& 1111
------
  1110
```

The result is not zero.

Output:

```text
false
```

The original binary representation `1011` contains two adjacent `1`s.

---

## 7. Important Edge Cases

| Input | Binary | Output | Reason |
|---:|---|---|---|
| 1 | `1` | true | Only one bit |
| 2 | `10` | true | Alternating bits |
| 5 | `101` | true | Alternating bits |
| 7 | `111` | false | Consecutive ones |
| 10 | `1010` | true | Alternating bits |
| 11 | `1011` | false | Last two bits are identical |
| 15 | `1111` | false | All bits are set |

---

## 8. Complexity Analysis

**Time Complexity: O(1)**

The solution performs:
- One right shift.
- One XOR operation.
- One addition.
- One bitwise AND operation.
- One comparison.

All operations take constant time for Java's fixed-width integer.

**Space Complexity: O(1)**

Only one additional variable, `x`, is used.

---

## 9. Pattern Recognition

**Pattern: XOR Adjacent Bits + Consecutive Set Bit Validation**

This technique is useful when:
- You need to compare adjacent binary bits.
- You need to verify alternating bit patterns.
- You need to transform a binary pattern into consecutive set bits.
- You need to validate a sequence of bits using bitwise properties rather than checking each position individually.

The key transformation is:

```text
n ^ (n >> 1)
```

For an alternating binary number, this produces consecutive set bits.

---

## 10. Final Mental Model

Think of this problem in two stages.

**Stage 1: Compare neighboring bits**

```text
x = n ^ (n >> 1)
```

If every adjacent pair is different, the result contains consecutive `1`s.

**Stage 2: Validate the resulting pattern**

```text
(x & (x + 1)) == 0
```

This checks whether all the set bits are consecutive, starting from the rightmost position.

Complete logic:

```text
Shift the number right by one

XOR original number with shifted number

Check whether:
    x & (x + 1) == 0

If true:
    Alternating bits

Otherwise:
    Not alternating
```

**The main takeaway:** XOR can compare adjacent bits when a number is XORed with its right-shifted version. For alternating bits, every comparison produces `1`, creating consecutive set bits. The expression `x & (x + 1)` validates that pattern efficiently.
# WIN #139 — 69. Sqrt(x)

[LeetCode Problem](https://leetcode.com/problems/sqrtx/)

**Difficulty:** Easy

**Topics:** Math, Binary Search

---

## Problem Statement

Given a non-negative integer `x`, return the square root of `x` rounded down to the nearest integer.

The returned integer must be non-negative.

You must not use any built-in exponent function or operator.

### Example 1

**Input:**
```text
x = 4
```

**Output:**
```text
2
```

**Explanation:**
The square root of 4 is 2, so we return 2.

### Example 2

**Input:**
```text
x = 8
```

**Output:**
```text
2
```

**Explanation:**
The square root of 8 is approximately 2.82842.

Since we round down to the nearest integer, the answer is 2.

---

## Core Idea

The key observation is that the square root of a number can be found using Binary Search.

We need to find the largest integer `mid` satisfying:

\[
mid^2 \leq x
\]

Since the square function is increasing for non-negative integers, we can eliminate half of the search space in every iteration.

We maintain two boundaries:

- `low`: The smallest possible candidate.
- `high`: The largest possible candidate.

For every middle value:

1. If `mid * mid == x`, return `mid`.
2. If `mid * mid < x`, the square root must be greater than `mid`, so move right.
3. If `mid * mid > x`, the square root must be smaller than `mid`, so move left.

When the search ends, `high` represents the largest integer whose square is less than or equal to `x`.

---

## Java Solution — Binary Search

```java
class Solution {
    public int mySqrt(int x) {
        int low = 0;
        int high = x;

        while(low <= high){
            int mid = low + (high - low) / 2;
            long square = (long) mid * mid;

            if(square == x){
                return mid;
            }else if(square < x){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return high;
    }
}
```

---

## Step-by-Step Explanation

### 1. Initialize the search boundaries

```java
int low = 0;
int high = x;
```

The square root of a non-negative integer cannot be greater than the number itself.

Therefore, our initial search range is:

\[
[0,x]
\]

### 2. Calculate the middle value

```java
int mid = low + (high - low) / 2;
```

We calculate the middle element of the current search range.

This formula avoids potential integer overflow compared with:

```java
int mid = (low + high) / 2;
```

### 3. Calculate the square safely

```java
long square = (long) mid * mid;
```

This is an important detail in your implementation.

If we multiply two `int` values directly, the result can overflow the 32-bit integer range.

By converting `mid` to `long` before multiplication, Java performs the multiplication using 64-bit arithmetic.

### 4. Compare the square with x

**Case 1: Exact square root**

```java
if(square == x){
    return mid;
}
```

If the square equals `x`, we have found the exact square root.

**Case 2: Square is smaller than x**

```java
else if(square < x){
    low = mid + 1;
}
```

If:

\[
mid^2 < x
\]

The square root could be larger.

We eliminate the current value and everything smaller:

```java
low = mid + 1;
```

**Case 3: Square is greater than x**

```java
else{
    high = mid - 1;
}
```

If:

\[
mid^2 > x
\]

The current value is too large.

We eliminate it and everything larger:

```java
high = mid - 1;
```

### 5. Return high

```java
return high;
```

This is the most important part of the solution.

When the loop terminates:

```text
low > high
```

At this point:

- `low` is the smallest integer whose square is greater than `x`.
- `high` is the largest integer whose square is less than or equal to `x`.

Therefore, `high` is the integer square root rounded down.

---

## Dry Run

Consider:

```text
x = 8
```

Initially:

```text
low = 0
high = 8
```

| Iteration | Low | High | Mid | Mid² | Decision |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 8 | 4 | 16 | Square too large, move left |
| 2 | 0 | 3 | 1 | 1 | Square too small, move right |
| 3 | 2 | 3 | 2 | 4 | Square too small, move right |
| 4 | 3 | 3 | 3 | 9 | Square too large, move left |

After iteration 4:

```text
low = 3
high = 2
```

The loop terminates because:

```text
low > high
```

Return:

```text
high = 2
```

**Output:**
```text
2
```

Because:

\[
2^2 \leq 8 < 3^2
\]

The integer square root is 2.

---

## Understanding Why We Return High

Consider:

```text
x = 10
```

The square root is approximately 3.162.

The largest integer satisfying:

\[
mid^2 \leq 10
\]

is 3.

At the end of Binary Search:

```text
low = 4
high = 3
```

Therefore:

- `high = 3` is the valid answer.
- `low = 4` is too large because \(4^2 = 16\).

This is why we return `high`, not `low`.

---

## Edge Cases

| Input | Output | Explanation |
|---|---:|---|
| `0` | 0 | Square root of zero |
| `1` | 1 | Exact square |
| `4` | 2 | Exact square |
| `8` | 2 | Floor of approximately 2.828 |
| `15` | 3 | Floor of approximately 3.873 |
| `16` | 4 | Exact square |
| `2147483647` | 46340 | Maximum Java integer input |

---

## Complexity Analysis

Let `x` be the input number.

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(log x) | The search space is divided approximately in half in every iteration. |
| Space | O(1) | Only a fixed number of variables are maintained. |

---

## Pattern Recognition

When a problem involves:

- Finding the square root of a number.
- Finding the maximum integer satisfying a mathematical condition.
- Searching over a monotonically increasing range.
- Returning a floor or ceiling value.

Think about **Binary Search on the Answer**.

The important idea is to convert the problem into a condition:

\[
mid^2 \leq x
\]

Then use Binary Search to find the largest valid value.

---

## Key Takeaway

Binary Search is not limited to searching for an existing element in an array.

It can also be applied to mathematical problems where the answer lies within a monotonic search space.

For this problem, we search for the largest integer satisfying:

\[
mid^2 \leq x
\]

The final `high` pointer gives the required integer square root.

**Final complexity: O(log x) time and O(1) extra space.**
# WIN #135 — 1442. Count Triplets That Can Form Two Arrays of Equal XOR

[LeetCode Problem](https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/)

**Difficulty:** Medium

**Topics:** Array, Math, Bit Manipulation

---

## Problem Statement

Given an array of integers `arr`.

We want to choose three indices `i`, `j`, and `k` such that:

- `0 <= i < j <= k < arr.length`
- The XOR of `arr[i]` through `arr[j - 1]` is equal to the XOR of `arr[j]` through `arr[k]`.

In other words:

\[
arr[i] \oplus \cdots \oplus arr[j-1]
=
arr[j] \oplus \cdots \oplus arr[k]
\]

Return the number of triplets `(i, j, k)` that satisfy this condition.

### Example 1

**Input:**
```text
arr = [2,3,1,6,7]
```

**Output:**
```text
4
```

**Explanation:**

The four valid triplets are:

```text
(i, j, k) = (0, 2, 2)
(i, j, k) = (0, 2, 4)
(i, j, k) = (0, 3, 3)
(i, j, k) = (2, 3, 3)
```

### Example 2

**Input:**
```text
arr = [1,1,1,1,1]
```

**Output:**
```text
10
```

---

## Core Idea

The key observation is that:

\[
a \oplus b = c
\]

can be rearranged using XOR properties.

For the problem, we need:

\[
XOR(i,j-1) = XOR(j,k)
\]

Taking XOR on both sides:

\[
XOR(i,j-1) \oplus XOR(j,k) = 0
\]

Since the two adjacent ranges together form one continuous subarray:

\[
XOR(i,k) = 0
\]

Therefore, **whenever the XOR of a subarray from index `i` to index `k` is zero, every possible split position `j` between them produces a valid triplet.**

The number of possible split positions is:

\[
k-i
\]

This is exactly what your implementation adds to the answer.

---

## Java Solution — Subarray XOR

```java
class Solution {
    public int countTriplets(int[] arr) {

        int count = 0;

        for(int i = 0; i < arr.length; i++){
            int xor = 0;

            for(int k = i; k < arr.length; k++){
                xor = xor ^ arr[k];

                if(xor == 0){
                    count += (k - i);
                }
            }
        }

        return count;
    }
}
```

---

## Step-by-Step Explanation

### 1. Initialize the answer

```java
int count = 0;
```

This variable stores the total number of valid triplets.

### 2. Choose the starting index

```java
for(int i = 0; i < arr.length; i++){
    int xor = 0;
```

The outer loop selects the starting index of the subarray.

We reset `xor` to zero for every new starting index.

### 3. Expand the subarray

```java
for(int k = i; k < arr.length; k++){
    xor = xor ^ arr[k];
```

The inner loop expands the subarray from `i` to `k`.

With each iteration, we include the next element in the running XOR.

This avoids recalculating the XOR of the entire subarray from scratch.

### 4. Check whether the XOR is zero

```java
if(xor == 0){
    count += (k - i);
}
```

When the XOR of the subarray is zero:

\[
XOR(i,k) = 0
\]

Every split position `j` satisfying:

\[
i < j \leq k
\]

produces a valid triplet.

The number of possible values of `j` is:

\[
k-i
\]

Therefore, we add:

```java
count += (k - i);
```

### 5. Return the count

```java
return count;
```

After examining every possible starting and ending index, we return the total number of valid triplets.

---

## Dry Run

Consider:

```text
arr = [2,3,1,6,7]
```

We calculate the running XOR for every starting index.

| Starting index `i` | Ending index `k` | Subarray | Running XOR | Contribution |
|---:|---:|---|---:|---:|
| 0 | 0 | [2] | 2 | 0 |
| 0 | 1 | [2,3] | 1 | 0 |
| 0 | 2 | [2,3,1] | 0 | 2 |
| 0 | 3 | [2,3,1,6] | 6 | 0 |
| 0 | 4 | [2,3,1,6,7] | 1 | 0 |
| 1 | 1 | [3] | 3 | 0 |
| 1 | 2 | [3,1] | 2 | 0 |
| 1 | 3 | [3,1,6] | 4 | 0 |
| 1 | 4 | [3,1,6,7] | 3 | 0 |
| 2 | 2 | [1] | 1 | 0 |
| 2 | 3 | [1,6] | 7 | 0 |
| 2 | 4 | [1,6,7] | 0 | 2 |
| 3 | 3 | [6] | 6 | 0 |
| 3 | 4 | [6,7] | 1 | 0 |
| 4 | 4 | [7] | 7 | 0 |

### Valid subarrays

**Subarray 1:**

```text
i = 0, k = 2
Subarray = [2,3,1]

XOR = 2 ^ 3 ^ 1 = 0
```

Number of valid split positions:

\[
k-i = 2-0 = 2
\]

Valid triplets:

```text
(0,1,2)
(0,2,2)
```

**Subarray 2:**

```text
i = 2, k = 4
Subarray = [1,6,7]

XOR = 1 ^ 6 ^ 7 = 0
```

Number of valid split positions:

\[
k-i = 4-2 = 2
\]

Valid triplets:

```text
(2,3,4)
(2,4,4)
```

Total:

\[
2+2=4
\]

**Output:**
```text
4
```

---

## Complexity Analysis

Let `n` be the length of the array.

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(n²) | Two nested loops examine every possible starting and ending index. |
| Space | O(1) | Only a few variables are maintained. |

---

## Pattern Recognition

When a problem involves:

- XOR of contiguous subarrays.
- Splitting an array into two adjacent parts.
- Equality between two XOR expressions.
- Counting valid index combinations.

Think about **Subarray XOR Properties**.

The important transformation is:

\[
XOR(i,j-1) = XOR(j,k)
\]

which implies:

\[
XOR(i,k) = 0
\]

Once a zero-XOR subarray is found, the number of valid split positions is simply:

\[
k-i
\]

This eliminates the need for a third nested loop.

---

## Key Takeaway

The most important insight is that two adjacent subarrays have equal XOR if and only if their combined subarray has XOR zero.

Instead of checking every possible triplet, we:

1. Fix a starting index.
2. Expand the ending index while maintaining a running XOR.
3. Whenever the XOR becomes zero, add `k - i` to the answer.

This reduces a straightforward three-loop solution to **O(n²) time and O(1) extra space**.
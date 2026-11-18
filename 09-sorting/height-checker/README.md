# WIN #141 — 1051. Height Checker

[LeetCode Problem](https://leetcode.com/problems/height-checker/)

**Difficulty:** Easy

**Topics:** Array, Sorting, Counting Sort

---

## Problem Statement

A school is trying to take a photo of all students.

The students are asked to stand in a single line in non-decreasing order of height.

Let this ordering be represented by the array `expected`, where `expected[i]` is the expected height of the student at position `i`.

You are given an integer array `heights` representing the current order of students.

Return the number of indices where:

\[
heights[i] \neq expected[i]
\]

### Example 1

**Input:**
```text
heights = [1,1,4,2,1,3]
```

**Output:**
```text
3
```

**Explanation:**

Expected order:

```text
[1,1,1,2,3,4]
```

Compare the arrays:

| Index | Actual | Expected | Mismatch |
|---|---:|---:|---|
| 0 | 1 | 1 | No |
| 1 | 1 | 1 | No |
| 2 | 4 | 1 | Yes |
| 3 | 2 | 2 | No |
| 4 | 1 | 3 | Yes |
| 5 | 3 | 4 | Yes |

There are 3 mismatched positions.

### Example 2

**Input:**
```text
heights = [5,1,2,3,4]
```

**Output:**
```text
5
```

### Example 3

**Input:**
```text
heights = [1,2,3,4,5]
```

**Output:**
```text
0
```

---

## Core Idea

The key observation is that we do not need to use a comparison-based sorting algorithm.

The problem constraints specify that heights are between `1` and `100`.

This allows us to use a frequency array to count how many times each height appears.

We then reconstruct the expected sorted order by traversing the frequency array in ascending order.

While reconstructing the sorted order, we compare each expected height with the corresponding element in the original array.

If they are different, we increment the mismatch counter.

This gives us the answer without creating a separate sorted array.

---

## Java Solution — Frequency Counting

```java
class Solution {
    public int heightChecker(int[] heights) {

        int[] frequency = new int[101];

        for(int height: heights){
            frequency[height]++;
        }

        int idx = 0;
        int count = 0;

        for(int num = 1; num <= 100; num++){
            if(frequency[num] > 0){
                while(frequency[num] > 0){
                    if(heights[idx] != num){
                        count++;
                    }
                    idx++;
                    frequency[num]--;
                }
            }
        }

        return count;
    }
}
```

---

## Step-by-Step Explanation

### 1. Create the frequency array

```java
int[] frequency = new int[101];
```

Since the height values range from `1` to `100`, we create a frequency array of size `101`.

The index represents the height, and the value represents its occurrence count.

For example:

```text
heights = [1,1,4,2,1,3]
```

Frequency array:

| Height | Frequency |
|---|---:|
| 1 | 3 |
| 2 | 1 |
| 3 | 1 |
| 4 | 1 |

### 2. Count the occurrences

```java
for(int height: heights){
    frequency[height]++;
}
```

We traverse the original array and increment the frequency of every height.

This gives us the information needed to reconstruct the sorted order.

### 3. Initialize the comparison variables

```java
int idx = 0;
int count = 0;
```

- `idx` tracks the current position in the original array.
- `count` stores the number of mismatched positions.

### 4. Reconstruct the sorted order

```java
for(int num = 1; num <= 100; num++){
```

We traverse possible heights from `1` to `100`.

Because we process them in ascending order, the expected sorted order is generated automatically.

### 5. Process each height according to its frequency

```java
while(frequency[num] > 0){
```

If a height occurs multiple times, we process it once for every occurrence.

For example:

```text
frequency[1] = 3
```

The expected sorted array contains three occurrences of `1`.

### 6. Compare expected and actual positions

```java
if(heights[idx] != num){
    count++;
}
```

Here:

- `heights[idx]` is the original height at the current position.
- `num` is the expected height in sorted order.

If they differ, that position is incorrect, so we increment `count`.

After every comparison:

```java
idx++;
frequency[num]--;
```

We move to the next original position and decrease the remaining frequency.

### 7. Return the mismatch count

```java
return count;
```

After processing every height, `count` represents the number of positions that differ from the expected sorted arrangement.

---

## Dry Run

Consider:

```text
heights = [1,1,4,2,1,3]
```

### Step 1: Build the frequency array

| Height | Frequency |
|---|---:|
| 1 | 3 |
| 2 | 1 |
| 3 | 1 |
| 4 | 1 |

### Step 2: Reconstruct and compare

Expected sorted order:

```text
[1,1,1,2,3,4]
```

We compare each expected height against the original array.

| `idx` | Original `heights[idx]` | Expected `num` | Comparison | Count |
|---:|---:|---:|---|---:|
| 0 | 1 | 1 | Match | 0 |
| 1 | 1 | 1 | Match | 0 |
| 2 | 4 | 1 | Mismatch | 1 |
| 3 | 2 | 2 | Match | 1 |
| 4 | 1 | 3 | Mismatch | 2 |
| 5 | 3 | 4 | Mismatch | 3 |

Final result:

```text
count = 3
```

**Output:**
```text
3
```

---

## Complexity Analysis

Let `n` be the length of the `heights` array and `K` be the maximum possible height (`100`).

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(n + K) | One traversal to count frequencies and another to reconstruct and compare the sorted order. |
| Space | O(K) | A frequency array of size 101 is used. |

Since the height range is fixed:

- Time complexity simplifies to O(n).
- Auxiliary space is O(1).

---

## Pattern Recognition

When a problem involves:

- Sorting integers within a small, fixed range.
- Comparing the original arrangement with the sorted arrangement.
- Counting positions that differ.
- Repeated values.

Think about **Frequency Counting / Counting Sort**.

The approach is:

1. Count the frequency of each possible value.
2. Traverse values in ascending order.
3. Reconstruct the expected sorted sequence.
4. Compare each expected value with the original array.
5. Count the mismatches.

A separate sorted array is not necessary if the comparison can be performed while reconstructing the sorted order.

---

## Key Takeaway

Frequency counting can be used not only to sort arrays but also to solve problems that depend on the expected sorted arrangement.

Instead of sorting the original array, your solution reconstructs the sorted order directly from the frequency array and compares each position.

**Final complexity: O(n + 100) time and O(100) auxiliary space, which simplifies to O(n) time and O(1) auxiliary space for the fixed height range.**
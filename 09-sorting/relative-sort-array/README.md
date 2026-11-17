# WIN #138 — 1122. Relative Sort Array

[LeetCode Problem](https://leetcode.com/problems/relative-sort-array/)

**Difficulty:** Easy

**Topics:** Array, Hash Table, Sorting, Counting Sort

---

## Problem Statement

Given two arrays `arr1` and `arr2`, sort the elements of `arr1` such that:

1. The relative ordering of the elements in `arr1` is the same as their ordering in `arr2`.
2. Elements that do not appear in `arr2` must be placed at the end of `arr1` in ascending order.

Return the resulting array.

### Example 1

**Input:**
```text
arr1 = [2,3,1,3,2,4,6,7,9,2,19]
arr2 = [2,1,4,3,9,6]
```

**Output:**
```text
[2,2,2,1,4,3,3,9,6,7,19]
```

**Explanation:**

- `2` appears three times and comes first because it is first in `arr2`.
- `1` appears once.
- `4` appears once.
- `3` appears twice.
- `9` and `6` follow their order in `arr2`.
- The remaining elements `7` and `19` are sorted in ascending order.

### Example 2

**Input:**
```text
arr1 = [28,6,22,8,44,17]
arr2 = [22,28,8,6]
```

**Output:**
```text
[22,28,8,6,17,44]
```

---

## Core Idea

The key observation is that the problem does not require a traditional sorting algorithm.

We can use a frequency array to count how many times each number appears in `arr1`.

Since the problem constraints limit the values to the range `0` through `1000`, we can create a frequency array of size `1001`.

The algorithm has three main steps:

1. Count the frequency of every element in `arr1`.
2. Traverse `arr2` and insert each element into the result according to its frequency.
3. Traverse the frequency array in ascending order and append all remaining elements.

This naturally produces the required relative ordering.

---

## Java Solution — Frequency Counting

```java
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] frequency = new int[1001];

        for(int arr: arr1){
            frequency[arr]++;
        }

        int[] result = new int[arr1.length];
        int i = 0;

        for(int arr: arr2){
            if(frequency[arr] > 0){
                while(frequency[arr] > 0){
                    result[i++] = arr;
                    frequency[arr]--;
                }
            }
        }

        for(int j = 0; j < frequency.length; j++){
            while(frequency[j] > 0){
                result[i++] = j;
                frequency[j]--;
            }
        }

        return result;
    }
}
```

---

## Step-by-Step Explanation

### 1. Create the frequency array

```java
int[] frequency = new int[1001];
```

We create an array to store the occurrence count of every possible value.

The index represents the number, and the value at that index represents its frequency.

For example:

```text
frequency[2] = 3
frequency[3] = 2
frequency[4] = 1
```

This means:

- `2` appears three times.
- `3` appears twice.
- `4` appears once.

### 2. Count occurrences in arr1

```java
for(int arr: arr1){
    frequency[arr]++;
}
```

We traverse `arr1` and increment the frequency of each element.

For example:

```text
arr1 = [2,3,1,3,2,4]
```

After counting:

| Number | Frequency |
|---|---:|
| 1 | 1 |
| 2 | 2 |
| 3 | 2 |
| 4 | 1 |

### 3. Create the result array

```java
int[] result = new int[arr1.length];
int i = 0;
```

- `result` stores the final sorted array.
- `i` tracks the next available position in the result array.

### 4. Process elements according to arr2

```java
for(int arr: arr2){
    if(frequency[arr] > 0){
        while(frequency[arr] > 0){
            result[i++] = arr;
            frequency[arr]--;
        }
    }
}
```

We traverse `arr2` in its original order.

For every element:

1. Check whether its frequency is greater than zero.
2. Insert it into the result as many times as it appears.
3. Decrease its frequency after every insertion.

For example:

```text
arr2 = [2,1,4,3]
```

The result is constructed as:

```text
[2,2,1,4,3,3]
```

After processing these elements, their frequencies become zero.

### 5. Append the remaining elements in ascending order

```java
for(int j = 0; j < frequency.length; j++){
    while(frequency[j] > 0){
        result[i++] = j;
        frequency[j]--;
    }
}
```

Now we traverse the frequency array from index `0` to `1000`.

Any remaining positive frequency represents an element that does not appear in `arr2`.

Because we traverse the frequency array in ascending order, these remaining elements are automatically inserted in ascending order.

For example:

```text
Remaining elements = [7,19]
```

They are appended to the result.

### 6. Return the result

```java
return result;
```

The result now satisfies both ordering conditions.

---

## Dry Run

Consider:

```text
arr1 = [2,3,1,3,2,4,6,7,9,2,19]
arr2 = [2,1,4,3,9,6]
```

### Step 1: Build the frequency array

Relevant frequencies:

| Number | Frequency |
|---|---:|
| 1 | 1 |
| 2 | 3 |
| 3 | 2 |
| 4 | 1 |
| 6 | 1 |
| 7 | 1 |
| 9 | 1 |
| 19 | 1 |

### Step 2: Process arr2

We follow the order of `arr2`.

| Element | Frequency | Inserted elements |
|---|---:|---|
| 2 | 3 | 2, 2, 2 |
| 1 | 1 | 1 |
| 4 | 1 | 4 |
| 3 | 2 | 3, 3 |
| 9 | 1 | 9 |
| 6 | 1 | 6 |

Result so far:

```text
[2,2,2,1,4,3,3,9,6]
```

### Step 3: Process remaining elements

The remaining elements are:

```text
7, 19
```

We traverse the frequency array in ascending order and append them.

Final result:

```text
[2,2,2,1,4,3,3,9,6,7,19]
```

**Output:**
```text
[2,2,2,1,4,3,3,9,6,7,19]
```

---

## Complexity Analysis

Let:

- `n` = length of `arr1`
- `m` = length of `arr2`
- `K` = size of the frequency array (`1001`)

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(n + m + K) | Count frequencies, process `arr2`, and traverse the frequency array. |
| Space | O(n + K) | Result array requires O(n), and the frequency array requires O(K). |

Since `K = 1001` is fixed by the problem constraints, the time complexity simplifies to O(n + m), and auxiliary space excluding the output array is O(1).

---

## Pattern Recognition

When a problem involves:

- Sorting integers within a small, known value range.
- Following a custom ordering defined by another array.
- Handling repeated elements.
- Placing remaining elements in ascending order.

Think about **Frequency Counting / Counting Sort**.

The important approach is:

1. Use array indices to represent possible values.
2. Store their occurrence counts.
3. Reconstruct the output using the required ordering.
4. Traverse the frequency array to handle remaining values.

This avoids comparison-based sorting.

---

## Key Takeaway

Frequency counting is especially useful when the input values belong to a limited range.

Instead of sorting the entire array, we count occurrences and reconstruct the result in the required order.

Your solution uses:

- Frequency counting to track occurrences.
- `arr2` to establish the relative ordering.
- An ascending frequency-array traversal to place remaining elements.

**Final complexity: O(n + m + 1001) time and O(n + 1001) total space.**
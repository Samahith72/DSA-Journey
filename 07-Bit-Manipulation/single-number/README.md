# WIN #120 — 136. Single Number

**LeetCode:** [136. Single Number](https://leetcode.com/problems/single-number/)  
**Difficulty:** Easy  
**Topics:** Array, Bit Manipulation

---

## 1. Problem Statement

Given a non-empty array of integers `nums`, every element appears twice except for one element that appears exactly once.

Find the element that appears only once.

You must implement a solution with linear runtime complexity and constant extra space.

### Examples

**Example 1**

Input:
```text
nums = [2,2,1]
```

Output:
```text
1
```

**Example 2**

Input:
```text
nums = [4,1,2,1,2]
```

Output:
```text
4
```

**Example 3**

Input:
```text
nums = [1]
```

Output:
```text
1
```

---

## 2. Core Idea — XOR (Bit Manipulation)

The key to this problem is understanding the XOR operator (`^`).

XOR compares two binary numbers bit by bit.

A bit becomes `1` when the corresponding bits are different. Otherwise, it becomes `0`.

### XOR Truth Table

| A | B | A XOR B |
|---|---|---|
| 0 | 0 | 0 |
| 0 | 1 | 1 |
| 1 | 0 | 1 |
| 1 | 1 | 0 |

### Three Important XOR Properties

**Property 1: XOR with itself produces zero**

```text
a ^ a = 0
```

Example:

```text
5 ^ 5 = 0
```

**Property 2: XOR with zero produces the same number**

```text
a ^ 0 = a
```

Example:

```text
5 ^ 0 = 5
```

**Property 3: XOR is commutative and associative**

Commutative:

```text
a ^ b = b ^ a
```

Associative:

```text
(a ^ b) ^ c = a ^ (b ^ c)
```

This means we can change the order of XOR operations without changing the final result.

### Why does XOR solve this problem?

Every number that appears twice cancels itself out.

Only the number that appears once remains.

For example:

```text
[4,1,2,1,2]
```

Applying XOR:

```text
4 ^ 1 ^ 2 ^ 1 ^ 2
```

Rearrange using commutative and associative properties:

```text
4 ^ (1 ^ 1) ^ (2 ^ 2)
```

Cancel the duplicates:

```text
4 ^ 0 ^ 0
```

Final result:

```text
4
```

---

## 3. Java Solution

```java
class Solution {
    public int singleNumber(int[] nums) {

        int result = 0;

        for(int num : nums){
            result ^= num;
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

We initialize the result with zero because XOR with zero does not change a number.

### Step 2: Traverse the array

```java
for(int num : nums){
    result ^= num;
}
```

Visit every element and XOR it with the current result.

The shorthand:

```java
result ^= num;
```

is equivalent to:

```java
result = result ^ num;
```

Whenever a duplicate number appears, its two occurrences cancel each other.

### Step 3: Return the remaining number

```java
return result;
```

After processing all elements, every duplicate has cancelled out.

The remaining value is the number that appears exactly once.

---

## 5. Dry Run

Let's trace the input:

```text
nums = [4,1,2,1,2]
```

Initial state:

```text
result = 0
```

| Iteration | Current Number | Operation | Result |
|---|---:|---|---:|
| 1 | 4 | 0 ^ 4 | 4 |
| 2 | 1 | 4 ^ 1 | 5 |
| 3 | 2 | 5 ^ 2 | 7 |
| 4 | 1 | 7 ^ 1 | 6 |
| 5 | 2 | 6 ^ 2 | 4 |

Final result:

```text
4
```

The duplicate numbers `1` and `2` cancel each other.

Only `4` remains.

---

## 6. Understanding XOR at the Binary Level

Let's understand why duplicates disappear.

Consider:

```text
4 ^ 4
```

Binary representation:

```text
4 = 100
4 = 100
---------
    000
```

Therefore:

```text
4 ^ 4 = 0
```

Now consider:

```text
4 ^ 0
```

Binary representation:

```text
4 = 100
0 = 000
---------
    100
```

Therefore:

```text
4 ^ 0 = 4
```

This is the mathematical foundation of your solution.

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `[1]` | 1 | Only one element |
| `[2,2,1]` | 1 | Duplicate cancels |
| `[4,1,2,1,2]` | 4 | Multiple duplicate pairs |
| `[-1,-1,-2]` | -2 | XOR also works with negative integers |
| `[0,1,1]` | 0 | Zero remains after cancellation |

---

## 8. Complexity Analysis

Let `n` be the length of the input array.

**Time Complexity: O(n)**

- We traverse the array exactly once.
- Each XOR operation takes constant time.

Therefore, time complexity is O(n).

**Space Complexity: O(1)**

- We use only one additional variable, `result`.
- No additional data structures are required.

Therefore, auxiliary space complexity is O(1).

---

## 9. Pattern Recognition

**Pattern: XOR-Based Cancellation**

This technique is useful when:
- Elements appear in pairs.
- One element has a different frequency.
- You need a constant-space solution.
- Sorting or using a HashMap would introduce unnecessary overhead.

The key observation is that XOR naturally cancels identical values.

---

## 10. Final Mental Model

Think of XOR as a cancellation operation.

```text
a ^ a = 0
a ^ 0 = a
```

Every duplicate pair disappears, and the unique number survives.

The entire algorithm is:

```text
Initialize result = 0

For every number:
    result = result XOR number

Return result
```

**The main takeaway:** XOR allows us to identify the unique element without sorting, hashing, or nested loops. Understanding its cancellation properties is the foundation for solving more advanced Bit Manipulation problems.
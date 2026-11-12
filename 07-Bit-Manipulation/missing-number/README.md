# WIN #125 — 268. Missing Number

**LeetCode:** [268. Missing Number](https://leetcode.com/problems/missing-number/)  
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Math, Bit Manipulation, Sorting

---

## 1. Problem Statement

Given an array `nums` containing `n` distinct numbers in the range `[0, n]`, return the only number in the range that is missing from the array.

### Examples

**Example 1**

Input:
```text
nums = [3,0,1]
```

Output:
```text
2
```

Explanation: The numbers in the range `[0,3]` are `[0,1,2,3]`. The number `2` is missing.

**Example 2**

Input:
```text
nums = [0,1]
```

Output:
```text
2
```

**Example 3**

Input:
```text
nums = [9,6,4,2,3,5,7,0,1]
```

Output:
```text
8
```

---

## 2. Core Idea — Mathematical Sum Formula

Your implemented solution uses the mathematical property that the sum of the first `n` natural numbers starting from zero is:

\[
S = \frac{n(n+1)}{2}
\]

Since exactly one number is missing:

\[
Missing = ExpectedSum - ActualSum
\]

For example:

```text
nums = [3,0,1]
```

The expected range is:

```text
[0,1,2,3]
```

Expected sum:

```text
0 + 1 + 2 + 3 = 6
```

Actual sum:

```text
3 + 0 + 1 = 4
```

Missing number:

```text
6 - 4 = 2
```

This allows us to find the missing number without sorting or using additional data structures.

---

## 3. Java Solution

### Approach 1: Mathematical Sum (Your Implemented Solution)

```java
class Solution {
    public int missingNumber(int[] nums) {

        int n = nums.length;
        int sum = 0;

        for(int num: nums){
            sum += num;
        }

        int s = (n * (n+1))/2;
        return s - sum;
    }
}
```

### Approach 2: XOR Cancellation (Your Alternative)

You also explored this approach in your commented-out code:

```java
int result = 0;

for(int i = 0; i <= nums.length; i++){
    result = result ^ i;
}

for(int num: nums){
    result = result ^ num;
}

return result;
```

This approach uses the XOR cancellation property:

```text
a ^ a = 0
a ^ 0 = a
```

We XOR all numbers in the expected range and all numbers in the given array.

Every number appearing in both sets cancels out. Only the missing number remains.

---

## 4. Step-by-Step Explanation — Mathematical Approach

### Step 1: Get the array length

```java
int n = nums.length;
```

If the array contains `n` elements, the complete range contains `n + 1` numbers.

For example:

```text
nums = [3,0,1]
n = 3

Expected range = [0,1,2,3]
```

### Step 2: Calculate the actual sum

```java
int sum = 0;

for(int num: nums){
    sum += num;
}
```

Traverse the array and calculate the sum of all available numbers.

### Step 3: Calculate the expected sum

```java
int s = (n * (n+1))/2;
```

Apply the formula:

\[
S = \frac{n(n+1)}{2}
\]

This gives the sum of every number from `0` to `n`.

### Step 4: Find the missing number

```java
return s - sum;
```

Subtract the actual sum from the expected sum.

The remaining value is the missing number.

---

## 5. Dry Run — Mathematical Approach

Input:

```text
nums = [3,0,1]
```

Initial state:

```text
n = 3
sum = 0
```

### Calculate actual sum

| Iteration | Number | Calculation | Sum |
|---|---:|---|---:|
| 1 | 3 | 0 + 3 | 3 |
| 2 | 0 | 3 + 0 | 3 |
| 3 | 1 | 3 + 1 | 4 |

Actual sum:

```text
sum = 4
```

### Calculate expected sum

```text
s = n * (n+1) / 2

s = 3 * 4 / 2

s = 6
```

### Calculate missing number

```text
missing = s - sum

missing = 6 - 4

missing = 2
```

Final output:

```text
2
```

---

## 6. Understanding the XOR Alternative

Let's understand your commented-out approach.

Input:

```text
nums = [3,0,1]
```

Expected range:

```text
[0,1,2,3]
```

First, XOR every number from `0` to `n`:

```text
result = 0 ^ 0 ^ 1 ^ 2 ^ 3
```

Then XOR every element in the array:

```text
result = result ^ 3 ^ 0 ^ 1
```

Combine everything:

```text
result = 0 ^ 1 ^ 2 ^ 3 ^ 3 ^ 0 ^ 1
```

Rearrange:

```text
result = (0 ^ 0) ^ (1 ^ 1) ^ (3 ^ 3) ^ 2
```

Cancel duplicates:

```text
result = 0 ^ 0 ^ 0 ^ 2
```

Final result:

```text
2
```

The missing number survives because it appears only in the expected range.

---

## 7. Comparing Both Approaches

| Feature | Mathematical Sum | XOR |
|---|---|---|
| Technique | Arithmetic formula | Bit manipulation |
| Time Complexity | O(n) | O(n) |
| Space Complexity | O(1) | O(1) |
| Main operation | Addition and subtraction | XOR cancellation |
| Overflow consideration | Possible with large sums | Avoids sum overflow |

Both approaches are valid for this problem.

**Important Java detail:** Your mathematical implementation uses `int` for the expected sum. Under the problem's constraints, this is safe. For larger integer ranges, consider using `long` to prevent arithmetic overflow.

---

## 8. Important Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `[0]` | 1 | Missing largest number |
| `[1]` | 0 | Missing zero |
| `[0,1]` | 2 | Missing largest number |
| `[3,0,1]` | 2 | Missing middle number |
| `[9,6,4,2,3,5,7,0,1]` | 8 | Missing number from a larger range |

---

## 9. Complexity Analysis

### Mathematical Approach

**Time Complexity: O(n)**

- One traversal to calculate the actual sum.
- Constant-time arithmetic to calculate the expected sum and difference.

**Space Complexity: O(1)**

Only two additional variables are used.

### XOR Approach

**Time Complexity: O(n)**

- One traversal over the expected range.
- One traversal over the input array.

**Space Complexity: O(1)**

Only one additional variable is required.

---

## 10. Pattern Recognition

**Pattern: Mathematical Invariant + XOR Cancellation**

The mathematical approach is useful when:
- Numbers belong to a continuous range.
- Exactly one number is missing.
- The expected sum can be calculated directly.

The XOR approach is useful when:
- Duplicate values need to cancel out.
- You need to avoid arithmetic sum overflow.
- The problem has a missing or uniquely occurring element.

Both approaches use a property of the complete expected range to identify what is missing.

---

## 11. Final Mental Model

Think of the problem as comparing what should exist with what actually exists.

**Mathematical approach:**

```text
Expected Sum - Actual Sum = Missing Number
```

**XOR approach:**

```text
Expected Range XOR Actual Array = Missing Number
```

The main takeaway: You don't need sorting, nested loops, or a HashSet to find the missing number. A mathematical invariant or XOR cancellation solves the problem in O(n) time and O(1) auxiliary space.

# WIN #155 — 179. Largest Number

[LeetCode Problem](https://leetcode.com/problems/largest-number/)

**Difficulty:** Medium  
**Topics:** Array, String, Greedy, Sorting

---

## Problem Statement

Given a list of non-negative integers `nums`, arrange them so that they form the largest possible number.

Return the result as a string because the number may be too large to fit in an integer.

### Example 1

**Input:**
```text
nums = [10,2]
```

**Output:**
```text
"210"
```

**Explanation:** Placing `2` before `10` produces the largest number: `"210"`.

### Example 2

**Input:**
```text
nums = [3,30,34,5,9]
```

**Output:**
```text
"9534330"
```

**Explanation:** The order `9, 5, 34, 3, 30` produces the largest possible number.

### Example 3

**Input:**
```text
nums = [0,0]
```

**Output:**
```text
"0"
```

**Explanation:** Multiple zeros should return a single `"0"`.

---

## Core Idea

The key is to determine which of two numbers should come first.

For two numbers represented as strings `a` and `b`, compare:

- `a + b`
- `b + a`

If `b + a` is larger, place `b` before `a`. Otherwise, place `a` before `b`.

For example, comparing `"3"` and `"30"`:

```text
"330" > "303"
```

So `"3"` should come before `"30"`.

After sorting all numbers using this rule, concatenate them to build the answer.

---

## Java Solution

```java
class Solution {
    public String largestNumber(int[] nums) {
        String[] arr = new String[nums.length];

        for (int i = 0; i < nums.length; i++) {
            arr[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(arr, (a, b) -> (b + a).compareTo(a + b));

        if (arr[0].equals("0")) {
            return "0";
        }

        StringBuilder result = new StringBuilder();

        for (String s : arr) {
            result.append(s);
        }

        return result.toString();
    }
}
```

---

## Step-by-Step Explanation

### 1. Convert integers to strings

```java
String[] arr = new String[nums.length];

for (int i = 0; i < nums.length; i++) {
    arr[i] = String.valueOf(nums[i]);
}
```

String conversion allows us to compare numbers by concatenating them in different orders.

### 2. Sort using concatenation

```java
Arrays.sort(arr, (a, b) -> (b + a).compareTo(a + b));
```

The comparator checks which concatenation is lexicographically larger.

- If `b + a` is larger, `b` comes before `a`.
- Otherwise, `a` comes before `b`.

This creates the order that produces the largest combined number.

### 3. Handle the all-zero case

```java
if (arr[0].equals("0")) {
    return "0";
}
```

If the first sorted value is `"0"`, all values must be zero. Returning the concatenation would produce something like `"000"`, so the solution returns just `"0"`.

### 4. Build the result

```java
StringBuilder result = new StringBuilder();

for (String s : arr) {
    result.append(s);
}

return result.toString();
```

Append each string in sorted order to form the largest possible number.

---

## Dry Run

**Input:**

```text
nums = [3,30,34,5,9]
```

After converting to strings:

```text
["3", "30", "34", "5", "9"]
```

The comparator orders the strings by comparing concatenations:

| Pair | First concatenation | Second concatenation | Preferred order |
|---|---|---|---|
| `"3"` and `"30"` | `"330"` | `"303"` | `"3"` before `"30"` |
| `"34"` and `"3"` | `"343"` | `"334"` | `"34"` before `"3"` |
| `"9"` and `"5"` | `"95"` | `"59"` | `"9"` before `"5"` |

After sorting:

```text
["9", "5", "34", "3", "30"]
```

Concatenating the strings gives:

```text
"9534330"
```

**Output:** `"9534330"`

---

## Edge Cases

| Input | Output | Reason |
|---|---|---|
| `[0,0]` | `"0"` | Avoids leading zeros |
| `[10,2]` | `"210"` | `"2"` must come before `"10"` |
| `[3,30]` | `"330"` | Compare `"330"` with `"303"` |
| `[1]` | `"1"` | A single number is already the answer |

---

## Complexity Analysis

Let `n` be the number of integers and `k` be the maximum number of digits in any integer.

- **Time Complexity:** `O(n log n × k)` — sorting performs `O(n log n)` comparisons, and each comparison concatenates and compares strings of up to `O(k)` characters. Building the final result takes `O(n × k)`.
- **Space Complexity:** `O(n × k)` — the string array stores the converted numbers, and the result stores their concatenation.

---

## Pattern Recognition

**Greedy sorting with a custom comparator**

Normal numeric sorting does not work because the goal is not to order the numbers by their individual values. Instead, the goal is to maximize the number formed by concatenating them.

The decision for each pair is based on which arrangement produces the larger concatenated value.

---

## Key Takeaway

When arranging numbers to form the largest possible concatenation, compare `a + b` with `b + a`. Sort according to the better concatenation, then join the strings. Always handle the all-zero case to avoid returning leading zeros.

#  WIN #1 — Two Sum

## Problem

Given an array of integers `nums` and an integer `target`, return the indices of the two numbers such that they add up to the target.

Each input has exactly one solution, and the same element cannot be used twice.

---

## My Java Solution

I used the **Brute Force approach**.

The idea is to check every possible pair of elements in the array.

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {

        // Brute Force approach
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {

                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{};
    }
}
```

---

##  My Thought Process

My first thought was:

> "For every number, check whether there is another number that combines with it to reach the target."

So I used two loops.

### Step-by-step:

1. Pick the first number using `i`.
2. Start another pointer `j` from `i + 1`.
3. Check whether:

```text
nums[i] + nums[j] == target
```

4. If the condition is true, return the indices.
5. Otherwise, continue checking the remaining pairs.

Starting `j` from `i + 1` ensures that I don't compare an element with itself or check the same pair twice.

---

## Pattern Recognition

### Pattern: Brute Force

I should recognize this pattern when:

* I need to find two elements.
* I need to check every possible pair.
* The array size is manageable.
* I am solving the problem before optimizing it.

The general idea is:

```text
For each element
    Check every element after it
        If the required condition is satisfied
            Return the answer
```

This is the simplest approach for problems involving finding pairs.

---

## Complexity

### Time Complexity

```text
O(n²)
```

Because for every element, I may need to check almost every remaining element.

### Space Complexity

```text
O(1)
```

Because I am not using any extra data structure.

---

##  Key Learning

Today I learned that before jumping directly to an optimized solution, it is important to first understand the problem using a simple approach.

For pair-based problems:

* Pick one element.
* Compare it with the remaining elements.
* Check whether the required condition is satisfied.

This solution uses the **Brute Force pattern**.

Later, this problem can be optimized using a **HashMap**, reducing the time complexity from:

```text
O(n²) → O(n)
```


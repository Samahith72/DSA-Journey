# WIN #4 — Removing Minimum and Maximum From Array

## Problem

You are given a 0-indexed array of distinct integers.

The array contains one minimum element and one maximum element.

The goal is to remove both the minimum and maximum elements from the array.

A deletion can only be performed in two ways:

1. Remove an element from the front of the array.
2. Remove an element from the back of the array.

Return the minimum number of deletions required to remove both the minimum and maximum elements.

For example:

```text
nums = [2,10,7,5,4,1,8,6]
```

The minimum value is:

```text
1 at index 5
```

The maximum value is:

```text
10 at index 1
```

We need to find the minimum number of deletions required to remove both elements.

---

## My Java Solution

```java
class Solution {
    public int minimumDeletions(int[] nums) {

        if (nums.length == 1) {
            return 1;
        }

        if (nums.length == 2) {
            return 2;
        }

        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;

        int minIndex = 0;
        int maxIndex = 0;

        // Find the minimum and maximum values and their indices
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] < minValue) {
                minIndex = i;
                minValue = nums[i];
            }

            if (nums[i] > maxValue) {
                maxIndex = i;
                maxValue = nums[i];
            }
        }

        // Delete both elements from the front
        int scenario1 = minimumDelete1(nums, minIndex, maxIndex);

        // Delete both elements from the back
        int scenario2 = minimumDelete2(nums, minIndex, maxIndex);

        // Delete one element from the front and one from the back
        int scenario3 = minimumDelete3(nums, minIndex, maxIndex);

        return Math.min(
            Math.min(scenario1, scenario2),
            scenario3
        );
    }

    public int minimumDelete1(int[] arr, int min, int max) {

        int j = 0;
        int count = 0;

        while (j <= min || j <= max) {
            count++;
            j++;
        }

        return count;
    }

    public int minimumDelete2(int[] arr, int min, int max) {

        int j = arr.length - 1;
        int count = 0;

        while (j >= min || j >= max) {
            count++;
            j--;
        }

        return count;
    }

    public int minimumDelete3(int[] arr, int min, int max) {

        int count = 0;

        int i = 0;
        int j = arr.length - 1;

        if (min < max) {

            while (i <= min) {
                count++;
                i++;
            }

            while (j >= max) {
                count++;
                j--;
            }

        } else {

            while (i <= max) {
                count++;
                i++;
            }

            while (j >= min) {
                count++;
                j--;
            }
        }

        return count;
    }
}
```

---

## My Thought Process

The first thing I needed to know was where the minimum and maximum elements were located.

So I first traversed the array and stored:

```text
Minimum value

Maximum value

Index of the minimum value

Index of the maximum value
```

After finding both indices, I realized that there are only three main ways to remove both elements.

### Scenario 1: Remove both elements from the front

If the minimum or maximum element is farther from the front, I need to delete every element until I reach that position.

So the number of deletions is determined by the farther index.

---

### Scenario 2: Remove both elements from the back

Similarly, I can remove elements from the back until both the minimum and maximum elements are deleted.

The number of deletions depends on whichever element is farther from the back.

---

### Scenario 3: Remove one element from the front and one from the back

Sometimes removing both elements from the same side is not the best option.

Instead, I can remove the element closer to the front from the front and the element closer to the back from the back.

I calculate the number of deletions required for this option as well.

Finally, I compare all three scenarios and return the minimum number of deletions.

The overall idea is:

```text
Find minimum and maximum
        ↓
Find their indices
        ↓
Calculate deletions from the front
        ↓
Calculate deletions from the back
        ↓
Calculate deletions using both sides
        ↓
Return the minimum
```

---

## Pattern Recognition

### Pattern: Case Analysis / Greedy

This pattern is useful when there are only a small number of possible strategies to solve a problem.

Instead of trying every possible sequence of operations, I can identify all the meaningful scenarios and calculate the result for each one.

The general approach is:

```text
Identify the important positions or values.

Find all possible meaningful scenarios.

Calculate the cost of each scenario.

Return the minimum or maximum result.
```

For this problem, the important values are:

```text
Minimum element

Maximum element
```

The important information is their positions in the array.

The three scenarios are:

```text
Remove both from the front

Remove both from the back

Remove one from the front and one from the back
```

After calculating all three possibilities:

```text
answer = minimum of all scenarios
```

---

## Complexity

### Time Complexity

```text
O(n)
```

The array is traversed once to find the minimum and maximum values.

The deletion calculations can also take up to `O(n)` time.

Since the operations are performed a constant number of times, the overall time complexity remains:

```text
O(n)
```

### Space Complexity

```text
O(1)
```

Only a fixed number of variables are used.

No extra data structure is created based on the size of the input.

---

## Key Learning

I learned that some problems can look complicated because there are many possible operations, but after analyzing the problem carefully, only a few meaningful cases need to be considered.

In this problem, the important step was not simulating every possible deletion.

Instead, I first found the positions of the minimum and maximum elements.

Then I compared the three possible strategies:

```text
Both from the front

Both from the back

One from each side
```

The key insight was that once the positions of the minimum and maximum elements are known, the rest of the problem becomes a comparison between different deletion strategies.

This problem helped me practice thinking in terms of possible cases and choosing the minimum cost among them.
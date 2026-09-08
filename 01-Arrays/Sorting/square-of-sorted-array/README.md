# WIN #21 — Squares of a Sorted Array

## Problem

Given an integer array `nums` sorted in **non-decreasing order**, return an array containing the **squares of each number**, also sorted in non-decreasing order.

For example:

```text
Input:
nums = [-4,-1,0,3,10]

After squaring:
[16,1,0,9,100]

Output:
[0,1,9,16,100]
```

The challenge is that even though the original array is sorted, squaring the numbers can change their relative order.

For example:

```text
[-4,-1,0,3,10]
```

becomes:

```text
[16,1,0,9,100]
```

So the squared values need to be sorted again.

---

## My Java Solution

```java
class Solution {
    public int[] sortedSquares(int[] nums) {

        for(int i = 0;i<nums.length;i++){
            nums[i] = nums[i] * nums[i];
        }
        Arrays.sort(nums);
        return nums;
    }
}
```

---

## My Thought Process

The first thing I noticed is that I don't need to create a separate array.

The problem allows the result to be returned as an array, and I can directly modify `nums`.

### Step 1: Square every element

I loop through the array and replace every number with its square.

For example:

```text
nums = [-4,-1,0,3,10]
```

After the first loop:

```text
[16,1,0,9,100]
```

At this point, every element is squared, but the array is **not sorted**.

### Step 2: Sort the squared values

I then use:

```java
Arrays.sort(nums);
```

which changes:

```text
[16,1,0,9,100]
```

into:

```text
[0,1,9,16,100]
```

Finally, I return `nums`.

---

## Why Does This Work?

Every element in the original array needs to be squared exactly once.

After squaring all elements, the only remaining requirement is:

> Return the squared values in non-decreasing order.

`Arrays.sort()` guarantees that the resulting array is sorted.

So the solution follows two simple steps:

```text
Original Array
      ↓
Square Every Element
      ↓
Sort the Array
      ↓
Return Result
```

---

## Example Walkthrough

Consider:

```text
nums = [-7,-3,2,3,11]
```

### Step 1 — Square each element

```text
-7² = 49
-3² = 9
 2² = 4
 3² = 9
11² = 121
```

The array becomes:

```text
[49,9,4,9,121]
```

### Step 2 — Sort

```text
[49,9,4,9,121]
```

becomes:

```text
[4,9,9,49,121]
```

### Final Result

```text
[4,9,9,49,121]
```

---

## Pattern Recognition

### Pattern: In-Place Transformation + Sorting

The solution can be recognized as a simple **transform-then-sort** pattern.

We first transform every element:

```text
nums[i] → nums[i]²
```

and then sort the transformed values.

The important observation is that the original sorted order does **not** guarantee sorted squared values.

For example:

```text
-4 < -1
```

but:

```text
(-4)² > (-1)²
16 > 1
```

So simply squaring the values is not enough.

---

## Why Doesn't the Original Sorted Order Help Directly?

The array is sorted by its original values:

```text
[-4,-1,0,3,10]
```

But after squaring, negative values can become large positive values:

```text
[-4,-1,0,3,10]
      ↓
[16,1,0,9,100]
```

This means the squared array is no longer guaranteed to be sorted.

Therefore, my approach is:

```text
Square → Sort
```

---

## Complexity

### Time Complexity

The first loop visits every element once:

```text
O(n)
```

Then `Arrays.sort()` sorts the array:

```text
O(n log n)
```

Therefore, the overall time complexity is:

```text
O(n log n)
```

The sorting operation dominates the linear loop.

### Space Complexity

The solution modifies the original array directly and does not create another array.

Therefore, the **auxiliary space used by the algorithm itself is `O(1)`**.

> Note: Java's `Arrays.sort()` has its own implementation-dependent stack/workspace behavior, but the solution does not explicitly allocate another result array.

---

## Follow-Up: Can We Do Better?

Yes.

The problem specifically asks whether we can solve it in:

```text
O(n)
```

without sorting.

The key observation is that the original array is already sorted.

The largest squared value must come from one of the two ends:

```text
[-7,-3,2,3,11]
 ↑             ↑
left          right
```

We can compare the absolute values at both ends and place the larger square into the result array from the back.

That leads to the **Two Pointers** approach with:

```text
O(n) time
O(n) result space
```

However, **my current solution does not use this optimization**. It uses the simpler:

```text
Square → Sort
```

approach.

---

## Key Learning

* A sorted input array does not always remain sorted after a transformation.
* Squaring negative numbers can change their relative order.
* A simple solution can be built by transforming every element and then sorting.
* Modifying the input array directly avoids creating a separate result array.
* `Arrays.sort()` makes the solution `O(n log n)`.
* This problem also introduces an important optimization opportunity: **Two Pointers can exploit the sorted input to achieve O(n) time**.
* Always check whether the structure of the input allows you to avoid an expensive sorting step.

---

## Final Takeaway

The solution is simple and effective:

```text
1. Square every element.
2. Sort the squared values.
3. Return the array.
```

The main lesson is that **the sorted property of the input is not automatically preserved after squaring**.

For the follow-up, the sorted property can be exploited using **Two Pointers** to avoid sorting altogether.

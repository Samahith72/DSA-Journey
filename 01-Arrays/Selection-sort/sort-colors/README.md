# WIN #17 — Sort Colors

## Problem

Given an array `nums` containing only the values `0`, `1`, and `2`, sort the array **in-place** so that:

```text
0s → first
1s → middle
2s → last
```

The values represent:

```text
0 → Red
1 → White
2 → Blue
```

The solution must not use the library's built-in sorting function.

For example:

```text
Input:
nums = [2,0,2,1,1,0]

Output:
[0,0,1,1,2,2]
```

Another example:

```text
Input:
nums = [2,0,1]

Output:
[0,1,2]
```

The important requirement is that the array must be sorted **in-place**, meaning I need to modify the original array instead of creating another array for the result.

---

## My Java Solution

```java
class Solution {
    public void sortColors(int[] nums) {

        if(nums.length < 2){
            return;
        }

        int i = 0;
        int j = 1;

        while(i < nums.length){
            if(nums[j] < nums[i]){
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }

            j++;

            if(j == nums.length){
                i++;
                j = i;
            }
        }
    }
}
```

---

## My Thought Process

The first thing I noticed is that the array contains only three possible values:

```text
0
1
2
```

The required order is:

```text
0 → 1 → 2
```

So I need to make sure that for every position, the smallest available value is placed there.

I approached this similarly to a simple **selection sort** idea.

I use two pointers:

```text
i → current position that I want to fix

j → position that I compare with i
```

The overall idea is:

```text
Choose position i
      ↓
Compare nums[i] with elements after it
      ↓
If a smaller element is found
      ↓
Swap it with nums[i]
      ↓
Continue until the end
      ↓
Move i forward
      ↓
Repeat
```

---

## Step 1: Handle Small Arrays

I first check:

```java
if(nums.length < 2){
    return;
}
```

If the array contains zero or one element, it is already sorted.

There is nothing to do.

For example:

```text
[]
```

or:

```text
[1]
```

are already sorted.

---

## Step 2: Initialize the Pointers

I start with:

```java
int i = 0;
int j = 1;
```

Here:

```text
i = current position
j = position after i
```

For example:

```text
nums = [2,0,2,1,1,0]

        i
        ↓
[2,0,2,1,1,0]
  ↑
  j
```

Initially:

```text
i = 0
j = 1
```

So I compare:

```text
nums[j] with nums[i]
```

---

## Step 3: Compare the Current Element

The main comparison is:

```java
if(nums[j] < nums[i])
```

If the element at `j` is smaller than the element at `i`, I swap them.

For example:

```text
nums = [2,0,2,1,1,0]

i = 0
j = 1
```

We have:

```text
nums[i] = 2
nums[j] = 0
```

Since:

```text
0 < 2
```

I swap them.

Before:

```text
[2,0,2,1,1,0]
 ↑ ↑
 i j
```

After:

```text
[0,2,2,1,1,0]
 ↑
 i
```

Now the smallest value found so far has been placed at position `i`.

---

## Step 4: Continue Moving `j`

After each comparison, I increment `j`:

```java
j++;
```

This allows me to compare the current `i` position with the next elements.

For example:

```text
[0,2,2,1,1,0]
    ↑ ↑
    i j
```

The process continues until `j` reaches the end of the array.

---

## Step 5: Move to the Next `i`

When:

```java
if(j == nums.length)
```

the current position `i` has been compared against all elements after it.

So I move `i` forward:

```java
i++;
```

And reset:

```java
j = i;
```

This starts the same process for the next position.

The overall movement looks like:

```text
i = 0
j = 1 → 2 → 3 → 4 → 5
                    ↓
                   end

i = 1
j = 2 → 3 → 4 → 5
                ↓
               end

i = 2
j = 3 → 4 → 5
            ↓
           end

...
```

This means each position gets compared with all positions to its right.

---

## Complete Example

Consider:

```text
nums = [2,0,2,1,1,0]
```

Initially:

```text
[2,0,2,1,1,0]
 ↑
 i
```

I compare the remaining elements with `nums[i]`.

### Compare `2` and `0`

```text
0 < 2
```

Swap:

```text
[0,2,2,1,1,0]
```

Now position `0` contains `0`.

So:

```text
index 0 → 0 ✓
```

---

### Move to Position 1

Now:

```text
i = 1
```

The array is:

```text
[0,2,2,1,1,0]
   ↑
   i
```

I compare `2` with the elements after it.

When I encounter `1`:

```text
1 < 2
```

I swap:

```text
[0,1,2,2,1,0]
```

Then I eventually encounter another `0`:

```text
0 < 1
```

and swap:

```text
[0,0,2,2,1,1]
```

Now the first two positions are correctly sorted:

```text
[0,0,...]
```

---

### Continue the Same Process

I continue fixing each position.

Eventually the array becomes:

```text
[0,0,1,1,2,2]
```

which is the required result.

---

## Why the Array Becomes Sorted

At every stage, `i` represents the position I am currently fixing.

I compare `nums[i]` with every element after it.

Whenever I find a smaller value:

```java
nums[j] < nums[i]
```

I swap it into position `i`.

Therefore, after `j` has reached the end:

```text
nums[i]
```

contains the smallest value encountered in the remaining unsorted portion.

Then I move to the next position.

So the array gradually becomes:

```text
Sorted portion | Unsorted portion
       ↓                ↓

[0,0,1, ... | ... ]
```

The sorted portion grows from left to right.

---

## Pattern Recognition

### Pattern: Selection Sort / In-Place Comparison Sorting

My solution follows the basic idea of **Selection Sort**.

The pattern is:

```text
Find the smallest element
        ↓
Place it at the current position
        ↓
Move to the next position
        ↓
Repeat
```

In my implementation:

```text
i → position being fixed

j → searches the remaining array
```

So:

```text
i = current position
j = search through remaining positions
```

Whenever:

```text
nums[j] < nums[i]
```

I swap them.

This means I am continuously bringing smaller values toward the front of the array.

The key pattern is:

```text
Unsorted Array
      ↓
Select smaller element
      ↓
Swap into current position
      ↓
Expand sorted portion
      ↓
Repeat
```

---

## In-Place Sorting

One important part of this problem is that the sorting must happen **in-place**.

My solution does not create another array.

Instead, I directly modify:

```java
nums
```

using a temporary variable only during swaps:

```java
int temp = nums[i];
nums[i] = nums[j];
nums[j] = temp;
```

The `temp` variable only holds one value at a time.

Therefore, no additional array or collection is required.

---

## Complexity

### Time Complexity

```text
O(n²)
```

For every position `i`, I potentially compare it with every position after it.

The comparisons are approximately:

```text
(n - 1) + (n - 2) + (n - 3) + ... + 1
```

This results in:

```text
O(n²)
```

So although the problem's follow-up asks whether it can be solved in one pass, **my current solution is an `O(n²)` solution**.

---

### Space Complexity

```text
O(1)
```

The solution sorts the array in-place.

The only extra variables are:

```text
i
j
temp
```

No additional array or data structure is used.

Therefore:

```text
Auxiliary Space = O(1)
```

---

## Important Observation

The problem specifically gives only three possible values:

```text
0, 1, 2
```

That means there are more optimized ways to solve this problem.

A common optimal approach is the **Dutch National Flag algorithm**, which uses three pointers and solves the problem in:

```text
O(n) time
O(1) space
```

However, my current implementation uses the selection-sort style approach:

```text
O(n²) time
O(1) space
```

This is important to recognize when analyzing my own solution: the solution satisfies the in-place requirement, but it does **not** achieve the one-pass `O(n)` follow-up.

---

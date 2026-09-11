# WIN #30 — Next Permutation

## Problem

Given an array of integers `nums`, rearrange the array into its **next lexicographically greater permutation**.

A permutation is simply an arrangement of the elements of an array.

For example, the permutations of:

```text
[1,2,3]
```

in lexicographical order are:

```text
[1,2,3]
[1,3,2]
[2,1,3]
[2,3,1]
[3,1,2]
[3,2,1]
```

Therefore:

```text
Next permutation of [1,2,3]
→ [1,3,2]
```

If the current permutation is already the largest possible permutation, we must rearrange it into the smallest possible permutation.

For example:

```text
[3,2,1]
```

has no larger permutation, so:

```text
[3,2,1]
→ [1,2,3]
```

The rearrangement must be done **in-place** using `O(1)` extra space.

---

## My Java Solution

```java
class Solution {
    public void nextPermutation(int[] nums) {

        int n = nums.length;
        int i = n-2;

        while( i>= 0 && nums[i] >= nums[i+1]){
            i--;
        }

        if(i >= 0){
            int j = n-1;

            while( nums[j] <= nums[i]){
                j--;
            }

            int temp = nums[j];
            nums[j] = nums[i];
            nums[i] = temp;
        }

        int left = i+1;
        int right = n-1;

        while(left < right){
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
```

---

## My Thought Process

The main challenge is understanding what **"next permutation"** actually means.

We don't want just any larger permutation.

We want:

> The **smallest permutation that is greater than the current permutation**.

For example:

```text
[1,2,3]
```

The next permutation is:

```text
[1,3,2]
```

not:

```text
[2,3,1]
```

because `[1,3,2]` is the smallest arrangement that is still greater than `[1,2,3]`.

So I need to make the **smallest possible change** to the current arrangement.

The key observation is to look at the array from **right to left**.

---

# Step 1: Find the Pivot

I start from:

```java
int i = n - 2;
```

Then:

```java
while(i >= 0 && nums[i] >= nums[i+1]){
    i--;
}
```

I'm looking for the first position from the right where:

```text
nums[i] < nums[i+1]
```

This element is called the **pivot**.

---

## Why Search From Right to Left?

Consider:

```text
[1,2,3]
```

Starting from the right:

```text
1  2  3
   ↑  ↑
```

We check:

```text
2 < 3
```

Yes.

So `2` is the pivot.

```text
[1, 2, 3]
    ↑
  pivot
```

This tells us that we can make the permutation slightly larger by modifying the suffix starting after `2`.

---

## Another Example

Consider:

```text
[1,3,5,4,2]
```

Start from the right:

```text
5 > 4
```

so continue moving left.

Then:

```text
4 > 2
```

continue.

Now:

```text
3 < 5
```

We found the pivot:

```text
[1,3,5,4,2]
  ↑
pivot
```

So:

```text
i = 1
nums[i] = 3
```

---

# Why Is the Suffix Decreasing?

The loop:

```java
while(i >= 0 && nums[i] >= nums[i+1]){
    i--;
}
```

stops only when it finds:

```text
nums[i] < nums[i+1]
```

Everything to the right of `i` has been scanned from right to left while satisfying:

```text
nums[x] >= nums[x+1]
```

So the suffix is in **non-increasing order**.

For example:

```text
[1,3,5,4,2]
   ↑
 pivot
```

The suffix:

```text
[5,4,2]
```

is decreasing.

This property becomes extremely useful later when we reverse the suffix.

---

# Step 2: What If There Is No Pivot?

Consider:

```text
[3,2,1]
```

Starting from the right:

```text
2 >= 1
```

continue left.

Then:

```text
3 >= 2
```

continue.

Eventually:

```text
i = -1
```

This means there is no position where:

```text
nums[i] < nums[i+1]
```

The array is completely decreasing:

```text
[3,2,1]
```

which means it is already the **largest possible permutation**.

Therefore, the next permutation must be the smallest possible arrangement:

```text
[1,2,3]
```

My code handles this naturally because:

```java
if(i >= 0)
```

is skipped.

Then:

```java
int left = i + 1;
```

becomes:

```text
left = 0
```

and the entire array is reversed.

---

# Step 3: Find the Next Larger Element

Once I find the pivot, I need to find an element that is **just larger than the pivot**.

I start from the end:

```java
int j = n - 1;
```

and move left while:

```java
while(nums[j] <= nums[i]){
    j--;
}
```

So I find the first element from the right that is greater than the pivot.

---

## Example

Consider:

```text
[1,3,5,4,2]
```

The pivot is:

```text
3
```

The suffix is:

```text
[5,4,2]
```

Starting from the right:

```text
2 <= 3
```

skip it.

Then:

```text
4 > 3
```

So `4` is selected.

We now have:

```text
pivot = 3
replacement = 4
```

---

# Step 4: Swap the Pivot

Now I swap:

```text
nums[i]
```

with:

```text
nums[j]
```

For:

```text
[1,3,5,4,2]
```

we swap `3` and `4`.

Before:

```text
[1,3,5,4,2]
```

After:

```text
[1,4,5,3,2]
```

The permutation is now definitely larger than the original.

But it is **not yet the next permutation**.

Why?

Because the suffix:

```text
[5,3,2]
```

is still arranged too large.

We need to make it as small as possible.

---

# Step 5: Reverse the Suffix

After swapping, I set:

```java
int left = i + 1;
int right = n - 1;
```

Then:

```java
while(left < right){
    int temp = nums[left];
    nums[left] = nums[right];
    nums[right] = temp;

    left++;
    right--;
}
```

This reverses the suffix.

For:

```text
[1,4,5,3,2]
```

the suffix is:

```text
[5,3,2]
```

Reverse it:

```text
[2,3,5]
```

Final result:

```text
[1,4,2,3,5]
```

This is the next permutation.

---

# Complete Example

Consider:

```text
nums = [1,2,3]
```

### Find Pivot

Start from the right:

```text
2 < 3
```

So:

```text
pivot = 2
```

Array:

```text
[1,2,3]
   ↑
   i
```

### Find Replacement

Start from the end:

```text
3 > 2
```

So choose `3`.

### Swap

```text
[1,2,3]
   ↑ ↑
   2 3
```

becomes:

```text
[1,3,2]
```

### Reverse Suffix

The suffix contains only:

```text
[2]
```

so nothing needs to change.

Final:

```text
[1,3,2]
```

---

# Another Example

Consider:

```text
[1,3,5,4,2]
```

### Step 1 — Find Pivot

From right:

```text
5 > 4
4 > 2
3 < 5
```

So:

```text
pivot = 3
```

Array:

```text
[1,3,5,4,2]
   ↑
```

---

### Step 2 — Find Next Larger Element

From right:

```text
2 < 3
```

skip.

Then:

```text
4 > 3
```

Choose `4`.

---

### Step 3 — Swap

```text
[1,3,5,4,2]
```

becomes:

```text
[1,4,5,3,2]
```

---

### Step 4 — Reverse the Suffix

Suffix:

```text
[5,3,2]
```

Reverse:

```text
[2,3,5]
```

Final:

```text
[1,4,2,3,5]
```

So:

```text
[1,3,5,4,2]
        ↓
[1,4,2,3,5]
```

---

# Why Do We Choose the Rightmost Larger Element?

This is an important detail.

After finding the pivot, we need the **smallest element greater than the pivot**.

Because the suffix is already in non-increasing order, scanning from the right finds the appropriate replacement.

For example:

```text
pivot = 3
suffix = [5,4,2]
```

Possible values greater than `3` are:

```text
4, 5
```

We want:

```text
4
```

because replacing `3` with `4` makes the smallest possible increase.

Scanning from the right gives us `4` first.

---

# Why Do We Reverse Instead of Sort?

After swapping the pivot, the suffix is still in non-increasing order.

For example:

```text
Before swap:
[1,3,5,4,2]

After swap:
[1,4,5,3,2]
```

Suffix:

```text
[5,3,2]
```

is decreasing.

To make the overall permutation as small as possible, we need the suffix in ascending order:

```text
[2,3,5]
```

Since the suffix is already decreasing, reversing it gives the ascending order directly.

So instead of sorting:

```text
O(n log n)
```

we can simply reverse:

```text
O(n)
```

---

# Pattern Recognition

### Pattern: Next Permutation / In-Place Array Manipulation

This problem has a very specific but extremely useful pattern:

```text
1. Find the pivot
2. Find the next larger element
3. Swap them
4. Reverse the suffix
```

The pattern is:

```text
Find first increasing pair from right
              ↓
            Pivot
              ↓
Find smallest larger element on right
              ↓
            Swap
              ↓
Reverse everything after pivot
```

The key condition for finding the pivot is:

```text
nums[i] < nums[i+1]
```

And the key condition for finding the replacement is:

```text
nums[j] > nums[i]
```

---

# Why Does This Give the Next Permutation?

We want the **smallest possible permutation that is larger than the current one**.

To achieve that:

### 1. Change as far right as possible

We search from right to left for the pivot.

This means we change the permutation at the latest possible position.

### 2. Increase the pivot by the smallest possible amount

We choose the smallest number greater than the pivot.

### 3. Minimize everything after the pivot

After increasing the pivot, we put the suffix into its smallest possible order.

Since the suffix is decreasing, reversing it gives ascending order.

Therefore:

```text
Smallest increase
       +
Smallest suffix
       =
Next permutation
```

---

# Edge Case: Descending Array

Consider:

```text
[3,2,1]
```

There is no pivot.

So:

```text
i = -1
```

Then:

```java
int left = i + 1;
```

gives:

```text
left = 0
```

The entire array is reversed:

```text
[3,2,1]
   ↓
[1,2,3]
```

This correctly converts the largest permutation into the smallest permutation.

---

# Edge Case: Duplicate Values

Consider:

```text
[1,1,5]
```

Find pivot:

```text
1 < 5
```

The pivot is the second `1`.

Then:

```text
5 > 1
```

Swap:

```text
[1,5,1]
```

The suffix has one element, so we're done.

Final:

```text
[1,5,1]
```

This also shows that the algorithm naturally handles duplicate values.

---

# Complexity

### Time Complexity

There are three main operations:

### Finding the pivot

```text
O(n)
```

### Finding the replacement

```text
O(n)
```

### Reversing the suffix

```text
O(n)
```

These operations happen sequentially, not nested.

Therefore:

```text
O(n) + O(n) + O(n)
```

which simplifies to:

```text
O(n)
```

### Space Complexity

The algorithm only uses a few variables:

```text
i
j
left
right
temp
```

No additional array or data structure is created.

Therefore:

```text
O(1)
```

extra space.

---

# Key Learning

* The next permutation is the **smallest permutation greater than the current one**.
* Search from right to left to find the first position where `nums[i] < nums[i+1]`.
* This position is the **pivot**.
* Find the smallest value greater than the pivot from the right side.
* Swap the pivot with that value.
* Reverse the suffix to make it as small as possible.
* If no pivot exists, the array is already the largest permutation, so reverse the entire array.
* The entire algorithm works **in-place**.
* The solution achieves **O(n) time and O(1) extra space**.

---

# Final Takeaway

The entire algorithm can be remembered with four steps:

```text
        [1,3,5,4,2]

1. Find Pivot
        ↓
        3

2. Find next larger element
        ↓
        4

3. Swap
        ↓
        [1,4,5,3,2]

4. Reverse suffix
        ↓
        [1,4,2,3,5]
```

The most important insight is:

> **To get the next permutation, make the smallest possible increase as far to the right as possible, then make everything after it as small as possible.**

In short:

```text
Pivot → Swap → Reverse
```

This three-step pattern is the key to remembering **Next Permutation**.

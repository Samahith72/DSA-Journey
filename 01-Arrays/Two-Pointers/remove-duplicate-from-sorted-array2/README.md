# WIN #29 — Remove Duplicates from Sorted Array II

## Problem

Given an integer array `nums` sorted in **non-decreasing order**, remove duplicates in-place so that each unique element appears **at most twice**.

The relative order of the elements must remain the same.

The final result must be stored in the **first `k` positions** of `nums`, and we return `k`.

For example:

```text
Input:
nums = [1,1,1,2,2,3]

Output:
k = 5

nums = [1,1,2,2,3,_]
```

The value `1` originally appears three times, but we are allowed to keep it only twice.

---

## My Java Solution

```java
class Solution {
    public int removeDuplicates(int[] nums) {

        if(nums.length <=2){
            return nums.length;
        }

        int k=2;

        for(int i=2;i < nums.length;i++){
            if(nums[i] != nums[k-2]){
                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }
}
```

---

## My Thought Process

The array is already sorted.

That means all duplicate values are next to each other.

For example:

```text
[0,0,1,1,1,2,2,3]
```

So I don't need a `HashMap` or any extra data structure to count frequencies.

The requirement is:

> Every unique number can appear at most **twice**.

Since the first two elements are always allowed, I start with:

```java
int k = 2;
```

Then I scan the array starting from index `2`.

I use:

```text
i → scans the original array

k → position where the next valid element should be written
```

The key condition is:

```java
if(nums[i] != nums[k-2])
```

This is the main idea of the solution.

---

## Understanding `i` and `k`

Consider:

```text
nums = [1,1,1,2,2,3]
```

Initially:

```text
k = 2
```

So the first two elements are automatically accepted:

```text
[1,1]
```

Now:

```text
i = 2
```

points to the next element:

```text
[1,1,1,2,2,3]
     ↑
     i
```

`k` points to where the next accepted element would be written:

```text
[1,1,_,_,_,_]
     ↑
     k
```

---

## Why Compare With `nums[k-2]`?

This is the most important part.

The condition is:

```java
nums[i] != nums[k-2]
```

Why `k-2`?

Because we are allowed to keep **two copies** of each number.

So before writing a new value at position `k`, we look two positions behind.

If:

```text
nums[i] == nums[k-2]
```

then the value would become the **third occurrence**.

Therefore, we skip it.

If:

```text
nums[i] != nums[k-2]
```

then adding it will not create more than two consecutive copies in the valid portion.

Therefore, we keep it.

---

## Example Walkthrough

Consider:

```text
nums = [1,1,1,2,2,3]
```

### Initial State

The first two elements can always stay:

```text
[1,1,1,2,2,3]
 ↑ ↑
```

So:

```text
k = 2
```

The valid portion is currently:

```text
[1,1]
```

---

### Step 1 — Process the Third `1`

Now:

```text
i = 2
nums[i] = 1
```

We compare:

```text
nums[i] = 1
nums[k-2] = nums[0] = 1
```

Therefore:

```text
1 == 1
```

The condition:

```java
nums[i] != nums[k-2]
```

is false.

So we **skip this `1`**.

The valid portion remains:

```text
[1,1]
```

`k` stays:

```text
k = 2
```

---

### Step 2 — Process `2`

Now:

```text
i = 3
nums[i] = 2
```

Compare:

```text
nums[i] = 2
nums[k-2] = nums[0] = 1
```

They are different:

```text
2 != 1
```

So we keep `2`:

```java
nums[k] = nums[i];
```

which means:

```text
nums[2] = 2
```

The array's valid portion becomes:

```text
[1,1,2]
```

Then:

```java
k++;
```

so:

```text
k = 3
```

---

### Step 3 — Process Second `2`

Now:

```text
i = 4
nums[i] = 2
```

Compare:

```text
nums[k-2]
= nums[1]
= 1
```

So:

```text
2 != 1
```

Keep it:

```text
[1,1,2,2]
```

Then:

```text
k = 4
```

---

### Step 4 — Process `3`

Now:

```text
i = 5
nums[i] = 3
```

Compare:

```text
nums[k-2]
= nums[2]
= 2
```

Since:

```text
3 != 2
```

we keep `3`.

The valid portion becomes:

```text
[1,1,2,2,3]
```

Finally:

```text
k = 5
```

So we return:

```text
5
```

---

## Complete Walkthrough

```text
Input:
[1,1,1,2,2,3]

Start:
k = 2

First two elements are automatically kept:

[1,1]
```

Then:

```text
i = 2
1 == nums[0]
→ Skip
```

```text
i = 3
2 != nums[0]
→ Keep 2

[1,1,2]
```

```text
i = 4
2 != nums[1]
→ Keep 2

[1,1,2,2]
```

```text
i = 5
3 != nums[2]
→ Keep 3

[1,1,2,2,3]
```

Final:

```text
k = 5
```

---

## Another Example

Consider:

```text
nums = [0,0,1,1,1,1,2,3,3]
```

The desired result is:

```text
[0,0,1,1,2,3,3]
```

Let's see how the condition handles the repeated `1`s.

After keeping:

```text
[0,0,1,1]
```

when another `1` appears:

```text
nums[i] = 1
nums[k-2] = 1
```

Therefore:

```text
1 == 1
```

and we skip it.

The next `1` is also skipped.

When `2` appears:

```text
2 != nums[k-2]
```

so we keep it.

This naturally limits every value to two occurrences.

---

## Why Does `k-2` Guarantee At Most Two Copies?

Suppose the valid portion currently ends with:

```text
[1,1]
```

and we encounter another:

```text
1
```

Since:

```text
k = 2
```

we compare against:

```text
nums[k-2] = nums[0] = 1
```

They are equal.

So the third `1` is rejected.

Now suppose we have:

```text
[1,1,2]
```

and encounter another `2`.

Now:

```text
k = 3
```

so:

```text
nums[k-2] = nums[1] = 1
```

The current value is:

```text
2
```

Since:

```text
2 != 1
```

we accept it:

```text
[1,1,2,2]
```

If another `2` arrives, then:

```text
k = 4
nums[k-2] = nums[2] = 2
```

Now:

```text
2 == 2
```

so it is rejected.

This is why looking **two positions behind** works perfectly.

---

## Why Can We Always Keep the First Two Elements?

The problem allows each unique number to appear **at most twice**.

Therefore, regardless of what the first two elements are, they can safely remain.

That's why:

```java
if(nums.length <=2){
    return nums.length;
}
```

handles the small-array case.

And for larger arrays:

```java
int k = 2;
```

starts the valid portion after those first two elements.

---

## Pattern Recognition

### Pattern: Two Pointers — Read/Write Pointer

This is another variation of the **Read/Write Pointer** pattern.

The two pointers have different jobs:

```text
i → reads/scans every element

k → writes valid elements
```

The valid portion is always maintained at the beginning:

```text
[valid, valid, valid, valid, _, _, _]
                         ↑
                         k
```

The key difference from **Remove Element** is that here we're not simply checking whether an element should be removed.

Instead, we're checking:

> Would adding this element create more than two copies of the same value?

Because the array is sorted, we can answer that by comparing with:

```text
nums[k-2]
```

---

## Why Does Sorting Matter?

The solution depends heavily on the array being sorted.

For example:

```text
[1,1,1,2,2,3]
```

All equal values are adjacent.

Therefore, if:

```text
nums[i] == nums[k-2]
```

we know that adding `nums[i]` would create a third occurrence of that value in the valid portion.

If the array were not sorted:

```text
[1,2,1,3,1]
```

this simple comparison would not be enough.

So the sorted property is what makes the solution possible with `O(1)` extra space.

---

## Complexity

### Time Complexity

The loop scans the array once:

```text
i = 2 → n-1
```

Each element is processed only once.

Therefore:

```text
O(n)
```

time.

### Space Complexity

No additional array or data structure is created.

Only the two pointers are used:

```text
i
k
```

Therefore:

```text
O(1)
```

extra space.

---

## Key Learning

* A sorted array makes duplicate handling much easier.
* When an array is sorted, equal values appear next to each other.
* The **Read/Write Pointer** pattern can modify the array in-place.
* The first two elements are always allowed because each value can appear twice.
* Comparing `nums[i]` with `nums[k-2]` tells us whether the current value would become a third occurrence.
* `k` represents the size of the valid portion of the array.
* The solution achieves both **O(n) time** and **O(1) extra space**.

---

## Final Takeaway

The entire idea can be remembered as:

```text
First two elements → Always keep

For every next element:

nums[i] == nums[k-2]
        ↓
   Third occurrence
        ↓
       Skip

nums[i] != nums[k-2]
        ↓
    Valid element
        ↓
  nums[k] = nums[i]
        ↓
       k++
```

The most important insight is:

> **Because the array is sorted, comparing the current element with the element two positions behind the write pointer tells us whether we have already kept two copies of that value.**

This lets us remove excess duplicates **in-place**, without using any additional data structure.

# WIN #23 — Remove Element

## Problem

Given an integer array `nums` and an integer `val`, remove all occurrences of `val` **in-place**.

The order of the remaining elements does not matter.

After removing the elements, return `k`, where `k` represents the number of elements that are **not equal to `val`**.

The important requirement is:

> The first `k` positions of `nums` must contain all elements that are not equal to `val`.

Anything after the first `k` elements does not matter.

For example:

```text
Input:
nums = [3,2,2,3]
val = 3

Output:
k = 2

nums = [2,2,_,_]
```

---

## My Java Solution

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int tracePtr =0;
        int countPtr =0;
        int count = 0;

        while(tracePtr < nums.length){
            if(nums[tracePtr] != val){
                nums[countPtr] = nums[tracePtr];
                count++;
                countPtr++;
                tracePtr++;
            }else{
                tracePtr++;
            }
        }

        return count;
    }
}
```

---

## My Thought Process

The main requirement is to modify the array **in-place**.

So I don't want to create another array to store the valid elements.

Instead, I can use the same array and overwrite the positions where unwanted values exist.

I use two pointers:

```text
tracePtr → scans through the entire array

countPtr → tells me where to place the next valid element
```

I also maintain:

```text
count → number of elements that are not equal to val
```

The basic idea is:

```text
Scan every element
      ↓
Is it equal to val?
      ↓
   Yes → Skip it
      ↓
   No → Copy it to countPtr
      ↓
Move the pointers
```

---

## Understanding the Two Pointers

Suppose:

```text
nums = [0,1,2,2,3,0,4,2]
val = 2
```

Initially:

```text
tracePtr = 0
countPtr = 0
count = 0
```

### `tracePtr`

This pointer's job is simply to **look at every element**.

```text
0  1  2  2  3  0  4  2
↑
tracePtr
```

### `countPtr`

This pointer tells us where the next valid element should be written.

```text
0  1  2  2  3  0  4  2
↑
countPtr
```

---

## Step 1: Find a Valid Element

The first value is:

```text
nums[tracePtr] = 0
```

Since:

```text
0 != 2
```

it should remain in the valid portion of the array.

So:

```java
nums[countPtr] = nums[tracePtr];
```

The array remains:

```text
[0,1,2,2,3,0,4,2]
```

Then:

```text
tracePtr++
countPtr++
count++
```

Now:

```text
tracePtr = 1
countPtr = 1
count = 1
```

---

## Step 2: Continue Scanning

Next:

```text
nums[tracePtr] = 1
```

Again:

```text
1 != 2
```

So we copy it:

```text
nums[countPtr] = nums[tracePtr]
```

Now:

```text
[0,1,2,2,3,0,4,2]
```

and:

```text
tracePtr = 2
countPtr = 2
count = 2
```

---

## Step 3: Encounter the Value to Remove

Now:

```text
nums[tracePtr] = 2
```

and:

```text
val = 2
```

Therefore:

```text
nums[tracePtr] == val
```

We don't copy it.

We simply skip it:

```text
tracePtr++
```

Notice that `countPtr` does **not** move.

This is important.

Why?

Because `countPtr` is still pointing to the next position where a valid element should be placed.

---

## Step 4: Find Another Valid Element

Eventually we reach:

```text
nums[tracePtr] = 3
```

Since:

```text
3 != 2
```

we write:

```text
nums[countPtr] = nums[tracePtr]
```

The valid elements are continuously packed toward the beginning of the array.

---

## Complete Walkthrough

For:

```text
nums = [0,1,2,2,3,0,4,2]
val = 2
```

the important operations are:

```text
tracePtr sees 0 → keep → write at countPtr 0
tracePtr sees 1 → keep → write at countPtr 1
tracePtr sees 2 → remove → skip
tracePtr sees 2 → remove → skip
tracePtr sees 3 → keep → write at countPtr 2
tracePtr sees 0 → keep → write at countPtr 3
tracePtr sees 4 → keep → write at countPtr 4
tracePtr sees 2 → remove → skip
```

The beginning of the array becomes:

```text
[0,1,3,0,4,_,_,_]
```

The order does not matter for this problem, and the first five elements are all valid.

Therefore:

```text
count = 5
```

and we return:

```text
5
```

---

## Why Does This Work?

The important invariant is:

> Everything before `countPtr` contains elements that are not equal to `val`.

Whenever `tracePtr` finds a valid element, we place it at `countPtr`.

For example:

```text
[0,1,2,2,3,0,4,2]
 ↑     ↑
 |     |
countPtr
       tracePtr
```

When `tracePtr` finds `2`, it skips it.

When it finds `3`, we move `3` into the position currently pointed to by `countPtr`.

This gradually creates a valid prefix:

```text
[0,1,3,0,4,_,_,_]
```

So when the scan finishes, `countPtr` (and `count`) represents the number of valid elements.

---

## Pattern Recognition

### Pattern: Two Pointers — Read/Write Pointer

This is a common **in-place array filtering** pattern.

Instead of using a new array:

```text
Original Array
      ↓
Read every element
      ↓
Keep valid elements
      ↓
Write them toward the front
```

The two pointers have different responsibilities:

```text
tracePtr
    ↓
"Which element am I currently checking?"

countPtr
    ↓
"Where should the next valid element go?"
```

This pattern is useful whenever a problem asks you to:

* Remove certain elements in-place
* Keep only elements satisfying a condition
* Compact an array
* Modify an array without using another array

---

## Why Don't We Need to Actually Delete Elements?

A common mistake is thinking that we need to physically remove elements from the array.

We don't.

The problem only cares about the **first `k` elements**.

For example:

```text
[0,1,3,0,4,2,2,2]
```

If:

```text
k = 5
```

then only this matters:

```text
[0,1,3,0,4]
```

Everything after that can contain anything.

So we simply overwrite the beginning of the array with valid elements.

This is what makes the in-place solution possible.

---

## Complexity

### Time Complexity

`tracePtr` moves from the beginning to the end of the array exactly once.

Therefore:

```text
O(n)
```

Every element is inspected once.

### Space Complexity

No additional array or data structure is created.

Only a few integer variables are used:

```text
tracePtr
countPtr
count
```

Therefore:

```text
O(1)
```

auxiliary space.

---

## Key Learning

* In-place problems often don't require physically deleting elements.
* If only the first `k` elements matter, we can overwrite unwanted positions.
* Two pointers can separate **reading** from **writing**.
* `tracePtr` scans the entire array.
* `countPtr` keeps track of where the next valid element should be placed.
* When an element equals `val`, we skip it.
* When an element is valid, we copy it forward.
* The solution achieves both **O(n) time** and **O(1) extra space**.

---

## Final Takeaway

The entire approach can be summarized as:

```text
tracePtr → Scan every element
countPtr → Build the valid portion

If nums[tracePtr] == val
        ↓
      Skip

If nums[tracePtr] != val
        ↓
Copy to nums[countPtr]
        ↓
Move countPtr
```

At the end:

```text
count = number of valid elements
```

and the first `count` elements of `nums` contain everything that should remain.

The key idea to remember is:

> **Don't delete — overwrite and compact.**

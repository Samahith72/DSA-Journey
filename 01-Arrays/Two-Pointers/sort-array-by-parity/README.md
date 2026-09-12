# WIN #32 — Sort Array By Parity

## Problem

Given an integer array `nums`, move all the **even integers** to the beginning of the array and all the **odd integers** to the end.

The order of the elements does not matter.

For example:

```text
Input:
nums = [3,1,2,4]

Output:
[2,4,3,1]
```

Other outputs such as:

```text
[4,2,3,1]
[2,4,1,3]
[4,2,1,3]
```

are also valid.

---

## My Java Solution

```java
class Solution {
    public int[] sortArrayByParity(int[] nums) {
        int i = 0;

        for(int j = 0; j < nums.length; j++){
            if(nums[j] % 2 == 0){
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
            }
        }

        return nums;
    }
}
```

---

## My Thought Process

The main thing I need to achieve is:

```text
EVENS → beginning
ODDS  → end
```

I don't actually need to sort the numbers.

I only need to **partition** the array based on whether each number is even or odd.

So I use two pointers:

```text
i → position where the next even number should go

j → scans through the entire array
```

The important idea is:

> Whenever `j` finds an even number, put it at position `i`.

---

## How the Two Pointers Work

Suppose:

```text
nums = [3,1,2,4]
```

Initially:

```text
i = 0
j = 0
```

### Step 1

`j` points to:

```text
3
```

`3` is odd, so we do nothing.

```text
i = 0
j = 1
```

---

### Step 2

`j` points to:

```text
1
```

`1` is odd, so again we do nothing.

```text
i = 0
j = 2
```

---

### Step 3

`j` points to:

```text
2
```

`2` is even.

The next available position for an even number is `i = 0`.

So we swap:

```text
[3,1,2,4]
 ↑   ↑
 i   j
```

After swapping:

```text
[2,1,3,4]
```

Now the even section has grown.

So:

```text
i++
```

giving:

```text
i = 1
```

---

### Step 4

`j` moves to:

```text
4
```

`4` is even.

The next position for an even number is `i = 1`.

Swap:

```text
[2,1,3,4]
   ↑     ↑
   i     j
```

After swapping:

```text
[2,4,3,1]
```

Now:

```text
i = 2
```

and the array is partitioned.

```text
[2,4 | 3,1]
 ↑      ↑
EVENS  ODDS
```

---

# The Important Invariant

At every point in the loop:

```text
[0 ........ i-1] = even numbers
```

So `i` always represents:

> **The first position that has not yet been filled with an even number.**

Meanwhile, `j` scans every element exactly once.

This makes the algorithm very simple.

---

# Why Do We Swap?

When `j` finds an even number, we want to move it into the even section.

For example:

```text
[3,1,2,4]
     ↑
     j
```

The even section is currently empty:

```text
[ | 3,1,2,4]
  ↑
  i
```

So we swap `nums[i]` and `nums[j]`:

```text
[2,1,3,4]
```

The number that was at `i` becomes part of the remaining portion of the array.

We don't care about the exact order because the problem allows **any valid ordering**.

---

# Pattern Recognition

## Pattern: Two Pointers / In-Place Partition

This problem is essentially a **partitioning problem**.

We divide the array into two sections:

```text
[ EVEN NUMBERS | UNPROCESSED / ODD NUMBERS ]
```

The pointer `i` separates these two sections.

The pointer `j` searches for the next even number.

Whenever an even number is found:

```text
swap(nums[i], nums[j])
i++
```

So the general pattern is:

```text
j scans
   ↓
find desired element
   ↓
place it at i
   ↓
i++
```

This same idea appears in many array problems where we need to **rearrange elements in-place based on a condition**.

---

# Example

Consider:

```text
nums = [0,1,2,3,4,5,6]
```

The algorithm gradually builds:

```text
[0 | 1,2,3,4,5,6]

[0,2 | 1,3,4,5,6]

[0,2,4 | 1,3,5,6]

[0,2,4,6 | 1,3,5]
```

Final result:

```text
[0,2,4,6,3,1,5]
```

All even numbers are at the beginning and all odd numbers are at the end.

---

# Why This Works

Every time we encounter an even number at `j`:

1. We move it to position `i`.
2. We increase `i`.
3. Therefore, the range `[0, i-1]` contains only even numbers.

If `nums[j]` is odd, we simply leave it where it is and continue scanning.

By the time `j` reaches the end of the array, every even number has been moved into the front section.

Everything remaining belongs to the odd section.

Therefore, the final array satisfies the required condition.

---

# Complexity

## Time Complexity

```text
O(n)
```

The pointer `j` goes through the array exactly once.

Each element is processed once.

Therefore:

```text
Time = O(n)
```

---

## Space Complexity

```text
O(1)
```

The array is modified **in-place**.

Apart from the temporary variable used for swapping, no additional data structure is created.

Therefore:

```text
Space = O(1)
```

---

# Key Learning

### 1. Don't Sort When You Only Need Partitioning

The problem is called **Sort Array By Parity**, but we don't actually need a sorting algorithm.

We only need:

```text
EVENS → LEFT
ODDS  → RIGHT
```

Recognizing this reduces the problem to a simple partition operation.

---

### 2. One Pointer Can Mark the Correct Position

The most important role of `i` is:

```text
i = next position where an even number should go
```

This is a useful way to think about in-place array problems.

Instead of asking:

> "Where should this element eventually be?"

ask:

> "What is the next position I need to fill?"

---

### 3. The Order Doesn't Matter

Because the problem accepts **any valid array**, we don't need to preserve the original order.

That allows us to freely swap elements.

If the problem required maintaining the relative order of even and odd numbers, this solution would not necessarily satisfy that requirement.

---

## Final Takeaway

The core idea is:

```text
j → scan the array
i → next position for an even number

if nums[j] is even:
    swap nums[i] and nums[j]
    i++
```

The pattern is:

```text
Two Pointers
      +
In-Place Partition
```

And the final complexity is:

```text
Time:  O(n)
Space: O(1)
```

A simple partitioning problem where recognizing that **we don't need to actually sort the array** turns the solution into a single linear scan.

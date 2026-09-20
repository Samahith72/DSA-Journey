# WIN #40 — Valid Mountain Array

## Problem

[LeetCode 941 — Valid Mountain Array](https://leetcode.com/problems/valid-mountain-array/)

Given an integer array `arr`, return `true` if and only if the array forms a **valid mountain**.

A valid mountain array must satisfy:

1. `arr.length >= 3`
2. The array must **strictly increase** up to a peak.
3. After the peak, the array must **strictly decrease**.
4. The peak cannot be the first or last element.

### Example 1

```text
Input: arr = [2,1]
Output: false
```

The array contains fewer than 3 elements.

### Example 2

```text
Input: arr = [3,5,5]
Output: false
```

The array increases, but the two `5`s are equal, so there is no valid strictly decreasing side.

### Example 3

```text
Input: arr = [0,3,2,1]
Output: true
```

The array follows:

```text
0 < 3 > 2 > 1
```

So it is a valid mountain.

---

## My Java Solution

```java
class Solution {
    public boolean validMountainArray(int[] arr) {
        if(arr.length <3){
            return false;
        }

        int ptr1 = 0;
        int ptr2 = 1;
        int peak =0;

        while(ptr2 < arr.length){
            if(arr[ptr1] == arr[ptr2]){
                return false;
            }else if(arr[ptr1] < arr[ptr2]){
                ptr1++;
                ptr2++;
            }
            else
            {
                peak = ptr1;
                break;
            }
        }

        if(peak == 0){
            return false;
        }
        int j = peak;

        while(j  < arr.length-1){
            if(arr[j] > arr[j+1]){
                j++;
            }else{
                return false;
            }
        }

        return true;
    }
}
```

---

## My Thought Process

The important thing is to recognize that a mountain has **two phases**:

```text
Increasing → Peak → Decreasing
```

For example:

```text
[0, 3, 5, 4, 2, 1]
      ↑
     Peak
```

So instead of trying to check the entire array with complicated conditions, I split the problem into two parts:

1. Find where the increasing section stops.
2. Verify that everything after that point is strictly decreasing.

---

# Step 1 — Handle Arrays Smaller Than 3

A mountain needs at least:

```text
increase + peak + decrease
```

Therefore, at least 3 elements are required.

```java
if(arr.length < 3){
    return false;
}
```

For example:

```text
[2,1]
```

cannot form a mountain.

---

# Step 2 — Start Two Pointers

I use two pointers:

```java
int ptr1 = 0;
int ptr2 = 1;
```

They represent two adjacent elements:

```text
ptr1
 ↓
[0, 3, 5, 4, 2, 1]
    ↑
   ptr2
```

The purpose is to determine where the increasing sequence ends.

---

# Step 3 — Find the Peak

While `ptr2` is inside the array, I compare:

```java
arr[ptr1]
```

with

```java
arr[ptr2]
```

There are three possibilities.

### Case 1 — Equal Values

```java
if(arr[ptr1] == arr[ptr2]){
    return false;
}
```

A mountain must be **strictly increasing** and **strictly decreasing**.

Therefore:

```text
[1, 2, 2, 1]
```

is invalid.

---

### Case 2 — Still Increasing

```java
else if(arr[ptr1] < arr[ptr2]){
    ptr1++;
    ptr2++;
}
```

If the next value is greater, we are still climbing.

For:

```text
[0, 3, 5, 4, 2]
```

the pointers move like:

```text
0 < 3
  ↓
3 < 5
    ↓
5 > 4
```

At this point, the increasing section has ended.

---

### Case 3 — We Start Decreasing

```java
else
{
    peak = ptr1;
    break;
}
```

When:

```text
arr[ptr1] > arr[ptr2]
```

we have found the peak.

For:

```text
[0, 3, 5, 4, 2, 1]
```

the peak is:

```text
        peak
          ↓
[0, 3, 5, 4, 2, 1]
```

So:

```java
peak = 2;
```

---

# Step 4 — Make Sure a Peak Actually Exists

After finding the increasing portion:

```java
if(peak == 0){
    return false;
}
```

Why?

Because if `peak` is still `0`, we never found a point where the array started decreasing.

For example:

```text
[1,2,3,4,5]
```

is only increasing.

There is no mountain peak followed by a decreasing section.

Therefore:

```text
false
```

---

# Step 5 — Verify the Decreasing Side

Now we start from the peak:

```java
int j = peak;
```

Then check:

```java
while(j < arr.length-1){
    if(arr[j] > arr[j+1]){
        j++;
    }else{
        return false;
    }
}
```

Every next element must be smaller.

For:

```text
[0, 3, 5, 4, 2, 1]
```

we check:

```text
5 > 4 ✓
4 > 2 ✓
2 > 1 ✓
```

Therefore the decreasing side is valid.

---

# Step 6 — Return True

If we successfully:

1. Found an increasing section
2. Found a peak
3. Verified the entire decreasing section

then the array is a valid mountain.

```java
return true;
```

---

## Complete Flow

The algorithm can be visualized as:

```text
[0, 3, 5, 4, 2, 1]
 ↑  ↑

Find increasing section
        ↓

[0, 3, 5 | 4, 2, 1]
        ↑
       Peak

        ↓

Check decreasing section

5 > 4 ✓
4 > 2 ✓
2 > 1 ✓

        ↓

      true
```

---

## Pattern Recognition

### Pattern: Two Pointers + Peak Detection

This problem can be recognized as a **Two Pointers / Mountain Pattern** problem.

The key structure is:

```text
Increasing → Peak → Decreasing
```

Whenever a problem asks you to verify something shaped like:

```text
↗
 ↗
  ↗
   ↓
    ↓
     ↓
```

think about:

* Finding the peak
* Checking the increasing side
* Checking the decreasing side

### Important Signal

The array must be **strictly** increasing and decreasing.

That means:

```text
1 < 2 < 3
```

is valid, but:

```text
1 < 2 = 2
```

is not.

Similarly:

```text
3 > 2 > 1
```

is valid, but:

```text
3 > 2 = 2
```

is not.

---

## Walkthrough

Consider:

```text
arr = [0,3,2,1]
```

### Increasing Phase

Initially:

```text
ptr1 = 0
ptr2 = 1
```

Compare:

```text
arr[0] = 0
arr[1] = 3
```

Since:

```text
0 < 3
```

move both pointers.

```text
ptr1 = 1
ptr2 = 2
```

Now:

```text
3 > 2
```

The increasing phase has ended.

Therefore:

```text
peak = 1
```

The array is:

```text
[0, 3, 2, 1]
    ↑
   peak
```

### Decreasing Phase

Start from the peak:

```text
3 > 2 ✓
2 > 1 ✓
```

Everything is strictly decreasing.

Therefore:

```text
true
```

---

## Important Edge Cases

### 1. Too Few Elements

```text
[1,2]
```

Result:

```text
false
```

---

### 2. Only Increasing

```text
[1,2,3,4]
```

There is no decreasing side.

```text
false
```

---

### 3. Only Decreasing

```text
[4,3,2,1]
```

There is no increasing side.

```text
false
```

---

### 4. Equal Adjacent Elements

```text
[1,2,2,1]
```

A mountain must be strictly increasing/decreasing.

```text
false
```

---

### 5. Valid Mountain

```text
[1,3,5,4,2]
```

```text
1 < 3 < 5 > 4 > 2
```

```text
true
```

---

## Complexity

### Time Complexity

```text
O(n)
```

The first loop moves through the increasing portion, and the second loop moves through the decreasing portion.

Together, they process the array linearly.

```text
O(n) + O(n) = O(n)
```

---

### Space Complexity

```text
O(1)
```

Only a few variables are used:

```java
ptr1
ptr2
peak
j
```

No additional array, list, or map is created.

---

## Key Learning

### 1. Break the Problem Into Phases

Instead of checking the entire mountain at once:

```text
Increasing → Peak → Decreasing
```

Handle each phase separately.

---

### 2. Strict Inequality Matters

Mountain arrays require:

```text
<
>
```

not:

```text
<=
>=
```

Equal adjacent values immediately make the array invalid.

---

### 3. Find the Peak First

Once the increasing sequence stops, that position becomes the candidate peak.

Then the remaining problem becomes simple:

> Is everything after the peak strictly decreasing?

---

### 4. Two Pointers Can Be Used for Traversal

Two pointers don't always have to move toward each other.

Here:

```java
ptr1++;
ptr2++;
```

both pointers move forward together to compare adjacent elements.

This is still a useful two-pointer traversal pattern.

---

## Final Takeaway

The main idea is:

```text
Check increasing part
        ↓
Find peak
        ↓
Check decreasing part
        ↓
Valid Mountain
```

The most important pattern to remember is:

> **A valid mountain has exactly two strict phases: increasing before the peak and decreasing after the peak.**

This gives us a simple **O(n) time and O(1) space** solution.

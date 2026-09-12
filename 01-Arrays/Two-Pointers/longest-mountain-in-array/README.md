# WIN #33 — Longest Mountain in Array

## Problem

Given an integer array `arr`, find the length of the **longest mountain subarray**.

A mountain must:

1. Have at least `3` elements.
2. Strictly increase up to a peak.
3. Strictly decrease after the peak.

For example:

```text
[1,4,7,3,2]
```

is a mountain because:

```text
1 < 4 < 7
      ↓
7 > 3 > 2
```

For:

```text
Input:
arr = [2,1,4,7,3,2,5]

Output:
5
```

The longest mountain is:

```text
[1,4,7,3,2]
```

which has length `5`.

---

## My Java Solution

```java
class Solution {
    public int longestMountain(int[] arr) {

        if(arr.length < 3){
            return 0;
        }

        int maxLength = 0;

        for(int i = 1; i < arr.length-1;i++){
            if(arr[i-1] < arr[i] && arr[i] > arr[i+1]){
                int peak = i;
                int left = i;
                int right = i;

                while(left > 0 && arr[left -1] < arr[left]){
                    left--;
                }

                while(right < arr.length-1 && arr[right] > arr[right+1]){
                    right++;
                }

                int currentLength = right-left+1;
                maxLength = Math.max(maxLength, currentLength);
            }
        }

        return maxLength;
    }
}
```

---

## My Thought Process

The most important observation is that every mountain has a **peak**.

For example:

```text
[1,4,7,3,2]
      ↑
     peak
```

The peak is the element where:

```text
arr[i-1] < arr[i] > arr[i+1]
```

So instead of trying to generate every possible subarray, I can:

1. Find every possible peak.
2. Expand to the left while the array is increasing.
3. Expand to the right while the array is decreasing.
4. Calculate the mountain length.
5. Keep the maximum length.

---

# Step 1 — Check Whether an Element Is a Peak

For an element to be a peak:

```java
arr[i-1] < arr[i] && arr[i] > arr[i+1]
```

For:

```text
[2,1,4,7,3,2,5]
```

when:

```text
i = 3
```

we have:

```text
arr[2] = 4
arr[3] = 7
arr[4] = 3
```

Therefore:

```text
4 < 7 > 3
```

So `7` is a valid peak.

---

# Step 2 — Expand to the Left

Once a peak is found, I start from the peak:

```text
[2,1,4,7,3,2,5]
       ↑
      peak
```

Then I keep moving left while:

```text
arr[left - 1] < arr[left]
```

For the example:

```text
7
↑
```

Move left:

```text
4 < 7
```

Then:

```text
1 < 4
```

Then:

```text
2 < 1
```

is false.

So the left boundary is:

```text
[1,4,7]
 ↑
left
```

---

# Step 3 — Expand to the Right

Now start from the peak and move right while:

```java
arr[right] > arr[right+1]
```

From:

```text
[1,4,7,3,2]
      ↑
     peak
```

we have:

```text
7 > 3
3 > 2
```

But:

```text
2 > 5
```

is false.

So the right boundary is:

```text
[1,4,7,3,2]
 ↑       ↑
left    right
```

---

# Step 4 — Calculate the Mountain Length

Once both boundaries are found:

```java
int currentLength = right - left + 1;
```

For:

```text
left = 1
right = 4
```

we get:

```text
4 - 1 + 1 = 4
```

Wait — the mountain is:

```text
[1,4,7,3,2]
```

which contains indices `1` through `5`.

So for this example:

```text
left = 1
right = 5
```

and:

```text
5 - 1 + 1 = 5
```

Therefore:

```text
currentLength = 5
```

We update:

```java
maxLength = Math.max(maxLength, currentLength);
```

---

# Complete Example

Consider:

```text
arr = [2,1,4,7,3,2,5]
```

The peak is:

```text
        7
       / \
      4   3
     /     \
    1       2
```

More precisely:

```text
[2, 1, 4, 7, 3, 2, 5]
    ↑  ↑  ↑  ↑  ↑
    └───────┘
     mountain
```

Starting from peak `7`:

### Expand left

```text
7 ← 4 ← 1
```

Stop because:

```text
2 < 1
```

is false.

### Expand right

```text
7 → 3 → 2
```

Stop because:

```text
2 > 5
```

is false.

Therefore the mountain is:

```text
[1,4,7,3,2]
```

Length:

```text
5
```

---

# Why We Only Start Expansion at a Peak

This condition:

```java
arr[i-1] < arr[i] && arr[i] > arr[i+1]
```

is very important.

If an element isn't a peak, there is no reason to expand from it because a mountain must have a point where the direction changes:

```text
Increasing
    ↓
   PEAK
    ↓
Decreasing
```

For example:

```text
[1,2,3,4]
```

has increasing values but no decreasing side.

Therefore it is not a mountain.

Similarly:

```text
[4,3,2,1]
```

has a decreasing side but no increasing side.

Therefore it is also not a mountain.

---

# Pattern Recognition

## Pattern: Peak Detection + Expansion

This problem can be recognized as:

```text
Find a peak
   ↓
Expand left
   ↓
Expand right
   ↓
Measure the mountain
   ↓
Keep the maximum
```

The key condition is:

```text
arr[i-1] < arr[i] > arr[i+1]
```

Once we find this pattern, the peak becomes the center of a potential mountain.

---

# Important Edge Cases

## Array Has Fewer Than 3 Elements

```text
[1,2]
```

A mountain requires at least `3` elements.

So:

```java
if(arr.length < 3){
    return 0;
}
```

---

## No Peak

For:

```text
[2,2,2]
```

there is no position satisfying:

```text
arr[i-1] < arr[i] > arr[i+1]
```

Therefore:

```text
Output:
0
```

---

## Strictly Increasing

```text
[1,2,3,4,5]
```

There is no decreasing side.

Therefore:

```text
Output:
0
```

---

## Strictly Decreasing

```text
[5,4,3,2,1]
```

There is no increasing side.

Therefore:

```text
Output:
0
```

---

## Multiple Mountains

Consider:

```text
[1,3,2,1,2,4,3]
```

There are multiple possible mountains.

My solution checks every peak and keeps:

```java
maxLength
```

so the largest mountain is returned.

---

# Complexity

## Time Complexity

The outer loop checks every possible peak:

```text
O(n)
```

However, for every detected peak, I expand to the left and right.

In the worst case, many peaks can cause repeated scanning of elements.

Therefore, the worst-case complexity of **my implementation** is:

```text
O(n²)
```

This is important because the problem's follow-up asks for a one-pass solution.

---

## Space Complexity

```text
O(1)
```

I only use a few integer variables:

```text
i
peak
left
right
maxLength
currentLength
```

No additional array or data structure is created.

Therefore:

```text
Space = O(1)
```

---

# About the Follow-Up

The problem asks:

> Can you solve it using only one pass?

My current solution does not fully achieve that.

My approach is:

```text
Find peak
   ↓
Expand left
   ↓
Expand right
```

which can revisit elements.

A more optimized solution can track the increasing and decreasing portions while scanning the array once.

The key idea would be to recognize a mountain while it is being built rather than expanding from every peak separately.

That can achieve:

```text
Time:  O(n)
Space: O(1)
```

---

# Key Learning

### 1. Look for the Peak

A mountain always has a unique turning point:

```text
        peak
         ↓
       /   \
Increasing  Decreasing
```

So finding the peak gives us a natural starting point.

---

### 2. Expand From the Center

Once a valid peak is found, the problem becomes much easier:

```text
          peak
           ↓
    ← expand | expand →
```

We can independently find the left and right boundaries.

---

### 3. Strict Inequality Matters

A mountain requires:

```text
<
```

on the increasing side and:

```text
>
```

on the decreasing side.

Equal values break the mountain.

For example:

```text
[1,2,2,1]
```

is not a mountain because:

```text
1 < 2 < 2
```

is false.

---

### 4. Always Check Your Actual Complexity

This problem is a good reminder that:

> A nested loop does not automatically mean `O(n²)`, but in this particular implementation, repeated expansion can cause `O(n²)` work.

The follow-up encourages us to improve the same idea rather than repeatedly scanning around each peak.

---

## Final Takeaway

My approach is:

```text
Scan for a peak
      ↓
Expand left while increasing
      ↓
Expand right while decreasing
      ↓
Calculate mountain length
      ↓
Update maximum
```

The core pattern is:

```text
Peak Detection
      +
Two-Sided Expansion
```

My current solution achieves:

```text
Time:  O(n²) worst case
Space: O(1)
```

The next optimization to learn from this problem is how to turn this **peak-expansion approach into a true one-pass `O(n)` solution**.

# WIN #62 — Sliding Window Maximum

## Problem

[LeetCode 239 — Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/)

You are given an integer array `nums` and a sliding window of size `k`.

The window moves from left to right one position at a time.

For every window, return the **maximum element** inside that window.

---

## Example 1

```text
Input:
nums = [1,3,-1,-3,5,3,6,7]
k = 3

Output:
[3,3,5,5,6,7]
```

The windows are:

```text
[1, 3, -1] → 3
[3, -1, -3] → 3
[-1, -3, 5] → 5
[-3, 5, 3] → 5
[5, 3, 6] → 6
[3, 6, 7] → 7
```

Therefore:

```text
answer = [3,3,5,5,6,7]
```

---

## Example 2

```text
Input:
nums = [1]
k = 1

Output:
[1]
```

There is only one window:

```text
[1]
```

Therefore the maximum is:

```text
1
```

---

# My Java Solution

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {

        int[] answer = new int[nums.length - k + 1];

        Deque<Integer> dq = new ArrayDeque<>();

        int index = 0;

        for(int right = 0; right < nums.length; right++){

            while(!dq.isEmpty() && dq.peekFirst() <= right - k){
                dq.pollFirst();
            }

            while(!dq.isEmpty() && nums[dq.peekLast()] <= nums[right]){
                dq.pollLast();
            }

            dq.offerLast(right);

            if(right >= k - 1){
                answer[index] = nums[dq.peekFirst()];
                index++;
            }
        }

        return answer;
    }
}
```

---

# The Main Challenge

At first, this looks like a normal fixed-size sliding-window problem.

For example:

```text
[1,3,-1]
```

We need the maximum:

```text
3
```

Then the window moves:

```text
[3,-1,-3]
```

Again we need:

```text
3
```

The obvious approach is to find the maximum in every window.

But finding the maximum of a window takes:

```text
O(k)
```

There are approximately:

```text
O(n)
```

windows.

So the brute-force solution becomes:

```text
O(n × k)
```

which can become:

```text
O(n²)
```

for large `k`.

With:

```text
n = 100000
```

this is too expensive.

We need a way to maintain the maximum while the window moves.

The key data structure is:

```text
Deque
```

More specifically:

```text
Monotonic Deque
```

---

# Core Idea

We don't need to remember every element in the current window.

We only need to remember elements that **could potentially become the maximum**.

Suppose the current window is:

```text
[1, 3, -1]
```

The maximum is:

```text
3
```

Do we need to keep `1`?

No.

Because `3` is larger than `1` and appears after it.

As long as `3` remains inside the window, `1` can never become the maximum.

Similarly, when we see a new element that is larger than elements at the back of the deque, those smaller elements can be removed.

This gives us a decreasing deque.

For example:

```text
3
```

then adding:

```text
-1
```

gives:

```text
3, -1
```

Then adding:

```text
5
```

causes:

```text
3, -1
```

to be removed because both are smaller than `5`.

The deque becomes:

```text
5
```

So the largest possible candidate is always at the **front**.

---

# Why Do We Store Indices Instead of Values?

This is one of the most important details.

The deque stores:

```text
indices
```

not the actual values.

For example:

```text
nums = [1,3,-1]
```

Instead of:

```text
[3,-1]
```

the deque might contain:

```text
[1,2]
```

where:

```text
index 1 → nums[1] = 3
index 2 → nums[2] = -1
```

We store indices because we need to know:

> Is this element still inside the current window?

For that, we need its position.

---

# The Monotonic Deque

The deque maintains two important properties.

### Property 1 — Indices are ordered

The indices are always stored from left to right:

```text
smaller index → larger index
```

because we process the array from left to right.

### Property 2 — Values are decreasing

The corresponding values satisfy:

```text
nums[dq[0]]
    >=
nums[dq[1]]
    >=
nums[dq[2]]
```

For example:

```text
Deque indices:
[3, 5, 6]

Corresponding values:
[7, 5, 2]
```

The values are decreasing.

Therefore:

```text
dq.peekFirst()
```

always points to the largest value currently in the window.

---

# Step 1 — Create the Answer Array

```java
int[] answer = new int[nums.length - k + 1];
```

How many windows are there?

If:

```text
n = nums.length
```

and:

```text
k = window size
```

then the number of windows is:

```text
n - k + 1
```

For:

```text
nums.length = 8
k = 3
```

we have:

```text
8 - 3 + 1 = 6
```

windows.

Therefore:

```text
answer.length = 6
```

---

# Step 2 — Create the Deque

```java
Deque<Integer> dq = new ArrayDeque<>();
```

The deque stores **indices**.

For example:

```text
dq = [1,2,4]
```

means:

```text
nums[1]
nums[2]
nums[4]
```

are the currently useful candidates for the maximum.

---

# Step 3 — Keep Track of the Answer Index

```java
int index = 0;
```

This tells us where to put the next maximum in the result.

For example:

```text
answer = [3,3,5,5,6,7]
          ↑
        index
```

After storing one answer:

```java
index++;
```

---

# Step 4 — Traverse the Array

```java
for(int right = 0; right < nums.length; right++){
```

`right` represents the element currently entering the sliding window.

For example:

```text
right = 0
```

means:

```text
nums[0]
```

is entering the window.

Then:

```text
right = 1
```

means:

```text
nums[1]
```

is being processed.

And so on.

---

# Step 5 — Remove Indices Outside the Window

```java
while(!dq.isEmpty() && dq.peekFirst() <= right - k){
    dq.pollFirst();
}
```

This handles elements that have left the sliding window.

Suppose:

```text
k = 3
right = 3
```

The current window is:

```text
[1,2,3]
```

Index `0` is no longer inside the window.

The condition becomes:

```text
0 <= 3 - 3
0 <= 0
```

which is true.

Therefore index `0` must be removed.

---

# Why `right - k`?

Suppose:

```text
k = 3
```

and:

```text
right = 3
```

The current window contains:

```text
indices:
1, 2, 3
```

Anything at:

```text
index <= 0
```

is outside the window.

Therefore:

```java
dq.peekFirst() <= right - k
```

checks whether the front element is too old.

---

# Important Point

We remove expired elements **from the front**:

```java
dq.pollFirst();
```

because the deque is ordered by index.

The oldest candidate is always toward the front.

---

# Step 6 — Remove Smaller Elements From the Back

This is the most important part of the algorithm:

```java
while(!dq.isEmpty() && nums[dq.peekLast()] <= nums[right]){
    dq.pollLast();
}
```

We compare the new element with the elements at the back.

If the new element is greater than or equal to an existing candidate, that candidate can never become the maximum again.

Therefore we remove it.

---

# Why Can We Remove Smaller Elements?

Suppose:

```text
window = [1,3]
```

and a new value:

```text
5
```

enters.

We now have:

```text
[1,3,5]
```

Can `1` ever become the maximum while `5` is still inside the window?

No.

Can `3` ever become the maximum while `5` is still inside the window?

No.

Therefore both can be discarded as candidates.

The deque becomes:

```text
[5]
```

This is what makes the algorithm efficient.

---

# Example

Suppose the deque represents:

```text
values:
[5,3,2]
```

and a new value:

```text
6
```

arrives.

Compare `6` with the back:

```text
2 <= 6
```

Remove `2`.

Now:

```text
[5,3]
```

Compare again:

```text
3 <= 6
```

Remove `3`.

Again:

```text
[5]
```

Compare:

```text
5 <= 6
```

Remove `5`.

Now:

```text
[]
```

Finally add `6`:

```text
[6]
```

All smaller elements were eliminated.

---

# Step 7 — Add the Current Index

```java
dq.offerLast(right);
```

After removing all useless candidates, we add the current index to the back.

For example:

```text
dq = [1,3]
```

and:

```text
right = 5
```

After:

```java
dq.offerLast(5);
```

we get:

```text
dq = [1,3,5]
```

The values represented by these indices remain decreasing.

---

# Step 8 — Check Whether the First Window Is Complete

```java
if(right >= k - 1){
```

We cannot produce an answer until the window contains `k` elements.

For:

```text
k = 3
```

the first complete window ends at:

```text
right = 2
```

because:

```text
2 >= 3 - 1
```

is true.

Therefore the first answer can be recorded.

---

# Step 9 — The Front Is the Maximum

```java
answer[index] = nums[dq.peekFirst()];
```

This works because the deque maintains decreasing values.

Therefore:

```text
nums[dq.peekFirst()]
```

is always the largest candidate.

For example:

```text
Deque indices:
[1,2]

Values:
[3,-1]
```

The front index is:

```text
1
```

and:

```text
nums[1] = 3
```

Therefore:

```text
maximum = 3
```

---

# Step 10 — Move the Answer Index

```java
index++;
```

After storing one maximum, move to the next position in the result.

---

# Complete Walkthrough

Consider:

```text
nums = [1,3,-1,-3,5,3,6,7]
k = 3
```

We process every element from left to right.

---

## right = 0

Value:

```text
1
```

Deque is empty.

Add index `0`:

```text
dq = [0]
```

Values:

```text
[1]
```

Window isn't complete yet.

---

## right = 1

Value:

```text
3
```

Check back:

```text
nums[0] = 1
1 <= 3
```

Remove index `0`.

```text
dq = []
```

Add index `1`:

```text
dq = [1]
```

Values:

```text
[3]
```

Window isn't complete yet.

---

## right = 2

Value:

```text
-1
```

No expired indices.

Check back:

```text
nums[1] = 3
3 <= -1
```

False.

Add index `2`:

```text
dq = [1,2]
```

Values:

```text
[3,-1]
```

Now:

```text
right >= k - 1
```

because:

```text
2 >= 2
```

So we record:

```text
answer = [3]
```

---

## right = 3

Value:

```text
-3
```

First check expired indices.

Window is:

```text
[3,-1,-3]
```

Index `1` is still valid.

Then compare with back:

```text
nums[2] = -1
-1 <= -3
```

False.

Add index `3`:

```text
dq = [1,2,3]
```

Values:

```text
[3,-1,-3]
```

Maximum:

```text
nums[1] = 3
```

Answer:

```text
[3,3]
```

---

## right = 4

Value:

```text
5
```

Check expired indices.

Index `1` is now outside the window because:

```text
1 <= 4 - 3
1 <= 1
```

Remove it:

```text
dq = [2,3]
```

Now remove smaller elements from the back.

```text
nums[3] = -3 <= 5
```

Remove:

```text
dq = [2]
```

Again:

```text
nums[2] = -1 <= 5
```

Remove:

```text
dq = []
```

Add index `4`:

```text
dq = [4]
```

Maximum:

```text
nums[4] = 5
```

Answer:

```text
[3,3,5]
```

---

## right = 5

Value:

```text
3
```

Index `4` is still valid.

Compare:

```text
nums[4] = 5
5 <= 3
```

False.

Add index `5`:

```text
dq = [4,5]
```

Values:

```text
[5,3]
```

Maximum:

```text
5
```

Answer:

```text
[3,3,5,5]
```

---

## right = 6

Value:

```text
6
```

Index `4` is still inside the window.

Now compare from the back:

```text
nums[5] = 3 <= 6
```

Remove index `5`.

Then:

```text
nums[4] = 5 <= 6
```

Remove index `4`.

Deque becomes:

```text
[]
```

Add index `6`:

```text
dq = [6]
```

Maximum:

```text
6
```

Answer:

```text
[3,3,5,5,6]
```

---

## right = 7

Value:

```text
7
```

Compare:

```text
nums[6] = 6 <= 7
```

Remove index `6`.

Deque:

```text
[]
```

Add index `7`:

```text
dq = [7]
```

Maximum:

```text
7
```

Final answer:

```text
[3,3,5,5,6,7]
```

---

# Visual Representation

For:

```text
nums = [1,3,-1,-3,5,3,6,7]
k = 3
```

The deque maintains only useful candidates.

```text
Window          Deque Values       Maximum
------------------------------------------------
[1,3,-1]        [3,-1]              3
[3,-1,-3]       [3,-1,-3]           3
[-1,-3,5]       [5]                 5
[-3,5,3]        [5,3]               5
[5,3,6]         [6]                 6
[3,6,7]         [7]                 7
```

Notice that the deque does **not** necessarily contain every element in the window.

It only contains elements that are still capable of becoming the maximum.

---

# Why We Remove Smaller Elements

This is the central insight.

Suppose:

```text
window = [4,2]
```

and the next element is:

```text
5
```

The new window is:

```text
[4,2,5]
```

If `5` stays inside the window:

```text
4
```

can never be the maximum.

Neither can:

```text
2
```

Therefore:

```text
4 and 2
```

are useless for future maximum calculations.

We can safely remove them.

This is why the deque remains small.

---

# Why We Remove Expired Elements

Consider:

```text
nums = [9,1,2]
k = 2
```

First window:

```text
[9,1]
```

Maximum:

```text
9
```

Now the window moves:

```text
[1,2]
```

The `9` has left the window.

If we don't remove it from the deque, we might incorrectly return:

```text
9
```

instead of:

```text
2
```

That's why we check:

```java
dq.peekFirst() <= right - k
```

and remove expired indices.

---

# Why the Deque Is Monotonic

The deque maintains values in decreasing order.

For example:

```text
[8,6,4,2]
```

This means:

```text
front → largest
back  → smallest
```

Whenever a new value arrives, we remove smaller values from the back.

Therefore the decreasing property is preserved.

This is why the data structure is called a:

```text
Monotonic Deque
```

---

# Why We Use a Deque Instead of a Stack

A stack would allow us to remove smaller elements from one side.

But we need **two different operations**:

### Remove expired elements

From the:

```text
front
```

### Remove smaller elements

From the:

```text
back
```

Therefore we need a data structure supporting both ends.

That's exactly what a:

```text
Deque
```

provides.

---

# Why We Don't Store Values

Suppose:

```text
nums = [1,1,1]
```

If the deque only stored values:

```text
[1,1,1]
```

we would not know which `1` has left the window.

But with indices:

```text
[0,1,2]
```

we know exactly which element belongs to which position.

Therefore indices are essential.

---

# Pattern Recognition

## Pattern: Fixed-Size Sliding Window + Monotonic Deque

This problem combines two patterns:

```text
Fixed-Size Sliding Window
+
Monotonic Deque
```

The sliding window tells us:

> Only elements inside the current window matter.

The monotonic deque tells us:

> Among those elements, keep only candidates that can still become the maximum.

The overall structure is:

```text
Array
  ↓
Sliding Window
  ↓
Monotonic Deque
  ↓
Maximum at Front
```

---

# The General Monotonic Deque Pattern

For a **maximum** sliding window:

```text
while deque.back is smaller than or equal to current:
    remove deque.back

add current
```

Therefore the deque is decreasing:

```text
largest
   ↓
smaller
   ↓
smaller
   ↓
smallest
```

For a **minimum** sliding window, the logic is reversed.

We would remove larger elements instead:

```text
while deque.back is greater than or equal to current:
    remove deque.back
```

This creates an increasing deque.

---

# Important Difference From Basic Sliding Window

A basic fixed-size sliding window often looks like:

```text
Add new element
Remove old element
Update answer
```

For example, in a sum problem:

```text
currentSum += nums[right]
currentSum -= nums[right-k]
```

But here, maintaining the maximum is harder.

We cannot simply maintain:

```text
currentMax
```

because when the maximum leaves the window, we would need to find the next maximum.

The monotonic deque solves exactly this problem.

---

# Why Not Just Use a Max Heap?

A Max Heap can maintain the maximum efficiently.

However, we also need to remove elements that have gone outside the window.

We would need to keep track of their indices and handle stale elements.

A heap-based solution can achieve:

```text
O(n log k)
```

but the monotonic deque can do:

```text
O(n)
```

because each element is added once and removed at most once from the deque.

---

# Why Not Sort Every Window?

Sorting every window would require:

```text
O(k log k)
```

per window.

Total:

```text
O(nk log k)
```

which is far too expensive for:

```text
n = 100000
```

The monotonic deque avoids sorting completely.

---

# Why Is the Algorithm O(n)?

At first, the nested `while` loops might look like they make the algorithm O(n²):

```java
while(...)
```

inside:

```java
for(...)
```

But they don't.

Every index can be:

```text
Added to deque → once
Removed from deque → at most once
```

An element cannot be removed multiple times.

Therefore, across the entire algorithm:

```text
Number of additions ≤ n
Number of removals ≤ n
```

So the total work is:

```text
O(n)
```

This is an important amortized-analysis pattern.

---

# Amortized Complexity

For every element:

```text
offerLast()
```

happens at most once.

And:

```text
pollFirst()
```

or:

```text
pollLast()
```

can happen at most once for that element.

Therefore:

```text
Total deque operations = O(n)
```

Hence:

```text
Time = O(n)
```

---

# Edge Cases

## 1. k = 1

```text
nums = [1,3,2]
k = 1
```

Every window contains one element:

```text
[1] → 1
[3] → 3
[2] → 2
```

Answer:

```text
[1,3,2]
```

---

## 2. k = nums.length

```text
nums = [1,3,-1]
k = 3
```

There is only one window:

```text
[1,3,-1]
```

Maximum:

```text
3
```

Answer:

```text
[3]
```

---

## 3. Decreasing Array

```text
nums = [5,4,3,2,1]
k = 3
```

Windows:

```text
[5,4,3] → 5
[4,3,2] → 4
[3,2,1] → 3
```

Answer:

```text
[5,4,3]
```

The deque will contain several indices because every new value is smaller than the previous one.

---

## 4. Increasing Array

```text
nums = [1,2,3,4,5]
k = 3
```

Windows:

```text
[1,2,3] → 3
[2,3,4] → 4
[3,4,5] → 5
```

Each new value removes all smaller values from the back.

The deque often contains only one element.

---

## 5. Duplicate Values

```text
nums = [2,2,2,2]
k = 2
```

The condition:

```java
nums[dq.peekLast()] <= nums[right]
```

removes the older equal value.

Therefore the deque keeps the newest occurrence.

This is valid because the newer equal value will remain in the window longer.

---

# Why `<=` Instead of `<`?

The code uses:

```java
nums[dq.peekLast()] <= nums[right]
```

instead of:

```java
nums[dq.peekLast()] < nums[right]
```

This means equal values are also removed.

Suppose:

```text
nums = [2,2]
```

When the second `2` arrives:

```text
2 <= 2
```

is true.

So the older `2` is removed.

The newer `2` remains.

This is safe because both values are equal, but the newer one has a longer future lifetime inside upcoming windows.

---

# The Most Important Lines

These three parts are the heart of the solution:

### 1. Remove expired elements

```java
while(!dq.isEmpty() && dq.peekFirst() <= right - k){
    dq.pollFirst();
}
```

Meaning:

> Remove elements that are no longer inside the window.

---

### 2. Remove useless smaller elements

```java
while(!dq.isEmpty() && nums[dq.peekLast()] <= nums[right]){
    dq.pollLast();
}
```

Meaning:

> If the new element is larger, older smaller elements can never become the maximum.

---

### 3. Maximum is at the front

```java
answer[index] = nums[dq.peekFirst()];
```

Meaning:

> Because the deque is monotonically decreasing, the front always contains the maximum.

---

# Complete Algorithm

```text
Start
  ↓
Create result array
  ↓
Create empty deque
  ↓
For every index right
  ↓
Remove indices outside current window
  ↓
Remove smaller/equal elements from deque back
  ↓
Add current index
  ↓
If window size is k
  ↓
Front of deque = maximum
  ↓
Store maximum
  ↓
Move to next index
  ↓
Return result
```

---

# Visual Summary

```text
nums:
1   3   -1   -3   5   3   6   7
    ↑
   k=3
```

The deque stores only useful candidates.

```text
Window       Deque       Maximum
---------------------------------
1 3 -1       3,-1          3
3 -1 -3      3,-1,-3       3
-1 -3 5      5             5
-3 5 3       5,3           5
5 3 6        6             6
3 6 7        7             7
```

Final:

```text
[3,3,5,5,6,7]
```

---

# Complexity

Let:

```text
n = nums.length
```

## Time Complexity

Every element:

```text
enters deque once
```

and:

```text
leaves deque at most once
```

Therefore:

```text
O(n)
```

---

## Space Complexity

The deque can contain at most `k` indices.

The result array contains:

```text
n - k + 1
```

elements.

Therefore the auxiliary space used by the deque is:

```text
O(k)
```

and the output requires:

```text
O(n - k + 1)
```

space.

So the total space including the output is:

```text
O(n)
```

while the **extra auxiliary space excluding the output** is:

```text
O(k)
```

---

# Brute Force vs Monotonic Deque

| Approach            |         Time | Extra Space | Main Idea                               |
| ------------------- | -----------: | ----------: | --------------------------------------- |
| Brute Force         |   `O(n × k)` |      `O(1)` | Find max in every window                |
| Max Heap            | `O(n log k)` |      `O(k)` | Maintain maximum with heap              |
| **Monotonic Deque** |     **O(n)** |    **O(k)** | Maintain only useful maximum candidates |

The important improvement is:

```text
Brute Force
    ↓
Recalculate maximum every time

Monotonic Deque
    ↓
Reuse information from previous windows
    ↓
Discard elements that can never become maximum
```

---

# Final Takeaway

The key idea is not simply:

> "Use a deque."

The real insight is:

> **Maintain a decreasing deque of indices containing only elements that can still become the maximum of the current or a future window.**

For every new element:

```text
1. Remove expired indices from the front.
2. Remove smaller/equal values from the back.
3. Add the current index.
4. The front is the maximum.
```

The pattern to remember is:

```text
Fixed-Size Sliding Window
            +
      Monotonic Deque
            ↓
      O(n) Maximum
```

Once you recognize a problem asking for the **maximum/minimum over every fixed-size sliding window**, the **monotonic deque** should immediately come to mind.

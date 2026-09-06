# WIN #18 — Minimum Size Subarray Sum

## Problem

Given an array of positive integers `nums` and a positive integer `target`, find the **minimum length of a contiguous subarray** whose sum is greater than or equal to `target`.

If no such subarray exists, return `0`.

For example:

```text
Input:
target = 7
nums = [2,3,1,2,4,3]

Output:
2
```

The subarray:

```text
[4,3]
```

has a sum of:

```text
4 + 3 = 7
```

and its length is:

```text
2
```

No valid subarray with length `1` exists, so the answer is `2`.

Another example:

```text
Input:
target = 4
nums = [1,4,4]

Output:
1
```

Because:

```text
[4]
```

already has a sum greater than or equal to `4`.

If no contiguous subarray satisfies the condition:

```text
Input:
target = 11
nums = [1,1,1,1,1,1,1,1]

Output:
0
```

---

## My Java Solution

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {

        int left = 0;
        int right = 0;
        int sum = 0;
        int size = Integer.MAX_VALUE;
        boolean isFound = false;

        while(right < nums.length){
            sum += nums[right];

            while(sum >= target){
                int currentLength = right - left + 1;
                size = Math.min(size, currentLength);
                sum = sum - nums[left];
                left++;
                isFound = true;
            }

            right++;
        }

        if(isFound){
            return size;
        }

        return 0;
    }
}
```

---

## My Thought Process

The first thing I noticed is that the problem asks for a **contiguous subarray**.

That means the elements I choose must be next to each other.

For example:

```text
[2,3,1,2,4,3]
```

A valid subarray could be:

```text
[3,1,2,4]
```

but I cannot choose:

```text
[2,1,4]
```

because those elements are not contiguous.

The second important observation is that all the numbers in the array are **positive integers**.

This is very important for the sliding window approach.

Because every number is positive:

```text
Adding a new element
    ↓
Sum increases

Removing an element
    ↓
Sum decreases
```

This predictable behavior allows me to maintain a window and adjust it dynamically.

So instead of checking every possible subarray, I use two pointers:

```text
left
right
```

to represent the current window.

The overall idea is:

```text
Expand the window
      ↓
Add nums[right] to sum
      ↓
If sum >= target
      ↓
Try shrinking from the left
      ↓
Keep the smallest valid window
```

---

## Sliding Window

The current window is represented by:

```text
[left ... right]
```

For example:

```text
nums = [2,3,1,2,4,3]

       left
        ↓
[2,3,1,2,4,3]
          ↑
        right
```

Everything between `left` and `right` belongs to the current subarray.

I maintain its sum using:

```java
int sum = 0;
```

Instead of recalculating the sum every time, I update it incrementally.

When `right` moves forward:

```java
sum += nums[right];
```

When `left` moves forward:

```java
sum = sum - nums[left];
```

This is what makes the sliding window efficient.

---

## Step 1: Initialize the Window

I start with:

```java
int left = 0;
int right = 0;
int sum = 0;
```

So initially:

```text
left = 0
right = 0
sum = 0
```

I also initialize:

```java
int size = Integer.MAX_VALUE;
```

This stores the smallest valid subarray length found so far.

Initially, I don't know any valid subarray, so I use a very large value.

I also use:

```java
boolean isFound = false;
```

This keeps track of whether I found at least one valid subarray.

---

## Step 2: Expand the Window

The outer loop is:

```java
while(right < nums.length)
```

For every position of `right`, I add the new element to the current window:

```java
sum += nums[right];
```

For example:

```text
target = 7
nums = [2,3,1,2,4,3]
```

Initially:

```text
[2]
```

The sum is:

```text
2
```

Then `right` moves forward:

```text
[2,3]
```

The sum becomes:

```text
2 + 3 = 5
```

Then:

```text
[2,3,1]
```

The sum becomes:

```text
2 + 3 + 1 = 6
```

Then:

```text
[2,3,1,2]
```

The sum becomes:

```text
2 + 3 + 1 + 2 = 8
```

Now:

```text
sum >= target
8 >= 7
```

So I have found a valid window.

---

## Step 3: Shrink the Window

This is the most important part of the solution:

```java
while(sum >= target)
```

Once the current window reaches the target, I don't immediately move `right`.

Instead, I try to make the window **smaller**.

Why?

Because the problem asks for the **minimum length**.

For example:

```text
[2,3,1,2]
```

has:

```text
sum = 8
length = 4
```

It is valid, but maybe I can remove something from the left and still remain valid.

So I calculate:

```java
int currentLength = right - left + 1;
```

For:

```text
left = 0
right = 3
```

we get:

```text
3 - 0 + 1 = 4
```

Then I update the best answer:

```java
size = Math.min(size, currentLength);
```

So:

```text
size = 4
```

---

## Step 4: Remove the Left Element

After recording the current window, I remove the leftmost element:

```java
sum = sum - nums[left];
left++;
```

For:

```text
[2,3,1,2]
```

I remove `2`.

The window becomes:

```text
[3,1,2]
```

and the sum becomes:

```text
3 + 1 + 2 = 6
```

Now:

```text
6 < 7
```

so the window is no longer valid.

The inner loop stops.

---

## Step 5: Continue Expanding

Now the outer loop continues and `right` moves forward.

The next element is `4`.

The current window becomes:

```text
[3,1,2,4]
```

The sum becomes:

```text
3 + 1 + 2 + 4 = 10
```

Again:

```text
sum >= target
```

So I start shrinking from the left.

Current window:

```text
[3,1,2,4]
```

Length:

```text
4
```

But I already have:

```text
size = 4
```

so there is no improvement yet.

Remove `3`:

```text
[1,2,4]
```

Sum:

```text
1 + 2 + 4 = 7
```

This is still valid.

Its length is:

```text
3
```

So:

```text
size = 3
```

Remove `1`:

```text
[2,4]
```

Sum:

```text
2 + 4 = 6
```

Now the sum is below the target, so shrinking stops.

The smallest valid window found so far is:

```text
[1,2,4]
```

with length:

```text
3
```

---

## Continue Until the End

Eventually, the window reaches:

```text
[4,3]
```

Its sum is:

```text
4 + 3 = 7
```

and its length is:

```text
2
```

So:

```text
size = 2
```

No smaller valid window exists.

Therefore, the final answer is:

```text
2
```

---

## Complete Example Walkthrough

Consider:

```text
target = 7
nums = [2,3,1,2,4,3]
```

The window changes approximately like this:

```text
[2]
sum = 2
```

Not enough, so expand:

```text
[2,3]
sum = 5
```

Still not enough:

```text
[2,3,1]
sum = 6
```

Still not enough:

```text
[2,3,1,2]
sum = 8
```

Now the window is valid.

Record:

```text
length = 4
```

Shrink:

```text
[3,1,2]
sum = 6
```

Not valid anymore.

Continue expanding:

```text
[3,1,2,4]
sum = 10
```

Valid.

Record:

```text
length = 4
```

Shrink:

```text
[1,2,4]
sum = 7
length = 3
```

Update:

```text
size = 3
```

Shrink again:

```text
[2,4]
sum = 6
```

Not valid.

Continue:

```text
[2,4,3]
sum = 9
```

Valid.

Record:

```text
length = 3
```

Shrink:

```text
[4,3]
sum = 7
length = 2
```

Update:

```text
size = 2
```

Shrink again:

```text
[3]
sum = 3
```

Not valid.

Final answer:

```text
2
```

---

## Why the Inner `while` Loop Is Important

The inner loop:

```java
while(sum >= target)
```

is what allows me to find the **minimum** valid window.

If I only checked:

```java
if(sum >= target)
```

I would find a valid subarray, but I might not find the smallest one.

For example:

```text
[2,3,1,2]
```

has a sum of `8`.

But I should continue removing elements from the left:

```text
[3,1,2]
```

Then:

```text
[1,2,4]
```

and so on.

The process is:

```text
Window becomes valid
        ↓
Record its length
        ↓
Remove left element
        ↓
Check again
        ↓
Keep shrinking while valid
```

This is the core of the minimum-length sliding window pattern.

---

## Why Positive Numbers Matter

The sliding window technique works here because all numbers are positive.

Suppose:

```text
sum >= target
```

If I remove:

```text
nums[left]
```

the sum will definitely decrease.

Similarly, when I move `right` forward and add a positive number, the sum will definitely increase.

Therefore:

```text
Expand → sum increases

Shrink → sum decreases
```

This gives the two pointers predictable behavior.

If negative numbers were allowed, this relationship would no longer hold.

For example:

```text
[5,-10,10]
```

Removing an element could behave very differently because the values can increase or decrease the sum unpredictably.

So one of the biggest clues for this problem is:

```text
Positive integers
+
Contiguous subarray
+
Minimum/maximum window
```

which strongly suggests **Sliding Window**.

---

## Why I Use `Integer.MAX_VALUE`

I initialize:

```java
int size = Integer.MAX_VALUE;
```

because I need to store the smallest length found.

Suppose the first valid window has length:

```text
5
```

Then:

```text
size = Math.min(Integer.MAX_VALUE, 5)
```

gives:

```text
size = 5
```

If later I find a window of length `3`:

```text
size = Math.min(5, 3)
```

gives:

```text
size = 3
```

So `size` always represents:

```text
Smallest valid window found so far
```

---

## Why I Use `isFound`

I also maintain:

```java
boolean isFound = false;
```

Whenever I find a valid window:

```java
isFound = true;
```

At the end:

```java
if(isFound){
    return size;
}
```

Otherwise:

```java
return 0;
```

This allows me to distinguish between:

```text
A valid subarray was found
```

and:

```text
No valid subarray exists
```

For example:

```text
target = 10
nums = [1,1,1]
```

The sum never reaches `10`.

Therefore:

```text
isFound = false
```

and the solution returns:

```text
0
```

---

## Pattern Recognition

### Pattern: Sliding Window / Two Pointers

This problem is a classic **Sliding Window** problem.

The general pattern is:

```text
left = 0
right = 0

while(right < n):

    Add nums[right]

    while(window satisfies condition):

        Update answer

        Remove nums[left]

        left++

    right++
```

For this problem, the condition is:

```text
sum >= target
```

and the goal is:

```text
Minimum window length
```

So the pattern becomes:

```text
Expand window
      ↓
Sum reaches target
      ↓
Record window length
      ↓
Shrink window
      ↓
Try to make it smaller
      ↓
Continue
```

A useful recognition rule is:

```text
Contiguous subarray
+
Positive numbers
+
Find minimum/maximum length
+
Condition based on sum
```

Think:

```text
SLIDING WINDOW
```

---

## Folder Structure

**Problem Number:** 209
**Problem Name:** Minimum Size Subarray Sum

**Category:** Arrays
**Pattern:** Sliding Window / Two Pointers

**Folder:**

```text
01-Arrays/Sliding-Window/minimum-size-subarray-sum/
```

Repository structure:

```text
01-Arrays/
└── Sliding-Window/
    └── minimum-size-subarray-sum/
        ├── README.md
        └── Solution.java
```

---

## Complexity

### Time Complexity

```text
O(n)
```

At first glance, there are two loops:

```java
while(right < nums.length)
```

and:

```java
while(sum >= target)
```

It might look like `O(n²)`, but the important point is that both pointers only move **forward**.

The `right` pointer moves from:

```text
0 → n - 1
```

at most once.

The `left` pointer also moves from:

```text
0 → n - 1
```

at most once.

Therefore, across the entire algorithm:

```text
right moves at most n times
left moves at most n times
```

So the total work is:

```text
O(n) + O(n)
```

which simplifies to:

```text
O(n)
```

---

### Space Complexity

```text
O(1)
```

Only a constant number of variables are used:

```text
left
right
sum
size
isFound
currentLength
```

No additional array, `HashMap`, `HashSet`, or other data structure is created.

Therefore:

```text
Auxiliary Space = O(1)
```

---

## Key Learning

I learned how the **Sliding Window** technique can efficiently solve contiguous subarray problems.

The most important idea is that I don't need to calculate the sum of every possible subarray.

Instead, I maintain one window:

```text
[left ... right]
```

and dynamically adjust it.

The strategy is:

```text
Expand
  ↓
Make the window valid
  ↓
Record the answer
  ↓
Shrink
  ↓
Find the smallest valid window
```

The key reason this works efficiently is that all numbers are positive.

That gives me the predictable behavior:

```text
Add element → sum increases

Remove element → sum decreases
```

I also learned an important complexity lesson: having a nested `while` loop does **not automatically mean `O(n²)`**.

Here, both `left` and `right` move only forward through the array, so each element is processed a limited number of times.

Therefore, the solution achieves:

```text
O(n) Time
O(1) Auxiliary Space
```

The main pattern I want to remember from this problem is:

```text
Contiguous Subarray
        +
Positive Numbers
        +
Minimum/Maximum Length
        ↓
Sliding Window
```

This is a very important pattern for array and string problems, especially when I need to find the smallest or largest window satisfying some condition.

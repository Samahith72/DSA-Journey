# WIN #27 — 3Sum Closest

## Problem

Given an integer array `nums` and an integer `target`, find three integers at **distinct indices** whose sum is closest to `target`.

Return the sum of those three integers.

It is guaranteed that there is exactly one closest solution.

For example:

```text
Input:
nums = [-1,2,1,-4]
target = 1

Output:
2
```

The possible three-number combinations include:

```text
-1 + 2 + 1 = 2
-1 + 2 + (-4) = -3
-1 + 1 + (-4) = -4
2 + 1 + (-4) = -1
```

The sum closest to `1` is:

```text
2
```

because:

```text
|2 - 1| = 1
```

---

## My Java Solution

```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];

        for(int i=0; i < nums.length-2;i++){
            int left = i+1;
            int right = nums.length-1;

            while(left < right){
                int sum = nums[i]+ nums[left]+nums[right];

                if(Math.abs(sum - target) < Math.abs(closestSum - target)){
                    closestSum = sum;
                }

                if(sum < target){
                    left++;
                }else if(sum > target){
                    right--;
                }else{
                    return sum;
                }
            }
        }

        return closestSum;
    }
}
```

---

## My Thought Process

The problem asks us to find three numbers whose sum is **as close as possible** to `target`.

A brute-force approach would be to try every possible combination of three numbers.

That would require three nested loops and take:

```text
O(n³)
```

time.

Instead, I can use the fact that **sorting the array gives me useful information about how the sum changes**.

So the first step is:

```java
Arrays.sort(nums);
```

Then I use:

```text
i      → fixes the first number
left   → searches from the left
right  → searches from the right
```

This reduces the problem from:

```text
Three nested loops
```

to:

```text
One loop + Two Pointers
```

---

## Step 1: Sort the Array

Suppose:

```text
nums = [-1,2,1,-4]
```

After sorting:

```text
[-4,-1,1,2]
```

Now the numbers have a predictable order.

For a fixed `i`, if I increase `left`, the sum increases.

If I decrease `right`, the sum decreases.

This is what makes the Two Pointer approach possible.

---

## Step 2: Initialize `closestSum`

I start with:

```java
int closestSum = nums[0] + nums[1] + nums[2];
```

This gives me an initial answer to compare against.

For:

```text
nums = [-4,-1,1,2]
```

the initial value is:

```text
-4 + (-1) + 1 = -4
```

Now every new sum can be compared against this current best answer.

---

## Step 3: Fix One Number

The outer loop:

```java
for(int i=0; i < nums.length-2;i++)
```

fixes the first number.

For example:

```text
nums = [-4,-1,1,2]
        ↑
        i
```

Once `nums[i]` is fixed, I only need to find two more numbers.

So:

```java
int left = i+1;
int right = nums.length-1;
```

gives:

```text
[-4, -1, 1, 2]
 ↑    ↑     ↑
 i   left  right
```

---

## Step 4: Calculate the Current Sum

I calculate:

```java
int sum = nums[i] + nums[left] + nums[right];
```

For the first iteration:

```text
-4 + (-1) + 2 = -3
```

Now I need to determine how close `-3` is to the target.

---

## Finding the Closest Sum

The important comparison is:

```java
Math.abs(sum - target)
```

This gives the **distance between the current sum and the target**.

For example:

```text
target = 1
sum = 2
```

Then:

```text
|2 - 1| = 1
```

If the current sum is:

```text
sum = -3
```

then:

```text
|-3 - 1| = 4
```

So `2` is much closer.

My condition is:

```java
if(Math.abs(sum - target) < Math.abs(closestSum - target)){
    closestSum = sum;
}
```

In simple terms:

> If the current sum is closer to the target than my previous best sum, update `closestSum`.

---

## The Important Part: Moving the Pointers

After calculating the sum, I need to decide which pointer to move.

There are three cases.

### Case 1: `sum < target`

```java
if(sum < target){
    left++;
}
```

The current sum is too small.

Because the array is sorted, moving `left` to the right gives us a **larger number**.

Therefore:

```text
sum increases
```

Example:

```text
nums = [-4,-1,1,2]
          ↑     ↑
        left   right

sum = -4 + (-1) + 2
    = -3
```

Since:

```text
-3 < target
```

we move:

```text
left++
```

Now `left` points to `1`, which is larger than `-1`.

---

### Case 2: `sum > target`

```java
else if(sum > target){
    right--;
}
```

The current sum is too large.

Because the array is sorted, moving `right` to the left gives us a **smaller number**.

Therefore:

```text
sum decreases
```

So:

```text
right--
```

helps bring the sum closer to the target.

---

### Case 3: `sum == target`

```java
else{
    return sum;
}
```

If:

```text
sum == target
```

then the difference is:

```text
0
```

Nothing can be closer than an exact match.

So we can immediately return the sum.

---

## Example Walkthrough

Consider:

```text
nums = [-1,2,1,-4]
target = 1
```

After sorting:

```text
[-4,-1,1,2]
```

Initial:

```text
closestSum = -4
```

---

### `i = 0`

```text
i = -4
left = -1
right = 2
```

Calculate:

```text
-4 + (-1) + 2 = -3
```

Compare with target:

```text
|-3 - 1| = 4
```

Current `closestSum` is `-4`:

```text
|-4 - 1| = 5
```

So `-3` is better.

Update:

```text
closestSum = -3
```

Since:

```text
-3 < 1
```

move:

```text
left++
```

---

### Move `left`

Now:

```text
i = -4
left = 1
right = 2
```

Calculate:

```text
-4 + 1 + 2 = -1
```

Distance:

```text
|-1 - 1| = 2
```

This is closer than `-3`.

Update:

```text
closestSum = -1
```

Since:

```text
-1 < 1
```

move:

```text
left++
```

Now `left == right`, so this `i` is finished.

---

### `i = 1`

Now:

```text
i = -1
left = 1
right = 2
```

Calculate:

```text
-1 + 1 + 2 = 2
```

Distance from target:

```text
|2 - 1| = 1
```

Current best:

```text
|-1 - 1| = 2
```

So update:

```text
closestSum = 2
```

Now:

```text
sum > target
```

so:

```text
right--
```

The pointers meet.

No exact `1` was found.

Finally:

```text
return closestSum;
```

returns:

```text
2
```

---

## Why Does Moving the Pointers Work?

This is the most important idea in the problem.

After sorting:

```text
[-4,-1,1,2]
```

Suppose:

```text
sum < target
```

The sum is too small.

Moving `right` left would make the sum even smaller, which is the wrong direction.

So we move:

```text
left++
```

because that gives us a larger value.

Similarly, if:

```text
sum > target
```

the sum is too large.

Moving `left` right would make it even larger.

So we move:

```text
right--
```

to decrease the sum.

The sorted order tells us exactly which direction to move.

---

## Pattern Recognition

### Pattern: Sorting + Two Pointers

This problem is a variation of the **3Sum** family of problems.

The general pattern is:

```text
Sort the array
      ↓
Fix one element
      ↓
Use two pointers for the remaining two
      ↓
Calculate the sum
      ↓
Compare distance from target
      ↓
Move left/right based on sum
```

The key relationship is:

```text
sum < target → left++

sum > target → right--

sum == target → return immediately
```

This is one of the most useful Two Pointer patterns to remember.

---

## Why `Math.abs()` Is Important

The goal is not simply to find a sum that is smaller or larger than the target.

We need the sum with the **smallest absolute difference**.

For example:

```text
target = 10
```

Suppose we have:

```text
sum1 = 8
sum2 = 12
```

Both are equally close:

```text
|8 - 10|  = 2
|12 - 10| = 2
```

Therefore, we need to compare:

```java
Math.abs(sum - target)
```

rather than simply comparing the sums themselves.

---

## Why Can We Return Immediately When `sum == target`?

Suppose:

```text
target = 5
sum = 5
```

The difference is:

```text
|5 - 5| = 0
```

A difference of zero is the smallest possible difference.

Therefore, no other combination can be closer.

So:

```java
return sum;
```

is safe.

---

## Complexity

### Time Complexity

First, the array is sorted:

```text
O(n log n)
```

Then:

* The outer loop runs `O(n)` times.
* For each `i`, the two pointers together move through the remaining array in `O(n)` time.

Therefore:

```text
O(n²)
```

for the Two Pointer portion.

Overall:

```text
O(n log n) + O(n²)
```

which simplifies to:

```text
O(n²)
```

### Space Complexity

The algorithm itself uses only a few variables:

```text
i
left
right
closestSum
sum
```

So the auxiliary space is:

```text
O(1)
```

excluding the space used internally by the sorting implementation.

---

## Key Learning

* Sorting can make a difficult search problem much easier.
* After sorting, Two Pointers can replace an additional nested loop.
* `sum < target` → increase `left`.
* `sum > target` → decrease `right`.
* `sum == target` → exact answer, return immediately.
* To find the closest value, compare the **absolute difference** from the target.
* `closestSum` should be initialized with a valid three-element sum before comparisons begin.
* The overall time complexity is `O(n²)`.

---

## Final Takeaway

The solution can be remembered as:

```text
Sort the array
      ↓
Fix nums[i]
      ↓
left = i + 1
right = n - 1
      ↓
Calculate 3-number sum
      ↓
Update closestSum if necessary
      ↓
sum < target → left++
sum > target → right--
sum == target → return
      ↓
Repeat
```

The key insight is:

> **Sorting gives direction to the Two Pointer movement. If the sum is too small, move left forward to increase it. If the sum is too large, move right backward to decrease it.**

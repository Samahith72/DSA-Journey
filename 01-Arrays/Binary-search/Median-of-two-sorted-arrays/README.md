# WIN #37 — Median of Two Sorted Arrays

## Problem

Given two sorted arrays `nums1` and `nums2`, return the **median** of the two arrays combined.

The required time complexity is:

```text
O(log(m + n))
```

For example:

```text
Input:
nums1 = [1,3]
nums2 = [2]

Merged:
[1,2,3]

Median:
2
```

Another example:

```text
Input:
nums1 = [1,2]
nums2 = [3,4]

Merged:
[1,2,3,4]

Median:
(2 + 3) / 2 = 2.5
```

---

## My Java Solution

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        if(nums1.length > nums2.length){
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int low = 0;
        int high = m;

        while(low <= high){

            int cut1 = (low + high) / 2;
            int cut2 = (m + n + 1) / 2 - cut1;

            double left1 = (cut1 == 0)
                    ? Integer.MIN_VALUE
                    : nums1[cut1 - 1];

            double left2 = (cut2 == 0)
                    ? Integer.MIN_VALUE
                    : nums2[cut2 - 1];

            double right1 = (cut1 == m)
                    ? Integer.MAX_VALUE
                    : nums1[cut1];

            double right2 = (cut2 == n)
                    ? Integer.MAX_VALUE
                    : nums2[cut2];

            if(left1 <= right2 && left2 <= right1){

                if((m + n) % 2 == 1){
                    return Math.max(left1, left2);
                }

                double leftMax = Math.max(left1, left2);
                double rightMin = Math.min(right1, right2);

                return (leftMax + rightMin) / 2.0;
            }

            if(left1 > right2){
                high = cut1 - 1;
            }else{
                low = cut1 + 1;
            }
        }

        return 0.0;
    }
}
```

---

## My Thought Process

The obvious approach would be:

```text
nums1 + nums2
      ↓
merge them
      ↓
find median
```

But that would require at least:

```text
O(m + n)
```

time.

The problem specifically requires:

```text
O(log(m+n))
```

So I cannot actually merge the arrays.

The important observation is:

> **Both arrays are already sorted.**

That means I can use the sorted structure to find the median without constructing the merged array.

The key idea is to find a **partition** that divides the combined elements into:

```text
LEFT HALF | RIGHT HALF
```

such that every element on the left is less than or equal to every element on the right.

---

# The Main Idea — Find the Correct Partition

Imagine the two arrays:

```text
nums1 = [1, 3]
nums2 = [2, 4, 5, 6]
```

The combined sorted order would be:

```text
[1, 2, 3 | 4, 5, 6]
```

We want to find the boundary:

```text
LEFT HALF | RIGHT HALF
```

The median will be right around this boundary.

Instead of actually merging the arrays, I decide how many elements should come from each array.

For example:

```text
nums1 = [1, 3]
             ↑
           cut1

nums2 = [2, 4, 5, 6]
          ↑
        cut2
```

The partition creates:

```text
nums1:
[1 | 3]

nums2:
[2 | 4,5,6]
```

Combined:

```text
LEFT:  [1,2]
RIGHT: [3,4,5,6]
```

The partition is correct only if:

```text
everything on LEFT <= everything on RIGHT
```

---

# Step 1 — Always Binary Search the Smaller Array

My solution starts with:

```java
if(nums1.length > nums2.length){
    return findMedianSortedArrays(nums2, nums1);
}
```

This ensures:

```text
m <= n
```

where:

```text
m = nums1.length
n = nums2.length
```

This is important because I perform binary search on `nums1`.

Therefore, the binary search runs on the smaller array.

Instead of searching over:

```text
m + n
```

elements, I only search over:

```text
m
```

possible partition positions.

So the complexity becomes:

```text
O(log m)
```

which satisfies the required:

```text
O(log(m+n))
```

---

# Step 2 — Binary Search the Partition

I initialize:

```java
int low = 0;
int high = m;
```

The partition in `nums1` can be anywhere from:

```text
0
```

to:

```text
m
```

For example:

```text
nums1 = [1,3,5,7]
```

Possible cuts are:

```text
| 1 3 5 7
1 | 3 5 7
1 3 | 5 7
1 3 5 | 7
1 3 5 7 |
```

So:

```text
cut1 ∈ [0,m]
```

Binary search helps us quickly find the correct one.

---

# Step 3 — Calculate `cut2`

This is the most important equation:

```java
int cut2 = (m+n+1)/2 - cut1;
```

Why?

Because we want the left side of the partition to contain half of the total elements.

Suppose:

```text
m = 2
n = 4
```

Total:

```text
m+n = 6
```

We need:

```text
6 / 2 = 3
```

elements on the left.

If `cut1` contributes `1` element:

```text
cut1 = 1
```

then `nums2` must contribute:

```text
3 - 1 = 2
```

So:

```text
cut2 = 2
```

The two cuts always work together.

---

# Why `(m+n+1)/2`?

We use:

```java
(m+n+1)/2
```

instead of simply:

```java
(m+n)/2
```

because it handles both odd and even total lengths elegantly.

For an odd total:

```text
5 elements
```

we get:

```text
(5+1)/2 = 3
```

So the left side contains the extra element.

For an even total:

```text
6 elements
```

we get:

```text
(6+1)/2 = 3
```

Integer division gives:

```text
3
```

So:

```text
Odd → left has one extra element
Even → both sides have equal elements
```

This makes the median calculation much cleaner.

---

# Step 4 — Understand the Four Boundary Values

Once we choose:

```text
cut1
cut2
```

we only need to look at **four values**.

For `nums1`:

```java
left1
right1
```

For `nums2`:

```java
left2
right2
```

Visually:

```text
nums1:

[ ... left1 | right1 ... ]
             ↑
            cut1


nums2:

[ ... left2 | right2 ... ]
             ↑
            cut2
```

These four values completely determine whether our partition is valid.

---

# Handling the Array Boundaries

There are cases where the cut is at the beginning or end of an array.

For example:

```text
nums1 = [1,2,3]
```

If:

```text
cut1 = 0
```

there is nothing on the left side of `nums1`.

So I use:

```java
Integer.MIN_VALUE
```

for `left1`.

```java
double left1 = (cut1 == 0)
        ? Integer.MIN_VALUE
        : nums1[cut1 - 1];
```

Similarly, if:

```text
cut1 = m
```

there is nothing on the right side.

So I use:

```java
Integer.MAX_VALUE
```

for `right1`.

```java
double right1 = (cut1 == m)
        ? Integer.MAX_VALUE
        : nums1[cut1];
```

The same logic is applied to `nums2`.

This lets the partition logic work even when the cut is at an extreme boundary.

---

# Step 5 — Determine Whether the Partition Is Correct

This is the most important condition:

```java
if(left1 <= right2 && left2 <= right1)
```

Why exactly these two comparisons?

We need:

```text
left1 <= right2
```

and:

```text
left2 <= right1
```

In other words:

```text
nums1 left side <= nums2 right side

AND

nums2 left side <= nums1 right side
```

Together, this guarantees:

```text
EVERYTHING ON LEFT <= EVERYTHING ON RIGHT
```

The partition is now correct.

---

# Visualizing the Correct Partition

Suppose:

```text
nums1 = [1,3]
nums2 = [2,4]
```

Correct partition:

```text
nums1:
[1 | 3]
 ↑     ↑
left1 right1

nums2:
[2 | 4]
 ↑     ↑
left2 right2
```

We have:

```text
left1 = 1
left2 = 2

right1 = 3
right2 = 4
```

Check:

```text
left1 <= right2
1 <= 4 ✓

left2 <= right1
2 <= 3 ✓
```

Therefore the partition is correct.

---

# Step 6 — Calculate the Median

Once the partition is valid, there are two cases.

## Case 1 — Total Length Is Odd

For:

```text
[1,2,3]
```

the median is the largest element on the left side.

Therefore:

```java
return Math.max(left1, left2);
```

Why?

Because the left side contains one extra element.

So the median is:

```text
max(left1, left2)
```

---

## Case 2 — Total Length Is Even

For:

```text
[1,2,3,4]
```

the median is:

```text
(2 + 3) / 2
```

The two values around the partition are:

```text
largest value on left
smallest value on right
```

So:

```java
double leftMax = Math.max(left1, left2);
double rightMin = Math.min(right1, right2);

return (leftMax + rightMin) / 2.0;
```

The formula is:

```text
median = (max(left side) + min(right side)) / 2
```

---

# How Binary Search Decides Which Direction to Move

Suppose our partition is invalid.

There are two possibilities.

## Case 1 — `left1 > right2`

```java
if(left1 > right2){
    high = cut1 - 1;
}
```

This means we took **too many elements from `nums1`**.

Visualized:

```text
nums1:
[ ... left1 | right1 ... ]

nums2:
[ ... left2 | right2 ... ]

left1 > right2
```

We need to move `cut1` to the left.

Therefore:

```text
cut1 ↓
```

and:

```java
high = cut1 - 1;
```

---

## Case 2 — Otherwise

If:

```text
left1 <= right2
```

but the partition is still invalid, then the remaining problem is:

```text
left2 > right1
```

This means we took **too few elements from `nums1`**.

So we need to move the partition to the right:

```java
low = cut1 + 1;
```

Therefore:

```text
cut1 ↑
```

---

# Complete Example

Consider:

```text
nums1 = [1,3]
nums2 = [2]
```

First, `nums1` is not larger than `nums2`.

So:

```text
m = 2
n = 1
```

Actually, because `nums1.length > nums2.length`, my first condition swaps them.

We now search the smaller array:

```text
nums1 = [2]
nums2 = [1,3]
```

So:

```text
m = 1
n = 2
```

Total:

```text
3
```

Left side needs:

```text
(3+1)/2 = 2
```

elements.

Suppose:

```text
cut1 = 1
```

Then:

```text
cut2 = 2 - 1
     = 1
```

Partition:

```text
nums1:
[2 | ]

nums2:
[1 | 3]
```

Therefore:

```text
left1 = 2
left2 = 1

right1 = MAX
right2 = 3
```

Check:

```text
2 <= 3 ✓
1 <= MAX ✓
```

Partition is correct.

Total length is odd:

```text
3 % 2 == 1
```

Therefore:

```text
median = max(2,1)
       = 2
```

---

# Why We Don't Merge the Arrays

A straightforward solution would be:

```text
nums1 + nums2
       ↓
merge
       ↓
sorted array
       ↓
find middle
```

Even if the merge is done efficiently, it still takes:

```text
O(m+n)
```

time.

But the problem explicitly requires:

```text
O(log(m+n))
```

So the key lesson is:

> **When sorted data must be searched but the problem forbids linear traversal, look for a way to use the ordering to eliminate half of the search space.**

That is exactly what the partition-based binary search does.

---

# Pattern Recognition

## Pattern: Binary Search on Partition

This isn't the typical:

```text
find target in sorted array
```

binary search.

Instead, we are using binary search to find the **correct partition point**.

The pattern is:

```text
Two sorted arrays
       ↓
Choose partition in smaller array
       ↓
Calculate corresponding partition in larger array
       ↓
Check boundary values
       ↓
Partition correct?
   ↓            ↓
 YES           NO
  ↓             ↓
Median      Move binary search
```

The most important condition to remember is:

```text
left1 <= right2
AND
left2 <= right1
```

---

# The Core Idea

The entire problem can be reduced to this picture:

```text
nums1:
[ elements on LEFT | elements on RIGHT ]
                     ↑
                   cut1


nums2:
[ elements on LEFT | elements on RIGHT ]
                     ↑
                   cut2
```

We want:

```text
MAX(LEFT SIDE) <= MIN(RIGHT SIDE)
```

Once this is true, we have found the correct partition.

Then:

```text
Odd:
median = max(left1, left2)

Even:
median = (max(left1,left2) + min(right1,right2)) / 2
```

---

# Important Edge Cases

## One Array Is Empty

For:

```text
nums1 = []
nums2 = [1]
```

the smaller array has:

```text
m = 0
```

So:

```text
cut1 = 0
```

and the partition is entirely determined by `nums2`.

The sentinel values:

```text
Integer.MIN_VALUE
Integer.MAX_VALUE
```

allow this case to work without special-case logic.

---

## Arrays Have Different Sizes

Example:

```text
nums1 = [1,3]
nums2 = [2,4,5,6]
```

This is exactly why I always binary search the smaller array.

The second partition is automatically calculated using:

```java
cut2 = (m+n+1)/2 - cut1;
```

---

## Duplicate Values

For:

```text
nums1 = [1,2]
nums2 = [2,2]
```

the partition condition uses:

```text
<=
```

rather than:

```text
<
```

because equal values are perfectly valid around the partition.

---

# Complexity

Let:

```text
m = nums1.length
n = nums2.length
```

and after the initial swap:

```text
m <= n
```

## Time Complexity

We perform binary search only on the smaller array.

The search space is:

```text
0 ... m
```

and it is divided roughly in half each iteration.

Therefore:

```text
O(log m)
```

Since:

```text
m <= m+n
```

this satisfies the required:

```text
O(log(m+n))
```

---

## Space Complexity

The algorithm uses only a few variables:

```text
low
high
cut1
cut2
left1
left2
right1
right2
```

No merged array is created.

Therefore:

```text
O(1)
```

auxiliary space.

---

# Key Learning

### 1. Don't Automatically Merge Sorted Arrays

Two sorted arrays don't necessarily need to be merged to answer questions about their combined order.

Sometimes the sorted structure can be exploited directly.

---

### 2. Binary Search Can Search for a Partition

Binary search is not limited to:

```text
"Does this value exist?"
```

It can also be used to find:

```text
"Where should the boundary be?"
```

Here, we are searching for the partition that makes the combined left and right halves valid.

---

### 3. Search the Smaller Array

This is a very important technique.

If one dimension can be chosen for the binary search, choose the smaller one.

That guarantees the search space remains minimal.

---

### 4. Four Boundary Values Are Enough

We never need to inspect the entire arrays.

For any candidate partition, we only care about:

```text
left1
right1
left2
right2
```

These four values tell us everything about whether the partition is valid.

---

### 5. Think in Terms of Left and Right Halves

The median is fundamentally about dividing the sorted data into:

```text
LEFT HALF | RIGHT HALF
```

Once that mental model becomes clear, the complicated-looking formula becomes much easier to understand.

---

## Final Takeaway

The solution can be summarized as:

```text
Make nums1 the smaller array
          ↓
Binary search cut1
          ↓
Calculate cut2
          ↓
Check:
left1 <= right2
left2 <= right1
          ↓
If invalid:
move binary search
          ↓
If valid:
calculate median
```

The core pattern is:

```text
Binary Search
      +
Partitioning
      +
Two Sorted Arrays
```

The final complexity is:

```text
Time:  O(log(min(m,n)))
Space: O(1)
```

This problem is a major example of how **changing the way you look at a problem—from "merge two arrays" to "find the correct partition"—can reduce an apparently linear problem to logarithmic time.**

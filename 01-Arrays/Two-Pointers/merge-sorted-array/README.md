# WIN #22 — Merge Sorted Array

## Problem

You are given two integer arrays `nums1` and `nums2`, both sorted in **non-decreasing order**.

* `nums1` contains `m` actual elements.
* `nums1` has extra space for `n` elements, initialized with `0`.
* `nums2` contains `n` elements.

The goal is to merge both arrays into a single sorted array and store the result **inside `nums1`**.

For example:

```text
Input:
nums1 = [1,2,3,0,0,0]
m = 3

nums2 = [2,5,6]
n = 3

Output:
[1,2,2,3,5,6]
```

The `0`s at the end of `nums1` are only placeholders and should not be considered part of the original array.

---

## My Java Solution

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {

        int i =0;
        int j = 0;
        int k = 0;
        int[] merged = new int[m+n];

        while( i< m && j <n){
            if(nums1[i] <nums2[j]){
                merged[k] = nums1[i];
                i++;
                k++;
            }else{
                merged[k] = nums2[j];
                j++;
                k++;
            }
        }

        while(i < m){
            merged[k] = nums1[i];
            k++;
            i++;
        }

        while(j <n){
            merged[k] = nums2[j];
            j++;
            k++;
        }
        
        int idx =0;
        for(int num: merged){
            nums1[idx] = num;
            idx++;
        }

    }
}
```

---

## My Thought Process

Both arrays are already sorted.

So instead of combining them first and sorting the entire result, I can take advantage of their sorted order.

I use **three pointers**:

```text
i → current position in nums1
j → current position in nums2
k → current position in merged
```

The basic idea is:

> Compare the current elements of both arrays and put the smaller one into the merged array.

---

## Step 1: Create a Temporary Array

I create:

```java
int[] merged = new int[m+n];
```

This array will store the final sorted result.

For:

```text
nums1 = [1,2,3,0,0,0]
nums2 = [2,5,6]
```

the temporary array has space for:

```text
[_,_,_,_,_,_]
```

---

## Step 2: Compare Both Arrays

I start with:

```text
i = 0
j = 0
k = 0
```

So:

```text
nums1[i] = 1
nums2[j] = 2
```

Compare:

```text
1 < 2
```

Therefore, put `1` into `merged`.

```text
merged = [1,_,_,_,_,_]
```

Then move:

```text
i++
k++
```

---

## Step 3: Continue Comparing

Now:

```text
nums1[i] = 2
nums2[j] = 2
```

The condition is:

```java
if(nums1[i] < nums2[j])
```

Since `2 < 2` is false, the `else` block executes and takes the value from `nums2`.

```text
merged = [1,2,_,_,_,_]
```

Then `j` and `k` move forward.

The process continues until one of the arrays has no remaining elements.

---

## Example Walkthrough

Consider:

```text
nums1 = [1,2,3]
nums2 = [2,5,6]
```

### Comparison 1

```text
1 vs 2
```

Take `1`.

```text
merged = [1]
```

### Comparison 2

```text
2 vs 2
```

Take `2` from `nums2`.

```text
merged = [1,2]
```

### Comparison 3

```text
2 vs 5
```

Take `2` from `nums1`.

```text
merged = [1,2,2]
```

### Comparison 4

```text
3 vs 5
```

Take `3`.

```text
merged = [1,2,2,3]
```

Now `nums1` has no more actual elements.

The remaining elements of `nums2` are:

```text
[5,6]
```

So they are copied directly.

Final:

```text
merged = [1,2,2,3,5,6]
```

---

## Step 4: Handle Remaining Elements

The main loop is:

```java
while(i < m && j < n)
```

It stops as soon as either array is exhausted.

But the other array may still contain elements.

That's why there are two additional loops.

### Remaining elements in `nums1`

```java
while(i < m){
    merged[k] = nums1[i];
    k++;
    i++;
}
```

### Remaining elements in `nums2`

```java
while(j < n){
    merged[k] = nums2[j];
    j++;
    k++;
}
```

Because the arrays are already sorted, we don't need to compare these remaining elements.

They can simply be copied.

---

## Step 5: Copy Back Into `nums1`

The problem requires the final result to be stored inside `nums1`.

So after creating:

```text
merged = [1,2,2,3,5,6]
```

I copy every element back:

```java
for(int num: merged){
    nums1[idx] = num;
    idx++;
}
```

Now:

```text
nums1 = [1,2,2,3,5,6]
```

---

## Pattern Recognition

### Pattern: Two Pointers / Merge

This problem is a classic example of the **Merge** technique used in algorithms such as Merge Sort.

The important observation is:

> Both input arrays are already sorted.

Therefore, we don't need to compare every element with every other element.

Instead, we only compare the current smallest unused element from each array.

```text
nums1: [1, 2, 3]
         ↑
         i

nums2: [2, 5, 6]
         ↑
         j
```

Compare:

```text
nums1[i] vs nums2[j]
```

Take the smaller value and move that pointer forward.

---

## Why Does This Work?

Because both arrays are sorted.

Suppose:

```text
nums1 = [1,4,7]
nums2 = [2,3,8]
```

The smallest remaining value must always be either:

```text
nums1[i]
```

or:

```text
nums2[j]
```

There is no need to look further into either array because all later elements are greater than or equal to the current element.

Therefore, choosing the smaller current value always gives us the next element in the final sorted array.

---

## Complexity

### Time Complexity

The first loop processes each element at most once.

Then the remaining elements are copied by the two additional loops.

Finally, the merged array is copied back into `nums1`.

Therefore:

```text
O(m + n)
```

The merge itself is linear because every element is processed only once.

### Space Complexity

I create an additional array:

```java
int[] merged = new int[m+n];
```

Therefore, the auxiliary space complexity is:

```text
O(m + n)
```

---

## Follow-Up

The problem asks whether we can achieve:

```text
O(m + n)
```

time.

Yes — **this solution already achieves O(m + n) time**.

However, there is an important limitation:

> The problem also expects the final result to be stored directly inside `nums1`.

My solution uses an additional `merged` array, so it does not achieve the optimal **O(1) extra space** approach.

A more optimized solution can merge **from the back of `nums1`**.

For example:

```text
nums1 = [1,2,3,0,0,0]
              ↑
              k

nums2 = [2,5,6]
           ↑
           j
```

Since `nums1` already has empty space at the end, we can place the **largest element first**.

This avoids overwriting the elements that still need to be processed.

That optimized approach achieves:

```text
Time:  O(m + n)
Space: O(1)
```

---

## Key Learning

* When two arrays are already sorted, think about the **Merge** technique.
* Two pointers can efficiently process sorted arrays.
* Always separate the **actual elements** from placeholder elements such as the `0`s in `nums1`.
* Once one array is exhausted, the remaining elements of the other array can be copied directly.
* My solution achieves the required **O(m + n) time complexity**.
* The main optimization opportunity is reducing the extra space from `O(m+n)` to `O(1)` by merging from the back.

---

## Final Takeaway

The core idea is:

```text
Two sorted arrays
       ↓
Compare current elements
       ↓
Take the smaller one
       ↓
Move that pointer
       ↓
Repeat
       ↓
Copy remaining elements
       ↓
Copy result into nums1
```

This is one of the most important patterns to recognize when working with **sorted arrays**.

The key question to ask is:

> "Can I use the fact that these arrays are already sorted instead of sorting everything again?"

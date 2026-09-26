# WIN #58 — Intersection of Two Arrays

## Problem

[LeetCode 349 — Intersection of Two Arrays](https://leetcode.com/problems/intersection-of-two-arrays/)

Given two integer arrays `nums1` and `nums2`, return an array containing their **intersection**.

Each element in the result must be **unique**, and the result can be returned in any order.

The intersection contains the elements that appear in **both arrays**.

The important requirement is:

> Every element should appear only once in the result.

---

## Example 1

```text
Input:
nums1 = [1,2,2,1]
nums2 = [2,2]

Output:
[2]
```

The value `2` appears in both arrays.

Even though `2` appears twice in `nums1` and twice in `nums2`, the result contains it only once:

```text
[2]
```

---

## Example 2

```text
Input:
nums1 = [4,9,5]
nums2 = [9,4,9,8,4]

Output:
[9,4]
```

Both `4` and `9` appear in both arrays.

Therefore:

```text
[9,4]
```

is a valid answer.

The order does not matter, so:

```text
[4,9]
```

is also accepted.

---

## My Java Solution

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {

        HashSet<Integer> set = new HashSet<>();

        for(int num : nums1){
            set.add(num);
        }

        HashSet<Integer> result = new HashSet<>();

        for(int num: nums2){
            if(set.contains(num)){
                result.add(num);
            }
        }

        int[] answer = new int[result.size()];

        int i=0;

        for(int num: result){
            answer[i] = num;
            i++;
        }

        return answer;
    }
}
```

---

## My Thought Process

The first thing to notice is that the problem asks for:

```text
Elements present in BOTH arrays
```

and:

```text
Every element must be UNIQUE
```

For example:

```text
nums1 = [1,2,2,1]
nums2 = [2,2]
```

We only need to know:

```text
Does this number exist in the other array?
```

We don't need to know how many times it appears.

This makes a `HashSet` a natural choice.

A `HashSet` automatically stores only unique values and provides average:

```text
O(1)
```

lookup using:

```java
set.contains(num)
```

So the strategy is:

```text
Put nums1 into a HashSet
        ↓
Traverse nums2
        ↓
Check whether each number exists in the set
        ↓
If it exists → add it to result
        ↓
HashSet removes duplicates automatically
        ↓
Convert result to int[]
```

---

# Step 1 — Create a HashSet for nums1

```java
HashSet<Integer> set = new HashSet<>();
```

The purpose of this HashSet is to store all unique elements from `nums1`.

For example:

```text
nums1 = [1,2,2,1]
```

After inserting everything:

```text
set = {1,2}
```

The duplicates disappear automatically.

---

# Step 2 — Add nums1 Elements to the HashSet

```java
for(int num : nums1){
    set.add(num);
}
```

Suppose:

```text
nums1 = [1,2,2,1]
```

We process:

```text
1 → add
2 → add
2 → already exists
1 → already exists
```

The final set is:

```text
{1,2}
```

This gives us a fast way to ask:

```text
Does this element from nums2 also exist in nums1?
```

---

# Step 3 — Create the Result HashSet

```java
HashSet<Integer> result = new HashSet<>();
```

We use another HashSet for the answer.

Why another HashSet?

Because the result must contain:

```text
UNIQUE elements
```

Consider:

```text
nums1 = [4,9,5]
nums2 = [9,4,9,8,4]
```

While traversing `nums2`, we encounter:

```text
9
4
9
8
4
```

Both `9` and `4` appear multiple times.

If we simply added them to an array, we could get:

```text
[9,4,9,4]
```

which is invalid.

Instead, the result HashSet becomes:

```text
{9,4}
```

Duplicates are automatically removed.

---

# Step 4 — Traverse nums2

```java
for(int num: nums2){
```

We now examine every element of `nums2`.

For every number, we ask:

```java
if(set.contains(num))
```

In other words:

```text
Does this number exist in nums1?
```

---

# Step 5 — Check Whether the Element Exists

```java
if(set.contains(num)){
    result.add(num);
}
```

This is the core of the solution.

Suppose:

```text
nums1 = [1,2,2,1]
```

The HashSet is:

```text
{1,2}
```

Now process:

```text
nums2 = [2,2]
```

For the first `2`:

```text
set.contains(2)
```

is:

```text
true
```

So:

```text
result.add(2)
```

The result becomes:

```text
{2}
```

For the second `2`:

```text
set.contains(2)
```

is still true.

But:

```text
result.add(2)
```

does not create another `2`, because a HashSet only stores unique values.

The result remains:

```text
{2}
```

---

# Step 6 — Convert the HashSet Into an Array

The LeetCode method requires:

```text
int[]
```

but our result is:

```text
HashSet<Integer>
```

So we create an array with the same size:

```java
int[] answer = new int[result.size()];
```

For example:

```text
result = {2,4,9}
```

Then:

```text
result.size() = 3
```

so:

```text
answer = new int[3]
```

---

# Step 7 — Copy the Elements

```java
int i=0;

for(int num: result){
    answer[i] = num;
    i++;
}
```

We iterate through the result HashSet and place each element into the array.

For:

```text
result = {4,9}
```

we might get:

```text
answer[0] = 4
answer[1] = 9
```

giving:

```text
[4,9]
```

The order does not matter for this problem.

---

# Step 8 — Return the Answer

```java
return answer;
```

The array now contains:

```text
Unique elements present in both arrays
```

---

# Complete Walkthrough

Consider:

```text
nums1 = [4,9,5]
nums2 = [9,4,9,8,4]
```

---

## Step 1 — Build the HashSet

Insert elements from `nums1`:

```text
4
9
5
```

The HashSet becomes:

```text
{4,9,5}
```

---

## Step 2 — Process nums2

Start with:

```text
9
```

Check:

```text
set.contains(9)
```

Result:

```text
true
```

Add:

```text
result = {9}
```

---

Next:

```text
4
```

Check:

```text
set.contains(4)
```

Result:

```text
true
```

Add:

```text
result = {9,4}
```

---

Next:

```text
9
```

Check:

```text
set.contains(9)
```

Result:

```text
true
```

But `9` already exists in the result HashSet.

So:

```text
result = {9,4}
```

---

Next:

```text
8
```

Check:

```text
set.contains(8)
```

Result:

```text
false
```

So we skip it.

---

Finally:

```text
4
```

Check:

```text
set.contains(4)
```

Result:

```text
true
```

But `4` is already in the result.

So the result remains:

```text
{9,4}
```

---

## Final Result

Convert:

```text
{9,4}
```

into:

```text
[9,4]
```

Therefore:

```text
answer = [9,4]
```

---

# Why HashSet Is Perfect for This Problem

There are two important requirements:

```text
1. Check whether an element exists
2. Store only unique elements
```

`HashSet` provides both.

### Existence Check

```java
set.contains(num)
```

Average:

```text
O(1)
```

### Unique Values

```java
set.add(num)
```

automatically ignores duplicates.

Therefore:

```text
HashSet = fast lookup + unique elements
```

which matches the requirements of this problem perfectly.

---

# Why We Don't Need a HashMap

A `HashMap` is useful when we need:

```text
value → frequency
```

For example:

```text
number → how many times it appears
```

But this problem does **not** care about frequency.

For:

```text
nums1 = [1,1,1]
nums2 = [1,1]
```

the answer is simply:

```text
[1]
```

We don't need to know:

```text
1 → 3
```

or:

```text
1 → 2
```

We only need to know:

```text
Does 1 exist?
```

Therefore, `HashSet` is sufficient.

---

# HashSet vs HashMap

This distinction is important.

### HashSet

Use when you need:

```text
Does this value exist?
```

or:

```text
Store unique values
```

Example:

```text
{1,2,3,4}
```

---

### HashMap

Use when you need:

```text
value → information
```

such as:

```text
number → frequency
```

Example:

```text
1 → 3
2 → 5
3 → 1
```

For this problem:

```text
HashSet
```

is enough.

---

# Pattern Recognition

## Pattern: HashSet + Membership Check

This problem is a classic HashSet intersection pattern.

The basic strategy is:

```text
Store elements from first collection
          ↓
Traverse second collection
          ↓
Check membership
          ↓
If present → add to result
          ↓
Use HashSet to remove duplicates
```

The important operation is:

```java
set.contains(num)
```

which allows us to quickly determine whether an element belongs to both arrays.

The pattern to remember is:

> **When you need the unique common elements between two collections, put one collection into a HashSet and use membership checks while traversing the other collection.**

---

# Why Duplicates Don't Matter

Consider:

```text
nums1 = [1,2,2,1]
nums2 = [2,2]
```

The first HashSet becomes:

```text
{1,2}
```

The second HashSet becomes:

```text
{2}
```

Even though the input contains:

```text
1 → 2 times
2 → 2 times
```

the problem asks only for unique intersection values.

Therefore:

```text
[2]
```

is the correct answer.

---

# Edge Cases

## 1. Both Arrays Contain the Same Element

```text
nums1 = [1]
nums2 = [1]
```

Result:

```text
[1]
```

---

## 2. No Common Elements

```text
nums1 = [1,2,3]
nums2 = [4,5,6]
```

No element exists in both arrays.

Therefore:

```text
[]
```

---

## 3. All Elements Are Duplicates

```text
nums1 = [1,1,1]
nums2 = [1,1]
```

The result is:

```text
[1]
```

not:

```text
[1,1]
```

because the result must contain unique values.

---

## 4. One Array Contains Many Duplicates

```text
nums1 = [4,9,5]
nums2 = [9,4,9,8,4]
```

The result is:

```text
[4,9]
```

The repeated `9` and `4` do not create duplicate output values.

---

## 5. No Duplicates

```text
nums1 = [1,2,3]
nums2 = [2,3,4]
```

The common elements are:

```text
2,3
```

So:

```text
[2,3]
```

---

# Complexity

Let:

```text
n = nums1.length
m = nums2.length
```

## Time Complexity

Adding all elements of `nums1` to the HashSet:

```text
O(n)
```

Traversing `nums2`:

```text
O(m)
```

Converting the result HashSet into an array:

```text
O(k)
```

where `k` is the number of unique intersection elements.

Therefore:

```text
O(n + m)
```

average time.

---

## Space Complexity

The first HashSet can contain up to `n` unique elements:

```text
O(n)
```

The result HashSet can contain up to:

```text
O(min(n,m))
```

unique elements.

Therefore the overall auxiliary space is:

```text
O(n + m)
```

in the general case.

---

# The Most Important Insight

Remember the two questions this problem asks:

```text
1. Does this number exist in nums1?
2. Has this number already been added to the result?
```

A HashSet can answer both.

First:

```java
set.contains(num)
```

checks whether the number exists in `nums1`.

Then:

```java
result.add(num)
```

ensures that the result contains the number only once.

So the entire solution is essentially:

```text
nums1
  ↓
HashSet
  ↓
Traverse nums2
  ↓
contains(num)?
  ↓
YES
  ↓
Add to result HashSet
  ↓
Convert to array
```

---

# Visual Summary

For:

```text
nums1 = [4,9,5]
nums2 = [9,4,9,8,4]
```

Build:

```text
nums1
  ↓
{4,9,5}
```

Process `nums2`:

```text
9 → exists → add
4 → exists → add
9 → exists → already added
8 → doesn't exist → skip
4 → exists → already added
```

Result:

```text
{9,4}
```

Convert to array:

```text
[9,4]
```

Final answer:

```text
[9,4]
```

---

# Final Takeaway

The complete strategy is:

```text
Put nums1 into a HashSet
          ↓
Traverse nums2
          ↓
Check if num exists in nums1
          ↓
       YES
          ↓
Add num to result HashSet
          ↓
Duplicates automatically removed
          ↓
Convert result HashSet to int[]
          ↓
Return answer
```

The main pattern to remember is:

> **For unique intersection problems, use a HashSet to store one collection and perform O(1) average membership checks against the other collection. Use another HashSet when the result itself must contain unique values.**

This is a fundamental **HashSet membership + uniqueness** pattern and is useful for many array and string problems.

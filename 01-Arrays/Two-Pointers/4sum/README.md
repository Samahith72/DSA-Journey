# WIN #15 — 4Sum

## Problem

Given an integer array `nums` of `n` integers and an integer `target`, return all the **unique quadruplets** `[nums[a], nums[b], nums[c], nums[d]]` such that:

```text
nums[a] + nums[b] + nums[c] + nums[d] == target
```

The four indices must be distinct.

The solution should return only **unique quadruplets**. The order of the quadruplets does not matter.

For example:

```text
Input:
nums = [1,0,-1,0,-2,2]
target = 0

Output:
[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
```

Another example:

```text
Input:
nums = [2,2,2,2,2]
target = 8

Output:
[[2,2,2,2]]
```

The important part of the problem is not only finding four numbers whose sum equals the target, but also making sure that duplicate quadruplets are not added to the answer.

---

## My Java Solution

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> answer = new ArrayList<>();

        Arrays.sort(nums);

        for(int i = 0; i < nums.length-3;i++){
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            for(int j = i+1; j < nums.length-2;j++){
                if(j > i + 1 && nums[j] == nums[j-1]){
                    continue;
                }
                int left = j+1;
                int right = nums.length-1;

                while(left < right){
                    long sum = nums[i] + nums[j] + (long)nums[left] + nums[right];
                    if(sum > target){
                        right--;
                    }
                    else if(sum < target){
                        left++;
                    }
                    else{
                        answer.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while(left < right && nums[left] == nums[left+1]){
                            left++;
                        }
                        while(left < right && nums[right] == nums[right-1]){
                            right--;
                        }
                        left++;
                        right--;

                    }
                }
            }
        }

        return answer;
        
    }
}
```

---

## My Thought Process

The first thing I noticed is that this problem is an extension of the **3Sum** idea.

Instead of finding three numbers whose sum equals the target, I need to find four numbers.

A brute-force approach would use four nested loops:

```text
i
 ↓
j
 ↓
left
 ↓
right
```

But that would take `O(n⁴)` time, which is too expensive.

So I wanted to reduce the problem by fixing the first two numbers and then efficiently searching for the remaining two numbers.

The main idea became:

```text
Fix nums[i]
    ↓
Fix nums[j]
    ↓
Find two numbers between j and the end
    ↓
Use two pointers
```

Before doing this, I sort the array.

```java
Arrays.sort(nums);
```

Sorting is important because it allows me to use the **two-pointer technique**.

For example:

```text
nums = [1,0,-1,0,-2,2]

After sorting:

[-2,-1,0,0,1,2]
```

Now the numbers are arranged from smallest to largest.

---

### Step 1: Fix the First Number

I use the outer loop to choose the first number:

```java
for(int i = 0; i < nums.length-3; i++)
```

I stop at `nums.length - 3` because I still need three more elements after `i`.

For example:

```text
[-2,-1,0,0,1,2]
 ↑
 i
```

Once `nums[i]` is fixed, I move to the second number.

---

### Step 2: Avoid Duplicate First Numbers

Because the problem asks for **unique quadruplets**, I need to avoid choosing the same value for `i` multiple times.

I use:

```java
if(i > 0 && nums[i] == nums[i-1]){
    continue;
}
```

For example:

```text
[-2,-2,-1,0,1,2]
```

If the first `-2` has already been processed, I skip the second `-2`.

This prevents generating the same quadruplets again.

---

### Step 3: Fix the Second Number

I use another loop:

```java
for(int j = i+1; j < nums.length-2; j++)
```

Now I have fixed two numbers:

```text
nums[i]
nums[j]
```

For example:

```text
[-2,-1,0,0,1,2]
 ↑  ↑
 i  j
```

The remaining problem is now:

```text
Find two numbers after j
such that:

nums[i] + nums[j] + nums[left] + nums[right] == target
```

This has effectively reduced the problem to a **2Sum problem**.

---

### Step 4: Avoid Duplicate Second Numbers

I also need to avoid duplicates for the second number.

I use:

```java
if(j > i + 1 && nums[j] == nums[j-1]){
    continue;
}
```

The condition `j > i + 1` ensures that I only skip duplicates after the first valid `j` position for the current `i`.

For example:

```text
[-2,0,0,1,2]
    ↑
    j
```

If another `0` appears immediately after it, processing it again would generate duplicate quadruplets.

So I skip it.

---

### Step 5: Use Two Pointers

After fixing `i` and `j`, I initialize:

```java
int left = j+1;
int right = nums.length-1;
```

So the remaining search happens between:

```text
left ---------------- right
```

For example:

```text
[-2,-1,0,0,1,2]
       ↑     ↑
     left  right
```

Now I calculate the sum:

```java
long sum = nums[i] + nums[j] + (long)nums[left] + nums[right];
```

I use `long` here because the values can be as large as `10⁹`, so adding four integers can exceed the range of a Java `int`.

---

### Step 6: Move the Pointers Based on the Sum

Because the array is sorted, I can determine which pointer to move based on the current sum.

#### If the sum is too large

```java
if(sum > target){
    right--;
}
```

I move `right` to the left.

Since the array is sorted, this decreases the value and therefore decreases the total sum.

For example:

```text
sum > target

[-2,-1,0,0,1,2]
             ↑
           right

Move right ←
```

---

#### If the sum is too small

```java
else if(sum < target){
    left++;
}
```

I move `left` to the right.

Since the array is sorted, this increases the value and therefore increases the total sum.

For example:

```text
sum < target

[-2,-1,0,0,1,2]
       ↑     ↑
     left  right

Move left →
```

---

### Step 7: When the Sum Equals the Target

When:

```java
sum == target
```

I found a valid quadruplet.

So I add it to the answer:

```java
answer.add(Arrays.asList(
    nums[i],
    nums[j],
    nums[left],
    nums[right]
));
```

For example:

```text
nums = [-2,-1,0,0,1,2]
target = 0

i = -2
j = -1
left = 1
right = 2
```

The sum is:

```text
-2 + (-1) + 1 + 2 = 0
```

So:

```text
[-2,-1,1,2]
```

is added to the result.

---

### Step 8: Skip Duplicate Left Values

After finding a valid quadruplet, I need to make sure I don't generate the same quadruplet again because of duplicate values.

I use:

```java
while(left < right && nums[left] == nums[left+1]){
    left++;
}
```

For example:

```text
[-2,0,0,0,1,2]
       ↑ ↑
     left
```

If the next value is the same as the current value, I skip it.

This ensures that duplicate values at the `left` pointer don't create duplicate quadruplets.

---

### Step 9: Skip Duplicate Right Values

I do the same thing from the right side:

```java
while(left < right && nums[right] == nums[right-1]){
    right--;
}
```

This skips repeated values at the `right` pointer.

Together, these two loops make sure that after finding a valid quadruplet, I move past duplicate values before searching for the next one.

---

### Step 10: Move Both Pointers

After skipping duplicates, I move both pointers:

```java
left++;
right--;
```

This allows me to continue searching for other possible pairs.

The complete two-pointer logic is:

```text
Calculate sum
     ↓
sum > target?
     ↓
right--

sum < target?
     ↓
left++

sum == target?
     ↓
Store quadruplet
     ↓
Skip duplicates
     ↓
left++
right--
```

---

## Example Walkthrough

Consider:

```text
nums = [1,0,-1,0,-2,2]
target = 0
```

After sorting:

```text
[-2,-1,0,0,1,2]
```

One of the searches is:

```text
i = -2
j = -1
```

Now:

```text
left = 0
right = 2
```

The values are:

```text
-2 + (-1) + 0 + 2 = -1
```

The sum is too small:

```text
-1 < 0
```

So:

```text
left++
```

Now:

```text
-2 + (-1) + 0 + 2 = -1
```

Eventually, the pointers reach:

```text
-2 + (-1) + 1 + 2 = 0
```

So we add:

```text
[-2,-1,1,2]
```

The same process continues for the remaining combinations.

The final result is:

```text
[[-2,-1,1,2],
 [-2,0,0,2],
 [-1,0,0,1]]
```

---

## Pattern Recognition

### Pattern: Sorting + Two Pointers + Nested Fixing

This problem is a classic example of combining multiple DSA techniques.

The overall structure is:

```text
Sort the array
     ↓
Fix the first number
     ↓
Fix the second number
     ↓
Use two pointers for the remaining two numbers
     ↓
Skip duplicates
```

The important transformation is:

```text
4Sum
  ↓
Fix 2 numbers
  ↓
2Sum
  ↓
Two Pointers
```

This is much more efficient than checking every possible combination with four nested loops.

The same general idea can be useful for problems such as:

```text
2Sum
3Sum
4Sum
K-Sum
```

For example:

```text
3Sum:
Fix 1 number
+
Two pointers

4Sum:
Fix 2 numbers
+
Two pointers
```

The sorted array is what makes the pointer movement possible.

Because the values are ordered:

```text
left  → increases the sum

right → decreases the sum
```

So instead of randomly checking combinations, I can intelligently eliminate possibilities.

---

## Handling Duplicates

Duplicate handling is one of the most important parts of this problem.

There are three places where duplicates need to be handled.

### Duplicate `i`

```java
if(i > 0 && nums[i] == nums[i-1]){
    continue;
}
```

This prevents duplicate choices for the first number.

### Duplicate `j`

```java
if(j > i + 1 && nums[j] == nums[j-1]){
    continue;
}
```

This prevents duplicate choices for the second number.

### Duplicate `left` and `right`

After finding a valid quadruplet:

```java
while(left < right && nums[left] == nums[left+1]){
    left++;
}

while(left < right && nums[right] == nums[right-1]){
    right--;
}
```

This prevents duplicate third and fourth values.

The overall idea is:

```text
Skip duplicates while selecting
        ↓
i
j
left
right
        ↓
Only unique quadruplets are added
```

---

## Why Sorting Is Important

Sorting is not just for convenience.

It enables the two-pointer technique.

For an unsorted array, if:

```text
sum < target
```

I would not know whether moving `left` would increase or decrease the sum.

But after sorting:

```text
nums[left] <= nums[right]
```

Therefore:

```text
left++
```

moves toward a larger value, while:

```text
right--
```

moves toward a smaller value.

This gives me a predictable way to adjust the sum.

Sorting also makes duplicate detection much easier because equal values appear next to each other.

---

## Complexity

### Time Complexity

```text
O(n³)
```

The array is first sorted:

```text
O(n log n)
```

Then:

```text
Outer loop → O(n)

Second loop → O(n)

Two-pointer search → O(n)
```

Therefore:

```text
O(n × n × n) = O(n³)
```

The sorting cost is smaller than the overall `O(n³)` complexity.

So the final time complexity is:

```text
O(n³)
```

---

### Space Complexity

```text
O(1)
```

Apart from the output list, the algorithm only uses a few variables:

```text
i
j
left
right
sum
```

The sorting is performed directly on the input array using:

```java
Arrays.sort(nums);
```

So the auxiliary space used by the algorithm is considered `O(1)` aside from the space required for the returned answer.

---

## Key Learning

I learned how a higher-order `K-Sum` problem can be reduced into smaller problems.

Instead of trying to find four numbers directly, I can:

```text
Fix two numbers
        ↓
Reduce the remaining problem to 2Sum
        ↓
Solve 2Sum using two pointers
```

The most important ideas from this problem are:

```text
1. Sort the array

2. Fix some elements using loops

3. Reduce the remaining problem

4. Use two pointers on the sorted portion

5. Move pointers based on the current sum

6. Carefully skip duplicates
```

I also learned why `long` is important when calculating the sum.

Since each number can be as large as `10⁹`, adding four numbers can exceed the range of an `int`. Casting one value to `long` ensures that the entire arithmetic expression is evaluated safely as a `long`.

The biggest takeaway for me is recognizing the structure:

```text
K-Sum
  ↓
Fix elements
  ↓
Reduce to 2Sum
  ↓
Two Pointers
```

This pattern can be reused for many array problems, especially when the problem asks for combinations of numbers that satisfy a target sum.

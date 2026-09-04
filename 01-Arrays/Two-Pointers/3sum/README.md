# WIN #11 — 3Sum

## Problem

Given an integer array `nums`, find all unique triplets `[nums[i], nums[j], nums[k]]` such that:

```text
nums[i] + nums[j] + nums[k] == 0
```

The three elements must come from different indices, and the solution must not contain duplicate triplets.

For example:

```text
Input: nums = [-1,0,1,2,-1,-4]
```

The unique triplets whose sum is `0` are:

```text
[-1,-1,2]
[-1,0,1]
```

So the output is:

```text
[[-1,-1,2],[-1,0,1]]
```

---

## My Java Solution

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {

                    result.add(
                        Arrays.asList(nums[i], nums[left], nums[right])
                    );

                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;

                } else if (sum > 0) {
                    right--;

                } else {
                    left++;
                }
            }
        }

        return result;
    }
}
```

---

## My Thought Process

The first thing I realized is that I need to find three numbers whose sum is zero.

A brute-force approach would use three nested loops and check every possible combination of three elements.

That would take `O(n³)` time.

Instead, I sorted the array first.

For example:

```text
[-1,0,1,2,-1,-4]
```

After sorting:

```text
[-4,-1,-1,0,1,2]
```

Once the array is sorted, I can fix one element and use two pointers to find the other two elements.

I use:

```text
i     → fixed element
left  → searches from the left
right → searches from the right
```

For every `i`, I set:

```text
left = i + 1
right = nums.length - 1
```

Then I calculate:

```text
sum = nums[i] + nums[left] + nums[right]
```

There are three possible cases.

### If the sum is zero

I found a valid triplet.

I add it to the result:

```text
[nums[i], nums[left], nums[right]]
```

Then I move both pointers.

Before moving them, I skip duplicate values so that the result does not contain duplicate triplets.

---

### If the sum is greater than zero

The sum is too large.

Because the array is sorted, I need to make the sum smaller.

So I move:

```text
right--
```

This gives me a smaller value.

---

### If the sum is less than zero

The sum is too small.

I need to make the sum larger.

So I move:

```text
left++
```

This gives me a larger value.

---

## Handling Duplicates

The problem requires unique triplets, so I need to avoid adding the same combination more than once.

I handle duplicates in two places.

First, for the fixed element:

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

If the current value is the same as the previous value, I skip it.

Second, after finding a valid triplet, I skip duplicate values for both pointers:

```java
while (left < right && nums[left] == nums[left + 1]) {
    left++;
}

while (left < right && nums[right] == nums[right - 1]) {
    right--;
}
```

This prevents the same triplet from being added multiple times.

---

## Pattern Recognition

### Pattern: Sorting + Two Pointers

This pattern is useful when I need to find pairs or triplets that satisfy a certain sum condition.

The general approach is:

```text
Sort the array

Fix one element

Use two pointers for the remaining elements

Compare the current sum with the target

If sum is too small:
    Move left forward

If sum is too large:
    Move right backward

If sum matches:
    Store the result
```

The important reason this works is the sorted order.

After sorting:

```text
Moving left forward  → increases the sum

Moving right backward → decreases the sum
```

This allows me to eliminate many unnecessary combinations.

The overall structure is:

```text
Sort the array
        ↓
Fix nums[i]
        ↓
left = i + 1
right = last index
        ↓
Calculate three-number sum
        ↓
Adjust left or right
        ↓
Skip duplicates
        ↓
Continue until all possible fixed elements are processed
```

---

## Complexity

### Time Complexity

```text
O(n²)
```

Sorting takes:

```text
O(n log n)
```

Then for every element, the two-pointer scan takes `O(n)` time.

Therefore:

```text
O(n log n) + O(n²)
```

The dominant term is:

```text
O(n²)
```

### Space Complexity

```text
O(1)
```

Apart from the space required for the returned result, the algorithm uses only a constant number of variables.

The sorting operation may use some implementation-dependent internal space, but the algorithm itself does not create another data structure proportional to the input.

---

## Key Learning

I learned how sorting can make a problem involving combinations much easier to solve.

Instead of using three nested loops, I can fix one element and use Two Pointers to find the other two elements.

The most important idea is:

```text
Sort the array

Fix one element

Use left and right pointers

Move the pointers based on the current sum
```

I also learned that when a problem asks for unique combinations, duplicate handling needs to be considered separately.

For this problem:

```text
Duplicate fixed value → skip using i

Duplicate left value → skip using left

Duplicate right value → skip using right
```

This reduces the brute-force `O(n³)` approach to an `O(n²)` solution.

This problem helped me understand how Sorting and Two Pointers can work together to efficiently solve problems involving pairs and triplets.
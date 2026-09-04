# WIN #12 — Subarray Sum Equals K

## Problem

Given an integer array `nums` and an integer `k`, return the total number of non-empty subarrays whose sum is equal to `k`.

A subarray is a contiguous sequence of elements from the array.

For example:

```text
Input: nums = [1,1,1], k = 2

Subarrays with sum 2:

[1,1]
[1,1]

Output: 2
```

---

## My Java Solution

```java
class Solution {
    public int subarraySum(int[] nums, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int sum = 0;
        int count = 0;

        for (int num : nums) {

            sum += num;

            int need = sum - k;

            if (map.containsKey(need)) {
                count += map.get(need);
            }

            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }
}
```

---

## My Thought Process

The first approach that comes to mind is to generate every possible subarray and calculate its sum.

However, that would require checking many different subarrays.

Instead, I used Prefix Sum with a HashMap.

The main idea is to keep track of the cumulative sum while traversing the array.

I maintain:

```text
sum → prefix sum up to the current position

count → number of valid subarrays found

map → stores previous prefix sums and how many times each one occurred
```

The important observation is:

```text
Current Prefix Sum - Previous Prefix Sum = Subarray Sum
```

If I want the subarray sum to be `k`:

```text
Current Prefix Sum - Previous Prefix Sum = k
```

Rearranging this:

```text
Previous Prefix Sum = Current Prefix Sum - k
```

So for every current prefix sum, I calculate:

```text
need = sum - k
```

Then I check whether `need` has already appeared in the HashMap.

If it has appeared, every occurrence of that prefix sum represents a subarray ending at the current position whose sum is `k`.

I add the number of occurrences to `count`.

---

## Understanding the HashMap

I initialize the HashMap with:

```java
map.put(0, 1);
```

This represents a prefix sum of `0` occurring once before the array starts.

This is important for subarrays that start from index `0`.

For example:

```text
nums = [3]
k = 3
```

After processing `3`:

```text
sum = 3

need = 3 - 3
need = 0
```

The HashMap already contains prefix sum `0`, so I know that the subarray from the beginning has sum `3`.

---

## Example

Consider:

```text
nums = [1,2,3]
k = 3
```

Start with:

```text
map = {0=1}
sum = 0
count = 0
```

Process `1`:

```text
sum = 1
need = 1 - 3 = -2
```

`-2` is not in the map.

Store:

```text
map = {0=1, 1=1}
```

Process `2`:

```text
sum = 3
need = 3 - 3 = 0
```

`0` exists in the map.

So:

```text
count = 1
```

The subarray is:

```text
[1,2]
```

Process `3`:

```text
sum = 6
need = 6 - 3 = 3
```

The prefix sum `3` exists in the map.

So:

```text
count = 2
```

The second subarray is:

```text
[3]
```

Therefore:

```text
Output = 2
```

---

## Pattern Recognition

### Pattern: Prefix Sum + HashMap

This pattern is useful when I need to find subarrays based on their sum, especially when:

* The array can contain positive and negative numbers.
* I need to count subarrays with a particular sum.
* I need better than `O(n²)` time.
* I need to remember previous cumulative sums.

The general approach is:

```text
Initialize prefix sum = 0

Store prefix sum 0 in the HashMap

For every element:

    Add the element to the prefix sum

    Calculate:
    need = current sum - target

    Check if need exists in the HashMap

    If it exists:
        Add its frequency to the answer

    Store the current prefix sum in the HashMap
```

The key relationship is:

```text
currentSum - previousSum = k
```

Therefore:

```text
previousSum = currentSum - k
```

This allows me to find valid subarrays without explicitly generating them.

---

## Complexity

### Time Complexity

```text
O(n)
```

The array is traversed once.

HashMap insertion and lookup take `O(1)` average time.

Therefore, the overall time complexity is:

```text
O(n)
```

### Space Complexity

```text
O(n)
```

In the worst case, the HashMap can contain a prefix sum for each element in the array.

---

## Key Learning

I learned how Prefix Sum can be combined with a HashMap to efficiently solve subarray sum problems.

The most important relationship I learned is:

```text
currentSum - previousSum = subarraySum
```

If the required subarray sum is `k`:

```text
previousSum = currentSum - k
```

So instead of checking every possible subarray, I can store previous prefix sums and check whether the required prefix sum has already appeared.

I also learned why the frequency of each prefix sum needs to be stored instead of just storing whether the sum exists.

If the same prefix sum occurs multiple times, each occurrence can represent a different valid subarray.

The key idea is:

```text
Store previous prefix sums

Find currentSum - k

Use the frequency of that prefix sum

Add that frequency to the answer
```

This reduces the brute-force approach from `O(n²)` to an `O(n)` solution.
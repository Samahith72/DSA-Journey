# WIN #24 — Intersection of Two Arrays II

## Problem

Given two integer arrays `nums1` and `nums2`, return their **intersection**.

Each element should appear in the result as many times as it appears in **both arrays**.

The result can be returned in any order.

For example:

```text
Input:
nums1 = [1,2,2,1]
nums2 = [2,2]

Output:
[2,2]
```

The number `2` appears:

```text
nums1 → 2 times
nums2 → 2 times
```

Therefore, it appears `2` times in the intersection.

Another example:

```text
Input:
nums1 = [4,9,5]
nums2 = [9,4,9,8,4]

Output:
[4,9]
```

Although `9` appears twice in `nums2`, it appears only once in `nums1`, so it can only appear **once** in the intersection.

---

## My Java Solution

```java
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {

        HashMap<Integer, Integer> frequency = new HashMap<>();

        for(int num : nums1){
            frequency.put(num, frequency.getOrDefault(num,0)+1);
        }


        List<Integer> list = new ArrayList<>();

        for(int num: nums2){
            if(frequency.containsKey(num) && frequency.get(num) > 0){
                list.add(num);
                frequency.put(num, frequency.get(num) -1);
            }
        }

        int[] answer = new int[list.size()];

        for(int i=0;i< list.size();i++){
            answer[i] = list.get(i);
        }


        return answer;
    }
}
```

---

## My Thought Process

The important part of this problem is that **duplicates matter**.

For example:

```text
nums1 = [1,2,2,1]
nums2 = [2,2]
```

We can't simply check whether a number exists.

We need to know:

> **How many times is each number available?**

That's why I use a `HashMap`.

The map stores:

```text
number → frequency
```

For example:

```text
nums1 = [1,2,2,1]
```

creates:

```text
1 → 2
2 → 2
```

This tells me that I can use:

* `1` at most 2 times
* `2` at most 2 times

---

## Step 1: Build the Frequency Map

I start with:

```java
HashMap<Integer, Integer> frequency = new HashMap<>();
```

Then I traverse `nums1`:

```java
for(int num : nums1){
    frequency.put(num, frequency.getOrDefault(num,0)+1);
}
```

For:

```text
nums1 = [1,2,2,1]
```

the map becomes:

```text
1 → 2
2 → 2
```

The `getOrDefault()` method is useful here.

If the number does not exist in the map:

```java
frequency.getOrDefault(num, 0)
```

returns `0`.

Then we add `1`.

So the frequency increases every time we encounter the same number.

---

## Step 2: Scan `nums2`

Now I traverse the second array:

```java
for(int num: nums2)
```

For every number, I check:

```java
if(frequency.containsKey(num) && frequency.get(num) > 0)
```

There are two conditions.

### Condition 1 — Does the number exist?

```java
frequency.containsKey(num)
```

This checks whether the number appeared in `nums1`.

### Condition 2 — Is there still an available occurrence?

```java
frequency.get(num) > 0
```

This is important because we cannot use the same occurrence more times than it appears in `nums1`.

---

## Step 3: Add a Matching Element

If both conditions are true:

```java
list.add(num);
```

I add the number to the result.

Then I decrease its frequency:

```java
frequency.put(num, frequency.get(num) -1);
```

This means:

> We have now consumed one occurrence of this number.

---

## Example Walkthrough

Consider:

```text
nums1 = [1,2,2,1]
nums2 = [2,2]
```

### Build Frequency Map

From `nums1`:

```text
1 → 2
2 → 2
```

---

### Process First `2`

`nums2` gives us:

```text
2
```

The map contains `2` and its frequency is `2`.

So we add it:

```text
list = [2]
```

Then decrease the frequency:

```text
2 → 1
```

---

### Process Second `2`

Again:

```text
2
```

Frequency is still greater than `0`:

```text
2 → 1
```

So we add it:

```text
list = [2,2]
```

Then:

```text
2 → 0
```

---

### Final Result

The list contains:

```text
[2,2]
```

So I create the final integer array:

```java
int[] answer = new int[list.size()];
```

and copy the values from the list into it.

Final result:

```text
[2,2]
```

---

## Why Do We Decrease the Frequency?

This is the most important part of the solution.

Consider:

```text
nums1 = [1]
nums2 = [1,1,1]
```

The frequency map starts as:

```text
1 → 1
```

The first `1` matches:

```text
list = [1]
1 → 0
```

The next `1` does not match because:

```text
frequency.get(1) > 0
```

is now false.

So the remaining `1`s are ignored.

The result is correctly:

```text
[1]
```

This ensures that duplicates are handled according to their actual frequency.

---

## Pattern Recognition

### Pattern: HashMap / Frequency Counting

This problem is a classic example of **frequency counting**.

Whenever a problem asks questions like:

* How many times does each value occur?
* Does another array contain the same value?
* Can I use this value again?
* How many duplicates should appear?

a `HashMap<value, frequency>` is often a strong approach.

The general pattern is:

```text
Input
  ↓
Count frequencies
  ↓
Process another collection
  ↓
Consume frequencies
  ↓
Build result
```

Here:

```text
nums1
  ↓
Frequency Map
  ↓
Scan nums2
  ↓
Match + decrease frequency
  ↓
Intersection
```

---

## Why Does This Work?

For every number, the frequency map represents how many unused occurrences are available from `nums1`.

When a matching number is found in `nums2`:

1. Add it to the answer.
2. Consume one occurrence from `nums1`.

Therefore, a number can only be added:

```text
min(frequency in nums1, frequency in nums2)
```

times.

That's exactly the definition of the intersection for this problem.

---

## Creating the Final Array

The result is initially stored in:

```java
List<Integer> list = new ArrayList<>();
```

This is convenient because we don't know the exact intersection size beforehand.

After processing both arrays, I know the exact size:

```java
list.size()
```

So I create:

```java
int[] answer = new int[list.size()];
```

Then copy each value:

```java
for(int i=0;i< list.size();i++){
    answer[i] = list.get(i);
}
```

Finally:

```java
return answer;
```

---

## Complexity

### Time Complexity

First, I traverse `nums1`:

```text
O(m)
```

Then I traverse `nums2`:

```text
O(n)
```

Finally, I copy the result into the answer array:

```text
O(k)
```

where `k` is the size of the intersection.

Therefore:

```text
O(m + n + k)
```

Since `k <= min(m,n)`, the overall complexity is:

```text
O(m + n)
```

### Space Complexity

The frequency map stores the distinct elements from `nums1`.

In the worst case:

```text
O(m)
```

The result also requires space for the intersection:

```text
O(k)
```

So the total additional space is:

```text
O(m + k)
```

If we focus on the auxiliary data structure used for processing, the HashMap requires:

```text
O(m)
```

space.

---

## Follow-Up Questions

### What if the arrays are already sorted?

If both arrays are sorted, we don't need a `HashMap`.

We can use **Two Pointers**.

For example:

```text
nums1 = [1,2,2,4]
nums2 = [2,2,3]
          ↑
```

Compare the current elements:

```text
nums1[i] vs nums2[j]
```

* If they are equal → add to result and move both.
* If `nums1[i] < nums2[j]` → move `i`.
* Otherwise → move `j`.

This also gives:

```text
O(m + n)
```

time with no HashMap.

---

### What if `nums1` is much smaller than `nums2`?

The HashMap approach can be useful because we can build the frequency map using the smaller array.

For example:

```text
nums1 → 100 elements
nums2 → 1,000,000 elements
```

Building the map from `nums1` keeps the map small.

Then scan the larger array and consume the frequencies.

This can reduce memory usage compared with building a frequency map for the larger array.

---

### What if `nums2` is stored on disk?

If `nums2` is too large to fit into memory, we don't necessarily need to load the entire array.

We can:

```text
1. Load nums1 into memory.
2. Build its frequency map.
3. Read nums2 in chunks.
4. Process each chunk.
5. Consume frequencies as matches are found.
```

This allows the algorithm to work even when `nums2` is too large to fit into memory at once.

---

## Key Learning

* When duplicates matter, think about **frequency counting**.
* A `HashMap` can store `value → frequency`.
* `getOrDefault()` makes frequency counting simple.
* Decreasing the frequency after using an element prevents over-counting duplicates.
* The intersection frequency is effectively:

```text
min(count in nums1, count in nums2)
```

* If arrays are already sorted, **Two Pointers** can replace the HashMap.
* When one array is much smaller, building the frequency map from the smaller array can save memory.
* Large external data can be processed in chunks instead of loading everything into memory.

---

## Final Takeaway

The core idea of my solution is:

```text
nums1
  ↓
Build Frequency Map
  ↓
1 → 2
2 → 2
  ↓
Scan nums2
  ↓
Is number available?
  ↓
Yes → Add to result
      Decrease frequency
  ↓
No → Skip
```

The most important concept to remember is:

> **When you need to match elements while respecting duplicates, count their frequencies and consume those counts as you find matches.**

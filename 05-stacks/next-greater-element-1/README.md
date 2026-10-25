# WIN #107 — 496. Next Greater Element I

**LeetCode:** [496. Next Greater Element I](https://leetcode.com/problems/next-greater-element-i/)  
**Difficulty:** Easy  
**Topics:** Array, Hash Table, Stack, Monotonic Stack

---

## 1. Problem

You are given two distinct integer arrays, `nums1` and `nums2`, where `nums1` is a subset of `nums2`.

For every element in `nums1`, find its **next greater element** in `nums2`.

The next greater element is the first element to the right of the given element that is strictly greater than it.

If no greater element exists, return `-1`.

Return an array `result` where `result[i]` contains the next greater element for `nums1[i]`.

### Example 1

**Input:**
```text
nums1 = [4, 1, 2]
nums2 = [1, 3, 4, 2]
```

**Output:**
```text
[-1, 3, -1]
```

**Explanation:**

- `4`: No greater element exists to its right, so the answer is `-1`.
- `1`: The first greater element to its right is `3`.
- `2`: No greater element exists to its right, so the answer is `-1`.

### Example 2

**Input:**
```text
nums1 = [2, 4]
nums2 = [1, 2, 3, 4]
```

**Output:**
```text
[3, -1]
```

---

## 2. Core Idea

Use a **monotonic stack and a HashMap** to find the next greater element for every value in `nums2`.

Instead of searching to the right separately for every element in `nums1`, first calculate the next greater element for all elements in `nums2`.

The solution has two main steps.

**Step 1: Process `nums2` using a stack.**

Traverse `nums2` from left to right.

For every number:

1. Compare it with the element at the top of the stack.
2. If the current number is greater, it is the next greater element for the stack's top element.
3. Remove that stack element and store the relationship in a HashMap.
4. Continue until the stack is empty or its top is greater than or equal to the current number.
5. Push the current number onto the stack.

**Step 2: Build the result for `nums1`.**

For every element in `nums1`, look up its next greater element in the HashMap.

If the element is not present in the map, no next greater element was found, so return `-1` for that element.

The HashMap stores relationships in this form:

```text
Element -> Next Greater Element
```

For example:

```text
1 -> 3
3 -> 4
```

---

## 3. Java Solution

```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        HashMap<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        for(int num: nums2){
            while(!stack.isEmpty() && stack.peek() < num){
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }

        int[] result = new int[nums1.length];
        for(int i=0;i < nums1.length;i++){
            result[i] = map.getOrDefault(nums1[i], -1);
        }

        return result;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the HashMap and Stack

```java
HashMap<Integer, Integer> map = new HashMap<>();
Stack<Integer> stack = new Stack<>();
```

The stack stores elements whose next greater elements have not yet been found.

The HashMap stores the next greater element for every number for which a greater element has been found.

For example, if the next greater element of `2` is `4`, we store:

```text
2 -> 4
```

### Step 2: Traverse `nums2`

```java
for(int num: nums2)
```

We process each element from left to right.

Consider:

```text
nums2 = [1, 3, 4, 2]
```

Initially:

```text
Stack: []
Map:   {}
```

### Step 3: Find elements smaller than the current number

```java
while(!stack.isEmpty() && stack.peek() < num){
    map.put(stack.pop(), num);
}
```

This is the most important part of the solution.

If the current number is greater than the stack's top, then the current number is the next greater element for that stack element.

Why?

Because we traverse from left to right. The current number is the first number encountered that is greater than the unresolved stack element.

We remove the resolved element from the stack and record the relationship in the map.

For example:

```text
Current number: 3
Stack: [1]
```

Since `1 < 3`:

- Pop `1`.
- Store `1 -> 3` in the map.

The result is:

```text
Stack: []
Map:   {1=3}
```

### Step 4: Push the current number

```java
stack.push(num);
```

After resolving all smaller elements, push the current number onto the stack.

It may become the next greater element for a smaller number encountered later.

For example, after processing `3`:

```text
Stack: [3]
```

When we process `4`, the stack's top is smaller than `4`, so we store `3 -> 4`.

### Step 5: Build the result array

```java
int[] result = new int[nums1.length];

for(int i=0;i < nums1.length;i++){
    result[i] = map.getOrDefault(nums1[i], -1);
}
```

Once we have processed all of `nums2`, the HashMap contains the next greater elements that were found.

We now iterate through `nums1`.

The method `getOrDefault(key, -1)` returns:

- The mapped next greater element if the key exists.
- `-1` if the key does not exist in the map.

Because every element of `nums1` is guaranteed to appear in `nums2`, a missing map entry means that the element has no next greater element.

### Step 6: Return the result

```java
return result;
```

The result array contains the next greater element for each value in `nums1`, in the original order.

---

## 5. Dry Run

**Input:**

```text
nums1 = [4, 1, 2]
nums2 = [1, 3, 4, 2]
```

### Part A: Build the HashMap using `nums2`

| Current number | Stack before processing | Action | Stack after processing | HashMap |
|---:|---|---|---|---|
| `1` | `[]` | Push `1` | `[1]` | `{}` |
| `3` | `[1]` | Pop `1`, store `1 -> 3`, push `3` | `[3]` | `{1=3}` |
| `4` | `[3]` | Pop `3`, store `3 -> 4`, push `4` | `[4]` | `{1=3, 3=4}` |
| `2` | `[4]` | Push `2` | `[4, 2]` | `{1=3, 3=4}` |

The stack is shown from bottom to top, so the rightmost element is the top.

At the end, the stack contains:

```text
[4, 2]
```

These elements have no next greater element to their right.

The final HashMap is:

```text
{
    1 -> 3,
    3 -> 4
}
```

Notice that `4` and `2` are not in the map because no greater element was found for them.

### Part B: Build the result for `nums1`

| `nums1[i]` | HashMap lookup | Result |
|---:|---|---:|
| `4` | Not found | `-1` |
| `1` | `1 -> 3` | `3` |
| `2` | Not found | `-1` |

Therefore:

```text
result = [-1, 3, -1]
```

**Output:**
```text
[-1, 3, -1]
```

---

## 6. Why Does the Monotonic Stack Work?

The stack maintains unresolved elements in decreasing order from bottom to top.

When a new number arrives, smaller elements at the top are removed because the current number is their next greater element.

For example:

```text
nums2 = [2, 1, 3]
```

After processing `2` and `1`:

```text
Stack:
Top -> 1
       2
```

When `3` arrives:

1. `1 < 3`, so record `1 -> 3` and pop `1`.
2. `2 < 3`, so record `2 -> 3` and pop `2`.
3. Push `3`.

The resulting map is:

```text
{
    1 -> 3,
    2 -> 3
}
```

Both elements have found their next greater element.

The stack works because an element is removed as soon as its next greater element is discovered. It never needs to be processed again.

---

## 7. Edge Cases

| Case | Input | Output |
|---|---|---|
| No next greater element | `nums1 = [4]`, `nums2 = [1, 2, 4]` | `[-1]` |
| Next greater element is adjacent | `nums1 = [1]`, `nums2 = [1, 3]` | `[3]` |
| Increasing array | `nums1 = [1, 2]`, `nums2 = [1, 2, 3]` | `[2, 3]` |
| Decreasing array | `nums1 = [2, 3]`, `nums2 = [4, 3, 2, 1]` | `[-1, -1]` |
| Element has no greater value anywhere to its right | `nums1 = [3]`, `nums2 = [1, 3, 2]` | `[-1]` |

---

## 8. Complexity Analysis

Let:

- `n` be the length of `nums2`.
- `m` be the length of `nums1`.

### Time Complexity: `O(n + m)`

**Processing `nums2`: `O(n)`**

Each element is pushed onto the stack once and popped at most once. Although there is a `while` loop inside the `for` loop, the total number of stack operations across the entire traversal is linear.

**Processing `nums1`: `O(m)`**

Each element requires one HashMap lookup, which takes `O(1)` average time.

Therefore, the total time complexity is:

`O(n + m)`

### Space Complexity: `O(n)`

The stack and HashMap can each store up to `n` elements.

The result array uses `O(m)` additional space.

Overall auxiliary space complexity is:

`O(n + m)`

Since `nums1` is a subset of `nums2`, `m <= n`, so this simplifies to **`O(n)`**.

---

## 9. Final Mental Model

Think of the stack as a collection of numbers waiting for their next greater element.

For each new number:

1. If it is greater than the stack's top, it resolves that element's next greater value.
2. Keep popping and recording relationships while the stack's top is smaller.
3. Push the current number because it may resolve an earlier element later.
4. Store the relationships in a HashMap.
5. Use the HashMap to answer the queries from `nums1`.

**Key takeaway:** A monotonic stack finds the next greater element for every number in one traversal, and a HashMap makes retrieving the answers efficient.
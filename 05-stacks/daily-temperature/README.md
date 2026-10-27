# WIN #110 — 739. Daily Temperatures

**LeetCode:** [739. Daily Temperatures](https://leetcode.com/problems/daily-temperatures/)  
**Difficulty:** Medium  
**Topics:** Array, Stack, Monotonic Stack

---

## Problem Statement

You are given an integer array `temperatures`, where `temperatures[i]` represents the temperature on the `i`th day.

Return an array `answer` where `answer[i]` represents the number of days you must wait after day `i` to experience a warmer temperature.

If there is no future day with a higher temperature, `answer[i]` must be `0`.

### Example 1

**Input:**
```text
temperatures = [73, 74, 75, 71, 69, 72, 76, 73]
```

**Output:**
```text
[1, 1, 4, 2, 1, 1, 0, 0]
```

**Explanation:**

- Day 0: Temperature `73`. The next warmer temperature is `74` on day 1. Wait `1` day.
- Day 1: Temperature `74`. The next warmer temperature is `75` on day 2. Wait `1` day.
- Day 2: Temperature `75`. The next warmer temperature is `76` on day 6. Wait `4` days.
- Day 3: Temperature `71`. The next warmer temperature is `72` on day 5. Wait `2` days.
- Day 4: Temperature `69`. The next warmer temperature is `72` on day 5. Wait `1` day.
- Day 5: Temperature `72`. The next warmer temperature is `76` on day 6. Wait `1` day.
- Day 6: Temperature `76`. No warmer temperature appears afterward. Answer: `0`.
- Day 7: Temperature `73`. No future day exists. Answer: `0`.

---

## Core Idea

The problem asks us to find the **next greater temperature for every day**.

A straightforward approach would compare each day's temperature with every future day's temperature. However, this could take O(n²) time.

Instead, we can use a **monotonic stack** to solve the problem efficiently in O(n) time.

### What Is a Monotonic Stack?

A monotonic stack maintains elements in a particular order.

In this solution, the stack stores **indices of days whose next warmer temperature has not yet been found**.

The temperatures corresponding to these indices remain in decreasing order from the bottom to the top of the stack.

When we encounter a warmer temperature, we can resolve the answer for previous days that were waiting for a warmer temperature.

### Main Strategy

1. Create an empty stack to store indices.
2. Create an answer array initialized with zeros.
3. Traverse the temperature array from left to right.
4. While the stack is not empty and the current temperature is greater than the temperature at the index on top of the stack:
   - Pop the previous index.
   - Calculate the waiting time using the difference between the current index and the previous index.
   - Store the result in the answer array.
5. Push the current index onto the stack.
6. Return the answer array.

Any indices remaining in the stack have no warmer temperature later in the array. Their answers remain `0`.

---

## Java Solution

```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {

        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int previousIdx = stack.pop();
                ans[previousIdx] = i - previousIdx;
            }

            stack.push(i);
        }

        return ans;
    }
}
```

---

## Step-by-Step Explanation

### 1. Create the Stack

```java
Stack<Integer> stack = new Stack<>();
```

The stack stores **indices**, not temperature values.

For example, if:

```text
temperatures = [73, 74, 75]
```

The index-temperature relationships are:

| Index | Temperature |
|---:|---:|
| 0 | 73 |
| 1 | 74 |
| 2 | 75 |

Storing indices lets us retrieve temperatures when needed and calculate the number of days between two positions.

### 2. Create the Answer Array

```java
int[] ans = new int[temperatures.length];
```

Java initializes every element of this integer array to `0`.

This is useful because if a day has no future warmer temperature, its answer should remain `0`.

We only need to update the answer when we find a warmer day.

### 3. Traverse the Temperature Array

```java
for (int i = 0; i < temperatures.length; i++)
```

The variable `i` represents the current day.

At each iteration, we check whether the current temperature resolves the waiting time for any previous days stored in the stack.

### 4. Find Previous Days With Lower Temperatures

```java
while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()])
```

This is the key condition in the solution.

It checks two things:

- `!stack.isEmpty()` ensures there is an unresolved day to examine.
- `temperatures[i] > temperatures[stack.peek()]` checks whether today's temperature is warmer than the temperature of the most recent unresolved day.

If both conditions are true, today's temperature is the first warmer temperature for the index at the top of the stack.

We can therefore calculate its answer.

### 5. Calculate the Waiting Time

```java
int previousIdx = stack.pop();
ans[previousIdx] = i - previousIdx;
```

First, remove the previous unresolved index from the stack.

Then calculate the waiting time:

```text
Waiting days = Current index - Previous index
```

For example, suppose:

```text
temperatures = [73, 74]
```

When the algorithm reaches index `1`:

- Current temperature: `74`
- Previous unresolved index: `0`
- Previous temperature: `73`

Since `74 > 73`, the answer for index `0` is:

```text
ans[0] = 1 - 0 = 1
```

The first day must wait one day for a warmer temperature.

The `while` loop continues because the current temperature might also resolve other previous days.

### 6. Push the Current Index

```java
stack.push(i);
```

After resolving all previous days that have a lower temperature, push the current index onto the stack.

This index represents a day whose next warmer temperature has not yet been found.

### 7. Return the Answer

```java
return ans;
```

Once every temperature has been processed, the answer array contains the waiting time for each day.

Any unresolved indices remaining in the stack keep their default answer of `0`.

---

## Dry Run

Consider the input:

```text
temperatures = [73, 74, 75, 71, 69, 72, 76, 73]
```

The stack stores indices. In the table below, stack contents are shown from bottom to top.

| Current index | Temperature | Action | Stack after processing | Answer updates |
|---:|---:|---|---|---|
| 0 | 73 | Push index 0 | `[0]` | None |
| 1 | 74 | Pop 0 because `74 > 73`; push 1 | `[1]` | `ans[0] = 1` |
| 2 | 75 | Pop 1 because `75 > 74`; push 2 | `[2]` | `ans[1] = 1` |
| 3 | 71 | Push index 3 | `[2, 3]` | None |
| 4 | 69 | Push index 4 | `[2, 3, 4]` | None |
| 5 | 72 | Pop 4 and 3; push 5 | `[2, 5]` | `ans[4] = 1`, `ans[3] = 2` |
| 6 | 76 | Pop 5 and 2; push 6 | `[6]` | `ans[5] = 1`, `ans[2] = 4` |
| 7 | 73 | Push index 7 | `[6, 7]` | None |

The indices `6` and `7` remain unresolved because no future temperatures are available to resolve them.

Their answers stay `0`.

### Final Answer

```text
[1, 1, 4, 2, 1, 1, 0, 0]
```

---

## Why Does the Algorithm Use Indices Instead of Temperatures?

The answer requires the **number of days to wait**, not just the warmer temperature.

Suppose:

```text
temperatures = [70, 72, 68, 75]
```

When the temperature reaches `75` at index `3`, it resolves the days at indices `2` and `1`.

For index `2`:

```text
3 - 2 = 1 day
```

For index `1`:

```text
3 - 1 = 2 days
```

The indices provide exactly the information needed to calculate these waiting times.

If the stack stored only temperatures, we would lose the positions needed for the subtraction.

---

## Edge Cases

| Case | Input | Expected output | Explanation |
|---|---|---|---|
| Single day | `[30]` | `[0]` | No future day exists. |
| Strictly increasing | `[30, 40, 50, 60]` | `[1, 1, 1, 0]` | Each day except the last has a warmer next day. |
| Strictly decreasing | `[60, 50, 40, 30]` | `[0, 0, 0, 0]` | No day has a warmer temperature afterward. |
| Equal temperatures | `[30, 30, 30]` | `[0, 0, 0]` | Equal temperatures are not warmer. |
| Warmer temperature after several days | `[30, 20, 25]` | `[0, 1, 0]` | The second day finds a warmer temperature on the next day; the first does not. |

**Important:** The comparison must use `>` rather than `>=`, because the problem requires a strictly warmer temperature.

---

## Complexity Analysis

Let `n` be the number of temperatures.

### Time Complexity: O(n)

Although the solution contains a `while` loop inside a `for` loop, it does not take O(n²) time.

Each index is:

- Pushed onto the stack exactly once.
- Popped from the stack at most once.

Therefore, the total number of stack operations is proportional to `n`.

**Time Complexity: O(n)**

### Space Complexity: O(n)

The answer array stores `n` results.

In the worst case, such as a strictly decreasing temperature array, all indices can remain in the stack.

Therefore, the stack may also use O(n) space.

**Auxiliary space complexity: O(n)**, including the output array.

---

## Pattern Recognition

**Pattern: Monotonic Stack — Next Greater Element**

This problem is an example of finding the next greater element for every position in an array.

The key observation is that when a warmer temperature appears, it can resolve the waiting times of earlier days that have not yet found a warmer temperature.

Use this pattern when:

- You need the next greater element to the right.
- You need to find the next smaller element to the right.
- You need to determine how far away the next qualifying element is.
- A brute-force solution would repeatedly scan future elements.

### Recognition Rule

If a problem asks you to find the next greater value for every array element, consider a monotonic stack.

When processing the current element:

1. Compare it with the element represented by the top stack index.
2. If the current value is greater, resolve the previous index.
3. Continue resolving previous indices while the condition remains true.
4. Push the current index.

This avoids repeatedly scanning the same future elements.

---

## Key Takeaways

1. Store indices in the stack when the answer depends on positions or distances.
2. Use a monotonic stack to efficiently find the next greater temperature.
3. The `while` loop resolves all previous days for which the current temperature is the first warmer temperature.
4. Calculate waiting time using `currentIndex - previousIndex`.
5. Use `>` because equal temperatures do not count as warmer.
6. Unresolved indices keep their default answer of `0`.
7. Every index is pushed once and popped at most once, giving O(n) time complexity.

**Final Mental Model:** Keep unresolved days on a stack. When a warmer temperature arrives, pop every previous day that is cooler than today and calculate how long each one waited. Push today's index and continue until every day has been processed.
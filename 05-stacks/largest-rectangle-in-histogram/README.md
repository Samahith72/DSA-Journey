# WIN #116 — 84. Largest Rectangle in Histogram

**LeetCode:** [84. Largest Rectangle in Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/)  
**Difficulty:** Hard  
**Topics:** Array, Stack, Monotonic Stack

---

## 1. Problem Statement

Given an array of integers `heights` representing the heights of histogram bars, where each bar has a width of `1`, return the area of the largest rectangle that can be formed in the histogram.

### Examples

**Example 1**

Input:
```text
heights = [2,1,5,6,2,3]
```

Output:
```text
10
```

Explanation: The largest rectangle has a height of `5` and a width of `2`.

Area = 5 × 2 = 10.

**Example 2**

Input:
```text
heights = [2,4]
```

Output:
```text
8
```

Explanation: Both bars form a rectangle with height `2` and width `2`.

---

## 2. Core Idea — Monotonic Increasing Stack

We need to find the largest possible rectangle using consecutive histogram bars.

For every bar, we want to determine how far it can extend:
- To the left, before encountering a shorter bar.
- To the right, before encountering a shorter bar.

The stack helps us find these boundaries efficiently.

Instead of storing heights, we store **indices of histogram bars**.

### Why an increasing stack?

The stack maintains indices whose corresponding heights are in non-decreasing order.

When we encounter a bar shorter than the bar at the top of the stack, we know that the taller bar cannot extend any further to the right.

We pop that bar and calculate the maximum rectangle area it can form.

### The important formula

```text
Area = Height × Width
```

The height is the height of the popped bar.

The width depends on the remaining stack:

**Case 1: Stack becomes empty**

```text
width = currentIndex
```

There is no shorter bar to the left, so the rectangle extends from index `0` to `currentIndex - 1`.

**Case 2: Stack is not empty**

```text
width = currentIndex - stack.peek() - 1
```

The new stack top represents the previous shorter-bar boundary.

The rectangle extends from `stack.peek() + 1` to `currentIndex - 1`.

---

## 3. Java Solution

```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int answer = 0;
        int n = heights.length;

        for(int i=0;i<=n;i++){
            int currentHeight = (i == n)? 0: heights[i];

            while(!stack.isEmpty()&& heights[stack.peek()] > currentHeight){
                int height = heights[stack.pop()];

                int width;
                if(stack.isEmpty()){
                    width = i;
                }
                else{
                    width = i - stack.peek()-1;
                }

                int area = height * width;
                answer = Math.max(answer, area);
            }

            stack.push(i);
        }

        return answer;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the stack

```java
Stack<Integer> stack = new Stack<>();
int answer = 0;
int n = heights.length;
```

- `stack` stores histogram indices.
- `answer` maintains the maximum rectangle area.
- `n` stores the number of bars.

### Step 2: Traverse the histogram

```java
for(int i=0;i<=n;i++){
    int currentHeight = (i == n)? 0: heights[i];
```

Notice that the loop runs until `i <= n`, not `i < n`.

When `i == n`, we use a virtual bar of height `0`.

This is called a **sentinel bar**.

It ensures that all remaining bars in the stack are eventually processed, even when the histogram ends with increasing heights.

### Step 3: Identify when to calculate an area

```java
while(!stack.isEmpty()&& heights[stack.peek()] > currentHeight){
```

If the current bar is shorter than the bar at the top of the stack, we can calculate the maximum rectangle for that taller bar.

The current index becomes its right boundary.

### Step 4: Get the popped bar's height

```java
int height = heights[stack.pop()];
```

Remove the index from the stack and retrieve its height.

This is the height of the rectangle we are calculating.

### Step 5: Calculate the width

```java
if(stack.isEmpty()){
    width = i;
}
else{
    width = i - stack.peek()-1;
}
```

There are two possibilities:

- If the stack is empty, there is no shorter bar to the left. The rectangle extends all the way to index `0`.
- Otherwise, the new stack top is the previous shorter-bar boundary.

Therefore, the width is the distance between the two boundaries, excluding both boundary bars.

### Step 6: Calculate and update the area

```java
int area = height * width;
answer = Math.max(answer, area);
```

Calculate the rectangle area and update the maximum answer.

### Step 7: Push the current index

```java
stack.push(i);
```

After processing any taller bars, push the current index onto the stack.

Its height becomes part of the increasing stack.

---

## 5. Dry Run

Let's trace:

```text
heights = [2,1,5,6,2,3]
```

The virtual bar at index `6` has height `0`.

| Index | Height | Operation | Area |
|---:|---:|---|---:|
| 0 | 2 | Push index 0 | — |
| 1 | 1 | Pop height 2, width 1 | 2 |
| 1 | 1 | Push index 1 | — |
| 2 | 5 | Push index 2 | — |
| 3 | 6 | Push index 3 | — |
| 4 | 2 | Pop height 6, width 1 | 6 |
| 4 | 2 | Pop height 5, width 2 | 10 |
| 4 | 2 | Push index 4 | — |
| 5 | 3 | Push index 5 | — |
| 6 | 0 | Pop height 3, width 1 | 3 |
| 6 | 0 | Pop height 2, width 4 | 8 |
| 6 | 0 | Pop height 1, width 6 | 6 |

The maximum area is:

```text
answer = 10
```

The winning rectangle:

```text
Height = 5
Width  = 2
Area   = 10
```

It covers the bars with heights `5` and `6`.

---

## 6. Understanding the Width Formula

This is the most important part of the problem.

Consider:

```text
heights = [2,1,5,6,2,3]
```

When we reach index `4`, the current height is `2`.

The stack contains indices:

```text
[1, 2, 3]
```

The height at index `3` is `6`.

Since `2 < 6`, we pop index `3`.

Now:

```text
height = 6
currentIndex = 4
stack.peek() = 2
```

Calculate:

```text
width = 4 - 2 - 1
      = 1
```

So:

```text
area = 6 × 1
     = 6
```

Next, we pop index `2`, whose height is `5`.

The stack now contains:

```text
[1]
```

Calculate:

```text
width = 4 - 1 - 1
      = 2
```

So:

```text
area = 5 × 2
     = 10
```

This is how the algorithm discovers that a rectangle of height `5` can extend across two bars.

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `[]` | 0 | Empty histogram |
| `[2]` | 2 | Single bar |
| `[2,4]` | 4 | Best rectangle uses both bars at height 2 |
| `[2,2,2]` | 6 | Equal-height bars form one rectangle |
| `[5,4,3,2,1]` | 9 | Largest rectangle has height 3 and width 3 |
| `[1,2,3,4,5]` | 9 | Largest rectangle has height 3 and width 3 |

---

## 8. Complexity Analysis

Let `n` be the number of histogram bars.

**Time Complexity: O(n)**

- Every index is pushed onto the stack once.
- Every index is popped from the stack at most once.
- Although there is a `while` loop inside the `for` loop, each element is processed only a constant number of times.

Therefore, the overall time complexity is O(n).

**Space Complexity: O(n)**

In the worst case, the stack can store all histogram indices.

Therefore, auxiliary space complexity is O(n).

---

## 9. Pattern Recognition

**Pattern: Monotonic Increasing Stack + Boundary Calculation**

This pattern is useful when:
- You need to find the nearest smaller element.
- You need to calculate the maximum width a particular element can extend across.
- A shorter element determines the boundary of a larger element.
- You need to calculate maximum areas using consecutive elements.

The key observation is that a bar's maximum rectangle can be calculated when we discover the first shorter bar to its right.

The remaining stack gives us the previous shorter-bar boundary.

---

## 10. Final Mental Model

Think of every histogram bar as a possible rectangle height.

For each bar, ask:

1. How far can this bar extend to the left?
2. How far can it extend to the right?
3. What is the maximum width it can cover?
4. What area does that width produce?

The stack helps us answer these questions without repeatedly scanning the histogram.

Remember these three important operations:

```text
Current bar shorter than stack top
        |
        v
Pop the taller bar
        |
        v
Calculate its maximum width
        |
        v
Calculate and update area
```

**The main takeaway:** A monotonic stack allows us to calculate the maximum rectangle for each bar when its right boundary becomes known. The stack top after popping provides the left boundary, making the entire solution O(n).
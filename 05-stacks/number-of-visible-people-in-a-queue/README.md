# WIN #119 — 1944. Number of Visible People in a Queue

**LeetCode:** [1944. Number of Visible People in a Queue](https://leetcode.com/problems/number-of-visible-people-in-a-queue/)  
**Difficulty:** Hard  
**Topics:** Array, Stack, Monotonic Stack

---

## 1. Problem Statement

There are `n` people standing in a queue, and each person has a unique height.

You are given an integer array `heights`, where `heights[i]` represents the height of the person at position `i`.

A person can see another person if:

- The other person is standing somewhere in front of them.
- Everyone between them is shorter than both of them.

Return an array `answer` where `answer[i]` represents the number of people that the person at position `i` can see.

### Examples

**Example 1**

Input:
```text
heights = [10,6,8,5,11,9]
```

Output:
```text
[3,1,2,1,1,0]
```

Explanation:

- Person at index 0 (height 10) can see heights 6, 8, and 11.
- Person at index 1 (height 6) can see height 8.
- Person at index 2 (height 8) can see heights 5 and 11.
- Person at index 3 (height 5) can see height 11.
- Person at index 4 (height 11) can see height 9.
- Person at index 5 has nobody in front of them.

**Example 2**

Input:
```text
heights = [5,1,2,3,10]
```

Output:
```text
[4,1,1,1,0]
```

---

## 2. Core Idea — Monotonic Stack + Right-to-Left Traversal

The main challenge is determining how many people each person can see without repeatedly scanning the people in front of them.

We can solve this efficiently using a monotonic stack.

Instead of processing the queue from left to right, we process it from **right to left**.

Why?

When processing a person at index `i`, all the people to their right have already been considered. The stack maintains the relevant heights among those people.

### The key observation

For the current person, there are two possibilities:

**Case 1: A person in front is shorter**

If the person at the top of the stack is shorter than the current person, they are visible.

We count them and remove them from the stack.

Why remove them?

The current person is taller, so they block those shorter people from being visible to people further behind.

**Case 2: A person in front is taller**

If the stack still contains someone after removing all shorter people, that person is the first taller person visible to the current person.

We count that person once.

We do not remove them because they may also be visible to people further behind.

---

## 3. Java Solution

```java
class Solution {
    public int[] canSeePersonsCount(int[] heights) {

        int n = heights.length;
        int[] answer = new int[n];
        int[] stack = new int[n];

        int top = -1;

        for(int i=n-1;i >=0;i--){
            int count = 0;
            int currentHeight = heights[i];

            while(top >=0 && stack[top] < currentHeight){
                count++;
                top--;
            }

            if(top >=0){
                count++;
            }

            top++;
            answer[i] = count;
            stack[top] = currentHeight;
        }
        
        return answer;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the arrays

```java
int n = heights.length;
int[] answer = new int[n];
int[] stack = new int[n];

int top = -1;
```

- `answer` stores the number of visible people for every position.
- `stack` is an array-based implementation of a stack.
- `top` represents the index of the current stack top.
- `top = -1` means the stack is initially empty.

Using an array as a stack avoids the need for a separate `Stack<Integer>` object.

### Step 2: Traverse from right to left

```java
for(int i=n-1;i >=0;i--){
```

We begin with the last person and move towards the first person.

The last person cannot see anyone because nobody is standing in front of them.

As we move left, the stack contains relevant people from the already-processed right side.

### Step 3: Initialize the current person's count

```java
int count = 0;
int currentHeight = heights[i];
```

For every person:
- Start their visible-person count at zero.
- Store their height for comparison.

### Step 4: Remove shorter people

```java
while(top >=0 && stack[top] < currentHeight){
    count++;
    top--;
}
```

As long as the stack is not empty and its top contains someone shorter than the current person:

1. Count that person as visible.
2. Pop them from the stack.

This removes people who are blocked by the current taller person for the remaining people further behind.

### Step 5: Check for the first taller person

```java
if(top >=0){
    count++;
}
```

After removing all shorter people, if the stack is still non-empty, its top represents the first taller person visible to the current person.

Count that person once.

We do not pop this taller person because they remain relevant to people further behind.

### Step 6: Store the answer and push the current person

```java
top++;
answer[i] = count;
stack[top] = currentHeight;
```

- Increase the stack top.
- Store the current person's visibility count.
- Push their height onto the stack.

This makes the current person available for visibility calculations of people further behind.

---

## 5. Dry Run

Let's trace the input:

```text
heights = [10,6,8,5,11,9]
```

We traverse from right to left.

Initial state:

```text
Stack  = []
Answer = [0,0,0,0,0,0]
```

| Index | Height | Operation | Visible Count | Stack (bottom → top) |
|---:|---:|---|---:|---|
| 5 | 9 | No people in front | 0 | `[9]` |
| 4 | 11 | Pop 9 because 9 is shorter | 1 | `[11]` |
| 3 | 5 | 11 is taller, count it | 1 | `[11, 5]` |
| 2 | 8 | Pop 5; count taller 11 | 2 | `[11, 8]` |
| 1 | 6 | 8 is taller, count it | 1 | `[11, 8, 6]` |
| 0 | 10 | Pop 6 and 8; count taller 11 | 3 | `[11, 10]` |

Final answer:

```text
[3,1,2,1,1,0]
```

---

## 6. Understanding the Most Important Operation

Consider the person at index `0`, whose height is `10`.

The people in front of them have heights:

```text
[6,8,5,11,9]
```

We process these from right to left, so the relevant stack before processing height `10` is:

```text
[11,8,6]
```

Now compare:

**First comparison:**

```text
10 > 6
```

Person with height `6` is visible.

```text
count = 1
```

Pop `6`.

**Second comparison:**

```text
10 > 8
```

Person with height `8` is visible.

```text
count = 2
```

Pop `8`.

**Third comparison:**

```text
10 < 11
```

Person with height `11` is taller.

They are also visible, so count them once.

```text
count = 3
```

We stop because height `11` blocks everyone behind them.

The person with height `9` cannot be seen.

Therefore:

```text
answer[0] = 3
```

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---|---|---|
| `[5]` | `[0]` | Only one person |
| `[5,4,3,2,1]` | `[1,1,1,1,0]` | Every person sees only the next person |
| `[1,2,3,4,5]` | `[4,3,2,1,0]` | Each person sees everyone until the end |
| `[10,6,8,5,11,9]` | `[3,1,2,1,1,0]` | Mixed heights |
| `[5,1,2,3,10]` | `[4,1,1,1,0]` | Increasing heights followed by a taller person |

---

## 8. Complexity Analysis

Let `n` be the number of people in the queue.

**Time Complexity: O(n)**

Although there is a `while` loop inside the `for` loop:

- Every person is pushed onto the stack exactly once.
- Every person is popped from the stack at most once.
- Each remaining stack-top comparison takes constant time per iteration of the outer loop.

Therefore, the total time complexity is O(n).

**Space Complexity: O(n)**

- The answer array requires O(n) space.
- The stack array requires O(n) space.

Therefore, auxiliary space complexity is O(n).

---

## 9. Pattern Recognition

**Pattern: Monotonic Stack + Reverse Traversal**

This pattern is useful when:

- You need to find the next or nearest greater element.
- You need to determine visibility based on relative heights.
- Taller elements act as blockers.
- You need to process elements based on information from their right side.

The key observation is that shorter elements can be removed once a taller person blocks them, while the first taller person must remain available as a visibility boundary.

---

## 10. Final Mental Model

Imagine standing at a position in the queue and looking towards the people in front of you.

- Shorter people can be seen until a taller person blocks the view.
- Every shorter person removed from the stack contributes one to the visible count.
- If a taller person remains, that person is also visible.
- The current person is then pushed onto the stack for the next person behind them.

The core logic is:

```text
While stack top is shorter:
    Count the person
    Pop the person

If stack is not empty:
    Count the first taller person

Push current person
```

**The main takeaway:** A monotonic stack allows us to eliminate people who are blocked by a taller person while preserving the nearest taller boundary. Processing from right to left makes it possible to calculate visibility for everyone in O(n) time.
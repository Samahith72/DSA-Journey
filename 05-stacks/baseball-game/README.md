# WIN #102 — 682. Baseball Game

**LeetCode:** [682. Baseball Game](https://leetcode.com/problems/baseball-game/)  
**Difficulty:** Easy  
**Topics:** Array, Stack, Simulation

---

## 1. Problem

You are given an array of strings called `operations`. Each string represents an operation that changes the baseball score record.

There are four possible operations:

| Operation | Meaning |
|---|---|
| Integer `x` | Record a new score of `x`. |
| `"+"` | Record the sum of the previous two scores. |
| `"D"` | Record double the previous score. |
| `"C"` | Remove the previous score from the record. |

Return the sum of all scores remaining after processing every operation.

### Example 1

**Input:**

```text
ops = ["5", "2", "C", "D", "+"]
```

**Output:**

```text
30
```

Explanation:

```text
"5"  -> [5]
"2"  -> [5, 2]
"C"  -> [5]
"D"  -> [5, 10]
"+"  -> [5, 10, 15]
```

Final sum:

```text
5 + 10 + 15 = 30
```

### Example 2

**Input:**

```text
ops = ["5", "-2", "4", "C", "D", "9", "+", "+"]
```

**Output:**

```text
27
```

### Example 3

**Input:**

```text
ops = ["1", "C"]
```

**Output:**

```text
0
```

The score `1` is removed, leaving an empty record.

---

## 2. Core Idea

The key observation is that every operation depends on the most recent scores.

A **stack** is ideal because it follows the Last In, First Out (LIFO) principle.

- `"C"` removes the most recent score.
- `"D"` reads the most recent score and pushes its double.
- `"+"` uses the previous two scores to calculate a new score.
- An integer is converted from a string and pushed onto the stack.

After processing all operations, pop every remaining score and add them together.

The stack represents the current valid score record throughout the simulation.

---

## 3. Java Solution

```java
class Solution {
    public int calPoints(String[] operations) {

        Stack<Integer> s = new Stack<>();

        for (String ch : operations) {
            if (ch.equals("C")) {
                if (s.isEmpty()) {
                    continue;
                }
                s.pop();

            } else if (ch.equals("D")) {
                if (s.isEmpty()) {
                    continue;
                }

                int num = s.peek();
                num = num * 2;
                s.push(num);

            } else if (ch.equals("+")) {
                if (s.size() < 2) {
                    continue;
                }

                int val2 = s.pop();
                int val1 = s.pop();
                int ans = val1 + val2;

                s.push(val1);
                s.push(val2);
                s.push(ans);

            } else {
                s.push(Integer.parseInt(ch));
            }
        }

        int ans = 0;

        while (!s.isEmpty()) {
            ans += s.pop();
        }

        return ans;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the stack

```java
Stack<Integer> s = new Stack<>();
```

The stack stores all valid scores in the order they were recorded.

For example:

```text
Bottom -> [5, 2] <- Top
```

Here, `2` is the most recent score.

### Step 2: Process each operation

```java
for (String ch : operations) {
```

We examine each operation in the input array and apply its corresponding rule.

### Step 3: Handle `"C"` — Cancel the previous score

```java
if (ch.equals("C")) {
    if (s.isEmpty()) {
        continue;
    }
    s.pop();
}
```

`pop()` removes the top score.

Example:

```text
Before: [5, 2, 4]
After:  [5, 2]
```

The score `4` is invalidated and no longer contributes to the final sum.

The empty-stack check is defensive; the problem guarantees that every `"C"` operation is valid.

### Step 4: Handle `"D"` — Double the previous score

```java
else if (ch.equals("D")) {
    if (s.isEmpty()) {
        continue;
    }

    int num = s.peek();
    num = num * 2;
    s.push(num);
}
```

`peek()` retrieves the top score without removing it.

We double that score and push the result as a new entry.

Example:

```text
Before: [5, 10]
Previous score: 10
New score: 20

After: [5, 10, 20]
```

The original score remains in the stack.

### Step 5: Handle `"+"` — Sum the previous two scores

```java
else if (ch.equals("+")) {
    if (s.size() < 2) {
        continue;
    }

    int val2 = s.pop();
    int val1 = s.pop();
    int ans = val1 + val2;

    s.push(val1);
    s.push(val2);
    s.push(ans);
}
```

The top two scores are temporarily removed.

- `val2` receives the most recent score.
- `val1` receives the score immediately before it.
- `ans` is their sum.

We then push `val1` and `val2` back in their original order, followed by the new score.

Example:

```text
Before: [5, 10]
val2 = 10
val1 = 5
ans = 15

Restore the previous scores and push 15.

After: [5, 10, 15]
```

**Important:** The previous two scores must remain in the record. The `"+"` operation creates an additional score; it does not replace the previous scores.

The size check is defensive because the problem guarantees at least two previous scores for `"+"`.

### Step 6: Handle an integer score

```java
else {
    s.push(Integer.parseInt(ch));
}
```

If the operation is not `"C"`, `"D"`, or `"+"`, it represents an integer.

`Integer.parseInt(ch)` converts the string into an integer, which is pushed onto the stack.

For example:

```text
Input operation: "-2"
Stack before: [5]
Stack after:  [5, -2]
```

### Step 7: Calculate the total score

```java
int ans = 0;

while (!s.isEmpty()) {
    ans += s.pop();
}

return ans;
```

Once every operation has been processed, the stack contains only valid scores.

We pop each score and add it to `ans`, then return the total.

---

## 5. Dry Run

Consider:

```text
operations = ["5", "2", "C", "D", "+"]
```

| Step | Operation | Action | Stack, bottom to top |
|---:|---|---|---|
| 1 | `"5"` | Push `5` | `[5]` |
| 2 | `"2"` | Push `2` | `[5, 2]` |
| 3 | `"C"` | Remove `2` | `[5]` |
| 4 | `"D"` | Push `5 × 2 = 10` | `[5, 10]` |
| 5 | `"+"` | Push `5 + 10 = 15` | `[5, 10, 15]` |

The final stack is:

```text
Bottom -> [5, 10, 15] <- Top
```

Calculate the total:

```text
5 + 10 + 15 = 30
```

**Final output: `30`**

---

## 6. Edge Cases

| Case | Input | Output |
|---|---|---:|
| Cancel the only score | `["1", "C"]` | `0` |
| Negative score | `["-2"]` | `-2` |
| Double a negative score | `["-2", "D"]` | `-6` |
| Add two previous scores | `["5", "2", "+"]` | `14` |
| Multiple cancellations | `["5", "2", "C", "C"]` | `0` |
| Only one score remains | `["7"]` | `7` |

The problem guarantees that all operations are valid, so the defensive checks for insufficient stack elements are not required for correctness under the stated constraints.

---

## 7. Complexity Analysis

Let \(n\) be the number of operations.

**Time Complexity: O(n)**

- Each operation is processed once.
- Each stack operation takes O(1) time.
- The final summation pops each remaining score once.

Therefore, the overall time complexity is O(n).

**Auxiliary Space Complexity: O(n)**

In the worst case, every operation adds a score, so the stack can hold up to \(n\) scores.

Therefore, the auxiliary space complexity is O(n).

---

## 8. Pattern Recognition

This problem demonstrates the **stack-based simulation pattern**.

Use a stack when:

- Operations depend on the most recent elements.
- You need to remove the latest element.
- You need to inspect the top one or two elements.
- The sequence of valid elements changes dynamically.

### Why is a stack appropriate?

The operation `"C"` requires removing the last score, which is exactly what `pop()` does.

The operations `"D"` and `"+"` depend on the most recent scores, which can be accessed efficiently using `peek()` and `pop()`.

The stack also ensures that cancelled scores are no longer included in later calculations.

---

## 9. Stack Patterns Learned So Far

| Problem | Pattern |
|---|---|
| Valid Parentheses | Matching pairs with a stack |
| Min Stack | Stack with auxiliary minimum tracking |
| Evaluate Reverse Polish Notation | Operand stack |
| Daily Temperatures | Monotonic decreasing stack |
| Next Greater Element | Monotonic stack |
| Next Greater Node In Linked List | Monotonic stack with indices |
| Remove All Adjacent Duplicates In String | Stack-based cancellation |
| Baseball Game | Stack-based simulation and score history |

---

## 10. Final Mental Model

Think of the stack as the current valid baseball record.

```text
Integer:
    Push the score

C:
    Pop the latest score

D:
    Peek at the latest score
    Push its double

+:
    Read the previous two scores
    Restore them
    Push their sum

After all operations:
    Sum the remaining scores
```

**Key takeaway:** When operations depend on the most recent one or two values and may invalidate earlier values, a stack makes the simulation straightforward and efficient. Here, every score is added, cancelled, or used to calculate a new score in O(1) time per operation.
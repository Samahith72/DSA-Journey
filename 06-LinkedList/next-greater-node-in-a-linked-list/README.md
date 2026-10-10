# WIN #93 — 1019. Next Greater Node In Linked List

[LeetCode — 1019. Next Greater Node In Linked List](https://leetcode.com/problems/next-greater-node-in-linked-list/)

**Difficulty:** Medium  
**Topics:** Linked List, ArrayList, Stack, Monotonic Stack

---

## 1. Problem

Given the head of a linked list, find the **next greater node** for every node.

The next greater node is the first node to the right whose value is **strictly greater** than the current node's value.

If no such node exists, store `0` in the answer array.

### Example 1

```text
Input:  head = [2,1,5]
Output: [5,5,0]
```

Explanation:

- For `2`, the next greater value is `5`.
- For `1`, the next greater value is `5`.
- For `5`, no greater value exists, so the answer is `0`.

### Example 2

```text
Input:  head = [2,7,4,3,5]
Output: [7,0,5,5,0]
```

Explanation:

- `2` → `7`
- `7` → no greater value, so `0`
- `4` → `5`
- `3` → `5`
- `5` → no greater value, so `0`

---

## 2. Core Idea

Your solution combines three data structures:

1. **ArrayList** — converts the linked list into an indexable collection of values.
2. **Stack** — stores indices of nodes whose next greater values have not yet been found.
3. **Answer array** — stores the next greater value for every position.

The most important observation is that we do not need to search forward separately for every node.

Instead, we process the values from left to right and use a stack to resolve previous nodes whenever we encounter a larger value.

This technique is called a **monotonic stack**.

The overall strategy is:

```text
Linked List
     |
     v
Convert to ArrayList
     |
     v
Create answer array
     |
     v
Process values from left to right
     |
     v
Use stack of unresolved indices
     |
     v
Fill answers when greater values appear
```

---

## 3. Java Solution

```java
class Solution {
    public int[] nextLargerNodes(ListNode head) {

        List<Integer> value = new ArrayList<>();

        while(head != null){
            value.add(head.val);
            head = head.next;
        }

        int n = value.size();
        int[] answer = new int[n];
        Stack<Integer> s = new Stack<>();

        for(int i = 0; i < n; i++){

            while(!s.isEmpty() && value.get(i) > value.get(s.peek())){
                int idx = s.pop();
                answer[idx] = value.get(i);
            }

            s.push(i);
        }

        return answer;
    }
}
```

---

## 4. Step 1 — Convert the Linked List into an ArrayList

```java
List<Integer> value = new ArrayList<>();

while(head != null){
    value.add(head.val);
    head = head.next;
}
```

A linked list does not provide direct access to an arbitrary index.

For example, accessing its fifth node requires traversing the preceding nodes.

By converting it into an `ArrayList`, we can access values using:

```java
value.get(i)
```

For the input:

```text
2 → 7 → 4 → 3 → 5
```

the ArrayList becomes:

```text
Index:   0  1  2  3  4
Value:   2  7  4  3  5
```

Notice that the stack will store **indices**, not node values.

This distinction is essential because we need to know exactly which position in the answer array must be updated.

---

## 5. Step 2 — Create the Answer Array

```java
int n = value.size();
int[] answer = new int[n];
```

The answer array initially contains zeros:

```text
answer = [0, 0, 0, 0, 0]
```

This is useful because the problem requires `0` whenever a node has no greater value to its right.

We only need to overwrite an entry when we discover its next greater value.

---

## 6. Step 3 — Create the Stack

```java
Stack<Integer> s = new Stack<>();
```

The stack stores indices of nodes whose next greater values have not yet been found.

For example, if the stack contains:

```text
[0, 2, 3]
```

it means the nodes at indices `0`, `2`, and `3` are still waiting for their next greater values.

Why store indices rather than values?

Because when we find a greater value, we must update:

```java
answer[idx]
```

Without the index, we would not know which answer position to modify.

---

## 7. Step 4 — Process Every Value

```java
for(int i = 0; i < n; i++){
```

We process each value from left to right.

For each index `i`, we ask:

> Is the current value greater than the value at the index on top of the stack?

If it is, the current value is the next greater value for that unresolved index.

The central condition is:

```java
while(!s.isEmpty() && value.get(i) > value.get(s.peek())){
```

Let's understand each part.

### `!s.isEmpty()`

```java
!s.isEmpty()
```

We must ensure that the stack contains an index before checking its top.

### `s.peek()`

```java
s.peek()
```

Returns the index at the top of the stack without removing it.

### `value.get(s.peek())`

```java
value.get(s.peek())
```

Retrieves the value associated with that index.

### Strictly greater comparison

```java
value.get(i) > value.get(s.peek())
```

The current value must be strictly greater.

Equal values do not qualify.

For example:

```text
Current value = 5
Stack top value = 5
```

The condition is false because `5` is not greater than `5`.

---

## 8. Step 5 — Resolve Previous Indices

Inside the `while` loop:

```java
int idx = s.pop();
answer[idx] = value.get(i);
```

Two operations happen here.

First:

```java
int idx = s.pop();
```

Remove the unresolved index from the stack.

Second:

```java
answer[idx] = value.get(i);
```

Store the current value as the next greater value for that index.

For example:

```text
Values:  [2, 7, 4]
Index:    0  1  2
```

Suppose the stack contains:

```text
[0]
```

The current value is `7` at index `1`.

Since:

```text
7 > value[0]
7 > 2
```

we pop index `0` and update:

```text
answer[0] = 7
```

The answer becomes:

```text
[7, 0, 0]
```

The stack no longer needs index `0`, because its next greater value has been found.

---

## 9. Step 6 — Push the Current Index

After resolving all previous indices that are smaller than the current value:

```java
s.push(i);
```

We push the current index onto the stack.

Why?

Because we have not yet found the next greater value for the current node.

For example:

```text
Current index = 1
Current value = 7
```

If no greater value has appeared yet, index `1` remains unresolved.

It must stay in the stack until a greater value appears or the traversal ends.

---

## 10. Complete Dry Run

Consider:

```text
Values = [2, 7, 4, 3, 5]
```

Initially:

```text
answer = [0, 0, 0, 0, 0]
stack  = []
```

### Iteration 1 — `i = 0`, value = 2

The stack is empty, so nothing can be resolved.

Push index `0`.

```text
Stack  = [0]
Answer = [0, 0, 0, 0, 0]
```

### Iteration 2 — `i = 1`, value = 7

Compare `7` with the value at the stack's top index:

```text
value[0] = 2
7 > 2
```

Pop index `0` and update:

```text
answer[0] = 7
```

Push index `1`.

```text
Stack  = [1]
Answer = [7, 0, 0, 0, 0]
```

### Iteration 3 — `i = 2`, value = 4

Compare:

```text
value[1] = 7
4 > 7  → false
```

No index is removed.

Push index `2`.

```text
Stack  = [1, 2]
Answer = [7, 0, 0, 0, 0]
```

### Iteration 4 — `i = 3`, value = 3

Compare:

```text
value[2] = 4
3 > 4  → false
```

Push index `3`.

```text
Stack  = [1, 2, 3]
Answer = [7, 0, 0, 0, 0]
```

### Iteration 5 — `i = 4`, value = 5

Now compare `5` with the stack's top value.

First comparison:

```text
value[3] = 3
5 > 3
```

Pop index `3`:

```text
answer[3] = 5
```

Stack:

```text
[1, 2]
```

Second comparison:

```text
value[2] = 4
5 > 4
```

Pop index `2`:

```text
answer[2] = 5
```

Stack:

```text
[1]
```

Third comparison:

```text
value[1] = 7
5 > 7  → false
```

Stop the `while` loop.

Push index `4`.

```text
Stack  = [1, 4]
Answer = [7, 0, 5, 5, 0]
```

The traversal is complete.

Indices `1` and `4` remain unresolved, so their answer values stay `0`.

Final output:

```text
[7, 0, 5, 5, 0]
```

---

## 11. Why Does the Stack Work?

The stack stores indices whose next greater values have not yet been found.

When we encounter a new value, it can resolve one or more indices at the top of the stack.

For example:

```text
Values: [2, 1, 5]
```

When `5` appears, it is greater than both `1` and `2`.

So one value resolves multiple previous positions.

The stack avoids repeating the same forward search for every node.

This is the key reason the algorithm is efficient.

---

## 12. Why Use a `while` Loop Instead of an `if`?

This is one of the most important details in the solution.

Suppose:

```text
Values = [2, 1, 5]
```

Before processing `5`, the stack contains:

```text
[0, 1]
```

The top index `1` has value `1`.

Since `5 > 1`, we pop index `1`.

But now index `0` is at the top, and:

```text
5 > 2
```

is also true.

We must pop index `0` too.

A single `if` would resolve only one index.

The `while` loop continues until the stack is empty or its top value is greater than or equal to the current value.

---

## 13. Why Do Unresolved Answers Remain Zero?

The answer array is initialized with zeros:

```java
int[] answer = new int[n];
```

An index is updated only when a greater value is found:

```java
answer[idx] = value.get(i);
```

If an index remains in the stack at the end, no later value was strictly greater than its value.

Therefore, its answer correctly remains `0`.

No additional loop is necessary to process the remaining stack.

---

## 14. Monotonic Stack Pattern

This problem uses a **monotonic stack of indices**.

As indices are pushed, the values at those indices form a non-increasing sequence from the bottom of the stack to the top.

When a larger value appears, smaller values at the top are removed.

The general pattern is:

```java
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty()
            && values[i] > values[stack.peek()]) {

        int idx = stack.pop();
        answer[idx] = values[i];
    }

    stack.push(i);
}
```

This pattern is useful for problems involving:

- Next greater element
- Next smaller element
- Daily temperatures
- Stock span
- Finding the first greater value to the right

The exact comparison and how the answer is recorded depend on the problem.

---

## 15. Edge Cases

### Increasing values

```text
Input:  [1, 2, 3]
Output: [2, 3, 0]
```

Each new value resolves the previous smaller value.

### Decreasing values

```text
Input:  [3, 2, 1]
Output: [0, 0, 0]
```

No greater value appears to the right of any node.

### Equal values

```text
Input:  [2, 2, 3]
Output: [3, 3, 0]
```

Equal values do not resolve one another because the comparison uses `>` rather than `>=`.

### Single node

```text
Input:  [5]
Output: [0]
```

There is no node to the right.

---

## 16. Complexity

Let `n` be the number of nodes.

### Time Complexity

**O(n)**

- Converting the linked list into an ArrayList takes `O(n)`.
- Each index is pushed onto the stack once.
- Each index is popped at most once.

Although there is a nested `while` loop, the total number of stack operations is linear.

Therefore, the overall time complexity is `O(n)`.

### Auxiliary Space Complexity

**O(n)**

The ArrayList, answer array, and stack can each require space proportional to the number of nodes.

The answer array is the required output; the ArrayList and stack are additional data structures used by the algorithm.

---

## 17. Pattern Recognition

When a problem asks for the first greater value to the right of each element, think:

```text
Next Greater Element
        |
        v
Monotonic Stack
        |
        v
Store unresolved indices
        |
        v
Resolve smaller values when a larger value appears
```

The key question to ask yourself is:

> Can one newly encountered value answer the question for multiple previous elements?

If yes, a monotonic stack may be a strong approach.

---

## 18. Linked List Patterns Learned So Far

| Problem | Pattern |
|---|---|
| Reverse Linked List | Three-pointer reversal |
| Middle of Linked List | Fast and slow pointers |
| Linked List Cycle | Floyd's cycle detection |
| Linked List Cycle II | Visited nodes / cycle entry |
| Palindrome Linked List | Middle + reverse + compare |
| Remove Nth Node | Fixed-gap two pointers |
| Reorder List | Middle + reverse + merge |
| Copy List with Random Pointer | HashMap object mapping |
| Sort List | Merge Sort + split + merge |
| Flatten Multilevel Doubly Linked List | DFS + pointer rewiring |
| Split Linked List in Parts | Length calculation + balanced partitioning |
| Next Greater Node In Linked List | ArrayList + monotonic stack |

---

## 19. Final Mental Model

Think of the stack as a collection of questions waiting for answers.

```text
A node enters the stack
        |
        v
Its next greater value is unknown
        |
        v
A larger value appears
        |
        v
Resolve every smaller value at the stack's top
        |
        v
Store the current value in those answer positions
```

The key takeaway is:

> Store indices of unresolved nodes. Whenever a strictly larger value appears, use it to resolve every smaller value at the top of the stack. This finds all next greater values in linear time.

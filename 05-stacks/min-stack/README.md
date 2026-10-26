# WIN #108 — 155. Min Stack

**LeetCode:** [155. Min Stack](https://leetcode.com/problems/min-stack/)  
**Difficulty:** Medium  
**Topics:** Stack, Design

---

## 1. Problem

Design a stack that supports the following operations, each in **O(1) time complexity**:

- `MinStack()` — Initializes the stack.
- `push(int value)` — Pushes an element onto the stack.
- `pop()` — Removes the top element.
- `top()` — Returns the top element.
- `getMin()` — Returns the minimum element currently in the stack.

The challenge is to retrieve the minimum element in constant time without traversing the entire stack.

### Example

**Operations:**

```text
push(-2)
push(0)
push(-3)
getMin()
pop()
top()
getMin()
```

**Output:**

```text
[null, null, null, -3, null, 0, -2]
```

**Explanation:**

1. Push `-2`. Stack: `[-2]`
2. Push `0`. Stack: `[-2, 0]`
3. Push `-3`. Stack: `[-2, 0, -3]`
4. `getMin()` returns `-3`.
5. `pop()` removes `-3`.
6. `top()` returns `0`.
7. `getMin()` returns `-2`.

When the minimum element is removed, the stack must still be able to identify the next minimum efficiently.

---

## 2. Core Idea

Use **two stacks** to maintain the elements and their minimum values.

- `stack` stores every element pushed into the MinStack.
- `minStack` keeps track of the minimum values.

The main idea is to maintain the minimum element at the top of `minStack`.

Whenever an element is pushed into `stack`, compare it with the current minimum:

- If `minStack` is empty, push the element into `minStack`.
- If the element is less than or equal to the current minimum, push it into `minStack`.
- Otherwise, leave `minStack` unchanged.

When popping an element from `stack`, check whether it equals the top of `minStack`. If they are equal, pop from `minStack` as well.

This ensures that `minStack.peek()` always returns the minimum element in the current stack.

**Important:** The comparison must use `>=` when deciding whether to push onto `minStack`. Duplicate minimum values must be tracked separately so that removing one occurrence does not lose the remaining minimum.

---

## 3. Java Solution

```java
class MinStack {

    private Stack<Integer> stack;
    private Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int value) {
        stack.push(value);

        if(!minStack.isEmpty() && minStack.peek() >= value){
            minStack.push(value);
        }else if(minStack.isEmpty()){
            minStack.push(value);
        }
    }

    public void pop() {

        int n = stack.pop();

        if(!minStack.isEmpty() && minStack.peek() == n){
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize two stacks

```java
private Stack<Integer> stack;
private Stack<Integer> minStack;
```

The first stack stores all the values.

The second stack stores the minimum values encountered so far, including repeated occurrences of a minimum.

The constructor initializes both:

```java
public MinStack() {
    stack = new Stack<>();
    minStack = new Stack<>();
}
```

Initially, both stacks are empty.

### Step 2: Implement `push()`

```java
public void push(int value) {
    stack.push(value);

    if(!minStack.isEmpty() && minStack.peek() >= value){
        minStack.push(value);
    }else if(minStack.isEmpty()){
        minStack.push(value);
    }
}
```

First, every value is pushed onto the main stack.

Then we determine whether it should also be pushed onto `minStack`.

**Case 1: `minStack` is empty.**

The first element is automatically the minimum, so push it onto both stacks.

**Case 2: The new value is less than or equal to the current minimum.**

Push it onto `minStack` because it becomes the new minimum or another occurrence of the existing minimum.

**Case 3: The new value is greater than the current minimum.**

Do not push it onto `minStack`. The current minimum remains unchanged.

For example, consider:

```text
push(-2)
push(0)
push(-3)
```

The stacks become:

```text
Main stack:       Minimum stack:

Top -> -3         Top -> -3
       0                  -2
      -2
```

The top of `minStack` gives us the current minimum, `-3`.

### Step 3: Implement `pop()`

```java
public void pop() {

    int n = stack.pop();

    if(!minStack.isEmpty() && minStack.peek() == n){
        minStack.pop();
    }
}
```

First, remove the top element from the main stack and store it in `n`.

Then compare the removed element with the top of `minStack`.

- If they are equal, remove the top of `minStack` too.
- Otherwise, leave `minStack` unchanged.

Why?

If the removed element was the current minimum, we must restore the previous minimum.

For example:

Before popping:

```text
Main stack:       Minimum stack:

Top -> -3         Top -> -3
       0                  -2
      -2
```

After `pop()`:

```text
Main stack:       Minimum stack:

Top -> 0          Top -> -2
       -2
```

The minimum is now `-2`, and it is available immediately at the top of `minStack`.

### Step 4: Implement `top()`

```java
public int top() {
    return stack.peek();
}
```

The `peek()` operation returns the top element without removing it.

Since the main stack stores every element, its top is the current top of the MinStack.

### Step 5: Implement `getMin()`

```java
public int getMin() {
    return minStack.peek();
}
```

The minimum value is always at the top of `minStack`.

We do not need to traverse the main stack or compare all its elements.

This operation takes **O(1) time**.

---

## 5. Dry Run

Consider these operations:

```text
push(-2)
push(0)
push(-3)
getMin()
pop()
top()
getMin()
```

| Operation | Main stack | Minimum stack | Result |
|---|---|---|---|
| `push(-2)` | `[-2]` | `[-2]` | — |
| `push(0)` | `[-2, 0]` | `[-2]` | — |
| `push(-3)` | `[-2, 0, -3]` | `[-2, -3]` | — |
| `getMin()` | `[-2, 0, -3]` | `[-2, -3]` | `-3` |
| `pop()` | `[-2, 0]` | `[-2]` | — |
| `top()` | `[-2, 0]` | `[-2]` | `0` |
| `getMin()` | `[-2, 0]` | `[-2]` | `-2` |

Stack contents are shown from bottom to top, so the rightmost element is the top.

The output matches the expected result.

### Important case: Duplicate minimum values

Consider:

```text
push(2)
push(1)
push(1)
pop()
getMin()
```

The stacks evolve as follows:

| Operation | Main stack | Minimum stack |
|---|---|---|
| `push(2)` | `[2]` | `[2]` |
| `push(1)` | `[2, 1]` | `[2, 1]` |
| `push(1)` | `[2, 1, 1]` | `[2, 1, 1]` |
| `pop()` | `[2, 1]` | `[2, 1]` |
| `getMin()` | `[2, 1]` | `[2, 1]` |

The minimum is still `1` after one occurrence is removed.

This is why the `push()` method uses:

```java
minStack.peek() >= value
```

Using `>=` ensures that duplicate minimum values are recorded separately.

---

## 6. Edge Cases

| Case | Operations | Expected behavior |
|---|---|---|
| Single element | `push(5)`, `getMin()` | Returns `5` |
| Descending values | `push(5)`, `push(3)`, `push(1)` | Minimum is `1` |
| Increasing values | `push(1)`, `push(3)`, `push(5)` | Minimum remains `1` |
| Duplicate minimum | `push(2)`, `push(1)`, `push(1)` | Minimum remains `1` after one `1` is popped |
| Remove the current minimum | `push(5)`, `push(2)`, `pop()` | Minimum becomes `5` |

The problem guarantees that `pop()`, `top()`, and `getMin()` are called only when the stack is non-empty, so additional empty-stack checks are unnecessary.

---

## 7. Complexity Analysis

Let `n` be the number of elements stored in the stack.

| Operation | Time Complexity |
|---|---|
| `push()` | `O(1)` |
| `pop()` | `O(1)` |
| `top()` | `O(1)` |
| `getMin()` | `O(1)` |

Each operation uses a constant number of stack operations.

**Space Complexity: `O(n)`**

In the worst case, every element is also stored in `minStack`, such as when the pushed values are in non-increasing order.

The two stacks together require linear space.

---

## 8. Pattern Recognition

This problem demonstrates **maintaining auxiliary state with a second stack**.

Recognize this pattern when:

- A data structure must support an additional query efficiently.
- Calculating the answer from all stored elements would be too slow.
- You need to preserve historical information when elements are removed.
- The auxiliary information can be updated during insertion and removal.

The second stack acts as a record of the minimum values that are currently relevant.

Instead of searching for the minimum every time `getMin()` is called, the answer is maintained as the stack changes.

---

## 9. Final Mental Model

Think of `stack` as the actual collection of values and `minStack` as a history of the minimum values.

- **Push:** Always push onto the main stack. Also push onto `minStack` if the value is less than or equal to its current minimum.
- **Pop:** Remove from the main stack. If the removed value equals the minimum stack's top, remove it there too.
- **Top:** Return the main stack's top.
- **Get minimum:** Return the minimum stack's top.

**Key takeaway:** A second stack stores the minimum values needed to answer minimum queries in `O(1)` time, even when the current minimum is removed.
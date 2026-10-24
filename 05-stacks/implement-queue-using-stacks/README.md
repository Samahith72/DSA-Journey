# WIN #106 — 232. Implement Queue using Stacks

**LeetCode:** [232. Implement Queue using Stacks](https://leetcode.com/problems/implement-queue-using-stacks/)  
**Difficulty:** Easy  
**Topics:** Stack, Design, Queue

---

## 1. Problem

Implement a **FIFO (First In, First Out) queue** using only two stacks.

A queue removes elements in the same order they were inserted, while a stack removes the most recently inserted element first.

Your `MyQueue` class must support four operations:

- `push(int x)` — Add an element to the back of the queue.
- `pop()` — Remove and return the front element.
- `peek()` — Return the front element without removing it.
- `empty()` — Return `true` if the queue is empty; otherwise, return `false`.

The implementation must use only standard stack operations.

### Example

**Operations:**

```text
["MyQueue", "push", "push", "peek", "pop", "empty"]
[[], [1], [2], [], [], []]
```

**Output:**

```text
[null, null, null, 1, 1, false]
```

**Explanation:**

1. Create an empty queue.
2. Push `1`. Queue: `[1]`
3. Push `2`. Queue: `[1, 2]`
4. Peek returns `1`.
5. Pop returns `1`. Queue: `[2]`
6. Empty returns `false`.

---

## 2. Core Idea

Use **two stacks to reverse the order of elements when necessary**.

- `inputStack` stores newly inserted elements.
- `outputStack` provides elements in queue order.

When we push elements `1`, `2`, and `3` into `inputStack`, the most recently inserted element, `3`, is at the top.

However, a queue must remove `1` first.

To achieve FIFO behavior, transfer all elements from `inputStack` to `outputStack`. This reverses their order, placing `1` at the top of `outputStack`.

The important optimization is that **we transfer elements only when `outputStack` is empty**.

If `outputStack` already contains elements, we use those first instead of transferring new elements immediately.

This gives us amortized `O(1)` time per operation.

---

## 3. Java Solution

```java
class MyQueue {

    private Stack<Integer> inputStack;
    private Stack<Integer> outputStack;

    public MyQueue() {
        inputStack = new Stack<>();
        outputStack = new Stack<>();
    }
    
    public void push(int x) {
        inputStack.push(x);
    }
    
    public int pop() {

        if(outputStack.isEmpty()){
            while(!inputStack.isEmpty()){
                outputStack.push(inputStack.pop());
            }
        }
        return outputStack.pop();
    }
    
    public int peek() {
        if(outputStack.isEmpty()){
            while(!inputStack.isEmpty()){
                outputStack.push(inputStack.pop());
            }
        }
        
        return outputStack.peek();
    }
    
    public boolean empty() {
        if(inputStack.isEmpty() && outputStack.isEmpty()){
            return true;
        }

        return false;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Create two stacks

```java
private Stack<Integer> inputStack;
private Stack<Integer> outputStack;
```

The two stacks have different responsibilities.

| Stack | Responsibility |
|---|---|
| `inputStack` | Stores incoming elements |
| `outputStack` | Provides the oldest element first |

The constructor initializes both stacks:

```java
public MyQueue() {
    inputStack = new Stack<>();
    outputStack = new Stack<>();
}
```

Initially, both stacks are empty.

### Step 2: Implement `push()`

```java
public void push(int x) {
    inputStack.push(x);
}
```

Every new element is pushed onto `inputStack`.

For example, after:

```text
push(1)
push(2)
push(3)
```

The stacks look like this:

```text
inputStack          outputStack

Top -> 3            Empty
       2
       1
```

We do not need to reverse anything during insertion. We simply store the new elements.

### Step 3: Implement `pop()`

```java
public int pop() {

    if(outputStack.isEmpty()){
        while(!inputStack.isEmpty()){
            outputStack.push(inputStack.pop());
        }
    }
    return outputStack.pop();
}
```

This method has two important stages.

**Stage A: Check `outputStack`.**

If it contains elements, its top is already the oldest element in the queue. We can remove that element directly.

If it is empty, we transfer every element from `inputStack` into `outputStack`.

**Stage B: Remove the front element.**

After the transfer, the oldest element is at the top of `outputStack`, so `outputStack.pop()` returns the correct queue element.

For example:

Before transfer:

```text
inputStack          outputStack

Top -> 3            Empty
       2
       1
```

After transfer:

```text
inputStack          outputStack

Empty               Top -> 1
                           2
                           3
```

Now `outputStack.pop()` returns `1`, which is the correct FIFO behavior.

### Step 4: Implement `peek()`

```java
public int peek() {
    if(outputStack.isEmpty()){
        while(!inputStack.isEmpty()){
            outputStack.push(inputStack.pop());
        }
    }
    
    return outputStack.peek();
}
```

The logic is almost identical to `pop()`.

The difference is:

- `pop()` removes the front element.
- `peek()` returns the front element without removing it.

If `outputStack` is empty, we transfer the elements first. Then `outputStack.peek()` returns the oldest element.

### Step 5: Implement `empty()`

```java
public boolean empty() {
    if(inputStack.isEmpty() && outputStack.isEmpty()){
        return true;
    }

    return false;
}
```

The queue is empty only when **both stacks are empty**.

For example:

| `inputStack` | `outputStack` | Queue empty? |
|---|---|---|
| Empty | Empty | `true` |
| Contains elements | Empty | `false` |
| Empty | Contains elements | `false` |
| Contains elements | Contains elements | `false` |

An element can be stored in either stack, so checking only one stack would be incorrect.

---

## 5. Dry Run

Consider these operations:

```text
push(1)
push(2)
peek()
pop()
push(3)
pop()
pop()
empty()
```

| Operation | `inputStack` | `outputStack` | Result |
|---|---|---|---|
| `push(1)` | `[1]` | `[]` | — |
| `push(2)` | `[1, 2]` | `[]` | — |
| `peek()` | `[]` | `[2, 1]` | `1` |
| `pop()` | `[]` | `[2]` | `1` |
| `push(3)` | `[3]` | `[2]` | — |
| `pop()` | `[3]` | `[]` | `2` |
| `pop()` | `[]` | `[3]` | `3` |
| `empty()` | `[]` | `[]` | `true` |

In the table, stack contents are shown from bottom to top, so the rightmost element is the top.

### Important observation

After `push(3)`, the new element remains in `inputStack`, while `outputStack` still contains `2`.

We do **not** transfer `3` immediately.

The next `pop()` removes `2` first. Only when `outputStack` becomes empty do we transfer `3`.

This is what makes the implementation efficient while preserving FIFO order.

---

## 6. Edge Cases

| Case | Expected behavior |
|---|---|
| Push one element, then pop | Returns that element |
| Push several elements | Removes them in insertion order |
| Call `peek()` repeatedly | Returns the same front element |
| Push new elements while `outputStack` has elements | Existing front elements remain first |
| Both stacks are empty | `empty()` returns `true` |

The problem guarantees that calls to `pop()` and `peek()` are valid, so we do not need additional checks for empty-stack removal.

---

## 7. Complexity Analysis

Let `n` be the number of elements currently stored in the queue.

| Operation | Worst-case time | Amortized time |
|---|---:|---:|
| `push()` | `O(1)` | `O(1)` |
| `pop()` | `O(n)` | `O(1)` |
| `peek()` | `O(n)` | `O(1)` |
| `empty()` | `O(1)` | `O(1)` |

### Why are `pop()` and `peek()` amortized `O(1)`?

A single `pop()` or `peek()` may transfer many elements, making that particular operation `O(n)`.

However, each element:

1. Is pushed into `inputStack` once.
2. Is transferred to `outputStack` at most once.
3. Is removed from `outputStack` at most once.

Therefore, across a sequence of `n` operations, the total work is `O(n)`.

The amortized time per operation is `O(1)`.

**Space Complexity:** `O(n)` because the two stacks together store the queue's elements.

---

## 8. Pattern Recognition

This problem demonstrates **implementing one data structure using another**.

Recognize this pattern when:

- You must reproduce queue behavior using stacks.
- The available data structure has a different ordering rule.
- Reversing the order of elements can bridge the behavioral difference.
- You need to optimize a sequence of operations rather than just one operation.

### Stack versus queue

| Property | Stack | Queue |
|---|---|---|
| Ordering | LIFO — Last In, First Out | FIFO — First In, First Out |
| Insert | Push onto top | Add to back |
| Remove | Pop from top | Remove from front |

The two-stack approach uses one reversal to transform LIFO ordering into FIFO ordering.

---

## 9. Stack Patterns Learned So Far

| Problem | Pattern learned |
|---|---|
| Valid Parentheses | Matching opening and closing symbols |
| Min Stack | Maintaining auxiliary state |
| Evaluate Reverse Polish Notation | Operand management and evaluation |
| Daily Temperatures | Next greater element using a monotonic stack |
| Next Greater Element | Next-greater-element pattern |
| Next Greater Node In Linked List | Monotonic stack with linked-list values |
| Remove All Adjacent Duplicates In String | Adjacent-element cancellation |
| Baseball Game | Stack-based history simulation |
| Make The String Great | Case-insensitive adjacent cancellation |
| Crawler Log Folder | Folder navigation and depth tracking |
| Final Prices With a Special Discount in a Shop | Next smaller or equal element |
| Implement Queue using Stacks | Two stacks, order reversal, amortized analysis |

---

## 10. Final Mental Model

Think of `inputStack` as the place where new elements arrive and `outputStack` as the place from which elements leave.

- **Push:** Always add to `inputStack`.
- **Pop:** If `outputStack` is empty, transfer everything from `inputStack`; then remove its top.
- **Peek:** If `outputStack` is empty, transfer everything; then inspect its top.
- **Empty:** Both stacks must be empty.

**Key takeaway:** Two stacks can implement a FIFO queue by reversing the insertion order only when the output stack needs elements. Each element moves between the stacks at most once, giving amortized `O(1)` time per operation.
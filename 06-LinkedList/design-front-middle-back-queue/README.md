# WIN #94 — 1670. Design Front Middle Back Queue

[LeetCode — 1670. Design Front Middle Back Queue](https://leetcode.com/problems/design-front-middle-back-queue/)

**Difficulty:** Medium  
**Topics:** Doubly Linked List, Design, Data Structures, Pointer Manipulation

---

## 1. Problem

Design a queue that supports insertion and deletion at the front, middle, and back.

Implement the following operations:

| Operation | Description |
|---|---|
| `pushFront(val)` | Insert a value at the front |
| `pushMiddle(val)` | Insert a value at the middle |
| `pushBack(val)` | Insert a value at the back |
| `popFront()` | Remove and return the front value |
| `popMiddle()` | Remove and return the middle value |
| `popBack()` | Remove and return the back value |

If a pop operation is called on an empty queue, return `-1`.

### Important Middle Rule

When there are two possible middle positions, use the **frontmost middle position**.

For example:

```text
Queue: [1, 2, 3, 4, 5]

pushMiddle(6)

Result: [1, 2, 6, 3, 4, 5]
```

For an even-sized queue:

```text
Queue: [1, 2, 3, 4, 5, 6]

popMiddle()

Returns: 3
Result:  [1, 2, 4, 5, 6]
```

---

## 2. Core Idea

Your solution implements the queue using a **custom doubly linked list**.

Each node stores:

```text
val
prev
next
```

The queue maintains three important pieces of information:

- `head` — the first node.
- `tail` — the last node.
- `size` — the number of nodes currently in the queue.

The structure looks like this:

```text
head                              tail
 ↓                                  ↓
[1] ⇄ [2] ⇄ [3] ⇄ [4] ⇄ [5]
```

Because every node has both `prev` and `next` pointers, you can insert or remove a node by updating its neighboring pointers.

Your implementation also includes:

```java
public Node getNode(int idx)
```

This helper traverses the list from `head` to find a node at a particular index.

The main idea is:

```text
Maintain a doubly linked list
          |
          v
Track head, tail and size
          |
          v
Use getNode() for middle operations
          |
          v
Rewire pointers to insert or remove nodes
```

---

## 3. Java Solution

```java
class FrontMiddleBackQueue {

    class Node {
        int val;
        Node next;
        Node prev;

        Node(int val) {
            this.val = val;
        }

        Node(int val, Node next, Node prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }

    Node head;
    Node tail;
    int size;

    public FrontMiddleBackQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    public Node getNode(int idx) {
        Node current = head;

        for (int i = 0; i < idx; i++) {
            current = current.next;
        }

        return current;
    }

    public void pushFront(int val) {
        Node node = new Node(val);

        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }

        size++;
    }

    public void pushMiddle(int val) {
        Node node = new Node(val);

        int idx = size / 2;

        if (idx == 0) {
            pushFront(val);
            return;
        }

        Node current = getNode(idx);
        Node previous = current.prev;

        previous.next = node;
        node.prev = previous;
        node.next = current;
        current.prev = node;

        size++;
    }

    public void pushBack(int val) {
        Node node = new Node(val);

        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }

        size++;
    }

    public int popFront() {
        if (head == null) {
            return -1;
        }

        int value = head.val;
        head = head.next;
        size--;

        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }

        return value;
    }

    public int popMiddle() {
        if (head == null) {
            return -1;
        }

        int idx = (size - 1) / 2;
        Node current = getNode(idx);
        int value = current.val;

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }

        size--;
        return value;
    }

    public int popBack() {
        if (tail == null) {
            return -1;
        }

        int value = tail.val;
        tail = tail.prev;

        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }

        size--;
        return value;
    }
}
```

---

## 4. Step 1 — Design the Node

```java
class Node {
    int val;
    Node next;
    Node prev;

    Node(int val) {
        this.val = val;
    }
}
```

Every node stores its value and references to its neighboring nodes.

For example:

```text
null ← [10] ⇄ [20] ⇄ [30] → null
```

For the node containing `20`:

```text
20.prev → 10
20.next → 30
```

This makes it possible to move in both directions.

That is especially useful for removing the last node or reconnecting the nodes around a middle element.

---

## 5. Step 2 — Maintain `head`, `tail` and `size`

```java
Node head;
Node tail;
int size;
```

These variables describe the entire queue.

### `head`

Points to the first node.

```text
head
 ↓
[10] ⇄ [20] ⇄ [30]
```

### `tail`

Points to the last node.

```text
[10] ⇄ [20] ⇄ [30]
                  ↑
                 tail
```

### `size`

Stores the current number of nodes.

For example:

```text
Queue: [10, 20, 30]

size = 3
```

Keeping `size` updated is essential because both middle operations calculate their positions using it.

---

## 6. Step 3 — The `getNode()` Helper

```java
public Node getNode(int idx) {
    Node current = head;

    for (int i = 0; i < idx; i++) {
        current = current.next;
    }

    return current;
}
```

This method returns the node at a zero-based index.

For example:

```text
Index:  0    1    2    3
Queue: [10] [20] [30] [40]
```

Calling:

```java
getNode(2);
```

returns the node containing `30`.

The method starts at `head` and follows `next` pointers until it reaches the requested index.

**Complexity:** `O(n)` in the worst case.

This helper is what makes your middle operations straightforward, although it also makes them linear-time operations.

---

## 7. Step 4 — `pushFront(int val)`

```java
public void pushFront(int val) {
    Node node = new Node(val);

    if (head == null) {
        head = tail = node;
    } else {
        node.next = head;
        head.prev = node;
        head = node;
    }

    size++;
}
```

There are two cases.

### Case 1: Queue is empty

```text
head = null
tail = null
```

After inserting `10`:

```text
head, tail
     |
    [10]
```

Both `head` and `tail` point to the new node.

### Case 2: Queue is not empty

Suppose:

```text
[20] ⇄ [30]
```

Insert `10` at the front.

First:

```java
node.next = head;
```

Then:

```java
head.prev = node;
```

Finally:

```java
head = node;
```

Result:

```text
head
 ↓
[10] ⇄ [20] ⇄ [30]
                ↑
               tail
```

The `size` is incremented once.

**Time Complexity:** `O(1)`.

---

## 8. Step 5 — `pushMiddle(int val)`

This operation inserts a node at the middle.

```java
int idx = size / 2;
```

This formula determines the insertion index.

### Example 1: Odd-sized queue

```text
Queue: [1, 2, 3, 4, 5]
size = 5

idx = 5 / 2 = 2
```

Index `2` contains `3`.

Inserting `6` at index `2` produces:

```text
[1, 2, 6, 3, 4, 5]
```

### Example 2: Even-sized queue

```text
Queue: [1, 2, 3, 4]
size = 4

idx = 4 / 2 = 2
```

Insert `6` at index `2`:

```text
[1, 2, 6, 3, 4]
```

This follows the problem's middle-insertion rule.

### Why handle `idx == 0`?

```java
if (idx == 0) {
    pushFront(val);
    return;
}
```

When the queue has zero or one element, the calculated insertion index is `0`.

In this case, inserting at the middle is equivalent to inserting at the front.

Calling `pushFront()` also correctly updates `head`, `tail`, and `size` for these small cases.

### Rewire the pointers

```java
Node current = getNode(idx);
Node previous = current.prev;
```

Suppose:

```text
[1] ⇄ [2] ⇄ [3] ⇄ [4]
```

We want to insert `6` before `3`.

The relevant nodes are:

```text
previous = 2
current  = 3
```

Your code performs:

```java
previous.next = node;
node.prev = previous;
node.next = current;
current.prev = node;
```

The resulting list is:

```text
[1] ⇄ [2] ⇄ [6] ⇄ [3] ⇄ [4]
```

Finally, increment `size`.

**Time Complexity:** `O(n)` because `getNode()` traverses the list.

---

## 9. Step 6 — `pushBack(int val)`

```java
public void pushBack(int val) {
    Node node = new Node(val);

    if (head == null) {
        head = tail = node;
    } else {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    size++;
}
```

If the queue is empty, the new node becomes both `head` and `tail`.

Otherwise, connect the new node after the existing tail.

For example:

```text
Before:

[1] ⇄ [2]

Insert 3:

[1] ⇄ [2] ⇄ [3]
```

The important assignments are:

```java
tail.next = node;
node.prev = tail;
tail = node;
```

Because the queue tracks its tail, it does not need to traverse the list.

**Time Complexity:** `O(1)`.

---

## 10. Step 7 — `popFront()`

```java
public int popFront() {
    if (head == null) {
        return -1;
    }

    int value = head.val;
    head = head.next;
    size--;

    if (head == null) {
        tail = null;
    } else {
        head.prev = null;
    }

    return value;
}
```

First, check whether the queue is empty.

If it is empty, return `-1`.

Otherwise, save the front value and advance `head`.

For example:

```text
Before:

head
 ↓
[1] ⇄ [2] ⇄ [3]
```

After:

```text
head
 ↓
[2] ⇄ [3]
```

The new head must not retain a backward pointer to the removed node:

```java
head.prev = null;
```

If the removed node was the only node, both `head` and `tail` must become `null`.

**Time Complexity:** `O(1)`.

---

## 11. Step 8 — `popMiddle()`

This operation removes the frontmost middle node.

```java
int idx = (size - 1) / 2;
Node current = getNode(idx);
```

The formula is important:

```text
(size - 1) / 2
```

It selects the correct index for both odd and even sizes.

| Queue size | Selected index | Example queue | Removed value |
|---:|---:|---|---:|
| 1 | 0 | `[5]` | 5 |
| 2 | 0 | `[5, 8]` | 5 |
| 3 | 1 | `[5, 8, 9]` | 8 |
| 4 | 1 | `[5, 8, 9, 12]` | 8 |
| 5 | 2 | `[5, 8, 9, 12, 15]` | 9 |
| 6 | 2 | `[5, 8, 9, 12, 15, 20]` | 9 |

For an even-sized queue, this selects the left of the two middle nodes.

### Save the value

```java
int value = current.val;
```

We need to return the removed node's value.

### Connect the previous node to the next node

```java
if (current.prev != null) {
    current.prev.next = current.next;
} else {
    head = current.next;
}
```

If a previous node exists, connect it to the next node.

Otherwise, the removed node was the head, so update `head`.

### Repair the backward pointer

```java
if (current.next != null) {
    current.next.prev = current.prev;
} else {
    tail = current.prev;
}
```

If a next node exists, update its `prev` pointer.

Otherwise, update `tail`.

Finally:

```java
size--;
return value;
```

For example:

```text
Before:

[1] ⇄ [2] ⇄ [3] ⇄ [4] ⇄ [5] ⇄ [6]

popMiddle() returns 3

After:

[1] ⇄ [2] ⇄ [4] ⇄ [5] ⇄ [6]
```

**Time Complexity:** `O(n)` because locating the middle uses `getNode()`.

---

## 12. Step 9 — `popBack()`

```java
public int popBack() {
    if (tail == null) {
        return -1;
    }

    int value = tail.val;
    tail = tail.prev;

    if (tail == null) {
        head = null;
    } else {
        tail.next = null;
    }

    size--;
    return value;
}
```

Because `tail` points directly to the last node, we can remove it without traversing the list.

For example:

```text
Before:

[1] ⇄ [2] ⇄ [3]
                ↑
               tail
```

Move `tail` backward:

```text
tail = tail.prev;
```

Then disconnect the new tail from the removed node:

```java
tail.next = null;
```

Result:

```text
[1] ⇄ [2]
```

If the queue had only one node, `tail` becomes `null`, so `head` must also become `null`.

**Time Complexity:** `O(1)`.

---

## 13. Complexity Analysis

Your implementation uses a doubly linked list, but middle operations require traversal through `getNode()`.

| Operation | Time Complexity |
|---|---|
| `pushFront()` | `O(1)` |
| `pushMiddle()` | `O(n)` |
| `pushBack()` | `O(1)` |
| `popFront()` | `O(1)` |
| `popMiddle()` | `O(n)` |
| `popBack()` | `O(1)` |
| `getNode()` | `O(n)` |

**Space Complexity:** `O(n)` for storing the queue's nodes.

The queue uses additional pointer and size variables in `O(1)` space.

---

## 14. Important Design Observation

Your solution is correct in its overall approach, and the pointer updates handle the empty, single-node, front, and back cases.

However, the middle operations are not constant-time because they call `getNode()`.

This is acceptable for the stated constraint of at most 1,000 method calls, but it is an important design trade-off.

A more optimized design uses **two doubly linked lists**:

```text
Left half | Right half
```

Maintain the left half with either the same number of nodes as the right half or one extra node.

For example:

```text
Left:  [1, 2, 3]
Right: [4, 5, 6]
```

The middle boundary can be maintained directly, avoiding a full traversal for every middle operation.

The trade-off is that balancing the two lists after each operation requires careful pointer management.

---

## 15. Pattern Recognition

When designing a data structure that supports insertion and removal at both ends, think:

```text
Doubly Linked List
```

When it must also support frequent middle operations, ask:

```text
Can I maintain the middle boundary directly?
```

The two main ideas are:

1. **Doubly linked list:** efficient insertion and deletion at known nodes.
2. **Middle tracking:** avoids traversing from the head every time a middle operation occurs.

Your current implementation uses the first idea and locates the middle through traversal.

---

## 16. Linked List Patterns Learned So Far

| Problem | Pattern |
|---|---|
| Reverse Linked List | Three-pointer reversal |
| Middle of Linked List | Fast and slow pointers |
| Linked List Cycle | Floyd's cycle detection |
| Linked List Cycle II | Visited nodes / cycle entry |
| Reorder List | Middle + reverse + merge |
| Copy List with Random Pointer | HashMap object mapping |
| Sort List | Merge Sort + split + merge |
| Flatten Multilevel Doubly Linked List | DFS + pointer rewiring |
| Split Linked List in Parts | Length calculation + balanced partitioning |
| Next Greater Node In Linked List | ArrayList + monotonic stack |
| Design Front Middle Back Queue | Doubly linked list + middle indexing |

---

## 17. Final Mental Model

Your implementation revolves around three pieces of state:

```text
head
tail
size
```

For front and back operations, update the relevant endpoint directly.

For middle operations:

```text
Calculate middle index
        |
        v
Find node using getNode()
        |
        v
Reconnect neighboring nodes
        |
        v
Update size
```

The most important pointer rule is:

> Whenever you insert or remove a node from a doubly linked list, update both `next` and `prev` connections, and make sure `head`, `tail`, and `size` remain consistent.

# WIN #98 — 146. LRU Cache

**LeetCode:** [146. LRU Cache](https://leetcode.com/problems/lru-cache/)  
**Difficulty:** Medium  
**Topics:** Hash Table, Linked List, Design, Doubly Linked List

---

## 1. Problem

Design a data structure that follows the **Least Recently Used (LRU)** cache policy.

Implement the `LRUCache` class with the following operations:

- `LRUCache(int capacity)`: Initialize the cache with a fixed positive capacity.
- `get(int key)`: Return the value associated with the key if it exists; otherwise, return `-1`. A successful access marks the key as recently used.
- `put(int key, int value)`: Insert a new key-value pair or update an existing key. If the cache exceeds its capacity, remove the least recently used entry.

Both `get()` and `put()` must run in **O(1) average time complexity**.

### Example

**Input:**

```text
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
```

**Output:**

```text
[null, null, null, 1, null, -1, null, -1, 3, 4]
```

The cache has a capacity of `2`.

1. `put(1, 1)` — Insert key `1`.
2. `put(2, 2)` — Insert key `2`.
3. `get(1)` — Return `1` and mark key `1` as recently used.
4. `put(3, 3)` — Evict key `2`, the least recently used entry.
5. `get(2)` — Return `-1` because key `2` was evicted.
6. `put(4, 4)` — Evict key `1`.
7. `get(1)` — Return `-1`.
8. `get(3)` — Return `3`.
9. `get(4)` — Return `4`.

---

## 2. Core Idea

The challenge is to support two operations efficiently:

- Find a key and retrieve its value in O(1) average time.
- Update the usage order and remove the least recently used entry in O(1) time.

A **HashMap + Doubly Linked List** solves this problem.

### HashMap

```java
HashMap<Integer, Node> map;
```

The map stores each key and a reference to its corresponding linked-list node.

This allows us to find a node in O(1) average time without traversing the list.

### Doubly linked list

Each node stores:

```java
int key;
int val;
Node prev;
Node next;
```

The list maintains usage order:

```text
Most Recently Used                  Least Recently Used
        |                                      |
        v                                      v
head <-> [Key A] <-> [Key B] <-> [Key C] <-> tail
```

In this implementation:

- The node immediately after `head` is the most recently used entry.
- The node immediately before `tail` is the least recently used entry.
- `head` and `tail` are dummy nodes that simplify insertion and removal.

Every successful `get()` and every `put()` that inserts or updates an entry moves that entry to the front.

When the cache is full and a new key is inserted, the node immediately before `tail` is evicted.

---

## 3. Java Solution

```java
class LRUCache {

    class Node {
        int val;
        int key;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.val = val;
            this.key = key;
        }
    }

    private Node head;
    private Node tail;
    private int capacity;
    private HashMap<Integer, Node> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);

        if (node == null) {
            return -1;
        }

        int value = node.val;
        moveNode(node);

        return value;
    }

    private void moveNode(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;

        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    public void put(int key, int value) {

        // Case 1: Key already exists
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            moveNode(node);
            return;
        }

        Node node = new Node(key, value);

        // Case 2: Space is available
        if (map.size() < capacity) {
            moveAhead(node);
            map.put(key, node);
        }
        // Case 3: Cache is full
        else {
            int k = tail.prev.key;
            map.remove(k);
            removeTail(tail.prev);

            moveAhead(node);
            map.put(key, node);
        }
    }

    private void moveAhead(Node node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    private void removeTail(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Define the node

```java
class Node {
    int val;
    int key;
    Node next;
    Node prev;
}
```

Each node stores both the key and its value.

Storing the key is essential because when an entry is evicted, we must remove its key from the HashMap as well as remove its node from the linked list.

The `prev` and `next` references allow us to remove or reposition a node without traversing the list.

### Step 2: Initialize the cache

```java
head = new Node(0, 0);
tail = new Node(0, 0);

head.next = tail;
tail.prev = head;
```

The two dummy nodes represent the boundaries of the list.

Initially:

```text
head <-> tail
```

When a real node is inserted, it is placed between `head` and the existing first node.

The dummy nodes eliminate special handling for inserting or removing the first and last real nodes.

### Step 3: Implement `get()`

```java
public int get(int key) {
    Node node = map.get(key);

    if (node == null) {
        return -1;
    }

    int value = node.val;
    moveNode(node);

    return value;
}
```

The HashMap provides direct access to the node.

There are two possibilities:

- **Key not found:** Return `-1`. The cache remains unchanged.
- **Key found:** Save its value, move the node to the front, and return the value.

Why move the node?

Because accessing an entry makes it the most recently used entry. The cache must record this change so that a later eviction removes the correct key.

### Step 4: Understand `moveNode()`

```java
private void moveNode(Node node) {
    node.next.prev = node.prev;
    node.prev.next = node.next;

    node.next = head.next;
    node.prev = head;

    head.next.prev = node;
    head.next = node;
}
```

This method moves an existing node to the front.

It performs two operations.

**First, detach the node from its current position:**

```java
node.next.prev = node.prev;
node.prev.next = node.next;
```

The node's previous and next neighbors are connected directly to each other.

**Second, insert the node immediately after `head`:**

```java
node.next = head.next;
node.prev = head;

head.next.prev = node;
head.next = node;
```

The node becomes the most recently used entry.

Only a constant number of pointer updates are needed.

### Step 5: Handle an existing key in `put()`

```java
if (map.containsKey(key)) {
    Node node = map.get(key);
    node.val = value;
    moveNode(node);
    return;
}
```

If the key already exists, do not create another node.

Instead:

1. Retrieve the existing node.
2. Update its value.
3. Move it to the front.
4. Return.

Updating an existing key counts as recent use.

### Step 6: Insert a new key when space is available

```java
if (map.size() < capacity) {
    moveAhead(node);
    map.put(key, node);
}
```

When the cache has unused capacity, insert the new node at the front and record it in the HashMap.

The new entry becomes the most recently used entry.

### Step 7: Evict the least recently used entry

```java
else {
    int k = tail.prev.key;
    map.remove(k);
    removeTail(tail.prev);

    moveAhead(node);
    map.put(key, node);
}
```

When the cache is full, the least recently used node is `tail.prev`.

The method:

1. Retrieves the key of that node.
2. Removes the key from the HashMap.
3. Removes the node from the doubly linked list.
4. Inserts the new node at the front.
5. Adds the new key-node mapping to the HashMap.

Both data structures must be updated together. Otherwise, the HashMap could contain a reference to an evicted node or the linked list could contain a node that is no longer in the map.

### Step 8: Understand `moveAhead()` and `removeTail()`

`moveAhead()` inserts a new node immediately after `head`:

```java
private void moveAhead(Node node) {
    node.prev = head;
    node.next = head.next;

    head.next.prev = node;
    head.next = node;
}
```

`removeTail()` removes a specified node by reconnecting its neighbors:

```java
private void removeTail(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
}
```

Neither method needs to traverse the list. Each changes a constant number of pointers.

---

## 5. Dry Run

Let the cache capacity be `2`.

### Operation 1: `put(1, 1)`

The cache is empty, so insert key `1`.

```text
MRU -> [1:1] <- LRU
```

### Operation 2: `put(2, 2)`

Insert key `2` at the front.

```text
MRU -> [2:2] <-> [1:1] <- LRU
```

Key `2` is now the most recently used entry. Key `1` is the least recently used entry.

### Operation 3: `get(1)`

Key `1` exists, so return `1` and move it to the front.

```text
MRU -> [1:1] <-> [2:2] <- LRU
```

Key `2` is now the least recently used entry.

### Operation 4: `put(3, 3)`

The cache is full. Evict key `2` and insert key `3`.

```text
MRU -> [3:3] <-> [1:1] <- LRU
```

### Operation 5: `get(2)`

Key `2` was evicted.

```text
Return: -1
```

The cache remains unchanged.

### Operation 6: `put(4, 4)`

The cache is full. Key `1` is now the least recently used entry, so evict it and insert key `4`.

```text
MRU -> [4:4] <-> [3:3] <- LRU
```

### Remaining operations

| Operation | Result | Cache order, MRU to LRU |
|---|---:|---|
| `get(1)` | `-1` | `4, 3` |
| `get(3)` | `3` | `3, 4` |
| `get(4)` | `4` | `4, 3` |

The returned values match the expected output.

---

## 6. Edge Cases

| Case | Expected behavior |
|---|---|
| Cache is empty | `get(key)` returns `-1`. |
| Capacity is `1` | Inserting a different key evicts the previous key. |
| Existing key is updated | Its value changes, and it becomes most recently used. |
| Existing key is accessed | Its node moves to the front. |
| New key is inserted into a full cache | The node immediately before `tail` is evicted. |
| Missing key is accessed | Return `-1` without changing usage order. |

---

## 7. Complexity Analysis

### Time Complexity

**`get(key)`: O(1) average**

- HashMap lookup takes O(1) average time.
- Moving a node requires a constant number of pointer updates.

**`put(key, value)`: O(1) average**

- Checking or inserting a key in the HashMap takes O(1) average time.
- Inserting, detaching, or removing a node takes O(1) time.
- Eviction does not require traversing the list because `tail.prev` identifies the least recently used entry.

### Auxiliary Space Complexity: O(capacity)

The HashMap and doubly linked list each store at most `capacity` real entries. Therefore, the overall storage used by the cache is O(capacity).

---

## 8. Pattern Recognition

This problem demonstrates the **HashMap + Doubly Linked List design pattern**.

Use this combination when a problem requires:

- Fast lookup by key.
- Constant-time removal of a known node.
- Constant-time insertion at a chosen end.
- Maintaining an order that changes as elements are accessed.

### Why not use only a HashMap?

A HashMap provides fast key lookup but does not maintain the required least-recently-used order.

### Why not use only a linked list?

A linked list can maintain usage order, but finding an arbitrary key would require O(n) traversal.

### Why a doubly linked list instead of a singly linked list?

A doubly linked list lets us remove a known node directly because it has both `prev` and `next` references. A singly linked list would generally require finding the predecessor before removing an arbitrary node.

### Why use dummy head and tail nodes?

Dummy nodes simplify boundary operations and avoid special cases when the list contains zero or one real node.

---

## 9. Linked List Patterns Learned So Far

| Problem | Pattern |
|---|---|
| Reverse Linked List | Three-pointer reversal |
| Middle of Linked List | Fast and slow pointers |
| Linked List Cycle | Floyd's cycle detection |
| Linked List Cycle II | Visited nodes and cycle entry |
| Palindrome Linked List | Find middle, reverse, and compare |
| Remove Nth Node From End | Fixed-gap two pointers |
| Reorder List | Find middle, reverse, and merge |
| Odd Even Linked List | Multiple pointer chains |
| Rotate List | Circular connection and split |
| Partition List | Separate chains |
| Copy List with Random Pointer | HashMap object mapping |
| Sort List | Merge sort and pointer splitting |
| Flatten a Multilevel Doubly Linked List | DFS and pointer rewiring |
| Split Linked List in Parts | Length calculation and balanced partitioning |
| Next Greater Node In Linked List | Monotonic stack |
| Design Front Middle Back Queue | Doubly linked list and middle indexing |
| Delete the Middle Node | Length calculation and predecessor rewiring |
| Merge In Between Linked Lists | Segment replacement and boundary-pointer rewiring |
| Merge k Sorted Lists | Min-heap and k-way merge |
| LRU Cache | HashMap + doubly linked list + recency tracking |

---

## 10. Final Mental Model

Think of the doubly linked list as a usage timeline:

```text
head -> Most Recently Used -> ... -> Least Recently Used <- tail
```

The HashMap tells you where a key is. The linked list tells you how recently it was used.

For `get()`:

```text
Find key -> Return value -> Move node to front
```

For `put()`:

```text
Key exists?
    Yes -> Update value -> Move node to front
    No  -> Cache full?
              Yes -> Evict tail.prev
              No  -> Use available space
          Insert new node at front
```

**Key takeaway:** A HashMap provides fast access, while a doubly linked list maintains recency order. Together, they support average O(1) `get()` and `put()` operations.
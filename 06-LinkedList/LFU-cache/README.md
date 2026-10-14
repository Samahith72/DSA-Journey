# WIN #99 — 460. LFU Cache

**LeetCode:** [460. LFU Cache](https://leetcode.com/problems/lfu-cache/)  
**Difficulty:** Hard  
**Topics:** Hash Table, Linked List, Doubly Linked List, Design

---

## 1. Problem

Design and implement a **Least Frequently Used (LFU) Cache**.

The cache supports two operations:

- `get(int key)`: Return the value associated with the key if it exists; otherwise, return `-1`.
- `put(int key, int value)`: Insert a new key-value pair or update an existing key. If the cache is full, evict the least frequently used key before inserting a new one.

Each key has a frequency counter:

- A newly inserted key starts with frequency `1`.
- A successful `get()` increments the key's frequency.
- A `put()` on an existing key updates its value and increments its frequency.
- If multiple keys share the lowest frequency, evict the **least recently used** key among them.

Both `get()` and `put()` must run in **O(1) average time complexity**.

### Example

**Input:**

```text
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
```

**Output:**

```text
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]
```

The cache capacity is `2`.

1. Insert keys `1` and `2`. Both have frequency `1`.
2. Access key `1`. Its frequency becomes `2`.
3. Insert key `3`. Key `2` is evicted because its frequency is lower.
4. Access key `3`. Its frequency becomes `2`.
5. Insert key `4`. Keys `1` and `3` both have frequency `2`, so key `1` is evicted because it is less recently used.
6. The remaining operations return `-1`, `3`, and `4`, respectively.

---

## 2. Core Idea

An LFU cache is more complicated than an LRU cache because it must track two things:

1. **Frequency:** How often each key has been accessed.
2. **Recency:** Which key was used least recently among keys with the same frequency.

To achieve O(1) average time for both operations, the implementation combines two HashMaps with doubly linked lists.

### A. `nodeMap`: Find a node by key

```java
private HashMap<Integer, Node> nodeMap;
```

Maps each key to its node.

This allows the cache to retrieve a node directly without searching through a linked list.

### B. `freqMap`: Group nodes by frequency

```java
private HashMap<Integer, DoublyLinkedList> freqMap;
```

Each frequency maps to a doubly linked list containing all nodes with that frequency.

Within each list:

- The front contains the most recently used node.
- The back contains the least recently used node.

Therefore, if eviction is required, the least recently used node among the minimum-frequency entries can be removed from the back of the corresponding list.

### C. `minFreq`: Track the lowest frequency

```java
private int minFreq;
```

This variable stores the minimum frequency among all keys currently in the cache.

It allows the algorithm to locate the correct eviction list immediately instead of searching all frequency groups.

### Overall structure

```text
nodeMap:
key 1 -> Node(key=1, val=1, freq=2)
key 3 -> Node(key=3, val=3, freq=2)

freqMap:
frequency 1 -> DoublyLinkedList of frequency-1 nodes
frequency 2 -> [Most recent] <-> [...] <-> [Least recent]

minFreq = 2
```

The exact contents change after each operation. The important idea is that every node is stored in the list matching its current frequency.

---

## 3. Java Solution

```java
class LFUCache {

    class Node {
        int key;
        int val;
        int freq;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }

    class DoublyLinkedList {
        Node head;
        Node tail;
        int size;

        public DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);

            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        public void addFirst(Node node) {
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;

            size++;
        }

        public Node removeLast() {
            if (size == 0) {
                return null;
            }

            Node node = tail.prev;
            remove(node);
            return node;
        }

        public void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
    }

    private int capacity;
    private HashMap<Integer, Node> nodeMap;
    private HashMap<Integer, DoublyLinkedList> freqMap;
    private int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;

        nodeMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        Node node = nodeMap.get(key);

        if (node == null) {
            return -1;
        }

        int value = node.val;
        updateFrequency(node);
        return value;
    }

    private void updateFrequency(Node node) {
        int oldFreq = node.freq;
        DoublyLinkedList oldList = freqMap.get(oldFreq);
        oldList.remove(node);

        if (oldList.size == 0 && minFreq == oldFreq) {
            minFreq++;
        }

        node.freq++;

        freqMap.putIfAbsent(node.freq, new DoublyLinkedList());
        freqMap.get(node.freq).addFirst(node);
    }

    public void put(int key, int value) {
        // Case 1: Key already exists
        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            node.val = value;
            updateFrequency(node);
            return;
        }

        Node node = new Node(key, value);

        // Case 2: Space available
        if (nodeMap.size() < capacity) {
            newKey(node);
        }
        // Case 3: Cache full
        else {
            Node removed = freqMap.get(minFreq).removeLast();
            int removedKey = removed.key;
            nodeMap.remove(removedKey);
            newKey(node);
        }
    }

    private void newKey(Node node) {
        nodeMap.put(node.key, node);
        freqMap.putIfAbsent(1, new DoublyLinkedList());
        freqMap.get(1).addFirst(node);
        minFreq = 1;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Understand the `Node` class

```java
class Node {
    int key;
    int val;
    int freq;
    Node next;
    Node prev;
}
```

Every node stores:

- `key`: Needed to remove the entry from `nodeMap` during eviction.
- `val`: The value returned by `get()`.
- `freq`: The number of accesses counted for this entry.
- `next` and `prev`: References used to maintain the doubly linked list.

The constructor initializes the frequency to `1`, as required for a newly inserted key.

### Step 2: Understand `DoublyLinkedList`

Each frequency has its own list.

```java
public void addFirst(Node node) {
    node.next = head.next;
    node.prev = head;

    head.next.prev = node;
    head.next = node;

    size++;
}
```

`addFirst()` inserts a node at the front, marking it as the most recently used node within that frequency group.

```java
public void remove(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
    size--;
}
```

`remove()` detaches a known node using its previous and next references.

```java
public Node removeLast() {
    if (size == 0) {
        return null;
    }

    Node node = tail.prev;
    remove(node);
    return node;
}
```

`removeLast()` removes the least recently used node from that frequency group.

All three operations take O(1) time.

### Step 3: Initialize the cache

```java
nodeMap = new HashMap<>();
freqMap = new HashMap<>();
minFreq = 0;
```

Initially, the cache contains no keys.

- `nodeMap` has no entries.
- `freqMap` has no frequency groups.
- `minFreq` is `0` because no real key has been inserted yet.

When the first key is inserted, its frequency is `1`, and `minFreq` becomes `1`.

### Step 4: Implement `get()`

```java
public int get(int key) {
    Node node = nodeMap.get(key);

    if (node == null) {
        return -1;
    }

    int value = node.val;
    updateFrequency(node);
    return value;
}
```

First, use `nodeMap` to find the node.

If the key does not exist, return `-1`. A failed access does not change frequency or recency.

If the key exists:

1. Save its value.
2. Call `updateFrequency(node)`.
3. Return the saved value.

A successful `get()` increases the frequency and makes the node the most recently used entry in its new frequency group.

### Step 5: Understand `updateFrequency()`

This is the most important method in the implementation.

```java
int oldFreq = node.freq;
DoublyLinkedList oldList = freqMap.get(oldFreq);
oldList.remove(node);
```

First, record the node's current frequency and remove it from that frequency's list.

For example, if the node's frequency is `2`, it must leave the frequency-2 list.

#### Update `minFreq` when necessary

```java
if (oldList.size == 0 && minFreq == oldFreq) {
    minFreq++;
}
```

If the old list becomes empty and its frequency was the minimum, increment `minFreq`.

Why?

The node is about to move to the next frequency. If it was the last node in the minimum-frequency group, that old frequency no longer exists among the cache's entries.

For this implementation, every successful access increases a frequency by exactly one. Consequently, if the minimum-frequency group becomes empty, the next minimum frequency is `oldFreq + 1`.

#### Increase the frequency

```java
node.freq++;
```

The node's frequency increases by one.

#### Insert into the new frequency group

```java
freqMap.putIfAbsent(node.freq, new DoublyLinkedList());
freqMap.get(node.freq).addFirst(node);
```

Create the new frequency list if necessary, then insert the node at its front.

This makes the node the most recently used entry among nodes with the new frequency.

### Step 6: Handle an existing key in `put()`

```java
if (nodeMap.containsKey(key)) {
    Node node = nodeMap.get(key);
    node.val = value;
    updateFrequency(node);
    return;
}
```

If the key already exists, the implementation:

1. Retrieves the node.
2. Updates its value.
3. Increments its frequency through `updateFrequency()`.
4. Returns without creating a duplicate node.

This is important: a `put()` operation on an existing key counts as a use and must update both frequency and recency.

### Step 7: Insert a new key

```java
private void newKey(Node node) {
    nodeMap.put(node.key, node);
    freqMap.putIfAbsent(1, new DoublyLinkedList());
    freqMap.get(1).addFirst(node);
    minFreq = 1;
}
```

A new key always starts with frequency `1`.

The method:

1. Adds the key-to-node mapping.
2. Creates the frequency-1 list if needed.
3. Inserts the node at the front of that list.
4. Sets `minFreq` to `1`.

The final assignment is essential because a newly inserted entry has frequency `1`, the lowest possible frequency for a non-empty cache.

### Step 8: Evict the correct key when full

```java
Node removed = freqMap.get(minFreq).removeLast();
int removedKey = removed.key;
nodeMap.remove(removedKey);
newKey(node);
```

When the cache is full, the implementation uses `minFreq` to select the lowest-frequency group.

Within that group, `removeLast()` selects the least recently used node.

The key is removed from `nodeMap`, and the new key is inserted with frequency `1`.

Both the frequency-based selection and the recency-based tie-breaker happen in O(1) time.

---

## 5. Dry Run

Let the capacity be `2`.

### Operation 1: `put(1, 1)`

Insert key `1`.

```text
Frequency 1: [1]
minFreq = 1
```

### Operation 2: `put(2, 2)`

Insert key `2` at the front of the frequency-1 list.

```text
Frequency 1: [2, 1]
             MRU  LRU

minFreq = 1
```

Both keys have frequency `1`. Key `2` is more recently used.

### Operation 3: `get(1)`

Return `1`.

Key `1` moves from frequency `1` to frequency `2`.

```text
Frequency 1: [2]
Frequency 2: [1]

minFreq = 1
```

Key `2` is now the only key with the lowest frequency.

### Operation 4: `put(3, 3)`

The cache is full.

The minimum frequency is `1`, so key `2` is evicted. Key `3` is inserted with frequency `1`.

```text
Frequency 1: [3]
Frequency 2: [1]

minFreq = 1
```

### Operation 5: `get(2)`

Key `2` no longer exists.

```text
Return: -1
```

The cache remains unchanged.

### Operation 6: `get(3)`

Return `3`. Its frequency increases from `1` to `2`.

```text
Frequency 2: [3, 1]
             MRU  LRU

minFreq = 2
```

Both keys have frequency `2`, but key `3` is more recently used.

### Operation 7: `put(4, 4)`

The cache is full, and both entries have frequency `2`.

The least recently used entry in that group is key `1`, so it is evicted.

Key `4` is inserted with frequency `1`.

```text
Frequency 1: [4]
Frequency 2: [3]

minFreq = 1
```

### Remaining operations

| Operation | Result | Cache state |
|---|---:|---|
| `get(1)` | `-1` | Key `1` was evicted |
| `get(3)` | `3` | Key `3` moves to frequency `3` |
| `get(4)` | `4` | Key `4` moves to frequency `2` |

The resulting output matches the expected example.

---

## 6. Edge Cases

| Case | Expected behavior |
|---|---|
| Capacity is `1` | The only entry is evicted when a different key is inserted. |
| Key does not exist | `get()` returns `-1` without changing cache state. |
| Existing key is updated | Its value changes and its frequency increases. |
| Several keys have equal frequencies | Evict the least recently used key within the minimum-frequency list. |
| Minimum-frequency list becomes empty | Update `minFreq` when its current group becomes empty. |
| New key is inserted after eviction | Its frequency starts at `1`, and `minFreq` resets to `1`. |

---

## 7. Complexity Analysis

Let `C` be the cache capacity.

### Time Complexity: O(1) average

**`get(key)`**

- HashMap lookup: O(1) average.
- Remove the node from its current frequency list: O(1).
- Increment its frequency and insert it into another list: O(1).

**`put(key, value)`**

- Existing key: update its value and frequency in O(1) average.
- New key with available space: insert into the maps and frequency-1 list in O(1) average.
- New key when full: evict from the minimum-frequency list and insert the new node in O(1) average.

No operation requires traversing a linked list or searching all frequency groups.

### Auxiliary Space Complexity: O(C)

The cache stores at most `C` real nodes.

- `nodeMap` stores at most `C` key-node mappings.
- `freqMap` stores the frequency groups and their linked lists.
- Each real node belongs to exactly one frequency list at a time.

Therefore, the total cache storage is O(C).

---

## 8. Pattern Recognition

This problem demonstrates the **frequency buckets + doubly linked list + HashMap** pattern.

Use this design when a system needs:

- Constant-time access by key.
- A changing priority or frequency for each entry.
- Constant-time removal of a known entry.
- A tie-breaking rule based on recency.
- Constant-time identification of the minimum-priority group.

### LRU versus LFU

| Feature | LRU Cache | LFU Cache |
|---|---|---|
| Eviction criterion | Least recently used overall | Lowest frequency first |
| Tie-breaking | Not required | Least recently used within the minimum frequency |
| Main data structures | HashMap + doubly linked list | HashMap + frequency map + doubly linked lists |
| Usage tracking | Recency order | Frequency and recency |
| `get()` / `put()` | O(1) average | O(1) average |

The key difference is that LRU maintains one global recency list, whereas LFU maintains multiple recency lists grouped by frequency.

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
| LFU Cache | Frequency buckets + doubly linked lists + minimum-frequency tracking |

---

## 10. Final Mental Model

An LFU cache answers three questions:

1. **Where is the key?** Use `nodeMap`.
2. **Which keys have a particular frequency?** Use `freqMap`.
3. **Which frequency should be evicted first?** Use `minFreq`.

Within each frequency group, the doubly linked list determines which entry is least recently used.

```text
get(key):
    Find node
    If missing, return -1
    Otherwise, increase frequency and move node to new group

put(key, value):
    If key exists:
        Update value and increase frequency
    Otherwise:
        If full, evict LRU node from minFreq group
        Insert new node into frequency 1
        Set minFreq = 1
```

**Key takeaway:** LFU eviction requires both frequency tracking and recency tracking. HashMaps provide direct lookup, doubly linked lists provide constant-time ordering changes, and `minFreq` identifies the group from which to evict. Together, these structures achieve O(1) average `get()` and `put()` operations.
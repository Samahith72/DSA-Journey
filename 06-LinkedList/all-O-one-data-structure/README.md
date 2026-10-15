# WIN #100 — 432. All O`one Data Structure

**LeetCode:** [432. All O`one Data Structure](https://leetcode.com/problems/all-oone-data-structure/)  
**Difficulty:** Hard  
**Topics:** Hash Table, Linked List, Doubly Linked List, Design

---

## 1. Problem

Design a data structure that stores strings and their counts while supporting efficient access to the strings with the minimum and maximum counts.

Implement the `AllOne` class with the following operations:

- `AllOne()`: Initialize the data structure.
- `inc(String key)`: Increment the count of a string by `1`. If the key does not exist, insert it with count `1`.
- `dec(String key)`: Decrement the count of a string by `1`. If its count becomes `0`, remove it.
- `getMaxKey()`: Return any string with the maximum count. Return `""` if the structure is empty.
- `getMinKey()`: Return any string with the minimum count. Return `""` if the structure is empty.

Every operation must run in **O(1) average time complexity**.

### Example

**Input:**

```text
["AllOne", "inc", "inc", "getMaxKey", "getMinKey", "inc", "getMaxKey", "getMinKey"]
[[], ["hello"], ["hello"], [], [], ["leet"], [], []]
```

**Output:**

```text
[null, null, null, "hello", "hello", null, "hello", "leet"]
```

Explanation:

1. `inc("hello")` — `hello` has count `1`.
2. `inc("hello")` — `hello` has count `2`.
3. `getMaxKey()` — Returns `"hello"`.
4. `getMinKey()` — Returns `"hello"`.
5. `inc("leet")` — `leet` has count `1`.
6. `getMaxKey()` — Returns `"hello"`.
7. `getMinKey()` — Returns `"leet"`.

---

## 2. Core Idea

The main challenge is maintaining string counts while finding the minimum and maximum counts without scanning all strings.

The solution combines a **HashMap, a doubly linked list, and a HashSet inside each list node**.

### A. HashMap: Find a string's frequency node

```java
private HashMap<String, Node> map;
```

The map stores each string and a reference to the node representing its current frequency.

For example:

```text
hello -> Node(freq = 2)
leet  -> Node(freq = 1)
```

This lets us locate a string's current frequency group in O(1) average time.

### B. Doubly linked list: Keep frequency groups ordered

Each `Node` represents one frequency and stores all strings having that frequency.

```java
class Node {
    int freq;
    HashSet<String> set;
    Node next;
    Node prev;
}
```

The nodes are arranged in increasing frequency order.

```text
head -> [freq 1] <-> [freq 2] <-> [freq 3] <- tail
```

- `head.next` points to the minimum-frequency group.
- `tail.prev` points to the maximum-frequency group.
- Empty frequency groups are removed.

Because counts change by exactly one per operation, a string only needs to move to an adjacent frequency group or to a newly created adjacent group.

### C. HashSet: Store strings within each frequency group

```java
HashSet<String> set;
```

Each frequency node contains a set of strings with that count.

For example:

```text
[freq = 2, set = {"hello", "world"}]
```

The set allows a string to be added to or removed from a frequency group in O(1) average time.

### Overall structure

```text
map:
hello -> frequency 2
leet  -> frequency 1

Doubly linked list:
head -> [1: {leet}] <-> [2: {hello}] <- tail
```

The minimum and maximum keys can be retrieved directly from the first and last real nodes.

---

## 3. Java Solution

```java
class AllOne {

    class Node {
        int freq;
        HashSet<String> set;
        Node next;
        Node prev;

        public Node(int freq) {
            this.freq = freq;
            this.set = new HashSet<>();
        }
    }

    private HashMap<String, Node> map;
    private Node head;
    private Node tail;

    public AllOne() {
        map = new HashMap<>();

        head = new Node(0);
        tail = new Node(0);

        head.next = tail;
        tail.prev = head;
    }

    private void addFirst(Node node) {
        head.next.prev = node;
        node.next = head.next;

        head.next = node;
        node.prev = head;
    }

    public void inc(String key) {
        // Case 1: New key
        if (!map.containsKey(key)) {
            Node firstNode = head.next;
            Node node;

            if (firstNode == tail || firstNode.freq > 1) {
                node = new Node(1);
                addFirst(node);
            } else {
                node = firstNode;
            }

            node.set.add(key);
            map.put(key, node);
            return;
        }

        // Case 2: Existing key
        Node current = map.get(key);
        int newFreq = current.freq + 1;
        Node target;

        if (current.next != tail && current.next.freq == newFreq) {
            target = current.next;
        } else {
            target = new Node(newFreq);
            addAfter(current, target);
        }

        current.set.remove(key);
        target.set.add(key);
        map.put(key, target);

        if (current.set.isEmpty()) {
            removeNode(current);
        }
    }

    private void addAfter(Node prevNode, Node node) {
        prevNode.next.prev = node;
        node.next = prevNode.next;

        prevNode.next = node;
        node.prev = prevNode;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void dec(String key) {
        Node node = map.get(key);

        if (node == null) {
            return;
        }

        Node target;
        int newFreq = node.freq - 1;

        if (newFreq < 1) {
            node.set.remove(key);
            map.remove(key);

            if (node.set.isEmpty()) {
                removeNode(node);
            }
            return;
        }

        if (node.prev.freq == newFreq) {
            target = node.prev;
        } else {
            target = new Node(newFreq);
            addBefore(node, target);
        }

        node.set.remove(key);
        target.set.add(key);
        map.put(key, target);

        if (node.set.isEmpty()) {
            removeNode(node);
        }
    }

    private void addBefore(Node current, Node target) {
        current.prev.next = target;
        target.prev = current.prev;

        current.prev = target;
        target.next = current;
    }

    public String getMaxKey() {
        if (tail.prev != head) {
            return tail.prev.set.iterator().next();
        } else {
            return "";
        }
    }

    public String getMinKey() {
        if (head.next == tail) {
            return "";
        }

        return head.next.set.iterator().next();
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the linked list

```java
head = new Node(0);
tail = new Node(0);

head.next = tail;
tail.prev = head;
```

The dummy nodes simplify insertion and removal.

Initially:

```text
head <-> tail
```

When the first string is inserted, a frequency-1 node is added between them.

### Step 2: Handle a new key in `inc()`

```java
if (!map.containsKey(key)) {
    Node firstNode = head.next;
    Node node;

    if (firstNode == tail || firstNode.freq > 1) {
        node = new Node(1);
        addFirst(node);
    } else {
        node = firstNode;
    }

    node.set.add(key);
    map.put(key, node);
    return;
}
```

A new key always starts with frequency `1`.

There are two possibilities:

- The list is empty, so create a frequency-1 node.
- The first node has frequency greater than `1`, so insert a frequency-1 node at the front.
- Otherwise, reuse the existing frequency-1 node.

Then add the string to the node's set and record its location in the map.

### Step 3: Increment an existing key

```java
Node current = map.get(key);
int newFreq = current.freq + 1;
```

The map immediately locates the string's current frequency node.

The target frequency is one greater than the current frequency.

For example:

```text
Current frequency: 2
New frequency:     3
```

The string must move from the frequency-2 group to the frequency-3 group.

### Step 4: Find or create the next frequency group

```java
if (current.next != tail && current.next.freq == newFreq) {
    target = current.next;
} else {
    target = new Node(newFreq);
    addAfter(current, target);
}
```

Because the list is sorted, the next frequency group is either immediately after the current node or does not yet exist.

If it exists, reuse it. Otherwise, create a new group directly after the current node.

This avoids traversing the entire list.

### Step 5: Move the key to the new group

```java
current.set.remove(key);
target.set.add(key);
map.put(key, target);
```

The string is removed from its old frequency set and added to its new frequency set. The map is updated to point to the new node.

If the old group becomes empty, remove it:

```java
if (current.set.isEmpty()) {
    removeNode(current);
}
```

This ensures that the list contains only non-empty frequency groups.

### Step 6: Handle `dec()`

```java
Node node = map.get(key);
int newFreq = node.freq - 1;
```

The problem guarantees that the key exists before `dec()` is called.

There are two cases.

**Case A: The new frequency would be zero**

```java
if (newFreq < 1) {
    node.set.remove(key);
    map.remove(key);

    if (node.set.isEmpty()) {
        removeNode(node);
    }
    return;
}
```

The string is removed from its set and from the map. If its frequency group becomes empty, that group is removed from the linked list.

**Case B: The new frequency is at least one**

```java
if (node.prev.freq == newFreq) {
    target = node.prev;
} else {
    target = new Node(newFreq);
    addBefore(node, target);
}
```

Because the frequency decreases by exactly one, the target group can only be immediately before the current group.

If the group already exists, reuse it. Otherwise, insert a new group immediately before the current node.

Then move the key and update its map reference:

```java
node.set.remove(key);
target.set.add(key);
map.put(key, target);
```

Finally, remove the old frequency group if it becomes empty.

### Step 7: Retrieve the maximum key

```java
public String getMaxKey() {
    if (tail.prev != head) {
        return tail.prev.set.iterator().next();
    } else {
        return "";
    }
}
```

The list is ordered by increasing frequency, so the final real node contains the maximum frequency.

The method returns any string from that node's set.

### Step 8: Retrieve the minimum key

```java
public String getMinKey() {
    if (head.next == tail) {
        return "";
    }

    return head.next.set.iterator().next();
}
```

The first real node contains the minimum frequency.

If the list is empty, return an empty string.

---

## 5. Dry Run

Consider these operations:

```text
inc("hello")
inc("hello")
getMaxKey()
getMinKey()
inc("leet")
getMaxKey()
getMinKey()
```

### Operation 1: `inc("hello")`

`hello` is new, so create frequency `1`.

```text
head -> [freq 1: {hello}] -> tail
```

### Operation 2: `inc("hello")`

The frequency increases from `1` to `2`.

The frequency-2 group does not exist, so create it after frequency `1`.

Move `hello` into the new group. The old frequency-1 group becomes empty and is removed.

```text
head -> [freq 2: {hello}] -> tail
```

### Operation 3: `getMaxKey()`

The last real node has frequency `2`.

```text
Returns: "hello"
```

### Operation 4: `getMinKey()`

The first real node also has frequency `2`.

```text
Returns: "hello"
```

### Operation 5: `inc("leet")`

`leet` is new, so it enters the frequency-1 group at the front.

```text
head -> [freq 1: {leet}] <-> [freq 2: {hello}] -> tail
```

### Operation 6: `getMaxKey()`

The last real node has frequency `2`.

```text
Returns: "hello"
```

### Operation 7: `getMinKey()`

The first real node has frequency `1`.

```text
Returns: "leet"
```

The outputs match the example.

---

## 6. Edge Cases

| Case | Expected behavior |
|---|---|
| First call is `inc(key)` | Create the frequency-1 group and insert the key. |
| Increment an existing key | Move it to the next frequency group. |
| Decrement a key with frequency `1` | Remove the key entirely. |
| Decrement when the target frequency group does not exist | Create the group immediately before the current group. |
| A frequency group becomes empty | Remove the group from the linked list. |
| All keys are removed | `getMinKey()` and `getMaxKey()` return `""`. |
| Multiple keys share the same frequency | Return any one of those keys. |

---

## 7. Complexity Analysis

Let `n` be the number of distinct keys currently stored.

### Time Complexity: O(1) average

- `inc(key)`: HashMap lookup, set updates, and adjacent linked-list operations take O(1) average time.
- `dec(key)`: HashMap lookup, set updates, and adjacent linked-list operations take O(1) average time.
- `getMaxKey()`: Access the last real node and retrieve one set element in O(1) average time.
- `getMinKey()`: Access the first real node and retrieve one set element in O(1) average time.

No operation traverses all frequency groups or all keys.

### Auxiliary Space Complexity: O(n)

Each distinct key is stored in the map and in exactly one frequency group's set. There are at most `n` non-empty frequency groups because every group contains at least one key.

Therefore, the data structure uses O(n) space.

---

## 8. Pattern Recognition

This problem demonstrates the **ordered frequency buckets + HashMap + HashSet** pattern.

Use this pattern when you need to:

- Track a count for each key.
- Increment or decrement counts by one.
- Find a key with the minimum or maximum count.
- Avoid scanning every key after each update.

The crucial observation is that frequency changes by exactly one. Therefore, a key only needs to move to an adjacent group, allowing the ordered list to remain correct without a full search.

### Comparison with LRU and LFU Cache

| Data structure | What is tracked? | Main technique |
|---|---|---|
| LRU Cache | Recent usage order | HashMap + doubly linked list |
| LFU Cache | Access frequency and recency within a frequency | HashMaps + frequency-grouped doubly linked lists |
| All O`one Data Structure | String counts and minimum/maximum count | HashMap + ordered frequency list + HashSets |

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
| All O`one Data Structure | Ordered frequency groups + HashMap + HashSet |

---

## 10. Final Mental Model

Think of the linked list as a line of frequency groups arranged from smallest to largest:

```text
head -> [freq 1] <-> [freq 2] <-> [freq 3] -> tail
```

Each group contains every string with that frequency.

When a string's count changes:

```text
inc(key):
    Find its current group
    Move it to frequency + 1
    Remove the old group if empty

dec(key):
    Find its current group
    Remove the key if its new count is zero
    Otherwise, move it to frequency - 1
    Remove the old group if empty
```

For queries:

```text
getMinKey() -> Take a key from head.next
getMaxKey() -> Take a key from tail.prev
```

**Key takeaway:** Keep frequency groups sorted in a doubly linked list and map every string directly to its current group. Since each update changes a frequency by exactly one, only adjacent groups need to be checked or created, allowing every operation to run in O(1) average time.
# WIN #92 — 725. Split Linked List in Parts

[LeetCode — 725. Split Linked List in Parts](https://leetcode.com/problems/split-linked-list-in-parts/)

**Difficulty:** Medium  
**Topic:** Linked List, Array, Pointer Manipulation

---

## 1. Problem

Given the head of a singly linked list and an integer `k`, split the linked list into `k` consecutive parts.

The parts should be as equal in size as possible, following these rules:

- The length of any two parts should not differ by more than one.
- Earlier parts must be at least as large as later parts.
- If there are fewer nodes than `k`, some parts will be `null`.
- The original list must be split into separate lists, not merely referenced as overlapping sections.

Return an array containing the heads of all `k` parts.

### Example 1

```text
Input: head = [1,2,3], k = 5

Output: [[1],[2],[3],[],[]]
```

### Example 2

```text
Input: head = [1,2,3,4,5,6,7,8,9,10], k = 3

Output: [[1,2,3,4],[5,6,7],[8,9,10]]
```

---

## 2. Core Idea

Your solution follows a simple three-step approach:

1. Count the total number of nodes.
2. Calculate the size of each part.
3. Traverse the list, assign each part's head, and disconnect the parts.

The key formulas are:

```java
int baseSize = n / k;
int extra = n % k;
```

Here:

- `n` is the total number of nodes.
- `baseSize` is the minimum number of nodes each part should receive.
- `extra` is the number of additional nodes that must be distributed.

The first `extra` parts receive one additional node.

For example, if there are `10` nodes and `3` parts:

```text
baseSize = 10 / 3 = 3
extra    = 10 % 3 = 1
```

So the part sizes are:

```text
Part 1: 4 nodes
Part 2: 3 nodes
Part 3: 3 nodes
```

---

## 3. Java Solution

```java
public class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {
    public ListNode[] splitListToParts(ListNode head, int k) {
        int n = 0;
        ListNode current = head;

        while (current != null) {
            n++;
            current = current.next;
        }

        int baseSize = n / k;
        int extra = n % k;
        current = head;

        ListNode[] parts = new ListNode[k];

        for (int i = 0; i < k; i++) {
            parts[i] = current;
            int partSize = baseSize;

            if (i < extra) {
                partSize++;
            }

            for (int j = 1; j < partSize; j++) {
                current = current.next;
            }

            if (current != null) {
                ListNode nextNode = current.next;
                current.next = null;
                current = nextNode;
            }
        }

        return parts;
    }
}
```

---

## 4. Step 1 — Count the Nodes

```java
int n = 0;
ListNode current = head;

while (current != null) {
    n++;
    current = current.next;
}
```

We traverse the entire linked list to calculate its length.

For example:

```text
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 → 10
```

After traversal:

```text
n = 10
```

We need the total length before we can determine how to distribute the nodes evenly.

---

## 5. Step 2 — Calculate the Part Sizes

```java
int baseSize = n / k;
int extra = n % k;
```

Suppose:

```text
n = 10
k = 3
```

Then:

```text
baseSize = 3
extra = 1
```

Every part starts with a size of `3`.

Since `extra = 1`, the first part receives one additional node.

The result is:

```text
Part 1: 4 nodes
Part 2: 3 nodes
Part 3: 3 nodes
```

### Why use `i < extra`?

```java
if (i < extra) {
    partSize++;
}
```

For `extra = 1`:

- `i = 0`: condition is true, so Part 1 gets an extra node.
- `i = 1`: condition is false.
- `i = 2`: condition is false.

This ensures that larger parts come first.

---

## 6. Step 3 — Create the Result Array

```java
ListNode[] parts = new ListNode[k];
```

We need exactly `k` entries in the result.

Each entry stores the head of one part.

For example:

```text
parts[0] → head of Part 1
parts[1] → head of Part 2
parts[2] → head of Part 3
```

If a part has no nodes, its array entry remains `null`.

---

## 7. Step 4 — Assign the Head of Each Part

```java
for (int i = 0; i < k; i++) {
    parts[i] = current;
```

At the beginning of each iteration, `current` points to the first node that belongs to the next part.

We save it in the result array.

For example:

```text
current
   ↓
1 → 2 → 3 → 4 → 5 → 6
```

When `i = 0`:

```java
parts[0] = current;
```

So:

```text
parts[0] → 1
```

The first part starts at node `1`.

---

## 8. Step 5 — Determine the Current Part's Size

```java
int partSize = baseSize;

if (i < extra) {
    partSize++;
}
```

This calculates the exact number of nodes to include in the current part.

For `n = 10` and `k = 3`:

| Part index | Base size | Extra node? | Final size |
|---|---:|---|---:|
| 0 | 3 | Yes | 4 |
| 1 | 3 | No | 3 |
| 2 | 3 | No | 3 |

Notice that the first part has four nodes, while the remaining parts have three.

---

## 9. Step 6 — Move to the Last Node of the Part

```java
for (int j = 1; j < partSize; j++) {
    current = current.next;
}
```

This is an important loop to understand.

We start at the first node of the part.

If a part needs `4` nodes, we must move forward only `3` times to reach its fourth node.

For example:

```text
1 → 2 → 3 → 4 → 5 → 6
↑           ↑
Start       Last node of Part 1
```

The movement is:

```text
j = 1: current moves to 2
j = 2: current moves to 3
j = 3: current moves to 4
```

Now `current` points to the last node of the current part.

### Why start `j` at 1?

Because the starting node already counts as the first node.

Therefore, for a part of size `partSize`, we need only `partSize - 1` movements.

---

## 10. Step 7 — Disconnect the Part

```java
if (current != null) {
    ListNode nextNode = current.next;
    current.next = null;
    current = nextNode;
}
```

This is the most important pointer manipulation in your solution.

Suppose the list is:

```text
1 → 2 → 3 → 4 → 5 → 6
```

After finding the end of the first part:

```text
current → 4
```

We save the next node:

```java
ListNode nextNode = current.next;
```

So:

```text
nextNode → 5
```

Then we disconnect the first part:

```java
current.next = null;
```

Now:

```text
Part 1:
1 → 2 → 3 → 4 → null

Remaining list:
5 → 6
```

Finally:

```java
current = nextNode;
```

We continue processing from node `5`.

### Why save `nextNode` first?

If we did this:

```java
current.next = null;
current = current.next;
```

then `current` would become `null`, and we would lose access to the remaining list.

Always save the next pointer before breaking a connection.

---

## 11. Complete Dry Run

Consider:

```text
head = [1,2,3,4,5,6,7,8,9,10]
k = 3
```

The calculations are:

```text
n = 10
baseSize = 10 / 3 = 3
extra = 10 % 3 = 1
```

### Iteration 1 — Part 1

```text
i = 0
partSize = 4
```

Take four nodes:

```text
1 → 2 → 3 → 4
```

Disconnect after node `4`.

```text
parts[0] = 1 → 2 → 3 → 4 → null
```

Remaining list:

```text
5 → 6 → 7 → 8 → 9 → 10
```

### Iteration 2 — Part 2

```text
i = 1
partSize = 3
```

Take three nodes:

```text
5 → 6 → 7
```

Disconnect after node `7`.

```text
parts[1] = 5 → 6 → 7 → null
```

Remaining list:

```text
8 → 9 → 10
```

### Iteration 3 — Part 3

```text
i = 2
partSize = 3
```

Take three nodes:

```text
8 → 9 → 10
```

Disconnect after node `10`.

```text
parts[2] = 8 → 9 → 10 → null
```

Final result:

```text
[
  [1,2,3,4],
  [5,6,7],
  [8,9,10]
]
```

---

## 12. What Happens When `n < k`?

Suppose:

```text
head = [1,2,3]
k = 5
```

Then:

```text
baseSize = 3 / 5 = 0
extra = 3 % 5 = 3
```

The part sizes become:

```text
Part 1: 1 node
Part 2: 1 node
Part 3: 1 node
Part 4: 0 nodes
Part 5: 0 nodes
```

The output is:

```text
[[1],[2],[3],[],[]]
```

In your code, when `partSize` is `0`, the inner loop does not execute.

The `current` pointer is not advanced by that loop. Once the actual nodes have been consumed, the remaining array entries are assigned `null`.

This is why your implementation correctly handles `n < k`.

---

## 13. Edge Cases

### Empty list

```text
head = null
k = 3
```

Output:

```text
[null, null, null]
```

### One node and multiple parts

```text
head = [7]
k = 3
```

Output:

```text
[[7], [], []]
```

### Even division

```text
head = [1,2,3,4,5,6]
k = 3
```

Output:

```text
[[1,2],[3,4],[5,6]]
```

### Uneven division

```text
head = [1,2,3,4,5]
k = 2
```

Output:

```text
[[1,2,3],[4,5]]
```

The first part receives the extra node.

---

## 14. Complexity

Let `n` be the number of nodes and `k` the number of parts.

### Time Complexity

```text
O(n + k)
```

- Counting the nodes takes `O(n)`.
- Splitting the list processes each node once, taking `O(n)`.
- The outer loop executes `k` times, even if some parts are empty.

Therefore, the total complexity is `O(n + k)`.

### Auxiliary Space Complexity

```text
O(1)
```

The algorithm uses only a constant number of pointer and integer variables beyond the required output array.

The result array itself requires:

```text
O(k)
```

space.

---

## 15. Pattern Recognition

When you see a problem that asks you to split a linked list into `k` nearly equal parts, think:

```text
Count → Distribute → Traverse → Disconnect
```

The general pattern is:

1. Find the total length.
2. Use division to calculate the base size.
3. Use the remainder to distribute extra nodes.
4. Find the end of each part.
5. Set its last node's `next` pointer to `null`.
6. Continue from the saved next node.

The important formulas are:

```java
int baseSize = n / k;
int extra = n % k;
```

---

## 16. Linked List Patterns Learned So Far

| Problem | Pattern |
|---|---|
| Reverse Linked List | Three-pointer reversal |
| Middle of Linked List | Fast and slow pointers |
| Linked List Cycle | Floyd's cycle detection |
| Linked List Cycle II | Visited nodes / cycle entry |
| Palindrome Linked List | Middle + reverse + compare |
| Remove Nth Node | Fixed-gap two pointers |
| Swap Nodes | Fixed-gap two pointers |
| Reorder List | Middle + reverse + merge |
| Odd Even Linked List | Multiple pointer chains |
| Rotate List | Circular connection + split |
| Partition List | Separate chains |
| Copy List with Random Pointer | HashMap object mapping |
| Sort List | Merge Sort + split + merge |
| Flatten Multilevel Doubly Linked List | DFS + pointer rewiring |
| Split Linked List in Parts | Length calculation + balanced partitioning |

---

## 17. Final Mental Model

Remember these three ideas:

```text
1. Count all nodes
       ↓
2. Calculate the part sizes
       ↓
3. Find each part's end and disconnect it
```

The size calculation is:

```text
baseSize = n / k
extra = n % k
```

The first `extra` parts receive one additional node.

The pointer operation that actually separates each part is:

```java
ListNode nextNode = current.next;
current.next = null;
current = nextNode;
```

The key takeaway is:

> Split a linked list into balanced parts by distributing the remainder to the earliest parts, then disconnect each part at its final node while preserving access to the remaining list.
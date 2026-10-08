# WIN #88 — 142. Linked List Cycle II

[LeetCode — 142. Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/)

**Difficulty:** Medium
**Topic:** Linked List, HashSet, Two Pointers, Floyd's Cycle Detection

---

## 1. Problem

Given the `head` of a linked list, return the **node where the cycle begins**.

If there is no cycle, return `null`.

A cycle exists when continuously following the `next` pointer eventually brings us back to a node that we have already visited.

The linked list must **not be modified**.

### Example 1

```text
Input:  head = [3,2,0,-4], pos = 1
Output: node at index 1
```

The last node points back to the node containing `2`.

```text
3 → 2 → 0 → -4
    ↑         ↓
    └─────────┘
```

### Example 2

```text
Input:  head = [1,2], pos = 0
Output: node at index 0
```

```text
1 → 2
↑   ↓
└───┘
```

### Example 3

```text
Input:  head = [1], pos = -1
Output: null
```

There is no cycle.

---

# 2. Core Idea

The important part of this problem is that we don't just need to determine whether a cycle exists.

We need to return the **exact node where the cycle starts**.

My approach uses a `HashSet<ListNode>`.

The idea is:

1. Start from `head`.
2. Keep moving through the linked list.
3. Store every node we visit in a `HashSet`.
4. Before moving forward, check whether the current node is already present in the set.
5. If it is already present, we have reached the beginning of the cycle.
6. Return that node.
7. If we reach `null`, there is no cycle.

The important detail is that we store the **node reference itself**, not its value.

---

# 3. Java Solution

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {

        if(head == null || head.next == null){
            return null;
        }

        ListNode current = head;
        HashSet<ListNode> set = new HashSet<>();

        while(current != null && current.next != null){
            if(set.contains(current)){
                return current;
            }else{
                set.add(current);
                current = current.next;
            }
        }
        
        return null;
    }
}
```

---

# 4. Step-by-Step Explanation

## Step 1: Handle very small lists

```java
if(head == null || head.next == null){
    return null;
}
```

If:

```text
head = null
```

there is no node.

If:

```text
head → 1 → null
```

there cannot be a cycle.

So we immediately return `null`.

---

## Step 2: Start from the head

```java
ListNode current = head;
```

`current` is used to traverse the linked list.

For example:

```text
3 → 2 → 0 → -4
```

Initially:

```text
current
   ↓
   3 → 2 → 0 → -4
```

---

## Step 3: Create a HashSet

```java
HashSet<ListNode> set = new HashSet<>();
```

The set stores all the nodes that we have already visited.

For example, after visiting:

```text
3 → 2 → 0
```

the set contains references to:

```text
3
2
0
```

This allows us to detect when we encounter the same node again.

---

# 5. Traversing the List

```java
while(current != null && current.next != null){
```

We continue while the current node and its next node exist.

Inside the loop:

```java
if(set.contains(current)){
    return current;
}
```

If the current node is already in the set, we have encountered a previously visited node.

That means there is a cycle.

The current node is exactly the node where the cycle begins.

---

# 6. Why Do We Return `current`?

Consider:

```text
3 → 2 → 0 → -4
    ↑         |
    └─────────┘
```

Traversal:

```text
current = 3
```

Set:

```text
{3}
```

Then:

```text
current = 2
```

Set:

```text
{3, 2}
```

Then:

```text
current = 0
```

Set:

```text
{3, 2, 0}
```

Then:

```text
current = -4
```

Set:

```text
{3, 2, 0, -4}
```

`-4.next` points back to `2`.

So:

```text
current = 2
```

But `2` is already in the set.

Therefore:

```java
set.contains(current)
```

is `true`.

So:

```java
return current;
```

returns the node containing `2`.

---

# 7. Why Store Nodes Instead of Values?

This is extremely important.

Suppose we have:

```text
1 → 2 → 3 → 2
```

The value `2` appearing twice does not necessarily mean there is a cycle.

Two different nodes can contain the same value.

For example:

```text
Node A: val = 5
Node B: val = 5
```

These are different nodes.

Therefore we need to compare **node references**, not values.

```java
HashSet<ListNode>
```

stores references to the actual `ListNode` objects.

So:

```java
set.contains(current)
```

asks:

> Have I already visited this exact node?

That is exactly what we need.

---

# 8. Adding the Current Node

If the node has not been visited:

```java
set.add(current);
```

Then move forward:

```java
current = current.next;
```

So every iteration follows this pattern:

```text
Check
  ↓
Already visited?
  ↓
Yes → cycle found → return node
  ↓
No
  ↓
Store node
  ↓
Move to next node
```

---

# 9. No Cycle Case

Consider:

```text
1 → 2 → 3 → null
```

Traversal:

```text
current = 1
```

Add `1`.

```text
current = 2
```

Add `2`.

```text
current = 3
```

Add `3`.

Then:

```text
current = null
```

The loop ends.

We return:

```java
return null;
```

So there is no cycle.

---

# 10. Visual Understanding

For:

```text
3 → 2 → 0 → -4
    ↑         |
    └─────────┘
```

The visited nodes are:

```text
Step 1:

current
   ↓
   3 → 2 → 0 → -4

set = {3}
```

```text
Step 2:

3 → current → 2 → 0 → -4

set = {3, 2}
```

```text
Step 3:

3 → 2 → current → 0 → -4

set = {3, 2, 0}
```

```text
Step 4:

3 → 2 → 0 → current → -4

set = {3, 2, 0, -4}
```

Next:

```text
current = 2
```

But:

```text
2 ∈ set
```

Therefore:

```text
Cycle begins at node 2
```

---

# 11. Edge Cases

### Empty list

```text
head = null
```

Return:

```text
null
```

### Single node without cycle

```text
1 → null
```

Return:

```text
null
```

### Single node with cycle

```text
1
↑
└──
```

The same node is encountered again.

Return:

```text
1
```

### Cycle starts at head

```text
1 → 2 → 3
↑       ↓
└───────┘
```

The first repeated node is `1`.

### Cycle starts somewhere in the middle

```text
1 → 2 → 3 → 4
        ↑   ↓
        └───┘
```

The first repeated node is `3`.

---

# 12. Complexity

Let `n` be the number of nodes visited before detecting the cycle or reaching `null`.

### Time Complexity

```text
O(n)
```

Every node is visited at most once before we detect the repeated node.

### Space Complexity

```text
O(n)
```

The `HashSet` can store up to `n` node references.

---

# 13. Pattern Recognition

This problem belongs to the:

```text
Linked List + Visited Set
```

pattern.

Whenever a linked list problem asks:

> Have I seen this exact node before?

A `HashSet<ListNode>` is a natural solution.

The general pattern is:

```java
Set<ListNode> visited = new HashSet<>();

while(current != null){
    if(visited.contains(current)){
        // repeated node found
    }

    visited.add(current);
    current = current.next;
}
```

---

# 14. Important Observation

This problem also has an important follow-up:

> Can you solve it using O(1) memory?

Yes.

The `HashSet` solution uses:

```text
O(n)
```

extra memory.

The constant-space solution uses **Floyd's Cycle Detection Algorithm**, which uses two pointers:

```text
slow → moves 1 step
fast → moves 2 steps
```

First, they are used to determine whether a cycle exists.

Then, after they meet, one pointer is moved back to `head` and both pointers move one step at a time.

Their meeting point is the beginning of the cycle.

This is an important extension of the linked-list patterns learned so far.

---

# 15. Linked List Patterns Learned So Far

| Problem                | Pattern                     |
| ---------------------- | --------------------------- |
| Reverse Linked List    | Three-pointer reversal      |
| Middle of Linked List  | Fast and slow pointers      |
| Linked List Cycle      | Floyd's cycle detection     |
| Palindrome Linked List | Middle + reverse + compare  |
| Remove Nth Node        | Fixed-gap two pointers      |
| Swap Nodes             | Fixed-gap two pointers      |
| Reorder List           | Middle + reverse + merge    |
| Odd Even Linked List   | Multiple pointer chains     |
| Rotate List            | Circular connection + split |
| Partition List         | Separate chains             |
| Linked List Cycle II   | Visited nodes / cycle entry |

The major pattern added here is:

```text
Visited Node Detection
```

---

# 16. Final Mental Model

When solving **Linked List Cycle II**, think:

```text
Traverse the list
      ↓
Have I seen this exact node before?
      ↓
    Yes
      ↓
Cycle begins here
      ↓
Return current
```

Using the `HashSet` approach:

```text
current node
      ↓
check HashSet
      ↓
already exists?
   /          \
 Yes           No
  ↓             ↓
return        add node
node             ↓
              move next
```

The key idea is:

> Store the actual nodes you have visited. The first node you encounter again is the node where the cycle begins.

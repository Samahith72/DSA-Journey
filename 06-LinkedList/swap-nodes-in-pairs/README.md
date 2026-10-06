# WIN #82 — 24. Swap Nodes in Pairs

[LeetCode — 24. Swap Nodes in Pairs](https://leetcode.com/problems/swap-nodes-in-pairs/)

**Difficulty:** Medium
**Topic:** Linked List, Pointer Manipulation

---

## Problem

Given a linked list, swap every two adjacent nodes and return the new head.

The important restriction is:

> We cannot modify the values inside the nodes. We must change the actual node connections.

### Example 1

```text
Input:
1 → 2 → 3 → 4

Output:
2 → 1 → 4 → 3
```

The pairs are:

```text
(1, 2) → (3, 4)
```

After swapping:

```text
(2, 1) → (4, 3)
```

### Example 2

```text
Input:
[]
```

Output:

```text
[]
```

### Example 3

```text
Input:
1
```

Output:

```text
1
```

There is no pair to swap.

### Example 4

```text
Input:
1 → 2 → 3
```

Output:

```text
2 → 1 → 3
```

The last node remains unchanged because it does not have a pair.

---

# Core Idea

The main challenge is changing the `next` pointers correctly without losing any nodes.

For every pair:

```text
first → second → remaining
```

we want:

```text
second → first → remaining
```

We use a **dummy node** and a `prev` pointer.

The structure looks like:

```text
dummy → first → second → remaining
  ↑
 prev
```

After swapping:

```text
dummy → second → first → remaining
```

Then we move `prev` forward to `first` and process the next pair.

---

# Java Solution

```java
class Solution {
    public ListNode swapPairs(ListNode head) {

        if(head == null || head.next == null){
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while(prev.next != null && prev.next.next != null){
            ListNode first = prev.next;
            ListNode second = first.next;

            first.next = second.next;
            second.next = first;
            prev.next = second;

            prev = first;
        }
        
        return dummy.next;
    }
}
```

---

# Step-by-Step Explanation

## 1. Handle lists with fewer than two nodes

```java
if(head == null || head.next == null){
    return head;
}
```

There is nothing to swap when:

```text
head = null
```

or:

```text
head = 1
```

So we immediately return the original head.

---

# 2. Create a dummy node

```java
ListNode dummy = new ListNode(0);
dummy.next = head;
```

For:

```text
1 → 2 → 3 → 4
```

we create:

```text
dummy → 1 → 2 → 3 → 4
```

This makes swapping the first pair much easier.

Without a dummy node, we would need special handling to update the head.

With the dummy node:

```text
dummy → 2 → 1 → 3 → 4
```

and finally:

```java
return dummy.next;
```

gives us the new head.

---

# 3. Initialize `prev`

```java
ListNode prev = dummy;
```

Initially:

```text
dummy → 1 → 2 → 3 → 4
  ↑
 prev
```

`prev` always points to the node **before the pair we are currently swapping**.

This is important because we need to connect the previous part of the list to the newly swapped pair.

---

# 4. Check whether a complete pair exists

```java
while(prev.next != null && prev.next.next != null)
```

We need two nodes to perform a swap.

The condition guarantees:

```text
prev.next       → first node exists
prev.next.next  → second node exists
```

For:

```text
1 → 2 → 3
```

the first pair is:

```text
1 → 2
```

After that, only `3` remains.

Since `3` has no second node, the loop stops.

---

# 5. Identify the two nodes

```java
ListNode first = prev.next;
ListNode second = first.next;
```

Initially:

```text
dummy → 1 → 2 → 3 → 4
         ↑   ↑
       first second
```

So:

```text
first = 1
second = 2
```

We want:

```text
2 → 1
```

---

# 6. Save the remaining part of the list

The first pointer change is:

```java
first.next = second.next;
```

Before:

```text
first → second → third
```

After:

```text
first → third
```

For our example:

```text
1 → 2 → 3 → 4
```

becomes temporarily:

```text
1 → 3 → 4

2
```

This is important because we don't want to lose the rest of the list.

---

# 7. Reverse the pair

Now:

```java
second.next = first;
```

This creates:

```text
2 → 1 → 3 → 4
```

The pair itself has now been reversed.

---

# 8. Connect the previous part to the new pair

We still need `prev` to point to `second`.

```java
prev.next = second;
```

Before:

```text
prev → first → second
```

After:

```text
prev → second → first
```

So the complete structure becomes:

```text
dummy → 2 → 1 → 3 → 4
```

---

# 9. Move `prev` forward

```java
prev = first;
```

This may look unusual at first.

After swapping:

```text
prev → second → first → remaining
```

The node `first` is now the **second node of the swapped pair**.

Therefore, `first` becomes the node immediately before the next pair.

For example:

```text
dummy → 2 → 1 → 3 → 4
             ↑
            prev
```

Now the next pair is:

```text
3 → 4
```

So we can repeat the same process.

---

# Complete Pointer Movement

For:

```text
1 → 2 → 3 → 4
```

### Initial state

```text
dummy → 1 → 2 → 3 → 4
  ↑
 prev
```

---

### Identify pair

```text
dummy → 1 → 2 → 3 → 4
         ↑   ↑
       first second
```

---

### Connect `first` to the remaining list

```text
first.next = second.next
```

Conceptually:

```text
1 → 3 → 4

2
```

---

### Reverse the pair

```text
second.next = first
```

Now:

```text
2 → 1 → 3 → 4
```

---

### Connect `prev`

```text
prev.next = second
```

Final state for the first pair:

```text
dummy → 2 → 1 → 3 → 4
```

---

### Move `prev`

```text
prev = first;
```

Now:

```text
dummy → 2 → 1 → 3 → 4
             ↑
            prev
```

The next pair is:

```text
3 → 4
```

Repeat the exact same operations.

Final result:

```text
dummy → 2 → 1 → 4 → 3
```

Return:

```java
return dummy.next;
```

Result:

```text
2 → 1 → 4 → 3
```

---

# The Three Pointer Changes

The heart of this solution is these three lines:

```java
first.next = second.next;
second.next = first;
prev.next = second;
```

You can remember them as:

```text
1. First skips second
2. Second points to first
3. Previous points to second
```

Visualized:

```text
Before:

prev → first → second → remaining


Step 1:

prev → first ─────────→ remaining
              second


Step 2:

prev → first ← second
       ↓
   remaining


Step 3:

prev → second → first → remaining
```

This is the actual pointer manipulation that performs the swap.

---

# Why Do We Need `prev`?

Suppose we only swapped:

```text
1 → 2
```

into:

```text
2 → 1
```

We would still need to connect the previous part of the list to `2`.

For example:

```text
previous → 1 → 2 → remaining
```

must become:

```text
previous → 2 → 1 → remaining
```

That's exactly what:

```java
prev.next = second;
```

does.

So `prev` acts as the **connector between the already processed list and the current swapped pair**.

---

# Why Use a Dummy Node?

The first pair is special because its first node is the original head.

For:

```text
1 → 2 → 3 → 4
```

after swapping:

```text
2 → 1 → 4 → 3
```

The head changes from `1` to `2`.

The dummy node gives us:

```text
dummy → 1 → 2 → 3 → 4
```

After swapping:

```text
dummy → 2 → 1 → 4 → 3
```

Therefore:

```java
return dummy.next;
```

automatically returns the correct new head.

This eliminates special handling for the first pair.

---

# Odd Number of Nodes

Consider:

```text
1 → 2 → 3
```

First pair:

```text
1 → 2
```

becomes:

```text
2 → 1
```

Now:

```text
3
```

is left.

The loop condition:

```java
prev.next != null && prev.next.next != null
```

fails because there is no second node.

Therefore `3` remains unchanged.

Final result:

```text
2 → 1 → 3
```

---

# Edge Cases

## 1. Empty list

```text
[]
```

Returns:

```text
[]
```

---

## 2. One node

```text
1
```

There is no pair, so:

```text
1
```

remains unchanged.

---

## 3. Two nodes

```text
1 → 2
```

Becomes:

```text
2 → 1
```

---

## 4. Odd number of nodes

```text
1 → 2 → 3
```

Becomes:

```text
2 → 1 → 3
```

The final unpaired node stays unchanged.

---

## 5. Even number of nodes

```text
1 → 2 → 3 → 4
```

Becomes:

```text
2 → 1 → 4 → 3
```

Every node belongs to a pair.

---

# Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

Every node is visited a constant number of times.

### Space Complexity

```text
O(1)
```

Only a few pointers are used:

```text
dummy
prev
first
second
```

No additional list or array is created.

---

# Pattern Recognition

This problem teaches an important **Linked List Pointer Rewiring** pattern.

Whenever you need to reverse or rearrange a small section of a linked list, think:

```text
Save the next part
        ↓
Change the current connections
        ↓
Reconnect the previous part
        ↓
Move forward
```

For swapping pairs:

```text
prev → first → second → remaining
```

becomes:

```text
prev → second → first → remaining
```

The dummy node makes the first operation behave exactly like every other operation.

---

# Connection With Previous Problems

This problem builds directly on the linked-list techniques learned in previous problems.

### 206. Reverse Linked List

You learned:

```text
current.next = prev
```

to reverse links.

### 92. Reverse Linked List II

You learned how to reverse a specific section while reconnecting it to the rest of the list.

### 24. Swap Nodes in Pairs

Now we apply the same pointer-rewiring idea to groups of exactly two nodes:

```text
first → second
```

becomes:

```text
second → first
```

So this problem is a good bridge between simple pointer manipulation and more advanced linked-list rearrangement.

---

# Key Takeaway

The most important mental model is:

```text
Before:

prev → first → second → remaining


After:

prev → second → first → remaining
```

And the three pointer operations are:

```java
first.next = second.next;
second.next = first;
prev.next = second;
```

Then:

```java
prev = first;
```

moves us to the correct position for the next pair.

The overall approach is:

```text
1. Create dummy node
2. Set prev = dummy
3. Find first and second
4. Save the remaining list
5. Reverse the two nodes
6. Connect prev to the new first node
7. Move prev forward
8. Repeat
9. Return dummy.next
```

Complexity:

```text
Time  → O(n)
Space → O(1)
```

---

# Linked List Patterns Learned So Far

```text
206  → Reverse Linked List
876  → Middle of the Linked List
141  → Linked List Cycle
234  → Palindrome Linked List
83   → Remove Duplicates from Sorted List
21   → Merge Two Sorted Lists
1290 → Convert Binary Number in a Linked List to Integer
203  → Remove Linked List Elements
160  → Intersection of Two Linked Lists
237  → Delete Node in a Linked List
2181 → Merge Nodes in Between Zeros
92   → Reverse Linked List II
707  → Design Linked List
2    → Add Two Numbers
1721 → Swapping Nodes in a Linked List
19   → Remove Nth Node From End of List
24   → Swap Nodes in Pairs
```

---

# Final Mental Model

For pair swapping, visualize only four parts:

```text
prev → first → second → remaining
```

Your job is to transform it into:

```text
prev → second → first → remaining
```

Use:

```java
first.next = second.next;
second.next = first;
prev.next = second;
```

Then:

```java
prev = first;
```

and repeat.

The key is not to think of it as "swapping values."

You are physically changing the linked-list connections:

```text
first → second
```

into:

```text
second → first
```

while preserving the connection to the remaining list.

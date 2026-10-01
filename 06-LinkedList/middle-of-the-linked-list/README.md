# WIN #66 — Middle of the Linked List

## Problem

[LeetCode 876 — Middle of the Linked List](https://leetcode.com/problems/middle-of-the-linked-list/)

Given the `head` of a singly linked list, return the **middle node** of the linked list.

If the linked list contains **two middle nodes**, return the **second middle node**.

For example:

```text
1 → 2 → 3 → 4 → 5
```

The middle node is:

```text
3
```

For an even-sized list:

```text
1 → 2 → 3 → 4 → 5 → 6
```

the two middle nodes are `3` and `4`, so we return:

```text
4
```

---

# Example 1

```text
Input:
head = [1,2,3,4,5]

Output:
[3,4,5]
```

The linked list is:

```text
1 → 2 → 3 → 4 → 5 → null
```

The middle node is `3`.

Therefore, we return:

```text
3 → 4 → 5 → null
```

---

# Example 2

```text
Input:
head = [1,2,3,4,5,6]

Output:
[4,5,6]
```

The linked list is:

```text
1 → 2 → 3 → 4 → 5 → 6 → null
```

There are two middle nodes:

```text
1 → 2 → [3] → [4] → 5 → 6
```

The problem asks us to return the **second middle**, so the answer is:

```text
4 → 5 → 6 → null
```

---

# My Java Solution

```java
class Solution {
    public ListNode middleNode(ListNode head) {

        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null){

            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }
}
```

---

# Core Idea

The key idea is the **Fast and Slow Pointer technique**.

We use two pointers:

```text
slow
fast
```

They both start at the head.

But they move at different speeds:

```text
slow → moves 1 node at a time

fast → moves 2 nodes at a time
```

Because `fast` moves twice as quickly as `slow`, when `fast` reaches the end of the linked list, `slow` will be at the middle.

---

# The Two Pointers

Initially:

```java
ListNode fast = head;
ListNode slow = head;
```

For:

```text
1 → 2 → 3 → 4 → 5 → null
```

we have:

```text
slow
 ↓
1 → 2 → 3 → 4 → 5

fast
 ↓
1 → 2 → 3 → 4 → 5
```

Both start at node `1`.

---

# Step 1

The loop condition is:

```java
while(fast != null && fast.next != null)
```

This ensures that `fast` can safely move two nodes.

Then:

```java
fast = fast.next.next;
slow = slow.next;
```

So:

```text
slow → moves 1 step
fast → moves 2 steps
```

After the first iteration:

```text
1 → 2 → 3 → 4 → 5
    ↑       ↑
   slow    fast
```

---

# Step 2

Move again:

```text
1 → 2 → 3 → 4 → 5
        ↑           ↑
       slow        fast
```

Now:

```text
slow = 3
fast = 5
```

The fast pointer has reached the last node.

---

# Step 3

The loop checks:

```java
fast != null && fast.next != null
```

Here:

```text
fast = 5
fast.next = null
```

Therefore the condition becomes false.

We stop and return:

```java
return slow;
```

So:

```text
slow = 3
```

Answer:

```text
3 → 4 → 5
```

---

# Odd-Length List

Consider:

```text
1 → 2 → 3 → 4 → 5
```

Pointer movement:

```text
Start:

slow = 1
fast = 1
```

After 1 iteration:

```text
slow = 2
fast = 3
```

After 2 iterations:

```text
slow = 3
fast = 5
```

Now:

```text
fast.next = null
```

Loop stops.

Therefore:

```text
slow = 3
```

which is exactly the middle.

---

# Even-Length List

Now consider:

```text
1 → 2 → 3 → 4 → 5 → 6
```

Initially:

```text
slow = 1
fast = 1
```

After first iteration:

```text
slow = 2
fast = 3
```

After second iteration:

```text
slow = 3
fast = 5
```

After third iteration:

```text
slow = 4
fast = null
```

Now the loop stops.

Therefore:

```text
slow = 4
```

This is important because the problem asks for the **second middle node**.

The two middle nodes are:

```text
1 → 2 → 3 → 4 → 5 → 6
        ↑   ↑
      first second
```

Our algorithm naturally returns:

```text
4
```

---

# Why Does This Work?

The important relationship is:

```text
fast moves 2 steps
slow moves 1 step
```

So:

```text
fast = 2 × slow's distance
```

When `fast` has traversed the entire list, `slow` has traversed approximately half of it.

Therefore:

```text
fast → end
        ↓
slow → middle
```

This allows us to find the middle without knowing the length of the linked list beforehand.

---

# Visual Understanding

For:

```text
1 → 2 → 3 → 4 → 5 → 6
```

Think of the pointers as:

```text
slow:  →  →  →  → 

fast:  →  →  →  →  →  → 
```

`fast` covers the list twice as quickly.

When:

```text
fast → null
```

we have:

```text
slow → 4
```

---

# Why Not Count the Length First?

One approach would be:

### First traversal

Find the length:

```text
n = 6
```

### Second traversal

Move:

```text
n / 2
```

positions.

This requires two traversals.

The Fast and Slow Pointer technique finds the answer in a **single traversal**.

We don't need to know the length beforehand.

---

# The Important Loop Condition

The solution uses:

```java
while(fast != null && fast.next != null)
```

Both checks are important.

We move:

```java
fast = fast.next.next;
```

Therefore, we need to make sure:

```text
fast
```

exists and:

```text
fast.next
```

also exists.

Otherwise, accessing:

```java
fast.next.next
```

could cause a `NullPointerException`.

---

# Why Does It Return the Second Middle?

This is one of the most useful details of this implementation.

For:

```text
1 → 2 → 3 → 4 → 5 → 6
```

the middle nodes are:

```text
3 and 4
```

Because `slow` starts at the head and moves whenever `fast` moves two steps:

```java
fast = fast.next.next;
slow = slow.next;
```

the final position of `slow` becomes:

```text
4
```

Therefore, this exact implementation naturally satisfies:

> If there are two middle nodes, return the second middle node.

---

# Pointer Movement Table

For:

```text
1 → 2 → 3 → 4 → 5 → 6
```

| Iteration | Slow | Fast |
| --------- | ---: | ---: |
| Start     |    1 |    1 |
| 1         |    2 |    3 |
| 2         |    3 |    5 |
| 3         |    4 | null |

Final:

```text
slow = 4
```

Therefore:

```text
4 → 5 → 6
```

is returned.

---

# Pattern Recognition

Whenever you see a linked-list problem asking for:

```text
Middle of a linked list
Cycle detection
Find kth position
Compare nodes at different speeds
```

think about:

```text
Fast & Slow Pointers
```

The basic pattern is:

```java
ListNode slow = head;
ListNode fast = head;

while(fast != null && fast.next != null){

    slow = slow.next;
    fast = fast.next.next;
}
```

This pattern is extremely important for Linked List DSA.

---

# Complexity

Let:

```text
n = number of nodes
```

The `fast` pointer moves through the list, while `slow` moves half as quickly.

The list is traversed once.

Therefore:

```text
Time Complexity: O(n)
```

Only two pointers are used:

```text
slow
fast
```

Therefore:

```text
Space Complexity: O(1)
```

---

# Edge Cases

## One Node

```text
1 → null
```

Both pointers start at `1`.

The loop doesn't execute.

Return:

```text
1
```

---

## Two Nodes

```text
1 → 2 → null
```

The middle nodes are:

```text
1 and 2
```

The problem asks for the second middle.

The algorithm returns:

```text
2
```

---

## Three Nodes

```text
1 → 2 → 3
```

The middle is:

```text
2
```

The algorithm returns:

```text
2
```

---

# Key Insight

The most important thing to remember is:

> **Move one pointer one step and another pointer two steps. When the fast pointer reaches the end, the slow pointer is at the middle.**

The pattern is:

```text
slow → 1 step
fast → 2 steps
```

Then:

```text
fast reaches end
        ↓
slow reaches middle
```

---

# Final Takeaway

This problem introduces one of the most important Linked List patterns:

```text
Fast & Slow Pointers
```

Remember:

```java
ListNode slow = head;
ListNode fast = head;

while(fast != null && fast.next != null){

    slow = slow.next;
    fast = fast.next.next;
}

return slow;
```

For an odd-length list:

```text
1 → 2 → [3] → 4 → 5
          ↑
        slow
```

For an even-length list:

```text
1 → 2 → 3 → [4] → 5 → 6
                ↑
              slow
```

So the pattern is:

```text
Linked List
     ↓
Fast & Slow Pointers
     ↓
slow → 1 step
fast → 2 steps
     ↓
fast reaches end
     ↓
slow = middle
```

### Complexity

```text
Time  : O(n)
Space : O(1)
```

### Pattern

```text
Linked List
    ↓
Two Pointers
    ↓
Fast & Slow Pointer
    ↓
Find Middle
```

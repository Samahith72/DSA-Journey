# WIN #85 — 61. Rotate List

[LeetCode — 61. Rotate List](https://leetcode.com/problems/rotate-list/)

**Difficulty:** Medium
**Topic:** Linked List, Two Pointers, Pointer Manipulation

---

## Problem

Given the `head` of a singly linked list, rotate the list to the **right by `k` places**.

Rotating right means that the last node moves to the front.

### Example 1

```text
Input:
1 → 2 → 3 → 4 → 5
k = 2

Output:
4 → 5 → 1 → 2 → 3
```

One rotation:

```text
5 → 1 → 2 → 3 → 4
```

Second rotation:

```text
4 → 5 → 1 → 2 → 3
```

Therefore:

```text
[1,2,3,4,5]
```

becomes:

```text
[4,5,1,2,3]
```

### Example 2

```text
Input:
0 → 1 → 2
k = 4

Output:
2 → 0 → 1
```

Since the list has 3 nodes:

```text
4 % 3 = 1
```

So rotating 4 times is equivalent to rotating once.

---

# Core Idea

The key observation is:

> Rotating a linked list to the right by `k` means moving the last `k` nodes to the front.

For:

```text
1 → 2 → 3 → 4 → 5
```

and:

```text
k = 2
```

we need to split the list into:

```text
1 → 2 → 3

4 → 5
```

and rearrange it as:

```text
4 → 5 → 1 → 2 → 3
```

The solution follows these steps:

```text
1. Find the length and tail.
2. Reduce k using k % length.
3. Connect tail to head to form a circular list.
4. Find the new tail.
5. The node after newTail becomes the new head.
6. Break the circular connection.
```

---

# Java Solution

```java
class Solution {
    public ListNode rotateRight(ListNode head, int k) {

        if(head == null || head.next == null || k == 0){
            return head;
        }

        ListNode tail = head;
        int length = 1;

        while(tail.next != null){
            tail = tail.next;
            length++;
        }

        k %= length;

        if(k == 0){
            return head;
        }

        tail.next = head;

        int steps = length - k - 1;
        ListNode newTail = head;

        for(int i = 0; i < steps; i++){
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;

        newTail.next = null;

        return newHead;
    }
}
```

---

# Step-by-Step Explanation

## 1. Handle simple cases

```java
if(head == null || head.next == null || k == 0){
    return head;
}
```

There is no need to rotate when:

```text
head == null
```

or:

```text
head.next == null
```

because a list with one node remains unchanged.

Also, if:

```text
k == 0
```

the list should remain unchanged.

---

# 2. Find the length and tail

We start with:

```java
ListNode tail = head;
int length = 1;
```

Then:

```java
while(tail.next != null){
    tail = tail.next;
    length++;
}
```

For:

```text
1 → 2 → 3 → 4 → 5
```

we eventually have:

```text
length = 5
tail = 5
```

So:

```text
head
 ↓
1 → 2 → 3 → 4 → 5
                ↑
               tail
```

Knowing the length is important because `k` can be extremely large.

---

# 3. Reduce `k`

The most important optimization is:

```java
k %= length;
```

Why?

Because rotating a list by its length brings the list back to its original form.

For example:

```text
1 → 2 → 3 → 4 → 5
```

Rotate once:

```text
5 → 1 → 2 → 3 → 4
```

Rotate twice:

```text
4 → 5 → 1 → 2 → 3
```

After 5 rotations:

```text
1 → 2 → 3 → 4 → 5
```

So:

```text
k = 5
```

is equivalent to:

```text
k = 0
```

Likewise:

```text
k = 7
length = 5

7 % 5 = 2
```

Therefore rotating 7 times is equivalent to rotating twice.

This is especially important because the constraints allow:

```text
k <= 2 * 10^9
```

---

# 4. Check if rotation is unnecessary

After:

```java
k %= length;
```

we check:

```java
if(k == 0){
    return head;
}
```

For example:

```text
length = 5
k = 10
```

gives:

```text
10 % 5 = 0
```

So the list remains unchanged.

---

# 5. Make the list circular

Now we perform:

```java
tail.next = head;
```

Originally:

```text
1 → 2 → 3 → 4 → 5
                ↑
               tail
```

After:

```text
5 → 1
```

the list becomes circular:

```text
1 → 2 → 3 → 4 → 5
↑               ↓
└───────────────┘
```

This is a very useful trick.

Instead of manually moving the last `k` nodes to the front, we temporarily create a circular linked list.

---

# 6. Find the new tail

The new head will be:

```text
length - k
```

positions from the beginning.

Therefore, the new tail is:

```text
length - k - 1
```

positions from the beginning.

Your code calculates:

```java
int steps = length - k - 1;
```

For:

```text
length = 5
k = 2
```

we get:

```text
steps = 5 - 2 - 1
      = 2
```

So the new tail is node `3`.

```text
1 → 2 → 3 → 4 → 5
        ↑
     newTail
```

---

# 7. Move to the new tail

We initialize:

```java
ListNode newTail = head;
```

Then:

```java
for(int i = 0; i < steps; i++){
    newTail = newTail.next;
}
```

For:

```text
1 → 2 → 3 → 4 → 5
```

and:

```text
steps = 2
```

the pointer moves:

```text
1 → 2 → 3
        ↑
     newTail
```

So:

```text
newTail = 3
```

---

# 8. Find the new head

The node immediately after the new tail becomes the new head:

```java
ListNode newHead = newTail.next;
```

Since:

```text
newTail = 3
```

we have:

```text
newHead = 4
```

Therefore:

```text
4 → 5 → 1 → 2 → 3
```

is the desired rotated list.

---

# 9. Break the circular connection

The list is currently circular:

```text
4 → 5 → 1 → 2 → 3 → 4 → ...
```

We need to break it after the new tail.

So:

```java
newTail.next = null;
```

The result becomes:

```text
4 → 5 → 1 → 2 → 3 → null
```

Now the list is a normal singly linked list again.

---

# Complete Walkthrough

Consider:

```text
head = [1,2,3,4,5]
k = 2
```

### Step 1: Find length

```text
length = 5
tail = 5
```

### Step 2: Reduce k

```text
k = 2 % 5
k = 2
```

### Step 3: Connect tail to head

```text
1 → 2 → 3 → 4 → 5
↑               ↓
└───────────────┘
```

### Step 4: Find new tail

```text
steps = 5 - 2 - 1
      = 2
```

So:

```text
1 → 2 → 3 → 4 → 5
        ↑
     newTail
```

### Step 5: Find new head

```text
newHead = newTail.next
```

Therefore:

```text
newHead = 4
```

### Step 6: Break the circle

```java
newTail.next = null;
```

Final:

```text
4 → 5 → 1 → 2 → 3
```

---

# Why `length - k - 1`?

This is an important formula to understand.

After rotating right by `k`, the new head is at position:

```text
length - k
```

using 1-based indexing.

Therefore, the node immediately before it is at:

```text
length - k - 1
```

zero-based moves from the original head.

For:

```text
length = 5
k = 2
```

the new head is:

```text
5 - 2 = 3
```

position from the beginning:

```text
1 → 2 → 3 → 4 → 5
        ↑
      newHead
```

Therefore the new tail is:

```text
2
```

moves from `head`:

```text
1 → 2 → 3
        ↑
     newTail
```

Hence:

```java
int steps = length - k - 1;
```

---

# Why Does the Circular Trick Work?

Instead of physically moving:

```text
4 → 5
```

to the beginning, we connect:

```text
5 → 1
```

creating:

```text
1 → 2 → 3 → 4 → 5
↑               ↓
└───────────────┘
```

Then we simply choose where to break the circle.

For:

```text
k = 2
```

we break after:

```text
3
```

which gives:

```text
4 → 5 → 1 → 2 → 3
```

So the entire rotation becomes:

```text
Create circle
      ↓
Find new tail
      ↓
Find new head
      ↓
Break circle
```

This is much cleaner than repeatedly moving the last node to the front.

---

# Edge Cases

## 1. Empty list

```text
[]
```

Immediately returns:

```text
[]
```

---

## 2. One node

```text
1
```

Any number of rotations gives:

```text
1
```

So the initial condition handles it.

---

## 3. `k = 0`

```text
1 → 2 → 3
```

No rotation is needed.

Result:

```text
1 → 2 → 3
```

---

## 4. `k` equals the length

```text
1 → 2 → 3 → 4 → 5
k = 5
```

Since:

```text
5 % 5 = 0
```

the list remains unchanged.

---

## 5. `k` is larger than the length

```text
1 → 2 → 3
k = 4
```

Calculate:

```text
4 % 3 = 1
```

So this is equivalent to rotating once:

```text
3 → 1 → 2
```

---

# Pattern Recognition

This problem teaches an important **Circular Linked List Transformation** pattern.

When a linked list needs to be rotated, think:

```text
1. Find length
2. Reduce k using modulo
3. Connect tail to head
4. Find the new tail
5. Break the circle
```

The central trick is:

```text
tail.next = head;
```

This temporarily converts the list into a circular structure.

Then:

```text
newTail.next = null;
```

converts it back into a normal linked list.

---

# Connection With Previous Linked List Problems

This problem combines several techniques you've already learned.

### 876. Middle of the Linked List

You learned how to locate a specific position in a linked list using pointer movement.

### 19. Remove Nth Node From End

You learned how relative positions can be handled using pointer gaps.

### 24. Swap Nodes in Pairs

You learned how to carefully modify `next` pointers without changing node values.

### 328. Odd Even Linked List

You learned how to rearrange an existing linked list by changing its connections.

### 61. Rotate List

Now we combine pointer positioning with a temporary circular structure.

---

# Key Takeaway

The most important insight is:

> Rotating a linked list is equivalent to making it circular and then breaking the circle at the correct position.

The complete mental model is:

```text
Original:

1 → 2 → 3 → 4 → 5


Create circle:

1 → 2 → 3 → 4 → 5
↑               ↓
└───────────────┘


Find new tail:

1 → 2 → 3 | 4 → 5
        ↑
    newTail


Break here:

4 → 5 → 1 → 2 → 3
↑
newHead
```

The key calculations are:

```text
k = k % length

steps = length - k - 1
```

Then:

```java
ListNode newHead = newTail.next;
newTail.next = null;
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
328  → Odd Even Linked List
61   → Rotate List
```

---

# Final Mental Model

When you see:

```text
"Rotate a linked list to the right by k"
```

think immediately:

```text
Find length
    ↓
k %= length
    ↓
Make list circular
    ↓
Find new tail
    ↓
newHead = newTail.next
    ↓
newTail.next = null
```

For:

```text
1 → 2 → 3 → 4 → 5
k = 2
```

the rotation is really just:

```text
1 → 2 → 3 | 4 → 5
            ↑
         new head
```

So the entire problem reduces to finding the correct place to cut the linked list.

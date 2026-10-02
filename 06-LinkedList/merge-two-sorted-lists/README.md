# WIN #70 — Merge Two Sorted Lists

## Problem

[LeetCode 21 — Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/)

You are given the heads of two **sorted linked lists** `list1` and `list2`.

Merge the two lists into one **sorted linked list** by splicing together the nodes of the original lists.

Return the head of the merged linked list.

For example:

```text
list1: 1 → 2 → 4
list2: 1 → 3 → 4
```

The merged list should be:

```text
1 → 1 → 2 → 3 → 4 → 4
```

---

# Example 1

```text
Input:
list1 = [1,2,4]
list2 = [1,3,4]

Output:
[1,1,2,3,4,4]
```

The two sorted lists are:

```text
list1:
1 → 2 → 4

list2:
1 → 3 → 4
```

Compare the nodes one by one and connect the smaller node to the result:

```text
1 → 1 → 2 → 3 → 4 → 4
```

---

# Example 2

```text
Input:
list1 = []
list2 = []

Output:
[]
```

Both lists are empty, so the result is also empty.

---

# Example 3

```text
Input:
list1 = []
list2 = [0]

Output:
[0]
```

If one list is empty, the other list is already the complete answer.

---

# My Java Solution

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        if(list1 == null && list2 == null){
            return list1;
        }

        if(list1 == null){
            return list2;
        }

        if(list2 == null){
            return list1;
        }

        ListNode answer = new ListNode(-1);
        ListNode current = answer;

        while(list1 != null && list2 != null){

            if(list1.val <= list2.val){
                current.next = list1;
                list1 = list1.next;
            }else{
                current.next = list2;
                list2 = list2.next;
            }

            current = current.next;
        }

        if(list1 != null){
            current.next = list1;
        }else{
            current.next = list2;
        }

        return answer.next;
    }
}
```

---

# Core Idea

Both input lists are already sorted.

For example:

```text
list1:
1 → 2 → 4

list2:
1 → 3 → 4
```

At any point, we only need to compare the **current node of each list**.

```text
list1
  ↓
1 → 2 → 4

list2
  ↓
1 → 3 → 4
```

Compare:

```text
1 <= 1
```

Take the node from `list1`.

Then:

```text
list1
     ↓
1 → 2 → 4

list2
  ↓
1 → 3 → 4
```

Compare again:

```text
2 > 1
```

Take the node from `list2`.

Continue this process until one list becomes empty.

---

# The Dummy Node

The solution creates:

```java
ListNode answer = new ListNode(-1);
ListNode current = answer;
```

The `answer` node is a **dummy node**.

It does not belong to the actual result.

It simply gives us a starting point from which we can build the merged list.

Initially:

```text
answer
  ↓
 -1 → null
```

`current` points to the dummy node:

```text
answer
  ↓
current
  ↓
 -1 → null
```

---

# Why Use a Dummy Node?

Without a dummy node, we would need special logic to determine the first node of the merged list.

For example:

```text
Which list contains the smaller first node?

list1 → 1
list2 → 3
```

We would have to manually initialize the result.

With a dummy node, every node can be attached using the same operation:

```java
current.next = list1;
```

or:

```java
current.next = list2;
```

This makes the implementation much cleaner.

---

# Step-by-Step Walkthrough

Consider:

```text
list1 = 1 → 2 → 4
list2 = 1 → 3 → 4
```

Initially:

```text
dummy
  ↓
 -1 → null

list1
  ↓
 1 → 2 → 4

list2
  ↓
 1 → 3 → 4
```

---

## Step 1

Compare:

```text
list1.val = 1
list2.val = 1
```

Our condition is:

```java
if(list1.val <= list2.val)
```

So we take `list1`.

```text
dummy
  ↓
-1 → 1
      ↑
    current
```

Then:

```java
list1 = list1.next;
```

Now:

```text
list1
  ↓
 2 → 4

list2
  ↓
 1 → 3 → 4
```

---

## Step 2

Compare:

```text
2 <= 1
```

False.

So we take `list2`.

```text
-1 → 1 → 1
           ↑
         current
```

Now:

```text
list1
  ↓
2 → 4

list2
  ↓
3 → 4
```

---

## Step 3

Compare:

```text
2 <= 3
```

True.

Take `list1`:

```text
-1 → 1 → 1 → 2
                ↑
              current
```

Now:

```text
list1
  ↓
4

list2
  ↓
3 → 4
```

---

## Step 4

Compare:

```text
4 <= 3
```

False.

Take `list2`:

```text
-1 → 1 → 1 → 2 → 3
                    ↑
                  current
```

Now:

```text
list1
  ↓
4

list2
  ↓
4
```

---

## Step 5

Compare:

```text
4 <= 4
```

True.

Take `list1`:

```text
-1 → 1 → 1 → 2 → 3 → 4
                        ↑
                      current
```

Now:

```text
list1 = null

list2
  ↓
4
```

The main loop stops because `list1` is `null`.

---

# Attach the Remaining List

At this point, one list is empty.

The other list still contains:

```text
4
```

Instead of processing it again one node at a time, we can simply attach the remaining list:

```java
if(list1 != null){
    current.next = list1;
}else{
    current.next = list2;
}
```

So:

```text
-1 → 1 → 1 → 2 → 3 → 4 → 4
```

---

# Why Can We Attach the Remaining List Directly?

This works because the remaining list is already sorted.

Suppose we have:

```text
Result:
1 → 2 → 3

Remaining list:
4 → 5 → 6
```

Since:

```text
3 <= 4
```

we know the entire remaining list can be safely attached:

```text
1 → 2 → 3 → 4 → 5 → 6
```

There is no need to compare every remaining node.

---

# Why Return `answer.next`?

Remember:

```text
answer
  ↓
-1 → 1 → 1 → 2 → 3 → 4 → 4
```

The `-1` node is only a dummy node.

It should not be part of the result.

Therefore:

```java
return answer.next;
```

returns:

```text
1 → 1 → 2 → 3 → 4 → 4
```

instead of:

```text
-1 → 1 → 1 → 2 → 3 → 4 → 4
```

---

# Important Pointer Movement

There are two types of pointer movement happening.

### Move the input list pointer

When we take a node from `list1`:

```java
list1 = list1.next;
```

When we take a node from `list2`:

```java
list2 = list2.next;
```

This moves through the original lists.

---

### Move the result pointer

After attaching a node:

```java
current = current.next;
```

This moves the result-building pointer forward.

So the three important operations are:

```text
Attach node
     ↓
current.next = list1/list2

Move input pointer
     ↓
list1 = list1.next
or
list2 = list2.next

Move result pointer
     ↓
current = current.next
```

---

# Splicing the Existing Nodes

An important part of the problem is that we don't create new nodes for every value.

We reuse the nodes already present in the input lists.

For example:

```text
list1:
1 → 2 → 4
```

When we do:

```java
current.next = list1;
```

we are connecting the existing node directly.

Conceptually:

```text
Before:

current → null

list1
  ↓
 1 → 2 → 4


After:

current → 1 → 2 → 4
           ↑
         list1
```

Then we move:

```java
list1 = list1.next;
```

This is why the problem describes the solution as **splicing together the nodes**.

---

# Handling Equal Values

The condition is:

```java
if(list1.val <= list2.val)
```

Notice the `<=`.

Suppose:

```text
list1:
1 → 2

list2:
1 → 3
```

Both values are `1`.

Either node can be selected first because they have the same value.

Using:

```java
list1.val <= list2.val
```

means we select the node from `list1` when values are equal.

The final sorted result is still correct:

```text
1 → 1 → 2 → 3
```

---

# Edge Cases

## Both Lists Empty

```text
list1 = null
list2 = null
```

The solution returns:

```text
null
```

---

## First List Empty

```text
list1 = null

list2:
1 → 2 → 3
```

We simply return:

```text
1 → 2 → 3
```

---

## Second List Empty

```text
list1:
1 → 2 → 3

list2 = null
```

We return:

```text
1 → 2 → 3
```

---

## One Node in Each List

```text
list1:
1

list2:
2
```

Compare:

```text
1 < 2
```

Result:

```text
1 → 2
```

---

## Duplicate Values

```text
list1:
1 → 1 → 2

list2:
1 → 2 → 2
```

Result:

```text
1 → 1 → 1 → 2 → 2 → 2
```

The algorithm handles duplicates naturally.

---

# Why the Sorted Property Is Important

This algorithm relies heavily on the fact that both lists are sorted.

For example:

```text
list1:
1 → 4 → 7

list2:
2 → 3 → 8
```

At any point, the smallest remaining value must be at the head of one of the two lists.

Therefore, we only need to compare:

```text
list1.val
```

with:

```text
list2.val
```

The smaller one must be the next node in the merged list.

This gives us a linear-time solution.

---

# Pattern Recognition

When you see:

```text
Two Sorted Linked Lists
        ↓
Merge into one sorted list
```

think:

```text
Two Pointers
     ↓
Compare Current Nodes
     ↓
Take Smaller Node
     ↓
Move That List Pointer
     ↓
Move Result Pointer
     ↓
Attach Remaining List
```

---

# Connection With Previous Problems

This problem builds directly on the linked-list pointer manipulation you've already learned.

### 206. Reverse Linked List

You learned how to manipulate:

```java
current.next
```

to change links.

### 83. Remove Duplicates from Sorted List

You learned how to skip nodes:

```java
prev.next = prev.next.next;
```

### 21. Merge Two Sorted Lists

Now we learn how to **connect nodes from two different linked lists**:

```java
current.next = list1;
```

or:

```java
current.next = list2;
```

---

# Visual Pattern

The complete algorithm can be visualized as:

```text
list1                  list2
  ↓                      ↓
 1 → 2 → 4              1 → 3 → 4
       ↓
       ↓ Compare heads
       ↓
    1 vs 1
       ↓
    Take smaller
       ↓
Result
  ↓
 1
```

Continue:

```text
Result
  ↓
1 → 1 → 2 → 3 → 4 → 4
```

---

# Complexity

Let:

```text
m = number of nodes in list1
n = number of nodes in list2
```

Every node is visited at most once.

Therefore:

```text
Time Complexity: O(m + n)
```

We only use a few pointers:

```text
answer
current
list1
list2
```

No additional data structure is required.

Therefore:

```text
Space Complexity: O(1)
```

The nodes themselves are reused rather than copied.

---

# Key Insight

The most important idea is:

> **Because both linked lists are already sorted, the smallest remaining node must always be at the head of one of the two lists. Compare the two current nodes, attach the smaller one, and move that list forward.**

The entire process is:

```text
Two sorted lists
       ↓
Compare heads
       ↓
Take smaller
       ↓
Move selected list
       ↓
Repeat
       ↓
One list becomes empty
       ↓
Attach remaining list
```

---

# Final Takeaway

The core code is:

```java
while(list1 != null && list2 != null){

    if(list1.val <= list2.val){
        current.next = list1;
        list1 = list1.next;
    }else{
        current.next = list2;
        list2 = list2.next;
    }

    current = current.next;
}
```

Then attach whatever remains:

```java
if(list1 != null){
    current.next = list1;
}else{
    current.next = list2;
}
```

Finally, skip the dummy node:

```java
return answer.next;
```

### Complexity

```text
Time  : O(m + n)
Space : O(1)
```

### Pattern

```text
Two Sorted Linked Lists
          ↓
       Compare
          ↓
     Take Smaller
          ↓
      Move Pointer
          ↓
        Repeat
          ↓
 Attach Remaining List
```

**Important Linked List patterns learned so far:**

```text
206 → Reverse Linked List
876 → Fast & Slow Pointers
141 → Floyd's Cycle Detection
234 → Find Middle + Reverse + Compare
83  → Sorted List + Remove Duplicates
21  → Merge Two Sorted Lists
```

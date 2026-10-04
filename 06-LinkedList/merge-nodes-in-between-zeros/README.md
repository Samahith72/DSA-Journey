# WIN #76 — 2181. Merge Nodes in Between Zeros

[LeetCode 2181 — Merge Nodes in Between Zeros](https://leetcode.com/problems/merge-nodes-in-between-zeros/)

Difficulty: Medium
Topic: Linked List

---

# Problem

You are given a linked list that contains groups of values separated by `0`.

The linked list always:

* Starts with `0`
* Ends with `0`
* Has no two consecutive `0`s

For every pair of consecutive `0`s, calculate the sum of all nodes between them and replace that entire group with a single node containing the sum.

The final linked list must not contain any `0`s.

---

# Example 1

```text
Input:
[0,3,1,0,4,5,2,0]

Output:
[4,11]
```

The groups are:

```text
0 → 3 → 1 → 0
    └───┬──┘
       4
```

and:

```text
0 → 4 → 5 → 2 → 0
    └────┬──────┘
        11
```

Therefore:

```text
3 + 1 = 4
4 + 5 + 2 = 11
```

Final:

```text
4 → 11
```

---

# Example 2

```text
Input:
[0,1,0,3,0,2,2,0]

Output:
[1,3,4]
```

Groups:

```text
0 → 1 → 0
```

gives:

```text
1
```

Then:

```text
0 → 3 → 0
```

gives:

```text
3
```

Then:

```text
0 → 2 → 2 → 0
```

gives:

```text
4
```

Final:

```text
1 → 3 → 4
```

---

# Core Idea

The main idea is to traverse the linked list once and maintain a running sum.

We encounter two types of nodes:

```text
0
```

and:

```text
non-zero value
```

For a non-zero node:

```text
sum += current.val;
```

When we encounter `0`, the current group is finished.

So we create a new node containing:

```text
sum
```

Then reset:

```text
sum = 0;
```

---

# Approach

We use four important variables:

```java
ListNode current;
ListNode result;
ListNode tail;
int sum;
```

Their purpose is:

```text
current → traverses the original list

result  → head of the new linked list

tail    → last node of the new linked list

sum     → sum of the current group
```

The final list is constructed separately from the original list.

---

# Java Solution

```java
class Solution {
    public ListNode mergeNodes(ListNode head) {

        ListNode current = head.next;
        ListNode result = null;
        ListNode tail = null;
        int sum = 0;

        while(current != null){
            if(current.val == 0){
                ListNode newnode = new ListNode(sum);

                if(result == null){
                    result = newnode;
                    tail = newnode;
                }else{
                    tail.next = newnode;
                    tail = newnode;
                }

                sum = 0;
            }else{
                sum += current.val;
            }

            current = current.next;
        }

        return result;
    }
}
```

---

# Step-by-Step Explanation

## 1. Start From `head.next`

```java
ListNode current = head.next;
```

The first node is guaranteed to be:

```text
0
```

We don't need to add it to the sum.

For example:

```text
0 → 3 → 1 → 0 → 4 → 5 → 2 → 0
↑
head
```

So we start at:

```text
3
```

```text
0 → 3 → 1 → 0 → 4 → 5 → 2 → 0
    ↑
  current
```

---

# 2. Maintain a Running Sum

Initially:

```java
int sum = 0;
```

When we encounter a non-zero value:

```java
sum += current.val;
```

For:

```text
3 → 1
```

we get:

```text
sum = 0

3 → sum = 3

1 → sum = 4
```

So when we reach the next `0`:

```text
0 → 3 → 1 → 0
            ↑
          current
```

we know:

```text
sum = 4
```

---

# 3. A Zero Means the Group Is Complete

When:

```java
current.val == 0
```

we know that all nodes belonging to the current group have been processed.

So we create:

```java
ListNode newnode = new ListNode(sum);
```

For example:

```text
0 → 3 → 1 → 0
```

has:

```text
sum = 4
```

So we create:

```text
[4]
```

---

# 4. Build the Result List

We maintain:

```java
ListNode result = null;
ListNode tail = null;
```

`result` points to the beginning of the answer.

`tail` points to the last node in the answer.

---

## First Result Node

Initially:

```text
result = null
tail = null
```

When we create the first node:

```java
if(result == null){
    result = newnode;
    tail = newnode;
}
```

Suppose the first sum is `4`.

Now:

```text
result
  ↓
  4
  ↑
 tail
```

Both point to the first result node.

---

# 5. Add Subsequent Nodes

Suppose the next group produces:

```text
sum = 11
```

We create:

```text
newnode = 11
```

Now:

```java
tail.next = newnode;
tail = newnode;
```

Before:

```text
result
  ↓
  4 → null
  ↑
 tail
```

After:

```text
result
  ↓
  4 → 11
       ↑
      tail
```

This allows us to construct the result list in order.

---

# 6. Reset the Sum

After creating the result node:

```java
sum = 0;
```

This is necessary because we are starting a new group.

For example:

```text
0 → 3 → 1 → 0 → 4 → 5 → 2 → 0
          ↑
       group ends
```

We calculate:

```text
3 + 1 = 4
```

Then reset:

```text
sum = 0
```

Now we start calculating:

```text
4 + 5 + 2 = 11
```

---

# 7. Move to the Next Node

At the end of every iteration:

```java
current = current.next;
```

This moves through the original linked list one node at a time.

---

# Complete Walkthrough

Consider:

```text
0 → 3 → 1 → 0 → 4 → 5 → 2 → 0
```

Initially:

```text
sum = 0
result = null
tail = null
```

---

## Read `3`

```text
sum = 3
```

```text
0 → 3 → 1 → 0 → 4 → 5 → 2 → 0
    ↑
 current
```

---

## Read `1`

```text
sum = 4
```

---

## Read `0`

The group is complete.

Create:

```text
newnode = 4
```

Result:

```text
4
```

Then:

```text
sum = 0
```

---

## Read `4`

```text
sum = 4
```

---

## Read `5`

```text
sum = 9
```

---

## Read `2`

```text
sum = 11
```

---

## Read Final `0`

Create:

```text
newnode = 11
```

Append it:

```text
4 → 11
```

Reset:

```text
sum = 0
```

The traversal ends.

Return:

```java
return result;
```

Final:

```text
4 → 11
```

---

# Why Do We Create New Nodes?

The original list looks like:

```text
0 → 3 → 1 → 0 → 4 → 5 → 2 → 0
```

We need:

```text
4 → 11
```

Each group is being replaced by a completely new node containing its sum.

Therefore, the solution constructs a new linked list:

```text
Original List
     ↓
Traverse
     ↓
Calculate Group Sum
     ↓
Create New Node
     ↓
Append to Result
```

---

# Understanding `result` vs `tail`

This is an important linked-list construction pattern.

Suppose we have:

```java
ListNode result = null;
ListNode tail = null;
```

### First node

```java
result = newnode;
tail = newnode;
```

Both point to:

```text
4
```

### Second node

```java
tail.next = newnode;
tail = newnode;
```

Now:

```text
result
  ↓
  4 → 11
       ↑
      tail
```

### Third node

```java
tail.next = newnode;
tail = newnode;
```

Now:

```text
result
  ↓
  4 → 11 → 7
            ↑
           tail
```

The `result` pointer never moves.

The `tail` pointer moves forward as new nodes are added.

This is the standard pattern for building a linked list efficiently.

---

# Why We Start at `head.next`

The first node is always `0`:

```text
0 → 3 → 1 → 0
↑
head
```

That zero is only a separator.

It does not belong to any sum.

Therefore:

```java
ListNode current = head.next;
```

allows us to immediately start processing the first group.

---

# What Happens at the Final Zero?

The last node is also guaranteed to be `0`.

For example:

```text
0 → 3 → 1 → 0
```

When we reach the final `0`, the accumulated sum:

```text
3 + 1 = 4
```

has to be added to the result.

Therefore the final zero is important.

It acts as the signal:

```text
"The current group is finished."
```

---

# Important Observation

The zeros themselves are not part of the answer.

They only act as **boundaries**.

Think of the list as:

```text
0 | 3 1 | 0 | 4 5 2 | 0
```

The separators divide the values into groups:

```text
[3,1]
[4,5,2]
```

Then we reduce every group:

```text
[3,1]     → 4
[4,5,2]   → 11
```

Final:

```text
4 → 11
```

---

# Pattern Recognition

Whenever you see:

> A linked list containing groups separated by a special value.

Think:

```text
Traverse
   ↓
Accumulate values
   ↓
Separator encountered
   ↓
Finalize current group
   ↓
Create result node
   ↓
Reset accumulator
```

In this problem:

```text
0 = separator
sum = accumulator
new node = result of current group
```

---

# Another Important Pattern: Building a Linked List

This problem also teaches the pattern:

```java
ListNode result = null;
ListNode tail = null;
```

Then:

```java
if(result == null){
    result = newnode;
    tail = newnode;
}else{
    tail.next = newnode;
    tail = newnode;
}
```

This is useful whenever we need to construct a linked list while traversing another data structure.

The general idea is:

```text
result → first node of answer
tail   → last node of answer
```

---

# Edge Cases

## 1. Single Group

```text
0 → 5 → 7 → 0
```

Sum:

```text
5 + 7 = 12
```

Result:

```text
12
```

---

## 2. Every Group Contains One Node

```text
0 → 1 → 0 → 3 → 0 → 5 → 0
```

Each group is processed individually:

```text
1
3
5
```

Result:

```text
1 → 3 → 5
```

---

## 3. Multiple Values in Every Group

```text
0 → 1 → 2 → 3 → 0 → 4 → 5 → 0
```

First group:

```text
1 + 2 + 3 = 6
```

Second group:

```text
4 + 5 = 9
```

Result:

```text
6 → 9
```

---

# Complexity

Let `n` be the number of nodes in the original linked list.

### Time Complexity

```text
O(n)
```

Every node is visited exactly once.

### Space Complexity

```text
O(k)
```

where `k` is the number of groups.

We create one result node for every group.

If the problem contains many groups, the output itself requires `O(k)` space.

The traversal variables themselves use:

```text
O(1)
```

extra space.

---

# Key Takeaway

The main idea is to treat the `0`s as **group separators**.

For every group:

```text
1. Traverse the nodes.
2. Add their values to sum.
3. When 0 is encountered, create a node containing sum.
4. Append that node to the result list.
5. Reset sum to 0.
```

The core logic is:

```java
if(current.val == 0){
    ListNode newnode = new ListNode(sum);

    // append newnode to result

    sum = 0;
}else{
    sum += current.val;
}
```

The second important pattern is linked-list construction using:

```text
result + tail
```

where:

```text
result → first node
tail   → last node
```

This allows us to construct the answer in one traversal.

---

# Linked List Patterns Learned So Far

```text
206  → Reverse Linked List
       └── 3-Pointer Reversal

876  → Middle of the Linked List
       └── Fast & Slow Pointers

141  → Linked List Cycle
       └── Floyd's Cycle Detection

234  → Palindrome Linked List
       └── Find Middle + Reverse + Compare

83   → Remove Duplicates from Sorted List
       └── Compare + Skip Duplicate Nodes

21   → Merge Two Sorted Lists
       └── Dummy Node + Two Pointers

1290 → Convert Binary Number in a Linked List to Integer
       └── Linked List Traversal + Running Calculation

203  → Remove Linked List Elements
       └── Dummy Node + Node Deletion

160  → Intersection of Two Linked Lists
       └── Two Pointers + Head Switching

237  → Delete Node in a Linked List
       └── Copy Next Node + Skip Next Node

2181 → Merge Nodes in Between Zeros
       └── Running Sum + Result List Construction
```

---

# Final Mental Model

```text
0 → values → 0 → values → 0
        ↓
   calculate sum
        ↓
0 → sum → sum → ...
```

The zeros are not values to keep.

They tell us:

```text
"Finish the current group and create one result node."
```

So the overall pattern is:

```text
Special separator
       ↓
Accumulate
       ↓
Separator
       ↓
Create result node
       ↓
Reset
       ↓
Repeat
```

This is a useful combination of **linked-list traversal, accumulation, and tail-based result construction**.

# WIN #86 — 86. Partition List

[LeetCode — 86. Partition List](https://leetcode.com/problems/partition-list/)

**Difficulty:** Medium
**Topic:** Linked List, Two Pointers, Partitioning

---

## Problem

Given the `head` of a linked list and a value `x`, partition the list such that:

* All nodes with values **less than `x`** come first.
* All nodes with values **greater than or equal to `x`** come after them.
* The **relative order** of nodes within each partition must remain unchanged.

### Example 1

```text
Input:
1 → 4 → 3 → 2 → 5 → 2
x = 3

Output:
1 → 2 → 2 → 4 → 3 → 5
```

The nodes less than `3` are:

```text
1 → 2 → 2
```

The nodes greater than or equal to `3` are:

```text
4 → 3 → 5
```

Notice that their original relative order is preserved.

---

### Example 2

```text
Input:
2 → 1
x = 2

Output:
1 → 2
```

The node `1` belongs to the smaller partition.

The node `2` belongs to the greater-than-or-equal partition.

---

# Core Idea

The easiest way to think about this problem is to build **two separate lists** while traversing the original list:

```text
Small list → values < x
Large list → values >= x
```

Then connect them:

```text
Small List → Large List
```

For:

```text
1 → 4 → 3 → 2 → 5 → 2
x = 3
```

we build:

```text
Small:
1 → 2 → 2

Large:
4 → 3 → 5
```

Then:

```text
1 → 2 → 2 → 4 → 3 → 5
```

Your solution uses **dummy nodes** for both partitions:

```text
small dummy → small nodes
large dummy → large nodes
```

and tail pointers:

```text
smallTail
largeTail
```

to efficiently append nodes.

---

# Java Solution

```java
class Solution {
    public ListNode partition(ListNode head, int x) {

        ListNode small = new ListNode(-1);
        ListNode large = new ListNode(-1);

        ListNode smallTail = small;
        ListNode largeTail = large;

        ListNode current = head;

        ListNode smallHead = small;
        ListNode largeHead = large;

        while(current != null){
            int number = current.val;

            if(number < x){
                ListNode node = new ListNode(number);
                smallTail.next = node;
                smallTail = smallTail.next;
            }else{
                ListNode node = new ListNode(number);
                largeTail.next = node;
                largeTail = largeTail.next;
            }

            current = current.next;
        }

        smallTail.next = largeHead.next;

        largeTail.next = null;
        largeHead.next = null;

        smallHead = smallHead.next;

        return smallHead;
    }
}
```

---

# Step-by-Step Explanation

## 1. Create two dummy lists

```java
ListNode small = new ListNode(-1);
ListNode large = new ListNode(-1);
```

We create two dummy nodes:

```text
small
  ↓
-1

large
  ↓
-1
```

These dummy nodes are not part of the final answer.

They simply make insertion easier.

---

# 2. Create tail pointers

```java
ListNode smallTail = small;
ListNode largeTail = large;
```

Initially:

```text
small:
dummy
  ↑
smallTail
```

and:

```text
large:
dummy
  ↑
largeTail
```

The tail pointers always point to the last node in their respective lists.

This allows us to append a node in `O(1)` time.

---

# 3. Keep pointers to the dummy heads

Your code also has:

```java
ListNode smallHead = small;
ListNode largeHead = large;
```

These preserve references to the beginning of both lists.

The important difference is:

```text
smallHead
```

stays at the dummy node.

While:

```text
smallTail
```

moves as new nodes are added.

Similarly:

```text
largeHead
```

stays fixed.

```text
largeTail
```

moves.

This allows us to later access:

```java
largeHead.next
```

which is the actual first node of the large partition.

---

# 4. Traverse the original list

```java
ListNode current = head;
```

We then process every node:

```java
while(current != null)
```

For each node:

```java
int number = current.val;
```

we check whether it belongs to the small or large partition.

---

# 5. Add values smaller than `x`

```java
if(number < x){
    ListNode node = new ListNode(number);
    smallTail.next = node;
    smallTail = smallTail.next;
}
```

If:

```text
number < x
```

we create a new node and append it to the small list.

For:

```text
x = 3
```

and:

```text
current.val = 1
```

we get:

```text
Small:

dummy → 1
        ↑
   smallTail
```

Another value:

```text
2
```

gives:

```text
dummy → 1 → 2
            ↑
        smallTail
```

The relative order is preserved.

---

# 6. Add values greater than or equal to `x`

Otherwise:

```java
else{
    ListNode node = new ListNode(number);

    largeTail.next = node;
    largeTail = largeTail.next;
}
```

If:

```text
number >= x
```

the node goes into the large partition.

For:

```text
x = 3
```

the values:

```text
4
3
5
```

produce:

```text
Large:

dummy → 4 → 3 → 5
                ↑
            largeTail
```

Again, their original relative order is preserved.

---

# Complete Example

Consider:

```text
head = 1 → 4 → 3 → 2 → 5 → 2
x = 3
```

We process each node.

### Node `1`

```text
1 < 3
```

Small:

```text
1
```

Large:

```text
empty
```

---

### Node `4`

```text
4 >= 3
```

Small:

```text
1
```

Large:

```text
4
```

---

### Node `3`

```text
3 >= 3
```

Small:

```text
1
```

Large:

```text
4 → 3
```

Notice that `3` belongs to the large partition because the condition is:

```text
>= x
```

not:

```text
> x
```

---

### Node `2`

```text
2 < 3
```

Small:

```text
1 → 2
```

Large:

```text
4 → 3
```

---

### Node `5`

```text
5 >= 3
```

Small:

```text
1 → 2
```

Large:

```text
4 → 3 → 5
```

---

### Node `2`

```text
2 < 3
```

Small:

```text
1 → 2 → 2
```

Large:

```text
4 → 3 → 5
```

Now we have:

```text
Small:
1 → 2 → 2

Large:
4 → 3 → 5
```

---

# 7. Connect the Two Lists

The most important final operation is:

```java
smallTail.next = largeHead.next;
```

Before:

```text
Small:

dummy → 1 → 2 → 2
               ↑
           smallTail


Large:

dummy → 4 → 3 → 5
        ↑
   largeHead.next
```

After:

```text
dummy → 1 → 2 → 2 → 4 → 3 → 5
```

So the two partitions are combined.

---

# 8. Terminate the Large List

Your solution contains:

```java
largeTail.next = null;
```

This explicitly makes sure the large partition ends properly.

```text
4 → 3 → 5 → null
```

This prevents the resulting list from accidentally retaining an unwanted connection.

---

# 9. Remove the Small Dummy Node

The small list currently looks like:

```text
dummy → 1 → 2 → 2 → 4 → 3 → 5
```

We don't want to return the dummy node.

So:

```java
smallHead = smallHead.next;
```

makes:

```text
1 → 2 → 2 → 4 → 3 → 5
↑
smallHead
```

Finally:

```java
return smallHead;
```

returns the correct list.

---

# Why Are Dummy Nodes Useful?

Without dummy nodes, adding the first node to each partition would require special handling.

For example:

```text
smallHead = null
smallTail = null
```

When the first smaller node arrives, we'd need:

```text
if(smallHead == null){
    smallHead = node;
    smallTail = node;
}
```

The dummy node eliminates that special case.

We can always do:

```java
smallTail.next = node;
smallTail = smallTail.next;
```

The same applies to the large partition.

This is a common linked-list pattern:

```text
Dummy Node
    ↓
Simplifies insertion at the beginning
```

---

# Why Does the Relative Order Stay the Same?

This is one of the most important requirements of the problem.

Suppose:

```text
Small values in original list:

1 → 2 → 2
```

Our algorithm processes them from left to right and always appends them to the tail:

```text
1
 ↓
1 → 2
     ↓
1 → 2 → 2
```

So their order never changes.

Similarly:

```text
Large values:

4 → 3 → 5
```

remain:

```text
4 → 3 → 5
```

Therefore the algorithm is **stable** within both partitions.

---

# Important Condition

The partition condition is:

```java
if(number < x)
```

Everything else goes into the second partition:

```java
else
```

Therefore the two groups are:

```text
Small:
value < x

Large:
value >= x
```

For:

```text
x = 3
```

we get:

```text
1 → Small
2 → Small
3 → Large
4 → Large
5 → Large
```

The value `3` does **not** belong to the small partition.

---

# Why Use `smallTail` and `largeTail`?

Suppose we only had:

```text
smallHead
largeHead
```

To append a node, we would have to traverse to the end every time.

That could lead to inefficient behavior.

Instead:

```text
smallTail
```

always points to the end of the small partition.

And:

```text
largeTail
```

always points to the end of the large partition.

Therefore:

```java
smallTail.next = node;
smallTail = smallTail.next;
```

is constant-time insertion.

---

# Pointer Visualization

Think of the algorithm as two pipelines:

```text
                x
                ↓

Original:
1 → 4 → 3 → 2 → 5 → 2
↓   ↓   ↓   ↓   ↓   ↓

Small:
1 → 2 → 2

Large:
4 → 3 → 5
```

Then:

```text
Small:
1 → 2 → 2
         ↓
         4 → 3 → 5
         Large
```

Final:

```text
1 → 2 → 2 → 4 → 3 → 5
```

---

# Edge Cases

## 1. Empty list

```text
head = []
```

The loop never executes.

Both partitions remain empty.

The returned list is effectively empty.

---

## 2. All nodes are smaller than `x`

```text
1 → 2 → 1
x = 5
```

Everything goes into the small partition:

```text
1 → 2 → 1
```

The large partition is empty.

---

## 3. All nodes are greater than or equal to `x`

```text
5 → 6 → 7
x = 5
```

Everything goes into the large partition:

```text
5 → 6 → 7
```

The small partition is empty.

---

## 4. Values equal to `x`

```text
1 → 3 → 2 → 3
x = 3
```

The `3`s belong to the large partition:

```text
Small:
1 → 2

Large:
3 → 3
```

Result:

```text
1 → 2 → 3 → 3
```

---

## 5. Already partitioned

```text
1 → 2 → 3 → 4
x = 3
```

The result remains:

```text
1 → 2 → 3 → 4
```

---

# Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

Every node is visited exactly once.

Each node is appended to one of the two lists in constant time.

### Space Complexity

For **your implementation**:

```text
O(n)
```

because you create a **new `ListNode` for every original node**:

```java
ListNode node = new ListNode(number);
```

This is an important distinction.

The algorithm uses only a constant number of pointer variables, but the new nodes themselves require `O(n)` memory.

So:

```text
Traversal / auxiliary pointers → O(1)
New nodes created              → O(n)
Overall extra space            → O(n)
```

The original LeetCode problem can be solved with **O(1) extra space** by reusing the existing nodes instead of creating new nodes.

---

# Pattern Recognition

This problem teaches the **Two-List Partitioning** pattern.

When a linked-list problem asks you to divide nodes based on a condition while preserving order, think:

```text
Original List
     ↓
Traverse once
     ↓
 ┌─────────────┐
 ↓             ↓
Group A      Group B
 ↓             ↓
tail A       tail B
 └──────┬──────┘
        ↓
   Connect A → B
```

For this problem:

```text
Group A → value < x
Group B → value >= x
```

---

# Connection With Previous Linked List Problems

This problem combines several patterns you've already learned.

### 21. Merge Two Sorted Lists

You learned how to maintain tail pointers while constructing a linked-list result.

### 203. Remove Linked List Elements

You learned how to modify linked-list connections safely.

### 24. Swap Nodes in Pairs

You learned how pointer rewiring changes the structure of an existing list.

### 328. Odd Even Linked List

You learned how to maintain two separate chains and connect them afterward.

### 86. Partition List

This extends the two-chain idea into a **condition-based partition**:

```text
Group 1 → values < x
Group 2 → values >= x
```

---

# Key Takeaway

The core idea is:

```text
Traverse the original list once.

If value < x:
    append to small list

Otherwise:
    append to large list

Finally:
    small list → large list
```

The structure is:

```text
Original:

1 → 4 → 3 → 2 → 5 → 2


Partition:

Small:
1 → 2 → 2

Large:
4 → 3 → 5


Connect:

1 → 2 → 2 → 4 → 3 → 5
```

The most important implementation pattern is:

```java
smallTail.next = node;
smallTail = smallTail.next;
```

and:

```java
largeTail.next = node;
largeTail = largeTail.next;
```

Then:

```java
smallTail.next = largeHead.next;
```

connects both partitions.

One important thing to remember from **your implementation** is that you create new nodes, so your solution uses `O(n)` extra space even though the pointer logic itself uses constant auxiliary space.

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
86   → Partition List
```

---

# Final Mental Model

When you see:

```text
"Partition a linked list based on a condition"
```

think:

```text
Create two chains
      ↓
Traverse once
      ↓
Put each node into the correct chain
      ↓
Preserve order using tail pointers
      ↓
Connect the first chain to the second
```

For this problem:

```text
value < x
    ↓
Small chain

value >= x
    ↓
Large chain
```

Then:

```text
Small Tail → Large Head
```

The key pattern is:

```text
Two dummy heads
Two tail pointers
One traversal
One final connection
```

Your current implementation correctly follows this partitioning strategy and preserves the relative order within both groups. The only complexity consideration is that it creates new nodes, making its extra space `O(n)` rather than the `O(1)` possible with in-place pointer rearrangement.

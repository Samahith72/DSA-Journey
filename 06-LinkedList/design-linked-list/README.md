# WIN #78 — 707. Design Linked List

[LeetCode 707 — Design Linked List](https://leetcode.com/problems/design-linked-list/)

Difficulty: Medium
Topic: Linked List / Data Structure Design

---

# Problem

Design your own linked list implementation.

You can choose between:

```text
Singly Linked List
```

or:

```text
Doubly Linked List
```

In this solution, we implement a **singly linked list**.

Each node contains:

```text
val
next
```

where:

* `val` stores the node's value.
* `next` points to the next node.

The linked list is **0-indexed**.

---

# Operations to Implement

The `MyLinkedList` class must support five operations.

### `get(index)`

Return the value at the given index.

If the index is invalid:

```text
return -1
```

### `addAtHead(val)`

Insert a new node at the beginning.

### `addAtTail(val)`

Insert a new node at the end.

### `addAtIndex(index, val)`

Insert a new node before the node currently at `index`.

If:

```text
index == length
```

the node is appended to the end.

If:

```text
index > length
```

nothing is inserted.

### `deleteAtIndex(index)`

Delete the node at the given index if the index is valid.

---

# Example

Operations:

```text
MyLinkedList()
addAtHead(1)
addAtTail(3)
addAtIndex(1, 2)
get(1)
deleteAtIndex(1)
get(1)
```

The list changes as follows:

```text
Initial:

empty
```

After:

```text
addAtHead(1)
```

we get:

```text
1
```

After:

```text
addAtTail(3)
```

we get:

```text
1 → 3
```

After:

```text
addAtIndex(1, 2)
```

we get:

```text
1 → 2 → 3
```

Then:

```text
get(1)
```

returns:

```text
2
```

After:

```text
deleteAtIndex(1)
```

the list becomes:

```text
1 → 3
```

Finally:

```text
get(1)
```

returns:

```text
3
```

---

# Core Idea

This problem is different from normal linked-list problems because we are not solving one operation.

We are **building the linked-list data structure itself**.

The main things we need to understand are:

```text
1. How to represent a node
2. How to maintain the head
3. How to traverse the list
4. How to insert a node
5. How to delete a node
```

The implementation uses:

```java
Node head;
```

to keep track of the first node.

---

# Node Structure

The solution defines an inner `Node` class:

```java
class Node{
    int val;
    Node next;

    Node(int val){
        this.val = val;
        this.next = null;
    }
}
```

Every node contains two things:

```text
Node
 ├── val
 └── next
```

For example:

```text
Node(10)
```

represents:

```text
10 → null
```

If we connect another node:

```text
10 → 20 → null
```

then:

```text
10.next
```

points to the node containing `20`.

---

# Java Solution

```java
class MyLinkedList {

    class Node{
        int val;
        Node next;

        Node(int val){
            this.val = val;
            this.next = null;
        }
    }

    Node head;

    public MyLinkedList() {
        head = null;
    }
    
    public int get(int index) {

        Node current = head;
        int count = 0;

        while(current != null){
            if(count == index){
                return current.val;
            }

            current = current.next;
            count++;
        }

        return -1;
    }
    
    public void addAtHead(int val) {

        Node dummy = new Node(val);
        dummy.next = head;
        head = dummy;
        
    }
    
    public void addAtTail(int val) {

        Node newNode = new Node(val);

        if(head == null){
            head = newNode;
            return;
        }

        Node current = head;

        while(current.next != null){
            current = current.next;
        }

        current.next = newNode;
        
    }
    
    public void addAtIndex(int index, int val) {
        
        if(index == 0){
            addAtHead(val);
            return;
        }

        int count = 0;
        Node current = head;

        while(current != null && count < index - 1){
            current = current.next;
            count++;
        }

        if(current == null)
            return;

        Node node = new Node(val);
        node.next = current.next;
        current.next = node;
        
    }
    
    public void deleteAtIndex(int index) {

        if(index == 0){
            head = head.next;
            return;
        }

        int count = 0;
        Node current = head;

        while(current != null && count < index - 1){
            current = current.next;
            count++;
        }

        if(current == null || current.next == null){
            return;
        }

        current.next = current.next.next;
    }
}
```

---

# 1. Creating the Linked List

The class stores:

```java
Node head;
```

Initially:

```java
public MyLinkedList() {
    head = null;
}
```

So the list starts empty:

```text
head
 ↓
null
```

When we add the first node:

```text
head
 ↓
10 → null
```

---

# 2. `get(index)`

The purpose is to find the node at a particular position.

Because this is a singly linked list, we cannot directly jump to an index.

For example:

```text
0    1    2    3
↓    ↓    ↓    ↓
10 → 20 → 30 → 40
```

To reach index `3`, we have to start from the head and follow:

```text
10 → 20 → 30 → 40
```

The implementation uses:

```java
Node current = head;
int count = 0;
```

Then:

```java
while(current != null){
```

traverses the list.

When:

```java
count == index
```

we return:

```java
current.val;
```

---

# `get()` Example

Suppose:

```text
10 → 20 → 30
```

and:

```text
get(1)
```

Start:

```text
current = 10
count = 0
```

Check:

```text
0 == 1
```

No.

Move:

```text
current = 20
count = 1
```

Now:

```text
1 == 1
```

Return:

```text
20
```

---

# Invalid Index

If traversal reaches:

```java
current == null
```

before finding the index, the index doesn't exist.

So:

```java
return -1;
```

For example:

```text
10 → 20 → 30
```

Calling:

```text
get(5)
```

returns:

```text
-1
```

---

# 3. `addAtHead(val)`

Adding at the head is the simplest insertion.

Suppose:

```text
head
 ↓
2 → 3 → 4
```

We want to add:

```text
1
```

Create:

```java
Node dummy = new Node(val);
```

This variable is actually a new node, despite being named `dummy`.

Then:

```java
dummy.next = head;
```

creates:

```text
1 → 2 → 3 → 4
```

Finally:

```java
head = dummy;
```

moves the head to the new node.

Result:

```text
head
 ↓
1 → 2 → 3 → 4
```

---

# Important Naming Note

In this method:

```java
Node dummy = new Node(val);
```

the variable is called `dummy`, but it is **not a dummy/sentinel node** in the usual linked-list sense.

It becomes the actual first node of the linked list:

```java
head = dummy;
```

A clearer name could be:

```java
Node newNode = new Node(val);
```

but the given implementation still works correctly.

---

# 4. `addAtTail(val)`

Adding at the tail is different because we need to reach the last node.

Suppose:

```text
1 → 2 → 3
```

We want:

```text
1 → 2 → 3 → 4
```

First create:

```java
Node newNode = new Node(val);
```

So:

```text
4 → null
```

Then check:

```java
if(head == null){
    head = newNode;
    return;
}
```

This handles the empty-list case.

---

# Empty List Case

If:

```text
head = null
```

and we call:

```text
addAtTail(5)
```

there is no existing node whose `next` we can modify.

So:

```java
head = newNode;
```

gives:

```text
5 → null
```

---

# Traversing to the Tail

If the list isn't empty:

```java
Node current = head;
```

Then:

```java
while(current.next != null){
    current = current.next;
}
```

moves `current` until it reaches the last node.

For:

```text
1 → 2 → 3 → null
```

we end at:

```text
1 → 2 → 3
         ↑
       current
```

because:

```text
current.next == null
```

Then:

```java
current.next = newNode;
```

produces:

```text
1 → 2 → 3 → 4
```

---

# 5. `addAtIndex(index, val)`

This is one of the most important operations.

The requirement is:

> Add the new node before the node currently at `index`.

For example:

```text
1 → 2 → 3
```

Calling:

```text
addAtIndex(1, 5)
```

should produce:

```text
1 → 5 → 2 → 3
```

---

# Why Do We Find `index - 1`?

To insert a node, we need access to the node **before** the insertion point.

Suppose:

```text
1 → 2 → 3
```

and:

```text
index = 1
```

We want:

```text
1 → 5 → 2 → 3
```

So we need:

```text
current = 1
```

which is index:

```text
index - 1 = 0
```

Therefore the loop uses:

```java
count < index - 1
```

---

# Insertion Operation

Once `current` points to the node before the insertion position:

```text
1 → 2 → 3
↑
current
```

Create:

```java
Node node = new Node(val);
```

Then:

```java
node.next = current.next;
```

gives:

```text
5 → 2 → 3
```

Finally:

```java
current.next = node;
```

gives:

```text
1 → 5 → 2 → 3
```

The two essential operations are:

```java
node.next = current.next;
current.next = node;
```

---

# Why Is the Order Important?

Suppose:

```text
current → 2 → 3
```

If we do:

```java
current.next = node;
```

first, we get:

```text
current → node
```

and we lose the original connection to `2`.

Therefore we first save the connection:

```java
node.next = current.next;
```

Then:

```java
current.next = node;
```

This gives:

```text
current → node → 2 → 3
```

This is a fundamental linked-list insertion pattern.

---

# `addAtIndex(0, val)`

The implementation handles index `0` separately:

```java
if(index == 0){
    addAtHead(val);
    return;
}
```

This is necessary because there is no node before index `0`.

For:

```text
1 → 2 → 3
```

calling:

```text
addAtIndex(0, 5)
```

gives:

```text
5 → 1 → 2 → 3
```

---

# Invalid `addAtIndex`

Suppose:

```text
1 → 2 → 3
```

and:

```text
addAtIndex(5, 10)
```

Index `5` is greater than the current length.

The traversal eventually reaches:

```text
current == null
```

So:

```java
if(current == null)
    return;
```

No insertion occurs.

---

# Index Equal to Length

The problem says that if:

```text
index == length
```

the node should be appended to the end.

For example:

```text
1 → 2 → 3
```

Length:

```text
3
```

Calling:

```text
addAtIndex(3, 4)
```

should give:

```text
1 → 2 → 3 → 4
```

The current implementation handles this naturally.

The loop stops at:

```text
current = 3
```

Then:

```java
node.next = current.next;
```

Since:

```text
current.next == null
```

we get:

```text
node.next = null
```

Then:

```java
current.next = node;
```

produces:

```text
1 → 2 → 3 → 4
```

---

# 6. `deleteAtIndex(index)`

Deletion is similar to insertion.

Suppose:

```text
1 → 2 → 3 → 4
```

We want to delete index `2`:

```text
1 → 2 → 3 → 4
        ↑
      delete
```

We need the node before it:

```text
1 → 2
    ↑
  current
```

Then:

```java
current.next = current.next.next;
```

changes:

```text
2 → 3 → 4
```

into:

```text
2 ─────→ 4
```

Final:

```text
1 → 2 → 4
```

---

# Deleting the Head

Index `0` is special because there is no previous node.

The solution handles it with:

```java
if(index == 0){
    head = head.next;
    return;
}
```

Suppose:

```text
head
 ↓
1 → 2 → 3
```

After:

```java
head = head.next;
```

we get:

```text
head
 ↓
2 → 3
```

The old first node is no longer part of the list.

---

# Invalid Delete

The implementation checks:

```java
if(current == null || current.next == null){
    return;
}
```

This handles cases where the requested index doesn't exist.

For example:

```text
1 → 2 → 3
```

Trying to delete index `5` eventually causes:

```text
current == null
```

so nothing happens.

---

# The Three Most Important Linked List Operations

This problem is essentially built around three pointer operations.

## Traversal

```java
current = current.next;
```

Move forward.

---

## Insertion

```java
node.next = current.next;
current.next = node;
```

Insert `node` after `current`.

---

## Deletion

```java
current.next = current.next.next;
```

Skip the node after `current`.

These three patterns are fundamental to implementing a singly linked list.

---

# Complete Example

Start:

```text
empty
```

### `addAtHead(1)`

```text
1
```

### `addAtTail(3)`

```text
1 → 3
```

### `addAtIndex(1, 2)`

Find node before index `1`:

```text
1
↑
current
```

Insert `2`:

```text
1 → 2 → 3
```

### `get(1)`

Traverse:

```text
index 0 → 1
index 1 → 2
```

Return:

```text
2
```

### `deleteAtIndex(1)`

Find node before index `1`:

```text
1 → 2 → 3
↑
current
```

Skip `2`:

```text
1 → 3
```

### `get(1)`

Index `1` now contains:

```text
3
```

Final output:

```text
[null, null, null, null, 2, null, 3]
```

---

# Edge Cases

## 1. Empty List

```text
head = null
```

Supported by:

```java
head == null
```

in insertion and traversal operations.

---

## 2. Adding to an Empty List

```text
addAtHead(5)
```

Result:

```text
5
```

---

## 3. Adding at the Tail of an Empty List

```text
addAtTail(5)
```

Result:

```text
5
```

---

## 4. Adding at Index 0

```text
1 → 2 → 3
```

```text
addAtIndex(0, 5)
```

Result:

```text
5 → 1 → 2 → 3
```

---

## 5. Adding at Index Equal to Length

```text
1 → 2 → 3
```

```text
addAtIndex(3, 4)
```

Result:

```text
1 → 2 → 3 → 4
```

---

## 6. Adding Beyond Length

```text
1 → 2 → 3
```

```text
addAtIndex(5, 4)
```

No change:

```text
1 → 2 → 3
```

---

## 7. Deleting the Head

```text
1 → 2 → 3
```

```text
deleteAtIndex(0)
```

Result:

```text
2 → 3
```

---

## 8. Deleting the Tail

```text
1 → 2 → 3
```

```text
deleteAtIndex(2)
```

Result:

```text
1 → 2
```

---

# Complexity

Let `n` be the current number of nodes.

### `get(index)`

```text
Time:  O(n)
Space: O(1)
```

We may need to traverse the entire list.

### `addAtHead(val)`

```text
Time:  O(1)
Space: O(1)
```

We directly modify `head`.

### `addAtTail(val)`

```text
Time:  O(n)
Space: O(1)
```

The implementation traverses to the final node.

### `addAtIndex(index, val)`

```text
Time:  O(n)
Space: O(1)
```

We may need to traverse to the node before the insertion point.

### `deleteAtIndex(index)`

```text
Time:  O(n)
Space: O(1)
```

We may need to traverse to the node before the deletion point.

The new node created during insertion is part of the data structure itself; the algorithm uses no additional traversal structure.

---

# Why Singly Linked List?

The problem allows either singly or doubly linked lists.

This solution chooses a singly linked list because every required operation can be implemented using:

```text
val
next
```

A doubly linked list would additionally store:

```text
prev
```

which would make backward traversal possible, but it isn't necessary for this implementation.

---

# Pattern Recognition

This problem is less about solving one linked-list problem and more about understanding how a linked list works internally.

The essential patterns are:

```text
Traverse:
current = current.next

Insert:
newNode.next = current.next
current.next = newNode

Delete:
current.next = current.next.next

Update Head:
head = head.next
```

Once these four operations are comfortable, implementing a basic singly linked list becomes much easier.

---

# Important Design Insight

The most important thing to understand is that a linked list does not provide direct indexing like an array.

For an array:

```text
arr[5]
```

can directly access index `5`.

For a linked list:

```text
get(5)
```

requires:

```text
head
 ↓
0 → 1 → 2 → 3 → 4 → 5
                    ↑
                  target
```

We must follow the `next` references one by one.

This is why operations such as `get`, `addAtIndex`, and `deleteAtIndex` are `O(n)` in this implementation.

---

# Key Takeaway

This problem teaches how to build a linked list from scratch instead of simply using Java's built-in `LinkedList`.

The most important pointer patterns are:

```java
// Traversal
current = current.next;
```

```java
// Insert
node.next = current.next;
current.next = node;
```

```java
// Delete
current.next = current.next.next;
```

```java
// Remove head
head = head.next;
```

The overall mental model is:

```text
              Linked List
                   |
          +--------+--------+
          |        |        |
       Traverse  Insert   Delete
          |        |        |
    next pointer   |        |
                   |        |
          reconnect pointers
```

Once you understand how `next` references are changed, most basic singly linked-list operations become straightforward.

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

92   → Reverse Linked List II
       └── Sublist Reversal + Front Insertion

707  → Design Linked List
       └── Linked List Implementation + Pointer Manipulation
```

---

# Final Mental Model

```text
Node:
[val | next]

      ↓

head
 ↓
[1] → [2] → [3] → null
```

To move:

```text
current = current.next
```

To insert after `current`:

```text
newNode.next = current.next;
current.next = newNode;
```

To delete after `current`:

```text
current.next = current.next.next;
```

To remove the first node:

```text
head = head.next;
```

These are the fundamental pointer operations behind a singly linked list.

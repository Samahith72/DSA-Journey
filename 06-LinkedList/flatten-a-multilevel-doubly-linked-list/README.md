# WIN #91 — 430. Flatten a Multilevel Doubly Linked List

[LeetCode — 430. Flatten a Multilevel Doubly Linked List](https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/)

**Difficulty:** Medium
**Topic:** Doubly Linked List, DFS, Recursion, Pointer Manipulation

---

## 1. Problem

We are given a **multilevel doubly linked list**.

Each node contains:

```text id="n9z3yw"
val
prev
next
child
```

The `child` pointer may point to another doubly linked list.

That child list can itself contain nodes with children, creating multiple levels.

We need to flatten the entire structure into a single-level doubly linked list.

The important ordering rule is:

> If a node has a child list, the child list must appear immediately after that node and before its original `next` node.

After flattening:

* Every node must belong to one single doubly linked list.
* Every `child` pointer must be `null`.
* `prev` and `next` pointers must be correctly connected.

---

# 2. Example

Consider:

```text id="7m0q0c"
1 → 2 → 3 → 4 → 5 → 6
        |
        7 → 8 → 9 → 10
            |
            11 → 12
```

Node `3` has a child list:

```text id="k4f0aq"
3.child → 7
```

Node `8` has another child:

```text id="b9e2d1"
8.child → 11
```

The flattened list should be:

```text id="p3g6aa"
1 → 2 → 3 → 7 → 8 → 11 → 12 → 9 → 10 → 4 → 5 → 6
```

The child list is inserted immediately after its parent.

---

# 3. Core Idea

The key challenge is that when we encounter a child list, we need to:

1. Remember the current node's original `next`.
2. Recursively flatten the child list.
3. Connect the current node to the child list.
4. Connect the end of the flattened child list to the original `next`.
5. Set `child = null`.

The recursive function returns the **tail of the flattened portion**.

This is the most important idea in the solution.

```text id="kz4p72"
Current Node
     |
     | child
     ↓
Child List
     ↓
Flatten Child
     ↓
Child Tail
```

Then:

```text id="u6u5cr"
current
   ↓
child list
   ↓
childTail
   ↓
original next
```

---

# 4. Java Solution

```java id="w3x5k1"
class Solution {
    public Node flatten(Node head) {
        if(head == null )
            return null;

        DFS(head);
        return head;
    }

    private Node DFS(Node current){
        Node tail = current;

        while(current != null){
            Node nextNode = current.next;

            //if no child
            if(current.child == null){
                tail = current;
            }
            // if there is child
            else{
                Node childHead = current.child;
                Node childTail = DFS(childHead);

                //connect the current to flatten child
                current.next = childHead;
                childHead.prev = current;
                current.child = null;

                //connect childtail to original list 
                if(nextNode != null){
                    childTail.next = nextNode;
                    nextNode.prev = childTail;
                }

                tail = childTail;
            }

            current = nextNode;
        }

        return tail;
    }
}
```

---

# 5. Step 1 — Handle an Empty List

```java id="4p8b8f"
if(head == null)
    return null;
```

If the list is empty, there is nothing to flatten.

So we immediately return `null`.

---

# 6. Step 2 — Start DFS

```java id="0f7g8z"
DFS(head);
return head;
```

The `DFS()` function performs the actual flattening.

The public method returns the original `head`.

The important thing is that the nodes themselves are reused. We are only changing their pointers.

---

# 7. The Important Part — `DFS()`

```java id="9f3nqv"
private Node DFS(Node current)
```

The function takes the beginning of a list and returns:

```text id="nq8y9e"
The tail of the flattened list
```

For example:

```text id="l6nq6h"
7 → 8 → 9
```

`DFS(7)` returns:

```text id="3hj7d7"
9
```

If there are deeper child lists, the returned node is the final tail after everything has been flattened.

---

# 8. Initialize the Tail

```java id="d9u5w8"
Node tail = current;
```

Initially, the tail is simply the current node.

As we traverse, `tail` gets updated.

The reason we need the tail is because after flattening a child list, we need to connect:

```text id="7v2b4f"
childTail → original next
```

---

# 9. Save the Original `next`

Inside the loop:

```java id="g7w2rc"
Node nextNode = current.next;
```

This is extremely important.

Suppose we have:

```text id="4y7c4a"
3 → 4
|
7 → 8
```

Before connecting the child list, we save:

```text id="8h1z4x"
nextNode = 4
```

Why?

Because we are about to change:

```java id="p8m8qz"
current.next = childHead;
```

Without saving `current.next` first, we could lose the original next part of the list.

So always remember:

> Save the pointer before rewiring it.

---

# 10. Case 1 — No Child

```java id="x6g7g0"
if(current.child == null){
    tail = current;
}
```

If the current node has no child, there is nothing to flatten.

We simply continue traversing.

For example:

```text id="1s7t0p"
1 → 2 → 3 → 4
```

If `current = 2` and:

```text id="2q9v94"
2.child == null
```

then:

```text id="j7x7nz"
tail = 2
```

and we continue to the next node.

---

# 11. Case 2 — Current Node Has a Child

This is where the main logic happens.

```java id="4uwq7a"
else{
    Node childHead = current.child;
    Node childTail = DFS(childHead);
```

First, save the child list's head:

```java id="6q7j8a"
Node childHead = current.child;
```

Then recursively flatten it:

```java id="o2h9wt"
Node childTail = DFS(childHead);
```

The recursive call returns the final node of the flattened child list.

---

# 12. Example of the Recursive Call

Suppose:

```text id="l0ik69"
3
|
7 → 8 → 9 → 10
    |
    11 → 12
```

When we are at `3`:

```text id="8n4zj7"
childHead = 7
```

We call:

```text id="ef1s0a"
DFS(7)
```

Inside that recursive call, node `8` has a child.

So it calls:

```text id="p3xg2v"
DFS(11)
```

That eventually flattens:

```text id="35b0kn"
11 → 12
```

Then the level starting at `7` becomes:

```text id="c6m5xz"
7 → 8 → 11 → 12 → 9 → 10
```

and:

```text id="2twg1v"
childTail = 10
```

is returned to the call processing node `3`.

---

# 13. Connect Current Node to Child

After flattening the child:

```java id="d5e2c1"
current.next = childHead;
childHead.prev = current;
```

Suppose:

```text id="e3q6z5"
3
|
7 → 8
```

We create:

```text id="k9u1k5"
3 → 7 → 8
↑
prev relationship
```

Specifically:

```text id="qz7s7m"
3.next = 7
7.prev = 3
```

Because this is a doubly linked list, we must update **both directions**.

---

# 14. Remove the Child Pointer

```java id="u4t0ve"
current.child = null;
```

The final flattened list must not contain any child pointers.

So after moving the child list into the main list:

```text id="7m0kq5"
current.child
      ↓
    null
```

This is required by the problem.

---

# 15. Connect the Child Tail to the Original List

We saved:

```java id="y3u7kg"
Node nextNode = current.next;
```

before modifying `current.next`.

So now we still know where the original list continues.

If:

```text id="8u7u1a"
3 → 4
|
7 → 8 → 9
```

after flattening the child:

```text id="kq3w4u"
3 → 7 → 8 → 9
```

we need:

```text id="5tq4y9"
9 → 4
```

So:

```java id="1a9wte"
if(nextNode != null){
    childTail.next = nextNode;
    nextNode.prev = childTail;
}
```

Now the complete structure is:

```text id="r1u5c0"
3 → 7 → 8 → 9 → 4
                ↑
              prev
```

---

# 16. Why `childTail` Is Important

We cannot connect the child list directly to the original `next`.

We need to connect the **end of the entire flattened child structure**.

For example:

```text id="7z0d0m"
3
|
7 → 8 → 9
    |
    11 → 12
```

After flattening:

```text id="g6i6j1"
7 → 8 → 11 → 12 → 9
```

The tail is:

```text id="0s4o9q"
12? 
```

Actually, because `9` comes after the child of `8`, the final tail of this child list is `9`.

So the recursive call must return:

```text id="u4l5gm"
9
```

Then:

```text id="j9w7zr"
9.next = original next
```

This is why returning the tail from `DFS()` is so useful.

---

# 17. Update the Tail

After connecting everything:

```java id="8f8g3c"
tail = childTail;
```

The tail of the flattened portion is now the tail returned by the recursive call.

---

# 18. Move to the Original Next Node

At the end:

```java id="mb2y0a"
current = nextNode;
```

Notice that this is the **original next node** that we saved before flattening the child.

This allows the traversal to continue with the remaining upper-level list.

---

# 19. Why We Don't Use `current.next`

This is an important pointer detail.

After:

```java
current.next = childHead;
```

`current.next` no longer points to the original next node.

It points to the child list.

That's why we previously stored:

```java
Node nextNode = current.next;
```

Then after flattening the child, we use:

```java
current = nextNode;
```

This preserves our ability to continue through the original list.

---

# 20. Complete Example

Consider:

```text id="s0b1yt"
1 → 2 → 3 → 4 → 5 → 6
        |
        7 → 8 → 9 → 10
            |
            11 → 12
```

We start:

```text id="h6b8l0"
current = 1
```

No child.

Move to `2`.

Then:

```text id="f1m3t5"
current = 3
```

Node `3` has a child.

Save:

```text id="yd3d3h"
nextNode = 4
```

Then:

```text id="f9l9s6"
DFS(7)
```

Inside that child list:

```text id="t2s7r4"
7 → 8 → 9 → 10
```

Node `8` has another child.

Save:

```text id="f2k6j1"
nextNode = 9
```

Flatten:

```text id="4s5jv9"
11 → 12
```

Connect:

```text id="t6j8p4"
8 → 11 → 12 → 9
```

The child list becomes:

```text id="7g4v5b"
7 → 8 → 11 → 12 → 9 → 10
```

Return its tail:

```text id="k5c9x2"
10
```

Back at node `3`, connect:

```text id="p9h2d0"
3 → 7 → 8 → 11 → 12 → 9 → 10 → 4
```

Then continue with:

```text id="2g0w9j"
4 → 5 → 6
```

Final:

```text id="4x8n2z"
1 → 2 → 3 → 7 → 8 → 11 → 12 → 9 → 10 → 4 → 5 → 6
```

---

# 21. What the Recursive Function Really Does

The easiest way to understand `DFS()` is:

> Flatten everything starting from `current` and return the final tail of that flattened section.

So:

```text id="9q9b4p"
DFS(current)
```

means:

```text
Flatten current's entire structure
        ↓
Return its final node
```

For example:

```text id="k8s4h0"
DFS(7)
```

could return:

```text
10
```

because after flattening everything below `7`, node `10` is the final node.

---

# 22. Edge Cases

### Empty list

```text id="q7x3jp"
head = null
```

Return:

```text id="y0j5d3"
null
```

---

### Single node

```text id="6j3h7w"
1
```

There is nothing to flatten.

Return the same node.

---

### Node with only a child

```text id="w6k4p0"
1
|
2
```

becomes:

```text id="p5k9s2"
1 → 2
```

and:

```text id="x7g5d1"
1.child = null
```

---

### Nested child

```text id="d5j8r3"
1
|
2
|
3
```

becomes:

```text id="e8k2m5"
1 → 2 → 3
```

All child pointers become `null`.

---

### Child at the end

```text id="n6z4q2"
1 → 2
    |
    3 → 4
```

becomes:

```text id="c2k8w5"
1 → 2 → 3 → 4
```

There is no original `next` after `2`, so:

```java id="p8f3q1"
if(nextNode != null)
```

prevents us from trying to connect to `null`.

---

# 23. Complexity

Let `n` be the total number of nodes across all levels.

### Time Complexity

```text id="7n1m3x"
O(n)
```

Every node is processed once during the flattening process.

### Space Complexity

The pointer manipulation itself uses:

```text id="0g5s4j"
O(1)
```

extra space.

However, because the solution uses recursion, the recursion stack can grow with the depth of the multilevel structure.

Therefore:

```text id="7c8x5p"
Auxiliary Space = O(d)
```

where `d` is the maximum nesting depth.

In the worst case:

```text id="2p6x4m"
O(n)
```

if the structure is deeply nested.

---

# 24. Important Pointer Pattern

This problem introduces an important pattern:

```text id="r4z8s1"
Save → Rewire → Reconnect
```

Whenever you are about to change a pointer:

### 1. Save what you are about to lose

```java id="y6d3q2"
Node nextNode = current.next;
```

### 2. Rewire

```java id="7s0m1k"
current.next = childHead;
childHead.prev = current;
```

### 3. Reconnect the remaining structure

```java id="8x3c9f"
childTail.next = nextNode;
nextNode.prev = childTail;
```

This is a very useful general technique for linked-list problems.

---

# 25. Pattern Recognition

When you see a linked list containing:

```text id="7g2d8s"
next
prev
child
```

and the requirement is to flatten nested structures, think:

```text id="v2h6q9"
DFS + Pointer Rewiring
```

The general strategy is:

```text id="4h8w2n"
Current node
    ↓
Has child?
  /     \
No       Yes
↓         ↓
Continue  Save next
          ↓
       DFS(child)
          ↓
      Get child tail
          ↓
       Reconnect
          ↓
       Continue
```

---

# 26. Linked List Patterns Learned So Far

| Problem                               | Pattern                     |
| ------------------------------------- | --------------------------- |
| Reverse Linked List                   | Three-pointer reversal      |
| Middle of Linked List                 | Fast and slow pointers      |
| Linked List Cycle                     | Floyd's cycle detection     |
| Linked List Cycle II                  | Visited nodes / cycle entry |
| Palindrome Linked List                | Middle + reverse + compare  |
| Remove Nth Node                       | Fixed-gap two pointers      |
| Swap Nodes                            | Fixed-gap two pointers      |
| Reorder List                          | Middle + reverse + merge    |
| Odd Even Linked List                  | Multiple pointer chains     |
| Rotate List                           | Circular connection + split |
| Partition List                        | Separate chains             |
| Copy List with Random Pointer         | HashMap object mapping      |
| Sort List                             | Merge Sort + split + merge  |
| Flatten Multilevel Doubly Linked List | DFS + pointer rewiring      |

The new major pattern is:

```text id="b4y8c2"
Recursive Structure + Pointer Rewiring
```

---

# 27. Final Mental Model

The most important thing to remember is:

```text id="0f6j9s"
current
   |
   | child
   ↓
child list
   ↓
DFS(child)
   ↓
child tail
   ↓
original next
```

Or more simply:

```text id="f3x9s2"
Save original next
        ↓
Flatten child
        ↓
Connect current → child
        ↓
Connect child tail → original next
        ↓
Set child = null
        ↓
Continue
```

For every node with a child:

```text id="p5w7d1"
Before:

current → next
   |
 child


After:

current → child → ... → childTail → next
```

The key idea is:

> When flattening a multilevel linked list, recursively flatten the child list, insert it between the current node and its original next node, and return the tail of the flattened section so the remaining list can be reconnected correctly.

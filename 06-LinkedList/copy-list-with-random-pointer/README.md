# WIN #89 — 138. Copy List with Random Pointer

[LeetCode — 138. Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/)

**Difficulty:** Medium
**Topic:** Linked List, HashMap, Deep Copy

---

## 1. Problem

Given a linked list where every node contains:

* `val`
* `next`
* `random`

The `next` pointer points to the next node in the list.

The `random` pointer can point to:

* Any node in the list
* `null`

We need to create a **deep copy** of the entire linked list.

The copied list must contain completely new nodes.

For every original node:

```text
Original Node → Corresponding Copy Node
```

The `next` and `random` relationships must be preserved.

Most importantly:

> No pointer in the copied list should point to a node from the original list.

---

# 2. Example

Consider:

```text
Original:

A → B → C

A.random → C
B.random → A
C.random → null
```

The copied list should be:

```text
Copy:

a → b → c

a.random → c
b.random → a
c.random → null
```

Notice that:

```text
A != a
B != b
C != c
```

They are completely different objects.

Only their relationships are the same.

---

# 3. Core Idea

The difficult part of this problem is the `random` pointer.

With a normal linked list, we can simply do:

```java
copy.next = original.next;
```

But with `random`, we don't know where the random pointer points.

For example:

```text
A → B → C → D

A.random → D
```

When we create the copy of `A`, we need to know:

```text
copyA.random = copyD
```

So we need a way to find:

```text
Original Node → Copied Node
```

This is exactly what a `HashMap` can provide.

We create:

```text
HashMap<Original Node, Copy Node>
```

For example:

```text
Original    Copy
   A   →     a
   B   →     b
   C   →     c
```

Then whenever we have an original node, we can immediately find its copied node.

---

# 4. Two-Pass Approach

The solution uses two traversals.

## Pass 1

Create a new node for every original node.

Store the relationship:

```text
original node → copied node
```

## Pass 2

Connect the `next` and `random` pointers using the map.

This separates the problem into:

```text
1. Create all nodes
2. Connect all nodes
```

---

# 5. Java Solution

```java
class Solution {
    public Node copyRandomList(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        Node current = head;

        while(current != null){
            map.put(current, new Node(current.val));
            current = current.next;
        }

        current = head;

        while(current != null){
            Node copy = map.get(current);
            copy.next = map.get(current.next);
            copy.random = map.get(current.random);
            current = current.next;
        }

        return map.get(head);
    }
}
```

---

# 6. Step-by-Step Explanation

## Step 1: Create the HashMap

```java
HashMap<Node, Node> map = new HashMap<>();
```

The map stores:

```text
Original Node → Copy Node
```

For example:

```text
map

A → a
B → b
C → c
```

---

# 7. First Traversal — Create the Nodes

```java
Node current = head;

while(current != null){
    map.put(current, new Node(current.val));
    current = current.next;
}
```

Suppose the original list is:

```text
A → B → C
```

with:

```text
A.val = 7
B.val = 13
C.val = 11
```

During the first traversal:

### First iteration

```java
map.put(A, new Node(7));
```

Map:

```text
A → a
```

### Second iteration

```java
map.put(B, new Node(13));
```

Map:

```text
A → a
B → b
```

### Third iteration

```java
map.put(C, new Node(11));
```

Map:

```text
A → a
B → b
C → c
```

At this point, all copied nodes exist.

But their pointers are not connected yet.

---

# 8. Why Can't We Set the Pointers Immediately?

Suppose:

```text
A.random → C
```

When we are processing `A`, we need:

```java
copyA.random = copyC;
```

But if `copyC` has not been created yet, we cannot reference it.

The first pass solves this problem by creating **all nodes first**.

After the first pass, we know:

```text
A → a
B → b
C → c
```

So now every original node has a corresponding copy.

---

# 9. Second Traversal

We reset:

```java
current = head;
```

Then traverse the original list again.

```java
while(current != null){
    Node copy = map.get(current);

    copy.next = map.get(current.next);
    copy.random = map.get(current.random);

    current = current.next;
}
```

---

# 10. Getting the Copy of the Current Node

```java
Node copy = map.get(current);
```

Suppose:

```text
current = B
```

Then:

```java
map.get(B)
```

returns:

```text
b
```

So:

```text
current = B
copy = b
```

---

# 11. Connecting the `next` Pointer

```java
copy.next = map.get(current.next);
```

Suppose:

```text
A → B
```

Then when:

```text
current = A
```

we have:

```text
current.next = B
```

Therefore:

```java
map.get(current.next)
```

becomes:

```java
map.get(B)
```

which returns:

```text
b
```

So:

```text
a.next = b
```

The copied `next` relationship is created.

---

# 12. Connecting the `random` Pointer

This is the main reason we use the HashMap.

```java
copy.random = map.get(current.random);
```

Suppose:

```text
A.random → C
```

Then:

```java
current.random
```

is `C`.

So:

```java
map.get(current.random)
```

becomes:

```java
map.get(C)
```

which returns:

```text
c
```

Therefore:

```text
a.random → c
```

The random relationship is correctly copied.

---

# 13. What Happens When `random == null`?

Suppose:

```text
C.random → null
```

Then:

```java
map.get(current.random)
```

becomes:

```java
map.get(null)
```

Java's `HashMap` allows `null` keys.

Since there is no mapping for `null`, it returns:

```text
null
```

Therefore:

```java
copy.random = null;
```

This is exactly what we want.

---

# 14. Complete Example

Suppose the input is:

```text
[[7,null],[13,0],[11,4],[10,2],[1,0]]
```

The structure is:

```text
7 → 13 → 11 → 10 → 1
```

Random pointers:

```text
7.random  → null
13.random → 7
11.random → 1
10.random → 11
1.random  → 7
```

After the first pass:

```text
Original    Copy

7     →      7'
13    →      13'
11    →      11'
10    →      10'
1     →      1'
```

Then the second pass creates:

```text
7' → 13' → 11' → 10' → 1'
```

and:

```text
7'.random  → null
13'.random → 7'
11'.random → 1'
10'.random → 11'
1'.random  → 7'
```

The copied list has exactly the same structure but completely different nodes.

---

# 15. Why This Is a Deep Copy

A shallow copy would reuse the original nodes.

For example:

```text
Original:
A → B → C

Bad Copy:
A → B → C
```

That is not a real copy.

Our solution creates:

```text
Original:
A → B → C

Copy:
a → b → c
```

The nodes are different objects:

```text
A != a
B != b
C != c
```

And all pointers in the copied list point only to copied nodes.

Therefore it is a **deep copy**.

---

# 16. Important Pattern

This problem teaches an important general technique:

```text
Object → Corresponding New Object
```

Whenever we need to clone a structure where objects have arbitrary relationships, a HashMap can help maintain the mapping.

The general pattern is:

```text
Original object
      ↓
HashMap
      ↓
Copied object
```

For this problem:

```text
Node → Node
```

---

# 17. Why We Need Two Passes

The two passes have different responsibilities.

### First pass

```text
Create every copied node
```

Result:

```text
Original → Copy
```

### Second pass

```text
Connect every copied node
```

Result:

```text
copy.next
copy.random
```

This makes the problem much easier to reason about.

The mental separation is:

```text
Pass 1:
"What nodes do I need?"

Pass 2:
"How should those nodes be connected?"
```

---

# 18. Edge Cases

### Empty list

```text
head = null
```

The first loop does nothing.

```java
return map.get(head);
```

becomes:

```java
return map.get(null);
```

which returns `null`.

---

### One node with no random pointer

```text
A → null
```

The copied node becomes:

```text
a → null
```

with:

```text
a.random = null
```

---

### One node pointing to itself

```text
A.next = null
A.random = A
```

The map contains:

```text
A → a
```

So:

```java
copy.random = map.get(A);
```

returns:

```text
a
```

Therefore:

```text
a.random → a
```

The self-reference is correctly preserved.

---

### Random pointer points backward

```text
A → B → C

C.random → A
```

The map allows us to immediately find the copy of `A`:

```text
map.get(A) → a
```

So:

```text
c.random → a
```

---

# 19. Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

We traverse the list twice.

```text
First pass  → O(n)
Second pass → O(n)

Total → O(n)
```

### Space Complexity

```text
O(n)
```

The HashMap stores one mapping for every original node.

The copied nodes themselves also require `O(n)` space because they are part of the required output.

The **auxiliary space used by the algorithm** is:

```text
O(n)
```

because of the HashMap.

---

# 20. Pattern Recognition

When you see a problem involving:

* Deep copying
* Arbitrary pointers
* Random relationships
* Objects referring to other objects
* Need to map original objects to new objects

Think:

```text
HashMap<Original, Copy>
```

The core pattern is:

```text
Original object
      ↓
   HashMap
      ↓
Copied object
```

For this problem:

```text
HashMap<Node, Node>
```

---

# 21. Linked List Patterns Learned So Far

| Problem                       | Pattern                     |
| ----------------------------- | --------------------------- |
| Reverse Linked List           | Three-pointer reversal      |
| Middle of Linked List         | Fast and slow pointers      |
| Linked List Cycle             | Floyd's cycle detection     |
| Linked List Cycle II          | Visited nodes / cycle entry |
| Palindrome Linked List        | Middle + reverse + compare  |
| Remove Nth Node               | Fixed-gap two pointers      |
| Swap Nodes                    | Fixed-gap two pointers      |
| Reorder List                  | Middle + reverse + merge    |
| Odd Even Linked List          | Multiple pointer chains     |
| Rotate List                   | Circular connection + split |
| Partition List                | Separate chains             |
| Copy List with Random Pointer | HashMap object mapping      |

This problem introduces an important new concept:

```text
Mapping original objects to copied objects
```

---

# 22. Final Mental Model

Think of the solution as building a dictionary:

```text
Original Node → Copy Node
```

First:

```text
Create all copies

A → a
B → b
C → c
```

Then:

```text
Use the dictionary to connect them

a.next   = copy of A.next
a.random = copy of A.random
```

So the complete mental model is:

```text
PASS 1
Original nodes
     ↓
Create copies
     ↓
Store Original → Copy


PASS 2
For every original node
     ↓
Find its copy
     ↓
Find copy of next
     ↓
Find copy of random
     ↓
Connect pointers
```

The key idea is:

> When copying a structure containing arbitrary references, first create a mapping from every original object to its copy, then use that mapping to reconstruct all relationships.

# WIN #61 — Insert Delete GetRandom O(1)

## Problem

[LeetCode 380 — Insert Delete GetRandom O(1)](https://leetcode.com/problems/insert-delete-getrandom-o1/)

Design a data structure that supports the following operations in **average O(1)** time:

* `insert(val)` — Insert `val` if it does not already exist.
* `remove(val)` — Remove `val` if it exists.
* `getRandom()` — Return a random element where every element has the same probability of being selected.

The main challenge is that **all three operations must work in average O(1)**.

A `HashSet` gives us:

```text
insert → O(1)
remove → O(1)
contains → O(1)
```

But it does not provide direct random access by index.

An `ArrayList` gives us:

```text
get(index) → O(1)
```

which is perfect for `getRandom()`.

But removing an arbitrary element from an `ArrayList` normally takes:

```text
O(n)
```

because elements after it need to be shifted.

The key idea is to combine:

```text
HashMap + ArrayList
```

The `HashMap` stores:

```text
value → index in ArrayList
```

while the `ArrayList` stores the actual values.

---

## Example

```text
Input:
["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]

[[], [1], [2], [2], [], [1], [2], []]

Output:
[null, true, false, true, 2, true, false, 2]
```

Operations:

```text
insert(1)
```

Set:

```text
[1]
```

Returns:

```text
true
```

---

```text
remove(2)
```

`2` does not exist.

Returns:

```text
false
```

---

```text
insert(2)
```

Set:

```text
[1,2]
```

Returns:

```text
true
```

---

```text
getRandom()
```

Can return:

```text
1
```

or:

```text
2
```

with equal probability.

---

```text
remove(1)
```

Set becomes:

```text
[2]
```

Returns:

```text
true
```

---

```text
insert(2)
```

`2` already exists.

Returns:

```text
false
```

---

```text
getRandom()
```

Only `2` remains, so:

```text
2
```

is returned.

---

# My Java Solution

```java
class RandomizedSet {
    ArrayList<Integer> list;
    HashMap<Integer, Integer> map;
    Random random;

    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        
        if(map.containsKey(val)){
            return false;
        }

        list.add(val);
        map.put(val, list.size() - 1);

        return true;
    }
    
    public boolean remove(int val) {

        if(!map.containsKey(val)){
            return false;
        }

        int index = map.get(val);
        int lastValue = list.get(list.size() - 1);

        list.set(index, lastValue);
        map.put(lastValue, index);

        list.remove(list.size() - 1);
        map.remove(val);

        return true;
    }
    
    public int getRandom() {

        int randomIndex = random.nextInt(list.size());

        return list.get(randomIndex);
    }
}
```

---

# Core Idea

The entire solution is based on maintaining two data structures:

```text
ArrayList
    +
HashMap
```

They have different responsibilities.

### ArrayList

The `ArrayList` allows:

```java
list.get(index)
```

in:

```text
O(1)
```

This makes it perfect for:

```text
getRandom()
```

because we can generate a random index and directly access the element.

---

### HashMap

The `HashMap` stores:

```text
value → index
```

For example:

```text
list = [10, 20, 30]
```

The HashMap is:

```text
10 → 0
20 → 1
30 → 2
```

This allows us to find the position of any value in average:

```text
O(1)
```

---

# Why Do We Need Both?

The two data structures solve different problems.

```text
             RandomizedSet
                  │
          ┌───────┴───────┐
          ↓               ↓
      HashMap          ArrayList
          │               │
    value → index      values
          │               │
     O(1) lookup       O(1) access
          │               │
      insert/remove    getRandom
```

The HashMap tells us:

> **Where is this value?**

The ArrayList tells us:

> **What value is stored at this index?**

Together they allow all three operations to be O(1) average.

---

# Step 1 — Create the Data Structures

```java
ArrayList<Integer> list;
HashMap<Integer, Integer> map;
Random random;
```

We maintain:

```text
list
```

for storing values.

```text
map
```

for storing:

```text
value → index
```

And:

```text
random
```

for generating random indices.

---

# Step 2 — Initialize Everything

```java
public RandomizedSet() {
    list = new ArrayList<>();
    map = new HashMap<>();
    random = new Random();
}
```

Initially:

```text
list = []
map = {}
```

There are no elements.

---

# Insert Operation

The requirement is:

```text
insert(val)
```

should return:

```text
true
```

if `val` was not already present.

Otherwise:

```text
false
```

---

## Step 3 — Check Whether the Value Exists

```java
if(map.containsKey(val)){
    return false;
}
```

Because the HashMap stores every value currently in the set, we can check its existence in average:

```text
O(1)
```

For example:

```text
map = {
    10 → 0,
    20 → 1
}
```

Calling:

```text
insert(20)
```

finds `20` immediately.

Therefore:

```text
false
```

is returned.

---

# Step 4 — Add the Value to the ArrayList

```java
list.add(val);
```

Suppose:

```text
list = [10,20]
```

and we call:

```text
insert(30)
```

After adding:

```text
list = [10,20,30]
```

The new value is at:

```text
index = 2
```

---

# Step 5 — Store the Index in the HashMap

```java
map.put(val, list.size() - 1);
```

For:

```text
list = [10,20,30]
```

we store:

```text
30 → 2
```

So the structures become:

```text
list:
[10,20,30]
```

and:

```text
map:
10 → 0
20 → 1
30 → 2
```

Now we can find the index of any value in average O(1).

---

# Why Insert Is O(1)

The operations are:

```text
HashMap.containsKey()
ArrayList.add()
HashMap.put()
```

All are average:

```text
O(1)
```

Therefore:

```text
insert = O(1) average
```

---

# The Difficult Part — Remove

The challenge is:

```text
remove(val)
```

Suppose:

```text
list = [10,20,30,40,50]
```

and we want to remove:

```text
30
```

The index is:

```text
2
```

If we simply call:

```java
list.remove(2);
```

the ArrayList becomes:

```text
[10,20,40,50]
```

But `40` and `50` had to shift left.

That takes:

```text
O(n)
```

which violates the requirement.

So we need a trick.

---

# The Swap-With-Last Trick

Instead of shifting elements, we replace the element we want to remove with the **last element**.

Original:

```text
[10,20,30,40,50]
```

We want to remove:

```text
30
```

The last element is:

```text
50
```

Replace `30` with `50`:

```text
[10,20,50,40,50]
```

Then remove the final element:

```text
[10,20,50,40]
```

No elements need to be shifted.

This makes removal O(1).

---

# Step 6 — Check Whether the Value Exists

```java
if(!map.containsKey(val)){
    return false;
}
```

If the value doesn't exist, there is nothing to remove.

Because the HashMap provides average O(1) lookup:

```text
remove(non-existing value) → O(1)
```

---

# Step 7 — Find the Index of the Value

```java
int index = map.get(val);
```

The HashMap tells us exactly where the value is located.

For:

```text
list = [10,20,30,40,50]
```

and:

```text
map:
10 → 0
20 → 1
30 → 2
40 → 3
50 → 4
```

For:

```text
remove(30)
```

we get:

```text
index = 2
```

---

# Step 8 — Find the Last Value

```java
int lastValue = list.get(list.size() - 1);
```

For:

```text
list = [10,20,30,40,50]
```

we get:

```text
lastValue = 50
```

---

# Step 9 — Replace the Removed Element

```java
list.set(index, lastValue);
```

Before:

```text
[10,20,30,40,50]
```

After:

```text
[10,20,50,40,50]
```

We have effectively moved the last element into the position of the removed value.

---

# Step 10 — Update the Last Value's Index

This step is extremely important:

```java
map.put(lastValue, index);
```

Originally:

```text
50 → 4
```

But after moving `50` to index `2`, the map must become:

```text
50 → 2
```

Otherwise the HashMap would contain an incorrect index.

So:

```text
Before:
50 → 4

After:
50 → 2
```

---

# Step 11 — Remove the Last Element

```java
list.remove(list.size() - 1);
```

The ArrayList currently contains:

```text
[10,20,50,40,50]
```

Remove the final `50`:

```text
[10,20,50,40]
```

The element we wanted to remove is now gone.

---

# Step 12 — Remove the Value From the HashMap

```java
map.remove(val);
```

We removed:

```text
30
```

so:

```text
30 → 2
```

must also be removed.

The final structures are:

```text
list:
[10,20,50,40]
```

and:

```text
map:
10 → 0
20 → 1
50 → 2
40 → 3
```

Everything is synchronized.

---

# Complete Remove Walkthrough

Suppose:

```text
list = [10,20,30,40,50]
```

and:

```text
map = {
    10 → 0,
    20 → 1,
    30 → 2,
    40 → 3,
    50 → 4
}
```

Call:

```text
remove(30)
```

### 1. Find index

```text
30 → 2
```

So:

```text
index = 2
```

### 2. Get last value

```text
lastValue = 50
```

### 3. Replace

```text
[10,20,50,40,50]
```

### 4. Update map

```text
50 → 2
```

### 5. Remove last element

```text
[10,20,50,40]
```

### 6. Remove 30 from map

Final:

```text
list:
[10,20,50,40]
```

```text
map:
10 → 0
20 → 1
50 → 2
40 → 3
```

No shifting was required.

---

# Special Case — Removing the Last Element

Suppose:

```text
list = [10,20,30]
```

and we call:

```text
remove(30)
```

Then:

```text
index = 2
lastValue = 30
```

The line:

```java
list.set(index, lastValue);
```

essentially does nothing.

Then:

```java
list.remove(list.size() - 1);
```

removes `30`.

Finally:

```java
map.remove(30);
```

Everything remains correct.

So the same logic handles this case automatically.

---

# getRandom()

The requirement says:

> Every element must have the same probability of being returned.

The ArrayList gives us exactly what we need.

---

## Step 13 — Generate a Random Index

```java
int randomIndex = random.nextInt(list.size());
```

Suppose:

```text
list.size() = 4
```

Then:

```java
random.nextInt(4)
```

can produce:

```text
0
1
2
3
```

with equal probability.

---

# Step 14 — Return the Element

```java
return list.get(randomIndex);
```

Because ArrayList provides O(1) random access:

```text
list.get(index)
```

takes:

```text
O(1)
```

Therefore:

```text
getRandom() = O(1)
```

---

# Why Every Element Has Equal Probability

Suppose:

```text
list = [10,20,30,40]
```

There are four possible indices:

```text
0
1
2
3
```

`random.nextInt(4)` selects each index with equal probability.

Therefore:

```text
10 → 25%
20 → 25%
30 → 25%
40 → 25%
```

So every element has the same probability of being returned.

---

# Why We Cannot Use Only a HashMap

A HashMap is excellent for:

```text
contains → O(1) average
insert → O(1) average
remove → O(1) average
```

But it does not provide:

```text
get element by random index
```

in the way required by `getRandom()`.

We need a structure where:

```text
index → value
```

is directly accessible.

That is why we use an ArrayList.

---

# Why We Cannot Use Only an ArrayList

An ArrayList provides:

```text
get(index) → O(1)
```

which makes `getRandom()` easy.

But:

```java
list.remove(index)
```

can take:

```text
O(n)
```

because elements after the removed position have to shift.

For example:

```text
[10,20,30,40,50]
```

Removing `20` requires:

```text
30 → shift left
40 → shift left
50 → shift left
```

Therefore:

```text
O(n)
```

We need the HashMap to find the element's index and the swap-with-last trick to remove it efficiently.

---

# The Complete Data Structure

The relationship between the two structures is:

```text
ArrayList:
Index    Value
  0       10
  1       20
  2       30
  3       40
```

HashMap:

```text
Value → Index

10 → 0
20 → 1
30 → 2
40 → 3
```

Both structures must always remain synchronized.

If we move a value inside the ArrayList:

```text
[10,20,40,30]
```

the HashMap must also be updated:

```text
40 → 2
30 → 3
```

This synchronization is the key to the solution.

---

# Pattern Recognition

## Pattern: HashMap + ArrayList

This is a classic data-structure design problem.

Whenever a problem requires:

```text
Fast lookup by value
+
Fast random access by index
+
Fast insertion/removal
```

think about combining:

```text
HashMap + ArrayList
```

The responsibilities are:

```text
HashMap
    ↓
value → index
    ↓
Fast lookup
```

and:

```text
ArrayList
    ↓
index → value
    ↓
Fast random access
```

The special technique for removal is:

```text
Replace target with last element
        ↓
Update last element's index
        ↓
Remove last element
```

This avoids the O(n) shifting performed by normal ArrayList removal.

---

# Important Pattern to Remember

The most important trick in this problem is:

> **To remove an arbitrary element from an ArrayList in O(1), replace it with the last element and remove the last element. Use a HashMap to maintain the index of every value.**

This pattern appears in many data-structure design problems.

---

# Complete Operation Flow

## Insert

```text
insert(val)
     ↓
Does val exist?
     ↓
YES → return false
     ↓
NO
     ↓
Add val to ArrayList
     ↓
Store val → index in HashMap
     ↓
return true
```

---

## Remove

```text
remove(val)
     ↓
Does val exist?
     ↓
NO → return false
     ↓
YES
     ↓
Find val's index
     ↓
Get last element
     ↓
Move last element to val's position
     ↓
Update last element's index
     ↓
Remove final ArrayList element
     ↓
Remove val from HashMap
     ↓
return true
```

---

## Get Random

```text
getRandom()
     ↓
Generate random index
     ↓
list.get(randomIndex)
     ↓
return value
```

---

# Example State Changes

Starting state:

```text
list = []
map = {}
```

### insert(10)

```text
list = [10]

map:
10 → 0
```

### insert(20)

```text
list = [10,20]

map:
10 → 0
20 → 1
```

### insert(30)

```text
list = [10,20,30]

map:
10 → 0
20 → 1
30 → 2
```

### remove(20)

Move `30` into index `1`:

```text
list = [10,30]
```

Update:

```text
map:
10 → 0
30 → 1
```

### getRandom()

Possible values:

```text
10
30
```

with equal probability.

---

# Edge Cases

## 1. Insert Duplicate

```text
list = [10,20]
```

Call:

```text
insert(20)
```

Since:

```text
20
```

already exists:

```text
return false
```

The data structure remains unchanged.

---

## 2. Remove Non-Existing Value

```text
list = [10,20]
```

Call:

```text
remove(30)
```

Since `30` isn't in the HashMap:

```text
return false
```

---

## 3. Remove the Only Element

```text
list = [10]
```

Call:

```text
remove(10)
```

After removal:

```text
list = []
map = {}
```

---

## 4. Remove the Last Element

```text
list = [10,20,30]
```

Call:

```text
remove(30)
```

The last element is already the target.

Simply remove it:

```text
list = [10,20]
```

---

## 5. Negative Values

The constraints allow:

```text
-231 <= val <= 231 - 1
```

The HashMap and ArrayList work with negative integers without any special handling.

For example:

```text
insert(-10)
insert(-20)
```

works exactly the same way.

---

# Why the Overall Complexity Is O(1)

## Insert

```text
HashMap.containsKey() → O(1) average
ArrayList.add()       → O(1) amortized
HashMap.put()         → O(1) average
```

Therefore:

```text
O(1) average
```

---

## Remove

```text
HashMap.containsKey() → O(1) average
HashMap.get()         → O(1) average
ArrayList.get()       → O(1)
ArrayList.set()       → O(1)
HashMap.put()         → O(1) average
ArrayList.remove(last)→ O(1)
HashMap.remove()      → O(1) average
```

Therefore:

```text
O(1) average
```

---

## getRandom

```text
Random.nextInt() → O(1)
ArrayList.get()  → O(1)
```

Therefore:

```text
O(1)
```

---

# Complexity Summary

| Operation     |  Time Complexity | Reason                            |
| ------------- | ---------------: | --------------------------------- |
| `insert()`    | **O(1) average** | HashMap lookup + ArrayList append |
| `remove()`    | **O(1) average** | Swap with last element            |
| `getRandom()` |         **O(1)** | Random index + ArrayList access   |
| Space         |         **O(n)** | HashMap + ArrayList               |

The problem specifically asks for **average O(1)** because HashMap operations have average O(1) complexity.

---

# Why the Swap Trick Works

Normally:

```text
ArrayList.remove(index)
```

is expensive because elements after the index shift.

Instead:

```text
[10,20,30,40,50]
       ↑
     remove
```

We don't care about maintaining the original ordering of the elements.

So we can do:

```text
[10,20,50,40,50]
```

and then:

```text
[10,20,50,40]
```

The order changes, but the problem does **not** require any particular order.

That freedom is what allows us to achieve O(1) removal.

---

# The Most Important Insight

The solution works because each data structure compensates for the other's weakness:

```text
HashMap
    ↓
Fast value lookup
    ↓
But no direct random indexing
```

and:

```text
ArrayList
    ↓
Fast random indexing
    ↓
But expensive arbitrary deletion
```

Combine them:

```text
              RandomizedSet
                   │
          ┌────────┴────────┐
          ↓                 ↓
      HashMap            ArrayList
          │                 │
    value → index        index → value
          │                 │
    Find quickly         Access quickly
          │                 │
          └───────┬─────────┘
                  ↓
       Swap-with-last removal
```

This gives us:

```text
insert    → O(1) average
remove    → O(1) average
getRandom → O(1)
```

---

# Final Takeaway

The complete idea is:

```text
HashMap + ArrayList
```

The HashMap maintains:

```text
value → index
```

The ArrayList maintains:

```text
index → value
```

For insertion:

```text
Append to ArrayList
+
Store its index in HashMap
```

For removal:

```text
Find index from HashMap
        ↓
Get last ArrayList element
        ↓
Move last element into removed element's position
        ↓
Update its index in HashMap
        ↓
Remove last ArrayList element
        ↓
Delete removed value from HashMap
```

For random access:

```text
Generate random index
        ↓
ArrayList.get(index)
```

The main pattern to remember is:

> **HashMap gives O(1) average lookup, ArrayList gives O(1) random access, and replacing the element to remove with the last element gives O(1) deletion.**

This combination is what makes all three required operations possible in **average O(1)** time.

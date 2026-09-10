# WIN #28 — Boats to Save People

## Problem

You are given an array `people` where `people[i]` represents the weight of a person.

Each boat:

* Can carry at most **two people**.
* Has a maximum weight capacity of `limit`.

Return the **minimum number of boats** needed to carry everyone.

For example:

```text
Input:
people = [1,2]
limit = 3

Output:
1
```

The two people can share one boat:

```text
1 + 2 = 3
```

which is within the limit.

---

## Example 2

```text
Input:
people = [3,2,2,1]
limit = 3
```

One possible arrangement is:

```text
Boat 1 → (1,2)
Boat 2 → (2)
Boat 3 → (3)
```

Therefore:

```text
Output = 3
```

---

## My Java Solution

```java
class Solution {
    public int numRescueBoats(int[] people, int limit) {

        int left =0;
        int right = people.length-1;

        int boats = 0;

        Arrays.sort(people);

        while(left <= right){
            if(people[left] + people[right] <= limit){
                left++;
                right--;
            }else{
                right--;
            }

            boats++;
        }

        return boats;
    }
}
```

---

## My Thought Process

The goal is to use the **minimum number of boats**.

Since each boat can carry at most two people, I want to pair people whenever possible.

The important question is:

> Which two people should I try to pair?

I sort the array first:

```java
Arrays.sort(people);
```

Then I use two pointers:

```text
left  → lightest remaining person
right → heaviest remaining person
```

The strategy is:

```text
Try lightest + heaviest
        ↓
Can they fit?
   ↙          ↘
 Yes           No
  ↓             ↓
Both share    Heaviest
one boat      goes alone
```

This greedy decision gives the minimum number of boats.

---

## Step 1: Sort the Array

Suppose:

```text
people = [3,2,2,1]
limit = 3
```

After sorting:

```text
[1,2,2,3]
```

Now:

```text
left → 1
right → 3
```

The pointers represent the lightest and heaviest people who haven't been placed in a boat yet.

---

## Step 2: Try to Pair Lightest and Heaviest

The first check is:

```text
1 + 3 = 4
```

But:

```text
4 > 3
```

So they cannot share a boat.

Therefore, the heaviest person `3` must take a boat alone.

```text
Boat 1 → (3)
```

We move:

```java
right--;
```

and count one boat:

```java
boats++;
```

---

## Step 3: Try Again

Now:

```text
[1,2,2,3]
 ↑   ↑
left right
```

The remaining people are:

```text
[1,2,2]
```

Try:

```text
1 + 2 = 3
```

They fit.

So:

```text
Boat 2 → (1,2)
```

Both people are handled, so:

```java
left++;
right--;
```

---

## Step 4: Last Person

One person remains:

```text
[1,2,2,3]
     ↑
```

That person needs one boat:

```text
Boat 3 → (2)
```

Now:

```text
left > right
```

and the process ends.

Final answer:

```text
3
```

---

## Complete Walkthrough

For:

```text
people = [3,2,2,1]
limit = 3
```

After sorting:

```text
[1,2,2,3]
```

### First iteration

```text
left = 1
right = 3
```

Check:

```text
1 + 3 = 4 > 3
```

Cannot pair.

```text
Boat → (3)
```

Move:

```text
right--
```

---

### Second iteration

```text
left = 1
right = 2
```

Check:

```text
1 + 2 = 3 <= 3
```

Can pair.

```text
Boat → (1,2)
```

Move:

```text
left++
right--
```

---

### Third iteration

One person remains:

```text
2
```

```text
Boat → (2)
```

Move both pointers past each other.

Final:

```text
boats = 3
```

---

## Why Always Try the Lightest With the Heaviest?

This is the key greedy insight.

Suppose the heaviest person weighs:

```text
8
```

and the limit is:

```text
10
```

We have:

```text
[1,2,3,4,8]
```

Can the `8` share a boat?

Only with someone weighing at most:

```text
10 - 8 = 2
```

So the **lightest person** gives the heaviest person the best possible chance of sharing a boat.

If even the lightest person cannot fit:

```text
lightest + heaviest > limit
```

then nobody else can fit with the heaviest person, because everyone else weighs at least as much as the lightest person.

Therefore, the heaviest person must go alone.

This makes the decision greedy and safe.

---

## The Two Cases

There are only two possibilities.

### Case 1: They Can Share

```java
if(people[left] + people[right] <= limit)
```

Then:

```text
lightest + heaviest
```

can share a boat.

So both are finished:

```java
left++;
right--;
```

and we count one boat.

---

### Case 2: They Cannot Share

```java
else
```

If:

```text
people[left] + people[right] > limit
```

then the heaviest person cannot share with **anyone**.

Why?

Because `people[left]` is already the lightest remaining person.

If even:

```text
lightest + heaviest
```

is too heavy, every other possible partner would also be too heavy.

Therefore:

```text
heaviest → one boat
```

and only:

```java
right--;
```

is needed.

---

## Pattern Recognition

### Pattern: Sorting + Two Pointers + Greedy

This problem combines three important ideas.

### 1. Sorting

Sorting allows us to know:

```text
left  = lightest
right = heaviest
```

### 2. Two Pointers

The two pointers let us consider the best possible pair for the heaviest person.

```text
left → lightest
right → heaviest
```

### 3. Greedy

At every step:

> Try to put the heaviest person with the lightest person.

If they fit, pair them.

If they don't, the heaviest person goes alone.

The overall pattern is:

```text
Sort
  ↓
Lightest ←       → Heaviest
  ↓                   ↓
Try to pair them
  ↓
Can fit?
 ↙     ↘
Yes     No
 ↓       ↓
Pair    Heaviest alone
 ↓       ↓
Move    Move right
both
  ↓
Count one boat
```

---

## Why Does the Greedy Approach Give the Minimum?

The heaviest person is the most difficult person to accommodate.

We should therefore try to pair them with the **lightest possible person**.

There are two situations:

### They Fit

If:

```text
lightest + heaviest <= limit
```

then pairing them saves a boat.

Instead of:

```text
Boat 1 → heaviest
Boat 2 → lightest
```

we can use:

```text
Boat 1 → heaviest + lightest
```

So using one boat for both is always beneficial.

### They Don't Fit

If:

```text
lightest + heaviest > limit
```

then the heaviest person cannot pair with anyone.

So giving them their own boat is unavoidable.

Therefore, every step makes a decision that cannot increase the minimum possible number of boats.

---

## Why Does `left <= right` Matter?

The loop is:

```java
while(left <= right)
```

The `<=` is important because one person can remain.

For example:

```text
people = [3]
```

Initially:

```text
left = 0
right = 0
```

We still need to process that person.

The condition:

```text
left <= right
```

is true.

So we count:

```text
boats = 1
```

Then the pointers cross and the loop ends.

---

## Complexity

### Time Complexity

First, the array is sorted:

```text
O(n log n)
```

Then the two pointers move through the array at most once:

```text
O(n)
```

Therefore:

```text
O(n log n) + O(n)
```

which simplifies to:

```text
O(n log n)
```

### Space Complexity

The algorithm uses only a few variables:

```text
left
right
boats
```

and modifies the `people` array in place.

Therefore, the auxiliary space is:

```text
O(1)
```

excluding any internal space used by the sorting implementation.

---

## Key Learning

* When trying to minimize the number of pairs/groups, look for a **greedy strategy**.
* Sorting can make greedy decisions much easier.
* The heaviest person should always be considered first.
* Pair the heaviest person with the lightest person whenever possible.
* If the lightest person cannot fit with the heaviest, nobody can.
* Two pointers allow us to efficiently process the sorted array.
* `left <= right` ensures that a single remaining person is also counted.
* The solution achieves **O(n log n) time** and **O(1) auxiliary space**.

---

## Final Takeaway

The entire solution can be remembered as:

```text
Sort the people
      ↓
left = lightest
right = heaviest
      ↓
Can lightest + heaviest fit?
      ↓
   Yes          No
    ↓            ↓
Pair them     Heaviest alone
    ↓            ↓
left++        right--
right--
    ↓
Count one boat
      ↓
Repeat
```

The most important insight is:

> **Always try the lightest person with the heaviest person. If even they cannot fit together, the heaviest person must go alone.**

This is a classic example of how **Sorting + Two Pointers + Greedy** can turn a potentially complicated pairing problem into a simple linear scan after sorting.

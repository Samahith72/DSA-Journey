# WIN #48 — Minimum Recolors to Get K Consecutive Black Blocks

## Problem

[LeetCode 2379 — Minimum Recolors to Get K Consecutive Black Blocks](https://leetcode.com/problems/minimum-recolors-to-get-k-consecutive-black-blocks/)

You are given a string `blocks` containing:

* `'B'` → Black block
* `'W'` → White block

You are also given an integer `k`.

The goal is to obtain at least `k` **consecutive black blocks**.

In one operation, we can recolor a white block:

```text
W → B
```

Return the **minimum number of recoloring operations** required.

### Key Observation

If we choose any window of exactly `k` blocks, the number of white blocks inside that window tells us exactly how many recoloring operations are required.

For example:

```text
[B, W, B, W, B]
```

For `k = 5`, there are two white blocks:

```text
W + W = 2 recolors
```

After recoloring:

```text
[B, B, B, B, B]
```

So the problem becomes:

> **Find the window of size `k` containing the minimum number of white blocks.**

---

## Example 1

```text
Input: blocks = "WBBWWBBWBW"
       k = 7

Output: 3
```

Consider the first window:

```text
W B B W W B B
```

It contains:

```text
W W W? 
```

Actually, the white blocks are:

```text
W
    W W
```

so there are `3` white blocks.

Recoloring those three blocks gives:

```text
B B B B B B B
```

Therefore, `3` operations are required.

---

## Example 2

```text
Input: blocks = "WBWBBBW"
       k = 2

Output: 0
```

There is already a window:

```text
BB
```

containing zero white blocks.

Therefore, no recoloring is required.

---

## My Java Solution

```java id="0r6d2c"
class Solution {
    public int minimumRecolors(String blocks, int k) {
        int white = 0;

        for(int i = 0; i< k ;i++){
            if(blocks.charAt(i) == 'W'){
                white++;
            }
        }

        int answer = white;

        for(int i=k; i< blocks.length();i++){
            if(blocks.charAt(i) == 'W'){
                white++;
            }

            if(blocks.charAt(i-k) == 'W'){
                white--;
            }

            answer = Math.min(answer, white);
        }
        return answer;
    }
}
```

---

## My Thought Process

The problem asks for:

```text
k consecutive black blocks
```

So we should look at every possible **window of size `k`**.

For example:

```text
blocks = "WBBWWBBWBW"
k = 7
```

The windows are:

```text
[W B B W W B B] W B W
 W [B B W W B B W] B W
 W B [B W W B B W B] W
 ...
```

For each window, we only care about:

```text
How many W's are inside?
```

Why?

Because every white block needs exactly one recoloring operation.

So:

```text
Number of W's = Number of recoloring operations
```

Then we simply find the window with the smallest number of white blocks.

---

# Step 1 — Count White Blocks in the First Window

First:

```java id="c6k8rp"
int white = 0;
```

This variable represents:

> Number of white blocks in the current window.

Then we process the first `k` blocks:

```java id="9q2v5e"
for(int i = 0; i < k; i++){
    if(blocks.charAt(i) == 'W'){
        white++;
    }
}
```

For:

```text id="7sk6d9"
blocks = "WBBWWBBWBW"
k = 7
```

the first window is:

```text id="v6s1gq"
W B B W W B B
```

White blocks:

```text id="x7h4w8"
W
      W W
```

So:

```text id="5w0x3a"
white = 3
```

---

# Step 2 — Store the First Answer

The first window is currently the best window we have seen.

So:

```java id="n6w4c2"
int answer = white;
```

Initially:

```text id="k4g0x8"
answer = 3
```

This represents the minimum number of recolors found so far.

---

# Step 3 — Slide the Window

Now we start from:

```java id="1t6m2r"
i = k
```

and move through the rest of the string:

```java id="q7x1c4"
for(int i = k; i < blocks.length(); i++){
```

When the window moves one position to the right:

```text id="c8j2m5"
Old window:
[W B B W W B B]

New window:
[ B B W W B B W ]
```

One block leaves from the left.

One block enters from the right.

We can update the white count instead of counting the entire window again.

---

# Step 4 — Add the Incoming Block

The new block entering the window is:

```java id="x9c5v1"
blocks.charAt(i)
```

If it is white:

```java id="p2k7m6"
if(blocks.charAt(i) == 'W'){
    white++;
}
```

So we increase the white count.

For example:

```text id="5g0t4y"
Old window → 3 white blocks
Incoming block → W
```

Then:

```text id="v4b8n2"
white = 4
```

---

# Step 5 — Remove the Outgoing Block

The element leaving the window is at:

```text id="q1d7h8"
i - k
```

So:

```java id="3m6r9p"
if(blocks.charAt(i-k) == 'W'){
    white--;
}
```

If the outgoing block was white, it should no longer be included in the current window.

For example:

```text id="j7x2c9"
Old window has 3 white blocks
Outgoing block = W
```

After removing it:

```text id="n5v8b1"
white = 2
```

---

# Step 6 — Update the Minimum

After adding the incoming block and removing the outgoing block:

```java id="w4p6z3"
answer = Math.min(answer, white);
```

This keeps the smallest number of white blocks found in any window.

Since:

```text
white = number of recolors required
```

the smallest `white` value is exactly the answer.

---

# Step 7 — Return the Minimum

After checking every possible window:

```java id="q8m3v7"
return answer;
```

This gives the minimum number of recoloring operations needed.

---

## Complete Walkthrough

Consider:

```text id="k7f3x1"
blocks = "WBBWWBBWBW"
k = 7
```

### First Window

```text id="a3n8p2"
W B B W W B B
```

White blocks:

```text id="d5q1r7"
3
```

Therefore:

```text id="m8v4c6"
white = 3
answer = 3
```

---

### Move the Window

The next window is:

```text id="z2x7m9"
B B W W B B W
```

The new block entering is:

```text id="p6c3v1"
W
```

So:

```text id="b5n8q4"
white++
```

Now:

```text id="9k2m6x"
white = 4
```

But the first block leaving the previous window is:

```text id="t4r7p3"
W
```

So:

```text id="w8c1m5"
white--
```

Now:

```text id="q6x3v9"
white = 3
```

Update:

```text id="r2n7k4"
answer = min(3, 3)
answer = 3
```

The same process continues for every possible window.

The minimum white count found is:

```text id="z8p4m2"
3
```

Therefore:

```text id="x1c7v5"
Output = 3
```

---

## Another Walkthrough

Consider:

```text id="y7k3m1"
blocks = "WBWBBBW"
k = 2
```

### First Window

```text id="p4c8x2"
W B
```

White count:

```text id="r6n1v9"
1
```

So:

```text id="m3q7k5"
answer = 1
```

---

### Second Window

Slide:

```text id="h8x2c6"
B W
```

One white block:

```text id="d4p9m1"
white = 1
```

Answer remains:

```text id="z7k3v8"
1
```

---

### Third Window

```text id="q2m6x9"
W B
```

Again:

```text id="s4c8n1"
white = 1
```

---

### Fourth Window

```text id="v5r1k7"
B B
```

There are no white blocks:

```text id="j8m3x6"
white = 0
```

Therefore:

```text id="f2c7p4"
answer = 0
```

We can already see that no recoloring is necessary.

Final result:

```text id="n6v9k2"
0
```

---

## Pattern Recognition

### Pattern: Fixed-Size Sliding Window

The strongest clue is:

```text id="5x8m2q"
k consecutive blocks
```

We need to examine every contiguous window of exactly `k` elements.

This is a classic:

> **Fixed-Size Sliding Window**

problem.

The general structure is:

```text id="c7v1n9"
Build first window
       ↓
Calculate some property
       ↓
Slide one position
       ↓
Add incoming element
       ↓
Remove outgoing element
       ↓
Update answer
```

Here, the property we maintain is:

```text id="b4m8x2"
Number of white blocks
```

---

## The Important Observation

The problem asks:

> How many recolors are needed to make `k` consecutive blocks black?

Suppose a window is:

```text id="q3n7v5"
B W B W B
```

There are:

```text id="m6x1c8"
2 white blocks
```

To make the entire window black:

```text id="r4p9k2"
B B B B B
```

we must recolor exactly those two white blocks.

Therefore:

```text id="s8v3m7"
recolors required = number of W's
```

This converts the original problem into:

> **Find the size-`k` window with the minimum number of white blocks.**

---

## Why Sliding Window Is Better Than Recounting

A brute-force approach could count all the white blocks in every window.

For each window:

```text id="x4m8q1"
Count all k elements
```

There can be approximately `n` windows.

That gives:

```text id="v7c2p9"
O(n × k)
```

time.

Instead, the sliding window reuses the previous count.

When the window moves:

```text id="n5x3m8"
newWhite = oldWhite
            + incoming W
            - outgoing W
```

So each window takes only constant work.

This gives:

```text id="q1v6c4"
O(n)
```

time.

---

## Edge Cases

### 1. `k = 1`

```text id="m7x2c9"
blocks = "WBW"
k = 1
```

Each window contains one block.

There is already a black block:

```text id="z8p3n6"
B
```

So:

```text id="j4v7m1"
Output = 0
```

---

### 2. Entire String Is the Window

```text id="c6x9p2"
blocks = "WWBB"
k = 4
```

There is only one possible window:

```text id="t3m8v5"
W W B B
```

There are two white blocks.

Therefore:

```text id="n7q1x4"
Output = 2
```

---

### 3. Already All Black

```text id="y5m2c8"
blocks = "BBBBB"
k = 3
```

Every window contains zero white blocks.

Therefore:

```text id="p9v4k1"
Output = 0
```

---

### 4. All White

```text id="r6x1m7"
blocks = "WWWWW"
k = 3
```

Every window contains three white blocks.

Therefore:

```text id="c8q3v5"
Output = 3
```

---

### 5. Minimum Recolors Occurs in the Middle

For example:

```text id="x2m7k4"
blocks = "WWBBWB"
k = 3
```

Windows:

```text id="a6v1p8"
WWB → 2 white
WBB → 1 white
BBW → 1 white
BWB → 1 white
```

The minimum is:

```text id="n3c8q5"
1
```

So only one recoloring is required.

---

## Complexity

### Time Complexity

The first window takes:

```text id="v4m7x2"
O(k)
```

Then we slide through the remaining elements once:

```text id="p8c1n6"
O(n-k)
```

Therefore:

```text id="q5v9m3"
O(k) + O(n-k) = O(n)
```

Overall:

```text id="j2x6c8"
O(n)
```

---

### Space Complexity

Only two variables are used:

```text id="w7m3p1"
white
answer
```

No additional data structure is created.

Therefore:

```text id="c4x8n2"
O(1)
```

auxiliary space.

---

## Key Learning

### 1. Count What Needs to Change

Instead of trying to simulate every recoloring operation, ask:

> How many blocks are already wrong inside this window?

Here:

```text id="m5v8q2"
B → already correct
W → needs recoloring
```

So counting `W` directly gives the number of operations.

---

### 2. Fixed Window + Minimum Value

This problem follows a useful variation of the sliding-window pattern:

```text id="x3c7n9"
Fixed-size window
        +
Calculate a property
        +
Find minimum
```

Here:

```text id="p1m6v4"
Property = number of W's
Goal = minimum
```

---

### 3. Update the Window Incrementally

Remember:

```text id="v8q2m5"
Add incoming
Remove outgoing
```

Instead of recalculating the entire window.

This is one of the most important sliding-window techniques.

---

### 4. The Answer Is Not the Number of Black Blocks

It is tempting to count black blocks.

But the number of operations required is actually:

```text id="n4c7x1"
number of white blocks
```

because only white blocks need to be recolored.

---

## Final Takeaway

The core idea is:

```text id="r3m8q6"
Take a window of size k
        ↓
Count the W blocks
        ↓
W blocks = recolors needed
        ↓
Slide the window
        ↓
Add incoming W if needed
        ↓
Remove outgoing W if needed
        ↓
Track the minimum
```

The pattern to remember is:

> **For a fixed-size window where some elements need to be changed, count the elements that violate the desired condition and find the minimum such count.**

This solution achieves **O(n) time** and **O(1) auxiliary space**.

# WIN #51 — Number of Recent Calls

## Problem

[LeetCode 933 — Number of Recent Calls](https://leetcode.com/problems/number-of-recent-calls/)

We need to design a `RecentCounter` that keeps track of requests made at different points in time.

Whenever `ping(t)` is called:

1. Add the new request at time `t`.
2. Consider only requests in the inclusive time range:

```text
[t - 3000, t]
```

3. Remove all requests that happened more than `3000` milliseconds ago.
4. Return the number of remaining requests.

The problem guarantees that every `ping()` call has a strictly increasing timestamp.

---

## Example

```text
Input:
["RecentCounter", "ping", "ping", "ping", "ping"]
[[], [1], [100], [3001], [3002]]

Output:
[null, 1, 2, 3, 3]
```

Let's understand what happens.

### `ping(1)`

Range:

```text
[-2999, 1]
```

Requests:

```text
[1]
```

Answer:

```text
1
```

---

### `ping(100)`

Range:

```text
[-2900, 100]
```

Requests:

```text
[1, 100]
```

Both are inside the range.

Answer:

```text
2
```

---

### `ping(3001)`

Range:

```text
[1, 3001]
```

Requests:

```text
[1, 100, 3001]
```

All three are valid.

Answer:

```text
3
```

---

### `ping(3002)`

Range:

```text
[2, 3002]
```

Requests:

```text
[1, 100, 3001, 3002]
```

The request at `1` is now outside the range because:

```text
1 < 2
```

Remove it.

Remaining:

```text
[100, 3001, 3002]
```

Answer:

```text
3
```

---

## My Java Solution

```java
class RecentCounter {

    Queue<Integer> queue;
    public RecentCounter() {
        queue = new LinkedList<>();
    }
    
    public int ping(int t) {
        queue.add(t);

        while(queue.peek() < t-3000){
            queue.poll();
        }

        return queue.size();
    }
}

/**
 * Your RecentCounter object will be instantiated and called as such:
 * RecentCounter obj = new RecentCounter();
 * int param_1 = obj.ping(t);
 */
```

---

## My Thought Process

The most important observation is:

> The timestamps are strictly increasing.

For example:

```text
1 → 100 → 3001 → 3002 → 7000
```

This means requests always arrive in chronological order.

So we can store them in a queue:

```text
Oldest                              Newest
  ↓                                    ↓
[ 1 ][ 100 ][ 3001 ][ 3002 ]
  ↑                                    ↑
front                                back
```

The oldest request is always at the front.

When a new timestamp `t` arrives, we only need to remove timestamps that are too old.

The valid range is:

```text
[t - 3000, t]
```

Therefore, any timestamp satisfying:

```text
timestamp < t - 3000
```

must be removed.

This is exactly what the queue is good at.

---

# Step 1 — Create the Queue

```java
Queue<Integer> queue;
```

The queue stores timestamps of recent requests.

Then in the constructor:

```java
public RecentCounter() {
    queue = new LinkedList<>();
}
```

we initialize an empty queue.

Initially:

```text
queue = []
```

---

# Step 2 — Add the New Request

Every time `ping(t)` is called:

```java
queue.add(t);
```

We add the new timestamp to the back of the queue.

For example:

```text
ping(1)
```

gives:

```text
[1]
```

Then:

```text
ping(100)
```

gives:

```text
[1, 100]
```

Then:

```text
ping(3001)
```

gives:

```text
[1, 100, 3001]
```

Because timestamps are strictly increasing, the queue remains sorted automatically.

---

# Step 3 — Remove Expired Requests

The valid range is:

```text
[t - 3000, t]
```

Therefore, anything smaller than:

```text
t - 3000
```

is too old.

Your code checks:

```java
while(queue.peek() < t-3000){
    queue.poll();
}
```

There are two important queue operations here.

### `queue.peek()`

Returns the oldest timestamp without removing it.

### `queue.poll()`

Removes the oldest timestamp from the queue.

So the loop means:

> While the oldest request is older than the allowed time range, remove it.

---

# Step 4 — Return the Number of Valid Requests

Once all expired requests have been removed:

```java
return queue.size();
```

The queue now contains exactly the requests inside:

```text
[t - 3000, t]
```

Therefore, its size is the answer.

---

## Complete Walkthrough

Let's trace your exact solution with:

```text
ping(1)
ping(100)
ping(3001)
ping(3002)
```

---

### `ping(1)`

First:

```java
queue.add(1);
```

Queue:

```text
[1]
```

Check:

```text
1 < 1 - 3000
1 < -2999
```

False.

Nothing is removed.

Return:

```text
1
```

---

### `ping(100)`

Add:

```text
[1, 100]
```

Check:

```text
1 < 100 - 3000
1 < -2900
```

False.

Return:

```text
2
```

---

### `ping(3001)`

Add:

```text
[1, 100, 3001]
```

Allowed range:

```text
[1, 3001]
```

Check:

```text
1 < 1
```

False.

Nothing is removed.

Return:

```text
3
```

---

### `ping(3002)`

Add:

```text
[1, 100, 3001, 3002]
```

Allowed range:

```text
[2, 3002]
```

Now check the oldest timestamp:

```text
1 < 2
```

True.

Remove `1`.

Queue becomes:

```text
[100, 3001, 3002]
```

Check again:

```text
100 < 2
```

False.

Stop.

Return:

```text
3
```

---

# Why a Queue Is Perfect Here

The queue follows the exact order in which requests arrive:

```text
First request
      ↓
   Queue front
      ↓
Older requests
      ↓
Newer requests
      ↓
   Queue back
```

When a request becomes invalid, it will always be the **oldest request**.

Therefore, we only need to check the front.

This gives us:

```text
add new request → back
remove expired request → front
```

which is exactly the FIFO behavior of a queue.

---

## Pattern Recognition

### Pattern: Queue + Sliding Time Window

The important clue is:

```text
requests within [t - 3000, t]
```

We have a window that moves forward in time.

Think of it as:

```text
        Sliding Time Window
┌──────────────────────────────┐
│                              │
[t - 3000] ---------------- [t]
     ↑                         ↑
   oldest                   newest
```

As `t` increases, the window moves forward.

Requests that fall behind the left boundary are removed.

Because timestamps arrive in increasing order, a queue naturally maintains the requests from oldest to newest.

---

## The Core Pattern

Whenever you see:

> Process events in chronological order and keep only events from the recent time window.

Think:

```text
Queue
+
Sliding Window
```

The general structure is:

```text
Add new event
     ↓
Remove expired events from front
     ↓
Use remaining queue
```

---

## Why the `while` Loop Is Important

You might wonder why we use:

```java
while(queue.peek() < t-3000)
```

instead of:

```java
if(queue.peek() < t-3000)
```

There may be multiple expired requests.

For example:

```text
queue = [1, 10, 50, 3000]
t = 6000
```

The valid range is:

```text
[3000, 6000]
```

Therefore:

```text
1   → remove
10  → remove
50  → remove
3000 → keep
```

We need to continue removing until the oldest remaining request is valid.

That's why we use `while`.

---

## Important Detail: Inclusive Range

The range is:

```text
[t - 3000, t]
```

It is **inclusive**.

Therefore, a timestamp exactly equal to:

```text
t - 3000
```

is still valid.

Your condition is:

```java
queue.peek() < t-3000
```

not:

```java
queue.peek() <= t-3000
```

For example:

```text
t = 3001
```

The lower boundary is:

```text
3001 - 3000 = 1
```

Timestamp `1` is valid.

Your condition:

```text
1 < 1
```

is false, so `1` remains in the queue.

This is exactly correct.

---

## Edge Cases

### 1. First Request

```text
ping(1)
```

Queue:

```text
[1]
```

Answer:

```text
1
```

---

### 2. Requests Within 3000 ms

For:

```text
1, 100, 500, 2000
```

all requests remain inside the appropriate recent window.

The queue keeps them.

---

### 3. Request Exactly 3000 ms Old

Suppose:

```text
t = 3001
```

and an earlier request happened at:

```text
1
```

Since:

```text
3001 - 1 = 3000
```

the request is still valid.

Your code correctly keeps it.

---

### 4. Request More Than 3000 ms Old

If:

```text
t = 3002
```

then:

```text
3002 - 1 = 3001
```

The request is now too old.

Since:

```text
1 < 2
```

it is removed.

---

### 5. Multiple Expired Requests

The `while` loop removes all expired timestamps from the front before returning the answer.

---

## Complexity

Let `n` be the total number of calls to `ping()`.

### Time Complexity

At first glance, the `while` loop might look like it could make the solution `O(n²)`.

However, each timestamp is:

* added to the queue exactly once
* removed from the queue at most once

Therefore, across **all** calls to `ping()`, each element is processed a constant number of times.

So the amortized time complexity is:

```text
O(n)
```

or:

```text
O(1) amortized per ping()
```

---

### Space Complexity

In the worst case, all requests can still be inside the 3000 ms window.

Therefore, the queue can contain up to `n` timestamps.

Space complexity:

```text
O(n)
```

---

## Key Learning

### 1. Increasing Timestamps Make the Queue Possible

The problem guarantees:

```text
t1 < t2 < t3 < t4 ...
```

Because of this, timestamps automatically enter the queue in sorted order.

We don't need to sort anything.

---

### 2. Queue Is Ideal for Expiring Old Data

The oldest request is always at:

```java
queue.peek()
```

If it has expired:

```java
queue.poll();
```

This is a natural FIFO operation.

---

### 3. Sliding Window Does Not Always Mean Two Pointers

Earlier sliding-window problems in this repository often used:

```text
left
right
```

pointers.

Here, the same concept is implemented using a:

```text
Queue
```

The queue itself represents the current window.

---

### 4. Think in Terms of "Active Data"

The queue contains only:

```text
recent requests
```

Expired requests are continuously removed.

So at every point:

```text
Queue = Active requests inside the current time window
```

This is a very useful way to think about streaming problems.

---

## Queue Visualization

The queue can be visualized as:

```text
Oldest                                      Newest
  ↓                                            ↓
┌────┬─────┬──────┬──────┐
│ 100│ 500 │ 2000 │ 3001 │
└────┴─────┴──────┴──────┘
  ↑
front

          Recent Time Window
        [t - 3000 -------- t]
```

When the window moves forward:

```text
Old requests
     ↓
┌────┬─────┬──────┬──────┐
│ 100│ 500 │ 2000 │ 3001 │
└────┴─────┴──────┴──────┘
  ↑
expired
```

we remove them from the front until the queue contains only valid requests.

---

## Final Takeaway

The entire solution can be remembered as:

```text
New timestamp arrives
        ↓
Add it to Queue
        ↓
Calculate lower boundary
        ↓
t - 3000
        ↓
Remove all timestamps
smaller than the boundary
        ↓
Queue now contains only
recent requests
        ↓
Return queue.size()
```

The main pattern to remember is:

> **When events arrive in chronological order and you need to maintain only events inside a moving time range, use a Queue as a sliding time window.**

This is a foundational **Queue + Sliding Window** pattern that will appear in many streaming, event-processing, and time-based problems.

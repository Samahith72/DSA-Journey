# WIN #114 — 853. Car Fleet

**LeetCode:** [853. Car Fleet](https://leetcode.com/problems/car-fleet/)  
**Difficulty:** Medium  
**Topics:** Array, Sorting, Stack, Monotonic Stack

---

## Problem Statement

There are `n` cars traveling toward the same destination, located at position `target`.

You are given two integer arrays:

- `position[i]`: The starting position of the `i`th car.
- `speed[i]`: The speed of the `i`th car.

A car cannot pass another car. However, it can catch up with a car ahead of it and form a fleet.

When a car catches up with another car, it travels at the speed of the slower car.

A car that reaches the destination at the same time as another car is considered part of the same fleet.

Return the number of car fleets that will arrive at the destination.

### Example 1

**Input:**
```text
target = 12
position = [10, 8, 0, 5, 3]
speed = [2, 4, 1, 1, 3]
```

**Output:**
```text
3
```

**Explanation:**

The cars are sorted by starting position, from closest to the destination to farthest.

| Position | Speed | Arrival time |
|---:|---:|---:|
| 10 | 2 | 1 |
| 8 | 4 | 1 |
| 5 | 1 | 7 |
| 3 | 3 | 3 |
| 0 | 1 | 12 |

- The car at position `8` catches up with the car at position `10` because both would otherwise reach the destination at the same time. They form one fleet.
- The car at position `5` forms a separate fleet.
- The car at position `3` catches up with the fleet ahead of it.
- The car at position `0` forms another fleet.

The total number of fleets is `3`.

---

## Core Idea

The problem requires us to determine which cars reach the destination independently and which cars catch up with fleets ahead of them.

The important observation is:

**A car can only catch up with a car or fleet that is ahead of it.**

Therefore, we should process cars in descending order of their starting positions, beginning with the car closest to the destination.

Your solution uses:

1. Sorting to arrange cars from closest to farthest.
2. Arrival-time calculation to determine how long each car takes to reach the destination.
3. A stack to maintain the arrival times of independent fleets.

### Arrival Time Formula

For every car:

```text
Arrival Time = (Target - Position) / Speed
```

For example:

```text
Target = 12
Position = 10
Speed = 2

Arrival Time = (12 - 10) / 2
             = 1
```

### Main Strategy

1. Create an array containing the original indices of all cars.
2. Sort those indices based on their positions in descending order.
3. Traverse the sorted indices.
4. Calculate the arrival time for each car.
5. If the stack is empty, push the arrival time because the car forms a new fleet.
6. If the current car takes longer to arrive than the fleet ahead, push its arrival time because it cannot catch that fleet.
7. Otherwise, do not push it because it will catch up with the fleet ahead.
8. Return the stack size as the number of fleets.

---

## Java Solution

```java
class Solution {
    public int carFleet(int target, int[] position, int[] speed) {

        Stack<Double> fleet = new Stack<>();
        int n = position.length;

        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, (a, b) ->
            Integer.compare(position[b], position[a])
        );

        for (int i = 0; i < n; i++) {
            int idx = indices[i];

            double arrivalTime =
                (double) (target - position[idx]) / speed[idx];

            if (fleet.isEmpty()) {
                fleet.push(arrivalTime);
                continue;
            }

            if (arrivalTime > fleet.peek()) {
                fleet.push(arrivalTime);
            }
        }

        return fleet.size();
    }
}
```

---

## Step-by-Step Explanation

### 1. Initialize the Stack

```java
Stack<Double> fleet = new Stack<>();
```

The stack stores the arrival times of independent car fleets.

Each value represents the time at which a fleet reaches the destination.

We use `Double` because arrival times can contain decimal values.

For example:

```text
Arrival time = 2.5
```

Using integer division would incorrectly truncate this value.

### 2. Get the Number of Cars

```java
int n = position.length;
```

The number of cars is determined by the length of the `position` array.

The `speed` array contains the corresponding speed for each car.

### 3. Create an Index Array

```java
Integer[] indices = new Integer[n];

for (int i = 0; i < n; i++) {
    indices[i] = i;
}
```

Instead of sorting the original `position` and `speed` arrays, your solution creates an array containing their indices.

For example:

```text
position = [10, 8, 0, 5, 3]
speed    = [2, 4, 1, 1, 3]

indices  = [0, 1, 2, 3, 4]
```

Each index connects the corresponding position and speed.

For example:

```text
indices[0] = 0

position[0] = 10
speed[0] = 2
```

This allows us to sort the cars without losing the relationship between their positions and speeds.

### 4. Sort Cars by Position in Descending Order

```java
Arrays.sort(indices, (a, b) ->
    Integer.compare(position[b], position[a])
);
```

This is one of the most important steps.

We sort the indices according to their corresponding positions, from highest to lowest.

Why?

Because the car closest to the destination must be processed first.

For example:

```text
Original positions:

[10, 8, 0, 5, 3]

Sorted indices:

[0, 1, 3, 4, 2]

Sorted positions:

[10, 8, 5, 3, 0]
```

Now we process cars from the front toward the back.

This makes it possible to determine whether a car catches up with an already processed fleet.

### 5. Calculate the Arrival Time

```java
int idx = indices[i];

double arrivalTime =
    (double) (target - position[idx]) / speed[idx];
```

For every car, retrieve its original index and calculate its arrival time.

The formula is:

```text
Arrival Time = Distance / Speed
```

For example:

```text
Target = 12
Position = 8
Speed = 4

Arrival Time = (12 - 8) / 4
             = 1
```

The explicit cast to `double` ensures that the division retains decimal precision.

### 6. Handle the First Fleet

```java
if (fleet.isEmpty()) {
    fleet.push(arrivalTime);
    continue;
}
```

The first processed car is the closest to the destination.

Since no fleet exists ahead of it, it must form a new fleet.

Its arrival time is pushed onto the stack.

The `continue` statement skips the remaining statements in the current iteration and moves to the next car.

### 7. Determine Whether a New Fleet Is Formed

```java
if (arrivalTime > fleet.peek()) {
    fleet.push(arrivalTime);
}
```

This is the key condition.

The stack's top represents the arrival time of the fleet immediately ahead of the current car.

There are two possibilities.

**Case 1: Current arrival time is greater**

```text
Current arrival time > Fleet arrival time
```

The current car is slower relative to the fleet ahead and cannot catch up before the destination.

Therefore, it forms a new fleet.

Push its arrival time onto the stack.

**Case 2: Current arrival time is smaller than or equal to the fleet ahead**

```text
Current arrival time <= Fleet arrival time
```

The current car can catch up with the fleet ahead before or at the destination.

It becomes part of that fleet.

No new entry is pushed onto the stack.

This also handles the case where two cars reach the destination at exactly the same time.

### 8. Return the Number of Fleets

```java
return fleet.size();
```

Each entry in the stack represents one independent fleet.

Therefore, the stack size gives the final number of car fleets.

---

## Dry Run

Consider:

```text
target = 12

position = [10, 8, 0, 5, 3]
speed    = [2, 4, 1, 1, 3]
```

### Step 1: Sort the Indices

Original indices:

```text
[0, 1, 2, 3, 4]
```

After sorting by position in descending order:

```text
[0, 1, 3, 4, 2]
```

The processing order becomes:

| Processing order | Original index | Position | Speed |
|---:|---:|---:|---:|
| 1 | 0 | 10 | 2 |
| 2 | 1 | 8 | 4 |
| 3 | 3 | 5 | 1 |
| 4 | 4 | 3 | 3 |
| 5 | 2 | 0 | 1 |

### Step 2: Calculate Arrival Times

| Position | Speed | Calculation | Arrival time |
|---:|---:|---|---:|
| 10 | 2 | `(12 - 10) / 2` | 1 |
| 8 | 4 | `(12 - 8) / 4` | 1 |
| 5 | 1 | `(12 - 5) / 1` | 7 |
| 3 | 3 | `(12 - 3) / 3` | 3 |
| 0 | 1 | `(12 - 0) / 1` | 12 |

### Step 3: Process Each Car

| Position | Arrival time | Comparison | Action | Stack |
|---:|---:|---|---|---|
| 10 | 1 | Stack is empty | Push `1` | `[1]` |
| 8 | 1 | `1 <= 1` | Joins the fleet ahead | `[1]` |
| 5 | 7 | `7 > 1` | Forms a new fleet | `[1, 7]` |
| 3 | 3 | `3 <= 7` | Joins the fleet ahead | `[1, 7]` |
| 0 | 12 | `12 > 7` | Forms a new fleet | `[1, 7, 12]` |

### Final Stack

```text
[1, 7, 12]
```

Each entry represents one independent fleet.

**Output:**
```text
3
```

---

## Understanding the Important Condition

```java
if (arrivalTime > fleet.peek()) {
    fleet.push(arrivalTime);
}
```

This condition determines whether a new fleet is formed.

Consider:

```text
Car A: Arrival time = 2
Car B: Arrival time = 5
```

Car A is ahead of Car B.

Since Car B would take longer to reach the destination, it cannot catch Car A.

They form separate fleets.

Now consider:

```text
Car A: Arrival time = 5
Car B: Arrival time = 2
```

Car B is behind Car A but would reach the destination earlier if traveling independently.

Since cars cannot pass one another, Car B eventually catches up with Car A.

They form one fleet.

This is why the algorithm only pushes a new arrival time when it is greater than the arrival time of the fleet ahead.

---

## Edge Cases

| Case | Position | Speed | Expected output | Explanation |
|---|---|---|---:|---|
| No cars | `[]` | `[]` | `0` | No fleets exist. |
| Single car | `[5]` | `[2]` | `1` | One car forms one fleet. |
| All cars move at the same speed | `[3, 5, 7]` | `[1, 1, 1]` | `3` | Cars maintain their relative distances. |
| All cars form one fleet | `[1, 2, 3]` | `[3, 2, 1]` | `1` | Cars behind catch up with the slower fleet ahead. |
| Equal arrival times | `[10, 8]` | `[2, 4]` | `1` | Both cars reach the destination together. |
| No collisions | `[10, 5, 0]` | `[1, 1, 1]` | `3` | Each car maintains its distance from the car ahead. |

---

## Complexity Analysis

Let `n` be the number of cars.

### Time Complexity: O(n log n)

Creating the index array takes O(n).

Sorting the indices based on position takes O(n log n).

The subsequent traversal processes every car once. Each car's arrival time is calculated in constant time, and each stack entry is pushed at most once.

Therefore, the overall time complexity is:

**O(n log n)**

### Space Complexity: O(n)

The index array requires O(n) space.

The stack can also store up to `n` arrival times in the worst case, when every car forms an independent fleet.

Therefore, the overall auxiliary space complexity is:

**O(n)**

---

## Pattern Recognition

**Pattern: Sorting + Monotonic Stack + Greedy**

This problem combines sorting and a stack to identify groups that merge based on their arrival times.

Recognize this approach when:

- Objects have starting positions and speeds.
- Objects move toward a common destination.
- Objects cannot overtake one another.
- Objects can merge into groups when they catch up.
- Processing order matters.

### Recognition Rule

For Car Fleet:

1. Sort cars by starting position in descending order.
2. Calculate each car's independent arrival time.
3. Compare it with the arrival time of the fleet ahead.
4. If the current arrival time is greater, create a new fleet.
5. Otherwise, the current car joins the fleet ahead.

The important insight is that once a car joins a fleet, its original independent arrival time no longer determines the fleet's arrival time.

The fleet ahead controls the arrival time.

---

## Key Takeaways

1. Sort cars by position in descending order to process the front car first.
2. Preserve the relationship between position and speed by sorting indices instead of the original arrays.
3. Calculate arrival time using `(target - position) / speed`.
4. Use `double` to preserve fractional arrival times.
5. If the current arrival time is greater than the fleet ahead, a new fleet is formed.
6. If the current arrival time is smaller than or equal to the fleet ahead, the car joins that fleet.
7. The stack size represents the total number of independent fleets.
8. Sorting dominates the time complexity, giving O(n log n).

**Final Mental Model:** Start with the car closest to the destination. Calculate its arrival time and treat it as a fleet. Move backward through the remaining cars. If a car would arrive later than the fleet ahead, it cannot catch up and forms a new fleet. Otherwise, it joins the existing fleet.